import {
  Component,
  OnInit,
  OnDestroy,
  ViewChild,
  ElementRef,
  Output,
  EventEmitter,
} from '@angular/core';

// ════════════════════════════════════════════════════════════════════════════
// INTERFACES
// ════════════════════════════════════════════════════════════════════════════

/**
 * Represents detailed information about a Tunisian governorate.
 * Used for displaying governorate data in the UI.
 */
export interface GovernorateInfo {
  /** Unique identifier code (e.g., 'TN83') */
  code: string;
  /** English name of the governorate */
  name: string;
  /** Arabic name of the governorate */
  nameAr?: string;
  /** Capital city of the governorate */
  capital?: string;
  /** Population count */
  population?: number;
  /** Area in square kilometers */
  area?: number;
  /** Number of delegations within the governorate */
  delegation?: number;
  /** Postal code range */
  zipCode?: string;
  /** Brief description of the governorate */
  description?: string;
}

/**
 * Represents a mapping between a point on the map and its containing governorate.
 * Used for associating data points with their respective regions.
 */
interface PointMapping {
  /** Unique identifier for the point */
  pointId: string;
  /** Code of the governorate containing this point */
  governorateCode: string;
  /** Latitude coordinate */
  lat: number;
  /** Longitude coordinate */
  lon: number;
}

/**
 * Represents a 2D coordinate point in SVG space.
 */
interface Point2D {
  x: number;
  y: number;
}

/**
 * Represents the current view state (zoom and pan).
 */
interface ViewState {
  zoom: number;
  panX: number;
  panY: number;
}

// ════════════════════════════════════════════════════════════════════════════
// CONSTANTS
// ════════════════════════════════════════════════════════════════════════════

/** Zoom level to transition to when drawer closes (partial zoom out) */
const DRAWER_CLOSE_ZOOM = 1.5;

/** Minimum zoom level to maintain some context after drawer close */
const PARTIAL_ZOOM_OUT_FACTOR = 0.6;

/** Default zoom level (no zoom applied) */
const DEFAULT_ZOOM = 1;

/** Minimum allowed zoom level */
const MIN_ZOOM = 1;

/** Maximum allowed zoom level */
const MAX_ZOOM = 5;

/** Zoom increment/decrement step */
const ZOOM_STEP = 0.3;

/** Zoom level applied when selecting a governorate */
const SELECTION_ZOOM = 2.5;

/** SVG viewBox width as defined in the SVG file */
const SVG_VIEWBOX_WIDTH = 800;

/** SVG viewBox height as defined in the SVG file */
const SVG_VIEWBOX_HEIGHT = 1100;

/** Default animation duration in milliseconds */
const DEFAULT_ANIMATION_DURATION = 400;

/** Fast animation duration for simple transitions */
const FAST_ANIMATION_DURATION = 200;

/** Reset animation duration */
const RESET_ANIMATION_DURATION = 300;

/** Zoom threshold for capital region labels */
const CAPITAL_REGION_ZOOM_THRESHOLD = 1.9;

/** Zoom threshold for small region labels */
const SMALL_REGION_ZOOM_THRESHOLD = 1.6;

/** Capital region governorate codes (densely packed area) */
const CAPITAL_REGION_CODES = ['TN11', 'TN14', 'TN12', 'TN13'];

/** Small/coastal region governorate codes */
const SMALL_REGION_CODES = ['TN52', 'TN51', 'TN82'];

/** Left mouse button code */
const LEFT_MOUSE_BUTTON = 0;

// ════════════════════════════════════════════════════════════════════════════
// COMPONENT
// ════════════════════════════════════════════════════════════════════════════

/**
 * Interactive map component for Tunisia displaying all 24 governorates.
 *
 * Features:
 * - Hover highlighting with synchronized labels
 * - Click to select with zoom and center
 * - Smooth pan and zoom with mouse wheel
 * - Drawer panel for governorate details
 * - Responsive label visibility based on zoom level
 *
 * @example
 * ```html
 * <app-tunisia-map></app-tunisia-map>
 * ```
 *
 * @example
 * ```typescript
 * // Programmatic selection from parent component
 * @ViewChild(TunisiaMapComponent) mapComponent: TunisiaMapComponent;
 * this.mapComponent.selectGovernorate('TN11');
 * ```
 */
@Component({
  selector: 'app-tunisia-map',
  templateUrl: './tunisia-map.component.html',
  standalone: false,
  styleUrls: ['./tunisia-map.component.scss'],
})
export class TunisiaMapComponent implements OnInit, OnDestroy {
  // ════════════════════════════════════════════════════════════════════════
  // VIEW REFERENCES
  // ════════════════════════════════════════════════════════════════════════

  /** Reference to the map container element */
  @ViewChild('mapContainer')
  private readonly mapContainer!: ElementRef<HTMLDivElement>;

  /** Reference to the SVG element */
  @ViewChild('svgElement')
  private readonly svgElement!: ElementRef<SVGSVGElement>;

  // ════════════════════════════════════════════════════════════════════════
  // OUTPUTS
  // ════════════════════════════════════════════════════════════════════════

  /** Emits when a governorate is selected to open the drawer */
  @Output() governorateSelected = new EventEmitter<GovernorateInfo>();

  // ════════════════════════════════════════════════════════════════════════
  // PUBLIC STATE (Template-bound)
  // ════════════════════════════════════════════════════════════════════════

  /** Currently hovered governorate code */
  hoveredGov: string | null = null;

  /** Currently selected governorate code */
  selectedGov: string | null = null;

  /** Whether the info drawer is visible */
  isDrawerVisible = false;

  /** Governorate info displayed in the drawer */
  drawerGovernorate: GovernorateInfo | null = null;

  /** Current zoom level */
  zoomLevel = DEFAULT_ZOOM;

  /** Current horizontal pan offset in pixels */
  panX = 0;

  /** Current vertical pan offset in pixels */
  panY = 0;

  // ════════════════════════════════════════════════════════════════════════
  // PRIVATE STATE
  // ════════════════════════════════════════════════════════════════════════

  /** Whether the user is currently panning the map */
  private isPanning = false;

  /** Starting X position for pan gesture */
  private panStartX = 0;

  /** Starting Y position for pan gesture */
  private panStartY = 0;

  /** Animation frame ID for cancellation */
  private animationFrameId: number | null = null;

  // ════════════════════════════════════════════════════════════════════════
  // STATIC DATA
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Center coordinates for each governorate in SVG space.
   * Used for centering the view when a governorate is selected.
   */
  private readonly governorateCenters: Readonly<Record<string, Point2D>> = {
    TN11: { x: 563.8, y: 142.6 }, // Tunis
    TN12: { x: 556.2, y: 124.5 }, // Ariana
    TN13: { x: 570.1, y: 165.7 }, // Ben Arous
    TN14: { x: 531.0, y: 144.9 }, // Manouba
    TN21: { x: 618.2, y: 159.4 }, // Nabeul
    TN22: { x: 546.3, y: 203.0 }, // Zaghouan
    TN23: { x: 484.7, y: 110.8 }, // Bizerte
    TN31: { x: 467.1, y: 167.0 }, // Béja
    TN32: { x: 415.8, y: 166.9 }, // Jendouba
    TN33: { x: 409.6, y: 233.7 }, // Le Kef
    TN34: { x: 480.8, y: 232.2 }, // Siliana
    TN41: { x: 530.7, y: 294.2 }, // Kairouan
    TN42: { x: 423.0, y: 347.5 }, // Kasserine
    TN43: { x: 502.5, y: 391.1 }, // Sidi Bou Zid
    TN51: { x: 593.4, y: 272.5 }, // Sousse
    TN52: { x: 622.5, y: 296.8 }, // Monastir
    TN53: { x: 587.0, y: 337.6 }, // Mahdia
    TN61: { x: 577.6, y: 398.1 }, // Sfax
    TN71: { x: 424.5, y: 444.0 }, // Gafsa
    TN72: { x: 335.3, y: 505.3 }, // Tozeur
    TN73: { x: 429.9, y: 590.5 }, // Kebili
    TN81: { x: 519.2, y: 511.3 }, // Gabès
    TN82: { x: 637.0, y: 583.6 }, // Médenine
    TN83: { x: 526.7, y: 734.0 }, // Tataouine
  };

  /**
   * Comprehensive governorate metadata.
   * Contains demographic, geographic, and descriptive information.
   */
  private readonly governorates: ReadonlyMap<string, GovernorateInfo> = new Map(
    [
      [
        'TN11',
        {
          code: 'TN11',
          name: 'Tunis',
          nameAr: 'تونس',
          capital: 'Tunis',
          population: 1056247,
          area: 346,
          delegation: 21,
          zipCode: '1000-1099',
          description:
            'The capital and largest city of Tunisia, a major economic and cultural center.',
        },
      ],
      [
        'TN12',
        {
          code: 'TN12',
          name: 'Ariana',
          nameAr: 'أريانة',
          capital: 'Ariana',
          population: 576088,
          area: 482,
          delegation: 7,
          zipCode: '2000-2099',
          description:
            'Located in the northern suburbs of Tunis, known for its residential areas.',
        },
      ],
      [
        'TN13',
        {
          code: 'TN13',
          name: 'Ben Arous',
          nameAr: 'بن عروس',
          capital: 'Ben Arous',
          population: 631842,
          area: 761,
          delegation: 12,
          zipCode: '2000-2099',
          description:
            'Southern suburbs of Tunis, includes the airport and major industrial zones.',
        },
      ],
      [
        'TN14',
        {
          code: 'TN14',
          name: 'Manouba',
          nameAr: 'منوبة',
          capital: 'Manouba',
          population: 379518,
          area: 1137,
          delegation: 8,
          zipCode: '2010-2099',
          description:
            'Western suburbs of Tunis, agricultural and residential area.',
        },
      ],
      [
        'TN21',
        {
          code: 'TN21',
          name: 'Nabeul',
          nameAr: 'نابل',
          capital: 'Nabeul',
          population: 787920,
          area: 2788,
          delegation: 16,
          zipCode: '8000-8099',
          description:
            'Located on the Cap Bon peninsula, famous for tourism and citrus fruits.',
        },
      ],
      [
        'TN22',
        {
          code: 'TN22',
          name: 'Zaghouan',
          nameAr: 'زغوان',
          capital: 'Zaghouan',
          population: 176945,
          area: 2820,
          delegation: 6,
          zipCode: '1100-1199',
          description:
            'Mountainous region known for its water sources and Roman aqueduct.',
        },
      ],
      [
        'TN23',
        {
          code: 'TN23',
          name: 'Bizerte',
          nameAr: 'بنزرت',
          capital: 'Bizerte',
          population: 568219,
          area: 3750,
          delegation: 14,
          zipCode: '7000-7099',
          description:
            'Northern coastal governorate, strategic port city with beautiful beaches.',
        },
      ],
      [
        'TN31',
        {
          code: 'TN31',
          name: 'Béja',
          nameAr: 'باجة',
          capital: 'Béja',
          population: 303032,
          area: 3558,
          delegation: 9,
          zipCode: '9000-9099',
          description:
            'Agricultural region in northwest Tunisia, known for wheat production.',
        },
      ],
      [
        'TN32',
        {
          code: 'TN32',
          name: 'Jendouba',
          nameAr: 'جندوبة',
          capital: 'Jendouba',
          population: 401477,
          area: 3102,
          delegation: 9,
          zipCode: '8100-8199',
          description:
            'Northwestern region, rich in forests and archaeological sites.',
        },
      ],
      [
        'TN33',
        {
          code: 'TN33',
          name: 'Le Kef',
          nameAr: 'الكاف',
          capital: 'Le Kef',
          population: 243156,
          area: 4965,
          delegation: 11,
          zipCode: '7100-7199',
          description:
            'Mountainous region near the Algerian border, historic military town.',
        },
      ],
      [
        'TN34',
        {
          code: 'TN34',
          name: 'Siliana',
          nameAr: 'سليانة',
          capital: 'Siliana',
          population: 223087,
          area: 4631,
          delegation: 11,
          zipCode: '6100-6199',
          description:
            'Agricultural governorate in northern Tunisia, cereals and livestock.',
        },
      ],
      [
        'TN41',
        {
          code: 'TN41',
          name: 'Kairouan',
          nameAr: 'القيروان',
          capital: 'Kairouan',
          population: 570559,
          area: 6712,
          delegation: 11,
          zipCode: '3100-3199',
          description:
            'Fourth holiest city in Islam, UNESCO World Heritage site with the Great Mosque.',
        },
      ],
      [
        'TN42',
        {
          code: 'TN42',
          name: 'Kassérine',
          nameAr: 'القصرين',
          capital: 'Kassérine',
          population: 439243,
          area: 8260,
          delegation: 13,
          zipCode: '1200-1299',
          description:
            'Mountainous region in western Tunisia, known for its natural resources.',
        },
      ],
      [
        'TN43',
        {
          code: 'TN43',
          name: 'Sidi Bou Zid',
          nameAr: 'سيدي بوزيد',
          capital: 'Sidi Bou Zid',
          population: 429912,
          area: 7405,
          delegation: 12,
          zipCode: '9100-9199',
          description:
            'Central Tunisia, birthplace of the 2011 Tunisian Revolution.',
        },
      ],
      [
        'TN51',
        {
          code: 'TN51',
          name: 'Sousse',
          nameAr: 'سوسة',
          capital: 'Sousse',
          population: 674971,
          area: 2621,
          delegation: 16,
          zipCode: '4000-4099',
          description:
            'Major coastal city, tourist destination with beautiful medina and beaches.',
        },
      ],
      [
        'TN52',
        {
          code: 'TN52',
          name: 'Monastir',
          nameAr: 'المنستير',
          capital: 'Monastir',
          population: 548828,
          area: 1019,
          delegation: 13,
          zipCode: '5000-5099',
          description:
            'Coastal city, birthplace of President Habib Bourguiba, tourist destination.',
        },
      ],
      [
        'TN53',
        {
          code: 'TN53',
          name: 'Mahdia',
          nameAr: 'المهدية',
          capital: 'Mahdia',
          population: 410812,
          area: 2966,
          delegation: 11,
          zipCode: '5100-5199',
          description:
            'Ancient Fatimid capital, coastal city known for fishing and tourism.',
        },
      ],
      [
        'TN61',
        {
          code: 'TN61',
          name: 'Sfax',
          nameAr: 'صفاقس',
          capital: 'Sfax',
          population: 955421,
          area: 7545,
          delegation: 16,
          zipCode: '3000-3099',
          description:
            'Second largest city, major industrial and commercial center.',
        },
      ],
      [
        'TN71',
        {
          code: 'TN71',
          name: 'Gafsa',
          nameAr: 'قفصة',
          capital: 'Gafsa',
          population: 337331,
          area: 8990,
          delegation: 11,
          zipCode: '2100-2199',
          description:
            'Mining region in southwestern Tunisia, known for phosphate production.',
        },
      ],
      [
        'TN72',
        {
          code: 'TN72',
          name: 'Tozeur',
          nameAr: 'توزر',
          capital: 'Tozeur',
          population: 107912,
          area: 5593,
          delegation: 6,
          zipCode: '2200-2299',
          description:
            'Desert oasis region, gateway to the Sahara, famous for dates and tourism.',
        },
      ],
      [
        'TN73',
        {
          code: 'TN73',
          name: 'Kebili',
          nameAr: 'قبلي',
          capital: 'Kebili',
          population: 156961,
          area: 22454,
          delegation: 6,
          zipCode: '4200-4299',
          description:
            'Largest governorate by area, Saharan region with ancient oases.',
        },
      ],
      [
        'TN81',
        {
          code: 'TN81',
          name: 'Gabès',
          nameAr: 'قابس',
          capital: 'Gabès',
          population: 374300,
          area: 7175,
          delegation: 10,
          zipCode: '6000-6099',
          description:
            'Coastal oasis, unique blend of sea and desert, industrial center.',
        },
      ],
      [
        'TN82',
        {
          code: 'TN82',
          name: 'Médenine',
          nameAr: 'مدنين',
          capital: 'Médenine',
          population: 479520,
          area: 8588,
          delegation: 9,
          zipCode: '4100-4199',
          description:
            'Southeastern region, known for traditional ksour (fortified granaries).',
        },
      ],
      [
        'TN83',
        {
          code: 'TN83',
          name: 'Tataouine',
          nameAr: 'تطاوين',
          capital: 'Tataouine',
          population: 149453,
          area: 38889,
          delegation: 7,
          zipCode: '3200-3299',
          description:
            'Southernmost governorate, desert landscapes, Star Wars filming location.',
        },
      ],
    ]
  );

  /**
   * Mappings between data points and their containing governorates.
   * Used for point-based interactions on the map.
   */
  private readonly pointMappings: readonly PointMapping[] = [
    {
      pointId: 'point-0',
      governorateCode: 'TN83',
      lat: 30.612365540573816,
      lon: 7.726714717861501,
    },
    {
      pointId: 'point-1',
      governorateCode: 'TN81',
      lat: 33.53761826839147,
      lon: 9.769571885386329,
    },
    {
      pointId: 'point-2',
      governorateCode: 'TN21',
      lat: 37.19418417816353,
      lon: 11.40385761940619,
    },
  ];

  // ════════════════════════════════════════════════════════════════════════
  // LIFECYCLE HOOKS
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Initializes the component.
   * Logs initialization information for debugging.
   */
  ngOnInit(): void {
    this.logDebug('Tunisia Map Component initialized');
    this.logDebug(`Loaded ${this.governorates.size} governorates`);
  }

  /**
   * Cleans up component resources.
   * Cancels any pending animations and resets state.
   */
  ngOnDestroy(): void {
    this.cancelAnimation();
    this.resetState();
  }

  // ════════════════════════════════════════════════════════════════════════
  // HOVER MANAGEMENT
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Sets the currently hovered governorate.
   * Triggers visual highlighting of the governorate path and label.
   *
   * @param code - The governorate code to highlight (e.g., 'TN83')
   */
  setHoveredGov(code: string): void {
    if (this.hoveredGov !== code) {
      this.hoveredGov = code;
      this.logDebug(`Hovered: ${this.getGovernorateName(code)} (${code})`);
    }
  }

  /**
   * Clears the current hover state.
   * Called when mouse leaves the SVG element.
   */
  clearHoveredGov(): void {
    if (this.hoveredGov !== null) {
      this.logDebug(`Cleared hover: ${this.hoveredGov}`);
      this.hoveredGov = null;
    }
  }

  // ════════════════════════════════════════════════════════════════════════
  // SELECTION MANAGEMENT
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Toggles the selection state of a governorate.
   * - If the governorate is already selected, it will be deselected
   * - If a different governorate is selected, it switches to the new one
   * - Triggers zoom animation and drawer visibility
   *
   * @param code - The governorate code to toggle (e.g., 'TN83')
   */
  toggleSelectedGov(code: string): void {
    if (this.selectedGov === code) {
      this.deselectGovernorate(code);
    } else {
      this.selectNewGovernorate(code);
    }
  }

/**
 * Clears the current selection programmatically.
 */
clearSelection(): void {
  if (this.selectedGov !== null) {
    this.logDebug(`Selection cleared: ${this.selectedGov}`);
    this.selectedGov = null;
    this.drawerGovernorate = null;
    this.isDrawerVisible = false;
    this.zoomOutPartially();
  }
}

  /**
   * Programmatically selects a governorate.
   * Can be called from parent components.
   *
   * @param code - The governorate code to select
   */
  selectGovernorate(code: string): void {
    if (this.governorates.has(code)) {
      this.selectNewGovernorate(code);
      this.logDebug(`Programmatic selection: ${code}`);
    } else {
      this.logWarn(`Invalid governorate code: ${code}`);
    }
  }

  /**
   * Returns the currently selected governorate code.
   *
   * @returns The selected governorate code or null if none selected
   */
  getSelectedGovernorate(): string | null {
    return this.selectedGov;
  }

  // ════════════════════════════════════════════════════════════════════════
  // DRAWER MANAGEMENT
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Closes the info drawer.
   * Resets drawer state without affecting selection.
   */
closeDrawer(): void {
  if (this.isDrawerVisible) {
    this.isDrawerVisible = false;
    this.logDebug('Drawer closing...');
    // State cleanup happens in onDrawerClosed after animation
  }
}

  /**
   * Handles the drawer closed event.
   * Resets view to default state.
   */
onDrawerClosed(): void {
  this.logDebug('Drawer close animation completed');



  // Safe to cleanup and zoom out
  this.selectedGov = null;
  this.drawerGovernorate = null;
  this.animateToDefaultView();
}

  /**
   * Handles the drawer opened event.
   * Used for logging and potential future functionality.
   */
  onDrawerOpened(): void {
    this.logDebug('Drawer opened');
  }

  // ════════════════════════════════════════════════════════════════════════
  // POINT INTERACTION
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Handles hover over a data point.
   * Highlights the governorate containing the point.
   *
   * @param pointId - The unique identifier of the point
   */
  onPointHover(pointId: string): void {
    const mapping = this.findPointMapping(pointId);
    if (mapping !== undefined) {
      this.setHoveredGov(mapping.governorateCode);
      this.logDebug(`Point ${pointId} → ${mapping.governorateCode}`);
    }
  }

  /**
   * Handles click on a data point.
   * Selects the governorate containing the point.
   *
   * @param pointId - The unique identifier of the point
   */
  onPointClick(pointId: string): void {
    const mapping = this.findPointMapping(pointId);
    if (mapping !== undefined) {
      this.toggleSelectedGov(mapping.governorateCode);
      this.logDebug(`Point clicked: ${pointId} → ${mapping.governorateCode}`);
    }
  }

  /**
   * Checks if a point belongs to a specific governorate.
   * Used for applying CSS classes to points.
   *
   * @param pointId - The point identifier
   * @param govCode - The governorate code to check against
   * @returns True if the point is in the specified governorate
   */
  isPointInGov(pointId: string, govCode: string | null): boolean {
    if (govCode === null) {
      return false;
    }
    const mapping = this.findPointMapping(pointId);
    return mapping?.governorateCode === govCode;
  }

  // ════════════════════════════════════════════════════════════════════════
  // DATA ACCESSORS
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Retrieves governorate information by code.
   *
   * @param code - The governorate code (e.g., 'TN83')
   * @returns The governorate info or undefined if not found
   */
  getGovernorateInfo(code: string): GovernorateInfo | undefined {
    return this.governorates.get(code);
  }

  /**
   * Retrieves the currently selected governorate's information.
   *
   * @returns The selected governorate info or null if none selected
   */
  getSelectedGovernorateInfo(): GovernorateInfo | null {
    if (this.selectedGov === null) {
      return null;
    }
    return this.governorates.get(this.selectedGov) ?? null;
  }

  /**
   * Returns all governorates as an array.
   * Useful for generating lists or statistics.
   *
   * @returns Array of all governorate info objects
   */
  getAllGovernorates(): GovernorateInfo[] {
    return Array.from(this.governorates.values());
  }

  /**
   * Formats a number with thousand separators.
   *
   * @param value - The number to format
   * @returns Formatted string (e.g., "1,056,247") or 'N/A' if undefined
   */
  formatNumber(value: number | undefined): string {
    if (value === undefined) {
      return 'N/A';
    }
    return value.toLocaleString('en-US');
  }

  // ════════════════════════════════════════════════════════════════════════
  // ZOOM & PAN - TRANSFORM
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Generates the CSS transform string for the SVG element.
   * Combines pan translation and zoom scale.
   *
   * @returns CSS transform string
   */
  getSvgTransform(): string {
    return `translate(${this.panX}px, ${this.panY}px) scale(${this.zoomLevel})`;
  }

  // ════════════════════════════════════════════════════════════════════════
  // ZOOM & PAN - CONTROLS
  // ════════════════════════════════════════════════════════════════════════

/**
 * Zooms out partially while keeping focus on the current area.
 * Provides more map context without fully resetting the view.
 */
private zoomOutPartially(): void {
  // Don't zoom out if already at minimum
  if (this.zoomLevel <= MIN_ZOOM) {
    this.logDebug('Already at minimum zoom');
    return;
  }

  // Calculate target zoom (proportional reduction)
  const targetZoom = Math.max(MIN_ZOOM, this.zoomLevel * PARTIAL_ZOOM_OUT_FACTOR);
  
  // Alternative: Use fixed zoom level
  // const targetZoom = Math.max(MIN_ZOOM, Math.min(this.zoomLevel, DRAWER_CLOSE_ZOOM));

  // Scale pan proportionally to maintain approximate center
  const ratio = targetZoom / this.zoomLevel;
  const targetPanX = this.panX * ratio;
  const targetPanY = this.panY * ratio;

  this.logDebug(`Partial zoom out: ${this.zoomLevel.toFixed(2)}x → ${targetZoom.toFixed(2)}x`);
  
  this.animateToView(targetZoom, targetPanX, targetPanY, RESET_ANIMATION_DURATION);
}

  /**
   * Zooms in by one step.
   * Respects the maximum zoom limit.
   */
  zoomIn(): void {
    if (this.zoomLevel < MAX_ZOOM) {
      const newZoom = Math.min(this.zoomLevel + ZOOM_STEP, MAX_ZOOM);
      this.animateToView(
        newZoom,
        this.panX,
        this.panY,
        FAST_ANIMATION_DURATION
      );
    }
  }

  /**
   * Zooms out by one step.
   * Scales pan proportionally and respects minimum zoom limit.
   */
  zoomOut(): void {
    if (this.zoomLevel > MIN_ZOOM) {
      const newZoom = Math.max(this.zoomLevel - ZOOM_STEP, MIN_ZOOM);
      const ratio = newZoom / this.zoomLevel;
      const newPanX = this.panX * ratio;
      const newPanY = this.panY * ratio;
      this.animateToView(newZoom, newPanX, newPanY, FAST_ANIMATION_DURATION);
    }
  }

  /**
   * Resets zoom and pan to the default state.
   */
  resetZoom(): void {
    this.animateToDefaultView();
    this.logDebug('Zoom reset');
  }

  /**
   * Handles mouse wheel events for zooming.
   * Zooms towards the mouse cursor position.
   *
   * @param event - The wheel event
   */
  onWheel(event: WheelEvent): void {
    event.preventDefault();

    const delta = event.deltaY > 0 ? -ZOOM_STEP : ZOOM_STEP;
    const newZoom = this.clampZoom(this.zoomLevel + delta);

    if (newZoom === this.zoomLevel) {
      return;
    }

    const container = this.mapContainer?.nativeElement;
    if (container !== undefined) {
      const rect = container.getBoundingClientRect();
      const mouseX = event.clientX - rect.left;
      const mouseY = event.clientY - rect.top;

      const zoomRatio = newZoom / this.zoomLevel;
      this.panX = mouseX - (mouseX - this.panX) * zoomRatio;
      this.panY = mouseY - (mouseY - this.panY) * zoomRatio;
    }

    this.zoomLevel = newZoom;
  }

  // ════════════════════════════════════════════════════════════════════════
  // ZOOM & PAN - PANNING
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Initiates a pan gesture.
   * Called on mousedown event.
   *
   * @param event - The mouse event
   */
  onPanStart(event: MouseEvent): void {
    if (event.button === LEFT_MOUSE_BUTTON) {
      this.isPanning = true;
      this.panStartX = event.clientX - this.panX;
      this.panStartY = event.clientY - this.panY;
      event.preventDefault();
    }
  }

  /**
   * Handles pan movement.
   * Called on mousemove event.
   *
   * @param event - The mouse event
   */
  onPanMove(event: MouseEvent): void {
    if (this.isPanning) {
      this.panX = event.clientX - this.panStartX;
      this.panY = event.clientY - this.panStartY;
      event.preventDefault();
    }
  }

  /**
   * Ends the pan gesture.
   * Called on mouseup or mouseleave events.
   */
  onPanEnd(): void {
    this.isPanning = false;
  }

  // ════════════════════════════════════════════════════════════════════════
  // ZOOM & PAN - GOVERNORATE CENTERING
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Zooms and pans to center a specific governorate.
   * Uses predefined center coordinates for accurate positioning.
   *
   * @param govCode - The governorate code to center on
   */
  zoomToGovernorate(govCode: string): void {
    const center = this.governorateCenters[govCode];
    if (center === undefined) {
      this.logWarn(`No center defined for ${govCode}`);
      return;
    }

    const dimensions = this.getContainerDimensions();
    if (dimensions === null) {
      return;
    }

    const baseScale = this.calculateBaseScale();
    const targetPan = this.calculateCenteringPan(
      center,
      baseScale,
      SELECTION_ZOOM,
      dimensions
    );

    this.logDebug(`Zooming to ${govCode}`, {
      center,
      dimensions,
      baseScale: baseScale.toFixed(4),
      targetPan,
    });

    this.animateToView(SELECTION_ZOOM, targetPan.x, targetPan.y);
  }

  /**
   * Zooms to a governorate by dynamically calculating its center from the path's bounding box.
   * Alternative to using predefined coordinates.
   *
   * @param govCode - The governorate code to center on
   */
  zoomToGovernorateByBBox(govCode: string): void {
    const path = document.getElementById(
      `${govCode}-shape`
    ) as unknown as SVGGraphicsElement | null;
    if (path === null) {
      this.logWarn(`Path element not found: ${govCode}-shape`);
      return;
    }

    const bbox = path.getBBox();
    const center: Point2D = {
      x: bbox.x + bbox.width / 2,
      y: bbox.y + bbox.height / 2,
    };

    const dimensions = this.getContainerDimensions();
    if (dimensions === null) {
      return;
    }

    const baseScale = this.calculateBaseScale();
    const targetPan = this.calculateCenteringPan(
      center,
      baseScale,
      SELECTION_ZOOM,
      dimensions
    );

    this.animateToView(SELECTION_ZOOM, targetPan.x, targetPan.y);
  }

  // ════════════════════════════════════════════════════════════════════════
  // LABEL VISIBILITY
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Determines if a governorate label should be visible based on zoom level.
   * Different regions have different visibility thresholds to prevent overlapping.
   *
   * @param govCode - The governorate code
   * @returns True if the label should be visible
   */
  isLabelVisible(govCode: string): boolean {
    if (CAPITAL_REGION_CODES.includes(govCode)) {
      return this.zoomLevel > CAPITAL_REGION_ZOOM_THRESHOLD;
    }

    if (SMALL_REGION_CODES.includes(govCode)) {
      return this.zoomLevel > SMALL_REGION_ZOOM_THRESHOLD;
    }

    return this.zoomLevel > DEFAULT_ZOOM;
  }

  /**
   * Checks if the map is at default zoom and position.
   *
   * @returns True if at default view
   */
  isAtDefaultZoom(): boolean {
    return (
      this.zoomLevel === DEFAULT_ZOOM && this.panX === 0 && this.panY === 0
    );
  }

  /**
   * Determines if a governorate label should be shown.
   * Labels appear when:
   * 1. Zoomed in sufficiently (based on region type)
   * 2. At default zoom AND hovering over the governorate
   *
   * @param govCode - The governorate code
   * @returns True if the label should be displayed
   */
  shouldShowLabel(govCode: string): boolean {
    if (this.isLabelVisible(govCode)) {
      return true;
    }

    return this.isAtDefaultZoom() && this.hoveredGov === govCode;
  }

  // ════════════════════════════════════════════════════════════════════════
  // PRIVATE METHODS - Selection
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Handles deselection of a governorate.
   *
   * @param code - The governorate code being deselected
   */
  private deselectGovernorate(code: string): void {
  this.logDebug(`Deselected: ${this.getGovernorateName(code)} (${code})`);
  this.selectedGov = null;
  this.drawerGovernorate = null;
  this.isDrawerVisible = false;
  this.zoomOutPartially();
  }

  /**
   * Handles selection of a new governorate.
   *
   * @param code - The governorate code to select
   */
  private selectNewGovernorate(code: string): void {
    this.logDebug(`Selected: ${this.getGovernorateName(code)} (${code})`);
    this.selectedGov = code;
  // Open drawer BEFORE zoom animation starts
  this.openDrawerForGovernorate(code);
  
  // Then animate to the governorate
  this.zoomToGovernorate(code);
  }
  /**
   * Opens the drawer with governorate information.
   *
   * @param code - The governorate code
   */
  private openDrawerForGovernorate(code: string): void {
    const govInfo = this.getGovernorateInfo(code);
    if (govInfo !== undefined) {
      this.drawerGovernorate = govInfo;
      this.isDrawerVisible = true;
      // Emit the event to parent component (dashboard) to open the drawer
      this.governorateSelected.emit(govInfo);
    }
  }

  // ════════════════════════════════════════════════════════════════════════
  // PRIVATE METHODS - Animation
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Animates the view to a target state using eased interpolation.
   *
   * @param targetZoom - Target zoom level
   * @param targetPanX - Target horizontal pan
   * @param targetPanY - Target vertical pan
   * @param duration - Animation duration in milliseconds
   */
  private animateToView(
    targetZoom: number,
    targetPanX: number,
    targetPanY: number,
    duration: number = DEFAULT_ANIMATION_DURATION
  ): void {
    this.cancelAnimation();

    const startTime = performance.now();
    const startState: ViewState = {
      zoom: this.zoomLevel,
      panX: this.panX,
      panY: this.panY,
    };

    const targetState: ViewState = {
      zoom: targetZoom,
      panX: targetPanX,
      panY: targetPanY,
    };

    const animate = (currentTime: number): void => {
      const elapsed = currentTime - startTime;
      const progress = Math.min(elapsed / duration, 1);
      const eased = this.easeOutCubic(progress);

      this.zoomLevel = this.interpolate(
        startState.zoom,
        targetState.zoom,
        eased
      );
      this.panX = this.interpolate(startState.panX, targetState.panX, eased);
      this.panY = this.interpolate(startState.panY, targetState.panY, eased);

      if (progress < 1) {
        this.animationFrameId = requestAnimationFrame(animate);
      } else {
        this.animationFrameId = null;
      }
    };

    this.animationFrameId = requestAnimationFrame(animate);
  }

  /**
   * Animates to the default view (no zoom, no pan).
   */
  private animateToDefaultView(): void {
    this.animateToView(DEFAULT_ZOOM, 0, 0, RESET_ANIMATION_DURATION);
  }

  /**
   * Cancels any running animation.
   */
  private cancelAnimation(): void {
    if (this.animationFrameId !== null) {
      cancelAnimationFrame(this.animationFrameId);
      this.animationFrameId = null;
    }
  }

  /**
   * Cubic ease-out function for smooth deceleration.
   *
   * @param t - Progress value from 0 to 1
   * @returns Eased value
   */
  private easeOutCubic(t: number): number {
    return 1 - Math.pow(1 - t, 3);
  }

  /**
   * Linear interpolation between two values.
   *
   * @param start - Start value
   * @param end - End value
   * @param t - Interpolation factor (0-1)
   * @returns Interpolated value
   */
  private interpolate(start: number, end: number, t: number): number {
    return start + (end - start) * t;
  }

  // ════════════════════════════════════════════════════════════════════════
  // PRIVATE METHODS - Calculations
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Calculates the base scale factor for SVG to screen coordinate conversion.
   * Accounts for current zoom level to return the true base scale.
   *
   * @returns The base scale factor
   */
  private calculateBaseScale(): number {
    const svg = this.svgElement?.nativeElement;
    if (svg === undefined) {
      return 1;
    }

    const svgRect = svg.getBoundingClientRect();
    const scaleX = svgRect.width / SVG_VIEWBOX_WIDTH / this.zoomLevel;
    const scaleY = svgRect.height / SVG_VIEWBOX_HEIGHT / this.zoomLevel;

    return Math.min(scaleX, scaleY);
  }

  /**
   * Calculates the pan values needed to center a point in the viewport.
   *
   * @param center - The point to center (in SVG coordinates)
   * @param baseScale - The base scale factor
   * @param targetZoom - The target zoom level
   * @param dimensions - Container dimensions
   * @returns The calculated pan coordinates
   */
  private calculateCenteringPan(
    center: Point2D,
    baseScale: number,
    targetZoom: number,
    dimensions: { width: number; height: number }
  ): Point2D {
    return {
      x: dimensions.width / 2 - center.x * baseScale * targetZoom,
      y: dimensions.height / 2 - center.y * baseScale * targetZoom,
    };
  }

  /**
   * Gets the container dimensions.
   *
   * @returns Container width and height, or null if unavailable
   */
  private getContainerDimensions(): { width: number; height: number } | null {
    const container = this.mapContainer?.nativeElement;
    const svg = this.svgElement?.nativeElement;

    if (container === undefined || svg === undefined) {
      return null;
    }

    return {
      width: container.clientWidth,
      height: container.clientHeight,
    };
  }

  /**
   * Clamps a zoom value to the allowed range.
   *
   * @param zoom - The zoom value to clamp
   * @returns Clamped zoom value
   */
  private clampZoom(zoom: number): number {
    return Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, zoom));
  }

  // ════════════════════════════════════════════════════════════════════════
  // PRIVATE METHODS - Data
  // ════════════════════════════════════════════════════════════════════════

  /**
   * Finds a point mapping by point ID.
   *
   * @param pointId - The point identifier
   * @returns The point mapping or undefined
   */
  private findPointMapping(pointId: string): PointMapping | undefined {
    return this.pointMappings.find((m) => m.pointId === pointId);
  }

  /**
   * Gets the name of a governorate by code.
   *
   * @param code - The governorate code
   * @returns The governorate name or the code if not found
   */
  private getGovernorateName(code: string): string {
    return this.governorates.get(code)?.name ?? code;
  }

  // ════════════════════════════════════════════════════════════════════════
  // PRIVATE METHODS - Utility
  // ════════════════════════════════════════════════════════════════════════

  /**
 * Calculates the target zoom level for partial zoom out.
 * 
 * @returns Target zoom level
 */
private calculatePartialZoomOut(): number {
  // Option 1: Fixed zoom level (simpler, consistent behavior)
  // return Math.max(MIN_ZOOM, DRAWER_CLOSE_ZOOM);
  
  // Option 2: Proportional zoom out (maintains relative context)
  const proportionalZoom = this.zoomLevel * PARTIAL_ZOOM_OUT_FACTOR;
  
  // Ensure we don't go below minimum zoom
  return Math.max(MIN_ZOOM, proportionalZoom);
}
  /**
   * Resets component state to initial values.
   */
  private resetState(): void {
    this.hoveredGov = null;
    this.selectedGov = null;
    this.isDrawerVisible = false;
    this.drawerGovernorate = null;
  }

  /**
   * Logs a debug message.
   * Replace with proper logging service in production.
   *
   * @param message - The message to log
   * @param data - Optional data to include
   */
  private logDebug(message: string, data?: unknown): void {
    // In production, replace with: this.logger.debug(message, data);
    if (data !== undefined) {
      console.log(`🗺️ ${message}`, data);
    } else {
      console.log(`🗺️ ${message}`);
    }
  }

  /**
   * Logs a warning message.
   * Replace with proper logging service in production.
   *
   * @param message - The warning message
   */
  private logWarn(message: string): void {
    // In production, replace with: this.logger.warn(message);
    console.warn(`⚠️ ${message}`);
  }
}
