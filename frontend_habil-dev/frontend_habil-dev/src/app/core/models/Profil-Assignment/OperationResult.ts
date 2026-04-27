import { ProfileOperation } from "./ProfileOperation";

export interface OperationResult {
  operation: ProfileOperation;
  success: boolean;
  error?: string;
}