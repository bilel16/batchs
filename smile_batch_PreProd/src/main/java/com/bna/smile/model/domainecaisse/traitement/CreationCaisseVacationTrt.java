package com.bna.smile.model.domainecaisse.traitement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.bna.commun.model.DetailSessionCaisse;
import com.bna.commun.model.MouvementSessionCaisse;
import com.bna.commun.model.SessionJrnCaisse;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecaisse.model.SessionJrnCaissePrVac;
import com.bna.smile.model.domainecaisse.model.SituationDetailCaisseStructureVo;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


/**
 * classe pour la création d'une caisse de vacation
 * sessionJrnCaisse + ses DetailSessionCaisses
 * @author BOUSSEN Youssef
 * @since 11/04/2011
 */

public class CreationCaisseVacationTrt extends Traitement {
    Context context = ContextHandler.getContext();

    
    public CreationCaisseVacationTrt() {
    }
    public  IValueObject perform(IValueObject vo) throws Exception {
    
     try {
         CRUDservice crudService = (CRUDservice)context.getBean("crudservice");

         SessionJrnCaissePrVac sessionJrnCaissePrVac =(SessionJrnCaissePrVac)vo;
         SessionJrnCaisse  sessionJrnCaissePr  = (SessionJrnCaisse)sessionJrnCaissePrVac.getSessionJrnCaissePr();
         SessionJrnCaisse  sessionJrnCaisseVac = (SessionJrnCaisse)sessionJrnCaissePrVac.getSessionJrnCaisseVac();
         Collection listDetailSessionCaisse = new ArrayList();    
         listDetailSessionCaisse.addAll(sessionJrnCaissePrVac.getListDetailSessionCaisseVac());

         crudService.update(sessionJrnCaissePr);

         Context context = ContextHandler.getContext();

         ///*** MAJ des detailsSessionCaisse de la session principale
         for (Iterator it = sessionJrnCaissePr.getDetailSessionCaisses().iterator(); it.hasNext(); ) {
             DetailSessionCaisse detailSessionCaisse = (DetailSessionCaisse)it.next();
             crudService = (CRUDservice)context.getBean("crudservice");
             crudService.update(detailSessionCaisse);
         }


         crudService.create(sessionJrnCaisseVac);
         
         ///*** creation des detailsSessionCaisse de la session de vacation
         for (Iterator it = listDetailSessionCaisse.iterator(); it.hasNext(); ) {
             DetailSessionCaisse detailSessionCaisse = (DetailSessionCaisse)it.next();
             detailSessionCaisse.setSessionJrnCaisse(sessionJrnCaisseVac);
             crudService = (CRUDservice)context.getBean("crudservice");
             crudService.create(detailSessionCaisse);
             ///*** garnir le mouvement_session_caisse Pr
             MouvementSessionCaisse mouvementSessionCaissePr = new MouvementSessionCaisse();
             mouvementSessionCaissePr.setCodStatMvtc("1");
             mouvementSessionCaissePr.setDatMvtMvtc(sessionJrnCaissePr.getJourneeCaisse().getJourneeCaisseId().getDatJrnJrn());
             mouvementSessionCaissePr.setMontMvtMvtc(detailSessionCaisse.getMontDebDsc());
             mouvementSessionCaissePr.setCodSensMvtc(Constants.COD_SENS_DB);
             
             Tache tache = new Tache();
             TacheId tacheId = new TacheId();
             tacheId.setCodTachTach(Constants.COD_TACH_OUV_CAISSE_VAC);
             tacheId.setCodOperOper(Constants.COD_OPER_OUV_CAISSE_VAC);
             tache.setTacheId(tacheId);
             mouvementSessionCaissePr.setTache(tache);
             mouvementSessionCaissePr.setLibOperMvtc("Creat caisse vacat");
             mouvementSessionCaissePr.setSessionJrnCaisse(sessionJrnCaissePr);
             mouvementSessionCaissePr.setDatSystMvtc(new Date());
             mouvementSessionCaissePr.setDevise(detailSessionCaisse.getDevise());
             mouvementSessionCaissePr.setStructure(sessionJrnCaissePr.getPersonnel().getStructure());
             mouvementSessionCaissePr.setCaisseStrc(sessionJrnCaissePr.getJourneeCaisse().getCaisseStrc());
             crudService.create(mouvementSessionCaissePr);
             ///*** garnir le mouvement_session_caisse Vac
             
             SituationDetailCaisseStructureVo situationDetailCaisseStructureVo = new SituationDetailCaisseStructureVo();
             situationDetailCaisseStructureVo.setJourneeCaisseOut(sessionJrnCaissePr.getJourneeCaisse());
             situationDetailCaisseStructureVo.setJourneeCaisseIn(sessionJrnCaisseVac.getJourneeCaisse());
             situationDetailCaisseStructureVo.setDetailSessionCaisse(detailSessionCaisse);
             ///*** appel de la generation des cro
             GenererCrosDetailsSessionsTrt genererCrosDetailsSessionsTrt =new GenererCrosDetailsSessionsTrt();
             situationDetailCaisseStructureVo = (SituationDetailCaisseStructureVo) genererCrosDetailsSessionsTrt.exec(situationDetailCaisseStructureVo);
         }
         
         return sessionJrnCaissePrVac;
    
     } catch (Exception e) {
         com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
         erreur.setCode("Technique");
         erreur.setDescription("CreationCaisseVacationTrt  "+e.getMessage());;
         logger.error("Exception : ",e);   
         throw new   RuntimeException(e);
     }

    }

    public void genCroText(ValueObject vo) {


    }

}
