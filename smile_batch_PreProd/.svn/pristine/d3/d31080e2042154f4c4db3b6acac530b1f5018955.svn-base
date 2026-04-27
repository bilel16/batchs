package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AbonnementPlacement;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.InteretServi;
import com.bna.commun.model.MouvementInterne;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;

import com.bna.smile.model.domaineplacement.dao.PlacementDAO;
import com.bna.smile.model.domaineplacement.model.ParamInsertInteret;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import com.oxia.fwk.util.DateHandler;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import java.util.GregorianCalendar;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

public class ExtourneInteretServiTrt extends Traitement{
    public ExtourneInteretServiTrt() {
    }
    public IValueObject perform (IValueObject vo ) {
            
        try {   
                //insertion dan la table mouvement interne pour historique
                ParamInsertInteret paramInsertInteret = (ParamInsertInteret)vo;
                OperationMoyPay operationMoyPay = paramInsertInteret.getOperationMoyPay();
                MouvementInterne mouvementInterne = new MouvementInterne();
                
                mouvementInterne.setCodRefmMvti(paramInsertInteret.getInteretServi().getNumIsrvIsrv().toString());
                mouvementInterne.setDatOperMvti(operationMoyPay.getDatOperOmp());
                mouvementInterne.setDatSystMvti(new Date());
                mouvementInterne.setDatValMvti(operationMoyPay.getDatValOmp());
                mouvementInterne.setLibMotfMvti("smile.placement.extourne.abonnement.Servi");
                mouvementInterne.setMontMvtiMvti(calculMntExtourneAnnuel(paramInsertInteret.getInteretServi()));
                Structure strc = new Structure();
                strc.setCodStrcStrc(paramInsertInteret.getInteretServi().getContratPlacement().getContratCpt().getStructure().getCodStrcStrc());
                Tache tache = new Tache();
                TacheId tacheId = new TacheId();
                tacheId.setCodTachTach(Long.valueOf("1"));
                tacheId.setCodOperOper(Constants.COD_OPER_ABONNE_EXTOURN_PLAC);
                tache.setTacheId(tacheId);
                Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                com.oxia.security.abc.model.Personnel user = null;
                if (obj instanceof UserDetails) {
                     user = (com.oxia.security.abc.model.Personnel)obj;
                }
                mouvementInterne.setTache(tache);
                Personnel pers = new Personnel();
                pers.setNumMatrUser(user.getNumMatrUser());
                mouvementInterne.setPersonnel(pers);
                mouvementInterne.setStructure(strc);
                InsertMouvementInterneTrt insertMouvementInterneTrt = new InsertMouvementInterneTrt();
                mouvementInterne = (MouvementInterne)insertMouvementInterneTrt.exec(mouvementInterne);
            
                
                this.setCroFlag(true); 
                return null;
            
            }catch (Exception e) {
                throw new RuntimeException();
            }   
        }
        
    public void genCroText(ValueObject vo) {
            
            ParamInsertInteret paramInsertInteret = (ParamInsertInteret)vo;
            OperationMoyPay operationMoyPay = paramInsertInteret.getOperationMoyPay();
            InteretServi interetServi =new InteretServi();
            interetServi = paramInsertInteret.getInteretServi();
            
              /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

               Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        com.oxia.security.abc.model.Personnel user = null;
                        if (obj instanceof UserDetails) {
                            user = (com.oxia.security.abc.model.Personnel)obj;
                       }
              
              this.setNumRefCro(Long.valueOf((operationMoyPay.getNumOperOmp())));
              this.setLibRefCro("smile.placement.extourne.abonnement.Servi");
              this.setDatValCro(operationMoyPay.getDatValOmp());
              this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());              
              this.setCodStrcImpt(operationMoyPay.getStructureInitiatrice().getCodStrcStrc());
              this.setCodEtatCro(0);              
              this.setCodeProduit(operationMoyPay.getProduit().getCodPrdPrd().toString()); // 
              this.setOperationId(Constants.COD_OPER_ABONNE_EXTOURN_PLAC.toString());
              this.setDateOperation(operationMoyPay.getDatOperOmp());
              SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
              formater=new SimpleDateFormat("HH:mm:ss");
              String heureString = formater.format(new Date());
              this.setHeureOperation(heureString);
              this.setDatExecCro(operationMoyPay.getDatSystOmp()); // date system
              this.setTypeOperationCro("O");
              this.setCodTachTach(1);
              this.setCodRefcOmp(operationMoyPay.getNumOperOmp());
              this.setNumCinUser(user.getNumMatrUser());
              this.setCodTypUser(user.getMatriculeTyp());
             
                 /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
              StringBuffer cro=new StringBuffer("");
              StringBuffer contratCPT =new StringBuffer("");
                 // contratClient
                 contratCPT.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3));
                 contratCPT.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4));
                 contratCPT.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6));
                 contratCPT.append(";");
                 cro.append("numCptBna=");
                 cro.append(contratCPT.toString());   
            //contrat placement 
            cro.append("CONTRAT_PLACEMENT.NUM_SEQ_CPLA=");
            cro.append(interetServi.getContratPlacement().getNumSeqCpla() +";");
            
            // BC
            if (operationMoyPay.getNumMoypOmp()!= null){
                    // categorie personne cas du BC/CAT
                    cro.append("CONTRAT_PLACEMENT.NUM_BC_CPLA=");
                    cro.append(operationMoyPay.getNumMoypOmp() +";");
            }
            
            Long montSintAbpl = calculMntExtourneAnnuel(interetServi);
            
            cro.append("ABONNEMENT_PLACEMENT.MONT_SINT_ABPL_619=");
            cro.append(montSintAbpl +";");
             
            this.setCroText(cro.toString());
        }

    private Long calculMntExtourneAnnuel(InteretServi interetServi) {
        //recherche de la somme de l'abonnement pour l'année precedante
        Context context = ContextHandler.getContext();
        PlacementDAO plcDao= (PlacementDAO)context.getBean("placementDAO");
        
        GregorianCalendar calendar = new java.util.GregorianCalendar(); 
        // recalcule de la date prochain interet à servir avant modif 
        calendar.setTime(interetServi.getContratPlacement().getDatPintCpla()); 
        calendar.add(Calendar.DATE, -365);
        
        Long montSintAbpl = plcDao.getSommeAbonAnnee(interetServi.getContratPlacement().getNumSeqCpla().toString(),DateHandler.dateToStr(calendar.getTime()));
        if(montSintAbpl<=0)
        {
        	calendar.add(Calendar.DATE, -1);
            montSintAbpl = plcDao.getSommeAbonAnnee(interetServi.getContratPlacement().getNumSeqCpla().toString(),DateHandler.dateToStr(calendar.getTime()));
        }
        else
        	if(montSintAbpl<=0)
            {
            	calendar.add(Calendar.DATE, +2);
                montSintAbpl = plcDao.getSommeAbonAnnee(interetServi.getContratPlacement().getNumSeqCpla().toString(),DateHandler.dateToStr(calendar.getTime()));
            }
        return montSintAbpl;
    }  
        
}
