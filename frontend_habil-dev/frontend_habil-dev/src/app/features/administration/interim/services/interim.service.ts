import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment';
import {
  Interim,
  InterimStats,
  InterimProfilGranted,
  InterimProfilBackup,
  InterimSearchParams,
  EtatInterim,
} from '../models/interim.model';

const API_URL = environment.apiURL;

@Injectable({
  providedIn: 'root',
})
export class InterimService {
  private readonly basePath = 'api/interims';

  constructor(private http: HttpClient) {}

  // ─── helpers ──────────────────────────────────────────────────────────
  private get headers(): HttpHeaders {
    const token = sessionStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });
  }

  private options(params?: HttpParams) {
    return params ? { headers: this.headers, params } : { headers: this.headers };
  }

  // ─── CRUD ─────────────────────────────────────────────────────────────
  getAll(): Observable<Interim[]> {
    return this.http.get<Interim[]>(
      `${API_URL}${this.basePath}`,
      this.options()
    );
  }

  getById(id: number): Observable<Interim> {
    return this.http.get<Interim>(
      `${API_URL}${this.basePath}/${id}`,
      this.options()
    );
  }

  create(interim: Partial<Interim>): Observable<Interim> {
    return this.http.post<Interim>(
      `${API_URL}${this.basePath}`,
      interim,
      this.options()
    );
  }

  update(id: number, interim: Partial<Interim>): Observable<Interim> {
    return this.http.put<Interim>(
      `${API_URL}${this.basePath}/${id}`,
      interim,
      this.options()
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(
      `${API_URL}${this.basePath}/${id}`,
      this.options()
    );
  }

  // ─── State transitions ────────────────────────────────────────────────
  cancel(id: number): Observable<Interim> {
    return this.http.patch<Interim>(
      `${API_URL}${this.basePath}/${id}/cancel`,
      {},
      this.options()
    );
  }

  activate(id: number): Observable<Interim> {
    return this.http.put<Interim>(
      `${API_URL}${this.basePath}/${id}/activate`,
      {},
      this.options()
    );
  }

  terminate(id: number): Observable<Interim> {
    return this.http.put<Interim>(
      `${API_URL}${this.basePath}/${id}/terminate`,
      {},
      this.options()
    );
  }

  // ─── Search / Filter ─────────────────────────────────────────────────
  search(params: InterimSearchParams): Observable<Interim[]> {
    let httpParams = new HttpParams();
    if (params.matriculeSource != null) httpParams = httpParams.set('matriculeSource', params.matriculeSource);
    if (params.matriculeCible != null) httpParams = httpParams.set('matriculeCible', params.matriculeCible);
    if (params.etat) httpParams = httpParams.set('etat', params.etat);
    if (params.codStrc != null) httpParams = httpParams.set('codStrc', params.codStrc);
    if (params.dateDebut) httpParams = httpParams.set('dateDebut', params.dateDebut);
    if (params.dateFin) httpParams = httpParams.set('dateFin', params.dateFin);

    return this.http.get<Interim[]>(
      `${API_URL}${this.basePath}/search`,
      this.options(httpParams)
    );
  }

  // ─── Active checks ───────────────────────────────────────────────────
  getActiveBySource(matricule: number): Observable<Interim[]> {
    return this.http.get<Interim[]>(
      `${API_URL}${this.basePath}/active/source/${matricule}`,
      this.options()
    );
  }

  getActiveByCible(matricule: number): Observable<Interim[]> {
    return this.http.get<Interim[]>(
      `${API_URL}${this.basePath}/active/cible/${matricule}`,
      this.options()
    );
  }

  // ─── Statistics ───────────────────────────────────────────────────────
  getStats(): Observable<InterimStats> {
    return this.http.get<InterimStats>(
      `${API_URL}${this.basePath}/stats`,
      this.options()
    );
  }

  // ─── Profile tracking ────────────────────────────────────────────────
  getGrantedProfiles(interimId: number): Observable<InterimProfilGranted[]> {
    return this.http.get<InterimProfilGranted[]>(
      `${API_URL}${this.basePath}/${interimId}/granted-profiles`,
      this.options()
    );
  }

  getBackedUpProfiles(interimId: number): Observable<InterimProfilBackup[]> {
    return this.http.get<InterimProfilBackup[]>(
      `${API_URL}${this.basePath}/${interimId}/backed-up-profiles`,
      this.options()
    );
  }
}
