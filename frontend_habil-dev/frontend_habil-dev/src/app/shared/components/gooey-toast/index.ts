/**
 * @fileoverview Public API barrel for gooey-toast
 *
 * Usage:
 *   import { GooeyToastModule, GooeyToastService } from '@shared/components/gooey-toast';
 */

export { GooeyToastModule } from './gooey-toast.module';
export { GooeyToastService } from './gooey-toast.service';
export { GooeyToasterComponent } from './gooey-toaster.component';
export type {
  GooeyToastType,
  GooeyToastPosition,
  GooeyToastAction,
  GooeyToastOptions,
  GooeyToastPromiseOptions,
  GooeyToasterConfig,
  GooeyToastState,
  GooeyToastClassNames,
} from './gooey-toast.types';
