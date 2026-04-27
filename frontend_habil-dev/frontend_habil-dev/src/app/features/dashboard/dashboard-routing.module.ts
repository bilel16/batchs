import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { PATHS } from "../../core/constants/paths";
import { DashboardComponent } from "./dashboard.component";
import {AuthGuard} from '../../core/guards/auth.guard.service';



const routes: Routes = [
  {
    path: PATHS.EMPTY,
    // canActivate: [AuthGuard],
    // data: {
    //   title: "ADMIN_DASHBOARD",
    // },
    component: DashboardComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DashboardRoutingModule {}
