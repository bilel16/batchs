import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PATHS} from '../../../core/constants/paths';
import {AuthGuard} from '../../../core/guards/auth.guard.service';
import {PersonnelComponent} from './personnel.component';
import { HrPersonnelComponent } from './hr-personnel/hr-personnel.component';

const routes: Routes = [
  {
    path: PATHS.EMPTY,
    canActivate: [AuthGuard],
    data: {
      title: "ADMIN_PERSONNEL_GESTION",
    },
    component: PersonnelComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PersonnelRoutingModule { }
