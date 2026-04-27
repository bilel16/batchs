import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StructuresegmentRoutingModule } from './structuresegment-routing.module';
import { StructuresegmentComponent } from './structuresegment.component';
import { SharedFrontModule } from '../../../shared/shared-front/shared-front.module';


@NgModule({
  declarations: [
    StructuresegmentComponent
  ],
  imports: [
    CommonModule,
    StructuresegmentRoutingModule,
    SharedFrontModule
  ]
})
export class StructuresegmentModule { }
