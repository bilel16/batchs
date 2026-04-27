import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import {
  BatchRequest,
  BatchExecutionDto,
  DashboardStats,
  ConsultationResult,
  AuditLog,
} from '../../models/batch.model';

@Injectable({ providedIn: 'root' })
export class BatchService {
  private readonly http = inject(HttpClient);
  private readonly base = environment.apiURL;

  // ─── Batch operations ───────────────────────────────────────────────
  launch(request: BatchRequest): Observable<BatchExecutionDto> {
    return this.http.post<BatchExecutionDto>(`${this.base}api/batch/launch`, request);
  }

  getStatus(executionId: string): Observable<BatchExecutionDto> {
    return this.http.get<BatchExecutionDto>(`${this.base}api/batch/status/${executionId}`);
  }

  stop(executionId: string): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(`${this.base}api/batch/${executionId}/stop`, {});
  }

  getHistory(instrument?: string): Observable<BatchExecutionDto[]> {
    let params = new HttpParams();
    if (instrument) params = params.set('instrument', instrument);
    return this.http.get<BatchExecutionDto[]>(`${this.base}api/batch/history`, { params });
  }

  // ─── Consultation ────────────────────────────────────────────────────
  getDashboardStats(): Observable<DashboardStats> {
    return this.http.get<DashboardStats>(`${this.base}api/consultation/stats/dashboard`);
  }

  search(filters: {
    dateStart?: string;
    dateEnd?: string;
    instrument?: string;
    sens?: string;
    valeur?: string;
    agence?: string;
  }): Observable<ConsultationResult[]> {
    let params = new HttpParams();
    Object.entries(filters).forEach(([k, v]) => {
      if (v) params = params.set(k, v);
    });
    return this.http.get<ConsultationResult[]>(`${this.base}api/consultation/search`, { params });
  }

  // ─── Audit ───────────────────────────────────────────────────────────
  getAuditLogs(filters: { user?: string; action?: string } = {}): Observable<AuditLog[]> {
    let params = new HttpParams();
    if (filters.user) params = params.set('user', filters.user);
    if (filters.action) params = params.set('action', filters.action);
    return this.http.get<AuditLog[]>(`${this.base}api/audit/logs`, { params });
  }
}
