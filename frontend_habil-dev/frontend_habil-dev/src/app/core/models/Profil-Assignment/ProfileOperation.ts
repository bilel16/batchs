import { OperationType } from "./profile-operations.model";

export interface ProfileOperation {
  type: OperationType;
  profileCode: string;
  dateDebut?: Date;
  dateFin?: Date;
  etat?: number;
}