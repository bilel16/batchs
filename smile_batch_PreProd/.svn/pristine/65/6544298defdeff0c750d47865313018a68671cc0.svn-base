package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;

import java.util.Date;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model.ParamModificationDonneesVo;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.traitement.ModifierDonneesClientTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.GestionEpargneVO;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class GestionEpargneTrt extends Traitement{
    //private static final Logger logger = Logger.getLogger(ValidModifMandTrt.class);
    public GestionEpargneTrt() {
    }
    
    /**
     * Cette  methode permet de traiter le transfert d'epargne
     * et le changement de categorie en une seule transaction
     * @param (GestionEpargneVO) IValueObject
     * @return IValueObject
     * @autor BOUSSEN Youssef
     */
    public IValueObject perform(IValueObject vo)  {
     
        ValueObject VO = new ValueObject();
        try {

            GestionEpargneVO gestionEpargneVO= (GestionEpargneVO)vo;


            if (!VO.hasError()) {
    //             MiseAJourDetailCatContratCmd miseAJourDetailCatContratCmd = new MiseAJourDetailCatContratCmd();
    //             DetailCatCpt detailCatCpt = (DetailCatCpt)miseAJourDetailCatContratCmd.execute(gestionEpargneVO.getParamMiseAjourDetailcatCpt());

             //    ModifierDonneesClientCmd modifierDonneesClientCmd = new ModifierDonneesClientCmd();
             //    VO = modifierDonneesClientCmd.execute(gestionEpargneVO.getParamModificationDonneesVo());
              
                 ModifierDonneesClientTrt modifierDonneesClientTrt = new ModifierDonneesClientTrt();
                 modifierDonneesClientTrt.setSecurityFlag(false);
                 VO = (ParamModificationDonneesVo)modifierDonneesClientTrt.exec (gestionEpargneVO.getParamModificationDonneesVo());

                 if (gestionEpargneVO.getType().equalsIgnoreCase("TC")){
                     MAJContratClientTransfertEpargneTrt mAJContratClientTransfertEpargneTrt = new MAJContratClientTransfertEpargneTrt();
                     VO =(ValueObject) mAJContratClientTransfertEpargneTrt.exec(gestionEpargneVO.getParamInsertContrat());
                 }
             } 
             
            this.sychronisationPascal(gestionEpargneVO); 


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text =new StringBuffer("Erreur lors de la GestionEpargneTrt ");
            text.append(e.toString());
            erreur.setCode("309");
            erreur.setDescription(text.toString());
            erreur.setKey("GestionEpargneTrt");
            VO.addError(erreur);
//            throw new RuntimeException(e);  

        }
        return VO;

    }


        public void genCroText(ValueObject vo) {
        }
        
        public String getNumeroTache(IValueObject vo){
        
            GestionEpargneVO gestionEpargneVO= (GestionEpargneVO)vo;
            
            if (gestionEpargneVO.getType().equalsIgnoreCase("TC")){// transfert epargne
                return(Constants.COD_OPER_TRANSF_CPT.toString()+
                StrHandler.lpad(Constants.COD_TACH_TRANSF_CPT.toString(),'0',2));
            }else{// changement categorie
                return(Constants.COD_OPER_CHANG_CAT_RGM.toString()+
                StrHandler.lpad(Constants.COD_TACH_CHANG_CAT_RGM.toString(),'0',2));
            }
            
        }
 

    public void genererSynchronisationPascal(ValueObject vo) { 
    
        GestionEpargneVO gestionEpargneVO = (GestionEpargneVO)vo;
        String nomPrenom ="";
        
       // DateFormat myformat = new SimpleDateFormat("ddMMyy");
        /*partie fixe*/
         if (gestionEpargneVO.getType().equalsIgnoreCase("TC")){// transfert epargne
            this.setCodeOperationSynch(Constants.COD_OPER_TRANSF_CPT);
            this.setCodeTacheSynch(Constants.COD_TACH_TRANSF_CPT);
            nomPrenom =StrHandler.rpad(gestionEpargneVO.getParamMiseAjourDetailcatCpt().getContratCpt().getClient().getPersonne().getNomNomPers(),' ',20) 
                             +StrHandler.rpad(gestionEpargneVO.getParamMiseAjourDetailcatCpt().getContratCpt().getClient().getPersonne().getNomPrnPers(),' ',20) ;         
        }else{// changement categorie
          this.setCodeOperationSynch(Constants.COD_OPER_CHANG_CAT_RGM);
          this.setCodeTacheSynch(Constants.COD_TACH_CHANG_CAT_RGM);
         }

        this.setDateOperationSynch(new Date());
        this.setCodeStructureSynch(gestionEpargneVO.getParamModificationDonneesVo().getCodeStructure());
        
        /*partie variable*/
        String partieVariable ="";
        String numCompte = StrHandler.lpad(gestionEpargneVO.getParamModificationDonneesVo().getContratModifie().getContratCptId().getCodPrdPrd().toString(),'0',4) +
                           StrHandler.lpad(gestionEpargneVO.getParamModificationDonneesVo().getContratModifie().getContratCptId().getNumCcptCcpt().toString(),'0',6);
       
        String rgm = StrHandler.lpad(gestionEpargneVO.getParamMiseAjourDetailcatCpt().getNouvelleCategorie().getCategorieId().getCodRgmRgm().toString(),'0',2);
        String cat = gestionEpargneVO.getParamMiseAjourDetailcatCpt().getNouvelleCategorie().getCategorieId().getCodCatCat();
        String lidep ="";
        
        if (gestionEpargneVO.getParamModificationDonneesVo().getContratModifie().getContratCptId().getCodPrdPrd().intValue()==Constants.COD_PRD_PRD_PEE){
            lidep = "N"+rgm+cat; 
        }else{
            if(gestionEpargneVO.getParamModificationDonneesVo().getContratModifie().getContratCptId().getCodPrdPrd().intValue()==Constants.COD_PRD_PRD_PEL){
                lidep = StrHandler.lpad(cat+rgm,' ',4);
            }else lidep = StrHandler.lpad(cat,' ',4);
        }
        partieVariable=partieVariable+numCompte+nomPrenom+lidep;
                             
        System.out.println(partieVariable);
        this.setTextSynch(partieVariable);
    }  
    
    

}
