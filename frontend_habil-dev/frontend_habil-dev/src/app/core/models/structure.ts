export interface StructureDto {
  id: number;
  libelleStructure: string;
  codeTypeStructure: number;
  codeStructureMere: number;
  libMailStrc: string;
  codCatStrc: string;
}

export interface SegmentDto {
  codeClasseSegment: string;
  codeSousClasseSegment: number;
  codeSegment: number;
  libelleSegment: string;
  dateMiseJour: Date;
}

export interface StructureWithSegmentsDto {
  id: number;
  libelleStructure: string;
  codeTypeStructure: number;
  codeStructureMere: number;
  libMailStrc: string;
  codCatStrc: string;
  segments: string[];
}

export interface SegmentStructureDto {
  codStrcStrc: number;
  codIpSegs: string;
}
