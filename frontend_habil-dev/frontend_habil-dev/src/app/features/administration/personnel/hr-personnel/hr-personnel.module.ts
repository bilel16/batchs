/**
 * @fileoverview HR Personnel Module
 * 
 * Feature module for HR Personnel management with filtering, pagination, and search.
 * 
 * @author BNA HABIL Development Team
 * @version 1.0.0
 * @since 2026-01-29
 */

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

// PrimeNG Modules
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { TooltipModule } from 'primeng/tooltip';
import { RippleModule } from 'primeng/ripple';

// Routing
import { HrPersonnelRoutingModule } from './hr-personnel-routing.module';

// Components
import { HrPersonnelComponent } from './hr-personnel.component';

@NgModule({
  declarations: [
    HrPersonnelComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HrPersonnelRoutingModule,
    // PrimeNG
    TableModule,
    ButtonModule,
    InputTextModule,
    DropdownModule,
    TooltipModule,
    RippleModule
  ]
})
export class HrPersonnelModule { }
