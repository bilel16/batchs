/** Fichier: ParamPers.java version 1.0.0 du 19/01/2006
  * Copyright(c) 2006 BNA (www.bna.com.tn)
  * Classe: ParamPers
  * package: com.bna.smile.model.souscriptionContratCompte.model
  * Auteur : Boussen Youssef & Kriaa Hatem
  */
package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model;

import com.oxia.fwk.core.ValueObject;

/** Fichier: ParamPers.java version 1.0.0 du 19/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: ParamPers
 * package: com.bna.smile.model.souscriptionContratCompte.model
 * Auteur : Boussen Youssef & Kriaa Hatem
 */
public class

ParamPers extends ValueObject {

    private Integer numSeqPers;
    private Integer age;
    private String codPaysPays;
    private Integer boolResPers;
    private String codFjFj;
    private String codCatpCatp;
    private String cas="";

    public ParamPers() {
    }


    public void setCodPaysPays(String codPaysPays) {
        this.codPaysPays = codPaysPays;
    }

    public String getCodPaysPays() {
        return codPaysPays;
    }


    public void setCodFjFj(String codFjFj) {
        this.codFjFj = codFjFj;
    }

    public String getCodFjFj() {
        return codFjFj;
    }

    public void setCodCatpCatp(String codCatpCatp) {
        this.codCatpCatp = codCatpCatp;
    }

    public String getCodCatpCatp() {
        return codCatpCatp;
    }

    public void setNumSeqPers(Integer numSeqPers) {
        this.numSeqPers = numSeqPers;
    }

    public Integer getNumSeqPers() {
        return numSeqPers;
    }

    public void setBoolResPers(Integer boolResPers) {
        this.boolResPers = boolResPers;
    }

    public Integer getBoolResPers() {
        return boolResPers;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }

    public String getCas() {
        return cas;
    }
}
