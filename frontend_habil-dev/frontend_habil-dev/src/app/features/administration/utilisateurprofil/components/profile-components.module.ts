import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DragDropModule } from '@angular/cdk/drag-drop';

// PrimeNG Modules
import { ButtonModule } from 'primeng/button';
import { ToolbarModule } from 'primeng/toolbar';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { TooltipModule } from 'primeng/tooltip';
import { SelectButtonModule } from 'primeng/selectbutton';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { DialogModule } from 'primeng/dialog';
import { CheckboxModule } from 'primeng/checkbox';
import { CalendarModule } from 'primeng/calendar';
import { ProgressBarModule } from 'primeng/progressbar';
import { RadioButtonModule } from 'primeng/radiobutton';
import { SharedFrontModule } from '../../../../shared/shared-front/shared-front.module';
import { ConfirmationStepComponent } from './confirmation-step/confirmation-step.component';
import { ProfileAssignmentStepComponent } from './profile-assignment-step/profile-assignment-step.component';
import { ProfileBadgeComponent } from './profile-badge/profile-badge.component';
import { ProfileFiltersComponent } from './profile-filters/profile-filters.component';
import { ProfileToolbarComponent } from './profile-toolbar/profile-toolbar.component';
import { UserSelectionStepComponent } from './user-selection-step/user-selection-step.component';
import { ApplicationSelectionModule } from '../../../../shared/components/application-selection/application-selection.module';

// Shared Module

// Components
import { PackCardComponent } from './pack-card/pack-card.component';
import { PackSelectorComponent } from './pack-selector/pack-selector.component';
import { PackCartComponent } from './pack-cart/pack-cart.component';
import { ProfileCloneComponent } from './profile-clone/profile-clone.component';

@NgModule({
  declarations: [
    ProfileToolbarComponent,
    ProfileFiltersComponent,
    ProfileBadgeComponent,
    UserSelectionStepComponent,
    ProfileAssignmentStepComponent,
    ConfirmationStepComponent,
    PackCardComponent,
    PackSelectorComponent,
    PackCartComponent,
    ProfileCloneComponent
  ],  imports: [
    CommonModule,
    FormsModule,
    DragDropModule,
    ButtonModule,
    ToolbarModule,
    DropdownModule,
    InputTextModule,
    ToastModule,
    SharedFrontModule,
    ApplicationSelectionModule,
    TooltipModule,
    SelectButtonModule,
    ProgressSpinnerModule,
    DialogModule,
    CheckboxModule,
    CalendarModule,
    ProgressBarModule,
    RadioButtonModule
  ],exports: [
    ProfileToolbarComponent,
    ProfileFiltersComponent,
    ProfileBadgeComponent,
    UserSelectionStepComponent,
    ProfileAssignmentStepComponent,
    ConfirmationStepComponent,
    PackCardComponent,
    PackSelectorComponent,
    PackCartComponent,
    ProfileCloneComponent
  ]
})
export class ProfileComponentsModule { }
