import {
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
} from "@angular/core";
import { Subject, takeUntil } from "rxjs";

import {CODE_STRUCTURE_OPTIONS, getCodeStructureLabel} from "../../../core/constants/codeStructure";
import { ActionType } from '../../../core/enums/actionType.enum';
import { Pack, PackService } from '../../../core/services/backend/pack.service';
import { ProfilService } from '../../../core/services/backend/profil.service';
import { SharedFrontService } from '../../../core/services/frontend/shared-front.service';
import { PackProfile } from './models';
import { PackProfileService } from './services/packprofile.service';
import AOS from 'aos';

@Component({
  selector: "app-pack-profile",
  templateUrl: "./pack-profile.component.html",
  styleUrls: ["./pack-profile.component.scss"],
  standalone: false
})
export class PackProfileComponent implements OnInit, AfterViewInit, OnDestroy {
  private destroy$ = new Subject<void>();

  packProfile: PackProfile = {
    codPackPack: "",
    codPflPfl: "",
    codTstrcTstrc: ""
  };
  packProfiles: PackProfile[] = [];
  filteredPacks: PackProfile[] = [];
  selectedPackProfiles: PackProfile[] = [];
  submitted = false;
  action: ActionType = ActionType.ADD;
  loading = false;

  packs: Pack[] = [];
  selectedPack: Pack;
  listProfiles: any[] = [];

  selectedProfiles: string[] = [];
  public actionType = ActionType;

  // Search and filter
  searchTerm: string = '';
  selectedStatus: string | null = null;
  statusOptions = [
    { label: 'Actif', value: 'active' },
    { label: 'Inactif', value: 'inactive' }
  ];

  // Stats
  totalPacks: number = 0;
  activePacks: number = 0;
  inactivePacks: number = 0;

  // Grid-based profile selection properties
  profileSearchTerm: string = '';
  filteredProfiles: any[] = [];
  paginatedProfiles: any[] = [];
  currentPage: number = 0;
  itemsPerPage: number = 6;

  // Fullscreen modal properties
  isFullscreenModalOpen: boolean = false;
  isHighlighting: boolean = false;
  isSaving: boolean = false;
  pageSizeOptions = [
    { label: '6', value: 6 },
    { label: '12', value: 12 },
    { label: '18', value: 18 },
    { label: '24', value: 24 }
  ];

  // Cache for profile priorities
  private profilePriorityCache: Map<string, string> = new Map();

  // Review section properties
  showProfileAccordion: boolean = false;

  // Workflow step management
  currentStep: number = 1;
  totalSteps: number = 4;
  workflowSteps = [
    {
      step: 1,
      title: 'Sélection du Pack',
      subtitle: 'Choisissez le pack à configurer',
      icon: 'pi-box',
      completed: false,
      active: true
    },
    {
      step: 2,
      title: 'Sélection des Profils',
      subtitle: 'Définissez les profils du pack',
      icon: 'pi-users',
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

  constructor(
    private packProfileService: PackProfileService,
    private packService: PackService,
    private profilService: ProfilService,
    private sharedService: SharedFrontService
  ) { }  ngOnInit() {
    // Initialize AOS
    AOS.init({
      duration: 1500,
      easing: 'linear',
      once: false,
      mirror: true
    });
    
    this.getPackProfiles();
    this.loadPacks();
  }

  ngAfterViewInit(): void {
    // Refresh AOS after view initialization to detect all elements
    setTimeout(() => {
      AOS.refresh();
    }, 100);
  }

  ngOnDestroy(): void {
    console.log("PackProfile component destroying");
    this.destroy$.next();
    this.destroy$.complete();
  }
  getPackProfiles() {
    console.log("getPackProfiles");
    this.loading = true;
    this.packProfileService
      .getAllPackProfiles()
      .subscribe(
        (res: { data: any; }) => {
          this.packProfiles = (res?.data || []).map((item: any) => ({
            ...item,
            boolEtat: item.boolEtat === 1 || item.boolEtat === "1",
          }));
          this.filteredPacks = [...this.packProfiles];
          this.calculateStats();
          this.loading = false;
          
          // Refresh AOS after data is loaded
          setTimeout(() => {
            AOS.refresh();
          }, 100);
        },
        (err: any) => {
          this.sharedService.handleError(
            err,
            "Impossible de charger les pack-profils"
          );
          this.loading = false;
        }
      );
  }

  calculateStats() {
    this.totalPacks = this.packProfiles.length;
    this.activePacks = this.packProfiles.filter(p => p.boolEtat).length;
    this.inactivePacks = this.totalPacks - this.activePacks;
  }

  onSearch() {
    this.applyFilters();
  }

  clearSearch() {
    this.searchTerm = '';
    this.applyFilters();
  }

  onFilterChange() {
    this.applyFilters();
  }

  applyFilters() {
    let filtered = [...this.packProfiles];

    // Apply search filter
    if (this.searchTerm) {
      const searchLower = this.searchTerm.toLowerCase();
      filtered = filtered.filter(pack =>
        pack.codPackPack?.toLowerCase().includes(searchLower) ||
        pack.codPflPfl?.toLowerCase().includes(searchLower) ||
        pack.codTstrcTstrc?.toLowerCase().includes(searchLower)
      );
    }

    // Apply status filter
    if (this.selectedStatus) {
      if (this.selectedStatus === 'active') {
        filtered = filtered.filter(pack => pack.boolEtat);
      } else if (this.selectedStatus === 'inactive') {
        filtered = filtered.filter(pack => !pack.boolEtat);
      }
    }

    this.filteredPacks = filtered;
  }
  refreshData() {
    this.getPackProfiles();
    this.sharedService.showSuccess('Données actualisées', 'Succès');
    
    // Refresh AOS after data refresh
    setTimeout(() => {
      AOS.refresh();
    }, 100);
  }

  getProfileCount(pack: PackProfile): string {
    return '1 profil';
  }

  viewPack(pack: PackProfile) {
    console.log('View pack:', pack);
    this.sharedService.showInfo(
      `Pack: ${pack.codPackPack}\nProfil: ${pack.codPflPfl}\nStructure: ${pack.codTstrcTstrc}`,
      'Détails du Pack'
    );
  }

  editPack(pack: PackProfile) {
    this.action = ActionType.EDIT;
    this.packProfile = { ...pack };
    this.selectedProfiles = [pack.codPflPfl];
    this.submitted = false;
    this.openFormModal();
  }

  confirmDelete(pack: PackProfile) {
    if (confirm(`Êtes-vous sûr de vouloir supprimer le pack ${pack.codPackPack} ?`)) {
      this.deletePack(pack);
    }
  }

  deletePack(pack: PackProfile) {
    this.packProfileService.deletePackProfile(pack.codPackPack, pack.codPflPfl)
      .subscribe(
        (response) => {
          this.sharedService.handleSuccess(response, "Pack supprimé avec succès");
          this.getPackProfiles();
        },
        (err: any) => {
          this.sharedService.handleError(err, "Erreur lors de la suppression");
        }
      );
  }

  openFormModal() {
    if (this.action === ActionType.ADD) {
      this.packProfile = {
        boolEtat: 1
      } as any;
      this.selectedProfiles = [];
      this.currentStep = 1;
      this.workflowSteps.forEach((step, index) => {
        step.completed = false;
        step.active = index === 0;
      });
    }
    this.isFullscreenModalOpen = true;
    document.body.style.overflow = 'hidden';
  }

  closeFormModal() {
    this.isFullscreenModalOpen = false;
    document.body.style.overflow = 'auto';
    this.action = ActionType.ADD;
  }

  savePackProfile() {
    this.submitted = true;
    this.isSaving = true;

    if (!this.packProfile.codPackPack || !this.selectedProfiles?.length) {
      this.sharedService.showWarn(
        "Veuillez sélectionner un pack et au moins un profil",
        "Validation"
      );
      this.isSaving = false;
      return;
    }

    const payloadList: any[] = this.selectedProfiles.map(
      (profileCode) => ({
        codPackPack: this.packProfile.codPackPack,
        codPflPfl: profileCode,
        codTstrcTstrc: this.packProfile.codTstrcTstrc,
        boolEtat: this.packProfile.boolEtat ? 1 : 0,
      })
    );

    this.packProfileService
      .createBatchPackProfiles(payloadList)
      .subscribe(
        (response) => {
          this.sharedService.handleSuccess(
            response,
            `🎉 ${payloadList.length} Pack-Profil créés avec succès`
          );
          this.getPackProfiles();
          this.closeFormModal();
          this.isSaving = false;
        },
        (err) => {
          this.sharedService.handleError(
            err,
            "Erreur lors de l'ajout des pack-profils"
          );
          this.isSaving = false;
        }
      );
  }

  updatePackProfile() {
    const payload: PackProfile = {
      ...this.packProfile,
      boolEtat: this.packProfile.boolEtat ? 1 : 0,
    };

    this.packProfileService.updatePackProfile(
      this.packProfile.codPackPack,
      this.packProfile.codPflPfl,
      payload
    ).subscribe(
      (response) => {
        this.sharedService.handleSuccess(response, "PackProfile modifié");
        this.getPackProfiles();
        this.closeFormModal();
      },
      (err: any) => {
        this.sharedService.handleError(err, "Erreur lors de la modification");
      }
    );
  }

  // Grid-based profile selection methods
  filterProfiles(): void {
    if (!this.listProfiles) {
      this.filteredProfiles = [];
      this.updatePagination();
      return;
    }

    if (!this.profileSearchTerm) {
      this.filteredProfiles = [...this.listProfiles];
    } else {
      const searchTerm = this.profileSearchTerm.toLowerCase();
      this.filteredProfiles = this.listProfiles.filter(profile =>
        profile.libpflpfl?.toLowerCase().includes(searchTerm) ||
        profile.codPflPfl?.toLowerCase().includes(searchTerm)
      );
    }

    this.currentPage = 0;
    this.updatePagination();
  }

  updatePagination(): void {
    const startIndex = this.currentPage * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.paginatedProfiles = this.filteredProfiles.slice(startIndex, endIndex);
  }

  onPageChange(event: any): void {
    this.currentPage = event.page;
    this.updatePagination();
  }

  isProfileSelected(profileCode: string): boolean {
    return this.selectedProfiles.includes(profileCode);
  }

  clearSelection(): void {
    this.selectedProfiles = [];
  }

  trackByProfileCode(index: number, profile: any): string {
    return profile.codPflPfl;
  }

  getProgressPercentage(): number {
    let progress = 0;

    if (this.packProfile.codPackPack) progress += 33;
    if (this.selectedProfiles?.length > 0) progress += 33;
    if (this.packProfile.codTstrcTstrc) progress += 34;

    return progress;
  }

  selectAllVisibleProfiles(): void {
    if (!this.paginatedProfiles?.length) return;

    this.paginatedProfiles.forEach(profile => {
      const profileCode = profile.codPflPfl;
      if (!this.selectedProfiles.includes(profileCode)) {
        this.selectedProfiles.push(profileCode);
      }
    });

    this.triggerHighlightEffect();
  }

  clearSearchProfile(): void {
    this.profileSearchTerm = '';
    this.filterProfiles();
  }

  resetSearch(): void {
    this.profileSearchTerm = '';
    this.currentPage = 0;
    this.filterProfiles();
  }

  onPageSizeChange(): void {
    this.currentPage = 0;
    this.updatePagination();
  }

  getTotalPages(): number {
    return Math.ceil((this.filteredProfiles?.length || 0) / this.itemsPerPage);
  }

  getItemsRangeText(): string {
    if (!this.filteredProfiles?.length) return '0 éléments';

    const start = this.currentPage * this.itemsPerPage + 1;
    const end = Math.min((this.currentPage + 1) * this.itemsPerPage, this.filteredProfiles.length);

    return `${start}-${end} sur ${this.filteredProfiles.length} éléments`;
  }

  triggerHighlightEffect(): void {
    this.isHighlighting = true;
    setTimeout(() => {
      this.isHighlighting = false;
    }, 600);
  }

  getProfileIcon(profile: any): string {
    const profileName = profile.libpflpfl?.toLowerCase() || '';

    if (profileName.includes('admin')) return 'shield';
    if (profileName.includes('user') || profileName.includes('utilisateur')) return 'user';
    if (profileName.includes('manager') || profileName.includes('gestionnaire')) return 'crown';
    if (profileName.includes('supervisor')) return 'eye';

    return 'user';
  }

  getProfilePriority(profile: any): string {
    const profileCode = profile.codPflPfl || '';

    if (this.profilePriorityCache.has(profileCode)) {
      return this.profilePriorityCache.get(profileCode)!;
    }

    let hash = 0;
    for (let i = 0; i < profileCode.length; i++) {
      const char = profileCode.charCodeAt(i);
      hash = ((hash << 5) - hash) + char;
      hash = hash & hash;
    }

    const priorities = ['Haute', 'Moyenne', 'Normale'];
    const index = Math.abs(hash) % priorities.length;
    const priority = priorities[index];

    this.profilePriorityCache.set(profileCode, priority);
    return priority;
  }

  isFormValid(): boolean {
    return !!(
      this.packProfile.codPackPack &&
      this.selectedProfiles?.length > 0
    );
  }

  getValidationMessage(): string {
    if (!this.packProfile.codPackPack) {
      return 'Veuillez sélectionner un pack';
    }

    if (!this.selectedProfiles?.length) {
      return 'Veuillez sélectionner au moins un profil';
    }

    return 'Configuration valide';
  }

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
      pack: this.packs.find(p => p.codPackPack === this.packProfile.codPackPack),
      profiles: this.listProfiles.filter(p => this.selectedProfiles.includes(p.codPflPfl)),
      structure: this.CodeStructureOptions.find(s => s.value === this.packProfile.codTstrcTstrc),
      active: this.packProfile.boolEtat
    };

    const profileList = config.profiles.map(profile => `• ${profile.libpflpfl}`).join('\n');
    const statusText = config.active ? 'Actif' : 'Inactif';

    const previewMessage = `
    APERÇU DE LA CONFIGURATION
    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    📦 Pack: ${config.pack?.libPackPack || 'Non défini'}
    🏢 Structure: ${config.structure?.label || 'Non définie'}
    ⚡ État: ${statusText}

    👥 Profils sélectionnés (${config.profiles.length}):
    ${profileList || '• Aucun profil sélectionné'}

    ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    Configuration prête à être sauvegardée
    `;

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
      case 1:
        return !!this.packProfile.codPackPack;
      case 2:
        return this.selectedProfiles?.length > 0;
      case 3:
        return !!this.packProfile.codTstrcTstrc;
      case 4:
        return this.packProfile.boolEtat !== undefined;
      default:
        return false;
    }
  }

  canNavigateToStep(step: number): boolean {
    if (step <= this.currentStep) return true;

    for (let i = 1; i < step; i++) {
      if (!this.workflowSteps[i - 1].completed) {
        return false;
      }
    }
    return true;
  }

  updateStepValidation(): void {
    this.workflowSteps[0].completed = !!this.packProfile.codPackPack;
    this.workflowSteps[1].completed = this.selectedProfiles?.length > 0;
    this.workflowSteps[2].completed = !!this.packProfile.codTstrcTstrc;
    this.workflowSteps[3].completed = this.packProfile.boolEtat !== undefined;
  }

  getStepClass(step: any): string {
    let classes = ['workflow-step'];

    if (step.completed) classes.push('completed');
    if (step.active) classes.push('active');
    if (!this.canNavigateToStep(step.step)) classes.push('disabled');

    return classes.join(' ');
  }

  onPackChange(event: any): void {
    const selectedCode = event.value; // this is codPackPack
    this.selectedPack = this.packs.find(pack => pack.codPackPack === selectedCode);
    console.log('Pack changed:', selectedCode, this.selectedPack);
    this.updateStepValidation();
    if (this.selectedPack) {
      this.loadProfiles();
    }
    if (selectedCode && this.currentStep === 1) {
      setTimeout(() => {
        this.nextStep();
      }, 600);
    }
  }

  toggleProfileSelection(profile: any): void {
    const profileCode = profile.codPflPfl;
    const index = this.selectedProfiles.indexOf(profileCode);

    if (index > -1) {
      this.selectedProfiles.splice(index, 1);
    } else {
      this.selectedProfiles.push(profileCode);
    }

    this.updateStepValidation();
    this.triggerHighlightEffect();
  }

  onStructureChange(): void {
    this.updateStepValidation();
  }

  onActivationChange(): void {
    this.updateStepValidation();
  }

  private loadPacks() {
    // Load all packs
    this.packService.getAll().subscribe(
      (packs) => {
        this.packs = packs;
      },
      (error) => {
        console.log("error loading packs", error);
      }
    );
  }
  private loadProfiles() {
    // Load profiles
    this.profilService.getProfilsByStructure(Number(this.selectedPack.codNivhPfl)).subscribe(
      (result: { data: never[]; }) => {
        this.listProfiles = result?.data || [];
        this.filterProfiles();
        this.calculateStats();
        
        // Refresh AOS after profiles load
        setTimeout(() => {
          AOS.refresh();
        }, 100);
      },
      (error: { err: any; }) => {
        console.log("error loading profiles", error.err);
      }
    );
  }

  getSelectedPackName(): string {
    const pack = this.packs.find(p => p.codPackPack === this.packProfile.codPackPack);
    return pack?.libPackPack || this.packProfile.codPackPack || 'Non sélectionné';
  }

  getSelectedStructureName(): string {
    const structure = this.CodeStructureOptions.find(s => s.value === this.packProfile.codTstrcTstrc);
    return structure?.label || this.packProfile.codTstrcTstrc || 'Non définie';
  }

  getProfileName(profileCode: string): string {
    const profile = this.listProfiles.find(p => p.codPflPfl === profileCode);
    return profile?.libpflpfl || profileCode;
  }

  toggleProfileAccordion(): void {
    this.showProfileAccordion = !this.showProfileAccordion;
  }

  removeProfile(profileCode: string): void {
    if (!this.selectedProfiles || !profileCode) {
      return;
    }

    const index = this.selectedProfiles.indexOf(profileCode);
    if (index > -1) {
      this.selectedProfiles.splice(index, 1);

      if (this.selectedProfiles.length === 0) {
        this.showProfileAccordion = false;
      }

      this.updateStepValidation();

      this.sharedService?.showSuccess(
        `Le profil ${this.getProfileName(profileCode)} a été retiré de la sélection.`,
        'Profil supprimé'
      );
    }
  }

  toggleActivationState(): void {
    this.packProfile.boolEtat = this.packProfile.boolEtat === 1 ? 0 : 1;
    this.onActivationChange();
  }

  selectPack(pack: any): void {
    if (this.currentStep !== 1) return;

    this.packProfile.codPackPack = pack.codPackPack;
    this.triggerHighlightEffect();
    this.onPackChange({ value: pack.codPackPack });
  }

  trackByPackCode(index: number, pack: any): string {
    return pack.codPackPack;
  }

  getPackIcon(pack: any): string {
    const structureCode = pack.codNivhPfl;

    switch (structureCode) {
      case '0': // TOUS
        return 'globe';
      case '1': // AGENCE
        return 'building';
      case '2': // DIRECTION REGIONALE
        return 'map';
      case '3': // DIRECTION CENTRALE
        return 'shield';
      case '4': // DIVISION
        return 'sitemap';
      case '5': // DIRECTION
        return 'briefcase';
      case '7': // Box de Change
        return 'money-bill';
      default:
        return 'box';
    }
  }

  getPackType(pack: any): string {
    return getCodeStructureLabel(pack.codNivhPfl || '') || 'Standard';
  }

  onKeydown(event: KeyboardEvent): void {
    if (event.key === 'Escape' && this.isFullscreenModalOpen) {
      this.closeFormModal();
    } else if (event.key === 'Enter' && event.ctrlKey) {
      if (this.isFormValid()) {
        this.savePackProfile();
      }
    }
  }

  onSearchFocus(event: FocusEvent): void {
    const input = event.target as HTMLInputElement;
    if (input) {
      setTimeout(() => input.select(), 10);
    }
  }

  private originalValues: Map<any, boolean> = new Map();

  // ===== INLINE STATUS TOGGLE ON LIST =====

  /**
   * Returns true if this row has a pending change on boolEtat
   */
  hasPendingStatusChange(item: PackProfile): boolean {
    const originalValue = this.originalValues.get(item);
    return originalValue !== undefined && originalValue !== item.boolEtat;
  }

  /**
   * Called when the toggle is changed in the table.
   * We store the original value the first time the user toggles.
   */
  onStatusToggle(item: PackProfile): void {
    if (!this.originalValues.has(item)) {
      // here item.boolEtat has already been toggled by [(ngModel)],
      // so we store the opposite value as the "original"
      this.originalValues.set(item, !item.boolEtat);
    }
  }

  /**
   * Save the new status for a single row using updatePackProfileStatus
   */
  saveStatus(item: PackProfile): void {
    const newValue = item.boolEtat ? 1 : 0;

    this.packProfileService
      .updatePackProfileStatus(item.codPackPack, item.codPflPfl, newValue)
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        (response) => {
          this.sharedService.handleSuccess(
            response,
            "Statut du Pack-Profil mis à jour"
          );
          // Remove pending state for this row
          this.originalValues.delete(item);

          // Reload list to be sure everything is in sync
          this.getPackProfiles();
        },
        (err: any) => {
          this.sharedService.handleError(
            err,
            "Erreur lors de la mise à jour du statut"
          );

          // Revert value on error
          const originalValue = this.originalValues.get(item);
          if (originalValue !== undefined) {
            item.boolEtat = originalValue;
          }
          this.originalValues.delete(item);
        }
      );
  }
}
