/**
 * Profile Assignment Models - Matches Backend DTO Contracts
 * 
 * Used for Custom Profile Assignment (Manager-Driven)
 * Endpoint: POST /api/utilisateur-profil/assign-multiple-profiles
 * Backend sets boolCustomProfil = 1
 */

/**
 * Represents a profile to be assigned to a user
 */
export interface AssignedProfile {
  profileCode: string;
  dateDebut?: string | Date | null;
  dateFin?: string | Date | null;
  etat?: number; // 1 = active (default)
}

/**
 * Request payload for assigning/revoking custom profiles
 */
export interface UserProfilesAssignmentRequest {
  userMatricule: string;
  appCode: string;
  assignedProfiles?: AssignedProfile[] | null;
  revokedProfiles?: string[] | null;
}

/**
 * Helper function to format date for API
 * @param date Date to format
 * @returns ISO string or null
 */
export function formatDateForApi(date?: Date | string | null): string | null {
  if (!date) return null;
  if (typeof date === 'string') return date;
  return date.toISOString();
}

/**
 * Helper function to create an AssignedProfile object
 * @param profileCode Profile code
 * @param dateDebut Optional start date
 * @param dateFin Optional end date
 * @param etat Optional status (default: 1)
 */
export function createAssignedProfile(
  profileCode: string,
  dateDebut?: Date | string | null,
  dateFin?: Date | string | null,
  etat: number = 1
): AssignedProfile {
  return {
    profileCode,
    dateDebut: formatDateForApi(dateDebut),
    dateFin: formatDateForApi(dateFin),
    etat
  };
}
