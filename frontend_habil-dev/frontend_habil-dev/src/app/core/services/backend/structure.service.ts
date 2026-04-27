import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import { Injectable } from "@angular/core";
import {Observable, throwError} from "rxjs";
import {environment} from '../../../../environments/environment';
import {SegmentStructureDto, StructureDto, StructureWithSegmentsDto} from '../../models/structure';
import {catchError} from 'rxjs/operators';

const API_URL = environment.apiURL;
@Injectable({
  providedIn: "root",
})
export class StructureService {
  api_key = sessionStorage.getItem("token");
  headers = new HttpHeaders({
    Authorization: `Bearer ${this.api_key}`,
    responseType: "text",
    //  'content-type': 'application/json'
  });

  requestOptions = { headers: this.headers };

  private baseUrl = `${API_URL}structure`;

  constructor(private http: HttpClient) {}

  getStructuresByType(codeTypeStrc: number): Observable<any> {
    return this.http.get(
      `${API_URL}` + "getStructuresByType" + `/${codeTypeStrc}`,
      this.requestOptions
    );
  }
  getStructuresById(codeTypeStrc: number): Observable<any> {
    return this.http.get(
      `${API_URL}` + "getStructuresById" + `/${codeTypeStrc}`,
      this.requestOptions
    );
  }

  getStructuresByTypeList(typeList: any): Observable<any> {
    return this.http.post(
      `${API_URL}` + "getStructuresByTypeList",
      typeList,
      this.requestOptions
    );
  }

  getAllStructuresWithSegments(): Observable<StructureWithSegmentsDto[]> {
    return this.http
      .get<StructureWithSegmentsDto[]>(`${this.baseUrl}/with-segments`)
      .pipe(catchError(this.handleError));
  }

  getStructureWithSegments(structureId: number): Observable<StructureWithSegmentsDto> {
    return this.http
      .get<StructureWithSegmentsDto>(`${this.baseUrl}/${structureId}/with-segments`)
      .pipe(catchError(this.handleError));
  }

  addSegmentToStructure(
    structureId: number,
    segmentCode: string
  ): Observable<SegmentStructureDto> {
    return this.http
      .post<SegmentStructureDto>(
        `${this.baseUrl}/${structureId}/segments/${segmentCode}`,
        {}
      )
      .pipe(catchError(this.handleError));
  }

  removeSegmentFromStructure(
    structureId: number,
    segmentCode: string
  ): Observable<void> {
    return this.http
      .delete<void>(`${this.baseUrl}/${structureId}/segments/${segmentCode}`)
      .pipe(catchError(this.handleError));
  }

  getUserStructure(userMatricule: string): Observable<StructureDto> {
    return this.http
      .get<StructureDto>(`${this.baseUrl}/user/${userMatricule}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An error occurred';

    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;

      if (error.error && error.error.message) {
        errorMessage = error.error.message;
      }
    }

    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }

}
