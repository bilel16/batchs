import { Component, OnInit } from '@angular/core';
import { PanelConfig } from '../../../shared/panel/panel.component';
import { PanelHelperService } from '../../../core/services/frontend/panel-helper.service';
import { Router } from '@angular/router';
import { ProductService } from '../../../core/services/frontend/product.service';
import { TabItem } from '../../../shared/horizontal-tab/horizontal-tab.component';

@Component({
  selector: 'app-sample',
  standalone: false,
  templateUrl: './sample.component.html',
  styleUrl: './sample.component.scss',
})
export class SampleComponent implements OnInit {

  stateOptions : any;
  value: string = 'off';
  ingredient!: string;
  // Dialog visibility states
  addFileDialogVisible: boolean = false;
  editDialogVisible: boolean = false;
  customDialogVisible: boolean = false;
  // Form data
  price: number = 0;
  quantity: number = 0;
  countries: any[] | undefined;
  selectedCountry: string | undefined;

  constructor(public helper: PanelHelperService, private router: Router, private productService: ProductService) {}

  /********************* Data Example  ********************************/
  /********************************************************************/
  

  /********** example  API data *************/
  tableData: any[] = []
   loadData(filterObject: any): void {


    this.productService.getProducts().subscribe({
      next: (data) => {
        this.tableData = Array.isArray(data) ? data : [];
        console.log('Products loaded:', this.tableData);
      },
      error: (err) => console.error('Error loading products:', err)
    });
      
  }

  // info data
  backendData = {
    caseNumber: 'CASE-2024-001',
    caseUrl: '/cases/CASE-2024-001',
    openDate: '2024-01-15',
    closeDate: null,
    court: 'Tribunal de Commerce',
    jurisdictionLevel: '1ère instance',
    defendants: ['BNA'],
    plaintiffs: [
      'Foulen Ben Foulen 1',
      'Foulen Ben Foulen 2',
      'Foulen Ben Foulen 3',
    ],
    description: '<strong>Important case</strong> regarding contract dispute',
    previousCaseNumber: 'CASE-2023-999',
    previousCaseUrl: '/cases/CASE-2023-999',
  };

  /********************* Example Panel Config  ********************************/
  /********************************************************************/
  panelConfig: PanelConfig = {
    title: 'Liste des <title here>',
    headerBackgroundColor: '#115952',
    headerTextColor: '#fff',
  };


  /********************* Example table Config  ********************************/
  /********************************************************************/

    tableConfig = {
    columns: [
      {
        field: 'id',
        header: 'N° Affaire',
        sortable: false,
        type: 'text',
        minWidth: '3rem',
        width: '8rem',
        filter: true,
        filterType: 'text', // text input filter
        filterStyle: { width: '100%' },
      },
      {
        field: 'name',
        header: 'Name',
        sortable: true,
        type: 'text',
        filter: true,
        minWidth: '3rem',
        width: '16rem',
        filterType: 'dropdown', // dropdown filter
        filterOptions: [
          'Product 1',
          'In Progress In ProgressvInProgressInProgressInProgressIn Progress ',
          'Done',
        ],
        filterStyle: { width: '100%' },
      },
      {
        field: 'price',
        header: 'Price',
        type: 'tag',
        severityFn: (status: string) => {
          switch (status) {
            case 'In Stock':
              return 'success';
            case 'Low Stock':
              return 'warning';
            case 'Out of Stock':
              return 'danger';
            default:
              return 'info';
          }
        },
      },
      {
        field: 'name2',
        header: 'Name',
        sortable: true,
        type: 'text',
        filter: true,
      },
      {
        field: 'name3',
        header: 'Name',
        sortable: true,
        type: 'text',
        filter: true,
      },
      {
        field: 'name5',
        header: 'Name',
        sortable: true,
        type: 'text',
        filter: true,
      },
      {
        field: 'name4',
        header: 'Name',
        sortable: true,
        type: 'text',
        filter: true,
      },
    ],

    showActions: true,
    actions: {
      view: true,
      edit: true,
      notification: true,
      send: true,
      delete: true,
    },
    styleAction: {
      'min-width': '4rem',
      width: '13.5rem',
    },
    pagination: true,
    pageSize: 10,
  };
  

  ngOnInit() {
    
  /********************* Examples of data  ********************************/
  /********************************************************************/

  this.loadData({});

    this.countries = [
      { name: 'Australia', code: 'AU' },
      { name: 'Brazil', code: 'BR' },
      { name: 'China', code: 'CN' },
      { name: 'Egypt', code: 'EG' },
      { name: 'France', code: 'FR' },
      { name: 'Japan', code: 'JP' },
      { name: 'Spain', code: 'ES' },
      { name: 'Tunisia', code: 'TN' },
    ];

    this.stateOptions = [
      { label: 'One-Way', value: 'one-way' },
      { label: 'Return', value: 'return' },
    ];
  }


  /********************* button calls examples  ********************************/
  /********************************************************************/
  editProduct(product: any) {
    console.log('Edit:', product);
  }

  notifProduct(product: any) {
    console.log('Notif:', product);
  }

  viewProduct(product: any) {
    console.log('View:', product);
  }
  deleteProduct(product: any) {
    console.log('Delete:', product);
  }

  showAddFileDialog() {
    this.addFileDialogVisible = true;
  }

  saveEdit() {
    console.log('Saving file:', { price: this.price, quantity: this.quantity });
    this.addFileDialogVisible = false;
  }

  cancel() {
    console.log('Cancelled');
    this.price = 0;
    this.quantity = 0;
  }



  /********* Tabs config  ************/
 currentTab: number = 0;

    tabs: TabItem[] = [
        { id: 'dashboard', label: 'Dashboard', icon: 'pi pi-home' },
        { id: 'analytics', label: 'Analytics', icon: 'pi pi-chart-line' },
        { id: 'reports', label: 'Reports', icon: 'pi pi-file' },
        { id: 'settings', label: 'Settings', icon: 'pi pi-cog' }
    ];

    onTabChanged(event: { tab: TabItem; index: number }): void {
        console.log('Tab changed to:', event.tab.label, 'Index:', event.index);
    }

    getCurrentTime(): string {
        return new Date().toLocaleTimeString();
    }
}
