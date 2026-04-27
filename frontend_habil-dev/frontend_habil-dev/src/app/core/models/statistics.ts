export interface StructurePersonnelCount {
  structureId: number;
  total: number;
  active: number;
  inactive: number;
}

export interface PersonnelStats {
  total: number;
  active: number;
  inactive: number;
  perStructure: StructurePersonnelCount[];
}

export interface ApplicationMenuCount {
  applicationCode: string;
  applicationLabel: string;
  menuCount: number;
}

export interface ApplicationStats {
  totalApplications: number;
  totalMenus: number;
  perApplication: ApplicationMenuCount[];
}

export interface ApplicationProfileCount {
  applicationCode: string;
  applicationLabel: string;
  totalProfiles: number;
  activeProfiles: number;
  inactiveProfiles: number;
}

export interface ProfileStats {
  totalProfiles: number;
  activeProfiles: number;
  inactiveProfiles: number;
  perApplication: ApplicationProfileCount[];
}

export interface ResponseSaga<T> {
  returnCode: number;
  message: string;
  data: T;
}
