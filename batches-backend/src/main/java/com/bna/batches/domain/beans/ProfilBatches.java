package com.bna.batches.domain.beans;

import com.bna.batches.domain.entities.extra.Menu;
import com.bna.batches.domain.entities.extra.Ressource;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ProfilBatches {
    private String codeProfil;
    private Set<Ressource> ressources = new HashSet<>();
    private Set<Menu> menus = new HashSet<>();
}
