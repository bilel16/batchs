import { OperationResult } from "./OperationResult";

export interface ProfileUpdateResult {
  success: boolean;
  globalError?: string;
  results: OperationResult[];
  summary: {
    ADD?: number;
    REVOKE?: number;
    UPDATE?: number;
  };
}