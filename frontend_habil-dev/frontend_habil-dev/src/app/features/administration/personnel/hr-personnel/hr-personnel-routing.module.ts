import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HrPersonnelComponent } from './hr-personnel.component';
import { AuthGuard } from '../../../../core/guards/auth.guard.service';
import { PATHS } from '../../../../core/constants/paths';

const routes: Routes = [
  {
    path: PATHS.EMPTY,
    canActivate: [AuthGuard],
    data: {
      title: 'ADMIN_PERSONNEL_HR',
    },
    component: HrPersonnelComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HrPersonnelRoutingModule {}
