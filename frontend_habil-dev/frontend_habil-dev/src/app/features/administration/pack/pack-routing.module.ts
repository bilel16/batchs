import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PATHS} from '../../../core/constants/paths';
import {AuthGuard} from '../../../core/guards/auth.guard.service';
import {PackComponent} from './pack.component';

const routes: Routes = [
  {
    path: PATHS.EMPTY,
    canActivate: [AuthGuard],
    data: {
      title: "ADMIN_PACK",
    },
    component: PackComponent,
  },
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PackRoutingModule { }
