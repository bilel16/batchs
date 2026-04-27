import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment.prod";
const API_URL = environment.apiURL;

@Injectable({
  providedIn: "root",
})
export class ChartService {
  api_key = sessionStorage.getItem("token");
  headers = new HttpHeaders({
    Authorization: `Bearer ${this.api_key}`,
    responseType: "text",
    //  'content-type': 'application/json'
  });

  requestOptions = { headers: this.headers };

  constructor(private http: HttpClient) {}

  fetchChartData(dataSet: string): Observable<any> {
    return this.http.get<any>(
      `${API_URL}` + "getCmplxDash",
      this.requestOptions
    );
  }
  getSimpleDash(): Observable<any> {
    return this.http.get<any>(
      `${API_URL}` + "getSimpleDash",
      this.requestOptions
    );
  }

  getCmplxDashBoard(): Observable<any> {
    return this.http.get<any>(
      `${API_URL}` + "getCmplxDashBoard",
      this.requestOptions
    );
  }
  getSaerchCriteria(serach): Observable<any> {
    return this.http.post<any>(
      `${API_URL}` + "getSaerchCriteria",
      serach,
      this.requestOptions
    );
  }
}
