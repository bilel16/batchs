import { NgModule } from "@angular/core";
import { ProfileRoutingModule } from "./profile-routing.module";
import { ProfileComponent } from "./profile.component";
import {SharedFrontModule} from '../../../shared/shared-front/shared-front.module';
import {ProfilService} from '../../../core/services/backend/profil.service';
import { ApplicationSelectionModule } from "../../../shared/components/application-selection/application-selection.module";
import {TabsModule} from 'primeng/tabs';
import {BadgeModule} from 'primeng/badge';

@NgModule({
  imports: [
    SharedFrontModule,
    ProfileRoutingModule,
    ApplicationSelectionModule,
    TabsModule,
    BadgeModule
  ],
  declarations: [ProfileComponent],
  providers: [
    ProfilService
  ],
})
export class ProfileModule {}
