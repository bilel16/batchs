import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import {PATHS} from '../../core/constants/paths';


const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: PATHS.APPLICATION,
        loadChildren: () =>
          import('./application/application.module').then(
            (module) => module.ApplicationModule
          ),
      },
      {
        path: PATHS.MENU_APPLICATION,
        loadChildren: () =>
          import('./menuapplication/menuapplication.module').then(
            (module) => module.MenuApplicationModule
          ),
      },
      {
        path: PATHS.PROFILE,
        loadChildren: () =>
          import("./profile/profile.module").then((module) =>  module.ProfileModule),
      },
      {
        path: PATHS.PROFIL_MENU_APPLICATION,
        loadChildren: () =>
          import('./profilmenuapplication/profilmenuapplication.module').then(
            (module) => module.ProfilMenuApplicationModule
          ),
      },
      {
        path: PATHS.UTILISATEUR_PROFIL,
        loadChildren: () =>
          import('./utilisateurprofil/utilisateurprofil.module').then(
            m => m.UtilisateurProfilModule)
      },
      {
        path: PATHS.PACK,
        loadChildren: () =>
          import("./pack/pack.module").then((module) => module.PackModule),
      },
      {
        path: PATHS.PACK_PROFILE,
        loadChildren: () =>
          import("./pack-profile/pack-profile.module").then((module) => module.PackProfileModule),
      },      {
        path: PATHS.PERSONNEL,
        loadChildren: () =>
          import("./personnel/personnel.module").then((module) => module.PersonnelModule),
            runGuardsAndResolvers: 'always'

      },
      {
        path: `${PATHS.PERSONNEL}/${PATHS.HR_PERSONAL}`,
        loadChildren: () =>
          import("./personnel/hr-personnel/hr-personnel.module").then((module) => module.HrPersonnelModule),
          runGuardsAndResolvers: 'always'
},      {
        path: PATHS.STR_SEG,
        loadChildren: () =>
          import("./structuresegment/structuresegment.module").then((module) => module.StructuresegmentModule),
      },
      {
        path: PATHS.INTERIM,
        loadChildren: () =>
          import("./interim/interim.module").then((module) => module.InterimModule),
      },
    ],
  },
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdministrationRoutingModule {}
