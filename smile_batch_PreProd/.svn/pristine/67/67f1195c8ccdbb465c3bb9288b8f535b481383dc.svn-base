package com.bna.smile.web.placement.servlets;


import com.bna.commun.conditiondebanque.commande.DemandeConditionCmd;
import com.bna.commun.conditiondebanque.vo.Condition;
import com.bna.commun.conditiondebanque.vo.ConditionBanque;
import com.bna.commun.conditiondebanque.vo.DemandeCondition;
import com.bna.commun.conditiondebanque.vo.DetailConditionBanque;
import com.bna.commun.conditiondebanque.vo.ListConditionVo;
import com.bna.commun.model.CodePostal;

import com.bna.commun.model.PersClient;
import com.bna.commun.model.Personne;
import com.bna.commun.model.ProduitPlacement;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.domainecommun.commande.GetCodePostalCmd;

import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;

import com.bna.smile.web.commun.model.ParamAgence;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

import org.apache.commons.collections.map.ListOrderedMap;

import org.springframework.jdbc.core.JdbcTemplate;

public class GetConditionBanqueServlet extends BaseAjaxServlet {
   
       
    private String datevaleur;
    private String tauxInteret;
    private String tmm;
    private String signeMarge;
    private String valeurMarge;
    
    
  
    public String getXmlContent(HttpServletRequest request, HttpServletResponse response) {
      
     String produit   =  request.getParameter("produit");
     String operation =  request.getParameter("operation");
     String numCpt =  request.getParameter("numCpt");
     String strc =  request.getParameter("strc");
     String prd =  request.getParameter("prd");
     String typePiece =  request.getParameter("typePiece");
     String numPiece =  request.getParameter("numPiece");
     String idContrat =  request.getParameter("idContrat");
     String montant =  request.getParameter("montant");
     String duree =  request.getParameter("duree");
     String dateRef =  request.getParameter("dateRef");
            
     
     DemandeConditionCmd cmd = new DemandeConditionCmd();
     DemandeCondition demCond = new DemandeCondition(); 
     
     if (numCpt.equals("")) numCpt = String.valueOf(0);
     if (strc.equals("")) strc = String.valueOf(0);
     if (prd.equals("")) prd = String.valueOf(0);
     if (typePiece.equals("")) typePiece = String.valueOf(0);
     if (numPiece.equals("")) numPiece = null;
     if (idContrat.equals("")) idContrat = null;
    
    //public DemandeCondition( int codPrdPrd,int codOperOper,int numCcptCcpt,int codStrcStrc,int codPrdCpt,int codTpceTpce,String numPcePers,String idContrat,float montant, int nbUnites ,Date dateReference)
     try{
     demCond = new  DemandeCondition((Long.valueOf(produit)).intValue(),(Long.valueOf(operation)).intValue(),(Long.valueOf(numCpt)).intValue(),(Long.valueOf(strc)).intValue(),(Long.valueOf(prd)).intValue(),(Long.valueOf(typePiece)).intValue(),numPiece,idContrat,(Long.valueOf(montant).floatValue()),(Long.valueOf(duree)).intValue(),DateHandler.strToDate(dateRef));
    
        ListConditionVo v = (ListConditionVo)cmd.execute(demCond);

        if (v.getListConditionBanque().size() == 0) {
            System.out.println("\n \n auccune condition de banque a appliquer \n");
            datevaleur= "NAN";
            tauxInteret="NAN";
        } else {
            
            for (Iterator itCond = v.getListConditionBanque().iterator(); 
                 itCond.hasNext(); ) {
                Condition condition = (Condition)itCond.next();
                List conditionsBanque = condition.getConditionBanque();
                for (Iterator it = conditionsBanque.iterator(); it.hasNext(); 
                ) {
                    ConditionBanque conditionBanque = (ConditionBanque)it.next();
                    //souscriptionContratPlacementForm.getDemandeDecisionView().setTauxIrc((String.valueOf(conditionBanque.getTvaCalculePourCommisions())));

                    List detailsConditionBanque = 
                        conditionBanque.getDetailConditionBanque();
                    for (Iterator itde = detailsConditionBanque.iterator(); itde.hasNext(); ) {
                        DetailConditionBanque detailConditionBanque = (DetailConditionBanque)itde.next();
                        if (detailConditionBanque.getCodTecdTecd().equals("D")) {
                            // garnir la date valeur                                      
                            datevaleur = detailConditionBanque.getDateValeur();
                            
                        } else {
                            if (detailConditionBanque.getCodTecdTecd().equals("T")) {
                                tauxInteret = String.valueOf(detailConditionBanque.getValValVael());                                
                                if(detailConditionBanque.getTauxReference() != 0.0f){
                                    tmm = String.valueOf(detailConditionBanque.getTauxReference());
                                }
                                if(!conditionBanque.getCodTcndTcnd().equals("1")){
                                    // condition de banque autre que generale
                                    if(detailConditionBanque.getSigMargVael()!=null){
                                        Float sigMargVael = Float.valueOf(detailConditionBanque.getSigMargVael());
                                        if(sigMargVael < 0){
                                            signeMarge = "-";
                                            valeurMarge = String.valueOf(Math.abs(sigMargVael.floatValue()));
                                            }else{
                                                signeMarge = "+";
                                                valeurMarge = sigMargVael.toString();   
                                                }
                                        
                                        }
                                    }
                                    
                                }
                            }
                        }
                    }
                }
            }

        
      
           return new AjaxXmlBuilder()
                .addItem("dateVal", datevaleur)
                .addItem("tauxGen", tauxInteret)    
                .addItem("tmm", tmm)  
                .addItem("marge", valeurMarge) 
                .addItem("signe", signeMarge) 
                .toString();          
        
   
    } catch (Exception e) {
        throw new RuntimeException(e);          
    
    }
}
    
}

