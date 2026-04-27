package com.bna.habil.interfaces.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignedProfile {
    private String profileCode;
    private Date dateDebut;
    private Date dateFin;
    private Integer etat;
}