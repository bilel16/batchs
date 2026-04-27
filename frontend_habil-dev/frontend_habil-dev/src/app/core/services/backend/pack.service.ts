import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, of } from "rxjs";
import { map, tap } from "rxjs/operators";
import {environment} from '../../../../environments/environment';
import {ResponseSAGA} from '../../models/response-saga.interface';

const API_BASE = environment.apiURL;
const API_URL = `${API_BASE}pack`;

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

export interface Pack {
  codPackPack: string;
  libPackPack?: string;
  descPack?: string;
  codNivhPfl?: string;
  codCatpPfl?: string;
  boolActifPack?: number;
  datCrePack?: string;
  userCrePack?: string;
  codAppApp?: string;
  boolEtat?: number; // Keep for backward compatibility
}

@Injectable({
  providedIn: "root",
})
export class PackService {
  private packsCache: Pack[] | null = null;

  constructor(private http: HttpClient) {}

  // ==================== CRUD OPERATIONS ====================

  /**
   * Get all packs
   * GET /api/pack
   */
  getAll(): Observable<Pack[]> {
    if (this.packsCache) {
      console.log("Returning cached packs:", this.packsCache);
      return of(this.packsCache);
    }

    console.log("Fetching packs from API");

    return this.http.get<ResponseSAGA<Pack[]>>(API_URL, httpOptions).pipe(
      map((response) => {
        if ("data" in response) {
          return response.data || [];
        }
        return [];
      }),
      tap((packs) => {
        this.packsCache = packs;
        console.log("Cached packs:", packs.length);
      })
    );
  }

  /**
   * Get pack by ID
   * GET /api/pack/{codPackPack}
   */
  getById(codPackPack: string): Observable<Pack> {
    return this.http
      .get<ResponseSAGA<Pack>>(
        `${API_URL}/${encodeURIComponent(codPackPack)}`,
        httpOptions
      )
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data;
          }
          return response as unknown as Pack;
        })
      );
  }

  /**
   * Create new pack
   * POST /api/pack
   */
  create(pack: Pack): Observable<Pack> {
    this.clearCache();

    return this.http
      .post<ResponseSAGA<Pack>>(API_URL, pack, httpOptions)
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data;
          }
          return response as unknown as Pack;
        }),
        tap((p) => console.log("Created pack:", p))
      );
  }

  /**
   * Update existing pack
   * PUT /api/pack/{codPackPack}
   */
  update(codPackPack: string, pack: Pack): Observable<Pack> {
    this.clearCache();

    return this.http
      .put<ResponseSAGA<Pack>>(
        `${API_URL}/${encodeURIComponent(codPackPack)}`,
        pack,
        httpOptions
      )
      .pipe(
        map((response) => {
          if ("data" in response) {
            return response.data;
          }
          return response as unknown as Pack;
        }),
        tap((p) => console.log("Updated pack:", p))
      );
  }

  /**
   * Delete pack
   * DELETE /api/pack/{codPackPack}
   */
  delete(codPackPack: string): Observable<void> {
    this.clearCache();

    return this.http
      .delete<ResponseSAGA<string>>(
        `${API_URL}/${encodeURIComponent(codPackPack)}`,
        httpOptions
      )
      .pipe(
        map(() => {
          console.log("Deleted pack:", codPackPack);
          return;
        })
      );
  }

  // ==================== QUERY OPERATIONS ====================

  /**
   * Get active packs
   * GET /api/pack/active
   */
  getActivePacks(): Observable<Pack[]> {
    return this.http
      .get<ResponseSAGA<Pack[]>>(`${API_URL}/active`, httpOptions)
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
   * Get packs by hierarchical level
   * GET /api/pack/by-niveau/{codNivhPfl}
   */
  getPacksByNiveau(codNivhPfl: string): Observable<Pack[]> {
    return this.http
      .get<ResponseSAGA<Pack[]>>(
        `${API_URL}/by-niveau/${encodeURIComponent(codNivhPfl)}`,
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
   * Get packs by category
   * GET /api/pack/by-categorie/{codCatpPfl}
   */
  getPacksByCategorie(codCatpPfl: string): Observable<Pack[]> {
    return this.http
      .get<ResponseSAGA<Pack[]>>(
        `${API_URL}/by-categorie/${encodeURIComponent(codCatpPfl)}`,
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
   * Get manager packs
   * GET /api/pack/manager
   */
  getManagerPacks(): Observable<Pack[]> {
    return this.http
      .get<ResponseSAGA<Pack[]>>(`${API_URL}/manager`, httpOptions)
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
   * Get available packs for user
   * GET /api/pack/available-for-user/{targetUserMat}
   */
  getAvailablePacksForUser(targetUserMat: string): Observable<Pack[]> {
    return this.http
      .get<ResponseSAGA<Pack[]>>(
        `${API_URL}/available-for-user/${encodeURIComponent(targetUserMat)}`,
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
   * Get not assigned packs for user
   * GET /api/pack/not-assigned/{targetUserMat}
   */
  getNotAssignedPacksForUser(targetUserMat: string): Observable<Pack[]> {
    return this.http
      .get<ResponseSAGA<Pack[]>>(
        `${API_URL}/not-assigned/${encodeURIComponent(targetUserMat)}`,
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

  // ==================== UTILITY METHODS ====================

  /**
   * Clear cached packs
   */
  clearCache(): void {
    this.packsCache = null;
    console.log("Pack cache cleared");
  }

  /**
   * Force refresh from API (bypasses cache)
   */
  refresh(): Observable<Pack[]> {
    this.clearCache();
    return this.getAll();
  }
}
