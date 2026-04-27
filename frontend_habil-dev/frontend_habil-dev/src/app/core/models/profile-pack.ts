/**
 * @fileoverview Profile Pack Models
 * 
 * Defines the data structures for profile packs (bundles of profiles)
 * that can be assigned to users as a group.
 * 
 * @author BNA HABIL Development Team
 * @version 1.0.0
 * @since 2025-12-04
 */

import { UnifiedProfile } from '../../features/administration/utilisateurprofil/utilisateurprofil.component';

/**
 * Pack Category - Groups packs by functional area
 */
export enum PackCategory {
  ADMINISTRATION = 'ADMINISTRATION',
  COMMERCIAL = 'COMMERCIAL',
  FINANCE = 'FINANCE',
  OPERATIONS = 'OPERATIONS',
  MANAGEMENT = 'MANAGEMENT',
  CUSTOM = 'CUSTOM'
}

/**
 * Pack Status - Indicates pack availability
 */
export enum PackStatus {
  ACTIVE = 'ACTIVE',
  INACTIVE = 'INACTIVE',
  DRAFT = 'DRAFT'
}

/**
 * Profile Pack Interface
 * Represents a bundle of profiles that can be assigned together
 */
export interface ProfilePack {
  /** Unique pack identifier */
  id: string;
  
  /** Pack code (e.g., "PACK_ADMIN_001") */
  code: string;
  
  /** Pack display name */
  name: string;
  
  /** Detailed description of the pack's purpose */
  description: string;
  
  /** Category for grouping packs */
  category: PackCategory;
  
  /** Pack status (active/inactive) */
  status: PackStatus;
  
  /** Array of profile codes included in this pack */
  profileCodes: string[];
  
  /** Full profile objects (populated from profileCodes) */
  profiles?: UnifiedProfile[];
  
  /** Icon to represent the pack visually */
  icon?: string;
  
  /** Color theme for the pack card */
  color?: string;
  
  /** Creation date */
  createdDate?: string;
  
  /** Last modified date */
  modifiedDate?: string;
  
  /** Creator/owner of the pack */
  createdBy?: string;
  
  /** Number of users currently assigned this pack */
  assignedUserCount?: number;
  
  /** Tags for additional categorization */
  tags?: string[];
}

/**
 * Pack Assignment Request
 * Used when assigning a pack to a user
 */
export interface PackAssignmentRequest {
  /** User matricule */
  userMatricule: string;
  
  /** Application code */
  appCode: string;
  
  /** Pack ID to assign */
  packId: string;
  
  /** Start date for all profiles in the pack */
  dateDebut: string;
  
  /** End date for all profiles in the pack */
  dateFin?: string;
}

/**
 * Pack Assignment Result
 * Response from pack assignment operation
 */
export interface PackAssignmentResult {
  /** Success flag */
  success: boolean;
  
  /** Pack that was assigned */
  pack: ProfilePack;
  
  /** Number of profiles successfully assigned */
  profilesAssigned: number;
  
  /** List of profiles that failed to assign */
  failedProfiles?: string[];
  
  /** Error message if any */
  message?: string;
}

/**
 * Pack Filter Options
 * Used for filtering packs in the UI
 */
export interface PackFilters {
  /** Search term for pack name/description */
  searchTerm: string;
  
  /** Filter by category */
  selectedCategory: PackCategory | null;
  
  /** Filter by status */
  selectedStatus: PackStatus | null;
  
  /** Minimum number of profiles in pack */
  minProfiles?: number;
  
  /** Maximum number of profiles in pack */
  maxProfiles?: number;
}
