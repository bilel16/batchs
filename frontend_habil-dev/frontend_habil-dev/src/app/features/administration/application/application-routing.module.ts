import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { ApplicationComponent } from "./application.component";
import { AuthGuard } from "../../../core/guards/auth.guard.service";

const routes: Routes = [
  {
    path: "",
    canActivate: [AuthGuard],
    data: {
      title: "ADMIN_APPLICATION",
    },
    component: ApplicationComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ApplicationRoutingModule {}
