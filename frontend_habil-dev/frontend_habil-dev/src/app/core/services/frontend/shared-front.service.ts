import { Injectable } from "@angular/core";
import { HttpErrorResponse } from "@angular/common/http";
import { ConfirmationService, MessageService } from "primeng/api";

/**
 * Backend error response structures
 */
interface ErrorResponse {
  code: string;
  message: string;
  timestamp: string;
}

interface BatchErrorResponse {
  code: string;
  message: string;
  errors: Array<{
    index: number;
    identifier: string;
    error: string;
  }>;
  timestamp: string;
}

interface ResponseSAGA<T = any> {
  code: number;
  message: string;
  data: T;
}

@Injectable({
  providedIn: "root"
})
export class SharedFrontService {

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  // ==================== EXISTING METHODS (UNCHANGED) ====================

  /**
   * ✅ EXISTING - Show success message
   */
  showSuccess(detail: string, summary: string = 'Succès') {
    this.messageService.add({
      severity: 'success',
      summary,
      detail,
      life: 3000,
    });
  }

  /**
   * ✅ EXISTING - Show error message
   */
  showError(detail: string, summary: string = 'Erreur') {
    this.messageService.add({
      severity: 'error',
      summary,
      detail,
      life: 5000,
    });
  }

  /**
   * ✅ EXISTING - Show info message
   */
  showInfo(detail: string, summary: string = 'Info') {
    this.messageService.add({
      severity: 'info',
      summary,
      detail,
      life: 3000,
    });
  }

  /**
   * ✅ EXISTING - Show warning message
   */
  showWarn(detail: string, summary: string = 'Attention') {
    this.messageService.add({
      severity: 'warn',
      summary,
      detail,
      life: 4000,
    });
  }

  /**
   * ✅ EXISTING - Confirmation dialog
   */
  confirm(
    message: string,
    acceptCallback: () => void,
    header: string = "Confirmation",
    icon: string = "pi pi-exclamation-triangle"
  ) {
    this.confirmationService.confirm({
      message,
      header,
      icon,
      acceptButtonStyleClass: "p-button-danger",
      rejectButtonStyleClass: "p-button-secondary",
      accept: () => {
        acceptCallback();
      },
    });
  }

  // ==================== NEW ENHANCED METHODS ====================
  /**
   * ✅ NEW - Handle HTTP error responses from backend
   * Extracts full error message and displays it properly
   */
  handleError(error: HttpErrorResponse | any, defaultMessage: string = 'Une erreur est survenue') {
    console.error('🚨 [SharedFrontService] Error details:', error);

    let errorMessage = defaultMessage;
    let errorSummary = 'Erreur';

    if (error instanceof HttpErrorResponse) {
      // Extract error from backend response
      if (error.error && typeof error.error === 'object') {
        
        // Case 1: Legacy ResponseSAGA with batch operation error (returnCode: 1)
        if (error.error.returnCode === 1 && error.error.data) {
          console.log('🔍 [SharedFrontService] Detected Legacy ResponseSAGA batch error');
          this.handleLegacyBatchError(error.error);
          return; // Don't show additional error message
        }
        
        // Case 2: Legacy ResponseSAGA with simple error (returnCode: 1)
        else if (error.error.returnCode === 1) {
          console.log('🔍 [SharedFrontService] Detected Legacy ResponseSAGA simple error');
          errorMessage = error.error.message || 'Erreur dans le système legacy';
          errorSummary = 'Erreur Legacy';
        }
        
        // Case 3: Modern ErrorResponse from GlobalExceptionHandler
        else if (error.error.code && error.error.message) {
          console.log('🔍 [SharedFrontService] Detected Modern GlobalExceptionHandler error');
          const errorResponse = error.error as ErrorResponse;
          errorMessage = errorResponse.message;
          errorSummary = this.mapErrorCodeToSummary(errorResponse.code);
        }
        
        // Case 4: Modern BatchErrorResponse with multiple errors
        else if (error.error.errors && Array.isArray(error.error.errors)) {
          console.log('🔍 [SharedFrontService] Detected Modern batch error');
          const batchError = error.error as BatchErrorResponse;
          this.displayBatchErrors(batchError);
          return; // Don't show additional error message
        }
        
        // Case 5: Generic error with message
        else if (error.error.message) {
          errorMessage = error.error.message;
        }
      }
      
      // Fallback to HTTP error message
      else if (error.message) {
        errorMessage = error.message;
      }
      else if (error.statusText) {
        errorMessage = `${error.status}: ${error.statusText}`;
      }
    }
    // Handle non-HTTP errors
    else if (error && error.message) {
      errorMessage = error.message;
    }

    this.showEnhancedError(errorMessage, errorSummary, {
      timestamp: new Date().toISOString(),
      errorType: 'api-error'
    });
  }

  /**
   * ✅ NEW - Handle success response from backend
   */
  handleSuccess(response: ResponseSAGA | any, defaultMessage: string = 'Opération réussie') {
    const message = response?.message || defaultMessage;
    this.showSuccess(message);
  }
  /**
   * ✅ NEW - Handle batch operation results with enhanced visual indicators
   */
  handleBatchResult(result: any) {
    if (!result) {
      this.showError('Résultat du traitement par lot non disponible');
      return;
    }

    // Extract data if wrapped in ResponseSAGA
    const data = result.data || result;
    const successful = data.successful || [];
    const failed = data.failed || [];

    const successCount = Array.isArray(successful) ? successful.length : 0;
    const failureCount = Array.isArray(failed) ? failed.length : 0;
    const totalCount = successCount + failureCount;

    if (failureCount === 0 && successCount > 0) {
      // All successful
      this.showEnhancedSuccess(
        `${successCount} élément(s) traité(s) avec succès`,
        'Succès complet',
        {
          timestamp: new Date().toISOString(),
          progressPercent: 100
        }
      );
    } 
    else if (successCount === 0 && failureCount > 0) {
      // All failed
      this.showEnhancedError(
        `Échec du traitement : ${failureCount} élément(s) en erreur`,
        'Échec complet',
        {
          timestamp: new Date().toISOString(),
          errorDetails: failed
        }
      );
      this.displayBatchErrorsList(failed);    } 
    else if (successCount > 0 && failureCount > 0) {
      // Partial success
      const successPercent = Math.round((successCount / totalCount) * 100);
      this.showPartialSuccess(
        `${successCount} élément(s) traité(s), ${failureCount} en erreur`,
        'Succès partiel',
        {
          timestamp: new Date().toISOString(),
          progressPercent: successPercent,
          successCount,
          failureCount,
          errorDetails: failed
        }
      );
    }
  }

  /**
   * ✅ NEW - Confirm delete action
   */
  confirmDelete(
    itemName: string,
    acceptCallback: () => void,
    rejectCallback?: () => void
  ) {
    this.confirmationService.confirm({
      message: `Êtes-vous sûr de vouloir supprimer "${itemName}" ? Cette action est irréversible.`,
      header: "Confirmer la suppression",
      icon: "pi pi-trash",
      acceptButtonStyleClass: "p-button-danger",
      rejectButtonStyleClass: "p-button-secondary",
      acceptLabel: "Oui",
      rejectLabel: "Non",
      accept: () => {
        acceptCallback();
      },
      reject: () => {
        if (rejectCallback) {
          rejectCallback();
        }
      },
    });
  }

  /**
   * ✅ NEW - Custom confirmation with more options
   */
  confirmCustom(
    message: string,
    acceptCallback: () => void,
    rejectCallback?: () => void,
    options?: {
      header?: string;
      icon?: string;
      acceptLabel?: string;
      rejectLabel?: string;
      acceptButtonClass?: string;
      rejectButtonClass?: string;
    }
  ) {
    this.confirmationService.confirm({
      message,
      header: options?.header || "Confirmation",
      icon: options?.icon || "pi pi-exclamation-triangle",
      acceptLabel: options?.acceptLabel || "Oui",
      rejectLabel: options?.rejectLabel || "Non",
      acceptButtonStyleClass: options?.acceptButtonClass || "p-button-primary",
      rejectButtonStyleClass: options?.rejectButtonClass || "p-button-secondary",
      accept: () => {
        acceptCallback();
      },
      reject: () => {
        if (rejectCallback) {
          rejectCallback();
        }
      },
    });
  }

  /**
   * ✅ NEW - Show sticky error (stays until dismissed)
   */
  showStickyError(detail: string, summary: string = 'Erreur critique') {
    this.messageService.add({
      severity: 'error',
      summary,
      detail,
      sticky: true,
    });
  }

  /**
   * ✅ NEW - Show loading message
   */
  showLoading(message: string = 'Chargement en cours...') {
    this.messageService.add({
      severity: 'info',
      summary: 'Chargement',
      detail: message,
      life: 1000,
    });
  }
  /**
   * ✅ NEW - Clear all messages
   */
  clearAll() {
    this.messageService.clear();
  }

  // ==================== ENHANCED TOAST METHODS ====================

  /**
   * ✅ NEW - Show enhanced success message with additional metadata
   */
  showEnhancedSuccess(detail: string, summary: string = 'Succès', metadata?: any) {
    this.messageService.add({
      severity: 'success',
      summary,
      detail,
      life: 4000,
      data: {
        ...metadata,
        type: 'enhanced-success'
      }
    });
  }

  /**
   * ✅ NEW - Show enhanced error message with additional metadata and actions
   */
  showEnhancedError(detail: string, summary: string = 'Erreur', metadata?: any) {
    this.messageService.add({
      severity: 'error',
      summary,
      detail,
      life: 6000,
      sticky: metadata?.sticky || false,
      data: {
        ...metadata,
        type: 'enhanced-error'
      }
    });
  }

  /**
   * ✅ NEW - Show partial success with progress indicator
   */
  showPartialSuccess(detail: string, summary: string = 'Succès partiel', metadata?: any) {
    const progressHtml = metadata?.progressPercent ? 
      `${detail}<div class="progress-indicator" style="margin-top: 0.5rem;">
        <div class="progress-bar" style="width: ${metadata.progressPercent}%"></div>
      </div>` : detail;

    this.messageService.add({
      severity: 'warn',
      summary,
      detail: progressHtml,
      life: 5000,
      data: {
        ...metadata,
        type: 'partial-success'
      }
    });

    // Show detailed errors after a short delay
    if (metadata?.errorDetails && Array.isArray(metadata.errorDetails)) {
      setTimeout(() => {
        this.displayBatchErrorsList(metadata.errorDetails);
      }, 1000);
    }
  }

  /**
   * ✅ NEW - Show batch operation summary with detailed breakdown
   */
  showBatchSummary(summary: {
    total: number;
    successful: number;
    failed: number;
    errors?: Array<any>;
  }) {
    const { total, successful, failed } = summary;
    const successPercent = Math.round((successful / total) * 100);
    
    let severity: string;
    let summaryText: string;
    let detailText: string;

    if (failed === 0) {
      severity = 'success';
      summaryText = '✅ Traitement complet';
      detailText = `${successful}/${total} éléments traités avec succès`;
    } else if (successful === 0) {
      severity = 'error';
      summaryText = '❌ Échec complet';
      detailText = `${failed}/${total} éléments en erreur`;
    } else {
      severity = 'warn';
      summaryText = '⚠️ Succès partiel';
      detailText = `${successful}/${total} réussis (${successPercent}%), ${failed} échecs`;
    }

    this.messageService.add({
      severity,
      summary: summaryText,
      detail: detailText,
      life: severity === 'error' ? 8000 : 5000,
      data: {
        type: 'batch-summary',
        progressPercent: successPercent,
        ...summary
      }
    });

    // Show error details for partial/complete failures
    if (failed > 0 && summary.errors) {
      setTimeout(() => {
        this.displayBatchErrorsList(summary.errors);
      }, 1000);
    }
  }

  /**
   * ✅ NEW - Show dual backend system detection message
   */
  showSystemDetection(systemType: 'modern' | 'legacy', details?: string) {
    const systemLabels = {
      modern: '🔗 Système Moderne (Global Exception Handler)',
      legacy: '🔄 Système Legacy (ResponseSAGA)'
    };

    this.messageService.add({
      severity: 'info',
      summary: 'Détection Backend',
      detail: `${systemLabels[systemType]}${details ? ` - ${details}` : ''}`,
      life: 3000,
      data: {
        type: 'system-detection',
        systemType,
        timestamp: new Date().toISOString()
      }
    });
  }

  // ==================== PRIVATE HELPER METHODS ====================
  /**
   * Map backend error codes to user-friendly summaries
   */
  private mapErrorCodeToSummary(code: string): string {
    const errorSummaries: { [key: string]: string } = {
      'DUPLICATE_RESOURCE': '❌ Doublon détecté',
      'ENTITY_NOT_FOUND': '❌ Non trouvé',
      'VALIDATION_ERROR': '❌ Validation',
      'BATCH_OPERATION_FAILED': '⚠️ Traitement par lot',
      'INTERNAL_SERVER_ERROR': '❌ Erreur serveur',
      'UNAUTHORIZED': '🔒 Non autorisé',
      'FORBIDDEN': '🚫 Accès refusé',
      'BAD_REQUEST': '❌ Requête invalide',
      'CONFLICT': '❌ Conflit de données',
      'NOT_ACCEPTABLE': '❌ Format non accepté'
    };

    return errorSummaries[code] || 'Erreur';
  }

  /**
   * Display batch errors from BatchErrorResponse
   */
  private displayBatchErrors(batchError: BatchErrorResponse) {
    const errors = batchError.errors || [];
    
    if (errors.length === 0) {
      this.showError(batchError.message || 'Erreur de traitement par lot');
      return;
    }

    // Show first 3 errors in detail
    const errorsToShow = errors.slice(0, 3);
    
    errorsToShow.forEach((error, index) => {
      setTimeout(() => {
        this.showError(
          `${error.identifier}: ${error.error}`,
          `Erreur ${error.index + 1}`
        );
      }, index * 500);
    });

    // If there are more errors, show summary
    if (errors.length > 3) {
      setTimeout(() => {
        this.showWarn(
          `... et ${errors.length - 3} autre(s) erreur(s)`,
          'Erreurs supplémentaires'
        );
      }, 1500);
    }
  }
  /**
   * Display batch errors from array
   */
  private displayBatchErrorsList(errors: Array<any>) {
    if (!errors || errors.length === 0) return;

    // Show first 3 errors
    const errorsToShow = errors.slice(0, 3);
    
    errorsToShow.forEach((error, index) => {
      setTimeout(() => {
        const errorMsg = error.error || error.message || 'Erreur inconnue';
        const identifier = error.identifier || `Élément ${error.index || index}`;
        
        this.showEnhancedError(`${identifier}: ${errorMsg}`, `Erreur ${index + 1}`, {
          timestamp: new Date().toISOString(),
          errorType: 'batch-error-detail'
        });
      }, index * 500);
    });

    // Summary for remaining errors
    if (errors.length > 3) {
      setTimeout(() => {
        this.showWarn(
          `... et ${errors.length - 3} autre(s) erreur(s)`,
          'Erreurs supplémentaires'
        );
      }, 1500);
    }
  }

  /**
   * ✅ NEW - Handle Legacy ResponseSAGA batch operation errors
   */
  private handleLegacyBatchError(legacyResponse: any) {
    console.log('🔍 [SharedFrontService] Processing Legacy batch error:', legacyResponse);
    
    const data = legacyResponse.data;
    if (!data) {
      this.showEnhancedError(
        legacyResponse.message || 'Erreur de traitement par lot',
        'Erreur Batch Legacy',
        { timestamp: new Date().toISOString(), errorType: 'legacy-batch-unknown' }
      );
      return;
    }

    const successful = data.successful || [];
    const failed = data.failed || [];
    const successCount = Array.isArray(successful) ? successful.length : 0;
    const failureCount = Array.isArray(failed) ? failed.length : 0;
    const totalCount = successCount + failureCount;

    console.log(`📊 [SharedFrontService] Batch results: ${successCount} success, ${failureCount} failures`);

    if (failureCount === 0 && successCount > 0) {
      // All successful (shouldn't happen in error case, but handle it)
      this.showEnhancedSuccess(
        `${successCount} élément(s) traité(s) avec succès`,
        'Succès complet',
        {
          timestamp: new Date().toISOString(),
          systemType: 'legacy',
          successCount,
          failureCount: 0
        }
      );
    } 
    else if (successCount === 0 && failureCount > 0) {
      // Complete failure
      this.showEnhancedError(
        `❌ Échec complet: ${failureCount} élément(s) en erreur`,
        'Échec du traitement par lot',
        {
          timestamp: new Date().toISOString(),
          systemType: 'legacy',
          errorType: 'batch-complete-failure',
          failureCount,
          sticky: failureCount > 5 // Stick if many errors
        }
      );
      
      // Show detailed errors
      setTimeout(() => {
        this.displayBatchErrorsList(failed);
      }, 800);
    } 
    else if (successCount > 0 && failureCount > 0) {
      // Partial success
      const successPercent = Math.round((successCount / totalCount) * 100);
      this.showPartialSuccess(
        `⚠️ Résultat mixte: ${successCount} succès, ${failureCount} échec(s)`,
        'Succès partiel',
        {
          timestamp: new Date().toISOString(),
          systemType: 'legacy',
          progressPercent: successPercent,
          successCount,
          failureCount,
          errorDetails: failed
        }
      );
    }
    else {
      // Fallback case
      this.showEnhancedError(
        legacyResponse.message || 'Erreur inconnue dans le traitement par lot',
        'Erreur Batch',
        {
          timestamp: new Date().toISOString(),
          systemType: 'legacy',
          errorType: 'batch-unknown'
        }
      );
    }
  }
}