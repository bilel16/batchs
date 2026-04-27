/**
 * @fileoverview PackSelectorComponent - Pack Assignment Hub with Marketplace Design
 * 
 * Provides UI for browsing, selecting, and assigning profile packs to users.
 * Features:
 * - Shopping cart for batch assignment
 * - Pack catalog with grid layout
 * - Search and category filtering
 * - Marketplace-style user experience
 * 
 * @author BNA HABIL Development Team
 * @version 2.0.0
 * @since 2025-01-07
 */

import { Component, Input, Output, EventEmitter, OnInit, OnChanges } from '@angular/core';
import { ProfilePack, PackCategory } from '../../../../../core/models/profile-pack';
import { CdkDragDrop } from '@angular/cdk/drag-drop';

@Component({
  selector: 'app-pack-selector',
  templateUrl: './pack-selector.component.html',
  styleUrls: ['./pack-selector.component.scss'],
  standalone: false
})
export class PackSelectorComponent implements OnInit, OnChanges {
  @Input() availablePacks: ProfilePack[] = [];
  @Input() assignedPacks: ProfilePack[] = [];
  @Input() selectedUser: any = null;
  @Input() loading: boolean = false;
  @Input() isAssigning: boolean = false;
  @Input() cartPacks: ProfilePack[] = []; // Cart state managed by parent

  @Output() packAssign = new EventEmitter<ProfilePack>();
  @Output() packUnassign = new EventEmitter<ProfilePack>();
  @Output() packInfo = new EventEmitter<ProfilePack>();
  @Output() packDrop = new EventEmitter<CdkDragDrop<ProfilePack[]>>();
  @Output() batchAssign = new EventEmitter<ProfilePack[]>();
  @Output() cartChange = new EventEmitter<ProfilePack[]>(); // Emit cart changes to parent

  // Filter State
  searchTerm: string = '';
  selectedCategory: PackCategory | null = null;
  showActiveOnly: boolean = true;

  // Filtered Data
  filteredAvailablePacks: ProfilePack[] = [];
  filteredAssignedPacks: ProfilePack[] = [];

  // UI State
  selectedPackId: string | null = null;
  showAssignedPacks: boolean = false;

  // Category Options
  categoryOptions = Object.values(PackCategory);

  ngOnInit(): void {
    this.applyFilters();
  }

  ngOnChanges(): void {
    this.applyFilters();
  }

  /**
   * Apply search and filter to packs
   */
  applyFilters(): void {
    // Filter Available Packs
    this.filteredAvailablePacks = this.availablePacks.filter(pack => {
      // Search filter
      const matchesSearch = !this.searchTerm || 
        pack.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        pack.code.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        pack.description?.toLowerCase().includes(this.searchTerm.toLowerCase());

      // Category filter
      const matchesCategory = !this.selectedCategory || pack.category === this.selectedCategory;

      // Status filter
      const matchesStatus = !this.showActiveOnly || pack.status === 'ACTIVE';

      return matchesSearch && matchesCategory && matchesStatus;
    });

    // Filter Assigned Packs
    this.filteredAssignedPacks = this.assignedPacks.filter(pack => {
      const matchesSearch = !this.searchTerm || 
        pack.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        pack.code.toLowerCase().includes(this.searchTerm.toLowerCase());

      return matchesSearch;
    });
  }

  /**
   * Handle search input change
   */
  onSearchChange(): void {
    this.applyFilters();
  }

  /**
   * Handle category filter change
   */
  onCategoryChange(): void {
    this.applyFilters();
  }

  /**
   * Handle status filter toggle
   */
  onStatusToggle(): void {
    this.applyFilters();
  }

  /**
   * Clear all filters
   */
  clearFilters(): void {
    this.searchTerm = '';
    this.selectedCategory = null;
    this.showActiveOnly = true;
    this.applyFilters();
  }

  /**
   * Handle pack selection
   */
  onPackClick(pack: ProfilePack): void {
    this.selectedPackId = pack.id === this.selectedPackId ? null : pack.id;
  }

  /**
   * Handle pack assignment (legacy - single assign)
   */
  onAssignClick(pack: ProfilePack): void {
    this.packAssign.emit(pack);
  }

  /**
   * Handle pack unassignment
   */
  onUnassignClick(pack: ProfilePack): void {
    this.packUnassign.emit(pack);
  }

  /**
   * Handle pack info click
   */
  onInfoClick(pack: ProfilePack): void {
    this.packInfo.emit(pack);
  }

  /**
   * Handle drag and drop
   */
  onDrop(event: CdkDragDrop<ProfilePack[]>): void {
    this.packDrop.emit(event);
  }

  // ==========================================
  // Shopping Cart Methods (Marketplace Design)
  // ==========================================
  /**
   * Add pack to shopping cart
   */
  onAddToCart(pack: ProfilePack): void {
    if (!this.isPackInCart(pack)) {
      const newCart = [...this.cartPacks, pack];
      this.cartChange.emit(newCart);
      console.log('✅ Pack added to cart:', pack.name);
    }
  }

  /**
   * Remove pack from shopping cart
   */
  onRemoveFromCart(pack: ProfilePack): void {
    const index = this.cartPacks.findIndex(p => p.id === pack.id);
    if (index !== -1) {
      const newCart = this.cartPacks.filter(p => p.id !== pack.id);
      this.cartChange.emit(newCart);
      console.log('🗑️ Pack removed from cart:', pack.name);
    }
  }

  /**
   * Clear all packs from cart
   */
  onClearCart(): void {
    this.cartChange.emit([]);
    console.log('🧹 Cart cleared');
  }

  /**
   * Assign all packs from cart
   */
  onAssignAllFromCart(): void {
    if (this.cartPacks.length > 0) {
      console.log('📦 Assigning all packs from cart:', this.cartPacks.length);
      this.batchAssign.emit([...this.cartPacks]);
    }
  }

  /**
   * Check if pack is in cart
   */
  isPackInCart(pack: ProfilePack): boolean {
    return this.cartPacks.some(p => p.id === pack.id);
  }

  /**
   * Toggle assigned packs section visibility
   */
  toggleAssignedSection(): void {
    this.showAssignedPacks = !this.showAssignedPacks;
  }

  /**
   * Check if pack is selected
   */
  isPackSelected(pack: ProfilePack): boolean {
    return this.selectedPackId === pack.id;
  }

  /**
   * Track by function for performance
   */
  trackByPackId(index: number, pack: ProfilePack): string {
    return pack.id;
  }
}