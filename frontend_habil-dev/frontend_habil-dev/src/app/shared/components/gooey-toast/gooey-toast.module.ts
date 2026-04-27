/**
 * @fileoverview GooeyToastModule
 *
 * NgModule that declares and exports the GooeyToasterComponent.
 * Import this module in your AppModule (or any shared module) and
 * place <gooey-toaster> once in your root layout template.
 *
 * The GooeyToastService is providedIn: 'root', so it's available
 * everywhere without needing to add it to providers.
 */

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GooeyToasterComponent } from './gooey-toaster.component';

@NgModule({
  declarations: [GooeyToasterComponent],
  imports: [CommonModule],
  exports: [GooeyToasterComponent],
})
export class GooeyToastModule {}
