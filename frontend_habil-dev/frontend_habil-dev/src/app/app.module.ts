/********************** angular imports **********************************/
/*************************************************************************/
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgModule, LOCALE_ID } from "@angular/core";
import { CommonModule, registerLocaleData } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {  provideHttpClient, withInterceptors,} from "@angular/common/http";
import localeFr from "@angular/common/locales/fr";
import { RouterModule } from "@angular/router";

/********************** primeng imports **********************************/
/*************************************************************************/
import { ConfirmationService, MessageService, SharedModule } from "primeng/api";
import { MenubarModule } from 'primeng/menubar';
import { TieredMenuModule } from 'primeng/tieredmenu';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { ButtonModule } from 'primeng/button';
import { OverlayBadgeModule } from 'primeng/overlaybadge';
import { BadgeModule } from 'primeng/badge';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ToolbarModule } from 'primeng/toolbar';
import { TagModule } from 'primeng/tag';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { InputIconModule } from 'primeng/inputicon';
import { IconFieldModule } from 'primeng/iconfield';
import { DropdownModule } from 'primeng/dropdown';
import { SelectModule } from 'primeng/select';
import { DividerModule } from 'primeng/divider';
import { DialogModule } from 'primeng/dialog';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialog } from 'primeng/confirmdialog';
import { TextareaModule } from 'primeng/textarea';
import { FileUpload } from 'primeng/fileupload';
import { Tag } from 'primeng/tag';
import { RadioButton } from 'primeng/radiobutton';
import { Rating } from 'primeng/rating';
import { InputNumber } from 'primeng/inputnumber';
import { SelectButton } from 'primeng/selectbutton';
import { providePrimeNG } from 'primeng/config';
import { TabsModule } from 'primeng/tabs';
import { SidebarModule } from 'primeng/sidebar';
import Aura from '@primeng/themes/aura';
import { definePreset  } from '@primeng/themes';

/********************** general imports **********************************/
/*************************************************************************/
import {NgbDropdownModule, NgbModule, NgbTooltipModule} from "@ng-bootstrap/ng-bootstrap";
import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar-portable';
import { AppRoutingModule } from "./app-routing.module";
import { ToastInterceptorService } from "./core/services/frontend/toast-interceptor.service";
import { GooeyToastModule } from "./shared/components/gooey-toast/toast/toast.module";

/********************** Components declaration **********************************/
/*************************************************************************/
import { AppComponent } from "./app.component";
import { NavigationItem } from "./core/interfaces/navigation";
import { NavigationComponent } from "./layout/navigation/navigation.component";
import { NavContentComponent } from "./layout/navigation/nav-content/nav-content.component";
import { NavGroupComponent } from "./layout/navigation/nav-content/nav-group/nav-group.component";
import { NavCollapseComponent } from "./layout/navigation/nav-content/nav-collapse/nav-collapse.component";
import { NavItemComponent } from "./layout/navigation/nav-content/nav-item/nav-item.component";
import { HeaderComponent } from "./layout/header/header.component";
import { LoginComponent } from "./features/login/login.component";
import { RessourcesComponent } from "./features/ressources/ressources.component";
import { SpinnerComponent } from "./shared/spinner/spinner.component";
import { PanelComponent } from './shared/panel/panel.component';
import { PanelRowComponent } from './shared/info-panel/panel-row/panel-row.component';
import { PanelFieldComponent } from './shared/info-panel/panel-field/panel-field.component';
import { PanelContainerComponent } from './shared/info-panel/panel-container/panel-container.component';
import { PanelSeparatorComponent } from './shared/info-panel/panel-separator/panel-separator.component';
import { SampleComponent } from './features/ressources/sample/sample.component';
import { HorizontalTabComponent } from './shared/horizontal-tab/horizontal-tab.component';
import { BnaLogoComponent } from './shared/components/bna-logo/bna-logo.component';

import {MultiSelect} from 'primeng/multiselect';
import {ToggleButton} from 'primeng/togglebutton';
import { ApplicationSelectionModule } from "./shared/components/application-selection/application-selection.module";
import { PaginatorModule } from "primeng/paginator";
import { SampleIdComponent } from "./features/ressources/sample-id/sample-id.component";
import {InputSwitch} from 'primeng/inputswitch';
import {ProgressSpinner} from 'primeng/progressspinner';
import {Tooltip}from 'primeng/tooltip';
import {Checkbox}from 'primeng/checkbox';
import { AlertDetailsModalComponent } from './shared/components/alert-details-modal/alert-details-modal.component';
import { SharedFrontModule } from "./shared/shared-front/shared-front.module";
import { CapsLockDirective } from "./shared/directive/caps-lock.directive";
import { authInterceptor } from "./core/interceptors/auth.interceptor";

registerLocaleData(localeFr);

const BlackAura = definePreset(Aura, {
    semantic: {
        primary: {
            50: '{slate.50}',
            100: '{slate.100}',
            200: '{slate.200}',
            300: '{slate.300}',
            400: '{slate.400}',
            500: '{slate.500}',
            600: '{slate.600}',
            700: '{slate.700}',
            800: '{slate.800}',
            900: '{slate.900}',
            950: '{slate.950}'
        },
        colorScheme: {
            light: {
                primary: {
                    color: '{slate.950}',
                    hoverColor: '{slate.800}',
                    activeColor: '{slate.700}'
                }
            }
        }
    }
});

@NgModule({  declarations: [
    AlertDetailsModalComponent,
    AppComponent,
    CapsLockDirective,
    HeaderComponent,
    LoginComponent,
    NavigationComponent,
    NavContentComponent,
    NavGroupComponent,
    NavCollapseComponent,
    NavItemComponent,
    PanelComponent,
    PanelRowComponent,
    PanelFieldComponent,
    PanelContainerComponent,
    PanelSeparatorComponent,
    RessourcesComponent,
    SpinnerComponent,
    SampleComponent,
    SampleIdComponent,
    HorizontalTabComponent,
    BnaLogoComponent,
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    SharedFrontModule,
    ButtonModule,
    BadgeModule,
    BreadcrumbModule,
    CommonModule,
    ConfirmDialog,
    DropdownModule,
    DividerModule,
    DialogModule,
    FormsModule,
    ReactiveFormsModule,
    FileUpload,
    InputNumber,
    InputTextModule,
    InputIconModule,
    IconFieldModule,
    MenubarModule,
    NgbDropdownModule,
    NgbTooltipModule,
    NgbModule,
    NgbDropdownModule,
    OverlayPanelModule,
    OverlayBadgeModule,
    PerfectScrollbarModule,
    RouterModule,
    RadioButton,
    Rating,
    SelectModule,
    SelectButton,
    SharedModule,
    TieredMenuModule,
    ToolbarModule,
    TagModule,
    TableModule, ToastModule,
    TabsModule, SidebarModule, TextareaModule,
    Tag, MultiSelect, ToggleButton,    ApplicationSelectionModule,    PaginatorModule, InputSwitch, ProgressSpinner, Tooltip, Checkbox,
    GooeyToastModule.forRoot({
      position: 'top-right',
      offset: '80px',
      showProgress: true,
    })
  ],  
  providers: [
    provideHttpClient(withInterceptors([authInterceptor])),
    ConfirmationService,
    // Provide ToastInterceptorService as MessageService to intercept all toast messages
    { 
      provide: MessageService, 
      useClass: ToastInterceptorService 
    },
    NavigationItem,
    {provide: LOCALE_ID, useValue: "fr"},
    providePrimeNG({
      theme: {
        preset: BlackAura,
        options: {
          darkModeSelector: false
        }
      }    })
  ],  bootstrap: [AppComponent]
})
export class AppModule {}
