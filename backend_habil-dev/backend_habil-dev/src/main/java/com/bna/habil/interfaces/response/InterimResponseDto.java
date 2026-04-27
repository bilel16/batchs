package com.bna.habil.interfaces.response;

import com.bna.habil.domain.beans.interim.EtatInterim;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class InterimResponseDto {

    private Long id;

    private Integer matriculeSource;

    private Integer matriculeCible;

    private Date dateDebutInterim;

    private Date dateFinInterim;

    private String codBctDestination;

    private String libMenuMenu;

    private EtatInterim etat;

    private Date dateOperation;
}
