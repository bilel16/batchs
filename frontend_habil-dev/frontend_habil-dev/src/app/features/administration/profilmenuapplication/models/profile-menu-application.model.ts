/**
 * ProfilMenuApplication Model
 * Represents the association between Profile, Menu, Application, and Structure
 */
export interface ProfilMenuApplication {
  codAppApp: string;      // Application code
  codMenuMenu: string;    // Menu code
  codPflPfl: string;      // Profile code
  codTstrcTstrc: string;  // Structure code
  boolEtatPma?: any;   // Status (0=inactive, 1=active)
}
