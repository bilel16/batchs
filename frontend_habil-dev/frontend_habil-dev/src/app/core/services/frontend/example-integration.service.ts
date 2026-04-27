import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BackendIntegrationService } from '../../../core/services/frontend/backend-integration.service';

/**
 * 🎯 EXAMPLE SERVICE INTEGRATION
 * 
 * This service demonstrates how to integrate any Angular service with the
 * dual backend system using the BackendIntegrationService.
 * 
 * Examples for all CRUD operations with both backend systems:
 * - Modern Spring Boot @RestControllerAdvice responses
 * - Legacy ResponseSAGA responses
 * - Batch operations with partial success handling
 */

@Injectable({
  providedIn: 'root'
})
export class ExampleIntegratedService {

  constructor(private backendIntegration: BackendIntegrationService) {}

  // ============================================================================
  // STANDARD CRUD OPERATIONS
  // ============================================================================

  /**
   * Example: GET operation that works with both backend systems
   * @param dataService The injected data service (e.g., ApplicationService)
   * @param successCallback Callback for successful data loading
   * @param errorCallback Callback for handling errors
   */
  loadData<T>(
    serviceCall: Observable<T>,
    successCallback: (data: any[]) => void,
    errorCallback?: (error: any) => void
  ) {
    return this.backendIntegration.quickOperation(
      serviceCall,
      "Données chargées avec succès",
      {
        onSuccess: (result) => {
          // Handle both response formats:
          // Modern: direct array or wrapped in data property
          // Legacy ResponseSAGA: result.data contains the actual data
          const data = result.data?.data || result.data || [];
          const normalizedData = Array.isArray(data) ? data : [];
          successCallback(normalizedData);
        },
        onError: (error) => {
          if (errorCallback) errorCallback(error);
        }
      }
    );
  }

  /**
   * Example: CREATE operation
   * @param serviceCall Create API call
   * @param itemName Name of item being created (for success message)
   * @param onSuccess Success callback
   * @param onError Error callback
   */
  createItem<T>(
    serviceCall: Observable<T>,
    itemName: string = "élément",
    onSuccess?: () => void,
    onError?: (error: any) => void
  ) {
    return this.backendIntegration.quickOperation(
      serviceCall,
      `${itemName} créé(e) avec succès`,
      {
        onSuccess: (result) => {
          if (onSuccess) onSuccess();
        },
        onError: (error) => {
          if (onError) onError(error);
        }
      }
    );
  }

  /**
   * Example: UPDATE operation
   * @param serviceCall Update API call
   * @param itemName Name of item being updated
   * @param onSuccess Success callback
   * @param onError Error callback
   */
  updateItem<T>(
    serviceCall: Observable<T>,
    itemName: string = "élément",
    onSuccess?: () => void,
    onError?: (error: any) => void
  ) {
    return this.backendIntegration.quickOperation(
      serviceCall,
      `${itemName} modifié(e) avec succès`,
      {
        onSuccess: (result) => {
          if (onSuccess) onSuccess();
        },
        onError: (error) => {
          if (onError) onError(error);
        }
      }
    );
  }

  /**
   * Example: DELETE operation with confirmation
   * @param serviceCall Delete API call
   * @param itemIdentifier User-friendly item identifier for confirmation
   * @param onSuccess Success callback
   * @param onError Error callback
   * @param onCancel Cancel callback
   */
  deleteItemWithConfirmation<T>(
    serviceCall: Observable<T>,
    itemIdentifier: string,
    onSuccess?: () => void,
    onError?: (error: any) => void,
    onCancel?: () => void
  ) {
    return this.backendIntegration.confirmAndDelete(
      itemIdentifier,
      serviceCall,
      "Élément supprimé avec succès",
      {
        onSuccess: (result) => {
          if (onSuccess) onSuccess();
        },
        onError: (error) => {
          if (onError) onError(error);
        },
        onCancel: onCancel
      }
    );
  }

  // ============================================================================
  // BATCH OPERATIONS EXAMPLES
  // ============================================================================

  /**
   * Example: Batch create operation
   * @param serviceCall Batch create API call
   * @param itemCount Number of items being created
   * @param onComplete Callback when operation completes (success, partial, or failure)
   */
  batchCreateItems<T>(
    serviceCall: Observable<T>,
    itemCount: number,
    onComplete?: (result: any) => void
  ) {
    return this.backendIntegration.quickOperation(
      serviceCall,
      `${itemCount} élément(s) créé(s) avec succès`,
      {
        onSuccess: (result) => {
          console.log('Batch operation result:', result);
          
          if (result.isPartialSuccess) {
            console.log(`Succès partiel: ${result.successCount} succès, ${result.errorCount} échecs`);
          } else {
            console.log(`Succès complet: ${result.successCount || itemCount} éléments créés`);
          }
          
          if (onComplete) onComplete(result);
        },
        onError: (error) => {
          console.error('Batch operation failed:', error);
          if (onComplete) onComplete({ success: false, error });
        }
      }
    );
  }

  /**
   * Example: Batch update operation
   * @param serviceCall Batch update API call
   * @param updates Array of updates being applied
   * @param onComplete Callback when operation completes
   */
  batchUpdateItems<T>(
    serviceCall: Observable<T>,
    updates: any[],
    onComplete?: (result: any) => void
  ) {
    const updateCount = updates.length;
    
    return this.backendIntegration.quickOperation(
      serviceCall,
      `${updateCount} mise(s) à jour appliquée(s) avec succès`,
      {
        onSuccess: (result) => {
          if (result.isPartialSuccess) {
            console.log(`Mises à jour partielles: ${result.successCount}/${updateCount}`);
          }
          if (onComplete) onComplete(result);
        },
        onError: (error) => {
          if (onComplete) onComplete({ success: false, error });
        }
      }
    );
  }

  // ============================================================================
  // COMPONENT INTEGRATION PATTERNS
  // ============================================================================

  /**
   * Complete CRUD example for a typical Angular component
   */
  getComponentMethods(dataService: any) {
    return {
      // Load all items
      loadAll: (callback: (items: any[]) => void) => {
        this.loadData(
          dataService.getAll(),
          callback,
          (error) => console.error('Load failed:', error)
        );
      },

      // Create new item
      create: (item: any, callback?: () => void) => {
        this.createItem(
          dataService.create(item),
          "Nouvel élément",
          callback
        );
      },

      // Update existing item
      update: (id: string, item: any, callback?: () => void) => {
        this.updateItem(
          dataService.update(id, item),
          "Élément",
          callback
        );
      },

      // Delete item
      delete: (item: any, callback?: () => void) => {
        const identifier = item.name || item.libelle || item.code || `élément ${item.id}`;
        this.deleteItemWithConfirmation(
          dataService.delete(item.id),
          identifier,
          callback
        );
      },

      // Batch operations
      batchCreate: (items: any[], callback?: (result: any) => void) => {
        this.batchCreateItems(
          dataService.createBatch(items),
          items.length,
          callback
        );
      }
    };
  }

  // ============================================================================
  // ADVANCED INTEGRATION EXAMPLES
  // ============================================================================

  /**
   * Example: Complex operation with multiple API calls
   * Shows how to chain operations while maintaining dual system compatibility
   */
  complexOperation<T>(
    step1Call: Observable<T>,
    step2CallFactory: (step1Result: any) => Observable<T>,
    step3CallFactory: (step2Result: any) => Observable<T>
  ) {
    // Step 1
    return this.backendIntegration.performApiOperation(
      step1Call,
      "Étape 1 terminée",
      "Complex Operation Step 1"
    ).subscribe({
      next: (result1) => {
        console.log('Step 1 completed:', result1);
        
        // Step 2 - using result from step 1
        const step2Data = result1.data?.data || result1.data;
        const step2Call = step2CallFactory(step2Data);
        
        this.backendIntegration.performApiOperation(
          step2Call,
          "Étape 2 terminée",
          "Complex Operation Step 2"
        ).subscribe({
          next: (result2) => {
            console.log('Step 2 completed:', result2);
            
            // Step 3 - using result from step 2
            const step3Data = result2.data?.data || result2.data;
            const step3Call = step3CallFactory(step3Data);
            
            this.backendIntegration.quickOperation(
              step3Call,
              "Opération complexe terminée avec succès",
              {
                onSuccess: (finalResult) => {
                  console.log('Complex operation completed:', finalResult);
                },
                onError: (error) => {
                  console.error('Step 3 failed:', error);
                }
              }
            );
          },
          error: (error) => {
            console.error('Step 2 failed:', error);
          }
        });
      },
      error: (error) => {
        console.error('Step 1 failed:', error);
      }
    });
  }

  // ============================================================================
  // UTILITY METHODS FOR COMMON PATTERNS
  // ============================================================================

  /**
   * Utility: Form submission handler
   * @param form Form data
   * @param serviceCall API call for form submission
   * @param onSuccess Success callback
   * @param onValidationError Validation error callback
   */
  handleFormSubmission<T>(
    form: any,
    serviceCall: Observable<T>,
    onSuccess?: (result: any) => void,
    onValidationError?: (errors: any) => void
  ) {
    // Client-side validation could go here
    
    return this.backendIntegration.quickOperation(
      serviceCall,
      "Formulaire soumis avec succès",
      {
        onSuccess: (result) => {
          if (onSuccess) onSuccess(result);
        },
        onError: (error) => {
          // Check if it's a validation error (HTTP 400)
          if (error.status === 400 && onValidationError) {
            onValidationError(error.error);
          }
        }
      }
    );
  }

  /**
   * Utility: Data refresh handler
   * @param refreshCall API call to refresh data
   * @param onDataRefreshed Callback with refreshed data
   */
  refreshData<T>(
    refreshCall: Observable<T>,
    onDataRefreshed: (data: any) => void
  ) {
    return this.backendIntegration.quickOperation(
      refreshCall,
      "", // No success message for refresh
      {
        onSuccess: (result) => {
          const data = result.data?.data || result.data || [];
          onDataRefreshed(data);
        }
      }
    );
  }
}
