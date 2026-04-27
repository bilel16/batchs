import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ApplicationSelectionRoutingModule } from './application-selection-routing.module';
import { ApplicationSelectionComponent } from './application-selection.component';
import { FormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';


@NgModule({
  declarations: [ApplicationSelectionComponent],
  imports: [
    CommonModule,
    FormsModule,
    DropdownModule,
    ApplicationSelectionRoutingModule
  ],
  exports: [
    ApplicationSelectionComponent
  ]
})
export class ApplicationSelectionModule { }
