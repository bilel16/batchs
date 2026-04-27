export interface UnifiedProfile {
  // Core profile data
  codPflPfl: string;
  libpflpfl: string;
  libelle?: string;

  // Profile details
  codCatpPfl?: string;
  codNivhPfl?: string;
  libhdebpfl?: string;
  libhfinpfl?: string;
  boolJouvPfl?: string;
  boolEtatPfl?: string;
  codAppApp?: string;

  // Assignment data (only for assigned profiles)
  numMatrUser?: string;
  datFadhUtpr?: string;
  datdadhutpr?: string;
  boolEtatUtpr?: number;

  // Computed properties
  assignmentStatus:any;
  isActive: boolean;

  // Display properties
  displayName: string;
  categoryName: string;
  accessHours: string;
  assignmentDates?: string;
}
