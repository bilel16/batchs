
/**
 * Enum for profile operation types
 */
export enum OperationType {
  ADD = 'ADD',
  REVOKE = 'REVOKE',
  UPDATE = 'UPDATE'
}

/**
 * Interface for a single profile operation
 */
export interface ProfileOperation {
  /** Type of operation to perform */
  type: OperationType;
  
  /** Profile code identifier */
  profileCode: string;
  
  /** Start date for the profile assignment */
  dateDebut: Date | string;
  
  /** End date for the profile assignment (optional) */
  dateFin?: Date | string | null;
  
  /** Profile status (0 = inactive, 1 = active) */
  etat: number;
}

/**
 * Request payload for batch profile updates
 */
export interface UserProfileUpdateRequest {
  /** User matricule */
  userMatricule: string;
  
  /** Application code */
  appCode: string;
  
  /** List of profile operations to perform */
  operations: ProfileOperation[];
  
  /** List of profile codes to revoke (optional - for backward compatibility) */
  revokedProfiles?: string[];
}

/**
 * Result of a batch profile update operation
 */
export interface ProfileUpdateResult {
  /** Whether the operation was successful */
  success: boolean;
  
  /** Number of profiles added */
  addedCount: number;
  
  /** Number of profiles revoked */
  revokedCount: number;
  
  /** Number of profiles updated */
  updatedCount: number;
  
  /** Global error message (if any) */
  globalError?: string;
  
  /** List of successful operations */
  successfulOperations?: ProfileOperationResult[];
  
  /** List of failed operations */
  failedOperations?: ProfileOperationResult[];
}

/**
 * Result of a single profile operation
 */
export interface ProfileOperationResult {
  /** Profile code */
  profileCode: string;
  
  /** Operation type performed */
  type: OperationType;
  
  /** Whether the operation was successful */
  success: boolean;
  
  /** Error message (if operation failed) */
  errorMessage?: string;
}
