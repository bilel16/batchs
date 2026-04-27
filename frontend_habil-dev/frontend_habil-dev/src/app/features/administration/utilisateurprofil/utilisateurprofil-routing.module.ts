import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { UtilisateurProfilComponent } from "./utilisateurprofil.component";
import { PATHS } from "../../../core/constant/paths";
import { AuthGuard } from "../../../core/guards/auth.guard.service";


const routes: Routes = [
  {
    path: PATHS.EMPTY,
    canActivate: [AuthGuard],
    data: {
      title: "ADMIN_USER_PROF",
    },
    component: UtilisateurProfilComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UtilisateurProfilRoutingModule {}
