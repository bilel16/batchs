import {
  Component,
  ContentChild,
  EventEmitter,
  Input,
  Output,
  TemplateRef,
} from '@angular/core';

@Component({
  selector: 'app-dialog',
  standalone: false,
  templateUrl: './dialog.component.html',
  styleUrl: './dialog.component.scss',
})
export class DialogComponent {
  @Input() visible: boolean = false;
  @Input() width: string = '25rem';
  @Input() modal: boolean = true;
  @Input() maximizable: boolean = true;
  @Input() breakpoints: any = { '1199px': '75vw', '575px': '90vw' };
  @Input() headerTitle: string = '';
  @Input() showDefaultFooter: boolean = true;
  @Input() saveLabel: string = 'Save';
  @Input() cancelLabel: string = 'Cancel';
  @Input() saveDisabled: boolean = false;
  @Input() cancelSeverity: string = 'secondary';
  @Input() style: any = { width: '25rem', 'min-height': '20rem' }; // Accept style object

  @Output() visibleChange = new EventEmitter<boolean>();

  @Output() onCancel = new EventEmitter<void>();
  @Output() onHide = new EventEmitter<void>();

  // Template refs for custom content
  @ContentChild('customHeader') customHeaderTemplate!: TemplateRef<any>;
  @ContentChild('customFooter') customFooterTemplate!: TemplateRef<any>;

  handleCancel() {
    this.onCancel.emit();
    this.closeDialog();
  }

  closeDialog() {
    this.visible = false;
    this.visibleChange.emit(false);
  }

  handleHide() {
    this.onHide.emit();
    this.visibleChange.emit(false);
  }
}
