/**
 * @fileoverview Interim (Delegation) Management — Data Models
 */

export interface Interim {
  id?: number;
  matriculeSource: number;
  matriculeCible: number;
  codStrcOrigine: number;
  codStrcDestination: number;
  dateDebutInterim: string;     // "yyyy-MM-dd"
  dateFinInterim: string;       // "yyyy-MM-dd"
  etat?: EtatInterim;
  dateOperation?: string;
  nomPrenomSource?: string;
  nomPrenomCible?: string;
}

export enum EtatInterim {
  EN_ATTENTE = 'EN_ATTENTE',
  ACTIF = 'ACTIF',
  TERMINE = 'TERMINE',
  ANNULE = 'ANNULE'
}

export interface InterimStats {
  totalEnAttente: number;
  totalActif: number;
  totalTermine: number;
  totalAnnule: number;
  total: number;
}

export interface InterimProfilGranted {
  id: number;
  interimId: number;
  numMatrUser: string;
  codPflPfl: string;
  previouslyExisted: number;
  previousEtat: number | null;
  dateGranted: string;
}

export interface InterimProfilBackup {
  id: number;
  interimId: number;
  numMatrUser: string;
  codPflPfl: string;
  boolEtatUtpr: number;
  datDadhUtpr: string;
  datFadhUtpr: string;
  boolCustomProfil: number;
  dateBackup: string;
}

export interface InterimSearchParams {
  matriculeSource?: number;
  matriculeCible?: number;
  etat?: EtatInterim;
  codStrc?: number;
  dateDebut?: string;
  dateFin?: string;
}

/** Helper: state display metadata */
export const ETAT_INTERIM_META: Record<EtatInterim, { label: string; icon: string; severity: string; bgColor: string; textColor: string; borderColor: string }> = {
  [EtatInterim.EN_ATTENTE]: {
    label: 'En Attente',
    icon: 'pi pi-clock',
    severity: 'warn',
    bgColor: '#FFF3E0',
    textColor: '#E65100',
    borderColor: '#FFB74D',
  },
  [EtatInterim.ACTIF]: {
    label: 'Actif',
    icon: 'pi pi-check-circle',
    severity: 'success',
    bgColor: '#E8F5E9',
    textColor: '#2E7D32',
    borderColor: '#81C784',
  },
  [EtatInterim.TERMINE]: {
    label: 'Terminé',
    icon: 'pi pi-history',
    severity: 'secondary',
    bgColor: '#F5F5F5',
    textColor: '#616161',
    borderColor: '#BDBDBD',
  },
  [EtatInterim.ANNULE]: {
    label: 'Annulé',
    icon: 'pi pi-times-circle',
    severity: 'danger',
    bgColor: '#FFEBEE',
    textColor: '#C62828',
    borderColor: '#EF9A9A',
  },
};
