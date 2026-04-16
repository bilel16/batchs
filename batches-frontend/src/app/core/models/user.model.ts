export interface User {
  id?: number;
  username: string;
  nom?: string;
  prenom?: string;
  email?: string;
  codeStructure?: string;
  poste?: string;
  token?: string;
  roles?: string[];
  rolesFull?: any[];
  role?: string;
  password?: string;
}
