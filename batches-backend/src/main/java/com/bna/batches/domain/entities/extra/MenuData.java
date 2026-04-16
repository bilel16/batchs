package com.bna.batches.domain.entities.extra;

import com.bna.batches.domain.beans.ProfilBatches;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class MenuData {
    private String mail;
    private Set<ProfilBatches> profils = new HashSet<>();
}
