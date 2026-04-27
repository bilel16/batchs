package com.bna.smile.web.commun.forms;

import com.bna.smile.web.commun.view.InitialisationView;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import org.apache.struts.action.ActionForm;

/**classe pour la recherche des mineur devenus majeurs
 * @author Mdimagh med Lassaad
 * @since 23/05/2008
 */
public class ListeMineursDevenusMajeursForm extends ActionForm{

    private InitialisationView initialisationView = new InitialisationView();
    private Long codeStructure;
    private Date dateJour;
    private List listeDesMineursDevenusMajeur = new ArrayList(0);
    
    public ListeMineursDevenusMajeursForm() {
    }

    public void setInitialisationView(InitialisationView initialisationView) {
        this.initialisationView = initialisationView;
    }

    public InitialisationView getInitialisationView() {
        return initialisationView;
    }

    public void setCodeStructure(Long codeStructure) {
        this.codeStructure = codeStructure;
    }

    public Long getCodeStructure() {
        return codeStructure;
    }

    public void setDateJour(Date dateJour) {
        this.dateJour = dateJour;
    }

    public Date getDateJour() {
        return dateJour;
    }

    public void setListeDesMineursDevenusMajeur(List listeDesMineursDevenusMajeur) {
        this.listeDesMineursDevenusMajeur = listeDesMineursDevenusMajeur;
    }

    public List getListeDesMineursDevenusMajeur() {
        return listeDesMineursDevenusMajeur;
    }
}
