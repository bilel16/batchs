import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InterimRoutingModule } from './interim-routing.module';
import { SharedFrontModule } from '../../../shared/shared-front/shared-front.module';
import { TooltipModule } from 'primeng/tooltip';

import { InterimListComponent } from './components/interim-list/interim-list.component';
import { InterimDetailComponent } from './components/interim-detail/interim-detail.component';
import { InterimStateBadgeComponent } from './components/interim-state-badge/interim-state-badge.component';

@NgModule({
  declarations: [
    InterimListComponent,
    InterimDetailComponent,
    InterimStateBadgeComponent,
  ],
  imports: [
    CommonModule,
    SharedFrontModule,
    InterimRoutingModule,
    TooltipModule,
  ],
})
export class InterimModule {}
