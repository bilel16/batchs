/**
 * @fileoverview HR Personnel Models
 * 
 * Data models and interfaces for HR Personnel management.
 * Based on the external HR system API endpoints.
 * 
 * @author BNA HABIL Development Team
 * @version 1.0.0
 * @since 2026-01-29
 */

/**
 * HR Personnel entity from external HR system
 */
export interface HrPersonnel {
  /** Employee ID/Matricule - Primary identifier */
  matcle: string;
  /** First name */
  prenom: string;
  /** Last name (usage name) */
  nomuse: string;
  /** National ID (CIN) */
  cin: string;
  /** Job ID */
  idjb00: string;
  /** Job title/label */
  lbjblg: string;
  /** Department/Unit label */
  lboulg: string;
  /** Organizational unit ID */
  idou00: string;
}

/**
 * Filter parameters for HR Personnel search
 */
export interface HrPersonnelFilterParams {
  /** Page number (0-indexed) */
  page: number;
  /** Page size */
  size: number;
  /** General search across matcle, prenom, nomuse, and full name */
  search?: string;
  /** Exact CIN filter */
  cin?: string;
  /** Matricule filter (partial for /filter endpoint, exact for /page) */
  matcle?: string;
  /** First name filter (partial match) */
  prenom?: string;
  /** Last name filter (partial match) */
  nomuse?: string;
  /** Sort field */
  sortBy: string;
  /** Sort direction */
  sortDirection: 'ASC' | 'DESC';
}

/**
 * Pageable information from API response
 */
export interface HrPageable {
  pageNumber: number;
  pageSize: number;
  sort: {
    sorted: boolean;
    direction: string;
    property: string;
  };
}

/**
 * Paginated response for HR Personnel
 */
export interface HrPersonnelPageResponse {
  content: HrPersonnel[];
  pageable: HrPageable;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
  numberOfElements: number;
  empty: boolean;
}

/**
 * Default filter parameters
 */
export const DEFAULT_HR_FILTER_PARAMS: HrPersonnelFilterParams = {
  page: 0,
  size: 20,
  sortBy: 'matcle',
  sortDirection: 'ASC'
};

/**
 * Sort options for HR Personnel
 */
export const HR_SORT_OPTIONS = [
  { label: 'Matricule', value: 'matcle' },
  { label: 'Prénom', value: 'prenom' },
  { label: 'Nom', value: 'nomuse' },
  { label: 'CIN', value: 'cin' },
  { label: 'Poste', value: 'lbjblg' },
  { label: 'Structure', value: 'lboulg' }
];

/**
 * Page size options
 */
export const HR_PAGE_SIZE_OPTIONS = [10, 20, 50, 100];
