import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PersonnelRoutingModule } from './personnel-routing.module';
import { PersonnelComponent } from './personnel.component';
import { SharedFrontModule } from '../../../shared/shared-front/shared-front.module';


@NgModule({
  declarations: [
    PersonnelComponent
  ],
  imports: [
    CommonModule,
    SharedFrontModule,
    PersonnelRoutingModule
    
  ]
})
export class PersonnelModule { }
