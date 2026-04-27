import {AfterViewInit, Component, OnInit, OnDestroy, ViewChild, ChangeDetectorRef, NgZone} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {Subscription, filter} from 'rxjs';
import {AnimatedDrawerComponent} from '../../../shared/components/animated-drawer/animated-drawer.component';
import {ActionType} from '../../../core/enums/actionType.enum';
import {SharedFrontService} from '../../../core/services/frontend/shared-front.service';
import {PersonneService} from '../../../core/services/backend/personne.service';
import {HrPersonnelService} from '../../../core/services/backend/hr-personnel.service';
import {BackendIntegrationService} from '../../../core/services/frontend/backend-integration.service';
import {ConfirmationService, MessageService} from 'primeng/api';
import {PersonnelDetailsDto} from '../../../core/models/personnel-detail';
import {PersonnelDto} from '../../../core/models/personnel';
import {HrPersonnel} from '../../../core/models/hr-personnel.model';
import {getCodeStructureLabel} from '../../../core/constants/codeStructure';
import AOS from 'aos';

@Component({
  selector: "app-personnel",
  templateUrl: "./personnel.component.html",
  styleUrls: ["./personnel.component.scss"],
  standalone: false
})
export class PersonnelComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('drawer') drawer!: AnimatedDrawerComponent;

  // Data
  personnel: PersonnelDetailsDto = {};
  personnels: PersonnelDetailsDto[] = [];
  filteredPersonnels: PersonnelDetailsDto[] = [];

  // HR Personnel Autocomplete
  hrPersonnelSuggestions: HrPersonnel[] = [];
  selectedHrPersonnel: HrPersonnel | null = null;
  cinSearchTerm = '';
  isSearchingHr = false;

  // UI State
  submitted = false;
  action: ActionType = ActionType.ADD;
  public actionType = ActionType;
  loading = false;

  // Stats
  totalPersonnels = 0;

  // Subscriptions
  private routerSub?: Subscription;

  // ============================================================================
  // FILTER STATE
  // ============================================================================
  adminStatusFilter: boolean | null = null;
  adminStructureFilter: number[] = [];
  adminStructureTypeFilter: string | null = null;
  adminSortBy = 'mat';
  adminSortDirection: 'ASC' | 'DESC' = 'ASC';

  structureOptions: { label: string; value: number }[] = [];
  structureTypeOptions: { label: string; value: string }[] = [];

  adminSortOptions = [
    { label: 'Matricule', value: 'mat' },
    { label: 'Nom & Prénom', value: 'nom_prenom' },
    { label: 'Structure', value: 'libelleStructure' },
  ];

  tableConfig = {
    columns: [
      {
        field: 'mat',
        header: 'Matricule',
        sortable: true,
        type: 'text',
        minWidth: '3rem',
        width: '8rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'nom_prenom',
        header: 'Nom & Prénom',
        sortable: true,
        type: 'text',
        filter: true,
        minWidth: '3rem',
        width: '16rem',
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'email',
        header: 'Email',
        type: 'text',
        width: '16rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'cod_stat_user',
        header: 'Statut',
        type: 'tag',
        width: '12rem',
        filter: false,
        severityFn: (status: boolean) => status ? 'success' : 'danger',
        formatFn: (value: boolean) => value ? 'Actif' : 'Inactif'
      },
      {
        field: 'cod_strc_strc',
        header: 'Code Structure',
        type: 'text',
        width: '10rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
        formatFn: (value: any) => value != null ? String(value) : '-',
      },
      {
        field: 'libelleStructure',
        header: 'Libellé Structure',
        type: 'text',
        width: '14rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
        formatFn: (value: string) => value?.trim() || '-',
      },
      {
        field: 'cod_tstr_tstr',
        header: 'Type Structure',
        type: 'text',
        width: '12rem',
        filter: false,
        formatFn: (value: string) => getCodeStructureLabel(value),
      }
    ],
    showActions: true,
    actions: {
      view: false,
      edit: true,
      notification: false,
      send: false,
      delete: false,
    },
    styleAction: {
      'min-width': '4rem',
      width: '5.5rem',
    },
    pagination: true,
    pageSize: 10,
  };

  constructor(
    private sharedService: SharedFrontService,
    private personneService: PersonneService,
    private hrPersonnelService: HrPersonnelService,
    private backendIntegration: BackendIntegrationService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef,
    private ngZone: NgZone
  ) {}

  ngOnInit() {
    AOS.init({ easing: 'linear', mirror: true });
    this.getAllPersonnels();

    this.routerSub = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: any) => {
      if (event.urlAfterRedirects === '/administration/personnel') {
        this.getAllPersonnels();
      }
    });
  }

  ngAfterViewInit(): void {}

  ngOnDestroy(): void {
    if (this.routerSub) {
      this.routerSub.unsubscribe();
    }
  }

  private getAllPersonnels() {
    this.loading = true;
    this.cdr.detectChanges();
    
    this.personneService.getAllPersonnelDetails().subscribe({
      next: (result) => {
        this.ngZone.run(() => {
          const data = result.data?.data || result.data || [];
          this.personnels = Array.isArray(data) ? data : [];
          this.updateStats();
          this.buildFilterOptions();
          this.applyFilters();
          this.loading = false;
          this.cdr.detectChanges();
          AOS.refresh();
        });
      },
      error: (error) => {
        this.ngZone.run(() => {
          console.error("Failed to load personnels", error);
          this.personnels = [];
          this.filteredPersonnels = [];
          this.loading = false;
          this.cdr.detectChanges();
        });
      }
    });
  }

  updateStats(): void {
    this.totalPersonnels = this.personnels.length;
  }

  // ============================================================================
  // FILTER OPTIONS — built from loaded data
  // ============================================================================

  private buildFilterOptions(): void {
    // Structure options (unique cod_strc_strc values, labeled as "libelle (code)")
    const uniqueStructures = new Map<number, string>();
    this.personnels.forEach(p => {
      if (p.cod_strc_strc != null) {
        const label = p.libelleStructure
          ? `${p.libelleStructure} (${p.cod_strc_strc})`
          : String(p.cod_strc_strc);
        uniqueStructures.set(p.cod_strc_strc, label);
      }
    });
    this.structureOptions = Array.from(uniqueStructures.entries())
      .sort((a, b) => a[0] - b[0])
      .map(([v, label]) => ({ label, value: v }));

    // Structure type options (unique cod_tstr_tstr values)
    const uniqueTypes = new Set<string>();
    this.personnels.forEach(p => {
      if (p.cod_tstr_tstr) uniqueTypes.add(p.cod_tstr_tstr.toString());
    });
    this.structureTypeOptions = Array.from(uniqueTypes).map(v => ({
      label: getCodeStructureLabel(v),
      value: v
    }));
  }

  // ============================================================================
  // FILTER METHODS
  // ============================================================================

  cycleStatusFilter(): void {
    if (this.adminStatusFilter === true) {
      this.adminStatusFilter = null;
    } else if (this.adminStatusFilter === null) {
      this.adminStatusFilter = false;
    } else {
      this.adminStatusFilter = true;
    }
    this.applyFilters();
  }

  onAdminStatusChange(status: boolean | null): void {
    this.adminStatusFilter = status;
    this.applyFilters();
  }

  onAdminStructureChange(structureIds: number[]): void {
    this.adminStructureFilter = structureIds || [];
    this.applyFilters();
  }

  onAdminStructureTypeChange(typeValue: string | null): void {
    this.adminStructureTypeFilter = typeValue;
    this.applyFilters();
  }

  onAdminSortChange(sortBy: string): void {
    if (this.adminSortBy === sortBy) {
      this.adminSortDirection = this.adminSortDirection === 'ASC' ? 'DESC' : 'ASC';
    } else {
      this.adminSortBy = sortBy;
      this.adminSortDirection = 'ASC';
    }
    this.applyFilters();
  }

  toggleSortDirection(): void {
    this.adminSortDirection = this.adminSortDirection === 'ASC' ? 'DESC' : 'ASC';
    this.applyFilters();
  }

  resetAdminFilters(): void {
    this.adminStatusFilter = null;
    this.adminStructureFilter = [];
    this.adminStructureTypeFilter = null;
    this.adminSortBy = 'mat';
    this.adminSortDirection = 'ASC';
    this.applyFilters();
  }

  hasActiveFilters(): boolean {
    return (
      this.adminStatusFilter !== null ||
      this.adminStructureFilter.length > 0 ||
      this.adminStructureTypeFilter !== null
    );
  }

  getStructureFilterCount(): number {
    return this.adminStructureFilter.length;
  }

  getStructureFilterLabel(): string {
    return this.adminStructureFilter
      .map(id => {
        const opt = this.structureOptions.find(o => o.value === id);
        return opt ? opt.label : String(id);
      })
      .join(', ');
  }

  getStructureTypeFilterLabel(): string {
    const opt = this.structureTypeOptions.find(o => o.value === this.adminStructureTypeFilter);
    return opt ? opt.label : String(this.adminStructureTypeFilter);
  }

  private applyFilters(): void {
    let result = [...this.personnels];

    // Status filter
    if (this.adminStatusFilter !== null) {
      result = result.filter(p => p.cod_stat_user === this.adminStatusFilter);
    }

    // Structure filter (multi-select)
    if (this.adminStructureFilter.length > 0) {
      result = result.filter(p =>
        p.cod_strc_strc != null && this.adminStructureFilter.includes(p.cod_strc_strc)
      );
    }

    // Structure type filter
    if (this.adminStructureTypeFilter !== null) {
      result = result.filter(p => p.cod_tstr_tstr === Number(this.adminStructureTypeFilter));
    }

    // Sort
    result.sort((a, b) => {
      const aVal = (a as any)[this.adminSortBy] ?? '';
      const bVal = (b as any)[this.adminSortBy] ?? '';
      const cmp = String(aVal).localeCompare(String(bVal), undefined, { numeric: true });
      return this.adminSortDirection === 'ASC' ? cmp : -cmp;
    });

    this.filteredPersonnels = result;
  }

  // ============================================================================
  // DRAWER
  // ============================================================================

  openNew() {
    this.action = ActionType.ADD;
    this.personnel = {};
    this.selectedHrPersonnel = null;
    this.cinSearchTerm = '';
    this.hrPersonnelSuggestions = [];
    this.submitted = false;
    this.drawer.openDrawer();
  }

  // ============================================================================
  // HR PERSONNEL AUTOCOMPLETE
  // ============================================================================

  searchHrPersonnelByCinExact(): void {
    const cin = this.cinSearchTerm?.trim();
    if (!cin || cin.length < 8) {
      this.messageService.add({ severity: 'warn', summary: 'Attention', detail: 'Veuillez saisir un CIN valide (minimum 8 caractères)' });
      return;
    }
    this.isSearchingHr = true;
    this.hrPersonnelService.getHrPersonnelPage({ page: 0, size: 10, sortBy: 'matcle', sortDirection: 'ASC', cin }).subscribe({
      next: (response) => {
        this.isSearchingHr = false;
        if (response.content && response.content.length > 0) {
          const hrPersonnel = response.content[0];
          this.selectedHrPersonnel = hrPersonnel;
          this.autoFillFormFromHrPersonnel(hrPersonnel);
        } else {
          this.messageService.add({ severity: 'warn', summary: 'Personnel introuvable', detail: `Aucun personnel trouvé avec le CIN: ${cin}` });
        }
      },
      error: (error) => {
        console.error('❌ Error searching HR personnel by CIN:', error);
        this.isSearchingHr = false;
        this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Erreur lors de la recherche dans la base RH' });
      }
    });
  }

  private autoFillFormFromHrPersonnel(hrPersonnel: HrPersonnel): void {
    this.personnel = {
      ...this.personnel,
      cin: hrPersonnel.cin,
      mat: hrPersonnel.matcle,
      nom_prenom: this.getHrFullName(hrPersonnel),
      email: '',
      cod_strc_strc: this.personnel.cod_strc_strc || null,
      cod_stat_user: this.personnel.cod_stat_user !== undefined ? this.personnel.cod_stat_user : true
    };
    this.messageService.add({ severity: 'success', summary: 'Personnel importé', detail: `${this.getHrFullName(hrPersonnel)} chargé depuis la base RH` });
  }

  searchHrPersonnelByCin(event: any): void {
    const query = event.query?.trim() || '';
    if (!query || query.length < 3) { this.hrPersonnelSuggestions = []; return; }
    this.isSearchingHr = true;
    this.hrPersonnelService.getHrPersonnelPage({ page: 0, size: 10, sortBy: 'matcle', sortDirection: 'ASC', cin: query }).subscribe({
      next: (response) => { this.hrPersonnelSuggestions = response.content; this.isSearchingHr = false; },
      error: (error) => {
        console.error('❌ Error searching HR personnel:', error);
        this.hrPersonnelSuggestions = [];
        this.isSearchingHr = false;
        this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Erreur lors de la recherche dans la base RH' });
      }
    });
  }

  onHrPersonnelSelect(event: any): void {
    const hrPersonnel: HrPersonnel = event.value;
    this.selectedHrPersonnel = hrPersonnel;
    this.personnel = {
      ...this.personnel,
      cin: hrPersonnel.cin,
      mat: hrPersonnel.matcle,
      nom_prenom: this.getHrFullName(hrPersonnel),
      email: '',
      cod_strc_strc: this.personnel.cod_strc_strc || null,
      cod_stat_user: this.personnel.cod_stat_user !== undefined ? this.personnel.cod_stat_user : true
    };
    this.messageService.add({ severity: 'success', summary: 'Personnel trouvé', detail: `${this.getHrFullName(hrPersonnel)} chargé depuis la base RH` });
  }

  onHrPersonnelClear(): void {
    this.selectedHrPersonnel = null;
    this.personnel = { cod_strc_strc: this.personnel.cod_strc_strc, cod_stat_user: this.personnel.cod_stat_user };
  }

  getHrFullName(hrPersonnel: HrPersonnel): string {
    return `${hrPersonnel.prenom || ''} ${hrPersonnel.nomuse || ''}`.trim() || 'N/A';
  }

  formatHrPersonnelDisplay(hrPersonnel: HrPersonnel): string {
    return `${hrPersonnel.cin} - ${this.getHrFullName(hrPersonnel)} (${hrPersonnel.matcle})`;
  }

  isHrPersonnelLoaded(): boolean {
    return !!this.selectedHrPersonnel || !!(this.personnel.cin && this.personnel.mat && this.personnel.nom_prenom);
  }

  // ============================================================================
  // PERSONNEL CRUD OPERATIONS
  // ============================================================================

  savePersonnel(personnel: any) {
    this.submitted = true;
    if (!this.selectedHrPersonnel && !personnel.cin) {
      this.messageService.add({ severity: 'warn', summary: 'Attention', detail: "Veuillez sélectionner un personnel de la base RH" });
      return;
    }
    if (!personnel || !personnel.mat) {
      this.messageService.add({ severity: 'warn', summary: 'Attention', detail: "Le matricule est requis" });
      return;
    }
    let activeStatus: number | null = null;
    if (personnel.cod_stat_user.value === true) activeStatus = 1;
    else if (personnel.cod_stat_user.value === false) activeStatus = 0;

    const createPayload: PersonnelDto = {
      matricule: personnel.mat,
      active: activeStatus ?? null,
      structureId: personnel.cod_strc_strc ?? null,
      cin: personnel.cin ?? null,
      structureName: undefined,
      structureType: undefined,
    };
    this.backendIntegration.quickOperation(
      this.personneService.createPersonnel(createPayload),
      "Personnel créé avec succès",
      {
        onSuccess: () => {
          this.messageService.add({ severity: 'success', summary: 'Succès', detail: "Personnel créé avec succès" });
          this.drawer.closeDrawer();
          this.getAllPersonnels();
        },
        onError: (error) => {
          console.error("Create personnel failed", error);
          this.messageService.add({ severity: 'error', summary: 'Erreur', detail: "Échec de la création du personnel" });
        }
      }
    );
  }

  editPersonnel(personnel: PersonnelDetailsDto) {
    this.action = ActionType.EDIT;
    this.personnel = {
      ...personnel,
      cod_stat_user: personnel.cod_stat_user
        ? { label: 'Actif', value: true }
        : { label: 'Inactif', value: false }
    };
    this.drawer.openDrawer();
  }

  updatePersonnel(personnel: any) {
    this.submitted = true;
    if (!personnel || !personnel.mat) {
      this.messageService.add({ severity: 'warn', summary: 'Attention', detail: "Le matricule est requis pour la modification" });
      return;
    }
    const mat = personnel.mat;
    let activeStatus: number | null = null;
    if (personnel.cod_stat_user.value === true) activeStatus = 1;
    else if (personnel.cod_stat_user.value === false) activeStatus = 0;

    const updatePayload: any = {
      mat: personnel.mat,
      cod_stat_user: activeStatus ?? null,
      cod_strc_strc: personnel.cod_strc_strc ?? null,
    };
    this.backendIntegration.quickOperation(
      this.personneService.updatePersonnel(mat, updatePayload),
      "Personnel modifié avec succès",
      {
        onSuccess: () => {
          this.messageService.add({ severity: 'success', summary: 'Succès', detail: "Personnel modifié avec succès" });
          this.drawer.closeDrawer();
          this.getAllPersonnels();
        },
        onError: (error) => {
          console.error("Update personnel failed", error);
          this.messageService.add({ severity: 'error', summary: 'Erreur', detail: "Échec de la modification du personnel" });
        }
      }
    );
  }

  deletePersonnel(personnel: PersonnelDetailsDto) {
    if (!personnel || !personnel.mat) {
      this.messageService.add({ severity: 'error', summary: 'Erreur', detail: "Identifiant personnel introuvable" });
      return;
    }
  }
}
