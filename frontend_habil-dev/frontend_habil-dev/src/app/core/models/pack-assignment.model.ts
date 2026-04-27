/**
 * Pack Assignment Models - Matches Backend DTO Contracts
 * 
 * Used for Pack Assignment (System-Driven)
 * Endpoint: POST /api/utilisateur-pack/assign-multiple-packs
 * Backend sets boolCustomProfil = 0
 * 
 * ⚠️ IMPORTANT: Frontend must NEVER send profile codes when assigning packs
 * Backend automatically assigns/removes all profiles inside the pack
 */

/**
 * Represents a pack to be assigned to a user
 */
export interface AssignedPack {
  packCode: string;
  dateDebut?: string | Date | null;
  dateFin?: string | Date | null;
  etat?: number; // 1 = active (default)
}

/**
 * Request payload for assigning/revoking packs
 * 
 * ⚠️ Do NOT include profile codes - backend handles profile assignment automatically
 */
export interface UserPacksAssignmentRequest {
  userMatricule: string;
  assignedPacks?: AssignedPack[] | null;
  revokedPacks?: string[] | null;
}

/**
 * Helper function to format date for API
 * @param date Date to format
 * @returns ISO string or null
 */
export function formatPackDateForApi(date?: Date | string | null): string | null {
  if (!date) return null;
  if (typeof date === 'string') return date;
  return date.toISOString();
}

/**
 * Helper function to create an AssignedPack object
 * @param packCode Pack code
 * @param dateDebut Optional start date
 * @param dateFin Optional end date
 * @param etat Optional status (default: 1)
 */
export function createAssignedPack(
  packCode: string,
  dateDebut?: Date | string | null,
  dateFin?: Date | string | null,
  etat: number = 1
): AssignedPack {
  return {
    packCode,
    dateDebut: formatPackDateForApi(dateDebut),
    dateFin: formatPackDateForApi(dateFin),
    etat
  };
}
