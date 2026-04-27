import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { map, tap } from "rxjs/operators";
import {environment} from '../../../../environments/environment';
import {ResponseSAGA} from '../../models/response-saga.interface';

const API_BASE = environment.apiURL;
const API_URL = `${API_BASE}pack-profil`;

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

export interface PackProfil {
  codPackPack: string;
  codPflPfl: string;
  boolEtat?: number;
  codTstrcTstrc?: string;
  // Add other PackProfil properties as needed
}

export interface BatchError {
  index: number;
  identifier: string;
  error: string;
}

export interface BatchOperationResult<T> {
  successful: T[];
  failed: BatchError[];
  mode: "ALL_OR_NOTHING" | "BEST_EFFORT";
}

export interface SyncResult {
  usersUpdated: number;
  profilesAdded: number;
  profilesRemoved: number;
}

@Injectable({
  providedIn: "root",
})
export class PackProfilService {
  constructor(private http: HttpClient) {}

  // ==================== CRUD OPERATIONS ====================

  /**
   * Create single PackProfil
   * POST /api/pack-profil
   */
  create(packProfil: PackProfil): Observable<PackProfil> {
    return this.http
      .post<ResponseSAGA<PackProfil>>(API_URL, packProfil, httpOptions)
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data;
          }
          return response as unknown as PackProfil;
        }),
        tap((pp) => console.log("Created PackProfil:", pp))
      );
  }

  /**
   * Create multiple PackProfils in batch
   * POST /api/pack-profil/batch
   */
  createBatch(
    packProfils: PackProfil[],
    mode: "ALL_OR_NOTHING" | "BEST_EFFORT" = "ALL_OR_NOTHING"
  ): Observable<BatchOperationResult<PackProfil>> {
    const params = new HttpParams().set("mode", mode);

    return this.http
      .post<ResponseSAGA<BatchOperationResult<PackProfil>>>(
        `${API_URL}/batch`,
        packProfils,
        { ...httpOptions, params }
      )
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data;
          }
          return response as unknown as BatchOperationResult<PackProfil>;
        }),
        tap((result) =>
          console.log(
            "Batch create result:",
            result.successful.length,
            "successful,",
            result.failed.length,
            "failed"
          )
        )
      );
  }

  /**
   * Get all PackProfils
   * GET /api/pack-profil
   */
  getAll(): Observable<PackProfil[]> {
    return this.http
      .get<ResponseSAGA<PackProfil[]>>(API_URL, httpOptions)
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data || [];
          }
          return [];
        })
      );
  }

  /**
   * Get PackProfils by pack
   * GET /api/pack-profil/by-pack/{codPackPack}
   */
  getByPack(codPackPack: string): Observable<PackProfil[]> {
    return this.http
      .get<ResponseSAGA<PackProfil[]>>(
        `${API_URL}/by-pack/${encodeURIComponent(codPackPack)}`,
        httpOptions
      )
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data || [];
          }
          return [];
        })
      );
  }

  /**
   * Get PackProfils by profile
   * GET /api/pack-profil/by-profile/{codPflPfl}
   */
  getByProfile(codPflPfl: string): Observable<PackProfil[]> {
    return this.http
      .get<ResponseSAGA<PackProfil[]>>(
        `${API_URL}/by-profile/${encodeURIComponent(codPflPfl)}`,
        httpOptions
      )
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data || [];
          }
          return [];
        })
      );
  }

  /**
   * Get active PackProfils by pack
   * GET /api/pack-profil/active/{codPackPack}
   */
  getActiveByPack(codPackPack: string): Observable<PackProfil[]> {
    return this.http
      .get<ResponseSAGA<PackProfil[]>>(
        `${API_URL}/active/${encodeURIComponent(codPackPack)}`,
        httpOptions
      )
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data || [];
          }
          return [];
        })
      );
  }

  /**
   * Get PackProfils by structure type
   * GET /api/pack-profil/by-structure/{codPackPack}/{codTstrcTstrc}
   */
  getByStructureType(
    codPackPack: string,
    codTstrcTstrc: string
  ): Observable<PackProfil[]> {
    return this.http
      .get<ResponseSAGA<PackProfil[]>>(
        `${API_URL}/by-structure/${encodeURIComponent(
          codPackPack
        )}/${encodeURIComponent(codTstrcTstrc)}`,
        httpOptions
      )
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data || [];
          }
          return [];
        })
      );
  }

  /**
   * Update PackProfil
   * PUT /api/pack-profil/{codPackPack}/{codPflPfl}
   */
  update(
    codPackPack: string,
    codPflPfl: string,
    packProfil: PackProfil
  ): Observable<PackProfil> {
    return this.http
      .put<ResponseSAGA<PackProfil>>(
        `${API_URL}/${encodeURIComponent(codPackPack)}/${encodeURIComponent(
          codPflPfl
        )}`,
        packProfil,
        httpOptions
      )
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data;
          }
          return response as unknown as PackProfil;
        }),
        tap((pp) => console.log("Updated PackProfil:", pp))
      );
  }

  /**
   * Update PackProfil status
   * PATCH /api/pack-profil/{codPackPack}/{codPflPfl}/status
   */
  // updateStatus(
  //   codPackPack: string,
  //   codPflPfl: string,
  //   boolEtat: number
  // ): Observable<string> {
  //   const params = new HttpParams().set("boolEtat", boolEtat.toString());
  //
  //   return this.http
  //     .patch<ResponseSAGA<string>>(
  //       `${API_URL}/${encodeURIComponent(codPackPack)}/${encodeURIComponent(
  //         codPflPfl
  //       )}/status`,
  //       null,
  //       { ...httpOptions, params }
  //     )
  //     .pipe(
  //       map((response) => {
  //         if ("data" in response) {
  //           return response.data;
  //         }
  //         return response.message;
  //       })
  //     );
  // }

  /**
   * Deactivate PackProfil
   * PUT /api/pack-profil/{codPackPack}/{codPflPfl}/deactivate
   */
  deactivate(codPackPack: string, codPflPfl: string): Observable<PackProfil> {
    return this.http
      .put<ResponseSAGA<PackProfil>>(
        `${API_URL}/${encodeURIComponent(codPackPack)}/${encodeURIComponent(
          codPflPfl
        )}/deactivate`,
        null,
        httpOptions
      )
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data;
          }
          return response as unknown as PackProfil;
        }),
        tap(() => console.log("Deactivated PackProfil"))
      );
  }

  /**
   * Activate PackProfil
   * PUT /api/pack-profil/{codPackPack}/{codPflPfl}/activate
   */
  activate(codPackPack: string, codPflPfl: string): Observable<PackProfil> {
    return this.http
      .put<ResponseSAGA<PackProfil>>(
        `${API_URL}/${encodeURIComponent(codPackPack)}/${encodeURIComponent(
          codPflPfl
        )}/activate`,
        null,
        httpOptions
      )
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data;
          }
          return response as unknown as PackProfil;
        }),
        tap(() => console.log("Activated PackProfil"))
      );
  }

  /**
   * Delete PackProfil
   * DELETE /api/pack-profil/{codPackPack}/{codPflPfl}
   */
  delete(codPackPack: string, codPflPfl: string): Observable<void> {
    return this.http
      .delete<ResponseSAGA<string>>(
        `${API_URL}/${encodeURIComponent(codPackPack)}/${encodeURIComponent(
          codPflPfl
        )}`,
        httpOptions
      )
      .pipe(
        map(() => {
          console.log("Deleted PackProfil:", codPackPack, codPflPfl);
          return;
        })
      );
  }

  /**
   * Delete multiple profiles from pack
   * DELETE /api/pack-profil/batch/{codPackPack}
   */
  // deleteMultipleFromPack(
  //   codPackPack: string,
  //   profilCodes: string[]
  // ): Observable<string> {
  //   return this.http
  //     .delete<ResponseSAGA<string>>(
  //       `${API_URL}/batch/${encodeURIComponent(codPackPack)}`,
  //       {
  //         ...httpOptions,
  //         body: profilCodes,
  //       }
  //     )
  //     .pipe(
  //       map((response) => {
  //         if ("data" in response) {
  //           return response.data;
  //         }
  //         return response.message;
  //       }),
  //       tap(() =>
  //         console.log("Deleted", profilCodes.length, "profiles from pack")
  //       )
  //     );
  // }

  // ==================== UTILITY OPERATIONS ====================

  /**
   * Sync pack profiles
   * PUT /api/pack-profil/{codPackPack}/sync
   */
  syncPackProfiles(codPackPack: string): Observable<SyncResult> {
    return this.http
      .put<ResponseSAGA<SyncResult>>(
        `${API_URL}/${encodeURIComponent(codPackPack)}/sync`,
        null,
        httpOptions
      )
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data;
          }
          return response as unknown as SyncResult;
        }),
        tap((result) => console.log("Sync result:", result))
      );
  }

  /**
   * Count profiles in pack
   * GET /api/pack-profil/count/{codPackPack}
   */
  countProfilesInPack(codPackPack: string): Observable<number> {
    return this.http
      .get<ResponseSAGA<{ codPackPack: string; count: number }>>(
        `${API_URL}/count/${encodeURIComponent(codPackPack)}`,
        httpOptions
      )
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data.count;
          }
          return 0;
        })
      );
  }

  /**
   * Check if profile exists in pack
   * GET /api/pack-profil/exists/{codPackPack}/{codPflPfl}
   */
  checkExists(codPackPack: string, codPflPfl: string): Observable<boolean> {
    return this.http
      .get<ResponseSAGA<{ exists: boolean }>>(
        `${API_URL}/exists/${encodeURIComponent(
          codPackPack
        )}/${encodeURIComponent(codPflPfl)}`,
        httpOptions
      )
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data.exists;
          }
          return false;
        })
      );
  }
}
