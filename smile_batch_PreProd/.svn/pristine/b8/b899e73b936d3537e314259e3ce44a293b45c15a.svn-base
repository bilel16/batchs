package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.util.Iterator;
import java.util.Set;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.DemandeCarteMandatPersonne;
import com.bna.commun.model.Mandat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.dao.DemandeCarteDAO;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.dao.MandatDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;


/**
 * extraire demande carte selon numéro demande et retourne 
 * les erreur applicatifs suivantes: contrat non valide, Fin pouvoir sur cet opération
 * @author Ramzi
 * @param PrimitiveVO: String numéro de la demande carte 
 * @return DemandeCarte
 * @since 21/06/2007
 * 
 */
public class GetDemandeCarteTrt  extends Traitement{
    public GetDemandeCarteTrt() {
    }

    public IValueObject perform(IValueObject vo) throws Exception{
        PrimitiveVO  primitiveVO  = (PrimitiveVO )vo;
        DemandeCarte demandeCarte = new DemandeCarte();
        try {
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
           
           
            demandeCarte = (DemandeCarte)searchEngine.get(DemandeCarte.class, primitiveVO.getVString());
            
            //verifier si contrat valide
            ContratCpt contratCpt = demandeCarte.getContratCpt();
            if(!contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)){
                com.oxia.fwk.core.Error erreurContratInvalide = new com.oxia.fwk.core.Error();
                erreurContratInvalide.setCode("ContratInValide");
                //erreurContratValide.setDescription("Le contrat sur cet demande n'est plus valide.");;
                demandeCarte.addError(erreurContratInvalide);
                return demandeCarte;
            }
            
            //verifier pour le cas ou le demandeur est un mandataire la validité du pouvoir du porteur
            if(demandeCarte.getCodDemDcar().equals(Constants.COD_DEM_DCAR_Mandataire)){
                DemandeCarteDAO  demandeCarteDAO = (DemandeCarteDAO)context.getBean("demandeCarteDAO");
                Long numMandat = demandeCarteDAO.getNumeroMandatParDemande(demandeCarte.getNumDemDcar());
                
                Mandat mandat = new Mandat();
                GetMandatCmd getMandatCmd = new GetMandatCmd();
                mandat.setNumMandMand(numMandat);
                mandat =  (Mandat)getMandatCmd.execute(mandat);
                
                //si mandat non valide ou fin mandat
                if (!mandat.getCodEtatMand().equals(Constants.COD_ETAT_MAND_VALID) || ( mandat.getDatFinMand()!= null && mandat.getDatFinMand().before(DateHandler.strToDate(DateHandler.dateJour())))) {
                    com.oxia.fwk.core.Error erreurMandatInvalide = new com.oxia.fwk.core.Error();
                    erreurMandatInvalide.setCode("MandatInvalide");
                    //erreurContratValide.setDescription("Le contrat sur cet demande n'est plus valide.");;
                    demandeCarte.addError(erreurMandatInvalide);
                    return demandeCarte;
                }
                 
                 // verification de la validité de l'opération demande carte pour une mandat de type spécial
                  MandatDAO  mandatDAO = (MandatDAO)context.getBean("mandatDAO");
                  if(mandat.getCodTypMand().equals(Constants.COD_TYPE_MAND_SPECIAL)){
                    if(mandatDAO.verifierMandatOpartion(mandat.getNumMandMand(),Constants.COD_OPER_OPER_PECDemandeCarte)){
                        com.oxia.fwk.core.Error erreurOperMandatInvalide = new com.oxia.fwk.core.Error();
                        erreurOperMandatInvalide.setCode("OperationInvalide");
                        //erreurContratValide.setDescription("Le contrat sur cet demande n'est plus valide.");;
                        demandeCarte.addError(erreurOperMandatInvalide);
                        return demandeCarte;
                    }
                  }
                  
                  // verification de la validité des mandataires
                  Set listeDemCarteMandatPersonne = demandeCarte.getDemandeCarteMandatPersonnes();
                  Iterator iterator = listeDemCarteMandatPersonne.iterator();
                  for(;iterator.hasNext();){
                      DemandeCarteMandatPersonne demandeCarteMandatPersonne = (DemandeCarteMandatPersonne) iterator.next();
                      if(!demandeCarteMandatPersonne.getMandatPersonne().getCodEtatMp().equals(Constants.COD_ETAT_MAND_PERSONNE_VALID)){
                          com.oxia.fwk.core.Error erreurMandataireInvalide = new com.oxia.fwk.core.Error();
                          erreurMandataireInvalide.setCode("MandataireInvalide");
                          //erreurContratValide.setDescription("Le contrat sur cet demande n'est plus valide.");;
                          demandeCarte.addError(erreurMandataireInvalide);
                          return demandeCarte;  
                      }
                  }
            }  

        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("GetDemandeCarteTrt "+e.getMessage());;
                demandeCarte.addError(erreur); 
                logger.error("Exception : ",e);
                throw new RuntimeException(e);       
        }
        return demandeCarte;
    }
    public void genCroText(ValueObject vo) {
    
    }
    public String getNumeroTache(IValueObject vo){
       return Constants.CODE_RESSOURCE_GENERALE;   
    }
   
}
