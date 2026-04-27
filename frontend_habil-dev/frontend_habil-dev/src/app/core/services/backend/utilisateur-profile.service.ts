import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { map, Observable } from "rxjs";
import { environment } from "../../../../environments/environment";
import { ResponseHABIL } from "../../models/Response/response-habil.interface";
import { UtilisateurProfil } from "../../models/utilisateurprofil";
import { PageResponse } from "../../models/Response/PageResponse";


const API_BASE = environment.apiURL+"utilisateur-profils/";

const AJOUT_USER_PROFIL_URL = `${API_BASE}ajoutUserProfil`;
const UPDATE_USER_PROFIL_URL = `${API_BASE}updateUtilisateurProfil`;
const DELETE_USER_PROFIL_URL = `${API_BASE}deleteUtilisateurProfil`;

const httpOptions = {
  headers: new HttpHeaders({
    "Content-Type": "application/json",
  }),
};

@Injectable({
  providedIn: "root",
})
export class UtilisateurProfilService {
  constructor(private http: HttpClient) {}

  // Create (POST /ajoutUserProfil)
  create(userProfil: UtilisateurProfil): Observable<void> {
    return this.http
      .post<ResponseHABIL<unknown>>(
        AJOUT_USER_PROFIL_URL,
        userProfil,
        httpOptions
      )
      .pipe(
        map((response) => {
          return;
        })
      );
  }

  getListByAppPaginated(
    codApp: string,
    page: number = 0,
    size: number = 10,
    sortBy: string = "DAT_FADH_UTPR",
    sortDirection: string = "ASC",
    filters?: any
  ): Observable<PageResponse<UtilisateurProfil>> {
    let url = `${API_BASE}paginated?cdp=${encodeURIComponent(
      codApp
    )}&page=${page}&size=${size}&sortBy=${sortBy}&sortDirection=${sortDirection}`;

    if (filters) {
      Object.keys(filters).forEach((field) => {
        const filter = filters[field];
        if (filter?.value != null && filter.value !== "") {
          url += `&${field}=${encodeURIComponent(
            filter.value
          )}&${field}MatchMode=${filter.matchMode}`;
        }
      });
    }

    return this.http
      .get<ResponseHABIL<PageResponse<UtilisateurProfil>>>(url, httpOptions)
      .pipe(map((response) => response.data));
  }


  // Read applications by matricule (GET /{matricule}/applicationsl)
  getApplicationsByMatricule(matricule: string): Observable<string[]> {
    const url = `${API_BASE}${encodeURIComponent(matricule)}/applicationsl`;
    return this.http.get<ResponseHABIL<string[]>>(url, httpOptions).pipe(
      map((response) => {
        return response.data || [];
      })
    );
  }
  /**
   * Get user profiles by application code and optional matricule
   * @param cdp - application code (mandatory)
   * @param matricule - user matricule (optional)
   */getUserProfiles(cdp: string, matricule?: string): Observable<any> {
    
    let params = new HttpParams().set('cdp', cdp);
    if (matricule && matricule.trim() !== '') {
      params = params.set('matricule', matricule);
    }

    console.log(params)
   return this.http.get(`${API_BASE}detailed`, { params });
  }

  // Update (POST /updateUtilisateurProfil)
  update(userProfil: UtilisateurProfil): Observable<void> {
    return this.http
      .post<ResponseHABIL<unknown>>(
        UPDATE_USER_PROFIL_URL,
        userProfil,
        httpOptions
      )
      .pipe(
        map((response) => {
          return;
        })
      );
  }

  // Delete (DELETE /deleteUtilisateurProfil/{codPflPfl}/{numMatrUser})
  delete(codPflPfl: string, numMatrUser: string): Observable<void> {
    const url = `${DELETE_USER_PROFIL_URL}/${encodeURIComponent(
      codPflPfl
    )}/${encodeURIComponent(numMatrUser)}`;
    return this.http.delete<ResponseHABIL<unknown>>(url, httpOptions).pipe(
      map((response) => {
        return;
      })
    );
  }
}
