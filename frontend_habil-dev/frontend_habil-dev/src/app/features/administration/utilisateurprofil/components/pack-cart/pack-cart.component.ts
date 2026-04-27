/**
 * @fileoverview Pack Cart Component - Premium Morph Animation between Empty/Filled States
 * 
 * Animation Architecture:
 * - Single morphing container that transforms between states
 * - Shared icon element that moves from center to header
 * - Staggered content reveals with spring physics
 * - Material-like layout morph transitions
 * 
 * Timing:
 * - Container morph: 400ms (primary movement)
 * - Icon transform: 300ms (50ms delay)
 * - Empty content fade: 150ms (immediate)
 * - Filled content reveal: 200ms (150ms delay)
 * - Cards stagger: 50ms interval, 300ms each
 * - Footer slide: 200ms (after cards)
 * 
 * @author BNA HABIL Development Team
 * @version 2.0.0 - Premium animation update
 * @since 2025-01-07
 */

import { Component, Input, Output, EventEmitter } from '@angular/core';
import { ProfilePack } from '../../../../../core/models/profile-pack';
import { 
  trigger, 
  state, 
  style, 
  animate, 
  transition,
  query,
  stagger,
  group,
  animateChild
} from '@angular/animations';

// Premium easing curves
const SPRING_EASE = 'cubic-bezier(0.34, 1.56, 0.64, 1)';      // Spring overshoot
const SMOOTH_EASE = 'cubic-bezier(0.4, 0, 0.2, 1)';           // Material standard
const DECEL_EASE = 'cubic-bezier(0, 0, 0.2, 1)';              // Deceleration
const ACCEL_EASE = 'cubic-bezier(0.4, 0, 1, 1)';              // Acceleration

@Component({
  selector: 'app-pack-cart',
  templateUrl: './pack-cart.component.html',
  styleUrls: ['./pack-cart.component.scss'],
  standalone: false,
  animations: [
    // ═══════════════════════════════════════════════════════════════════
    // CONTAINER MORPH - The main layout transformation
    // ═══════════════════════════════════════════════════════════════════
    trigger('containerMorph', [
      state('empty', style({
        width: '565.92px',
        minHeight: '180px',
        background: 'linear-gradient(135deg, #f8fafc 0%, #e2e8f0 100%)',
        borderRadius: '16px',
        boxShadow: '0 4px 16px rgba(0, 0, 0, 0.06)',
        border: '2px dashed #cbd5e1',
        padding: '0'
      })),
      state('filled', style({
        width: '100%',
        minHeight: 'auto',
        background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
        borderRadius: '20px',
        boxShadow: '0 8px 32px rgba(102, 126, 234, 0.3)',
        border: '2px solid transparent',
        padding: '0'
      })),
      // Empty → Filled: Expansion animation
      transition('empty => filled', [
        group([
          animate(`350ms ${SPRING_EASE}`),
          query('@*', animateChild(), { optional: true })
        ])
      ]),
      // Filled → Empty: Contraction animation
      transition('filled => empty', [
        group([
          animate(`350ms ${SMOOTH_EASE}`),
          query('@*', animateChild(), { optional: true })
        ])
      ])
    ]),    // ═══════════════════════════════════════════════════════════════════
    // ICON MORPH - Shared element that moves from center to header
    // ═══════════════════════════════════════════════════════════════════
    trigger('iconMorph', [
      state('empty', style({
        position: 'absolute',
        top: '40px',
        left: '50%',
        transform: 'translateX(-50%) scale(1)',
        fontSize: '3.5rem',
        color: '#cbd5e1',
        opacity: 0.6
      })),
      state('filled', style({
        position: 'absolute',
        top: '45px',
        left: '25px',
        transform: 'translateX(0) scale(1.85)',
        fontSize: '3rem',
        color: '#ffffff',
        opacity: 1
      })),
      transition('empty => filled', [
        animate(`350ms 50ms ${SPRING_EASE}`)
      ]),
      transition('filled => empty', [
        animate(`350ms ${SMOOTH_EASE}`)
      ])
    ]),

    // ═══════════════════════════════════════════════════════════════════
    // EMPTY CONTENT - Fades out when transitioning to filled
    // ═══════════════════════════════════════════════════════════════════
    trigger('emptyContent', [
      state('visible', style({
        opacity: 1,
        transform: 'translateY(0)',
        pointerEvents: 'auto'
      })),
      state('hidden', style({
        opacity: 0,
        transform: 'translateY(-10px)',
        pointerEvents: 'none',
        position: 'absolute'
      })),
      transition('visible => hidden', [
        animate(`150ms ${ACCEL_EASE}`)
      ]),
      transition('hidden => visible', [
        animate(`150ms 100ms ${DECEL_EASE}`)
      ])
    ]),

    // ═══════════════════════════════════════════════════════════════════
    // FILLED CONTENT - Reveals with delay after container starts morphing
    // ═══════════════════════════════════════════════════════════════════
    trigger('filledContent', [
      state('visible', style({
        opacity: 1,
        transform: 'translateY(0)',
        pointerEvents: 'auto'
      })),
      state('hidden', style({
        opacity: 0,
        transform: 'translateY(20px)',
        pointerEvents: 'none',
        position: 'absolute',
        width: '100%'
      })),
      transition('hidden => visible', [
        animate(`250ms 150ms ${DECEL_EASE}`)
      ]),
      transition('visible => hidden', [
        animate(`150ms ${ACCEL_EASE}`)
      ])
    ]),

    // ═══════════════════════════════════════════════════════════════════
    // HEADER TEXT - Slides in with spring feel
    // ═══════════════════════════════════════════════════════════════════
    trigger('headerText', [
      state('visible', style({
        opacity: 1,
        transform: 'translateX(0)'
      })),
      state('hidden', style({
        opacity: 0,
        transform: 'translateX(-20px)'
      })),
      transition('hidden => visible', [
        animate(`300ms 200ms ${SPRING_EASE}`)
      ]),
      transition('visible => hidden', [
        animate(`150ms ${ACCEL_EASE}`)
      ])
    ]),

    // ═══════════════════════════════════════════════════════════════════
    // EXPAND CONTENT - For the expandable section
    // ═══════════════════════════════════════════════════════════════════
    trigger('expandContent', [
      transition(':enter', [
        style({ 
          opacity: 0, 
          height: 0,
          transform: 'translateY(-10px)'
        }),
        animate(`300ms ${SMOOTH_EASE}`, style({ 
          opacity: 1, 
          height: '*',
          transform: 'translateY(0)'
        }))
      ]),
      transition(':leave', [
        animate(`200ms ${ACCEL_EASE}`, style({ 
          opacity: 0, 
          height: 0,
          transform: 'translateY(-10px)'
        }))
      ])
    ]),

    // ═══════════════════════════════════════════════════════════════════
    // CARD STAGGER - Each card enters with delay based on index
    // ═══════════════════════════════════════════════════════════════════
    trigger('cardStagger', [
      transition(':enter', [
        style({ 
          opacity: 0, 
          transform: 'translateY(20px) scale(0.95)'
        }),
        animate(`300ms {{ delay }}ms ${SPRING_EASE}`, style({ 
          opacity: 1, 
          transform: 'translateY(0) scale(1)'
        }))
      ], { params: { delay: 0 } }),
      transition(':leave', [
        animate(`200ms ${ACCEL_EASE}`, style({ 
          opacity: 0, 
          transform: 'translateX(30px) scale(0.95)'
        }))
      ])
    ]),

    // ═══════════════════════════════════════════════════════════════════
    // FOOTER SLIDE - Slides up from bottom after cards
    // ═══════════════════════════════════════════════════════════════════
    trigger('footerSlide', [
      transition(':enter', [
        style({ 
          opacity: 0, 
          transform: 'translateY(20px)'
        }),
        animate(`250ms 100ms ${DECEL_EASE}`, style({ 
          opacity: 1, 
          transform: 'translateY(0)'
        }))
      ]),
      transition(':leave', [
        animate(`150ms ${ACCEL_EASE}`, style({ 
          opacity: 0, 
          transform: 'translateY(20px)'
        }))
      ])
    ])
  ]
})
export class PackCartComponent {
  /** Packs in cart ready for assignment */
  @Input() cartPacks: ProfilePack[] = [];
  
  /** Selected user to assign to */
  @Input() selectedUser: any = null;
  
  /** Whether assignment is in progress */
  @Input() isAssigning = false;
  
  /** Emits when assign all button clicked */
  @Output() assignAll = new EventEmitter<void>();
  
  /** Emits when remove from cart clicked */
  @Output() removeFromCart = new EventEmitter<ProfilePack>();
  
  /** Emits when clear cart clicked */
  @Output() clearCart = new EventEmitter<void>();
  
  /** Emits when pack in cart clicked */
  @Output() cartPackClick = new EventEmitter<ProfilePack>();

  /** Whether cart is expanded (default true) */
  isExpanded = true;

  /**
   * Get total profile count across all cart packs
   */
  get totalProfileCount(): number {
    return this.cartPacks.reduce((sum, pack) => 
      sum + (pack.profiles?.length || pack.profileCodes?.length || 0), 0);
  }

  /**
   * Get count of valid packs (with profiles) in cart
   */
  get validPacksCount(): number {
    return this.cartPacks.filter(pack => 
      (pack.profiles?.length || 0) > 0 || (pack.profileCodes?.length || 0) > 0
    ).length;
  }

  /**
   * Check if cart has any valid packs that can be assigned
   */
  get hasValidPacks(): boolean {
    return this.validPacksCount > 0;
  }

  /**
   * Check if a pack has profiles
   */
  hasProfiles(pack: ProfilePack): boolean {
    return (pack.profiles?.length || 0) > 0 || (pack.profileCodes?.length || 0) > 0;
  }

  /**
   * Toggle cart expansion
   */
  toggleExpand(): void {
    this.isExpanded = !this.isExpanded;
  }

  /**
   * Handle assign all click
   */
  onAssignAll(): void {
    if (this.hasValidPacks && !this.isAssigning) {
      this.assignAll.emit();
    }
  }

  /**
   * Handle remove from cart
   */
  onRemoveFromCart(pack: ProfilePack, event: Event): void {
    event.stopPropagation();
    this.removeFromCart.emit(pack);
  }

  /**
   * Handle clear cart
   */
  onClearCart(): void {
    this.clearCart.emit();
  }

  /**
   * Handle cart pack click
   */
  onCartPackClick(pack: ProfilePack): void {
    this.cartPackClick.emit(pack);
  }

  /**
   * Get category color
   */
  getCategoryColor(category: string): string {
    const colors: Record<string, string> = {
      'ADMINISTRATION': '#3b82f6',
      'COMMERCIAL': '#10b981',
      'FINANCE': '#f59e0b',
      'OPERATIONS': '#8b5cf6',
      'MANAGEMENT': '#ef4444',
      'CUSTOM': '#6366f1'
    };
    return colors[category] || '#6b7280';
  }

  /**
   * TrackBy function for cart items
   */
  trackByPackId(index: number, pack: ProfilePack): string {
    return pack.id;
  }
}
