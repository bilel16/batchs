import {ProfileAssignmentData} from "./profile-assigment";

export interface UserProfilesAssignmentRequest {
  userMatricule: string;
  appCode: string;
  assignedProfiles?: ProfileAssignmentData[];
  revokedProfiles?: string[];
}