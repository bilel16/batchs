/** Fichier: TypeCatPers.java version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: TypeCatPers
 * package: com.bna.smile.model.souscriptionContratCompte.model
 * Auteur : Boussen Youssef & Kriaa Hatem
 */
package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model;

import com.oxia.fwk.core.ValueObject;

/** Fichier: TypeCatPers.java version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: TypeCatPers
 * package: com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model;
 * Auteur : Boussen Youssef & Kriaa Hatem
 */
public class

TypeCatPers extends ValueObject {

    private String codTperTper;
    private String codCatpCatp;


    public TypeCatPers() {
    }

    public TypeCatPers(String codTperTper, String codCatpCatp) {
        this.codTperTper = codTperTper;
        this.codCatpCatp = codCatpCatp;
    }

    public void setCodTperTper(String codTperTper) {
        this.codTperTper = codTperTper;
    }

    public String getCodTperTper() {
        return codTperTper;
    }

    public void setCodCatpCatp(String codCatpCatp) {
        this.codCatpCatp = codCatpCatp;
    }

    public String getCodCatpCatp() {
        return codCatpCatp;
    }
}
