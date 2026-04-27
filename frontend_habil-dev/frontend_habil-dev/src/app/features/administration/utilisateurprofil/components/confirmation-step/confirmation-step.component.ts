import { ChangeDetectionStrategy, ChangeDetectorRef, Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';

/**
 * Interface for assignment result item
 */
export interface AssignmentResultItem {
  profileCode: string;
  profileName: string;
  success: boolean;
  error?: string;
}

/**
 * Interface for assignment results
 */
export interface AssignmentResults {
  successful: AssignmentResultItem[];
  failed: AssignmentResultItem[];
  totalProcessed: number;
  successCount: number;
  failureCount: number;
  timestamp: Date;
}

@Component({
  selector: 'app-confirmation-step',
  standalone: false,
  templateUrl: './confirmation-step.component.html',
  styleUrl: './confirmation-step.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class ConfirmationStepComponent {
  @Input() selectedUser: any = null;
  @Input() selectedAppCode: string = '';
  @Input() newlyMovedProfiles: { assigned: Set<string>, available: Set<string> } = {
    assigned: new Set<string>(),
    available: new Set<string>()
  };
  @Input() loading: boolean = false;
  
  // Pack-related inputs
  @Input() packsToAssign: any[] = [];
  @Input() allProfiles: any[] = []; // To get profile details

  // Assignment results inputs
  @Input() assignmentResults: AssignmentResults | null = null;
  @Input() isAssigning: boolean = false;
  @Input() assignmentProgress: number = 0;

  @Output() saveAssignments = new EventEmitter<void>();
  @Output() previousStep = new EventEmitter<void>();

  // Reference to results section for auto-scroll
  @ViewChild('resultsSection') resultsSection!: ElementRef;

  // Track expanded state for result items
  expandedResults: Set<string> = new Set();

  constructor(private cdr: ChangeDetectorRef) {}

  getAssignedArray() {
    return Array.from(this.newlyMovedProfiles.assigned);
  }

  getAvailableArray() {
    return Array.from(this.newlyMovedProfiles.available);
  }

  trackByProfile(index: number, profile: any): any {
    return profile;
  }

  getNewlyMovedCount(): number {
    return this.newlyMovedProfiles.assigned.size + this.newlyMovedProfiles.available.size;
  }

  onSaveAssignments(): void {
    this.saveAssignments.emit();
  }

  onPreviousStep(): void {
    this.previousStep.emit();
  }

  getProfileDisplayName(profileCode: string): string {
    return profileCode;
  }

  /**
   * Get profiles for a specific pack
   */
  getPackProfiles(pack: any): string[] {
    return pack.profileCodes || pack.profiles?.map((p: any) => p.code || p.codPflPfl) || [];
  }

  /**
   * Get profile name from code
   */
  getProfileName(profileCode: string): string {
    const profile = this.allProfiles.find(p => p.codPflPfl === profileCode);
    console.log("heyyyyyyyyy",this.allProfiles)
    console.log(profileCode)
    return profile?.libpflpfl || profile?.displayName || profileCode;
  }

  /**
   * Get total count including packs and individual profiles
   */
  getTotalChangesCount(): number {
    const packProfilesCount = this.packsToAssign.reduce((sum, pack) => {
      return sum + this.getPackProfiles(pack).length;
    }, 0);
    return this.getNewlyMovedCount() + packProfilesCount;
  }

  /**
   * Toggle result item expansion
   */
  toggleResultExpansion(profileCode: string): void {
    if (this.expandedResults.has(profileCode)) {
      this.expandedResults.delete(profileCode);
    } else {
      this.expandedResults.add(profileCode);
    }
  }

  /**
   * Check if result item is expanded
   */
  isResultExpanded(profileCode: string): boolean {
    return this.expandedResults.has(profileCode);
  }

  /**
   * Get summary of assignment results
   */
  getResultsSummary(): { success: number; failed: number } {
    if (!this.assignmentResults) {
      return { success: 0, failed: 0 };
    }
    return {
      success: this.assignmentResults.successCount,
      failed: this.assignmentResults.failureCount
    };
  }

  /**
   * Check if there are any results to display
   */
  hasResults(): boolean {
    return this.assignmentResults !== null && this.assignmentResults.totalProcessed > 0;
  }

  /**
   * Check if all assignments were successful
   */
  isAllSuccess(): boolean {
    return this.assignmentResults !== null && 
           this.assignmentResults.failureCount === 0 && 
           this.assignmentResults.successCount > 0;
  }

  /**
   * Check if all assignments failed
   */
  isAllFailed(): boolean {
    return this.assignmentResults !== null && 
           this.assignmentResults.successCount === 0 && 
           this.assignmentResults.failureCount > 0;
  }

  /**
   * Check if there was partial success
   */
  isPartialSuccess(): boolean {
    return this.assignmentResults !== null && 
           this.assignmentResults.successCount > 0 && 
           this.assignmentResults.failureCount > 0;
  }

  /**
   * Scroll to results section
   */
  scrollToResults(): void {
    setTimeout(() => {
      if (this.resultsSection?.nativeElement) {
        this.resultsSection.nativeElement.scrollIntoView({ 
          behavior: 'smooth', 
          block: 'start' 
        });
      }
      this.cdr.markForCheck();
    }, 100);
  }

  /**
   * Format timestamp for display
   */
  formatTimestamp(date: Date): string {
    if (!date) return '';
    const d = new Date(date);
    return d.toLocaleTimeString('fr-FR', { 
      hour: '2-digit', 
      minute: '2-digit',
      second: '2-digit'
    });
  }
}

