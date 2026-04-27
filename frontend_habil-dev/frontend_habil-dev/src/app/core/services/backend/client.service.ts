import { Injectable } from "@angular/core";

import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import {environment} from '../../../../environments/environment';

const API_URL = environment.apiURL;

@Injectable({
  providedIn: "root",
})
export class ClientService {
  api_key = sessionStorage.getItem("token");
  headers = new HttpHeaders({
    Authorization: `Bearer ${this.api_key}`,
    responseType: "text",
    //  'content-type': 'application/json'
  });

  requestOptions = { headers: this.headers };

  constructor(private http: HttpClient) {}

  getClientById(numSeqPers: number): Observable<any> {
    return this.http.get<any>(
      `${API_URL}` + "getClientById" + `/${numSeqPers}`,
      this.requestOptions
    );
  }

  getClientByPiece(codeTypePiece: number, numPiece: string): Observable<any> {
    return this.http.get<any>(
      `${API_URL}` + "getClientByPiece" + `/${codeTypePiece}` + `/${numPiece}`,
      this.requestOptions
    );
  }

  getClientByCompte(
    strc: string,
    codPrd: string,
    numCcpt: string
  ): Observable<any> {
    return this.http.get<any>(
      `${API_URL}` +
        "getClientByCompte" +
        `/${strc}` +
        `/${codPrd}` +
        `/${numCcpt}`,
      this.requestOptions
    );
  }

  getClientByGarantieId(garantieId: number): Observable<any> {
    return this.http.get<any>(
      `${API_URL}` + "getClientByGarantieId" + `/${garantieId}`,
      this.requestOptions
    );
  }

  checkPerimetreClient(
    codeStructure: number,
    numSeqPers: number
  ): Observable<any> {
    return this.http.get<any>(
      `${API_URL}` +
        "checkPerimetreClient" +
        `/${codeStructure}` +
        `/${numSeqPers}`,
      this.requestOptions
    );
  }
}
