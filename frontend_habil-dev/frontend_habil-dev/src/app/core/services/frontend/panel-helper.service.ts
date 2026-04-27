import { Injectable } from '@angular/core';
import { formatDate } from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class PanelHelperService {
  constructor() {}

  formatDate(date: any, format: string = 'dd/MM/yyyy'): string {
    if (!date) return '**********';
    return formatDate(date, format, 'en-US');
  }

  formatCurrency(value: number): string {
    return new Intl.NumberFormat('fr-FR', {
      style: 'currency',
      currency: 'EUR',
    }).format(value);
  }

  formatPercentage(value: number): string {
    return `${value}%`;
  }

  buildHref(path: string, id: string): string {
    return `${path}/${id}`;
  }

  getTagSeverity(status: string): string {
    const severityMap: { [key: string]: string } = {
      active: 'success',
      pending: 'warning',
      closed: 'danger',
      draft: 'info',
    };
    return severityMap[status.toLowerCase()] || 'info';
  }
}
