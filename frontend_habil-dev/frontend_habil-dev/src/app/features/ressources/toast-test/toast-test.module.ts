import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { ToastTestComponent } from './toast-test.component';

const routes: Routes = [
  { path: '', component: ToastTestComponent },
];

@NgModule({
  declarations: [ToastTestComponent],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(routes),
  ],
})
export class ToastTestModule {}
