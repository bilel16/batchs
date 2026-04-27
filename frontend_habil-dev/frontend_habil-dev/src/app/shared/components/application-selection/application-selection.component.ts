import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from "@angular/core";
import { Subject, takeUntil } from "rxjs";

import { Application } from "../../../core/models/application";
import { ApplicationService } from "../../../core/services/backend/application.service";
import { ApplicationContextService } from "../../../core/services/frontend/ApplicationContextService.service";

@Component({
  selector: "app-application-selection",
  templateUrl: "./application-selection.component.html",
  styleUrl: "./application-selection.component.scss",
  standalone: false,
})
export class ApplicationSelectionComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();

  applications: Application[] = [];
  loading = false;
  private isInitialized = false;

  @Input() codApp: string | null = null;
  @Output() codAppChange = new EventEmitter<string>();

  @Output() selectionChange = new EventEmitter<string | null>();
  @Output() appsLoaded = new EventEmitter<Application[]>();

  @Input() label = "Sélectionner une application";
  @Input() placeholder = "Choisir une application";
  @Input() showLabel = true;
  @Input() disabled = false;
  @Input() width = "300px";
  @Input() id = "appSelector";
  @Input() autoSelectFirst = false;

  selectedCodApp: string | null = null;
  constructor(
    private applicationService: ApplicationService,
    private appContext: ApplicationContextService,
    private cdr: ChangeDetectorRef

  ) {}
  ngOnInit(): void {
    // Get the current context value first
    const contextValue = this.appContext.getSelectedApp();
    if (contextValue) {
      this.selectedCodApp = contextValue;
    }
    // Then load applications
    this.loadApplications();
  }
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private loadApplications(): void {
    if (this.loading) return; // Prevent multiple concurrent calls

    this.loading = true;
    this.applicationService
      .getAll()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (apps) => {
          this.applications = apps || [];

          if (!this.isInitialized) {
            this.initializeSelection();
            this.isInitialized = true;
          }
          this.appsLoaded.emit(this.applications);

        },
        error: (error) => {
          console.error("Error loading applications:", error);
          this.applications = [];
          this.cdr.detectChanges();
          this.loading = false;


        },
        complete: () => {
          this.loading = false;
         this.cdr.detectChanges(); // Force change detection

        },
      });
  }

private initializeSelection(): void {
    // If we already have a selected value from context, keep it
    if (this.selectedCodApp && this.applications.some(app => app.codApp === this.selectedCodApp)) {
      // Value is already set, just ensure it's valid
      return;
    }

    // If there's an input codApp, use it
    if (this.codApp && this.applications.some(app => app.codApp === this.codApp)) {
      this.selectedCodApp = this.codApp;
      // Only set if not null
      if (this.selectedCodApp) {
        this.appContext.setSelectedApp(this.selectedCodApp);
      }
      this.selectionChange.emit(this.selectedCodApp);
      return;
    }

    // If autoSelectFirst is true and no selection exists, select first
    if (this.autoSelectFirst && this.applications.length > 0 && !this.selectedCodApp) {
      this.selectedCodApp = this.applications[0].codApp || null;
      // Only set if not null
      if (this.selectedCodApp) {
        this.appContext.setSelectedApp(this.selectedCodApp);
      }
      this.selectionChange.emit(this.selectedCodApp);
    }
  }
  onSelectionChange(): void {
    // This is called when user manually changes the selection OR clears it
    if (this.isInitialized) {
      if (this.selectedCodApp) {
        // Selection made
        this.appContext.setSelectedApp(this.selectedCodApp);
        this.codAppChange.emit(this.selectedCodApp);
        this.selectionChange.emit(this.selectedCodApp);
      } else {
        // Selection cleared
        this.appContext.clearSelectedApp();
        this.codAppChange.emit('');
        this.selectionChange.emit(null);
      }
    }
  }

}