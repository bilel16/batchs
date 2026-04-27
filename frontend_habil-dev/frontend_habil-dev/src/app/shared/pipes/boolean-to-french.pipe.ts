import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'booleanToFrench',
  standalone: false
})
export class BooleanToFrenchPipe implements PipeTransform {
  transform(value: boolean): string {
    return value ? 'Oui' : 'Non';
  }
}