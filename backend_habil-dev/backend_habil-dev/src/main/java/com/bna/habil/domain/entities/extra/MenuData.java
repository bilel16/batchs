package com.bna.habil.domain.entities.extra;

import java.util.HashSet;

import java.util.Set;

import com.bna.habil.domain.beans.ProfilHabil;


public class MenuData {

    private String mail;
    private Set<ProfilHabil> profils = new HashSet<ProfilHabil>();

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<ProfilHabil> getProfils() {
        return profils;
    }

    public void setProfils(Set<ProfilHabil> profils) {
        this.profils = profils;
    }

    public MenuData(String mail, Set<ProfilHabil> profils) {
        super();
        this.mail = mail;
        this.profils = profils;
    }

    public MenuData() {
        super();

    }

}
