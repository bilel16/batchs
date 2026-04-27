package com.bna.habil.application.dto;

import com.bna.habil.domain.beans.interim.EtatInterim;
import lombok.Data;

import java.util.Date;

@Data
public class InterimDto {

    private Long id;

    private Integer matriculeSource;

    private Integer matriculeCible;

    private Date dateDebutInterim;

    private Date dateFinInterim;

    private Integer codStrcDestination;

    private Integer codStrcOrigine;

    private EtatInterim etat;

    private Date dateOperation;
}