/**
 * User Role Models
 * For user role management
 */
export interface UserRoleDTO {
  codPflPfl: string;
  libPfl: string;
  hasAccess: boolean;
}

export interface RoleUpdateRequest {
  roles: UserRoleDTO[];
}