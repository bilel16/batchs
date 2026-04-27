import { NgModule } from "@angular/core";
import { MenuApplicationComponent } from "./menuapplication.component";
import { MenuApplicationRoutingModule } from "./menuapplication-routing.module";
import { CommonModule } from "@angular/common";
import {SharedFrontModule} from '../../../shared/shared-front/shared-front.module';
import {MenuApplicationService} from '../../../core/services/backend/menu-application.service';
import { ApplicationSelectionModule } from "../../../shared/components/application-selection/application-selection.module";


@NgModule({
  imports: [
    CommonModule,
    MenuApplicationRoutingModule,
    SharedFrontModule,
    ApplicationSelectionModule
    ],
  declarations: [MenuApplicationComponent],
  providers: [MenuApplicationService],
})
export class MenuApplicationModule {}
