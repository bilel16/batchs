// import { HttpClient, HttpParams } from "@angular/common/http";
// import { Injectable } from "@angular/core";
// import { Observable } from "rxjs";
// import { map } from 'rxjs/operators';
// import { environment } from '../../../../environments/environment';
// import { ProfilMenuApplication } from '../../models/profilmenuapplication';
// import { ResponseSAGA } from "../../../features/administration/profilmenuapplication/models";

// const API_URL = environment.apiURL+"api/";


// /**
//  * Batch operation result
//  */
// interface BatchOperationResult<T> {
//   successful: T[];
//   failed: Array<{
//     index: number;
//     identifier: string;
//     error: string;
//   }>;
//   mode: 'ALL_OR_NOTHING' | 'BEST_EFFORT';
// }

// @Injectable()
// export class ProfilMenuService {
//   private readonly baseUrl = `${API_URL}profil-menu-applications`;

//   constructor(private http: HttpClient) {}

//   /**
//    * Get all profil menu applications
//    * OLD: GET /getAllProfilMenuApplicationList
//    * NEW: GET /api/profil-menu-applications
//    */
//   getAllProfilMenuApplication(): Observable<any> {
//     return this.http.get<ResponseSAGA<ProfilMenuApplication[]>>(this.baseUrl).pipe(
//       map(response => {
//         // Transform new response format to match old format
//         return {
//           code: response.code,
//           message: response.message,
//           data: response.data
//         };
//       })
//     );
//   }

//   /**
//    * Get profil menu applications by application code
//    * OLD: GET /getProfilMenuApplicationList/{codAppApp}
//    * NEW: GET /api/profil-menu-applications/by-application/{codAppApp}
//    */
//   getProfilMenuApplicationList(codAppApp: string | null): Observable<any> {
//     if (!codAppApp) {
//       return this.getAllProfilMenuApplication();
//     }
    
//     return this.http.get<ResponseSAGA<ProfilMenuApplication[]>>(
//       `${this.baseUrl}/by-application/${codAppApp}`
//     ).pipe(
//       map(response => ({
//         code: response.code,
//         message: response.message,
//         data: response.data
//       }))
//     );
//   }

//   /**
//    * Update profil menu application
//    * OLD: POST /updateProfilMenuApp
//    * NEW: PUT /api/profil-menu-applications/{codApp}/{codMenu}/{codPfl}/{codTstrc}
//    */
//   updateProfilMenuApplication(profilMenuApplication: ProfilMenuApplication): Observable<any> {
//     return this.http.put<ResponseSAGA<ProfilMenuApplication>>(
//       `${this.baseUrl}/${profilMenuApplication.codAppApp}/${profilMenuApplication.codMenuMenu}/${profilMenuApplication.codPflPfl}/${profilMenuApplication.codTstrcTstrc}`,
//       profilMenuApplication
//     ).pipe(
//       map(response => ({
//         code: response.code,
//         message: response.message,
//         data: response.data
//       }))
//     );
//   }

//   /**
//    * Add list of profil menu applications (Batch create)
//    * OLD: POST /ajoutListProfilMenuApplication
//    * NEW: POST /api/profil-menu-applications/batch
//    */
//   ajoutListProfilMenuApplication(profilMenuApplicationList: ProfilMenuApplication[]): Observable<any> {
//     const params = new HttpParams().set('mode', 'ALL_OR_NOTHING');
    
//     return this.http.post<ResponseSAGA<BatchOperationResult<ProfilMenuApplication>>>(
//       `${this.baseUrl}/batch`,
//       profilMenuApplicationList,
//       { params }
//     ).pipe(
//       map(response => {
//         // Check if batch operation was successful
//         const result = response.data;
        
//         if (result.failed && result.failed.length > 0) {
//           // Return error format if there were failures
//           return {
//             code: 1,
//             message: `${result.failed.length} items failed to create`,
//             data: result.failed
//           };
//         }
        
//         // Return success format
//         return {
//           code: 0,
//           message: response.message,
//           data: result.successful
//         };
//       })
//     );
//   }

//   /**
//    * Add single profil menu application
//    * OLD: POST /ajoutProfilMenuApplication
//    * NEW: POST /api/profil-menu-applications
//    */
//   ajoutProfilMenuApplication(profilMenuApplication: ProfilMenuApplication): Observable<any> {
//     return this.http.post<ResponseSAGA<ProfilMenuApplication>>(
//       this.baseUrl,
//       profilMenuApplication
//     ).pipe(
//       map(response => ({
//         code: response.code,
//         message: response.message,
//         data: response.data
//       }))
//     );
//   }

//   /**
//    * Delete profil menu application
//    * OLD: DELETE /deleteProfilMenuApp/{codMenuMenu}/{codPflPfl}/{codTstrcTstrc}
//    * NEW: DELETE /api/profil-menu-applications/{codApp}/{codMenu}/{codPfl}/{codTstrc}
//    * 
//    * NOTE: Missing codAppApp parameter in old API - you'll need to provide it
//    */
//   deleteProfilMenuApplication(
//     codMenuMenu: string, 
//     codPflPfl: string, 
//     codTstrcTstrc: string,
//     codAppApp?: string  // Optional parameter for backward compatibility
//   ): Observable<any> {
    
//     // If codAppApp is not provided, throw error (or handle gracefully)
//     if (!codAppApp) {
//       console.error('codAppApp is required for delete operation in new API');
//       // You might need to get this from somewhere else or modify component calls
//       throw new Error('codAppApp parameter is required');
//     }

//     return this.http.delete<ResponseSAGA<string>>(
//       `${this.baseUrl}/${codAppApp}/${codMenuMenu}/${codPflPfl}/${codTstrcTstrc}`
//     ).pipe(
//       map(response => ({
//         code: response.code,
//         message: response.message,
//         data: response.data
//       }))
//     );
//   }

//   // ==================== NEW METHODS (Optional - for advanced features) ====================

//   /**
//    * Get user roles for application (NEW FEATURE)
//    */
//   getUserRoles(matricule: string, appCode: string): Observable<any> {
//     return this.http.get<ResponseSAGA<any>>(
//       `${this.baseUrl}/user/${matricule}/app/${appCode}/roles`
//     ).pipe(
//       map(response => ({
//         code: response.code,
//         message: response.message,
//         data: response.data
//       }))
//     );
//   }

//   /**
//    * Save user roles for application (NEW FEATURE)
//    */
//   saveUserRoles(matricule: string, appCode: string, roles: any[]): Observable<any> {
//     return this.http.post<ResponseSAGA<any>>(
//       `${this.baseUrl}/user/${matricule}/app/${appCode}/roles`,
//       { roles }
//     ).pipe(
//       map(response => ({
//         code: response.code,
//         message: response.message,
//         data: response.data
//       }))
//     );
//   }

//   /**
//    * Batch create with BEST_EFFORT mode (NEW FEATURE)
//    * Continues creating even if some fail
//    */
//   ajoutListProfilMenuApplicationBestEffort(profilMenuApplicationList: ProfilMenuApplication[]): Observable<any> {
//     const params = new HttpParams().set('mode', 'BEST_EFFORT');
    
//     return this.http.post<ResponseSAGA<BatchOperationResult<ProfilMenuApplication>>>(
//       `${this.baseUrl}/batch`,
//       profilMenuApplicationList,
//       { params }
//     ).pipe(
//       map(response => {
//         const result = response.data;
        
//         return {
//           code: result.failed.length > 0 ? 207 : 0, // 207 = Multi-Status
//           message: `${result.successful.length} succeeded, ${result.failed.length} failed`,
//           data: {
//             successful: result.successful,
//             failed: result.failed
//           }
//         };
//       })
//     );
//   }
// }