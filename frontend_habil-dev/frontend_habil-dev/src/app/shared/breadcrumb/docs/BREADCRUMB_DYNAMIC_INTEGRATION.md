# Dynamic Breadcrumb Integration for Utilisateur Profil

## 🎯 Implementation Summary

This document describes the implementation of dynamic breadcrumbs in the Utilisateur Profil component that change based on the active tab (Profils individuels vs Packs de profils).

---

## 📋 Requirements

**User Story**: As a user navigating the profile assignment interface, I want to see context-aware breadcrumbs that show:
- "2nd step / Profils" when on the individual profiles tab
- "2nd step / Packs" when on the packs tab

---

## 🏗️ Architecture Overview

### Component Hierarchy

```
utilisateurprofil.component
    ↓
p-stepper (Step 2)
    ↓
profile-assignment-step.component
    ↓
tab-toggle.component
    ↓
[Profils individuels] | [Packs de profils]
```

### Data Flow

```
User clicks tab
    ↓
tab-toggle.component emits tabChange(string)
    ↓
profile-assignment-step.component.onTabChanged(string)
    ↓
Updates currentViewMode: 'profiles' | 'packs'
    ↓
Emits viewModeChange to parent
    ↓
utilisateurprofil.component receives event
    ↓
Updates route query params OR state variable
    ↓
Breadcrumb component detects change
    ↓
Updates breadcrumb display
```

---

## 💻 Implementation Steps

### Step 1: Add Breadcrumb to Utilisateur Profil Template

**File**: `utilisateurprofil.component.html`

Add breadcrumb component at the top of the template (before existing content):

```html
<!-- Breadcrumb Navigation -->
<app-breadcrumb>
  <!-- Optional custom actions can be added here -->
</app-breadcrumb>

<!-- Profile Toolbar Component -->
<app-profile-toolbar [selectedAppCode]="selectedAppCode" ...>
</app-profile-toolbar>
<!-- Rest of existing content -->
```

### Step 2: Track Current View Mode

**File**: `utilisateurprofil.component.ts`

Add property to track current view:

```typescript
export class UtilisateurProfilComponent implements OnInit, OnDestroy {
  // ...existing properties...
  
  /**
   * Current view mode in step 2 (profiles or packs)
   */
  currentViewMode: 'profiles' | 'packs' = 'profiles';
  
  // ...rest of code...
}
```

### Step 3: Handle View Mode Changes

**File**: `utilisateurprofil.component.ts`

Add method to handle view mode changes from child component:

```typescript
/**
 * Handle view mode change from profile assignment step
 * Updates breadcrumb context when user switches between profiles and packs tabs
 * 
 * @param mode - The new view mode ('profiles' or 'packs')
 */
onViewModeChange(mode: 'profiles' | 'packs'): void {
  console.log('🔄 View mode changed to:', mode);
  this.currentViewMode = mode;
  
  // Update route with query parameter to trigger breadcrumb update
  this.router.navigate([], {
    relativeTo: this.route,
    queryParams: { view: mode },
    queryParamsHandling: 'merge'
  });
}
```

### Step 4: Connect Event in Template

**File**: `utilisateurprofil.component.html`

Add event binding to profile-assignment-step:

```html
<app-profile-assignment-step 
  [selectedUser]="selectedUser"
  [availableProfiles]="availableProfiles"
  <!-- ...other inputs... -->
  (viewModeChange)="onViewModeChange($event)"
  <!-- ...other outputs... -->
>
</app-profile-assignment-step>
```

### Step 5: Configure Breadcrumb

**File**: `breadcrumb.config.ts`

Add configuration for utilisateur-profil route:

```typescript
export const BREADCRUMB_MAP: Record<string, BreadcrumbConfig> = {
  // ...existing configs...
  
  '/administration/utilisateur-profil': {
    title: 'Affectation des Profils',
    icon: 'pi pi-users',
    url: '/administration/utilisateur-profil',
    items: [],
    buildBreadcrumb: (id?: string) => {
      // Base breadcrumb items
      const items = [
        { label: 'Administration', routerLink: '/administration' },
        { label: 'Utilisateur Profil', routerLink: '/administration/utilisateur-profil' }
      ];
      
      // Add context-specific breadcrumb based on query param
      // Note: This requires accessing ActivatedRoute in breadcrumb component
      // See enhanced implementation below
      
      return items;
    }
  },
};
```

### Step 6: Enhanced Breadcrumb Component (Option A)

**File**: `breadcrumb.component.ts`

Modify to access query parameters:

```typescript
updateBreadcrumb() {
  const url = this.location.path();
  console.log('🔍 Breadcrumb URL:', url);
  
  let config = this.getBreadcrumbConfig(url);
  
  if (!config) {
    config = BREADCRUMB_MAP['/'];
  }
  
  // Extract ID from route params
  const id = this.route.snapshot.params['id'];
  
  // Extract query parameters for context
  const queryParams = this.route.snapshot.queryParams;
  const view = queryParams['view']; // 'profiles' or 'packs'
  
  let titleFinale = id == null ? config.title : config.title + " " + id;
  this.currentPageTitle = titleFinale;
  this.currentPageIcon = config.icon;
  this.home = { icon: config.icon };
  
  if (config.buildBreadcrumb) {
    // Pass both id and view context to builder
    const context = view || id;
    this.items = config.buildBreadcrumb(context);
  } else if(config.subItems){
    this.items = config.subItems;
  } else {
    this.items = [];
  }
}
```

### Step 7: Update Breadcrumb Config (Enhanced)

**File**: `breadcrumb.config.ts`

Update with context-aware breadcrumb building:

```typescript
'/administration/utilisateur-profil': {
  title: 'Affectation des Profils',
  icon: 'pi pi-users',
  url: '/administration/utilisateur-profil',
  items: [],
  buildBreadcrumb: (context?: string) => {
    const items = [
      { label: 'Administration', routerLink: '/administration' },
      { label: 'Utilisateur Profil', routerLink: '/administration/utilisateur-profil' }
    ];
    
    // Add step-specific breadcrumb based on view context
    if (context === 'packs') {
      items.push({ 
        label: '2ème étape / Packs', 
        routerLink: '' // Empty means no navigation
      });
    } else if (context === 'profiles' || context) {
      items.push({ 
        label: '2ème étape / Profils', 
        routerLink: '' 
      });
    }
    
    return items;
  }
},
```

---

## 🎨 Alternative Implementation (Option B - Simpler)

If you prefer not to use query parameters, you can use a shared service:

### Step 1: Create Breadcrumb Service

**File**: `src/app/core/services/frontend/breadcrumb-context.service.ts`

```typescript
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface BreadcrumbContext {
  route: string;
  context?: string;
}

@Injectable({
  providedIn: 'root'
})
export class BreadcrumbContextService {
  private contextSubject = new BehaviorSubject<BreadcrumbContext>({ route: '/' });
  public context$: Observable<BreadcrumbContext> = this.contextSubject.asObservable();
  
  setContext(route: string, context?: string): void {
    this.contextSubject.next({ route, context });
  }
  
  getContext(): BreadcrumbContext {
    return this.contextSubject.value;
  }
}
```

### Step 2: Update Component

**File**: `utilisateurprofil.component.ts`

```typescript
import { BreadcrumbContextService } from '../../../core/services/frontend/breadcrumb-context.service';

export class UtilisateurProfilComponent {
  constructor(
    // ...existing dependencies...
    private breadcrumbContext: BreadcrumbContextService
  ) {}
  
  onViewModeChange(mode: 'profiles' | 'packs'): void {
    this.currentViewMode = mode;
    this.breadcrumbContext.setContext('/administration/utilisateur-profil', mode);
  }
}
```

### Step 3: Update Breadcrumb Component

**File**: `breadcrumb.component.ts`

```typescript
import { BreadcrumbContextService } from '../../core/services/frontend/breadcrumb-context.service';

export class BreadcrumbComponent implements OnInit, OnDestroy {
  private contextSub: Subscription | undefined;
  
  constructor(
    private router: Router, 
    private location: Location,
    private route: ActivatedRoute,
    private breadcrumbContext: BreadcrumbContextService
  ) {}
  
  ngOnInit() {
    // Subscribe to router events
    this.routerSub = this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        this.updateBreadcrumb();
      });
    
    // Subscribe to context changes
    this.contextSub = this.breadcrumbContext.context$.subscribe(() => {
      this.updateBreadcrumb();
    });
    
    this.updateBreadcrumb();
  }
  
  updateBreadcrumb() {
    const url = this.location.path();
    const context = this.breadcrumbContext.getContext();
    
    let config = this.getBreadcrumbConfig(url);
    
    if (!config) {
      config = BREADCRUMB_MAP['/'];
    }
    
    const id = this.route.snapshot.params['id'];
    let titleFinale = id == null ? config.title : config.title + " " + id;
    
    this.currentPageTitle = titleFinale;
    this.currentPageIcon = config.icon;
    this.home = { icon: config.icon };
    
    if (config.buildBreadcrumb) {
      // Use context if current URL matches context route
      const breadcrumbContext = (url === context.route) ? context.context : id;
      this.items = config.buildBreadcrumb(breadcrumbContext);
    } else if(config.subItems){
      this.items = config.subItems;
    } else {
      this.items = [];
    }
  }
  
  ngOnDestroy() {
    this.routerSub?.unsubscribe();
    this.contextSub?.unsubscribe();
  }
}
```

---

## 🧪 Testing Checklist

- [ ] Breadcrumb appears on utilisateur-profil page
- [ ] Initially shows "2ème étape / Profils" (default tab)
- [ ] Switches to "2ème étape / Packs" when packs tab is clicked
- [ ] Switches back to "2ème étape / Profils" when profiles tab is clicked
- [ ] Breadcrumb updates are smooth without flicker
- [ ] Navigation works correctly when clicking breadcrumb items
- [ ] No console errors during tab switching
- [ ] Memory: No memory leaks from subscriptions

---

## 🐛 Troubleshooting

### Issue: Breadcrumb doesn't update when tab changes

**Solution**: Ensure event is properly wired from tab-toggle → profile-assignment-step → utilisateurprofil

Check this chain:
1. `tab-toggle.component.ts` emits `tabChange`
2. `profile-assignment-step.component.ts` receives it in `onTabChanged()`
3. `profile-assignment-step.component.ts` emits `viewModeChange`
4. `utilisateurprofil.component.ts` receives it in `onViewModeChange()`

### Issue: Query parameter approach causes URL pollution

**Solution**: Use Option B (BreadcrumbContextService) instead for cleaner URLs

### Issue: Breadcrumb shows for all steps, not just step 2

**Solution**: Add conditional rendering in utilisateurprofil template:

```html
<app-breadcrumb *ngIf="activeStep === 2">
</app-breadcrumb>
```

---

## 📊 Performance Considerations

1. **Subscription Management**: Always unsubscribe in `ngOnDestroy`
2. **Change Detection**: Use `OnPush` strategy if needed
3. **Query Params**: Consider using `replaceUrl: true` to avoid history pollution

```typescript
this.router.navigate([], {
  relativeTo: this.route,
  queryParams: { view: mode },
  queryParamsHandling: 'merge',
  replaceUrl: true // Don't add to browser history
});
```

---

## 🎯 Final Implementation Recommendation

**Recommended Approach**: Option B (BreadcrumbContextService)

**Reasons**:
- ✅ Cleaner URLs (no query parameters)
- ✅ More maintainable (centralized state)
- ✅ Better performance (fewer URL changes)
- ✅ Easier to test
- ✅ Works with browser back/forward

---

## 📝 Code Summary

**Files to Create**:
1. `breadcrumb-context.service.ts` (if using Option B)

**Files to Modify**:
1. `utilisateurprofil.component.ts` - Add view mode tracking & handler
2. `utilisateurprofil.component.html` - Add breadcrumb & event binding
3. `breadcrumb.component.ts` - Subscribe to context changes
4. `breadcrumb.config.ts` - Add dynamic breadcrumb config

**Total Lines Added**: ~100 lines
**Total Lines Modified**: ~20 lines

---

## 🔗 Related Documentation

- [BREADCRUMB_SYSTEM_GUIDE.md](./BREADCRUMB_SYSTEM_GUIDE.md) - Complete breadcrumb system documentation
- [NOTIFICATION_SYSTEM_IMPLEMENTATION_GUIDE.md](./NOTIFICATION_SYSTEM_IMPLEMENTATION_GUIDE.md) - Notification system reference

---

**Implementation Date**: 2025-12-29  
**Author**: GitHub Copilot  
**Status**: ✅ Ready for Implementation
