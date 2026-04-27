import { Component, Input, Output, EventEmitter, OnInit, OnDestroy, OnChanges, SimpleChanges, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';

/**
 * AnimatedDrawerComponent
 * 
 * A reusable drawer component that animates from the top-right corner of the navbar,
 * mimicking the login square morphing animation style. The drawer emerges like the
 * login squares that transform into the navbar and sidebar.
 * 
 * Features:
 * - Morphing animation from small square to full drawer
 * - Backdrop blur effect
 * - Responsive design
 * - BNA Habilitation UI standards compliance
 * - Reusable for CRUD operations (Create/Edit/Details)
 */
@Component({
  selector: 'app-animated-drawer',
  templateUrl: './animated-drawer.component.html',
  styleUrls: ['./animated-drawer.component.scss'],
  standalone: false
})
export class AnimatedDrawerComponent implements OnInit, OnDestroy, OnChanges {
  /**
   * Controls the visibility of the drawer
   */
  @Input() visible: boolean = false;

  /**
   * The title displayed in the drawer header
   */
  @Input() title: string = 'Drawer';  /**
   * Width of the drawer (default: 400px, flexible for different use cases)
   */
  @Input() width: string = '400px';

  /**
   * Whether to show the backdrop blur effect
   */
  @Input() showBackdrop: boolean = true;

  /**
   * Custom CSS class for the drawer
   */
  @Input() customClass: string = '';

  /**
   * Emitted when the drawer is closed
   */
  @Output() closed = new EventEmitter<void>();

  /**
   * Emitted when the drawer animation starts opening
   */
  @Output() opening = new EventEmitter<void>();

  /**
   * Emitted when the drawer animation completes opening
   */
  @Output() opened = new EventEmitter<void>();  /**
   * Internal state to track animation phases
   */
  isAnimating: boolean = false;
  animationPhase: 'idle' | 'growing' | 'open' | 'shrinking' = 'idle';
  showShape: boolean = false;

  constructor(private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    // Listen for escape key to close drawer
    if (this.visible) {
      this.openDrawer();
    }
  }

  ngOnDestroy(): void {
    // Clean up any pending animations
    this.animationPhase = 'idle';
  }
  /**
   * Handles changes to the visible input
   */
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['visible'] && !changes['visible'].firstChange) {
      if (this.visible && this.animationPhase === 'idle') {
        this.openDrawer();
      } else if (!this.visible && this.animationPhase === 'open') {
        this.closeDrawer();
      }
    }
  }  /**
   * Opens the drawer with 3-phase morphing animation
   */
  public openDrawer(): void {
    this.isAnimating = true;
    this.opening.emit();

    // Add body class to prevent scrolling
    document.body.classList.add('drawer-open');

    // PHASE 1: Show the small square (0ms)
    this.showShape = true;
    this.cdr.detectChanges();

    // PHASE 2: Start morphing animation (300ms delay)
    setTimeout(() => {
      this.animationPhase = 'growing';
      this.cdr.detectChanges();
    }, 300);

    // PHASE 3: Show content after morphing completes (1800ms total)
    setTimeout(() => {
      this.animationPhase = 'open';
      this.isAnimating = false;
      this.opened.emit();
      this.cdr.detectChanges();
    }, 350);
  }
  /**
   * Closes the drawer with reverse 3-phase morphing animation
   */
  closeDrawer(): void {
    if (this.animationPhase !== 'open') return;

    this.isAnimating = true;
    this.animationPhase = 'shrinking';
    this.cdr.detectChanges();

    // Remove body class to restore scrolling
    document.body.classList.remove('drawer-open');

    // Complete the closing animation and hide shape
    setTimeout(() => {
      this.animationPhase = 'idle';
      this.showShape = false;
      this.isAnimating = false;
      this.closed.emit();
      this.cdr.detectChanges();
    }, 250); // Match new animation duration (1.5s + buffer)
  }

  /**
   * Handles backdrop click to close drawer
   */
  onBackdropClick(event: Event): void {
    event.stopPropagation();
    this.closeDrawer();
  }

  /**
   * Prevents drawer content clicks from closing the drawer
   */
  onDrawerClick(event: Event): void {
    event.stopPropagation();
  }

  /**
   * Handles escape key press
   */
  onKeyDown(event: KeyboardEvent): void {
    if (event.key === 'Escape') {
      this.closeDrawer();
    }
  }

  /**
   * Gets the current drawer state classes
   */
  getDrawerClasses(): string {
    return [
      'animated-drawer',
      this.customClass,
      this.animationPhase,
      this.isAnimating ? 'animating' : ''
    ].filter(Boolean).join(' ');
  }

  /**
   * Animation event handlers
   */
  onAnimationStart(): void {
    // Animation started
  }

  onAnimationEnd(): void {
    // Animation completed
    if (this.animationPhase === 'shrinking') {
      this.animationPhase = 'idle';
      this.isAnimating = false;
    } else if (this.animationPhase === 'growing') {
      this.animationPhase = 'open';
      this.isAnimating = false;
    }
  }
}
