import { Injectable, inject } from '@angular/core';
import { Subject } from 'rxjs';
import { AuthSignalService } from './auth-signal.service';
import { User } from '../../models/user';

/**
 * Backwards-compatible adapter around the new signal-based auth store.
 *
 * IMPORTANT: keep this service's public API stable to avoid breaking the app.
 * Storage is no longer the source-of-truth; persistence is handled by `AuthSignalService`.
 */
@Injectable({ providedIn: 'root' })
export class TokenStorageService {
  private readonly auth = inject(AuthSignalService);

  /** Compatibility stream used by some components to refresh header/user UI. */
  private readonly userUpdatedSubject = new Subject<User | null>();
  readonly userUpdated$ = this.userUpdatedSubject.asObservable();

  signOut(): void {
    this.auth.clear();
    this.userUpdatedSubject.next(null);
  }

  public saveToken(token: string): void {
    this.auth.setToken(token);
  }

  public getToken(): string | null {
    return this.auth.token();
  }

  public saveUser(user: User): void {
    this.auth.setUser(user);
    this.userUpdatedSubject.next(user);
  }

  public getUser(): User | null {
    return this.auth.user();
  }

  /**
   * Compatibility helper used in some templates/components.
   * Prefer reading `AuthSignalService.currentMatricule()` in new code.
   */
  public getCurrentUserMatricule(): string {
    return this.auth.currentMatricule();
  }

  /**
   * Compatibility helper used by some admin-only screens.
   * Prefer reading `AuthSignalService.hasAdminRole()` in new code.
   */
  public hasAdminRole(): boolean {
    return this.auth.hasAdminRole();
  }
}