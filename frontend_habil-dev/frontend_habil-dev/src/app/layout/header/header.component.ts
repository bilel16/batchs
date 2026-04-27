import { Component, EventEmitter, OnInit, OnDestroy, Output, inject } from '@angular/core';
import { Router } from '@angular/router';
import { NextConfig } from '../../core/configuration/NextConfig';
import { MenuItem, MessageService } from 'primeng/api';
import { environment } from '../../../environments/environment';
import {NavigationItem} from '../../core/interfaces/navigation';
import {TokenStorageService} from '../../core/services/frontend/token-storage.service';
import {NavigationAuthService} from '../../core/services/frontend/navigation-auth.service';
import { NotificationService, NotificationItem } from '../../core/services/frontend/notification.service';
import { Subscription } from 'rxjs';
import { AuthSignalService } from '../../core/services/frontend/auth-signal.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
  standalone: false,
})
export class HeaderComponent implements OnInit, OnDestroy {
  public flatConfig: any;
  public menuClass: boolean;
  public collapseStyle: string;
  public windowWidth: number;
  date: any;
  user: any;
  nameAPP = environment.nameAPP;

  @Output() onNavCollapse = new EventEmitter();
  @Output() onNavHeaderMobCollapse = new EventEmitter();
  isMenuOpen = false;
  projectItems: MenuItem[] = [];
  // Notification panel state
  notifications: NotificationItem[] = [];
  alerts: NotificationItem[] = [];
  unreadNotificationsCount = 0;
  unreadAlertsCount = 0;
  expandedNotificationId: string | null = null;
  expandedAlertId: string | null = null;

  // Alert details modal state
  alertDetailsModalVisible = false;
  selectedAlertForModal: NotificationItem | null = null;

  private subscriptions: Subscription[] = [];
  private menus: string[] = [];
  private navigationItems: any[] = [];

   // ─── Injected services ───
  private readonly auth = inject(AuthSignalService);
  private readonly navigationAuth = inject(NavigationAuthService);
  private readonly router = inject(Router);
  private readonly messageService = inject(MessageService);
  private readonly navigation = inject(NavigationItem);
  public readonly notificationService = inject(NotificationService);

  constructor() {
    this.flatConfig = NextConfig.config;
    this.menuClass = false;
    this.collapseStyle = 'none';
    this.windowWidth = window.innerWidth;
  }
  ngOnInit() {
    const today = new Date();
    this.date = today.toISOString().split('T')[0];
    this.loadMenus();
    this.subscribeToNotifications();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  /**
   * Subscribe to notification service observables
   */
  private subscribeToNotifications(): void {
    this.subscriptions.push(
      this.notificationService.notifications$.subscribe(notifications => {
        this.notifications = notifications;
      }),
      this.notificationService.alerts$.subscribe(alerts => {
        this.alerts = alerts;
      }),
      this.notificationService.unreadNotificationsCount$.subscribe(count => {
        this.unreadNotificationsCount = count;
      }),
      this.notificationService.unreadAlertsCount$.subscribe(count => {
        this.unreadAlertsCount = count;
      })
    );
  }

  /**
   * Toggle notification details expansion
   */
  toggleNotificationDetails(id: string): void {
    this.expandedNotificationId = this.expandedNotificationId === id ? null : id;
    this.notificationService.markAsRead(id, 'notification');
  }

  /**
   * Toggle alert details expansion
   */
  toggleAlertDetails(id: string): void {
    this.expandedAlertId = this.expandedAlertId === id ? null : id;
    this.notificationService.markAsRead(id, 'alert');
  }

  /**
   * Remove a notification
   */
  removeNotification(id: string, event: Event): void {
    event.stopPropagation();
    this.notificationService.remove(id, 'notification');
  }

  /**
   * Remove an alert
   */
  removeAlert(id: string, event: Event): void {
    event.stopPropagation();
    this.notificationService.remove(id, 'alert');
  }

  /**
   * Mark all notifications as read
   */
  markAllNotificationsRead(): void {
    this.notificationService.markAllAsRead('notification');
  }
  /**
   * Mark all alerts as read
   */
  markAllAlertsRead(): void {
    this.notificationService.markAllAsRead('alert');
  }

  /**
   * Open the alert details modal
   */
  openAlertDetailsModal(alert: NotificationItem, event?: Event): void {
    if (event) {
      event.stopPropagation();
    }
    this.selectedAlertForModal = alert;
    this.alertDetailsModalVisible = true;
    // Mark as read when opening in modal
    this.notificationService.markAsRead(alert.id, 'alert');
  }

  /**
   * Handle remove alert from modal
   */
  onRemoveAlertFromModal(alertId: string): void {
    this.notificationService.remove(alertId, 'alert');
    this.alertDetailsModalVisible = false;
    this.selectedAlertForModal = null;
  }

  /**
   * Format timestamp for display
   */
  formatTimestamp(date: Date): string {
    const now = new Date();
    const diff = now.getTime() - date.getTime();
    const minutes = Math.floor(diff / 60000);
    const hours = Math.floor(diff / 3600000);
    const days = Math.floor(diff / 86400000);

    if (minutes < 1) return 'À l\'instant';
    if (minutes < 60) return `Il y a ${minutes} min`;
    if (hours < 24) return `Il y a ${hours}h`;
    if (days < 7) return `Il y a ${days}j`;
    return date.toLocaleDateString('fr-FR');
  }

  /**
   * Get severity icon
   */
  getSeverityIcon(severity: string): string {
    const icons: Record<string, string> = {
      success: 'pi pi-check-circle',
      info: 'pi pi-info-circle',
      warn: 'pi pi-exclamation-triangle',
      error: 'pi pi-times-circle'
    };
    return icons[severity] || 'pi pi-bell';
  }

  /**
   * Get severity color class
   */
  getSeverityClass(severity: string): string {
    return `severity-${severity}`;
  }

  private loadMenus(): void {
    //Read from signal (hydrated from storage by AuthSignalService)
    this.menus = this.auth.menus();
    this.navigationItems = this.navigation.get();
    this.buildPrimeNGMenu();
  }

  private buildPrimeNGMenu(): void {
    this.projectItems = [];

    this.navigationItems.forEach((group) => {
      if (group.type === 'group' && Array.isArray(group.children)) {
        group.children.forEach((item: any) => {
          const menuItem = this.convertToMenuItem(item);
          if (menuItem && !menuItem.visible) {
            return;
          }
          if (menuItem) {
            this.projectItems.push(menuItem);
          }
        });
      }
    });
  }

  private convertToMenuItem(navItem: any): MenuItem | null {
    if (navItem.type === 'collapse' && Array.isArray(navItem.children)) {
      const children: MenuItem[] = [];
      let hasVisibleChildren = false;

      navItem.children.forEach((child: any) => {
        const childMenuItem = this.convertToMenuItem(child);
        if (childMenuItem && childMenuItem.visible !== false) {
          children.push(childMenuItem);
          hasVisibleChildren = true;
        }
      });

      if (!hasVisibleChildren) {
        return null;
      }

      return {
        label: navItem.title,
        icon: navItem.icon,
        items: children,
        visible: true,
      };
    } else if (navItem.type === 'item') {
      const hasAccess = navItem.id && this.menus.includes(navItem.id);

      if (!hasAccess) {
        return null;
      }

      return {
        label: navItem.title,
        icon: navItem.icon,
        command: () => {
          if (navItem.url) {
            this.router.navigate([navItem.url]);
          }
        },
        visible: true,
      };
    }

    return null;
  }

  toggleMobOption() {
    this.menuClass = !this.menuClass;
    this.collapseStyle = this.menuClass ? 'block' : 'none';
  }

  navCollapse() {
    if (this.windowWidth >= 992) {
      this.onNavCollapse.emit();
    } else {
      this.onNavHeaderMobCollapse.emit();
    }
  }
  logout() {
     this.navigationAuth.clearMenuPermissions();
    this.auth.clear();
     localStorage.clear();
    this.router.navigate(['/login']);
  }

  logoutDialogVisible = false;

  confirmLogout() {
    this.logoutDialogVisible = true;
  }

  onLogoutReject() {
    this.logoutDialogVisible = false;
    this.messageService.add({
      severity: 'error',
      summary: 'Rejected',
      detail: 'You have rejected',
      life: 3000,
    });
  }
}
