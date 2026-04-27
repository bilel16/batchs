import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders,HttpBackend } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from '../../../../environments/environment';

const API_URL = environment.apiURL;
@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  private httpClientBackend: HttpClient;

  api_key = sessionStorage.getItem("token");
  headers = new HttpHeaders({
    'Authorization': `Bearer ${this.api_key}`,
    //'Content-type':'multipart/form-data'
  });

  requestOptions = {
    headers: this.headers,
    //skip: `${API_URL}upload`
  };

  constructor(private http:HttpClient, private handler: HttpBackend) {
    this.httpClientBackend = new HttpClient(handler);
  }

  upload(formData: FormData) : Observable<any> {
    return this.httpClientBackend.post<any>(
      `${API_URL}upload`,
      formData,
      this.requestOptions
      );
  }

  getFiles(filename:string): Observable<any> {
    return this.http.get(`${API_URL}download/`+filename, {  headers: this.headers , responseType: 'blob' } );
  }


  uploadFileToSeedoc(formData: FormData): Observable<any> {
    return this.httpClientBackend.post<any>(
      `${API_URL}upload-file-to-seedoc`,
      formData,{  headers: this.headers,  responseType: 'json'  // Use 'text' as 'json' for type compatibility
      }
    );
}


}
