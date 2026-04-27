package com.bna.smile.model.domainecontratcompte.procuration.traitement;

import org.apache.log4j.Logger;

import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.traitement.GetDetailContratTrt;
import com.bna.smile.model.domainecommun.traitement.GetMembreCotitulaireTrt;
import com.bna.smile.model.domainecontratcompte.procuration.model.ListMandatOperationVo;
import com.bna.smile.model.domainecontratcompte.procuration.model.ParamMandatOperationVo;
import com.bna.smile.model.domainecontratcompte.procuration.model.PouvoirVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * Classe qui permet d'extraire tous les pouvoirs d'une personne sur 
 * un contrat donné (input ContratPersonne) et retourne le type du pouvoir (T,M,C) et la Liste des mandats ou  Cotitulaire
 *  selon le cas.
 * @param vo : ContratPersonne
 * @return vo : PouvoirVo
 * @author Ramzi
 * 
 */
public class GetPouvoirPersonneContratTrt {
   

    public GetPouvoirPersonneContratTrt() {
    }

    public IValueObject execute(IValueObject vo) {
        Logger logger = Logger.getLogger(GetPouvoirPersonneContratTrt.class);
        ContratPersonne contratPersonne = (ContratPersonne)vo;
        PouvoirVo pouvoirVo = new PouvoirVo();
        ListMandatOperationVo listMandatOperationVo;
        CoTitulaire coTitulaire;

        // Recherche du client du contrat


        try {
            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            ICriteria criteria = searchEngine.createCriteria();
            IExpression expression = searchEngine.createExpression();
            // recherche du client 
            GetDetailContratTrt getDetailContratTrt = 
                new GetDetailContratTrt();
            ContratCpt contratCpt = 
                (ContratCpt)getDetailContratTrt.exec(contratPersonne.getContratCptId());
            Long codTpceClient = 
                contratCpt.getClient().getPersonne().getTypePiece().getCodTpceTpce();
            String numPceClient = 
                contratCpt.getClient().getPersonne().getNumPcePers();

            Long codTpcePers = 
                contratPersonne.getPersonneId().getCodTpceTpce();
            String numPcePers = 
                contratPersonne.getPersonneId().getNumPcePers();

            /// 1 ER CAS : SI TITULAIRE DU CONTRAT
            if (codTpceClient.equals(codTpcePers) && 
                numPceClient.equals(numPcePers)) {
                pouvoirVo.setTypePouvoir("T");
                /// 2 EME CAS : SI ENTITE COTITULAIRE 
            } else if (contratCpt.getClient().getTypePers().getCodTperTper().equals(Constants.ENTCOTITULAIRE)) {
                GetMembreCotitulaireTrt getMembreCotitulaireTrt = 
                    new GetMembreCotitulaireTrt();
                coTitulaire = 
                        (CoTitulaire)getMembreCotitulaireTrt.exec(contratPersonne);
                if (coTitulaire == null) {
                    pouvoirVo.setTypePouvoir("");
                } else {
                    pouvoirVo.setTypePouvoir("C");
                    pouvoirVo.setCoTitulaire(coTitulaire);

                }
                          
            }
            /// 3 EME CAS : SI MANDATAIRE 
            if(pouvoirVo.getTypePouvoir()==null || pouvoirVo.getTypePouvoir().equals("")){
                GetListMandatOperationPersonneContratOperationTrt getListMandatOperationPersonneContratOperationTrt = 
                    new GetListMandatOperationPersonneContratOperationTrt();
                ParamMandatOperationVo paramMandatOperationVo = 
                    new ParamMandatOperationVo();
                paramMandatOperationVo.setContraCptId(contratPersonne.getContratCptId());
                paramMandatOperationVo.setPersonneStrc(contratPersonne.getPersonneId());
                if(contratPersonne.getOperation()!=null ){
                    paramMandatOperationVo.setOperation(contratPersonne.getOperation()); 
                }
                listMandatOperationVo = 
                        (ListMandatOperationVo)getListMandatOperationPersonneContratOperationTrt.execute(paramMandatOperationVo);
                if ((listMandatOperationVo.getListMandatsGeneraux() == null || 
                     listMandatOperationVo.getListMandatsGeneraux().size() == 
                     0) && 
                    (listMandatOperationVo.getListMandatsSpeciauxOperations() == 
                     null || 
                     listMandatOperationVo.getListMandatsSpeciauxOperations().size() == 
                     0))
                    pouvoirVo.setTypePouvoir("");
                else
                    pouvoirVo.setTypePouvoir("M");
                    pouvoirVo.setListMandatOperation(listMandatOperationVo);
            }


            } catch (Exception e) {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                erreur.setCode("9999");
                erreur.setDescription("GetPouvoirPersonneContratTrt " + 
                                      e.getMessage());
                logger.error("Exception",e);
                throw new RuntimeException(e);
            }
        return (pouvoirVo);
    }
}
