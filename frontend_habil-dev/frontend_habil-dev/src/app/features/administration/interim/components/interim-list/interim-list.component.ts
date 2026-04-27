import {
  Component,
  OnInit,
  OnDestroy,
  ChangeDetectorRef,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, forkJoin, takeUntil } from 'rxjs';
import { ConfirmationService, MessageService } from 'primeng/api';
import { InterimService } from '../../services/interim.service';
import {
  Interim,
  EtatInterim,
  InterimStats,
  InterimSearchParams,
  ETAT_INTERIM_META,
} from '../../models/interim.model';
import AOS from 'aos';
import {ProfileAssignmentService} from '../../../../../core/services/backend/ProfileAssignmentService.service';
import {TokenStorageService} from '../../../../../core/services/frontend/token-storage.service';

/** Extended interim with pre-computed display values to avoid
 *  calling methods per-card inside *ngFor (which triggers every
 *  change-detection cycle and can freeze the page). */
interface DisplayInterim extends Interim {
  _delegationDays: number;
  _countdown: string;
  _canEdit: boolean;
  _canCancel: boolean;
  _etatMeta: ReturnType<InterimListComponent['getEtatMeta']>;
}

@Component({
  selector: 'app-interim-list',
  standalone: false,
  templateUrl: './interim-list.component.html',
  styleUrls: ['./interim-list.component.scss'],
})
export class InterimListComponent implements OnInit, OnDestroy {
  // ─── data ────────────────────────────────────────────────────────────
  interims: Interim[] = [];
  filteredInterims: DisplayInterim[] = [];
  stats: InterimStats = { totalEnAttente: 0, totalActif: 0, totalTermine: 0, totalAnnule: 0, total: 0 };

  // ─── ui ──────────────────────────────────────────────────────────────
  loading = false;
  submitted = false;
  viewMode: 'cards' | 'table' = 'table';

  // ─── active stat filter (clickable cards) ────────────────────────────
  activeStatFilter: EtatInterim | null = null;

  // ─── chip filters ────────────────────────────────────────────────────
  filterMatriculeSource: number | null = null;
  filterMatriculeCible: number | null = null;
  filterEtat: EtatInterim | null = null;
  filterDateDebut: Date | null = null;
  filterDateFin: Date | null = null;

  /** Pre-computed flag so the template never calls a method for this */
  activeFilters = false;

  etatOptions = Object.values(EtatInterim).map((e) => ({
    label: ETAT_INTERIM_META[e].label,
    value: e,
  }));

  // ─── wizard dialog ──────────────────────────────────────────────────
  wizardVisible = false;
  wizardActiveStep = 0;
  isEditMode = false;
  editingId: number | null = null;

  wizardSteps = [
    { label: 'Utilisateurs' },
    { label: 'Période' },
    { label: 'Confirmation' },
  ];

  interimForm!: FormGroup;
  today = new Date();

  // ─── metadata ────────────────────────────────────────────────────────
  EtatInterim = EtatInterim;
  ETAT_INTERIM_META = ETAT_INTERIM_META;

  private destroy$ = new Subject<void>();
  managedUsers: any[] = [];
  managedUserOptions: { label: string; value: number; structureId: number }[] = [];
  isLoadingManagedUsers = false;
  currentManagerMatricule: string | null = null; // set this from auth/session

  constructor(
    private interimService: InterimService,
    private fb: FormBuilder,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private cdr: ChangeDetectorRef,
    private router: Router,
    private profileAssignmentService: ProfileAssignmentService,
    private tokenStorage: TokenStorageService,
  ) {}

  // ═══════════════════════════════════════════════════════════════════════
  // LIFECYCLE
  // ═══════════════════════════════════════════════════════════════════════
  ngOnInit(): void {
    AOS.init({ easing: 'linear', mirror: true });
    this.initForm();
    this.loadData();
    this.loadManagedUsers();
  }

  private loadManagedUsers(): void {
    this.currentManagerMatricule = this.getCurrentManagerMatricule();
    if (!this.currentManagerMatricule) return;

    this.isLoadingManagedUsers = true;
    this.profileAssignmentService
      .getManagedUsersWithDetails(this.currentManagerMatricule)
      .subscribe({
        next: (users) => {
          this.managedUsers = users;
          this.managedUserOptions = users.map((user: any) => ({
            label: `${user.nom_prenom || 'N/A'} (${user.mat})`,
            value: user.mat,
            structureId: user.cod_strc_strc,
          }));
          this.isLoadingManagedUsers = false;
        },
        error: (error) => {
          console.error('Error loading managed users:', error);
          this.isLoadingManagedUsers = false;
        },
      });
  }

  private getCurrentManagerMatricule(): string {
    return this.tokenStorage.getCurrentUserMatricule();
  }

  onSourceUserChange(matricule: number): void {
    const user = this.managedUserOptions.find(u => u.value === matricule);
    if (user) {
      this.interimForm.patchValue({ codStrcOrigine: user.structureId });
    }
  }

  onCibleUserChange(matricule: number): void {
    const user = this.managedUserOptions.find(u => u.value === matricule);
    if (user) {
      this.interimForm.patchValue({ codStrcDestination: user.structureId });
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  // ═══════════════════════════════════════════════════════════════════════
  // FORM
  // ═══════════════════════════════════════════════════════════════════════
  private initForm(): void {
    this.interimForm = this.fb.group(
      {
        matriculeSource: [null, [Validators.required]],
        matriculeCible: [null, [Validators.required]],
        codStrcOrigine: [{ value: null, disabled: true }, [Validators.required]],
        codStrcDestination: [{ value: null, disabled: true }, [Validators.required]],
        dateDebutInterim: [null, [Validators.required]],
        dateFinInterim: [null, [Validators.required]],
      },
      { validators: [this.dateRangeValidator, this.differentMatriculeValidator] }
    );
  }

  private dateRangeValidator(group: FormGroup): { [key: string]: boolean } | null {
    const start = group.get('dateDebutInterim')?.value;
    const end = group.get('dateFinInterim')?.value;
    if (start && end && new Date(start) > new Date(end)) return { dateRange: true };
    return null;
  }

  private differentMatriculeValidator(group: FormGroup): { [key: string]: boolean } | null {
    const src = group.get('matriculeSource')?.value;
    const cbl = group.get('matriculeCible')?.value;
    if (src != null && cbl != null && +src === +cbl) return { sameMatricule: true };
    return null;
  }

  get f() { return this.interimForm.controls; }

  // ═══════════════════════════════════════════════════════════════════════
  // DATA LOADING
  // ═══════════════════════════════════════════════════════════════════════
  loadData(): void {
    this.loading = true;
    forkJoin({
      interims: this.interimService.getAll(),
      stats: this.interimService.getStats(),
    })
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: ({ interims, stats }) => {
          this.interims = Array.isArray(interims) ? interims : [];
          if (stats) this.stats = stats;
          this.applyStatFilter();
          this.loading = false;
          this.cdr.detectChanges();
          AOS.refresh();
        },
        error: () => {
          this.interims = [];
          this.filteredInterims = [];
          this.loading = false;
          this.cdr.detectChanges();
        },
      });
  }

  // ═══════════════════════════════════════════════════════════════════════
  // PRE-COMPUTE DISPLAY PROPERTIES
  // ═══════════════════════════════════════════════════════════════════════
  /** Enrich each interim once so the template reads plain properties
   *  instead of calling methods on every change-detection cycle. */
  private enrichInterims(list: Interim[]): DisplayInterim[] {
    return list.map(i => ({
      ...i,
      _delegationDays: this.getDelegationDays(i),
      _countdown: this.getCountdown(i),
      _canEdit: this.canEditInterim(i),
      _canCancel: this.canCancelInterim(i),
      _etatMeta: this.getEtatMeta(i.etat!),
    }));
  }

  /** Recompute the activeFilters flag (avoids method call in template). */
  private refreshActiveFilters(): void {
    this.activeFilters = !!(this.filterMatriculeSource || this.filterMatriculeCible ||
      this.filterEtat || this.filterDateDebut || this.filterDateFin);
  }

  // ═══════════════════════════════════════════════════════════════════════
  // CLICKABLE STAT CARDS
  // ═══════════════════════════════════════════════════════════════════════
  toggleStatFilter(etat: EtatInterim): void {
    this.activeStatFilter = this.activeStatFilter === etat ? null : etat;
    this.applyStatFilter();
  }

  private applyStatFilter(): void {
    const base = !this.activeStatFilter
      ? [...this.interims]
      : this.interims.filter(i => i.etat === this.activeStatFilter);
    this.filteredInterims = this.enrichInterims(base);
    this.refreshActiveFilters();
  }

  // ═══════════════════════════════════════════════════════════════════════
  // SEARCH / FILTER
  // ═══════════════════════════════════════════════════════════════════════
  applyFilter(): void {
    this.refreshActiveFilters();
    const params: InterimSearchParams = {};
    if (this.filterMatriculeSource) params.matriculeSource = this.filterMatriculeSource;
    if (this.filterMatriculeCible) params.matriculeCible = this.filterMatriculeCible;
    if (this.filterEtat) params.etat = this.filterEtat;
    if (this.filterDateDebut) params.dateDebut = this.formatDate(this.filterDateDebut);
    if (this.filterDateFin) params.dateFin = this.formatDate(this.filterDateFin);

    if (!Object.keys(params).length) { this.loadData(); return; }

    this.loading = true;
    this.activeStatFilter = null;
    this.interimService.search(params).pipe(takeUntil(this.destroy$)).subscribe({
      next: (res) => {
        this.interims = Array.isArray(res) ? res : [];
        this.filteredInterims = this.enrichInterims([...this.interims]);
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: () => { this.loading = false; },
    });
  }

  clearFilters(): void {
    this.filterMatriculeSource = null;
    this.filterMatriculeCible = null;
    this.filterEtat = null;
    this.filterDateDebut = null;
    this.filterDateFin = null;
    this.activeStatFilter = null;
    this.refreshActiveFilters();
    this.loadData();
  }

  hasActiveFilters(): boolean {
    return !!(this.filterMatriculeSource || this.filterMatriculeCible ||
              this.filterEtat || this.filterDateDebut || this.filterDateFin);
  }

  // ═══════════════════════════════════════════════════════════════════════
  // WIZARD
  // ═══════════════════════════════════════════════════════════════════════
  openCreateWizard(): void {
    this.isEditMode = false;
    this.editingId = null;
    this.submitted = false;
    this.wizardActiveStep = 0;
    this.interimForm.reset();
    this.wizardVisible = true;
  }

  openEditWizard(interim: Interim): void {
    if (interim.etat !== EtatInterim.EN_ATTENTE) {
      this.messageService.add({ severity: 'warn', summary: 'Attention', detail: 'Seules les délégations en attente peuvent être modifiées.' });
      return;
    }
    this.isEditMode = true;
    this.editingId = interim.id!;
    this.submitted = false;
    this.wizardActiveStep = 0;
    this.interimForm.patchValue({
      matriculeSource: interim.matriculeSource,
      matriculeCible: interim.matriculeCible,
      codStrcOrigine: interim.codStrcOrigine,
      codStrcDestination: interim.codStrcDestination,
      dateDebutInterim: interim.dateDebutInterim ? new Date(interim.dateDebutInterim) : null,
      dateFinInterim: interim.dateFinInterim ? new Date(interim.dateFinInterim) : null,
    });
    this.wizardVisible = true;
  }

  nextStep(): void {
    if (this.wizardActiveStep === 0) {
      const ctrls = ['matriculeSource', 'matriculeCible', 'codStrcOrigine', 'codStrcDestination'];
      ctrls.forEach(c => this.f[c].markAsTouched());
      if (ctrls.some(c => this.f[c].invalid)) return;
      if (this.interimForm.errors?.['sameMatricule']) return;
    }
    if (this.wizardActiveStep === 1) {
      ['dateDebutInterim', 'dateFinInterim'].forEach(c => this.f[c].markAsTouched());
      if (this.f['dateDebutInterim'].invalid || this.f['dateFinInterim'].invalid) return;
      if (this.interimForm.errors?.['dateRange']) return;
    }
    this.wizardActiveStep = Math.min(this.wizardActiveStep + 1, 2);
  }

  prevStep(): void {
    this.wizardActiveStep = Math.max(this.wizardActiveStep - 1, 0);
  }

  confirmInterim(): void {
    this.submitted = true;
    if (this.interimForm.invalid) return;
    const raw = this.interimForm.getRawValue();
    const payload: Partial<Interim> = {
      matriculeSource: raw.matriculeSource,
      matriculeCible: raw.matriculeCible,
      codStrcOrigine: raw.codStrcOrigine,
      codStrcDestination: raw.codStrcDestination,
      dateDebutInterim: this.formatDate(raw.dateDebutInterim),
      dateFinInterim: this.formatDate(raw.dateFinInterim),
    };
    const obs$ = this.isEditMode
      ? this.interimService.update(this.editingId!, payload)
      : this.interimService.create(payload);

    obs$.pipe(takeUntil(this.destroy$)).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Succès', detail: this.isEditMode ? 'Délégation modifiée' : 'Délégation créée avec succès' });
        this.wizardVisible = false;
        this.loadData();
      },
      error: (err) => {
        this.messageService.add({ severity: 'error', summary: 'Erreur', detail: err?.error?.message || 'Une erreur est survenue' });
      },
    });
  }

  // ═══════════════════════════════════════════════════════════════════════
  // VIEW / CANCEL
  // ═══════════════════════════════════════════════════════════════════════
  viewInterim(interim: Interim): void {
    this.router.navigate(['/administration/interim', interim.id]);
  }

  cancelInterim(interim: Interim, event?: Event): void {
    event?.stopPropagation();
    if (interim.etat !== EtatInterim.EN_ATTENTE && interim.etat !== EtatInterim.ACTIF) {
      this.messageService.add({ severity: 'warn', summary: 'Attention', detail: 'Cette délégation ne peut plus être annulée.' });
      return;
    }
    const msg = interim.etat === EtatInterim.ACTIF
      ? `Cette délégation est actuellement <b>active</b>. L'annulation restaurera les profils de l'utilisateur source. Confirmer ?`
      : `Êtes-vous sûr de vouloir annuler cette délégation en attente ?`;
    this.confirmationService.confirm({
      header: "Confirmer l'annulation", message: msg, icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Oui, annuler', rejectLabel: 'Non', acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.interimService.cancel(interim.id!).pipe(takeUntil(this.destroy$)).subscribe({
          next: () => { this.messageService.add({ severity: 'success', summary: 'Succès', detail: 'Délégation annulée' }); this.loadData(); },
          error: (err) => { this.messageService.add({ severity: 'error', summary: 'Erreur', detail: err?.error?.message || "Échec de l'annulation" }); },
        });
      },
    });
  }

  // ═══════════════════════════════════════════════════════════════════════
  // HELPERS
  // ═══════════════════════════════════════════════════════════════════════
  formatDate(date: Date | string | null): string {
    if (!date) return '';
    const d = typeof date === 'string' ? new Date(date) : date;
    return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`;
  }

  getDelegationDays(interim: Interim): number {
    const start = new Date(interim.dateDebutInterim);
    const end = new Date(interim.dateFinInterim);
    return Math.max(1, Math.ceil((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24)));
  }

  getCountdown(interim: Interim): string {
    if (interim.etat !== EtatInterim.ACTIF) return '';
    const diff = new Date(interim.dateFinInterim).getTime() - new Date().getTime();
    if (diff <= 0) return 'Expiré';
    const days = Math.floor(diff / 86400000);
    const hours = Math.floor((diff % 86400000) / 3600000);
    return days > 0 ? `${days}j ${hours}h restants` : `${hours}h restantes`;
  }

  getWizardDuration(): number {
    const s = this.f['dateDebutInterim']?.value;
    const e = this.f['dateFinInterim']?.value;
    if (!s || !e) return 0;
    const start = typeof s === 'string' ? new Date(s) : s;
    const end = typeof e === 'string' ? new Date(e) : e;
    return Math.max(1, Math.ceil((end.getTime() - start.getTime()) / 86400000));
  }

  isImmediateActivation(): boolean {
    const s = this.f['dateDebutInterim']?.value;
    if (!s) return false;
    return (typeof s === 'string' ? new Date(s) : s).toDateString() === new Date().toDateString();
  }

  canCancelInterim(i: Interim): boolean { return i.etat === EtatInterim.EN_ATTENTE || i.etat === EtatInterim.ACTIF; }
  canEditInterim(i: Interim): boolean { return i.etat === EtatInterim.EN_ATTENTE; }
  getEtatMeta(etat: EtatInterim) { return ETAT_INTERIM_META[etat] ?? ETAT_INTERIM_META[EtatInterim.EN_ATTENTE]; }

  getUserName(matricule: number): string {
    const user = this.managedUsers.find((u: any) => u.mat === matricule);
    return user?.nom_prenom || '';
  }
}
