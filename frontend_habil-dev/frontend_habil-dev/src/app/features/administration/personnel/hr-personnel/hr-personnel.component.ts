/**
 * @fileoverview HR Personnel Component
 * 
 * Component for managing HR Personnel data with full filtering, pagination, and search.
 * Based on the user-selection-step component design pattern.
 * 
 * Features:
 * - Server-side pagination
 * - Debounced search (300ms)
 * - Multiple filter types (general search, individual fields)
 * - Sortable columns
 * - Loading states
 * - Empty state handling
 * 
 * @author BNA HABIL Development Team
 * @version 1.0.0
 * @since 2026-01-29
 */

import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, takeUntil } from 'rxjs/operators';

import { HrPersonnelService } from '../../../../core/services/backend/hr-personnel.service';
import { NotificationService } from '../../../../core/services/frontend/notification.service';
import {
  HrPersonnel,
  HrPersonnelFilterParams,
  HrPersonnelPageResponse,
  HR_SORT_OPTIONS,
  HR_PAGE_SIZE_OPTIONS
} from '../../../../core/models/hr-personnel.model';

@Component({
  selector: 'app-hr-personnel',
  templateUrl: './hr-personnel.component.html',
  styleUrls: ['./hr-personnel.component.scss'],
  standalone: false
})
export class HrPersonnelComponent implements OnInit, OnDestroy {
  // ============================================================================
  // DATA STATE
  // ============================================================================
  
  /** List of HR personnel from API */
  hrPersonnelList: HrPersonnel[] = [];
  
  /** Selected personnel (for future actions) */
  selectedPersonnel: HrPersonnel | null = null;
  
  /** Multiple selection (for bulk actions) */
  selectedPersonnelList: HrPersonnel[] = [];

  // ============================================================================
  // PAGINATION STATE
  // ============================================================================
  
  /** Current page (0-indexed) */
  currentPage = 0;
  
  /** Items per page */
  pageSize = 20;
  
  /** Total number of pages */
  totalPages = 0;
  
  /** Total number of elements */
  totalElements = 0;
  
  /** Is first page flag */
  isFirstPage = true;
  
  /** Is last page flag */
  isLastPage = false;

  // ============================================================================
  // FILTER STATE
  // ============================================================================
  
  /** General search term (debounced) */
  searchTerm = '';
  
  /** CIN filter */
  cinFilter = '';
  
  /** Matricule filter */
  matcleFilter = '';
  
  /** First name filter */
  prenomFilter = '';
  
  /** Last name filter */
  nomuseFilter = '';
  
  /** Sort field */
  sortBy = 'matcle';
  
  /** Sort direction */
  sortDirection: 'ASC' | 'DESC' = 'ASC';

  // ============================================================================
  // UI STATE
  // ============================================================================
  
  /** Loading indicator */
  isLoading = false;
  
  /** Show advanced filters panel */
  showAdvancedFilters = false;
  
  /** View mode (table or grid) */
  viewMode: 'table' | 'grid' = 'table';

  // ============================================================================
  // OPTIONS
  // ============================================================================
  
  /** Sort options for dropdown */
  sortOptions = HR_SORT_OPTIONS;
  
  /** Page size options for dropdown */
  pageSizeOptions = HR_PAGE_SIZE_OPTIONS;

  // ============================================================================
  // SUBJECTS FOR DEBOUNCING
  // ============================================================================
  
  /** Subject for search debouncing */
  private searchSubject = new Subject<string>();
  
  /** Subject for component destruction */
  private destroy$ = new Subject<void>();

  constructor(
    private hrPersonnelService: HrPersonnelService,
    private notificationService: NotificationService
  ) {}

  // ============================================================================
  // LIFECYCLE HOOKS
  // ============================================================================

  ngOnInit(): void {
    this.setupSearchDebounce();
    this.loadHrPersonnel();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  // ============================================================================
  // INITIALIZATION
  // ============================================================================

  /**
   * Setup debounced search subscription
   */
  private setupSearchDebounce(): void {
    this.searchSubject.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      takeUntil(this.destroy$)
    ).subscribe(searchTerm => {
      this.searchTerm = searchTerm;
      this.currentPage = 0; // Reset to first page on search
      this.loadHrPersonnel();
    });
  }

  // ============================================================================
  // DATA LOADING
  // ============================================================================

  /**
   * Load HR Personnel data from API
   */
  loadHrPersonnel(): void {
    this.isLoading = true;

    const params: HrPersonnelFilterParams = {
      page: this.currentPage,
      size: this.pageSize,
      sortBy: this.sortBy,
      sortDirection: this.sortDirection,
      search: this.searchTerm || undefined,
      cin: this.cinFilter || undefined,
      matcle: this.matcleFilter || undefined,
      prenom: this.prenomFilter || undefined,
      nomuse: this.nomuseFilter || undefined
    };

    // Use smart search to decide which endpoint
    this.hrPersonnelService.searchHrPersonnel(params).subscribe({
      next: (response: HrPersonnelPageResponse) => {
        this.hrPersonnelList = response.content;
        this.totalElements = response.totalElements;
        this.totalPages = response.totalPages;
        this.isFirstPage = response.first;
        this.isLastPage = response.last;
        this.isLoading = false;
        
        console.log(`📋 Loaded ${response.numberOfElements} HR personnel (page ${this.currentPage + 1}/${this.totalPages})`);
      },
      error: (error) => {
        console.error('❌ Failed to load HR personnel:', error);
        this.hrPersonnelList = [];
        this.totalElements = 0;
        this.totalPages = 0;
        this.isLoading = false;
        
        this.notificationService.addAlert(
          'Erreur de chargement',
          'Impossible de charger les données du personnel RH. Veuillez réessayer.',
          'error',
          'system'
        );
      }
    });
  }

  /**
   * Refresh data
   */
  refreshData(): void {
    this.loadHrPersonnel();
  }

  // ============================================================================
  // SEARCH HANDLERS
  // ============================================================================

  /**
   * Handle general search input (debounced)
   */
  onSearchChange(value: string): void {
    this.searchSubject.next(value);
  }

  /**
   * Handle CIN filter change
   */
  onCinFilterChange(value: string): void {
    this.cinFilter = value;
    this.currentPage = 0;
    this.loadHrPersonnel();
  }

  /**
   * Handle matricule filter change
   */
  onMatcleFilterChange(value: string): void {
    this.matcleFilter = value;
    this.currentPage = 0;
    this.loadHrPersonnel();
  }

  /**
   * Handle first name filter change
   */
  onPrenomFilterChange(value: string): void {
    this.prenomFilter = value;
    this.currentPage = 0;
    this.loadHrPersonnel();
  }

  /**
   * Handle last name filter change
   */
  onNomuseFilterChange(value: string): void {
    this.nomuseFilter = value;
    this.currentPage = 0;
    this.loadHrPersonnel();
  }

  // ============================================================================
  // FILTER MANAGEMENT
  // ============================================================================

  /**
   * Toggle advanced filters panel
   */
  toggleAdvancedFilters(): void {
    this.showAdvancedFilters = !this.showAdvancedFilters;
  }

  /**
   * Check if any filters are active
   */
  hasActiveFilters(): boolean {
    return !!(
      this.searchTerm ||
      this.cinFilter ||
      this.matcleFilter ||
      this.prenomFilter ||
      this.nomuseFilter
    );
  }

  /**
   * Get count of active filters
   */
  getActiveFilterCount(): number {
    let count = 0;
    if (this.searchTerm) count++;
    if (this.cinFilter) count++;
    if (this.matcleFilter) count++;
    if (this.prenomFilter) count++;
    if (this.nomuseFilter) count++;
    return count;
  }

  /**
   * Clear all filters and reset to defaults
   */
  clearFilters(): void {
    this.searchTerm = '';
    this.cinFilter = '';
    this.matcleFilter = '';
    this.prenomFilter = '';
    this.nomuseFilter = '';
    this.currentPage = 0;
    this.sortBy = 'matcle';
    this.sortDirection = 'ASC';
    this.loadHrPersonnel();
  }

  /**
   * Remove a specific filter
   */
  removeFilter(filterType: string): void {
    switch (filterType) {
      case 'search':
        this.searchTerm = '';
        break;
      case 'cin':
        this.cinFilter = '';
        break;
      case 'matcle':
        this.matcleFilter = '';
        break;
      case 'prenom':
        this.prenomFilter = '';
        break;
      case 'nomuse':
        this.nomuseFilter = '';
        break;
    }
    this.currentPage = 0;
    this.loadHrPersonnel();
  }

  // ============================================================================
  // SORTING
  // ============================================================================

  /**
   * Handle sort field change
   */
  onSortChange(sortBy: string): void {
    if (this.sortBy === sortBy) {
      // Toggle direction if same field
      this.sortDirection = this.sortDirection === 'ASC' ? 'DESC' : 'ASC';
    } else {
      this.sortBy = sortBy;
      this.sortDirection = 'ASC';
    }
    this.loadHrPersonnel();
  }

  /**
   * Toggle sort direction
   */
  toggleSortDirection(): void {
    this.sortDirection = this.sortDirection === 'ASC' ? 'DESC' : 'ASC';
    this.loadHrPersonnel();
  }

  /**
   * Handle column header click for sorting
   */
  onColumnSort(field: string): void {
    this.onSortChange(field);
  }

  /**
   * Get sort icon for column
   */
  getSortIcon(field: string): string {
    if (this.sortBy !== field) {
      return 'pi pi-sort-alt';
    }
    return this.sortDirection === 'ASC' ? 'pi pi-sort-amount-up' : 'pi pi-sort-amount-down';
  }

  // ============================================================================
  // PAGINATION
  // ============================================================================

  /**
   * Go to specific page
   */
  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadHrPersonnel();
    }
  }

  /**
   * Go to next page
   */
  nextPage(): void {
    if (!this.isLastPage) {
      this.currentPage++;
      this.loadHrPersonnel();
    }
  }

  /**
   * Go to previous page
   */
  previousPage(): void {
    if (!this.isFirstPage) {
      this.currentPage--;
      this.loadHrPersonnel();
    }
  }

  /**
   * Go to first page
   */
  firstPage(): void {
    this.currentPage = 0;
    this.loadHrPersonnel();
  }

  /**
   * Go to last page
   */
  lastPage(): void {
    this.currentPage = this.totalPages - 1;
    this.loadHrPersonnel();
  }

  /**
   * Handle page size change
   */
  onPageSizeChange(size: number): void {
    this.pageSize = size;
    this.currentPage = 0; // Reset to first page
    this.loadHrPersonnel();
  }

  /**
   * Get array of page numbers for pagination display
   */
  getPageNumbers(): number[] {
    const pages: number[] = [];
    const maxPagesToShow = 5;
    let startPage = Math.max(0, this.currentPage - Math.floor(maxPagesToShow / 2));
    let endPage = Math.min(this.totalPages - 1, startPage + maxPagesToShow - 1);
    
    // Adjust start if we're near the end
    if (endPage - startPage < maxPagesToShow - 1) {
      startPage = Math.max(0, endPage - maxPagesToShow + 1);
    }
    
    for (let i = startPage; i <= endPage; i++) {
      pages.push(i);
    }
    return pages;
  }

  // ============================================================================
  // ROW SELECTION
  // ============================================================================

  /**
   * Handle row selection
   */
  onRowSelect(personnel: HrPersonnel): void {
    this.selectedPersonnel = personnel;
    console.log('👤 Selected:', personnel.matcle, '-', personnel.prenom, personnel.nomuse);
  }

  /**
   * Handle row unselection
   */
  onRowUnselect(): void {
    this.selectedPersonnel = null;
  }

  /**
   * Check if a row is selected
   */
  isSelected(personnel: HrPersonnel): boolean {
    return this.selectedPersonnel?.matcle === personnel.matcle;
  }

  // ============================================================================
  // VIEW HELPERS
  // ============================================================================

  /**
   * Toggle view mode
   */
  toggleViewMode(): void {
    this.viewMode = this.viewMode === 'table' ? 'grid' : 'table';
  }

  /**
   * Get full name from personnel
   */
  getFullName(personnel: HrPersonnel): string {
    return `${personnel.prenom || ''} ${personnel.nomuse || ''}`.trim() || 'N/A';
  }

  /**
   * Track by function for ngFor optimization
   */
  trackByMatcle(index: number, personnel: HrPersonnel): string {
    return personnel.matcle;
  }
}
