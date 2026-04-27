import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-profile-toolbar',
  templateUrl: './profile-toolbar.component.html',
  styleUrls: ['./profile-toolbar.component.scss'],
  standalone: false
})
export class ProfileToolbarComponent {
  @Input() showStatistics: boolean = false;
  @Input() assignmentStatistics: any = null;
  @Input() selectedAppCode: string = '';
  @Input() hasSelectedUser: boolean = false;

  @Output() statisticsToggle = new EventEmitter<boolean>();
  @Output() addNew = new EventEmitter<void>();
  @Output() cloneProfiles = new EventEmitter<void>();
  @Output() appSelectionChange = new EventEmitter<any>();
  @Output() appsLoaded = new EventEmitter<any>();

  onStatisticsToggle(): void {
    this.showStatistics = !this.showStatistics;
    this.statisticsToggle.emit(this.showStatistics);
  }

  onAddNew(): void {
    this.addNew.emit();
  }

  onCloneProfiles(): void {
    this.cloneProfiles.emit();
  }

  onAppSelectionChange(event: any): void {
    this.appSelectionChange.emit(event);
  }

  onAppsLoaded(event: any): void {
    this.appsLoaded.emit(event);
  }
}
