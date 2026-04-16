package com.bna.batches.domain.entities.extra;

import com.bna.batches.domain.beans.ProfilBatches;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AuthData {

    @JsonProperty private String codeStructure;
    @JsonProperty private String typeStructure;
    @JsonProperty private String libStructure;
    @JsonProperty private String nom;
    @JsonProperty private String prenom;
    @JsonProperty private Integer codeBct;
    @JsonProperty private Integer structureMere;
    @JsonProperty private String dateComptable;
    @JsonProperty private String dateJournee;
    @JsonProperty private String mail;
    @JsonProperty private Set<ProfilBatches> profils = new HashSet<>();
    @JsonProperty private Set<String> ressources;
    @JsonProperty private String poste;
    @JsonProperty private String codePoste;
}
