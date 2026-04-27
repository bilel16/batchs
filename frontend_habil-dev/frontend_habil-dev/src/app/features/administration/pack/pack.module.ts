import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PackRoutingModule } from './pack-routing.module';
import { PackComponent } from './pack.component';
import { SharedFrontModule } from '../../../shared/shared-front/shared-front.module';
import { TabsModule } from 'primeng/tabs';
import { BadgeModule } from 'primeng/badge';

@NgModule({
  declarations: [
    PackComponent
  ],
  imports: [
    CommonModule,
    SharedFrontModule,
    PackRoutingModule,
    TabsModule,
    BadgeModule
  ]
})
export class PackModule { }
