package com.bna.smile.model.domainecaisse.traitement;

import com.bna.commun.model.MouvementsCaisses;
import com.bna.commun.service.CURService;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecaisse.dao.CaisseDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe qui permet d'inserer un mouvement caisse
 * @author Mdimagh Med Lassaad
 * @since 28/03/2008
 */
public class InsertMouvementCaisseTrt extends Traitement {
    Context context = ContextHandler.getContext();
    
    public InsertMouvementCaisseTrt() {
    }
    
    public  IValueObject perform(IValueObject vo) throws Exception {
    MouvementsCaisses mouvementCaisse =(MouvementsCaisses) vo;
     try {
            
            CaisseDAO caisseDAO =  (CaisseDAO)context.getBean("caisseDAO");
            Long numeroMouvement = caisseDAO.getSequenceMouvementCaisse();
           
            CURService crudService = (CURService)context.getBean("CURService");
            mouvementCaisse.setNumMvtMc(numeroMouvement);
            crudService.create(mouvementCaisse);
            return (mouvementCaisse);
            
         } catch (Exception e) {
         System.out.println(e.toString());
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = 
                 new StringBuffer("Erreur dans InsertMouvementCaisseTrt : ");
             text.append(e.toString());
             erreur.setCode("200");
             erreur.setDescription(text.toString());
             erreur.setKey("caisse");
             mouvementCaisse.addError(erreur);
             return (mouvementCaisse);
         }

         }

         public void genCroText(ValueObject vo) {

         }
}
