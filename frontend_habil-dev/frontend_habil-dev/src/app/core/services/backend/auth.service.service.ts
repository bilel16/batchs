import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../../models/user';
import { map, retry } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { AuthSignalService } from '../frontend/auth-signal.service';

@Injectable({
  providedIn: 'root',
})

export class AuthService {
    private readonly http = inject(HttpClient);
  private readonly auth = inject(AuthSignalService);

  private userSubject: BehaviorSubject<User>;
  public user: Observable<User>;
  constructor( ) {
    this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('user')!));
    this.user = this.userSubject.asObservable();
  }
  public get userValue(): User {
    return this.userSubject.value;
  }

  login(username: any, password: any) {
    return this.http
      .post<User>(environment.apiURL + `authenticate`, {username, password})
      .pipe(
        map((response) => {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          this.handleTokenResponse(response);
          return response;
        })
      );
  }
    // ─── Private ───

private handleTokenResponse(response: any): void {
  // ✅ Token
  const token = response.accessToken || response.token;
  if (token) {
    this.auth.setToken(token);
  }

  // ✅ User (mapped correctly)
const mappedUser: User = {
  username: response.username,
  nom: response.nom,
  prenom: response.prenom,
  email: response.email,
  codeStructure: response.codeStructure,
  poste: response.poste,
  token: response.token,
  // ✅ store both
  roles: this.parseRoles(response.roles),           // ['HABIL_RH']
  rolesFull: typeof response.roles === 'string' ? JSON.parse(response.roles) : response.roles, // raw full roles
  id: undefined,
  password: undefined,
  role: undefined
};

  this.auth.setUser(mappedUser);

  // ✅ Menus from roles
  const menus = this.extractMenusFromFullRoles(response.rolesFull);

  this.auth.setMenus(menus );
}
private parseRoles(roles: any): string[] {
  try {
    if (Array.isArray(roles)) {
      return roles.map((r: any) => r.codeProfil || r);
    }

    if (typeof roles === 'string') {
      const parsed = JSON.parse(roles);
      return parsed.map((r: any) => r.codeProfil || r);
    }

    return [];
  } catch {
    return [];
  }
}
private extractMenusFromRoles(roles: any): string[] {
  const menus = new Set<string>();

  try {
    console.log("the extracted")
    const parsed = typeof roles === 'string' ? JSON.parse(roles) : roles;

    parsed.forEach((role: any) => {
      role.menus?.forEach((menu: any) => {
        if (menu.codeMenu) {
          menus.add(menu.codeMenu);
        }
      });
    });
  } catch {}

  return Array.from(menus);
}
private extractMenusFromFullRoles(rolesFull: any): string[] {
  const menus = new Set<string>();
  if (!rolesFull) return [];

  rolesFull.forEach((role: any) => {
    role.menus?.forEach((menu: any) => {
      if (menu.codeMenu) menus.add(menu.codeMenu);
    });
  });

  return Array.from(menus);
}
}
