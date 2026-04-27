import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { AnimatedDrawerComponent } from '../../../shared/components/animated-drawer/animated-drawer.component';
import { ActionType } from '../../../core/enums/actionType.enum';
import { ConfirmationService, MessageService } from 'primeng/api';
import { StructureWithSegmentsDto} from '../../../core/models/structure';
import {StructureService} from '../../../core/services/backend/structure.service';
import AOS from 'aos';

@Component({
  selector: 'app-structuresegment',
  standalone: false,
  templateUrl: './structuresegment.component.html',
  styleUrl: './structuresegment.component.scss'
})
export class StructuresegmentComponent implements OnInit, AfterViewInit {
  @ViewChild('drawer') drawer!: AnimatedDrawerComponent;

  // Data
  structures: StructureWithSegmentsDto[] = [];
  filteredStructures: StructureWithSegmentsDto[] = [];
  selectedStructure: StructureWithSegmentsDto | null = null;

  // For adding/removing segments
  segmentCode = '';

  // Validation states
  isSegmentInvalid = false;
  isSegmentDuplicate = false;
  segmentValidationMessage = '';

  // UI State
  submitted = false;
  action: ActionType = ActionType.ADD;
  public actionType = ActionType;
  loading = false;

  // Stats
  totalStructures = 0;
  totalSegments = 0;

  tableConfig = {
    columns: [
      {
        field: 'id',
        header: 'ID Structure',
        sortable: true,
        type: 'number',
        minWidth: '3rem',
        width: '8rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'libelleStructure',
        header: 'Libellé Structure',
        sortable: true,
        type: 'text',
        filter: true,
        minWidth: '3rem',
        width: '16rem',
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'codeTypeStructure',
        header: 'Type Structure',
        type: 'number',
        width: '10rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'codeStructureMere',
        header: 'Structure Mère',
        type: 'number',
        width: '10rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'libMailStrc',
        header: 'Email',
        type: 'text',
        width: '14rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      },
      {
        field: 'codCatStrc',
        header: 'Catégorie',
        type: 'text',
        width: '10rem',
        filter: true,
        filterType: 'text',
        filterStyle: { width: '50%' },
      }
    ],
    showActions: true,
    actions: {
      view: true,
      edit: false,
      notification: false,
      send: false,
      delete: false,
    },
    styleAction: {
      'min-width': '4rem',
      width: '5.5rem',
    },
    pagination: true,
    pageSize: 10,
  };

  constructor(
    private structureService: StructureService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
    

    this.updateStats();
  }

  ngAfterViewInit(): void {
        AOS.init({
      easing: 'linear',
      mirror: true
    });
    this.getAllStructuresWithSegments();
  }

  private getAllStructuresWithSegments() {
    this.loading = true;
    this.structureService.getAllStructuresWithSegments().subscribe({
      next: (data) => {
        this.structures = data || [];
        this.filteredStructures = [...this.structures];
        this.updateStats();
        this.loading = false;
      },
      error: (error) => {
        console.error("Failed to load structures with segments", error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: 'Échec du chargement des structures'
        });
        this.structures = [];
        this.filteredStructures = [];
        this.loading = false;
      }
    });
  }

  updateStats(): void {
    this.totalStructures = this.structures.length;
    this.totalSegments = this.structures.reduce((sum, structure) => {
      return sum + (structure.segments ? structure.segments.length : 0);
    }, 0);
  }

  viewStructureSegments(structure: StructureWithSegmentsDto) {
    this.selectedStructure = { ...structure };
    this.segmentCode = '';
    this.resetValidation();
    this.drawer.openDrawer();
  }

  onSegmentCodeChange() {
    this.resetValidation();

    if (!this.segmentCode) {
      return;
    }

    // Validate format: must be a number
    const numericValue = parseInt(this.segmentCode, 10);

    if (isNaN(numericValue) || !/^\d+$/.test(this.segmentCode)) {
      this.isSegmentInvalid = true;
      this.segmentValidationMessage = 'Le code doit être un nombre entier';
      return;
    }

    // Validate range: 0-255
    if (numericValue < 0 || numericValue > 255) {
      this.isSegmentInvalid = true;
      this.segmentValidationMessage = 'Le code doit être entre 0 et 255';
      return;
    }

    // Check for duplicates
    if (this.selectedStructure && this.selectedStructure.segments) {
      const isDuplicate = this.selectedStructure.segments.some(
        segment => segment === this.segmentCode
      );

      if (isDuplicate) {
        this.isSegmentDuplicate = true;
        this.segmentValidationMessage = 'Ce segment est déjà associé à cette structure';
        return;
      }
    }
  }

  resetValidation() {
    this.isSegmentInvalid = false;
    this.isSegmentDuplicate = false;
    this.segmentValidationMessage = '';
  }

  isAddButtonDisabled(): boolean {
    return !this.segmentCode ||
      this.isSegmentInvalid ||
      this.isSegmentDuplicate ||
      this.loading;
  }

  addSegmentToStructure() {
    if (!this.selectedStructure || !this.segmentCode) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Attention',
        detail: 'Veuillez saisir un code segment valide'
      });
      return;
    }

    // Final validation before submission
    const numericValue = parseInt(this.segmentCode, 10);
    if (isNaN(numericValue) || numericValue < 0 || numericValue > 255) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Format Invalide',
        detail: 'Le code segment doit être un nombre entre 0 et 255'
      });
      return;
    }

    // Check for duplicates one more time
    if (this.selectedStructure.segments &&
      this.selectedStructure.segments.includes(this.segmentCode)) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Doublon',
        detail: 'Ce segment est déjà associé à cette structure'
      });
      return;
    }

    this.loading = true;
    this.structureService.addSegmentToStructure(this.selectedStructure.id, this.segmentCode).subscribe({
      next: (result) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Succès',
          detail: 'Segment ajouté avec succès'
        });
        this.segmentCode = '';
        this.resetValidation();
        this.refreshCurrentStructure();
      },
      error: (error) => {
        console.error("Failed to add segment", error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur',
          detail: error.message || 'Échec de l\'ajout du segment'
        });
        this.loading = false;
      }
    });
  }

  deleteSegment(segmentCode: string) {
    if (!this.selectedStructure || !segmentCode) {
      return;
    }

    this.confirmationService.confirm({
      message: `Êtes-vous sûr de vouloir supprimer le segment "${segmentCode}" ?`,
      header: 'Confirmation de suppression',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Oui',
      rejectLabel: 'Non',
      accept: () => {
        this.loading = true;
        this.structureService.removeSegmentFromStructure(this.selectedStructure!.id, segmentCode).subscribe({
          next: () => {
            this.messageService.add({
              severity: 'success',
              summary: 'Succès',
              detail: 'Segment supprimé avec succès'
            });
            this.refreshCurrentStructure();
          },
          error: (error) => {
            console.error("Failed to delete segment", error);
            this.messageService.add({
              severity: 'error',
              summary: 'Erreur',
              detail: 'Échec de la suppression du segment'
            });
            this.loading = false;
          }
        });
      }
    });
  }

  private refreshCurrentStructure() {
    if (!this.selectedStructure) {
      return;
    }

    this.structureService.getStructureWithSegments(this.selectedStructure.id).subscribe({
      next: (data) => {
        this.selectedStructure = data;
        // Update in main list
        const index = this.structures.findIndex(s => s.id === data.id);
        if (index !== -1) {
          this.structures[index] = data;
          this.filteredStructures = [...this.structures];
        }
        this.updateStats();
        this.loading = false;
      },
      error: (error) => {
        console.error("Failed to refresh structure", error);
        this.loading = false;
      }
    });
  }
}
