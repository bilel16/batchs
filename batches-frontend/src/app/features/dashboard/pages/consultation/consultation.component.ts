import { Component, inject, signal } from '@angular/core';
import { BatchService } from '../../../../core/services/backend/batch.service';
import { ConsultationResult } from '../../../../core/models/batch.model';

@Component({
  selector: 'app-consultation',
  templateUrl: './consultation.component.html',
  styleUrl: './consultation.component.scss',
  standalone: false,
})
export class ConsultationComponent {
  private readonly batchService = inject(BatchService);

  filters = {
    dateStart: '',
    dateEnd: '',
    instrument: '',
    sens: '',
    valeur: '',
    agence: '',
  };

  results  = signal<ConsultationResult[]>([]);
  loading  = signal(false);
  searched = signal(false);
  error    = signal<string | null>(null);

  search(): void {
    this.loading.set(true);
    this.error.set(null);
    this.searched.set(false);

    const activeFilters = Object.fromEntries(
      Object.entries(this.filters).filter(([, v]) => v !== '')
    );

    this.batchService.search(activeFilters).subscribe({
      next: (data) => {
        this.results.set(data);
        this.loading.set(false);
        this.searched.set(true);
      },
      error: () => {
        this.error.set('Erreur lors de la recherche.');
        this.loading.set(false);
        this.searched.set(true);
      },
    });
  }

  reset(): void {
    this.filters = { dateStart: '', dateEnd: '', instrument: '', sens: '', valeur: '', agence: '' };
    this.results.set([]);
    this.searched.set(false);
    this.error.set(null);
  }

  totalMontant(): number {
    return this.results().reduce((sum, r) => sum + r.montantTotal, 0);
  }

  totalNombre(): number {
    return this.results().reduce((sum, r) => sum + r.nombreTotal, 0);
  }

  formatMontant(m: number): string {
    return m.toLocaleString('fr-FR', { minimumFractionDigits: 2 }) + ' DT';
  }
}
