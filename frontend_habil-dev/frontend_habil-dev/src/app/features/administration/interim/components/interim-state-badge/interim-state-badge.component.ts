import { Component, Input } from '@angular/core';
import { EtatInterim, ETAT_INTERIM_META } from '../../models/interim.model';

@Component({
  selector: 'app-interim-state-badge',
  standalone: false,
  template: `
    <span
      class="state-badge"
      [style.background]="meta.bgColor"
      [style.color]="meta.textColor"
      [style.border-color]="meta.borderColor">
      <i [class]="meta.icon"></i>
      {{ meta.label }}
    </span>
  `,
  styles: [`
    .state-badge {
      display: inline-flex;
      align-items: center;
      gap: 0.4rem;
      padding: 0.35rem 0.85rem;
      border-radius: 20px;
      font-size: 0.8125rem;
      font-weight: 600;
      border: 1.5px solid;
      white-space: nowrap;
      letter-spacing: 0.02em;

      i { font-size: 0.85rem; }
    }
  `],
})
export class InterimStateBadgeComponent {
  @Input() etat: EtatInterim = EtatInterim.EN_ATTENTE;

  get meta() {
    return ETAT_INTERIM_META[this.etat] ?? ETAT_INTERIM_META[EtatInterim.EN_ATTENTE];
  }
}
