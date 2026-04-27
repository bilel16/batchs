import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

/**
 * Breadcrumb context information
 */
export interface BreadcrumbContext {
  route: string;
  context?: string;
}

/**
 * Service for managing breadcrumb context across the application
 * 
 * This service allows components to set context information that the breadcrumb
 * component can use to display dynamic breadcrumbs. This is particularly useful
 * for pages with tabs or multiple views where the breadcrumb should reflect
 * the current active view.
 * 
 * @example
 * ```typescript
 * // In a component with tabs
 * constructor(private breadcrumbContext: BreadcrumbContextService) {}
 * 
 * onTabChange(tab: string) {
 *   this.breadcrumbContext.setContext('/my-route', tab);
 * }
 * ```
 * 
 * @author BNA HABIL Development Team
 * @version 1.0.0
 * @since 2025-12-29
 */
@Injectable({
  providedIn: 'root'
})
export class BreadcrumbContextService {
  private contextSubject = new BehaviorSubject<BreadcrumbContext>({ route: '/' });
  
  /**
   * Observable stream of breadcrumb context changes
   */
  public context$: Observable<BreadcrumbContext> = this.contextSubject.asObservable();
  
  /**
   * Set the breadcrumb context for a specific route
   * 
   * @param route - The route path
   * @param context - Optional context information (e.g., 'profiles', 'packs')
   */
  setContext(route: string, context?: string): void {
    console.log('🔖 Breadcrumb context updated:', { route, context });
    this.contextSubject.next({ route, context });
  }
  
  /**
   * Get the current breadcrumb context
   * 
   * @returns The current context
   */
  getContext(): BreadcrumbContext {
    return this.contextSubject.value;
  }
  
  /**
   * Clear the breadcrumb context (reset to default)
   */
  clearContext(): void {
    this.contextSubject.next({ route: '/' });
  }
}
