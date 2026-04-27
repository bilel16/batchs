package com.bna.smile.model.domainecontratcompte.procuration.traitement;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.procuration.model.DetailMandat;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class DetailMandatTrt extends Traitement{
    //private static final Logger logger = Logger.getLogger(DetailMandatTrt.class);

    public DetailMandatTrt() {
    }

    /**
     * Methode permettant de rechercher le detail d'un Mandat dans la BD
     * @param vo : Mandat
     * @return DeatilMandat
     */
    public IValueObject perform(IValueObject vo) {
        Mandat mandat = (Mandat)vo;
        DetailMandat detailMandat = new DetailMandat();
        List list1 = new ArrayList();
        List list2 = new ArrayList();
        this.setCroFlag(false);
        try{
        /* recherche des MandatOperations */
        /*if (!mandat.getCodEtatMand().equalsIgnoreCase("N")) {*/

            for (Iterator it = mandat.getMandatOperations().iterator(); 
                 it.hasNext(); ) {
                MandatOperation mandatOperation = (MandatOperation)it.next();
                if (mandatOperation.getDatFinMaop() == null) {
                    list1.add(mandatOperation);
                }

            }
            detailMandat.setListeMandatOperations(list1);
            /* recherche des MandatPersonnes */

            for (Iterator it1 = mandat.getMandatPersonnes().iterator(); 
                 it1.hasNext(); ) {
                MandatPersonne mandatPersonne = (MandatPersonne)it1.next();
                if (mandatPersonne.getCodEtatMp().equalsIgnoreCase("V")) {
                    list2.add(mandatPersonne);
                }

            }
            detailMandat.setListeMandatPersonnes(list2);
            return (detailMandat);
       /* } else{
        
                return null;
            }*/
        
    }catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur détails mandat ");
            text.append(e.toString());
            erreur.setCode("400");
            erreur.setDescription(text.toString());
            erreur.setKey("détailsMandat");
            detailMandat.addError(erreur);
            logger.error("  Erreur lors de détailsMandatTrt concernant l'agence "+mandat.getCodStrcMand()+" : ", e);
            return (detailMandat);
        }
}
    public void genCroText(ValueObject vo) {
          
         
        }  
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
        
        
    }
}