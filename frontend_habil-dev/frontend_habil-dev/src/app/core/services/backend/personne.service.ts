import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import {environment} from '../../../../environments/environment';
import {ResponseSAGA} from '../../models/response-saga.interface';
import {PersonnelDetailsDto} from '../../models/personnel-detail';
import {PersonnelDto} from '../../models/personnel';

const API_URL = environment.apiURL;

@Injectable({
  providedIn: "root"
})
export class PersonneService {
  constructor(private http: HttpClient) {}

  getAllPersonne(): Observable<any> {
    let api_key = sessionStorage.getItem("token");
    let headers = new HttpHeaders({
      Authorization: `Bearer ${api_key}`,
      responseType: "text",
      //  'content-type': 'application/json'
    });
    const requestOptions = { headers: headers };

    return this.http.get(
      `${API_URL}` + "getAllPersonne",

      requestOptions
    );
  }

  getPersonneById(numSeqPers: number): Observable<any> {
    let api_key = sessionStorage.getItem("token");
    let headers = new HttpHeaders({
      Authorization: `Bearer ${api_key}`,
      responseType: "text",
      //  'content-type': 'application/json'
    });
    const requestOptions = { headers: headers };

    return this.http.get(
      `${API_URL}` + "getPersonneById" + `/${numSeqPers}`,

      requestOptions
    );
  }

  createPersonnel(dto: PersonnelDto): Observable<ResponseSAGA<PersonnelDetailsDto>> {
    const api_key = sessionStorage.getItem("token");
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${api_key}`,
      'Content-Type': 'application/json'
    });
    const requestOptions = { headers: headers };

    return this.http.post<ResponseSAGA<PersonnelDetailsDto>>(
      `${API_URL}personnel/create`,
      dto,
      requestOptions
    );
  }

  // Get all personnel details
  getAllPersonnelDetails(): Observable<any> {
    const api_key = sessionStorage.getItem("token");
    const headers = new HttpHeaders({
      Authorization: `Bearer ${api_key}`,
      responseType: "json"
    });
    const requestOptions = { headers: headers };

    return this.http.get(`${API_URL}personnel/all-details`, requestOptions);
  }

  // Get personnel page with pagination
  getPersonnelPage(page: number = 0, size: number = 20): Observable<any> {
    const api_key = sessionStorage.getItem("token");
    const headers = new HttpHeaders({
      Authorization: `Bearer ${api_key}`,
      responseType: "json"
    });
    const requestOptions = { headers: headers, params: { page: page.toString(), size: size.toString() } };

    return this.http.get(`${API_URL}personnel/page`, requestOptions);
  }

  // Update personnel by mat
  updatePersonnel(mat: string, dto: any): Observable<any> {
    const api_key = sessionStorage.getItem("token");
    const headers = new HttpHeaders({
      Authorization: `Bearer ${api_key}`,
      'Content-Type': 'application/json',
      responseType: "json"
    });
    const requestOptions = { headers: headers };

    return this.http.put(`${API_URL}personnel/${mat}`, dto, requestOptions);
  }

}
