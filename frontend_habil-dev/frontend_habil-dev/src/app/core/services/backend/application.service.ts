import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { map, Observable, of, tap } from "rxjs";
import { environment } from "../../../../environments/environment";
import { Application } from "../../models/application";


const API_BASE = environment.apiURL;
const API_URL = `${API_BASE}applications`;

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

/**
 * Standard API Response wrapper from new backend
 */
interface ResponseSAGA<T = any> {
  code: number;
  message: string;
  data: T;
}

@Injectable({
  providedIn: "root",
})
export class ApplicationService {
  private appsCache: Application[] | null = null;

  constructor(private http: HttpClient) {}

  /**
   * Get all applications
   * Supports caching for performance
   *
   * ✅ UNCHANGED method name and signature
   * OLD: GET /api/applications
   * NEW: GET /api/applications
   */
  getAll(): Observable<Application[]> {
    // Return from cache if available
    if (this.appsCache) {
      console.log('Returning cached applications:', this.appsCache);
      return of(this.appsCache);
    }

    console.log('Fetching applications from API');

    return this.http
      .get<ResponseSAGA<Application[]>>(API_URL+'/authorized', httpOptions)
      .pipe(
        map((response) => {
          // Handle both old (ResponseHABIL) and new (ResponseSAGA) format
          if ('data' in response) {
            return response.data || [];
          }
          // Fallback for unexpected format
          return [];
        }),
        tap((apps) => {
          this.appsCache = apps;  // Store in cache
          console.log('Cached applications:', apps.length);
        })
      );
  }

  /**
   * Get application by ID
   *
   * ✅ UNCHANGED method name and signature
   * OLD: GET /api/applications/{id}
   * NEW: GET /api/applications/{id}
   */
  getById(codApp: string): Observable<Application> {
    return this.http
      .get<ResponseSAGA<Application>>(
        `${API_URL}/${encodeURIComponent(codApp)}`,
        httpOptions
      )
      .pipe(
        map((response) => {
          // Transform ResponseSAGA to Application
          if ('data' in response) {
            return response.data;
          }
          // If response is already Application object (backward compatibility)
          return response as unknown as Application;
        })
      );
  }

  /**
   * Create new application
   *
   * ✅ UNCHANGED method name and signature
   * OLD: POST /api/applications
   * NEW: POST /api/applications
   */
  create(application: Application): Observable<Application> {
    // Clear cache when creating new item
    this.clearCache();

    return this.http
      .post<ResponseSAGA<Application>>(API_URL, application, httpOptions)
      .pipe(
        map((response) => {
          // Transform ResponseSAGA to Application
          if ('data' in response) {
            return response.data;
          }
          return response as unknown as Application;
        }),
        tap((app) => console.log('Created application:', app))
      );
  }

  /**
   * Update existing application
   *
   * ✅ UNCHANGED method name and signature
   * OLD: PUT /api/applications/{id}
   * NEW: PUT /api/applications/{id}
   */
  update(codApp: string, application: Application): Observable<Application> {
    // Clear cache when updating
    this.clearCache();

    return this.http
      .put<ResponseSAGA<Application>>(
        `${API_URL}/${encodeURIComponent(codApp)}`,
        application,
        httpOptions
      )
      .pipe(
        map((response) => {
          // Transform ResponseSAGA to Application
          if ('data' in response) {
            return response.data;
          }
          return response as unknown as Application;
        }),
        tap((app) => console.log('Updated application:', app))
      );
  }

  /**
   * Delete application
   *
   * ✅ UNCHANGED method name and signature
   * OLD: DELETE /api/applications/{id}
   * NEW: DELETE /api/applications/{id}
   */
  delete(codApp: string): Observable<void> {
    // Clear cache when deleting
    this.clearCache();

    return this.http
      .delete<ResponseSAGA<string>>(
        `${API_URL}/${encodeURIComponent(codApp)}`,
        httpOptions
      )
      .pipe(
        map(() => {
          // Transform to void as expected by method signature
          console.log('Deleted application:', codApp);
          return;
        })
      );
  }

  /**
   * Clear cached applications
   *
   * ✅ UNCHANGED method name and signature
   */
  clearCache(): void {
    this.appsCache = null;
    console.log('Application cache cleared');
  }

  /**
   * ✅ NEW: Force refresh from API (bypasses cache)
   * Optional - doesn't affect existing code
   */
  refresh(): Observable<Application[]> {
    this.clearCache();
    return this.getAll();
  }

  /**
   * ✅ NEW: Typed version for future use
   * Optional - doesn't affect existing code
   */
  getAllTyped(): Observable<ResponseSAGA<Application[]>> {
    return this.http.get<ResponseSAGA<Application[]>>(API_URL, httpOptions);
  }

  /**
   * ✅ NEW: Get by ID with full response
   * Optional - doesn't affect existing code
   */
  getByIdTyped(codApp: string): Observable<ResponseSAGA<Application>> {
    return this.http.get<ResponseSAGA<Application>>(
      `${API_URL}/${encodeURIComponent(codApp)}`,
      httpOptions
    );
  }

  /**
   * Create new application with profiles
   * POST /api/applications/add
   */
  createWithProfiles(application: Application): Observable<Application> {
    // Clear cache when creating new item
    this.clearCache();

    return this.http
      .post<ResponseSAGA<Application>>(`${API_URL}/add`, application, httpOptions)
      .pipe(
        map((response) => {
          // Transform ResponseSAGA to Application
          if ('data' in response) {
            return response.data;
          }
          return response as unknown as Application;
        }),
        tap((app) => console.log('Created application with profiles:', app))
      );
  }

  /**
    * Update application with profiles
    * PUT /api/applications/update/{codApp}
  */
  updateWithProfiles(codApp: string, application: Application): Observable<Application> {
    this.clearCache();

    return this.http
      .put<ResponseSAGA<Application>>(
        `${API_URL}/update/${encodeURIComponent(codApp)}`,
        application,
        httpOptions
      )
      .pipe(
        map((response) => {
          if ('data' in response) {
            return response.data;
          }
          return response as unknown as Application;
        }),
        tap((app) => console.log('Updated application with profiles:', app))
      );
  }

  /**
   * Get application details with profile flags
   * GET /api/applications/details/{codApp}
   */
  getApplicationDetails(codApp: string): Observable<Application> {
    return this.http
      .get<ResponseSAGA<Application>>(
        `${API_URL}/details/${encodeURIComponent(codApp)}`,
        httpOptions
      )
      .pipe(
        map((response) => {
          if ('data' in response) {
            return response.data;
          }
          return response as unknown as Application;
        })
      );
  }
}
