import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, forkJoin, takeUntil } from 'rxjs';
import { ConfirmationService, MessageService } from 'primeng/api';
import { InterimService } from '../../services/interim.service';
import {
  Interim,
  EtatInterim,
  InterimProfilGranted,
  InterimProfilBackup,
  ETAT_INTERIM_META,
} from '../../models/interim.model';

@Component({
  selector: 'app-interim-detail',
  standalone: false,
  templateUrl: './interim-detail.component.html',
  styleUrls: ['./interim-detail.component.scss'],
})
export class InterimDetailComponent implements OnInit, OnDestroy {
  interim: Interim | null = null;
  grantedProfiles: InterimProfilGranted[] = [];
  backedUpProfiles: InterimProfilBackup[] = [];
  loading = true;
  interimId!: number;
  activeTab = 0;

  EtatInterim = EtatInterim;

  // ─── computed properties (set once on data load) ─────────────────────
  timelineEvents: { label: string; icon: string; color: string; date: string; active: boolean }[] = [];
  timelineBarProgress = 0;
  timelineBarColor = '#e5e7eb';

  // ─── table configs ───────────────────────────────────────────────────
  grantedTableConfig = {
    columns: [
      { field: 'codPflPfl', header: 'Profil', sortable: true, type: 'text', width: '12rem', filter: true, filterType: 'text' },
      { field: 'numMatrUser', header: 'Matricule', sortable: true, type: 'text', width: '8rem', filter: true, filterType: 'text' },
      { field: 'previouslyExisted', header: 'Existait Avant', type: 'tag', width: '10rem',
        severityFn: (v: number) => v === 1 ? 'success' : 'warn',
        formatFn: (v: number) => v === 1 ? 'Oui' : 'Non' },
      { field: 'dateGranted', header: 'Date Attribution', sortable: true, type: 'text', width: '10rem' },
    ],
    showActions: false,
    actions: { view: false, edit: false, notification: false, send: false, delete: false },
    pagination: true,
    pageSize: 10,
  };

  backupTableConfig = {
    columns: [
      { field: 'codPflPfl', header: 'Profil', sortable: true, type: 'text', width: '12rem', filter: true, filterType: 'text' },
      { field: 'numMatrUser', header: 'Matricule', sortable: true, type: 'text', width: '8rem', filter: true, filterType: 'text' },
      { field: 'boolEtatUtpr', header: 'État Orig.', type: 'tag', width: '8rem',
        severityFn: (v: number) => v === 1 ? 'success' : 'danger',
        formatFn: (v: number) => v === 1 ? 'Actif' : 'Inactif' },
      { field: 'datDadhUtpr', header: 'Adhésion', sortable: true, type: 'text', width: '10rem' },
      { field: 'datFadhUtpr', header: 'Fin Adhésion', sortable: true, type: 'text', width: '10rem' },
      { field: 'dateBackup', header: 'Sauvegardé le', sortable: true, type: 'text', width: '10rem' },
    ],
    showActions: false,
    actions: { view: false, edit: false, notification: false, send: false, delete: false },
    pagination: true,
    pageSize: 10,
  };

  private destroy$ = new Subject<void>();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private interimService: InterimService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.interimId = +this.route.snapshot.paramMap.get('id')!;
    this.loadDetail();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadDetail(): void {
    this.loading = true;
    forkJoin({
      interim: this.interimService.getById(this.interimId),
      granted: this.interimService.getGrantedProfiles(this.interimId),
      backup: this.interimService.getBackedUpProfiles(this.interimId),
    })
      .pipe(takeUntil(this.destroy$))      .subscribe({        next: ({ interim, granted, backup }) => {
          this.interim = interim;
          this.grantedProfiles = granted ?? [];
          this.backedUpProfiles = backup ?? [];
          this.computeTimeline();
          this.loading = false;
        },
        error: () => {
          this.loading = false;
          this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Impossible de charger les détails' });
        },
      });
  }
  // ─── timeline computation (called once on data load) ──────────────
  private computeTimeline(): void {
    if (!this.interim) return;

    // Timeline events
    const events: { label: string; icon: string; color: string; date: string; active: boolean }[] = [];

    events.push({ label: 'Créée', icon: 'pi pi-plus-circle', color: '#3b82f6', date: this.interim.dateOperation ?? '', active: true });

    const isActiveOrLater = this.interim.etat === EtatInterim.ACTIF || this.interim.etat === EtatInterim.TERMINE;
    events.push({ label: 'Activée', icon: 'pi pi-check-circle', color: '#10b981',
      date: isActiveOrLater ? this.interim.dateDebutInterim : '', active: isActiveOrLater });

    if (this.interim.etat === EtatInterim.TERMINE) {
      events.push({ label: 'Terminée', icon: 'pi pi-history', color: '#6b7280', date: this.interim.dateFinInterim, active: true });
    } else if (this.interim.etat === EtatInterim.ANNULE) {
      events.push({ label: 'Annulée', icon: 'pi pi-times-circle', color: '#ef4444', date: '', active: true });
    } else if (this.interim.etat === EtatInterim.EN_ATTENTE) {
      events.push({ label: 'En attente', icon: 'pi pi-clock', color: '#f59e0b', date: '', active: false });
    }

    this.timelineEvents = events;

    // Timeline bar progress
    if (this.interim.etat === EtatInterim.EN_ATTENTE) {
      this.timelineBarProgress = 10;
    } else if (this.interim.etat === EtatInterim.ANNULE || this.interim.etat === EtatInterim.TERMINE) {
      this.timelineBarProgress = 100;
    } else {
      const start = new Date(this.interim.dateDebutInterim).getTime();
      const end = new Date(this.interim.dateFinInterim).getTime();
      const now = new Date().getTime();
      const total = end - start;
      this.timelineBarProgress = total <= 0 ? 100 : Math.min(100, Math.max(15, Math.round(((now - start) / total) * 100)));
    }

    // Timeline bar color
    switch (this.interim.etat) {
      case EtatInterim.EN_ATTENTE: this.timelineBarColor = '#f59e0b'; break;
      case EtatInterim.ACTIF: this.timelineBarColor = '#10b981'; break;
      case EtatInterim.TERMINE: this.timelineBarColor = '#6b7280'; break;
      case EtatInterim.ANNULE: this.timelineBarColor = '#ef4444'; break;
      default: this.timelineBarColor = '#e5e7eb';
    }
  }

  getDelegationDays(): number {
    if (!this.interim) return 0;
    const start = new Date(this.interim.dateDebutInterim);
    const end = new Date(this.interim.dateFinInterim);
    return Math.max(1, Math.ceil((end.getTime() - start.getTime()) / 86400000));
  }

  getCountdown(): string {
    if (!this.interim || this.interim.etat !== EtatInterim.ACTIF) return '';
    const diff = new Date(this.interim.dateFinInterim).getTime() - new Date().getTime();
    if (diff <= 0) return 'Expiré';
    const days = Math.floor(diff / 86400000);
    const hours = Math.floor((diff % 86400000) / 3600000);
    return days > 0 ? `${days}j ${hours}h restants` : `${hours}h restantes`;
  }

  getEtatMeta(etat: EtatInterim) { return ETAT_INTERIM_META[etat]; }
  canCancel(): boolean { return this.interim?.etat === EtatInterim.EN_ATTENTE || this.interim?.etat === EtatInterim.ACTIF; }

  cancelInterim(): void {
    if (!this.interim) return;
    const msg = this.interim.etat === EtatInterim.ACTIF
      ? `Cette délégation est <b>active</b>. L'annulation restaurera les profils de l'utilisateur source. Confirmer ?`
      : `Annuler cette délégation en attente ?`;
    this.confirmationService.confirm({
      header: "Confirmer l'annulation", message: msg, icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Oui, annuler', rejectLabel: 'Non', acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.interimService.cancel(this.interim!.id!).pipe(takeUntil(this.destroy$)).subscribe({
          next: () => { this.messageService.add({ severity: 'success', summary: 'Succès', detail: 'Délégation annulée' }); this.loadDetail(); },
          error: (err) => { this.messageService.add({ severity: 'error', summary: 'Erreur', detail: err?.error?.message || "Échec" }); },
        });
      },
    });
  }

  goBack(): void { this.router.navigate(['/administration/interim']); }
}
