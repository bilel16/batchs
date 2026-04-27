import { NgModule } from "@angular/core";
import { UtilisateurProfilRoutingModule } from "./utilisateurprofil-routing.module";

import { AutoCompleteModule } from "primeng/autocomplete";
import { TagModule } from "primeng/tag";
import { DragDropModule } from '@angular/cdk/drag-drop';
import { TooltipModule } from 'primeng/tooltip';
import { StepperModule } from 'primeng/stepper';
import { ProfileComponentsModule } from "./components/profile-components.module";
import { SharedFrontModule } from "../../../shared/shared-front/shared-front.module";
import { UtilisateurProfilComponent } from "./utilisateurprofil.component";
import { ProfilService } from "../../../core/services/backend/profil.service";
import { UtilisateurProfilService } from "../../../core/services/backend/utilisateur-profile.service";
import { AppModule } from "../../../app.module";


@NgModule({
  imports: [
    SharedFrontModule,
    UtilisateurProfilRoutingModule,
    AutoCompleteModule,
    TagModule,
    DragDropModule,
    TooltipModule,
    StepperModule,
    ProfileComponentsModule,
    
  ],
  declarations: [UtilisateurProfilComponent],
  providers: [
    ProfilService,
    UtilisateurProfilService
  ],
})
export class UtilisateurProfilModule {}

