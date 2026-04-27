package com.bna.smile.model.debutJournee.traitement;


import java.sql.SQLException;
import java.util.Date;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.DateHandler;
import com.bna.commun.vo.JourneeVo;
import com.bna.smile.model.clotureDomaine.model.OperMoyPayVo;
import com.bna.smile.model.clotureDomaine.model.PramDetailPlacVo;
import com.bna.smile.model.clotureDomaine.traitement.DetailContratPlacementTrt;
import com.bna.smile.model.clotureDomaine.traitement.DetailOperMoyPayTrt;
import com.bna.smile.model.clotureDomaine.traitement.DetailSouscPlacementTrt;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.debutJournee.model.DebutJourneeVo;
import com.bna.smile.model.domainecommun.model.Listes;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class GetDonneeDebJourneeTrt extends Traitement{
    public GetDonneeDebJourneeTrt() {
    }
    public IValueObject perform(IValueObject vo) throws SQLException, 
                                                        Exception {
        
        JourneeVo journeeVo = (JourneeVo)vo;
        DebutJourneeVo debutJourneeVo=new DebutJourneeVo();
       
       
        try {
            /*contrat à liquider ds 7 jours*/
            PramDetailPlacVo pramDetailPlacVo = new PramDetailPlacVo();
            DetailContratPlacementTrt detailContratPlacementTrt=new DetailContratPlacementTrt();  
            pramDetailPlacVo.setCodeStructure(journeeVo.getStructure().getCodStrcStrc());
            pramDetailPlacVo.setDateSup(DateHandler.addJour(journeeVo.getDateJourneeOuverte(),7));
            pramDetailPlacVo.setEtat("V");
            Listes listeContratPlacement = (Listes)detailContratPlacementTrt.exec(pramDetailPlacVo);
            if(listeContratPlacement.getList()!=null && listeContratPlacement.getList().size()>0){
                debutJourneeVo.setContratEchu5Jour(listeContratPlacement.getList());
            }
            /*contrat arrivé à écheance*/
            PramDetailPlacVo pramDetailPlacVo1 = new PramDetailPlacVo();
            pramDetailPlacVo1.setCodeStructure(journeeVo.getStructure().getCodStrcStrc());
            pramDetailPlacVo1.setDateEcheance(journeeVo.getDateJourneeOuverte());
            pramDetailPlacVo1.setEtat("V");
            Listes listeContratPlacement1 = (Listes)detailContratPlacementTrt.exec(pramDetailPlacVo1);
            if(listeContratPlacement1.getList()!=null && listeContratPlacement1.getList().size()>0){
                 debutJourneeVo.setContratArriveeAEche(listeContratPlacement1.getList());
             }
             
            /*souscription en attente*/
             PramDetailPlacVo pramDetailPlacVo2 = new PramDetailPlacVo();
             DetailSouscPlacementTrt detailSouscPlacementTrt=new DetailSouscPlacementTrt();
             pramDetailPlacVo2.setEtat("V");
             pramDetailPlacVo2.setTypedem("S");
             pramDetailPlacVo2.setCodeStructure(journeeVo.getStructure().getCodStrcStrc());
             Listes listeDemandeDecision = (Listes)detailSouscPlacementTrt.exec(pramDetailPlacVo2);
             if(listeDemandeDecision.getList()!=null && listeDemandeDecision.getList().size()>0){
                     debutJourneeVo.setListeSouscAttVAl(listeDemandeDecision.getList());
                 }
            /*liquidation Batch*/
             PramDetailPlacVo pramDetailPlacVo3 = new PramDetailPlacVo();
             pramDetailPlacVo3.setEtat("L");
             //pramDetailPlacVo3.setDateLiquidation(journeeVo.getDateJourneeOuverte());
             pramDetailPlacVo3.setCodeStructure(journeeVo.getStructure().getCodStrcStrc());
             pramDetailPlacVo3.setDateInfLiq(journeeVo.getDateJourneeOuverte());
             pramDetailPlacVo3.setDateSupLiq(new Date());
             Listes listeContartBatch = (Listes)detailContratPlacementTrt.exec(pramDetailPlacVo3);
             if(listeContartBatch.getList()!=null && listeContartBatch.getList().size()>0){
                    debutJourneeVo.setLiquidTraitSoir(listeContartBatch.getList());
                }  
                
                
             /*ineret versés*/
              OperMoyPayVo operMoyPayVo =new OperMoyPayVo();
              DetailOperMoyPayTrt detailOperMoyPayTrt=new DetailOperMoyPayTrt();
              operMoyPayVo.setCodOperOpm(Constants.COD_OPER_VERSEMENT_INTERET_PLAC_POST);
              operMoyPayVo.setCodStrcStrc(journeeVo.getStructure().getCodStrcStrc());
              operMoyPayVo.setDateOperOpm(journeeVo.getDateJourneeOuverte());
              Listes listeInertetBatch =(Listes)detailOperMoyPayTrt.exec(operMoyPayVo);
              if(listeInertetBatch.getList()!=null && listeInertetBatch.getList().size()>0){
                  debutJourneeVo.setIntPartVerseSoir(listeInertetBatch.getList());
                 }  
            /*renouvellement Batch*/
              OperMoyPayVo operRenBatch =new OperMoyPayVo();
              operRenBatch.setCodOperOpm(Constants.OPER_RENOUVEL_PLAC_AVAN);
              operRenBatch.setCodStrcStrc(journeeVo.getStructure().getCodStrcStrc());
              operRenBatch.setDateOperOpm(journeeVo.getDateJourneeOuverte());
              Listes listeRentBatch =(Listes)detailOperMoyPayTrt.exec(operRenBatch);
              if(listeRentBatch.getList()!=null && listeRentBatch.getList().size()>0){
                  debutJourneeVo.setListeRenouvBatch(listeRentBatch.getList());
              }  
            /*renouvellement en attente*/
             PramDetailPlacVo pramDetailPlacVo4 = new PramDetailPlacVo();
             pramDetailPlacVo4.setEtat("AR");
             pramDetailPlacVo4.setCodeStructure(journeeVo.getStructure().getCodStrcStrc());
             Listes listeRenAttente = (Listes)detailContratPlacementTrt.exec(pramDetailPlacVo4);
             if(listeRenAttente.getList()!=null && listeRenAttente.getList().size()>0){
                    debutJourneeVo.setListeRenouvAtt(listeRenAttente.getList());
                }  
            
           
            
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("GetDonneeDebJourneeTrt " + e.getMessage());
            debutJourneeVo.addError(erreur);
            throw new Exception(e);
        }                                                
        return debutJourneeVo;                                                         
    }
    public void genCroText(ValueObject vo) {

    }
}
