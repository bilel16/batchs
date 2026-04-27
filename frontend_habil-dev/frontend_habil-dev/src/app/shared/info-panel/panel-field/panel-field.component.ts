import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-panel-field',
  standalone: false,
  templateUrl: './panel-field.component.html',
  styleUrl: './panel-field.component.scss',
})
export class PanelFieldComponent {
  @Input() label: string = '';
  @Input() value: any;
  @Input() type?:
    | 'text'
    | 'link'
    | 'tag'
    | 'html'
    | 'list'
    | 'list-long'
    | 'object'
    | 'object-long';
  @Input() href?: string;
  @Input() severity?: string;
  @Input() fullWidth: boolean = false;

  isFullWidth: boolean = false;

  ngOnInit() {
    this.isFullWidth = this.fullWidth || this.isFullWidthType(this.type);
  }

  isFullWidthType(type?: string): boolean {
    return ['list-long', 'object-long'].includes(type || '');
  }

  objectToString(obj: any): string {
    return JSON.stringify(obj, null, 2);
  }
}
