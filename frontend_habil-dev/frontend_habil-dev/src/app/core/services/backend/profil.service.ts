import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { environment } from "../../../../environments/environment";
import { MenuApplication } from "../../models/menuapplication";
import { ProfilMenuApplication } from "../../../features/administration/profilmenuapplication/models/profile-menu-application.model";
import {Profile} from '../../models/profile';

const API_URL = environment.apiURL;

/**
 * Standard API Response wrapper from backend
 */
interface ResponseSAGA<T = any> {
  code: number;
  message: string;
  data: T;
}

/**
 * Profile/Profil model
 */
interface Profil {
  codPflPfl: string;
  libpflpfl?: string;
  codCatpPfl?: string;
  codAppApp?: string;
  // ... other fields
}

/**
 * User Profile association model
 */
interface UtilisateurProfil {
  codPflPfl: string;
  numMatrUser: string;
  datdadhutpr?: Date;
  datFadhUtpr?: Date;
  boolEtatUtpr?: number;
  // ... other fields
}

@Injectable({
  providedIn: "root"  // ✅ Changed to 'root' for modern Angular
})
export class ProfilService {

  constructor(private http: HttpClient) {}

  /**
   * Helper method to get authorization headers
   */
  private getAuthHeaders(): HttpHeaders {
    const api_key = sessionStorage.getItem("token");
    return new HttpHeaders({
      "Authorization": `Bearer ${api_key}`,
      "Content-Type": "application/json"
    });
  }

  // ==================== PROFIL CRUD OPERATIONS ====================

  /**
   * Get all profils
   *
   * ✅ UNCHANGED method name and signature
   * OLD: GET /getAllProfil
   * NEW: GET /api/profils
   */
  getAllProfils(): Observable<any> {
    return this.http.get<ResponseSAGA<Profil[]>>(`${API_URL}profils`).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }  /**
   * Get profil by id
   *
   * ✅ UNCHANGED method name and signature
   * OLD: GET /getAllProfil
   * NEW: GET /api/profils/{codPfl}
   */
  getProfilById(codPfl:string): Observable<any> {
    return this.http.get<ResponseSAGA<Profil[]>>(`${API_URL}profils/${encodeURIComponent(codPfl)}`).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }
  /**
   * Get profils by application code
   *
   * ✅ UNCHANGED method name and signature
   * OLD: GET /getProfilList/{codAppApp}
   * NEW: GET /api/profils/by-application/{codAppApp}
   */
  getProfilList(codAppApp: string): Observable<any> {
    return this.http.get<ResponseSAGA<Profil[]>>(
      `${API_URL}profils/by-application/${encodeURIComponent(codAppApp)}`
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Add new profil
   *
   * ✅ UNCHANGED method name and signature
   * OLD: POST /ajoutProfil
   * NEW: POST /api/profils
   */
  ajoutProfil(profilDto: any): Observable<any> {
    return this.http.post<ResponseSAGA<Profil>>(
      `${API_URL}profils`,
      profilDto,
      { headers: this.getAuthHeaders() }
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Update existing profil
   *
   * ✅ UNCHANGED method name and signature
   * OLD: POST /updateProfil
   * NEW: PUT /api/profils/{codPflPfl}
   */
  updateProfil(profilDto: any): Observable<any> {
    return this.http.put<ResponseSAGA<Profil>>(
      `${API_URL}profils/${encodeURIComponent(profilDto.codPflPfl)}`,
      profilDto,
      { headers: this.getAuthHeaders() }
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Delete profil by code
   *
   * ✅ UNCHANGED method name and signature
   * OLD: DELETE /deleteProfil/{codPflPfl}
   * NEW: DELETE /api/profils/{codPflPfl}
   */
  deleteProfil(codPflPfl: string): Observable<any> {
    return this.http.delete<ResponseSAGA<string>>(
      `${API_URL}profils/${encodeURIComponent(codPflPfl)}`,
      { headers: this.getAuthHeaders() }
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Get manager profiles (authorized profiles for current manager)
   *
   * ✅ UNCHANGED method name and signature
   * OLD: GET /getProfilListForManager/{codAppApp}
   * NEW: GET /api/profils/manager/{codAppApp}
   */
  getManagerProfiles(codAppApp: string): Observable<any> {
    return this.http.get<ResponseSAGA<Profil[]>>(
      `${API_URL}profils/manager/${encodeURIComponent(codAppApp)}`
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Get user-specific profiles
   *
   * ✅ UNCHANGED method name and signature
   * OLD: GET /getProfilListForUser/{codAppApp}/{targetUserMat}
   * NEW: GET /api/profils/available-for-user/{codAppApp}/{targetUserMat}
   */
  getUserSpecificProfiles(codAppApp: string, targetUserMat: string): Observable<any> {
    return this.http.get<ResponseSAGA<Profil[]>>(
      `${API_URL}profils/available-for-user/${encodeURIComponent(codAppApp)}/${encodeURIComponent(targetUserMat)}`
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  // ==================== MENU APPLICATION OPERATIONS ====================

  /**
   * Add menu application
   *
   * ✅ UNCHANGED method name and signature
   * OLD: POST /ajoutMenuApplication
   * NEW: POST /api/menu-applications
   */
  addMenuApplication(menuApplication: MenuApplication): Observable<any> {
    return this.http.post<ResponseSAGA<MenuApplication>>(
      `${API_URL}menu-applications`,
      menuApplication,
      { headers: this.getAuthHeaders() }
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
  updateMenuApplication(menuApp: MenuApplication): Observable<any> {
    return this.http.put<ResponseSAGA<MenuApplication>>(
      `${API_URL}menu-applications/${encodeURIComponent(menuApp.codAppApp)}/${encodeURIComponent(menuApp.codMenuMenu)}`,
      menuApp,
      { headers: this.getAuthHeaders() }
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
   * OLD: DELETE /deleteMenuApp/{codMenuMenu}
   * NEW: DELETE /api/menu-applications/{codApp}/{codMenu}
   *
   * ⚠️ WARNING: Old API only used codMenuMenu, new API needs both!
   */
  deleteMenuApplication(menuApp: any): Observable<any> {
    // Extract codAppApp if available, otherwise might fail
    const codAppApp = menuApp.codAppApp || 'UNKNOWN';

    return this.http.delete<ResponseSAGA<string>>(
      `${API_URL}menu-applications/${encodeURIComponent(codAppApp)}/${encodeURIComponent(menuApp.codMenuMenu)}`,
      { headers: this.getAuthHeaders() }
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  // ==================== PROFIL MENU APPLICATION OPERATIONS ====================

  /**
   * Add profil menu application
   *
   * ✅ UNCHANGED method name and signature
   * OLD: POST /ajoutProfilMenuApplication
   * NEW: POST /api/profil-menu-applications
   */
  ajoutProfilMenuApplication(userprofil: ProfilMenuApplication): Observable<any> {
    return this.http.post<ResponseSAGA<ProfilMenuApplication>>(
      `${API_URL}profil-menu-applications`,
      userprofil,
      { headers: this.getAuthHeaders() }
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Update profil menu application
   *
   * ✅ UNCHANGED method name and signature
   * OLD: POST /updateProfilMenuApp
   * NEW: PUT /api/profil-menu-applications/{codApp}/{codMenu}/{codPfl}/{codTstrc}
   */
  updateProfilMenuApplication(menuApp: any): Observable<any> {
    return this.http.put<ResponseSAGA<ProfilMenuApplication>>(
      `${API_URL}profil-menu-applications/${encodeURIComponent(menuApp.codAppApp)}/${encodeURIComponent(menuApp.codMenuMenu)}/${encodeURIComponent(menuApp.codPflPfl)}/${encodeURIComponent(menuApp.codTstrcTstrc)}`,
      menuApp,
      { headers: this.getAuthHeaders() }
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Delete profil menu application
   *
   * ✅ UNCHANGED method name and signature
   * OLD: DELETE /deleteProfilMenuApp/{codMenu}/{codPfl}/{codTstrc}
   * NEW: DELETE /api/profil-menu-applications/{codApp}/{codMenu}/{codPfl}/{codTstrc}
   *
   * ⚠️ WARNING: Added codAppApp parameter (required by new API)
   */
  deleteProfilMenuApplication(
    codMenuMenu: string,
    codPflPfl: string,
    codTstrcTstrc: string,
    codAppApp?: string  // ⚠️ NEW: Optional parameter for backward compatibility
  ): Observable<any> {

    // If codAppApp not provided, log warning
    if (!codAppApp) {
      console.warn('deleteProfilMenuApplication: codAppApp parameter is recommended');
      codAppApp = 'UNKNOWN';  // You may need to get this from context
    }

    return this.http.delete<ResponseSAGA<string>>(
      `${API_URL}profil-menu-applications/${encodeURIComponent(codAppApp)}/${encodeURIComponent(codMenuMenu)}/${encodeURIComponent(codPflPfl)}/${encodeURIComponent(codTstrcTstrc)}`,
      { headers: this.getAuthHeaders() }
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  // ==================== USER PROFIL OPERATIONS ====================

  /**
   * Add user profil
   *
   * ✅ UNCHANGED method name and signature
   * OLD: POST /ajoutUserProfil
   * NEW: POST /api/utilisateur-profils
   */
  ajoutUserProfil(userprofil: any): Observable<any> {
    return this.http.post<ResponseSAGA<UtilisateurProfil>>(
      `${API_URL}utilisateur-profils`,
      userprofil,
      { headers: this.getAuthHeaders() }
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Get user profil list
   *
   * ✅ UNCHANGED method name and signature
   * OLD: GET /getListUserProfil
   * NEW: GET /api/utilisateur-profils
   */
  getUserProfilList(): Observable<any> {
    return this.http.get<ResponseSAGA<UtilisateurProfil[]>>(
      `${API_URL}utilisateur-profils`,
      { headers: this.getAuthHeaders() }
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Get user profil list (Promise version for legacy code)
   *
   * ✅ UNCHANGED method name and signature
   * ⚠️ DEPRECATED: Use getUserProfilList() with .toPromise() instead
   */
  getUserProfilListTest(params?: any): Promise<any> {
    console.warn('getUserProfilListTest is deprecated. Use getUserProfilList().toPromise() instead');
    return this.getUserProfilList().toPromise();
  }

  /**
   * Update user profil
   *
   * ✅ UNCHANGED method name and signature
   * OLD: POST /updateUtilisateurProfil
   * NEW: PUT /api/utilisateur-profils/{codPfl}/{numMatr}
   */
  updateUserProfil(userprofil: any): Observable<any> {
    return this.http.put<ResponseSAGA<UtilisateurProfil>>(
      `${API_URL}utilisateur-profils/${encodeURIComponent(userprofil.codPflPfl)}/${encodeURIComponent(userprofil.numMatrUser)}`,
      userprofil,
      { headers: this.getAuthHeaders() }
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Delete user profil
   *
   * ✅ UNCHANGED method name and signature
   * OLD: DELETE /deleteUtilisateurProfil/{codPflPfl}/{numMatrUser}
   * NEW: DELETE /api/utilisateur-profils/{codPfl}/{numMatr}
   */
  deleteUserProfil(codPflPfl: string, numMatrUser: string): Observable<any> {
    return this.http.delete<ResponseSAGA<string>>(
      `${API_URL}utilisateur-profils/${encodeURIComponent(codPflPfl)}/${encodeURIComponent(numMatrUser)}`,
      { headers: this.getAuthHeaders() }
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  // ==================== LOG OPERATIONS ====================

  /**
   * Get list of archived logs
   *
   * ✅ UNCHANGED method name and signature
   * OLD: GET /getArchivedLogs
   * NEW: GET /api/logs/archived
   */
  ListLogs(): Observable<any> {
    return this.http.get<ResponseSAGA<any[]>>(
      `${API_URL}logs/archived`,
      { headers: this.getAuthHeaders() }
    ).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }

  /**
   * Get specific log file
   *
   * ✅ UNCHANGED method name and signature
   * OLD: GET /getSpecifiedLog/{fileName}
   * NEW: GET /api/logs/{fileName}
   */
  getSpecifiedLog(fileName: any): Observable<any> {
    return this.http.get(
      `${API_URL}logs/${encodeURIComponent(fileName)}`,
      {
        headers: this.getAuthHeaders(),
        responseType: "blob"
      }
    );
  }

  /**
   * Get current logs
   *
   * ✅ UNCHANGED method name and signature
   * OLD: GET /getLogs
   * NEW: GET /api/logs/current
   */
  getLogs(): Observable<any> {
    return this.http.get(
      `${API_URL}logs/current`,
      {
        headers: this.getAuthHeaders(),
        responseType: "blob"
      }
    );
  }

  getProfilsByStructure(structureID: number): Observable<any> {
    return this.http.get<ResponseSAGA<Profile[]>>(`${API_URL}profils/getProfilsByStructure/${structureID}`).pipe(
      map(response => ({
        code: response.code,
        message: response.message,
        data: response.data
      }))
    );
  }
}
