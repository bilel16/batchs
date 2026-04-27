import { Component, Input } from '@angular/core';

export interface PanelConfig {
  title: string;
  headerBackgroundColor: string;
  headerTextColor: string;
}

@Component({
  selector: 'app-panel',
  standalone: false,
  templateUrl: './panel.component.html',
  styleUrl: './panel.component.scss',
})
export class PanelComponent {
  @Input() config: PanelConfig = {
    title: 'Default Title',
    headerBackgroundColor: '#115952',
    headerTextColor: 'white',
  };
}
