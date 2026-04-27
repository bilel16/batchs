package com.bna.batches.interfaces.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgenceProgressMessage {
    private String executionId;
    private String structure;
    /** EN_ATTENTE | EN_COURS | TERMINE | ERREUR */
    private String etat;
    private String dateComptable;
    private String instrument;
    private String phase;
    private int progressPercent;
    private String errorMessage;
}
