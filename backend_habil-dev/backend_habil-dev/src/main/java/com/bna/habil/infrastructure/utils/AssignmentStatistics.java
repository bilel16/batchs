package com.bna.habil.infrastructure.utils;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentStatistics {
    private int totalManagedUsers;
    private int totalStructures;
    private long totalActiveProfiles;
    private Map<String, Integer> profileDistribution = new HashMap<>();
}