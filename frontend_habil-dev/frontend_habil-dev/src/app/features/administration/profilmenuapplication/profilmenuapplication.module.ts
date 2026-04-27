import { NgModule } from "@angular/core";
import { ProfilMenuApplicationComponent } from "./profilmenuapplication.component";
import { ProfilMenuApplicationRoutingModule } from "./profilmenuapplication-routing.module";
import {SharedFrontModule} from '../../../shared/shared-front/shared-front.module';
import {MenuApplicationService} from '../../../core/services/backend/menu-application.service';
import {ProfilService} from '../../../core/services/backend/profil.service';
import { ApplicationSelectionModule } from "../../../shared/components/application-selection/application-selection.module";
import { ProfilMenuApplicationService } from "./services";


@NgModule({
  imports: [
    SharedFrontModule,
    ProfilMenuApplicationRoutingModule,
    ApplicationSelectionModule
  ],
  declarations: [
    ProfilMenuApplicationComponent
  ],
  providers: [
    MenuApplicationService,
    ProfilService,
    ProfilMenuApplicationService
  ],
})
export class ProfilMenuApplicationModule {}
