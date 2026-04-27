package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import org.apache.log4j.Logger;

import com.bna.commun.model.MandatPersonne;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;


public class GetMandatPersonneByIdTrt extends Traitement{

    
    private static final Logger logger = Logger.getLogger(GetMandatPersonneByIdTrt.class);

    public GetMandatPersonneByIdTrt() {
    }


        public IValueObject perform(IValueObject vo) {
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");

            MandatPersonne mandatPersonne = (MandatPersonne)vo;
            MandatPersonne mandatPersonneRetour=new MandatPersonne();
            this.setCroFlag(false);
            try{
            /* Charger le mandatPersonne existante */
             mandatPersonneRetour =(MandatPersonne) searchEngine.get(MandatPersonne.class,mandatPersonne.getMandatPersonneId() );
            return (mandatPersonneRetour);
            }catch (Exception e) {
                    com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                    StringBuffer text = 
                        new StringBuffer("Erreur GetMandatPersonneByIdTrt ");
                    text.append(e.toString());
                    erreur.setCode("400");
                    erreur.setDescription(text.toString());
                    erreur.setKey("GetMandatPersonneByIdTrt");
                    mandatPersonneRetour.addError(erreur);
                    logger.error(" *** Erreur lors de GetMandatPersonneByIdTrt concernant l'agence "+mandatPersonne.getMandat().getCodStrcMand()+" : ", e);
                    return (mandatPersonneRetour);
                }
        }
        public void genCroText(ValueObject vo) {
              
             
            } 
        public String getNumeroTache(ValueObject vo) {
            return (Constants.CODE_RESSOURCE_GENERALE);    
            
            
        }
    

}
