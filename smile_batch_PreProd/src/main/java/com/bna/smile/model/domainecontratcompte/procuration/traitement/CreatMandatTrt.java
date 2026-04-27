package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import com.bna.commun.model.Mandat;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TraceMandat;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.procuration.model.ParamInsertMandat;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class CreatMandatTrt extends Traitement{
   // private static final Logger logger = Logger.getLogger(CreatMandatTrt.class);

    public CreatMandatTrt() {
    }

    public IValueObject perform(IValueObject vo) throws Exception{

        ParamInsertMandat paramInsertMandat=(ParamInsertMandat)vo;
        Mandat mandatRetour= new Mandat();
    try{

            CreationMandatTrt creationMandatTrt = new CreationMandatTrt();
             mandatRetour= (Mandat)creationMandatTrt.exec(paramInsertMandat);
            //* Insertion trace mandat
            if(!mandatRetour.hasError()){
            TraceMandat traceMandat = new TraceMandat();
            traceMandat.setMandat(mandatRetour);
            Personnel personnel = new Personnel();
            personnel.setNumMatrUser(paramInsertMandat.getPersonnel().getNumMatrUser());
            traceMandat.setPersonnel(personnel);
            Operation operation = new Operation();
            operation.setCodOperOper(Constants.COD_OPER_CREAT_MANDAT);

            Tache tache = new Tache();
            TacheId tacheId = new TacheId();
            tacheId.setCodOperOper(Constants.COD_OPER_CREAT_MANDAT);
            // en cas de mineur, la tache du mandat doit être mise à Valide, sinon (cas morale ou incapable ) tache = saisie
            if(paramInsertMandat.getParamInsertContrat() != null && paramInsertMandat.getParamInsertContrat().getContratCpt() !=null && 
               paramInsertMandat.getParamInsertContrat().getContratCpt().getClient().getPersonne().getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_MINEUR)  ){
                  tacheId.setCodTachTach(Constants.COD_TACHE_VALID_MANDAT);
               }else{
                  tacheId.setCodTachTach(Constants.COD_TACHE_SAISIE_MANDAT);
               }
               
            tache.setTacheId(tacheId);
            traceMandat.setTache(tache);

            InsertTraceMandatTrt insertTraceMandatTrt=new InsertTraceMandatTrt();
            TraceMandat traceMandatRetour=(TraceMandat)insertTraceMandatTrt.exec(traceMandat);
            if(traceMandatRetour.hasError()){
               // mandatRetour. =true;
                mandatRetour.setErrors(traceMandatRetour.getErrors());
            }
            
            }else{                  
                return (mandatRetour);
            }
        }  catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur lors de la création du mandat ");
                text.append(e.toString());
                erreur.setCode("302");
                erreur.setDescription(text.toString());
                erreur.setKey("créatMandat");
                mandatRetour.addError(erreur); 
                logger.error("Exception dans CreatMandatTrt concernant l'agence "+mandatRetour.getCodStrcMand()+": ",e);
                throw new RuntimeException(e);  
            }
        return mandatRetour ;   
    }
    
    
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {
        return(Constants.COD_OPER_CREAT_MANDAT.toString()+
        StrHandler.lpad(Constants.COD_TACHE_SAISIE_MANDAT.toString(),'0',2));
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamInsertMandat paramInsertMandat=(ParamInsertMandat)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(paramInsertMandat.getPersonnel().getStructure().getCodStrcStrc());
        return structureDomaine;
    }
    
    
}
