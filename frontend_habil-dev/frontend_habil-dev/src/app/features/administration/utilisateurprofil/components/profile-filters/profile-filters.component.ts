import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
export interface ProfileFilters {
  searchTerm: string;
  selectedLevel: string | null;
  selectedCategory: string | null;
  selectedStatus: number | null;
}

@Component({
  selector: 'app-profile-filters',
  templateUrl: './profile-filters.component.html',
  styleUrls: ['./profile-filters.component.scss'],
  standalone: false
})
export class ProfileFiltersComponent implements OnInit {
  @Input() profileFilters: ProfileFilters = {
    searchTerm: '',
    selectedLevel: null,
    selectedCategory: null,
    selectedStatus: null
  };
  @Input() levelFilterOptions: any[] = [];
  @Input() categoryFilterOptions: any[] = [];
  @Input() statusFilterOptions: any[] = [];

  @Output() filtersChange = new EventEmitter<ProfileFilters>();
  @Output() clearAllFilters = new EventEmitter<void>();
  @Output() tabChange = new EventEmitter<number>();

  selectedTabIndex: number = 0;

  ngOnChanges(changes: any): void {
    // Update local filters when input changes (e.g., when switching tabs)
    if (changes.profileFilters && changes.profileFilters.currentValue) {
      // The profileFilters input has changed, component will automatically update
    }
  }
  
  ngOnInit(): void {
    // Initialize with default status filter options
    if (this.statusFilterOptions.length === 0) {
      this.statusFilterOptions = [
        { label: 'Actif', value: 1 },
        { label: 'Inactif', value: 0 }
      ];
    }
  }
  onFilterChange(): void {
    this.filtersChange.emit(this.profileFilters);
  }

  onClearAllFilters(): void {
    this.profileFilters = {
      searchTerm: '',
      selectedLevel: null,
      selectedCategory: null,
      selectedStatus: null
    };
    this.clearAllFilters.emit();
  }  onTabChanged(tabValue: string | number): void {
    // Convert string tab value to index number
    let index: number;
    if (typeof tabValue === 'string') {
      // Map tab labels to indices
      const tabLabels = ['Profils disponibles', 'Profils assignés'];
      index = tabLabels.indexOf(tabValue);
      if (index === -1) index = 0; // Default to first tab if not found
    } else {
      index = tabValue;
    }
    
    this.selectedTabIndex = index;
    this.tabChange.emit(index);
  }

  hasActiveFilters(): boolean {
    return !!(
      this.profileFilters.searchTerm?.trim() ||
      this.profileFilters.selectedLevel ||
      this.profileFilters.selectedCategory ||
      this.profileFilters.selectedStatus !== null
    );
  }
}