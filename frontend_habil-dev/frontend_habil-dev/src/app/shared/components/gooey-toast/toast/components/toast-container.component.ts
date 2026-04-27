/**
 * @fileoverview Toast Container Component
 *
 * The root container — place once in your app layout:
 *   <gooey-toast-container></gooey-toast-container>
 *
 * Reactively reads config from ToastConfigService.
 * All animation/morph logic is self-contained.
 * No app-level imports — fully portable.
 */

import {
  Component,
  OnInit,
  OnDestroy,
  AfterViewInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  NgZone,
  ElementRef,
  ViewChildren,
  QueryList,
} from '@angular/core';
import { Subscription } from 'rxjs';
import { ToastService } from '../services/toast.service';
import { ToastConfigService } from '../services/toast-config.service';
import { ToastPosition, ToastState } from '../models/toast.model';
import {
  PH, EXPAND_DUR, COLLAPSE_DUR, ENTRY_DUR, EXIT_DUR,
  EXPAND_BODY_DELAY, DISMISS_AFTER_COLLAPSE,
  smoothEase, entryEase,
  morphPathRaw, morphPathCenterRaw,
} from '../animations/toast.animations';

// ── Per-toast morph state (internal) ──────────────────────────────────────
interface ToastMorphState {
  t: number;
  targetT: number;
  pw: number; bw: number; th: number;
  animId: number | null;
  entryAnimId: number | null;
  entryProgress: number;
  exitAnimId: number | null;
  exitProgress: number;
  shakeAnimId: number | null;
  showBody: boolean;
  dismissed: boolean;
  userDismissed: boolean;
  collapsing: boolean;
  hovered: boolean;
  expandedDims: { pw: number; bw: number; th: number };
  remainingMs: number | null;
  timerStart: number;
  prevPhase: string;
}

@Component({
  selector: 'gooey-toast-container',
  standalone: false,
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div class="gooey-toaster"
         [ngClass]="positionClass"
         [class.theme-dark]="theme === 'dark'"
         [style.--gooey-gap]="gap + 'px'"
         [style.--gooey-offset]="offsetValue"
         (mouseenter)="onContainerEnter()"
         (mouseleave)="onContainerLeave()">

      <div *ngFor="let toast of toasts; trackBy: trackById"
           class="gooey-toast-wrapper"
           [ngClass]="getToastClasses(toast)"
           [attr.data-toast-id]="toast.id"
           [style.transform]="getWrapperBaseTransform(toast)"
           (mouseenter)="onToastEnter(toast)"
           (mouseleave)="onToastLeave(toast)"
           #toastEl>

        <svg class="gooey-blob-svg" aria-hidden="true"
             [attr.data-blob-id]="toast.id">
          <path [attr.fill]="getBlobFill(toast)"
                [attr.stroke]="toast.options.borderColor || 'none'"
                [attr.stroke-width]="toast.options.borderColor ? (toast.options.borderWidth || 1.5) : 0" />
        </svg>

        <div class="gooey-content"
             [attr.data-content-id]="toast.id"
             [ngClass]="toast.options.classNames?.content || ''"
             [style.transform]="getContentTransform()"
             [style.text-align]="getContentTextAlign()">

          <div class="gooey-header"
               [attr.data-header-id]="toast.id"
               [ngClass]="toast.options.classNames?.header || ''">

            <span class="gooey-icon" [ngClass]="toast.options.classNames?.icon || ''"
                  *ngIf="!toast.loading">
              <ng-container [ngSwitch]="toast.type">
                <svg *ngSwitchCase="'success'" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <circle cx="12" cy="12" r="10"/><path d="M9 12l2 2 4-4"/>
                </svg>
                <svg *ngSwitchCase="'error'" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <circle cx="12" cy="12" r="10"/><path d="M15 9l-6 6"/><path d="M9 9l6 6"/>
                </svg>
                <svg *ngSwitchCase="'warning'" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/>
                </svg>
                <svg *ngSwitchCase="'info'" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <circle cx="12" cy="12" r="10"/><line x1="12" y1="16" x2="12" y2="12"/><line x1="12" y1="8" x2="12.01" y2="8"/>
                </svg>
                <svg *ngSwitchDefault xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/>
                </svg>
              </ng-container>
            </span>

            <span class="gooey-icon gooey-spinner" *ngIf="toast.loading">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M21 12a9 9 0 1 1-6.219-8.56"/>
              </svg>
            </span>

            <span class="gooey-title" [ngClass]="toast.options.classNames?.title || ''">{{ toast.title }}</span>

            <span class="gooey-timestamp"
                  *ngIf="!getMorphState(toast)?.showBody && !toast.description && !toast.action && !toast.actionSuccess">
              {{ toast.createdAt | date:'h:mm:ss a' }}
            </span>
          </div>

          <div class="gooey-description"
               *ngIf="getMorphState(toast)?.showBody && toast.description && !getMorphState(toast)?.dismissed"
               [ngClass]="toast.options.classNames?.description || ''">
            <span class="gooey-timestamp-body">{{ toast.createdAt | date:'h:mm:ss a' }}</span>
            {{ toast.description }}
          </div>

          <div class="gooey-timestamp"
               *ngIf="getMorphState(toast)?.showBody && !toast.description && toast.action && !toast.actionSuccess && !getMorphState(toast)?.dismissed"
               style="text-align: right; margin-top: 8px; padding-left: 0;">
            {{ toast.createdAt | date:'h:mm:ss a' }}
          </div>

          <div class="gooey-action-wrapper"
               *ngIf="getMorphState(toast)?.showBody && (toast.action || toast.actionSuccess) && !getMorphState(toast)?.dismissed"
               [ngClass]="toast.options.classNames?.actionWrapper || ''">
            <button *ngIf="!toast.actionSuccess && toast.action"
                    class="gooey-action-btn"
                    [ngClass]="toast.options.classNames?.actionButton || ''"
                    type="button"
                    (click)="onActionClick(toast, $event)">
              {{ toast.action.label }}
            </button>
            <span *ngIf="toast.actionSuccess" class="gooey-action-success">
              <svg viewBox="0 0 20 20" fill="currentColor" class="gooey-action-check"><path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/></svg>
              {{ toast.actionSuccess }}
            </span>
          </div>

          <div class="gooey-progress-track"
               *ngIf="(showProgress || toast.options.showProgress) && !toast.loading && toast.duration > 0"
               [class.gooey-progress-paused]="isAnyHovered()"
               [style.opacity]="getMorphState(toast)?.showBody && !toast.actionSuccess ? 1 : 0">
            <div class="gooey-progress-fill"
                 [class]="'progress-' + toast.type"
                 [style.--gooey-progress-duration]="toast.duration + 'ms'">
            </div>
          </div>
        </div>

        <button class="gooey-close-btn" (click)="dismissToast(toast, $event)" aria-label="Close">
          <svg viewBox="0 0 20 20" fill="currentColor"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"/></svg>
        </button>

      </div>
    </div>
  `,
  styleUrls: [
    '../styles/_toast-base.scss',
    '../styles/_toast-theme.scss',
    '../styles/_toast-gooey.scss',
  ],
})
export class ToastContainerComponent implements OnInit, OnDestroy, AfterViewInit {

  @ViewChildren('toastEl') toastEls!: QueryList<ElementRef<HTMLElement>>;

  toasts: ToastState[] = [];

  // Config fields (reactively updated from ToastConfigService)
  position: ToastPosition = 'bottom-right';
  duration: number = 4000;
  gap: number = 14;
  offset: number | string = '24px';
  theme: 'light' | 'dark' = 'light';
  maxQueue: number = Infinity;
  showProgress: boolean = false;

  private _containerHovered = false;
  private _sub!: Subscription;
  private _configSub!: Subscription;
  private _timers = new Map<string | number, any>();
  private _progressTimers = new Map<string | number, any>();
  private _morphStates = new Map<string | number, ToastMorphState>();

  constructor(
    private _svc: ToastService,
    private _configSvc: ToastConfigService,
    private _cd: ChangeDetectorRef,
    private _zone: NgZone,
    private _elRef: ElementRef,
  ) {}

  // ── Lifecycle ───────────────────────────────────────────────────────

  ngOnInit(): void {
    // Subscribe to reactive config changes (live updates from builder)
    this._configSub = this._configSvc.config$.subscribe(cfg => {
      this.position = cfg.position;
      this.duration = cfg.duration;
      this.gap = cfg.gap;
      this.offset = cfg.offset;
      this.theme = cfg.theme;
      this.maxQueue = cfg.maxQueue;
      this.showProgress = cfg.showProgress;
      this._cd.markForCheck();
    });

    this._sub = this._svc.events$.subscribe(evt => {
      switch (evt.action) {
        case 'add':
          if (evt.toast) this._addToast(evt.toast);
          break;
        case 'dismiss':
          if (evt.id != null) {
            this._startDismiss(evt.id);
          } else {
            [...this.toasts].forEach(t => this._startDismiss(t.id));
          }
          break;
        case 'update':
          if (evt.id != null && evt.updates) this._updateToast(evt.id, evt.updates);
          break;
      }
      this._cd.markForCheck();
    });
  }

  ngAfterViewInit(): void {
    this.toastEls.changes.subscribe(() => {
      this.toasts.forEach(toast => {
        if (!this._morphStates.has(toast.id)) return;
        const ms = this._morphStates.get(toast.id)!;
        if (ms.pw === 0) {
          this._zone.runOutsideAngular(() => {
            requestAnimationFrame(() => this._initMorph(toast));
          });
        }
      });
    });
  }

  ngOnDestroy(): void {
    this._sub?.unsubscribe();
    this._configSub?.unsubscribe();
    this._timers.forEach(t => clearTimeout(t));
    this._progressTimers.forEach(t => clearInterval(t));
    this._morphStates.forEach(ms => {
      if (ms.animId) cancelAnimationFrame(ms.animId);
      if (ms.entryAnimId) cancelAnimationFrame(ms.entryAnimId);
      if (ms.exitAnimId) cancelAnimationFrame(ms.exitAnimId);
      if (ms.shakeAnimId) cancelAnimationFrame(ms.shakeAnimId);
    });
  }

  // ── Getters ─────────────────────────────────────────────────────────

  get positionClass(): string { return `pos-${this.position}`; }

  get offsetValue(): string {
    return typeof this.offset === 'number' ? `${this.offset}px` : String(this.offset);
  }

  get isRight(): boolean { return this.position.includes('right'); }
  get isCenter(): boolean { return this.position.includes('center'); }

  // ── Template helpers ────────────────────────────────────────────────

  trackById(_: number, toast: ToastState): string | number { return toast.id; }

  getToastClasses(toast: ToastState): string {
    const ms = this._morphStates.get(toast.id);
    return [
      `type-${toast.type}`,
      ms?.showBody ? 'phase-expanded' : 'phase-pill',
      toast.loading ? 'is-loading' : '',
      toast.options.classNames?.wrapper || '',
    ].filter(Boolean).join(' ');
  }

  getMorphState(toast: ToastState): ToastMorphState | undefined {
    return this._morphStates.get(toast.id);
  }

  getBlobFill(toast: ToastState): string {
    if (toast.options.fillColor) return toast.options.fillColor;
    return this.theme === 'dark' ? '#1a1a1a' : '#ffffff';
  }

  getWrapperBaseTransform(_toast: ToastState): string {
    return this.isRight ? 'scaleX(-1)' : '';
  }

  getContentTransform(): string {
    return this.isRight ? 'scaleX(-1)' : '';
  }

  getContentTextAlign(): string {
    if (this.isCenter) return 'center';
    if (this.isRight) return 'right';
    return 'left';
  }

  isAnyHovered(): boolean {
    if (this._containerHovered) return true;
    for (const ms of this._morphStates.values()) {
      if (ms.hovered) return true;
    }
    return false;
  }

  // ── User interactions ───────────────────────────────────────────────

  onActionClick(toast: ToastState, event: MouseEvent): void {
    event.stopPropagation();
    if (!toast.action) return;
    const actionRef = toast.action;
    const successLabel = actionRef.successLabel;

    if (successLabel) {
      const ms = this._morphStates.get(toast.id);
      if (ms) {
        ms.expandedDims = { pw: ms.pw, bw: ms.bw, th: ms.th };
        ms.collapsing = true;
      }
      toast.actionSuccess = successLabel;
      toast.title = successLabel;
      toast.type = 'success';
      toast.description = undefined;
      toast.action = undefined;
      this._clearTimer(toast.id);
      this._cd.markForCheck();

      if (ms && ms.showBody && ms.t > 0) {
        ms.showBody = false;
        this._cd.markForCheck();
        this._zone.runOutsideAngular(() => {
          requestAnimationFrame(() => {
            this._measure(toast);
            this._collapseAfterAction(toast);
          });
        });
      }
    }

    try { actionRef.onClick(); } catch { /* swallow */ }
  }

  dismissToast(toast: ToastState, event: MouseEvent): void {
    event.stopPropagation();
    const ms = this._morphStates.get(toast.id);
    if (ms) ms.userDismissed = true;
    this._startDismiss(toast.id);
  }

  onContainerEnter(): void {
    this._containerHovered = true;
    this._onHoverChange();
  }

  onContainerLeave(): void {
    this._containerHovered = false;
    this._onHoverChange();
  }

  onToastEnter(toast: ToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;
    ms.hovered = true;
    this._onHoverChange();
    if (ms.dismissed && !ms.userDismissed && (toast.description || toast.action)) {
      this._reExpandToast(toast);
    }
  }

  onToastLeave(toast: ToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;
    ms.hovered = false;
    this._onHoverChange();
  }

  // ── Internal: Toast lifecycle ───────────────────────────────────────

  private _addToast(toast: ToastState): void {
    if (toast.duration === undefined || toast.duration === null) {
      toast.duration = this.duration;
    }
    if (!toast.createdAt) { toast.createdAt = new Date(); }
    while (this.toasts.length >= this.maxQueue) {
      const oldest = this.toasts[0];
      if (oldest) this._startDismiss(oldest.id);
    }

    this._morphStates.set(toast.id, {
      t: 0, targetT: 0,
      pw: 0, bw: 0, th: 0,
      animId: null, entryAnimId: null, entryProgress: 0,
      exitAnimId: null, exitProgress: 0, shakeAnimId: null,
      showBody: false, dismissed: false, userDismissed: false,
      collapsing: false, hovered: false,
      expandedDims: { pw: 0, bw: 0, th: 0 },
      remainingMs: null, timerStart: 0, prevPhase: toast.type,
    });

    this.toasts.push(toast);
    toast.phase = 'pill';
  }

  private _updateToast(id: string | number, updates: Partial<ToastState>): void {
    const toast = this.toasts.find(t => t.id === id);
    if (!toast) return;
    const ms = this._morphStates.get(id);
    const prevType = toast.type;
    const prevLoading = toast.loading;
    Object.assign(toast, updates);

    if (ms && toast.type === 'error' && prevType !== 'error' && !ms.dismissed) {
      this._zone.runOutsideAngular(() => this._runShakeAnimation(toast));
    }
    if (ms) ms.prevPhase = toast.type;

    if (!toast.loading && toast.duration > 0) {
      const nowHasBody = !!(toast.description || toast.action);
      if (ms && prevLoading && !ms.dismissed) {
        if (nowHasBody && !ms.showBody) {
          this._zone.runOutsideAngular(() => {
            setTimeout(() => {
              if (ms.dismissed) return;
              this._zone.run(() => { ms.showBody = true; this._cd.markForCheck(); });
              requestAnimationFrame(() => {
                this._measure(toast);
                this._animateMorph(toast, 0, 1, EXPAND_DUR, () => {
                  this._zone.run(() => {
                    toast.phase = 'expanded';
                    this._cd.markForCheck();
                    this._startAutoClose(toast);
                  });
                });
              });
            }, EXPAND_BODY_DELAY);
          });
        } else if (!nowHasBody && ms.showBody && ms.t > 0) {
          ms.expandedDims = { pw: ms.pw, bw: ms.bw, th: ms.th };
          ms.collapsing = true;
          ms.showBody = false;
          this._cd.markForCheck();
          this._zone.runOutsideAngular(() => {
            requestAnimationFrame(() => {
              this._measure(toast);
              this._animateMorph(toast, ms.t, 0, COLLAPSE_DUR, () => {
                this._zone.run(() => {
                  ms.collapsing = false;
                  toast.phase = 'pill';
                  this._cd.markForCheck();
                  this._startAutoClose(toast);
                });
              });
            });
          });
        } else {
          this._zone.runOutsideAngular(() => {
            requestAnimationFrame(() => { this._measure(toast); this._flushPath(toast); });
          });
          this._startAutoClose(toast);
        }
      } else {
        this._zone.runOutsideAngular(() => {
          requestAnimationFrame(() => { this._measure(toast); this._flushPath(toast); });
        });
        this._startAutoClose(toast);
      }
    }
    this._cd.markForCheck();
  }

  private _startDismiss(id: string | number): void {
    const toast = this.toasts.find(t => t.id === id);
    if (!toast) return;
    const ms = this._morphStates.get(id);
    if (!ms || ms.dismissed) return;
    this._clearTimer(id);
    toast.options.onDismiss?.(id);
    ms.dismissed = true;
    if (ms.showBody && ms.t > 0) {
      this._collapseAndExit(toast);
    } else {
      this._runExitAnimation(toast);
    }
  }

  private _reExpandToast(toast: ToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;
    if (ms.animId) { cancelAnimationFrame(ms.animId); ms.animId = null; }
    if (ms.exitAnimId) { cancelAnimationFrame(ms.exitAnimId); ms.exitAnimId = null; }
    ms.dismissed = false;
    ms.userDismissed = false;
    ms.collapsing = false;
    ms.remainingMs = null;
    ms.showBody = true;
    this._cd.markForCheck();

    this._zone.runOutsideAngular(() => {
      requestAnimationFrame(() => {
        const wrapperEl = this._getWrapperEl(toast.id);
        if (wrapperEl) {
          wrapperEl.style.opacity = '1';
          wrapperEl.style.transform = this.isRight ? 'scaleX(-1)' : '';
        }
        this._measure(toast);
        const currentT = ms.t;
        this._animateMorph(toast, currentT, 1, EXPAND_DUR, () => {
          this._zone.run(() => {
            toast.phase = 'expanded';
            this._cd.markForCheck();
            this._startAutoClose(toast);
          });
        });
      });
    });
  }

  private _removeToast(id: string | number): void {
    this._zone.run(() => {
      this.toasts = this.toasts.filter(t => t.id !== id);
      const ms = this._morphStates.get(id);
      if (ms) {
        if (ms.animId) cancelAnimationFrame(ms.animId);
        if (ms.entryAnimId) cancelAnimationFrame(ms.entryAnimId);
        if (ms.exitAnimId) cancelAnimationFrame(ms.exitAnimId);
        if (ms.shakeAnimId) cancelAnimationFrame(ms.shakeAnimId);
      }
      this._morphStates.delete(id);
      this._clearTimer(id);
      this._clearProgressTimer(id);
      this._cd.markForCheck();
    });
  }

  // ── Internal: SVG path morphing system ──────────────────────────────

  private _initMorph(toast: ToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;
    this._measure(toast);
    ms.t = 0;
    this._flushPath(toast);
    this._runEntryAnimation(toast);
  }

  private _measure(toast: ToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;
    const wrapperEl = this._getWrapperEl(toast.id);
    if (!wrapperEl) return;
    const contentEl = wrapperEl.querySelector(`[data-content-id="${toast.id}"]`) as HTMLElement;
    const headerEl = wrapperEl.querySelector(`[data-header-id="${toast.id}"]`) as HTMLElement;
    if (!contentEl || !headerEl) return;

    const savedOv = contentEl.style.overflow;
    const savedMH = contentEl.style.maxHeight;
    const savedCW = contentEl.style.width;
    const savedWrW = wrapperEl.style.width;
    contentEl.style.overflow = '';
    contentEl.style.maxHeight = '';
    contentEl.style.width = '';
    wrapperEl.style.width = '';

    const cs = getComputedStyle(contentEl);
    const paddingX = parseFloat(cs.paddingLeft) + parseFloat(cs.paddingRight);
    ms.pw = headerEl.offsetWidth + paddingX;
    ms.bw = contentEl.offsetWidth;
    ms.th = contentEl.offsetHeight;

    wrapperEl.style.width = savedWrW;
    contentEl.style.overflow = savedOv;
    contentEl.style.maxHeight = savedMH;
    contentEl.style.width = savedCW;
  }

  private _flushPath(toast: ToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms || ms.pw <= 0 || ms.bw <= 0 || ms.th <= 0) return;

    const wrapperEl = this._getWrapperEl(toast.id);
    if (!wrapperEl) return;
    const svgEl = wrapperEl.querySelector(`[data-blob-id="${toast.id}"]`) as SVGSVGElement;
    const pathEl = svgEl?.querySelector('path') as SVGPathElement;
    const contentEl = wrapperEl.querySelector(`[data-content-id="${toast.id}"]`) as HTMLElement;
    if (!pathEl || !contentEl) return;

    const { pw, bw, th } = ms;
    const t = Math.max(0, Math.min(1, ms.t));
    const pillW = Math.min(pw, bw);
    const centerPos = this.isCenter;
    const rightSide = this.isRight;

    if (centerPos) {
      const centerBw = Math.max(bw, ms.expandedDims.bw, pw);
      pathEl.setAttribute('d', morphPathCenterRaw(pw, centerBw, th, t));
      svgEl.setAttribute('width', String(Math.ceil(centerBw)));
      svgEl.setAttribute('height', String(Math.ceil(t >= 1 ? th : (PH + (th - PH) * t))));
    } else {
      pathEl.setAttribute('d', morphPathRaw(pw, bw, th, t));
      const currentW = t >= 1 ? bw : (pillW + (bw - pillW) * t);
      const currentH = t >= 1 ? th : (PH + (th - PH) * t);
      svgEl.setAttribute('width', String(Math.ceil(currentW)));
      svgEl.setAttribute('height', String(Math.ceil(currentH)));
    }

    if (t >= 1) {
      wrapperEl.style.width = '';
      contentEl.style.width = '';
      contentEl.style.overflow = '';
      contentEl.style.maxHeight = '';
      contentEl.style.clipPath = '';
      this._positionCloseBtn(wrapperEl, centerPos, 0);
    } else if (t > 0) {
      const currentW = pillW + (bw - pillW) * t;
      const currentH = PH + (th - PH) * t;
      if (centerPos) {
        const centerBw = Math.max(bw, ms.expandedDims.bw, pw);
        wrapperEl.style.width = centerBw + 'px';
        contentEl.style.width = centerBw + 'px';
        contentEl.style.overflow = 'hidden';
        contentEl.style.maxHeight = currentH + 'px';
        const clip = (centerBw - currentW) / 2;
        contentEl.style.clipPath = `inset(0 ${clip}px 0 ${clip}px)`;
        this._positionCloseBtn(wrapperEl, true, clip);
      } else {
        wrapperEl.style.width = currentW + 'px';
        contentEl.style.width = bw + 'px';
        contentEl.style.overflow = 'hidden';
        contentEl.style.maxHeight = currentH + 'px';
        const clip = bw - currentW;
        contentEl.style.clipPath = rightSide ? `inset(0 0 0 ${clip}px)` : `inset(0 ${clip}px 0 0)`;
        this._positionCloseBtn(wrapperEl, false, 0);
      }
    } else {
      if (centerPos) {
        const centerBw = Math.max(bw, ms.expandedDims.bw, pw);
        wrapperEl.style.width = centerBw + 'px';
        contentEl.style.width = centerBw + 'px';
        const clip = (centerBw - pillW) / 2;
        contentEl.style.clipPath = `inset(0 ${clip}px 0 ${clip}px)`;
        this._positionCloseBtn(wrapperEl, true, clip);
      } else {
        wrapperEl.style.width = pillW + 'px';
        contentEl.style.width = '';
        contentEl.style.clipPath = '';
        this._positionCloseBtn(wrapperEl, false, 0);
      }
      contentEl.style.overflow = 'hidden';
      contentEl.style.maxHeight = PH + 'px';
    }
  }

  private _animateMorph(toast: ToastState, from: number, to: number, durationMs: number, onComplete?: () => void): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;
    if (ms.animId) cancelAnimationFrame(ms.animId);
    const startTime = performance.now();

    const tick = (now: number) => {
      const elapsed = now - startTime;
      const raw = Math.min(1, elapsed / durationMs);
      ms.t = from + (to - from) * smoothEase(raw);
      this._flushPath(toast);
      if (raw < 1) {
        ms.animId = requestAnimationFrame(tick);
      } else {
        ms.t = to;
        ms.animId = null;
        this._flushPath(toast);
        onComplete?.();
      }
    };
    ms.animId = requestAnimationFrame(tick);
  }

  // ── Entry / Exit / Shake animations ─────────────────────────────────

  private _runEntryAnimation(toast: ToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;
    const wrapperEl = this._getWrapperEl(toast.id);
    if (!wrapperEl) return;
    if (ms.entryAnimId) cancelAnimationFrame(ms.entryAnimId);

    const startTime = performance.now();
    const baseTransform = this.isRight ? 'scaleX(-1) ' : '';
    wrapperEl.style.opacity = '0';
    wrapperEl.style.transform = baseTransform + 'scale(0.6)';

    const tick = (now: number) => {
      const elapsed = now - startTime;
      const raw = Math.min(1, elapsed / ENTRY_DUR);
      const eased = entryEase(raw);
      wrapperEl.style.opacity = String(Math.min(1, raw * 2.5));
      wrapperEl.style.transform = baseTransform + `scale(${0.6 + 0.4 * eased})`;

      if (raw < 1) {
        ms.entryAnimId = requestAnimationFrame(tick);
      } else {
        wrapperEl.style.opacity = '1';
        wrapperEl.style.transform = baseTransform.trim() || '';
        ms.entryAnimId = null;

        this._zone.run(() => {
          if (toast.description || toast.action) {
            setTimeout(() => {
              if (ms.dismissed) return;
              ms.showBody = true;
              this._cd.markForCheck();
              this._zone.runOutsideAngular(() => {
                requestAnimationFrame(() => {
                  this._measure(toast);
                  this._animateMorph(toast, 0, 1, EXPAND_DUR, () => {
                    this._zone.run(() => {
                      toast.phase = 'expanded';
                      this._cd.markForCheck();
                      this._startAutoClose(toast);
                    });
                  });
                });
              });
            }, EXPAND_BODY_DELAY);
          } else {
            this._startAutoClose(toast);
          }
        });
      }
    };
    ms.entryAnimId = requestAnimationFrame(tick);
  }

  private _collapseAndExit(toast: ToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;
    ms.expandedDims = { pw: ms.pw, bw: ms.bw, th: ms.th };
    ms.collapsing = true;
    ms.showBody = false;
    this._cd.markForCheck();

    this._zone.runOutsideAngular(() => {
      requestAnimationFrame(() => {
        const wrapperEl = this._getWrapperEl(toast.id);
        const contentEl = wrapperEl?.querySelector(`[data-content-id="${toast.id}"]`) as HTMLElement;
        const headerEl = wrapperEl?.querySelector(`[data-header-id="${toast.id}"]`) as HTMLElement;

        let targetPw = ms.pw;
        if (contentEl && headerEl) {
          const cs = getComputedStyle(contentEl);
          targetPw = headerEl.offsetWidth + parseFloat(cs.paddingLeft) + parseFloat(cs.paddingRight);
        }
        const savedDims = ms.expandedDims.bw > 0 ? { ...ms.expandedDims } : { pw: ms.pw, bw: ms.bw, th: ms.th };
        const targetDims = { pw: targetPw, bw: targetPw, th: PH };
        const startTime = performance.now();
        const startT = ms.t;
        if (ms.animId) cancelAnimationFrame(ms.animId);

        const tick = (now: number) => {
          const elapsed = now - startTime;
          const raw = Math.min(1, elapsed / COLLAPSE_DUR);
          const eased = smoothEase(raw);
          ms.t = startT * (1 - eased);
          ms.pw = targetDims.pw + (savedDims.pw - targetDims.pw) * ms.t;
          ms.bw = targetDims.bw + (savedDims.bw - targetDims.bw) * ms.t;
          ms.th = targetDims.th + (savedDims.th - targetDims.th) * ms.t;
          this._flushPath(toast);
          if (raw < 1) {
            ms.animId = requestAnimationFrame(tick);
          } else {
            ms.t = 0; ms.animId = null; ms.collapsing = false;
            ms.pw = targetDims.pw; ms.bw = targetDims.bw; ms.th = targetDims.th;
            this._flushPath(toast);
            setTimeout(() => {
              if (!ms.userDismissed && (ms.hovered || this._containerHovered)) return;
              this._runExitAnimation(toast);
            }, DISMISS_AFTER_COLLAPSE);
          }
        };
        ms.animId = requestAnimationFrame(tick);
      });
    });
  }

  private _collapseAfterAction(toast: ToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;
    const wrapperEl = this._getWrapperEl(toast.id);
    const contentEl = wrapperEl?.querySelector(`[data-content-id="${toast.id}"]`) as HTMLElement;
    const headerEl = wrapperEl?.querySelector(`[data-header-id="${toast.id}"]`) as HTMLElement;

    let targetPw = ms.pw;
    if (contentEl && headerEl) {
      const cs = getComputedStyle(contentEl);
      targetPw = headerEl.offsetWidth + parseFloat(cs.paddingLeft) + parseFloat(cs.paddingRight);
    }
    const savedDims = ms.expandedDims.bw > 0 ? { ...ms.expandedDims } : { pw: ms.pw, bw: ms.bw, th: ms.th };
    const targetDims = { pw: targetPw, bw: targetPw, th: PH };
    const startTime = performance.now();
    const startT = ms.t;
    if (ms.animId) cancelAnimationFrame(ms.animId);

    const tick = (now: number) => {
      const elapsed = now - startTime;
      const raw = Math.min(1, elapsed / COLLAPSE_DUR);
      const eased = smoothEase(raw);
      ms.t = startT * (1 - eased);
      ms.pw = targetDims.pw + (savedDims.pw - targetDims.pw) * ms.t;
      ms.bw = targetDims.bw + (savedDims.bw - targetDims.bw) * ms.t;
      ms.th = targetDims.th + (savedDims.th - targetDims.th) * ms.t;
      this._flushPath(toast);
      if (raw < 1) {
        ms.animId = requestAnimationFrame(tick);
      } else {
        ms.t = 0; ms.animId = null; ms.collapsing = false;
        ms.pw = targetDims.pw; ms.bw = targetDims.bw; ms.th = targetDims.th;
        this._flushPath(toast);
        this._zone.run(() => {
          setTimeout(() => this._startDismiss(toast.id), 1200);
        });
      }
    };
    ms.animId = requestAnimationFrame(tick);
  }

  private _runExitAnimation(toast: ToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;
    const wrapperEl = this._getWrapperEl(toast.id);
    if (!wrapperEl) return;
    if (ms.exitAnimId) cancelAnimationFrame(ms.exitAnimId);
    wrapperEl.style.clipPath = '';

    const startTime = performance.now();
    const startOpacity = parseFloat(wrapperEl.style.opacity || '1');
    const baseTransform = this.isRight ? 'scaleX(-1) ' : '';

    const tick = (now: number) => {
      const elapsed = now - startTime;
      const raw = Math.min(1, elapsed / EXIT_DUR);
      const eased = raw * raw;
      wrapperEl.style.opacity = String(startOpacity * (1 - eased));
      wrapperEl.style.transform = baseTransform + `scale(${1 - 0.15 * eased}) translateY(${8 * eased}px)`;
      if (raw < 1) {
        ms.exitAnimId = requestAnimationFrame(tick);
      } else {
        ms.exitAnimId = null;
        this._removeToast(toast.id);
      }
    };
    ms.exitAnimId = requestAnimationFrame(tick);
  }

  private _runShakeAnimation(toast: ToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;
    const wrapperEl = this._getWrapperEl(toast.id);
    if (!wrapperEl) return;
    if (ms.shakeAnimId) cancelAnimationFrame(ms.shakeAnimId);

    const startTime = performance.now();
    const duration = 400;
    const baseTransform = this.isRight ? 'scaleX(-1) ' : '';

    const tick = (now: number) => {
      const elapsed = now - startTime;
      const raw = Math.min(1, elapsed / duration);
      const shake = Math.sin(raw * Math.PI * 6) * (1 - raw) * 3;
      wrapperEl.style.transform = baseTransform + `translateX(${shake}px)`;
      if (raw < 1) {
        ms.shakeAnimId = requestAnimationFrame(tick);
      } else {
        wrapperEl.style.transform = baseTransform.trim() || '';
        ms.shakeAnimId = null;
      }
    };
    ms.shakeAnimId = requestAnimationFrame(tick);
  }

  // ── Auto-close & timers ─────────────────────────────────────────────

  private _startAutoClose(toast: ToastState): void {
    if (toast.duration <= 0 || toast.loading) return;
    this._clearTimer(toast.id);
    this._clearProgressTimer(toast.id);
    const ms = this._morphStates.get(toast.id);
    if (ms && (ms.hovered || this._containerHovered)) return;

    const delay = ms?.remainingMs ?? toast.duration;
    if (ms) { ms.timerStart = Date.now(); ms.remainingMs = null; }

    const timer = setTimeout(() => {
      if (ms && (ms.hovered || this._containerHovered)) {
        if (ms) ms.remainingMs = 0;
        return;
      }
      this._zone.run(() => {
        toast.options.onAutoClose?.(toast.id);
        this._startDismiss(toast.id);
      });
    }, delay);
    this._timers.set(toast.id, timer);
  }

  private _onHoverChange(): void {
    const anyHovered = this.isAnyHovered();
    this.toasts.forEach(toast => {
      const ms = this._morphStates.get(toast.id);
      if (!ms || ms.dismissed) return;
      if (anyHovered) {
        const timer = this._timers.get(toast.id);
        if (timer) {
          clearTimeout(timer);
          this._timers.delete(toast.id);
          if (ms.timerStart > 0) {
            const elapsed = Date.now() - ms.timerStart;
            ms.remainingMs = Math.max(0, (ms.remainingMs ?? toast.duration) - elapsed);
          }
        }
      } else {
        if (ms.remainingMs != null && ms.remainingMs >= 0 && !ms.dismissed && !toast.loading) {
          this._startAutoClose(toast);
        }
      }
    });
    this._cd.markForCheck();
  }

  private _clearTimer(id: string | number): void {
    const t = this._timers.get(id);
    if (t) { clearTimeout(t); this._timers.delete(id); }
  }

  private _clearProgressTimer(id: string | number): void {
    const t = this._progressTimers.get(id);
    if (t) { clearInterval(t); this._progressTimers.delete(id); }
  }

  // ── DOM helpers ─────────────────────────────────────────────────────

  private _positionCloseBtn(wrapperEl: HTMLElement, isCenter: boolean, clipInset: number): void {
    const closeBtn = wrapperEl.querySelector('.gooey-close-btn') as HTMLElement;
    if (!closeBtn) return;
    if (isCenter && clipInset > 0) {
      closeBtn.style.right = (clipInset - 6) + 'px';
      const over = 28;
      const clipR = Math.max(0, clipInset - over);
      const clipL = Math.max(0, clipInset - over);
      wrapperEl.style.clipPath = `inset(-${over}px ${clipR}px -16px ${clipL}px)`;
    } else {
      closeBtn.style.right = '';
      wrapperEl.style.clipPath = '';
    }
  }

  private _getWrapperEl(toastId: string | number): HTMLElement | null {
    return this._elRef.nativeElement.querySelector(`[data-toast-id="${toastId}"]`);
  }
}
