package com.bna.smile.model.domainecaisse.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import com.bna.commun.model.MouvementSessionCaisse;
import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GenererCroMouvementTrt extends Traitement {
    public GenererCroMouvementTrt() {
    }
    
    public  IValueObject perform(IValueObject vo) throws Exception {
    
     try {
             
         MouvementSessionCaisse mouvementSessionCaisse = (MouvementSessionCaisse)vo;
         this.setCroFlag(true);
        return mouvementSessionCaisse;
    
     } catch (Exception e) {
         com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
         erreur.setCode("Technique");
         erreur.setDescription("GenererCroMouvementTrt  "+e.getMessage());;
         logger.error("Exception : ",e);   
         throw new   RuntimeException(e);
     }

    }

    
    public void genCroText(ValueObject vo) {


        MouvementSessionCaisse mouvementSessionCaisse = (MouvementSessionCaisse)vo;
        
           /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

               Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        com.oxia.security.abc.model.Personnel user = null;
                        if (obj instanceof UserDetails) {
                            user = (com.oxia.security.abc.model.Personnel)obj;
                       }
              
              this.setNumRefCro(mouvementSessionCaisse.getNumMvtMvtc());
              this.setLibRefCro("SMILE.Caisse.Mvt");
              this.setDatValCro(mouvementSessionCaisse.getDatMvtMvtc());
              this.setCodeStructInitiatrice(mouvementSessionCaisse.getSessionJrnCaisse().getJourneeCaisse().getCaisseStrc().getCaisseStrcId().getCodStrcStrc().toString());              
              this.setCodStrcImpt(mouvementSessionCaisse.getCaisseStrc().getCaisseStrcId().getCodStrcStrc());
              this.setCodEtatCro(0);              
              this.setCodeProduit(Constants.COD_DOM_GUICHET.toString());
              this.setOperationId(mouvementSessionCaisse.getTache().getTacheId().getCodOperOper().toString());
              this.setDateOperation(new Date());
              SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
              formater=new SimpleDateFormat("HH:mm:ss");
              String heureString = formater.format(new Date());
              this.setHeureOperation(heureString);
              this.setTypeOperationCro("O");
              this.setCodTachTach(mouvementSessionCaisse.getTache().getTacheId().getCodTachTach());

              this.setCodRefcOmp(" ");
              this.setDatExecCro(new Date());

              this.setNumCinUser(user.getNumMatrUser());
              this.setCodTypUser(user.getMatriculeTyp());
              
                 /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
            StringBuffer cro=new StringBuffer("");
                
            cro.append("NumCaisCaisOut=");
            cro.append(mouvementSessionCaisse.getSessionJrnCaisse().getJourneeCaisse().getCaisseStrc().getCaisseStrcId().getNumCaisCais()+";");               
     
            cro.append("NumCaisCaisIn=");
            cro.append(mouvementSessionCaisse.getCaisseStrc().getCaisseStrcId().getNumCaisCais()+";");               

            cro.append("CodTypCais=");
            cro.append(mouvementSessionCaisse.getSessionJrnCaisse().getJourneeCaisse().getCaisseStrc().getCodTypCais()+";");               

            cro.append("CodNatuDsc=");
            cro.append(mouvementSessionCaisse.getCodNatdMvtc()+";");               
       
            cro.append("Montant=");
            cro.append(mouvementSessionCaisse.getMontMvtMvtc().longValue() +";");
       
            cro.append("Devise=");
            cro.append(mouvementSessionCaisse.getDevise().getCodDevDev().longValue() +";");
        
            this.setCroText(cro.toString());

    }
}
