const CODE_STRUCTURE = {
  '0': 'TOUS',
  '1': 'AGENCE',
  '2': 'DIRECTION REGIONALE',
  '3': 'DIRECTION CENTRALE',
  '4': 'DIVISION',
  '5': 'DIRECTION',
  // '6': 'SUCCURSALE',
  '7': 'Box de Change'
} as const;


export const CODE_STRUCTURE_OPTIONS = Object.entries(CODE_STRUCTURE).map(([value, label]) => ({ label, value }));
export const getCodeStructureLabel = (v: string) => CODE_STRUCTURE[v as keyof typeof CODE_STRUCTURE] || v;
