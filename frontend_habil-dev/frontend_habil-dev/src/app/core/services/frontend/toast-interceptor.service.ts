/**
 * @fileoverview Toast Interceptor Service
 * 
 * Automatically intercepts all toast messages (MessageService.add() calls) and
 * sends them to the appropriate notification panel:
 * - Success/Info toasts → Bell icon (notifications panel)
 * - Error/Warn toasts → Megaphone icon (alerts panel)
 * 
 * This ensures all user feedback is preserved in the persistent panels,
 * not just the transient toasts that disappear after a few seconds.
 * 
 * @author BNA HABIL Development Team
 * @version 1.0.0
 * @since 2026-01-12
 */

import { Injectable, Injector } from '@angular/core';
import { MessageService, ToastMessageOptions } from 'primeng/api';
import { NotificationService, NotificationSeverity, NotificationCategory } from './notification.service';
import { ToastService } from '../../../shared/components/gooey-toast/toast/services/toast.service';
import { ToastType } from '../../../shared/components/gooey-toast/toast/models/toast.model';

/**
 * Toast Interceptor Service
 * 
 * Wraps PrimeNG MessageService to intercept toast messages and
 * automatically send them to notification/alert panels.
 * 
 * Usage:
 * 1. Inject this service instead of MessageService directly
 * 2. Use standard MessageService API (add, addAll, clear)
 * 3. Toasts are automatically sent to appropriate panels
 */
@Injectable({
  providedIn: 'root'
})
export class ToastInterceptorService extends MessageService {
  /** Reference to the notification service for panel updates */
  private notificationService?: NotificationService;
    /** Reference to the gooey toast service for visual toasts */
  private _gooeyToast?: ToastService;

  /** Flag to prevent infinite loops when notification service shows toasts */
  private isInternalCall = false;
  
  constructor(private injector: Injector) {
    super();
  }
  
  /**
   * Initialize the interceptor with notification service
   * Called by NotificationService constructor to avoid circular dependency
   */
  public initialize(notificationService: NotificationService): void {
    this.notificationService = notificationService;
  }

  /**
   * Lazy-resolve GooeyToastService to avoid DI ordering issues.
   */  private get gooeyToast(): ToastService {
    if (!this._gooeyToast) {
      this._gooeyToast = this.injector.get(ToastService);
    }
    return this._gooeyToast;
  }
    /**
   * Override add() to intercept toast messages
   */  override add(message: ToastMessageOptions): void {
    // Call parent to show the PrimeNG toast (kept as fallback / for template bindings)
    super.add(message);
    
    // ── Bridge to GooeyToastService ──
    if (!this.isInternalCall) {
      this.showGooeyToast(message);
    }

    // Send to appropriate panel (unless this is an internal call from NotificationService)
    if (!this.isInternalCall && this.notificationService) {
      this.sendToPanel(message);
    }
  }
  
  /**
   * Override addAll() to intercept multiple toast messages
   */  override addAll(messages: ToastMessageOptions[]): void {
    // Call parent to show the PrimeNG toasts
    super.addAll(messages);
    
    // ── Bridge each message to GooeyToastService ──
    if (!this.isInternalCall) {
      messages.forEach(msg => this.showGooeyToast(msg));
    }

    // Send each to appropriate panel
    if (!this.isInternalCall && this.notificationService) {
      messages.forEach(msg => this.sendToPanel(msg));
    }
  }
  
  /**
   * Allow NotificationService to show toasts without triggering panel updates
   */
  public addWithoutInterception(message: ToastMessageOptions): void {
    this.isInternalCall = true;
    super.add(message);
    this.isInternalCall = false;
  }
    /**
   * Bridge a PrimeNG toast message to the GooeyToastService
   */
  private showGooeyToast(message: ToastMessageOptions): void {
    if (!message.summary) return;    const typeMap: Record<string, ToastType> = {
      success: 'success',
      info:    'info',
      warn:    'warning',
      error:   'error',
    };
    const type: ToastType = typeMap[message.severity || 'info'] || 'default';
    const title = message.summary;
    const description = message.detail || undefined;
    const duration = message.life || 4000;

    // Call the appropriate method on GooeyToastService
    switch (type) {
      case 'success': this.gooeyToast.success(title, { description, duration }); break;
      case 'error':   this.gooeyToast.error(title, { description, duration }); break;
      case 'warning': this.gooeyToast.warning(title, { description, duration }); break;
      case 'info':    this.gooeyToast.info(title, { description, duration }); break;
      default:        this.gooeyToast.show(title, { description, duration }); break;
    }
  }

  /**
   * Send a toast message to the appropriate notification panel
   */
  private sendToPanel(message: ToastMessageOptions): void {
    if (!this.notificationService || !message.summary) {
      return;
    }
    
    const severity = (message.severity || 'info') as NotificationSeverity;
    const title = message.summary;
    const detail = message.detail || '';
    const category = this.extractCategory(message);
    
    // Determine destination based on severity
    if (severity === 'success' || severity === 'info') {
      // Send to notifications panel (bell icon)
      this.notificationService.addNotificationOnly(
        title,
        detail,
        severity,
        category
      );
    } else if (severity === 'warn' || severity === 'error') {
      // Send to alerts panel (megaphone icon)
      this.notificationService.addAlertOnly(
        title,
        detail,
        severity,
        category
      );
    }
  }
    /**
   * Extract category from message data if available
   */
  private extractCategory(message: ToastMessageOptions): NotificationCategory {
    if (message.data?.category) {
      return message.data.category;
    }
    
    // Try to infer from title/detail
    const text = `${message.summary} ${message.detail}`.toLowerCase();
    
    if (text.includes('profil')) {
      return 'profile-assignment';
    } else if (text.includes('pack')) {
      return 'pack-assignment';
    } else if (text.includes('utilisateur') || text.includes('user')) {
      return 'user-management';
    } else if (text.includes('système') || text.includes('system')) {
      return 'system';
    }
    
    return 'general';
  }
}
