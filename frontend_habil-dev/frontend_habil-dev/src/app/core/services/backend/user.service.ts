import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { catchError, map, Observable, of } from "rxjs";
import { HttpHeaders } from "@angular/common/http";
import { HttpHandler, HttpRequest } from "@angular/common/http";
import { environment } from "../../../../environments/environment";
import { ResponseHABIL } from "../../models/Response/response-habil.interface";

const API_URL = environment.apiURL;

/**
 * Personnel data interface
 */
export interface Personnel {
  id?: number;
  matricule?: string;
  mat?: string;
  nom?: string;
  prenom?: string;
  nom_prenom?: string;
  email?: string;
  fonction?: string;
  direction?: string;
  fullName?: string;
  displayName?: string;
  cod_stat_user?: number | boolean;
  cod_strc_strc?: number;
  cod_tstr_tstr?: number;
}

/**
 * Structure option for dropdown filters
 */
export interface StructureOption {
  id: number;
  label: string;
  typeCode: number;
  typeName: string;
}

/**
 * Structure type option for dropdown filters
 */
export interface StructureTypeOption {
  code: number;
  label: string;
}

/**
 * Admin filter parameters for personnel search
 * NOTE: codStrcStrc is now an array to support multiple structure selection
 */
export interface PersonnelFilterParams {
  search?: string;
  codStatUser?: boolean;
  codStrcStrc?: number[];  // Changed to array for multi-select
  codTstrTstr?: number;
  sortBy?: string;
  sortDirection?: 'ASC' | 'DESC';
}

/**
 * Paginated response interface for personnel
 */
export interface PaginatedPersonnelResponse {
  content: Personnel[];
  pageable: {
    pageNumber: number;
    pageSize: number;
  };
  totalPages: number;
  totalElements: number;
  first: boolean;
  last: boolean;
  size: number;
  number: number;
}

@Injectable({
  providedIn: "root",
})
export class UserService {
  constructor(private http: HttpClient) {}
  private baseUrl = environment.apiURL || 'http://localhost:8084/api';

  /**
   * Get all personnel with pagination and filters (Admin access only)
   * GET /api/personnel/page?page={page}&size={size}&search={search}&codStatUser={status}&codStrcStrc={structure}&codTstrTstr={structureType}&sortBy={field}&sortDirection={direction}
   * @param page Page number (0-indexed)
   * @param size Number of items per page
   * @param filters Optional filter parameters (search, status, structure, structure type, sorting)
   * @returns Observable of paginated personnel response
   */
  getAllPersonnelPaginated(
    page: number = 0, 
    size: number = 20,
    filters?: PersonnelFilterParams
  ): Observable<PaginatedPersonnelResponse> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());    // Add optional filter parameters
    if (filters) {
      if (filters.search && filters.search.trim()) {
        params = params.set('search', filters.search.trim());
      }
      if (filters.codStatUser !== undefined && filters.codStatUser !== null) {
        params = params.set('codStatUser', filters.codStatUser.toString());
      }
      // Handle multiple structure IDs - append each one separately
      if (filters.codStrcStrc && filters.codStrcStrc.length > 0) {
        filters.codStrcStrc.forEach(structureId => {
          params = params.append('codStrcStrc', structureId.toString());
        });
      }
      if (filters.codTstrTstr !== undefined && filters.codTstrTstr !== null) {
        params = params.set('codTstrTstr', filters.codTstrTstr.toString());
      }
      if (filters.sortBy) {
        params = params.set('sortBy', filters.sortBy);
      }
      if (filters.sortDirection) {
        params = params.set('sortDirection', filters.sortDirection);
      }
    }
    
    return this.http.get<ResponseHABIL<PaginatedPersonnelResponse>>(`${this.baseUrl}personnel/page`, { params })
      .pipe(
        map(response => {
          const data = response?.data;
          if (data && data.content) {
            // Enrich users with computed fields for consistency
            data.content = data.content.map((user: any) => ({
              ...user,
              numMatrUser: user.mat,
              displayName: user.nom_prenom || user.mat,
              label: `${user.mat} - ${user.nom_prenom || 'N/A'}`,
              selected: false,
              isActive: user.cod_stat_user === 1 || user.cod_stat_user === true,
              structureId: user.cod_strc_strc,
            }));
          }
          return data || {
            content: [],
            pageable: { pageNumber: 0, pageSize: size },
            totalPages: 0,
            totalElements: 0,
            first: true,
            last: true,
            size: size,
            number: 0
          };
        }),
        catchError(error => {
          console.error('Error fetching paginated personnel:', error);
          return of({
            content: [],
            pageable: { pageNumber: 0, pageSize: size },
            totalPages: 0,
            totalElements: 0,
            first: true,
            last: true,
            size: size,
            number: 0
          });
        })
      );
  }
  /**
   * Search users by matricule (partial search)
   * GET /api/personnel/users/by-matricule?query={query}
   */
  searchByMatricule(query: string): Observable<Personnel[]> {
    const params = new HttpParams().set('query', query);
    
    return this.http.get<ResponseHABIL<Personnel[]>>(`${this.baseUrl}personnel/users/by-matricule`, { params })
      .pipe(
        map(response => {
          const users = response?.data || [];
          // Enrich users with computed fields
          return users.map((user:any) => ({
            ...user,
            numMatrUser: user.mat,
        codStatUser: user.cod_stat_user,
        codStrcStrc: user.cod_strc_strc
          }));
        }),
        catchError(error => {
          console.error('Error searching users:', error);
          return of([]);
        })
      );
  }

  /**
   * Get structure options for dropdown filter
   * GET /api/personnel/structure-options
   * @returns Observable of structure options array
   */
  getStructureOptions(): Observable<StructureOption[]> {
    return this.http.get<ResponseHABIL<StructureOption[]>>(`${this.baseUrl}structure/structure-options`)
      .pipe(
        map(response => response?.data || []),
        catchError(error => {
          console.error('Error fetching structure options:', error);
          return of([]);
        })
      );
  }

  /**
   * Get structure type options for dropdown filter
   * GET /api/personnel/structure-type-options
   * @returns Observable of structure type options array
   */
  getStructureTypeOptions(): Observable<StructureTypeOption[]> {
    return this.http.get<ResponseHABIL<StructureTypeOption[]>>(`${this.baseUrl}structure/structure-type-options`)
      .pipe(
        map(response => response?.data || []),
        catchError(error => {
          console.error('Error fetching structure type options:', error);
          return of([]);
        })
      );
  }

  /**
   * Get structures filtered by type code (for cascading dropdown)
   * GET /api/personnel/structures-by-type/{typeCode}
   * @param typeCode Structure type code to filter by
   * @returns Observable of structure options array
   */
  getStructuresByType(typeCode: number): Observable<StructureOption[]> {
    return this.http.get<ResponseHABIL<StructureOption[]>>(`${this.baseUrl}structure/structures-by-type/${typeCode}`)
      .pipe(
        map(response => response?.data || []),
        catchError(error => {
          console.error('Error fetching structures by type:', error);
          return of([]);
        })
      );
  }

  /**
   * Get applications for a user by matricule
   * GET /api/personnel/{matricule}/applications
   */
  getUserApplications(matricule: string): Observable<string[]> {
    return this.http.get<ResponseHABIL<string[]>>(`${this.baseUrl}personnel/${matricule}/applications`)
      .pipe(
        map(response => response?.data || []),
        catchError(error => {
          console.error('Error fetching user applications:', error);
          return of([]);
        })
      );
  }
getUserRolesInApplication(matricule: string, appCode: string): Observable<any[]> {
  return this.http.get<ResponseHABIL<any[]>>(
      `${this.baseUrl}roles/user/${matricule}/app/${appCode}`
    )
    .pipe(
      map(response => response?.data || []),
      catchError(error => {
        console.error('Error fetching user roles for application:', error);
        return of([]);
      })
    );
}
  
  getHelloWord(): Observable<any> {
    let api_key = sessionStorage.getItem("token");
    const headers = new HttpHeaders({
      Authorization: `Bearer ${api_key}`,
      responseType: "text",
      //  'content-type': 'application/json'
    });

    const requestOptions = { headers: headers };
    /*     const clicks = sessionStorage.getItem('token');
    const headers= new HttpHeaders()
  .set('Access-Control-Allow-Origin', '*')
  .set('Access-Control-Allow-Credentials', 'true')
  .set('Authorization',  `Bearer ${clicks}`)
  .set('Access-Control-Allow-Methods', 'GET'); */

    return this.http.get(API_URL, requestOptions);
  }
}
