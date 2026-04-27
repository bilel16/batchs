// src/app/core/guards/login.guard.ts
import { Injectable, inject } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthSignalService } from '../services/frontend/auth-signal.service';

@Injectable({
  providedIn: 'root',
})
export class LoginGuard implements CanActivate {
  private readonly auth = inject(AuthSignalService);
  private readonly router = inject(Router);

  canActivate(): boolean {
    if (this.auth.isLoggedIn()) {
      console.log(
        '✅ LoginGuard: User already authenticated, redirecting to dashboard...'
      );
      this.router.navigate(['/dashboard']);
      return false;
    }

    console.log(
      '❌ LoginGuard: User NOT authenticated, showing login page'
    );
    return true;
  }
}