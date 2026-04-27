import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { ProfilMenuApplicationComponent } from "./profilmenuapplication.component";
import {PATHS} from '../../../core/constants/paths';
import {AuthGuard} from '../../../core/guards/auth.guard.service';


const routes: Routes = [
  {
    path: PATHS.EMPTY,
    canActivate: [AuthGuard],
    data: {
      title: "ADMIN_PROF_MENU_APP",
    },
    component: ProfilMenuApplicationComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProfilMenuApplicationRoutingModule {}
