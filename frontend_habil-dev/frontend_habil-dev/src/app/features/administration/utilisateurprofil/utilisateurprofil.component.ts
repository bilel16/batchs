/**
 * @fileoverview UtilisateurProfilComponent - User Profile Assignment Management
 *
 * This component manages the assignment of security profiles to users within the BNA HABIL system.
 * It provides a comprehensive interface for:
 * - User selection and management
 * - Profile assignment and unassignment with drag-and-drop functionality
 * - Profile filtering and searching
 * - Assignment validation and confirmation
 *
 * @author BNA HABIL Development Team
 * @version 2.0.0
 * @since 2025-10-31
 *
 * @component UtilisateurProfilComponent
 * @selector app-utilisateur-profil
 * @templateUrl ./utilisateurprofil.component.html
 * @styleUrls ./utilisateurprofil.component.scss
 */

// ============================================================================
// ANGULAR CORE IMPORTS
// ============================================================================
import {
  ChangeDetectorRef,
  Component,
  HostListener,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';

// ============================================================================
// ANGULAR CDK IMPORTS
// ============================================================================
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';

// ============================================================================
// RXJS IMPORTS
// ============================================================================
import {
  debounceTime,
  distinctUntilChanged,
  Observable,
  Subject,
  takeUntil,
} from 'rxjs';
import { ActionType } from '../../../core/models/action-type.enum';
import { Application } from '../../../core/models/application';
import { UtilisateurProfil } from '../../../core/models/utilisateurprofil';
import { ProfilService } from '../../../core/services/backend/profil.service';
import { UtilisateurProfilService } from '../../../core/services/backend/utilisateur-profile.service';
import { ApplicationContextService } from '../../../core/services/frontend/ApplicationContextService.service';
import { SharedFrontService } from '../../../core/services/frontend/shared-front.service';
import { TokenStorageService } from '../../../core/services/frontend/token-storage.service';
import { NotificationService } from '../../../core/services/frontend/notification.service';
import { OperationNotificationService, OperationType as OpNotificationType } from '../../../core/services/frontend/operation-notification.service';
import { UserService } from '../../../core/services/backend/user.service';
import { ProfileAssignmentService } from '../../../core/services/backend/ProfileAssignmentService.service';
import { ProfileAssignmentData } from '../../../core/models/profile-assigment';
import {
  UserProfilesAssignmentRequest,
  AssignedProfile,
  formatDateForApi,
} from '../../../core/models/profile-assignment.model';
import {
  BatchAssignmentResult,
  isPartialSuccess,
  isCompleteSuccess,
  isCompleteFailure,
  getFailedItemsArray,
} from '../../../core/models/batch-assignement-result';
import {
  UserPacksAssignmentRequest,
  AssignedPack,
  formatPackDateForApi,
} from '../../../core/models/pack-assignment.model';
import {
  UserProfileUpdateRequest,
  ProfileUpdateResult,
  OperationType,
} from '../../../core/models/profile-operation';
import { PackService, Pack } from '../../../core/services/backend/pack.service';
import { UtilisateurPackService } from '../../../core/services/backend/utilisateur-pack.service';
import { PackProfileService } from '../../administration/pack-profile/services/packprofile.service';
import {
  ProfilePack,
  PackCategory,
  PackStatus,
} from '../../../core/models/profile-pack';
import { ConfirmationService } from 'primeng/api';
import {
  CloneableProfile,
  CloneTargetUser,
  CloneOptions,
  CloneResult,
} from './components/profile-clone/profile-clone.component';
import {
  AssignmentResults,
  AssignmentResultItem,
} from './components/confirmation-step/confirmation-step.component';
import { ConfirmationStepComponent } from './components/confirmation-step/confirmation-step.component';

// ============================================================================
// APPLICATION CORE IMPORTS
// ============================================================================

// Models

// Add these to your imports section
// Backend Services

// Frontend Services

// ============================================================================
// INTERFACES AND TYPE DEFINITIONS
// ============================================================================
export interface StructureOption {
  id: number;
  label: string;
  typeCode: number;
  typeName: string;
}

export interface StructureTypeOption {
  code: number;
  label: string;
}
/**
 * Unified profile interface that combines both available and assigned profile data
 * This interface standardizes profile representation across different API responses
 */
export interface UnifiedProfile {
  // Core profile identification
  /** Profile code identifier */
  codPflPfl: string;
  /** Profile name/label */
  libpflpfl: string;
  /** Alternative profile label */
  libelle?: string;

  // Profile classification and hierarchy
  /** Profile category code */
  codCatpPfl?: string;
  /** Profile level code */
  codNivhPfl?: string;
  /** Access start time */
  libhdebpfl?: string;
  /** Access end time */
  libhfinpfl?: string;
  /** Working days only flag */
  boolJouvPfl?: string;
  /** Profile status flag */
  boolEtatPfl?: string;
  /** Application code */
  codAppApp?: string;

  // Assignment metadata (only for assigned profiles)
  /** User matricule */
  numMatrUser?: string;
  /** Assignment start date */
  datFadhUtpr?: string;
  /** Assignment end date */
  datdadhutpr?: string;
  /** Assignment status */
  boolEtatUtpr?: number;

  // Computed display properties
  /** Current assignment status */
  assignmentStatus: any;
  /** Whether the profile is currently active */
  isActive: boolean;
  /** Display-friendly profile name */
  displayName: string;
  /** Human-readable category name */
  categoryName: string;
  /** Formatted access hours */
  accessHours: string;
  /** Formatted assignment date range */
  assignmentDates?: string;
}

/**
 * Profile filter configuration object
 */
interface ProfileFilters {
  /** Search term for profile name/code */
  searchTerm: string;
  /** Selected level filter */
  selectedLevel: string | null;
  /** Selected category filter */
  selectedCategory: string | null;
  /** Selected status filter */
  selectedStatus: number | null;
}

/**
 * Selection tracking configuration
 */
interface SelectionState {
  /** Set of selected available profile codes */
  available: Set<string>;
  /** Set of selected assigned profile codes */
  assigned: Set<string>;
}

// Add this interface after your existing interfaces
interface ProfileChangeTracking {
  toAdd: Set<string>; // Profiles to be added (not in original)
  toRevoke: Set<string>; // Profiles to be removed (was in original)
  toUpdate: Map<string, any>; // Profiles with changes (dates/status)
}
// ============================================================================
// COMPONENT DEFINITION
// ============================================================================

@Component({
  selector: 'app-utilisateur-profil',
  templateUrl: './utilisateurprofil.component.html',
  styleUrls: ['./utilisateurprofil.component.scss'],
  standalone: false,
})
export class UtilisateurProfilComponent implements OnInit, OnDestroy {
  // ============================================================================
  // VIEW CHILD REFERENCES
  // ============================================================================

  /** Reference to confirmation step component for triggering scroll */
  @ViewChild('confirmationStep')
  confirmationStepRef!: ConfirmationStepComponent;

  // ============================================================================
  // STEPPER AND WORKFLOW PROPERTIES
  // ============================================================================

  /** Workflow steps configuration */
  steps = [
    { value: 1, icon: 'pi pi-user', label: 'Sélectionner personnel' },
    { value: 2, icon: 'pi pi-shield', label: 'Sélectionner les rôles' },
    { value: 3, icon: 'pi pi-check', label: 'Confirmer' },
  ];

  /** Current active step in the workflow */
  activeStep: number = 1;

  // ============================================================================
  // USER SELECTION AND MANAGEMENT PROPERTIES
  // ============================================================================

  /** Currently selected individual user */
  selectedUser: any = null;

  /** Array of selected users for bulk operations */
  selectedUsers: any[] = [];

  /** List of all managed users under current manager */
  managedUsers: any[] = [];

  /** Full list of all users for profile cloning (unpaginated, unfiltered) */
  allUsersForCloning: any[] = [];

  /** Current manager's matricule from authentication */
  currentManagerMatricule: string = '';

  /** User search and filter value */
  filterValue: string = '';

  /** User suggestions for autocomplete */
  userSuggestions: any[] = [];

  /** Subject for debouncing user search */
  private searchTerms = new Subject<string>();

  /** Flag indicating if managed users are being loaded */
  isLoadingManagedUsers = false;

  // ============================================================================
  // BULK OPERATIONS AND VIEW CONTROL PROPERTIES
  // ============================================================================

  /** Whether to show bulk action controls */
  showBulkActions = false;

  /** Selected profile for bulk assignment */
  selectedBulkProfile: any = null;

  /** Flag for bulk assignment mode */
  bulkAssignmentMode = false;

  /** Whether to show assignment statistics */
  showStatistics = false;

  /** Assignment statistics data */
  assignmentStatistics: any = null;

  // ============================================================================
  // ADMIN PAGINATION PROPERTIES (for SUPER_ADMIN_HABIL or HABIL_RH roles)
  // ============================================================================

  /** Flag indicating if current user has admin role */
  isAdminUser: boolean = false;

  /** Current page for admin pagination (0-indexed) */
  adminCurrentPage: number = 0;

  /** Page size for admin pagination */
  adminPageSize: number = 20;

  /** Total pages for admin pagination */
  adminTotalPages: number = 0;

  /** Total elements for admin pagination */
  adminTotalElements: number = 0;

  /** Is first page flag */
  adminIsFirstPage: boolean = true;

  /** Is last page flag */
  adminIsLastPage: boolean = false;

  // ============================================================================
  // ADMIN FILTER PROPERTIES
  // ============================================================================
  /** Admin search term (search by matricule, name, email) */
  adminSearchTerm: string = '';

  /** Admin status filter (true = active, false = inactive, null = all) */
  adminStatusFilter: boolean | null = null;

  /** Admin structure filter (multiple structure IDs for multi-select) */
  adminStructureFilter: number[] = [];

  /** Admin structure type filter (structure type code) */
  adminStructureTypeFilter: number | null = null;

  /** Admin sort field */
  adminSortBy: string = 'mat';

  /** Admin sort direction */
  adminSortDirection: 'ASC' | 'DESC' = 'ASC';

  /** Available structure options for filter dropdown */
  structureOptions: { label: string; value: number }[] = [];

  /** All structure options (unfiltered, for reference) */
  allStructureOptions: { label: string; value: number }[] = [];

  /** Available structure type options for filter dropdown */
  structureTypeOptions: { label: string; value: number }[] = [];

  /** Status filter options */
  adminStatusOptions = [
    { label: 'Tous', value: null },
    { label: 'Actif', value: true },
    { label: 'Inactif', value: false }
  ];

  /** Sort field options */
  adminSortOptions = [
    { label: 'Matricule', value: 'mat' },
    { label: 'Nom', value: 'nom_prenom' },
    { label: 'Structure', value: 'cod_strc_strc' },
    { label: 'Statut', value: 'cod_stat_user' }
  ];

  // ============================================================================
  // PROFILE DATA AND STATE MANAGEMENT
  // ============================================================================

  /** Array of available profiles for assignment */
  availableProfiles: UnifiedProfile[] = [];

  /** Array of currently assigned profiles */
  assignedProfiles: UnifiedProfile[] = [];

  /** Filtered view of available profiles */
  filteredAvailableProfiles: UnifiedProfile[] = [];

  /** Filtered view of assigned profiles */
  filteredAssignedProfiles: UnifiedProfile[] = [];

  // ============================================================================
  // PROFILE FILTERING AND SEARCH PROPERTIES
  // ============================================================================

  /** Filter configuration for available profiles */
  availableProfileFilters: ProfileFilters = {
    searchTerm: '',
    selectedLevel: null,
    selectedCategory: null,
    selectedStatus: null,
  };

  /** Filter configuration for assigned profiles */
  assignedProfileFilters: ProfileFilters = {
    searchTerm: '',
    selectedLevel: null,
    selectedCategory: null,
    selectedStatus: null,
  };

  /** Available filter options for profile levels */
  levelFilterOptions: any[] = [];

  /** Available filter options for profile categories */
  categoryFilterOptions: any[] = [];
  /** Available filter options for profile status */
  statusFilterOptions = [
    { label: 'Actif', value: 1 },
    { label: 'Inactif', value: 0 },
  ];

  // ============================================================================
  // DRAG-AND-DROP AND SELECTION TRACKING
  // ============================================================================

  /** Current drag operation state */
  dragState: 'idle' | 'dragging' | 'success' = 'idle';

  /** Profile selection tracking */
  selectedProfiles: SelectionState = {
    available: new Set(),
    assigned: new Set(),
  };

  /** Tracking of newly moved profiles needing validation */
  newlyMovedProfiles: SelectionState = {
    available: new Set(),
    assigned: new Set(),
  };

  /** Original profile positions for reset functionality */
  originalPositions: SelectionState = {
    available: new Set(),
    assigned: new Set(),
  };

  // ============================================================================
  // DIALOG AND FORM MANAGEMENT
  // ============================================================================

  /** Profile assignment dialog visibility */
  profileAssignmentDialog = false;

  /** Profile information dialog visibility */
  profileInfoDialog = false;

  /** Selected profile for information display */
  selectedProfileInfo: UnifiedProfile | null = null;

  /** Pending profile assignments awaiting confirmation */
  pendingProfileAssignments: ProfileAssignmentData[] = [];

  /** Current assignment form data */
  currentAssignmentForm: ProfileAssignmentData = {
    profileCode: '',
    dateDebut: new Date(),
    dateFin: null,
    etat: 1,
  };

  // ============================================================================
  // PROFILE RESTORATION PROPERTIES
  // ============================================================================

  /** Profile restore dialog visibility */
  profileRestoreDialog = false;

  /** Selected profile for restoration */
  selectedProfileToRestore: UnifiedProfile | null = null;
  /** Restoration form data */
  restoreProfileForm: {
    dateDebut: Date | null;
    dateFin: Date | null;
  } = {
    dateDebut: new Date(), // Default to current day
    dateFin: null,
  };

  // ============================================================================
  // PACK-RELATED PROPERTIES
  // ============================================================================

  /** Available packs for assignment */
  availablePacks: ProfilePack[] = [];

  /** Packs currently assigned to the user */
  assignedPacks: ProfilePack[] = [];

  /** Filtered view of available packs */
  filteredAvailablePacks: ProfilePack[] = [];

  /** Filtered view of assigned packs */
  filteredAssignedPacks: ProfilePack[] = [];

  /** Loading state for packs */
  loadingPacks = false;

  /** Flag to indicate user packs need to be loaded after available packs finish loading */
  pendingUserPacksLoad = false;

  /** Packs pending assignment (for confirmation step) */
  packsToAssign: ProfilePack[] = [];

  /** Flag to track if we're in batch pack assignment mode */
  isBatchPackAssignment = false;

  // ============================================================================
  // PROFILE CLONING PROPERTIES
  // ============================================================================

  /** Profile clone dialog visibility */
  profileCloneDialogVisible = false;

  /** Profiles for cloning (from source user) */
  profilesForCloning: CloneableProfile[] = [];

  /** Clone operation in progress */
  cloningInProgress = false;

  /** Clone progress percentage */
  cloneProgress = 0;

  /** Clone results */
  cloneResults: CloneResult[] = [];

  /** Assignment results for confirmation step display */
  assignmentResults: AssignmentResults | null = null;

  /** Assignment in progress flag */
  isAssigning: boolean = false;

  /** Assignment progress percentage */
  assignmentProgress: number = 0;

  // ============================================================================
  // LEGACY DIALOG AND CRUD PROPERTIES
  // ============================================================================

  /** Legacy user-profile dialog visibility */
  utilisateurProfilDialog = false;

  /** Legacy user-profile model for CRUD operations */
  utilisateurProfil: UtilisateurProfil = {
    codPflPfl: '',
    numMatrUser: '',
    datFadhUtpr: undefined,
    datdadhutpr: undefined,
    boolEtatUtpr: 1,
  };

  /** Legacy profile list for dropdown */
  listRols: any[] = [];

  /** Legacy roles array */
  roles: any[] = [];

  /** Legacy user roles array */
  userRoles: any[] = [];

  /** Form submission state */
  submitted = false;

  /** Current CRUD action type */
  action!: ActionType;

  /** Action type enum reference */
  public actionType = ActionType;

  // ============================================================================
  // APPLICATION CONTEXT AND LEGACY PROPERTIES
  // ============================================================================

  /** Currently selected application code */
  selectedAppCode: string = '';

  /** Selected application for legacy operations */
  selectedApplication: any = null;

  /** Selected application code for legacy operations */
  selectedAppX!: string;

  /** Available applications list */
  applications: Application[] = [];

  /** Available applications for current user */
  availableApplicationsForUser: any[] = [];

  /** Legacy users list */
  users: any[] = [];

  /** Filtered users array */
  filteredUsers: any[] = [];

  /** Flag to show no application message */
  showNoAppMessage = true;

  // ============================================================================
  // PAGINATION AND TABLE MANAGEMENT
  // ============================================================================

  /** Legacy user-profile list */
  utilisateurProfils: UtilisateurProfil[] = [];

  /** Selected user-profiles for table operations */
  selectedUtilisateurProfils: UtilisateurProfil[] = [];

  /** Current page number */
  page = 0;

  /** Page size for pagination */
  size = 10;

  /** Total number of elements */
  totalElements = 0;

  /** Global filter value for table */
  globalFilterValue: string = '';

  /** Loading state indicator */
  loading: boolean = false;

  /** Last lazy loading event */
  lastLazyEvent: any;

  /** Last sort field used */
  lastSortField: string = '';

  /** Last sort order (1 for ASC, -1 for DESC) */
  lastSortOrder: number = 1;

  /** Mapping of frontend sort fields to backend fields */
  private sortFieldMapping: { [key: string]: string } = {
    datFadhUtpr: 'DAT_FADH_UTPR',
    datdadhutpr: 'DAT_DADH_UTPR',
    numMatrUser: 'NUM_MATR_USER',
    codPflPfl: 'COD_PFL_PFL',
    boolEtatUtpr: 'BOOL_ETAT_UTPR',
  };

  // ============================================================================
  // LIFECYCLE AND CLEANUP
  // ============================================================================

  /** Subject for component cleanup */
  private destroy$ = new Subject<void>();

  /** Keyboard event listener for cleanup */
  private keyboardListener?: (event: KeyboardEvent) => void; // ============================================================================
  // CONSTRUCTOR AND DEPENDENCY INJECTION
  // ============================================================================

  /**  * UtilisateurProfilComponent constructor
   * Injects all required services for profile assignment management
   */ constructor(
    private utilisateurProfilService: UtilisateurProfilService,
    private profilService: ProfilService,
    private sharedService: SharedFrontService,
    private appContext: ApplicationContextService,
    private userService: UserService,
    private profileAssignmentService: ProfileAssignmentService,
    private tokenStorage: TokenStorageService,
    private cdr: ChangeDetectorRef,
    private packService: PackService,
    private utilisateurPackService: UtilisateurPackService,
    private packProfileService: PackProfileService,
    private confirmationService: ConfirmationService,
    private notificationService: NotificationService,
    private operationNotificationService: OperationNotificationService
  ) {}

  // ============================================================================
  // LIFECYCLE METHODS
  // ============================================================================
  /**
   * Component initialization
   * Sets up subscriptions, loads initial data, and configures event listeners
   */
  ngOnInit(): void {
    // Initialize manager context
    this.currentManagerMatricule = this.getCurrentManagerMatricule();

    // Initialize profile arrays
    this.filteredAvailableProfiles = [];
    this.filteredAssignedProfiles = [];
    
    // Load structure filter options from backend
    this.loadStructureFilterOptions();
    
    // Load managed users for the current manager
    this.loadManagedUsers();

    // Load available packs
    this.loadAvailablePacks();

    // Set up user search debouncing
    this.setupUserSearchSubscription();

    // Set up admin search debouncing (for server-side filtering)
    this.initAdminSearchDebounce();

    // Set up application context subscription
    this.setupApplicationContextSubscription();

    // Initialize keyboard shortcuts
    this.addKeyboardListeners();
  }

  /**
   * Component cleanup
   * Unsubscribes from observables and removes event listeners
   */
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();

    // Clean up keyboard listener
    if (this.keyboardListener) {
      document.removeEventListener('keydown', this.keyboardListener);
    }
  }

  // ============================================================================
  // INITIALIZATION HELPER METHODS
  // ============================================================================

  /**
   * Sets up user search subscription with debouncing
   * @private
   */
  private setupUserSearchSubscription(): void {
    this.searchTerms
      .pipe(
        debounceTime(300), // Wait 300ms after user stops typing
        distinctUntilChanged(),
        takeUntil(this.destroy$)
      )
      .subscribe((term: string) => {
        this.userSuggestions = this.filterManagedUsers(term);
        console.log('User suggestions updated:', this.userSuggestions);
      });
  }

  /**
   * Sets up application context subscription
   * @private
   */
  private setupApplicationContextSubscription(): void {
    this.appContext.selectedApp$
      .pipe(distinctUntilChanged(), takeUntil(this.destroy$))
      .subscribe((appCode) => {
        this.selectedAppCode = appCode || '';
        console.log('Current global app:', appCode);
      });
  }

  /**
   * Adds keyboard event listeners for shortcuts
   * @private
   */
  private addKeyboardListeners(): void {
    this.keyboardListener = (event: KeyboardEvent) => {
      // Ctrl+Z or Cmd+Z for reset
      if (
        (event.ctrlKey || event.metaKey) &&
        event.key === 'z' &&
        !event.shiftKey
      ) {
        if (this.getNewlyMovedCount() > 0) {
          event.preventDefault();
          this.resetAllModifications();
        }
      }
      // Ctrl+S or Cmd+S for save
      if ((event.ctrlKey || event.metaKey) && event.key === 's') {
        if (this.getNewlyMovedCount() > 0) {
          event.preventDefault();
        }
      }
    };
    document.addEventListener('keydown', this.keyboardListener);
  }
  /**
   * Gets current manager matricule from token storage
   * @private
   * @returns Current manager's matricule
   */
  private getCurrentManagerMatricule(): string {
    return this.tokenStorage.getCurrentUserMatricule();
  }

  /**
   * Checks if current user has admin role (SUPER_ADMIN_HABIL or HABIL_RH)
   * @private
   * @returns True if user has admin access
   */
  private checkAdminRole(): boolean {
    return this.tokenStorage.hasAdminRole();
  }

  // ============================================================================
  // USER MANAGEMENT AND SELECTION METHODS
  // ============================================================================

  /**
   * Loads users based on user role:
   * - Admin users (SUPER_ADMIN_HABIL, HABIL_RH): Load all personnel with pagination
   * - Other users: Load managed users only (existing implementation)
   * @protected
   */
  protected loadManagedUsers(): void {
    // Check admin role on first load
    this.isAdminUser = this.checkAdminRole();

    if (this.isAdminUser) {
      // Admin: Load all personnel with pagination
      this.loadAllPersonnelPaginated();
    } else {
      // Non-admin: Load managed users only (existing implementation)
      this.loadManagedUsersOnly();
    }
  }
  /**
   * Loads all personnel with pagination and filters (Admin access only)
   * GET /api/personnel/page?page={page}&size={size}&filters...
   * @param page Page number (0-indexed), defaults to current page
   */  protected loadAllPersonnelPaginated(page: number = this.adminCurrentPage): void {
    this.isLoadingManagedUsers = true;
    this.adminCurrentPage = page;

    // Normalize search term: trim and replace multiple spaces with single space
    const normalizedSearch = this.adminSearchTerm
      ? this.adminSearchTerm.trim().replace(/\s+/g, ' ')
      : undefined;

    // Build filter parameters
    const filters = {
      search: normalizedSearch,
      codStatUser: this.adminStatusFilter !== null ? this.adminStatusFilter : undefined,
      codStrcStrc: this.adminStructureFilter.length > 0 ? this.adminStructureFilter : undefined,
      codTstrTstr: this.adminStructureTypeFilter !== null ? this.adminStructureTypeFilter : undefined,
      sortBy: this.adminSortBy,
      sortDirection: this.adminSortDirection
    };

    this.userService
      .getAllPersonnelPaginated(page, this.adminPageSize, filters)
      .subscribe({
        next: (response) => {
          // Map response to managedUsers format
          this.managedUsers = response.content.map((user: any) => ({
            ...user,
            mat: user.mat || user.matricule,
            numMatrUser: user.mat || user.matricule,
            nom_prenom: user.nom_prenom || `${user.prenom || ''} ${user.nom || ''}`.trim(),
            displayName: user.nom_prenom || user.mat || user.matricule,
            label: `${user.mat || user.matricule} - ${user.nom_prenom || 'N/A'}`,
            selected: false,
            isActive: user.cod_stat_user === 1 || user.cod_stat_user === true,
            structureId: user.cod_strc_strc,
            cod_strc_strc: user.cod_strc_strc,
            cod_stat_user: user.cod_stat_user,
          }));          // Update pagination state
          this.adminTotalPages = response.totalPages;
          this.adminTotalElements = response.totalElements;
          this.adminIsFirstPage = response.first;
          this.adminIsLastPage = response.last;
          this.adminCurrentPage = response.number || page;

          this.isLoadingManagedUsers = false;
          this.cdr.detectChanges();
        },
        error: (error) => {
          console.error('Error loading paginated personnel:', error);
          this.sharedService.handleError(
            error,
            'Erreur lors du chargement du personnel'
          );
          this.isLoadingManagedUsers = false;
        },
      });
  }

  /**
   * Loads managed users for the current manager (Non-admin users)
   * GET /api/profiles/managed-users/details
   * @private
   */
  private loadManagedUsersOnly(): void {
    if (!this.currentManagerMatricule) return;

    this.isLoadingManagedUsers = true;
    this.profileAssignmentService
      .getManagedUsersWithDetails(this.currentManagerMatricule)
      .subscribe({
        next: (users) => {
          this.managedUsers = users.map((user) => ({
            ...user,
            numMatrUser: user.mat,
            displayName: user.nom_prenom || user.mat,
            label: `${user.mat} - Structure ${user.cod_strc_strc}`,
            selected: false,
            isActive: user.cod_stat_user,
            structureId: user.cod_strc_strc,
          }));
          this.isLoadingManagedUsers = false;
        },
        error: (error) => {
          console.error('Error loading managed users:', error);
          this.sharedService.handleError(
            error,
            'Erreur lors du chargement des utilisateurs gérés'
          );
          this.isLoadingManagedUsers = false;
        },
      });
  }

  /**
   * Navigate to next page (Admin pagination)
   */
  nextPage(): void {
    if (!this.adminIsLastPage && this.isAdminUser) {
      this.loadAllPersonnelPaginated(this.adminCurrentPage + 1);
    }
  }

  /**
   * Navigate to previous page (Admin pagination)
   */
  previousPage(): void {
    if (!this.adminIsFirstPage && this.isAdminUser) {
      this.loadAllPersonnelPaginated(this.adminCurrentPage - 1);
    }
  }

  /**
   * Navigate to a specific page (Admin pagination)
   * @param page Page number (0-indexed)
   */
  goToPage(page: number): void {
    if (this.isAdminUser && page >= 0 && page < this.adminTotalPages) {
      this.loadAllPersonnelPaginated(page);
    }
  }
  /**
   * Change page size and reload data (Admin pagination)
   * @param size New page size
   */
  changePageSize(size: number): void {
    if (this.isAdminUser) {
      this.adminPageSize = size;
      this.adminCurrentPage = 0; // Reset to first page
      this.loadAllPersonnelPaginated(0);
    }
  }

  // ============================================================================
  // ADMIN FILTER METHODS
  // ============================================================================

  /** Subject for debouncing admin search */
  private adminSearchSubject = new Subject<string>();

  /**
   * Handle admin search term change (with debounce)
   * @param searchTerm The search term entered by the user
   */
  onAdminSearchChange(searchTerm: string): void {
    this.adminSearchTerm = searchTerm;
    this.adminSearchSubject.next(searchTerm);
  }

  /**
   * Initialize admin search debounce subscription
   * Called from ngOnInit
   */
  private initAdminSearchDebounce(): void {
    this.adminSearchSubject
      .pipe(
        debounceTime(400),
        distinctUntilChanged(),
        takeUntil(this.destroy$)
      )
      .subscribe(() => {
        this.adminCurrentPage = 0; // Reset to first page on search
        this.loadAllPersonnelPaginated(0);
      });
  }

  /**
   * Handle admin status filter change
   * @param status The status filter value (true = active, false = inactive, null = all)
   */
  onAdminStatusChange(status: boolean | null): void {
    this.adminStatusFilter = status;
    this.adminCurrentPage = 0;
    this.loadAllPersonnelPaginated(0);
  }
  /**
   * Handle admin structure filter change (multi-select)
   * @param structureIds Array of selected structure IDs
   */
  onAdminStructureChange(structureIds: number[]): void {
    this.adminStructureFilter = structureIds || [];
    this.adminCurrentPage = 0;
    this.loadAllPersonnelPaginated(0);
  }

  /**
   * Handle admin structure type filter change
   * When a type is selected, optionally filter structures by that type
   * @param structureTypeId The structure type ID to filter by
   */
  onAdminStructureTypeChange(structureTypeId: number | null): void {
    this.adminStructureTypeFilter = structureTypeId;
    
    // Optional: Filter structure options by selected type (cascading dropdown)
    if (structureTypeId !== null) {
      this.userService.getStructuresByType(structureTypeId).subscribe({
        next: (options) => {
          this.structureOptions = options.map(opt => ({
            label: `${opt.label} (${opt.typeName})`,
            value: opt.id
          }));
          console.log(`✅ Filtered structures for type ${structureTypeId}:`, this.structureOptions.length);
        },
        error: (error) => {
          console.error('Error filtering structures by type:', error);
        }
      });
    } else {
      // Reset to all structures if no type selected
      this.structureOptions = [...this.allStructureOptions];
    }
    
    this.adminCurrentPage = 0;
    this.loadAllPersonnelPaginated(0);
  }

  /**
   * Handle admin sort change
   * @param sortConfig Object containing sortBy and sortDirection
   */
  onAdminSortChange(sortConfig: { sortBy: string; sortDirection: 'ASC' | 'DESC' }): void {
    this.adminSortBy = sortConfig.sortBy;
    this.adminSortDirection = sortConfig.sortDirection;
    this.loadAllPersonnelPaginated(this.adminCurrentPage);
  }

  /**
   * Reset all admin filters to default values
   */
  resetAdminFilters(): void {
    this.adminSearchTerm = '';
    this.adminStatusFilter = null;
    this.adminStructureFilter = [];
    this.adminStructureTypeFilter = null;
    // Reset structure options to all
    this.structureOptions = [...this.allStructureOptions];
    this.adminSortBy = 'mat';
    this.adminSortDirection = 'ASC';
    this.adminCurrentPage = 0;
    this.loadAllPersonnelPaginated(0);
  }
  /**
   * Loads structure and structure type filter options from backend API
   * Called on component initialization
   * @private
   */  private loadStructureFilterOptions(): void {
    // Load structure options
    this.userService.getStructureOptions().subscribe({
      next: (options) => {
        this.allStructureOptions = options.map(opt => ({
          label: `${opt.label} (${opt.typeName})`,
          value: opt.id
        }));
        this.structureOptions = [...this.allStructureOptions];
        console.log('✅ Structure options loaded:', this.structureOptions.length);
      },
      error: (error) => {
        console.error('❌ Error loading structure options:', error);
        this.structureOptions = [];
        this.allStructureOptions = [];
        
        // Show user-friendly warning
        this.notificationService.addAlert(
          'Filtres de structure indisponibles', 
          'Les filtres de structure ne peuvent pas être chargés pour le moment. Veuillez contacter l\'administrateur système.',
          'warn',
          'system'
        );
      }
    });

    // Load structure type options
    this.userService.getStructureTypeOptions().subscribe({
      next: (options) => {
        this.structureTypeOptions = options.map(opt => ({
          label: opt.label,
          value: opt.code
        }));
        console.log('✅ Structure type options loaded:', this.structureTypeOptions.length);
      },
      error: (error) => {
        console.error('❌ Error loading structure type options:', error);
        this.structureTypeOptions = [];
        
        // Show user-friendly warning
        this.notificationService.addAlert(
          'Filtres de type structure indisponibles', 
          'Les filtres de type de structure ne peuvent pas être chargés pour le moment. Veuillez contacter l\'administrateur système.',
          'warn',
          'system'
        );
      }
    });
  }

  /**
   * Updates the array of selected users based on selection state
   * @protected
   */
  protected updateSelectedUsers(): void {
    this.selectedUsers = this.managedUsers.filter((u) => u.selected);
    this.showBulkActions = this.selectedUsers.length > 1;

    // Auto-load profiles for single user selection
    if (this.selectedUsers.length === 1) {
      this.loadUserProfiles(this.selectedUsers[0]);
      // Note: loadUserPacks() is now called inside loadUserProfiles()
    } else {
      this.selectedUser = null;
    }
  }

  /**
   * Filters managed users based on search term (local filtering)
   * @private
   * @param term Search term
   * @returns Filtered user array
   */
  private filterManagedUsers(term: string): any[] {
    if (!term || term.trim().length < 1) {
      return [];
    }

    const searchTerm = term.toLowerCase().trim();
    const filtered = this.managedUsers.filter((user) => {
      const matricule = (user.mat || '').toString().toLowerCase();
      return matricule.includes(searchTerm);
    });

    return filtered.slice(0, 10); // Return max 10 suggestions
  }

  /**
   * Gets filtered users based on current filter value
   * @returns Array of filtered users
   */
  getFilteredUsers(): any[] {
    if (!this.filterValue) {
      return this.managedUsers;
    }
    const searchTerm = this.filterValue.toLowerCase();
    return this.managedUsers.filter(
      (user) =>
        user.mat.toLowerCase().includes(searchTerm) ||
        user.cod_strc_strc.toString().includes(searchTerm)
    );
  }

  /**
   * Checks if all filtered users are selected
   * @returns True if all filtered users are selected
   */
  areAllFilteredUsersSelected(): boolean {
    const filtered = this.getFilteredUsers();
    return filtered.length > 0 && filtered.every((u) => u.selected);
  }

  /**
   * Checks if some but not all filtered users are selected
   * @returns True if some users are selected
   */
  areSomeFilteredUsersSelected(): boolean {
    const filtered = this.getFilteredUsers();
    return (
      filtered.some((u) => u.selected) && !this.areAllFilteredUsersSelected()
    );
  }

  /**
   * Clears user selection and returns to user list
   */
  clearUserSelection(): void {
    this.selectedUser = null;
    this.selectedUsers = [];
    this.bulkAssignmentMode = false;

    // Clear all user selections
    this.managedUsers.forEach((user) => (user.selected = false));

    // Clear profile data
    this.availableProfiles = [];
    this.assignedProfiles = [];

    // Clear all tracking
    this.clearAllSelectionTracking();
  }

  /**
   * Shows all managed users when autocomplete field is focused
   */
  showAllManagedUsers(): void {
    this.userSuggestions = this.managedUsers.slice(0, 20);
  }

  /**
   * Gets structure name for display
   * @param structureId Structure identifier
   * @returns Formatted structure name
   */
  getStructureName(structureId: number): string {
    return `Structure ${structureId}`;
  }

  /**
   * Selects a user for step-based workflow and loads their profiles
   * @param user User object to select
   */
  selectUserForStep(user: any): void {
    this.selectedUser = user;
    this.onUserSelect({ value: user });
  }

  // ============================================================================
  // USER SEARCH AND AUTOCOMPLETE METHODS
  // ============================================================================

  /**
   * Searches users by matricule (used for autocomplete)
   * @param term Search term
   * @returns Observable of user array
   */
  searchUsers(term: string): Observable<any[]> {
    return this.userService.searchByMatricule(term);
  }

  /**
   * Handles search term changes for autocomplete
   * @param event Search event containing query
   */
  onSearchChange(event: any): void {
    const query = event.query;
    if (query && query.length >= 0) {
      this.searchTerms.next(query);
    } else {
      this.userSuggestions = [];
    }
  }

  /**
   * Handles user selection from autocomplete
   * @param event Selection event containing selected user
   */
  onUserSelect(event: any): void {
    this.selectedUser = event.value;
    const matricule = this.selectedUser.numMatrUser || this.selectedUser.mat;
    if (!matricule) return;
    console.log('thhhhhe user ', this.selectedUser);
    this.loadUserPacks();
    // Clear previous selections
    this.clearAllSelectionTracking();

    this.loading = true;

    // Load user's current profiles
    this.utilisateurProfilService
      .getUserProfiles(this.selectedAppCode, matricule)
      .subscribe({
        next: (res: any) => {
          this.assignedProfiles = (res.data || []).map((item: any) =>
            this.transformAssignedProfile(item)
          );
          this.trackOriginalPositions();

          // Load all available profiles and filter out assigned ones
          this.loadAvailableProfiles();
        },
        error: (error) => {
          this.loading = false;
          this.sharedService.handleError(
            error,
            'Erreur lors du chargement des profils utilisateur.'
          );
        },
      });
  }

  /**
   * Loads available profiles for assignment
   * @private
   */
  private loadAvailableProfiles(): void {
    this.profilService.getProfilList(this.selectedAppCode).subscribe({
      next: (all: any) => {
        this.availableProfiles = (all.data || all)
          .filter(
            (p: any) =>
              !this.assignedProfiles.some(
                (a: any) => a.codPflPfl === p.codPflPfl
              )
          )
          .map((profile: any) => this.transformAvailableProfile(profile));

        this.trackOriginalPositions();
        this.initializeProfileFilterOptions();
        this.applyProfileFilters();

        this.loading = false;
      },
      error: (error) => {
        this.loading = false;
        this.sharedService.handleError(
          error,
          'Erreur lors du chargement des profils disponibles.'
        );
      },
    });
  } // ============================================================================
  // STEPPER AND WORKFLOW METHODS
  // ============================================================================

  /**
   * Gets appropriate icon for each step based on completion state
   * @param step Step configuration object
   * @param value Current step value
   * @returns Icon class string
   */
  getStepIcon(step: any, value: number): string {
    const isLastStep = step.value === this.steps.length;

    // Completed previous steps
    if (!isLastStep && value < this.activeStep) {
      return 'pi pi-check-circle';
    }

    // Final step completed
    if (isLastStep && this.activeStep > this.steps.length) {
      return 'pi pi-check';
    }
    return step.icon;
  }

  // ============================================================================
  // PROFILE LOADING AND DATA MANAGEMENT METHODS
  // ============================================================================

  /**
   * Loads profiles for a specific user
   * @param user User object to load profiles for
   */
  loadUserProfiles(user: any): void {
    this.selectedUser = user;
    this.selectedUsers = [user];
    this.bulkAssignmentMode = false;

    const matricule = user.mat;
    this.loading = true;

    // Clear previous selections
    this.clearAllSelectionTracking();

    // Load user's current profiles
    this.utilisateurProfilService
      .getUserProfiles(this.selectedAppCode, matricule)
      .subscribe({
        next: (res: any) => {
          this.assignedProfiles = (res.data || []).map((item: any) =>
            this.transformAssignedProfile(item)
          );
          this.trackOriginalPositions();
        },
        error: (error) => {
          this.loading = false;
          this.sharedService.handleError(
            error,
            'Erreur lors du chargement des profils utilisateur.'
          );
        },
      });
  }


  /**
   * Transforms assigned profile data to unified model
   * @private
   * @param item Raw assigned profile data
   * @returns Unified profile object
   */
  private transformAssignedProfile(item: any): UnifiedProfile {
    const profile = item.profil || {};

    return {
      // Core profile data
      codPflPfl: item.codPflPfl,
      libpflpfl: profile.libpflpfl || item.codPflPfl,
      libelle: profile.libpflpfl || item.codPflPfl,

      // Profile details from nested profil object
      codCatpPfl: profile.codCatpPfl,
      codNivhPfl: profile.codNivhPfl,
      libhdebpfl: profile.libhdebpfl,
      libhfinpfl: profile.libhfinpfl,
      boolJouvPfl: profile.boolJouvPfl,
      boolEtatPfl: profile.boolEtatPfl,
      codAppApp: profile.codAppApp,

      // Assignment data
      numMatrUser: item.numMatrUser,
      datFadhUtpr: item.datFadhUtpr,
      datdadhutpr: item.datdadhutpr,
      boolEtatUtpr: item.boolEtatUtpr,

      // Computed properties
      assignmentStatus: this.getAssignmentStatus(
        item.datFadhUtpr,
        item.datdadhutpr
      ),
      isActive: item.boolEtatUtpr === 1,

      // Display properties
      displayName: profile.libpflpfl || item.codPflPfl,
      categoryName: this.getCategoryName(profile.codCatpPfl),
      accessHours: this.formatAccessHours(
        profile.libhdebpfl,
        profile.libhfinpfl
      ),
      assignmentDates: this.formatAssignmentDates(
        item.datdadhutpr,
        item.datFadhUtpr
      ),
    };
  }

  /**
   * Transforms available profile data to unified model
   * @private
   * @param profile Raw available profile data
   * @returns Unified profile object
   */
  private transformAvailableProfile(profile: any): UnifiedProfile {
    return {
      // Core profile data
      codPflPfl: profile.codPflPfl,
      libpflpfl: profile.libpflpfl || profile.libelle || profile.codPflPfl,
      libelle: profile.libelle || profile.libpflpfl || profile.codPflPfl,

      // Profile details
      codCatpPfl: profile.codCatpPfl,
      codNivhPfl: profile.codNivhPfl,
      libhdebpfl: profile.libhdebpfl,
      libhfinpfl: profile.libhfinpfl,
      boolJouvPfl: profile.boolJouvPfl,
      boolEtatPfl: profile.boolEtatPfl,
      codAppApp: profile.codAppApp,

      // No assignment data for available profiles
      numMatrUser: undefined,
      datFadhUtpr: undefined,
      datdadhutpr: undefined,
      boolEtatUtpr: undefined,

      // Computed properties
      assignmentStatus: 'available',
      isActive: true,

      // Display properties
      displayName: profile.libpflpfl || profile.libelle || profile.codPflPfl,
      categoryName: this.getCategoryName(profile.codCatpPfl),
      accessHours: this.formatAccessHours(
        profile.libhdebpfl,
        profile.libhfinpfl
      ),
      assignmentDates: undefined,
    };
  }

  /**
   * Gets category name from category code
   * @private
   * @param codCatpPfl Category code
   * @returns Human-readable category name
   */
  private getCategoryName(codCatpPfl: string): string {
    if (!codCatpPfl) return '';

    const categoryMap: { [key: string]: string } = {
      '1': 'AGENCE',
      '2': 'DIRECTION REGIONALE',
      '3': 'DIRECTION CENTRALE',
      '4': 'DIVISION',
      '5': 'DIRECTION',
      '6': 'SUCCURSALE',
      '7': 'Box de Change',
    };

    return categoryMap[codCatpPfl] || 'DIRECTION CENTRALE';
  }

  /**
   * Formats access hours for display
   * @private
   * @param libhdebpfl Start hour
   * @param libhfinpfl End hour
   * @returns Formatted access hours string
   */
  private formatAccessHours(libhdebpfl: string, libhfinpfl: string): string {
    if (!libhdebpfl || !libhfinpfl) return '24h/24';
    return `${libhdebpfl}h - ${libhfinpfl}h`;
  }

  /**
   * Formats assignment dates for display
   * @private
   * @param datFadhUtpr Start date
   * @param datdadhutpr End date
   * @returns Formatted date range string
   */
  private formatAssignmentDates(
    datFadhUtpr: string,
    datdadhutpr: string
  ): string {
    if (!datFadhUtpr) return '';

    const startDate = new Date(datFadhUtpr).toLocaleDateString('fr-FR');
    const endDate = datdadhutpr
      ? new Date(datdadhutpr).toLocaleDateString('fr-FR')
      : 'Indéterminée';

    return `Du ${startDate} au ${endDate}`;
  }

  /**
   * Gets assignment status based on date range
   * @private
   * @param startDate Assignment start date
   * @param endDate Assignment end date
   * @returns Status string
   */
  private getAssignmentStatus(startDate: string, endDate: string): string {
    const now = new Date();
    const start = new Date(startDate);
    const end = endDate ? new Date(endDate) : null;

    if (start > now) {
      return 'future';
    } else if (end && end < now) {
      return 'expired';
    } else {
      return 'available';
    }
  } // ============================================================================
  // SELECTION TRACKING AND STATE MANAGEMENT METHODS
  // ============================================================================

  /**
   * Clears all selection tracking sets
   * @private
   */
  private clearAllSelectionTracking(): void {
    this.selectedProfiles.available.clear();
    this.selectedProfiles.assigned.clear();
    this.newlyMovedProfiles.available.clear();
    this.newlyMovedProfiles.assigned.clear();
    this.originalPositions.available.clear();
    this.originalPositions.assigned.clear();
  }

  /**
   * Tracks original positions of profiles for reset functionality
   * @private
   */
  private trackOriginalPositions(): void {
    this.originalPositions.assigned.clear();
    this.originalPositions.available.clear();

    this.assignedProfiles.forEach((profile) => {
      this.originalPositions.assigned.add(profile.codPflPfl);
    });

    this.availableProfiles.forEach((profile) => {
      this.originalPositions.available.add(profile.codPflPfl);
    });
  }

  /**
   * Toggles selection of a specific profile
   * @param profile Profile to toggle
   * @param listType List type (available or assigned)
   */
  toggleProfileSelection(
    profile: any,
    listType: 'available' | 'assigned'
  ): void {
    const profileCode = profile.codPflPfl;
    const selectedSet = this.selectedProfiles[listType];

    if (selectedSet.has(profileCode)) {
      selectedSet.delete(profileCode);
    } else {
      selectedSet.add(profileCode);
    }
  }

  /**
   * Checks if a profile is currently selected
   * @param profile Profile to check
   * @param listType List type to check in
   * @returns True if profile is selected
   */
  isProfileSelected(profile: any, listType: 'available' | 'assigned'): boolean {
    return this.selectedProfiles[listType].has(profile.codPflPfl);
  }

  /**
   * Checks if a profile is newly moved and needs validation
   * @param profile Profile to check
   * @param listType List type to check in
   * @returns True if profile is newly moved
   */
  isProfileNewlyMoved(
    profile: any,
    listType: 'available' | 'assigned'
  ): boolean {
    return this.newlyMovedProfiles[listType].has(profile.codPflPfl);
  }

  /**
   * Checks if a profile is in its original position
   * @param profile Profile to check
   * @param listType List type to check in
   * @returns True if profile is in original position
   */
  isProfileInOriginalPosition(
    profile: any,
    listType: 'available' | 'assigned'
  ): boolean {
    return this.originalPositions[listType].has(profile.codPflPfl);
  }

  /**
   * Gets the count of newly moved profiles
   * @returns Total count of newly moved profiles
   */
  getNewlyMovedCount(): number {
    return (
      this.newlyMovedProfiles.available.size +
      this.newlyMovedProfiles.assigned.size
    );
  }
  /**
   * Gets the total count of changes including packs and individual profiles
   * @returns Total count including pack profiles and individual profile changes
   */
  getTotalChangesCount(): number {
    const individualProfileCount = this.getNewlyMovedCount();
    const packProfilesCount = this.packsToAssign.reduce((sum, pack) => {
      return sum + (pack.profileCodes?.length || pack.profiles?.length || 0);
    }, 0);
    return individualProfileCount + packProfilesCount;
  }

  /**
   * Gets all profiles (available + assigned)
   * @returns Combined array of all profiles
   */
  getAllProfiles(): UnifiedProfile[] {
    return [...this.availableProfiles, ...this.assignedProfiles];
  }

  /**
   * Checks if there are any changes to save (individual profiles or packs)
   * @returns True if there are changes to save
   */
  hasChangesToSave(): boolean {
    return this.getNewlyMovedCount() > 0 || this.packsToAssign.length > 0;
  }

  /**
   * Navigates to confirmation step with validation for revoked profiles in packs
   * Shows warning if packs contain profiles that were previously revoked from the user
   * @param activateCallback Stepper callback to activate step 3
   */
  goToConfirmationStep(activateCallback: (step: number) => void): void {
    // Clear any previous assignment results when entering confirmation step
    this.clearAssignmentResults();

    // Check if there are packs to assign
    if (this.packsToAssign.length === 0) {
      // No packs, proceed directly
      activateCallback(3);
      return;
    }

    // Find revoked profiles that would be re-activated by packs
    const revokedProfilesInPacks = this.findRevokedProfilesInPacks();

    if (revokedProfilesInPacks.length > 0) {
      // Show warning dialog
      this.showRevokedProfilesWarning(revokedProfilesInPacks, activateCallback);
    } else {
      // No conflicts, proceed
      activateCallback(3);
    }
  }

  /**
   * Finds profiles in packs that were previously revoked from the user
   * (profiles that are currently in the "available" list after being moved from "assigned")
   * @private
   * @returns Array of pack-profile conflicts
   */ private findRevokedProfilesInPacks(): Array<{
    packName: string;
    packCode: string;
    profiles: string[];
  }> {
    const conflicts: Array<{
      packName: string;
      packCode: string;
      profiles: string[];
    }> = [];

    // Enhanced debugging - show full state
    console.log(
      `[Revoked Check] ========== STARTING REVOKED PROFILES CHECK ==========`
    );
    console.log(
      `[Revoked Check] Available profiles count: ${this.availableProfiles.length}`
    );
    console.log(
      `[Revoked Check] Assigned profiles count: ${this.assignedProfiles.length}`
    );
    console.log(
      `[Revoked Check] Packs to assign count: ${this.packsToAssign.length}`
    );
    console.log(
      `[Revoked Check] newlyMovedProfiles.available Set:`,
      Array.from(this.newlyMovedProfiles.available)
    );
    console.log(
      `[Revoked Check] newlyMovedProfiles.assigned Set:`,
      Array.from(this.newlyMovedProfiles.assigned)
    );
    console.log(
      `[Revoked Check] originalPositions.available Set:`,
      Array.from(this.originalPositions.available)
    );
    console.log(
      `[Revoked Check] originalPositions.assigned Set:`,
      Array.from(this.originalPositions.assigned)
    );

    // Get all profile codes that were revoked or are inactive
    const revokedProfileCodes = new Set<string>();

    // 1. Profiles moved from assigned → available in current session
    console.log(
      `[Revoked Check] --- Checking availableProfiles for revoked profiles ---`
    );
    this.availableProfiles.forEach((profile) => {
      const isInNewlyMoved = this.newlyMovedProfiles.available.has(
        profile.codPflPfl
      );
      console.log(
        `[Revoked Check] Profile ${profile.codPflPfl} (${profile.libpflpfl}): inNewlyMoved=${isInNewlyMoved}`
      );
      if (isInNewlyMoved) {
        revokedProfileCodes.add(profile.codPflPfl);
        console.log(
          `[Revoked Check] ✓ Profile ${profile.codPflPfl} - moved to available (revoked in session)`
        );
      }
    });

    // 2. Profiles that are currently inactive/revoked (boolEtatUtpr === 0)
    console.log(
      `[Revoked Check] --- Checking assignedProfiles for inactive profiles ---`
    );
    this.assignedProfiles.forEach((profile) => {
      console.log(
        `[Revoked Check] Profile ${profile.codPflPfl} (${profile.libpflpfl}): boolEtatUtpr=${profile.boolEtatUtpr}`
      );
      if (profile.boolEtatUtpr === 0) {
        revokedProfileCodes.add(profile.codPflPfl);
        console.log(
          `[Revoked Check] ✓ Profile ${profile.codPflPfl} - inactive (boolEtatUtpr === 0)`
        );
      }
    });

    // 3. Get currently ACTIVE assigned profile codes
    const activeAssignedProfileCodes = new Set<string>();
    this.assignedProfiles.forEach((profile) => {
      if (
        profile.boolEtatUtpr === 1 &&
        !this.newlyMovedProfiles.available.has(profile.codPflPfl)
      ) {
        activeAssignedProfileCodes.add(profile.codPflPfl);
      }
    });

    console.log(
      `[Revoked Check] SUMMARY - Revoked profiles: [${Array.from(
        revokedProfileCodes
      ).join(', ')}]`
    );
    console.log(
      `[Revoked Check] SUMMARY - Active assigned profiles: [${Array.from(
        activeAssignedProfileCodes
      ).join(', ')}]`
    );

    // Check each pack in cart for conflicts
    console.log(`[Revoked Check] --- Checking packs for conflicts ---`);
    this.packsToAssign.forEach((pack) => {
      const packProfileCodes = pack.profileCodes || [];
      const conflictingProfiles: string[] = [];

      console.log(
        `[Revoked Check] Pack "${pack.name}" (${pack.code}) has ${
          packProfileCodes.length
        } profiles: [${packProfileCodes.join(', ')}]`
      );

      packProfileCodes.forEach((profileCode) => {
        const isRevoked = revokedProfileCodes.has(profileCode);

        // Only warn about profiles that were REVOKED (not just "not assigned")
        // A profile is considered revoked if:
        // - It was moved from assigned to available in this session
        // - It exists in assigned list but is inactive (boolEtatUtpr === 0)
        if (isRevoked) {
          // Get profile display name from all profiles
          const profile = this.getAllProfiles().find(
            (p) => p.codPflPfl === profileCode
          );
          const displayName =
            profile?.displayName || profile?.libpflpfl || profileCode;
          conflictingProfiles.push(displayName);
          console.log(
            `[Revoked Check] ⚠️ CONFLICT: Pack "${pack.name}" profile "${displayName}" (${profileCode}) was revoked`
          );
        } else {
          console.log(
            `[Revoked Check] ✓ Pack "${pack.name}" profile ${profileCode} - not revoked`
          );
        }
      });

      if (conflictingProfiles.length > 0) {
        conflicts.push({
          packName: pack.name,
          packCode: pack.code,
          profiles: conflictingProfiles,
        });
      }
    });

    console.log(
      `[Revoked Check] ========== RESULT: ${conflicts.length} packs with conflicts ==========`
    );
    console.log(
      `[Revoked Check] Conflicts:`,
      JSON.stringify(conflicts, null, 2)
    );
    return conflicts;
  }

  /**
   * Shows warning dialog about revoked profiles being re-activated via packs
   * @private
   * @param conflicts Array of pack-profile conflicts
   * @param activateCallback Stepper callback to activate step 3
   */
  private showRevokedProfilesWarning(
    conflicts: Array<{
      packName: string;
      packCode: string;
      profiles: string[];
    }>,
    activateCallback: (step: number) => void
  ): void {
    // Build warning message
    let message = `<div class="revoked-profiles-warning">
      <p class="mb-3"><strong>⚠️ Attention :</strong> Les packs suivants contiennent des profils qui ont été révoqués pour cet utilisateur :</p>
      <ul class="pack-conflicts-list">`;

    conflicts.forEach((conflict) => {
      message += `<li class="mb-2">
        <strong>${conflict.packName}</strong>
        <ul class="profile-list mt-1">`;
      conflict.profiles.forEach((profile) => {
        message += `<li><i class="pi pi-exclamation-triangle text-warning mr-1"></i>${profile}</li>`;
      });
      message += `</ul></li>`;
    });

    message += `</ul>
      <p class="mt-3 text-warning"><i class="pi pi-info-circle mr-1"></i>L'attribution de ces packs réactivera automatiquement ces profils pour l'utilisateur.</p>
    </div>`;

    this.confirmationService.confirm({
      message: message,
      header: 'Profils révoqués détectés',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Continuer quand même',
      rejectLabel: 'Annuler',
      acceptButtonStyleClass: 'p-button-warning',
      rejectButtonStyleClass: 'p-button-secondary',
      accept: () => {
        // User acknowledged, proceed to confirmation
        activateCallback(3);
      },
      reject: () => {
        // User cancelled, stay on step 2
        console.log('User cancelled due to revoked profiles warning');
      },
    });
  }

  /**
   * Checks if there are any selected profiles
   * @returns True if any profiles are selected
   */
  hasSelectedProfiles(): boolean {
    return (
      this.selectedProfiles.available.size > 0 ||
      this.selectedProfiles.assigned.size > 0
    );
  }

  // ============================================================================
  // PROFILE ASSIGNMENT AND SAVE METHODS
  // ============================================================================

  /**
   * Saves profile assignments for the selected user
   * Handles both individual profiles and batch pack assignments
   */
  saveAssignments(): void {
    if (!this.selectedUser) return;

    const matricule = this.selectedUser.numMatrUser || this.selectedUser.mat;

    // Check if we have packs to assign (regardless of isBatchPackAssignment flag)
    const hasPacks = this.packsToAssign.length > 0;
    const hasIndividualChanges = this.getNewlyMovedCount() > 0;

    // If we have packs to assign, handle pack assignment first
    if (hasPacks && hasIndividualChanges) {
      this.saveBatchPackAssignments(matricule, hasIndividualChanges);
      this.saveIndividualProfileAssignments(matricule);
      return;
    }
    if (hasPacks) {
      this.saveBatchPackAssignments(matricule, hasIndividualChanges);
      return;
    }

    // Handle regular individual profile assignments
    this.saveIndividualProfileAssignments(matricule);
  }
  /**
   * Save batch pack assignments
   * @param matricule User matricule
   * @param hasIndividualChanges Whether there are also individual profile changes to save after packs
   */
  private saveBatchPackAssignments(
    matricule: string,
    hasIndividualChanges: boolean = false
  ): void {
    // Transform pack codes to AssignedPack objects with assignment metadata
    const assignedPacksData: AssignedPack[] = this.packsToAssign.map(
      (pack) => ({
        packCode: pack.code,
        dateDebut: formatPackDateForApi(new Date()),
        dateFin: null,
        etat: 1,
      })
    );

    const packRequest: UserPacksAssignmentRequest = {
      userMatricule: matricule,
      assignedPacks: assignedPacksData,
      revokedPacks: [],
    };

    console.log('📦 Saving batch pack assignments:', packRequest);

    this.loading = true;

    this.utilisateurPackService.assignMultiplePacks(packRequest).subscribe({
      next: (result: BatchAssignmentResult) => {
        this.handlePackAssignmentResult(
          result,
          matricule,
          hasIndividualChanges
        );
      },
      error: (error) => {
        this.loading = false;
        console.error('Error assigning packs:', error);
        this.sharedService.handleError(
          error,
          "Une erreur est survenue lors de l'assignation des packs"
        );
      },
    });
  }
  /**
   * Handle pack assignment result (200/207/400 responses)
   * @private
   */
  private handlePackAssignmentResult(
    result: BatchAssignmentResult,
    matricule: string,
    hasIndividualChanges: boolean
  ): void {
    // Get target user name for notification context
    const targetUserName =
      this.selectedUser?.nom_prenom ||
      `${this.selectedUser?.prenUtilUtl || ''} ${this.selectedUser?.nomUtilUtl || ''}`.trim() ||
      this.selectedUser?.mat ||
      'Utilisateur';

    // Use the new operation notification service
    this.operationNotificationService.handleBatchOperationResult(
      result,
      OpNotificationType.PACK_ASSIGNMENT,
      { targetUser: targetUserName }
    );

    // Handle UI state based on result
    if (isCompleteSuccess(result)) {
      // Move all packs from available to assigned
      this.movePacksToAssigned(this.packsToAssign.map((p) => p.code));
      this.finishPackAssignment(matricule, hasIndividualChanges);
    } else if (isCompleteFailure(result)) {
      // Keep packs in pending state
      this.loading = false;
    } else if (isPartialSuccess(result)) {
      // Move only successful packs to assigned
      const successfulPackCodes = result.successful || [];
      this.movePacksToAssigned(successfulPackCodes);
      this.finishPackAssignment(matricule, hasIndividualChanges);
    }
  }

  /**
   * Move successful packs from pending to assigned
   * @private
   */
  private movePacksToAssigned(successfulPackCodes: string[]): void {
    successfulPackCodes.forEach((packCode) => {
      const pack = this.packsToAssign.find((p) => p.code === packCode);
      if (pack) {
        this.availablePacks = this.availablePacks.filter(
          (p) => p.code !== packCode
        );
        this.assignedPacks.push(pack);
      }
    });

    // Remove successful packs from pending
    this.packsToAssign = this.packsToAssign.filter(
      (p) => !successfulPackCodes.includes(p.code)
    );

    // Update filtered lists
    this.filteredAvailablePacks = [...this.availablePacks];
    this.filteredAssignedPacks = [...this.assignedPacks];
  }

  /**
   * Finish pack assignment process
   * @private
   */
  private finishPackAssignment(
    matricule: string,
    hasIndividualChanges: boolean
  ): void {
    // Clear pack assignment state if all packs processed
    if (this.packsToAssign.length === 0) {
      this.isBatchPackAssignment = false;
    }

    // If there are also individual profile changes, save them too
    if (hasIndividualChanges) {
      this.saveIndividualProfileAssignments(matricule);
    } else {
      this.loading = false;
      // Reload user profiles to show the newly assigned profiles
      this.loadUserProfiles(this.selectedUser);
      // Return to step 2
      this.activeStep = 2;
    }
  }
  /**
   * Get display name for a pack code
   * @private
   */
  private getPackDisplayName(packCode: string): string {
    const pack = [
      ...this.availablePacks,
      ...this.assignedPacks,
      ...this.packsToAssign,
    ].find((p) => p.code === packCode);
    return pack?.name || packCode;
  }

  /**
   * Save individual profile assignments
   */
  private saveIndividualProfileAssignments(matricule: string): void {
    // Prepare assigned profiles data (profiles moved to assigned that need to be added/updated)
    // Using AssignedProfile interface from profile-assignment.model.ts
    const assignedProfilesData: AssignedProfile[] = this.assignedProfiles
      .filter((p) => this.newlyMovedProfiles.assigned.has(p.codPflPfl))
      .map((p) => ({
        profileCode: p.codPflPfl,
        dateDebut: formatDateForApi(
          p.datFadhUtpr ? new Date(p.datFadhUtpr) : new Date()
        ),
        dateFin: formatDateForApi(
          p.datdadhutpr ? new Date(p.datdadhutpr) : null
        ),
        etat: p.boolEtatUtpr !== undefined ? p.boolEtatUtpr : 1,
      }));

    // Prepare revoked profiles data (profiles moved from assigned back to available)
    const revokedProfileCodes: string[] = this.availableProfiles
      .filter((p) => this.newlyMovedProfiles.available.has(p.codPflPfl))
      .map((p) => p.codPflPfl);

    // Check if there are any changes to save
    if (assignedProfilesData.length === 0 && revokedProfileCodes.length === 0) {
      this.sharedService.showWarn('Aucune modification à enregistrer');
      return;
    }

    const request: UserProfilesAssignmentRequest = {
      userMatricule: matricule,
      appCode: this.selectedAppCode,
      assignedProfiles:
        assignedProfilesData.length > 0 ? assignedProfilesData : undefined,
      revokedProfiles:
        revokedProfileCodes.length > 0 ? revokedProfileCodes : undefined,
    };

    this.loading = true;

    this.profileAssignmentService.assignMultipleProfiles(request).subscribe({
      next: (result: any) => {
        this.handleSuccessfulSave(result);
      },
      error: (error) => {
        this.handleSaveError(error);
      },
    });
  }  /**
   * Handles successful save operation (including 207 Multi-Status responses)
   * @private
   * @param result Save operation result with successful/failed profile codes
   */
  private handleSuccessfulSave(result: BatchAssignmentResult): void {
    this.loading = false;
    this.isAssigning = false;

    // Get target user name for notification context
    const targetUserName =
      this.selectedUser?.nom_prenom ||
      `${this.selectedUser?.prenUtilUtl || ''} ${this.selectedUser?.nomUtilUtl || ''}`.trim() ||
      this.selectedUser?.mat ||
      'Utilisateur';

    // Transform failed items for confirmation step display
    const failedArray = getFailedItemsArray(result).map((item) => ({
      identifier: this.getProfileDisplayName(item.identifier),
      error: item.error,
    }));

    // Build assignment results for confirmation step display
    this.buildAssignmentResults(result, failedArray);

    // Use the new operation notification service
    this.operationNotificationService.handleBatchOperationResult(
      result,
      OpNotificationType.PROFILE_ASSIGNMENT,
      { targetUser: targetUserName }
    );

    // Handle UI state based on result type
    if (isCompleteSuccess(result)) {
      // Clear tracking and update positions
      this.newlyMovedProfiles.available.clear();
      this.newlyMovedProfiles.assigned.clear();
      this.trackOriginalPositions();
    } else if (isPartialSuccess(result)) {
      // Clear only successful profiles from tracking
      const successfulProfileCodes = result.successful || [];
      successfulProfileCodes.forEach((profileCode: string) => {
        this.newlyMovedProfiles.assigned.delete(profileCode);
        this.newlyMovedProfiles.available.delete(profileCode);
      });
      // Update original positions for successful profiles only
      this.trackOriginalPositions();
    }
    // Note: For complete failure, we keep profiles in newly moved state
  }
  /**
   * Builds assignment results for display in confirmation step
   * @private
   * @param result BatchAssignmentResult from the API
   * @param failedArray Array of failed items with profile name and error
   */
  private buildAssignmentResults(
    result: BatchAssignmentResult,
    failedArray: Array<{ identifier: string; error: string }>
  ): void {
    const successfulCodes = result.successful || [];

    // Build successful items
    const successfulItems: AssignmentResultItem[] = successfulCodes.map(
      (code) => ({
        profileCode: code,
        profileName: this.getProfileDisplayName(code),
        success: true,
      })
    );

    // Build failed items
    const failedItems: AssignmentResultItem[] = failedArray.map((item) => ({
      profileCode: item.identifier,
      profileName: item.identifier,
      success: false,
      error: item.error,
    }));

    // Set assignment results
    this.assignmentResults = {
      successful: successfulItems,
      failed: failedItems,
      totalProcessed: (result.successCount || 0) + (result.failureCount || 0),
      successCount: result.successCount || 0,
      failureCount: result.failureCount || 0,
      timestamp: new Date(),
    };

    // Trigger change detection and scroll to results
    this.cdr.detectChanges();

    // Scroll to results section after a short delay
    setTimeout(() => {
      if (this.confirmationStepRef) {
        this.confirmationStepRef.scrollToResults();
      }
    }, 150);
  }

  /**
   * Clears assignment results (called when navigating away or starting new assignment)
   */
  clearAssignmentResults(): void {
    this.assignmentResults = null;
    this.isAssigning = false;
    this.assignmentProgress = 0;
  }

  /**
   * Handles save operation errors
   * @private
   * @param error Error object
   */
  private handleSaveError(error: any): void {
    this.loading = false;
    this.sharedService.handleError(
      error,
      'Erreur lors de la sauvegarde des profils.'
    );
    console.error('Error saving profiles:', error);
  }

  // ============================================================================
  // PROFILE RESTORATION METHODS
  // ============================================================================
  /**
   * Handles the restore profile button click event
   * Opens the restoration dialog with the selected profile
   * @param profile The revoked profile to restore
   * @param event The click event
   */
  onRestoreProfile(profile: UnifiedProfile, event?: Event): void {
    if (event) {
      event.stopPropagation();
    }
    console.log('Opening restore dialog for profile:', profile);

    this.selectedProfileToRestore = profile;

    // Initialize form with current dates or defaults
    this.restoreProfileForm = {
      dateDebut: profile.datFadhUtpr
        ? new Date(profile.datFadhUtpr)
        : new Date(),
      dateFin: profile.datdadhutpr ? new Date(profile.datdadhutpr) : null,
    };

    this.profileRestoreDialog = true;
  }
  /**
   * Confirms the profile restoration with new dates
   * Calls the batch-update API to immediately restore the profile
   */ confirmProfileRestore(): void {
    if (!this.selectedProfileToRestore || !this.restoreProfileForm.dateDebut) {
      this.sharedService.showWarn(
        'Veuillez sélectionner une date de début',
        'Validation requise'
      );
      return;
    }

    // Validate that dateFin is provided (now required)
    if (!this.restoreProfileForm.dateFin) {
      this.sharedService.showWarn(
        'Veuillez sélectionner une date de fin',
        'Validation requise'
      );
      return;
    }

    // Validate dates
    if (
      this.restoreProfileForm.dateFin &&
      this.restoreProfileForm.dateFin < this.restoreProfileForm.dateDebut
    ) {
      this.sharedService.showWarn(
        'La date de fin doit être postérieure à la date de début',
        'Dates invalides'
      );
      return;
    }

    const profile = this.selectedProfileToRestore;

    // Show loading state
    this.loading = true;

    // Prepare the batch update request
    const updateRequest: UserProfileUpdateRequest = {
      userMatricule: this.selectedUser.mat,
      appCode: this.selectedAppCode,
      operations: [
        {
          type: OperationType.UPDATE,
          profileCode: profile.codPflPfl,
          dateDebut: this.formatDateForBackend(
            this.restoreProfileForm.dateDebut
          ),
          dateFin: this.restoreProfileForm.dateFin
            ? this.formatDateForBackend(this.restoreProfileForm.dateFin)
            : null,
          etat: 1, // Active status
        },
      ],
    };

    // Call the batch update API
    this.profileAssignmentService
      .batchUpdateProfiles(updateRequest)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (result: ProfileUpdateResult) => {
          this.loading = false;

          if (result.success || result.updatedCount > 0) {
            // Update profile in local state
            profile.boolEtatUtpr = 1;
            profile.isActive = true;
            profile.datFadhUtpr = this.formatDateForBackend(
              this.restoreProfileForm.dateDebut
            );
            profile.datdadhutpr = this.restoreProfileForm.dateFin
              ? this.formatDateForBackend(this.restoreProfileForm.dateFin)
              : undefined;

            // Recalculate assignment dates display
            profile.assignmentDates = this.formatAssignmentDates(
              profile.datFadhUtpr,
              profile.datdadhutpr
            );

            // Apply filters to refresh the display
            this.applyAssignedProfileFilters();

            // Show success message
            this.sharedService.showEnhancedSuccess(
              `Le profil "${profile.displayName}" a été réactivé avec succès.`,
              '✅ Profil réactivé',
              {
                timestamp: new Date().toISOString(),
                successType: 'profile-restoration',
                details: `${result.updatedCount} profil(s) mis à jour`,
              }
            );

            // Close dialog and reset
            this.profileRestoreDialog = false;
            this.selectedProfileToRestore = null;
            this.restoreProfileForm = { dateDebut: null, dateFin: null };

            console.log('Profile restored successfully:', result);
          } else {
            // Handle partial failure or complete failure
            this.handleProfileRestoreError(result);
          }
        },
        error: (error) => {
          this.loading = false;
          this.handleProfileRestoreError(error);
        },
      });
  }

  /**
   * Cancels the profile restoration and closes the dialog
   */
  cancelProfileRestore(): void {
    this.profileRestoreDialog = false;
    this.selectedProfileToRestore = null;
    this.restoreProfileForm = { dateDebut: null, dateFin: null };

    console.log('Profile restoration cancelled');
  }

  /**
   * Handles errors during profile restoration
   * @private
   * @param error Error object or ProfileUpdateResult with errors
   */
  private handleProfileRestoreError(error: any): void {
    console.error('Profile restoration error:', error);

    let errorMessage =
      'Une erreur est survenue lors de la réactivation du profil.';

    if (error.globalError) {
      errorMessage = error.globalError;
    } else if (error.failedOperations && error.failedOperations.length > 0) {
      const failedOp = error.failedOperations[0];
      errorMessage = failedOp.errorMessage || errorMessage;
    } else if (error.error && error.error.message) {
      errorMessage = error.error.message;
    } else if (error.message) {
      errorMessage = error.message;
    }

    this.sharedService.showError(errorMessage, 'Erreur de réactivation');

    // Keep dialog open so user can try again
  }

  /**
   * Formats a date object for backend API (YYYY-MM-DD)
   * @private
   * @param date Date object to format
   * @returns Formatted date string
   */
  private formatDateForBackend(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  /**
   * Resets all modifications back to original positions
   */
  resetAllModifications(): void {
    if (this.getNewlyMovedCount() === 0) return;
    this.sharedService.confirmDelete(
      'Voulez-vous vraiment annuler toutes les modifications ? Cette action restaurera les profils à leurs positions originales.',
      () => {
        this.performReset();
      }
    );
  }

  /**
   * Performs the actual reset operation
   * @private
   */
  private performReset(): void {
    // Create new arrays to hold the reset profiles
    const newAvailableProfiles: any[] = [];
    const newAssignedProfiles: any[] = [];

    // Get all profiles from both lists
    const allProfiles = [...this.availableProfiles, ...this.assignedProfiles];

    // Redistribute profiles based on original positions
    allProfiles.forEach((profile) => {
      const profileCode = profile.codPflPfl;

      if (this.originalPositions.available.has(profileCode)) {
        newAvailableProfiles.push(profile);
      } else if (this.originalPositions.assigned.has(profileCode)) {
        newAssignedProfiles.push(profile);
      }
    });

    // Update arrays
    this.availableProfiles = newAvailableProfiles;
    this.assignedProfiles = newAssignedProfiles;

    // Update filtered profiles and clear tracking
    this.applyProfileFilters();
    this.newlyMovedProfiles.available.clear();
    this.newlyMovedProfiles.assigned.clear();
    this.selectedProfiles.available.clear();
    this.selectedProfiles.assigned.clear();

    this.sharedService.handleSuccess(
      {},
      'Toutes les modifications ont été annulées.'
    );
  }
  // ============================================================================
  // DRAG-AND-DROP METHODS
  // ============================================================================

  /**
   * Handles drag and drop operations between profile lists
   * @param event CDK drag drop event
   */
  onDrop(event: CdkDragDrop<any[]>): void {
    this.dragState = 'dragging';

    if (event.previousContainer === event.container) {
      // Reordering within the same list
      moveItemInArray(
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
    } else {
      // Moving between different lists
      this.handleCrossListDrop(event);
    }
  }
  /**
   * Handles drag and drop between different lists
   * @private
   * @param event CDK drag drop event
   */
  private handleCrossListDrop(event: CdkDragDrop<any[]>): void {
    // Access dragged profile data from CDK's internal structure
    // When cdkDrag is on a component, data is in _parentDrag.data
    const draggedProfile =
      (event.item as any)._parentDrag?.data || event.item.data;

    if (!draggedProfile) {
      console.error('No profile data found in drag event');
      return;
    }

    const draggedProfileCode = draggedProfile.codPflPfl;

    const sourceListType =
      event.previousContainer.id === 'availableList' ? 'available' : 'assigned';
    const targetListType =
      event.container.id === 'assignedList' ? 'assigned' : 'available';

    // Get the unfiltered source array
    const sourceArray =
      sourceListType === 'available'
        ? this.availableProfiles
        : this.assignedProfiles;

    // Determine profiles to move
    const selectedProfilesInSource = this.selectedProfiles[sourceListType];
    let profilesToMove: any[] = [];

    if (selectedProfilesInSource.has(draggedProfileCode)) {
      // Move all selected profiles from unfiltered source
      profilesToMove = sourceArray.filter((profile) =>
        selectedProfilesInSource.has(profile.codPflPfl)
      );
    } else {
      // Move only the dragged profile
      profilesToMove = [draggedProfile];
    }

    // Handle assignment dialog for available to assigned moves
    if (targetListType === 'assigned') {
      this.openProfileAssignmentDialog(profilesToMove);
      return;
    }

    // Complete move for assigned to available
    this.completeProfileMove(profilesToMove, sourceListType, targetListType);
  }

  /**
   * Gets dynamic drag icon based on current state
   * @returns Icon class string
   */
  getDragIcon(): string {
    switch (this.dragState) {
      case 'dragging':
        return 'pi-arrow-right-arrow-left';
      case 'success':
        return 'pi-check';
      default:
        return 'pi-arrows-h';
    }
  }

  /**
   * Gets dynamic drag text based on current state
   * @returns Drag instruction text
   */
  getDragText(): string {
    switch (this.dragState) {
      case 'dragging':
        return 'Relâchez ici';
      case 'success':
        return 'Transféré!';
      default:
        return 'Glissez-déposez';
    }
  }

  /**
   * Gets appropriate drag center icon based on selection state
   * @returns Icon class string
   */
  getDragCenterIcon(): string {
    const hasAvailableSelected = this.selectedProfiles.available.size > 0;
    const hasAssignedSelected = this.selectedProfiles.assigned.size > 0;
    const isMobileLayout = window.innerWidth <= 992;

    if (hasAvailableSelected && !hasAssignedSelected) {
      return isMobileLayout ? 'pi-arrow-down' : 'pi-arrow-right';
    } else if (hasAssignedSelected && !hasAvailableSelected) {
      return isMobileLayout ? 'pi-arrow-up' : 'pi-arrow-left';
    } else if (hasAvailableSelected && hasAssignedSelected) {
      return isMobileLayout ? 'pi-sort' : 'pi-arrows-h';
    }

    return this.getDragIcon();
  }

  /**
   * Gets drag center text based on selection state
   * @returns Drag instruction text
   */
  getDragCenterText(): string {
    const hasAvailableSelected = this.selectedProfiles.available.size > 0;
    const hasAssignedSelected = this.selectedProfiles.assigned.size > 0;

    if (hasAvailableSelected && !hasAssignedSelected) {
      return `Assigner ${this.selectedProfiles.available.size} profil(s)`;
    } else if (hasAssignedSelected && !hasAvailableSelected) {
      return `Retirer ${this.selectedProfiles.assigned.size} profil(s)`;
    } else if (hasAvailableSelected && hasAssignedSelected) {
      return 'Échanger les sélections';
    }

    return this.getDragText();
  }

  /**
   * Handles click on drag center when there are selections
   */
  onDragCenterClick(): void {
    const hasAvailableSelected = this.selectedProfiles.available.size > 0;
    const hasAssignedSelected = this.selectedProfiles.assigned.size > 0;

    if (hasAvailableSelected && !hasAssignedSelected) {
      this.moveSelectedProfiles('available', 'assigned');
    } else if (hasAssignedSelected && !hasAvailableSelected) {
      this.moveSelectedProfiles('assigned', 'available');
    } else if (hasAvailableSelected && hasAssignedSelected) {
      this.swapSelectedProfiles();
    }
  }

  /**
   * Gets drag preview text for multiple selection
   * @param profile Profile being dragged
   * @param listType Source list type
   * @returns Preview text
   */
  getDragPreviewText(
    profile: UnifiedProfile,
    listType: 'available' | 'assigned'
  ): string {
    const selectedCount = this.selectedProfiles[listType].size;
    const isSelected = this.isProfileSelected(profile, listType);

    if (isSelected && selectedCount > 1) {
      return `${selectedCount} profils sélectionnés`;
    }

    return profile.displayName;
  }

  /**
   * Checks if we're dragging multiple items
   * @param profile Profile being checked
   * @param listType List type
   * @returns True if dragging multiple items
   */
  isDraggingMultiple(
    profile: any,
    listType: 'available' | 'assigned'
  ): boolean {
    const selectedCount = this.selectedProfiles[listType].size;
    const isSelected = this.isProfileSelected(profile, listType);
    return isSelected && selectedCount > 1;
  }

  // ============================================================================
  // PROFILE MOVEMENT METHODS
  // ============================================================================

  /**
   * Moves all selected profiles from source to target column
   * @private
   * @param sourceType Source column type
   * @param targetType Target column type
   */
  private moveSelectedProfiles(
    sourceType: 'available' | 'assigned',
    targetType: 'available' | 'assigned'
  ): void {
    const sourceArray =
      sourceType === 'available'
        ? this.availableProfiles
        : this.assignedProfiles;
    const targetArray =
      targetType === 'available'
        ? this.availableProfiles
        : this.assignedProfiles;
    const selectedSet = this.selectedProfiles[sourceType];

    const profilesToMove = sourceArray.filter((profile) =>
      selectedSet.has(profile.codPflPfl)
    );

    if (profilesToMove.length === 0) return;

    // Remove from source
    profilesToMove.forEach((profile) => {
      const index = sourceArray.findIndex(
        (p) => p.codPflPfl === profile.codPflPfl
      );
      if (index !== -1) {
        sourceArray.splice(index, 1);
      }
    });

    // Add to target
    targetArray.push(...profilesToMove);

    // Handle tracking
    profilesToMove.forEach((profile) => {
      this.handleProfileMovement(
        profile,
        targetType === 'assigned' ? 'assignedList' : 'availableList'
      );
    });

    selectedSet.clear();
    this.showSuccessAnimation();
  }

  /**
   * Swaps selected profiles between columns
   * @private
   */
  private swapSelectedProfiles(): void {
    const selectedAvailable = this.availableProfiles.filter((profile) =>
      this.selectedProfiles.available.has(profile.codPflPfl)
    );
    const selectedAssigned = this.assignedProfiles.filter((profile) =>
      this.selectedProfiles.assigned.has(profile.codPflPfl)
    );

    if (selectedAvailable.length === 0 && selectedAssigned.length === 0) return;

    // Remove from current arrays
    this.removeProfilesFromArray(selectedAvailable, this.availableProfiles);
    this.removeProfilesFromArray(selectedAssigned, this.assignedProfiles);

    // Add to opposite arrays
    this.assignedProfiles.push(...selectedAvailable);
    this.availableProfiles.push(...selectedAssigned);

    // Handle tracking
    selectedAvailable.forEach((profile) => {
      this.handleProfileMovement(profile, 'assignedList');
    });
    selectedAssigned.forEach((profile) => {
      this.handleProfileMovement(profile, 'availableList');
    });

    // Clear selections
    this.selectedProfiles.available.clear();
    this.selectedProfiles.assigned.clear();

    this.showSuccessAnimation();
  }

  /**
   * Removes profiles from an array
   * @private
   * @param profilesToRemove Profiles to remove
   * @param targetArray Array to remove from
   */
  private removeProfilesFromArray(
    profilesToRemove: any[],
    targetArray: any[]
  ): void {
    profilesToRemove.forEach((profile) => {
      const index = targetArray.findIndex(
        (p) => p.codPflPfl === profile.codPflPfl
      );
      if (index !== -1) targetArray.splice(index, 1);
    });
  }

  /**
   * Handles + icon click - moves profile from available to assigned
   * @param profile Profile to move
   * @param event Click event
   */
  addProfileToAssigned(profile: any, event: Event): void {
    event.stopPropagation();

    const availableIndex = this.availableProfiles.findIndex(
      (p) => p.codPflPfl === profile.codPflPfl
    );
    if (availableIndex === -1) return;

    const profileToMove = this.availableProfiles.splice(availableIndex, 1)[0];
    this.assignedProfiles.push(profileToMove);

    this.handleProfileMovement(profileToMove, 'assignedList');
    this.applyProfileFilters();
    this.showSuccessAnimation();
  }

  /**
   * Handles - icon click - moves profile from assigned to available
   * @param profile Profile to move
   * @param event Click event
   */
  removeProfileFromAssigned(profile: any, event: Event): void {
    event.stopPropagation();

    const assignedIndex = this.assignedProfiles.findIndex(
      (p) => p.codPflPfl === profile.codPflPfl
    );
    if (assignedIndex === -1) return;

    const profileToMove = this.assignedProfiles.splice(assignedIndex, 1)[0];
    this.availableProfiles.push(profileToMove);

    this.handleProfileMovement(profileToMove, 'availableList');
    this.applyProfileFilters();
    this.showSuccessAnimation();
  }

  /**
   * Common logic for handling profile movement tracking
   * @private
   * @param profile Profile being moved
   * @param targetListId Target list identifier
   */ private handleProfileMovement(profile: any, targetListId: string): void {
    const profileCode = profile.codPflPfl;

    console.log(`[Movement] ========== PROFILE MOVEMENT ==========`);
    console.log(`[Movement] Profile: ${profileCode} (${profile.libpflpfl})`);
    console.log(`[Movement] Target: ${targetListId}`);
    console.log(
      `[Movement] originalPositions.assigned has ${profileCode}: ${this.originalPositions.assigned.has(
        profileCode
      )}`
    );
    console.log(
      `[Movement] originalPositions.available has ${profileCode}: ${this.originalPositions.available.has(
        profileCode
      )}`
    );

    // Clear from both newly moved sets
    this.newlyMovedProfiles.available.delete(profileCode);
    this.newlyMovedProfiles.assigned.delete(profileCode);

    // Check if moving back to original position
    const isMovingToOriginalPosition =
      (targetListId === 'assignedList' &&
        this.originalPositions.assigned.has(profileCode)) ||
      (targetListId === 'availableList' &&
        this.originalPositions.available.has(profileCode));

    console.log(
      `[Movement] isMovingToOriginalPosition: ${isMovingToOriginalPosition}`
    );

    // Only track as newly moved if not returning to original position
    if (!isMovingToOriginalPosition) {
      if (targetListId === 'assignedList') {
        this.newlyMovedProfiles.assigned.add(profileCode);
        console.log(`[Movement] ✓ Added to newlyMovedProfiles.assigned`);
      } else if (targetListId === 'availableList') {
        this.newlyMovedProfiles.available.add(profileCode);
        console.log(`[Movement] ✓ Added to newlyMovedProfiles.available`);
      }
    } else {
      console.log(`[Movement] ✗ NOT tracked (returning to original position)`);
    }

    console.log(
      `[Movement] newlyMovedProfiles.available now: [${Array.from(
        this.newlyMovedProfiles.available
      ).join(', ')}]`
    );
    console.log(
      `[Movement] newlyMovedProfiles.assigned now: [${Array.from(
        this.newlyMovedProfiles.assigned
      ).join(', ')}]`
    );

    // Clear selection when item is moved
    this.selectedProfiles.available.delete(profileCode);
    this.selectedProfiles.assigned.delete(profileCode);
  }

  /**
   * Shows success animation for UI feedback
   * @private
   */
  private showSuccessAnimation(): void {
    this.dragState = 'success';
    setTimeout(() => {
      this.dragState = 'idle';
    }, 1000);
  }
  // ============================================================================
  // PROFILE ASSIGNMENT DIALOG METHODS
  // ============================================================================

  /**
   * Opens profile assignment dialog for setting assignment details
   * @param profiles Profiles to be assigned
   */
  openProfileAssignmentDialog(profiles: any[]): void {
    // Reset form
    this.currentAssignmentForm = {
      profileCode:
        profiles.length === 1
          ? profiles[0].codPflPfl
          : `${profiles.length} profils`,
      dateDebut: new Date(),
      dateFin: new Date(),
      etat: 1,
    };

    // Store profiles to be assigned
    this.pendingProfileAssignments = profiles.map((p) => ({
      profileCode: p.codPflPfl,
      dateDebut: new Date(),
      dateFin: null,
      etat: 1,
    }));

    this.profileAssignmentDialog = true;
  }

  /**
   * Confirms profile assignment with specified dates and status
   */
  confirmProfileAssignment(): void {
    // Validate dates
    if (
      this.currentAssignmentForm.dateFin &&
      this.currentAssignmentForm.dateFin < this.currentAssignmentForm.dateDebut
    ) {
      this.sharedService.showWarn(
        'La date de fin ne peut pas être antérieure à la date de début'
      );
      return;
    }

    // Update all pending assignments with form values
    this.pendingProfileAssignments.forEach((assignment) => {
      assignment.dateDebut = this.currentAssignmentForm.dateDebut;
      assignment.dateFin = this.currentAssignmentForm.dateFin;
      assignment.etat = this.currentAssignmentForm.etat;
    });

    // Get profiles to move
    const profilesToMove = this.availableProfiles.filter((p) =>
      this.pendingProfileAssignments.some((a) => a.profileCode === p.codPflPfl)
    );

    // Complete the move
    this.completeProfileMove(profilesToMove, 'available', 'assigned');

    // Close dialog
    this.profileAssignmentDialog = false;
    this.sharedService.handleSuccess(
      {},
      `${profilesToMove.length} profil(s) ajouté(s) avec succès`
    );
  }

  /**
   * Cancels profile assignment dialog
   */
  cancelProfileAssignment(): void {
    this.profileAssignmentDialog = false;
    this.pendingProfileAssignments = [];
  }

  /**
   * Completes the profile move after dialog confirmation
   * @private
   * @param profilesToMove Profiles to move
   * @param sourceType Source list type
   * @param targetType Target list type
   */
  private completeProfileMove(
    profilesToMove: any[],
    sourceType: string,
    targetType: string
  ): void {
    const sourceArray =
      sourceType === 'available'
        ? this.availableProfiles
        : this.assignedProfiles;
    const targetArray =
      targetType === 'available'
        ? this.availableProfiles
        : this.assignedProfiles;

    // Remove from source array
    profilesToMove.forEach((profile) => {
      const mainIndex = sourceArray.findIndex(
        (p) => p.codPflPfl === profile.codPflPfl
      );
      if (mainIndex !== -1) {
        sourceArray.splice(mainIndex, 1);
      }
    });

    // Add to target array with assignment data if moving to assigned
    profilesToMove.forEach((profileToMove) => {
      const existsInTarget = targetArray.some(
        (p) => p.codPflPfl === profileToMove.codPflPfl
      );
      if (!existsInTarget) {
        // Add assignment data if moving to assigned
        if (targetType === 'assigned') {
          const assignmentData = this.pendingProfileAssignments.find(
            (a) => a.profileCode === profileToMove.codPflPfl
          );
          if (assignmentData) {
            profileToMove.datFadhUtpr = assignmentData.dateDebut;
            profileToMove.datdadhutpr = assignmentData.dateFin;
            profileToMove.boolEtatUtpr = assignmentData.etat;
          }
        }
        targetArray.push(profileToMove);
      }
    });

    // Update filtered arrays and handle tracking
    this.applyProfileFilters();

    profilesToMove.forEach((movedProfile) => {
      this.handleProfileMovement(
        movedProfile,
        targetType === 'assigned' ? 'assignedList' : 'availableList'
      );
    });

    // Show success state
    this.dragState = 'success';
    setTimeout(() => {
      this.dragState = 'idle';
    }, 1000);
  }

  // ============================================================================
  // PROFILE INFORMATION DIALOG METHODS
  // ============================================================================

  /**
   * Shows detailed profile information in a dialog
   * @param profile Profile to display
   * @param event Click event (to prevent bubble up)
   */
  showProfileInfo(profile: UnifiedProfile, event: Event): void {
    event.stopPropagation();
    this.selectedProfileInfo = profile;
    this.profileInfoDialog = true;
  }

  /**
   * Closes the profile info dialog
   */
  closeProfileInfo(): void {
    this.profileInfoDialog = false;
    this.selectedProfileInfo = null;
  }
  // ============================================================================
  // APPLICATION SELECTION AND LEGACY METHODS
  // ============================================================================

  /**
   * Handles when applications are loaded for the first time
   * @param applications Array of available applications
   */
  onAppsLoaded(applications: Application[]): void {
    console.log('OnAppsLoaded executing');
    // Legacy method - applications are auto-selected by app-selection component
  }
  /**
   * Handles application selection change
   * @param event Selection change event (application code or null when cleared)
   */
  onAppSelectionChange(event: string | null): void {
    if (event) {
      // Application selected - load profiles
      this.clearUserSelection()
    } else {
      // Application cleared - reset data
      this.utilisateurProfils = [];
      this.selectedUtilisateurProfils = [];
      this.availableProfiles = [];
      this.assignedProfiles = [];
      this.filteredAvailableProfiles = [];
      this.filteredAssignedProfiles = [];
    }
  }

  /**
   * Handles app selection for legacy functionality
   */
  onAppSelected(): void {
    if (!this.selectedUser || !this.selectedAppX) return;

    this.userService
      .getUserRolesInApplication(this.selectedUser, this.selectedAppX)
      .subscribe((roles) => {
        this.userRoles = roles;
        console.log('User roles:', roles);
      });
  }

  /**
   * Handles app selection from dropdown
   * @param event Selection event
   */
  onAppSelect(event: any): void {
    this.selectedApplication = event;
  }

  /**
   * Gets dialog header text based on current action
   * @returns Dialog header string
   */
  get dialogHeader(): string {
    return this.action === ActionType.ADD
      ? 'Ajouter Utilisateur-Profil'
      : 'Modifier Utilisateur-Profil';
  }

  // ============================================================================
  // LEGACY CRUD OPERATIONS FOR USER-PROFILE MANAGEMENT
  // ============================================================================

  /**
   * Opens dialog for creating new user-profile assignment
   */
  openNew(): void {
    this.action = ActionType.ADD;
    this.utilisateurProfilDialog = true;
    this.submitted = false;

    this.utilisateurProfil = {
      codPflPfl: '',
      numMatrUser: this.selectedUser?.mat || '',
      datFadhUtpr: new Date(),
      datdadhutpr: undefined,
      boolEtatUtpr: 1,
    };
    this.getProfilList(this.selectedApplication?.code);
  }

  /**
   * Opens dialog for editing existing user-profile assignment
   * @param item User-profile item to edit
   */
  editUtilisateurProfil(item: UtilisateurProfil): void {
    this.action = ActionType.EDIT;
    this.utilisateurProfilDialog = true;
    document.body.classList.add('dialog-open-blur');
    this.submitted = false;

    this.utilisateurProfil = {
      codPflPfl: item.codPflPfl,
      numMatrUser: item.numMatrUser,
      datFadhUtpr: item.datFadhUtpr ? new Date(item.datFadhUtpr) : undefined,
      datdadhutpr: item.datdadhutpr ? new Date(item.datdadhutpr) : undefined,
      boolEtatUtpr: item.boolEtatUtpr ? 1 : 0,
      ...item,
    };

    this.getProfilList(this.selectedAppCode);
  }

  /**
   * Gets profile list for dropdown
   * @private
   * @param appCode Application code
   */
  private getProfilList(appCode: string): void {
    this.profilService.getProfilList(appCode).subscribe({
      next: (res) => (this.listRols = res?.data || []),
      error: () => {},
    });
  }

  /**
   * Hides the user-profile dialog
   */
  hideDialog(): void {
    this.utilisateurProfilDialog = false;
    this.submitted = false;
    document.body.classList.remove('dialog-open-blur');
  }

  /**
   * Saves new user-profile assignment
   */
  saveUtilisateurProfil(): void {
    this.submitted = true;

    if (
      !this.utilisateurProfil.codPflPfl ||
      !this.utilisateurProfil.numMatrUser
    ) {
      this.sharedService.showWarn(
        'Veuillez renseigner le profil et le matricule',
        'Validation'
      );
      return;
    }

    if (
      !this.validateDateRange(
        this.utilisateurProfil.datFadhUtpr,
        this.utilisateurProfil.datdadhutpr
      )
    ) {
      this.sharedService.showWarn(
        'La date de fin ne peut pas être antérieure à la date de début',
        'Validation'
      );
      return;
    }

    const payload = this.toPayload(this.utilisateurProfil);

    this.utilisateurProfilService.create(payload).subscribe({
      next: (response) => {
        this.sharedService.handleSuccess(response, 'Utilisateur-Profil créé');
        this.utilisateurProfilDialog = false;
      },
      error: (error) =>
        this.sharedService.handleError(
          error,
          "Erreur lors de l'ajout de l'utilisateur-profil"
        ),
    });
  }

  /**
   * Updates existing user-profile assignment
   */
  updateUtilisateurProfil(): void {
    if (!this.utilisateurProfil) return;

    if (typeof this.utilisateurProfil.datFadhUtpr === 'string') {
      this.utilisateurProfil.datFadhUtpr =
        this.parseFrString(this.utilisateurProfil.datFadhUtpr) ?? undefined;
    }
    if (typeof this.utilisateurProfil.datdadhutpr === 'string') {
      this.utilisateurProfil.datdadhutpr =
        this.parseFrString(this.utilisateurProfil.datdadhutpr) ?? undefined;
    }

    if (
      !this.validateDateRange(
        this.utilisateurProfil.datFadhUtpr,
        this.utilisateurProfil.datdadhutpr
      )
    ) {
      this.sharedService.showWarn(
        'La date de fin ne peut pas être antérieure à la date de début',
        'Validation'
      );
      return;
    }

    const payload = this.toPayload(this.utilisateurProfil);

    this.utilisateurProfilService.update(payload).subscribe({
      next: (response) => {
        this.sharedService.handleSuccess(
          response,
          'Utilisateur-Profil modifié'
        );
        this.utilisateurProfilDialog = false;
      },
      error: (error) =>
        this.sharedService.handleError(error, 'Erreur lors de la modification'),
    });
  }

  /**
   * Deletes user-profile assignment
   * @param item User-profile item to delete
   */
  deleteUtilisateurProfil(item: UtilisateurProfil): void {
    this.sharedService.confirmDelete(
      'Voulez-vous supprimer cet enregistrement ?',
      () => {
        if (!item.codPflPfl || !item.numMatrUser) {
          this.sharedService.handleError(
            new Error('Invalid data'),
            'Identifiants requis manquants'
          );
          return;
        }

        this.utilisateurProfilService
          .delete(item.codPflPfl, item.numMatrUser)
          .subscribe({
            next: (response) => {
              this.sharedService.handleSuccess(response, 'Supprimé');
            },
            error: (error) =>
              this.sharedService.handleError(
                error,
                'Erreur lors de la suppression'
              ),
          });
      }
    );
  }

  // ============================================================================
  // UTILITY AND HELPER METHODS
  // ============================================================================

  /**
   * Converts user-profile object to API payload format
   * @private
   * @param src Source user-profile object
   * @returns API payload object
   */
  private toPayload(src: UtilisateurProfil): any {
    return {
      codPflPfl: src.codPflPfl?.trim(),
      numMatrUser: src.numMatrUser?.trim(),
      datFadhUtpr: this.dateToFr(src.datFadhUtpr),
      datdadhutpr: this.dateToFr(src.datdadhutpr),
      boolEtatUtpr: this.toInt01(src.boolEtatUtpr as any),
    };
  }

  /**
   * Converts value to 0 or 1 integer
   * @private
   * @param v Value to convert
   * @returns 0 or 1
   */
  private toInt01(v: unknown): 0 | 1 {
    return v === 1 || v === '1' || v === true || v === 'true' ? 1 : 0;
  }

  /**
   * Formats date for display
   * @param d Date to format
   * @returns Formatted date string
   */
  fmtDate(d?: Date | string | null): string {
    if (!d) return '';
    const date = typeof d === 'string' ? this.parseFrString(d) : d;
    if (!date) return '';
    return [
      `${date.getDate()}`.padStart(2, '0'),
      `${date.getMonth() + 1}`.padStart(2, '0'),
      `${date.getFullYear()}`,
    ].join('/');
  }

  /**
   * Parses French date string (DD/MM/YYYY)
   * @private
   * @param s Date string to parse
   * @returns Date object or null
   */
  private parseFrString(s: string): Date | null {
    const parts = s?.split('/') ?? [];
    if (parts.length !== 3) return null;
    const [dd, mm, yyyy] = parts.map(Number);
    const d = new Date(yyyy, mm - 1, dd);
    return d.getFullYear() === yyyy &&
      d.getMonth() === mm - 1 &&
      d.getDate() === dd
      ? d
      : null;
  }

  /**
   * Validates date range
   * @private
   * @param start Start date
   * @param end End date
   * @returns True if range is valid
   */
  private validateDateRange(start?: Date | null, end?: Date | null): boolean {
    if (!start || !end) return true;
    const s = new Date(
      start.getFullYear(),
      start.getMonth(),
      start.getDate()
    ).getTime();
    const e = new Date(
      end.getFullYear(),
      end.getMonth(),
      end.getDate()
    ).getTime();
    return e >= s;
  }

  /**
   * Converts date to French format string
   * @private
   * @param d Date to convert
   * @returns French format date string or null
   */
  private dateToFr(d: Date | string | null | undefined): string | null {
    if (!d) return null;
    const dt = d instanceof Date ? d : new Date(d);
    if (isNaN(dt.getTime())) return null;
    const dd = String(dt.getDate()).padStart(2, '0');
    const mm = String(dt.getMonth() + 1).padStart(2, '0');
    const yyyy = dt.getFullYear();
    return `${dd}/${mm}/${yyyy}`;
  }

  /**
   * Track profiles for performance optimization
   * @param index Array index
   * @param profile Profile object
   * @returns Profile code for tracking
   */
  trackByProfileCode(index: number, profile: any): string {
    return profile.codPflPfl;
  }

  /**
   * Gets profile display name for confirmation step
   * @param profileCode Profile code to get display name for
   * @returns Display name or profile code
   */
  getProfileDisplayName(profileCode: string): string {
    const allProfiles = [...this.availableProfiles, ...this.assignedProfiles];
    const profile = allProfiles.find((p) => p.codPflPfl === profileCode);
    return profile
      ? profile.displayName || profile.libpflpfl || profileCode
      : profileCode;
  }

  /**
   * Window resize handler to update icons based on screen size
   * @param event Resize event
   */
  @HostListener('window:resize', ['$event'])
  onWindowResize(event: any): void {
    this.cdr.detectChanges();
  }
  // ============================================================================
  // PROFILE DISPLAY AND STATUS METHODS
  // ============================================================================

  /**
   * Gets status icon for profile based on its state
   * @param profile Profile to get status icon for
   * @returns Icon class string
   */
  getStatusIcon(profile: UnifiedProfile): string {
    switch (profile.boolEtatPfl) {
      case '1':
        return 'pi-check-circle text-success';
      case 'future':
        return 'pi-clock';
      case '0':
        return 'pi-times-circle text-danger';
      default:
        return 'pi-circle';
    }
  }

  /**
   * Gets status color for profile
   * @param profile Profile to get status color for
   * @returns CSS color class
   */
  getStatusColor(profile: UnifiedProfile): string {
    switch (profile.boolEtatPfl) {
      case '1':
        return 'text-success';
      case 'future':
        return 'text-warning';
      case '0':
        return 'text-danger';
      default:
        return 'text-muted';
    }
  }

  /**
   * Gets profile category display name
   * @param profile Profile to get category for
   * @returns Category name
   */
  getProfileCategory(profile: UnifiedProfile): string {
    return profile.categoryName;
  }

  /**
   * Gets profile access hours
   * @param profile Profile to get access hours for
   * @returns Formatted access hours
   */
  getAccessHours(profile: UnifiedProfile): string {
    return profile.accessHours;
  }

  /**
   * Gets assignment dates for tooltip
   * @param profile Profile to get assignment dates for
   * @returns Formatted assignment dates
   */
  getAssignmentDates(profile: UnifiedProfile): string {
    return profile.assignmentDates || '';
  }

  /**
   * Gets tooltip content for available profiles
   * @param profile Profile to get tooltip for
   * @returns HTML tooltip content
   */
  getProfileTooltipContent(profile: UnifiedProfile): string {
    return `
      <div class="profile-tooltip">
        <h5>${profile.displayName}</h5>
        <div class="tooltip-section">
          <strong>Code:</strong> ${profile.codPflPfl}<br>
          <strong>Catégorie:</strong> ${profile.categoryName}<br>
          <strong>Niveau:</strong> ${profile.codNivhPfl || 'N/A'}<br>
          <strong>Accès:</strong> ${profile.accessHours}<br>
          <strong>Jours ouvrés:</strong> ${
            profile.boolJouvPfl === '1' ? 'Oui' : 'Non'
          }
        </div>
      </div>
    `;
  }

  /**
   * Gets enhanced tooltip content for assigned profiles
   * @param profile Profile to get tooltip for
   * @returns HTML tooltip content with assignment info
   */
  getAssignedProfileTooltipContent(profile: UnifiedProfile): string {
    const baseTooltip = this.getProfileTooltipContent(profile);

    if (profile.assignmentDates) {
      const assignmentInfo = `
        <div class="assignment-section">
          <h6>📋 Informations d'attribution</h6>
          <strong>Période:</strong> ${profile.assignmentDates}<br>
          <strong>Statut:</strong> ${this.getAssignmentStatusText(
            profile.assignmentStatus
          )}<br>
          <strong>Actif:</strong> ${profile.isActive ? 'Oui' : 'Non'}
        </div>
      `;
      return baseTooltip + assignmentInfo;
    }

    return baseTooltip;
  }

  /**
   * Gets assignment status text in French
   * @private
   * @param status Assignment status
   * @returns Localized status text
   */
  private getAssignmentStatusText(status: string): string {
    switch (status) {
      case 'available':
        return '✅ Actif';
      case 'future':
        return '⏳ À venir';
      case 'expired':
        return '❌ Expiré';
      default:
        return '❓ Inconnu';
    }
  }

  /**
   * Gets level description based on level code
   * @param level Level code
   * @returns Human-readable level description
   */
  getLevelDescription(level: string): string {
    const levelDescriptions: { [key: string]: string } = {
      '1': "Niveau Agence - Accès limité aux fonctions d'agence",
      '2': 'Niveau Direction Régionale - Accès étendu dir. Rég + Agence',
      '3': 'Niveau Centrale - Accès complet aux fonctionnalités',
      '4': 'Niveau expert - Accès administrateur avec privilèges étendus',
      '5': 'Niveau administrateur - Contrôle total du système',
    };

    return levelDescriptions[level] || `Niveau personnalisé (${level})`;
  }

  // ============================================================================
  // PROFILE FILTERING METHODS
  // ============================================================================

  /**
   * Initializes profile filter options based on available profiles
   * @private
   */
  private initializeProfileFilterOptions(): void {
    // Extract unique levels
    const levels = [
      ...new Set(
        this.availableProfiles.map((p) => p.codNivhPfl).filter(Boolean)
      ),
    ].sort();
    this.levelFilterOptions = levels.map((level) => ({
      label: `Niveau ${level}`,
      value: level,
    }));

    // Extract unique categories
    const categories = [
      ...new Set(
        this.availableProfiles.map((p) => p.codCatpPfl).filter(Boolean)
      ),
    ].sort();
    this.categoryFilterOptions = categories.map((category) => ({
      label: category,
      value: category,
    }));
  }

  /**
   * Applies filters to available profiles
   */
  applyAvailableProfileFilters(): void {
    let filtered = [...this.availableProfiles];

    // Apply search term filter
    if (
      this.availableProfileFilters.searchTerm &&
      this.availableProfileFilters.searchTerm.trim()
    ) {
      const searchTerm = this.availableProfileFilters.searchTerm
        .toLowerCase()
        .trim();
      filtered = filtered.filter((profile) =>
        (profile.libpflpfl || profile.displayName || profile.codPflPfl || '')
          .toString()
          .toLowerCase()
          .includes(searchTerm)
      );
    }

    // Apply level filter
    if (this.availableProfileFilters.selectedLevel) {
      filtered = filtered.filter(
        (profile) =>
          profile.codNivhPfl === this.availableProfileFilters.selectedLevel
      );
    }

    // Apply category filter
    if (this.availableProfileFilters.selectedCategory) {
      filtered = filtered.filter(
        (profile) =>
          profile.codCatpPfl === this.availableProfileFilters.selectedCategory
      );
    } // Apply status filter
    if (
      this.availableProfileFilters.selectedStatus !== null &&
      this.availableProfileFilters.selectedStatus !== undefined
    ) {
      const expectedStatus = this.availableProfileFilters.selectedStatus;
      filtered = filtered.filter(
        (profile) => Number(profile.boolEtatPfl) === expectedStatus
      );
    }

    this.filteredAvailableProfiles = filtered;
  }

  /**
   * Applies filters to assigned profiles
   */
  applyAssignedProfileFilters(): void {
    let filtered = [...this.assignedProfiles];

    // Apply search term filter
    if (
      this.assignedProfileFilters.searchTerm &&
      this.assignedProfileFilters.searchTerm.trim()
    ) {
      const searchTerm = this.assignedProfileFilters.searchTerm
        .toLowerCase()
        .trim();
      filtered = filtered.filter((profile) =>
        (profile.libpflpfl || profile.displayName || profile.codPflPfl || '')
          .toString()
          .toLowerCase()
          .includes(searchTerm)
      );
    }

    // Apply level filter
    if (this.assignedProfileFilters.selectedLevel) {
      filtered = filtered.filter(
        (profile) =>
          profile.codNivhPfl === this.assignedProfileFilters.selectedLevel
      );
    }

    // Apply category filter
    if (this.assignedProfileFilters.selectedCategory) {
      filtered = filtered.filter(
        (profile) =>
          profile.codCatpPfl === this.assignedProfileFilters.selectedCategory
      );
    } // Apply status filter
    if (
      this.assignedProfileFilters.selectedStatus !== null &&
      this.assignedProfileFilters.selectedStatus !== undefined
    ) {
      const expectedStatus = this.assignedProfileFilters.selectedStatus;
      filtered = filtered.filter(
        (profile) => Number(profile.boolEtatUtpr) === expectedStatus
      );
    }

    this.filteredAssignedProfiles = filtered;
  }

  /**
   * Applies all profile filters (both available and assigned)
   */
  applyProfileFilters(): void {
    this.applyAvailableProfileFilters();
    this.applyAssignedProfileFilters();
  }

  /**
   * Clears all profile filters
   */
  clearAllProfileFilters(): void {
    this.availableProfileFilters = {
      searchTerm: '',
      selectedLevel: null,
      selectedCategory: null,
      selectedStatus: null,
    };
    this.assignedProfileFilters = {
      searchTerm: '',
      selectedLevel: null,
      selectedCategory: null,
      selectedStatus: null,
    };
    this.applyProfileFilters();
  }

  /**
   * Clears available profile filters only
   */
  clearAvailableProfileFilters(): void {
    this.availableProfileFilters = {
      searchTerm: '',
      selectedLevel: null,
      selectedCategory: null,
      selectedStatus: null,
    };
    this.applyAvailableProfileFilters();
  }

  /**
   * Clears assigned profile filters only
   */
  clearAssignedProfileFilters(): void {
    this.assignedProfileFilters = {
      searchTerm: '',
      selectedLevel: null,
      selectedCategory: null,
      selectedStatus: null,
    };
    this.applyAssignedProfileFilters();
  }

  /**
   * Handles filter tab change to switch between available and assigned profile filters
   * @param tabIndex Tab index (0 for available, 1 for assigned)
   */
  onFilterTabChange(tabIndex: number): void {
    console.log(
      'Filter tab changed to:',
      tabIndex === 0 ? 'Available Profiles' : 'Assigned Profiles'
    );
  }

  /**
   * Checks if any profile filters are currently active
   * @returns True if any filters are active
   */
  hasActiveProfileFilters(): boolean {
    return (
      this.hasActiveAvailableProfileFilters() ||
      this.hasActiveAssignedProfileFilters()
    );
  }

  /**
   * Checks if available profile filters are active
   * @returns True if available filters are active
   */
  hasActiveAvailableProfileFilters(): boolean {
    return !!(
      this.availableProfileFilters.searchTerm?.trim() ||
      this.availableProfileFilters.selectedLevel ||
      this.availableProfileFilters.selectedCategory ||
      this.availableProfileFilters.selectedStatus !== null
    );
  }

  /**
   * Checks if assigned profile filters are active
   * @returns True if assigned filters are active
   */
  hasActiveAssignedProfileFilters(): boolean {
    return !!(
      this.assignedProfileFilters.searchTerm?.trim() ||
      this.assignedProfileFilters.selectedLevel ||
      this.assignedProfileFilters.selectedCategory ||
      this.assignedProfileFilters.selectedStatus !== null
    );
  }
  /**
   * Handles profile action button clicks (+ and - buttons)
   * @param action The action to perform ('add' or 'remove')
   * @param profile The profile to act upon
   * @param event The click event
   */
  onProfileAction(action: string, profile: UnifiedProfile, event: Event): void {
    event.stopPropagation(); // Prevent profile selection

    switch (action) {
      case 'add':
        // Move single profile from available to assigned
        this.moveSingleProfile(profile, 'available', 'assigned');
        break;
      case 'remove':
        // Move single profile from assigned to available
        this.moveSingleProfile(profile, 'assigned', 'available');
        break;
      default:
        console.warn(`Unknown profile action: ${action}`);
    }
  }
  /**
   * Moves a single profile between lists
   * @private
   * @param profile Profile to move
   * @param sourceType Source list type
   * @param targetType Target list type
   */
  private moveSingleProfile(
    profile: UnifiedProfile,
    sourceType: 'available' | 'assigned',
    targetType: 'available' | 'assigned'
  ): void {
    const sourceArray =
      sourceType === 'available'
        ? this.availableProfiles
        : this.assignedProfiles;
    const targetArray =
      targetType === 'available'
        ? this.availableProfiles
        : this.assignedProfiles;

    // Find profile in source (but don't remove yet)
    const index = sourceArray.findIndex(
      (p) => p.codPflPfl === profile.codPflPfl
    );
    if (index === -1) return;

    // Handle assignment dialog for available to assigned moves
    if (targetType === 'assigned') {
      // Don't remove the profile yet - let completeProfileMove handle it
      this.openProfileAssignmentDialog([profile]);
      return;
    }

    // Direct move for assigned to available (remove and add)
    const profileToMove = sourceArray.splice(index, 1)[0];
    targetArray.push(profileToMove);
    this.handleProfileMovement(profileToMove, 'availableList');

    this.applyProfileFilters();
    this.showSuccessAnimation();

    // Show success message
    this.sharedService.showSuccess('Profil retiré avec succès');
  }

  // ============================================================================
  // PACK-RELATED METHODS
  // ============================================================================
  /**
   * Load available packs from backend
   */ loadAvailablePacks(): void {
    console.log('Loading available packs...');
    this.loadingPacks = true;

    this.packService.getAll().subscribe({
      next: (packs) => {
        console.log('Raw packs received from API:', packs);

        // Transform backend Pack to ProfilePack format
        const transformedPacks = packs.map((pack) =>
          this.transformToProfilePack(pack)
        );

        // Load profile codes for each pack
        this.loadPackProfiles(transformedPacks);
      },
      error: (error) => {
        console.error('Error loading packs:', error);
        this.sharedService.showError('Erreur lors du chargement des packs');
        this.loadingPacks = false;
      },
    });
  }

  /**
   * Load profile codes for each pack from PACK_PROFIL table
   */
  private loadPackProfiles(packs: ProfilePack[]): void {
    if (packs.length === 0) {
      this.availablePacks = [];
      this.filteredAvailablePacks = [];
      this.loadingPacks = false;
      this.checkPendingUserPacksLoad();
      return;
    }

    let completedCount = 0;
    const totalPacks = packs.length;

    packs.forEach((pack) => {
      this.packProfileService.getPackProfilesByPack(pack.code).subscribe({
        next: (response) => {
          const packProfiles = response?.data || [];
          pack.profileCodes = packProfiles.map((pp: any) => pp.codPflPfl);

          console.log(
            `Pack ${pack.code} has ${pack.profileCodes.length} profiles`
          );

          completedCount++;
          if (completedCount === totalPacks) {
            // All packs loaded, update the arrays
            this.availablePacks = packs;
            this.filteredAvailablePacks = [...this.availablePacks];
            this.loadingPacks = false;

            console.log(
              'Available packs loaded with profile counts:',
              this.availablePacks.length
            );
            console.log('Packs with profiles:', this.availablePacks);

            // Check if user packs need to be loaded
            this.checkPendingUserPacksLoad();
          }
        },
        error: (error) => {
          console.error(`Error loading profiles for pack ${pack.code}:`, error);
          pack.profileCodes = []; // Set empty array on error

          completedCount++;
          if (completedCount === totalPacks) {
            this.availablePacks = packs;
            this.filteredAvailablePacks = [...this.availablePacks];
            this.loadingPacks = false;

            // Check if user packs need to be loaded
            this.checkPendingUserPacksLoad();
          }
        },
      });
    });
  }

  /**
   * Check if there's a pending user packs load and execute it
   * @private
   */
  private checkPendingUserPacksLoad(): void {
    if (this.pendingUserPacksLoad && this.selectedUser) {
      console.log('Loading pending user packs...');
      this.pendingUserPacksLoad = false;
      this.loadUserPacks();
    }
  }
  /**
   * Load packs assigned to current user
   * Note: This method requires availablePacks to be loaded first
   */
  loadUserPacks(): void {
    console.log('thhhhhe user ', this.selectedUser);
    if (!this.selectedUser?.mat) {
      console.warn('No user selected for loading packs');
      return;
    }

    const matricule = this.selectedUser.numMatrUser || this.selectedUser.mat;
    console.log('Loading packs for user:', matricule);

    // If availablePacks not yet loaded, wait and retry
    if (this.loadingPacks) {
      console.log(
        'Packs still loading, will retry loadUserPacks after packs are loaded'
      );
      // Set a flag to reload user packs after available packs are loaded
      this.pendingUserPacksLoad = true;
      return;
    }

    this.utilisateurPackService.getPacksByMatricule(matricule).subscribe({
      next: (userPacks) => {
        console.log('User packs received:', userPacks);

        // Get all packs (both currently available and assigned) to find the user's packs
        const allPacks = [...this.availablePacks, ...this.assignedPacks];

        // Get full pack details for assigned packs
        this.assignedPacks = allPacks.filter((pack) =>
          userPacks.some((up) => up.codPackPack === pack.code)
        );

        // Available packs are those not assigned to the user
        this.availablePacks = allPacks.filter(
          (pack) => !userPacks.some((up) => up.codPackPack === pack.code)
        );

        this.filteredAssignedPacks = [...this.assignedPacks];
        this.filteredAvailablePacks = [...this.availablePacks];

        console.log('Assigned packs:', this.assignedPacks.length);
        console.log('Available packs:', this.availablePacks.length);
      },
      error: (error) => {
        console.error('Error loading user packs:', error);
        this.sharedService.showError(
          'Erreur lors du chargement des packs utilisateur'
        );
      },
    });
  }
  /**
   * Transform backend Pack to ProfilePack interface
   */
  private transformToProfilePack(backendPack: Pack): ProfilePack {
    console.log('Transforming pack:', backendPack);

    return {
      id: backendPack.codPackPack || '',
      code: backendPack.codPackPack || '',
      name: backendPack.libPackPack || 'Sans nom',
      description: backendPack.descPack || '',
      category: this.mapToPackCategory(backendPack.codCatpPfl),
      status:
        backendPack.boolActifPack === 1 || backendPack.boolEtat === 1
          ? PackStatus.ACTIVE
          : PackStatus.INACTIVE,
      profileCodes: [], // Will be populated from PACK_PROFIL table
      profiles: [], // Will be populated after loading profiles
      createdDate: backendPack.datCrePack,
      createdBy: backendPack.userCrePack,
    };
  }

  /**
   * Map backend category code to PackCategory enum
   */
  private mapToPackCategory(codCatp?: string): PackCategory {
    // Handle numeric category codes from backend
    const categoryMap: Record<string, PackCategory> = {
      '0': PackCategory.CUSTOM,
      '1': PackCategory.ADMINISTRATION,
      '2': PackCategory.COMMERCIAL,
      '3': PackCategory.FINANCE,
      '4': PackCategory.OPERATIONS,
      '5': PackCategory.MANAGEMENT,
      // Also support string-based codes
      ADM: PackCategory.ADMINISTRATION,
      ADMINISTRATION: PackCategory.ADMINISTRATION,
      COM: PackCategory.COMMERCIAL,
      COMMERCIAL: PackCategory.COMMERCIAL,
      FIN: PackCategory.FINANCE,
      FINANCE: PackCategory.FINANCE,
      OPS: PackCategory.OPERATIONS,
      OPERATIONS: PackCategory.OPERATIONS,
      MGT: PackCategory.MANAGEMENT,
      MANAGEMENT: PackCategory.MANAGEMENT,
    };

    const mappedCategory = categoryMap[codCatp || ''] || PackCategory.CUSTOM;
    console.log(`Mapping category ${codCatp} to ${mappedCategory}`);

    return mappedCategory;
  }
  /**
   * Handle pack assignment
   */
  onPackAssign(pack: ProfilePack): void {
    if (!this.selectedUser) {
      this.sharedService.showWarn('Veuillez sélectionner un utilisateur');
      return;
    }

    const userName = `${
      this.selectedUser.prenUtilUtl || this.selectedUser.prenom || ''
    } ${this.selectedUser.nomUtilUtl || this.selectedUser.nom || ''}`.trim();

    // Show confirmation dialog
    this.confirmationService.confirm({
      message: `Voulez-vous assigner le pack "${pack.name}" à l'utilisateur ${userName}?`,
      header: "Confirmation d'assignation de pack",
      icon: 'pi pi-question-circle',
      acceptLabel: 'Oui, assigner',
      rejectLabel: 'Annuler',
      acceptButtonStyleClass: 'p-button-success',
      rejectButtonStyleClass: 'p-button-secondary',
      accept: () => {
        this.assignPackToUser(pack);
      },
    });
  }
  /**
   * Assign pack to user
   */ private assignPackToUser(pack: ProfilePack): void {
    if (!this.selectedUser) return;

    this.loading = true;

    const matricule = this.selectedUser.numMatrUser || this.selectedUser.mat;

    const request: UserPacksAssignmentRequest = {
      userMatricule: matricule,
      assignedPacks: [
        {
          packCode: pack.code,
          dateDebut: formatPackDateForApi(new Date()),
          dateFin: null,
          etat: 1,
        },
      ],
      revokedPacks: [],
    };

    console.log('Assigning pack:', request);

    this.utilisateurPackService.assignMultiplePacks(request).subscribe({
      next: (result: BatchAssignmentResult) => {
        this.loading = false;

        if (isCompleteSuccess(result)) {
          this.sharedService.showSuccess(
            `Pack "${pack.name}" assigné avec succès`,
            `${result.successCount} pack(s) assigné(s)`
          );

          // Move pack from available to assigned
          this.availablePacks = this.availablePacks.filter(
            (p) => p.id !== pack.id
          );
          this.assignedPacks.push(pack);
          this.filteredAvailablePacks = [...this.availablePacks];
          this.filteredAssignedPacks = [...this.assignedPacks];

          // Reload user profiles to show the newly assigned profiles
          this.loadUserProfiles(this.selectedUser);
        } else {
          const failedItems = getFailedItemsArray(result);
          const errorMsg =
            failedItems.length > 0
              ? failedItems.map((f) => f.error).join(', ')
              : 'Erreur inconnue';
          this.sharedService.showError(
            `Erreur lors de l'assignation: ${errorMsg}`,
            'Erreur'
          );
        }
      },
      error: (error) => {
        this.loading = false;
        console.error('Error assigning pack:', error);
        this.sharedService.showError(
          "Une erreur est survenue lors de l'assignation du pack",
          'Erreur'
        );
      },
    });
  }
  /**
   * Handle pack unassignment
   */
  onPackUnassign(pack: ProfilePack): void {
    if (!this.selectedUser) return;

    this.confirmationService.confirm({
      message: `Voulez-vous retirer le pack "${pack.name}" de l'utilisateur?`,
      header: 'Confirmation de retrait de pack',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Oui, retirer',
      rejectLabel: 'Annuler',
      acceptButtonStyleClass: 'p-button-danger',
      rejectButtonStyleClass: 'p-button-secondary',
      accept: () => {
        this.unassignPackFromUser(pack);
      },
    });
  }
  /**
   * Unassign pack from user
   */
  private unassignPackFromUser(pack: ProfilePack): void {
    if (!this.selectedUser) return;

    this.loading = true;

    const matricule = this.selectedUser.numMatrUser || this.selectedUser.mat;
    const request: UserPacksAssignmentRequest = {
      userMatricule: matricule,
      assignedPacks: [],
      revokedPacks: [pack.code],
    };

    console.log('Unassigning pack:', request);

    this.utilisateurPackService.assignMultiplePacks(request).subscribe({
      next: (result: BatchAssignmentResult) => {
        this.loading = false;

        if (isCompleteSuccess(result)) {
          this.sharedService.showSuccess(
            `Pack "${pack.name}" retiré avec succès`,
            `${result.successCount} pack(s) révoqué(s)`
          );

          // Move pack from assigned to available
          this.assignedPacks = this.assignedPacks.filter(
            (p) => p.id !== pack.id
          );
          this.availablePacks.push(pack);
          this.filteredAvailablePacks = [...this.availablePacks];
          this.filteredAssignedPacks = [...this.assignedPacks];

          // Reload user profiles to show the changes
          this.loadUserProfiles(this.selectedUser);
        } else {
          const failedItems = getFailedItemsArray(result);
          const errorMsg =
            failedItems.length > 0
              ? failedItems.map((f) => f.error).join(', ')
              : 'Erreur inconnue';
          this.sharedService.showError(
            `Erreur lors du retrait: ${errorMsg}`,
            'Erreur'
          );
        }
      },
      error: (error) => {
        this.loading = false;
        console.error('Error unassigning pack:', error);
        this.sharedService.showError(
          'Une erreur est survenue lors du retrait du pack',
          'Erreur'
        );
      },
    });
  }

  /**
   * Show pack details dialog
   */
  onPackInfo(pack: ProfilePack): void {
    console.log('Pack info requested:', pack);

    // TODO: Implement pack details dialog
    // For now, show pack information in a toast
    this.sharedService.showInfo(
      `Pack: ${pack.name}\nCode: ${pack.code}\nCatégorie: ${
        pack.category
      }\nStatut: ${pack.status}\nDescription: ${
        pack.description || 'Aucune description'
      }`,
      'Détails du pack'
    );
  }

  /**
   * Handle pack drag and drop
   */
  onPackDrop(event: CdkDragDrop<ProfilePack[]>): void {
    if (event.previousContainer === event.container) {
      // Reordering within same list - no action needed
      return;
    }

    const pack = event.previousContainer.data[event.previousIndex];

    if (event.container.id === 'assignedPackList') {
      // Dragged from available to assigned
      this.onPackAssign(pack);
    } else {
      // Dragged from assigned to available
      this.onPackUnassign(pack);
    }
  }

  /**
   * Handle cart packs change from pack selector
   * Syncs cart state with packsToAssign for persistence across step navigation
   */ onCartPacksChange(packs: ProfilePack[]): void {
    console.log('🛒 Cart updated:', packs.length, 'packs');
    // Log detailed pack info including profileCodes
    packs.forEach((pack) => {
      console.log(
        `🛒 Pack in cart: "${pack.name}" (${pack.code}) - profileCodes: [${(
          pack.profileCodes || []
        ).join(', ')}]`
      );
    });
    this.packsToAssign = [...packs];
  }

  /**
   * Handle batch pack assignment from cart (Assigner tous button)
   * Moves to confirmation step to show all packs and their profiles
   */
  onBatchPackAssign(packs: ProfilePack[]): void {
    if (!this.selectedUser) {
      this.sharedService.showWarn('Veuillez sélectionner un utilisateur');
      return;
    }

    if (!packs || packs.length === 0) {
      this.sharedService.showWarn('Aucun pack à assigner');
      return;
    }

    console.log('🎯 Batch pack assignment initiated:', packs.length, 'packs');

    // Store packs pending assignment
    this.packsToAssign = [...packs];

    // Set flag to indicate batch pack assignment mode
    this.isBatchPackAssignment = true;

    // Move to confirmation step (step 3)
    this.activeStep = 3;

    // Show info toast
    this.sharedService.showInfo(
      `${packs.length} pack(s) prêt(s) pour l'assignation. Vérifiez et confirmez.`,
      'Confirmation requise'
    );
  } // ============================================================================
  // END OF PACK-RELATED METHODS
  // ============================================================================

  // ============================================================================
  // PROFILE CLONING METHODS
  // ============================================================================
  /**
   * Opens the profile clone dialog
   */
  openProfileCloneDialog(): void {
    if (!this.selectedUser) {
      this.sharedService.showWarn(
        'Veuillez sélectionner un utilisateur source'
      );
      return;
    }

    // Load all users for cloning if not already loaded
    if (this.allUsersForCloning.length === 0) {
      this.loadAllUsersForCloning();
    }

    // Prepare profiles for cloning from current user
    this.profilesForCloning = this.assignedProfiles.map((profile) => ({
      codPflPfl: profile.codPflPfl,
      libpflpfl: profile.libpflpfl,
      displayName:
        profile.displayName || profile.libpflpfl || profile.codPflPfl,
      dateDebut: profile.datFadhUtpr,
      dateFin: profile.datdadhutpr,
      boolEtatUtpr: profile.boolEtatUtpr,
      selected: true,
    }));

    this.cloneResults = [];
    this.cloneProgress = 0;
    this.cloningInProgress = false;
    this.profileCloneDialogVisible = true;
  }

  /**
   * Handles clone dialog visibility change
   */
  onCloneDialogVisibleChange(visible: boolean): void {
    this.profileCloneDialogVisible = visible;
    if (!visible) {
      this.cloneResults = [];
      this.cloneProgress = 0;
      this.cloningInProgress = false;
    }
  }

  /**
   * Loads profiles for a source user (for clone dialog)
   */
  onLoadSourceProfiles(sourceUser: any): void {
    if (!sourceUser?.mat) {
      this.profilesForCloning = [];
      return;
    }

    const matricule = sourceUser.numMatrUser || sourceUser.mat;

    this.utilisateurProfilService
      .getUserProfiles(this.selectedAppCode, matricule)
      .subscribe({
        next: (res: any) => {
          const userProfiles = res.data || [];
          this.profilesForCloning = userProfiles.map((item: any) => ({
            codPflPfl: item.codPflPfl,
            libpflpfl: item.libpflpfl || item.profil?.libpflpfl,
            displayName:
              item.profil?.libpflpfl || item.libpflpfl || item.codPflPfl,
            dateDebut: item.datFadhUtpr,
            dateFin: item.datdadhutpr,
            boolEtatUtpr: item.boolEtatUtpr,
            selected: true,
          }));
        },
        error: (error) => {
          console.error('Error loading source user profiles:', error);
          this.sharedService.showError(
            'Erreur lors du chargement des profils source'
          );
          this.profilesForCloning = [];
        },
      });
  }

  /**
   * Executes the profile cloning operation
   */
  onExecuteClone(event: {
    sourceUser: any;
    targetUsers: CloneTargetUser[];
    selectedProfiles: CloneableProfile[];
    options: CloneOptions;
  }): void {
    const { sourceUser, targetUsers, selectedProfiles, options } = event;

    if (targetUsers.length === 0 || selectedProfiles.length === 0) {
      this.sharedService.showWarn(
        'Aucun profil ou utilisateur cible sélectionné'
      );
      return;
    }

    this.cloningInProgress = true;
    this.cloneProgress = 0;
    this.cloneResults = [];

    // Process each target user (pass sourceUser for notification context)
    this.cloneProfilesToTargets(
      targetUsers,
      selectedProfiles,
      options,
      sourceUser
    );
  }
  /**
   * Clone profiles to multiple target users
   * @private
   */
  private async cloneProfilesToTargets(
    targetUsers: CloneTargetUser[],
    profiles: CloneableProfile[],
    options: CloneOptions,
    sourceUser?: any
  ): Promise<void> {
    const totalTargets = targetUsers.length;
    let completedCount = 0;
    const sourceUserName =
      sourceUser?.nom_prenom || sourceUser?.mat || 'Source';

    for (const targetUser of targetUsers) {
      try {
        const result = await this.cloneProfilesToSingleUser(
          targetUser,
          profiles,
          options
        );
        this.cloneResults = [...this.cloneResults, result]; // Create new array reference
        console.log(result);
        console.log(this.cloneResults);
      } catch (error: any) {
        console.log(error);
        this.cloneResults = [
          ...this.cloneResults,
          {
            targetUser: targetUser.nom_prenom || targetUser.mat,
            success: false,
            clonedCount: 0,
            skippedCount: 0,
            errorMessage: error.message || 'Erreur inconnue',
          },
        ];
      }

      completedCount++;
      this.cloneProgress = Math.round((completedCount / totalTargets) * 100);

      // Force change detection to update the UI
      this.cdr.detectChanges();
    }

    this.cloningInProgress = false;
    this.cdr.detectChanges();

    // Show summary toast
    const successCount = this.cloneResults.filter((r) => r.success).length;
    const failedCount = this.cloneResults.filter((r) => !r.success).length;

    if (failedCount === 0) {
      this.sharedService.showSuccess(
        `Profils clonés avec succès vers ${successCount} utilisateur(s)`,
        'Clonage terminé'
      );
    } else if (successCount > 0) {
      this.sharedService.showWarn(
        `${successCount} succès, ${failedCount} échec(s)`,
        'Clonage partiel'
      );
    } else {
      this.sharedService.showError(
        `Échec du clonage pour tous les utilisateurs`,
        'Erreur de clonage'
      );
    }

    // Send detailed results to notification panel
    this.notificationService.handleCloneResults(
      this.cloneResults,
      sourceUserName
    );
  }

  /**
   * Clone profiles to a single target user
   * @private
   */
  private cloneProfilesToSingleUser(
    targetUser: CloneTargetUser,
    profiles: CloneableProfile[],
    options: CloneOptions
  ): Promise<CloneResult> {
    return new Promise((resolve, reject) => {
      const matricule = targetUser.mat;
      const userName = targetUser.nom_prenom || targetUser.mat;

      // Prepare assigned profiles with appropriate dates
      const assignedProfiles: AssignedProfile[] = profiles
        .filter((p) => options.copyInactiveProfiles || p.boolEtatUtpr !== 0)
        .map((profile) => {
          let dateDebut: string | null = null;
          let dateFin: string | null = null;

          if (options.keepExistingDates) {
            // Use source profile dates
            dateDebut = profile.dateDebut
              ? formatDateForApi(new Date(profile.dateDebut as string))
              : formatDateForApi(new Date());
            dateFin = profile.dateFin
              ? formatDateForApi(new Date(profile.dateFin as string))
              : null;
          } else if (options.setNewStartDate) {
            // Check date mode: same for all or individual per profile
            if (options.dateMode === 'individual' && profile.customDateDebut) {
              // Use individual dates set for each profile
              dateDebut = formatDateForApi(profile.customDateDebut);
              dateFin = profile.customDateFin
                ? formatDateForApi(profile.customDateFin)
                : null;
            } else if (options.newStartDate) {
              // Use same dates for all profiles
              dateDebut = formatDateForApi(options.newStartDate);
              dateFin = options.newEndDate
                ? formatDateForApi(options.newEndDate)
                : null;
            } else {
              // Fallback to today
              dateDebut = formatDateForApi(new Date());
              dateFin = null;
            }
          } else {
            // Default to today
            dateDebut = formatDateForApi(new Date());
            dateFin = null;
          }

          return {
            profileCode: profile.codPflPfl,
            dateDebut,
            dateFin,
            etat: 1, // Always active when cloning
          };
        });

      if (assignedProfiles.length === 0) {
        resolve({
          targetUser: userName,
          success: true,
          clonedCount: 0,
          skippedCount: profiles.length,
          errorMessage: 'Aucun profil actif à cloner',
        });
        return;
      }
      const request: UserProfilesAssignmentRequest = {
        userMatricule: matricule,
        appCode: this.selectedAppCode,
        assignedProfiles: assignedProfiles,
        revokedProfiles: null,
      };

      console.log('🔍 [Clone] Sending request for:', userName);
      console.log('🔍 [Clone] Request payload:', request);

      this.profileAssignmentService.assignMultipleProfiles(request).subscribe({
        next: (result: BatchAssignmentResult) => {
          console.log('✅ [Clone] API Response (next):', result);

          const clonedCount = result.successCount || 0;
          const skippedCount = assignedProfiles.length - clonedCount;

          // Build detailed profile lists
          const successfulProfiles = result.successful || [];
          const failedProfiles = getFailedItemsArray(result).map((item) => ({
            profileCode: item.identifier,
            error: item.error,
          }));

          console.log('✅ [Clone] Successful profiles:', successfulProfiles);
          console.log('✅ [Clone] Failed profiles:', failedProfiles);

          resolve({
            targetUser: userName,
            success: isCompleteSuccess(result) || isPartialSuccess(result),
            clonedCount,
            skippedCount,
            successfulProfiles,
            failedProfiles,
            errorMessage: isCompleteFailure(result)
              ? this.formatCloneErrors(result)
              : undefined,
          });
        },
        error: (error) => {
          console.error('❌ [Clone] API Response (error):', error);
          console.error('❌ [Clone] Error status:', error.status);
          console.error('❌ [Clone] Error body:', error.error);

          // Check if this is a 400 response with BatchAssignmentResult
          // Backend returns 400 for complete failures but includes the result in error.error
          if (
            error.error &&
            typeof error.error === 'object' &&
            'failed' in error.error
          ) {
            console.log(
              '🔧 [Clone] Extracting BatchAssignmentResult from error.error'
            );

            const result: BatchAssignmentResult = error.error;
            const failedProfiles = getFailedItemsArray(result).map((item) => ({
              profileCode: item.identifier,
              error: item.error,
            }));

            console.log(
              '✅ [Clone] Extracted failed profiles from error:',
              failedProfiles
            );

            resolve({
              targetUser: userName,
              success: false,
              clonedCount: 0,
              skippedCount: assignedProfiles.length,
              successfulProfiles: [],
              failedProfiles,
              errorMessage: `${failedProfiles.length} profil(s) rejeté(s)`,
            });
          } else {
            // This is a real network/server error
            console.error('🔥 [Clone] Real error, not a BatchAssignmentResult');
            reject(new Error(error.message || 'Erreur lors du clonage'));
          }
        },
      });
    });
  }

  /**
   * Format clone errors for display
   * @private
   */
  private formatCloneErrors(result: BatchAssignmentResult): string {
    const failedItems = getFailedItemsArray(result);
    if (failedItems.length === 0) return 'Erreur inconnue';

    if (failedItems.length === 1) {
      return failedItems[0].error;
    }

    return `${failedItems.length} erreurs`;
  }

  /**
   * Get clone component reference for progress updates
   */
  updateCloneProgress(progress: number, results?: CloneResult[]): void {
    this.cloneProgress = progress;
    if (results) {
      this.cloneResults = results;
    }
  }
  /**
   * Loads all users for profile cloning (unpaginated, unfiltered)
   * For admin users: fetches all personnel without filters
   * For non-admin users: fetches all managed users
   * @private
   */
  private loadAllUsersForCloning(): void {
    console.log('🔍 Loading all users for cloning...');
    
    if (this.isAdminUser) {
      // Admin: Load all personnel without filters and pagination
      const noFilters = {
        search: undefined,
        codStatUser: undefined,
        codStrcStrc: undefined,
        codTstrTstr: undefined,
        sortBy: 'mat',
        sortDirection: 'ASC' as 'ASC' | 'DESC'
      };

      // Use a large page size to get all users in one request
      this.userService
        .getAllPersonnelPaginated(0, 10000, noFilters)
        .subscribe({
          next: (response) => {
            this.allUsersForCloning = response.content.map((user: any) => ({
              ...user,
              mat: user.mat || user.matricule,
              numMatrUser: user.mat || user.matricule,
              nom_prenom: user.nom_prenom || `${user.prenom || ''} ${user.nom || ''}`.trim(),
              displayName: user.nom_prenom || user.mat || user.matricule,
              label: `${user.mat || user.matricule} - ${user.nom_prenom || 'N/A'}`,
              selected: false,
              isActive: user.cod_stat_user === 1 || user.cod_stat_user === true,
              structureId: user.cod_strc_strc,
              cod_strc_strc: user.cod_strc_strc,
              cod_stat_user: user.cod_stat_user,
            }));
            
            console.log('✅ Loaded all users for cloning (admin):', this.allUsersForCloning.length);
          },
          error: (error) => {
            console.error('Error loading all users for cloning:', error);
            // Fallback to current managedUsers if loading fails
            this.allUsersForCloning = [...this.managedUsers];
            this.sharedService.handleError(
              error,
              'Erreur lors du chargement des utilisateurs pour le clonage'
            );
          },
        });
    } else {
      // Non-admin: Load all managed users (no pagination for non-admins)
      if (!this.currentManagerMatricule) {
        console.warn('Cannot load users for cloning: no manager matricule');
        this.allUsersForCloning = [...this.managedUsers];
        return;
      }

      this.profileAssignmentService
        .getManagedUsersWithDetails(this.currentManagerMatricule)
        .subscribe({
          next: (users) => {
            this.allUsersForCloning = users.map((user) => ({
              ...user,
              numMatrUser: user.mat,
              displayName: user.nom_prenom || user.mat,
              label: `${user.mat} - Structure ${user.cod_strc_strc}`,
              selected: false,
              isActive: user.cod_stat_user,
              structureId: user.cod_strc_strc,
            }));
            
            console.log('✅ Loaded all users for cloning (non-admin):', this.allUsersForCloning.length);
          },
          error: (error) => {
            console.error('Error loading managed users for cloning:', error);
            // Fallback to current managedUsers
            this.allUsersForCloning = [...this.managedUsers];
            this.sharedService.handleError(
              error,
              'Erreur lors du chargement des utilisateurs pour le clonage'
            );
          },
        });
    }
  }
}
