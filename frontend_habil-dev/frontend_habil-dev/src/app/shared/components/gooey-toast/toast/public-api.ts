/**
 * @fileoverview Gooey Toast — Public API Barrel
 *
 * Single entry point for the gooey-toast library.
 * Import everything from here:
 *
 * ```ts
 * import {
 *   GooeyToastModule,
 *   ToastService,
 *   ToastConfigService,
 *   ToastConfig,
 *   ToastOptions,
 *   TOAST_DEFAULT_CONFIG,
 * } from '@shared/components/gooey-toast/toast';
 * ```
 */

// ── Module ────────────────────────────────────────────────────────────────
export { GooeyToastModule } from './toast.module';

// ── Component ─────────────────────────────────────────────────────────────
export { ToastContainerComponent } from './components/toast-container.component';

// ── Services ──────────────────────────────────────────────────────────────
export { ToastService } from './services/toast.service';
export type { ToastEvent } from './services/toast.service';
export { ToastConfigService } from './services/toast-config.service';

// ── Models / Types ────────────────────────────────────────────────────────
export type {
  ToastType,
  ToastPosition,
  ToastAction,
  ToastClassNames,
  ToastOptions,
  ToastPromiseOptions,
  ToastState,
} from './models/toast.model';

export type { ToastConfig } from './models/toast-config.model';

export {
  DEFAULT_TOAST_CONFIG,
  TOAST_DEFAULT_CONFIG,
  TOAST_CONFIG_STORAGE_KEY,
} from './models/toast-config.model';

// ── Animations (for advanced users / custom containers) ───────────────────
export {
  PH, PR,
  EXPAND_DUR, COLLAPSE_DUR, ENTRY_DUR, EXIT_DUR,
  EXPAND_BODY_DELAY, DISMISS_AFTER_COLLAPSE,
  cubicBezier, smoothEase, entryEase,
  morphPathRaw, morphPathCenterRaw,
} from './animations/toast.animations';
