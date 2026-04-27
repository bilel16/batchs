package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model;

import java.awt.image.BufferedImage;
import java.util.Date;

import com.bna.smile.model.domainecommun.model.ContratPersonne;

/** Fichier: SignaturePersCpt.java version 1.0.0 du 28/05/2007
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: SignaturePersCpt
 * package: com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model
 * Auteur : Ramzi
 */
public class SignaturePersCpt extends com.oxia.fwk.core.ValueObject implements java.io.Serializable{

    private ContratPersonne contratPersonne;
    private BufferedImage bufferedImageFr;
    private BufferedImage bufferedImageAr;
    private Date    dateModification;
    private Long numMatricule;


    public SignaturePersCpt() {
    }

    public void setContratPersonne(ContratPersonne contratPersonne) {
        this.contratPersonne = contratPersonne;
    }

    public ContratPersonne getContratPersonne() {
        return contratPersonne;
    }


    public void setBufferedImageFr(BufferedImage bufferedImageFr) {
        this.bufferedImageFr = bufferedImageFr;
    }

    public BufferedImage getBufferedImageFr() {
        return bufferedImageFr;
    }

    public void setBufferedImageAr(BufferedImage bufferedImageAr) {
        this.bufferedImageAr = bufferedImageAr;
    }

    public BufferedImage getBufferedImageAr() {
        return bufferedImageAr;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setNumMatricule(Long numMatricule) {
        this.numMatricule = numMatricule;
    }

    public Long getNumMatricule() {
        return numMatricule;
    }
}
