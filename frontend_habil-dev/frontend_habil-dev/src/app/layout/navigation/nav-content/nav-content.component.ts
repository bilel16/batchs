import {
  AfterViewInit,
  Component,
  computed,
  ElementRef,
  OnInit,
  output,
  signal,
  ViewChild,
  OnDestroy,
  inject,
} from '@angular/core';
import { Subscription } from 'rxjs';
import { Location } from '@angular/common';

import { NavigationItem } from '../../../core/interfaces/navigation';
import { TokenStorageService } from '../../../core/services/frontend/token-storage.service';
import { NavigationAuthService } from '../../../core/services/frontend/navigation-auth.service';
import { NextConfig } from '../../../core/configuration/NextConfig';
import { AuthSignalService } from '../../../core/services/frontend/auth-signal.service';

@Component({
  selector: 'app-nav-content',
  templateUrl: './nav-content.component.html',
  styleUrls: ['./nav-content.component.scss'],
  standalone: false,
  providers: [NavigationItem],
})
export class NavContentComponent implements OnInit, AfterViewInit, OnDestroy {
  // Injected services
  private readonly navigationAuth = inject(NavigationAuthService);
  private readonly location = inject(Location);
  public readonly nav = inject(NavigationItem);
  private readonly auth = inject(AuthSignalService);

  // Configuration & layout signals
  public readonly flatConfig = signal<any>(NextConfig.config);
  public readonly windowWidth = signal<number>(window.innerWidth);
  public readonly isNavProfile = signal<boolean>(false);

  // Navigation data signals
  private readonly navigation = signal<any[]>([]);
  public readonly filteredNavigation = signal<any[]>([]);

  // Scroll state signals
  public readonly scrollWidth = signal<number>(0);
  public readonly contentWidth = signal<number>(0);
  public readonly wrapperWidth = signal<number>(0);

  // Computed scroll button disabled states
  public readonly prevDisabled = computed(() =>
    this.scrollWidth() <= 0 ? 'disabled' : ''
  );

  public readonly nextDisabled = computed(() => {
    const content = this.contentWidth();
    const wrapper = this.wrapperWidth();
    return content > 0 && this.scrollWidth() >= content - wrapper
      ? 'disabled'
      : '';
  });

  // User data signals
  public readonly nom = signal<string>('');
  public readonly prenom = signal<string>('');
  public readonly matricule = signal<string>('');
  public readonly structure = signal<string>('');
  public readonly fonction = signal<string>('');

  // Computed display helpers
  public readonly hasUserName = computed(
    () => !!this.getUserName() || !!this.nom()
  );
  public readonly displayName = computed(
    () => `${this.getUserName()} ${this.nom()}`.trim()
  );
  public readonly displayMatricule = computed(
    () => this.auth.user().username || '...'
  );
  public readonly displayStructure = computed(
    () =>  this.auth.user().codeStructure || '...'
  );
  public readonly displayFonction = computed(
    () => this.auth.user().poste || '...'
  );

  private getUserName():string{
    return this.auth.user().prenom +" "+this.auth.user().nom
  }

  // Layout computed
  public readonly isVerticalLayout = computed(
    () => this.flatConfig()['layout'] === 'vertical'
  );
  public readonly isHorizontalLayout = computed(
    () => this.flatConfig()['layout'] === 'horizontal'
  );

  private subscriptions: Subscription[] = [];

  // Output using the modern `output()` function
  onNavMobCollapse = output<void>();

  @ViewChild('navbarContent') navbarContent!: ElementRef;
  @ViewChild('navbarWrapper') navbarWrapper!: ElementRef;

  ngOnInit(): void {
    // Load initial navigation items
    this.navigation.set(this.nav.get());
    console.log('NavContent - User data from signal:', this.auth.user());


    // Initialize navigation auth service and update permissions
    this.navigationAuth.updateAllowedMenus();
    this.updateNavigationVisibility();

    // Subscribe to menu permission changes
    const menuSub = this.navigationAuth.allowedMenus$.subscribe(
      (allowedMenus) => {
        console.log('Navigation permissions updated:', allowedMenus);
        this.updateNavigationVisibility();
      }
    );
    this.subscriptions.push(menuSub);

    // Handle responsive layout
    if (this.windowWidth() < 992) {
      this.flatConfig.update((config) => ({ ...config, layout: 'vertical' }));
      setTimeout(() => {
        document
          .querySelector('.pcoded-navbar')
          ?.classList.add('menupos-static');
        const navEl = document.querySelector(
          '#nav-ps-flat-able'
        ) as HTMLElement | null;
        if (navEl) {
          navEl.style.maxHeight = '100%';
        }
      }, 500);
    }
  }

  ngAfterViewInit(): void {
    if (this.isHorizontalLayout()) {
      this.contentWidth.set(this.navbarContent.nativeElement.clientWidth);
      this.wrapperWidth.set(this.navbarWrapper.nativeElement.clientWidth);
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }

  /**
   * Set user data from user object, with safe fallbacks
   */
  private setUserData(user: any): void {
    if (user) {
      this.nom.set(user.nom || '');
      this.prenom.set(user.prenom || '');
      this.matricule.set(user.username || '');
      this.structure.set(user.codeStructure || '');
      this.fonction.set(user.poste || '');
    } else {
      console.warn('NavContent - No user data in session storage');
      this.nom.set('');
      this.prenom.set('');
      this.matricule.set('');
      this.structure.set('');
      this.fonction.set('');
    }
  }

  /**
   * Update navigation visibility based on user permissions
   */
  private updateNavigationVisibility(): void {
    this.filteredNavigation.set(
      this.navigationAuth.filterNavigationItems(this.navigation())
    );
    console.log('Filtered navigation:', this.filteredNavigation());
  }

  toggleNavProfile(): void {
    this.isNavProfile.update((v) => !v);
  }

  scrollPlus(): void {
    const wrapper = this.wrapperWidth();
    const content = this.contentWidth();

    let newScroll = this.scrollWidth() + (wrapper - 80);
    if (newScroll > content - wrapper) {
      newScroll = content - wrapper + 80;
    }
    this.scrollWidth.set(newScroll);
    this.applyHorizontalScroll();
  }

  scrollMinus(): void {
    let newScroll = this.scrollWidth() - this.wrapperWidth();
    if (newScroll < 0) {
      newScroll = 0;
    }
    this.scrollWidth.set(newScroll);
    this.applyHorizontalScroll();
  }

  private applyHorizontalScroll(): void {
    const el = document.querySelector(
      '#side-nav-horizontal'
    ) as HTMLElement | null;
    if (!el) return;

    const marginProp = this.flatConfig().rtlLayout ? 'marginRight' : 'marginLeft';
    el.style[marginProp] = `-${this.scrollWidth()}px`;
  }

  fireLeave(): void {
    const sections = document.querySelectorAll('.pcoded-hasmenu');
    sections.forEach((section) => {
      section.classList.remove('active');
      section.classList.remove('pcoded-trigger');
    });

    const current_url = this.resolveCurrentUrl();
    const ele = this.findNavLinkElement(current_url);
    if (!ele) return;

    const parent = ele.parentElement;
    const up_parent = parent?.parentElement?.parentElement;
    const last_parent = up_parent?.parentElement;

    if (parent?.classList.contains('pcoded-hasmenu')) {
      parent.classList.add('active');
    } else if (up_parent?.classList.contains('pcoded-hasmenu')) {
      up_parent.classList.add('active');
    } else if (last_parent?.classList.contains('pcoded-hasmenu')) {
      last_parent.classList.add('active');
    }
  }

  navMob(): void {
    if (
      this.windowWidth() < 992 &&
      document
        .querySelector('app-navigation.pcoded-navbar')
        ?.classList.contains('mob-open')
    ) {
      this.onNavMobCollapse.emit();
    }
  }

  fireOutClick(): void {
    const current_url = this.resolveCurrentUrl();
    const ele = this.findNavLinkElement(current_url);
    if (!ele) return;

    const parent = ele.parentElement;
    const up_parent = parent?.parentElement?.parentElement;
    const last_parent = up_parent?.parentElement;
    const isVertical = this.isVerticalLayout();

    if (parent?.classList.contains('pcoded-hasmenu')) {
      if (isVertical) parent.classList.add('pcoded-trigger');
      parent.classList.add('active');
    } else if (up_parent?.classList.contains('pcoded-hasmenu')) {
      if (isVertical) up_parent.classList.add('pcoded-trigger');
      up_parent.classList.add('active');
    } else if (last_parent?.classList.contains('pcoded-hasmenu')) {
      if (isVertical) last_parent.classList.add('pcoded-trigger');
      last_parent.classList.add('active');
    }
  }

  /**
   * Resolve the current URL, accounting for base href
   */
  private resolveCurrentUrl(): string {
    let url = this.location.path();
    if ((this.location as any)['_baseHref']) {
      url = (this.location as any)['_baseHref'] + url;
    }
    return url;
  }

  /**
   * Find the nav link element for the given URL
   */
  private findNavLinkElement(url: string): Element | null {
    const selector = `a.nav-link[href='${url}']`;
    return document.querySelector(selector);
  }
}
