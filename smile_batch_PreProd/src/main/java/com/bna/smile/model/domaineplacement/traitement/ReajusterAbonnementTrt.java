package com.bna.smile.model.domaineplacement.traitement;

import com.bna.commun.model.AbonnementPlacement;
import com.bna.commun.model.AvancRembLiquid;
import com.bna.commun.model.ContratPlacement;
import com.bna.commun.model.DetailsOperationPlacement;
import com.bna.commun.model.MandPersOperPlac;
import com.bna.commun.model.MouvementInterne;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.ReajustAvrembliq;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;

import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domaineplacement.model.ParamAbonnementement;

import com.bna.smile.model.domaineplacement.model.ParamLiquidation;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

public class ReajusterAbonnementTrt extends Traitement{
    public ReajusterAbonnementTrt() {
    }
    
    /**
     * methode qui réajuste et genere le tableau des abonnements suite au Remboursement anticipée - à écheance d'un 
     * un placement donné 
     * @param vo : ParamAbonnementement
     * @return   : ParamAbonnementement
     * @autor    : BOUSSEN Youssef  
     * @date     : 12/08/2009
     */
    
    public IValueObject perform(IValueObject vo) {
        //Context context = ContextHandler.getContext();
        //ISearchEngine searchEngine = (SearchEngine)context.getBean("searchEngine");

        ParamAbonnementement paramAbonnementement = (ParamAbonnementement)vo;

       
        try{
            paramAbonnementement.getAvancRembLiquid().setDatArlArl(DateHandler.addJour(paramAbonnementement.getAvancRembLiquid().getDatArlArl(),-1));///*** ne pas compter le dernier jour
                     ///*** réajustement dans le cas de Remboursement d'une avance
            boolean befor15 = false;
            GetListAbonnementsInteretsByArlTrt getListAbonnementsInteretsByArlTrt = new GetListAbonnementsInteretsByArlTrt();
            UpdateAbonnementPlacementTrt updateAbonnementPlacementTrt = new UpdateAbonnementPlacementTrt();
            Listes listAbonnement = new Listes() ;
            listAbonnement = (Listes)getListAbonnementsInteretsByArlTrt.exec(paramAbonnementement.getAvancRembLiquid().getAvancRembLiquid());
                      
            if(listAbonnement.getList()!=null && listAbonnement.getList().size()> 0){

                    for (Iterator it = listAbonnement.getList().iterator();it.hasNext(); ) { 
                        AbonnementPlacement abonnementPlacement = (AbonnementPlacement)it.next();
                        ///*** ajuster cet abonnement 
                        Double montSintAbpl=abonnementPlacement.getMontSintAbpl();
                        ///*** cas remboursement anticipé <15 jours
                        if(Math.rint(DateHandler.getDaysBetween(paramAbonnementement.getDatDebArl(),paramAbonnementement.getAvancRembLiquid().getDatArlArl()))<=15 ){
                            paramAbonnementement.getAvancRembLiquid().setDatArlArl(DateHandler.addJour(paramAbonnementement.getDatDebArl(),14));
                            
                            befor15 = true;
                        }
/*System.out.println(" - date ARL : "+paramAbonnementement.getAvancRembLiquid().getDatArlArl());
System.out.println(" - date DEB  : "+abonnementPlacement.getDatDebAbpl());
System.out.println(" - date FIN  : "+abonnementPlacement.getDatFinAbpl());
*/
                ///*** cas d'un remboursement a temps
                if (paramAbonnementement.getAvancRembLiquid().getCodTypiArl()==null || paramAbonnementement.getAvancRembLiquid().getCodTypiArl()==""){///*** 24.05.2010
                    if ((paramAbonnementement.getAvancRembLiquid().getDatArlArl().equals(abonnementPlacement.getDatFinAbpl()))||(paramAbonnementement.getAvancRembLiquid().getDatArlArl().after(abonnementPlacement.getDatDebAbpl()) && paramAbonnementement.getAvancRembLiquid().getDatArlArl().before(abonnementPlacement.getDatFinAbpl()))) {
                        abonnementPlacement.setCodEtatAbpl("T"); 
                        paramAbonnementement.setMontAcmrReaj(abonnementPlacement.getMontAbplAbpl().longValue());
                        abonnementPlacement = (AbonnementPlacement)updateAbonnementPlacementTrt.exec(abonnementPlacement);
                        generateMouvementInterne(paramAbonnementement);
                    }else{
                        if (paramAbonnementement.getAvancRembLiquid().getDatArlArl().before(abonnementPlacement.getDatDebAbpl()) || (paramAbonnementement.getAvancRembLiquid().getDatArlArl().equals(abonnementPlacement.getDatDebAbpl()))){
                            abonnementPlacement.setCodEtatAbpl("N");
                            abonnementPlacement = (AbonnementPlacement)updateAbonnementPlacementTrt.exec(abonnementPlacement);  
                        }
                    }
                    this.setCroFlag(true); 
                }else{
                        if((paramAbonnementement.getAvancRembLiquid().getDatArlArl().after(DateHandler.addJour(abonnementPlacement.getDatDebAbpl(),-1)) && paramAbonnementement.getAvancRembLiquid().getDatArlArl().before(abonnementPlacement.getDatFinAbpl())) ){
                            abonnementPlacement.setCodEtatAbpl("J");
                            abonnementPlacement = (AbonnementPlacement)updateAbonnementPlacementTrt.exec(abonnementPlacement);

                            AbonnementPlacement newAbonnementPlacement  = new AbonnementPlacement();
                            newAbonnementPlacement.setDatDebAbpl(abonnementPlacement.getDatDebAbpl());
                            newAbonnementPlacement.setDatFinAbpl(paramAbonnementement.getAvancRembLiquid().getDatArlArl());
                            newAbonnementPlacement.setDatCompAbpl(getDateComptabilisation(newAbonnementPlacement.getDatFinAbpl()));
                            if (!befor15){
                            newAbonnementPlacement.setDatValAbpl(getDateComptabilisation(paramAbonnementement.getAvancRembLiquid().getDatArlArl()));///*** date de l'execution du batch
                            }else {
                                newAbonnementPlacement.setDatValAbpl(new Date());///*** date de l'execution du batch
                            }
                            newAbonnementPlacement.setNumNbrjAbpl(Long.valueOf((Double.valueOf(Math.rint(DateHandler.getDaysBetween(abonnementPlacement.getDatDebAbpl(),paramAbonnementement.getAvancRembLiquid().getDatArlArl())))).longValue()+1));
                            ReajustAvrembliq reajustAvrembliq = new ReajustAvrembliq();
 
                            if (paramAbonnementement.getAvancRembLiquid().getCodTypiArl()!=null && paramAbonnementement.getAvancRembLiquid().getCodTypiArl().equalsIgnoreCase("S")){ ///*** Remboursement anticipé
                                montSintAbpl = Math.rint(paramAbonnementement.getAvancRembLiquid().getAvancRembLiquid().getMontInetArl().doubleValue()-paramAbonnementement.getMontRembAbpl().doubleValue());
                                Double mont=Math.rint(abonnementPlacement.getMontAbplAbpl()+montSintAbpl-abonnementPlacement.getMontSintAbpl());
                                newAbonnementPlacement.setMontAbplAbpl(Math.abs(mont));                                        
                                newAbonnementPlacement.setMontSintAbpl(montSintAbpl);
                                newAbonnementPlacement.setAbonnementPlacementM(abonnementPlacement);
                                       
                                AvancRembLiquid avancRembLiquid =new AvancRembLiquid();
                                avancRembLiquid.setNumSeqArl(paramAbonnementement.getNumSeqArl());
                                newAbonnementPlacement.setAvancRembLiquid(avancRembLiquid);
                                newAbonnementPlacement.setCodTypAbpl("S"); ///*** servi dans le cas de Remboursement anticipé de l'avance
                                newAbonnementPlacement.setCodEtatAbpl("T");///*** Traité
                                newAbonnementPlacement.setCodToprAbpl("A");///*** avance
                                newAbonnementPlacement.setCodStrcAbpl(paramAbonnementement.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc());
                                newAbonnementPlacement.setContratPlacement(paramAbonnementement.getContratPlacement());

                                if (!befor15){         
                                    paramAbonnementement.setMontAcmrReaj(Long.valueOf(mont.longValue()));///***12
                                    reajustAvrembliq.setMontAcmrReaj(mont);///***12
                                }else{
                                    paramAbonnementement.setMontAcmrReaj(Long.valueOf(montSintAbpl.longValue()));///***12
                                    reajustAvrembliq.setMontAcmrReaj(montSintAbpl);///***12                                    
                                }
                                paramAbonnementement.setMontRisaReaj(Long.valueOf(paramAbonnementement.getMontRembAbpl().longValue()));///***13
                                reajustAvrembliq.setMontRisaReaj(Double.valueOf(paramAbonnementement.getMontRembAbpl().longValue()));///***13
                                
                                ///*** Generer un enregistrement dans MouvementInterne
                                generateMouvementInterne(paramAbonnementement);  
                                        
                                this.setCroFlag(true); 

                            }else{ ///*** Remboursement retard
                                montSintAbpl = Math.rint(paramAbonnementement.getAvancRembLiquid().getMontInetArl().doubleValue()+paramAbonnementement.getAvancRembLiquid().getAvancRembLiquid().getMontInetArl().doubleValue());
                                newAbonnementPlacement.setMontSintAbpl(montSintAbpl);
                                Double mont=Math.rint(abonnementPlacement.getMontAbplAbpl()+montSintAbpl-abonnementPlacement.getMontSintAbpl());///(15)
                                newAbonnementPlacement.setMontAbplAbpl(mont);
                                newAbonnementPlacement.setAbonnementPlacementM(abonnementPlacement);
                                        
                                AvancRembLiquid avancRembLiquid =new AvancRembLiquid();
                                avancRembLiquid.setNumSeqArl(paramAbonnementement.getNumSeqArl());
                                newAbonnementPlacement.setAvancRembLiquid(avancRembLiquid);
                                newAbonnementPlacement.setCodTypAbpl("P"); ///*** percu dans le cas de Remboursement en retard de l'avance
                                newAbonnementPlacement.setCodEtatAbpl("T");///*** traité (appel imediat du cro)
                                newAbonnementPlacement.setCodToprAbpl("A");///*** avance
                                newAbonnementPlacement.setCodStrcAbpl(paramAbonnementement.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc());
                                newAbonnementPlacement.setContratPlacement(paramAbonnementement.getContratPlacement());
                                 
                                paramAbonnementement.setMontIcomReaj(Long.valueOf((Double.valueOf(paramAbonnementement.getAvancRembLiquid().getMontInetArl().doubleValue()-mont)).longValue()));///***14
                                paramAbonnementement.setMontIpanReaj(Long.valueOf(mont.longValue()));///***15
                                paramAbonnementement.setMontInetReaj(Long.valueOf(paramAbonnementement.getAvancRembLiquid().getMontInetArl().longValue()));///***16
                                reajustAvrembliq.setMontIcomReaj(Double.valueOf(paramAbonnementement.getMontIcomReaj().longValue()));
                                reajustAvrembliq.setMontIpanReaj(Double.valueOf(paramAbonnementement.getMontIpanReaj().longValue()));
                                reajustAvrembliq.setMontInetReaj(Double.valueOf(paramAbonnementement.getMontInetReaj().longValue()));
                                    
                            }
                            reajustAvrembliq.setAvancRembLiquid(paramAbonnementement.getAvancRembLiquid());
                            InsertReajustAvRembLiqTrt insertReajustAvRembLiqTrt =new InsertReajustAvRembLiqTrt();
                            reajustAvrembliq = (ReajustAvrembliq)insertReajustAvRembLiqTrt.exec(reajustAvrembliq);

                            InsertAbonnementPlacementTrt insertAbonnementPlacementTrt =new InsertAbonnementPlacementTrt();
                            newAbonnementPlacement= (AbonnementPlacement)insertAbonnementPlacementTrt.exec(newAbonnementPlacement);
                                   
                        }else{///*** annuler les abonnements surestimés (mettres l'etat à N)
                            if(paramAbonnementement.getAvancRembLiquid().getDatArlArl().before(abonnementPlacement.getDatDebAbpl())){
                                abonnementPlacement.setCodEtatAbpl("N");
                                abonnementPlacement = (AbonnementPlacement)updateAbonnementPlacementTrt.exec(abonnementPlacement);  
                            }
                            if(paramAbonnementement.getAvancRembLiquid().getDatArlArl().after(abonnementPlacement.getDatFinAbpl()) && befor15){
                                abonnementPlacement.setCodEtatAbpl("T");
                                abonnementPlacement = (AbonnementPlacement)updateAbonnementPlacementTrt.exec(abonnementPlacement);  
                            }
                        }
                    }                 
                }
            }
        
        return (paramAbonnementement);
        }catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("Erreur ReajusterAbonnementTrt ");
                text.append(e.toString());
                erreur.setCode("400");
                erreur.setDescription(text.toString());
                erreur.setKey("GenererAbonnementTrt");
                paramAbonnementement.addError(erreur);
                logger.error(" *** Erreur lors de ReajusterAbonnementTrt" /*concernant l'agence "+avancRembLiquid.get().getCodStrcMand()*/+" : ", e);
                throw new RuntimeException(e);
            }
    }
    
    private void generateMouvementInterne(ParamAbonnementement paramAbonnementement) {
        try {
          MouvementInterne mouvementInterne = new MouvementInterne();
          
          mouvementInterne.setCodRefmMvti(paramAbonnementement.getAvancRembLiquid().getNumSeqArl().toString());
          ///*** mouvementInterne.setDatOperMvti(DateHandler.addJour(paramAbonnementement.getAvancRembLiquid().getDatArlArl(),1));
          mouvementInterne.setDatOperMvti(paramAbonnementement.getDateCompAgence());///***
          mouvementInterne.setDatSystMvti(new Date());
          if (paramAbonnementement.getAvancRembLiquid().getDatValiArl() == null){///*** cas remboursement a temps
              mouvementInterne.setDatValMvti(new Date());              
          }else{
              mouvementInterne.setDatValMvti(paramAbonnementement.getAvancRembLiquid().getDatValiArl());              
          }
          mouvementInterne.setLibMotfMvti("Abonnements Remboursement Avance (304)");
          mouvementInterne.setMontMvtiMvti(Long.valueOf(Math.round(paramAbonnementement.getMontAcmrReaj())));
          Structure strc = new Structure();
          strc.setCodStrcStrc(paramAbonnementement.getAvancRembLiquid().getContratPlacement().getContratCpt().getStructure().getCodStrcStrc());
          Tache tache = new Tache();
          TacheId tacheId = new TacheId();
          tacheId.setCodTachTach(Long.valueOf("1"));
          tacheId.setCodOperOper(Constants.COD_OPER_ABON_INTERET_REMB_AVANCE_PLAC);
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
                 
           
          } 
          catch (Exception e) {
              com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
              erreur.setCode("304");
              erreur.setDescription("ReajusterAbonnementTrt.generateMouvementInterne  "+e.getMessage());;
              paramAbonnementement.addError(erreur);
              logger.error("Exception : ",e);   
              throw new   RuntimeException(e);
          }
    }


    public Date getLastDayOfMonth(Date date){
    
    Date d=new Date();
    int year = date.getYear()+1900;
    int month = date.getMonth()+1;
    d.setYear(date.getYear());
    d.setMonth(date.getMonth());
    d.setHours(date.getHours());
    
        if (month == 4 || month == 6 || month == 9 || month == 11)  
        {
            d.setDate(30);
        }
        else {
            if (month == 2){
                if ((year % 4 == 0) && ((year % 100 != 0) ||  (year % 400 == 0))){
                    d.setDate(29);
                }
                else  d.setDate(28);
            }
            else d.setDate(31);
        }
        return d;
    }
    public  Date getDateComptabilisation(Date d)  {

        try{
         Date DateReturn=d;
          while(CalanderHandler.isJourFerier(d)){
            DateReturn = DateHandler.addJour(d,-1);
            d = DateHandler.addJour(d,-1);
            System.out.print("-d- *** "+d);
          }
            return(DateReturn);
        
        }catch(Exception e){
            logger.error(" Erreur dans GetDateComptable.execute : " , e);
            return (d);
        }
    }
    
/*    public  Long calculMontAvance(Long nbrJour, Long montant, Double taux){
    
    
    try{
        
        Math.rint(montant * nbrJour * taux/Constants.NBR_JOURS_BC_CAT.doubleValue());
        
                                         
    } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("veuillez transmettre ce message à l'équipe informatique: ");
                text.append(". Exception calculMontAvance : "); text.append(e.getMessage());
                erreur.setCode("200");
                erreur.setDescription(text.toString());
            }

    }
    */
    public void genCroText(ValueObject vo) {
                ParamAbonnementement paramAbonnementement = (ParamAbonnementement)vo;
              /* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

               Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        com.oxia.security.abc.model.Personnel user = null;
                        if (obj instanceof UserDetails) {
                            user = (com.oxia.security.abc.model.Personnel)obj;
                       }
              
              this.setNumRefCro((paramAbonnementement.getAvancRembLiquid().getNumSeqArl()));
              this.setLibRefCro("SMILE.PLC.ABON.AV");
              
              this.setDatValCro(paramAbonnementement.getAvancRembLiquid().getDatValiArl());
              this.setCodeStructInitiatrice((StrHandler.lpad(paramAbonnementement.getContratPlacement().getNumSeqCpla().toString(),'0',15)).substring(0,3));
              //user.getStructure().getCodStrcStrc().toString());              
              this.setCodStrcImpt(Long.valueOf((StrHandler.lpad(paramAbonnementement.getContratPlacement().getNumSeqCpla().toString(),'0',15)).substring(0,3)));
              this.setCodEtatCro(0);         
              
              this.setCodeProduit(paramAbonnementement.getContratPlacement().getProduitPlacement().getCodPrdPlc().toString());
              this.setOperationId(Constants.COD_OPER_ABON_INTERET_REMB_AVANCE_PLAC.toString());              
              
              ///this.setDateOperation(paramAbonnementement.getAvancRembLiquid().getDatReelArl());///*** a revoir
              this.setDateOperation(paramAbonnementement.getDateCompAgence());///*** 
              SimpleDateFormat formater=new SimpleDateFormat("dd/MM/yyyy");
              formater=new SimpleDateFormat("HH:mm:ss");
              String heureString = formater.format(new Date());
              this.setHeureOperation(heureString);
              this.setTypeOperationCro("O");
              this.setCodTachTach(1);
              
              this.setCodRefcOmp(paramAbonnementement.getAvancRembLiquid().getNumSeqArl().toString());
              this.setDatExecCro(new Date());

              this.setNumCinUser(user.getNumMatrUser());
              this.setCodTypUser(user.getMatriculeTyp());
              
                 /* ------------------Garniture de la partie VARIABLE du CRO----------------------------------  */
            StringBuffer cro=new StringBuffer("");
            
            cro.append("numCptBna=");
            cro.append(StrHandler.lpad(paramAbonnementement.getContratPlacement().getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3)+StrHandler.lpad(paramAbonnementement.getContratPlacement().getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4)+StrHandler.lpad(paramAbonnementement.getContratPlacement().getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6)+";");               

            //  LE contrat de placement  
            cro.append("CONTRAT_PLACEMENT.NUM_SEQ_CPLA=");
            cro.append(paramAbonnementement.getContratPlacement().getNumSeqCpla() +";");
              
            cro.append("AVANC_REMB_LIQUID.NUM_SEQ_ARL_304=");
            cro.append(paramAbonnementement.getAvancRembLiquid().getNumSeqArl() +";");
              
            cro.append("ABONNEMENT_PLACEMENT.MONT_ABPL_ABPL_304=");
            cro.append(Math.round(paramAbonnementement.getMontAcmrReaj()) +";");

             
            this.setCroText(cro.toString());
            }  
         
        
   
    public String getNumeroTache(ValueObject vo) {
        return (Constants.CODE_RESSOURCE_GENERALE);    
        
        
    }
}
