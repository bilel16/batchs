# AnimatedDrawerComponent

A highly customizable and animated drawer component that morphs from a small square (inspired by login squares) into a full-height right-side panel. The component maintains consistency with the login page square transformations and provides smooth 3-phase animations.

## 🎯 Features

- **3-Phase Morphing Animation**: Square appears → slides down and expands → content reveals
- **Login-Inspired Design**: Matches the square morphing aesthetic from the login page
- **Flexible Width**: Configurable width with responsive breakpoints
- **BNA Habilitation UI Compliance**: Follows brand color scheme and design standards
- **Backdrop Blur Effect**: Professional overlay with blur backdrop
- **Keyboard Support**: ESC key to close, proper ARIA labels
- **Reusable**: Perfect for CRUD operations, forms, and detail views

## 📋 Table of Contents

- [Installation](#installation)
- [Basic Usage](#basic-usage)
- [API Reference](#api-reference)
- [Customization](#customization)
- [Animation Phases](#animation-phases)
- [Responsive Design](#responsive-design)
- [Styling](#styling)
- [Examples](#examples)
- [Best Practices](#best-practices)

## 🚀 Installation

The component is already available in the shared components module. Make sure it's imported in your module:

```typescript
import { AnimatedDrawerComponent } from './shared/components/animated-drawer/animated-drawer.component';

@NgModule({
  declarations: [
    AnimatedDrawerComponent,
    // ... other components
  ],
  // ...
})
export class YourModule { }
```

## 📖 Basic Usage

### Simple Example

```html
<app-animated-drawer 
  [visible]="showDrawer" 
  title="Add New Item"
  (closed)="onDrawerClosed()">
  
  <!-- Your content here -->
  <form>
    <div class="form-group">
      <label>Name</label>
      <input type="text" pInputText />
    </div>
  </form>
  
  <!-- Footer buttons -->
  <div slot="footer" class="drawer-footer-buttons">
    <button pButton label="Cancel" class="btn-secondary" (click)="closeDrawer()"></button>
    <button pButton label="Save" class="btn-primary" (click)="save()"></button>
  </div>
</app-animated-drawer>
```

### Component Logic

```typescript
export class YourComponent {
  showDrawer = false;

  openDrawer() {
    this.showDrawer = true;
  }

  closeDrawer() {
    this.showDrawer = false;
  }

  onDrawerClosed() {
    console.log('Drawer closed');
    // Reset form or perform cleanup
  }
}
```

## 🔧 API Reference

### Inputs

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| `visible` | `boolean` | `false` | Controls the visibility of the drawer |
| `title` | `string` | `'Drawer'` | The title displayed in the drawer header |
| `width` | `string` | `'400px'` | Width of the drawer (px, %, rem, etc.) |
| `showBackdrop` | `boolean` | `true` | Whether to show the backdrop blur effect |
| `customClass` | `string` | `''` | Custom CSS class for additional styling |

### Outputs

| Event | Type | Description |
|-------|------|-------------|
| `closed` | `EventEmitter<void>` | Emitted when the drawer is closed |
| `opening` | `EventEmitter<void>` | Emitted when the drawer animation starts opening |
| `opened` | `EventEmitter<void>` | Emitted when the drawer animation completes opening |

### Content Projection

| Selector | Description |
|----------|-------------|
| Default | Main content area of the drawer |
| `[slot="footer"]` | Footer area for buttons or actions |

## 🎨 Customization

### Width Options

```html
<!-- Fixed width -->
<app-animated-drawer width="350px" title="Narrow Drawer">
  <!-- Content -->
</app-animated-drawer>

<!-- Percentage width -->
<app-animated-drawer width="30%" title="Responsive Drawer">
  <!-- Content -->
</app-animated-drawer>

<!-- Wide drawer for complex forms -->
<app-animated-drawer width="600px" title="Wide Form">
  <!-- Complex form content -->
</app-animated-drawer>
```

### Custom Styling

```html
<app-animated-drawer 
  customClass="my-custom-drawer"
  title="Styled Drawer">
  <!-- Content -->
</app-animated-drawer>
```

```scss
// In your component's SCSS file
:host ::ng-deep .my-custom-drawer {
  .drawer-header {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  }
  
  .drawer-title {
    color: #ffffff;
    font-size: 1.25rem;
  }
}
```

## 🎬 Animation Phases

### Opening Animation (3 Phases)

1. **Phase 1 (0-300ms)**: Square Appearance
   - Small square materializes in top-right corner
   - Scale animation from 0 to 1

2. **Phase 2 (300-1500ms)**: Morphing & Expansion
   - Square slides down slightly
   - Rotates and expands to full drawer dimensions
   - Transforms from 6em×6em to full width×height

3. **Phase 3 (1500ms+)**: Content Reveal
   - Drawer content slides in from bottom
   - Header slides in from right with 0.2s delay
   - All elements become interactive

### Closing Animation

1. **Content Fade**: Header and content fade out
2. **Shape Morphing**: Drawer transforms back to rectangle
3. **Slide Up**: Rectangle slides up while rotating
4. **Final Shrink**: Becomes small square and disappears

## 📱 Responsive Design

### Breakpoints

| Screen Size | Drawer Width | Behavior |
|-------------|--------------|----------|
| Desktop (>1024px) | Configured width (max 40vw) | Normal drawer behavior |
| Tablet (≤1024px) | 50% of screen width | Responsive width override |
| Mobile (≤768px) | 100% of screen width | Full-screen drawer |
| Small Mobile (≤500px) | 100% of screen width | Optimized for small screens |

### Square Size Adjustments

- **Desktop**: 6em × 6em
- **Tablet**: 4em × 4em  
- **Mobile**: 3em × 3em

## 🎨 Styling

### Color Scheme (BNA Habilitation UI)

```scss
// Primary Colors
$primary-dark: #162636;     // Header background
$accent-green: #37978f;     // Accent elements, icons
$secondary-green: #00ab86;  // Hover states, gradients
$background: #f9fafb;       // Body background
$text-primary: #1e293b;     // Main text color
```

### Header Styling

- **Background**: Dark blue (#162636) with gradient accent line
- **Title**: White text, uppercase, Cairo font
- **Close Button**: Hover effects with rotation animation
- **Accent Line**: Animated gradient flow at top

### Body Styling

- **Background**: White with subtle gradient from header
- **Scrollbar**: Custom styled for consistency
- **Padding**: 2rem on desktop, responsive on mobile

## 📚 Examples

### CRUD Operations

```html
<!-- Add/Edit Form -->
<app-animated-drawer 
  [visible]="showApplicationForm" 
  [title]="isEdit ? 'Edit Application' : 'Add Application'"
  width="450px"
  (closed)="resetForm()">
  
  <form [formGroup]="applicationForm">
    <div class="form-group">
      <label for="codApp">Application Code *</label>
      <input id="codApp" type="text" pInputText formControlName="codApp" />
    </div>
    
    <div class="form-group">
      <label for="libApp">Application Name *</label>
      <input id="libApp" type="text" pInputText formControlName="libApp" />
    </div>
    
    <div class="form-group">
      <label for="description">Description</label>
      <textarea id="description" pInputTextarea formControlName="description" rows="4"></textarea>
    </div>
  </form>
  
  <div slot="footer" class="drawer-footer-buttons">
    <button pButton label="Cancel" class="btn-secondary" (click)="closeDrawer()"></button>
    <button pButton label="Save" class="btn-primary" [disabled]="!applicationForm.valid" (click)="saveApplication()"></button>
  </div>
</app-animated-drawer>
```

### Detail View

```html
<!-- Read-only Detail View -->
<app-animated-drawer 
  [visible]="showDetails" 
  title="Application Details"
  width="500px"
  (closed)="selectedItem = null">
  
  <div class="detail-section" *ngIf="selectedItem">
    <h4>Basic Information</h4>
    <div class="detail-row">
      <label>Code:</label>
      <span>{{ selectedItem.codApp }}</span>
    </div>
    <div class="detail-row">
      <label>Name:</label>
      <span>{{ selectedItem.libApp }}</span>
    </div>
    <div class="detail-row">
      <label>Created:</label>
      <span>{{ selectedItem.createdDate | date:'medium' }}</span>
    </div>
  </div>
  
  <div slot="footer" class="drawer-footer-buttons">
    <button pButton label="Close" class="btn-secondary" (click)="closeDetails()"></button>
    <button pButton label="Edit" class="btn-primary" (click)="editItem(selectedItem)"></button>
  </div>
</app-animated-drawer>
```

### Settings Panel

```html
<!-- Settings/Configuration -->
<app-animated-drawer 
  [visible]="showSettings" 
  title="User Settings"
  width="380px"
  customClass="settings-drawer">
  
  <div class="settings-section">
    <h4>Preferences</h4>
    <div class="setting-item">
      <label>Theme</label>
      <p-dropdown [options]="themes" [(ngModel)]="selectedTheme"></p-dropdown>
    </div>
    <div class="setting-item">
      <label>Language</label>
      <p-dropdown [options]="languages" [(ngModel)]="selectedLanguage"></p-dropdown>
    </div>
    <div class="setting-item">
      <p-checkbox [(ngModel)]="notifications" label="Enable Notifications"></p-checkbox>
    </div>
  </div>
  
  <div slot="footer" class="drawer-footer-buttons">
    <button pButton label="Cancel" class="btn-secondary" (click)="cancelSettings()"></button>
    <button pButton label="Apply" class="btn-primary" (click)="saveSettings()"></button>
  </div>
</app-animated-drawer>
```

## ✅ Best Practices

### 1. State Management

```typescript
export class YourComponent {
  // Use clear state variables
  showDrawer = false;
  isEdit = false;
  selectedItem: any = null;

  // Reset state when drawer closes
  onDrawerClosed() {
    this.resetForm();
    this.selectedItem = null;
    this.isEdit = false;
  }
}
```

### 2. Form Handling

```typescript
// Disable form submission during animations
saveItem() {
  if (this.isAnimating) return;
  
  // Your save logic
  this.showDrawer = false;
}
```

### 3. Responsive Considerations

```html
<!-- Use appropriate widths for different use cases -->
<!-- Simple forms: 350-400px -->
<!-- Complex forms: 500-600px -->
<!-- Detail views: 450-550px -->
<app-animated-drawer width="400px" title="Simple Form">
  <!-- Minimal form content -->
</app-animated-drawer>
```

### 4. Accessibility

```html
<!-- Always provide meaningful titles -->
<app-animated-drawer 
  [title]="isEdit ? 'Edit ' + itemName : 'Create New Item'"
  [visible]="showDrawer">
  
  <!-- Use proper form labels -->
  <label for="itemName">Item Name *</label>
  <input id="itemName" type="text" pInputText required />
</app-animated-drawer>
```

### 5. Performance

```typescript
// Use OnPush change detection for better performance
@Component({
  selector: 'app-your-component',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class YourComponent {
  // Component logic
}
```

## 🔧 Troubleshooting

### Common Issues

1. **Drawer not showing**: Check that `visible` is properly bound
2. **Animation glitches**: Ensure no conflicting CSS transforms
3. **Content overflow**: Use proper responsive styles in drawer body
4. **Z-index conflicts**: The drawer uses z-index 1200, adjust if needed

### Browser Support

- Chrome/Edge: Full support
- Firefox: Full support  
- Safari: Full support (webkit prefixes included)
- IE11: Not supported (uses modern CSS features)

---

## 📄 License

This component is part of the BNA Habilitation UI library and follows the project's licensing terms.

---

## 🤝 Contributing

To contribute improvements or report issues:

1. Follow the existing code style and patterns
2. Test animations across different screen sizes
3. Ensure accessibility compliance
4. Update documentation for any API changes
