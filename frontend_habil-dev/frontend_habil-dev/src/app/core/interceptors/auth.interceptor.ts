// src/app/core/interceptors/auth.interceptor.ts
import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { AuthSignalService } from '../services/frontend/auth-signal.service';

const SKIP_AUTH_URLS = [
  '/authenticate',
  '/auth/exchange-code',
  '/auth/callback',
  '/session/validate',
];

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthSignalService);
  const router = inject(Router);
      console.log('[INTERCEPTOR] Authorization added for:', req.url);

console.log("interceptingg:",auth.token())
  // Skip auth header for public endpoints
  const shouldSkip = SKIP_AUTH_URLS.some((url) => req.url.includes(url));
console.log("shouldSkip:",shouldSkip)

  if (!shouldSkip) {
    const token = auth.token();
    if (token) {
      req = req.clone({
        setHeaders: { Authorization: `Bearer ${token}` },
      });
      console.log("req:",req)

    }
  }

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401 && !req.url.includes('authenticate')) {
        auth.clear();
        router.navigate(['/login']);
      }
      return throwError(() => error);
    })
  );
};