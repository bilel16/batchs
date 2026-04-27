export interface PersonnelDto {
  matricule: string;
  active: number | null;
  structureId: number | null;
  structureName?: string;
  structureType?: number;
  cin?: string;
}
