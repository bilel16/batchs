import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { AuthSignalService } from '../frontend/auth-signal.service';
import { User } from '../../models/user.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly auth = inject(AuthSignalService);

  login(username: string, password: string): Observable<any> {
    return this.http
      .post<any>(environment.apiURL + 'authenticate', { username, password })
      .pipe(
        map((response) => {
          this.handleTokenResponse(response);
          return response;
        })
      );
  }

  logout(): void {
    this.auth.clear();
  }

  private handleTokenResponse(response: any): void {
    const token = response.accessToken || response.token;
    if (token) {
      this.auth.setToken(token);
    }

    const mappedUser: User = {
      username: response.username,
      nom: response.nom,
      prenom: response.prenom,
      email: response.email,
      codeStructure: response.codeStructure,
      poste: response.poste,
      token: response.token,
      roles: this.parseRoles(response.roles),
      rolesFull: typeof response.roles === 'string'
        ? JSON.parse(response.roles)
        : response.roles,
    };

    this.auth.setUser(mappedUser);

    const menus = this.extractMenus(response.roles);
    this.auth.setMenus(menus);
  }

  private parseRoles(roles: any): string[] {
    try {
      if (Array.isArray(roles)) return roles.map((r: any) => r.codeProfil || r);
      if (typeof roles === 'string') {
        const parsed = JSON.parse(roles);
        return parsed.map((r: any) => r.codeProfil || r);
      }
      return [];
    } catch {
      return [];
    }
  }

  private extractMenus(roles: any): string[] {
    const menus = new Set<string>();
    try {
      const parsed = typeof roles === 'string' ? JSON.parse(roles) : roles;
      if (Array.isArray(parsed)) {
        parsed.forEach((role: any) => {
          role.menus?.forEach((menu: any) => {
            if (menu.codeMenu) menus.add(menu.codeMenu);
          });
        });
      }
    } catch { /* ignore */ }
    return Array.from(menus);
  }
}
