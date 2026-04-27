/**
 * @fileoverview Notification Service
 * 
 * Enterprise-grade notification system for BNA HABIL application.
 * Manages both transient toast notifications and persistent panel notifications/alerts.
 * 
 * Architecture:
 * - Toasts: Immediate, transient notifications (PrimeNG MessageService)
 * - Notifications: Informational items shown in the bell icon panel (success, info)
 * - Alerts: Critical items shown in the megaphone icon panel (errors, warnings)
 * 
 * @author BNA HABIL Development Team
 * @version 1.0.0
 * @since 2025-12-25
 */

import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { MessageService } from 'primeng/api';
import { ToastInterceptorService } from './toast-interceptor.service';

/**
 * Notification severity levels
 */
export type NotificationSeverity = 'success' | 'info' | 'warn' | 'error';

/**
 * Notification/Alert categories for better organization
 */
export type NotificationCategory = 
  | 'profile-assignment' 
  | 'profile-clone' 
  | 'pack-assignment' 
  | 'user-management'
  | 'system'
  | 'general';

/**
 * Structure for a single notification item
 */
export interface NotificationItem {
  id: string;
  title: string;
  message: string;
  severity: NotificationSeverity;
  category: NotificationCategory;
  timestamp: Date;
  read: boolean;
  /** Expandable details (e.g., list of failed profiles) */
  details?: NotificationDetail[];
  /** Related action data */
  actionData?: any;
  /** Auto-dismiss timeout in ms (0 = never) */
  autoHideMs?: number;
}

/**
 * Detail item for expandable notifications (e.g., individual profile errors)
 */
export interface NotificationDetail {
  identifier: string;
  message: string;
  icon?: string;
}

/**
 * Structure for batch operation results
 */
export interface BatchOperationResult {
  successful: string[];
  failed: { [key: string]: string } | Array<{ identifier: string; error: string }>;
  totalProcessed: number;
  successCount: number;
  failureCount: number;
  summary?: string;
}

/**
 * Notification Service
 * Central hub for all application notifications
 */
@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  
  // ============================================================================
  // STATE MANAGEMENT
  // ============================================================================

  /** Notifications (bell icon panel) - informational items */
  private notificationsSubject = new BehaviorSubject<NotificationItem[]>([]);
  public notifications$: Observable<NotificationItem[]> = this.notificationsSubject.asObservable();
  
  /** Alerts (megaphone icon panel) - critical items requiring attention */
  private alertsSubject = new BehaviorSubject<NotificationItem[]>([]);
  public alerts$: Observable<NotificationItem[]> = this.alertsSubject.asObservable();
  
  /** Unread counts for badge display */
  private unreadNotificationsCountSubject = new BehaviorSubject<number>(0);
  public unreadNotificationsCount$: Observable<number> = this.unreadNotificationsCountSubject.asObservable();
  
  private unreadAlertsCountSubject = new BehaviorSubject<number>(0);
  public unreadAlertsCount$: Observable<number> = this.unreadAlertsCountSubject.asObservable();
  /** Maximum items to keep in memory */
  private readonly MAX_NOTIFICATIONS = 50;
  private readonly MAX_ALERTS = 30;

  constructor(private messageService: MessageService) {
    // Initialize toast interceptor to enable automatic panel routing
    if (messageService instanceof ToastInterceptorService) {
      messageService.initialize(this);
    }
    
    // Load persisted notifications from session storage
    this.loadPersistedData();
  }

  // ============================================================================
  // PUBLIC API - TOAST MESSAGES (Transient)
  // ============================================================================
  /**
   * Show a simple toast notification
   */
  showToast(severity: NotificationSeverity, summary: string, detail: string, life: number = 5000): void {
    const message = {
      severity,
      summary,
      detail,
      life,
      data: { type: 'simple-toast' }
    };
    
    // Use addWithoutInterception to prevent infinite loops
    if (this.messageService instanceof ToastInterceptorService) {
      this.messageService.addWithoutInterception(message);
    } else {
      this.messageService.add(message);
    }
  }
  /**
   * Show a sticky toast that requires manual dismissal
   */
  showStickyToast(severity: NotificationSeverity, summary: string, detail: string): void {
    const message = {
      severity,
      summary,
      detail,
      sticky: true,
      data: { type: 'sticky-toast' }
    };
    
    // Use addWithoutInterception to prevent infinite loops
    if (this.messageService instanceof ToastInterceptorService) {
      this.messageService.addWithoutInterception(message);
    } else {
      this.messageService.add(message);
    }
  }

  /**
   * Clear all toast messages
   */
  clearToasts(): void {
    this.messageService.clear();
  }

  // ============================================================================
  // PUBLIC API - PANEL NOTIFICATIONS (Persistent)
  // ============================================================================

  /**
   * Add a notification to the notifications panel (bell icon)
   * Use for success and informational messages
   */
  addNotification(
    title: string,
    message: string,
    severity: NotificationSeverity = 'info',
    category: NotificationCategory = 'general',
    details?: NotificationDetail[],
    actionData?: any
  ): NotificationItem {
    const notification: NotificationItem = {
      id: this.generateId(),
      title,
      message,
      severity,
      category,
      timestamp: new Date(),
      read: false,
      details,
      actionData
    };

    const current = this.notificationsSubject.value;
    const updated = [notification, ...current].slice(0, this.MAX_NOTIFICATIONS);
    this.notificationsSubject.next(updated);
    this.updateUnreadCounts();
    this.persistData();

    // Also show a brief toast
    this.showToast(severity, title, message, 4000);

    return notification;
  }

  /**
   * Add a notification WITHOUT showing a toast
   * Used by ToastInterceptorService to prevent infinite loops
   */
  addNotificationOnly(
    title: string,
    message: string,
    severity: NotificationSeverity = 'info',
    category: NotificationCategory = 'general',
    details?: NotificationDetail[],
    actionData?: any
  ): NotificationItem {
    const notification: NotificationItem = {
      id: this.generateId(),
      title,
      message,
      severity,
      category,
      timestamp: new Date(),
      read: false,
      details,
      actionData
    };

    const current = this.notificationsSubject.value;
    const updated = [notification, ...current].slice(0, this.MAX_NOTIFICATIONS);
    this.notificationsSubject.next(updated);
    this.updateUnreadCounts();
    this.persistData();

    return notification;
  }

  /**
   * Add an alert to the alerts panel (megaphone icon)
   * Use for errors and warnings that require attention
   */
  addAlert(
    title: string,
    message: string,
    severity: NotificationSeverity = 'error',
    category: NotificationCategory = 'general',
    details?: NotificationDetail[],
    actionData?: any
  ): NotificationItem {
    const alert: NotificationItem = {
      id: this.generateId(),
      title,
      message,
      severity,
      category,
      timestamp: new Date(),
      read: false,
      details,
      actionData
    };

    const current = this.alertsSubject.value;
    const updated = [alert, ...current].slice(0, this.MAX_ALERTS);
    this.alertsSubject.next(updated);
    this.updateUnreadCounts();
    this.persistData();

    // Also show a sticky toast for critical alerts
    if (severity === 'error') {
      this.showStickyToast(severity, title, message);
    } else {
      this.showToast(severity, title, message, 6000);
    }

    return alert;
  }

  /**
   * Add an alert WITHOUT showing a toast
   * Used by ToastInterceptorService to prevent infinite loops
   */
  addAlertOnly(
    title: string,
    message: string,
    severity: NotificationSeverity = 'error',
    category: NotificationCategory = 'general',
    details?: NotificationDetail[],
    actionData?: any
  ): NotificationItem {
    const alert: NotificationItem = {
      id: this.generateId(),
      title,
      message,
      severity,
      category,
      timestamp: new Date(),
      read: false,
      details,
      actionData
    };

    const current = this.alertsSubject.value;
    const updated = [alert, ...current].slice(0, this.MAX_ALERTS);
    this.alertsSubject.next(updated);
    this.updateUnreadCounts();
    this.persistData();

    return alert;
  }

  // ============================================================================
  // PUBLIC API - BATCH OPERATION HANDLING
  // ============================================================================

  /**
   * Handle batch operation results from profile/pack assignment
   * This is the main method to handle the API response you showed
   * 
   * @param result - The batch operation result from the API
   * @param operationType - Type of operation for categorization
   * @param operationContext - Additional context (e.g., target user name)
   */
  handleBatchOperationResult(
    result: BatchOperationResult,
    operationType: 'profile-assignment' | 'profile-clone' | 'pack-assignment',
    operationContext?: { targetUser?: string; sourceUser?: string }
  ): void {
    const { successful, failed, totalProcessed, successCount, failureCount, summary } = result;

    // Convert failed object to details array
    const failedDetails = this.convertFailedToDetails(failed);
    const successDetails = successful.map(code => ({
      identifier: code,
      message: 'Assigné avec succès',
      icon: 'pi pi-check-circle'
    }));

    // Determine severity and create appropriate notification/alert
    if (failureCount === 0 && successCount > 0) {
      // Complete success
      this.addNotification(
        this.getOperationTitle(operationType, 'success'),
        this.formatSuccessMessage(successCount, operationContext),
        'success',
        operationType,
        successDetails,
        { result, operationType, operationContext }
      );
    } else if (successCount === 0 && failureCount > 0) {
      // Complete failure - ADD TO ALERTS
      this.addAlert(
        this.getOperationTitle(operationType, 'error'),
        this.formatFailureMessage(failureCount, operationContext),
        'error',
        operationType,
        failedDetails,
        { result, operationType, operationContext }
      );
    } else if (successCount > 0 && failureCount > 0) {
      // Partial success - ADD TO BOTH
      this.addNotification(
        this.getOperationTitle(operationType, 'partial'),
        `${successCount} profil(s) assigné(s) avec succès`,
        'success',
        operationType,
        successDetails,
        { result, operationType, operationContext }
      );

      this.addAlert(
        this.getOperationTitle(operationType, 'warning'),
        this.formatPartialFailureMessage(successCount, failureCount, operationContext),
        'warn',
        operationType,
        failedDetails,
        { result, operationType, operationContext }
      );
    }
  }
  /**
   * Handle profile cloning results with detailed per-user breakdown
   */
  handleCloneResults(
    cloneResults: Array<{
      targetUser: string;
      success: boolean;
      clonedCount: number;
      skippedCount: number;
      errorMessage?: string;
      successfulProfiles?: string[];
      failedProfiles?: Array<{ profileCode: string; error: string }>;
    }>,
    sourceUser: string
  ): void {
    console.log('🔍 [NotificationService] handleCloneResults called');
    console.log('🔍 [NotificationService] Clone results:', cloneResults);
    console.log('🔍 [NotificationService] Source user:', sourceUser);
    
    const totalTargets = cloneResults.length;
    const successfulTargets = cloneResults.filter(r => r.success).length;
    const failedTargets = cloneResults.filter(r => !r.success).length;

    // Create detailed breakdown
    const successDetails: NotificationDetail[] = cloneResults
      .filter(r => r.success)
      .map(r => ({
        identifier: r.targetUser,
        message: `${r.clonedCount} profil(s) cloné(s)`,
        icon: 'pi pi-check-circle'
      }));

    const failedDetails: NotificationDetail[] = [];
    cloneResults.filter(r => !r.success || (r.failedProfiles && r.failedProfiles.length > 0)).forEach(r => {
      // Priority 1: Show individual failed profiles
      if (r.failedProfiles && r.failedProfiles.length > 0) {
        console.log(`🔍 [NotificationService] Processing failed profiles for ${r.targetUser}:`, r.failedProfiles);
        r.failedProfiles.forEach(fp => {
          failedDetails.push({
            identifier: fp.profileCode,
            message: fp.error,
            icon: 'pi pi-times-circle'
          });
        });
      }
      // Priority 2: Only show generic error if no detailed profiles available
      else if (r.errorMessage && !r.failedProfiles) {
        console.log(`🔍 [NotificationService] Adding generic error for ${r.targetUser}:`, r.errorMessage);
        failedDetails.push({
          identifier: r.targetUser,
          message: r.errorMessage,
          icon: 'pi pi-exclamation-triangle'
        });
      }
    });

    console.log('✅ [NotificationService] Success details:', successDetails);
    console.log('✅ [NotificationService] Failed details:', failedDetails);

    if (failedTargets === 0 && successfulTargets > 0) {
      console.log('✅ [NotificationService] Complete success - adding notification');
      this.addNotification(
        'Clonage réussi',
        `Profils clonés avec succès vers ${successfulTargets} utilisateur(s)`,
        'success',
        'profile-clone',
        successDetails,
        { cloneResults, sourceUser }
      );
    } else if (successfulTargets === 0 && failedTargets > 0) {
      console.log('❌ [NotificationService] Complete failure - adding alert');
      this.addAlert(
        'Échec du clonage',
        `Impossible de cloner les profils vers ${failedTargets} utilisateur(s)`,
        'error',
        'profile-clone',
        failedDetails,
        { cloneResults, sourceUser }
      );
    } else {
      console.log('⚠️ [NotificationService] Partial success - adding both notification and alert');
      // Partial success
      this.addNotification(
        'Clonage partiel',
        `${successfulTargets}/${totalTargets} utilisateur(s) traité(s) avec succès`,
        'warn',
        'profile-clone',
        successDetails,
        { cloneResults, sourceUser }
      );

      this.addAlert(
        'Erreurs de clonage',
        `${failedDetails.length} erreur(s) lors du clonage`,
        'warn',
        'profile-clone',
        failedDetails,
        { cloneResults, sourceUser }
      );
    }
  }

  // ============================================================================
  // PUBLIC API - NOTIFICATION MANAGEMENT
  // ============================================================================

  /**
   * Mark a notification as read
   */
  markAsRead(id: string, type: 'notification' | 'alert'): void {
    if (type === 'notification') {
      const current = this.notificationsSubject.value;
      const updated = current.map(n => n.id === id ? { ...n, read: true } : n);
      this.notificationsSubject.next(updated);
    } else {
      const current = this.alertsSubject.value;
      const updated = current.map(a => a.id === id ? { ...a, read: true } : a);
      this.alertsSubject.next(updated);
    }
    this.updateUnreadCounts();
    this.persistData();
  }

  /**
   * Mark all notifications/alerts as read
   */
  markAllAsRead(type: 'notification' | 'alert'): void {
    if (type === 'notification') {
      const current = this.notificationsSubject.value;
      const updated = current.map(n => ({ ...n, read: true }));
      this.notificationsSubject.next(updated);
    } else {
      const current = this.alertsSubject.value;
      const updated = current.map(a => ({ ...a, read: true }));
      this.alertsSubject.next(updated);
    }
    this.updateUnreadCounts();
    this.persistData();
  }

  /**
   * Remove a notification/alert
   */
  remove(id: string, type: 'notification' | 'alert'): void {
    if (type === 'notification') {
      const current = this.notificationsSubject.value;
      const updated = current.filter(n => n.id !== id);
      this.notificationsSubject.next(updated);
    } else {
      const current = this.alertsSubject.value;
      const updated = current.filter(a => a.id !== id);
      this.alertsSubject.next(updated);
    }
    this.updateUnreadCounts();
    this.persistData();
  }

  /**
   * Clear all notifications/alerts
   */
  clearAll(type: 'notification' | 'alert'): void {
    if (type === 'notification') {
      this.notificationsSubject.next([]);
    } else {
      this.alertsSubject.next([]);
    }
    this.updateUnreadCounts();
    this.persistData();
  }

  /**
   * Get notifications by category
   */
  getByCategory(category: NotificationCategory, type: 'notification' | 'alert'): NotificationItem[] {
    const items = type === 'notification' 
      ? this.notificationsSubject.value 
      : this.alertsSubject.value;
    return items.filter(item => item.category === category);
  }

  // ============================================================================
  // PRIVATE HELPERS
  // ============================================================================

  private generateId(): string {
    return `${Date.now()}-${Math.random().toString(36).substring(2, 9)}`;
  }

  private updateUnreadCounts(): void {
    const notifUnread = this.notificationsSubject.value.filter(n => !n.read).length;
    const alertUnread = this.alertsSubject.value.filter(a => !a.read).length;
    this.unreadNotificationsCountSubject.next(notifUnread);
    this.unreadAlertsCountSubject.next(alertUnread);
  }

  private convertFailedToDetails(
    failed: { [key: string]: string } | Array<{ identifier: string; error: string }>
  ): NotificationDetail[] {
    console.log(failed)
    if (Array.isArray(failed)) {
      return failed.map(f => ({
        identifier: f.identifier,
        message: f.error,
        icon: 'pi pi-times-circle'
      }));
    }
    
    // Convert object to array
    return Object.entries(failed).map(([key, value]) => ({
      identifier: key,
      message: value.replace('⛔ ', ''), // Remove emoji for cleaner display
      icon: 'pi pi-times-circle'
    }));
  }

  private getOperationTitle(
    operationType: 'profile-assignment' | 'profile-clone' | 'pack-assignment',
    result: 'success' | 'error' | 'partial' | 'warning'
  ): string {
    const titles: Record<string, Record<string, string>> = {
      'profile-assignment': {
        success: 'Profils assignés',
        error: 'Échec d\'assignation',
        partial: 'Assignation partielle',
        warning: 'Erreurs d\'assignation'
      },
      'profile-clone': {
        success: 'Clonage réussi',
        error: 'Échec du clonage',
        partial: 'Clonage partiel',
        warning: 'Erreurs de clonage'
      },
      'pack-assignment': {
        success: 'Packs assignés',
        error: 'Échec d\'assignation de packs',
        partial: 'Assignation partielle',
        warning: 'Erreurs d\'assignation de packs'
      }
    };
    return titles[operationType]?.[result] || 'Opération';
  }

  private formatSuccessMessage(count: number, context?: { targetUser?: string }): string {
    const userInfo = context?.targetUser ? ` à ${context.targetUser}` : '';
    return `${count} profil(s) assigné(s) avec succès${userInfo}`;
  }

  private formatFailureMessage(count: number, context?: { targetUser?: string }): string {
    const userInfo = context?.targetUser ? ` pour ${context.targetUser}` : '';
    return `${count} profil(s) n'ont pas pu être assigné(s)${userInfo}. Cliquez pour voir les détails.`;
  }

  private formatPartialFailureMessage(
    successCount: number, 
    failureCount: number, 
    context?: { targetUser?: string }
  ): string {
    const userInfo = context?.targetUser ? ` pour ${context.targetUser}` : '';
    return `${failureCount} profil(s) en erreur${userInfo}. Cliquez pour voir les détails.`;
  }

  private persistData(): void {
    try {
      const data = {
        notifications: this.notificationsSubject.value,
        alerts: this.alertsSubject.value
      };
      sessionStorage.setItem('bna_notifications', JSON.stringify(data));
    } catch (e) {
      console.warn('Failed to persist notifications:', e);
    }
  }

  private loadPersistedData(): void {
    try {
      const raw = sessionStorage.getItem('bna_notifications');
      if (raw) {
        const data = JSON.parse(raw);
        if (data.notifications) {
          // Convert timestamp strings back to Date objects
          const notifications = data.notifications.map((n: any) => ({
            ...n,
            timestamp: new Date(n.timestamp)
          }));
          this.notificationsSubject.next(notifications);
        }
        if (data.alerts) {
          const alerts = data.alerts.map((a: any) => ({
            ...a,
            timestamp: new Date(a.timestamp)
          }));
          this.alertsSubject.next(alerts);
        }
        this.updateUnreadCounts();
      }
    } catch (e) {
      console.warn('Failed to load persisted notifications:', e);
    }
  }
}
