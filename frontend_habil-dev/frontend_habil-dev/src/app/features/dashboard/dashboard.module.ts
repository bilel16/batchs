import { NgModule } from "@angular/core";
import { DashboardRoutingModule } from "./dashboard-routing.module";
import { DashboardComponent } from "./dashboard.component";
import { StatisticsService } from "../../core/services/backend/statistics.service";
import { SharedFrontModule } from "../../shared/shared-front/shared-front.module";
import { TunisiaMapComponent } from "../tunisia-map/tunisia-map.component";

@NgModule({
  imports: [
    SharedFrontModule,
    DashboardRoutingModule
    ],
  declarations: [DashboardComponent,TunisiaMapComponent],
  providers: [
    StatisticsService
  ],
})
export class DashboardModule {}
