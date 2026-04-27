/**
 * Batch Operation Result
 * Contains results of batch operations with success/failure tracking
 */
export interface BatchOperationResult<T> {
  successful: T[];
  failed: BatchError[];
  mode: 'ALL_OR_NOTHING' | 'BEST_EFFORT';
}

export interface BatchError {
  index: number;
  identifier: string;
  error: string;
}