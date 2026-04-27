import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
  OnDestroy,
} from '@angular/core';
import { Table, TableLazyLoadEvent } from 'primeng/table';

export interface TableColumn {
  field: string;
  header: string;
  sortable?: boolean;
  type?: 'text' | 'numeric' | 'image' | 'date';
  width?: string;
  minWidth?: string;
  maxWidth?: string;
  style?: any;
  filter?: boolean;
  filterType?: 'input' | 'select';
  filterOptions?: { label: string; value: any }[];
  filterPlaceholder?: string;
}

@Component({
  selector: 'app-paginated-table',
  standalone: false,
  templateUrl: './table-pagination.component.html',
  styleUrl: './table-pagination.component.scss',
})
export class TablePaginatedComponent implements OnInit, OnDestroy {
  @ViewChild('dt') table!: Table;

  @Input() config: any;
  @Input() data: any[] = [];
  @Input() columns: any[] = [];
  @Input() showActions: boolean = false;
  @Input() actions: any = {
    view: true,
    edit: true,
    notification: true,
    send: true,
    delete: true,
  };
  @Input() styleAction: any = {};

  @Output() onEdit = new EventEmitter<any>();
  @Output() onNotification = new EventEmitter<any>();
  @Output() onView = new EventEmitter<any>();
  @Output() onSend = new EventEmitter<any>();
  @Output() onDelete = new EventEmitter<any>();
  @Output() filtersApplied = new EventEmitter<any>();

  filterValues: { [key: string]: any } = {};
  isFiltered: { [key: string]: boolean } = {};

  /********** Lazy Loading Options *****************/
  @Input() lazy: boolean = true;
  @Input() totalRecords: number = 0;
  @Input() loading: boolean | null = false;
  @Input() rows: number = 15;
  @Input() rowsPerPageOptions: number[] = [10, 15, 20, 50];
  @Output() lazyLoad = new EventEmitter<TableLazyLoadEvent>();
  /*************************************************/

  private filterDebounceTimers: { [key: string]: any } = {};
  private activeFilters: { [key: string]: any } = {};
  private readonly DEBOUNCE_TIME = 500;
  private lastLazyEvent?: TableLazyLoadEvent;

  ngOnInit() {
    if (this.config) {
      this.columns = this.config.columns || [];
      this.showActions = this.config.showActions || false;
      this.actions = this.config.actions || {
        view: true,
        edit: true,
        notification: true,
        send: true,
        delete: true,
      };
      this.styleAction = this.config.styleAction || {};
      this.columns.forEach((col) => {
        this.filterValues[col.field] = '';
        this.isFiltered[col.field] = false;
      });
    }
  }

  ngOnDestroy() {
    Object.keys(this.filterDebounceTimers).forEach((key) => {
      clearTimeout(this.filterDebounceTimers[key]);
    });
  }

  // Handle lazy load event from PrimeNG table
  onLazyLoadInternal(event: TableLazyLoadEvent) {
    this.lastLazyEvent = event;

    // Merge active filters with event
    const eventWithFilters = {
      ...event,
      filters: this.buildFilterEvent(),
    };
    this.lazyLoad.emit(eventWithFilters);
  }

  // Build filter event in PrimeNG format
  private buildFilterEvent(): any {
    const filters: any = {};

    Object.keys(this.activeFilters).forEach((key) => {
      filters[key] = [
        {
          value: this.activeFilters[key],
          matchMode: 'contains',
          operator: 'and',
        },
      ];
    });

    return filters;
  }

  hasFilterValue(col: any): boolean {
    const fieldKey = this.getFieldKey(col.field);
    const value = this.filterValues[fieldKey];
    return value !== null && value !== undefined && value !== '';
  }
  getFieldKey(field: any): string {
    // Handle array format: [{ dataKey: 'qualitePersonne.libelle', idKey: 'qualitePersonne.id' }]
    if (Array.isArray(field) && field.length > 0) {
      return field[0].idKey || '';
    }
    // Handle object format: { dataKey: 'qualitePersonne.libelle', idKey: 'qualitePersonne.id' }
    if (typeof field === 'object' && field !== null && field.idKey) {
      return field.idKey;
    }
    // Handle simple string format: 'libelle'
    return field || '';
  }

  // Update your existing method to accept any type
  onFilterChange(field: any, filterType: 'input' | 'select' = 'input') {
    const fieldKey = this.getFieldKey(field); // Convert to string key
    const filterValue = this.filterValues[fieldKey];
    this.isFiltered[fieldKey] =
      filterValue && filterValue.toString().length > 0;

    if (filterType === 'select') {
      this.updateFilterObject(fieldKey, filterValue);
      this.applyFiltersWithBackend();
    } else {
      this.debounceFilter(fieldKey, filterValue);
    }
  }

  private updateFilterObject(field: string, value: any) {
    if (this.isValidFilterValue(value)) {
      this.activeFilters[field] = value;
    } else {
      delete this.activeFilters[field];
    }
  }

  private debounceFilter(field: string, value: any) {
    if (this.filterDebounceTimers[field]) {
      clearTimeout(this.filterDebounceTimers[field]);
    }

    this.filterDebounceTimers[field] = setTimeout(() => {
      this.updateFilterObject(field, value);
      this.applyFiltersWithBackend();
    }, this.DEBOUNCE_TIME);
  }

  private isValidFilterValue(value: any): boolean {
    if (value === null || value === undefined || value === '') {
      return false;
    }
    return true;
  }

  // Apply filters and trigger backend call
  private applyFiltersWithBackend() {
    if (this.lazy && this.table) {
      // Reset to first page when filtering
      this.table.first = 0;

      // Create lazy load event with current state
      const event: TableLazyLoadEvent = {
        first: 0,
        rows: this.table.rows || this.rows,
        sortField: this.table.sortField,
        sortOrder: this.table.sortOrder,
        filters: this.buildFilterEvent(),
        globalFilter: null,
      };

      this.onLazyLoadInternal(event);
    } else {
      // For non-lazy tables, use existing logic
      this.applyFilters();
    }
  }

  // Keep your existing applyFilters for backward compatibility
  applyFilters() {
    this.filtersApplied.emit(this.activeFilters);
  }

  getActiveFiltersAsJSON(): string {
    return JSON.stringify(this.activeFilters);
  }

  clearAllFilters() {
    this.filterValues = {};
    this.isFiltered = {};
    this.activeFilters = {};

    Object.keys(this.filterDebounceTimers).forEach((key) => {
      clearTimeout(this.filterDebounceTimers[key]);
    });
    this.filterDebounceTimers = {};

    if (this.lazy && this.table) {
      this.table.clear();
    } else if (this.config?.data) {
      this.data = [...this.config.data];
    }
  }

  // Public method to refresh table (called from parent)
  refresh() {
    if (this.lastLazyEvent) {
      this.onLazyLoadInternal(this.lastLazyEvent);
    }
  }

  getNestedValue(obj: any, field: any): any {
    const path = typeof field === 'string' ? field : field[0]?.dataKey;
    return path
      ?.split('.')
      .reduce((current: any, prop: any) => current?.[prop], obj);
  }

  getColumnStyle(col: TableColumn): any {
    const style: any = {};
    if (col.minWidth) style['min-width'] = col.minWidth;
    if (col.width) style['width'] = col.width;
    if (col.maxWidth) style['max-width'] = col.width;
    return { ...style, ...col.style };
  }

  getDropdownOptions(
    options: { label: string; value: any }[] = []
  ): { label: string; value: any }[] {
    return options; // Just return as-is since they're already in the correct format
  }

  onRowClick(item: any) {
    console.log('Row clicked:', item);
  }
}
