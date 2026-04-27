import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { AdministrationRoutingModule } from "./administration-routing.module";
import { RouterModule } from "@angular/router";
import { ApplicationSelectionModule } from "../../shared/components/application-selection/application-selection.module";
import { SharedFrontModule } from "../../shared/shared-front/shared-front.module";
import { ProfileComponentsModule } from "./utilisateurprofil/components/profile-components.module";


@NgModule({
  imports: [
    CommonModule,
    AdministrationRoutingModule,
    RouterModule,
    ApplicationSelectionModule,
    SharedFrontModule,
    ProfileComponentsModule,
  ],
  declarations: [],
  providers: [],
})
export class AdministrationModule {}
