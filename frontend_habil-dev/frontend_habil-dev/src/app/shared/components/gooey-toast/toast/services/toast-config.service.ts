/**
 * @fileoverview Gooey Toast — Reactive Configuration Service
 *
 * Global configuration that propagates live to all toast instances.
 * Uses BehaviorSubject for reactive updates — NO system reinitialisation needed.
 *
 * Features:
 * - InjectionToken-based defaults via TOAST_DEFAULT_CONFIG
 * - localStorage persistence (opt-in)
 * - Live reactive updates to ToastContainerComponent
 * - applyConfig() for builder UI integration
 */

import { Injectable, Inject, Optional } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import {
  ToastConfig,
  DEFAULT_TOAST_CONFIG,
  TOAST_DEFAULT_CONFIG,
  TOAST_CONFIG_STORAGE_KEY,
} from '../models/toast-config.model';

@Injectable({ providedIn: 'root' })
export class ToastConfigService {

  private _config$: BehaviorSubject<ToastConfig>;

  /** Observable stream of the current config — subscribe in components */
  get config$(): Observable<ToastConfig> {
    return this._config$.asObservable();
  }

  /** Current snapshot of the config (synchronous read) */
  get snapshot(): ToastConfig {
    return this._config$.value;
  }

  constructor(
    @Optional() @Inject(TOAST_DEFAULT_CONFIG) private _injectedDefaults: Partial<ToastConfig> | null,
  ) {
    // Priority: localStorage > injected defaults > hardcoded defaults
    const persisted = this._loadFromStorage();
    const merged: ToastConfig = {
      ...DEFAULT_TOAST_CONFIG,
      ...(this._injectedDefaults || {}),
      ...(persisted || {}),
    };
    this._config$ = new BehaviorSubject<ToastConfig>(merged);
  }

  // ── Public API ──────────────────────────────────────────────────────

  /**
   * Apply a full or partial config update.
   * Propagates reactively to all live toast containers.
   * Persists to localStorage for cross-session retention.
   *
   * @example
   * this.toastConfig.applyConfig({ position: 'top-center', theme: 'dark' });
   */
  applyConfig(partial: Partial<ToastConfig>): void {
    const next: ToastConfig = { ...this._config$.value, ...partial };
    this._config$.next(next);
    this._saveToStorage(next);
  }

  /** Update position only */
  setPosition(position: ToastConfig['position']): void {
    this.applyConfig({ position });
  }

  /** Update theme only */
  setTheme(theme: ToastConfig['theme']): void {
    this.applyConfig({ theme });
  }

  /** Update duration only */
  setDuration(duration: number): void {
    this.applyConfig({ duration });
  }

  /** Update showProgress only */
  setShowProgress(showProgress: boolean): void {
    this.applyConfig({ showProgress });
  }

  /** Reset to factory defaults (clears localStorage) */
  resetToDefaults(): void {
    const defaults: ToastConfig = {
      ...DEFAULT_TOAST_CONFIG,
      ...(this._injectedDefaults || {}),
    };
    this._config$.next(defaults);
    this._clearStorage();
  }

  // ── localStorage helpers ────────────────────────────────────────────

  private _loadFromStorage(): Partial<ToastConfig> | null {
    try {
      const raw = localStorage.getItem(TOAST_CONFIG_STORAGE_KEY);
      return raw ? JSON.parse(raw) : null;
    } catch {
      return null;
    }
  }

  private _saveToStorage(config: ToastConfig): void {
    try {
      localStorage.setItem(TOAST_CONFIG_STORAGE_KEY, JSON.stringify(config));
    } catch { /* localStorage unavailable — silent fail */ }
  }

  private _clearStorage(): void {
    try {
      localStorage.removeItem(TOAST_CONFIG_STORAGE_KEY);
    } catch { /* silent */ }
  }
}
