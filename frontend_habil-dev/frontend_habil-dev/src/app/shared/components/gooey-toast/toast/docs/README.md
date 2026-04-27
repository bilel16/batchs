# 🫧 Gooey Toast — Angular Toast Engine

> Morphing blob notifications for Angular — fully self-contained, reactive, library-ready.

![Angular](https://img.shields.io/badge/Angular-16%2B-red?logo=angular)
![TypeScript](https://img.shields.io/badge/TypeScript-5.x-blue?logo=typescript)
![License](https://img.shields.io/badge/License-MIT-green)

---

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Quick Start](#quick-start)
- [Configuration](#configuration)
  - [forRoot() — Static Defaults](#forroot--static-defaults)
  - [ToastConfigService — Runtime Updates](#toastconfigservice--runtime-updates)
  - [Config Options Reference](#config-options-reference)
- [API — ToastService](#api--toastservice)
  - [Basic Methods](#basic-methods)
  - [Promise Toasts](#promise-toasts)
  - [Dismiss & Update](#dismiss--update)
- [Toast Options](#toast-options)
- [Action Buttons](#action-buttons)
- [Positioning](#positioning)
- [Theming](#theming)
- [Progress Bar](#progress-bar)
- [PrimeNG Bridge](#primeng-bridge)
- [Gooey Mode — SVG Blob Morphing](#gooey-mode--svg-blob-morphing)
- [Builder / Demo Page](#builder--demo-page)
- [Architecture](#architecture)
- [Publishing as npm Package](#publishing-as-npm-package)
- [Examples](#examples)

---

## Features

| Feature | Description |
|---------|-------------|
| 🫧 **Gooey blob morphing** | SVG path animation — pill ↔ expanded body with organic squish |
| ⚡ **Reactive config** | `BehaviorSubject`-based — change position/theme/duration live, no re-render |
| 🎨 **Light + Dark themes** | CSS custom properties, one class toggle |
| 📍 **6 positions** | `top-left`, `top-center`, `top-right`, `bottom-left`, `bottom-center`, `bottom-right` |
| 🔘 **Action buttons** | Click → morphs into success pill with checkmark |
| ⏱️ **Progress bar** | Animated countdown, pauses on hover |
| 🔄 **Promise toasts** | Loading spinner → success/error auto-transition |
| 💾 **localStorage persistence** | Config survives page refresh |
| 🧩 **`forRoot()` pattern** | `InjectionToken`-based DI — library-standard config injection |
| 🏗️ **Library-ready** | Zero app dependencies — publishable as `@yourname/gooey-toast-angular` |
| ♿ **Accessible** | ARIA labels, keyboard-friendly close buttons, `prefers-reduced-motion` support |

---

## Installation

### In-project (current setup)

The toast engine lives at:
```
src/app/shared/components/gooey-toast/toast/
```

No additional packages required — only `@angular/core`, `@angular/common`, and `rxjs`.

### Future npm package

```bash
npm install @yourname/gooey-toast-angular
```

---

## Quick Start

### 1. Import the module

```typescript
// app.module.ts
import { GooeyToastModule } from './shared/components/gooey-toast/toast';

@NgModule({
  imports: [
    GooeyToastModule.forRoot({
      position: 'top-right',
      offset: '80px',
      showProgress: true,
    }),
  ],
})
export class AppModule {}
```

### 2. Place the container (once, in your root template)

```html
<!-- app.component.html -->
<gooey-toast-container></gooey-toast-container>
<router-outlet></router-outlet>
```

### 3. Show toasts

```typescript
import { ToastService } from './shared/components/gooey-toast/toast';

@Component({ /* ... */ })
export class MyComponent {
  constructor(private toast: ToastService) {}

  save() {
    this.toast.success('Saved successfully!');
  }

  handleError() {
    this.toast.error('Something went wrong', {
      description: 'Please try again later.',
      duration: 6000,
    });
  }
}
```

---

## Configuration

### `forRoot()` — Static Defaults

Set once in your root module. These become the baseline for all toasts:

```typescript
GooeyToastModule.forRoot({
  position: 'top-right',    // where toasts appear
  duration: 4000,            // auto-dismiss after 4s
  gap: 14,                   // spacing between stacked toasts (px)
  offset: '80px',            // distance from screen edge
  theme: 'light',            // 'light' or 'dark'
  maxQueue: Infinity,        // max visible toasts
  showProgress: true,        // show countdown progress bar
})
```

### `ToastConfigService` — Runtime Updates

Change config **live** without recreating the container:

```typescript
import { ToastConfigService } from './shared/components/gooey-toast/toast';

@Component({ /* ... */ })
export class SettingsComponent {
  constructor(private toastConfig: ToastConfigService) {}

  // Change position at runtime
  switchPosition() {
    this.toastConfig.setPosition('bottom-center');
  }

  // Toggle dark mode
  toggleTheme() {
    this.toastConfig.setTheme('dark');
  }

  // Apply multiple settings at once (Builder "Apply" button)
  applyBuilderConfig() {
    this.toastConfig.applyConfig({
      position: 'top-center',
      duration: 3000,
      theme: 'dark',
      showProgress: true,
      gap: 20,
    });
  }

  // Reset everything to factory defaults
  reset() {
    this.toastConfig.resetToDefaults();
  }

  // Read current config
  logCurrent() {
    console.log(this.toastConfig.snapshot);
  }

  // Subscribe to config changes
  ngOnInit() {
    this.toastConfig.config$.subscribe(cfg => {
      console.log('Config changed:', cfg);
    });
  }
}
```

### Config Options Reference

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `position` | `ToastPosition` | `'bottom-right'` | Screen position for the toast stack |
| `duration` | `number` | `4000` | Auto-dismiss duration in ms (0 = no auto-dismiss) |
| `gap` | `number` | `14` | Gap between stacked toasts (px) |
| `offset` | `number \| string` | `'24px'` | Distance from screen edge |
| `theme` | `'light' \| 'dark'` | `'light'` | Color theme |
| `maxQueue` | `number` | `Infinity` | Maximum simultaneously visible toasts |
| `showProgress` | `boolean` | `false` | Show animated countdown progress bar |

---

## API — ToastService

### Basic Methods

```typescript
// Neutral toast
toast.show('Hello world');

// Success
toast.success('Profile saved!');

// Error
toast.error('Connection failed', { description: 'Server unreachable.' });

// Warning
toast.warning('Session expires soon', { duration: 8000 });

// Info
toast.info('Update available', { showProgress: true });
```

All methods return the toast `id` (string | number) for later reference.

### Promise Toasts

Automatically transition from loading → success/error:

```typescript
// With Observable
const save$ = this.http.post('/api/save', data);
toast.promise(save$, {
  loading: 'Saving...',
  success: (res) => `Saved: ${res.name}`,
  error: (err) => `Failed: ${err.message}`,
  description: {
    loading: 'Please wait...',
    success: 'All changes persisted.',
    error: 'Please try again.',
  },
});

// With Promise
toast.promise(fetch('/api/data').then(r => r.json()), {
  loading: 'Loading data...',
  success: 'Data loaded!',
  error: 'Failed to load data',
});
```

### Dismiss & Update

```typescript
// Dismiss a specific toast
const id = toast.success('Saved!');
toast.dismiss(id);

// Dismiss all toasts
toast.dismiss();

// Update an existing toast
const id = toast.info('Processing...');
toast.update(id, {
  type: 'success',
  title: 'Done!',
  description: 'Processing complete.',
});
```

---

## Toast Options

Per-toast options override global config:

```typescript
toast.success('Saved!', {
  description: 'All changes have been persisted.',
  duration: 6000,
  showProgress: true,
  action: {
    label: 'Undo',
    onClick: () => this.undoSave(),
    successLabel: 'Undone!',
  },
  id: 'custom-id',
  fillColor: '#f0fdf4',
  borderColor: '#22c55e',
  borderWidth: 2,
  classNames: {
    wrapper: 'my-toast-wrapper',
    title: 'my-toast-title',
  },
  onDismiss: (id) => console.log(`Toast ${id} dismissed`),
  onAutoClose: (id) => console.log(`Toast ${id} auto-closed`),
});
```

| Option | Type | Description |
|--------|------|-------------|
| `description` | `string` | Body text below the title |
| `duration` | `number` | Override auto-dismiss ms (0 = permanent) |
| `action` | `ToastAction` | Action button config |
| `id` | `string \| number` | Custom identifier |
| `showProgress` | `boolean` | Override global progress bar setting |
| `fillColor` | `string` | Custom blob background color |
| `borderColor` | `string` | Custom blob border color |
| `borderWidth` | `number` | Border width in px (default: 1.5) |
| `classNames` | `ToastClassNames` | CSS class overrides for inner elements |
| `onDismiss` | `(id) => void` | Callback when toast is dismissed |
| `onAutoClose` | `(id) => void` | Callback when toast auto-closes |

---

## Action Buttons

Action buttons morph into a success pill after click:

```typescript
toast.warning('Unsaved changes', {
  description: 'You have unsaved work.',
  action: {
    label: 'Save now',
    onClick: () => {
      this.saveAll();
    },
    successLabel: 'Saved!', // Pill text after click (with ✓ checkmark)
  },
  duration: 10000,
});
```

**Flow:** `[Save now]` → click → `✓ Saved!` (success pill) → auto-collapse

---

## Positioning

Six positions supported:

```
┌──────────────────────────────────────┐
│  top-left    top-center    top-right │
│                                      │
│                                      │
│                                      │
│ bottom-left bottom-center bottom-right│
└──────────────────────────────────────┘
```

Change at runtime:
```typescript
this.toastConfig.setPosition('top-center');
```

---

## Theming

### Light (default)
Clean white blobs with subtle shadows.

### Dark
```typescript
this.toastConfig.setTheme('dark');
```
Dark gray blobs with adjusted icon/text colors.

### Custom colors per toast
```typescript
toast.info('Custom', {
  fillColor: '#1e1b4b',
  borderColor: '#818cf8',
});
```

---

## Progress Bar

Visual countdown bar at the bottom of each toast:

```typescript
// Global (all toasts)
GooeyToastModule.forRoot({ showProgress: true })

// Per-toast override
toast.info('Loading...', { showProgress: true, duration: 5000 });
```

- Animates from 100% → 0% over the toast's `duration`
- **Pauses on hover** — timer resumes when mouse leaves
- Color matches the toast type (green for success, red for error, etc.)

---

## PrimeNG Bridge

The `ToastInterceptorService` automatically bridges `MessageService.add()` calls to the gooey toast system:

```typescript
// Any existing code using MessageService still works:
this.messageService.add({
  severity: 'success',
  summary: 'User created',
  detail: 'The account has been set up.',
  life: 5000,
});
// → Automatically shown as a gooey toast!
```

The PrimeNG `<p-toast>` is kept hidden as a fallback. The interceptor maps:
- `severity: 'success'` → `toast.success()`
- `severity: 'error'` → `toast.error()`
- `severity: 'warn'` → `toast.warning()`
- `severity: 'info'` → `toast.info()`

---

## Gooey Mode — SVG Blob Morphing

The signature visual effect — SVG `<path>` morphs organically:

1. **Pill phase** — Small rounded pill with icon + title
2. **Expand** — Blob morphs open, revealing description/action
3. **Hover rescue** — If user hovers, auto-dismiss pauses
4. **Collapse** — Blob morphs closed, then fades out

All animations use custom cubic-bezier easing (`smoothEase`, `entryEase`) with `requestAnimationFrame` for 60fps performance.

---

## Builder / Demo Page

Navigate to `/ressources/toast-test` for the interactive builder:

- 🎨 Pick type (success, error, warning, info, default)
- 📍 Choose position (6 grid buttons)
- ✏️ Edit title & description
- 🔘 Toggle action button, progress bar
- ⏱️ Adjust duration slider
- 🌙 Dark mode toggle
- 📋 Live code preview
- 🎯 Preset examples (promise toasts, spam test, bridge test)

---

## Architecture

```
toast/
├── animations/
│   └── toast.animations.ts        # Morph math, easing, SVG path generators
├── components/
│   └── toast-container.component.ts  # Main container (template + logic, 980+ lines)
├── docs/
│   └── README.md                  # This file
├── models/
│   ├── toast.model.ts             # Public types (ToastType, ToastOptions, ToastState, etc.)
│   └── toast-config.model.ts      # Config interface, InjectionToken, defaults
├── services/
│   ├── toast.service.ts           # Imperative API (show/success/error/warning/info/promise/dismiss)
│   └── toast-config.service.ts    # Reactive config (BehaviorSubject, localStorage, DI)
├── styles/
│   ├── _toast-base.scss           # Layout, positioning, typography, responsive
│   ├── _toast-theme.scss          # Light/dark color tokens, per-type colors
│   └── _toast-gooey.scss          # SVG blob drop-shadow filter
├── toast.module.ts                # NgModule with forRoot() static method
├── public-api.ts                  # Public API barrel (all exports)
└── index.ts                       # Re-export barrel
```

### Key Design Decisions

| Decision | Rationale |
|----------|-----------|
| **No `@Input` bindings on container** | Config flows through `ToastConfigService` → reactive, not template-driven |
| **`BehaviorSubject<ToastConfig>`** | Components subscribe and get instant updates — no zone tricks needed |
| **`InjectionToken` + `forRoot()`** | Standard Angular library pattern — tree-shakeable, testable |
| **`providedIn: 'root'`** on services | Singleton guarantee without manual provider registration |
| **localStorage persistence** | Builder config survives refresh — opt-in via `applyConfig()` |
| **Inline template** | Single-file component — easier to ship as library |
| **3 SCSS partials** | Separation of concerns (structure / color / effects) |

---

## Publishing as npm Package

The `/toast` folder is structured for extraction:

### 1. Create package scaffolding

```
gooey-toast-angular/
├── src/
│   └── (copy entire toast/ folder contents)
├── package.json
├── ng-package.json
├── tsconfig.lib.json
└── README.md
```

### 2. `ng-package.json`

```json
{
  "$schema": "../../node_modules/ng-packagr/ng-package.schema.json",
  "dest": "../../dist/gooey-toast-angular",
  "lib": {
    "entryFile": "src/public-api.ts"
  }
}
```

### 3. `package.json`

```json
{
  "name": "@yourname/gooey-toast-angular",
  "version": "1.0.0",
  "peerDependencies": {
    "@angular/common": "^16.0.0 || ^17.0.0 || ^18.0.0 || ^19.0.0",
    "@angular/core": "^16.0.0 || ^17.0.0 || ^18.0.0 || ^19.0.0",
    "rxjs": "^7.0.0"
  }
}
```

### 4. Build & Publish

```bash
ng build gooey-toast-angular
cd dist/gooey-toast-angular
npm publish --access public
```

### 5. Consumer usage

```typescript
import { GooeyToastModule, ToastService } from '@yourname/gooey-toast-angular';
```

---

## Examples

### Basic success toast

```typescript
this.toast.success('Changes saved!');
```

### Error with description

```typescript
this.toast.error('Upload failed', {
  description: 'The file exceeds the 10MB limit.',
  duration: 8000,
  showProgress: true,
});
```

### Warning with action button

```typescript
this.toast.warning('Delete item?', {
  description: 'This action cannot be undone.',
  action: {
    label: 'Delete',
    onClick: () => this.deleteItem(),
    successLabel: 'Deleted!',
  },
  duration: 10000,
});
```

### Promise toast (Observable)

```typescript
const upload$ = this.fileService.upload(file);
this.toast.promise(upload$, {
  loading: 'Uploading file...',
  success: (res) => `Uploaded: ${res.filename}`,
  error: 'Upload failed',
  description: {
    loading: `Uploading ${file.name} (${file.size} bytes)`,
    success: (res) => `${res.filename} — ${res.size} bytes`,
    error: 'Please check your connection and try again.',
  },
});
```

### Centered toast (e.g. cookie banner)

```typescript
this.toastConfig.applyConfig({ position: 'bottom-center' });
this.toast.info('We use cookies', {
  description: 'By continuing, you agree to our cookie policy.',
  action: {
    label: 'Accept',
    onClick: () => this.acceptCookies(),
    successLabel: 'Accepted!',
  },
  duration: 0, // No auto-dismiss
});
```

### Runtime theme toggle

```typescript
toggleDarkMode() {
  const current = this.toastConfig.snapshot.theme;
  this.toastConfig.setTheme(current === 'light' ? 'dark' : 'light');
}
```

---

## License

MIT — Built for the BNA Habilitation project. Ready for open-source extraction.
