package com.bna.habil.application.dto.filters;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HrPersonnelFilterDto {

    /**
     * General search term - searches across matcle, prenom, nomuse
     */
    private String search;

    /**
     * Exact CIN filter
     */
    private String cin;

    /**
     * Exact or partial MATCLE filter
     */
    private String matcle;

    /**
     * Partial PRENOM filter (for individual field search)
     */
    private String prenom;

    /**
     * Partial NOMUSE filter (for individual field search)
     */
    private String nomuse;

    /**
     * Check if any search criteria is applied
     */
    public boolean hasAnyCriteria() {
        return isNotEmpty(search) || isNotEmpty(cin) ||
                isNotEmpty(matcle) || isNotEmpty(prenom) || isNotEmpty(nomuse);
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}