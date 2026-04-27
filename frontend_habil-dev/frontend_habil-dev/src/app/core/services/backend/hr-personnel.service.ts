/**
 * @fileoverview HR Personnel Service
 *
 * Service for interacting with the external HR Personnel API.
 * Provides paginated search, filtering, and data retrieval.
 *
 * API Endpoints:
 * - GET /api/hr/personnel/page - Main paginated endpoint with general search
 * - GET /api/hr/personnel/filter - Individual field filters endpoint
 * - GET /api/hr/personnel?cin={cin} - Legacy non-paginated endpoint
 *
 * @author BNA HABIL Development Team
 * @version 1.0.0
 * @since 2026-01-29
 */

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseHABIL } from '../../models/Response/response-habil.interface';
import {
  HrPersonnel,
  HrPersonnelFilterParams,
  HrPersonnelPageResponse,
  DEFAULT_HR_FILTER_PARAMS
} from '../../models/hr-personnel.model';

@Injectable({
  providedIn: 'root'
})
export class HrPersonnelService {
  private baseUrl = environment.apiURL ;

  constructor(private http: HttpClient) {}

  /**
   * Get HR Personnel with pagination and general search
   * Uses the /api/hr/personnel/page endpoint
   *
   * @param params Filter parameters including pagination, search, and sorting
   * @returns Observable of paginated HR Personnel response
   *
   * @example
   * // Basic pagination
   * this.hrService.getHrPersonnelPage({ page: 0, size: 20, sortBy: 'matcle', sortDirection: 'ASC' })
   *
   * // With search
   * this.hrService.getHrPersonnelPage({ ...params, search: 'Mohamed' })
   */
  getHrPersonnelPage(params: HrPersonnelFilterParams = DEFAULT_HR_FILTER_PARAMS): Observable<HrPersonnelPageResponse> {
    let httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('size', params.size.toString())
      .set('sortBy', params.sortBy)
      .set('sortDirection', params.sortDirection);

    // Add optional search parameter
    if (params.search && params.search.trim()) {
      httpParams = httpParams.set('search', params.search.trim());
    }

    // Add optional CIN filter (exact match)
    if (params.cin && params.cin.trim()) {
      httpParams = httpParams.set('cin', params.cin.trim());
    }

    // Add optional matcle filter (exact match on /page endpoint)
    if (params.matcle && params.matcle.trim()) {
      httpParams = httpParams.set('matcle', params.matcle.trim());
    }

    return this.http.get<ResponseHABIL<HrPersonnelPageResponse>>(
      `${this.baseUrl}hr/personnel/page`,
      { params: httpParams }
    ).pipe(
      map(response => {
        console.log('✅ HR Personnel page loaded:', response.data?.totalElements || 0, 'total');
        return response.data || this.getEmptyPageResponse(params.size);
      }),
      catchError(error => {
        console.error('❌ Error fetching HR personnel page:', error);
        return of(this.getEmptyPageResponse(params.size));
      })
    );
  }

  /**
   * Get HR Personnel with individual field filters
   * Uses the /api/hr/personnel/filter endpoint
   * Supports partial matching (LIKE search) for prenom, nomuse, matcle
   *
   * @param params Filter parameters with individual field filters
   * @returns Observable of paginated HR Personnel response
   *
   * @example
   * // Filter by first name and last name
   * this.hrService.getHrPersonnelWithFilters({ ...params, prenom: 'Mohamed', nomuse: 'Ben' })
   */
  getHrPersonnelWithFilters(params: HrPersonnelFilterParams): Observable<HrPersonnelPageResponse> {
    let httpParams = new HttpParams()
      .set('page', params.page.toString())
      .set('size', params.size.toString())
      .set('sortBy', params.sortBy)
      .set('sortDirection', params.sortDirection);

    // Add individual field filters
    if (params.cin && params.cin.trim()) {
      httpParams = httpParams.set('cin', params.cin.trim());
    }

    if (params.matcle && params.matcle.trim()) {
      httpParams = httpParams.set('matcle', params.matcle.trim());
    }

    if (params.prenom && params.prenom.trim()) {
      httpParams = httpParams.set('prenom', params.prenom.trim());
    }

    if (params.nomuse && params.nomuse.trim()) {
      httpParams = httpParams.set('nomuse', params.nomuse.trim());
    }

    return this.http.get<ResponseHABIL<HrPersonnelPageResponse>>(
      `${this.baseUrl}hr/personnel/filter`,
      { params: httpParams }
    ).pipe(
      map(response => {
        console.log('✅ HR Personnel filtered:', response.data?.totalElements || 0, 'results');
        return response.data || this.getEmptyPageResponse(params.size);
      }),
      catchError(error => {
        console.error('❌ Error fetching HR personnel with filters:', error);
        return of(this.getEmptyPageResponse(params.size));
      })
    );
  }

  /**
   * Get HR Personnel by CIN (legacy non-paginated endpoint)
   * Uses the /api/hr/personnel?cin={cin} endpoint
   *
   * @param cin National ID to search for
   * @returns Observable of HR Personnel array
   */
  getHrPersonnelByCin(cin: string): Observable<HrPersonnel[]> {
    const params = new HttpParams().set('cin', cin);

    return this.http.get<ResponseHABIL<HrPersonnel[]>>(
      `${this.baseUrl}hr/personnel`,
      { params }
    ).pipe(
      map(response => {
        const data = response.data || [];
        console.log('✅ HR Personnel by CIN:', data.length, 'found');
        return Array.isArray(data) ? data : [];
      }),
      catchError(error => {
        console.error('❌ Error fetching HR personnel by CIN:', error);
        return of([]);
      })
    );
  }

  /**
   * Smart search - decides which endpoint to use based on filters provided
   * - If only general search or CIN: uses /page endpoint
   * - If individual field filters (prenom, nomuse): uses /filter endpoint
   *
   * @param params Filter parameters
   * @returns Observable of paginated response
   */
  searchHrPersonnel(params: HrPersonnelFilterParams): Observable<HrPersonnelPageResponse> {
    const hasIndividualFilters = !!(params.prenom || params.nomuse);

    if (hasIndividualFilters) {
      return this.getHrPersonnelWithFilters(params);
    } else {
      return this.getHrPersonnelPage(params);
    }
  }

  /**
   * Returns an empty page response for error cases
   */
  private getEmptyPageResponse(size: number): HrPersonnelPageResponse {
    return {
      content: [],
      pageable: {
        pageNumber: 0,
        pageSize: size,
        sort: {
          sorted: false,
          direction: 'ASC',
          property: 'matcle'
        }
      },
      totalElements: 0,
      totalPages: 0,
      size: size,
      number: 0,
      first: true,
      last: true,
      numberOfElements: 0,
      empty: true
    };
  }

  /**
   * Get poste (job position code) for a given matricule
   * Uses the /api/hr/personnel/poste endpoint
   *
   * @param matcle Employee matricule
   * @returns Observable of poste code string
   */
  getPosteByMatricule(matcle: string): Observable<string> {
    const params = new HttpParams().set('matcle', matcle.trim());

    return this.http.get<ResponseHABIL<string>>(
      `${this.baseUrl}hr/personnel/poste`,
      { params }
    ).pipe(
      map(response => {
        console.log('✅ Poste for matricule', matcle, ':', response.data);
        return response.data || '';
      }),
      catchError(error => {
        console.error('❌ Error fetching poste for matricule:', matcle, error);
        return of('');
      })
    );
  }

  /**
   * Verify if a given codePoste matches the matricule's actual poste
   * Uses the /api/hr/personnel/verify-poste endpoint
   *
   * @param matcle Employee matricule
   * @param codePoste Job position code to verify
   * @returns Observable<boolean> - true if poste matches
   */
  verifyPosteByMatricule(matcle: string, codePoste: string): Observable<boolean> {
    const params = new HttpParams()
      .set('matcle', matcle.trim())
      .set('codePoste', codePoste.trim());

    return this.http.get<ResponseHABIL<boolean>>(
      `${this.baseUrl}hr/personnel/verify-poste`,
      { params }
    ).pipe(
      map(response => {
        console.log('✅ Verify poste', codePoste, 'for matricule', matcle, ':', response.data);
        return response.data ?? false;
      }),
      catchError(error => {
        console.error('❌ Error verifying poste for matricule:', matcle, error);
        return of(false);
      })
    );
  }
}
