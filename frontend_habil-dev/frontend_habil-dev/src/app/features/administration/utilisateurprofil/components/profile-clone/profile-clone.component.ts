/**
 * @fileoverview ProfileCloneComponent - Clone Profiles Between Users
 * 
 * Provides UI for cloning profile assignments from a source user to target user(s).
 * Features:
 * - Source user selection with profile preview
 * - Target user selection (single or multiple)
 * - Profile selection (clone all or specific profiles)
 * - Conflict detection and resolution options
 * - Batch cloning with progress feedback
 * 
 * @author BNA HABIL Development Team
 * @version 1.0.0
 * @since 2025-12-23
 */

import { Component, Input, Output, EventEmitter, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { ConfirmationService } from 'primeng/api';

export interface CloneableProfile {
  codPflPfl: string;
  libpflpfl: string;
  displayName?: string;
  dateDebut?: Date | string;
  dateFin?: Date | string;
  boolEtatUtpr?: number;
  selected?: boolean;
  /** Custom start date for this profile (used when dateMode = 'individual') */
  customDateDebut?: Date;
  /** Custom end date for this profile (used when dateMode = 'individual') */
  customDateFin?: Date;
}

export interface CloneTargetUser {
  mat: string;
  nom_prenom: string;
  nomUtilUtl?: string;
  prenUtilUtl?: string;
  structureId?: number;
  selected?: boolean;
  hasConflicts?: boolean;
  conflictingProfiles?: string[];
}

export interface CloneOptions {
  keepExistingDates: boolean;
  setNewStartDate: boolean;
  /** 'keep' = keep source dates, 'same' = same date for all profiles, 'individual' = different date per profile */
  dateMode: 'keep' | 'same' | 'individual';
  newStartDate?: Date;
  newEndDate?: Date;
  copyInactiveProfiles: boolean;
}

export interface CloneResult {
  targetUser: string;
  success: boolean;
  clonedCount: number;
  skippedCount: number;
  errorMessage?: string;
  /** Array of successfully cloned profile codes/names */
  successfulProfiles?: string[];
  /** Array of failed profiles with error messages */
  failedProfiles?: Array<{ profileCode: string; error: string }>;
  /** UI state: whether details are expanded */
  expanded?: boolean;
}

@Component({
  selector: 'app-profile-clone',
  templateUrl: './profile-clone.component.html',
  styleUrls: ['./profile-clone.component.scss'],
  standalone: false
})
export class ProfileCloneComponent implements OnInit, OnChanges {
  // Input data
  @Input() availableUsers: any[] = [];
  @Input() currentUser: any = null; // Currently selected user (source by default)
  @Input() sourceUserProfiles: CloneableProfile[] = [];
  @Input() loading: boolean = false;
  @Input() visible: boolean = false;
  @Input() appCode: string = '';
  
  // Cloning state inputs from parent
  @Input() cloningInProgress: boolean = false;
  @Input() externalCloneProgress: number = 0;
  @Input() externalCloneResults: CloneResult[] = [];

  // Output events
  @Output() visibleChange = new EventEmitter<boolean>();  @Output() clone = new EventEmitter<{
    sourceUser: any;
    targetUsers: CloneTargetUser[];
    selectedProfiles: CloneableProfile[];
    options: CloneOptions;
  }>();
  @Output() sourceUserChange = new EventEmitter<any>();
  @Output() loadSourceProfiles = new EventEmitter<any>();
  
  // Component state
  currentStep: number = 1;
  totalSteps: number = 3; // Reduced from 4 to 3 (removed source selection step)

  // Source user selection
  sourceUser: any = null;
  sourceSearchTerm: string = '';
  filteredSourceUsers: any[] = [];

  // Profiles to clone
  profilesToClone: CloneableProfile[] = [];
  selectAllProfiles: boolean = true;
  profileSearchTerm: string = '';
  filteredProfiles: CloneableProfile[] = [];

  // Target users selection
  targetUsers: CloneTargetUser[] = [];
  targetSearchTerm: string = '';
  filteredTargetUsers: any[] = [];
  selectAllTargets: boolean = false;
    // Target users pagination
  targetUsersCurrentPage: number = 0;
  targetUsersPageSize: number = 10; // Show 10 users per page
  paginatedTargetUsers: any[] = [];
  totalTargetUsers: number = 0;
  rowsPerPageOptions = [
    { label: '10', value: 10 },
    { label: '20', value: 20 },
    { label: '50', value: 50 },
    { label: '100', value: 100 }
  ];
    // Clone options
  cloneOptions: CloneOptions = {
    keepExistingDates: true,
    setNewStartDate: false,
    dateMode: 'keep',
    newStartDate: new Date(),
    newEndDate: undefined,
    copyInactiveProfiles: false
  };

  // Cloning state
  isCloning: boolean = false;
  cloneProgress: number = 0;
  cloneResults: CloneResult[] = [];

  constructor(private confirmationService: ConfirmationService) {}
  ngOnInit(): void {
    this.initializeComponent();
  }
  ngOnChanges(changes: SimpleChanges): void {
    // Handle currentUser changes - always set as source user
    if (changes['currentUser']) {
      if (this.currentUser) {
        this.sourceUser = this.currentUser;
        this.sourceUserChange.emit(this.currentUser);
      }
    }
    
    // Handle sourceUserProfiles changes
    if (changes['sourceUserProfiles'] && this.sourceUserProfiles) {
      this.initializeProfiles();
    }
    
    // Handle availableUsers changes
    if (changes['availableUsers']) {
      this.filterTargetUsers();
    }
    
    // Handle dialog visibility changes
    if (changes['visible']) {
      if (this.visible) {
        // When dialog opens, ensure source user is set from currentUser
        if (this.currentUser) {
          this.sourceUser = this.currentUser;
          this.sourceUserChange.emit(this.currentUser);
          
          // Initialize profiles if they exist
          if (this.sourceUserProfiles && this.sourceUserProfiles.length > 0) {
            this.initializeProfiles();
          }
        } else {
          console.warn('Profile clone dialog opened without currentUser');
        }
      }
    }
    
    // Sync external cloning state with internal state
    if (changes['cloningInProgress']) {
      this.isCloning = this.cloningInProgress;
    }
    if (changes['externalCloneProgress']) {
      this.cloneProgress = this.externalCloneProgress;
    }
    if (changes['externalCloneResults']) {
      this.cloneResults = this.externalCloneResults || [];
    }
  }

  /**
   * Initialize component with default values
   */
  private initializeComponent(): void {
    if (this.currentUser) {
      this.sourceUser = this.currentUser;
    }
    this.filterSourceUsers();
    this.initializeProfiles();
  }
  /**
   * Initialize profiles for cloning - filter out inactive profiles
   */
  private initializeProfiles(): void {
    // Filter out inactive profiles (boolEtatUtpr === 0)
    const activeProfiles = (this.sourceUserProfiles || []).filter(
      profile => profile.boolEtatUtpr !== 0
    );
    
    this.profilesToClone = activeProfiles.map(profile => ({
      ...profile,
      selected: true,
      displayName: profile.displayName || profile.libpflpfl || profile.codPflPfl
    }));
    this.filteredProfiles = [...this.profilesToClone];
  }

  /**
   * Reset wizard to initial state
   */
  resetWizard(): void {
    this.currentStep = 1;
    this.cloneResults = [];
    this.cloneProgress = 0;
    this.isCloning = false;
    
    if (this.currentUser) {
      this.sourceUser = this.currentUser;
    }
    
    this.targetUsers = [];
    this.selectAllTargets = false;
    this.initializeProfiles();
    this.filterTargetUsers();
  }

  // ============================================================================
  // STEP NAVIGATION
  // ============================================================================
  /**
   * Navigate to next step
   */
  nextStep(): void {
    if (this.canProceedToNextStep()) {
      this.currentStep++;
      
      // Handle step-specific initialization
      if (this.currentStep === 2) {
        // Step 2: Target selection - filter target users
        this.filterTargetUsers();
      }
    }
  }

  /**
   * Navigate to previous step
   */
  previousStep(): void {
    if (this.currentStep > 1) {
      this.currentStep--;
    }
  }

  /**
   * Go to specific step
   */
  goToStep(step: number): void {
    if (step >= 1 && step <= this.totalSteps && step <= this.currentStep) {
      this.currentStep = step;
    }
  }
  /**
   * Check if can proceed to next step
   */
  canProceedToNextStep(): boolean {
    switch (this.currentStep) {
      case 1:
        // Step 1: Profile selection - must have selected profiles
        return this.getSelectedProfilesCount() > 0;
      case 2:
        // Step 2: Target selection - must have selected targets
        return this.getSelectedTargetsCount() > 0;
      case 3:
        // Step 3: Confirmation - can always proceed
        return true;
      default:
        return false;
    }
  }
  /**
   * Get step label
   */
  getStepLabel(step: number): string {
    const labels = [
      'Profils',    // Step 1: Profile selection (removed source selection)
      'Cibles',     // Step 2: Target selection
      'Confirmer'   // Step 3: Confirmation
    ];
    return labels[step - 1] || '';
  }

  /**
   * Check if step is completed
   */
  isStepCompleted(step: number): boolean {
    return step < this.currentStep;
  }

  /**
   * Check if step is active
   */
  isStepActive(step: number): boolean {
    return step === this.currentStep;
  }

  // ============================================================================
  // SOURCE USER SELECTION (STEP 1)
  // ============================================================================

  /**
   * Filter source users based on search
   */
  filterSourceUsers(): void {
    if (!this.sourceSearchTerm) {
      this.filteredSourceUsers = [...this.availableUsers];
    } else {
      const term = this.sourceSearchTerm.toLowerCase();
      this.filteredSourceUsers = this.availableUsers.filter(user =>
        (user.mat || '').toLowerCase().includes(term) ||
        (user.nom_prenom || '').toLowerCase().includes(term) ||
        (user.nomUtilUtl || '').toLowerCase().includes(term) ||
        (user.prenUtilUtl || '').toLowerCase().includes(term)
      );
    }
  }

  /**
   * Select source user
   */
  selectSourceUser(user: any): void {
    this.sourceUser = user;
    this.sourceUserChange.emit(user);
    this.loadSourceProfiles.emit(user);
  }

  /**
   * Check if user is selected as source
   */
  isSourceSelected(user: any): boolean {
    return this.sourceUser?.mat === user.mat;
  }

  // ============================================================================
  // PROFILE SELECTION (STEP 2)
  // ============================================================================

  /**
   * Filter profiles based on search
   */
  filterProfiles(): void {
    if (!this.profileSearchTerm) {
      this.filteredProfiles = [...this.profilesToClone];
    } else {
      const term = this.profileSearchTerm.toLowerCase();
      this.filteredProfiles = this.profilesToClone.filter(profile =>
        (profile.codPflPfl || '').toLowerCase().includes(term) ||
        (profile.libpflpfl || '').toLowerCase().includes(term) ||
        (profile.displayName || '').toLowerCase().includes(term)
      );
    }
  }

  /**
   * Toggle profile selection
   */
  toggleProfileSelection(profile: CloneableProfile): void {
    profile.selected = !profile.selected;
    this.updateSelectAllProfiles();
  }

  /**
   * Toggle all profiles selection
   */
  toggleAllProfiles(): void {
    const newState = this.selectAllProfiles;
    this.profilesToClone.forEach(profile => {
      // Only toggle active profiles if copyInactiveProfiles is false
      if (this.cloneOptions.copyInactiveProfiles || profile.boolEtatUtpr !== 0) {
        profile.selected = newState;
      }
    });
  }

  /**
   * Update select all checkbox state
   */
  private updateSelectAllProfiles(): void {
    const activeProfiles = this.profilesToClone.filter(p => 
      this.cloneOptions.copyInactiveProfiles || p.boolEtatUtpr !== 0
    );
    this.selectAllProfiles = activeProfiles.every(p => p.selected);
  }

  /**
   * Get selected profiles count
   */
  getSelectedProfilesCount(): number {
    return this.profilesToClone.filter(p => p.selected).length;
  }

  /**
   * Get selected profiles
   */
  getSelectedProfiles(): CloneableProfile[] {
    return this.profilesToClone.filter(p => p.selected);
  }

  /**
   * Check if profile is inactive
   */
  isProfileInactive(profile: CloneableProfile): boolean {
    return profile.boolEtatUtpr === 0;
  }

  // ============================================================================
  // TARGET USER SELECTION (STEP 3)
  // ============================================================================
  /**
   * Filter target users (exclude source user)
   */
  filterTargetUsers(): void {
    let users = this.availableUsers.filter(user => 
      user.mat !== this.sourceUser?.mat
    );

    if (this.targetSearchTerm) {
      const term = this.targetSearchTerm.toLowerCase();
      users = users.filter(user =>
        (user.mat || '').toLowerCase().includes(term) ||
        (user.nom_prenom || '').toLowerCase().includes(term) ||
        (user.nomUtilUtl || '').toLowerCase().includes(term) ||
        (user.prenUtilUtl || '').toLowerCase().includes(term)
      );
    }

    this.filteredTargetUsers = users;
    this.totalTargetUsers = users.length;
    
    // Reset to first page when filtering
    this.targetUsersCurrentPage = 0;
    this.updatePaginatedTargetUsers();
  }

  /**
   * Update paginated target users based on current page
   */
  private updatePaginatedTargetUsers(): void {
    const startIndex = this.targetUsersCurrentPage * this.targetUsersPageSize;
    const endIndex = startIndex + this.targetUsersPageSize;
    this.paginatedTargetUsers = this.filteredTargetUsers.slice(startIndex, endIndex);
  }
  /**
   * Handle page change event from paginator
   */
  onTargetUsersPageChange(event: any): void {
    this.targetUsersCurrentPage = event.page;
    this.targetUsersPageSize = event.rows;
    this.updatePaginatedTargetUsers();
  }

  /**
   * Handle rows per page change from separate p-select
   */
  onRowsPerPageChange(event: any): void {
    this.targetUsersPageSize = event.value;
    this.targetUsersCurrentPage = 0;
    this.updatePaginatedTargetUsers();
  }

  /**
   * Toggle target user selection
   */
  toggleTargetSelection(user: any): void {
    const index = this.targetUsers.findIndex(t => t.mat === user.mat);
    if (index >= 0) {
      this.targetUsers.splice(index, 1);
    } else {
      this.targetUsers.push({
        mat: user.mat,
        nom_prenom: user.nom_prenom || `${user.prenUtilUtl || ''} ${user.nomUtilUtl || ''}`.trim(),
        nomUtilUtl: user.nomUtilUtl,
        prenUtilUtl: user.prenUtilUtl,
        structureId: user.structureId,
        selected: true
      });
    }
    this.updateSelectAllTargets();
  }

  /**
   * Toggle all target users selection
   */
  toggleAllTargets(): void {
    if (this.selectAllTargets) {
      // Select all visible users
      this.targetUsers = this.filteredTargetUsers.map(user => ({
        mat: user.mat,
        nom_prenom: user.nom_prenom || `${user.prenUtilUtl || ''} ${user.nomUtilUtl || ''}`.trim(),
        nomUtilUtl: user.nomUtilUtl,
        prenUtilUtl: user.prenUtilUtl,
        structureId: user.structureId,
        selected: true
      }));
    } else {
      this.targetUsers = [];
    }
  }

  /**
   * Update select all targets checkbox state
   */
  private updateSelectAllTargets(): void {
    this.selectAllTargets = this.filteredTargetUsers.length > 0 &&
      this.filteredTargetUsers.every(user => this.isTargetSelected(user));
  }

  /**
   * Check if user is selected as target
   */
  isTargetSelected(user: any): boolean {
    return this.targetUsers.some(t => t.mat === user.mat);
  }

  /**
   * Get selected targets count
   */
  getSelectedTargetsCount(): number {
    return this.targetUsers.length;
  }

  /**
   * Remove target from selection
   */
  removeTarget(target: CloneTargetUser): void {
    this.targetUsers = this.targetUsers.filter(t => t.mat !== target.mat);
    this.updateSelectAllTargets();
  }
  // ============================================================================
  // CLONE OPTIONS (STEP 3)
  // ============================================================================
  /**
   * Handle unified date mode change from radio cards.
   * Maps the new dateMode to existing business logic flags.
   * - 'keep'       → keepExistingDates=true,  setNewStartDate=false
   * - 'same'       → keepExistingDates=false, setNewStartDate=true
   * - 'individual' → keepExistingDates=false, setNewStartDate=true  (+ individual dates init)
   */
  onDateModeChange(): void {
    switch (this.cloneOptions.dateMode) {
      case 'keep':
        this.cloneOptions.keepExistingDates = true;
        this.cloneOptions.setNewStartDate = false;
        break;
      case 'same':
        this.cloneOptions.keepExistingDates = false;
        this.cloneOptions.setNewStartDate = true;
        break;
      case 'individual':
        this.cloneOptions.keepExistingDates = false;
        this.cloneOptions.setNewStartDate = true;
        this.initializeIndividualDates();
        break;
    }
  }

  /**
   * Initialize individual dates for selected profiles
   */
  private initializeIndividualDates(): void {
    const today = new Date();
    this.profilesToClone.forEach(profile => {
      if (profile.selected && !profile.customDateDebut) {
        profile.customDateDebut = today;
      }
    });
  }

  /**
   * Apply the same date to all selected profiles (for individual mode)
   */
  applyDateToAllProfiles(): void {
    const startDate = this.cloneOptions.newStartDate || new Date();
    const endDate = this.cloneOptions.newEndDate;
    
    this.profilesToClone.forEach(profile => {
      if (profile.selected) {
        profile.customDateDebut = startDate;
        profile.customDateFin = endDate;
      }
    });
  }

  // ============================================================================
  // CLONE EXECUTION
  // ============================================================================

  /**
   * Execute the clone operation
   */
  executeClone(): void {
    const selectedProfiles = this.getSelectedProfiles();
    
    if (selectedProfiles.length === 0 || this.targetUsers.length === 0) {
      return;
    }

    // Confirmation dialog
    const profileCount = selectedProfiles.length;
    const targetCount = this.targetUsers.length;
    
    this.confirmationService.confirm({
      message: `Voulez-vous cloner ${profileCount} profil(s) vers ${targetCount} utilisateur(s)?`,
      header: 'Confirmation de clonage',
      icon: 'pi pi-copy',
      acceptLabel: 'Oui, cloner',
      rejectLabel: 'Annuler',
      acceptButtonStyleClass: 'p-button-success',
      rejectButtonStyleClass: 'p-button-secondary',
      accept: () => {
        this.performClone();
      }
    });
  }

  /**
   * Perform the actual clone operation
   */
  private performClone(): void {
    this.isCloning = true;
    this.cloneProgress = 0;
    this.cloneResults = [];

    this.clone.emit({
      sourceUser: this.sourceUser,
      targetUsers: this.targetUsers,
      selectedProfiles: this.getSelectedProfiles(),
      options: this.cloneOptions
    });
  }

  /**
   * Update clone progress (called from parent)
   */
  updateProgress(progress: number, results?: CloneResult[]): void {
    this.cloneProgress = progress;
    if (results) {
      this.cloneResults = results;
    }
    if (progress >= 100) {
      this.isCloning = false;
    }
  }

  /**
   * Get clone summary
   */
  getCloneSummary(): { success: number; failed: number; total: number } {
    const success = this.cloneResults.filter(r => r.success).length;
    const failed = this.cloneResults.filter(r => !r.success).length;
    return { success, failed, total: this.cloneResults.length };
  }

  // ============================================================================
  // DIALOG MANAGEMENT
  // ============================================================================

  /**
   * Close dialog
   */
  closeDialog(): void {
    if (this.isCloning) {
      this.confirmationService.confirm({
        message: 'Le clonage est en cours. Voulez-vous vraiment annuler?',
        header: 'Annulation',
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: 'Oui, annuler',
        rejectLabel: 'Continuer',
        acceptButtonStyleClass: 'p-button-danger',
        accept: () => {
          this.forceClose();
        }
      });
    } else {
      this.forceClose();
    }
  }

  /**
   * Force close dialog
   */
  private forceClose(): void {
    this.visible = false;
    this.visibleChange.emit(false);
    this.resetWizard();
  }
  /**
   * Get user display name
   */
  getUserDisplayName(user: any): string {
    if (!user) {
      console.warn('getUserDisplayName called with null/undefined user');
      return '';
    }
    if (user.nom_prenom) {
      return user.nom_prenom;
    }
    return `${user.prenUtilUtl || ''} ${user.nomUtilUtl || ''}`.trim() || user.mat || 'Utilisateur inconnu';
  }

  /**
   * Format date for display
   */
  formatDate(date: Date | string | undefined): string {
    if (!date) return '-';
    const d = typeof date === 'string' ? new Date(date) : date;
    return d.toLocaleDateString('fr-FR');
  }

  /**
   * Track by function for profiles
   */
  trackByProfileCode(index: number, profile: CloneableProfile): string {
    return profile.codPflPfl;
  }

  /**
   * Track by function for users
   */
  trackByUserMat(index: number, user: any): string {
    return user.mat;
  }
  /**
   * Toggle the expanded state of a clone result to show/hide details
   */
  toggleResultDetails(result: CloneResult): void {
    // Only toggle if there are details to show
    if ((result.successfulProfiles?.length ?? 0) > 0 || (result.failedProfiles?.length ?? 0) > 0) {
      result.expanded = !result.expanded;
    }
  }

  // ============================================================================
  // CLONE RESULTS UI HELPERS
  // ============================================================================

  /** Track which detail tab is active per result (keyed by targetUser) */
  resultDetailTab: Record<string, 'success' | 'failed' | 'skipped'> = {};

  /** Per-user detail popup state */
  resultPopupVisible: boolean = false;
  resultPopupData: CloneResult | null = null;
  /** Separate tab tracker for the popup (avoids interfering with inline tabs) */
  resultPopupTab: 'success' | 'failed' | 'skipped' = 'success';

  /**
   * Open the enlarged result popup for a specific user
   */
  openResultPopup(result: CloneResult, event: MouseEvent): void {
    event.stopPropagation(); // prevent accordion toggle
    this.resultPopupData = result;
    // Default to the most relevant tab
    if (result.failedProfiles?.length) {
      this.resultPopupTab = 'failed';
    } else if (result.successfulProfiles?.length) {
      this.resultPopupTab = 'success';
    } else {
      this.resultPopupTab = 'skipped';
    }
    this.resultPopupVisible = true;
  }

  /**
   * Close the result popup
   */
  closeResultPopup(): void {
    this.resultPopupVisible = false;
    this.resultPopupData = null;
  }

  /**
   * Get the active detail tab for a result, defaulting to the most relevant one
   */
  getResultTab(result: CloneResult): 'success' | 'failed' | 'skipped' {
    if (!this.resultDetailTab[result.targetUser]) {
      // Default to failed if there are failures, otherwise success
      if (result.failedProfiles?.length) {
        this.resultDetailTab[result.targetUser] = 'failed';
      } else if (result.successfulProfiles?.length) {
        this.resultDetailTab[result.targetUser] = 'success';
      } else {
        this.resultDetailTab[result.targetUser] = 'skipped';
      }
    }
    return this.resultDetailTab[result.targetUser];
  }

  /**
   * Set the active detail tab for a result
   */
  setResultTab(result: CloneResult, tab: 'success' | 'failed' | 'skipped'): void {
    this.resultDetailTab[result.targetUser] = tab;
  }

  /**
   * Get overall totals across all results
   */
  getOverallTotals(): { totalProfiles: number; totalSuccess: number; totalFailed: number; totalSkipped: number } {
    let totalSuccess = 0;
    let totalFailed = 0;
    let totalSkipped = 0;

    this.cloneResults.forEach(r => {
      totalSuccess += r.clonedCount || 0;
      totalFailed += r.failedProfiles?.length || 0;
      totalSkipped += r.skippedCount || 0;
    });

    return {
      totalProfiles: totalSuccess + totalFailed + totalSkipped,
      totalSuccess,
      totalFailed,
      totalSkipped
    };
  }

  /**
   * Get success rate as a percentage
   */
  getSuccessRate(): number {
    const totals = this.getOverallTotals();
    if (totals.totalProfiles === 0) return 0;
    return Math.round((totals.totalSuccess / totals.totalProfiles) * 100);
  }

  /**
   * Expand or collapse all results
   */
  toggleAllResults(expand: boolean): void {
    this.cloneResults.forEach(r => {
      if ((r.successfulProfiles?.length ?? 0) > 0 || (r.failedProfiles?.length ?? 0) > 0) {
        r.expanded = expand;
      }
    });
  }

  /**
   * Track by for clone results
   */
  trackByTargetUser(index: number, result: CloneResult): string {
    return result.targetUser;
  }
}
