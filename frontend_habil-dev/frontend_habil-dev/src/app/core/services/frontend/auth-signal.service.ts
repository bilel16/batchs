// src/app/core/services/auth-signal.service.ts
import { Injectable, signal, computed, effect } from '@angular/core';
import { User } from '../../models/user';

const STORAGE_KEY = '__auth';
const ADMIN_ROLES = ['SUPER_ADMIN_HABIL', 'HABIL_RH'] as const;

interface AuthState {
  user: User | null;
  token: string | null;
  menus: string[];
}

@Injectable({ providedIn: 'root' })
export class AuthSignalService {
  // ─── Core signals ───
  readonly user = signal<User | null>(null);
  readonly token = signal<string | null>(null);
  readonly menus = signal<string[]>([]);
  // ─── Derived state ───
  readonly isLoggedIn = computed(() => !!(this.user() && this.token()));
  readonly roleCodes = computed(() => this.extractRoleCodes());
  readonly hasAdminRole = computed(() =>
    this.roleCodes().some((r) => (ADMIN_ROLES as readonly string[]).includes(r))
  );
  readonly currentMatricule = computed(() => this.user()?.username ?? '');

  constructor() {
    // ─── Hydrate ONCE from storage ───
    this.hydrateFromStorage();

    // ─── Auto-persist via effect ───
    // Every time ANY signal changes, write the full state to storage
    effect(() => {
      const state: AuthState = {
        user: this.user(),
        token: this.token(),
        menus: this.menus(),
      };

      try {
        // Only write if there's something to persist
        if (state.token || state.user) {
          sessionStorage.setItem(STORAGE_KEY, JSON.stringify(state));
        } else {
          sessionStorage.removeItem(STORAGE_KEY);
        }
      } catch {
        // Storage full or unavailable — fail silently
      }
    });
  }

  // ─── Simple setters (no manual storage calls) ───

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
    // effect() will auto-remove the storage key since token & user are null
  }

  // ─── Role helpers ───

  hasRole(role: string): boolean {
    return this.roleCodes().includes(role);
  }

  // ─── Private ───

  private hydrateFromStorage(): void {
    try {
      var raw = sessionStorage.getItem(STORAGE_KEY);
      console.log("first time;; ",raw)
      if (!raw)
        {
           raw = localStorage.getItem('user');
           console.log("user::",raw)
           raw=converToAuthStateObj(raw);
          if (!raw){
            return;
          }
          
          } 

      const parsed: AuthState = JSON.parse(raw);

      if (parsed.token) {
        this.token.set(parsed.token);
      }

      if (parsed.user) {
        this.user.set(parsed.user);
      }

      if (Array.isArray(parsed.menus)) {
        this.menus.set(parsed.menus);
      }
   
  }catch {
      // Corrupted storage — start fresh
      try {
        sessionStorage.removeItem(STORAGE_KEY);
      } catch {
        // ignore
      }
    }
  }

  private extractRoleCodes(): string[] {
    const u = this.user() as any;
    if (!u?.roles) return [];

    try {
      let roles = u.roles;

      if (typeof roles === 'string') {
        roles = JSON.parse(roles);
      }

      if (Array.isArray(roles)) {
        return roles
          .map((r: any) => r.codeProfil || r.authority || r)
          .filter((code: any) => typeof code === 'string' && code.length > 0);
      }

      return [];
    } catch {
      if (typeof u.roles === 'string') {
        return u.roles
          .replace(/[\[\]]/g, '')
          .split(',')
          .map((r: string) => r.trim())
          .filter((r: string) => r.length > 0);
      }
      return [];
    }
  }
}

/**
 * Convert old user object (from localStorage) into new AuthState JSON string
 * expected by AuthSignalService.
 */
function converToAuthStateObj(raw: string | null): string | null {
  if (!raw) return null;

  try {
    const oldUser = JSON.parse(raw);

    // Normalize roles
    let rolesFull: any[] = [];
    let roles: string[] = [];

    if (oldUser.roles) {
      rolesFull = typeof oldUser.roles === 'string' ? JSON.parse(oldUser.roles) : oldUser.roles;
      roles = rolesFull.map((r: any) => r.codeProfil || r);
    }

    // Build new AuthState object
    const authState = {
      token: oldUser.token || null,
      user: {
        ...oldUser,
        roles,       // array of role codes
        rolesFull,   // full role objects with menus
      },
      menus: rolesFull
        ? rolesFull.flatMap((r: any) =>
            (r.menus || []).map((m: any) => m.codeMenu)
          )
        : [],
    };

    return JSON.stringify(authState);
  } catch (e) {
    console.error('Failed to convert old user to AuthState:', e);
    return null;
  }
}
