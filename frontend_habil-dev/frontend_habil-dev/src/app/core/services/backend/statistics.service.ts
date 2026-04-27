import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../../environments/environment';
import {ApplicationStats, PersonnelStats, ProfileStats, ResponseSaga} from '../../models/statistics';

const API_URL = environment.apiURL;

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  private baseUrl = environment.apiURL + 'statistics';

  constructor(private http: HttpClient) {}

  getStats(): Observable<ResponseSaga<PersonnelStats>> {
    return this.http.get<ResponseSaga<PersonnelStats>>(
      `${this.baseUrl}/personnel`
    );
  }

  getStructureLabels(structureIds: number[]): Observable<ResponseSaga<{ [key: number]: string }>> {
    return this.http.post<ResponseSaga<{ [key: number]: string }>>(
      `${this.baseUrl}/structure/labels`,
      structureIds
    );
  }

  getApplicationStats(): Observable<ResponseSaga<ApplicationStats>> {
    return this.http.get<ResponseSaga<ApplicationStats>>(
      `${this.baseUrl}/applications`
    );
  }

  getProfileStats(): Observable<ResponseSaga<ProfileStats>> {
    return this.http.get<ResponseSaga<ProfileStats>>(
      `${this.baseUrl}/profiles`
    );
  }
}
