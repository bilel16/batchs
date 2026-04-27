import { Injectable, signal, OnDestroy } from '@angular/core';
import { Client, IFrame } from '@stomp/stompjs';
import { environment } from '../../../../environments/environment';
import { AgenceProgress, BatchStatusMessage } from '../../models/batch.model';

@Injectable({ providedIn: 'root' })
export class WebSocketService implements OnDestroy {
  // ── Reactive signals consumed by components ──────────────────────
  readonly agenceProgress = signal<AgenceProgress[]>([]);
  readonly batchStatus = signal<BatchStatusMessage | null>(null);
  readonly connected = signal<boolean>(false);

  private client: Client | null = null;
  private currentExecutionId: string | null = null;

  /** Connect to STOMP and subscribe to batch topics */
  connect(executionId: string): void {
    // If already connected to same execution, skip
    if (this.connected() && this.currentExecutionId === executionId) return;

    this.disconnect();
    this.currentExecutionId = executionId;
    this.agenceProgress.set([]);
    this.batchStatus.set(null);

    const wsUrl = environment.apiURL.replace(/^http/, 'ws').replace(/\/$/, '') + '/ws';

    this.client = new Client({
      webSocketFactory: () => {
        // SockJS fallback via HTTP polling when native WS unavailable
        const SockJS = (window as any)['SockJS'] ?? null;
        if (SockJS) return new SockJS(environment.apiURL.replace(/\/$/, '') + '/ws');
        return new WebSocket(wsUrl);
      },
      reconnectDelay: 3000,
      onConnect: (_frame: IFrame) => {
        this.connected.set(true);

        // Subscribe to per-agency progress
        this.client?.subscribe('/topic/batch/progress', (msg) => {
          try {
            const data: AgenceProgress = JSON.parse(msg.body);
            if (data.executionId !== this.currentExecutionId) return;
            this.agenceProgress.update((list) => {
              const idx = list.findIndex((a) => a.structure === data.structure);
              return idx >= 0 ? list.map((a, i) => (i === idx ? data : a)) : [...list, data];
            });
          } catch { /* ignore parse error */ }
        });

        // Subscribe to global batch status
        this.client?.subscribe('/topic/batch/status', (msg) => {
          try {
            const data: BatchStatusMessage = JSON.parse(msg.body);
            if (data.executionId !== this.currentExecutionId) return;
            this.batchStatus.set(data);
          } catch { /* ignore */ }
        });
      },
      onDisconnect: () => this.connected.set(false),
      onStompError: () => this.connected.set(false),
    });

    this.client.activate();
  }

  disconnect(): void {
    this.client?.deactivate();
    this.client = null;
    this.connected.set(false);
    this.currentExecutionId = null;
  }

  ngOnDestroy(): void {
    this.disconnect();
  }
}
