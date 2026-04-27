package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model;

import java.util.ArrayList;
import java.util.List;

import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.oxia.fwk.core.ValueObject;
/**
 * value object pour determiner quels sont
 * les contrats à modifier suite à une modification des données cleint
 * @author Mdimagh Med Lassaad
 * @since 4/07/2007
 */
public class ParamListContratsAmodifierVo extends ValueObject {

 private PersonneStrc personneStrc;
 private ParamPers parampers;
 private List listContratAmodifier = new ArrayList();
 
    public ParamListContratsAmodifierVo() {
    
    }

    public void setParampers(ParamPers parampers) {
        this.parampers = parampers;
    }

    public ParamPers getParampers() {
        return parampers;
    }

    public void setListContratAmodifier(List listContratAmodifier) {
        this.listContratAmodifier = listContratAmodifier;
    }

    public List getListContratAmodifier() {
        return listContratAmodifier;
    }

   
    public void setPersonneStrc(PersonneStrc personneStrc) {
        this.personneStrc = personneStrc;
    }

    public PersonneStrc getPersonneStrc() {
        return personneStrc;
    }
}
