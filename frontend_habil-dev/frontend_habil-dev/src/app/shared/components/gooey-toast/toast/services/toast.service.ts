/**
 * @fileoverview Gooey Toast — Imperative Toast Service
 *
 * Public API for showing/dismissing/updating toasts.
 * Reads defaults from ToastConfigService — no hardcoded values.
 *
 * Usage:
 *   constructor(private toast: ToastService) {}
 *   this.toast.success('Saved!');
 *   this.toast.error('Failed', { description: 'Card declined.' });
 *   this.toast.promise(myObservable, { loading: '…', success: '…', error: '…' });
 */

import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';
import {
  ToastType,
  ToastOptions,
  ToastState,
  ToastPromiseOptions,
} from '../models/toast.model';
import { ToastConfigService } from './toast-config.service';

/** Internal event payload consumed by the container component */
export interface ToastEvent {
  action: 'add' | 'dismiss' | 'update';
  toast?: ToastState;
  id?: string | number;
  updates?: Partial<ToastState>;
}

let _idCounter = 0;

@Injectable({ providedIn: 'root' })
export class ToastService {

  private _events$ = new Subject<ToastEvent>();

  /** Stream consumed by ToastContainerComponent */
  get events$(): Observable<ToastEvent> {
    return this._events$.asObservable();
  }

  constructor(private _configSvc: ToastConfigService) {}

  // ── Public API ──────────────────────────────────────────────────────

  /** Show a default (neutral) toast */
  show(title: string, options?: ToastOptions): string | number {
    return this._add('default', title, options);
  }

  /** Show a success toast */
  success(title: string, options?: ToastOptions): string | number {
    return this._add('success', title, options);
  }

  /** Show an error toast */
  error(title: string, options?: ToastOptions): string | number {
    return this._add('error', title, options);
  }

  /** Show a warning toast */
  warning(title: string, options?: ToastOptions): string | number {
    return this._add('warning', title, options);
  }

  /** Show an info toast */
  info(title: string, options?: ToastOptions): string | number {
    return this._add('info', title, options);
  }

  /**
   * Promise toast — shows loading, then success/error based on result.
   * Accepts a Promise or an Observable.
   */
  promise<T>(
    promiseOrObservable: Promise<T> | Observable<T>,
    opts: ToastPromiseOptions<T>,
    toastOptions?: ToastOptions,
  ): string | number {
    const defaultDuration = this._configSvc.snapshot.duration;
    const id = this._add('default', opts.loading, {
      ...toastOptions,
      description: opts.description?.loading,
      duration: 0, // don't auto-dismiss while loading
    });

    // Mark as loading
    this._events$.next({ action: 'update', id, updates: { loading: true } });

    const handleResult = (data: T) => {
      const title = typeof opts.success === 'function' ? opts.success(data) : opts.success;
      const desc = opts.description?.success
        ? (typeof opts.description.success === 'function' ? opts.description.success(data) : opts.description.success)
        : undefined;
      this._events$.next({
        action: 'update', id, updates: {
          type: 'success',
          title,
          description: desc,
          action: opts.action?.success,
          loading: false,
          duration: toastOptions?.duration || defaultDuration,
        },
      });
    };

    const handleError = (err: unknown) => {
      const title = typeof opts.error === 'function' ? opts.error(err) : opts.error;
      const desc = opts.description?.error
        ? (typeof opts.description.error === 'function' ? opts.description.error(err) : opts.description.error)
        : undefined;
      this._events$.next({
        action: 'update', id, updates: {
          type: 'error',
          title,
          description: desc,
          action: opts.action?.error,
          loading: false,
          duration: toastOptions?.duration || defaultDuration,
        },
      });
    };

    if (promiseOrObservable instanceof Observable) {
      promiseOrObservable.subscribe({ next: handleResult, error: handleError });
    } else {
      promiseOrObservable.then(handleResult).catch(handleError);
    }

    return id;
  }

  /** Dismiss a specific toast, or all toasts if no id given */
  dismiss(id?: string | number): void {
    this._events$.next({ action: 'dismiss', id });
  }

  /** Update an existing toast in place */
  update(id: string | number, updates: Partial<Pick<ToastState, 'title' | 'description' | 'type' | 'action'>>): void {
    this._events$.next({ action: 'update', id, updates });
  }

  // ── Config delegation (convenience wrappers) ────────────────────────

  /** Change position at runtime (delegates to ToastConfigService) */
  setPosition(position: ToastState['type'] extends string ? any : never): void {
    this._configSvc.setPosition(position);
  }

  /** Change theme at runtime (delegates to ToastConfigService) */
  setTheme(theme: 'light' | 'dark'): void {
    this._configSvc.setTheme(theme);
  }

  // ── Internal ────────────────────────────────────────────────────────

  private _add(type: ToastType, title: string, options?: ToastOptions): string | number {
    const cfg = this._configSvc.snapshot;
    const id = options?.id ?? `gooey-${++_idCounter}`;
    const toast: ToastState = {
      id,
      type,
      title,
      description: options?.description,
      action: options?.action,
      options: options || {},
      phase: 'pill',
      actionSuccess: null,
      progress: 0,
      createdAt: new Date(),
      duration: options?.duration ?? cfg.duration,
      loading: false,
    };
    this._events$.next({ action: 'add', toast });
    return id;
  }
}
