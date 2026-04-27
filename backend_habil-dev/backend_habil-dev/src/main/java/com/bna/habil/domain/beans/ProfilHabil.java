package com.bna.habil.domain.beans;


import java.util.HashSet;

import java.util.Set;

import com.bna.habil.domain.entities.extra.Menu;
import com.bna.habil.domain.entities.extra.Ressource;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ProfilHabil {
    private String codeProfil;

    private Set<Ressource> ressources = new HashSet<>();

    private Set<Menu> menus = new HashSet<>();

}
