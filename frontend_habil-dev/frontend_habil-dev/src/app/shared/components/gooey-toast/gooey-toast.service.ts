/**
 * @fileoverview Gooey Toast Service
 * 
 * Imperative toast API for Angular — inspired by goey-toast (React).
 * Usage:
 *   constructor(private toast: GooeyToastService) {}
 *   this.toast.success('Saved!');
 *   this.toast.error('Failed', { description: 'Card declined.' });
 *   this.toast.promise(myObservable, { loading: '...', success: '...', error: '...' });
 */

import { Injectable } from '@angular/core';
import { Subject, Observable, BehaviorSubject } from 'rxjs';
import {
  GooeyToastType,
  GooeyToastOptions,
  GooeyToastState,
  GooeyToastPromiseOptions,
  GooeyToastPosition,
} from './gooey-toast.types';

let _idCounter = 0;

@Injectable({ providedIn: 'root' })
export class GooeyToastService {

  private _toasts$ = new Subject<{ action: 'add' | 'dismiss' | 'update'; toast?: GooeyToastState; id?: string | number; updates?: Partial<GooeyToastState> }>();

  /** Stream consumed by the GooeyToasterComponent */
  get events$(): Observable<{ action: 'add' | 'dismiss' | 'update'; toast?: GooeyToastState; id?: string | number; updates?: Partial<GooeyToastState> }> {
    return this._toasts$.asObservable();
  }

  /** Runtime config overrides (position, theme, etc.) */
  private _config$ = new BehaviorSubject<Partial<{ position: GooeyToastPosition; theme: 'light' | 'dark' }>>({});
  get config$(): Observable<Partial<{ position: GooeyToastPosition; theme: 'light' | 'dark' }>> {
    return this._config$.asObservable();
  }

  /** Change the toaster position at runtime */
  setPosition(position: GooeyToastPosition): void {
    this._config$.next({ ...this._config$.value, position });
  }

  /** Change the toaster theme at runtime */
  setTheme(theme: 'light' | 'dark'): void {
    this._config$.next({ ...this._config$.value, theme });
  }

  // ── Public API ──────────────────────────────────────────────────────

  /** Show a default (neutral) toast */
  show(title: string, options?: GooeyToastOptions): string | number {
    return this._add('default', title, options);
  }

  /** Show a success toast */
  success(title: string, options?: GooeyToastOptions): string | number {
    return this._add('success', title, options);
  }

  /** Show an error toast */
  error(title: string, options?: GooeyToastOptions): string | number {
    return this._add('error', title, options);
  }

  /** Show a warning toast */
  warning(title: string, options?: GooeyToastOptions): string | number {
    return this._add('warning', title, options);
  }

  /** Show an info toast */
  info(title: string, options?: GooeyToastOptions): string | number {
    return this._add('info', title, options);
  }

  /**
   * Promise toast — shows loading, then success/error based on result.
   * Accepts a Promise or an Observable.
   */
  promise<T>(
    promiseOrObservable: Promise<T> | Observable<T>,
    opts: GooeyToastPromiseOptions<T>,
    toastOptions?: GooeyToastOptions
  ): string | number {
    const id = this._add('default', opts.loading, {
      ...toastOptions,
      description: opts.description?.loading,
      duration: 0, // don't auto-dismiss while loading
    });

    // Mark as loading
    this._toasts$.next({ action: 'update', id, updates: { loading: true } });

    const handleResult = (data: T) => {
      const title = typeof opts.success === 'function' ? opts.success(data) : opts.success;
      const desc = opts.description?.success
        ? (typeof opts.description.success === 'function' ? opts.description.success(data) : opts.description.success)
        : undefined;
      this._toasts$.next({
        action: 'update', id, updates: {
          type: 'success',
          title,
          description: desc,
          action: opts.action?.success,
          loading: false,
          duration: toastOptions?.duration || 4000,
        }
      });
    };

    const handleError = (err: unknown) => {
      const title = typeof opts.error === 'function' ? opts.error(err) : opts.error;
      const desc = opts.description?.error
        ? (typeof opts.description.error === 'function' ? opts.description.error(err) : opts.description.error)
        : undefined;
      this._toasts$.next({
        action: 'update', id, updates: {
          type: 'error',
          title,
          description: desc,
          action: opts.action?.error,
          loading: false,
          duration: toastOptions?.duration || 4000,
        }
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
    this._toasts$.next({ action: 'dismiss', id });
  }

  /** Update an existing toast in place */
  update(id: string | number, updates: Partial<Pick<GooeyToastState, 'title' | 'description' | 'type' | 'action'>>): void {
    this._toasts$.next({ action: 'update', id, updates });
  }

  // ── Internal ────────────────────────────────────────────────────────

  private _add(type: GooeyToastType, title: string, options?: GooeyToastOptions): string | number {
    const id = options?.id ?? `gooey-${++_idCounter}`;
    const toast: GooeyToastState = {
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
      duration: options?.duration ?? 4000,
      loading: false,
    };
    this._toasts$.next({ action: 'add', toast });
    return id;
  }
}
