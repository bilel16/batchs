import {
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { distinctUntilChanged, Subject, takeUntil } from "rxjs";

import {CODE_STRUCTURE_OPTIONS} from "../../../core/constants/codeStructure";
import {ActionType} from '../../../core/enums/actionType.enum';
import {Application} from '../../../core/models/application';
import AOS from 'aos';
import {ProfilService} from '../../../core/services/backend/profil.service';
import {MenuApplicationService} from '../../../core/services/backend/menu-application.service';
import {SharedFrontService} from '../../../core/services/frontend/shared-front.service';
import {ApplicationContextService} from '../../../core/services/frontend/ApplicationContextService.service';
import {AnimatedDrawerComponent} from '../../../shared/components/animated-drawer/animated-drawer.component';
import { ProfilMenuApplicationService } from "./services";
import { ProfilMenuApplication } from "./models";

@Component({
  selector: "app-profilmenuapplication",
  templateUrl: "./profilmenuapplication.component.html",
  styleUrls: ["./profilmenuapplication.component.scss"],
  standalone: false
})
export class ProfilMenuApplicationComponent implements OnInit, OnDestroy {
  @ViewChild('drawer') drawer!: AnimatedDrawerComponent;

  private destroy$ = new Subject<void>();
  private originalValues: Map<any, boolean> = new Map();

  profilMenuApp: ProfilMenuApplication = {
    codAppApp: "",
    codMenuMenu: "",
    codPflPfl: "",
    codTstrcTstrc: ""
  };
  profilMenuApps: ProfilMenuApplication[] = [];
  selectedProfilMenuApps: ProfilMenuApplication[] = [];
  submitted = false;
  action: ActionType = ActionType.ADD;
  showNoAppMessage = true;

  applications: Application[] = [];
  selectedAppCode!: string;

  listRols: any[] = [];
  listMenu: any[] = [];

  selectedMenus: string[] = [];
  public actionType = ActionType;

  // Grid-based menu selection properties
  menuSearchTerm: string = '';
  filteredMenus: any[] = [];
  paginatedMenus: any[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 6; // 2 rows of 3 items each
  // Immersive full-screen mode properties
  isFullscreenMode: boolean = false;
  isHighlighting: boolean = false;
  isSaving: boolean = false;
  pageSizeOptions = [
    { label: '6', value: 6 },
    { label: '12', value: 12 },
    { label: '18', value: 18 },
    { label: '24', value: 24 }
  ];

  // Cache for menu priorities to avoid recalculation
  private menuPriorityCache: Map<string, string> = new Map();
  // Track menu counts per profile and existing menus for selected profile
  profileMenuCounts: Map<string, number> = new Map();
  existingMenusForProfile: Set<string> = new Set();

  // Track additions and revocations separately
  menusToAdd: string[] = []; // New menus being added
  menusToRevoke: Set<string> = new Set(); // Existing menus being revoked (status → false)

  // Review section properties
  showAllMenus: boolean = false;
  showMenuDetails: boolean = false;
  showMenuAccordion: boolean = false;

  // Workflow step management
  currentStep: number = 1;
  totalSteps: number = 4;
  workflowSteps = [
    {
      step: 1,
      title: 'Sélection du Profil',
      subtitle: 'Choisissez le profil à configurer',
      icon: 'pi-user',
      completed: false,
      active: true
    },
    {
      step: 2,
      title: 'Sélection des Menus',
      subtitle: 'Définissez les accès aux menus',
      icon: 'pi-th-large',
      completed: false,
      active: false
    },
    {
      step: 3,
      title: 'Configuration Structure',
      subtitle: 'Paramétrez la hiérarchie',
      icon: 'pi-sitemap',
      completed: false,
      active: false
    },
    {
      step: 4,
      title: 'État d\'Activation',
      subtitle: 'Activez la configuration',
      icon: 'pi-power-off',
      completed: false,
      active: false
    }
  ];

  CodeStructureOptions = CODE_STRUCTURE_OPTIONS;

  tableConfig = {
    columns: [
      {
        field: 'codPflPfl',
        header: 'Code Profil',
        sortable: true,
        type: 'text',
        minWidth: '3rem',
        width: '12rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'codMenuMenu',
        header: 'Code Menu',
        sortable: true,
        type: 'text',
        minWidth: '3rem',
        width: '12rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'codTstrcTstrc',
        header: 'Code Structure',
        sortable: true,
        type: 'text',
        minWidth: '3rem',
        width: '10rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'boolEtatPma',
        header: 'Active',
        sortable: true,
        type: 'boolean',
        minWidth: '3rem',
        width: '8rem',
        filter: true,
        filterType: 'boolean',
        filterStyle: { width: '50%' },
      },
    ],

    showActions: true,
    actions: {
      view: false,
      edit: false,
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

  totalProfilMenus: number = 0;
  activeProfilMenus: number = 0;
  inactiveProfilMenus: number = 0;

  // Add these properties
  searchTerm: string = '';
  selectedStatus: string | null = null;
  statusOptions = [
    { label: 'Actif', value: 'active' },
    { label: 'Inactif', value: 'inactive' }
  ];
  filteredProfilMenuApps: any[] = [];

  constructor(
    private profilMenuService: ProfilMenuApplicationService,
    private profilService: ProfilService,
    private menuApplicationService: MenuApplicationService,
    private sharedService: SharedFrontService,
    private appContext: ApplicationContextService
  ) {}

  ngOnInit() {
        AOS.init({
      easing: 'linear',
      mirror: true
    });
    this.appContext.selectedApp$
      .pipe(
        distinctUntilChanged(),
        takeUntil(this.destroy$)
      )
      .subscribe((appCode: string) => {
        this.selectedAppCode = appCode;
        console.log("Current global app:", appCode);

        if (appCode) {
          this.getProfilMenuApps();
        } else {
          this.profilMenuApps = [];
          this.showNoAppMessage = true;
        }
      });
  }

  ngOnDestroy(): void {
    console.log("ProfilMenuApplication component destroying");
    this.destroy$.next();
    this.destroy$.complete();
  }

  onAppsLoaded(applications: Application[]): void {
    console.log("OnAppsLoaded executing");
  }
  onAppSelectionChange(newApp: string | null): void {
    console.log("onAppSelectionChange executing with:", newApp);

    if (newApp) {
      // Application selected
      this.selectedAppCode = newApp;
      this.getProfilMenuApps();
      this.getRolesAndMenus(newApp);
    } else {
      // Application cleared
      this.selectedAppCode = '';
      this.profilMenuApps = [];
      this.listRols = [];
      this.listMenu = [];
      this.filteredMenus = [];
      this.selectedMenus = [];
      this.calculateStatistics();
    }
  }

  getProfilMenuApps() {
    console.log("getProfilMenuApps");
    this.profilMenuService
      .getProfilMenuApplicationList(this.selectedAppCode)
      .subscribe(
        (res: { data: any; }) => {
          this.profilMenuApps = (res?.data || []).map((item: any) => ({
            ...item,
            boolEtatPma: item.boolEtatPma === 1 || item.boolEtatPma === "1",
          }));

          this.calculateStatistics();
          this.applyFilters();
          this.calculateProfileMenuCounts(); // Calculate menu counts per profile
        },
        (err: any) => {
          this.sharedService.handleError(
            err,
            "Impossible de charger les profils par menus"
          );
        }
      );
  }

  saveProfilMenuApp() {
    this.submitted = true;
    this.isSaving = true;

    if (!this.profilMenuApp.codPflPfl || !this.selectedMenus?.length) {
      this.sharedService.showWarn(
        "Veuillez sélectionner un profil et au moins un menu",
        "Validation"
      );
      this.isSaving = false;
      return;
    }

    const payloadList: any[] = this.selectedMenus.map(
      (menuCode) => ({
        codAppApp: this.selectedAppCode,
        codPflPfl: this.profilMenuApp.codPflPfl,
        codMenuMenu: menuCode,
        codTstrcTstrc: this.profilMenuApp.codTstrcTstrc,
        boolEtatPma: this.profilMenuApp.boolEtatPma ? 1 : 0,
      })
    );

    this.profilMenuService
      .ajoutListProfilMenuApplication(payloadList)
      .subscribe(        (response) => {
          this.sharedService.handleSuccess(
            response,
            `🎉 ${payloadList.length} Profil-Menu créés avec succès`
          );
          this.getProfilMenuApps();
          this.closeFullscreen();
          this.drawer.closeDrawer();
          this.isSaving = false;
        },
        (err) => {
          this.sharedService.handleError(
            err,
            "Erreur lors de l'ajout des profils-menus"
          );
          this.isSaving = false;
        }
      );
  }

  editProfilMenuApp(profilMenuApp: ProfilMenuApplication) {
    this.action = ActionType.EDIT;
    this.profilMenuApp = { ...profilMenuApp };
    this.selectedMenus = [];
    this.submitted = false;
    this.getRolesAndMenus(this.selectedAppCode);
  }

  updateProfilMenuApp() {
    const payload: ProfilMenuApplication = {
      ...this.profilMenuApp,
      boolEtatPma: this.profilMenuApp.boolEtatPma ? 1 : 0,
    };    this.profilMenuService.updateProfilMenuApplication(payload).subscribe(
      (response) => {
        this.sharedService.handleSuccess(response, "ProfilMenuApplication modifié");
        this.getProfilMenuApps();
        this.drawer.closeDrawer();
      },
      (err: any) => {
        this.sharedService.handleError(err, "Erreur lors de la modification");
      }
    );
  }

  hasPendingChanges(item: any): boolean {
    const originalValue = this.originalValues.get(item);
    return originalValue !== undefined && originalValue !== item.boolEtatPma;
  }

  onActiveStatusChange(item: any): void {
    if (!this.originalValues.has(item)) {
      this.originalValues.set(item, !item.boolEtatPma);
    }
  }

  hasAnyPendingChanges(): boolean {
    return this.originalValues.size > 0;
  }

  saveChanges(item: ProfilMenuApplication): void {
    const payload: ProfilMenuApplication = {
      ...item,
      boolEtatPma: item.boolEtatPma ? 1 : 0,
    } as ProfilMenuApplication;

    this.profilMenuService
      .updateProfilMenuApplication(payload)
      .pipe(takeUntil(this.destroy$))
      .subscribe(        (response) => {
          this.sharedService.handleSuccess(response, "ProfilMenuApplication modifié");
          this.originalValues.delete(item);
          this.getProfilMenuApps();
        },
        (err: any) => {
          this.sharedService.handleError(err, "Erreur lors de la modification");
          const originalValue = this.originalValues.get(item);
          if (originalValue !== undefined) {
            item.boolEtatPma = originalValue ? 1 : 0;
          }
          this.originalValues.delete(item);
        }
      );
  }
  // Grid-based menu selection methods
  filterMenus(): void {
    if (!this.listMenu) {
      this.filteredMenus = [];
      this.updatePagination();
      return;
    }

    let menusToFilter = [...this.listMenu];

    if (this.menuSearchTerm) {
      const searchTerm = this.menuSearchTerm.toLowerCase();
      menusToFilter = menusToFilter.filter(menu =>
        menu.libMenuMenu?.toLowerCase().includes(searchTerm) ||
        menu.codMenuMenu?.toLowerCase().includes(searchTerm)
      );
    }

    // Sort menus: already-assigned first, then unassigned
    this.filteredMenus = menusToFilter.sort((a, b) => {
      const aAssigned = this.isMenuAlreadyAssigned(a.codMenuMenu);
      const bAssigned = this.isMenuAlreadyAssigned(b.codMenuMenu);

      if (aAssigned && !bAssigned) return -1;
      if (!aAssigned && bAssigned) return 1;
      return 0;
    });

    this.currentPage = 0; // Reset to first page
    this.updatePagination();
  }

  updatePagination(): void {
    const startIndex = this.currentPage * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.paginatedMenus = this.filteredMenus.slice(startIndex, endIndex);
  }

  resetSearch(): void {
    this.menuSearchTerm = '';
    this.currentPage = 0;
    this.filterMenus();
  }

  onPageChange(event: any): void {
    this.currentPage = event.page;
    this.updatePagination();  }

  isMenuSelected(menuCode: string): boolean {
    return this.selectedMenus.includes(menuCode);
  }

  clearSelection(): void {
    this.selectedMenus = [];
  }

  trackByMenuCode(index: number, menu: any): string {
    return menu.codMenuMenu;
  }

  // ===== IMMERSIVE FULL-SCREEN METHODS =====
    openFullscreen(): void {
    this.isFullscreenMode = true;
    document.body.style.overflow = 'hidden'; // Prevent background scrolling
    this.filterMenus(); // Initialize the grid

    // Remove autofocus from any currently focused element
    if (document.activeElement instanceof HTMLElement) {
      document.activeElement.blur();
    }
  }

  closeFullscreen(): void {
    this.isFullscreenMode = false;
    document.body.style.overflow = 'auto';
  }

  toggleFullscreen(): void {
    if (this.isFullscreenMode) {
      this.closeFullscreen();
      this.drawer.openDrawer(); // Switch to compact mode
    } else {
      this.drawer.closeDrawer();
      this.openFullscreen();
    }
  }

  onOverlayClick(event: Event): void {
    // Close only if clicking on the overlay, not the container
    this.closeFullscreen();
  }

  getProgressPercentage(): number {
    let progress = 0;

    // Profile selected (33%)
    if (this.profilMenuApp.codPflPfl) progress += 33;

    // At least one menu selected (33%)
    if (this.selectedMenus?.length > 0) progress += 33;

    // Structure selected (34%)
    if (this.profilMenuApp.codTstrcTstrc) progress += 34;

    return progress;
  }  // Enhanced menu selection methods
  selectAllVisibleMenus(): void {
    if (!this.listMenu?.length) return;

    this.listMenu.forEach(menu => {
      const menuCode = menu.codMenuMenu;

      // Only select menus that are not already assigned
      if (!this.isMenuAlreadyAssigned(menuCode)) {
        if (!this.selectedMenus.includes(menuCode)) {
          this.selectedMenus.push(menuCode);
        }
        if (!this.menusToAdd.includes(menuCode)) {
          this.menusToAdd.push(menuCode);
        }
      }
    });

    this.updateStepValidation(); // Update workflow step validation
    this.triggerHighlightEffect();
  }

  onPageSizeChange(): void {
    this.currentPage = 0;
    this.updatePagination();
  }

  getTotalPages(): number {
    return Math.ceil((this.filteredMenus?.length || 0) / this.itemsPerPage);
  }

  getItemsRangeText(): string {
    if (!this.filteredMenus?.length) return '0 éléments';

    const start = this.currentPage * this.itemsPerPage + 1;
    const end = Math.min((this.currentPage + 1) * this.itemsPerPage, this.filteredMenus.length);

    return `${start}-${end} sur ${this.filteredMenus.length} éléments`;
  }
    // Visual enhancements
  onMenuHover(menu: any): void {
    // Add hover effects or preview logic
  }

  onMenuLeave(menu: any): void {
    // Clean up hover effects
  }

  onProfileHover(profile: any): void {
    // Add hover effects or preview logic for profiles
  }

  onProfileLeave(profile: any): void {
    // Clean up hover effects for profiles
  }

  triggerHighlightEffect(): void {
    this.isHighlighting = true;
    setTimeout(() => {
      this.isHighlighting = false;
    }, 600);
  }

  getMenuIcon(menu: any): string {
    // Dynamic icon based on menu type or name
    const menuName = menu.libMenuMenu?.toLowerCase() || '';

    if (menuName.includes('dashboard') || menuName.includes('tableau')) return 'chart-line';
    if (menuName.includes('user') || menuName.includes('utilisateur')) return 'users';
    if (menuName.includes('admin') || menuName.includes('administration')) return 'cog';
    if (menuName.includes('report') || menuName.includes('rapport')) return 'file-text';
    if (menuName.includes('setting') || menuName.includes('paramètre')) return 'sliders-h';

    return 'bars'; // Default icon
  }
  getMenuPriority(menu: any): string {
    const menuCode = menu.codMenuMenu || '';

    // Check cache first
    if (this.menuPriorityCache.has(menuCode)) {
      return this.menuPriorityCache.get(menuCode)!;
    }

    // Calculate priority based on menu code hash (stable/deterministic)
    let hash = 0;
    for (let i = 0; i < menuCode.length; i++) {
      const char = menuCode.charCodeAt(i);
      hash = ((hash << 5) - hash) + char;
      hash = hash & hash; // Convert to 32-bit integer
    }

    const priorities = ['Haute', 'Moyenne', 'Normale'];
    const index = Math.abs(hash) % priorities.length;
    const priority = priorities[index];

    // Cache the result
    this.menuPriorityCache.set(menuCode, priority);
    return priority;
  }

  // Form validation
  isFormValid(): boolean {
    return !!(
      this.profilMenuApp.codPflPfl &&
      this.selectedMenus?.length > 0
    );
  }

  getValidationMessage(): string {
    if (!this.profilMenuApp.codPflPfl) {
      return 'Veuillez sélectionner un profil';
    }

    if (!this.selectedMenus?.length) {
      return 'Veuillez sélectionner au moins un menu';
    }

    return 'Configuration valide';
  }  // Preview functionality
  previewConfiguration(): void {
    console.log('Preview button clicked!');

    if (!this.isFormValid()) {
      this.sharedService.showWarn(
        "Veuillez compléter la configuration avant de prévisualiser",
        "Configuration incomplète"
      );
      return;
    }

    const config = {
      profil: this.listRols.find(r => r.codPflPfl === this.profilMenuApp.codPflPfl),
      menus: this.listMenu.filter(m => this.selectedMenus.includes(m.codMenuMenu)),
      structure: this.CodeStructureOptions.find(s => s.value === this.profilMenuApp.codTstrcTstrc),
      active: this.profilMenuApp.boolEtatPma
    };

    // Create a readable preview message without HTML
    const menuList = config.menus.map(menu => `• ${menu.libMenuMenu}`).join('\n');
    const statusText = config.active ? 'Actif' : 'Inactif';

    const previewMessage = `
APERÇU DE LA CONFIGURATION
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

👤 Profil: ${config.profil?.libpflpfl || 'Non défini'}
📱 Application: ${this.selectedAppCode}
🏢 Structure: ${config.structure?.label || 'Non définie'}
⚡ État: ${statusText}

🍽️ Menus sélectionnés (${config.menus.length}):
${menuList || '• Aucun menu sélectionné'}

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Configuration prête à être sauvegardée
    `;

    // Use showInfo with the formatted text
    this.sharedService.showInfo(
      previewMessage.trim(),
      '📋 Prévisualisation'
    );
  }

  // ===== WORKFLOW STEP MANAGEMENT =====

  nextStep(): void {
    if (this.canProceedToNextStep()) {
      this.workflowSteps[this.currentStep - 1].completed = true;
      this.workflowSteps[this.currentStep - 1].active = false;

      if (this.currentStep < this.totalSteps) {
        this.currentStep++;
        this.workflowSteps[this.currentStep - 1].active = true;
        this.updateStepValidation();
      }
    }
  }

  previousStep(): void {
    if (this.currentStep > 1) {
      this.workflowSteps[this.currentStep - 1].active = false;
      this.currentStep--;
      this.workflowSteps[this.currentStep - 1].active = true;
      this.workflowSteps[this.currentStep - 1].completed = false;
    }
  }

  goToStep(step: number): void {
    if (this.canNavigateToStep(step)) {
      this.workflowSteps[this.currentStep - 1].active = false;
      this.currentStep = step;
      this.workflowSteps[this.currentStep - 1].active = true;
    }
  }

  canProceedToNextStep(): boolean {
    switch (this.currentStep) {
      case 1: // Profile selection
        return !!this.profilMenuApp.codPflPfl;
      case 2: // Menu selection
        return this.selectedMenus?.length > 0;
      case 3: // Structure configuration
        return !!this.profilMenuApp.codTstrcTstrc;
      case 4: // Activation state
        return this.profilMenuApp.boolEtatPma !== undefined;
      default:
        return false;
    }
  }

  canNavigateToStep(step: number): boolean {
    // Can only navigate to completed steps or the next immediate step
    if (step <= this.currentStep) return true;

    // Check if all previous steps are completed
    for (let i = 1; i < step; i++) {
      if (!this.workflowSteps[i - 1].completed) {
        return false;
      }
    }
    return true;
  }
  updateStepValidation(): void {
    // Update completion status for all steps
    this.workflowSteps[0].completed = !!this.profilMenuApp.codPflPfl;
    // Step 2 is completed if there are new menus to add OR existing menus
    this.workflowSteps[1].completed = (this.menusToAdd.length > 0 || this.existingMenusForProfile.size > 0);
    this.workflowSteps[2].completed = !!this.profilMenuApp.codTstrcTstrc;
    this.workflowSteps[3].completed = this.profilMenuApp.boolEtatPma !== undefined;
  }

  getStepClass(step: any): string {
    let classes = ['workflow-step'];

    if (step.completed) classes.push('completed');
    if (step.active) classes.push('active');
    if (!this.canNavigateToStep(step.step)) classes.push('disabled');
      return classes.join(' ');
  }
  // Enhanced profile change handler
  onProfileChange(): void {
    console.log('Profile changed:', this.profilMenuApp.codPflPfl);

    // Reset tracking arrays
    this.menusToAdd = [];
    this.menusToRevoke.clear();

    this.updateStepValidation();

    if (this.profilMenuApp.codPflPfl && this.currentStep === 1) {
      // Load existing menus for this profile and pre-select them
      this.loadExistingMenusForProfile(this.profilMenuApp.codPflPfl);
      console.log('the existingMenusForProfile',this.existingMenusForProfile)
      // Pre-select existing menus (for backward compatibility with selectedMenus)
      this.existingMenusForProfile.forEach(menuCode => {
        if (!this.selectedMenus.includes(menuCode)) {
          this.selectedMenus.push(menuCode);
        }
      });

      // Show success state briefly before auto-advancing
      setTimeout(() => {
        this.nextStep();
      }, 600); // Slightly longer delay to show the success state
    }
  }
  // Enhanced menu selection with workflow awareness
  toggleMenuSelection(menu: any): void {
    const menuCode = menu.codMenuMenu;
    const isAlreadyAssigned = this.isMenuAlreadyAssigned(menuCode);

    // If menu is already assigned, don't allow selection/deselection
    // User must use revoke button instead
    if (isAlreadyAssigned) {
      return;
    }

    const index = this.menusToAdd.indexOf(menuCode);

    if (index > -1) {
      this.menusToAdd.splice(index, 1);
    } else {
      this.menusToAdd.push(menuCode);
    }

    // Also update selectedMenus for backward compatibility
    const selectedIndex = this.selectedMenus.indexOf(menuCode);
    if (selectedIndex > -1) {
      this.selectedMenus.splice(selectedIndex, 1);
    } else {
      this.selectedMenus.push(menuCode);
    }

    this.updateStepValidation();
    this.triggerHighlightEffect();
  }

  // Toggle revocation for already-assigned menus
  toggleMenuRevocation(menu: any, event?: Event): void {
    if (event) {
      event.stopPropagation(); // Prevent card click
    }

    const menuCode = menu.codMenuMenu;

    if (this.menusToRevoke.has(menuCode)) {
      this.menusToRevoke.delete(menuCode);
    } else {
      this.menusToRevoke.add(menuCode);
    }

    this.updateStepValidation();
    this.triggerHighlightEffect();
  }

  // Check if menu is marked for revocation
  isMenuMarkedForRevocation(menuCode: string): boolean {
    return this.menusToRevoke.has(menuCode);
  }

  // Get count of new additions
  getAdditionsCount(): number {
    return this.menusToAdd.length;
  }

  // Get count of revocations
  getRevocationsCount(): number {
    return this.menusToRevoke.size;
  }

  // Check if there are any changes to save
  hasMenuChanges(): boolean {
    return this.menusToAdd.length > 0 || this.menusToRevoke.size > 0;
  }
    // Enhanced structure change handler
  onStructureChange(): void {
    this.updateStepValidation();
    // Removed auto-advance behavior to allow users to configure activation state
  }

  // Enhanced activation state handler
  onActivationChange(): void {
    this.updateStepValidation();
  }

  // Override openNew to reset workflow
  openNew() {
    this.action = ActionType.ADD;
    this.profilMenuApp = {
      codAppApp: this.selectedAppCode,
      boolEtatPma: 1 // Default to active (1 for number type)
    } as any;
    this.selectedMenus = [];
    this.submitted = false;

    // Reset workflow
    this.currentStep = 1;
    this.workflowSteps.forEach((step, index) => {
      step.completed = false;
      step.active = index === 0;
    });

    this.getRolesAndMenus(this.selectedAppCode);
  }

  private getRolesAndMenus(appCode: string) {
    this.profilService.getProfilList(appCode).subscribe(
      (result: { data: never[]; }) => {
        this.listRols = result?.data || [];
        this.calculateStatistics();
      },
      (error: { err: any; }) => {
        console.log("error", error.err);
      }
    );

    this.menuApplicationService.getMenuApplicationList(appCode).subscribe(
      (result: { data: never[]; }) => {
        this.listMenu = result?.data || [];
        this.filterMenus();
        this.calculateStatistics();
      },
      (error: { err: any; }) => {
        console.log("error", error.err);
      }
    );
  }

  // Helper methods for template
  getSelectedProfileName(): string {
    const profile = this.listRols.find(r => r.codPflPfl === this.profilMenuApp.codPflPfl);
    return profile?.libpflpfl || this.profilMenuApp.codPflPfl || 'Non sélectionné';
  }

  getSelectedStructureName(): string {
    const structure = this.CodeStructureOptions.find(s => s.value === this.profilMenuApp.codTstrcTstrc);
    return structure?.label || this.profilMenuApp.codTstrcTstrc || 'Non définie';
  }

  getMenuName(menuCode: string): string {
    const menu = this.listMenu.find(m => m.codMenuMenu === menuCode);
    return menu?.libMenuMenu || menuCode;
  }
  // Toggle menu details visibility in review section
  toggleMenuDetails(): void {
    this.showMenuDetails = !this.showMenuDetails;
    // Also toggle accordion for compatibility with new design
    this.showMenuAccordion = !this.showMenuAccordion;
  }

  // Get selected profile object
  getSelectedProfile(): any {
    return this.listRols.find(r => r.codPflPfl === this.profilMenuApp.codPflPfl) || {};
  }

  // ===== UTILITY METHODS =====

  // Safe focus method to prevent autofocus warnings
  private safeFocus(element: HTMLElement | null, delay: number = 100): void {
    if (!element) return;

    // Use requestAnimationFrame and setTimeout to ensure DOM is ready
    requestAnimationFrame(() => {
      setTimeout(() => {
        try {
          // Check if element is visible and focusable
          if (element.offsetParent !== null && !element.hasAttribute('disabled')) {
            element.focus({ preventScroll: true });
          }
        } catch (error) {
          // Silently handle any focus errors to prevent autofocus warnings
          console.debug('Focus prevented by browser policy');
        }
      }, delay);
    });
  }

  // Enhanced search focus handling
  focusSearchInput(): void {
    const searchInput = document.querySelector('.immersive-search-input') as HTMLElement;
    this.safeFocus(searchInput, 200);
  }

  // ===== ACCESSIBILITY IMPROVEMENTS =====

  // Handle keyboard navigation in workflow
  onKeydown(event: KeyboardEvent): void {
    if (event.key === 'Escape' && this.isFullscreenMode) {
      this.closeFullscreen();
    } else if (event.key === 'Enter' && event.ctrlKey) {
      // Ctrl+Enter to save
      if (this.isFormValid()) {
        this.saveProfilMenuApp();
      }
    }
  }

  // Handle search focus event
  onSearchFocus(event: FocusEvent): void {
    const input = event.target as HTMLInputElement;
    if (input) {
      // Select all text for easy replacement
      setTimeout(() => input.select(), 10);
    }
  }

  // ===== PROFILE CARD SELECTION METHODS =====

  selectProfile(profile: any): void {
    if (this.currentStep !== 1) return; // Only allow selection on step 1

    this.profilMenuApp.codPflPfl = profile.codPflPfl;
    this.triggerHighlightEffect();
    this.onProfileChange(); // Trigger existing change handler
  }
  trackByProfileCode(index: number, profile: any): string {
    return profile.codPflPfl;
  }

  // ===== PROFILE MENU COUNT TRACKING METHODS =====

  /**
   * Calculate how many menus each profile has assigned
   */
  calculateProfileMenuCounts(): void {
    this.profileMenuCounts.clear();

    if (!this.profilMenuApps?.length) return;

    this.profilMenuApps.forEach(item => {
      const profileCode = item.codPflPfl;
      if (profileCode) {
        const currentCount = this.profileMenuCounts.get(profileCode) || 0;
        this.profileMenuCounts.set(profileCode, currentCount + 1);
      }
    });
  }

  /**
   * Get the menu count for a specific profile
   */
  getProfileMenuCount(profileCode: string): number {
    return this.profileMenuCounts.get(profileCode) || 0;
  }

  /**
   * Check if a profile already has menus assigned
   */
  profileHasMenus(profileCode: string): boolean {
    return this.getProfileMenuCount(profileCode) > 0;
  }
  /**
   * Load existing menus for the selected profile
   */
  loadExistingMenusForProfile(profileCode: string): void {
    this.existingMenusForProfile.clear();

    if (!profileCode || !this.profilMenuApps?.length) return;

    // Filter by BOTH profile code AND current application
    this.profilMenuApps
      .filter(item =>
        item.codPflPfl === profileCode &&
        item.codAppApp === this.selectedAppCode
      )
      .forEach(item => {
        if (item.codMenuMenu) {
          this.existingMenusForProfile.add(item.codMenuMenu);

        }
      });

    console.log('Loaded existing menus for profile', profileCode, ':', Array.from(this.existingMenusForProfile));
  }

  /**
   * Check if a menu is already assigned to the current profile
   */
  isMenuAlreadyAssigned(menuCode: string): boolean {
    return this.existingMenusForProfile.has(menuCode);
  }
  /**
   * Check if we should show a separator before this menu
   * Returns true if this is the first unassigned menu after assigned menus
   */
  shouldShowSeparatorBefore(menuIndex: number): boolean {
    if (!this.filteredMenus || menuIndex === 0) return false;

    const currentMenu = this.filteredMenus[menuIndex];
    const previousMenu = this.filteredMenus[menuIndex - 1];

    const currentAssigned = this.isMenuAlreadyAssigned(currentMenu?.codMenuMenu);
    const previousAssigned = this.isMenuAlreadyAssigned(previousMenu?.codMenuMenu);

    // Show separator when transitioning from assigned to unassigned
    return previousAssigned && !currentAssigned;
  }

  /**
   * Get count of already-assigned menus
   */
  getAlreadyAssignedCount(): number {
        console.log('waaat',this.filteredMenus.filter(menu => this.isMenuAlreadyAssigned(menu.codMenuMenu)))

    if (!this.filteredMenus) return 0;
    return this.filteredMenus.filter(menu => this.isMenuAlreadyAssigned(menu.codMenuMenu)).length;
  }

  getProfileIcon(profile: any): string {
    const profileName = profile.libpflpfl?.toLowerCase() || '';

    if (profileName.includes('admin') || profileName.includes('administrateur')) return 'shield';
    if (profileName.includes('user') || profileName.includes('utilisateur')) return 'user';
    if (profileName.includes('manager') || profileName.includes('gestionnaire')) return 'crown';
    if (profileName.includes('supervisor') || profileName.includes('superviseur')) return 'eye';
    if (profileName.includes('analyst') || profileName.includes('analyste')) return 'chart-line';
    if (profileName.includes('operator') || profileName.includes('opérateur')) return 'cog';

    return 'user'; // Default icon
  }
    getProfileType(profile: any): string {
    const profileName = profile.libpflpfl?.toLowerCase() || '';

    if (profileName.includes('admin')) return 'Administrateur';
    if (profileName.includes('user')) return 'Utilisateur';
    if (profileName.includes('manager')) return 'Gestionnaire';
    if (profileName.includes('supervisor')) return 'Superviseur';
    if (profileName.includes('analyst')) return 'Analyste';
    if (profileName.includes('operator')) return 'Opérateur';

    return 'Standard';
  }

  // ===== NEW MENU ACCORDION AND CARD FUNCTIONALITY =====

  /**
   * Toggle the expandable menu accordion below the Menu card
   */
  toggleMenuAccordion(): void {
    this.showMenuAccordion = !this.showMenuAccordion;
  }
  /**
   * Remove a specific menu from the selection
   * Used by the (-) buttons on menu tags and in the accordion
   */
  removeMenu(menuCode: string): void {
    if (!this.selectedMenus || !menuCode) {
      return;
    }

    const index = this.selectedMenus.indexOf(menuCode);
    if (index > -1) {
      this.selectedMenus.splice(index, 1);

      // Close accordion if no menus left
      if (this.selectedMenus.length === 0) {
        this.showMenuAccordion = false;
      }

      // Update workflow step completion if method exists
      if (typeof (this as any).updateWorkflowStepCompletion === 'function') {
        (this as any).updateWorkflowStepCompletion();
      }
        // Show feedback message
      this.sharedService?.showSuccess(
        `Le menu ${this.getMenuNameSafe(menuCode)} a été retiré de la sélection.`,
        'Menu supprimé'
      );
    }
  }
  /**
   * Toggle activation state from the État card button
   */
  toggleActivationState(): void {
    // Toggle between 0 (inactive) and 1 (active) as per model definition
    this.profilMenuApp.boolEtatPma = this.profilMenuApp.boolEtatPma === 1 ? 0 : 1;

    // Call existing change handler if it exists
    if (typeof (this as any).onActivationChange === 'function') {
      (this as any).onActivationChange();
    }
  }

  /**
   * Get menu name from the menu code (safe version to avoid duplicates)
   * Used for displaying menu names in tags and accordion
   */
  getMenuNameSafe(menuCode: string): string {
    if (!this.listMenu || !menuCode) {
      return menuCode || 'Menu inconnu';
    }

    const menu = this.listMenu.find(m => m.codMenuMenu === menuCode);
    return menu ? menu.libMenuMenu : menuCode;
  }

  calculateStatistics(): void {
    // Total profil-menus
    this.totalProfilMenus = this.profilMenuApps?.length || 0;

    // Active profil-menus
    this.activeProfilMenus = this.profilMenuApps?.filter(
      item => item.boolEtatPma === true || item.boolEtatPma === 1
    ).length || 0;

    // Inactive profil-menus
    this.inactiveProfilMenus = this.totalProfilMenus -  this.activeProfilMenus;
  }

  onSearch(): void {
    this.applyFilters();
  }

  clearSearch(): void {
    this.searchTerm = '';
    this.applyFilters();
  }

  onFilterChange(): void {
    this.applyFilters();
  }

  refreshData(): void {
    this.searchTerm = '';
    this.selectedStatus = null;
    this.getProfilMenuApps();
  }

  private applyFilters(): void {
    let filtered = [...(this.profilMenuApps || [])];

    // Apply search filter
    if (this.searchTerm && this.searchTerm.trim()) {
      const searchLower = this.searchTerm.toLowerCase().trim();
      filtered = filtered.filter(item =>
        item.codAppApp?.toLowerCase().includes(searchLower) ||
        item.codMenuMenu?.toLowerCase().includes(searchLower) ||
        item.codPflPfl?.toLowerCase().includes(searchLower) ||
        item.codTstrcTstrc?.toLowerCase().includes(searchLower)
      );
    }

    // Apply status filter
    if (this.selectedStatus !== null && this.selectedStatus !== undefined) {
      if (this.selectedStatus === 'active') {
        filtered = filtered.filter(item => item.boolEtatPma === true || item.boolEtatPma === 1);
      } else if (this.selectedStatus === 'inactive') {
        filtered = filtered.filter(item => item.boolEtatPma === false || item.boolEtatPma === 0);
      }
    }

    this.filteredProfilMenuApps = filtered;
  }
}
