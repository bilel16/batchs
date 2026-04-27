package com.bna.habil.application.dto.statistics;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterimStatsDto {
    private long totalEnAttente;
    private long totalActif;
    private long totalTermine;
    private long totalAnnule;

    public long getTotal() {
        return totalEnAttente + totalActif + totalTermine + totalAnnule;
    }
}
