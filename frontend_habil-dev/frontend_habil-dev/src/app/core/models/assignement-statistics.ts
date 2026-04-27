export interface AssignmentStatistics {
  totalManagedUsers: number;
  totalStructures: number;
  totalActiveProfiles: number;
  profileDistribution: { [key: string]: number };
}
