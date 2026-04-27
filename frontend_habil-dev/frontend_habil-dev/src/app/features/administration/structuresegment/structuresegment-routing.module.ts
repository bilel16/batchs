import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PATHS} from '../../../core/constants/paths';
import {AuthGuard} from '../../../core/guards/auth.guard.service';
import {StructuresegmentComponent} from './structuresegment.component';

const routes: Routes = [
  {
    path: PATHS.EMPTY,
    canActivate: [AuthGuard],
    data: {
      title: "ADMIN_SEG_STR",
    },
    component: StructuresegmentComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StructuresegmentRoutingModule { }
