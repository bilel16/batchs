import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './features/login/login.component';
import { RessourcesComponent } from './features/ressources/ressources.component';
import { SampleComponent } from './features/ressources/sample/sample.component';
import { PATHS } from './core/constants/paths';
import { AuthGuard } from './core/guards/auth.guard.service';
import { LoginGuard } from './core/guards/login.guard.service';
import { ToastTestModule } from './features/ressources/toast-test/toast-test.module';

const routes: Routes = [
  {
    path: PATHS.EMPTY,
    redirectTo: '/login',
    pathMatch: 'full',
  },  {
    path: PATHS.LOGIN,
    canActivate: [LoginGuard],
    component: LoginComponent,
  },  
  {
    path: PATHS.DASHBOARD,
    canActivate: [AuthGuard],
    loadChildren: () =>
      import('./features/dashboard/dashboard.module').then(
        (m) => m.DashboardModule
      ),
  },
  {
    path: 'administration',
    canActivate: [AuthGuard],
    loadChildren: () =>
      import('./features/administration/administration.module').then(
        (m) => m.AdministrationModule
      ),
  },
  {
    path: 'ressources',
    
    canActivate: [AuthGuard],
    data: {
      title: 'ADMIN_USER_PROF',
    },
    component: RessourcesComponent,
    children: [      {
        path: 'sample',
        canActivate: [AuthGuard],
        data: {
          title: 'ADMIN_USER_PROF',
        },
        component: SampleComponent,
      },
      {
        path: 'toast-test',
        canActivate: [AuthGuard],
        loadChildren: () =>
          import('./features/ressources/toast-test/toast-test.module').then(
            (m) =>ToastTestModule
          ),
      },

    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [],
})
export class AppRoutingModule {}
