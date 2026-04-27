import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PATHS } from '../../../core/constants/paths';
import { AuthGuard } from '../../../core/guards/auth.guard.service';
import { InterimListComponent } from './components/interim-list/interim-list.component';
import { InterimDetailComponent } from './components/interim-detail/interim-detail.component';

const routes: Routes = [
  {
    path: PATHS.EMPTY,
    canActivate: [AuthGuard],
    data: {
      title: 'ADMIN_INTERIM',
    },
    component: InterimListComponent,
  },
  {
    path: ':id',
    canActivate: [AuthGuard],
    data: {
      title: 'ADMIN_INTERIM_DETAIL',
    },
    component: InterimDetailComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class InterimRoutingModule {}
