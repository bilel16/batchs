/**
 * @fileoverview Gooey Toast — Type Definitions
 *
 * All public types for the gooey-toast library.
 * Zero external dependencies.
 */

// ── Position & Type enums ─────────────────────────────────────────────────

export type ToastType = 'default' | 'success' | 'error' | 'warning' | 'info';

export type ToastPosition =
  | 'top-left' | 'top-center' | 'top-right'
  | 'bottom-left' | 'bottom-center' | 'bottom-right';

// ── Action button ─────────────────────────────────────────────────────────

export interface ToastAction {
  /** Button label text */
  label: string;
  /** Callback fired on click */
  onClick: () => void;
  /** If set, the button morphs into a success pill with this label after click */
  successLabel?: string;
}

// ── Class name overrides ──────────────────────────────────────────────────

export interface ToastClassNames {
  wrapper?: string;
  content?: string;
  header?: string;
  title?: string;
  icon?: string;
  description?: string;
  actionWrapper?: string;
  actionButton?: string;
}

// ── Per-toast options (passed to service methods) ─────────────────────────

export interface ToastOptions {
  /** Body content (string) */
  description?: string;
  /** Action button config */
  action?: ToastAction;
  /** Display duration in ms (default: from config, typically 4000) */
  duration?: number;
  /** Unique toast identifier */
  id?: string | number;
  /** CSS class overrides */
  classNames?: ToastClassNames;
  /** Background color of the blob */
  fillColor?: string;
  /** Border color of the blob */
  borderColor?: string;
  /** Border width in px (default 1.5) */
  borderWidth?: number;
  /** Show countdown progress bar (overrides global setting) */
  showProgress?: boolean;
  /** Callback fired when toast is dismissed (manual or auto) */
  onDismiss?: (id: string | number) => void;
  /** Callback fired when toast auto-closes (not manual dismiss) */
  onAutoClose?: (id: string | number) => void;
}

// ── Promise toast options ─────────────────────────────────────────────────

export interface ToastPromiseOptions<T = unknown> {
  loading: string;
  success: string | ((data: T) => string);
  error: string | ((err: unknown) => string);
  description?: {
    loading?: string;
    success?: string | ((data: T) => string);
    error?: string | ((err: unknown) => string);
  };
  action?: {
    success?: ToastAction;
    error?: ToastAction;
  };
}

// ── Internal toast state ──────────────────────────────────────────────────

export interface ToastState {
  id: string | number;
  type: ToastType;
  title: string;
  description?: string;
  action?: ToastAction;
  options: ToastOptions;
  /** Phase: pill → expanded → dismissing → removed */
  phase: 'pill' | 'expanded' | 'dismissing' | 'removed';
  /** Success label string when action was clicked (null = not yet clicked) */
  actionSuccess: string | null;
  /** Progress 0–100 */
  progress: number;
  /** Timestamp created */
  createdAt: Date;
  /** Duration for this toast */
  duration: number;
  /** Whether the toast is a promise toast in loading state */
  loading: boolean;
}
