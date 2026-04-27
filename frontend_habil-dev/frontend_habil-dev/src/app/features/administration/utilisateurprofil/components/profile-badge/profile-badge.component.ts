import { Component, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { Tooltip } from 'primeng/tooltip';

export interface UnifiedProfile {
  codPflPfl: string;
  libpflpfl: string;
  libelle?: string;
  codCatpPfl?: string;
  codNivhPfl?: string;
  libhdebpfl?: string;
  libhfinpfl?: string;
  boolJouvPfl?: string;
  boolEtatPfl?: string;
  codAppApp?: string;
  numMatrUser?: string;
  datFadhUtpr?: string;
  datdadhutpr?: string;
  boolEtatUtpr?: number;
  assignmentStatus: any;
  isActive: boolean;
  displayName: string;
  categoryName: string;
  accessHours: string;
  assignmentDates?: string;
}

@Component({
  selector: 'app-profile-badge',
  templateUrl: './profile-badge.component.html',
  styleUrls: ['./profile-badge.component.scss'],
  standalone: false
})
export class ProfileBadgeComponent {
  triggerShake: boolean = false;
@ViewChild('revokedTooltip', { read: Tooltip })
revokedTooltip!: Tooltip;  @Input() profile!: UnifiedProfile;
  @Input() listType: 'available' | 'assigned' = 'available';
  @Input() isSelected: boolean = false;
  @Input() isNewlyMoved: boolean = false;
  @Input() isDraggingMultiple: boolean = false;
  @Input() selectedCount: number = 0;
  @Output() profileClick = new EventEmitter<UnifiedProfile>();
  @Output() actionClick = new EventEmitter<{ action: string, profile: UnifiedProfile, event: Event }>();
  @Output() profileInfo = new EventEmitter<{ profile: UnifiedProfile, event: Event }>();
  @Output() restoreProfile = new EventEmitter<{ profile: UnifiedProfile, event: Event }>();

  showRestoreAction: boolean = false;
  isRestoring: boolean = false;

  onProfileClick(): void {
    this.profileClick.emit(this.profile);
  }

  /**
   * Shows restore action on hover for revoked profiles
   */
  onMouseEnter(): void {
    if (this.isProfileRevoked && this.listType === 'assigned') {
      this.showRestoreAction = true;
    }
  }

  /**
   * Hides restore action on mouse leave
   */
  onMouseLeave(): void {
    this.showRestoreAction = false;
  }

  /**
   * Handles profile restoration
   */
  onRestoreClick(event: Event): void {
    event.stopPropagation();
    this.isRestoring = true;
    
    // Emit restore event to parent
    this.restoreProfile.emit({ profile: this.profile, event });
    
    // Reset after animation
    setTimeout(() => {
      this.isRestoring = false;
      this.showRestoreAction = false;
    }, 1000);
  }

  onActionClick(action: string, event: Event): void {
    event.stopPropagation();
    this.actionClick.emit({ action, profile: this.profile, event });
  }

  onProfileInfo(event: Event): void {
    event.stopPropagation();
    this.profileInfo.emit({ profile: this.profile, event });
  }
  getDragPreviewText(): string {
    if (this.listType === 'available') {
      return this.profile.displayName || this.profile.libpflpfl;
    } else {
      return this.profile.displayName || this.profile.libpflpfl;
    }
  }

  get isProfilActive():boolean {
      console.log(this.profile)
        return this.profile.boolEtatPfl === '1';
  }
  /**
   * Check if this profile is revoked/inactive for the current user
   * boolEtatUtpr: 0 = revoked/inactive, 1 = active
   */
  get isProfileRevoked(): boolean {
    return this.profile.boolEtatUtpr === 0;
  }

  /**
   * Get the revoked status message
   */
  get revokedStatusMessage(): string {
    return this.isProfileRevoked ? 'PROFIL RÉVOQUÉ' : '';
  }

  onDragAttempt(event: Event): void {
  if (this.isProfileRevoked) {
    event.stopPropagation();

    // Shake animation
    // Show tooltip
    this.revokedTooltip.show();
    this.triggerShake = true;
    setTimeout(() => (this.triggerShake = false), 400);

    // Show tooltip
    if (this.revokedTooltip) {
      this.revokedTooltip.show();
      setTimeout(() => {this.revokedTooltip.hide();
        // hied tooltip
  this.revokedTooltip.hide();
      }, 1500);
    }
  }
}

}
