// src/app/core/services/navigation-auth.service.ts
import { Injectable, inject, effect } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { AuthSignalService } from './auth-signal.service';

@Injectable({
  providedIn: 'root',
})
export class NavigationAuthService {
  private readonly auth = inject(AuthSignalService);

  // Keep BehaviorSubject for templates using async pipe
  private readonly allowedMenusSubject = new BehaviorSubject<string[]>([]);
  public readonly allowedMenus$ = this.allowedMenusSubject.asObservable();

  constructor() {
    // ─── Bridge: signal → BehaviorSubject (automatic)
    effect(() => {
      const menus = this.auth.menus();
      this.allowedMenusSubject.next(menus);
    });
  }

  /**
   * Update allowed menu codes from user roles.
   * Now reads full roles from the user signal and updates menus signal.
   */
  updateAllowedMenus(): void {
    const currentUser = this.auth.user();
    if (!currentUser?.rolesFull) {
      // fallback: empty menus
      this.auth.setMenus([]);
      return;
    }

    const allowedMenuCodes: string[] = this.extractMenusFromFullRoles(currentUser.rolesFull);

    this.auth.setMenus(allowedMenuCodes.length > 0 ? allowedMenuCodes : ['PEC_ENV']);
    console.log('Updated menus signal:', this.auth.menus());
  }

  /**
   * Extract menus from full roles (rolesFull preserves menus from backend)
   */
  private extractMenusFromFullRoles(rolesFull: any[]): string[] {
    const menus = new Set<string>();

    if (!rolesFull || !Array.isArray(rolesFull)) return [];

    rolesFull.forEach((role) => {
      // each role may have a "menus" array
      role.menus?.forEach((menu: any) => {
        if (menu?.codeMenu) {
          menus.add(menu.codeMenu);
        }
      });
    });

    return Array.from(menus);
  }

  /**
   * Check if a menu item should be visible based on user roles
   */
  isMenuVisible(menuId: string): boolean {
    if (!menuId) return true;
    const allowedMenus = this.auth.menus();
    return allowedMenus.includes(menuId);
  }

  /**
   * Get current allowed menu codes
   */
  getAllowedMenus(): string[] {
    return this.auth.menus();
  }

  /**
   * Clear all menu permissions (for logout)
   */
  clearMenuPermissions(): void {
    this.auth.setMenus([]);
  }

  /**
   * Filter navigation items based on user permissions
   */
  filterNavigationItems(items: any[]): any[] {
    return items
      .map((item) => this.filterSingleItem(item))
      .filter((item) => item !== null);
  }

  /**
   * Recursively filter a single item
   */
  private filterSingleItem(item: any): any | null {
    const filteredItem = { ...item };

    // handle children first
    if (item.children && item.children.length > 0) {
      const visibleChildren = item.children
        .map((child: any) => this.filterSingleItem(child))
        .filter((child: any) => child !== null);

      if (visibleChildren.length === 0) return null;

      filteredItem.children = visibleChildren;
      return filteredItem;
    }

    // filter individual menu items
    if (item.type === 'item' && item.id) {
      return this.isMenuVisible(item.id) ? filteredItem : null;
    }

    return filteredItem;
  }
}