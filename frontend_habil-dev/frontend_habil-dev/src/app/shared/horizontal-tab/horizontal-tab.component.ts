import { ChangeDetectorRef, Component, ContentChildren, EventEmitter, Input, Output, QueryList, TemplateRef } from '@angular/core';

export interface TabItem {
    id: string;
    label: string;
    icon?: string;
}
@Component({
  selector: 'app-horizontal-tab',
  standalone: false,
  templateUrl: './horizontal-tab.component.html',
  styleUrl: './horizontal-tab.component.scss'
})
export class HorizontalTabComponent {
    @Input() tabs: TabItem[] = [];
    @Input() activeTabIndex: number = 0;
    
    @Output() activeTabIndexChange = new EventEmitter<number>();
    @Output() tabChanged = new EventEmitter<{ tab: TabItem; index: number }>();

    // Get all tab content templates
    @ContentChildren('tabContent') tabContents!: QueryList<TemplateRef<any>>;

    tabContentArray: TemplateRef<any>[] = [];

    ngAfterContentInit() {
        this.tabContentArray = this.tabContents.toArray();
        console.log('Tab contents loaded:', this.tabContentArray.length);
    }

    selectTab(index: number): void {
        console.log('Selecting tab:', index);
        this.activeTabIndex = index;
        this.activeTabIndexChange.emit(index);
        
        if (this.tabs[index]) {
            this.tabChanged.emit({
                tab: this.tabs[index],
                index: index
            });
        }
    }

    // Track by function to force re-render
    trackByIndex(index: number): number {
        return index;
    }
  }