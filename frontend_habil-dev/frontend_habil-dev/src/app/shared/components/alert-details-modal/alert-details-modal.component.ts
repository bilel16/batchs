/**
 * @fileoverview Alert Details Modal Component
 * 
 * Full-screen modal dialog for displaying detailed error information from alerts.
 * Provides better visibility and navigation for large error lists (40+ items).
 * 
 * @author BNA HABIL Development Team
 * @version 1.0.0
 * @since 2025-12-26
 */

import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NotificationItem, NotificationDetail } from '../../../core/services/frontend/notification.service';

@Component({
  selector: 'app-alert-details-modal',
  templateUrl: './alert-details-modal.component.html',
  styleUrl: './alert-details-modal.component.scss',
  standalone: false
})
export class AlertDetailsModalComponent {
  
  /**
   * Control modal visibility
   */
  @Input() visible = false;
  
  /**
   * The alert item to display
   */
  @Input() alert: NotificationItem | null = null;
  
  /**
   * Event emitted when modal is closed
   */
  @Output() visibleChange = new EventEmitter<boolean>();
  
  /**
   * Event emitted when user requests to remove the alert
   */
  @Output() onRemoveAlert = new EventEmitter<string>();
  
  /**
   * Search term for filtering error details
   */
  searchTerm = '';
  
  /**
   * Filtered details based on search
   */
  filteredDetails: NotificationDetail[] = [];
  
  /**
   * Selected detail item for highlighting
   */
  selectedDetailIndex: number | null = null;
  
  /**
   * Get filtered details based on search term
   */
  get displayedDetails(): NotificationDetail[] {
    if (!this.alert?.details) {
      return [];
    }
    
    if (!this.searchTerm.trim()) {
      return this.alert.details;
    }
    
    const term = this.searchTerm.toLowerCase().trim();
    return this.alert.details.filter(detail => 
      detail.identifier.toLowerCase().includes(term) ||
      detail.message.toLowerCase().includes(term)
    );
  }
  
  /**
   * Get severity icon class
   */
  getSeverityIcon(severity: string): string {
    const icons: Record<string, string> = {
      success: 'pi pi-check-circle',
      info: 'pi pi-info-circle',
      warn: 'pi pi-exclamation-triangle',
      error: 'pi pi-times-circle'
    };
    return icons[severity] || 'pi pi-bell';
  }
  
  /**
   * Get severity color class
   */
  getSeverityClass(severity: string): string {
    return `severity-${severity}`;
  }
  
  /**
   * Format timestamp for display
   */
  formatTimestamp(date: Date): string {
    const now = new Date();
    const diff = now.getTime() - date.getTime();
    const minutes = Math.floor(diff / 60000);
    const hours = Math.floor(diff / 3600000);
    const days = Math.floor(diff / 86400000);

    if (minutes < 1) return 'À l\'instant';
    if (minutes < 60) return `Il y a ${minutes} min`;
    if (hours < 24) return `Il y a ${hours}h`;
    if (days < 7) return `Il y a ${days}j`;
    return date.toLocaleDateString('fr-FR');
  }
  
  /**
   * Close the modal
   */
  closeModal(): void {
    this.visible = false;
    this.visibleChange.emit(false);
    this.searchTerm = '';
    this.selectedDetailIndex = null;
  }
  
  /**
   * Handle remove alert request
   */
  removeAlert(): void {
    if (this.alert) {
      this.onRemoveAlert.emit(this.alert.id);
      this.closeModal();
    }
  }
  
  /**
   * Select a detail item
   */
  selectDetail(index: number): void {
    this.selectedDetailIndex = this.selectedDetailIndex === index ? null : index;
  }
  
  /**
   * Export error list to clipboard
   */
  exportToClipboard(): void {
    if (!this.alert?.details) return;
    
    const errorList = this.displayedDetails
      .map((detail, index) => `${index + 1}. ${detail.identifier}: ${detail.message}`)
      .join('\n');
    
    const exportText = `
===========================================
RAPPORT D'ERREURS - ${this.alert.title}
===========================================
Date: ${this.formatTimestamp(this.alert.timestamp)}
Total d'erreurs: ${this.displayedDetails.length}
${this.searchTerm ? `Filtre appliqué: "${this.searchTerm}"` : ''}

${errorList}
===========================================
    `.trim();
    
    navigator.clipboard.writeText(exportText).then(() => {
      console.log('✅ Liste d\'erreurs copiée dans le presse-papiers');
    }).catch(err => {
      console.error('❌ Erreur lors de la copie:', err);
    });
  }
  
  /**
   * Track by function for ngFor optimization
   */
  trackByIndex(index: number): number {
    return index;
  }
}
