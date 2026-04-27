# Breadcrumb System Implementation Guide

## 📋 Table of Contents
1. [Overview](#overview)
2. [Architecture](#architecture)
3. [Configuration System](#configuration-system)
4. [Dynamic URL Parameter Handling](#dynamic-url-parameter-handling)
5. [Usage Examples](#usage-examples)
6. [Integration Guide](#integration-guide)
7. [Troubleshooting](#troubleshooting)

---

## 🎯 Overview

The BNA HABIL breadcrumb system provides a flexible, configuration-driven approach to displaying navigation breadcrumbs throughout the application. It automatically updates based on route changes and supports dynamic URL parameters.

### Key Features
- ✅ **Automatic Route Detection**: Listens to router events and updates breadcrumbs
- ✅ **Dynamic URL Parameters**: Supports `:id` patterns in routes
- ✅ **Configuration-Based**: Centralized breadcrumb configuration in `breadcrumb.config.ts`
- ✅ **Custom Build Functions**: Allows dynamic breadcrumb generation based on route params
- ✅ **PrimeNG Integration**: Uses PrimeNG breadcrumb component for consistent UI
- ✅ **Content Projection**: Supports custom actions on left and right sides

---

## 🏗️ Architecture

### Component Structure

```
app-breadcrumb (BreadcrumbComponent)
├── breadcrumb.component.ts       # Component logic
├── breadcrumb.component.html     # Template
├── breadcrumb.component.scss     # Styles
└── breadcrumb.config.ts          # Configuration (separate file)
```

### Data Flow

```
Router NavigationEnd Event
    ↓
BreadcrumbComponent.updateBreadcrumb()
    ↓
Get current URL path via Location service
    ↓
Match URL against BREADCRUMB_MAP patterns
    ↓
Extract route parameters (:id)
    ↓
Build breadcrumb items (static or dynamic)
    ↓
Update component properties (title, icon, items)
    ↓
Template renders breadcrumb UI
```

---

## ⚙️ Configuration System

### BreadcrumbConfig Interface

```typescript
export interface BreadcrumbConfig {
  title: string;                    // Page title displayed
  icon: string;                     // PrimeNG icon class
  items: Array<{                    // Breadcrumb items (if static)
    label: string;
    icon: string;
    routerLink: string;
  }>;
  url: string;                      // Base URL
  subItems?: Array<{                // Alternative to items
    label: string;
    routerLink: string;
  }>;
  buildBreadcrumb?: (id?: string) => Array<{  // Dynamic builder function
    label: string;
    routerLink: string;
  }>;
}
```

### Configuration Map

Located in: `src/app/core/configuration/breadcrumb.config.ts`

```typescript
export const BREADCRUMB_MAP: Record<string, BreadcrumbConfig> = {
  // Static breadcrumb example
  '/ressources/sample': {
    title: 'Sample Page',
    icon: 'pi pi-file-plus',
    url: '/ressources/sample',
    items: [],
    subItems: [
      {
        label: 'Sample Page',
        routerLink: '/sample',
      },
    ],
  },

  // Dynamic breadcrumb with URL parameter
  '/ressources/sample/:id': {
    title: 'Sample ',
    icon: 'pi pi-book',
    url: '/ressources',
    items: [],
    buildBreadcrumb: (id) => [
      { label: 'Ressources', routerLink: '/ressources' },
      { label: `Sample #${id}`, routerLink: `/ressources/sample/${id}` }
    ]
  },
};
```

---

## 🔗 Dynamic URL Parameter Handling

### How It Works

1. **Pattern Matching**: The component converts `:id` patterns in config keys to regex patterns
2. **Parameter Extraction**: Uses Angular's `ActivatedRoute.snapshot.params['id']` to get URL params
3. **Title Generation**: Appends parameter to title if present
4. **Dynamic Building**: Calls `buildBreadcrumb(id)` function if defined

### Example URL Flow

```
URL: /administration/utilisateur-profil/12345

Config Key: '/administration/utilisateur-profil/:id'
    ↓
Regex Pattern: ^/administration/utilisateur-profil/[^/]+$
    ↓
Match: ✅ true
    ↓
Extract: id = '12345'
    ↓
Title: 'Utilisateur Profil 12345'
    ↓
Breadcrumb: buildBreadcrumb('12345')
```

### Pattern Matching Code

```typescript
getBreadcrumbConfig(url: string): BreadcrumbConfig | undefined {
  for (const key of Object.keys(BREADCRUMB_MAP)) {
    // Replace :id in the key with regex pattern [^/]+
    const pattern = new RegExp('^' + key.replace(':id', '[^/]+') + '$');
    if (pattern.test(url)) {
      return BREADCRUMB_MAP[key];
    }
  }
  return undefined;
}
```

---

## 📝 Usage Examples

### Example 1: Static Breadcrumb

**Use Case**: Simple page with fixed navigation path

```typescript
'/administration/profile': {
  title: 'Profils',
  icon: 'pi pi-id-card',
  url: '/administration/profile',
  items: [],
  subItems: [
    { label: 'Administration', routerLink: '/administration' },
    { label: 'Profils', routerLink: '/administration/profile' },
  ],
}
```

### Example 2: Dynamic Breadcrumb with ID

**Use Case**: Detail page showing specific record

```typescript
'/administration/profile/:id': {
  title: 'Détails du profil ',
  icon: 'pi pi-id-card',
  url: '/administration/profile',
  items: [],
  buildBreadcrumb: (id) => [
    { label: 'Administration', routerLink: '/administration' },
    { label: 'Profils', routerLink: '/administration/profile' },
    { label: `Profil #${id}`, routerLink: `/administration/profile/${id}` }
  ]
}
```

### Example 3: Dynamic Breadcrumb with Context

**Use Case**: Breadcrumb changes based on active tab or state

```typescript
'/administration/utilisateur-profil': {
  title: 'Affectation des Profils',
  icon: 'pi pi-users',
  url: '/administration/utilisateur-profil',
  items: [],
  buildBreadcrumb: (context?: string) => {
    const baseItems = [
      { label: 'Administration', routerLink: '/administration' },
      { label: 'Utilisateur Profil', routerLink: '/administration/utilisateur-profil' }
    ];
    
    // Add context-specific breadcrumb
    if (context === 'packs') {
      baseItems.push({ label: '2nd step / Packs', routerLink: '' });
    } else if (context === 'profils') {
      baseItems.push({ label: '2nd step / Profils', routerLink: '' });
    }
    
    return baseItems;
  }
}
```

---

## 🔧 Integration Guide

### Step 1: Add Breadcrumb to Layout

Most commonly, breadcrumbs are added to a layout component or at the top of a feature module:

```html
<!-- In your layout or page component template -->
<app-breadcrumb>
  <!-- Optional: Add custom actions -->
  <div bar-actions-left>
    <button pButton label="Action" icon="pi pi-plus"></button>
  </div>
  <div bar-actions-right>
    <button pButton label="Settings" icon="pi pi-cog"></button>
  </div>
</app-breadcrumb>

<!-- Your page content -->
<div class="page-content">
  <!-- ... -->
</div>
```

### Step 2: Configure Routes in breadcrumb.config.ts

Add your route configuration to `BREADCRUMB_MAP`:

```typescript
export const BREADCRUMB_MAP: Record<string, BreadcrumbConfig> = {
  // ...existing configs...
  
  '/your-module/your-page': {
    title: 'Your Page Title',
    icon: 'pi pi-icon-name',
    url: '/your-module/your-page',
    items: [],
    subItems: [
      { label: 'Module', routerLink: '/your-module' },
      { label: 'Page', routerLink: '/your-module/your-page' },
    ],
  },
};
```

### Step 3: Update Breadcrumb Programmatically (Optional)

For dynamic updates based on component state, you can trigger breadcrumb updates:

```typescript
import { Router } from '@angular/router';

export class YourComponent {
  constructor(private router: Router) {}
  
  updateBreadcrumb() {
    // Navigate to trigger breadcrumb update
    // Or use a breadcrumb service if you create one
  }
}
```

### Step 4: Dynamic Context Updates

For components with tabs or multiple views, pass context to buildBreadcrumb:

```typescript
// In component
currentTab: 'profils' | 'packs' = 'profils';

onTabChange(tab: string) {
  this.currentTab = tab as 'profils' | 'packs';
  // Update route with query param or state
  this.router.navigate([], {
    relativeTo: this.route,
    queryParams: { view: tab },
    queryParamsHandling: 'merge'
  });
}

// In breadcrumb config
buildBreadcrumb: (id?: string) => {
  // Access query params or component state
  const queryParams = this.route.snapshot.queryParams;
  const view = queryParams['view'] || 'profils';
  
  return [
    { label: 'Base', routerLink: '/base' },
    { label: `View: ${view}`, routerLink: '' }
  ];
}
```

---

## 🐛 Troubleshooting

### Issue 1: Breadcrumb Not Updating

**Symptoms**: Breadcrumb shows old data or doesn't update on navigation

**Solutions**:
1. Check that route is defined in `BREADCRUMB_MAP`
2. Verify URL pattern matches exactly (including leading `/`)
3. Ensure router events are being triggered
4. Check console for pattern matching logs

```typescript
// Debug: Add console.log in updateBreadcrumb()
updateBreadcrumb() {
  const url = this.location.path();
  console.log('🔍 Current URL:', url);
  let config = this.getBreadcrumbConfig(url);
  console.log('📋 Matched config:', config);
  // ...rest of code
}
```

### Issue 2: URL Parameters Not Working

**Symptoms**: `:id` in URL doesn't match or extract properly

**Solutions**:
1. Check that config key uses `:id` exactly
2. Verify URL doesn't have extra segments
3. Test regex pattern manually

```typescript
// Debug: Test pattern
const key = '/administration/profile/:id';
const url = '/administration/profile/123';
const pattern = new RegExp('^' + key.replace(':id', '[^/]+') + '$');
console.log('Pattern test:', pattern.test(url)); // Should be true
```

### Issue 3: buildBreadcrumb Not Called

**Symptoms**: Static items shown instead of dynamic ones

**Solutions**:
1. Ensure `buildBreadcrumb` function is defined in config
2. Check that it returns array of breadcrumb items
3. Verify no errors in buildBreadcrumb function

```typescript
// Correct format
buildBreadcrumb: (id) => [
  { label: 'Home', routerLink: '/home' },
  { label: `Item ${id}`, routerLink: `/item/${id}` }
]

// Incorrect - missing return array
buildBreadcrumb: (id) => {
  console.log(id);
  // Missing return!
}
```

### Issue 4: Memory Leaks

**Symptoms**: Application slows down over time

**Solutions**:
1. Ensure `ngOnDestroy` unsubscribes from router events
2. Check that subscription is properly created

```typescript
private routerSub: Subscription | undefined;

ngOnInit() {
  this.routerSub = this.router.events
    .pipe(filter((event) => event instanceof NavigationEnd))
    .subscribe(() => {
      this.updateBreadcrumb();
    });
}

ngOnDestroy() {
  this.routerSub?.unsubscribe(); // ✅ Always unsubscribe
}
```

---

## 📚 Best Practices

### 1. Keep Config Organized

Group related routes together in `BREADCRUMB_MAP`:

```typescript
export const BREADCRUMB_MAP: Record<string, BreadcrumbConfig> = {
  // ===== ADMINISTRATION MODULE =====
  '/administration': { /* ... */ },
  '/administration/users': { /* ... */ },
  '/administration/users/:id': { /* ... */ },
  
  // ===== RESOURCES MODULE =====
  '/resources': { /* ... */ },
  '/resources/documents': { /* ... */ },
};
```

### 2. Reuse Common Patterns

Create helper functions for common breadcrumb patterns:

```typescript
function createAdminBreadcrumb(page: string, pageRoute: string): BreadcrumbConfig {
  return {
    title: page,
    icon: 'pi pi-cog',
    url: pageRoute,
    items: [],
    subItems: [
      { label: 'Administration', routerLink: '/administration' },
      { label: page, routerLink: pageRoute },
    ],
  };
}

export const BREADCRUMB_MAP: Record<string, BreadcrumbConfig> = {
  '/administration/users': createAdminBreadcrumb('Users', '/administration/users'),
  '/administration/roles': createAdminBreadcrumb('Roles', '/administration/roles'),
};
```

### 3. Fallback Configuration

Always define a fallback config for `/`:

```typescript
export const BREADCRUMB_MAP: Record<string, BreadcrumbConfig> = {
  '/': {
    title: 'Accueil',
    icon: 'pi pi-home',
    url: '/',
    items: [],
  },
  // ...other routes
};
```

---

## 🎨 Styling Guide

The breadcrumb component uses these CSS classes:

```scss
.bar-container {
  // Main container
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  background: white;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.bar-left {
  // Left side (title + actions)
  display: flex;
  align-items: center;
  gap: 1rem;
}

.bar-title {
  // Page title
  font-size: 1.5rem;
  font-weight: 600;
  color: #333;
}

.bar-right {
  // Right side (breadcrumb + actions)
  display: flex;
  align-items: center;
  gap: 1rem;
}

.bar-breadcrumb {
  // Breadcrumb container
  ::ng-deep .p-breadcrumb {
    background: transparent;
    border: none;
  }
}
```

---

## 📖 Related Documentation

- [PrimeNG Breadcrumb Component](https://primeng.org/breadcrumb)
- [Angular Router Documentation](https://angular.io/guide/router)
- [BNA HABIL Navigation Guide](./NAVIGATION_GUIDE.md)

---

## 🔄 Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0.0 | 2025-01-01 | Initial breadcrumb system implementation |
| 1.1.0 | 2025-12-29 | Added dynamic context support for tabs |

---

## 💡 Tips & Tricks

### Tip 1: Debug URL Matching

Add temporary logging to see URL matching in action:

```typescript
updateBreadcrumb() {
  const url = this.location.path();
  console.log('🔍 URL:', url);
  console.log('🗺️ Available patterns:', Object.keys(BREADCRUMB_MAP));
  // ...rest of code
}
```

### Tip 2: Query Parameters

Access query parameters in buildBreadcrumb:

```typescript
buildBreadcrumb: (id) => {
  // Note: You'll need to inject ActivatedRoute into the config
  // Or pass query params from component
  const baseItems = [...];
  return baseItems;
}
```

### Tip 3: Conditional Breadcrumbs

Show different breadcrumbs based on user role:

```typescript
buildBreadcrumb: (id) => {
  // Access user service or token storage
  const isAdmin = this.checkAdminRole();
  
  const items = [
    { label: 'Home', routerLink: '/' }
  ];
  
  if (isAdmin) {
    items.push({ label: 'Admin', routerLink: '/admin' });
  }
  
  return items;
}
```

---

**End of Breadcrumb System Implementation Guide**
