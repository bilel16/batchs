// services/pack-profile.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment';

import {
  PackProfile,
  BatchOperationResult,
  SyncResult
} from '../models';

interface ResponseSAGA<T = any> {
  code: number;
  message: string;
  data: T;
}

const API_URL = environment.apiURL || 'http://localhost:8083/api/';

@Injectable({
  providedIn: 'root'
})
export class PackProfileService {

  private readonly baseUrl = `${API_URL}pack-profil`;

  constructor(private http: HttpClient) {}

  /**
   * Get all pack profiles
   * GET /api/pack-profil
   */
  getAllPackProfiles(): Observable<any> {
    return this.http.get<ResponseSAGA<PackProfile[]>>(this.baseUrl).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Get pack profiles by pack code
   * GET /api/pack-profil/by-pack/{codPackPack}
   */
  getPackProfilesByPack(codPackPack: string): Observable<any> {
    return this.http.get<ResponseSAGA<PackProfile[]>>(
      `${this.baseUrl}/by-pack/${codPackPack}`
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Get pack profiles by profile code
   * GET /api/pack-profil/by-profile/{codPflPfl}
   */
  getPackProfilesByProfile(codPflPfl: string): Observable<any> {
    return this.http.get<ResponseSAGA<PackProfile[]>>(
      `${this.baseUrl}/by-profile/${codPflPfl}`
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Get active pack profiles by pack
   * GET /api/pack-profil/active/{codPackPack}
   */
  getActivePackProfilesByPack(codPackPack: string): Observable<any> {
    return this.http.get<ResponseSAGA<PackProfile[]>>(
      `${this.baseUrl}/active/${codPackPack}`
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Get pack profiles by structure type
   * GET /api/pack-profil/by-structure/{codPackPack}/{codTstrcTstrc}
   */
  getPackProfilesByStructure(codPackPack: string, codTstrcTstrc: string): Observable<any> {
    return this.http.get<ResponseSAGA<PackProfile[]>>(
      `${this.baseUrl}/by-structure/${codPackPack}/${codTstrcTstrc}`
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Create single pack profile
   * POST /api/pack-profil
   */
  createPackProfile(packProfile: PackProfile): Observable<any> {
    return this.http.post<ResponseSAGA<PackProfile>>(
      this.baseUrl,
      packProfile
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Batch create pack profiles
   * POST /api/pack-profil/batch
   */
  createBatchPackProfiles(
    packProfiles: PackProfile[],
    mode: 'ALL_OR_NOTHING' | 'BEST_EFFORT' = 'ALL_OR_NOTHING'
  ): Observable<any> {
    const params = new HttpParams().set('mode', mode);

    return this.http.post<ResponseSAGA<BatchOperationResult<PackProfile>>>(
      `${this.baseUrl}/batch`,
      packProfiles,
      { params }
    ).pipe(
      map(response => {
        const result = response.data;

        if (result.failed && result.failed.length > 0 && mode === 'ALL_OR_NOTHING') {
          return {
            code: 1,
            message: `${result.failed.length} items failed to create`,
            data: result
          };
        }

        return {
          code: response.code,
          message: response.message,
          data: result
        };
      })
    );
  }

  /**
   * Update pack profile
   * PUT /api/pack-profil/{codPackPack}/{codPflPfl}
   */
  updatePackProfile(codPackPack: string, codPflPfl: string, packProfile: PackProfile): Observable<any> {
    return this.http.put<ResponseSAGA<PackProfile>>(
      `${this.baseUrl}/${codPackPack}/${codPflPfl}`,
      packProfile
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Update pack profile status
   * PATCH /api/pack-profil/{codPackPack}/{codPflPfl}/status
   */
  updatePackProfileStatus(codPackPack: string, codPflPfl: string, boolEtat: number): Observable<any> {
    const params = new HttpParams().set('boolEtat', boolEtat.toString());

    return this.http.patch<ResponseSAGA<string>>(
      `${this.baseUrl}/${codPackPack}/${codPflPfl}/status`,
      null,
      { params }
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Activate pack profile
   * PUT /api/pack-profil/{codPackPack}/{codPflPfl}/activate
   */
  activatePackProfile(codPackPack: string, codPflPfl: string): Observable<any> {
    return this.http.put<ResponseSAGA<PackProfile>>(
      `${this.baseUrl}/${codPackPack}/${codPflPfl}/activate`,
      null
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Deactivate pack profile
   * PUT /api/pack-profil/{codPackPack}/{codPflPfl}/deactivate
   */
  deactivatePackProfile(codPackPack: string, codPflPfl: string): Observable<any> {
    return this.http.put<ResponseSAGA<PackProfile>>(
      `${this.baseUrl}/${codPackPack}/${codPflPfl}/deactivate`,
      null
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Delete pack profile
   * DELETE /api/pack-profil/{codPackPack}/{codPflPfl}
   */
  deletePackProfile(codPackPack: string, codPflPfl: string): Observable<any> {
    return this.http.delete<ResponseSAGA<string>>(
      `${this.baseUrl}/${codPackPack}/${codPflPfl}`
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Delete multiple profiles from pack
   * DELETE /api/pack-profil/batch/{codPackPack}
   */
  deleteMultipleFromPack(codPackPack: string, profilCodes: string[]): Observable<any> {
    return this.http.delete<ResponseSAGA<string>>(
      `${this.baseUrl}/batch/${codPackPack}`,
      { body: profilCodes }
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Sync pack profiles
   * PUT /api/pack-profil/{codPackPack}/sync
   */
  syncPackProfiles(codPackPack: string): Observable<any> {
    return this.http.put<ResponseSAGA<SyncResult>>(
      `${this.baseUrl}/${codPackPack}/sync`,
      null
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Count profiles in pack
   * GET /api/pack-profil/count/{codPackPack}
   */
  countProfilesInPack(codPackPack: string): Observable<any> {
    return this.http.get<ResponseSAGA<{ codPackPack: string; count: number }>>(
      `${this.baseUrl}/count/${codPackPack}`
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Check if profile exists in pack
   * GET /api/pack-profil/exists/{codPackPack}/{codPflPfl}
   */
  checkProfileExistsInPack(codPackPack: string, codPflPfl: string): Observable<any> {
    return this.http.get<ResponseSAGA<{ exists: boolean }>>(
      `${this.baseUrl}/exists/${codPackPack}/${codPflPfl}`
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  getPackConflicts(codPackPack: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/${codPackPack}/conflicts`);
  }
}
