package com.bna.habil.interfaces.request;

import lombok.Data;

import java.util.Date;

@Data
public class ProfileOperation {
    private OperationType type; // ADD, REVOKE, UPDATE
    private String profileCode;
    private Date dateDebut;
    private Date dateFin;
    private Integer etat;
}
