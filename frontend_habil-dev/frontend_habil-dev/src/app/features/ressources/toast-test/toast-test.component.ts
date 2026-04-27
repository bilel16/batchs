/**
 * @fileoverview GooeyToast Test / Demo Page
 *
 * Interactive builder page — similar to the goey-toast demo site.
 * Lets you pick type, position, options, and fire toasts live.
 *
 * Route: /ressources/toast-test
 */

import { Component } from '@angular/core';
import { ToastService } from '../../../shared/components/gooey-toast/toast/services/toast.service';
import { ToastConfigService } from '../../../shared/components/gooey-toast/toast/services/toast-config.service';
import { ToastPosition, ToastType } from '../../../shared/components/gooey-toast/toast/models/toast.model';
import { MessageService } from 'primeng/api';
import { Observable, of, throwError, delay } from 'rxjs';

@Component({
  selector: 'app-toast-test',
  templateUrl: './toast-test.component.html',
  styleUrls: ['./toast-test.component.scss'],
  standalone: false,
})
export class ToastTestComponent {
  // ── Builder state ─────────────────────────────────────────────────
  selectedType: ToastType = 'success';
  selectedPosition: ToastPosition = 'top-right';
  title = 'Opération réussie !';
  description = 'Le profil a été mis à jour avec succès.';
  showDescription = true;
  showAction = false;
  actionLabel = 'Annuler';
  actionSuccessLabel = 'Annulé !';
  duration = 4000;
  showProgress = true;
  theme: 'light' | 'dark' = 'light';
  useBridge = false; // test via MessageService bridge

  types: { value: ToastType; label: string; icon: string; color: string }[] = [
    { value: 'success', label: 'Succès',        icon: 'pi pi-check-circle',         color: '#22c55e' },
    { value: 'error',   label: 'Erreur',         icon: 'pi pi-times-circle',         color: '#ef4444' },
    { value: 'warning', label: 'Avertissement',  icon: 'pi pi-exclamation-triangle', color: '#f59e0b' },
    { value: 'info',    label: 'Information',     icon: 'pi pi-info-circle',          color: '#3b82f6' },
    { value: 'default', label: 'Défaut',          icon: 'pi pi-circle',               color: '#64748b' },
  ];

  positions: { value: ToastPosition; label: string }[] = [
    { value: 'top-left',      label: '↖ Haut gauche' },
    { value: 'top-center',    label: '↑ Haut centre' },
    { value: 'top-right',     label: '↗ Haut droite' },
    { value: 'bottom-left',   label: '↙ Bas gauche' },
    { value: 'bottom-center', label: '↓ Bas centre' },
    { value: 'bottom-right',  label: '↘ Bas droite' },
  ];

  presets: { label: string; icon: string; fn: () => void }[] = [];

  // ── Code preview ────────────────────────────────────────────────
  get codePreview(): string {
    if (this.useBridge) {
      const sevMap: Record<string, string> = { success: 'success', error: 'error', warning: 'warn', info: 'info', default: 'info' };
      return `this.messageService.add({
  severity: '${sevMap[this.selectedType]}',
  summary: '${this.title}',${this.showDescription ? `\n  detail: '${this.description}',` : ''}
  life: ${this.duration}
});`;
    }
    const method = this.selectedType === 'default' ? 'show' : this.selectedType;
    let opts = '';
    const parts: string[] = [];
    if (this.showDescription) parts.push(`  description: '${this.description}'`);
    if (this.duration !== 4000) parts.push(`  duration: ${this.duration}`);
    if (this.showProgress) parts.push(`  showProgress: true`);
    if (this.showAction) {
      parts.push(`  action: {
    label: '${this.actionLabel}',
    onClick: () => console.log('clicked'),
    successLabel: '${this.actionSuccessLabel}'
  }`);
    }
    if (parts.length) opts = `, {\n${parts.join(',\n')}\n}`;
    return `this.toast.${method}('${this.title}'${opts});`;
  }
  constructor(
    public toast: ToastService,
    private toastConfig: ToastConfigService,
    private messageService: MessageService,
  ) {
    this.presets = [
      {
        label: 'Succès simple',
        icon: 'pi pi-check',
        fn: () => this.toast.success('Enregistrement réussi'),
      },
      {
        label: 'Erreur + détail',
        icon: 'pi pi-times',
        fn: () => this.toast.error('Échec de connexion', {
          description: 'Le serveur ne répond pas. Veuillez réessayer ultérieurement.',
          duration: 6000,
        }),
      },
      {
        label: 'Avertissement + action',
        icon: 'pi pi-exclamation-triangle',
        fn: () => this.toast.warning('Session expirée bientôt', {
          description: 'Votre session expire dans 5 minutes.',
          action: {
            label: 'Prolonger',
            onClick: () => this.toast.success('Session prolongée !'),
            successLabel: 'Prolongée !',
          },
          duration: 8000,
        }),
      },
      {
        label: 'Info avec progrès',
        icon: 'pi pi-info-circle',
        fn: () => this.toast.info('Synchronisation en cours', {
          description: 'Les données sont mises à jour.',
          showProgress: true,
          duration: 5000,
        }),
      },
      {
        label: 'Promise (succès)',
        icon: 'pi pi-spin pi-spinner',
        fn: () => {
          const fakeApi: Observable<string> = of('42 profils synchronisés').pipe(delay(2500));
          this.toast.promise(fakeApi, {
            loading: 'Chargement des données…',
            success: (data) => `Terminé : ${data}`,
            error: 'Erreur lors du chargement',
            description: {
              loading: 'Veuillez patienter pendant le traitement.',
              success: (data) => `${data} — opération achevée avec succès.`,
            },
          });
        },
      },
      {
        label: 'Promise (échec)',
        icon: 'pi pi-ban',
        fn: () => {
          const fakeErr: Observable<never> = throwError(() => new Error('timeout')).pipe(delay(2000));
          this.toast.promise(fakeErr, {
            loading: 'Tentative de connexion…',
            success: 'Connecté',
            error: (err: any) => `Erreur : ${err.message || 'inconnue'}`,
            description: {
              loading: 'Connexion au serveur en cours…',
              error: 'La connexion au serveur a échoué.',
            },
          });
        },
      },
      {
        label: 'Via MessageService (bridge)',
        icon: 'pi pi-envelope',
        fn: () => {
          this.messageService.add({
            severity: 'success',
            summary: 'Toast PrimeNG ponté',
            detail: 'Ce toast a été envoyé via MessageService.add() et intercepté par le bridge GooeyToast.',
            life: 5000,
          });
        },
      },      {
        label: 'Spam 5 toasts',
        icon: 'pi pi-bolt',
        fn: () => {
          const types: ToastType[] = ['success', 'error', 'warning', 'info', 'default'];
          types.forEach((t, i) => {
            setTimeout(() => {
              const method = t === 'default' ? 'show' : t;
              (this.toast as any)[method](`Toast ${i + 1} — ${t}`, {
                description: `Auto-test de type "${t}"`,
                showProgress: true,
              });
            }, i * 400);
          });
        },
      },
      {
        label: 'Error shake (promise)',
        icon: 'pi pi-exclamation-circle',
        fn: () => {
          const fakeErr: Observable<never> = throwError(() => new Error('shake test')).pipe(delay(1500));
          this.toast.promise(fakeErr, {
            loading: 'Vérification en cours…',
            success: 'OK',
            error: 'Échec de vérification !',
            description: {
              loading: 'Patientez…',
              error: 'Le test de shake a fonctionné.',
            },
          });
        },
      },
    ];
  }

  // ── Fire the builder toast ──────────────────────────────────────
  fireToast(): void {
    const opts: any = {
      duration: this.duration,
      showProgress: this.showProgress,
    };
    if (this.showDescription) opts.description = this.description;
    if (this.showAction) {
      opts.action = {
        label: this.actionLabel,
        onClick: () => console.log('Action clicked'),
        successLabel: this.actionSuccessLabel,
      };
    }

    if (this.useBridge) {
      const sevMap: Record<string, string> = { success: 'success', error: 'error', warning: 'warn', info: 'info', default: 'info' };
      this.messageService.add({
        severity: sevMap[this.selectedType],
        summary: this.title,
        detail: this.showDescription ? this.description : undefined,
        life: this.duration,
      });
      return;
    }

    const method = this.selectedType === 'default' ? 'show' : this.selectedType;
    (this.toast as any)[method](this.title, opts);
  }
  dismissAll(): void {
    this.toast.dismiss();
  }
  onPositionChange(pos: ToastPosition): void {
    this.selectedPosition = pos;
    this.toastConfig.setPosition(pos);
  }

  onThemeChange(): void {
    this.theme = this.theme === 'light' ? 'dark' : 'light';
    this.toastConfig.setTheme(this.theme);
  }

  onTypeChange(type: ToastType): void {
    this.selectedType = type;
    const titleMap: Record<ToastType, string> = {
      success: 'Opération réussie !',
      error: 'Une erreur est survenue',
      warning: 'Attention requise',
      info: 'Information importante',
      default: 'Notification',
    };
    const descMap: Record<ToastType, string> = {
      success: 'Le profil a été mis à jour avec succès.',
      error: 'Impossible de sauvegarder les modifications.',
      warning: 'Certaines données peuvent être incomplètes.',
      info: 'Une mise à jour système est disponible.',
      default: 'Ceci est un message de notification.',
    };
    this.title = titleMap[type];
    this.description = descMap[type];
  }
}
