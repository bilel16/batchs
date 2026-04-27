import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import { Observable } from "rxjs";
import { map, tap } from "rxjs/operators";
import { environment } from '../../../../environments/environment';
import { ResponseSAGA } from '../../models/response-saga.interface';
import { 
  AssignedPack, 
  UserPacksAssignmentRequest, 
  formatPackDateForApi 
} from '../../models/pack-assignment.model';
import { BatchAssignmentResult } from '../../models/batch-assignement-result';

const API_BASE = environment.apiURL;
const API_URL = `${API_BASE}utilisateur-pack`;

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};


export interface UtilisateurPack {
  matUtilUtl: string;
  codPackPack: string;
  boolEtat?: number;
  // Add other UtilisateurPack properties as needed
}

// Re-export types for convenience
export type { AssignedPack, UserPacksAssignmentRequest } from '../../models/pack-assignment.model';
export type { BatchAssignmentResult } from '../../models/batch-assignement-result';

@Injectable({
  providedIn: "root",
})
export class UtilisateurPackService {
  constructor(private http: HttpClient) {}

  /**
   * Get all packs assigned to a user by matricule
   * GET /api/utilisateur-pack/by-matricule/{matricule}
   */
  getPacksByMatricule(matricule: string): Observable<UtilisateurPack[]> {
    return this.http
      .get<ResponseSAGA<UtilisateurPack[]>>(
        `${API_URL}/by-matricule/${encodeURIComponent(matricule)}`,
        httpOptions
      )
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data || [];
          }
          return [];
        }),
        tap((packs) =>
          console.log("User packs for", matricule, ":", packs.length)
        )
      );
  }
  /**
   * Assign multiple packs to a user and revoke specified packs
   * POST /api/utilisateur-pack/assign-multiple-packs
   *
   * ⚠️ IMPORTANT: Do NOT send profile codes - backend handles profile assignment automatically
   * Backend sets boolCustomProfil = 0 for all profiles assigned via packs
   * 
   * HTTP Status:
   * - 200 → full success
   * - 207 → partial success  
   * - 400 → all failed
   */
  assignMultiplePacks(
    request: UserPacksAssignmentRequest
  ): Observable<BatchAssignmentResult> {
    return this.http
      .post<BatchAssignmentResult>(
        `${API_URL}/assign-multiple-packs`,
        request,
        { ...httpOptions, observe: 'response' }
      )
      .pipe(
        map((response: HttpResponse<BatchAssignmentResult>) => {
          const result = response.body as BatchAssignmentResult;
          // Log HTTP status for debugging (200 = full success, 207 = partial)
          console.log(`Pack assignment response status: ${response.status}`);
          return result;
        }),
        tap((result) =>
          console.log(
            "Batch pack assignment result:",
            result.successCount,
            "successful,",
            result.failureCount,
            "failed"
          )
        )
      );
  }
}
