import { computed, Injectable, signal } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { BehaviorSubject, Observable, of, throwError } from "rxjs";
import { catchError, map, tap   } from "rxjs/operators";
import { environment } from "../../../../environments/environment";

export interface User {
  username: string;
  email: string;
  fullName?: string;
  nom?: string;
  prenom?: string;
  codeStructure?: string;
  typeStructure?: string;
  libStructure?: string;
  codeBct?: string;
  structureMere?: string;
  applications?: {
    [appCode: string]: {
      codeApp: string;
      profilsDetail: Array<{
        codeProfil: string;
        menus: string[];
        ressources: any[];
      }>;
    };
  };
  // Legacy fields for backward compatibility
  id?: number;
  firstName?: string;
  lastName?: string;
  roles?: string[];
}

export interface TokenResponse {
  accessToken: string;
  refreshToken: string;
  tokenType: string;
  expiresIn: number;
  user: User;
  menus?: string[]; // Optional menus from SSO
}

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private currentUserSignal = signal<User | null>(null);
  private isAuthenticatedSignal = signal<boolean>(false);
  readonly currentUser = computed(() => this.currentUserSignal());
  readonly isAuthenticated = computed(() => this.isAuthenticatedSignal());
  private userSubject: BehaviorSubject<User>;
  public user: Observable<User>;


    private readonly ACCESS_TOKEN_KEY = 'access_token';
  private readonly REFRESH_TOKEN_KEY = 'refresh_token';
  private readonly USER_KEY = 'user';

  constructor(private http: HttpClient) {
    this.userSubject = new BehaviorSubject<User>(
      JSON.parse(localStorage.getItem("user"))
    );
    this.user = this.userSubject.asObservable();
  }
  //Edited
  /*getRole() {
    this.roleAs = sessionStorage.getItem("ROLE");
    return this.roleAs;
  }*/


  login(username, password) {
    return this.http
      .post<User>(environment.apiURL + `authenticate`, {
        username,
        password,
      })
      .pipe(
        map((user) => {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem("user", JSON.stringify(user));
          this.userSubject.next(user);
          return user;
        })
      );
  }

    private loadStoredUser(): void {
    const token = this.getAccessToken();
    const userStr = localStorage.getItem(this.USER_KEY);

    if (token && userStr && !this.isTokenExpired()) {
      try {
        const user = JSON.parse(userStr);
        this.currentUserSignal.set(user);
        this.isAuthenticatedSignal.set(true);
      } catch {
        this.clearStorage();
      }
    }
  }
  public get userValue(): User {
    return this.userSubject.value;
  }
getAccessToken(): string | null {
    return localStorage.getItem(this.ACCESS_TOKEN_KEY);
  }
  getRefreshToken(): string | null {
    return localStorage.getItem(this.REFRESH_TOKEN_KEY);
  }
 isTokenExpired(): boolean {
    const token = this.getAccessToken();
    if (!token) return true;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return Date.now() >= payload.exp * 1000;
    } catch {
      return true;
    }
  }    redirectToLogin(): void {
    console.log('🔄 Redirecting to portal login...');
    const loginUrl = `${environment.portalLogin}`;
    console.log('Target URL:', loginUrl);
    window.location.href = loginUrl;
  }
 redirectToPortal(): void {
    console.log('🏠 Redirecting to portal home...');
    window.location.href = `${environment.authUiUrl}/portal`;
  }
  hasRole(role: string): boolean {
    const user = this.currentUser();
    if (!user) return false;
    if (!user.roles) return false;
    return user.roles.includes(role) || user.roles.includes('ROLE_' + role);
  }    private clearStorage(): void {
    // Clear localStorage
    localStorage.removeItem(this.ACCESS_TOKEN_KEY);
    localStorage.removeItem(this.REFRESH_TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    
    // Clear sessionStorage
    sessionStorage.removeItem('access_token');
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('menus');
    
    console.log('✅ All storage cleared');
  }
checkSsoSession(): Observable<TokenResponse | null> {
    return this.http.get<any>(
      `${environment.authServerUrl}/api/session/validate?appId=${environment.appId}`,
      { withCredentials: true }
    ).pipe(
      tap(response => {
        if (response.valid) {
          this.handleTokenResponse({
            accessToken: response.accessToken,
            refreshToken: response.refreshToken,
            tokenType: 'Bearer',
            expiresIn: response.expiresIn,
            user: response.user
          });
        }
      }),
      catchError(error => {
        console.log('No valid SSO session');
        return of(null);
      })
    );
  }  
  exchangeCodeForTokens(code: string, redirectUri?: string): Observable<TokenResponse> {
    const finalRedirectUri = redirectUri || environment.callbackUrl || `${window.location.origin}/auth/callback`;
    const requestBody = {
      code: code,
      redirectUri: finalRedirectUri
    };

    console.log('📤 Sending token exchange request:', {
      endpoint: `${environment.authServerUrl}/api/auth/exchange-code`,
      code: `${code.substring(0, 8)}...`,
      redirectUri: finalRedirectUri
    });

    return this.http.post<TokenResponse>(
      `${environment.authServerUrl}/api/auth/exchange-code`,
      requestBody,
      { withCredentials: true }
    ).pipe(
      tap(response => {
        console.log('📥 Token exchange response received:', {
          hasAccessToken: !!response.accessToken,
          hasRefreshToken: !!response.refreshToken,
          hasUser: !!response.user,
          tokenLength: response.accessToken?.length
        });
        this.handleTokenResponse(response);
      }),
      catchError(error => {
        console.error('❌ Code exchange failed:', {
          status: error.status,
          statusText: error.statusText,
          url: error.url,
          message: error.message,
          error: error.error
        });
        return throwError(() => error);
      })
    );
  }
private handleTokenResponse(response: TokenResponse): void {
    console.log('💾 Saving tokens and user data...');
    
    // Save to localStorage for AuthService
    localStorage.setItem(this.ACCESS_TOKEN_KEY, response.accessToken);
    localStorage.setItem(this.REFRESH_TOKEN_KEY, response.refreshToken);
    localStorage.setItem(this.USER_KEY, JSON.stringify(response.user));
    
    // Save to sessionStorage for AuthGuard/TokenStorageService (KEY MUST MATCH 'access_token')
    sessionStorage.setItem('access_token', response.accessToken);
    sessionStorage.setItem('user', JSON.stringify(response.user));
    
    console.log('✅ Tokens saved:', {
      localStorage: !!localStorage.getItem('access_token'),
      sessionStorage: !!sessionStorage.getItem('access_token')
    });
    
    // Debug: Log user object structure
    console.log('🔍 User object structure:', {
      username: response.user.username,
      hasApplications: !!response.user.applications,
      applicationCodes: response.user.applications ? Object.keys(response.user.applications) : [],
      fullUserObject: response.user
    });
    
    // Extract and flatten all menus from user.applications
    const allMenus = this.extractMenusFromUser(response.user);
      // Handle menus - prefer SSO provided menus, then extract from user, or use default
    let menusToSave: string[];
    if (response.menus && response.menus.length > 0) {
      menusToSave = response.menus;
      console.log('✅ Menus from SSO response:', menusToSave);
    } else if (allMenus.length > 0) {
      menusToSave = allMenus;
      console.log('✅ Menus extracted from user.applications:', menusToSave.length, 'unique menus');
    } else {
      // Set default menu code for testing
      menusToSave = ['PEC_ENV'];
      console.warn('⚠️ No menus found, using default:', menusToSave);
    }
    
    // Save menus to BOTH sessionStorage and localStorage
    const menusJson = JSON.stringify(menusToSave);
    sessionStorage.setItem('menus', menusJson);
    localStorage.setItem('menus', menusJson);
    console.log('✅ Menus saved to both storages:', menusToSave);
    
    this.currentUserSignal.set(response.user);
    this.isAuthenticatedSignal.set(true);
    
    console.log('✅ Authentication complete - ALL data saved to storage');
  }  
 logout(): Observable<any> {
    console.log('🚪 Logging out - calling auth server...');
    return this.http.post(`${environment.authServerUrl}/api/auth/logout`, {}, {
      withCredentials: true
    }).pipe(
      tap(() => {
        console.log('✅ Logout API call successful');
        this.handleLogout();
      }),
      catchError(error => {
        console.error('❌ Logout API call failed:', error);
        this.handleLogout(); // Still clear local data even if API fails
        return throwError(() => error);
      })
    );
  }
 private handleLogout(): void {
    console.log('🗑️ Clearing storage FIRST before redirect...');
    
    // Clear signals and subjects first
    this.currentUserSignal.set(null);
    this.isAuthenticatedSignal.set(false);
    
    // Clear ALL storage synchronously
    localStorage.clear();
    sessionStorage.clear();
    
    console.log('✅ All storage cleared');
    console.log('🔍 Verification:', {
      localStorage_access_token: localStorage.getItem('access_token'),
      sessionStorage_access_token: sessionStorage.getItem('access_token'),
      localStorage_length: localStorage.length,
      sessionStorage_length: sessionStorage.length
    });
    
    // Small delay to ensure storage operations complete
    setTimeout(() => {
      console.log('🔄 Redirecting to portal login...');
      window.location.href = environment.portalLogin;
    }, 100);
  }

/**
   * Extract all unique menu codes from the user's applications
   */
  private extractMenusFromUser(user: User): string[] {
    const menusSet = new Set<string>();
    
    if (!user.applications) {
      console.warn('⚠️ No applications found in user object');
      return [];
    }
    
    // Iterate through all applications
    Object.keys(user.applications).forEach(appCode => {
      const app = user.applications![appCode];
      console.log(`📱 Application '${appCode}':`, {
        hasProfilsDetail: !!app.profilsDetail,
        profilsCount: app.profilsDetail?.length || 0
      });
      
      // Iterate through all profiles in the application
      app.profilsDetail?.forEach((profil, index) => {
        console.log(`  👤 Profile ${index + 1}:`, {
          codeProfil: profil.codeProfil,
          hasMenus: !!profil.menus,
          menusCount: profil.menus?.length || 0,
          menus: profil.menus || []
        });
        
        // Add all menus from this profile
        profil.menus?.forEach(menu => {
          menusSet.add(menu);
        });
      });
    });
    
    const uniqueMenus = Array.from(menusSet);
    console.log(`📋 Extracted ${uniqueMenus.length} unique menus from ${Object.keys(user.applications).length} applications:`, uniqueMenus);
    
    return uniqueMenus;
  }


}
