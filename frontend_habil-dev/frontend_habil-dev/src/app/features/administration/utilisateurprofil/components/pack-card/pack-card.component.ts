/**
 * @fileoverview Pack Card Component
 * 
 * Displays a profile pack as a visually appealing card
 * with pack details, profile count, and action buttons
 * 
 * @author BNA HABIL Development Team
 * @version 2.0.0
 * @since 2025-12-04
 * @updated 2025-01-07 - Added marketplace mode support
 * @updated 2025-12-24 - Added BNA Speed Dial with branded logo button
 */

import { Component, Input, Output, EventEmitter, OnInit, HostListener } from '@angular/core';
import { trigger, transition, style, animate, state } from '@angular/animations';
import { ProfilePack } from '../../../../../core/models/profile-pack';
import { ProfilService } from '../../../../../core/services/backend/profil.service';

/**
 * Speed Dial Action Interface
 * Defines the structure for each action button in the radial menu
 */
interface SpeedDialAction {
  id: string;
  icon: string;
  tooltip: string;
  action: () => void;
}

@Component({
  selector: 'app-pack-card',
  templateUrl: './pack-card.component.html',
  styleUrls: ['./pack-card.component.scss'],
  standalone: false,
  animations: [
    // Existing slideDown animation for profile preview
    trigger('slideDown', [
      transition(':enter', [
        style({ height: '0', opacity: 0, overflow: 'hidden' }),
        animate('300ms ease-out', style({ height: '*', opacity: 1 }))
      ]),
      transition(':leave', [
        animate('200ms ease-in', style({ height: '0', opacity: 0, overflow: 'hidden' }))
      ])
    ]),
    /**
     * Speed Dial Logo Rotation Animation
     * Rotates the BNA logo 180° to indicate activation state
     * - Smooth cubic-bezier easing for premium feel
     * - 250ms duration for responsive but not jarring motion
     */
    trigger('logoRotate', [
      state('closed', style({ transform: 'rotate(0deg)' })),
      state('open', style({ transform: 'rotate(180deg)' })),
      transition('closed <=> open', animate('250ms cubic-bezier(0.4, 0, 0.2, 1)'))
    ]),
    /**
     * Speed Dial Action Button Animation
     * Each action button expands from center with staggered timing
     * - Scale from 0 to 1 for pop-in effect
     * - Opacity fade for smooth appearance
     * - Transform for radial positioning is handled in SCSS
     */
    trigger('actionExpand', [
      transition(':enter', [
        style({ opacity: 0, transform: 'scale(0) translate(0, 0)' }),
        animate('200ms cubic-bezier(0.4, 0, 0.2, 1)', 
          style({ opacity: 1, transform: 'scale(1)' }))
      ]),
      transition(':leave', [
        animate('150ms cubic-bezier(0.4, 0, 0.2, 1)', 
          style({ opacity: 0, transform: 'scale(0)' }))
      ])
    ])
  ]
})
export class PackCardComponent implements OnInit {
  /** Pack to display */
  @Input() pack!: ProfilePack;
  
  /** Whether the pack is selected */
  @Input() isSelected = false;
  
  /** Whether the pack is already assigned */
  @Input() isAssigned = false;
  
  /** Whether to show compact view */
  @Input() compact = false;
  
  /** Display mode: 'standard' or 'marketplace' */
  @Input() mode: 'standard' | 'marketplace' = 'standard';
  
  /** Emits when pack is clicked */
  @Output() packClick = new EventEmitter<ProfilePack>();
  
  /** Emits when assign button is clicked */
  @Output() assignClick = new EventEmitter<ProfilePack>();
  
  /** Emits when unassign button is clicked */
  @Output() unassignClick = new EventEmitter<ProfilePack>();
  
  /** Emits when info button is clicked */
  @Output() infoClick = new EventEmitter<ProfilePack>();

  /** Show profile preview section */
  showProfilePreview = false;

  /** Profile details dialog state */
  showProfileDetailsDialog = false;
  showAllProfilesDialog = false;
  selectedProfileForDetails: any = null;
  profileDetails: any = null;
  loadingProfileDetails = false;  /** All profiles list and filtering */
  allProfiles: Array<{ code: string, name: string, isActive?: boolean }> = [];
  filteredAllProfiles: Array<{ code: string, name: string, isActive?: boolean }> = [];
  profileSearchTerm = '';

  /** Cache for profile details to avoid repeated API calls */
  private profileDetailsCache: Map<string, any> = new Map();

  /** Cached profiles preview to prevent infinite loop in template */
  profilesPreviewCache: Array<{ code: string, name: string, isActive?: boolean }> = [];

  // ============================================================================
  // SPEED DIAL STATE & CONFIGURATION
  // ============================================================================
  
  /** 
   * Speed Dial open/closed state 
   * Controls logo rotation and action button visibility
   */
  speedDialOpen = false;

  /**
   * Speed Dial action items
   * Configured with icons, tooltips, and action handlers
   * Actions expand radially from the center BNA logo button
   */
  speedDialActions: SpeedDialAction[] = [];
  constructor(private profilService: ProfilService) {}
  ngOnInit(): void {
    // Initialize Speed Dial actions with pack-specific handlers
    this.initSpeedDialActions();
    
    // Initialize profiles preview cache
    this.updateProfilesPreviewCache();
    
    // Initialize component and load profile details if only codes are available
    if (this.pack && (!this.pack.profiles || this.pack.profiles.length === 0) && this.pack.profileCodes && this.pack.profileCodes.length > 0) {
      this.loadProfileDetailsForCodes();
    }
  }

  // ============================================================================
  // SPEED DIAL METHODS
  // ============================================================================

  /**
   * Initialize Speed Dial action buttons
   * Each action is positioned radially and has its own handler
   */
  private initSpeedDialActions(): void {
    this.speedDialActions = [
      {
        id: 'view-profiles',
        icon: 'pi pi-eye',
        tooltip: 'Voir les profils',
        action: () => this.onSpeedDialViewProfiles()
      },
      {
        id: 'add-to-cart',
        icon: 'pi pi-plus',
        tooltip: 'Ajouter au panier',
        action: () => this.onSpeedDialAddToCart()
      },
      {
        id: 'view-details',
        icon: 'pi pi-info-circle',
        tooltip: 'Détails du pack',
        action: () => this.onSpeedDialViewDetails()
      }
    ];
  }

  /**
   * Toggle Speed Dial open/closed state
   * Triggers logo rotation animation
   * @param event - Click event to stop propagation
   */
  toggleSpeedDial(event: Event): void {
    event.stopPropagation();
    this.speedDialOpen = !this.speedDialOpen;
  }

  /**
   * Close Speed Dial when clicking outside
   * Called via HostListener on document click
   */
  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    // Close speed dial if clicking outside of it
    if (this.speedDialOpen) {
      const target = event.target as HTMLElement;
      if (!target.closest('.bna-speed-dial')) {
        this.speedDialOpen = false;
      }
    }
  }

  /**
   * Execute Speed Dial action and close the menu
   * @param action - The action to execute
   * @param event - Click event to stop propagation
   */
  onSpeedDialAction(action: SpeedDialAction, event: Event): void {
    event.stopPropagation();
    action.action();
    this.speedDialOpen = false;
  }

  /**
   * Speed Dial Action: View pack profiles
   * Opens the profile preview section
   */
  private onSpeedDialViewProfiles(): void {
    if (this.profileCount > 0) {
      this.showProfilePreview = true;
    }
  }

  /**
   * Speed Dial Action: Add pack to cart
   * Emits the assign event for marketplace mode
   */
  private onSpeedDialAddToCart(): void {
    if (this.canAssign) {
      this.assignClick.emit(this.pack);
    }
  }

  /**
   * Speed Dial Action: View pack details
   * Emits the info event to show detailed pack information
   */
  private onSpeedDialViewDetails(): void {
    this.infoClick.emit(this.pack);
  }

  /**
   * Handle pack card click
   */
  onPackClick(): void {
    this.packClick.emit(this.pack);
  }

  /**
   * Handle assign button click
   */
  onAssignClick(event: Event): void {
    event.stopPropagation();
    this.assignClick.emit(this.pack);
  }

  /**
   * Handle unassign button click
   */
  onUnassignClick(event: Event): void {
    event.stopPropagation();
    this.unassignClick.emit(this.pack);
  }

  /**
   * Handle info button click
   */
  onInfoClick(event: Event): void {
    event.stopPropagation();
    this.infoClick.emit(this.pack);
  }

  /**
   * Get category color based on pack category
   */
  getCategoryColor(): string {
    const colors: Record<string, string> = {
      'ADMINISTRATION': '#3b82f6',
      'COMMERCIAL': '#10b981',
      'FINANCE': '#f59e0b',
      'OPERATIONS': '#8b5cf6',
      'MANAGEMENT': '#ef4444',
      'CUSTOM': '#6366f1'
    };
    return colors[this.pack.category] || '#6b7280';
  }

  /**
   * Get category icon based on pack category
   */
  getCategoryIcon(): string {
    const icons: Record<string, string> = {
      'ADMINISTRATION': 'pi-cog',
      'COMMERCIAL': 'pi-shopping-cart',
      'FINANCE': 'pi-dollar',
      'OPERATIONS': 'pi-sitemap',
      'MANAGEMENT': 'pi-users',
      'CUSTOM': 'pi-box'
    };
    return icons[this.pack.category] || 'pi-box';
  }
  /**
   * Get profile count for display
   */
  get profileCount(): number {
    return this.pack.profiles?.length || this.pack.profileCodes?.length || 0;
  }

  /**
   * Check if pack has any profiles
   */
  get hasProfiles(): boolean {
    return this.profileCount > 0;
  }

  /**
   * Check if pack can be assigned (has profiles)
   */
  get canAssign(): boolean {
    return this.hasProfiles && !this.isAssigned;
  }

  /**
   * Get tooltip content showing profile list
   */
  getProfileTooltip(): string {
    if (this.profileCount === 0) {
      return 'Aucun profil assigné';
    }

    const profiles = this.pack.profiles || [];
    const profileCodes = this.pack.profileCodes || [];
    
    let tooltipContent = '<div class="profile-tooltip-content">';
    tooltipContent += `<strong>${this.profileCount} Profil${this.profileCount > 1 ? 's' : ''}</strong><br/>`;
    
    if (profiles.length > 0) {
      // Show actual profile names if available
      const displayProfiles = profiles.slice(0, 5);
      displayProfiles.forEach(profile => {
        tooltipContent += `<span class="profile-item">• ${profile.libpflpfl || profile.codPflPfl}</span><br/>`;
      });
      if (profiles.length > 5) {
        tooltipContent += `<span class="more-profiles">... et ${profiles.length - 5} de plus</span>`;
      }
    } else if (profileCodes.length > 0) {
      // Show profile codes if names not available
      const displayCodes = profileCodes.slice(0, 5);
      displayCodes.forEach(code => {
        tooltipContent += `<span class="profile-item">• ${code}</span><br/>`;
      });
      if (profileCodes.length > 5) {
        tooltipContent += `<span class="more-profiles">... et ${profileCodes.length - 5} de plus</span>`;
      }
    }
    
    tooltipContent += '</div>';
    return tooltipContent;
  }
  /**
   * Update profiles preview cache
   * Called once during init and when pack data changes
   * Prevents infinite loop by caching the result
   */
  private updateProfilesPreviewCache(): void {
    const profiles = this.pack.profiles || [];
    const profileCodes = this.pack.profileCodes || [];

    if (profiles.length > 0) {
      this.profilesPreviewCache = profiles.slice(0, 3).map(p => ({
        code: p.codPflPfl,
        name: p.libpflpfl || p.codPflPfl,
        isActive: this.checkProfileActive(p)
      }));
    } else {
      this.profilesPreviewCache = profileCodes.slice(0, 3).map(code => ({
        code: code,
        name: code,
        isActive: true // Default to active if status unknown
      }));
    }
  }

  /**
   * Get first 3 profiles for preview
   * @deprecated Use profilesPreviewCache property directly instead
   * This method is kept for backward compatibility but returns cached data
   */
  getProfilesPreview(): Array<{ code: string, name: string, isActive?: boolean }> {
    return this.profilesPreviewCache;
  }

  /**
   * Handle view profiles button click
   */
  onViewProfiles(event: Event): void {
    event.stopPropagation();
    this.showProfilePreview = !this.showProfilePreview;
  }

  /**
   * Close profile preview
   */
  closeProfilePreview(event: Event): void {
    event.stopPropagation();
    this.showProfilePreview = false;
  }

  /**
   * Handle profile chip click - Opens profile details dialog
   */
  onProfileChipClick(profile: { code: string, name: string }, event: Event): void {
    event.stopPropagation();
    this.selectedProfileForDetails = profile;
    this.loadProfileDetails(profile.code);
    this.showProfileDetailsDialog = true;
  }

  /**
   * Handle view all profiles button click
   */
  onViewAllProfiles(event: Event): void {
    event.stopPropagation();
    this.prepareAllProfilesList();
    this.showAllProfilesDialog = true;
  }

  /**
   * Handle profile item click in the all profiles list
   */
  onProfileItemClick(profile: { code: string, name: string }, event: Event): void {
    // Just highlight or show info, don't open dialog
    console.log('Profile clicked:', profile);
  }

  /**
   * Load full profile details from API
   * Optimized to use getProfilById for efficient single-profile fetching
   */
  private loadProfileDetails(profileCode: string): void {
    this.loadingProfileDetails = true;
    this.profileDetails = null;

    // Priority 1: Check cache - Get profile details from pack.profiles array (already loaded)
    if (this.pack.profiles && this.pack.profiles.length > 0) {
      const profile = this.pack.profiles.find(p => p.codPflPfl === profileCode);
      if (profile) {
        this.profileDetails = profile;
        this.loadingProfileDetails = false;
        return;
      }
    }

    // Priority 2: Call getProfilById API for efficient single-profile fetch
    this.profilService.getProfilById(profileCode).subscribe({
      next: (response: any) => {
        // API returns: { code, message, data: { codPflPfl, libpflpfl, ... } }
        if (response.data) {
          this.profileDetails = response.data;
        } else {
          // Fallback to basic info if no data in response
          this.profileDetails = {
            codPflPfl: profileCode,
            libpflpfl: profileCode,
            status: 'unknown'
          };
        }
        this.loadingProfileDetails = false;
      },
      error: (error) => {
        console.error('Error loading profile details:', error);
        this.loadingProfileDetails = false;
        
        // Priority 3: Fallback to basic info on error
        this.profileDetails = {
          codPflPfl: profileCode,
          libpflpfl: profileCode,
          status: 'unknown',
          application: 'Non disponible'
        };
      }
    });
  }

  /**
   * Prepare list of all profiles for the all profiles dialog
   */
  private prepareAllProfilesList(): void {
    const profiles = this.pack.profiles || [];
    const profileCodes = this.pack.profileCodes || [];
    
    // DEBUG: Log the profiles data to see the actual structure
    console.log('===== PROFILES DATA DEBUG =====');
    console.log('All profiles:', profiles);
    if (profiles.length > 0) {
      console.log('First profile sample:', profiles[0]);
      console.log('boolEtatPfl value:', profiles[0].boolEtatPfl);
      console.log('boolEtatPfl type:', typeof profiles[0].boolEtatPfl);
      console.log('All profile fields:', Object.keys(profiles[0]));
    }
    console.log('==============================');
    
    if (profiles.length > 0) {
      this.allProfiles = profiles.map(p => {
        // Check multiple possible status field formats
        const isActive = this.checkProfileActive(p);
        console.log(`Profile ${p.codPflPfl}: isActive = ${isActive} (boolEtatPfl = ${p.boolEtatPfl})`);
        
        return {
          code: p.codPflPfl,
          name: p.libpflpfl || p.codPflPfl,
          isActive: isActive
        };
      });
    } else {
      this.allProfiles = profileCodes.map(code => ({
        code: code,
        name: code,
        isActive: true // Default to active if status unknown
      }));
    }

    this.filteredAllProfiles = [...this.allProfiles];
    this.profileSearchTerm = '';
  }

  /**
   * Filter all profiles based on search term
   */
  filterAllProfiles(): void {
    if (!this.profileSearchTerm || this.profileSearchTerm.trim() === '') {
      this.filteredAllProfiles = [...this.allProfiles];
      return;
    }

    const searchLower = this.profileSearchTerm.toLowerCase();
    this.filteredAllProfiles = this.allProfiles.filter(profile =>
      profile.code.toLowerCase().includes(searchLower) ||
      profile.name.toLowerCase().includes(searchLower)
    );
  }

  /**
   * Get dialog header text
   */
  getDialogHeader(): string {
    if (this.selectedProfileForDetails) {
      return `Détails du profil: ${this.selectedProfileForDetails.code}`;
    }
    return 'Détails du profil';
  }

  /**
   * Close profile details dialog
   */
  closeProfileDetailsDialog(): void {
    this.showProfileDetailsDialog = false;
    this.selectedProfileForDetails = null;
    this.profileDetails = null;
  }

  /**
   * Close all profiles dialog
   */
  closeAllProfilesDialog(): void {
    this.showAllProfilesDialog = false;
    this.profileSearchTerm = '';
  }

  /**
   * Get structure type based on hierarchy level code
   * @param codNivhPfl Hierarchy level code
   * @returns Structure type label
   */
  getStructureType(codNivhPfl: string | number): string {
    const structureMap: Record<string, string> = {
      '1': 'AGENCE',
      '2': 'DIRECTION REGIONALE',
      '3': 'DIRECTION CENTRALE',
      '4': 'DIVISION',
      '5': 'DIRECTION',
      '6': 'SUCCURSALE',
      '7': 'Box de Change'
    };
    return structureMap[String(codNivhPfl)] || 'N/A';
  }

  /**
   * Check if a profile is active - handles multiple possible field formats
   * @param profile Profile object with status information
   * @returns true if active, false if inactive
   */
  private checkProfileActive(profile: any): boolean {
    // Check boolEtatPfl field - could be string '1'/'0', boolean, or number
    if (profile.boolEtatPfl !== undefined && profile.boolEtatPfl !== null) {
      // String format: '1' = active, '0' = inactive
      if (typeof profile.boolEtatPfl === 'string') {
        return profile.boolEtatPfl === '1';
      }
      // Boolean format: true = active, false = inactive
      if (typeof profile.boolEtatPfl === 'boolean') {
        return profile.boolEtatPfl;
      }
      // Number format: 1 = active, 0 = inactive
      if (typeof profile.boolEtatPfl === 'number') {
        return profile.boolEtatPfl === 1;
      }
    }
    
    // Check alternative field names
    if (profile.status !== undefined) {
      if (typeof profile.status === 'string') {
        return profile.status.toLowerCase() === 'active' || profile.status === '1';
      }
      if (typeof profile.status === 'boolean') {
        return profile.status;
      }
    }
    
    if (profile.active !== undefined) {
      return !!profile.active;
    }
    
    if (profile.isActive !== undefined) {
      return !!profile.isActive;
    }
    
    // Default to active if no status field found
    return true;
  }

  /**
   * Load full profile details when only profile codes are available
   * This populates the profiles array with full profile objects including status
   */
  private loadProfileDetailsForCodes(): void {
    if (!this.pack.profileCodes || this.pack.profileCodes.length === 0) {
      return;
    }

    console.log('Loading profile details for codes:', this.pack.profileCodes);

    // Initialize profiles array if not exists
    if (!this.pack.profiles) {
      this.pack.profiles = [];
    }

    // Load details for each profile code
    this.pack.profileCodes.forEach((profileCode: string) => {
      // Check cache first
      if (this.profileDetailsCache.has(profileCode)) {
        const cachedProfile = this.profileDetailsCache.get(profileCode);
        if (!this.pack.profiles!.find(p => p.codPflPfl === profileCode)) {
          this.pack.profiles!.push(cachedProfile);
        }
        return;
      }

      // Fetch from API
      this.profilService.getProfilById(profileCode).subscribe({
        next: (response: any) => {
          if (response.data) {
            const profileData = response.data;
            
            // Add to cache
            this.profileDetailsCache.set(profileCode, profileData);
            
            // Add to pack.profiles if not already there
            if (!this.pack.profiles!.find(p => p.codPflPfl === profileCode)) {
              this.pack.profiles!.push(profileData);
            }
            
            console.log(`Loaded profile ${profileCode}:`, profileData);
            console.log(`  - boolEtatPfl: ${profileData.boolEtatPfl}`);
            console.log(`  - isActive: ${this.checkProfileActive(profileData)}`);
          }
        },
        error: (error) => {
          console.error(`Error loading profile ${profileCode}:`, error);
          
          // Add minimal profile object on error
          const minimalProfile: any = {
            codPflPfl: profileCode,
            libpflpfl: profileCode,
            boolEtatPfl: '1', // Default to active on error
            assignmentStatus: 'unknown',
            isActive: true,
            displayName: profileCode,
            categoryName: 'N/A',
            accessHours: 'N/A'
          };
          
          this.profileDetailsCache.set(profileCode, minimalProfile);
          
          if (!this.pack.profiles!.find(p => p.codPflPfl === profileCode)) {
            this.pack.profiles!.push(minimalProfile);
          }
        }
      });
    });
  }
}
