import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Table} from 'primeng/table';

export interface TableColumn {
  field: string;
  header: string;
  sortable?: boolean;
  type?: 'text' | 'number' | 'date' | 'tag' | 'boolean';
  width?: string;
  minWidth?: string;
  maxWidth?: string;
  style?: any;
  filter?: boolean;
  filterType?: 'text' | 'dropdown' | 'boolean';
  filterOptions?: string[];
  severityFn?: (value: any) => string;
}

@Component({
  selector: 'app-table',
  standalone: false,
  templateUrl: './table.component.html',
  styleUrl: './table.component.scss',
})
export class TableComponent implements OnInit {
  @ViewChild('dt') table!: Table;
  @Input() config: any;
  @Input() data: any[] = [];
  @Input() columns: any[] = [];
  @Input() search: boolean = true;
  @Input() showActions: boolean = false;
  @Input() actions: any = {
    view: true,
    edit: true,
    notification: true,
    send: true,
    delete: true,
  };
  @Input() styleAction: any = {};
  @Input() totalLabel: string = 'Total des lignes';
  @Input() showTotal: boolean = true;
  @Input() loading: boolean = false;
  @Input() searchPlaceholder: string = 'Rechercher';
  @Input() emptyMessage: string = 'Aucune donnée trouvée';
  @Input() emptySubMessage: string = 'Aucun résultat ne correspond à votre recherche';

  @Output() onEdit = new EventEmitter<any>();
  @Output() onNotification = new EventEmitter<any>();
  @Output() onView = new EventEmitter<any>();
  @Output() onSend = new EventEmitter<any>();
  @Output() onDelete = new EventEmitter<any>();
  @Output() onRowClick = new EventEmitter<any>();
  @Output() filteredDataChange = new EventEmitter<any[]>();
  searchTerm: string = '';
  filteredData: any[] = [];
  skeletonRows: any[] = Array(5).fill({}); // 5 skeleton rows

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
    }
    this.filteredData = [...this.data];
  }

  ngOnChanges() {
    this.filteredData = [...this.data];
    this.applySearch();
    this.filteredDataChange.emit(this.filteredData);
  }

  getColumnStyle(col: TableColumn): any {
    const style: any = {};
    if (col.minWidth) {
      style['min-width'] = col.minWidth;
    }
    if (col.width) {
      style['width'] = col.width;
    }
    if (col.maxWidth) {
      style['max-width'] = col.width;
    }
    return { ...style, ...col.style };
  }

  getDropdownOptions(options: string[] = []): { label: string; value: any }[] {
    return options.map((o) => ({ label: o, value: o }));
  }

  handleRowClick(item: any) {
    this.onRowClick.emit(item);
  }

  getNestedValue(obj: any, path: string): any {
    return path.split('.').reduce((current, prop) => current?.[prop], obj);
  }

  onSearch() {
    this.applySearch();
  }

  clearSearch() {
    this.searchTerm = '';
    this.applySearch();
  }

  applySearch() {
    if (!this.searchTerm) {
      this.filteredData = [...this.data];
    } else {
      const searchNormalized = this.searchTerm.toString().toLowerCase().replace(/\s+/g, '');
      this.filteredData = this.data.filter(item => {
        return this.columns.some(col => {
          const value = this.getNestedValue(item, col.field);
          if (value === null || value === undefined) return false;
          const valueNormalized = value.toString().toLowerCase().replace(/\s+/g, '');
          return valueNormalized.includes(searchNormalized);
        });
      });
    }

    if (this.table) {
      this.table.reset();
    }
    this.filteredDataChange.emit(this.filteredData);
  }
 /**
   * 🔥 PUBLIC API — apply filter from parent
   */
  applyGlobalFilter(value: any[], reason: string = 'external'): void {
    this.filteredData = [...value];

    // reset paginator to first page
    this.table?.reset();

    console.log(`[Table] Filter applied (${reason})`, this.filteredData);
  }
  refreshData() {
    this.filteredData = [...this.data];
    this.searchTerm = '';
    this.filteredDataChange.emit(this.filteredData);
  }
}
