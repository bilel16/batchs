import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { MenuApplicationComponent } from "./menuapplication.component";
import {PATHS} from '../../../core/constants/paths';
import {AuthGuard} from '../../../core/guards/auth.guard.service';

const routes: Routes = [
  {
    path: PATHS.EMPTY,
    canActivate: [AuthGuard],
    data: {
      title: "ADMIN_MENU_APP",
    },
    component: MenuApplicationComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MenuApplicationRoutingModule {}
