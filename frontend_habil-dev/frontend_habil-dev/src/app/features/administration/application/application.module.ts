import { NgModule } from "@angular/core";
import { ApplicationRoutingModule } from "./application-routing.module";
import { CommonModule } from "@angular/common";
import { ApplicationService } from "../../../core/services/backend/application.service";
import { SharedFrontModule } from "../../../shared/shared-front/shared-front.module";
import {ApplicationComponent} from './application.component';

@NgModule({
  imports: [CommonModule, SharedFrontModule, ApplicationRoutingModule],
  declarations: [ApplicationComponent],
  providers: [ApplicationService],
})
export class ApplicationModule {}
