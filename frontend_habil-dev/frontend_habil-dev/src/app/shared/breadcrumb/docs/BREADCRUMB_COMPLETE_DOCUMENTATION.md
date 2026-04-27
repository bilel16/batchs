# 🧭 BNA HABIL Dynamic Breadcrumb System - Complete Documentation

## 📋 Table of Contents

1. [Overview](#overview)
2. [Architecture](#architecture)
3. [Implementation Guide](#implementation-guide)
4. [Configuration Reference](#configuration-reference)
5. [Usage Examples](#usage-examples)
6. [Advanced Features](#advanced-features)
7. [Troubleshooting](#troubleshooting)
8. [Best Practices](#best-practices)
9. [API Reference](#api-reference)

---

## 📖 Overview

### What is the Breadcrumb System?

The BNA HABIL Breadcrumb System is a **dynamic, context-aware navigation component** that automatically displays breadcrumb trails based on:
- Current route/URL
- Application state (tabs, filters, etc.)
- Dynamic URL parameters

### Key Features

✅ **Automatic Route Detection** - Listens to router events and updates breadcrumbs  
✅ **Dynamic Context Support** - Changes based on application state (e.g., active tab)  
✅ **Query Parameter Handling** - Works with URLs containing query strings  
✅ **Configuration-Driven** - Centralized breadcrumb configuration  
✅ **PrimeNG Integration** - Uses PrimeNG breadcrumb component for consistent UI  
✅ **Type-Safe** - Full TypeScript support with interfaces  
✅ **Memory-Safe** - Proper subscription management and cleanup  
✅ **Extensible** - Easy to add new routes and contexts  

### Live Example

```
User navigates to: /utilisateur-profil
Breadcrumb shows: Administration > Utilisateur Profil

User clicks "Packs de profils" tab
Breadcrumb updates to: Administration > Utilisateur Profil > 2ème étape / Packs

User clicks "Profils individuels" tab
Breadcrumb updates to: Administration > Utilisateur Profil > 2ème étape / Profils
```

---

## 🏗️ Architecture

### System Components

```
┌─────────────────────────────────────────────────────────────┐
│                    Angular Router                           │
│                 (NavigationEnd events)                      │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│              BreadcrumbComponent                            │
│  - Subscribes to router events                              │
│  - Subscribes to context changes                            │
│  - Matches URL to configuration                             │
│  - Builds breadcrumb items                                  │
└──────────────┬──────────────────┬───────────────────────────┘
               │                  │
               ▼                  ▼
┌──────────────────────┐  ┌──────────────────────────────────┐
│ BreadcrumbContext    │  │   BREADCRUMB_MAP                 │
│     Service          │  │   (Configuration)                │
│                      │  │                                  │
│ - Manages state      │  │ - Route patterns                 │
│ - BehaviorSubject    │  │ - Titles & icons                 │
│ - Observable stream  │  │ - Builder functions              │
└──────────┬───────────┘  └──────────────────────────────────┘
           │
           ▼
┌─────────────────────────────────────────────────────────────┐
│              Feature Components                             │
│  (e.g., UtilisateurProfilComponent)                        │
│                                                             │
│  - Call breadcrumbContext.setContext()                     │
│  - Trigger breadcrumb updates                               │
└─────────────────────────────────────────────────────────────┘
```

### Data Flow

```
1. User Action (navigation, tab click, etc.)
   ↓
2. Component calls breadcrumbContext.setContext(route, context)
   ↓
3. BehaviorSubject emits new context
   ↓
4. BreadcrumbComponent receives update via subscription
   ↓
5. updateBreadcrumb() is called
   ↓
6. URL is cleaned (remove query params)
   ↓
7. getBreadcrumbConfig() matches URL to configuration
   ↓
8. buildBreadcrumb() function is called (if defined)
   ↓
9. Breadcrumb items are updated in UI
```

### File Structure

```
src/
├── app/
│   ├── core/
│   │   ├── configuration/
│   │   │   └── breadcrumb.config.ts          # Route configurations
│   │   └── services/
│   │       └── frontend/
│   │           └── breadcrumb-context.service.ts  # Context management
│   ├── shared/
│   │   └── breadcrumb/
│   │       ├── breadcrumb.component.ts        # Main component
│   │       ├── breadcrumb.component.html      # Template
│   │       └── breadcrumb.component.scss      # Styles
│   └── features/
│       └── [your-feature]/
│           └── your-component.ts              # Uses breadcrumb
```

---

## 🚀 Implementation Guide

### Step 1: Understand the Core Components

#### 1.1 BreadcrumbContextService

**Purpose**: Manages breadcrumb context across the application.

**Location**: `src/app/core/services/frontend/breadcrumb-context.service.ts`

```typescript
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface BreadcrumbContext {
  route: string;      // The route path (e.g., '/utilisateur-profil')
  context?: string;   // Optional context (e.g., 'packs', 'profiles')
}

@Injectable({
  providedIn: 'root'
})
export class BreadcrumbContextService {
  private contextSubject = new BehaviorSubject<BreadcrumbContext>({ route: '/' });
  public context$: Observable<BreadcrumbContext> = this.contextSubject.asObservable();
  
  setContext(route: string, context?: string): void {
    console.log('🔖 Breadcrumb context updated:', { route, context });
    this.contextSubject.next({ route, context });
  }
  
  getContext(): BreadcrumbContext {
    return this.contextSubject.value;
  }
  
  clearContext(): void {
    this.contextSubject.next({ route: '/' });
  }
}
```

**Key Methods**:
- `setContext(route, context)` - Update breadcrumb context
- `getContext()` - Get current context
- `clearContext()` - Reset to default

#### 1.2 BreadcrumbComponent

**Purpose**: Displays breadcrumbs and handles route/context updates.

**Location**: `src/app/shared/breadcrumb/breadcrumb.component.ts`

**Key Features**:
- Subscribes to router NavigationEnd events
- Subscribes to breadcrumbContext changes
- Matches URLs to configurations
- Handles query parameters
- Null-safe with fallbacks

#### 1.3 BREADCRUMB_MAP Configuration

**Purpose**: Centralized route-to-breadcrumb mapping.

**Location**: `src/app/core/configuration/breadcrumb.config.ts`

**Structure**:
```typescript
export interface BreadcrumbConfig {
  title: string;                              // Page title
  icon: string;                               // PrimeNG icon class
  url: string;                                // Base URL
  items: Array<{                              // Static breadcrumb items
    label: string;
    icon: string;
    routerLink: string;
  }>;
  subItems?: Array<{                          // Alternative to items
    label: string;
    routerLink: string;
  }>;
  buildBreadcrumb?: (context?: string) =>     // Dynamic builder function
    Array<{ label: string; routerLink: string }>;
}
```

---

## 📝 How to Implement in Your Component

### Scenario: Add Dynamic Breadcrumbs to a New Feature

Let's say you want to add breadcrumbs to a **"Product Management"** feature with two tabs: **"Products"** and **"Categories"**.

### Step 1: Add Route Configuration

Open: `src/app/core/configuration/breadcrumb.config.ts`

```typescript
export const BREADCRUMB_MAP: Record<string, BreadcrumbConfig> = {
  // ...existing configurations...
  
  // 👇 Add your new route configuration
  '/product-management': {
    title: 'Gestion des Produits',
    icon: 'pi pi-box',
    url: '/product-management',
    items: [],
    buildBreadcrumb: (context?: string) => {
      const items = [
        { label: 'Accueil', routerLink: '/' },
        { label: 'Gestion des Produits', routerLink: '/product-management' }
      ];
      
      // Add context-specific breadcrumb based on active tab
      if (context === 'categories') {
        items.push({ 
          label: 'Vue / Catégories', 
          routerLink: '' 
        });
      } else if (context === 'products') {
        items.push({ 
          label: 'Vue / Produits', 
          routerLink: '' 
        });
      }
      
      return items;
    }
  },
};
```

### Step 2: Add Breadcrumb to Your Component Template

Open: `src/app/features/product-management/product-management.component.html`

```html
<!-- Add breadcrumb at the top of your component -->
<app-breadcrumb></app-breadcrumb>

<!-- Your existing content -->
<div class="product-management-container">
  <!-- Tab component -->
  <app-tab-toggle 
    [tabs]="['Produits', 'Catégories']" 
    [selectedIndex]="selectedTabIndex"
    (tabChange)="onTabChanged($event)">
  </app-tab-toggle>
  
  <!-- Tab content -->
  <div *ngIf="currentView === 'products'">
    <!-- Products view -->
  </div>
  
  <div *ngIf="currentView === 'categories'">
    <!-- Categories view -->
  </div>
</div>
```

### Step 3: Update Component TypeScript

Open: `src/app/features/product-management/product-management.component.ts`

```typescript
import { Component, OnInit } from '@angular/core';
import { BreadcrumbContextService } from '../../../core/services/frontend/breadcrumb-context.service';

@Component({
  selector: 'app-product-management',
  templateUrl: './product-management.component.html',
  styleUrls: ['./product-management.component.scss']
})
export class ProductManagementComponent implements OnInit {
  
  // Track current view
  currentView: 'products' | 'categories' = 'products';
  selectedTabIndex: number = 0;
  
  constructor(
    private breadcrumbContext: BreadcrumbContextService
  ) {}
  
  ngOnInit(): void {
    // Set initial breadcrumb context
    this.breadcrumbContext.setContext('/product-management', 'products');
  }
  
  /**
   * Handle tab change and update breadcrumb
   */
  onTabChanged(tabLabel: string): void {
    // Determine view based on tab label
    if (tabLabel === 'Catégories') {
      this.currentView = 'categories';
      this.selectedTabIndex = 1;
      
      // Update breadcrumb context
      this.breadcrumbContext.setContext('/product-management', 'categories');
    } else {
      this.currentView = 'products';
      this.selectedTabIndex = 0;
      
      // Update breadcrumb context
      this.breadcrumbContext.setContext('/product-management', 'products');
    }
  }
}
```

### Step 4: Import BreadcrumbComponent (if not already done)

Your breadcrumb component should already be exported from `SharedFrontModule`. If not:

Open: `src/app/shared/shared-front/shared-front.module.ts`

```typescript
@NgModule({
  declarations: [
    BreadcrumbComponent,  // ✅ Declared
    // ...other components
  ],
  exports: [
    BreadcrumbComponent,  // ✅ Exported
    // ...other exports
  ],
})
export class SharedFrontModule {}
```

Then import `SharedFrontModule` in your feature module:

```typescript
import { SharedFrontModule } from '../../../shared/shared-front/shared-front.module';

@NgModule({
  imports: [
    SharedFrontModule,  // ✅ Import this
    // ...other imports
  ],
})
export class ProductManagementModule {}
```

---

## 🎯 Configuration Reference

### Basic Static Breadcrumb

Use when breadcrumb never changes:

```typescript
'/my-static-page': {
  title: 'Static Page',
  icon: 'pi pi-file',
  url: '/my-static-page',
  items: [],
  subItems: [
    { label: 'Home', routerLink: '/' },
    { label: 'Static Page', routerLink: '/my-static-page' },
  ],
}
```

### Dynamic Breadcrumb with URL Parameter

Use for detail pages with IDs:

```typescript
'/products/:id': {
  title: 'Product Details',
  icon: 'pi pi-box',
  url: '/products',
  items: [],
  buildBreadcrumb: (id) => [
    { label: 'Home', routerLink: '/' },
    { label: 'Products', routerLink: '/products' },
    { label: `Product #${id}`, routerLink: `/products/${id}` }
  ]
}
```

### Dynamic Breadcrumb with Context

Use for pages with multiple views/tabs:

```typescript
'/dashboard': {
  title: 'Dashboard',
  icon: 'pi pi-chart-line',
  url: '/dashboard',
  items: [],
  buildBreadcrumb: (context?: string) => {
    const items = [
      { label: 'Home', routerLink: '/' },
      { label: 'Dashboard', routerLink: '/dashboard' }
    ];
    
    if (context === 'analytics') {
      items.push({ label: 'Analytics View', routerLink: '' });
    } else if (context === 'reports') {
      items.push({ label: 'Reports View', routerLink: '' });
    }
    
    return items;
  }
}
```

### Breadcrumb with Conditional Logic

Use for complex scenarios:

```typescript
'/user-profile/:id': {
  title: 'User Profile',
  icon: 'pi pi-user',
  url: '/user-profile',
  items: [],
  buildBreadcrumb: (id) => {
    const items = [
      { label: 'Home', routerLink: '/' },
      { label: 'Users', routerLink: '/users' }
    ];
    
    // Add different breadcrumb based on ID
    if (id === 'me') {
      items.push({ label: 'My Profile', routerLink: '/user-profile/me' });
    } else {
      items.push({ label: `User ${id}`, routerLink: `/user-profile/${id}` });
    }
    
    return items;
  }
}
```

---

## 💡 Usage Examples

### Example 1: Simple Page (No Dynamic Context)

**Scenario**: A settings page that doesn't change based on user interaction.

**breadcrumb.config.ts**:
```typescript
'/settings': {
  title: 'Settings',
  icon: 'pi pi-cog',
  url: '/settings',
  items: [],
  subItems: [
    { label: 'Home', routerLink: '/' },
    { label: 'Settings', routerLink: '/settings' },
  ],
}
```

**settings.component.html**:
```html
<app-breadcrumb></app-breadcrumb>
<div class="settings-content">
  <!-- Your settings form -->
</div>
```

**Result**:
```
Home > Settings
```

---

### Example 2: Tabbed Interface

**Scenario**: A user management page with "Users" and "Roles" tabs.

**breadcrumb.config.ts**:
```typescript
'/user-management': {
  title: 'User Management',
  icon: 'pi pi-users',
  url: '/user-management',
  items: [],
  buildBreadcrumb: (context?: string) => {
    const items = [
      { label: 'Home', routerLink: '/' },
      { label: 'User Management', routerLink: '/user-management' }
    ];
    
    if (context === 'roles') {
      items.push({ label: 'Roles', routerLink: '' });
    } else if (context === 'users') {
      items.push({ label: 'Users', routerLink: '' });
    }
    
    return items;
  }
}
```

**user-management.component.ts**:
```typescript
export class UserManagementComponent {
  currentTab: 'users' | 'roles' = 'users';
  
  constructor(private breadcrumbContext: BreadcrumbContextService) {}
  
  ngOnInit(): void {
    this.breadcrumbContext.setContext('/user-management', 'users');
  }
  
  onTabChange(tab: 'users' | 'roles'): void {
    this.currentTab = tab;
    this.breadcrumbContext.setContext('/user-management', tab);
  }
}
```

**user-management.component.html**:
```html
<app-breadcrumb></app-breadcrumb>

<app-tab-toggle 
  [tabs]="['Users', 'Roles']"
  (tabChange)="onTabChange($event)">
</app-tab-toggle>

<div *ngIf="currentTab === 'users'">
  <!-- Users content -->
</div>

<div *ngIf="currentTab === 'roles'">
  <!-- Roles content -->
</div>
```

**Result**:
```
Users tab:  Home > User Management > Users
Roles tab:  Home > User Management > Roles
```

---

### Example 3: Master-Detail with ID

**Scenario**: List of products with detail view.

**breadcrumb.config.ts**:
```typescript
'/products': {
  title: 'Products',
  icon: 'pi pi-box',
  url: '/products',
  items: [],
  subItems: [
    { label: 'Home', routerLink: '/' },
    { label: 'Products', routerLink: '/products' },
  ],
},

'/products/:id': {
  title: 'Product Details',
  icon: 'pi pi-box',
  url: '/products',
  items: [],
  buildBreadcrumb: (id) => [
    { label: 'Home', routerLink: '/' },
    { label: 'Products', routerLink: '/products' },
    { label: `Product #${id}`, routerLink: `/products/${id}` }
  ]
}
```

**product-detail.component.html**:
```html
<app-breadcrumb></app-breadcrumb>

<div class="product-detail">
  <h2>Product {{ productId }}</h2>
  <!-- Product details -->
</div>
```

**Result**:
```
List page:   Home > Products
Detail page: Home > Products > Product #123
```

---

### Example 4: Multi-Step Wizard

**Scenario**: A 3-step wizard for creating an order.

**breadcrumb.config.ts**:
```typescript
'/create-order': {
  title: 'Create Order',
  icon: 'pi pi-shopping-cart',
  url: '/create-order',
  items: [],
  buildBreadcrumb: (step?: string) => {
    const items = [
      { label: 'Home', routerLink: '/' },
      { label: 'Create Order', routerLink: '/create-order' }
    ];
    
    if (step === 'step1') {
      items.push({ label: 'Step 1: Products', routerLink: '' });
    } else if (step === 'step2') {
      items.push({ label: 'Step 2: Delivery', routerLink: '' });
    } else if (step === 'step3') {
      items.push({ label: 'Step 3: Payment', routerLink: '' });
    }
    
    return items;
  }
}
```

**create-order.component.ts**:
```typescript
export class CreateOrderComponent {
  currentStep: number = 1;
  
  constructor(private breadcrumbContext: BreadcrumbContextService) {}
  
  ngOnInit(): void {
    this.updateBreadcrumb();
  }
  
  nextStep(): void {
    this.currentStep++;
    this.updateBreadcrumb();
  }
  
  previousStep(): void {
    this.currentStep--;
    this.updateBreadcrumb();
  }
  
  private updateBreadcrumb(): void {
    this.breadcrumbContext.setContext(
      '/create-order', 
      `step${this.currentStep}`
    );
  }
}
```

**Result**:
```
Step 1: Home > Create Order > Step 1: Products
Step 2: Home > Create Order > Step 2: Delivery
Step 3: Home > Create Order > Step 3: Payment
```

---

## 🔧 Advanced Features

### Feature 1: Conditional Breadcrumbs Based on User Role

```typescript
'/admin-panel': {
  title: 'Admin Panel',
  icon: 'pi pi-shield',
  url: '/admin-panel',
  items: [],
  buildBreadcrumb: (context?: string) => {
    const items = [{ label: 'Home', routerLink: '/' }];
    
    // You can access services here via dependency injection
    // or pass user role as context
    if (context === 'superadmin') {
      items.push({ label: 'Super Admin Panel', routerLink: '/admin-panel' });
    } else {
      items.push({ label: 'Admin Panel', routerLink: '/admin-panel' });
    }
    
    return items;
  }
}
```

### Feature 2: Breadcrumbs with Icons

```typescript
buildBreadcrumb: (context?: string) => [
  { 
    label: 'Home', 
    icon: 'pi pi-home',
    routerLink: '/' 
  },
  { 
    label: 'Products', 
    icon: 'pi pi-box',
    routerLink: '/products' 
  },
]
```

### Feature 3: Breadcrumbs with Query Parameters

The system automatically handles query parameters! No special configuration needed.

```typescript
// URL: /products?category=electronics&sort=price
// Breadcrumb config still works because query params are stripped
'/products': {
  title: 'Products',
  icon: 'pi pi-box',
  url: '/products',
  items: [],
  subItems: [
    { label: 'Home', routerLink: '/' },
    { label: 'Products', routerLink: '/products' },
  ],
}
```

### Feature 4: Programmatic Breadcrumb Updates

Update breadcrumb from anywhere in your component:

```typescript
export class MyComponent {
  constructor(private breadcrumbContext: BreadcrumbContextService) {}
  
  onFilterChange(filter: string): void {
    // Update breadcrumb when filter changes
    this.breadcrumbContext.setContext('/my-route', filter);
  }
  
  resetBreadcrumb(): void {
    // Clear context
    this.breadcrumbContext.clearContext();
  }
}
```

### Feature 5: Custom Breadcrumb Styling

Override breadcrumb styles in your component:

```scss
// your-component.component.scss
::ng-deep {
  .bar-container {
    background-color: #f5f5f5;
    
    .bar-title {
      color: #007F6D;
      font-weight: bold;
    }
    
    .p-breadcrumb {
      .p-menuitem-link {
        color: #666;
        
        &:hover {
          color: #007F6D;
        }
      }
    }
  }
}
```

---

## 🐛 Troubleshooting

### Issue 1: Breadcrumb Not Showing

**Symptoms**: Breadcrumb component renders but no items appear.

**Possible Causes**:
1. No matching configuration for current URL
2. Route not added to BREADCRUMB_MAP
3. URL path mismatch

**Solution**:
```typescript
// Check console for warnings:
// ⚠️ No match found for URL: /your-route

// Add your route to breadcrumb.config.ts:
'/your-route': {
  title: 'Your Page',
  icon: 'pi pi-file',
  url: '/your-route',
  items: [],
  subItems: [
    { label: 'Home', routerLink: '/' },
    { label: 'Your Page', routerLink: '/your-route' },
  ],
}
```

---

### Issue 2: Breadcrumb Not Updating on Tab Change

**Symptoms**: Breadcrumb stays the same when switching tabs.

**Possible Causes**:
1. Not calling `breadcrumbContext.setContext()`
2. Wrong route passed to `setContext()`
3. Context not defined in `buildBreadcrumb`

**Solution**:
```typescript
// In your component:
onTabChange(tab: string): void {
  // ✅ Call setContext with correct route and context
  this.breadcrumbContext.setContext('/your-route', tab);
}

// In breadcrumb.config.ts:
buildBreadcrumb: (context?: string) => {
  const items = [
    { label: 'Home', routerLink: '/' },
    { label: 'Your Page', routerLink: '/your-route' }
  ];
  
  // ✅ Handle your context
  if (context === 'tab1') {
    items.push({ label: 'Tab 1', routerLink: '' });
  }
  
  return items;
}
```

---

### Issue 3: Error "Cannot read property 'title' of undefined"

**Symptoms**: Console error when navigating.

**Possible Causes**:
1. URL doesn't match any pattern in BREADCRUMB_MAP
2. Missing fallback configuration

**Solution**:
```typescript
// Already fixed in the component with null-safe fallback
// Make sure you have a default '/' route:

'/': {
  title: 'Accueil',
  icon: 'pi pi-home',
  url: '/',
  items: [],
}
```

---

### Issue 4: Query Parameters Breaking Breadcrumb

**Symptoms**: Breadcrumb stops working when URL has `?param=value`.

**Solution**: ✅ Already fixed! The component automatically strips query parameters.

---

### Issue 5: Memory Leaks

**Symptoms**: Application slows down over time.

**Solution**: ✅ Already implemented! Component properly unsubscribes:

```typescript
ngOnDestroy() {
  this.routerSub?.unsubscribe();
  this.contextSub?.unsubscribe();
}
```

---

## ✅ Best Practices

### 1. Keep Configuration Organized

Group related routes together:

```typescript
export const BREADCRUMB_MAP: Record<string, BreadcrumbConfig> = {
  // ===== HOME =====
  '/': { /* ... */ },
  
  // ===== USER MANAGEMENT =====
  '/users': { /* ... */ },
  '/users/:id': { /* ... */ },
  '/user-roles': { /* ... */ },
  
  // ===== PRODUCT MANAGEMENT =====
  '/products': { /* ... */ },
  '/products/:id': { /* ... */ },
  '/categories': { /* ... */ },
};
```

### 2. Use Descriptive Context Names

```typescript
// ❌ Bad: cryptic names
setContext('/page', 'v1');
setContext('/page', 'mode2');

// ✅ Good: descriptive names
setContext('/user-management', 'users');
setContext('/user-management', 'roles');
```

### 3. Initialize Breadcrumb in ngOnInit

```typescript
ngOnInit(): void {
  // ✅ Set initial breadcrumb context
  this.breadcrumbContext.setContext('/your-route', 'initial-view');
}
```

### 4. Clear Context When Leaving

```typescript
ngOnDestroy(): void {
  // ✅ Optional: Clear breadcrumb when component is destroyed
  this.breadcrumbContext.clearContext();
}
```

### 5. Use TypeScript Enums for Context Values

```typescript
enum DashboardView {
  ANALYTICS = 'analytics',
  REPORTS = 'reports',
  STATISTICS = 'statistics'
}

onViewChange(view: DashboardView): void {
  this.breadcrumbContext.setContext('/dashboard', view);
}
```

### 6. Add Console Logging for Debugging

```typescript
onTabChange(tab: string): void {
  console.log('🔄 Tab changed to:', tab);
  this.breadcrumbContext.setContext('/your-route', tab);
}
```

### 7. Reuse Helper Functions

```typescript
// breadcrumb.config.ts
function createAdminBreadcrumb(
  page: string, 
  route: string
): BreadcrumbConfig {
  return {
    title: page,
    icon: 'pi pi-cog',
    url: route,
    items: [],
    subItems: [
      { label: 'Administration', routerLink: '/admin' },
      { label: page, routerLink: route },
    ],
  };
}

export const BREADCRUMB_MAP: Record<string, BreadcrumbConfig> = {
  '/admin/users': createAdminBreadcrumb('Users', '/admin/users'),
  '/admin/roles': createAdminBreadcrumb('Roles', '/admin/roles'),
  '/admin/settings': createAdminBreadcrumb('Settings', '/admin/settings'),
};
```

---

## 📚 API Reference

### BreadcrumbContextService

#### Methods

##### `setContext(route: string, context?: string): void`

Updates the breadcrumb context.

**Parameters**:
- `route` (string) - The route path (e.g., '/products')
- `context` (string, optional) - Additional context (e.g., 'list', 'grid')

**Example**:
```typescript
this.breadcrumbContext.setContext('/products', 'list');
```

---

##### `getContext(): BreadcrumbContext`

Gets the current breadcrumb context.

**Returns**: `BreadcrumbContext` object with `route` and `context`

**Example**:
```typescript
const current = this.breadcrumbContext.getContext();
console.log(current.route);    // '/products'
console.log(current.context);  // 'list'
```

---

##### `clearContext(): void`

Resets breadcrumb context to default.

**Example**:
```typescript
this.breadcrumbContext.clearContext();
```

---

### BreadcrumbConfig Interface

```typescript
interface BreadcrumbConfig {
  title: string;                    // Page title
  icon: string;                     // PrimeNG icon (e.g., 'pi pi-home')
  url: string;                      // Base URL
  items: Array<{                    // Static breadcrumb items
    label: string;
    icon?: string;
    routerLink: string;
  }>;
  subItems?: Array<{                // Alternative static items
    label: string;
    routerLink: string;
  }>;
  buildBreadcrumb?: (              // Dynamic builder function
    context?: string
  ) => Array<{
    label: string;
    routerLink: string;
  }>;
}
```

---

### BreadcrumbContext Interface

```typescript
interface BreadcrumbContext {
  route: string;      // Current route path
  context?: string;   // Optional context identifier
}
```

---

## 🎓 Complete Implementation Checklist

Use this checklist when implementing breadcrumbs in a new component:

### Configuration Phase

- [ ] Open `breadcrumb.config.ts`
- [ ] Add route configuration with appropriate pattern
- [ ] Define `title` and `icon`
- [ ] Choose configuration type:
  - [ ] Static (`subItems`)
  - [ ] Dynamic with ID (`buildBreadcrumb` with ID parameter)
  - [ ] Dynamic with context (`buildBreadcrumb` with context parameter)
- [ ] Test URL pattern matching

### Component Phase

- [ ] Import `BreadcrumbContextService` in component
- [ ] Inject service in constructor
- [ ] Add `<app-breadcrumb>` to template (top of component)
- [ ] Call `setContext()` in `ngOnInit()` for initial state
- [ ] Add event handlers for state changes (tabs, filters, etc.)
- [ ] Update context on state changes
- [ ] (Optional) Clear context in `ngOnDestroy()`
- [ ] Add debug logging for troubleshooting

### Module Phase

- [ ] Verify `SharedFrontModule` is imported in your feature module
- [ ] Verify `BreadcrumbComponent` is exported from `SharedFrontModule`

### Testing Phase

- [ ] Navigate to your component
- [ ] Verify breadcrumb appears with correct title
- [ ] Verify breadcrumb items are correct
- [ ] Test tab/view changes (if applicable)
- [ ] Verify breadcrumb updates correctly
- [ ] Test browser back/forward buttons
- [ ] Check console for any warnings
- [ ] Test with query parameters (if applicable)
- [ ] Verify no memory leaks (check DevTools > Memory)

---

## 🌟 Real-World Example: Complete Implementation

Let's implement breadcrumbs for a **"Report Manager"** with three views: **List**, **Calendar**, and **Analytics**.

### Step 1: Configuration

**breadcrumb.config.ts**:
```typescript
export const BREADCRUMB_MAP: Record<string, BreadcrumbConfig> = {
  // ...existing configs...
  
  '/reports': {
    title: 'Report Manager',
    icon: 'pi pi-chart-bar',
    url: '/reports',
    items: [],
    buildBreadcrumb: (view?: string) => {
      const items = [
        { label: 'Home', routerLink: '/' },
        { label: 'Reports', routerLink: '/reports' }
      ];
      
      switch (view) {
        case 'list':
          items.push({ label: 'List View', routerLink: '' });
          break;
        case 'calendar':
          items.push({ label: 'Calendar View', routerLink: '' });
          break;
        case 'analytics':
          items.push({ label: 'Analytics View', routerLink: '' });
          break;
      }
      
      return items;
    }
  },
};
```

### Step 2: Component TypeScript

**report-manager.component.ts**:
```typescript
import { Component, OnInit, OnDestroy } from '@angular/core';
import { BreadcrumbContextService } from '../../../core/services/frontend/breadcrumb-context.service';

enum ReportView {
  LIST = 'list',
  CALENDAR = 'calendar',
  ANALYTICS = 'analytics'
}

@Component({
  selector: 'app-report-manager',
  templateUrl: './report-manager.component.html',
  styleUrls: ['./report-manager.component.scss']
})
export class ReportManagerComponent implements OnInit, OnDestroy {
  
  currentView: ReportView = ReportView.LIST;
  ReportView = ReportView; // Make enum available in template
  
  constructor(
    private breadcrumbContext: BreadcrumbContextService
  ) {}
  
  ngOnInit(): void {
    // Set initial breadcrumb
    this.updateBreadcrumb();
  }
  
  ngOnDestroy(): void {
    // Optional: Clear breadcrumb on component destroy
    this.breadcrumbContext.clearContext();
  }
  
  /**
   * Switch between report views
   */
  switchView(view: ReportView): void {
    console.log('🔄 Switching to view:', view);
    this.currentView = view;
    this.updateBreadcrumb();
  }
  
  /**
   * Update breadcrumb based on current view
   */
  private updateBreadcrumb(): void {
    this.breadcrumbContext.setContext('/reports', this.currentView);
  }
}
```

### Step 3: Component Template

**report-manager.component.html**:
```html
<!-- Breadcrumb -->
<app-breadcrumb></app-breadcrumb>

<!-- View Selector -->
<div class="view-selector">
  <button 
    pButton 
    label="List View" 
    icon="pi pi-list"
    [class.active]="currentView === ReportView.LIST"
    (click)="switchView(ReportView.LIST)">
  </button>
  
  <button 
    pButton 
    label="Calendar View" 
    icon="pi pi-calendar"
    [class.active]="currentView === ReportView.CALENDAR"
    (click)="switchView(ReportView.CALENDAR)">
  </button>
  
  <button 
    pButton 
    label="Analytics View" 
    icon="pi pi-chart-line"
    [class.active]="currentView === ReportView.ANALYTICS"
    (click)="switchView(ReportView.ANALYTICS)">
  </button>
</div>

<!-- View Content -->
<div class="view-content">
  <div *ngIf="currentView === ReportView.LIST">
    <!-- List view content -->
    <p-table [value]="reports">
      <!-- Table content -->
    </p-table>
  </div>
  
  <div *ngIf="currentView === ReportView.CALENDAR">
    <!-- Calendar view content -->
    <p-fullCalendar [events]="reportEvents"></p-fullCalendar>
  </div>
  
  <div *ngIf="currentView === ReportView.ANALYTICS">
    <!-- Analytics view content -->
    <p-chart [data]="analyticsData"></p-chart>
  </div>
</div>
```

### Step 4: Module Import

**report-manager.module.ts**:
```typescript
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedFrontModule } from '../../../shared/shared-front/shared-front.module';
import { ReportManagerComponent } from './report-manager.component';

@NgModule({
  declarations: [
    ReportManagerComponent
  ],
  imports: [
    CommonModule,
    SharedFrontModule  // ✅ Import to get BreadcrumbComponent
  ]
})
export class ReportManagerModule {}
```

### Result

When user navigates and switches views:

```
Initial:          Home > Reports > List View
Clicks Calendar:  Home > Reports > Calendar View
Clicks Analytics: Home > Reports > Analytics View
Clicks List:      Home > Reports > List View
```

---

## 📖 Summary

### What You've Learned

✅ How the breadcrumb system works architecturally  
✅ How to configure routes in `breadcrumb.config.ts`  
✅ How to add breadcrumbs to any component  
✅ How to handle dynamic contexts (tabs, views, etc.)  
✅ How to debug breadcrumb issues  
✅ Best practices for maintainable breadcrumb code  

### Key Takeaways

1. **Configuration is centralized** - All breadcrumb routes go in `breadcrumb.config.ts`
2. **Service-based state management** - Use `BreadcrumbContextService` to update breadcrumbs
3. **Automatic URL handling** - Query parameters and hashes are automatically handled
4. **Type-safe and extensible** - Full TypeScript support with clear interfaces
5. **Memory-safe** - Proper subscription management prevents leaks

### Quick Reference

```typescript
// 1. Add config
BREADCRUMB_MAP['/my-route'] = { title, icon, buildBreadcrumb };

// 2. Add to template
<app-breadcrumb></app-breadcrumb>

// 3. Update from component
constructor(private breadcrumbContext: BreadcrumbContextService) {}
this.breadcrumbContext.setContext('/my-route', 'context');
```

---

## 🔗 Related Documentation

- [BREADCRUMB_SYSTEM_GUIDE.md](./BREADCRUMB_SYSTEM_GUIDE.md) - Technical reference
- [BREADCRUMB_IMPLEMENTATION_SUMMARY.md](./BREADCRUMB_IMPLEMENTATION_SUMMARY.md) - Implementation notes
- [PrimeNG Breadcrumb Documentation](https://primeng.org/breadcrumb)

---

## 📞 Support

If you encounter issues not covered in this documentation:

1. Check console logs for debug information (🔍, 🔖, ⚠️ emojis)
2. Verify your route is in `BREADCRUMB_MAP`
3. Confirm `setContext()` is being called with correct parameters
4. Review the troubleshooting section

---

**Documentation Version**: 1.0.0  
**Last Updated**: December 29, 2025  
**Author**: BNA HABIL Development Team  
**Status**: ✅ Production Ready

---

🎉 **You're all set!** Start adding beautiful, dynamic breadcrumbs to your application!
