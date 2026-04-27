import { Component, OnInit, inject, signal } from '@angular/core';
import { BatchService } from '../../../../core/services/backend/batch.service';
import { DashboardStats, BatchExecutionDto } from '../../../../core/models/batch.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  standalone: false,
})
export class HomeComponent implements OnInit {
  private readonly batchService = inject(BatchService);

  stats = signal<DashboardStats | null>(null);
  loading = signal(true);
  error = signal<string | null>(null);

  ngOnInit(): void {
    this.loadStats();
  }

  loadStats(): void {
    this.loading.set(true);
    this.error.set(null);
    this.batchService.getDashboardStats().subscribe({
      next: (data) => {
        this.stats.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Impossible de charger les statistiques.');
        this.loading.set(false);
      },
    });
  }

  statusLabel(status: string): string {
    const map: Record<string, string> = {
      COMPLETED: 'Terminé',
      COMPLETED_WITH_ERRORS: 'Terminé avec erreurs',
      FAILED: 'Échoué',
      STOPPED: 'Arrêté',
      RUNNING: 'En cours',
      STARTED: 'Démarré',
    };
    return map[status] ?? status;
  }

  statusClass(status: string): string {
    const map: Record<string, string> = {
      COMPLETED: 'badge-success',
      COMPLETED_WITH_ERRORS: 'badge-warning',
      FAILED: 'badge-danger',
      STOPPED: 'badge-secondary',
      RUNNING: 'badge-info',
      STARTED: 'badge-info',
    };
    return map[status] ?? 'badge-secondary';
  }

  formatDate(iso: string): string {
    if (!iso) return '—';
    return new Date(iso).toLocaleString('fr-FR', {
      day: '2-digit', month: '2-digit', year: 'numeric',
      hour: '2-digit', minute: '2-digit',
    });
  }

  maxTrend(stats: DashboardStats): number {
    if (!stats.volumeTrend?.length) return 1;
    return Math.max(...stats.volumeTrend.map(d => d.cheque + d.effet + d.prelevement), 1);
  }

  barWidth(value: number, max: number): string {
    return Math.round((value / max) * 100) + '%';
  }
}
