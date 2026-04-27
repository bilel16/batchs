import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import {
  BREADCRUMB_MAP,
  BreadcrumbConfig,
} from '../../core/configuration/breadcrumb.config';
import { filter } from 'rxjs/operators';
import { Location } from '@angular/common';
import { Subscription } from 'rxjs';
import { BreadcrumbContextService } from '../../core/services/frontend/breadcrumb-context.service';

@Component({
  selector: 'app-breadcrumb',
  standalone: false,
  templateUrl: './breadcrumb.component.html',
  styleUrl: './breadcrumb.component.scss',
})
export class BreadcrumbComponent implements OnInit {
  currentPageTitle = '';
  currentPageIcon = '';
  items: any;
  home: any;
  private routerSub: Subscription | undefined;
  private contextSub: Subscription | undefined;
  private route = inject(ActivatedRoute);
  
  constructor(
    private router: Router, 
    private location: Location,
    private breadcrumbContext: BreadcrumbContextService
  ) {}
  ngOnInit() {
    // Subscribe to router navigation events
    this.routerSub = this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        this.updateBreadcrumb();
      });
    
    // Subscribe to breadcrumb context changes
    this.contextSub = this.breadcrumbContext.context$.subscribe(() => {
      this.updateBreadcrumb();
    });
    
    this.updateBreadcrumb();
  }

  
updateBreadcrumb() {
    const url = this.location.path();
    const cleanUrl = url.split('?')[0].split('#')[0]; // Remove query params and hash
    const context = this.breadcrumbContext.getContext();
    
    console.log('🔍 Breadcrumb Full URL:', url);
    console.log('🔍 Breadcrumb Clean URL:', cleanUrl);
    console.log('🔖 Breadcrumb Context:', context);
    
    let config = this.getBreadcrumbConfig(url);
    
    // If no config found, use default or return early
    if (!config) {
      console.warn('⚠️ No breadcrumb config found for URL:', cleanUrl);
      // Try to use default config
      config = BREADCRUMB_MAP['/'];
      
      // If still no config, set defaults and return
      if (!config) {
        this.currentPageTitle = 'Page';
        this.currentPageIcon = 'pi pi-home';
        this.home = { icon: 'pi pi-home' };
        this.items = [];
        return;
      }
    }
    
    const id = this.route.snapshot.params['id'];
    let titleFinale = id == null ? config.title : config.title + " " + id;
    
    this.currentPageTitle = titleFinale;
    this.currentPageIcon = config.icon;
    this.home = { icon: config.icon };
    
    if (config.buildBreadcrumb) {
      // Use context if current URL matches context route, otherwise use id
      const breadcrumbContext = (cleanUrl === context.route || cleanUrl.startsWith(context.route)) 
        ? context.context 
        : id;
      this.items = config.buildBreadcrumb(breadcrumbContext || undefined);
    } else if(config.subItems){
      this.items = config.subItems;
    } else {
      this.items = [];
    }
  }
  getBreadcrumbConfig(url: string): BreadcrumbConfig | undefined {
    // Remove query parameters and hash from URL for matching
    const cleanUrl = url.split('?')[0].split('#')[0];
    
    console.log('🔍 Clean URL for matching:', cleanUrl);
    
    for (const key of Object.keys(BREADCRUMB_MAP)) {
      // Replace :id in the key with regex
      const pattern = new RegExp('^' + key.replace(':id', '[^/]+') + '$');
      
      console.log(`🔍 Testing pattern "${key}" (${pattern}) against "${cleanUrl}"`);
      
      if (pattern.test(cleanUrl)) {
        console.log(`✅ Match found for pattern: ${key}`);
        return BREADCRUMB_MAP[key];
      }
    }
    
    console.warn(`⚠️ No match found for URL: ${cleanUrl}`);
    return undefined;
  }ngOnDestroy() {
    this.routerSub?.unsubscribe();
    this.contextSub?.unsubscribe();
  }
}
