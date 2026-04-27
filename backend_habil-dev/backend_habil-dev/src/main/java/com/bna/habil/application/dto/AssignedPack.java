package com.bna.habil.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignedPack {
    private String packCode;
    private Date dateDebut;
    private Date dateFin;
    private Integer etat;
}