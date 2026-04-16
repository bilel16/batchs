import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthSignalService } from '../services/frontend/auth-signal.service';

export const loginGuard: CanActivateFn = () => {
  const auth = inject(AuthSignalService);
  const router = inject(Router);

  if (auth.isLoggedIn()) {
    router.navigate(['/dashboard']);
    return false;
  }

  return true;
};
