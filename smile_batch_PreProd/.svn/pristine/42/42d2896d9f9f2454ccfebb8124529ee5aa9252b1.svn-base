
package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import java.util.Date;
import java.util.Iterator;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailRenouvellementMandat;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.procuration.model.ParamInsertMandat;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.InsertClientContratTrt;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe  pour la prise en charge totale de la création d'un mandat
 * ainsi que la création des mandat_personne et des mandat_operation
 * @author BOUSSEN Youssef & MDIMAGH Lassaad
 */
public class CreationMandatTrt extends Traitement{
    //private static final Logger logger = Logger.getLogger(CreationMandatTrt.class);

    public CreationMandatTrt() {
    }

    public IValueObject perform(IValueObject vo) throws Exception{

        ParamInsertMandat paramInsertMandat = (ParamInsertMandat)vo;
        MandatOperation mandatOperation = new MandatOperation();
        MandatPersonne mandatPersonne = new MandatPersonne();
try{
        
        /* insertion des objet Personne, client,Contrat_cpt en cas de souscription qui fait appel à la création d'un mandat  */
        if(paramInsertMandat.getParamInsertContrat() != null ){
            InsertClientContratTrt insertClientContratTrt = new InsertClientContratTrt();
            ContratCpt contratCptSouscription = (ContratCpt)insertClientContratTrt.exec(paramInsertMandat.getParamInsertContrat());  
            if(!contratCptSouscription.hasError()){
              paramInsertMandat.getMandat().setContratCpt(contratCptSouscription);
            }else{                       
                paramInsertMandat.getMandat().addError(contratCptSouscription.getErrors().get(0));   
                return (paramInsertMandat.getMandat());
            }
        } 
        /* ###############################################################################################*/
        /* Insertion de l'objet Mandat*/
        paramInsertMandat.getMandat().setDatCreMand(new Date());
        InsertMandatTrt insertMandatTrt = new InsertMandatTrt();
        paramInsertMandat.setMandat((Mandat)insertMandatTrt.exec(paramInsertMandat.getMandat()));
        
        /* Insertion de l'objet DetailRenouvellementMandat*/
        if (paramInsertMandat.getMandat().getDatFinMand()!=null){
            InsertDetailRenouvellementMandatTrt insertDetailRenouvellementMandatTrt=new InsertDetailRenouvellementMandatTrt();
            DetailRenouvellementMandat detailRenouvellementMandat = (DetailRenouvellementMandat) insertDetailRenouvellementMandatTrt.exec(paramInsertMandat.getMandat());
        }
        /* Insertion des Mandat_Operation*/
        if (paramInsertMandat.getMandat().getCodTypMand().equalsIgnoreCase("S") || paramInsertMandat.getMandat().getCodTypMand().equalsIgnoreCase("JS") ){ /// seulement le cas d'un mandat spécial
            InsertMandatOperationTrt insertMandatOperationTrt = new InsertMandatOperationTrt();
            for (Iterator it = paramInsertMandat.getMandat().getMandatOperations().iterator(); it.hasNext(); ) {
                MandatOperation mandatOperationTemp = (MandatOperation)it.next();
                mandatOperationTemp.setMandat(paramInsertMandat.getMandat());
                mandatOperationTemp.getMandatOperationId().setNumMandMand(paramInsertMandat.getMandat().getNumMandMand());
                mandatOperation = (MandatOperation)insertMandatOperationTrt.exec(mandatOperationTemp);
            }
        }
        /* Insertion des Mandats_Personnes*/
        InsertMandatPersonneTrt insertMandatPersonneTrt = new InsertMandatPersonneTrt();
        for (Iterator it = paramInsertMandat.getMandat().getMandatPersonnes().iterator(); it.hasNext(); ) {
            MandatPersonne mandatPersonneTemp = (MandatPersonne)it.next();
            mandatPersonneTemp.setMandat(paramInsertMandat.getMandat());
            mandatPersonneTemp.getMandatPersonneId().setNumMandMand(paramInsertMandat.getMandat().getNumMandMand());
            mandatPersonne = (MandatPersonne)insertMandatPersonneTrt.exec(mandatPersonneTemp);
        }
         //  System.out.println (1/0);
       
        }  catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur lors de la création du mandat ");
                text.append(e.toString());
                erreur.setCode("300");
                erreur.setDescription(text.toString());
                erreur.setKey("créationMandat");
                paramInsertMandat.getMandat().addError(erreur);
                logger.error("Exception dans CreationMandatTrt concernant l'agence "+mandatPersonne.getMandat().getCodStrcMand()+" : ",e);
                throw new RuntimeException(e);  
               
            }
            finally{
                return (paramInsertMandat.getMandat());                
            }
            
    }
    
    
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
      return (Constants.CODE_RESSOURCE_GENERALE);        
    }
    

}
