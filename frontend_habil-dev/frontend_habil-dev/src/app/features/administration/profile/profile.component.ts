import {
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { ActivatedRoute, Router } from '@angular/router';
import { distinctUntilChanged, Subject, takeUntil } from "rxjs";
import { MessageService, ConfirmationService } from 'primeng/api';
import AOS from 'aos';
import { Profile } from '../../../core/models/profile';
import { ActionType } from '../../../core/enums/actionType.enum';
import { Application } from '../../../core/models/application';
import { ProfilService } from '../../../core/services/backend/profil.service';
import { SharedFrontService } from '../../../core/services/frontend/shared-front.service';
import { ApplicationContextService } from '../../../core/services/frontend/ApplicationContextService.service';
import { CODE_STRUCTURE_OPTIONS, getCodeStructureLabel } from '../../../core/constants/codeStructure';
import { AnimatedDrawerComponent } from '../../../shared/components/animated-drawer/animated-drawer.component';
import { TableColumn, TableComponent } from '../../../shared/table/table.component';
import { ProfilMenuApplicationService } from '../profilmenuapplication/services';
import { MenuApplicationService } from '../../../core/services/backend/menu-application.service';

interface ProfileMenuItem {
  codAppApp: string;
  codMenuMenu: string;
  codPflPfl: string;
  codTstrcTstrc: string;
  libMenuMenu?: string;
  boolEtatPma: boolean | number;
}

@Component({
  selector: "app-profile",
  templateUrl: "./profile.component.html",
  styleUrls: ["./profile.component.scss"],
  standalone: false
})
export class ProfileComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('drawer') drawer!: AnimatedDrawerComponent;
  @ViewChild('profileTable') profileTable!: TableComponent;

  private destroy$ = new Subject<void>();

  // Data
  profile: Profile = {};
  profiles: Profile[] = [];
  filteredProfiles: Profile[] = [];
  selectedProfiles: Profile[] = [];
  applications: Application[] = [];

  // UI State
  showNoAppMessage = true;
  submitted = false;
  action: ActionType = ActionType.ADD;
  public actionType = ActionType;
  loading = false;
  categoryDisabled = false;

  // Selection
  selectedAppCode: string | null = null;

  // Search and Filter
  searchTerm = '';

  // Dashboard Navigation Filter
  isDashboardFilter = false;
  dashboardFilterApp: string | null = null;
  dashboardFilterStatus: string | null = null;
  dashboardFilterAppLabel: string | null = null;

  // Stats
  totalProfiles = 0;
  activeProfiles = 0;
  inactiveProfiles = 0;

  // Profile Details Dialog
  showProfileDetailsDialog = false;
  selectedProfileForDetails: Profile | null = null;
  profileMenus: ProfileMenuItem[] = [];
  activeProfileMenus: ProfileMenuItem[] = [];
  inactiveProfileMenus: ProfileMenuItem[] = [];
  loadingMenus = false;

  // Add Menu to Profile Dialog
  showAddMenuDialog = false;
  availableMenus: any[] = [];
  filteredAvailableMenus: any[] = [];
  selectedMenusToAdd: string[] = [];
  menuSearchTerm = '';
  loadingAvailableMenus = false;

  // Structure selection for new menus
  selectedStructureForNewMenus: string = '';

  tableConfig = {
    columns: [
      {
        field: 'codPflPfl',
        header: 'Code Profil',
        sortable: true,
        type: 'text',
        minWidth: '3rem',
        width: '8rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'libpflpfl',
        header: 'Libellé',
        sortable: true,
        type: 'text',
        minWidth: '3rem',
        width: '16rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'libhdebpfl',
        header: 'Heure début',
        sortable: true,
        type: 'text',
        minWidth: '3rem',
        width: '8rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'libhfinpfl',
        header: 'Heure fin',
        sortable: true,
        type: 'text',
        minWidth: '3rem',
        width: '8rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'codNivhPfl',
        header: 'Type Structure',
        sortable: true,
        type: 'text',
        minWidth: '3rem',
        width: '8rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'boolEtatPfl',
        header: 'Actif',
        sortable: true,
        type: 'boolean',
        minWidth: '3rem',
        width: '6rem',
        filter: true,
        filterType: 'boolean',
        filterStyle: { width: '50%' },
      },
      {
        field: 'boolJouvPfl',
        header: 'Jour ouvrable',
        sortable: true,
        type: 'boolean',
        minWidth: '3rem',
        width: '10rem',
        filter: true,
        filterType: 'boolean',
        filterStyle: { width: '50%' },
      },
      {
        field: 'codCatpPfl',
        header: 'Code Structure',
        sortable: true,
        type: 'text',
        minWidth: '3rem',
        width: '10rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
    ],
    showActions: true,
    actions: {
      view: true,
      edit: true,
      notification: false,
      send: false,
      delete: false,
    },
    styleAction: {
      'min-width': '4rem',
      width: '8rem',
    },
    pagination: true,
    pageSize: 10,
  };

  CodeStructureOptions = CODE_STRUCTURE_OPTIONS;

  constructor(
    private profilService: ProfilService,
    private profilMenuService: ProfilMenuApplicationService,
    private menuApplicationService: MenuApplicationService,
    private sharedService: SharedFrontService,
    private appContext: ApplicationContextService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    AOS.init({
      easing: 'linear',
      mirror: true
    });

    this.route.queryParams.pipe(takeUntil(this.destroy$)).subscribe(params => {
      if (params['fromDashboard'] === 'true') {
        this.isDashboardFilter = true;
        this.dashboardFilterApp = params['filterByApp'] || null;
        this.dashboardFilterStatus = params['filterByStatus'] || null;
        this.dashboardFilterAppLabel = params['appLabel'] || null;

        if (this.dashboardFilterApp) {
          this.appContext.setSelectedApp(this.dashboardFilterApp);
        }
      }
    });

    this.appContext.selectedApp$
      .pipe(
        distinctUntilChanged(),
        takeUntil(this.destroy$)
      )
      .subscribe((appCode) => {
        this.selectedAppCode = appCode;

        if (appCode) {
          this.showNoAppMessage = false;
          this.getProfilesByApp();
        } else {
          this.profiles = [];
          this.filteredProfiles = [];
          this.showNoAppMessage = true;
        }
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  ngAfterViewInit(): void {
    // Component initialized
  }

  onAppsLoaded(applications: Application[]): void {
    console.log("OnAppsLoaded executing");
  }

  onAppSelectionChange(newApp: string) {
    console.log("Selection changed in this page:", newApp);
    setTimeout(() => {
      AOS.refresh();
    }, 100);
  }

  getProfilesByApp() {
    if (!this.selectedAppCode) {
      this.profiles = [];
      this.filteredProfiles = [];
      return;
    }

    this.loading = true;
    this.profilService.getProfilList(this.selectedAppCode).subscribe(
      (result) => {
        this.profiles = (result?.data || []).map((p: any) => ({
          ...p,
          boolEtatPfl: p.boolEtatPfl === "1" || p.boolEtatPfl === 1,
          boolJouvPfl: p.boolJouvPfl === "1" || p.boolJouvPfl === 1,
        }));
        this.filteredProfiles = [...this.profiles];
        this.updateStats();
        this.loading = false;

        setTimeout(() => {
          AOS.refresh();
        }, 100);

        if (this.isDashboardFilter) {
          setTimeout(() => this.applyDashboardFilters(), 100);
        }
      },
      (error) => {
        console.error("Failed to load profiles", error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Erreur lors du chargement des profils'
        });
        this.loading = false;
      }
    );
  }

  updateStats(): void {
    this.totalProfiles = this.profiles.length;
    this.activeProfiles = this.profiles.filter(p => p.boolEtatPfl).length;
    this.inactiveProfiles = this.profiles.filter(p => !p.boolEtatPfl).length;
  }

  openNew() {
    if (!this.selectedAppCode) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Attention',
        detail: 'Veuillez sélectionner une application'
      });
      return;
    }

    this.action = ActionType.ADD;
    this.profile = { codAppApp: this.selectedAppCode } as Profile;
    this.submitted = false;
    this.drawer.openDrawer();
  }

  saveProfil(profile: Profile) {
    this.submitted = true;

    const hasRequiredFields = profile.codPflPfl && profile.libpflpfl && profile.codAppApp;

    if (!hasRequiredFields) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Attention',
        detail: 'Veuillez remplir tous les champs obligatoires'
      });
      return;
    }

    const payload: Profile = {
      ...profile,
      boolEtatPfl: profile.boolEtatPfl ? "1" : "0",
      boolJouvPfl: profile.boolJouvPfl ? "1" : "0",
    };

    this.profilService.ajoutProfil(payload).subscribe(
      (response) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Succès',
          detail: 'Profil créé avec succès'
        });
        this.drawer.closeDrawer();
        this.getProfilesByApp();
      },
      (error) => {
        console.error("Failed to create profile", error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Erreur lors de la création du profil'
        });
      }
    );
  }

  editProfil(profile: Profile) {
    this.action = ActionType.EDIT;
    this.profile = { ...profile };
    this.submitted = false;
    this.onStructureChange(this.profile.codNivhPfl || '');
  }

  edit(profile: Profile) {
    this.submitted = true;

    if (!profile) {
      this.messageService.add({
        severity: 'error',
        summary: 'Erreur',
        detail: 'Données de profil invalides'
      });
      return;
    }

    const payload: Profile = {
      ...profile,
      boolEtatPfl: profile.boolEtatPfl ? "1" : "0",
      boolJouvPfl: profile.boolJouvPfl ? "1" : "0",
    };

    this.profilService.updateProfil(payload).subscribe(
      (response) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Succès',
          detail: 'Profil modifié avec succès'
        });
        this.drawer.closeDrawer();
        this.getProfilesByApp();
      },
      (error) => {
        console.error("Update profil failed", error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Erreur lors de la modification du profil'
        });
      }
    );
  }

  deleteProfil(profile: Profile) {
    this.confirmationService.confirm({
      message: `Êtes-vous sûr de vouloir supprimer le profil "${profile.libpflpfl}" ?`,
      header: 'Confirmation de suppression',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Oui, supprimer',
      rejectLabel: 'Annuler',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Succès',
          detail: 'Profil supprimé avec succès'
        });
        this.getProfilesByApp();
      }
    });
  }

  removeSpaces(event: any): void {
    const input = event.target as HTMLInputElement;
    const value = input.value.replace(/\s/g, '');
    this.profile.codPflPfl = value;
    input.value = value;
  }

  onSearch(): void {
    this.applyFilters();
  }

  applyFilters(): void {
    let filtered = [...this.profiles];

    if (this.searchTerm && this.searchTerm.trim() !== '') {
      const searchLower = this.searchTerm.toLowerCase().trim();
      filtered = filtered.filter(profile =>
        profile.codPflPfl?.toLowerCase().includes(searchLower) ||
        profile.libpflpfl?.toLowerCase().includes(searchLower) ||
        profile.codCatpPfl?.toLowerCase().includes(searchLower)
      );
    }

    this.filteredProfiles = filtered;
  }

  onStructureChange(selectedValue: string) {
    switch (selectedValue) {
      case '0':
        this.profile.codCatpPfl = '0';
        this.categoryDisabled = true;
        break;
      case '1':
        this.profile.codCatpPfl = '1';
        this.categoryDisabled = true;
        break;
      case '2':
        this.profile.codCatpPfl = '2';
        this.categoryDisabled = true;
        break;
      case '7':
        this.profile.codCatpPfl = '7';
        this.categoryDisabled = true;
        break;
      case '3':
        this.profile.codCatpPfl = '3';
        this.categoryDisabled = false;
        break;
      case '4':
        this.profile.codCatpPfl = '4';
        this.categoryDisabled = false;
        break;
      case '5':
        this.profile.codCatpPfl = '5';
        this.categoryDisabled = false;
        break;
      default:
        this.profile.codCatpPfl = '';
        this.categoryDisabled = false;
        break;
    }
  }

  // Dashboard Filter Methods
  applyDashboardFilters(): void {
    const statusBoolean = this.dashboardFilterStatus === '1';
    this.filteredProfiles = this.profiles.filter(p => {
      const profileStatus = typeof p.boolEtatPfl === 'boolean'
        ? p.boolEtatPfl
        : (p.boolEtatPfl === "1" || Number(p.boolEtatPfl) === 1);
      return profileStatus === statusBoolean;
    });

    this.profileTable.applyGlobalFilter(this.filteredProfiles, 'dashboard');

    const statusLabel = statusBoolean ? 'Actif' : 'Inactif';

    this.messageService.add({
      severity: 'info',
      summary: 'Filtre Appliqué',
      detail: `Affichage des profils ${statusLabel.toLowerCase()}s pour ${this.dashboardFilterAppLabel}`,
      life: 5000
    });
  }

  clearDashboardFilters(): void {
    this.isDashboardFilter = false;
    this.dashboardFilterApp = null;
    this.dashboardFilterStatus = null;
    this.dashboardFilterAppLabel = null;

    this.filteredProfiles = [...this.profiles];

    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {},
      replaceUrl: true
    });

    this.profileTable.refreshData();

    this.messageService.add({
      severity: 'success',
      summary: 'Filtres Effacés',
      detail: 'Tous les filtres ont été supprimés',
      life: 3000
    });
  }

  getDashboardFilterMessage(): string {
    if (!this.isDashboardFilter || !this.dashboardFilterAppLabel || !this.dashboardFilterStatus) {
      return '';
    }

    const statusLabel = this.dashboardFilterStatus === '1' ? 'actifs' : 'inactifs';
    return `Affichage des profils ${statusLabel} pour "${this.dashboardFilterAppLabel}"`;
  }

  // ===== PROFILE DETAILS DIALOG =====

  viewProfile(profile: Profile) {
    this.selectedProfileForDetails = profile;
    this.showProfileDetailsDialog = true;
    this.loadProfileMenus(profile.codPflPfl!, profile.codAppApp!);
  }

  closeProfileDetailsDialog() {
    this.showProfileDetailsDialog = false;
    this.selectedProfileForDetails = null;
    this.profileMenus = [];
    this.activeProfileMenus = [];
    this.inactiveProfileMenus = [];
  }

  loadProfileMenus(codPflPfl: string, codAppApp: string) {
    this.loadingMenus = true;

    this.profilMenuService.getProfilMenuApplicationList(codAppApp)
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        (response) => {
          const allMenus = response?.data || [];

          // Filter menus for this specific profile
          this.profileMenus = allMenus
            .filter((item: any) => item.codPflPfl === codPflPfl)
            .map((item: any) => ({
              ...item,
              boolEtatPma: item.boolEtatPma === 1 || item.boolEtatPma === "1",
            }));

          this.activeProfileMenus = this.profileMenus.filter(m => m.boolEtatPma);
          this.inactiveProfileMenus = this.profileMenus.filter(m => !m.boolEtatPma);
          this.loadingMenus = false;
        },
        (error) => {
          console.error("Failed to load profile menus", error);
          this.messageService.add({
            severity: 'error',
            summary: 'Erreur',
            detail: 'Erreur lors du chargement des menus du profil'
          });
          this.loadingMenus = false;
        }
      );
  }

  getStructureLabel(code: string): string {
    return getCodeStructureLabel(code) || code || 'Non défini';
  }

  // ===== MENU MANAGEMENT =====

  deactivateMenu(menu: ProfileMenuItem) {
    this.confirmationService.confirm({
      message: `Êtes-vous sûr de vouloir désactiver le menu "${menu.libMenuMenu || menu.codMenuMenu}" de ce profil ?`,
      header: 'Confirmation de désactivation',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Oui, désactiver',
      rejectLabel: 'Annuler',
      acceptButtonStyleClass: 'p-button-warning',
      accept: () => {
        const payload = {
          ...menu,
          boolEtatPma: 0
        };

        this.profilMenuService.updateProfilMenuApplication(payload)
          .pipe(takeUntil(this.destroy$))
          .subscribe(
            () => {
              this.messageService.add({
                severity: 'success',
                summary: 'Succès',
                detail: 'Menu désactivé avec succès'
              });
              this.loadProfileMenus(menu.codPflPfl, menu.codAppApp);
            },
            (error) => {
              console.error("Failed to deactivate menu", error);
              this.messageService.add({
                severity: 'error',
                summary: 'Erreur',
                detail: 'Erreur lors de la désactivation du menu'
              });
            }
          );
      }
    });
  }

  activateMenu(menu: ProfileMenuItem) {
    const payload = {
      ...menu,
      boolEtatPma: 1
    };

    this.profilMenuService.updateProfilMenuApplication(payload)
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        () => {
          this.messageService.add({
            severity: 'success',
            summary: 'Succès',
            detail: 'Menu activé avec succès'
          });
          this.loadProfileMenus(menu.codPflPfl, menu.codAppApp);
        },
        (error) => {
          console.error("Failed to activate menu", error);
          this.messageService.add({
            severity: 'error',
            summary: 'Erreur',
            detail: "Erreur lors de l'activation du menu"
          });
        }
      );
  }

  removeMenuFromProfile(menu: ProfileMenuItem) {
    this.confirmationService.confirm({
      message: `Êtes-vous sûr de vouloir supprimer définitivement le menu "${menu.libMenuMenu || menu.codMenuMenu}" de ce profil ?`,
      header: 'Confirmation de suppression',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Oui, supprimer',
      rejectLabel: 'Annuler',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.profilMenuService.deleteProfilMenuApplication(
          menu.codMenuMenu,
          menu.codPflPfl,
          menu.codTstrcTstrc,
          menu.codAppApp
        )
          .pipe(takeUntil(this.destroy$))
          .subscribe(
            () => {
              this.messageService.add({
                severity: 'success',
                summary: 'Succès',
                detail: 'Menu supprimé du profil avec succès'
              });
              this.loadProfileMenus(menu.codPflPfl, menu.codAppApp);
            },
            (error) => {
              console.error("Failed to remove menu", error);
              this.messageService.add({
                severity: 'error',
                summary: 'Erreur',
                detail: 'Erreur lors de la suppression du menu'
              });
            }
          );
      }
    });
  }

  // ===== ADD MENU DIALOG =====

  openAddMenuDialog() {
    if (!this.selectedProfileForDetails) return;

    this.showAddMenuDialog = true;
    this.selectedMenusToAdd = [];
    this.menuSearchTerm = '';
    this.selectedStructureForNewMenus = this.selectedProfileForDetails.codNivhPfl || '';
    this.loadAvailableMenus();
  }

  closeAddMenuDialog() {
    this.showAddMenuDialog = false;
    this.availableMenus = [];
    this.filteredAvailableMenus = [];
    this.selectedMenusToAdd = [];
    this.menuSearchTerm = '';
    this.selectedStructureForNewMenus = '';
  }

  loadAvailableMenus() {
    if (!this.selectedAppCode) {
      this.availableMenus = [];
      this.filteredAvailableMenus = [];
      return;
    }

    this.loadingAvailableMenus = true;

    this.menuApplicationService.getMenuApplicationList(this.selectedAppCode)
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        (result: any) => {
          const allMenus = result?.data || [];
          // Filter out menus already in the profile
          const existingMenuCodes = this.profileMenus.map(m => m.codMenuMenu);
          this.availableMenus = allMenus.filter(
            (m: any) => !existingMenuCodes.includes(m.codMenuMenu)
          );
          this.filteredAvailableMenus = [...this.availableMenus];
          this.loadingAvailableMenus = false;
        },
        (error) => {
          console.error("Failed to load available menus", error);
          this.messageService.add({
            severity: 'error',
            summary: 'Erreur',
            detail: 'Erreur lors du chargement des menus disponibles'
          });
          this.loadingAvailableMenus = false;
        }
      );
  }

  filterAvailableMenus() {
    if (!this.menuSearchTerm) {
      this.filteredAvailableMenus = [...this.availableMenus];
    } else {
      const searchLower = this.menuSearchTerm.toLowerCase();
      this.filteredAvailableMenus = this.availableMenus.filter(m =>
        m.codMenuMenu?.toLowerCase().includes(searchLower) ||
        m.libMenuMenu?.toLowerCase().includes(searchLower)
      );
    }
  }

  isMenuSelectedToAdd(codMenuMenu: string): boolean {
    return this.selectedMenusToAdd.includes(codMenuMenu);
  }

  toggleMenuToAdd(codMenuMenu: string) {
    const index = this.selectedMenusToAdd.indexOf(codMenuMenu);
    if (index > -1) {
      this.selectedMenusToAdd.splice(index, 1);
    } else {
      this.selectedMenusToAdd.push(codMenuMenu);
    }
  }

  selectAllAvailableMenus() {
    this.selectedMenusToAdd = this.filteredAvailableMenus.map(m => m.codMenuMenu);
  }

  clearMenuSelection() {
    this.selectedMenusToAdd = [];
  }

  addMenusToProfile() {
    if (!this.selectedProfileForDetails || this.selectedMenusToAdd.length === 0) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Attention',
        detail: 'Veuillez sélectionner au moins un menu'
      });
      return;
    }

    if (!this.selectedStructureForNewMenus) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Attention',
        detail: 'Veuillez sélectionner une structure'
      });
      return;
    }

    const payloadList = this.selectedMenusToAdd.map(codMenuMenu => ({
      codAppApp: this.selectedProfileForDetails!.codAppApp,
      codPflPfl: this.selectedProfileForDetails!.codPflPfl,
      codMenuMenu: codMenuMenu,
      codTstrcTstrc: this.selectedStructureForNewMenus,
      boolEtatPma: 1
    }));

    this.profilMenuService.ajoutListProfilMenuApplication(payloadList)
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        (response) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Succès',
            detail: `${this.selectedMenusToAdd.length} menu(s) ajouté(s) au profil`
          });
          this.closeAddMenuDialog();
          this.loadProfileMenus(
            this.selectedProfileForDetails!.codPflPfl!,
            this.selectedProfileForDetails!.codAppApp!
          );
        },
        (error) => {
          console.error("Failed to add menus to profile", error);
          this.messageService.add({
            severity: 'error',
            summary: 'Erreur',
            detail: "Erreur lors de l'ajout des menus au profil"
          });
        }
      );
  }
}
