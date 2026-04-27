import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-panel-row',
  standalone: false,
  templateUrl: './panel-row.component.html',
  styleUrl: './panel-row.component.scss',
})

export class PanelRowComponent {
  @Input() columns: number = 1;
  @Input() fullWidth: boolean = false;
}
