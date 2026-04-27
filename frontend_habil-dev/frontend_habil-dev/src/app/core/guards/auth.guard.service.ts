// src/app/core/guards/auth.guard.service.ts
import { Injectable, inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { AuthSignalService } from '../services/frontend/auth-signal.service';
import { NavigationAuthService } from '../services/frontend/navigation-auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  private readonly router = inject(Router);
  private readonly auth = inject(AuthSignalService);
  private readonly navigationAuth = inject(NavigationAuthService);

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const isLoggedIn = this.auth.isLoggedIn();

    console.log('AuthGuard - Checking authentication:', {
      isLoggedIn,
      hasToken: !!this.auth.token(),
      hasUser: !!this.auth.user(),
      requestedUrl: state.url,
    });

    if (!isLoggedIn) {
      console.warn('AuthGuard - Not authenticated, redirecting to login');
      this.router.navigate(['/login']);
      return false;
    }

    // Update navigation permissions based on current user
    this.navigationAuth.updateAllowedMenus();

    // Get allowed menu codes
    const allowedMenus = this.navigationAuth.getAllowedMenus();
    console.log('AuthGuard - Allowed menus:', allowedMenus);

    const routeTitle = route.data['title'];
    console.log(routeTitle);

    // Check if route requires specific permission
    if (routeTitle) {
      const hasPermission = allowedMenus.includes(routeTitle);
      console.log(
        `AuthGuard - Route '${routeTitle}' permission check:`,
        hasPermission
      );

      if (!hasPermission) {
        console.warn(`Access denied to route: ${routeTitle}`);
        this.router.navigate(['/']);
        return false;
      }
    }

    return true;
  }
}