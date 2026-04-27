import { Component, NgZone, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { NextConfig } from './core/configuration/NextConfig';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
  title = 'bna-habilitation';

  public flatConfig: any;
  public navCollapsed: boolean;
  public navCollapsedMob: boolean;
  public windowWidth: number;

  constructor(private location: Location) {
    this.flatConfig = NextConfig.config;
    let currentURL = this.location.path();
    const baseHerf = (this.location as any)['_baseHref'];
    if (baseHerf) {
      currentURL = baseHerf + this.location.path();
    }

    this.windowWidth = window.innerWidth;

    if (currentURL === baseHerf + '/layout/collapse-menu' || currentURL === baseHerf + '/layout/box' || (this.windowWidth >= 992 && this.windowWidth <= 1024)) {
      this.flatConfig.collapseMenu = true;
    }

    this.navCollapsed = this.windowWidth >= 992 ? this.flatConfig.collapseMenu : false;
    this.navCollapsedMob = false;
  }

  ngOnInit() {
    if (this.windowWidth < 992) {
      this.flatConfig.layout = 'vertical';
      setTimeout(() => {
        document.querySelector('.pcoded-navbar')!.classList.add('menupos-static');
        (document.querySelector('#nav-ps-flat-able') as HTMLElement).style.maxHeight = '100%';
      }, 500);
    }
  }

  navMobClick() {
    if (this.windowWidth < 992) {
      if (this.navCollapsedMob && !document.querySelector('app-navigation.pcoded-navbar')!.classList.contains('mob-open')) {
        this.navCollapsedMob = !this.navCollapsedMob;
        setTimeout(() => {
          this.navCollapsedMob = !this.navCollapsedMob;
        }, 100);
      } else {
        this.navCollapsedMob = !this.navCollapsedMob;
      }
    }
  }

  isLoginUrl(): boolean {
    const currentUrl = window.location.href;
    const loginPaths = ['/login'];
    return loginPaths.some((path) => currentUrl.includes(path));
  }
  isLoginOrDashboardUrl(): boolean {
    const currentUrl = window.location.href;
    const loginPaths = ['/login', '/dashboard'];
    return loginPaths.some((path) => currentUrl.includes(path));
  }

  // ==================== ENHANCED TOAST METHODS ====================

  /**
   * Get appropriate icon for toast message based on severity
   */
  getToastIcon(severity: string): string {
    const iconMap: { [key: string]: string } = {
      'success': 'pi pi-check-circle',
      'error': 'pi pi-times-circle',
      'warn': 'pi pi-exclamation-triangle',
      'info': 'pi pi-info-circle',
      'custom': 'pi pi-cog'
    };
    return iconMap[severity] || 'pi pi-info-circle';
  }

  /**
   * Format timestamp for toast messages
   */
  formatTimestamp(timestamp: string): string {
    if (!timestamp) return '';
    
    try {
      const date = new Date(timestamp);
      return date.toLocaleTimeString('fr-FR', {
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
      });
    } catch (error) {
      return '';
    }
  }

  /**
   * Execute custom actions from toast messages
   */
  executeToastAction(action: any): void {
    if (!action || !action.callback) return;
    
    try {
      if (typeof action.callback === 'function') {
        action.callback();
      } else if (typeof action.callback === 'string') {
        // Handle string-based callbacks (e.g., route navigation)
        console.log('Executing toast action:', action.callback);
      }
    } catch (error) {
      console.error('Error executing toast action:', error);
    }
  }
}
