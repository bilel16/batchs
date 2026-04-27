import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

// ✅ PrimeNG modules
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { CalendarModule } from 'primeng/calendar';
import { SliderModule } from 'primeng/slider';
import { MultiSelectModule } from 'primeng/multiselect';
import { SelectModule } from 'primeng/select';
import { ContextMenuModule } from 'primeng/contextmenu';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { ProgressBarModule } from 'primeng/progressbar';
import { InputTextModule } from 'primeng/inputtext';
import { FileUploadModule } from 'primeng/fileupload';
import { ToolbarModule } from 'primeng/toolbar';
import { RatingModule } from 'primeng/rating';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputNumberModule } from 'primeng/inputnumber';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { SidebarModule } from 'primeng/sidebar';
import { SharedModule } from 'primeng/api';
import { TextareaModule } from 'primeng/textarea';
import { PaginatorModule } from 'primeng/paginator';
import { AccordionModule } from 'primeng/accordion';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { InputSwitchModule } from 'primeng/inputswitch';
import { TagModule } from 'primeng/tag';
import { SkeletonModule } from 'primeng/skeleton';
import { FloatLabelModule } from 'primeng/floatlabel';
import { CheckboxModule } from 'primeng/checkbox';
import { AutoCompleteModule } from 'primeng/autocomplete';
import {
  NgbAccordionModule,
  NgbCollapseModule,
} from '@ng-bootstrap/ng-bootstrap';
import { ApplicationSelectionModule } from '../components/application-selection/application-selection.module';
import { SharedFrontService } from '../../core/services/frontend/shared-front.service';
import { TabToggleComponent } from '../components/tab-toggle/tab-toggle.component';
import { BreadcrumbComponent } from '../breadcrumb/breadcrumb.component';
import { AnimatedDrawerComponent } from '../components/animated-drawer/animated-drawer.component';
import { TableComponent } from '../table/table.component';
import { TablePaginatedComponent } from '../table-pagination/table-pagination.component';
import { ToolbarComponent } from '../toolbar/toolbar.component';
import { DialogComponent } from '../dialog/dialog.component';
import { BooleanToFrenchPipe } from '../pipes/boolean-to-french.pipe';
import { BreadcrumbModule } from 'primeng/breadcrumb';

// Services

@NgModule({
  declarations: [
    TabToggleComponent, 
    BreadcrumbComponent, 
    AnimatedDrawerComponent,
    TableComponent,
    TablePaginatedComponent,
    ToolbarComponent,
    DialogComponent,
    BooleanToFrenchPipe
  ],  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TableModule,
    ToastModule,
    CalendarModule,
    SliderModule,
    MultiSelectModule,
    SelectModule,
    ContextMenuModule,
    DialogModule,
    ButtonModule,
    DropdownModule,
    ProgressBarModule,
    InputTextModule,
    FileUploadModule,
    ToolbarModule,
    RatingModule,
    RadioButtonModule,
    InputNumberModule,
    ConfirmDialogModule,
    ToggleButtonModule,
    SidebarModule,
    SharedModule,
    TextareaModule,
    PaginatorModule,
    AccordionModule,
    ProgressSpinnerModule,
    InputSwitchModule,
    TagModule,
    CheckboxModule,
    AutoCompleteModule,
    NgbAccordionModule,
    NgbCollapseModule,
    ApplicationSelectionModule,
    BreadcrumbModule,
    SkeletonModule,
    InputTextModule,
    FloatLabelModule
  ],  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TableModule,
    ToastModule,
    CalendarModule,
    SliderModule,
    MultiSelectModule,
    SelectModule,
    ContextMenuModule,
    DialogModule,
    ButtonModule,
    DropdownModule,
    ProgressBarModule,
    InputTextModule,
    FileUploadModule,
    ToolbarModule,
    RatingModule,
    RadioButtonModule,
    InputNumberModule,
    ConfirmDialogModule,
    ToggleButtonModule,
    TabToggleComponent,
    SidebarModule,
    SharedModule,
    TextareaModule,
    PaginatorModule,
    AccordionModule,
    ProgressSpinnerModule,
    InputSwitchModule,
    TagModule,
    CheckboxModule,
    AutoCompleteModule,
    SkeletonModule,
    NgbAccordionModule,
    NgbCollapseModule,
    BreadcrumbComponent,
    AnimatedDrawerComponent,
    TableComponent,
    TablePaginatedComponent,
    ToolbarComponent,
    DialogComponent,
    BooleanToFrenchPipe,
  ],
  providers: [SharedFrontService],
})
export class SharedFrontModule {}
