import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/backend/auth.service';
import { AuthSignalService } from '../../core/services/frontend/auth-signal.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
  standalone: false,
})
export class DashboardComponent {
  private readonly authService = inject(AuthService);
  private readonly auth = inject(AuthSignalService);
  private readonly router = inject(Router);

  readonly user = this.auth.user;
  sidebarCollapsed = false;

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  toggleSidebar(): void {
    this.sidebarCollapsed = !this.sidebarCollapsed;
  }
}
