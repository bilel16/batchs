import { Directive, HostListener, output } from '@angular/core';

@Directive({
  selector: '[appCapsLock]',
  standalone: false
})
export class CapsLockDirective {
  capsLockOn = output<boolean>();

  @HostListener('window:keydown', ['$event'])
  @HostListener('window:keyup', ['$event'])
  onKeyEvent(event: KeyboardEvent): void {
    const capsLockOn = event.getModifierState?.('CapsLock') ?? false;
    this.capsLockOn.emit(capsLockOn);
  }
}