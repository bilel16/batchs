package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.Date;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailCatCpt;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.dao.SequenceDAO;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamDetailCatCpt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.ParamMiseAjourDetailcatCpt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class MiseAJourDetailCatContratTrt extends Traitement{
   
    public MiseAJourDetailCatContratTrt() {
    }
    public IValueObject perform(IValueObject vo) {

            DetailCatCpt detailCatCpt = new DetailCatCpt();
            ContratCpt contratCpt=new ContratCpt();
        try {
             
            Context context = ContextHandler.getContext();
            CRUDservice crudService = (CRUDservice)context.getBean("crudservice");

            SequenceDAO sequenceDAO =(SequenceDAO)context.getBean("sequenceDAO");
            ParamMiseAjourDetailcatCpt paramMiseAjourDetailcatCpt = (ParamMiseAjourDetailcatCpt)vo;
            
            /****mise à jour  de l'actuel detail cat contrat*****/
             detailCatCpt=paramMiseAjourDetailcatCpt.getDetailCatCpt();
             detailCatCpt.setDatFinDcc(new Date());
             crudService.update(detailCatCpt);
             
            /****mise à jour  de la categorie du contrat contrat*****/
             contratCpt=paramMiseAjourDetailcatCpt.getContratCpt();
             contratCpt.setCatCcptCcpt(paramMiseAjourDetailcatCpt.getNouvelleCategorie().getCategorieId().getCodRgmRgm()+
             paramMiseAjourDetailcatCpt.getNouvelleCategorie().getCategorieId().getCodCatCat());
             crudService.update(contratCpt);
             
            /****insertion du nouveau detail cat contrat*****/
            ParamDetailCatCpt paramDetailCatCpt=new ParamDetailCatCpt();
            if (paramMiseAjourDetailcatCpt.getNouvelleCategorie().getCategorieId().getCodRgmRgm().intValue()==0){
                paramMiseAjourDetailcatCpt.getNouvelleCategorie().getCategorieId().setCodRgmRgm(detailCatCpt.getCategorie().getCategorieId().getCodRgmRgm());
            }
            paramDetailCatCpt.setCategorie(paramMiseAjourDetailcatCpt.getNouvelleCategorie());
        
        paramDetailCatCpt.setContratCpt(paramMiseAjourDetailcatCpt.getContratCpt());
            InsertDetailCatContratTrt insertDetailCatContratTrt=new InsertDetailCatContratTrt();
            detailCatCpt=(DetailCatCpt)insertDetailCatContratTrt.exec(paramDetailCatCpt);
            
            
            /*manque MAJ cod_cat_cat dans contrat_ccpt*/
            
            
            return detailCatCpt;
  

     }
              catch (Exception e) {
                  com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                  StringBuffer text = new StringBuffer("Erreur dans MiseAJourDetailCatContratTrt : ");
                  text.append(e.toString());
                  erreur.setCode("200");
                  erreur.setDescription(text.toString());
                  erreur.setKey("MiseAJourDetailCatContrat");
                  detailCatCpt.addError(erreur);
                  logger.error("Exception dans MiseAJourDetailCatContrat concernant l'agence "+contratCpt.getStructure().getCodStrcStrc()+" : ",e);
                  return (detailCatCpt);
              }
    }
    
    public void genCroText(ValueObject vo) {    
    
    }
    
    public String getNumeroTache(IValueObject vo) {

        ParamMiseAjourDetailcatCpt paramMiseAjourDetailcatCpt = (ParamMiseAjourDetailcatCpt)vo;
        
        if (paramMiseAjourDetailcatCpt.getType().equalsIgnoreCase("TC")){// transfert epargne
            return(Constants.COD_OPER_TRANSF_CPT.toString()+
            StrHandler.lpad(Constants.COD_TACH_TRANSF_CPT.toString(),'0',2));
        }else{// changement categorie
            return(Constants.COD_OPER_CHANG_CAT_RGM.toString()+
            StrHandler.lpad(Constants.COD_TACH_CHANG_CAT_RGM.toString(),'0',2));
        }
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        ParamMiseAjourDetailcatCpt paramMiseAjourDetailcatCpt = (ParamMiseAjourDetailcatCpt)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(paramMiseAjourDetailcatCpt.getContratCpt().getStructure().getCodStrcStrc());
        return structureDomaine;
    }
}
