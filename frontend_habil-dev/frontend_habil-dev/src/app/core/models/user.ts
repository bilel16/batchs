import { Role } from "./role"

export class User {
    id: any;
    username: any;
    password: any;
    prenom: any;
    nom: any;
    role: any;
    token?: any;
    codeStructure:any;
    roles: any; //To be remove 
    email:any;
    poste:any;
    rolesFull:any
}
export class AuthResponse {
    id: any;
    username: any;
    password: any;
    prenom: any;
    nom: any;
    role: any;
    token?: any;
    codeStructure:any;
    roles: any; //To be remove 
}
