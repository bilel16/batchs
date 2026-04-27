import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Subject } from 'rxjs';
import { distinctUntilChanged, takeUntil } from 'rxjs/operators';
import { trigger, transition, style, animate } from '@angular/animations';
import { MessageService, ConfirmationService } from 'primeng/api';
import {AnimatedDrawerComponent} from '../../../shared/components/animated-drawer/animated-drawer.component';
import {MenuApplication} from '../../../core/models/menuapplication';
import {Application} from '../../../core/models/application';
import {ActionType} from '../../../core/enums/actionType.enum';
import {ApplicationService} from '../../../core/services/backend/application.service';
import {MenuApplicationService} from '../../../core/services/backend/menu-application.service';
import {SharedFrontService} from '../../../core/services/frontend/shared-front.service';
import {ApplicationContextService} from '../../../core/services/frontend/ApplicationContextService.service';
import AOS from 'aos';



@Component({
  selector: "app-menuapplication",
  templateUrl: "./menuapplication.component.html",
  styleUrls: ["./menuapplication.component.scss"],
  standalone: false
})
export class MenuApplicationComponent implements OnInit, OnDestroy {
  @ViewChild('drawer') drawer!: AnimatedDrawerComponent;

  private destroy$ = new Subject<void>();

  // Data
  menuApp: MenuApplication = {};
  menuApps: MenuApplication[] = [];
  filteredMenuApps: MenuApplication[] = [];
  applications: Application[] = [];

  // UI State
  showNoAppMessage = true;
  submitted = false;
  action: ActionType = ActionType.ADD;
  public actionType = ActionType;
  loading = false;

  // Selection
  selectedAppCode: string | null = '';

  // Search and Filter
  searchTerm = '';

  // Stats
  totalMenus = 0;

  tableConfig = {
    columns: [
      {
        field: 'codAppApp',
        header: 'Code Application',
        sortable: true,
        type: 'text',
        minWidth: '3rem',
        width: '10rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'codMenuMenu',
        header: 'Code Menu',
        sortable: true,
        type: 'text',
        minWidth: '3rem',
        width: '10rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'libMenuMenu',
        header: 'Libellé Menu',
        sortable: true,
        type: 'text',
        filter: true,
        minWidth: '3rem',
        width: '16rem',
        filterType: 'text',
        filterStyle: { width: '50%' },
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
    private applicationService: ApplicationService,
    private menuApplicationService: MenuApplicationService,
    private sharedService: SharedFrontService,
    private appContext: ApplicationContextService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
        AOS.init({
      easing: 'linear',
      mirror: true
    });
    this.appContext.selectedApp$
      .pipe(
        distinctUntilChanged(),
        takeUntil(this.destroy$)
      )
      .subscribe((appCode) => {
        this.selectedAppCode = appCode;
        console.log("Current global app:", appCode);

        if (appCode) {
          this.showNoAppMessage = false;
          this.getMenuApplicationsByApp();
        } else {
          this.menuApps = [];
          this.filteredMenuApps = [];
          this.showNoAppMessage = true;
        }
      });
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private getAllApplications() {
    this.applicationService.getAll().subscribe(
      (apps) => {
        this.applications = apps || [];
      },
      (error) => {
        console.error("Failed to load applications", error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Erreur lors du chargement des applications'
        });
      }
    );
  }

  onAppsLoaded(applications: Application[]): void {
    console.log("OnAppsLoaded executing");
  }

  onAppSelectionChange(newApp: string): void {
    console.log("changes occurred: " + newApp);
  }

  private getMenuApplicationsByApp(): void {
    if (!this.selectedAppCode) {
      this.menuApps = [];
      this.filteredMenuApps = [];
      return;
    }

    this.loading = true;
    this.menuApplicationService.getMenuApplicationList(this.selectedAppCode).subscribe(
      (result) => {
        this.menuApps = result?.data || [];
        this.filteredMenuApps = [...this.menuApps];
        this.updateStats();
        this.loading = false;
      },
      (error) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Erreur lors du chargement des menus'
        });
        this.loading = false;
      }
    );
  }

  updateStats(): void {
    this.totalMenus = this.menuApps.length;
  }

  openNew() {
    if (!this.selectedAppCode) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Attention',
        detail: 'Veuillez sélectionner une application'
      });
      return;
    }

    this.getAllApplications();
    this.action = ActionType.ADD;
    this.menuApp = { codAppApp: this.selectedAppCode } as MenuApplication;
    this.submitted = false;
    this.drawer.openDrawer();
  }

  saveMenuApp(menuApp: MenuApplication) {
    this.submitted = true;
    const hasRequiredFields = menuApp.codAppApp && menuApp.codMenuMenu && menuApp.libMenuMenu;

    if (!hasRequiredFields) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Attention',
        detail: 'Veuillez remplir tous les champs obligatoires'
      });
      return;
    }

    const payload: MenuApplication = { ...menuApp };

    this.menuApplicationService.addMenuApplication(payload).subscribe(
      (data: { returnCode: number; message: any; }) => {
        if (data?.returnCode === 1) {
          this.messageService.add({
            severity: 'error',
            summary: 'Erreur',
            detail: data?.message || 'Impossible de créer le menu'
          });
        } else {
          this.messageService.add({
            severity: 'success',
            summary: 'Succès',
            detail: 'Menu créé avec succès'
          });
          this.drawer.closeDrawer();
          this.getMenuApplicationsByApp();
        }
      },
      (error: any) => {
        console.error("Create menu failed", error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Erreur lors de la création du menu'
        });
      }
    );
  }

  editMenuApp(menuApp: MenuApplication) {
    this.action = ActionType.EDIT;
    this.menuApp = { ...menuApp };
    this.submitted = false;
  }

  edit(menuApp: MenuApplication) {
    this.submitted = true;

    if (!menuApp) {
      this.messageService.add({
        severity: 'error',
        summary: 'Erreur',
        detail: 'Données du menu invalides'
      });
      return;
    }

    const payload: MenuApplication = { ...menuApp };

    this.menuApplicationService.updateMenuApplication(payload).subscribe(
      (response) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Succès',
          detail: 'Menu modifié avec succès'
        });
        this.drawer.closeDrawer();
        this.getMenuApplicationsByApp();
      },
      (error) => {
        console.error("Update menu failed", error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Erreur lors de la modification du menu'
        });
      }
    );
  }

  deleteMenuApp(menu: MenuApplication) {
    this.confirmationService.confirm({
      message: `Êtes-vous sûr de vouloir supprimer le menu "${menu.libMenuMenu}" ?`,
      header: 'Confirmation de suppression',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Oui, supprimer',
      rejectLabel: 'Annuler',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.menuApplicationService.deleteMenuApplication(menu).subscribe(
          (response) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Succès',
              detail: 'Menu supprimé avec succès'
            });
            this.getMenuApplicationsByApp();
          },
          (error) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Erreur',
              detail: 'Erreur lors de la suppression du menu'
            });
          }
        );
      }
    });
  }

  removeSpaces(event: any): void {
    const input = event.target as HTMLInputElement;
    const value = input.value.replace(/\s/g, '');
    this.menuApp.codMenuMenu = value;
    input.value = value;
  }

}
