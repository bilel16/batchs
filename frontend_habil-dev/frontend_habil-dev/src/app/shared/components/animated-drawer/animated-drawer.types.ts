/**
 * AnimatedDrawerComponent Type Definitions
 * 
 * This file contains TypeScript interfaces and types for the AnimatedDrawerComponent
 * to provide better type safety and IntelliSense support.
 */

/**
 * Configuration options for the AnimatedDrawerComponent
 */
export interface DrawerConfig {
  /** Controls the visibility of the drawer */
  visible: boolean;
  /** The title displayed in the drawer header */
  title: string;
  /** Width of the drawer (px, %, rem, etc.) */
  width?: string;
  /** Whether to show the backdrop blur effect */
  showBackdrop?: boolean;
  /** Custom CSS class for additional styling */
  customClass?: string;
}

/**
 * Events emitted by the AnimatedDrawerComponent
 */
export interface DrawerEvents {
  /** Emitted when the drawer is closed */
  closed: () => void;
  /** Emitted when the drawer animation starts opening */
  opening: () => void;
  /** Emitted when the drawer animation completes opening */
  opened: () => void;
}

/**
 * Animation phases for the drawer component
 */
export type AnimationPhase = 'idle' | 'growing' | 'open' | 'shrinking';

/**
 * Responsive breakpoints for the drawer
 */
export interface DrawerBreakpoints {
  /** Desktop breakpoint (>1024px) */
  desktop: string;
  /** Tablet breakpoint (≤1024px) */
  tablet: string;
  /** Mobile breakpoint (≤768px) */
  mobile: string;
  /** Small mobile breakpoint (≤500px) */
  smallMobile: string;
}

/**
 * Default drawer widths for different use cases
 */
export const DRAWER_WIDTHS = {
  /** Narrow drawer for simple forms */
  NARROW: '320px',
  /** Standard drawer for most use cases */
  STANDARD: '400px',
  /** Medium drawer for complex forms */
  MEDIUM: '500px',
  /** Wide drawer for detailed content */
  WIDE: '600px',
  /** Extra wide drawer for complex layouts */
  EXTRA_WIDE: '700px'
} as const;

/**
 * Drawer width type based on predefined sizes
 */
export type DrawerWidth = typeof DRAWER_WIDTHS[keyof typeof DRAWER_WIDTHS] | string;

/**
 * Common drawer use cases and their recommended configurations
 */
export interface DrawerUseCase {
  /** Simple CRUD forms */
  CRUD_FORM: {
    width: typeof DRAWER_WIDTHS.STANDARD;
    title: string;
  };
  /** Detail/view panels */
  DETAIL_VIEW: {
    width: typeof DRAWER_WIDTHS.MEDIUM;
    title: string;
  };
  /** Settings/configuration panels */
  SETTINGS_PANEL: {
    width: typeof DRAWER_WIDTHS.NARROW;
    title: string;
  };
  /** Complex multi-step forms */
  COMPLEX_FORM: {
    width: typeof DRAWER_WIDTHS.WIDE;
    title: string;
  };
}

/**
 * Drawer color scheme following BNA Habilitation UI standards
 */
export interface DrawerColorScheme {
  /** Primary dark blue for headers */
  primaryDark: '#162636';
  /** Accent green for interactive elements */
  accentGreen: '#37978f';
  /** Secondary green for hover states */
  secondaryGreen: '#00ab86';
  /** Background color for body content */
  background: '#f9fafb';
  /** Primary text color */
  textPrimary: '#1e293b';
  /** White color for header text */
  white: '#ffffff';
}

/**
 * Animation timing configuration
 */
export interface DrawerAnimationTiming {
  /** Duration for square appearance (ms) */
  squareAppear: 300;
  /** Duration for morphing animation (ms) */
  morphing: 1200;
  /** Duration for content reveal (ms) */
  contentReveal: 600;
  /** Total opening animation duration (ms) */
  totalOpening: 1800;
  /** Total closing animation duration (ms) */
  totalClosing: 1800;
}

/**
 * Utility type for drawer component methods
 */
export interface DrawerMethods {
  /** Opens the drawer with animation */
  openDrawer(): void;
  /** Closes the drawer with animation */
  closeDrawer(): void;
  /** Handles backdrop click events */
  onBackdropClick(event: Event): void;
  /** Handles keyboard events (ESC key) */
  onKeyDown(event: KeyboardEvent): void;
  /** Gets current drawer CSS classes */
  getDrawerClasses(): string;
}

/**
 * Content projection slots available in the drawer
 */
export interface DrawerSlots {
  /** Main content area */
  default: any;
  /** Footer area for buttons/actions */
  footer: any;
}

/**
 * Responsive square sizes for different breakpoints
 */
export interface SquareSizes {
  desktop: {
    width: '6em';
    height: '6em';
    top: '12px';
    right: '12px';
  };
  tablet: {
    width: '4em';
    height: '4em';
    top: '8px';
    right: '8px';
  };
  mobile: {
    width: '3em';
    height: '3em';
    top: '6px';
    right: '6px';
  };
}

/**
 * Example usage patterns for the drawer component
 */
export interface DrawerUsageExamples {
  /** Basic CRUD form example */
  basicForm: {
    component: string;
    template: string;
    typescript: string;
  };
  /** Detail view example */
  detailView: {
    component: string;
    template: string;
    typescript: string;
  };
  /** Settings panel example */
  settingsPanel: {
    component: string;
    template: string;
    typescript: string;
  };
}
