/**
 * @fileoverview Operation Notification Service
 * 
 * Generalized service for handling batch operation notifications across the application.
 * This service provides a unified interface for displaying operation results (success, partial, failure)
 * using toast messages and persistent notification panels.
 * 
 * Follows SonarQube standards:
 * - Cognitive complexity < 15 per method
 * - Single Responsibility Principle
 * - DRY (Don't Repeat Yourself)
 * - Strong typing with interfaces
 * - Comprehensive documentation
 * - Error handling best practices
 * 
 * @author BNA HABIL Development Team
 * @version 1.0.0
 * @since 2026-01-12
 */

import { Injectable } from '@angular/core';
import { MessageService } from 'primeng/api';
import { NotificationService, NotificationCategory, NotificationSeverity } from './notification.service';
import { BatchAssignmentResult, getFailedItemsArray, isCompleteSuccess, isPartialSuccess, isCompleteFailure } from '../../models/batch-assignement-result';

// ============================================================================
// TYPE DEFINITIONS & INTERFACES
// ============================================================================

/**
 * Operation type enum for categorization
 */
export enum OperationType {
  PROFILE_ASSIGNMENT = 'profile-assignment',
  PROFILE_CLONE = 'profile-clone',
  PACK_ASSIGNMENT = 'pack-assignment',
  USER_CREATION = 'user-creation',
  USER_UPDATE = 'user-update',
  USER_DELETION = 'user-deletion',
  BATCH_UPDATE = 'batch-update',
  GENERAL = 'general'
}

/**
 * Operation context for providing additional information
 */
export interface OperationContext {
  /** Target user name or identifier */
  targetUser?: string;
  /** Source user name (for clone operations) */
  sourceUser?: string;
  /** Application code */
  appCode?: string;
  /** Custom data for specific operations */
  customData?: Record<string, any>;
}

/**
 * Single operation result item
 */
export interface OperationResultItem {
  /** Identifier (profile code, user name, etc.) */
  identifier: string;
  /** Error message if failed */
  error?: string;
  /** Success status */
  success: boolean;
}

/**
 * Notification configuration options
 */
export interface NotificationOptions {
  /** Show toast message */
  showToast?: boolean;
  /** Show in notification panel */
  showInPanel?: boolean;
  /** Toast life duration in ms */
  toastLife?: number;
  /** Custom success message */
  successMessage?: string;
  /** Custom error message */
  errorMessage?: string;
  /** Custom partial success message */
  partialSuccessMessage?: string;
}

// ============================================================================
// SERVICE IMPLEMENTATION
// ============================================================================

@Injectable({
  providedIn: 'root'
})
export class OperationNotificationService {

  /** Default notification options */
  private readonly DEFAULT_OPTIONS: NotificationOptions = {
    showToast: true,
    showInPanel: true,
    toastLife: 5000
  };

  constructor(
    private messageService: MessageService,
    private notificationService: NotificationService
  ) {}

  // ============================================================================
  // PUBLIC API - BATCH OPERATIONS
  // ============================================================================

  /**
   * Handle batch operation result with automatic categorization
   * 
   * @param result - Batch operation result from API
   * @param operationType - Type of operation performed
   * @param context - Additional context information
   * @param options - Notification display options
   */
  handleBatchOperationResult(
    result: BatchAssignmentResult,
    operationType: OperationType,
    context?: OperationContext,
    options?: NotificationOptions
  ): void {
    const opts = { ...this.DEFAULT_OPTIONS, ...options };

    if (isCompleteSuccess(result)) {
      this.handleCompleteSuccess(result, operationType, context, opts);
    } else if (isPartialSuccess(result)) {
      this.handlePartialSuccess(result, operationType, context, opts);
    } else if (isCompleteFailure(result)) {
      this.handleCompleteFailure(result, operationType, context, opts);
    }
  }

  /**
   * Handle single operation success
   * 
   * @param message - Success message
   * @param operationType - Type of operation
   * @param context - Additional context
   * @param options - Notification options
   */
  handleSingleSuccess(
    message: string,
    operationType: OperationType,
    context?: OperationContext,
    options?: NotificationOptions
  ): void {
    const opts = { ...this.DEFAULT_OPTIONS, ...options };

    if (opts.showToast) {
      this.showSuccessToast('Succès', message, opts.toastLife);
    }

    if (opts.showInPanel) {
      this.addSuccessNotification(
        message,
        this.getOperationLabel(operationType),
        this.mapOperationTypeToCategory(operationType),
        context
      );
    }
  }

  /**
   * Handle single operation error
   * 
   * @param error - Error message or object
   * @param operationType - Type of operation
   * @param context - Additional context
   * @param options - Notification options
   */
  handleSingleError(
    error: string | Error,
    operationType: OperationType,
    context?: OperationContext,
    options?: NotificationOptions
  ): void {
    const opts = { ...this.DEFAULT_OPTIONS, ...options };
    const errorMessage = typeof error === 'string' ? error : error.message;

    if (opts.showToast) {
      this.showErrorToast('Erreur', errorMessage, opts.toastLife);
    }

    if (opts.showInPanel) {
      this.addErrorAlert(
        this.getOperationLabel(operationType),
        errorMessage,
        this.mapOperationTypeToCategory(operationType),
        context
      );
    }
  }

  // ============================================================================
  // PRIVATE HELPERS - RESULT HANDLING
  // ============================================================================

  /**
   * Handle complete success scenario
   */
  private handleCompleteSuccess(
    result: BatchAssignmentResult,
    operationType: OperationType,
    context?: OperationContext,
    options?: NotificationOptions
  ): void {
    const message = options?.successMessage || 
      this.buildSuccessMessage(result, operationType, context);

    if (options?.showToast) {
      this.showSuccessToast('Succès', message, options.toastLife);
    }

    if (options?.showInPanel && result.successCount > 0) {
      this.addSuccessNotification(
        message,
        this.buildDetailedSuccessList(result, context),
        this.mapOperationTypeToCategory(operationType),
        context
      );
    }
  }

  /**
   * Handle partial success scenario
   */
  private handlePartialSuccess(
    result: BatchAssignmentResult,
    operationType: OperationType,
    context?: OperationContext,
    options?: NotificationOptions
  ): void {
    const successMessage = options?.partialSuccessMessage || 
      this.buildPartialSuccessMessage(result, operationType, context);
    const errorMessage = this.buildPartialErrorMessage(result, operationType, context);

    // Show toast for partial success
    if (options?.showToast) {
      this.showWarningToast('Succès partiel', successMessage, options.toastLife);
    }

    // Add success notification for successful items
    if (options?.showInPanel && result.successCount > 0) {
      this.addSuccessNotification(
        successMessage,
        this.buildDetailedSuccessList(result, context),
        this.mapOperationTypeToCategory(operationType),
        context
      );
    }

    // Add alert for failed items
    if (options?.showInPanel && result.failureCount > 0) {
      this.addErrorAlert(
        errorMessage,
        this.buildDetailedErrorList(result, context),
        this.mapOperationTypeToCategory(operationType),
        context
      );
    }
  }

  /**
   * Handle complete failure scenario
   */
  private handleCompleteFailure(
    result: BatchAssignmentResult,
    operationType: OperationType,
    context?: OperationContext,
    options?: NotificationOptions
  ): void {
    const message = options?.errorMessage || 
      this.buildErrorMessage(result, operationType, context);

    if (options?.showToast) {
      this.showErrorToast('Erreur', message, options.toastLife);
    }

    if (options?.showInPanel) {
      this.addErrorAlert(
        message,
        this.buildDetailedErrorList(result, context),
        this.mapOperationTypeToCategory(operationType),
        context
      );
    }
  }

  // ============================================================================
  // PRIVATE HELPERS - MESSAGE BUILDERS
  // ============================================================================

  /**
   * Build success message for complete success
   */
  private buildSuccessMessage(
    result: BatchAssignmentResult,
    operationType: OperationType,
    context?: OperationContext
  ): string {
    const count = result.successCount;
    const operationLabel = this.getOperationLabel(operationType);
    const target = context?.targetUser ? ` pour ${context.targetUser}` : '';
    
    return `${operationLabel} réussi : ${count} élément(s)${target}`;
  }
  /**
   * Build message for partial success
   */
  private buildPartialSuccessMessage(
    result: BatchAssignmentResult,
    operationType: OperationType,
    context?: OperationContext
  ): string {
    const successCount = result.successCount;
    const total = result.successCount + result.failureCount;
    const operationLabel = this.getOperationLabel(operationType);
    const target = context?.targetUser ? ` pour ${context.targetUser}` : '';
    
    return `${operationLabel} partiel : ${successCount}/${total} élément(s) traité(s)${target}`;
  }

  /**
   * Build error message for partial success
   */
  private buildPartialErrorMessage(
    result: BatchAssignmentResult,
    operationType: OperationType,
    context?: OperationContext
  ): string {
    const failureCount = result.failureCount;
    const total = result.successCount + result.failureCount;
    const operationLabel = this.getOperationLabel(operationType);
    const target = context?.targetUser ? ` - ${context.targetUser}` : '';
    
    return `${operationLabel} échoué : ${failureCount}/${total} élément(s)${target}`;
  }

  /**
   * Build error message for complete failure
   */
  private buildErrorMessage(
    result: BatchAssignmentResult,
    operationType: OperationType,
    context?: OperationContext
  ): string {
    const count = result.failureCount;
    const operationLabel = this.getOperationLabel(operationType);
    const target = context?.targetUser ? ` pour ${context.targetUser}` : '';
    
    return `${operationLabel} échoué : ${count} élément(s)${target}`;
  }

  /**
   * Build detailed success list for notification details
   */
  private buildDetailedSuccessList(
    result: BatchAssignmentResult,
    context?: OperationContext
  ): string {
    const items = result.successful || [];
    const prefix = context?.targetUser ? `${context.targetUser} → ` : '';
    
    return items.map(item => `${prefix}${item}`).join('\n');
  }

  /**
   * Build detailed error list for alert details
   */
  private buildDetailedErrorList(
    result: BatchAssignmentResult,
    context?: OperationContext
  ): string {
    const failedItems = getFailedItemsArray(result);
    const prefix = context?.targetUser ? `${context.targetUser} → ` : '';
    
    return failedItems
      .map(item => `${prefix}${item.identifier}: ${item.error}`)
      .join('\n');
  }

  /**
   * Get human-readable operation label
   */
  private getOperationLabel(operationType: OperationType): string {
    const labels: Record<OperationType, string> = {
      [OperationType.PROFILE_ASSIGNMENT]: 'Attribution de profils',
      [OperationType.PROFILE_CLONE]: 'Clonage de profils',
      [OperationType.PACK_ASSIGNMENT]: 'Attribution de packs',
      [OperationType.USER_CREATION]: 'Création d\'utilisateur',
      [OperationType.USER_UPDATE]: 'Modification d\'utilisateur',
      [OperationType.USER_DELETION]: 'Suppression d\'utilisateur',
      [OperationType.BATCH_UPDATE]: 'Mise à jour en lot',
      [OperationType.GENERAL]: 'Opération'
    };
    
    return labels[operationType] || 'Opération';
  }

  /**
   * Map operation type to notification category
   */
  private mapOperationTypeToCategory(operationType: OperationType): NotificationCategory {
    const mapping: Record<OperationType, NotificationCategory> = {
      [OperationType.PROFILE_ASSIGNMENT]: 'profile-assignment',
      [OperationType.PROFILE_CLONE]: 'profile-clone',
      [OperationType.PACK_ASSIGNMENT]: 'pack-assignment',
      [OperationType.USER_CREATION]: 'user-management',
      [OperationType.USER_UPDATE]: 'user-management',
      [OperationType.USER_DELETION]: 'user-management',
      [OperationType.BATCH_UPDATE]: 'system',
      [OperationType.GENERAL]: 'general'
    };
    
    return mapping[operationType] || 'general';
  }

  // ============================================================================
  // PRIVATE HELPERS - TOAST MESSAGES
  // ============================================================================

  /**
   * Show success toast
   */
  private showSuccessToast(summary: string, detail: string, life?: number): void {
    this.messageService.add({
      severity: 'success',
      summary,
      detail,
      life: life || 5000
    });
  }

  /**
   * Show error toast
   */
  private showErrorToast(summary: string, detail: string, life?: number): void {
    this.messageService.add({
      severity: 'error',
      summary,
      detail,
      life: life || 5000
    });
  }

  /**
   * Show warning toast
   */
  private showWarningToast(summary: string, detail: string, life?: number): void {
    this.messageService.add({
      severity: 'warn',
      summary,
      detail,
      life: life || 5000
    });
  }

  /**
   * Show info toast
   */
  private showInfoToast(summary: string, detail: string, life?: number): void {
    this.messageService.add({
      severity: 'info',
      summary,
      detail,
      life: life || 5000
    });
  }

  // ============================================================================
  // PRIVATE HELPERS - PANEL NOTIFICATIONS
  // ============================================================================

  /**
   * Add success notification to panel
   */
  private addSuccessNotification(
    title: string,
    message: string,
    category: NotificationCategory,
    context?: OperationContext
  ): void {
    this.notificationService.addNotification(
      title,
      message,
      'success',
      category,
      undefined,
      context
    );
  }

  /**
   * Add error alert to panel
   */
  private addErrorAlert(
    title: string,
    message: string,
    category: NotificationCategory,
    context?: OperationContext
  ): void {
    const failedItems = message.split('\n').map(line => {
      const parts = line.split(':');
      return {
        identifier: parts[0]?.trim() || '',
        message: parts.slice(1).join(':').trim() || ''
      };
    });

    this.notificationService.addAlert(
      title,
      message,
      'error',
      category,
      failedItems,
      context
    );
  }
}
