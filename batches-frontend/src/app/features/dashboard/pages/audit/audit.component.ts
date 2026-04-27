import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { BatchService } from '../../../../core/services/backend/batch.service';
import { AuditLog } from '../../../../core/models/batch.model';

@Component({
  selector: 'app-audit',
  templateUrl: './audit.component.html',
  styleUrl: './audit.component.scss',
  standalone: false,
})
export class AuditComponent implements OnInit {
  private readonly batchService = inject(BatchService);

  filters = { user: '', action: '' };

  allLogs = signal<AuditLog[]>([]);
  loading = signal(true);
  error   = signal<string | null>(null);

  readonly filtered = computed(() => {
    const u = this.filters.user.toLowerCase();
    const a = this.filters.action.toLowerCase();
    return this.allLogs().filter(
      (l) =>
        (!u || l.username.toLowerCase().includes(u)) &&
        (!a || l.action.toLowerCase().includes(a))
    );
  });

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading.set(true);
    this.error.set(null);
    this.batchService.getAuditLogs().subscribe({
      next: (data) => { this.allLogs.set(data); this.loading.set(false); },
      error: () => { this.error.set('Impossible de charger les logs.'); this.loading.set(false); },
    });
  }

  reset(): void {
    this.filters = { user: '', action: '' };
  }

  formatDate(iso: string): string {
    if (!iso) return '—';
    return new Date(iso).toLocaleString('fr-FR', {
      day: '2-digit', month: '2-digit', year: 'numeric',
      hour: '2-digit', minute: '2-digit', second: '2-digit',
    });
  }

  resultClass(result: string): string {
    if (result === 'SUCCESS') return 'badge-success';
    if (result === 'FAILURE') return 'badge-danger';
    return 'badge-secondary';
  }

  actionColor(action: string): string {
    if (action.includes('LAUNCH')) return 'action-launch';
    if (action.includes('STOP'))   return 'action-stop';
    if (action.includes('LOGIN'))  return 'action-login';
    if (action.includes('LOGOUT')) return 'action-logout';
    return 'action-default';
  }
}
