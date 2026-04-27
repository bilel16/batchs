// models/pack-profile.model.ts
/**
 * PackProfile Model
 * Represents the association between Pack and Profile
 */
export interface PackProfile {
  codPackPack: string;    // Pack code
  codPflPfl: string;      // Profile code
  boolEtat?: any;      // Status (0=inactive, 1=active)
  codTstrcTstrc?: string; // Structure code (optional)

  // Additional fields for display (populated from joins)
  libPackPack?: string;   // Pack label
  libpflpfl?: string;     // Profile label
}
