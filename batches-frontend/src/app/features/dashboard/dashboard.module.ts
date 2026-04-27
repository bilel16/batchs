import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './dashboard.component';
import { HomeComponent } from './pages/home/home.component';
import { BatchLauncherComponent } from './pages/batch-launcher/batch-launcher.component';
import { ConsultationComponent } from './pages/consultation/consultation.component';
import { AuditComponent } from './pages/audit/audit.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: HomeComponent },
      { path: 'batch-launcher', component: BatchLauncherComponent },
      { path: 'consultation', component: ConsultationComponent },
      { path: 'audit', component: AuditComponent },
    ],
  },
];

@NgModule({
  declarations: [
    DashboardComponent,
    HomeComponent,
    BatchLauncherComponent,
    ConsultationComponent,
    AuditComponent,
  ],
  imports: [CommonModule, FormsModule, RouterModule.forChild(routes)],
})
export class DashboardModule {}
