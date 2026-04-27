import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-tab-toggle',
  standalone: false,
  templateUrl: './tab-toggle.component.html',
  styleUrl: './tab-toggle.component.scss',
})
export class TabToggleComponent {
  // Tabs passed from parent (array of labels or objects)
  @Input() tabs: (string | { label: string; value: string })[] = [];

  // Default selected tab index
  @Input() selectedIndex: number = 0;

  // Emit the selected tab value
  @Output() tabChange = new EventEmitter<string>();

  // Internal function to handle selection
  selectTab(index: number) {
    this.selectedIndex = index;

    const selected =
      typeof this.tabs[index] === 'string'
        ? (this.tabs[index] as string)
        : (this.tabs[index] as any).value;

    this.tabChange.emit(selected);
  }
  getLabel(tab: string | { label: string; value: string }) {
    return typeof tab === 'string' ? tab : tab.label;
  }
}
