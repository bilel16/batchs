import {
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { Subject, takeUntil } from "rxjs";
import { MessageService, ConfirmationService } from 'primeng/api';
import { Pack, PackService } from "../../../core/services/backend/pack.service";
import { ActionType } from "../../../core/enums/actionType.enum";
import { SharedFrontService } from "../../../core/services/frontend/shared-front.service";
import { CODE_STRUCTURE_OPTIONS, getCodeStructureLabel } from "../../../core/constants/codeStructure";
import { AnimatedDrawerComponent } from "../../../shared/components/animated-drawer/animated-drawer.component";
import { Application } from "../../../core/models/application";
import { ApplicationService } from "../../../core/services/backend/application.service";
import { PackProfileService } from "../pack-profile/services/packprofile.service";
import { ProfilService } from "../../../core/services/backend/profil.service";
import AOS from 'aos';

interface PackProfileItem {
  codPackPack: string;
  codPflPfl: string;
  libpflpfl?: string;
  boolEtat: boolean | number;
  codTstrcTstrc?: string;
}

@Component({
  selector: "app-pack",
  templateUrl: "./pack.component.html",
  styleUrls: ["./pack.component.scss"],
  standalone: false
})
export class PackComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild("drawer") drawer!: AnimatedDrawerComponent;

  private destroy$ = new Subject<void>();

  // Data
  pack: Pack = {
    boolActifPack: 0,
    codAppApp: '',
    codCatpPfl: '',
    codNivhPfl: '',
    codPackPack: '',
    libPackPack: ''
  };
  packs: Pack[] = [];
  filteredPacks: Pack[] = [];
  selectedPacks: Pack[] = [];
  applications: Application[] = [];

  // UI State
  submitted = false;
  action: ActionType = ActionType.ADD;
  public actionType = ActionType;
  loading = false;
  categoryDisabled = false;

  // Search and Filter
  searchTerm = '';

  // Stats
  totalPacks = 0;
  activePacks = 0;
  inactivePacks = 0;

  // Pack Details Dialog
  showPackDetailsDialog = false;
  selectedPackForDetails: Pack | null = null;
  packProfiles: PackProfileItem[] = [];
  activePackProfiles: PackProfileItem[] = [];
  inactivePackProfiles: PackProfileItem[] = [];
  loadingProfiles = false;

  // Add Profile to Pack
  showAddProfileDialog = false;
  availableProfiles: any[] = [];
  filteredAvailableProfiles: any[] = [];
  selectedProfilesToAdd: string[] = [];
  profileSearchTerm = '';
  loadingAvailableProfiles = false;

  tableConfig = {
    columns: [
      {
        field: "codPackPack",
        header: "Code Pack",
        sortable: true,
        type: "text",
        minWidth: "3rem",
        width: "10rem",
        filter: true,
        filterType: "text",
        filterStyle: { width: "50%" },
      },
      {
        field: "libPackPack",
        header: "Libellé",
        sortable: true,
        type: "text",
        minWidth: "3rem",
        width: "18rem",
        filter: true,
        filterType: "text",
        filterStyle: { width: "50%" },
      },
      {
        field: "codNivhPfl",
        header: "Type Structure",
        sortable: true,
        type: "text",
        minWidth: "3rem",
        width: "10rem",
        filter: true,
        filterType: "text",
        filterStyle: { width: "50%" },
      },
      {
        field: "codCatpPfl",
        header: "Catégorie",
        sortable: true,
        type: "text",
        minWidth: "3rem",
        width: "10rem",
        filter: true,
        filterType: "text",
        filterStyle: { width: "50%" },
      },
      {
        field: "boolActifPack",
        header: "Actif",
        sortable: true,
        type: "boolean",
        minWidth: "3rem",
        width: "8rem",
        filter: true,
        filterType: "boolean",
        filterStyle: { width: "50%" },
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
      "min-width": "4rem",
      width: "8rem",
    },
    pagination: true,
    pageSize: 10,
  };

  CodeStructureOptions = CODE_STRUCTURE_OPTIONS;

  // Sync Conflicts
  syncConflicts: any[] = [];
  showConflictsDialog = false;

  constructor(
    private packService: PackService,
    private applicationService: ApplicationService,
    private packProfileService: PackProfileService,
    private profilService: ProfilService,
    private sharedService: SharedFrontService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
    AOS.init({
      duration: 1500,
      easing: 'linear',
      once: false,
      mirror: true
    });
    this.loadApplications();
    this.loadPacks();
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      AOS.refresh();
    }, 100);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadApplications() {
    this.applicationService.getAll().subscribe(
      (apps) => {
        this.applications = apps;
      },
      (error) => {
        console.error("Failed to load applications", error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Erreur lors du chargement des applications'
        });
      }
    );
  }

  loadPacks() {
    this.loading = true;
    this.packService.getAll().subscribe(
      (packs) => {
        this.packs = packs.map((p: any) => ({
          ...p,
          boolActifPack: p.boolActifPack === "1" || p.boolActifPack === 1,
        }));
        this.filteredPacks = [...this.packs];
        this.updateStats();
        this.loading = false;
        setTimeout(() => {
          AOS.refresh();
        }, 100);
      },
      (error) => {
        console.error("Failed to load packs", error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Erreur lors du chargement des packs'
        });
        this.loading = false;
      }
    );
  }

  updateStats(): void {
    this.totalPacks = this.packs.length;
    this.activePacks = this.packs.filter(p => p.boolActifPack).length;
    this.inactivePacks = this.packs.filter(p => !p.boolActifPack).length;
  }

  openNew() {
    this.action = ActionType.ADD;
    this.pack = { boolActifPack: 1 } as Pack;
    this.submitted = false;
    this.drawer.openDrawer();
  }

  savePack(pack: Pack) {
    this.submitted = true;

    const hasRequiredFields = pack.codPackPack && pack.libPackPack;

    if (!hasRequiredFields) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Attention',
        detail: 'Veuillez remplir tous les champs obligatoires'
      });
      return;
    }

    const payload: Pack = {
      ...pack,
      boolActifPack: pack.boolActifPack ? 1 : 0,
    };

    this.packService.create(payload).subscribe(
      (response) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Succès',
          detail: 'Pack créé avec succès'
        });
        this.drawer.closeDrawer();
        this.loadPacks();
      },
      (error) => {
        console.error("Failed to create pack", error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Erreur lors de la création du pack'
        });
      }
    );
  }

  editPack(pack: Pack) {
    this.action = ActionType.EDIT;
    this.pack = { ...pack };
    this.submitted = false;
    this.onStructureChange(this.pack.codNivhPfl || '');
  }

  edit(pack: Pack) {
    this.submitted = true;

    if (!pack || !pack.codPackPack) {
      this.messageService.add({
        severity: 'error',
        summary: 'Erreur',
        detail: 'Données de pack invalides'
      });
      return;
    }

    const payload: Pack = {
      ...pack,
      boolActifPack: pack.boolActifPack ? 1 : 0,
    };

    this.packService.update(pack.codPackPack, payload).subscribe(
      (response) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Succès',
          detail: 'Pack modifié avec succès'
        });
        this.drawer.closeDrawer();
        this.loadPacks();
      },
      (error) => {
        console.error("Update pack failed", error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Erreur lors de la modification du pack'
        });
      }
    );
  }

  deletePack(pack: Pack) {
    if (!pack.codPackPack) {
      return;
    }

    this.confirmationService.confirm({
      message: `Êtes-vous sûr de vouloir supprimer le pack "${pack.libPackPack}" ?`,
      header: 'Confirmation de suppression',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Oui, supprimer',
      rejectLabel: 'Annuler',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.packService.delete(pack.codPackPack!).subscribe(
          () => {
            this.messageService.add({
              severity: 'success',
              summary: 'Succès',
              detail: 'Pack supprimé avec succès'
            });
            this.loadPacks();
          },
          (error) => {
            console.error("Delete pack failed", error);
            this.messageService.add({
              severity: 'error',
              summary: 'Erreur',
              detail: 'Erreur lors de la suppression du pack'
            });
          }
        );
      }
    });
  }

  removeSpaces(event: any): void {
    const input = event.target as HTMLInputElement;
    const value = input.value.replace(/\s/g, "");
    this.pack.codPackPack = value;
    input.value = value;
  }

  onSearch(): void {
    this.applyFilters();
  }

  applyFilters(): void {
    let filtered = [...this.packs];

    if (this.searchTerm && this.searchTerm.trim() !== '') {
      const searchLower = this.searchTerm.toLowerCase().trim();
      filtered = filtered.filter(pack =>
        pack.codPackPack?.toLowerCase().includes(searchLower) ||
        pack.libPackPack?.toLowerCase().includes(searchLower) ||
        pack.codCatpPfl?.toLowerCase().includes(searchLower) ||
        pack.codNivhPfl?.toLowerCase().includes(searchLower)
      );
    }

    this.filteredPacks = filtered;
  }

  onStructureChange(selectedValue: string) {
    switch (selectedValue) {
      case '0':
        this.pack.codCatpPfl = '0';
        this.categoryDisabled = true;
        break;
      case '1':
        this.pack.codCatpPfl = '1';
        this.categoryDisabled = true;
        break;
      case '2':
        this.pack.codCatpPfl = '2';
        this.categoryDisabled = true;
        break;
      case '7':
        this.pack.codCatpPfl = '7';
        this.categoryDisabled = true;
        break;
      case '3':
        this.pack.codCatpPfl = '3';
        this.categoryDisabled = false;
        break;
      case '4':
        this.pack.codCatpPfl = '4';
        this.categoryDisabled = false;
        break;
      case '5':
        this.pack.codCatpPfl = '5';
        this.categoryDisabled = false;
        break;
      default:
        this.pack.codCatpPfl = '';
        this.categoryDisabled = false;
        break;
    }
  }

  // ===== PACK DETAILS DIALOG =====

  viewPack(pack: Pack) {
    this.selectedPackForDetails = pack;
    this.showPackDetailsDialog = true;
    this.loadPackProfiles(pack.codPackPack!);
  }

  closePackDetailsDialog() {
    this.showPackDetailsDialog = false;
    this.selectedPackForDetails = null;
    this.packProfiles = [];
    this.activePackProfiles = [];
    this.inactivePackProfiles = [];
  }

  loadPackProfiles(codPackPack: string) {
    this.loadingProfiles = true;
    this.packProfileService.getPackProfilesByPack(codPackPack)
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        (response) => {
          this.packProfiles = (response?.data || []).map((item: any) => ({
            ...item,
            boolEtat: item.boolEtat === 1 || item.boolEtat === "1",
          }));
          this.activePackProfiles = this.packProfiles.filter(p => p.boolEtat);
          this.inactivePackProfiles = this.packProfiles.filter(p => !p.boolEtat);
          this.loadingProfiles = false;
        },
        (error) => {
          console.error("Failed to load pack profiles", error);
          this.messageService.add({
            severity: 'error',
            summary: 'Erreur',
            detail: 'Erreur lors du chargement des profils du pack'
          });
          this.loadingProfiles = false;
        }
      );
  }

  getStructureLabel(code: string): string {
    return getCodeStructureLabel(code) || code || 'Non défini';
  }

  // ===== PROFILE MANAGEMENT =====

  deactivateProfile(profile: PackProfileItem) {
    this.confirmationService.confirm({
      message: `Êtes-vous sûr de vouloir désactiver le profil "${profile.libpflpfl || profile.codPflPfl}" de ce pack ?`,
      header: 'Confirmation de désactivation',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Oui, désactiver',
      rejectLabel: 'Annuler',
      acceptButtonStyleClass: 'p-button-warning',
      accept: () => {
        this.packProfileService.updatePackProfileStatus(profile.codPackPack, profile.codPflPfl, 0)
          .pipe(takeUntil(this.destroy$))
          .subscribe(
            () => {
              this.messageService.add({
                severity: 'success',
                summary: 'Succès',
                detail: 'Profil désactivé avec succès'
              });
              this.loadPackProfiles(profile.codPackPack);
            },
            (error) => {
              console.error("Failed to deactivate profile", error);
              this.messageService.add({
                severity: 'error',
                summary: 'Erreur',
                detail: 'Erreur lors de la désactivation du profil'
              });
            }
          );
      }
    });
  }

  activateProfile(profile: PackProfileItem) {
    this.packProfileService.updatePackProfileStatus(profile.codPackPack, profile.codPflPfl, 1)
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        () => {
          this.messageService.add({
            severity: 'success',
            summary: 'Succès',
            detail: 'Profil activé avec succès'
          });
          this.loadPackProfiles(profile.codPackPack);
        },
        (error) => {
          console.error("Failed to activate profile", error);
          this.messageService.add({
            severity: 'error',
            summary: 'Erreur',
            detail: "Erreur lors de l'activation du profil"
          });
        }
      );
  }

  removeProfileFromPack(profile: PackProfileItem) {
    this.confirmationService.confirm({
      message: `Êtes-vous sûr de vouloir supprimer définitivement le profil "${profile.libpflpfl || profile.codPflPfl}" de ce pack ?`,
      header: 'Confirmation de suppression',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Oui, supprimer',
      rejectLabel: 'Annuler',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.packProfileService.deletePackProfile(profile.codPackPack, profile.codPflPfl)
          .pipe(takeUntil(this.destroy$))
          .subscribe(
            () => {
              this.messageService.add({
                severity: 'success',
                summary: 'Succès',
                detail: 'Profil supprimé du pack avec succès'
              });
              this.loadPackProfiles(profile.codPackPack);
            },
            (error) => {
              console.error("Failed to remove profile", error);
              this.messageService.add({
                severity: 'error',
                summary: 'Erreur',
                detail: 'Erreur lors de la suppression du profil'
              });
            }
          );
      }
    });
  }

  // ===== ADD PROFILE DIALOG =====

  openAddProfileDialog() {
    if (!this.selectedPackForDetails) return;

    this.showAddProfileDialog = true;
    this.selectedProfilesToAdd = [];
    this.profileSearchTerm = '';
    this.loadAvailableProfiles();
  }

  closeAddProfileDialog() {
    this.showAddProfileDialog = false;
    this.availableProfiles = [];
    this.filteredAvailableProfiles = [];
    this.selectedProfilesToAdd = [];
    this.profileSearchTerm = '';
  }

  loadAvailableProfiles() {
    if (!this.selectedPackForDetails?.codNivhPfl) {
      this.availableProfiles = [];
      this.filteredAvailableProfiles = [];
      return;
    }

    this.loadingAvailableProfiles = true;
    const structureCode = Number(this.selectedPackForDetails.codNivhPfl);

    this.profilService.getProfilsByStructure(structureCode)
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        (result: any) => {
          const allProfiles = result?.data || [];
          // Filter out profiles already in the pack
          const existingProfileCodes = this.packProfiles.map(p => p.codPflPfl);
          this.availableProfiles = allProfiles.filter(
            (p: any) => !existingProfileCodes.includes(p.codPflPfl)
          );
          this.filteredAvailableProfiles = [...this.availableProfiles];
          this.loadingAvailableProfiles = false;
        },
        (error) => {
          console.error("Failed to load available profiles", error);
          this.messageService.add({
            severity: 'error',
            summary: 'Erreur',
            detail: 'Erreur lors du chargement des profils disponibles'
          });
          this.loadingAvailableProfiles = false;
        }
      );
  }

  filterAvailableProfiles() {
    if (!this.profileSearchTerm) {
      this.filteredAvailableProfiles = [...this.availableProfiles];
    } else {
      const searchLower = this.profileSearchTerm.toLowerCase();
      this.filteredAvailableProfiles = this.availableProfiles.filter(p =>
        p.codPflPfl?.toLowerCase().includes(searchLower) ||
        p.libpflpfl?.toLowerCase().includes(searchLower)
      );
    }
  }

  isProfileSelectedToAdd(codPflPfl: string): boolean {
    return this.selectedProfilesToAdd.includes(codPflPfl);
  }

  toggleProfileToAdd(codPflPfl: string) {
    const index = this.selectedProfilesToAdd.indexOf(codPflPfl);
    if (index > -1) {
      this.selectedProfilesToAdd.splice(index, 1);
    } else {
      this.selectedProfilesToAdd.push(codPflPfl);
    }
  }

  selectAllAvailableProfiles() {
    this.selectedProfilesToAdd = this.filteredAvailableProfiles.map(p => p.codPflPfl);
  }

  clearProfileSelection() {
    this.selectedProfilesToAdd = [];
  }

  addProfilesToPack() {
    if (!this.selectedPackForDetails || this.selectedProfilesToAdd.length === 0) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Attention',
        detail: 'Veuillez sélectionner au moins un profil'
      });
      return;
    }

    const payloadList = this.selectedProfilesToAdd.map(codPflPfl => ({
      codPackPack: this.selectedPackForDetails!.codPackPack,
      codPflPfl: codPflPfl,
      codTstrcTstrc: this.selectedPackForDetails!.codNivhPfl,
      boolEtat: 1
    }));

    this.packProfileService.createBatchPackProfiles(payloadList)
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        (response) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Succès',
            detail: `${this.selectedProfilesToAdd.length} profil(s) ajouté(s) au pack`
          });
          this.closeAddProfileDialog();
          this.loadPackProfiles(this.selectedPackForDetails!.codPackPack!);
        },
        (error) => {
          console.error("Failed to add profiles to pack", error);
          this.messageService.add({
            severity: 'error',
            summary: 'Erreur',
            detail: "Erreur lors de l'ajout des profils au pack"
          });
        }
      );
  }
  confirmSync(pack: Pack): void {
    this.confirmationService.confirm({
      message: `Êtes-vous sûr de vouloir synchroniser le pack <strong>${pack.libPackPack}</strong> ?<br><br>Cette action mettra à jour tous les profils associés à ce pack.`,
      header: 'Confirmation de synchronisation',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Oui, synchroniser',
      rejectLabel: 'Annuler',
      acceptButtonStyleClass: 'p-button-info',
      rejectButtonStyleClass: 'p-button-text',
      accept: () => {
        this.syncPack(pack);
      }
    });
  }

  syncPack(pack: Pack): void {
    if (!pack.codPackPack) {
      this.messageService.add({
        severity: 'error',
        summary: 'Erreur',
        detail: 'Code pack invalide'
      });
      return;
    }

    this.loading = true;

    this.packProfileService.syncPackProfiles(pack.codPackPack)
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        (result: any) => {
          const syncData = result?.data || result;
          const conflicts = syncData?.conflicts || [];

          if (conflicts.length > 0) {
            // Show warning with conflict details
            const revokedCount = conflicts.filter((c: any) => c.conflictType === 'REVOKED').length;
            const customCount = conflicts.filter((c: any) => c.conflictType === 'CUSTOM').length;
            const deactivatedCount = conflicts.filter((c: any) => c.conflictType === 'WILL_BE_DEACTIVATED').length;

            let detail = `Pack "${pack.libPackPack}" synchronisé.`;
            if (revokedCount > 0) {
              detail += ` ⚠️ ${revokedCount} profil(s) révoqué(s) ont été réactivés.`;
            }
            if (deactivatedCount > 0) {
              detail += ` ⚠️ ${deactivatedCount} profil(s) actif(s) ont été désactivés.`;
            }
            if (customCount > 0) {
              detail += ` ⚠️ ${customCount} conflit(s) avec des profils personnalisés.`;
            }

            this.messageService.add({
              severity: 'warn',
              summary: 'Synchronisation avec avertissements',
              detail: detail,
              sticky: true
            });

            // Store conflicts for display
            this.syncConflicts = conflicts;
            this.showConflictsDialog = true;
          } else {
            this.messageService.add({
              severity: 'success',
              summary: 'Synchronisation réussie',
              detail: `Pack "${pack.libPackPack}" synchronisé avec succès`
            });
          }

          this.loadPacks();
          if (this.showPackDetailsDialog &&
            this.selectedPackForDetails?.codPackPack === pack.codPackPack) {
            this.loadPackProfiles(pack.codPackPack);
          }
          this.loading = false;
        },
        (error) => {
          console.error("Sync pack failed", error);
          this.messageService.add({
            severity: 'error',
            summary: 'Erreur',
            detail: 'Erreur lors de la synchronisation du pack'
          });
          this.loading = false;
        }
      );
  }
}
