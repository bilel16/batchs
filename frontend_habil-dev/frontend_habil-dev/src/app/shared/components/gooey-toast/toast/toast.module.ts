/**
 * @fileoverview GooeyToastModule (v2 — Portable Toast Engine)
 *
 * NgModule that declares/exports the ToastContainerComponent.
 * Provides the reactive config system via `forRoot()`.
 *
 * Usage:
 * ```ts
 * // Basic — use all defaults
 * imports: [GooeyToastModule]
 *
 * // With custom defaults
 * imports: [GooeyToastModule.forRoot({ position: 'top-right', theme: 'dark', duration: 3000 })]
 * ```
 *
 * Then in your root template:
 * ```html
 * <gooey-toast-container></gooey-toast-container>
 * ```
 */

import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToastContainerComponent } from './components/toast-container.component';
import { ToastConfig, TOAST_DEFAULT_CONFIG } from './models/toast-config.model';

@NgModule({
  declarations: [ToastContainerComponent],
  imports: [CommonModule],
  exports: [ToastContainerComponent],
})
export class GooeyToastModule {

  /**
   * Configure the toast system with custom defaults.
   * Call once in your root AppModule.
   *
   * @param config - Partial config to merge with defaults
   * @returns ModuleWithProviders with TOAST_DEFAULT_CONFIG provider
   *
   * @example
   * ```ts
   * @NgModule({
   *   imports: [
   *     GooeyToastModule.forRoot({
   *       position: 'top-right',
   *       duration: 3000,
   *       theme: 'dark',
   *       showProgress: true,
   *       offset: '80px',
   *     }),
   *   ],
   * })
   * export class AppModule {}
   * ```
   */
  static forRoot(config: Partial<ToastConfig> = {}): ModuleWithProviders<GooeyToastModule> {
    return {
      ngModule: GooeyToastModule,
      providers: [
        { provide: TOAST_DEFAULT_CONFIG, useValue: config },
      ],
    };
  }
}
