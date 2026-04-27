import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { trigger, transition, style, animate } from '@angular/animations';
import { MessageService, ConfirmationService } from 'primeng/api';
import {AnimatedDrawerComponent} from '../../../shared/components/animated-drawer/animated-drawer.component';
import {Application} from '../../../core/models/application';
import {SharedFrontService} from '../../../core/services/frontend/shared-front.service';
import {ApplicationService} from '../../../core/services/backend/application.service';
import {BackendIntegrationService} from '../../../core/services/frontend/backend-integration.service';
import {ActionType} from '../../../core/enums/actionType.enum';
import AOS from 'aos';

@Component({
  selector: "app-application",
  templateUrl: "./application.component.html",
  styleUrls: ["./application.component.scss"],
  standalone: false
})
export class ApplicationComponent implements OnInit, AfterViewInit {
  @ViewChild('drawer') drawer!: AnimatedDrawerComponent;

  // Data
  application: Application = {};
  applications: Application[] = [];
  filteredApplications: Application[] = [];

  // UI State
  submitted = false;
  action: ActionType = ActionType.ADD;
  public actionType = ActionType;
  loading = false;

  // Search and Filter
  searchTerm = '';

  // Stats
  totalApplications = 0;

  tableConfig = {
    columns: [
      {
        field: 'codApp',
        header: 'Code Application',
        sortable: true,
        type: 'text',
        minWidth: '3rem',
        width: '8rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'libApp',
        header: 'Libellé Application',
        sortable: true,
        type: 'text',
        filter: true,
        minWidth: '3rem',
        width: '16rem',
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'libLab',
        header: 'Description Application',
        type: 'tag',
        width: '12rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
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
      }
    ],
    showActions: true,
    actions: {
      view: false,
      edit: true,
      notification: false,
      send: false,
      delete: false,
    },
    styleAction: {
      'min-width': '4rem',
      width: '5.5rem',
    },
    pagination: true,
    pageSize: 10,
  };

  constructor(
    private sharedService: SharedFrontService,
    private applicationService: ApplicationService,
    private backendIntegration: BackendIntegrationService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
            AOS.init({
          easing: 'linear',
          mirror: true
        });
    this.updateStats();
  }

  ngAfterViewInit(): void {
    this.getAllApplications();
  }

  private getAllApplications() {
    this.loading = true;
    this.applicationService.getAll().subscribe({
      next: (result) => {
        const data = result || [];
        this.applications = Array.isArray(data) ? data : [];
        this.filteredApplications = [...this.applications];
        this.updateStats();
        this.loading = false;
      },
      error: (error) => {
        console.error("Failed to load applications", error);
        this.applications = [];
        this.filteredApplications = [];
        this.loading = false;
      }
    });
  }

  updateStats(): void {
    this.totalApplications = this.applications.length;
  }

  openNew() {
    this.action = ActionType.ADD;
    this.application = {};
    this.submitted = false;
    this.drawer.openDrawer();
  }

  saveApplication(app: Application) {
    this.submitted = true;

    if (!app || !app.codApp) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Attention',
        detail: "Le code de l'application est requis"
      });
      return;
    }

    const payload: Application = {
      ...app,
      // Ensure booleans default to false if not set
      central: app.central || false,
      regional: app.regional || false,
      agence: app.agence || false
    };

    this.backendIntegration.quickOperation(
      this.applicationService.createWithProfiles(payload),
      "Application créée avec succès",
      {
        onSuccess: (result) => {
          this.drawer.closeDrawer();
          this.getAllApplications();
        },
        onError: (error) => {
          console.error("Create application failed", error);
        }
      }
    );
  }

  editApplication(app: Application) {
    this.action = ActionType.EDIT;
    this.application = { ...app };

    // Fetch details with profile flags
    this.applicationService.getApplicationDetails(app.codApp!).subscribe({
      next: (details) => {
        this.application = {
          ...this.application,
          central: details.central || false,
          regional: details.regional || false,
          agence: details.agence || false
        };
      },
      error: (err) => {
        console.error('Failed to load application details', err);
        this.application.central = false;
        this.application.regional = false;
        this.application.agence = false;
      }
    });
  }

  edit(app: Application) {
    this.submitted = true;

    if (!app || !app.codApp) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Attention',
        detail: "Le code de l'application est requis pour la modification"
      });
      return;
    }

    const codApp = app.codApp;

    const payload: Application = {
      ...app,
      central: app.central || false,
      regional: app.regional || false,
      agence: app.agence || false
    };

    this.backendIntegration.quickOperation(
      this.applicationService.updateWithProfiles(codApp, payload),
      "Application modifiée avec succès",
      {
        onSuccess: (result) => {
          this.drawer.closeDrawer();
          this.getAllApplications();
        },
        onError: (error) => {
          console.error("Update application failed", error);
        }
      }
    );
  }

  deleteApplication(app: Application) {
    if (!app || !app.codApp) {
      this.messageService.add({
        severity: 'error',
        summary: 'Erreur',
        detail: "Identifiant application introuvable"
      });
      return;
    }

    const confirmMessage = `l'application "${app.libApp || app.codApp}"`;

    this.backendIntegration.confirmAndDelete(
      confirmMessage,
      this.applicationService.delete(app.codApp),
      "Application supprimée avec succès",
      {
        onSuccess: (result) => {
          this.getAllApplications();
        },
        onError: (error) => {
          console.error("Failed to delete application", error);
        }
      }
    );
  }

}
