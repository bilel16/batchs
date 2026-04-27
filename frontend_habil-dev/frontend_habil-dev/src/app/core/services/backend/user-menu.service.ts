import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { UtilisateurProfil } from "../../models/utilisateurprofil";
import {environment} from '../../../../environments/environment';
const API_URL = environment.apiURL;

@Injectable()
export class UserMenuService {
  constructor(private http: HttpClient) {}

  getProfilMenuApplicationList(): Observable<any> {
    let api_key = sessionStorage.getItem("token");
    let headers = new HttpHeaders({
      Authorization: `Bearer ${api_key}`,
      responseType: "text",
      //  'content-type': 'application/json'
    });
    const requestOptions = { headers: headers };

    return this.http.get(
      `${API_URL}` + "getProfilMenuApplicationList",

      requestOptions
    );
  }
  getMenuApplicationList(): Observable<any> {
    let api_key = sessionStorage.getItem("token");
    let headers = new HttpHeaders({
      Authorization: `Bearer ${api_key}`,
      responseType: "text",
      //  'content-type': 'application/json'
    });
    const requestOptions = { headers: headers };

    return this.http.get(
      `${API_URL}` + "getMenuApplicationList",

      requestOptions
    );
  }

  ajoutUtilisateurProfil(
    utilisateurProfil: UtilisateurProfil
  ): Observable<any> {
    let api_key = sessionStorage.getItem("token");
    let headers = new HttpHeaders({
      Authorization: `Bearer ${api_key}`,
      responseType: "text",
      //  'content-type': 'application/json'
    });
    const requestOptions = { headers: headers };

    return this.http.post(
      `${API_URL}` + "ajoutUtilisateurProfil/",
      utilisateurProfil,
      requestOptions
    );
  }
}
