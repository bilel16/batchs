import { Injectable, signal, computed, effect } from '@angular/core';
import { User } from '../../models/user.model';

const STORAGE_KEY = '__auth_batches';

interface AuthState {
  user: User | null;
  token: string | null;
  menus: string[];
}

@Injectable({ providedIn: 'root' })
export class AuthSignalService {
  // Core signals
  readonly user = signal<User | null>(null);
  readonly token = signal<string | null>(null);
  readonly menus = signal<string[]>([]);

  // Derived state
  readonly isLoggedIn = computed(() => !!(this.user() && this.token()));
  readonly roleCodes = computed(() => this.extractRoleCodes());
  readonly currentUsername = computed(() => this.user()?.username ?? '');

  constructor() {
    this.hydrateFromStorage();

    effect(() => {
      const state: AuthState = {
        user: this.user(),
        token: this.token(),
        menus: this.menus(),
      };
      try {
        if (state.token || state.user) {
          sessionStorage.setItem(STORAGE_KEY, JSON.stringify(state));
        } else {
          sessionStorage.removeItem(STORAGE_KEY);
        }
      } catch {
        // Storage unavailable — fail silently
      }
    });
  }

  setUser(user: User | null): void {
    this.user.set(user);
  }

  setToken(token: string | null): void {
    this.token.set(token);
  }

  setMenus(menus: string[]): void {
    this.menus.set(Array.isArray(menus) ? menus : []);
  }

  clear(): void {
    this.user.set(null);
    this.token.set(null);
    this.menus.set([]);
    sessionStorage.removeItem(STORAGE_KEY);
  }

  hasRole(role: string): boolean {
    return this.roleCodes().includes(role);
  }

  private hydrateFromStorage(): void {
    try {
      const raw = sessionStorage.getItem(STORAGE_KEY);
      if (!raw) return;
      const parsed: AuthState = JSON.parse(raw);
      if (parsed.token) this.token.set(parsed.token);
      if (parsed.user) this.user.set(parsed.user);
      if (Array.isArray(parsed.menus)) this.menus.set(parsed.menus);
    } catch {
      try { sessionStorage.removeItem(STORAGE_KEY); } catch { /* ignore */ }
    }
  }

  private extractRoleCodes(): string[] {
    const u = this.user() as any;
    if (!u?.roles) return [];
    try {
      let roles = u.roles;
      if (typeof roles === 'string') roles = JSON.parse(roles);
      if (Array.isArray(roles)) {
        return roles
          .map((r: any) => r.codeProfil || r.authority || r)
          .filter((code: any) => typeof code === 'string' && code.length > 0);
      }
      return [];
    } catch {
      return [];
    }
  }
}
