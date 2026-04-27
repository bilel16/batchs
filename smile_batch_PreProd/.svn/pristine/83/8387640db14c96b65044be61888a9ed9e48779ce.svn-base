package com.bna.smile.model.domainecaisse.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import com.bna.commun.traitements.Traitement;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecaisse.model.SituationDetailCaisseStructureVo;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GenererCrosDetailsSessionsTrt extends Traitement {
    public GenererCrosDetailsSessionsTrt() {
    }
    
    public  IValueObject perform(IValueObject vo) throws Exception {
    
     try {
             
        SituationDetailCaisseStructureVo situationDetailCaisseStructureVo = (SituationDetailCaisseStructureVo)vo; 
        this.setCroFlag(true);
        return situationDetailCaisseStructureVo;
    
     } catch (Exception e) {
         com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
         erreur.setCode("Technique");
         erreur.setDescription("GenererCrosDetailsSessions  "+e.getMessage());;
         logger.error("Exception : ",e);   
         throw new   RuntimeException(e);
     }

    }

    
    public void genCroText(ValueObject vo) {


            SituationDetailCaisseStructureVo situationDetailCaisseStructureVo = (SituationDetailCaisseStructureVo)vo;

              /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

               Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        com.oxia.security.abc.model.Personnel user = null;
                        if (obj instanceof UserDetails) {
                            user = (com.oxia.security.abc.model.Personnel)obj;
                       }
              
              this.setNumRefCro(situationDetailCaisseStructureVo.getDetailSessionCaisse().getNumDscDsc());
              this.setLibRefCro("SMILE.Caisse.CreatCaisVac");
              this.setDatValCro(situationDetailCaisseStructureVo.getJourneeCaisseIn().getJourneeCaisseId().getDatJrnJrn());
              this.setCodeStructInitiatrice(situationDetailCaisseStructureVo.getJourneeCaisseOut().getCaisseStrc().getCaisseStrcId().getCodStrcStrc().toString());              
              this.setCodStrcImpt(situationDetailCaisseStructureVo.getJourneeCaisseIn().getCaisseStrc().getCaisseStrcId().getCodStrcStrc());
              this.setCodEtatCro(0);              
              this.setCodeProduit(Constants.COD_DOM_GUICHET.toString());
              this.setOperationId(String.valueOf(Constants.COD_OPER_OUV_CAISSE_VAC));
              this.setDateOperation(new Date());
              SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
              formater=new SimpleDateFormat("HH:mm:ss");
              String heureString = formater.format(new Date());
              this.setHeureOperation(heureString);
              this.setTypeOperationCro("O");
              this.setCodTachTach(Constants.COD_TACH_OUV_CAISSE_VAC);

              this.setCodRefcOmp(" ");
              this.setDatExecCro(new Date());

              this.setNumCinUser(user.getNumMatrUser());
              this.setCodTypUser(user.getMatriculeTyp());
              
                 /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
            StringBuffer cro=new StringBuffer("");
                
            cro.append("NumCaisCaisOut=");
            cro.append(situationDetailCaisseStructureVo.getJourneeCaisseOut().getCaisseStrc().getCaisseStrcId().getNumCaisCais()+";");               

            cro.append("NumCaisCaisIn=");
            cro.append(situationDetailCaisseStructureVo.getJourneeCaisseIn().getCaisseStrc().getCaisseStrcId().getNumCaisCais()+";");               

            cro.append("CodTypCais=");
            cro.append(situationDetailCaisseStructureVo.getJourneeCaisseOut().getCaisseStrc().getCodTypCais()+";");               

            cro.append("CodNatuDsc=");
            cro.append(situationDetailCaisseStructureVo.getDetailSessionCaisse().getCodNatuDsc()+";");               

            cro.append("Montant=");
            cro.append(situationDetailCaisseStructureVo.getDetailSessionCaisse().getMontDebDsc().longValue() +";");

            cro.append("Devise=");
            cro.append(situationDetailCaisseStructureVo.getDetailSessionCaisse().getDevise().getCodDevDev().longValue() +";");
            
            this.setCroText(cro.toString());

    }
}
