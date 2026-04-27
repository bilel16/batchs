/**
 * Batch Assignment Result - Matches Backend API Response
 * 
 * Used by both Profile and Pack Assignment APIs
 * HTTP Status:
 * - 200 → full success
 * - 207 → partial success
 * - 400 → all failed
 */
export interface BatchAssignmentResult {
  /** Array of successfully processed identifiers (profile codes or pack codes) */
  successful: string[];
  
  /** Map of failed identifiers to error messages */
  failed: Record<string, string>;
  
  /** Number of successfully processed items */
  successCount: number;
  
  /** Number of failed items */
  failureCount: number;
}

/**
 * Helper to check if result is partial success
 */
export function isPartialSuccess(result: BatchAssignmentResult): boolean {
  return result.successCount > 0 && result.failureCount > 0;
}

/**
 * Helper to check if result is complete success
 */
export function isCompleteSuccess(result: BatchAssignmentResult): boolean {
  return result.successCount > 0 && result.failureCount === 0;
}

/**
 * Helper to check if result is complete failure
 */
export function isCompleteFailure(result: BatchAssignmentResult): boolean {
  return result.successCount === 0 && result.failureCount > 0;
}

/**
 * Helper to get failed items as array
 */
export function getFailedItemsArray(result: BatchAssignmentResult): Array<{ identifier: string; error: string }> {
  if (!result.failed || typeof result.failed !== 'object') {
    return [];
  }
  return Object.entries(result.failed).map(([identifier, error]) => ({
    identifier,
    error
  }));
}
