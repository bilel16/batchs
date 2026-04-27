export type Instrument = 'CHEQUE' | 'EFFET' | 'PRELEVEMENT';
export type Phase = 'INSERTION' | 'POSITION';
export type BatchStatus =
  | 'STARTED'
  | 'RUNNING'
  | 'COMPLETED'
  | 'COMPLETED_WITH_ERRORS'
  | 'FAILED'
  | 'STOPPED';
export type AgenceEtat = 'EN_ATTENTE' | 'EN_COURS' | 'TERMINE' | 'ERREUR';

export interface BatchRequest {
  instrument: Instrument;
  phase: Phase;
  dryRun: boolean;
  notifyEmail: boolean;
  retryMax: number;
}

export interface AgenceProgress {
  executionId: string;
  structure: string;
  etat: AgenceEtat;
  dateComptable: string;
  instrument: string;
  phase: string;
  progressPercent: number;
  errorMessage?: string;
}

export interface BatchStatusMessage {
  executionId: string;
  status: BatchStatus;
  instrument: string;
  phase: string;
  globalProgress: number;
  totalAgences: number;
  doneAgences: number;
  errorAgences: number;
  message?: string;
}

export interface BatchExecutionDto {
  executionId: string;
  instrument: string;
  phase: string;
  status: BatchStatus;
  launchedBy: string;
  startTime: string;
  endTime?: string;
  dryRun: boolean;
  totalAgences: number;
  doneAgences: number;
  errorAgences: number;
  endMessage?: string;
  agenceDetails: AgenceProgress[];
}

export interface DashboardStats {
  totalCheque: number;
  totalEffet: number;
  totalPrelevement: number;
  healthScore: number;
  lastExecution: string;
  lastExecutionStatus: string;
  activeJobs: number;
  recentExecutions: BatchExecutionDto[];
  volumeTrend: DailyVolume[];
}

export interface DailyVolume {
  date: string;
  cheque: number;
  effet: number;
  prelevement: number;
}

export interface ConsultationResult {
  structure: string;
  libStructure: string;
  instrument: string;
  sens: string;
  valeur: string;
  dateOperation: string;
  nombreTotal: number;
  montantTotal: number;
  nombreIntra: number;
  montantIntra: number;
  nombreInter: number;
  montantInter: number;
}

export interface AuditLog {
  id: string;
  username: string;
  action: string;
  detail: string;
  ip: string;
  timestamp: string;
  result: string;
}
