// models/sync-result.model.ts
/**
 * Sync Result Model
 * Result of pack profile synchronization
 */
export interface SyncResult {
  usersUpdated: number;
  profilesAdded: number;
  profilesRemoved: number;
}
