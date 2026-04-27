import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { SharedFrontService } from './shared-front.service';

/**
 * 🚀 DUAL BACKEND SYSTEM INTEGRATION SERVICE
 * 
 * This service provides seamless integration with the Spring Boot backend's
 * dual error handling system:
 * - Modern Global Exception Handler (@RestControllerAdvice)
 * - Legacy ResponseSAGA system
 * 
 * Features:
 * - Automatic system detection
 * - Unified error handling across both systems
 * - Batch operation support with partial success handling
 * - Enhanced logging and debugging
 * - User-friendly French error messages
 */

// ============================================================================
// BACKEND RESPONSE INTERFACES
// ============================================================================

interface ModernErrorResponse {
  code: string;
  message: string;
  timestamp: string;
}

interface BatchOperationResponse {
  code: string;
  message: string;
  errors: Array<{
    index: number;
    identifier: string;
    error: string;
  }>;
  timestamp: string;
  totalItems?: number;
}

interface LegacyResponseSAGA<T = any> {
  returnCode: 0 | 1;
  message: string;
  data: T;
}

interface ApiOperationResult {
  success: boolean;
  data?: any;
  error?: any;
  isPartialSuccess?: boolean;
  successCount?: number;
  errorCount?: number;
}

// ============================================================================
// SERVICE IMPLEMENTATION
// ============================================================================

@Injectable({
  providedIn: 'root'
})
export class BackendIntegrationService {

  constructor(private sharedService: SharedFrontService) {}

  // ============================================================================
  // DUAL SYSTEM API OPERATION HANDLER
  // ============================================================================

  /**
   * Universal API operation handler that works with both backend systems
   * @param serviceCall Observable API call
   * @param successMessage Message to show on success
   * @param operationName Operation name for logging
   * @returns Observable with unified result handling
   */
  performApiOperation<T>(
    serviceCall: Observable<T>,
    successMessage: string,
    operationName: string = 'API Operation'
  ): Observable<ApiOperationResult> {
    this.logApiCall(operationName, 'starting');

    return serviceCall.pipe(
      map((response: any) => {
        this.logApiCall(operationName, 'response', null, response);
        return this.handleApiResponse(response, successMessage);
      }),
      catchError((httpError: HttpErrorResponse) => {
        this.logApiCall(operationName, 'error', null, null, httpError);
        this.handleApiError(httpError, operationName);
        return throwError(() => httpError);
      })
    );
  }

  /**
   * Handle successful API responses from either backend system
   * @param response API response
   * @param successMessage Success message to display
   * @returns ApiOperationResult
   */
  private handleApiResponse(response: any, successMessage: string): ApiOperationResult {
    // Detect legacy ResponseSAGA system
    if (this.isLegacyResponse(response)) {
      return this.handleLegacyResponse(response, successMessage);
    }
    
    // Handle modern system response
    return this.handleModernResponse(response, successMessage);
  }

  /**
   * Detect if response is from legacy ResponseSAGA system
   * @param response API response
   * @returns true if legacy response
   */
  private isLegacyResponse(response: any): boolean {
    return response && typeof response.returnCode !== 'undefined';
  }
  /**
   * Handle legacy ResponseSAGA responses
   * @param response Legacy response
   * @param successMessage Success message
   * @returns ApiOperationResult
   */
  private handleLegacyResponse(response: LegacyResponseSAGA, successMessage: string): ApiOperationResult {
    if (response.returnCode === 0) {
      // Success
      this.sharedService.handleSuccess(response, successMessage);
      return {
        success: true,
        data: response.data
      };
    } else {
      // Error from legacy system - create proper HttpErrorResponse-like structure
      console.log('🔍 [BackendIntegration] Legacy error response:', response);
      
      const mockHttpError = {
        error: response, // Pass the full legacy response
        status: 400,
        statusText: 'Legacy System Error',
        message: response.message || 'Erreur dans le système legacy'
      } as any;
      
      // Let SharedFrontService handle the full legacy response
      this.sharedService.handleError(mockHttpError, response.message || 'Erreur Legacy');
      
      return {
        success: false,
        error: response.message
      };
    }
  }

  /**
   * Handle modern system responses
   * @param response Modern response
   * @param successMessage Success message
   * @returns ApiOperationResult
   */
  private handleModernResponse(response: any, successMessage: string): ApiOperationResult {
    // Check for batch operation indicators
    if (this.isBatchOperation(response)) {
      return this.handleBatchResponse(response, successMessage);
    }

    // Standard success response
    this.sharedService.handleSuccess(response, successMessage);
    return {
      success: true,
      data: response
    };
  }

  /**
   * Check if response indicates a batch operation
   * @param response API response
   * @returns true if batch operation
   */
  private isBatchOperation(response: any): boolean {
    return response && (
      Array.isArray(response.errors) ||
      typeof response.successCount !== 'undefined' ||
      typeof response.failureCount !== 'undefined' ||
      response.code === 'BATCH_OPERATION_FAILED'
    );
  }

  /**
   * Handle batch operation responses
   * @param response Batch response
   * @param successMessage Success message
   * @returns ApiOperationResult
   */
  private handleBatchResponse(response: any, successMessage: string): ApiOperationResult {
    const errors = response.errors || [];
    const successCount = response.successCount || (response.totalItems - errors.length) || 0;
    const errorCount = errors.length;
    const totalItems = response.totalItems || (successCount + errorCount);

    if (errorCount === 0) {
      // Complete success
      this.sharedService.handleSuccess(
        response,
        `✅ ${successMessage} - ${successCount} élément(s) traité(s)`
      );
      return {
        success: true,
        data: response,
        successCount,
        errorCount: 0
      };
    } else if (successCount === 0) {
      // Complete failure
      const message = `❌ Échec complet: ${errorCount} erreur(s)`;
      this.sharedService.handleError(
        new Error(response.message || 'Batch operation failed'),
        message
      );
      this.showBatchErrorDetails(errors);
      return {
        success: false,
        error: response.message,
        successCount: 0,
        errorCount
      };
    } else {
      // Partial success
      const message = `⚠️ Résultat mixte: ${successCount} succès, ${errorCount} échec(s)`;
      this.sharedService.showWarn(message, 'Succès partiel');
      this.showBatchErrorDetails(errors);
      return {
        success: true,
        isPartialSuccess: true,
        data: response,
        successCount,
        errorCount
      };
    }
  }

  // ============================================================================
  // ERROR HANDLING BY HTTP STATUS CODE
  // ============================================================================

  /**
   * Handle HTTP errors by status code and backend system
   * @param httpError HTTP error response
   * @param operationName Operation name for context
   */
  private handleApiError(httpError: HttpErrorResponse, operationName: string): void {
    console.error(`❌ [${operationName}] HTTP Error:`, httpError.status, httpError.error);

    switch (httpError.status) {
      case 400: // ValidationException
        this.handleValidationError(httpError);
        break;
      case 404: // EntityNotFoundException
        this.handleNotFoundError(httpError);
        break;
      case 409: // DuplicateResourceException
        this.handleDuplicateError(httpError);
        break;
      case 207: // BatchOperationException
        this.handleBatchOperationError(httpError);
        break;
      case 500: // Server Error
        this.handleServerError(httpError);
        break;
      case 401: // Unauthorized
        this.handleUnauthorizedError(httpError);
        break;
      case 403: // Forbidden
        this.handleForbiddenError(httpError);
        break;
      default:
        this.handleGenericError(httpError);
    }
  }

  /**
   * Handle 400 - Validation errors
   */
  private handleValidationError(httpError: HttpErrorResponse): void {
    const message = this.extractErrorMessage(httpError) || 'Données invalides';
    this.sharedService.handleError(httpError, `❌ Validation: ${message}`);
  }

  /**
   * Handle 404 - Not found errors
   */
  private handleNotFoundError(httpError: HttpErrorResponse): void {
    const message = this.extractErrorMessage(httpError) || 'Ressource introuvable';
    this.sharedService.handleError(httpError, `❌ Non trouvé: ${message}`);
  }

  /**
   * Handle 409 - Duplicate resource errors
   */
  private handleDuplicateError(httpError: HttpErrorResponse): void {
    const message = this.extractErrorMessage(httpError) || 'Ressource déjà existante';
    this.sharedService.handleError(httpError, `❌ Doublon: ${message}`);
  }

  /**
   * Handle 207 - Batch operation errors
   */
  private handleBatchOperationError(httpError: HttpErrorResponse): void {
    if (httpError.error && httpError.error.errors) {
      const batchError = httpError.error as BatchOperationResponse;
      this.handleBatchResponse(httpError.error, 'Opération par lot');
    } else {
      const message = this.extractErrorMessage(httpError) || 'Erreur dans le traitement par lot';
      this.sharedService.handleError(httpError, `❌ Batch: ${message}`);
    }
  }

  /**
   * Handle 500 - Server errors
   */
  private handleServerError(httpError: HttpErrorResponse): void {
    this.sharedService.handleError(
      httpError,
      '❌ Erreur serveur - Contactez l\'administrateur'
    );
  }

  /**
   * Handle 401 - Unauthorized errors
   */
  private handleUnauthorizedError(httpError: HttpErrorResponse): void {
    const message = this.extractErrorMessage(httpError) || 'Session expirée';
    this.sharedService.handleError(httpError, `❌ Non autorisé: ${message}`);
  }

  /**
   * Handle 403 - Forbidden errors
   */
  private handleForbiddenError(httpError: HttpErrorResponse): void {
    const message = this.extractErrorMessage(httpError) || 'Accès interdit';
    this.sharedService.handleError(httpError, `❌ Accès refusé: ${message}`);
  }

  /**
   * Handle generic/unknown errors
   */
  private handleGenericError(httpError: HttpErrorResponse): void {
    const message = this.extractErrorMessage(httpError) || 'Erreur technique';
    this.sharedService.handleError(
      httpError,
      `❌ Erreur (${httpError.status}): ${message}`
    );
  }

  // ============================================================================
  // UTILITY METHODS
  // ============================================================================

  /**
   * Extract error message from HTTP error response
   * @param httpError HTTP error response
   * @returns Extracted error message
   */
  private extractErrorMessage(httpError: HttpErrorResponse): string | null {
    // Try modern error response format
    if (httpError.error && httpError.error.message) {
      return httpError.error.message;
    }

    // Try legacy format
    if (httpError.error && httpError.error.message) {
      return httpError.error.message;
    }

    // Fallback to HTTP error message
    if (httpError.message) {
      return httpError.message;
    }

    return null;
  }

  /**
   * Show detailed batch error information
   * @param errors Array of batch errors
   */
  private showBatchErrorDetails(errors: Array<any>): void {
    if (!errors || errors.length === 0) return;

    console.group('🔍 Détails des erreurs batch:');
    errors.forEach((error, index) => {
      const identifier = error.identifier || `Élément ${error.index || index}`;
      const errorMsg = error.error || 'Erreur inconnue';
      console.log(`• ${identifier}: ${errorMsg}`);
    });
    console.groupEnd();

    // Show first few errors in UI
    const errorsToShow = errors.slice(0, 3);
    errorsToShow.forEach((error, index) => {
      setTimeout(() => {
        const identifier = error.identifier || `Élément ${error.index || index}`;
        const errorMsg = error.error || 'Erreur inconnue';
        this.sharedService.showError(
          `${identifier}: ${errorMsg}`,
          `Erreur ${index + 1}`
        );
      }, index * 500);
    });

    // Show summary for remaining errors
    if (errors.length > 3) {
      setTimeout(() => {
        this.sharedService.showWarn(
          `... et ${errors.length - 3} autre(s) erreur(s)`,
          'Erreurs supplémentaires'
        );
      }, 1500);
    }
  }

  /**
   * Enhanced logging for API calls
   * @param operation Operation name
   * @param phase Operation phase
   * @param request Request data
   * @param response Response data
   * @param error Error data
   */
  private logApiCall(
    operation: string,
    phase: 'starting' | 'response' | 'error',
    request?: any,
    response?: any,
    error?: any
  ): void {
    const timestamp = new Date().toISOString();
    
    switch (phase) {
      case 'starting':
        console.group(`🚀 [${timestamp}] ${operation} - Starting`);
        if (request) console.log('📤 Request:', request);
        console.groupEnd();
        break;
        
      case 'response':
        console.group(`✅ [${timestamp}] ${operation} - Success`);
        console.log('📥 Response:', response);
        console.log('🏷️ System:', this.isLegacyResponse(response) ? 'Legacy ResponseSAGA' : 'Modern GlobalExceptionHandler');
        if (this.isBatchOperation(response)) {
          console.log('🔄 Type: Batch Operation');
        }
        console.groupEnd();
        break;
        
      case 'error':
        console.group(`❌ [${timestamp}] ${operation} - Error`);
        console.error('🚨 HTTP Status:', error?.status);
        console.error('🚨 Error Code:', error?.error?.code);
        console.error('🚨 Error Message:', error?.error?.message);
        console.error('🚨 Full Error:', error);
        console.groupEnd();
        break;
    }
  }

  // ============================================================================
  // CONVENIENCE METHODS FOR COMPONENTS
  // ============================================================================

  /**
   * Quick method for simple CRUD operations
   * @param serviceCall API call observable
   * @param successMessage Success message
   * @param callbacks Success and error callbacks
   */
  quickOperation<T>(
    serviceCall: Observable<T>,
    successMessage: string,
    callbacks?: {
      onSuccess?: (result: ApiOperationResult) => void;
      onError?: (error: any) => void;
      onFinally?: () => void;
    }
  ) {
    return this.performApiOperation(serviceCall, successMessage)
      .subscribe({
        next: (result) => {
          if (callbacks?.onSuccess) callbacks.onSuccess(result);
        },
        error: (error) => {
          if (callbacks?.onError) callbacks.onError(error);
        },
        complete: () => {
          if (callbacks?.onFinally) callbacks.onFinally();
        }
      });
  }

  /**
   * Method specifically for delete operations with confirmation
   * @param confirmMessage Confirmation message
   * @param serviceCall Delete API call
   * @param successMessage Success message
   * @param callbacks Callbacks
   */
  confirmAndDelete<T>(
    confirmMessage: string,
    serviceCall: Observable<T>,
    successMessage: string = 'Élément supprimé avec succès',
    callbacks?: {
      onSuccess?: (result: ApiOperationResult) => void;
      onError?: (error: any) => void;
      onCancel?: () => void;
    }
  ) {
    this.sharedService.confirmDelete(confirmMessage, () => {
      this.quickOperation(serviceCall, successMessage, {
        onSuccess: callbacks?.onSuccess,
        onError: callbacks?.onError
      });
    }, callbacks?.onCancel);
  }
}
