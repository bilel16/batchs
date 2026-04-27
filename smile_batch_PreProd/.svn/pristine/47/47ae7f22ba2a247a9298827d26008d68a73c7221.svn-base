package com.bna.smile.model.domainecaisse.traitement;

import java.util.List;

import com.bna.commun.model.SessionJrnCaisse;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.domainecaisse.model.ListeCaisseStructureVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;


/**
 * Classe qui permet de retourner la liste des sessionsJrnCaisse d'une structure pour une journée
 * @author JERBI Lamia
 * @since 28/03/2011
 */
public class GetListSessionJrnCaisseTrt extends Traitement {
    Context context = ContextHandler.getContext();
    public GetListSessionJrnCaisseTrt() {
    }
   
   
    public  IValueObject perform(IValueObject vo) throws Exception {
     ListeCaisseStructureVo listeCaisseStructureVo = (ListeCaisseStructureVo)  vo;;
     try {
              
        ISearchEngine searchEngine  = (SearchEngine)context.getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();   
        
        
        if (listeCaisseStructureVo.getNumSeqSjc()!=null){
            criteria.add(expression.eq("numSeqSjc", listeCaisseStructureVo.getNumSeqSjc()));
        }
        
        if (listeCaisseStructureVo.getCodeStructure()!=null){
            criteria.add(expression.eq("journeeCaisse.journeeCaisseId.codStrcJrn", listeCaisseStructureVo.getCodeStructure()));
        }
            
        if (listeCaisseStructureVo.getDateJournee()!=null){
            criteria.add(expression.between("journeeCaisse.journeeCaisseId.datJrnJrn",  DateHandler.strToDate( DateHandler.dateToStr(listeCaisseStructureVo.getDateJournee())), DateHandler.addJour(listeCaisseStructureVo.getDateJournee(),1) ));
        }
     
        if (listeCaisseStructureVo.getNumMatriculeUser()!=null){
            criteria.add(expression.eq("personnel.numMatrUser", listeCaisseStructureVo.getNumMatriculeUser()));
         }

       //-------------------------- Critére sur le satus  ----------------------------------//
       if (listeCaisseStructureVo.getCodeStatus()!= null && !listeCaisseStructureVo.getCodeStatus().equals("") ){
           criteria.add(expression.eq("codStatSjc",   listeCaisseStructureVo.getCodeStatus()  ));
       }
            
        if (listeCaisseStructureVo.getTypeCaisse()!= null && !listeCaisseStructureVo.getTypeCaisse().equals("") ){
            criteria.add(expression.eq("codTypSjc",   listeCaisseStructureVo.getTypeCaisse()  ));
        }
    
        List listCaisses = searchEngine.find(SessionJrnCaisse.class, criteria);
        //List listCaisses = searchEngine.findAll(SessionJrnCaisse.class);
        
        if (listCaisses != null && listCaisses.size() > 0) {
            listeCaisseStructureVo.setListeCaisseStructure(listCaisses);
        }
        
        
        return listeCaisseStructureVo;
     
    } catch (Exception e) {
        com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
        StringBuffer text = new StringBuffer("Erreur dans GetListSessionJrnCaisseTrt : ");
        text.append(e.toString());
        erreur.setCode("200");
        erreur.setDescription(text.toString());
        erreur.setKey("caisse");
        listeCaisseStructureVo.addError(erreur);
        return (listeCaisseStructureVo);
    }

    }

    public void genCroText(ValueObject vo) {

    }
    
    
    
    
}
