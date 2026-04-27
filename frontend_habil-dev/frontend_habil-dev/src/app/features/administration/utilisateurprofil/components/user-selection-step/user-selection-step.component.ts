import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-user-selection-step',
  templateUrl: './user-selection-step.component.html',
  styleUrls: ['./user-selection-step.component.scss'],
  standalone: false
})
export class UserSelectionStepComponent {
  @Input() managedUsers: any[] = [];
  @Input() selectedUser: any = null;
  @Input() isLoadingManagedUsers: boolean = false;
  @Input() viewMode: "grid" | "list" = "grid";
  @Input() filterValue: string = "";

  // Admin pagination inputs
  @Input() isAdminUser: boolean = false;
  @Input() adminCurrentPage: number = 0;
  @Input() adminTotalPages: number = 0;
  @Input() adminTotalElements: number = 0;
  @Input() adminPageSize: number = 20;
  @Input() adminIsFirstPage: boolean = true;
  @Input() adminIsLastPage: boolean = false;
  // Admin filter inputs
  @Input() adminSearchTerm: string = '';
  @Input() adminStatusFilter: boolean | null = null;
  @Input() adminStructureFilter: number[] = [];  // Changed to array for multi-select
  @Input() adminStructureTypeFilter: number | null = null;
  @Input() adminSortBy: string = 'mat';
  @Input() adminSortDirection: 'ASC' | 'DESC' = 'ASC';
  @Input() adminStatusOptions: { label: string; value: boolean | null }[] = [];
  @Input() adminSortOptions: { label: string; value: string }[] = [];
  @Input() structureOptions: { label: string; value: number | null }[] = [];
  @Input() structureTypeOptions: { label: string; value: number | null }[] = [];

  @Output() userSelect = new EventEmitter<any>();
  @Output() viewModeChange = new EventEmitter<"grid" | "list">();
  @Output() refreshUsers = new EventEmitter<void>();
  @Output() filterChange = new EventEmitter<string>();

  // Admin pagination outputs
  @Output() nextPage = new EventEmitter<void>();
  @Output() previousPage = new EventEmitter<void>();
  @Output() goToPage = new EventEmitter<number>();
  @Output() pageSizeChange = new EventEmitter<number>();
  // Admin filter outputs
  @Output() adminSearchChange = new EventEmitter<string>();
  @Output() adminStatusChange = new EventEmitter<boolean | null>();
  @Output() adminStructureChange = new EventEmitter<number[]>();  // Changed to array
  @Output() adminStructureTypeChange = new EventEmitter<number | null>();
  @Output() adminSortChange = new EventEmitter<{ sortBy: string; sortDirection: 'ASC' | 'DESC' }>();
  @Output() adminFiltersReset = new EventEmitter<void>();

  // Page size options for dropdown
  pageSizeOptions = [10, 20, 50, 100];

  // Local search term for debouncing
  localSearchTerm: string = '';

  onUserSelect(user: any): void {
    this.userSelect.emit(user);
  }

  onViewModeChange(mode: "grid" | "list"): void {
    this.viewMode = mode;
    this.viewModeChange.emit(mode);
  }

  onRefreshUsers(): void {
    this.refreshUsers.emit();
  }

  // Pagination methods
  onNextPage(): void {
    this.nextPage.emit();
  }

  onPreviousPage(): void {
    this.previousPage.emit();
  }

  onGoToPage(page: number): void {
    this.goToPage.emit(page);
  }

  onPageSizeChange(size: number): void {
    this.pageSizeChange.emit(size);
  }

  // ============================================
  // ADMIN FILTER METHODS
  // ============================================

  /**
   * Handle admin search input change
   */
  onAdminSearchChange(searchTerm: string): void {
    this.localSearchTerm = searchTerm;
    this.adminSearchChange.emit(searchTerm);
  }

  /**
   * Handle admin status filter change
   */
  onAdminStatusChange(status: boolean | null): void {
    this.adminStatusChange.emit(status);
  }

  /**
   * Cycle through status filter states: Active -> All -> Inactive -> Active
   * Called when clicking on the toggle track
   */
  cycleStatusFilter(): void {
    let newStatus: boolean | null;
    if (this.adminStatusFilter === true) {
      newStatus = null; // Active -> All
    } else if (this.adminStatusFilter === null) {
      newStatus = false; // All -> Inactive
    } else {
      newStatus = true; // Inactive -> Active
    }
    this.adminStatusChange.emit(newStatus);
  }
  /**
   * Handle admin structure filter change (multi-select)
   */
  onAdminStructureChange(structureIds: number[]): void {
    this.adminStructureChange.emit(structureIds || []);
  }

  /**
   * Handle admin structure type filter change
   */
  onAdminStructureTypeChange(structureTypeId: number | null): void {
    this.adminStructureTypeChange.emit(structureTypeId);
  }

  /**
   * Handle admin sort change
   */
  onAdminSortChange(sortBy: string): void {
    // Toggle direction if same field, else default to ASC
    let newDirection: 'ASC' | 'DESC' = 'ASC';
    if (this.adminSortBy === sortBy) {
      newDirection = this.adminSortDirection === 'ASC' ? 'DESC' : 'ASC';
    }
    this.adminSortChange.emit({ sortBy, sortDirection: newDirection });
  }

  /**
   * Handle sort direction toggle
   */
  toggleSortDirection(): void {
    const newDirection = this.adminSortDirection === 'ASC' ? 'DESC' : 'ASC';
    this.adminSortChange.emit({ sortBy: this.adminSortBy, sortDirection: newDirection });
  }

  /**
   * Reset all admin filters
   */
  resetAdminFilters(): void {
    this.localSearchTerm = '';
    this.adminFiltersReset.emit();
  }
  /**
   * Check if any admin filters are active
   */
  hasActiveFilters(): boolean {
    return !!(
      this.adminSearchTerm ||
      this.adminStatusFilter !== null ||
      this.adminStructureFilter.length > 0 ||
      this.adminStructureTypeFilter !== null
    );
  }

  // Get page numbers for pagination display
  getPageNumbers(): number[] {
    const pages: number[] = [];
    const maxPagesToShow = 5;
    let startPage = Math.max(0, this.adminCurrentPage - Math.floor(maxPagesToShow / 2));
    let endPage = Math.min(this.adminTotalPages - 1, startPage + maxPagesToShow - 1);
    
    // Adjust start if we're near the end
    if (endPage - startPage < maxPagesToShow - 1) {
      startPage = Math.max(0, endPage - maxPagesToShow + 1);
    }
    
    for (let i = startPage; i <= endPage; i++) {
      pages.push(i);
    }
    return pages;
  }

  // Get filtered users based on search (local filtering for non-admin users)
  getFilteredUsers(): any[] {
    // For admin users, filtering is done server-side
    if (this.isAdminUser) {
      return this.managedUsers;
    }
    
    // For non-admin users, use local filtering
    if (!this.filterValue) {
      return this.managedUsers;
    }
    const searchTerm = this.filterValue.toLowerCase();
    return this.managedUsers.filter(
      (user) =>
        (user.mat || '').toLowerCase().includes(searchTerm) ||
        (user.nom_prenom || '').toLowerCase().includes(searchTerm)
    );
  }

  // Get user status color
  getUserStatusColor(user: any): string {
    return user.isActive ? "success" : "danger";
  }
  // Get user status icon
  getUserStatusIcon(user: any): string {
    return user.isActive ? "pi-check-circle" : "pi-times-circle";
  }

  // Get structure badge color based on type
  getStructureBadgeColor(structureId: number): string {
    // You can customize this based on your structure types
    if (structureId >= 800 && structureId < 900) return "primary";
    if (structureId >= 900 && structureId < 1000) return "success";
    return "secondary";
  }

  // Add method to get structure name (if you have structure data)
  getStructureName(structureId: number): string {
    // You can implement structure name lookup if you have that data
    return `Structure ${structureId}`;
  }
  /**
   * Get the label for the selected structure filters (multi-select)
   * Returns comma-separated list of structure names
   */
  getStructureFilterLabel(): string {
    if (!this.adminStructureFilter || this.adminStructureFilter.length === 0) return '';
    
    const labels = this.adminStructureFilter
      .map(id => {
        const option = this.structureOptions.find(opt => opt.value === id);
        return option ? option.label : `Structure ${id}`;
      })
      .join(', ');
    
    return labels;
  }

  /**
   * Get count of selected structures for badge display
   */
  getStructureFilterCount(): number {
    return this.adminStructureFilter?.length || 0;
  }

  /**
   * Get the label for the selected structure type filter
   */
  getStructureTypeFilterLabel(): string {
    if (this.adminStructureTypeFilter === null) return '';
    const option = this.structureTypeOptions.find(opt => opt.value === this.adminStructureTypeFilter);
    return option ? option.label : `Type ${this.adminStructureTypeFilter}`;
  }
}
