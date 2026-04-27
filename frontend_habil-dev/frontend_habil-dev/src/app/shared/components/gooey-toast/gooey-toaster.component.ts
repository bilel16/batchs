/**
 * @fileoverview GooeyToasterComponent — Pixel-perfect Angular clone of goey-toast
 *
 * Architecture cloned from the original React implementation:
 * - SVG <path> blob shape via morphPathRaw() (ported verbatim from goey-toast)
 * - morphPathCenterRaw() for center-aligned positions
 * - scaleX(-1) mirror trick on wrapper for right-aligned positions
 * - Imperative requestAnimationFrame animation driving morph parameter t ∈ [0,1]
 * - CSS drop-shadow on SVG (NO feGaussianBlur/feColorMatrix gooey filter)
 * - Content overflow:hidden + maxHeight + clipPath clipping during morph
 * - PH=34 pill height, pr=17 pill radius (exact original constants)
 * - Expand: 600ms, Collapse: 900ms, Ease: cubic-bezier(0.4, 0, 0.2, 1)
 * - Entry: 330ms spring scale, Exit: 240ms fade+scale
 * - Hover pauses auto-close & re-expands if collapsing (exact original behavior)
 * - Error shake animation on phase transition to 'error'
 */

import {
  Component,
  Input,
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
import { GooeyToastService } from './gooey-toast.service';
import {
  GooeyToastPosition,
  GooeyToastState,
} from './gooey-toast.types';

// ── EXACT CONSTANTS FROM ORIGINAL ─────────────────────────────────────────
const PH = 34;                          // pill height (px)
const PR = PH / 2;                      // pill border-radius = 17
const DEFAULT_EXPAND_DUR = 600;         // ms (0.6s in original)
const DEFAULT_COLLAPSE_DUR = 900;       // ms (0.9s in original)
const ENTRY_DUR = 330;                  // ms — toast appears
const EXIT_DUR = 240;                   // ms — toast fades out
const EXPAND_BODY_DELAY = 330;          // ms — delay before showing body text
const DISMISS_AFTER_COLLAPSE = 800;     // ms — delay after collapse before sonner dismiss
const SMOOTH_EASE = [0.4, 0, 0.2, 1];  // cubic-bezier from original

// ── MORPH PATH — PORTED VERBATIM FROM goey-toast/dist/index.js ───────────
function morphPathRaw(pw: number, bw: number, th: number, t: number): string {
  const pr = PR;
  const pillW = Math.min(pw, bw);
  const bodyH = PH + (th - PH) * t;
  if (t <= 0 || bodyH - PH < 8) {
    return [
      `M 0,${pr}`,
      `A ${pr},${pr} 0 0 1 ${pr},0`,
      `H ${pillW - pr}`,
      `A ${pr},${pr} 0 0 1 ${pillW},${pr}`,
      `A ${pr},${pr} 0 0 1 ${pillW - pr},${PH}`,
      `H ${pr}`,
      `A ${pr},${pr} 0 0 1 0,${pr}`,
      `Z`
    ].join(' ');
  }
  const curve = 14 * t;
  const cr = Math.min(16, (bodyH - PH) * 0.45);
  const bodyW = pillW + (bw - pillW) * t;
  const bodyTop = PH - curve;
  const qEndX = Math.min(pillW + curve, bodyW - cr);
  return [
    `M 0,${pr}`,
    `A ${pr},${pr} 0 0 1 ${pr},0`,
    `H ${pillW - pr}`,
    `A ${pr},${pr} 0 0 1 ${pillW},${pr}`,
    `L ${pillW},${bodyTop}`,
    `Q ${pillW},${bodyTop + curve} ${qEndX},${bodyTop + curve}`,
    `H ${bodyW - cr}`,
    `A ${cr},${cr} 0 0 1 ${bodyW},${bodyTop + curve + cr}`,
    `L ${bodyW},${bodyH - cr}`,
    `A ${cr},${cr} 0 0 1 ${bodyW - cr},${bodyH}`,
    `H ${cr}`,
    `A ${cr},${cr} 0 0 1 0,${bodyH - cr}`,
    `Z`
  ].join(' ');
}

// ── CENTER MORPH PATH — PORTED VERBATIM FROM goey-toast/dist/index.js ────
function morphPathCenterRaw(pw: number, bw: number, th: number, t: number): string {
  const pr = PR;
  const pillW = Math.min(pw, bw);
  const pillOffset = (bw - pillW) / 2;
  if (t <= 0 || PH + (th - PH) * t - PH < 8) {
    return [
      `M ${pillOffset},${pr}`,
      `A ${pr},${pr} 0 0 1 ${pillOffset + pr},0`,
      `H ${pillOffset + pillW - pr}`,
      `A ${pr},${pr} 0 0 1 ${pillOffset + pillW},${pr}`,
      `A ${pr},${pr} 0 0 1 ${pillOffset + pillW - pr},${PH}`,
      `H ${pillOffset + pr}`,
      `A ${pr},${pr} 0 0 1 ${pillOffset},${pr}`,
      `Z`
    ].join(' ');
  }
  const bodyH = PH + (th - PH) * t;
  const curve = 14 * t;
  const cr = Math.min(16, (bodyH - PH) * 0.45);
  const bodyTop = PH - curve;
  const bodyCenter = bw / 2;
  const halfBodyW = pillW / 2 + (bw - pillW) / 2 * t;
  const bodyLeft = bodyCenter - halfBodyW;
  const bodyRight = bodyCenter + halfBodyW;
  const qLeftX = Math.max(bodyLeft + cr, pillOffset - curve);
  const qRightX = Math.min(bodyRight - cr, pillOffset + pillW + curve);
  return [
    `M ${pillOffset},${pr}`,
    `A ${pr},${pr} 0 0 1 ${pillOffset + pr},0`,
    `H ${pillOffset + pillW - pr}`,
    `A ${pr},${pr} 0 0 1 ${pillOffset + pillW},${pr}`,
    `L ${pillOffset + pillW},${bodyTop}`,
    `Q ${pillOffset + pillW},${bodyTop + curve} ${qRightX},${bodyTop + curve}`,
    `H ${bodyRight - cr}`,
    `A ${cr},${cr} 0 0 1 ${bodyRight},${bodyTop + curve + cr}`,
    `L ${bodyRight},${bodyH - cr}`,
    `A ${cr},${cr} 0 0 1 ${bodyRight - cr},${bodyH}`,
    `H ${bodyLeft + cr}`,
    `A ${cr},${cr} 0 0 1 ${bodyLeft},${bodyH - cr}`,
    `L ${bodyLeft},${bodyTop + curve + cr}`,
    `A ${cr},${cr} 0 0 1 ${bodyLeft + cr},${bodyTop + curve}`,
    `H ${qLeftX}`,
    `Q ${pillOffset},${bodyTop + curve} ${pillOffset},${bodyTop}`,
    `Z`
  ].join(' ');
}

// ── EASING — exact cubic-bezier(0.4, 0, 0.2, 1) sampler ──────────────────
function cubicBezier(p1x: number, p1y: number, p2x: number, p2y: number) {
  return (t: number): number => {
    let lo = 0, hi = 1, mid: number;
    for (let i = 0; i < 20; i++) {
      mid = (lo + hi) / 2;
      const x = 3 * p1x * mid * (1 - mid) * (1 - mid) + 3 * p2x * mid * mid * (1 - mid) + mid * mid * mid;
      if (x < t) lo = mid; else hi = mid;
    }
    mid = (lo + hi) / 2;
    return 3 * p1y * mid * (1 - mid) * (1 - mid) + 3 * p2y * mid * mid * (1 - mid) + mid * mid * mid;
  };
}

const smoothEase = cubicBezier(0.4, 0, 0.2, 1);
const entryEase = cubicBezier(0.34, 1.56, 0.64, 1); // spring overshoot for entry

// Per-toast morph state
interface ToastMorphState {
  t: number;             // current morph 0–1
  targetT: number;       // target morph value
  pw: number;            // pill width
  bw: number;            // body width
  th: number;            // total height
  animId: number | null; // rAF id for morph
  entryAnimId: number | null;
  entryProgress: number; // 0→1 entry animation
  exitAnimId: number | null;
  exitProgress: number;  // 0→1 exit animation
  shakeAnimId: number | null;  // rAF id for shake
  showBody: boolean;     // whether expanded body text is visible
  dismissed: boolean;    // has started exit (pre-dismiss collapsing)
  userDismissed: boolean; // user clicked close (skip hover rescue)
  collapsing: boolean;   // currently collapsing morph
  hovered: boolean;      // mouse is over this toast
  expandedDims: { pw: number; bw: number; th: number }; // saved dims at expand
  remainingMs: number | null;  // remaining auto-close ms when paused
  timerStart: number;    // timestamp when auto-close timer started
  prevPhase: string;     // previous phase for shake detection
}

@Component({
  selector: 'gooey-toaster',
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

        <!-- Blob SVG — single <path> with drop-shadow, NO gooey filter -->
        <svg class="gooey-blob-svg" aria-hidden="true"
             [attr.data-blob-id]="toast.id">
          <path [attr.fill]="getBlobFill(toast)"
                [attr.stroke]="toast.options.borderColor || 'none'"
                [attr.stroke-width]="toast.options.borderColor ? (toast.options.borderWidth || 1.5) : 0" />
        </svg>

        <!-- Content -->
        <div class="gooey-content"
             [attr.data-content-id]="toast.id"
             [ngClass]="toast.options.classNames?.content || ''"
             [style.transform]="getContentTransform()"
             [style.text-align]="getContentTextAlign()">

          <!-- Header row -->
          <div class="gooey-header"
               [attr.data-header-id]="toast.id"
               [ngClass]="toast.options.classNames?.header || ''">

            <!-- Type icons (Lucide stroke, 24×24 viewBox, 18px rendered) -->
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

            <!-- Loading spinner -->
            <span class="gooey-icon gooey-spinner" *ngIf="toast.loading">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M21 12a9 9 0 1 1-6.219-8.56"/>
              </svg>
            </span>

            <span class="gooey-title" [ngClass]="toast.options.classNames?.title || ''">{{ toast.title }}</span>

            <!-- Timestamp in pill (no body) -->
            <span class="gooey-timestamp"
                  *ngIf="!getMorphState(toast)?.showBody && !toast.description && !toast.action && !toast.actionSuccess">
              {{ toast.createdAt | date:'h:mm:ss a' }}
            </span>
          </div>

          <!-- Description (visible only when body is shown) -->
          <div class="gooey-description"
               *ngIf="getMorphState(toast)?.showBody && toast.description && !getMorphState(toast)?.dismissed"
               [ngClass]="toast.options.classNames?.description || ''">
            <span class="gooey-timestamp-body">{{ toast.createdAt | date:'h:mm:ss a' }}</span>
            {{ toast.description }}
          </div>

          <!-- Timestamp when action only, no description -->
          <div class="gooey-timestamp"
               *ngIf="getMorphState(toast)?.showBody && !toast.description && toast.action && !toast.actionSuccess && !getMorphState(toast)?.dismissed"
               style="text-align: right; margin-top: 8px; padding-left: 0;">
            {{ toast.createdAt | date:'h:mm:ss a' }}
          </div>          <!-- Action Button -->
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

          <!-- Progress bar -->
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

        <!-- Close button -->
        <button class="gooey-close-btn" (click)="dismissToast(toast, $event)" aria-label="Fermer">
          <svg viewBox="0 0 20 20" fill="currentColor"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"/></svg>
        </button>

      </div>
    </div>
  `,
  styleUrls: ['./gooey-toaster.component.scss'],
})
export class GooeyToasterComponent implements OnInit, OnDestroy, AfterViewInit {

  @Input() position: GooeyToastPosition = 'bottom-right';
  @Input() duration: number = 4000;
  @Input() gap: number = 14;
  @Input() offset: number | string = '24px';
  @Input() theme: 'light' | 'dark' = 'light';
  @Input() maxQueue: number = Infinity;
  @Input() showProgress: boolean = false;

  @ViewChildren('toastEl') toastEls!: QueryList<ElementRef<HTMLElement>>;

  toasts: GooeyToastState[] = [];

  /** Container-level hover state (original tracks this globally) */
  private _containerHovered = false;

  private _sub!: Subscription;
  private _configSub!: Subscription;
  private _timers = new Map<string | number, any>();
  private _progressTimers = new Map<string | number, any>();
  private _morphStates = new Map<string | number, ToastMorphState>();

  constructor(
    private _svc: GooeyToastService,
    private _cd: ChangeDetectorRef,
    private _zone: NgZone,
    private _elRef: ElementRef,
  ) {}

  // ── Lifecycle ───────────────────────────────────────────────────────

  ngOnInit(): void {
    this._configSub = this._svc.config$.subscribe(cfg => {
      if (cfg.position) this.position = cfg.position;
      if (cfg.theme) this.theme = cfg.theme;
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

  get positionClass(): string {
    return `pos-${this.position}`;
  }

  get offsetValue(): string {
    return typeof this.offset === 'number' ? `${this.offset}px` : this.offset;
  }

  /** Is current position right-aligned? (original uses scaleX(-1) mirror) */
  get isRight(): boolean {
    return this.position.includes('right');
  }

  /** Is current position center-aligned? (uses morphPathCenterRaw) */
  get isCenter(): boolean {
    return this.position.includes('center');
  }

  // ── Template helpers ────────────────────────────────────────────────

  trackById(_: number, toast: GooeyToastState): string | number {
    return toast.id;
  }

  getToastClasses(toast: GooeyToastState): string {
    const ms = this._morphStates.get(toast.id);
    const cls = [
      `type-${toast.type}`,
      ms?.showBody ? 'phase-expanded' : 'phase-pill',
      toast.loading ? 'is-loading' : '',
      toast.options.classNames?.wrapper || '',
    ];
    return cls.filter(Boolean).join(' ');
  }

  getMorphState(toast: GooeyToastState): ToastMorphState | undefined {
    return this._morphStates.get(toast.id);
  }

  getBlobFill(toast: GooeyToastState): string {
    if (toast.options.fillColor) return toast.options.fillColor;
    return this.theme === 'dark' ? '#1a1a1a' : '#ffffff';
  }

  /**
   * Original uses scaleX(-1) on wrapper for right positions so the blob
   * (which opens leftward) appears to open rightward. The content is then
   * counter-mirrored with scaleX(-1) + textAlign right.
   */
  getWrapperBaseTransform(_toast: GooeyToastState): string {
    if (this.isRight) return 'scaleX(-1)';
    return '';
  }

  /** Content counter-transform: un-mirror for right, center for center */
  getContentTransform(): string {
    if (this.isRight) return 'scaleX(-1)';
    return '';
  }

  getContentTextAlign(): string {
    if (this.isCenter) return 'center';
    if (this.isRight) return 'right';
    return 'left';
  }

  /** Is any toast or the container hovered? (for progress pause) */
  isAnyHovered(): boolean {
    if (this._containerHovered) return true;
    for (const ms of this._morphStates.values()) {
      if (ms.hovered) return true;
    }
    return false;
  }  onActionClick(toast: GooeyToastState, event: MouseEvent): void {
    event.stopPropagation();
    if (!toast.action) return;

    // Capture references before mutating (original captures via useCallback closure)
    const actionRef = toast.action;
    const successLabel = actionRef.successLabel;

    if (successLabel) {
      // Original order: save dims → set collapsing → setActionSuccess → THEN onClick
      // (lines 1080-1087 of original)
      const ms = this._morphStates.get(toast.id);
      if (ms) {
        ms.expandedDims = { pw: ms.pw, bw: ms.bw, th: ms.th };
        ms.collapsing = true;
      }

      // Set actionSuccess string → derives effectiveTitle/Phase/Description/Action
      toast.actionSuccess = successLabel;
      toast.title = successLabel;          // effectiveTitle = actionSuccess ?? title
      toast.type = 'success';              // effectivePhase = actionSuccess ? "success" : phase
      toast.description = undefined;       // effectiveDescription = actionSuccess ? void 0 : description
      toast.action = undefined;            // effectiveAction = actionSuccess ? void 0 : action

      this._clearTimer(toast.id);
      this._cd.markForCheck();

      // Trigger collapse → then dismiss after 1200ms once showBody is false
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

    // Call the action handler AFTER state changes (matches original order)
    try {
      actionRef.onClick();
    } catch { /* swallow like original */ }
  }
  dismissToast(toast: GooeyToastState, event: MouseEvent): void {
    event.stopPropagation();
    // Mark as user-initiated so hover rescue won't re-expand
    const ms = this._morphStates.get(toast.id);
    if (ms) ms.userDismissed = true;
    this._startDismiss(toast.id);
  }

  // ── Hover handlers ──────────────────────────────────────────────────

  onContainerEnter(): void {
    this._containerHovered = true;
    this._onHoverChange();
  }

  onContainerLeave(): void {
    this._containerHovered = false;
    this._onHoverChange();
  }
  onToastEnter(toast: GooeyToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;
    ms.hovered = true;
    this._onHoverChange();

    // If this toast is in the process of collapsing/dismissing, re-expand it
    // BUT NOT if the user explicitly clicked the close button
    if (ms.dismissed && !ms.userDismissed && (toast.description || toast.action)) {
      this._reExpandToast(toast);
    }
  }

  onToastLeave(toast: GooeyToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;
    ms.hovered = false;
    this._onHoverChange();
  }

  // ── Internal: Toast lifecycle ───────────────────────────────────────

  private _addToast(toast: GooeyToastState): void {
    if (toast.duration === undefined || toast.duration === null) {
      toast.duration = this.duration;
    }
    if (!toast.createdAt) {
      toast.createdAt = new Date();
    }
    while (this.toasts.length >= this.maxQueue) {
      const oldest = this.toasts[0];
      if (oldest) this._startDismiss(oldest.id);
    }

    // Initialize morph state
    this._morphStates.set(toast.id, {
      t: 0,
      targetT: 0,
      pw: 0, bw: 0, th: 0,
      animId: null,
      entryAnimId: null,
      entryProgress: 0,
      exitAnimId: null,
      exitProgress: 0,
      shakeAnimId: null,      showBody: false,
      dismissed: false,
      userDismissed: false,
      collapsing: false,
      hovered: false,
      expandedDims: { pw: 0, bw: 0, th: 0 },
      remainingMs: null,
      timerStart: 0,
      prevPhase: toast.type,
    });

    this.toasts.push(toast);
    toast.phase = 'pill';
  }
  private _updateToast(id: string | number, updates: Partial<GooeyToastState>): void {
    const toast = this.toasts.find(t => t.id === id);
    if (!toast) return;
    const ms = this._morphStates.get(id);    const prevType = toast.type;
    const prevLoading = toast.loading;
    Object.assign(toast, updates);

    // Error shake: if transitioning TO error phase
    if (ms && toast.type === 'error' && prevType !== 'error' && !ms.dismissed) {
      this._zone.runOutsideAngular(() => {
        this._runShakeAnimation(toast);
      });
    }
    if (ms) ms.prevPhase = toast.type;    if (!toast.loading && toast.duration > 0) {
      const nowHasBody = !!(toast.description || toast.action);

      if (ms && prevLoading && !ms.dismissed) {
        if (nowHasBody && !ms.showBody) {
          // Case 1: Loading → resolved, gained body content → expand
          this._zone.runOutsideAngular(() => {
            setTimeout(() => {
              if (ms.dismissed) return;
              this._zone.run(() => {
                ms.showBody = true;
                this._cd.markForCheck();
              });
              requestAnimationFrame(() => {
                this._measure(toast);
                this._animateMorph(toast, 0, 1, DEFAULT_EXPAND_DUR, () => {
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
          // Case 2: Loading → resolved, lost body content → collapse to pill
          ms.expandedDims = { pw: ms.pw, bw: ms.bw, th: ms.th };
          ms.collapsing = true;
          ms.showBody = false;
          this._cd.markForCheck();
          this._zone.runOutsideAngular(() => {
            requestAnimationFrame(() => {
              this._measure(toast);
              this._animateMorph(toast, ms.t, 0, DEFAULT_COLLAPSE_DUR, () => {
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
          // Case 3: Content changed but expand state stays same → re-measure
          this._zone.runOutsideAngular(() => {
            requestAnimationFrame(() => {
              this._measure(toast);
              this._flushPath(toast);
            });
          });
          this._startAutoClose(toast);
        }
      } else {
        // Non-promise update — re-measure and restart auto-close
        this._zone.runOutsideAngular(() => {
          requestAnimationFrame(() => {
            this._measure(toast);
            this._flushPath(toast);
          });
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

    // If expanded, collapse first then exit
    if (ms.showBody && ms.t > 0) {
      this._collapseAndExit(toast);
    } else {
      this._runExitAnimation(toast);
    }
  }

  /** Re-expand a toast that was in the process of dismissing (hover rescue) */
  private _reExpandToast(toast: GooeyToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;

    // Cancel any ongoing collapse/exit
    if (ms.animId) { cancelAnimationFrame(ms.animId); ms.animId = null; }
    if (ms.exitAnimId) { cancelAnimationFrame(ms.exitAnimId); ms.exitAnimId = null; }    ms.dismissed = false;
    ms.userDismissed = false;
    ms.collapsing = false;
    ms.remainingMs = null;
    ms.showBody = true;
    this._cd.markForCheck();

    // Re-expand from current t → 1
    this._zone.runOutsideAngular(() => {
      requestAnimationFrame(() => {
        // Restore wrapper opacity/transform (may have been faded by exit)
        const wrapperEl = this._getWrapperEl(toast.id);
        if (wrapperEl) {
          wrapperEl.style.opacity = '1';
          wrapperEl.style.transform = this.isRight ? 'scaleX(-1)' : '';
        }

        this._measure(toast);
        const currentT = ms.t;
        this._animateMorph(toast, currentT, 1, DEFAULT_EXPAND_DUR, () => {
          this._zone.run(() => {
            toast.phase = 'expanded';
            this._cd.markForCheck();
            // Restart auto-close from scratch
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

  private _initMorph(toast: GooeyToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;

    this._measure(toast);
    ms.t = 0;
    this._flushPath(toast);
    this._runEntryAnimation(toast);
  }

  private _measure(toast: GooeyToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;

    const wrapperEl = this._getWrapperEl(toast.id);
    if (!wrapperEl) return;

    const contentEl = wrapperEl.querySelector(`[data-content-id="${toast.id}"]`) as HTMLElement;
    const headerEl = wrapperEl.querySelector(`[data-header-id="${toast.id}"]`) as HTMLElement;
    if (!contentEl || !headerEl) return;

    // Temporarily remove constraints to get natural sizes
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
    const pw = headerEl.offsetWidth + paddingX;
    const bw = contentEl.offsetWidth;
    const th = contentEl.offsetHeight;

    // Restore
    wrapperEl.style.width = savedWrW;
    contentEl.style.overflow = savedOv;
    contentEl.style.maxHeight = savedMH;
    contentEl.style.width = savedCW;

    ms.pw = pw;
    ms.bw = bw;
    ms.th = th;
  }

  /** Flush the SVG path + content clipping for current morph state */
  private _flushPath(toast: GooeyToastState): void {
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
    const rightSide = this.isRight; // original: d === "rtl" ? !posRight : posRight

    // Compute and set path
    if (centerPos) {
      const centerBw = Math.max(bw, ms.expandedDims.bw, pw);
      const d = morphPathCenterRaw(pw, centerBw, th, t);
      pathEl.setAttribute('d', d);

      // SVG viewport for center: full expanded width
      const viewW = Math.ceil(centerBw);
      const viewH = Math.ceil(t >= 1 ? th : (PH + (th - PH) * t));
      svgEl.setAttribute('width', String(viewW));
      svgEl.setAttribute('height', String(viewH));
    } else {
      const d = morphPathRaw(pw, bw, th, t);
      pathEl.setAttribute('d', d);

      const currentW = t >= 1 ? bw : (pillW + (bw - pillW) * t);
      const currentH = t >= 1 ? th : (PH + (th - PH) * t);
      svgEl.setAttribute('width', String(Math.ceil(currentW)));
      svgEl.setAttribute('height', String(Math.ceil(currentH)));
    }    // Apply content clipping during morph — EXACT logic from original flush()
    if (t >= 1) {
      wrapperEl.style.width = '';
      contentEl.style.width = '';
      contentEl.style.overflow = '';
      contentEl.style.maxHeight = '';
      contentEl.style.clipPath = '';
      // Reset close button to default position
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
        // Position close button at the visible right edge
        this._positionCloseBtn(wrapperEl, true, clip);
      } else {
        wrapperEl.style.width = currentW + 'px';
        contentEl.style.width = bw + 'px';
        contentEl.style.overflow = 'hidden';
        contentEl.style.maxHeight = currentH + 'px';
        const clip = bw - currentW;
        // Original: rightSide ? inset(0 0 0 clip) : inset(0 clip 0 0)
        // But we already mirror with scaleX(-1) so the clip direction stays consistent
        contentEl.style.clipPath = rightSide
          ? `inset(0 0 0 ${clip}px)`
          : `inset(0 ${clip}px 0 0)`;
        this._positionCloseBtn(wrapperEl, false, 0);
      }
    } else {
      // Pill state (t=0)
      if (centerPos) {
        const centerBw = Math.max(bw, ms.expandedDims.bw, pw);
        wrapperEl.style.width = centerBw + 'px';
        contentEl.style.width = centerBw + 'px';
        const clip = (centerBw - pillW) / 2;
        contentEl.style.clipPath = `inset(0 ${clip}px 0 ${clip}px)`;
        // Position close button at the visible pill right edge
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

  /** Animate morph t from current → target over duration ms */
  private _animateMorph(toast: GooeyToastState, from: number, to: number, durationMs: number, onComplete?: () => void): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;

    if (ms.animId) cancelAnimationFrame(ms.animId);

    const startTime = performance.now();

    const tick = (now: number) => {
      const elapsed = now - startTime;
      const raw = Math.min(1, elapsed / durationMs);
      const eased = smoothEase(raw);
      ms.t = from + (to - from) * eased;

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

  // ── Internal: Entry / Exit animations ───────────────────────────────

  private _runEntryAnimation(toast: GooeyToastState): void {
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

      const scale = 0.6 + 0.4 * eased;
      const opacity = Math.min(1, raw * 2.5);
      wrapperEl.style.opacity = String(opacity);
      wrapperEl.style.transform = baseTransform + `scale(${scale})`;

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
                  this._animateMorph(toast, 0, 1, DEFAULT_EXPAND_DUR, () => {
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
            // Pill-only toast — start auto-close immediately
            this._startAutoClose(toast);
          }
        });
      }
    };

    ms.entryAnimId = requestAnimationFrame(tick);
  }

  /** Collapse morph then run exit animation */
  private _collapseAndExit(toast: GooeyToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;

    // Save expanded dims before collapse (for potential re-expand)
    ms.expandedDims = { pw: ms.pw, bw: ms.bw, th: ms.th };
    ms.collapsing = true;

    // Hide body content
    ms.showBody = false;
    this._cd.markForCheck();

    // Compute target pill dims
    this._zone.runOutsideAngular(() => {
      requestAnimationFrame(() => {
        const wrapperEl = this._getWrapperEl(toast.id);
        const contentEl = wrapperEl?.querySelector(`[data-content-id="${toast.id}"]`) as HTMLElement;
        const headerEl = wrapperEl?.querySelector(`[data-header-id="${toast.id}"]`) as HTMLElement;

        let targetPw = ms.pw;
        if (contentEl && headerEl) {
          const cs = getComputedStyle(contentEl);
          const padX = parseFloat(cs.paddingLeft) + parseFloat(cs.paddingRight);
          targetPw = headerEl.offsetWidth + padX;
        }

        const savedDims = ms.expandedDims.bw > 0 ? { ...ms.expandedDims } : { pw: ms.pw, bw: ms.bw, th: ms.th };
        const targetDims = { pw: targetPw, bw: targetPw, th: PH };

        // Animate morph from current t → 0, interpolating dims too
        const startTime = performance.now();
        const startT = ms.t;

        if (ms.animId) cancelAnimationFrame(ms.animId);

        const tick = (now: number) => {
          const elapsed = now - startTime;
          const raw = Math.min(1, elapsed / DEFAULT_COLLAPSE_DUR);
          const eased = smoothEase(raw);

          ms.t = startT * (1 - eased);

          // Interpolate dims during collapse (like original)
          ms.pw = targetDims.pw + (savedDims.pw - targetDims.pw) * ms.t;
          ms.bw = targetDims.bw + (savedDims.bw - targetDims.bw) * ms.t;
          ms.th = targetDims.th + (savedDims.th - targetDims.th) * ms.t;

          this._flushPath(toast);

          if (raw < 1) {
            ms.animId = requestAnimationFrame(tick);
          } else {
            ms.t = 0;
            ms.animId = null;
            ms.collapsing = false;
            ms.pw = targetDims.pw;
            ms.bw = targetDims.bw;
            ms.th = targetDims.th;
            this._flushPath(toast);            // After collapse completes, wait then exit (like original's 800ms delay)
            setTimeout(() => {
              if (!ms.userDismissed && (ms.hovered || this._containerHovered)) {
                // Don't exit if hovered — re-expand will handle it
                // But if user explicitly clicked close, always exit
                return;
              }
              this._runExitAnimation(toast);
            }, DISMISS_AFTER_COLLAPSE);
          }
        };        ms.animId = requestAnimationFrame(tick);
      });
    });
  }

  /** Collapse after action success → wait 1200ms → dismiss (original useEffect at line 964) */
  private _collapseAfterAction(toast: GooeyToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;

    const wrapperEl = this._getWrapperEl(toast.id);
    const contentEl = wrapperEl?.querySelector(`[data-content-id="${toast.id}"]`) as HTMLElement;
    const headerEl = wrapperEl?.querySelector(`[data-header-id="${toast.id}"]`) as HTMLElement;

    let targetPw = ms.pw;
    if (contentEl && headerEl) {
      const cs = getComputedStyle(contentEl);
      const padX = parseFloat(cs.paddingLeft) + parseFloat(cs.paddingRight);
      targetPw = headerEl.offsetWidth + padX;
    }

    const savedDims = ms.expandedDims.bw > 0 ? { ...ms.expandedDims } : { pw: ms.pw, bw: ms.bw, th: ms.th };
    const targetDims = { pw: targetPw, bw: targetPw, th: PH };

    const startTime = performance.now();
    const startT = ms.t;

    if (ms.animId) cancelAnimationFrame(ms.animId);

    const tick = (now: number) => {
      const elapsed = now - startTime;
      const raw = Math.min(1, elapsed / DEFAULT_COLLAPSE_DUR);
      const eased = smoothEase(raw);

      ms.t = startT * (1 - eased);
      ms.pw = targetDims.pw + (savedDims.pw - targetDims.pw) * ms.t;
      ms.bw = targetDims.bw + (savedDims.bw - targetDims.bw) * ms.t;
      ms.th = targetDims.th + (savedDims.th - targetDims.th) * ms.t;

      this._flushPath(toast);

      if (raw < 1) {
        ms.animId = requestAnimationFrame(tick);
      } else {
        ms.t = 0;
        ms.animId = null;
        ms.collapsing = false;
        ms.pw = targetDims.pw;
        ms.bw = targetDims.bw;
        ms.th = targetDims.th;
        this._flushPath(toast);

        // Original: useEffect fires dismiss 1200ms after actionSuccess && !showBody
        this._zone.run(() => {
          setTimeout(() => {
            this._startDismiss(toast.id);
          }, 1200);
        });
      }
    };

    ms.animId = requestAnimationFrame(tick);
  }

  private _runExitAnimation(toast: GooeyToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;

    const wrapperEl = this._getWrapperEl(toast.id);
    if (!wrapperEl) return;    if (ms.exitAnimId) cancelAnimationFrame(ms.exitAnimId);

    // Clear wrapper clip-path so exit scale/fade isn't clipped (center positions)
    wrapperEl.style.clipPath = '';

    const startTime = performance.now();
    const startOpacity = parseFloat(wrapperEl.style.opacity || '1');
    const baseTransform = this.isRight ? 'scaleX(-1) ' : '';

    const tick = (now: number) => {
      const elapsed = now - startTime;
      const raw = Math.min(1, elapsed / EXIT_DUR);
      const eased = raw * raw; // ease-in quad

      const opacity = startOpacity * (1 - eased);
      const scale = 1 - 0.15 * eased;
      const translateY = 8 * eased;
      wrapperEl.style.opacity = String(opacity);
      wrapperEl.style.transform = baseTransform + `scale(${scale}) translateY(${translateY}px)`;

      if (raw < 1) {
        ms.exitAnimId = requestAnimationFrame(tick);
      } else {
        ms.exitAnimId = null;
        this._removeToast(toast.id);
      }
    };

    ms.exitAnimId = requestAnimationFrame(tick);
  }

  // ── Internal: Error shake animation ─────────────────────────────────

  private _runShakeAnimation(toast: GooeyToastState): void {
    const ms = this._morphStates.get(toast.id);
    if (!ms) return;

    const wrapperEl = this._getWrapperEl(toast.id);
    if (!wrapperEl) return;

    if (ms.shakeAnimId) cancelAnimationFrame(ms.shakeAnimId);

    const startTime = performance.now();
    const duration = 400; // 0.4s from original
    const baseTransform = this.isRight ? 'scaleX(-1) ' : '';

    const tick = (now: number) => {
      const elapsed = now - startTime;
      const raw = Math.min(1, elapsed / duration);

      const decay = 1 - raw; // easeOut
      const shake = Math.sin(raw * Math.PI * 6) * decay * 3;
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

  // ── Internal: Auto-close & timers ───────────────────────────────────

  private _startAutoClose(toast: GooeyToastState): void {
    if (toast.duration <= 0 || toast.loading) return;
    this._clearTimer(toast.id);
    this._clearProgressTimer(toast.id);

    const ms = this._morphStates.get(toast.id);

    // If hovered, don't start timer — will be started on leave
    if (ms && (ms.hovered || this._containerHovered)) return;

    const delay = ms?.remainingMs ?? toast.duration;
    if (ms) {
      ms.timerStart = Date.now();
      ms.remainingMs = null;
    }

    const timer = setTimeout(() => {
      // Double-check not hovered at fire time
      if (ms && (ms.hovered || this._containerHovered)) {
        // Save remaining (which is 0 since timer fired)
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

  /** Called when hover state changes — pause or resume timers */
  private _onHoverChange(): void {
    const anyHovered = this.isAnyHovered();

    this.toasts.forEach(toast => {
      const ms = this._morphStates.get(toast.id);
      if (!ms || ms.dismissed) return;

      if (anyHovered) {
        // Pause: save remaining time
        const timer = this._timers.get(toast.id);
        if (timer) {
          clearTimeout(timer);
          this._timers.delete(toast.id);
          if (ms.timerStart > 0) {
            const elapsed = Date.now() - ms.timerStart;
            const remaining = Math.max(0, (ms.remainingMs ?? toast.duration) - elapsed);
            ms.remainingMs = remaining;
          }
        }
      } else {
        // Resume: restart with remaining time
        if (ms.remainingMs != null && ms.remainingMs >= 0 && !ms.dismissed && !toast.loading) {
          this._startAutoClose(toast);
        }
      }
    });

    this._cd.markForCheck();
  }

  private _clearTimer(id: string | number): void {
    const timer = this._timers.get(id);
    if (timer) {
      clearTimeout(timer);
      this._timers.delete(id);
    }
  }

  private _clearProgressTimer(id: string | number): void {
    const timer = this._progressTimers.get(id);
    if (timer) {
      clearInterval(timer);
      this._progressTimers.delete(id);
    }
  }
  // ── Internal: DOM helpers ───────────────────────────────────────────

  /**
   * Position the close button at the visible edge of the toast.
   * For center positions, the wrapper is wider than the visible pill/body,
   * so the close button must be offset inward by the clip amount.
   * For right positions (scaleX -1 mirrored), CSS already handles via left: -6px rule.
   */  /**
   * For center positions, the wrapper is wider than the visible pill/body.
   * This method:
   * 1. Repositions the close button to the visible right edge
   * 2. Clips the wrapper so hover/click hit-testing only covers the visible area
   *    (with generous overflow for close button + drop-shadow)
   */
  private _positionCloseBtn(wrapperEl: HTMLElement, isCenter: boolean, clipInset: number): void {
    const closeBtn = wrapperEl.querySelector('.gooey-close-btn') as HTMLElement;
    if (!closeBtn) return;

    if (isCenter && clipInset > 0) {
      // Reposition close button at the visible right edge
      closeBtn.style.right = (clipInset - 6) + 'px';
      // Clip wrapper to visible area + overflow for close btn (28px above/sides) + shadow (16px below)
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
