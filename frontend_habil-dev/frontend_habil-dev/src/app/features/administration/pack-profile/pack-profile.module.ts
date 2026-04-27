import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PackProfileRoutingModule } from './pack-profile-routing.module';
import { PackProfileComponent } from './pack-profile.component';
import { SharedFrontModule } from '../../../shared/shared-front/shared-front.module';


@NgModule({
  declarations: [PackProfileComponent],
  imports: [
    CommonModule,
    SharedFrontModule,
    PackProfileRoutingModule
  ]
})
export class PackProfileModule { }
