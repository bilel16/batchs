/**
 * @fileoverview Gooey Toast — Configuration Model
 *
 * Global configuration for the toast system.
 * Uses InjectionToken for DI — supports `forRoot()` pattern.
 */

import { InjectionToken } from '@angular/core';
import { ToastPosition } from './toast.model';

// ── Global config interface ───────────────────────────────────────────────

export interface ToastConfig {
  /** Default position for all toasts */
  position: ToastPosition;
  /** Default display duration in ms */
  duration: number;
  /** Gap between stacked toasts (px) */
  gap: number;
  /** Distance from screen edge (px or CSS string) */
  offset: number | string;
  /** Color theme */
  theme: 'light' | 'dark';
  /** Max simultaneous toasts */
  maxQueue: number;
  /** Show progress bar on all toasts */
  showProgress: boolean;
}

// ── Defaults ──────────────────────────────────────────────────────────────

export const DEFAULT_TOAST_CONFIG: ToastConfig = {
  position: 'bottom-right',
  duration: 4000,
  gap: 14,
  offset: '24px',
  theme: 'light',
  maxQueue: Infinity,
  showProgress: false,
};

// ── InjectionToken ────────────────────────────────────────────────────────

/**
 * Injection token for providing default toast configuration.
 *
 * Usage with `forRoot()`:
 * ```ts
 * GooeyToastModule.forRoot({
 *   position: 'top-right',
 *   duration: 3000,
 *   showProgress: true,
 * })
 * ```
 *
 * Usage with manual provider:
 * ```ts
 * { provide: TOAST_DEFAULT_CONFIG, useValue: { position: 'top-center', theme: 'dark' } }
 * ```
 */
export const TOAST_DEFAULT_CONFIG = new InjectionToken<Partial<ToastConfig>>(
  'TOAST_DEFAULT_CONFIG',
  { providedIn: 'root', factory: () => DEFAULT_TOAST_CONFIG },
);

// ── LocalStorage key ──────────────────────────────────────────────────────

export const TOAST_CONFIG_STORAGE_KEY = 'gooey-toast-config';
