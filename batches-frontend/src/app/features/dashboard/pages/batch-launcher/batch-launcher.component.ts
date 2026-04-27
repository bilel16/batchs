import { Component, inject, OnDestroy, signal, computed } from '@angular/core';
import { BatchService } from '../../../../core/services/backend/batch.service';
import { WebSocketService } from '../../../../core/services/backend/websocket.service';
import { BatchRequest, BatchExecutionDto, AgenceEtat } from '../../../../core/models/batch.model';

@Component({
  selector: 'app-batch-launcher',
  templateUrl: './batch-launcher.component.html',
  styleUrl: './batch-launcher.component.scss',
  standalone: false,
})
export class BatchLauncherComponent implements OnDestroy {
  private readonly batchService = inject(BatchService);
  readonly ws = inject(WebSocketService);

  // ── Form model ──────────────────────────────────────────────────────
  form: BatchRequest = {
    instrument: 'CHEQUE',
    phase: 'INSERTION',
    dryRun: false,
    notifyEmail: false,
    retryMax: 1,
  };

  // ── State ────────────────────────────────────────────────────────────
  launching = signal(false);
  stopping  = signal(false);
  error     = signal<string | null>(null);
  currentExecution = signal<BatchExecutionDto | null>(null);

  // ── Derived ─────────────────────────────────────────────────────────
  readonly isRunning = computed(() => {
    const s = this.ws.batchStatus();
    return s?.status === 'RUNNING' || s?.status === 'STARTED';
  });

  readonly isFinished = computed(() => {
    const s = this.ws.batchStatus();
    return s?.status === 'COMPLETED' || s?.status === 'COMPLETED_WITH_ERRORS'
        || s?.status === 'FAILED'    || s?.status === 'STOPPED';
  });

  launch(): void {
    this.error.set(null);
    this.launching.set(true);
    this.ws.agenceProgress.set([]);
    this.ws.batchStatus.set(null);
    this.currentExecution.set(null);

    this.batchService.launch(this.form).subscribe({
      next: (exec) => {
        this.currentExecution.set(exec);
        this.launching.set(false);
        this.ws.connect(exec.executionId);
      },
      error: (err) => {
        this.error.set(err?.error?.message ?? 'Erreur lors du lancement.');
        this.launching.set(false);
      },
    });
  }

  stop(): void {
    const exec = this.currentExecution();
    if (!exec) return;
    this.stopping.set(true);
    this.batchService.stop(exec.executionId).subscribe({
      next: () => this.stopping.set(false),
      error: () => this.stopping.set(false),
    });
  }

  reset(): void {
    this.ws.disconnect();
    this.ws.agenceProgress.set([]);
    this.ws.batchStatus.set(null);
    this.currentExecution.set(null);
    this.error.set(null);
  }

  etatIcon(etat: AgenceEtat): string {
    const map: Record<AgenceEtat, string> = {
      EN_ATTENTE: 'pi-clock',
      EN_COURS:   'pi-spin pi-spinner',
      TERMINE:    'pi-check-circle',
      ERREUR:     'pi-times-circle',
    };
    return map[etat] ?? 'pi-circle';
  }

  etatClass(etat: AgenceEtat): string {
    const map: Record<AgenceEtat, string> = {
      EN_ATTENTE: 'etat-attente',
      EN_COURS:   'etat-cours',
      TERMINE:    'etat-termine',
      ERREUR:     'etat-erreur',
    };
    return map[etat] ?? '';
  }

  statusLabel(status: string): string {
    const map: Record<string, string> = {
      STARTED: 'Démarré', RUNNING: 'En cours', COMPLETED: 'Terminé',
      COMPLETED_WITH_ERRORS: 'Terminé avec erreurs', FAILED: 'Échoué', STOPPED: 'Arrêté',
    };
    return map[status] ?? status;
  }

  statusClass(status: string): string {
    const map: Record<string, string> = {
      COMPLETED: 'badge-success', COMPLETED_WITH_ERRORS: 'badge-warning',
      FAILED: 'badge-danger', STOPPED: 'badge-secondary',
      RUNNING: 'badge-info', STARTED: 'badge-info',
    };
    return map[status] ?? 'badge-secondary';
  }

  ngOnDestroy(): void {
    // Leave ws connected if batch still running so user can navigate back
    if (this.isFinished()) this.ws.disconnect();
  }
}
