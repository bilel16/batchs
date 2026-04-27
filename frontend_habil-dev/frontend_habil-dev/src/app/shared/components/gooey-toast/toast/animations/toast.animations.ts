/**
 * @fileoverview Gooey Toast — Animation Constants & Easing
 *
 * All timing constants and easing functions extracted from the original
 * goey-toast React implementation. Self-contained — no external deps.
 *
 * Architecture:
 * - morphPathRaw()       → left/right aligned blob path
 * - morphPathCenterRaw() → center aligned blob path
 * - cubicBezier()        → CSS cubic-bezier sampler for rAF animations
 * - smoothEase           → expand/collapse easing (0.4, 0, 0.2, 1)
 * - entryEase            → entry spring overshoot (0.34, 1.56, 0.64, 1)
 */

// ── Timing constants (exact from original) ────────────────────────────────

/** Pill height in px */
export const PH = 34;
/** Pill border-radius = PH / 2 */
export const PR = PH / 2;
/** Expand animation duration (ms) — 0.6s in original */
export const EXPAND_DUR = 600;
/** Collapse animation duration (ms) — 0.9s in original */
export const COLLAPSE_DUR = 900;
/** Toast entry animation duration (ms) */
export const ENTRY_DUR = 330;
/** Toast exit animation duration (ms) */
export const EXIT_DUR = 240;
/** Delay before showing body text after entry (ms) */
export const EXPAND_BODY_DELAY = 330;
/** Delay after collapse before running exit animation (ms) */
export const DISMISS_AFTER_COLLAPSE = 800;
/** CSS cubic-bezier values used by original */
export const SMOOTH_EASE_VALUES: [number, number, number, number] = [0.4, 0, 0.2, 1];

// ── Cubic-bezier sampler ──────────────────────────────────────────────────

/**
 * Creates a cubic-bezier easing function that can be evaluated at any t ∈ [0,1].
 * Uses binary search with 20 iterations for high precision.
 */
export function cubicBezier(p1x: number, p1y: number, p2x: number, p2y: number): (t: number) => number {
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

/** Standard smooth easing — cubic-bezier(0.4, 0, 0.2, 1) */
export const smoothEase = cubicBezier(0.4, 0, 0.2, 1);
/** Entry spring overshoot — cubic-bezier(0.34, 1.56, 0.64, 1) */
export const entryEase = cubicBezier(0.34, 1.56, 0.64, 1);

// ── SVG Morph Paths — ported verbatim from goey-toast/dist/index.js ──────

/**
 * Generate the blob SVG path for left/right-aligned toasts.
 * @param pw - pill width
 * @param bw - body width (expanded)
 * @param th - total height (expanded)
 * @param t  - morph parameter [0,1] (0=pill, 1=expanded)
 */
export function morphPathRaw(pw: number, bw: number, th: number, t: number): string {
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

/**
 * Generate the blob SVG path for center-aligned toasts.
 * The pill is centered within the body width.
 */
export function morphPathCenterRaw(pw: number, bw: number, th: number, t: number): string {
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
