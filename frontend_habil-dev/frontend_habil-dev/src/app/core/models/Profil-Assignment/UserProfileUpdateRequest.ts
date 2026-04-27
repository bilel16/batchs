import { ProfileOperation } from "./ProfileOperation";

export interface UserProfileUpdateRequest {
  userMatricule: string;
  appCode: string;
  operations: ProfileOperation[];
}