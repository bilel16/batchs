package com.bna.smile.model.clotureDomaine.traitement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.JourneeStructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.clotureDomaine.dao.StatMoyPaiDAO;
import com.bna.smile.model.clotureDomaine.model.StatMoyPai;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.moyenPayement.dao.AccuseDAO;
import com.bna.smile.model.moyenPayement.model.Accuse;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetDonneeMoyPaiTrt extends Traitement{
    public GetDonneeMoyPaiTrt() {
    }
    public IValueObject perform(IValueObject vo) throws SQLException, 
                                                        Exception {

    Context context = ContextHandler.getContext();
    StatMoyPai statMoyPai = (StatMoyPai)vo;
    List listeVir = new ArrayList();
    ListOrderedMap listeOperMoyPay = null;
    try {
        StatMoyPaiDAO statMoyPaiDAO = 
            (StatMoyPaiDAO)context.getBean("statMoyPaiDAO");  
        AccuseDAO accuseDAO=(AccuseDAO)context.getBean("accuseDAO");
        List<Accuse> listAccuses= new ArrayList<Accuse>();
        JourneeStructureDomaine journeeStructureDomaine = 
            this.getJourneeStructureDomaine(statMoyPai.getJourneeStructureDomaineId());

        if (this.checkClotureJournee()) {

            if (((journeeStructureDomaine.getCodStatJsd().equals(Constants.ETAT_JSDOM_OUV)) || 
                (journeeStructureDomaine.getCodStatJsd().equals(Constants.ETAT_JSDOM_COURCLO))) &&
                (journeeStructureDomaine.getJourneeStructureDomaineId().get_codStrcStrc()!=Long.valueOf("900"))){
                /*mise à jour de l'etat du domaine*/
                
                journeeStructureDomaine.setCodStatJsd(Constants.ETAT_JSDOM_COURCLO);
                CRUDservice crudService = 
                    (CRUDservice)context.getBean("crudservice");
                crudService.update(journeeStructureDomaine);  
                /*virement compensés recus*/
                 listeVir = 
                         statMoyPaiDAO.getStatOperMoyPai(statMoyPai.getStructure(), 
                                                               Constants.COD_OPER_POS_VIR    , 
                                                               statMoyPai.getDateJournee());

                 listeOperMoyPay = (ListOrderedMap)listeVir.get(0);
                 statMoyPai.setNbr822(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
                 if (listeOperMoyPay.getValue(1) != null) {
                     statMoyPai.setMnt822(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
                 } else {
                     statMoyPai.setMnt822(Double.valueOf("0"));
                 }
                
                listeVir = 
                        statMoyPaiDAO.getStatMouvInterne(statMoyPai.getStructure(), 
                                                         Constants.COD_OPER_POS_VIR, 
                                                          statMoyPai.getDateJournee());
                listeOperMoyPay = (ListOrderedMap)listeVir.get(0);
                statMoyPai.setNbr822(statMoyPai.getNbr822()+(Long.valueOf(listeOperMoyPay.getValue(0).toString())));
                if (listeOperMoyPay.getValue(1) != null) {
                    statMoyPai.setMnt822(statMoyPai.getMnt822()+(Double.valueOf(listeOperMoyPay.getValue(1).toString())));
                } 
            /*rejet virement  recus*/
             listeVir = 
                     statMoyPaiDAO.getStatOperMoyPai(statMoyPai.getStructure(), 
                                                           Constants.COD_OPER_POS_REJ    , 
                                                           statMoyPai.getDateJournee());

             listeOperMoyPay = (ListOrderedMap)listeVir.get(0);
             statMoyPai.setNbr948(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
             if (listeOperMoyPay.getValue(1) != null) {
                 statMoyPai.setMnt948(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
             } else {
                 statMoyPai.setMnt948(Double.valueOf("0"));
             }
            
            listeVir = 
                    statMoyPaiDAO.getStatMouvInterne(statMoyPai.getStructure(), 
                                                     Constants.COD_OPER_POS_REJ, 
                                                      statMoyPai.getDateJournee());
            listeOperMoyPay = (ListOrderedMap)listeVir.get(0);
            statMoyPai.setNbr948(statMoyPai.getNbr948()+(Long.valueOf(listeOperMoyPay.getValue(0).toString())));
            if (listeOperMoyPay.getValue(1) != null) {
                statMoyPai.setMnt948(statMoyPai.getMnt948()+(Double.valueOf(listeOperMoyPay.getValue(1).toString())));
            } 
            
            /*reception rejet vir par l'agence 947*/
            
             listeVir = 
                     statMoyPaiDAO.getStatAdDetailVirement(statMoyPai.getStructure(), 
                                                           Long.valueOf(2),Long.valueOf(22) ,
                                                           statMoyPai.getDateJournee());

             listeOperMoyPay = (ListOrderedMap)listeVir.get(0);
             statMoyPai.setNbr947(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
             if (listeOperMoyPay.getValue(1) != null) {
                 statMoyPai.setMnt947(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
             } else {
                 statMoyPai.setMnt947(Double.valueOf("0"));
             }
            
            /*reception  vir recu par l'agence 821*/
            
             listeVir = 
                     statMoyPaiDAO.getStatAdDetailVirement(statMoyPai.getStructure(), 
                                                           Long.valueOf(2)    ,Long.valueOf(21) ,
                                                           statMoyPai.getDateJournee());

             listeOperMoyPay = (ListOrderedMap)listeVir.get(0);
             statMoyPai.setNbr821(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
             if (listeOperMoyPay.getValue(1) != null) {
                 statMoyPai.setMnt821(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
             } else {
                 statMoyPai.setMnt821(Double.valueOf("0"));
             }
            
            /*rejet des vir recu 823*/
             /*envoi de nos vir agence 824*/
            
             listeVir = 
                     statMoyPaiDAO.getStatAdDetailVirement(statMoyPai.getStructure(), 
                                                           Long.valueOf(1)    ,Long.valueOf(22) ,
                                                           statMoyPai.getDateJournee());

             listeOperMoyPay = (ListOrderedMap)listeVir.get(0);
             statMoyPai.setNbr823(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
            statMoyPai.setNbr824(Long.valueOf(listeOperMoyPay.getValue(0).toString()));
             if (listeOperMoyPay.getValue(1) != null) {
                 statMoyPai.setMnt823(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
                 statMoyPai.setMnt824(Double.valueOf(listeOperMoyPay.getValue(1).toString()));
             } else {
                 statMoyPai.setMnt823(Double.valueOf("0"));
                 statMoyPai.setMnt824(Double.valueOf("0"));
             }
            
            
            
                /*virement emis*/
                listAccuses=statMoyPaiDAO.getAccuseByStructure(statMoyPai.getStructure(),statMoyPai.getDateJournee());
                statMoyPai.setListeAccusee(listAccuses);
                
        
        } else if (journeeStructureDomaine.getCodStatJsd().equals(Constants.ETAT_JSDOM_SCLO)) {
            com.oxia.fwk.core.Error erreur = 
                new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("la session n'est pas encore ouverte...");
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetDonnePlacementTrt");
            statMoyPai.addError(erreur);

        } else {

            com.oxia.fwk.core.Error erreur = 
                new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Le domaine est déja cloturée...");
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("GetDonnePlacementTrt");
            statMoyPai.addError(erreur);


        }


        } else {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = 
            new StringBuffer("La journée est déja clôturée...");
        erreur.setCode("100");
        erreur.setDescription(text.toString());
        erreur.setKey("GetDonnePlacementTrt");
        statMoyPai.addError(erreur);

        }

        return statMoyPai;    
        
        
        
        
        
    } catch (Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        erreur.setCode("Technique");
        erreur.setDescription("GetDonneeMoyPaiTrt " + e.getMessage());
        statMoyPai.addError(erreur);
        throw new Exception(e);
    }
  
    }
    public void genCroText(ValueObject vo) {

    }
}
