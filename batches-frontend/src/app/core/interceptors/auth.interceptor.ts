import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { AuthSignalService } from '../services/frontend/auth-signal.service';

const SKIP_AUTH_URLS = ['/authenticate'];

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthSignalService);
  const router = inject(Router);

  const shouldSkip = SKIP_AUTH_URLS.some((url) => req.url.includes(url));

  if (!shouldSkip) {
    const token = auth.token();
    if (token) {
      req = req.clone({
        setHeaders: { Authorization: `Bearer ${token}` },
      });
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
