import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { environment } from '../../../../environments/environment';
import { MenuApplication } from '../../models/menuapplication';

const API_BASE = environment.apiURL;
const API_URL = `${API_BASE}menu-applications`;

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

/**
 * Standard API Response wrapper from backend
 */
interface ResponseSAGA<T = any> {
  code: number;
  message: string;
  data: T;
}

/**
 * Batch operation result structure
 */
interface BatchOperationResult<T> {
  successful: T[];
  failed: Array<{
    index: number;
    identifier: string;
    error: string;
  }>;
  mode: 'ALL_OR_NOTHING' | 'BEST_EFFORT';
}

@Injectable({
  providedIn: "root",
})
export class MenuApplicationService {
  constructor(private http: HttpClient) {}

  /**
   * Get all menu applications
   * 
   * ✅ UNCHANGED method name and signature
   * OLD: GET /getAllMenuApplicationList
   * NEW: GET /api/menu-applications
   */
  getAllMenuApplicationList(): Observable<any> {
    return this.http.get<ResponseSAGA<MenuApplication[]>>(API_URL, httpOptions).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Get menu applications by application code
   * 
   * ✅ UNCHANGED method name and signature
   * OLD: GET /getMenuApplicationList/{codeApp}
   * NEW: GET /api/menu-applications/by-application/{codeApp}
   */
  getMenuApplicationList(codeApp: string): Observable<any> {
    return this.http.get<ResponseSAGA<MenuApplication[]>>(
      `${API_URL}/by-application/${encodeURIComponent(codeApp)}`,
      httpOptions
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Add new menu application
   * 
   * ✅ UNCHANGED method name and signature
   * OLD: POST /ajoutMenuApplication
   * NEW: POST /api/menu-applications
   */
  addMenuApplication(menuApplicationDto: MenuApplication): Observable<any> {
    return this.http.post<ResponseSAGA<MenuApplication>>(
      API_URL,
      menuApplicationDto,
      httpOptions
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Update menu application
   * 
   * ✅ UNCHANGED method name and signature
   * OLD: POST /updateMenuApp
   * NEW: PUT /api/menu-applications/{codApp}/{codMenu}
   */
  updateMenuApplication(menuApplicationDto: MenuApplication): Observable<any> {
    return this.http.put<ResponseSAGA<MenuApplication>>(
      `${API_URL}/${encodeURIComponent(menuApplicationDto.codAppApp)}/${encodeURIComponent(menuApplicationDto.codMenuMenu)}`,
      menuApplicationDto,
      httpOptions
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Delete menu application
   * 
   * ✅ UNCHANGED method name and signature
   * OLD: DELETE /deleteMenuApp (with body)
   * NEW: DELETE /api/menu-applications/{codApp}/{codMenu}
   * 
   * Note: Changed from request body to URL parameters (RESTful standard)
   */
  deleteMenuApplication(menuApplicationDto: MenuApplication): Observable<any> {
    return this.http.delete<ResponseSAGA<string>>(
      `${API_URL}/${encodeURIComponent(menuApplicationDto.codAppApp)}/${encodeURIComponent(menuApplicationDto.codMenuMenu)}`,
      httpOptions
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  // ==================== OPTIONAL NEW METHODS ====================

  /**
   * ✅ NEW: Create multiple menu applications in batch
   * Optional - doesn't affect existing code
   */
  addMenuApplicationBatch(menuApplications: MenuApplication[]): Observable<any> {
    return this.http.post<ResponseSAGA<any>>(
      `${API_URL}/batch`,
      menuApplications,
      httpOptions
    ).pipe(
      map(response => this.transformBatchResponse(response))
    );
  }

  /**
   * ✅ NEW: Get by composite ID
   * Optional - doesn't affect existing code
   */
  getMenuApplicationById(codAppApp: string, codMenuMenu: string): Observable<any> {
    return this.http.get<ResponseSAGA<MenuApplication>>(
      `${API_URL}/${encodeURIComponent(codAppApp)}/${encodeURIComponent(codMenuMenu)}`,
      httpOptions
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * ✅ NEW: Typed version for future use
   * Optional - doesn't affect existing code
   */
  getAllTyped(): Observable<ResponseSAGA<MenuApplication[]>> {
    return this.http.get<ResponseSAGA<MenuApplication[]>>(API_URL, httpOptions);
  }

  /**
   * Helper method to transform batch operation responses
   */
  private transformBatchResponse(response: ResponseSAGA<any>): any {
    const data = response.data;
    
    // If response.data is already an array
    if (Array.isArray(data)) {
      return {
        code: response.code,
        message: response.message,
        data: data
      };
    }
    
    // If response.data is BatchOperationResult
    if (data && typeof data === 'object' && 'successful' in data) {
      const result = data as BatchOperationResult<MenuApplication>;
      
      if (result.failed && result.failed.length > 0) {
        return {
          code: 1,
          message: `${result.failed.length} items failed`,
          data: result.failed,
          successful: result.successful
        };
      }
      
      return {
        code: 0,
        message: response.message,
        data: result.successful
      };
    }
    
    // Fallback
    return {
      code: response.code,
      message: response.message,
      data: data
    };
  }
}