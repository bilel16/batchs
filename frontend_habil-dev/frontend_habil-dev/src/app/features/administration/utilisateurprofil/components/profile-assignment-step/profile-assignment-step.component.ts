import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CdkDragDrop } from '@angular/cdk/drag-drop';
import { UnifiedProfile } from '../profile-badge/profile-badge.component';
import { ProfileFilters } from '../profile-filters/profile-filters.component';

@Component({
  selector: 'app-profile-assignment-step',
  templateUrl: './profile-assignment-step.component.html',
  styleUrls: ['./profile-assignment-step.component.scss'],
  standalone: false
})
export class ProfileAssignmentStepComponent {
  @Input() selectedUser: any = null;
  @Input() availableProfiles: UnifiedProfile[] = [];
  @Input() assignedProfiles: UnifiedProfile[] = [];
  @Input() filteredAvailableProfiles: UnifiedProfile[] = [];
  @Input() filteredAssignedProfiles: UnifiedProfile[] = [];
  @Input() availableProfileFilters: ProfileFilters = {
    searchTerm: '',
    selectedLevel: null,
    selectedCategory: null,
    selectedStatus: null
  };
  @Input() assignedProfileFilters: ProfileFilters = {
    searchTerm: '',
    selectedLevel: null,
    selectedCategory: null,
    selectedStatus: null
  };
  @Input() levelFilterOptions: any[] = [];
  @Input() categoryFilterOptions: any[] = [];
  @Input() statusFilterOptions: any[] = [];
  @Input() selectedProfiles: any = { available: new Set(), assigned: new Set() };
  @Input() newlyMovedProfiles: any = { available: new Set(), assigned: new Set() };
  @Input() dragState: "idle" | "dragging" | "success" = "idle";
  @Input() newlyMovedCount: number = 0;
  @Output() profileDrop = new EventEmitter<CdkDragDrop<UnifiedProfile[]>>();
  @Output() profileSelection = new EventEmitter<{ profile: UnifiedProfile, listType: 'available' | 'assigned' }>();
  @Output() profileAction = new EventEmitter<{ action: string, profile: UnifiedProfile, event: Event }>();
  @Output() profileInfo = new EventEmitter<{ profile: UnifiedProfile, event: Event }>();
  @Output() availableFiltersChange = new EventEmitter<ProfileFilters>();
  @Output() assignedFiltersChange = new EventEmitter<ProfileFilters>();
  @Output() clearAvailableFilters = new EventEmitter<void>();
  @Output() clearAssignedFilters = new EventEmitter<void>();
  @Output() resetModifications = new EventEmitter<void>();
  @Output() filterTabChange = new EventEmitter<number>();
  @Output() restoreProfile = new EventEmitter<{ profile: UnifiedProfile, event: Event }>();
  @Output() viewModeChange = new EventEmitter<'profiles' | 'packs'>();

  // Current view mode ('profiles' or 'packs')
  currentViewMode: 'profiles' | 'packs' = 'profiles';
  
  // Tab toggle state
  selectedTabIndex: number = 0;  // Pack-related inputs
  @Input() availablePacks: any[] = [];
  @Input() assignedPacks: any[] = [];
  @Input() cartPacks: any[] = []; // Cart packs managed by parent
  @Output() packAssign = new EventEmitter<any>();
  @Output() packUnassign = new EventEmitter<any>();
  @Output() packInfo = new EventEmitter<any>();
  @Output() packDrop = new EventEmitter<any>();
  @Output() batchPackAssign = new EventEmitter<any[]>();
  @Output() cartPacksChange = new EventEmitter<any[]>(); // Emit cart changes to parent

  // Current filter mode ('available' or 'assigned')
  currentFilterMode: 'available' | 'assigned' = 'available';
  
  // Get current filters based on selected mode
  get currentProfileFilters(): ProfileFilters {
    return this.currentFilterMode === 'available' ? this.availableProfileFilters : this.assignedProfileFilters;
  }

  onDrop(event: CdkDragDrop<UnifiedProfile[]>): void {
    this.profileDrop.emit(event);
  }

  onProfileSelection(profile: UnifiedProfile, listType: 'available' | 'assigned'): void {
    this.profileSelection.emit({ profile, listType });
  }

  onProfileAction(action: string, profile: UnifiedProfile, event: Event): void {
    this.profileAction.emit({ action, profile, event });
  }
  onProfileInfo(profile: UnifiedProfile, event: Event): void {
    this.profileInfo.emit({ profile, event });
  }

  onRestoreProfile(profile: UnifiedProfile, event: Event): void {
    this.restoreProfile.emit({ profile, event });
  }

  onAvailableFiltersChange(filters: ProfileFilters): void {
    this.availableFiltersChange.emit(filters);
  }

  onAssignedFiltersChange(filters: ProfileFilters): void {
    this.assignedFiltersChange.emit(filters);
  }

  onClearAvailableFilters(): void {
    this.clearAvailableFilters.emit();
  }
  onClearAssignedFilters(): void {
    this.clearAssignedFilters.emit();
  }
  onFilterTabChange(tabIndex: number): void {
    this.currentFilterMode = tabIndex === 0 ? 'available' : 'assigned';
    this.filterTabChange.emit(tabIndex);
  }

  onFiltersChange(filters: ProfileFilters): void {
    if (this.currentFilterMode === 'available') {
      this.onAvailableFiltersChange(filters);
    } else {
      this.onAssignedFiltersChange(filters);
    }
  }

  onClearFilters(): void {
    if (this.currentFilterMode === 'available') {
      this.onClearAvailableFilters();
    } else {
      this.onClearAssignedFilters();
    }
  }

  onResetModifications(): void {
    this.resetModifications.emit();
  }

  // Tab toggle handler - receives the tab label string
  onTabChanged(tabValue: string): void {
    // Determine view mode based on tab label
    const isProfiles = tabValue === 'Profils individuels';
    this.selectedTabIndex = isProfiles ? 0 : 1;
    this.currentViewMode = isProfiles ? 'profiles' : 'packs';
    this.viewModeChange.emit(this.currentViewMode);
  }

  // Pack-related methods
  onViewModeChange(mode: 'profiles' | 'packs'): void {
    this.currentViewMode = mode;
    this.selectedTabIndex = mode === 'profiles' ? 0 : 1;
    this.viewModeChange.emit(mode);
  }

  onPackAssign(pack: any): void {
    this.packAssign.emit(pack);
  }

  onPackUnassign(pack: any): void {
    this.packUnassign.emit(pack);
  }

  onPackInfo(pack: any): void {
    this.packInfo.emit(pack);
  }
  onPackDrop(event: any): void {
    this.packDrop.emit(event);
  }

  /**
   * Handle cart changes from pack selector
   */
  onCartChange(packs: any[]): void {
    this.cartPacksChange.emit(packs);
  }

  /**
   * Handle batch pack assignment from shopping cart
   */
  onBatchPackAssign(packs: any[]): void {
    console.log('🛒 Batch assigning packs from cart:', packs.length);
    // Emit the entire array for batch assignment
    this.batchPackAssign.emit(packs);
  }

  // Helper methods
  isProfileSelected(profile: UnifiedProfile, listType: 'available' | 'assigned'): boolean {
    return this.selectedProfiles[listType].has(profile.codPflPfl);
  }

  isProfileNewlyMoved(profile: UnifiedProfile, listType: 'available' | 'assigned'): boolean {
    // This logic should be implemented based on your business logic
    return this.newlyMovedProfiles[listType].has(profile.codPflPfl);

  }

  isDraggingMultiple(profile: UnifiedProfile, listType: 'available' | 'assigned'): boolean {
    return this.selectedProfiles[listType].size > 1 && this.isProfileSelected(profile, listType);
  }

  trackByProfileCode(index: number, profile: UnifiedProfile): string {
    return profile.codPflPfl;
  }
}
