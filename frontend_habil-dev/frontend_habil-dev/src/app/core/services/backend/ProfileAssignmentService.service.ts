import { Injectable } from "@angular/core";
import { HttpClient, HttpParams, HttpResponse } from "@angular/common/http";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { environment } from "../../../../environments/environment";
import { 
  AssignedProfile,
  UserProfilesAssignmentRequest 
} from "../../models/profile-assignment.model";
import { BatchAssignmentResult } from "../../models/batch-assignement-result";
import { 
  UserProfileUpdateRequest, 
  ProfileUpdateResult 
} from "../../models/profile-operation";

const API_BASE = environment.apiURL;

export interface ProfileAssignmentRequest {
  managerMatricule: string;
  userMatricule: string;
  profileCode: string;
  appCode: string;
}

export interface BulkAssignmentRequest {
  managerMatricule: string;
  userMatricules: string[];
  profileCode: string;
  appCode: string;
}

// Re-export types for convenience
export type { AssignedProfile, UserProfilesAssignmentRequest } from "../../models/profile-assignment.model";
export type { BatchAssignmentResult } from "../../models/batch-assignement-result";

export interface AssignmentStatistics {
  totalManagedUsers: number;
  totalStructures: number;
  totalActiveProfiles: number;
  profileDistribution: { [key: string]: number };
}

@Injectable({
  providedIn: "root",
})
export class ProfileAssignmentService {
  constructor(private http: HttpClient) {}

  // Get managed users for a manager
  getManagedUsers(managerMatricule: string): Observable<string[]> {
    const params = new HttpParams().set('managerMatricule', managerMatricule);
    return this.http.get<string[]>(`${API_BASE}profiles/managed-users`, { params });
  }

  // Get managed users with details
  getManagedUsersWithDetails(managerMatricule: string): Observable<any[]> {
    const params = new HttpParams().set('managerMatricule', managerMatricule);
    return this.http.get<any[]>(`${API_BASE}profiles/managed-users/details`, { params });
  }

  // Get assignable profiles for a specific user
  getAssignableProfiles(managerMatricule: string, userMatricule: string, appCode: string): Observable<any[]> {
    const params = new HttpParams()
      .set('managerMatricule', managerMatricule)
      .set('userMatricule', userMatricule)
      .set('appCode', appCode);
    return this.http.get<any[]>(`${API_BASE}profiles/assignable`, { params });
  }

  // Check if manager can assign a profile
  canAssignProfile(managerMatricule: string, profileCode: string, appCode: string): Observable<boolean> {
    const params = new HttpParams()
      .set('managerMatricule', managerMatricule)
      .set('profileCode', profileCode)
      .set('appCode', appCode);
    return this.http.get<boolean>(`${API_BASE}profiles/can-assign`, { params });
  }

  // Get user's current profiles
  getUserProfiles(matricule: string): Observable<any[]> {
    return this.http.get<any[]>(`${API_BASE}profiles/user/${matricule}`);
  }

  // Assign profile with hierarchy validation
  assignProfile(request: ProfileAssignmentRequest): Observable<any> {
    return this.http.post(`${API_BASE}profiles/assign`, request);
  }

  // Bulk assign profiles
  bulkAssignProfiles(request: BulkAssignmentRequest): Observable<BatchAssignmentResult> {
    return this.http.post<BatchAssignmentResult>(`${API_BASE}profiles/bulk-assign`, request);
  }

  // Remove profile assignment
  removeProfile(managerMatricule: string, userMatricule: string, profileCode: string): Observable<any> {
    const params = new HttpParams()
      .set('managerMatricule', managerMatricule)
      .set('userMatricule', userMatricule)
      .set('profileCode', profileCode);
    return this.http.delete(`${API_BASE}profiles/remove`, { params });
  }

  // Get assignment statistics
  getStatistics(managerMatricule: string): Observable<AssignmentStatistics> {
    const params = new HttpParams().set('managerMatricule', managerMatricule);
    return this.http.get<AssignmentStatistics>(`${API_BASE}profiles/statistics`, { params });
  }

  // Get manager-specific profiles
  getManagerProfiles(codAppApp: string): Observable<any> {
    return this.http.get<any>(`${API_BASE}getProfilListForManager/${codAppApp}`);
  }
  // Get user-specific profiles
  getUserSpecificProfiles(codAppApp: string, targetUserMat: string): Observable<any> {
    return this.http.get<any>(`${API_BASE}getProfilListForUser/${codAppApp}/${targetUserMat}`);
  }

  /**
   * Assign multiple custom profiles to a user (Manager-Driven)
   * POST /api/utilisateur-profil/assign-multiple-profiles
   * 
   * ⚠️ IMPORTANT: Backend sets boolCustomProfil = 1 for these profiles
   * Only use for manual profile assignments by managers
   * 
   * HTTP Status:
   * - 200 → full success
   * - 207 → partial success
   * - 400 → all failed
   */
  assignMultipleProfiles(request: UserProfilesAssignmentRequest): Observable<BatchAssignmentResult> {
    return this.http
      .post<BatchAssignmentResult>(
        `${API_BASE}profiles/assign-multiple-profiles`,
        request,
        { observe: 'response' }
      )
      .pipe(
        map((response: HttpResponse<BatchAssignmentResult>) => {
          const result = response.body as BatchAssignmentResult;
          // Log HTTP status for debugging (200 = full success, 207 = partial)
          console.log(`Profile assignment response status: ${response.status}`);
          return result;
        })
      );
  }

  /**
   * Batch update user profiles (add, revoke, or update profiles)
   * Uses the new /batch-update endpoint
   * @param request The batch update request containing operations
   * @returns Observable of ProfileUpdateResult
   */
  batchUpdateProfiles(request: UserProfileUpdateRequest): Observable<ProfileUpdateResult> {
    return this.http.put<ProfileUpdateResult>(`${API_BASE}profiles/batch-update`, request);
  }

}