/**
 * @fileoverview Gooey Toast Types
 * Angular clone of goey-toast — all type definitions
 */

export type GooeyToastType = 'default' | 'success' | 'error' | 'warning' | 'info';

export type GooeyToastPosition =
  | 'top-left' | 'top-center' | 'top-right'
  | 'bottom-left' | 'bottom-center' | 'bottom-right';

export interface GooeyToastAction {
  label: string;
  onClick: () => void;
  /** If set, the button morphs into a success pill with this label */
  successLabel?: string;
}

export interface GooeyToastClassNames {
  wrapper?: string;
  content?: string;
  header?: string;
  title?: string;
  icon?: string;
  description?: string;
  actionWrapper?: string;
  actionButton?: string;
}

export interface GooeyToastOptions {
  /** Body content (string) */
  description?: string;
  /** Action button config */
  action?: GooeyToastAction;
  /** Display duration in ms (default: 4000) */
  duration?: number;
  /** Unique toast identifier */
  id?: string | number;
  /** CSS class overrides */
  classNames?: GooeyToastClassNames;
  /** Background color of the blob */
  fillColor?: string;
  /** Border color of the blob */
  borderColor?: string;
  /** Border width in px (default 1.5) */
  borderWidth?: number;
  /** Show countdown progress bar */
  showProgress?: boolean;
  /** Callback fired when toast is dismissed */
  onDismiss?: (id: string | number) => void;
  /** Callback fired when toast auto-closes */
  onAutoClose?: (id: string | number) => void;
}

export interface GooeyToastPromiseOptions<T = unknown> {
  loading: string;
  success: string | ((data: T) => string);
  error: string | ((err: unknown) => string);
  description?: {
    loading?: string;
    success?: string | ((data: T) => string);
    error?: string | ((err: unknown) => string);
  };
  action?: {
    success?: GooeyToastAction;
    error?: GooeyToastAction;
  };
}

export interface GooeyToasterConfig {
  position?: GooeyToastPosition;
  /** Default display duration (ms) */
  duration?: number;
  /** Gap between stacked toasts */
  gap?: number;
  /** Distance from screen edge */
  offset?: number | string;
  /** Color theme */
  theme?: 'light' | 'dark';
  /** Max queue size */
  maxQueue?: number;
  /** Show progress bar on all toasts */
  showProgress?: boolean;
}

/** Internal toast state */
export interface GooeyToastState {
  id: string | number;
  type: GooeyToastType;
  title: string;
  description?: string;
  action?: GooeyToastAction;
  options: GooeyToastOptions;
  /** Phase: pill → expanded → dismissing → removed */
  phase: 'pill' | 'expanded' | 'dismissing' | 'removed';  /** Success label string when action was clicked (null = not yet clicked) */
  actionSuccess: string | null;  /** Progress 0–100 */
  progress: number;
  /** Timestamp created */
  createdAt: Date;
  /** Duration for this toast */
  duration: number;
  /** Whether the toast is a promise toast in loading state */
  loading: boolean;
}
