package com.bna.batches.interfaces.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationResultDto {
    private String structure;
    private String libStructure;
    private String instrument;
    private String sens;
    private String valeur;
    private String dateOperation;
    private long nombreTotal;
    private long montantTotal;
    private long nombreIntra;
    private long montantIntra;
    private long nombreInter;
    private long montantInter;
}
