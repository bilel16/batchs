// src/app/core/guards/root.guard.ts
import { Injectable, inject } from '@angular/core';
import {
  CanActivate,
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from '@angular/router';
import { AuthSignalService } from '../services/frontend/auth-signal.service';

/**
 * Root Guard
 *
 * Handles the root path (/) routing logic:
 * - If user is authenticated → redirect to dashboard
 * - If user is NOT authenticated → redirect to login
 */
@Injectable({
  providedIn: 'root',
})
export class RootGuard implements CanActivate {
  private readonly auth = inject(AuthSignalService);
  private readonly router = inject(Router);

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    if (this.auth.isLoggedIn()) {
      console.log(
        '✅ RootGuard: User authenticated, redirecting to dashboard'
      );
      this.router.navigate(['/dashboard']);
    } else {
      console.log(
        '❌ RootGuard: User NOT authenticated, redirecting to login'
      );
      this.router.navigate(['/login']);
    }

    // Always return false because we're redirecting
    return false;
  }
}