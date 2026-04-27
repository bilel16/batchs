package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.OperationCompte;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.dao.OperationCompteDAO;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

import java.util.Date;

import org.apache.log4j.Logger;

public class InsertOperationCompteTrt {
    Context context = ContextHandler.getContext();

    public InsertOperationCompteTrt() {
    }

    public ValueObject execute(ValueObject vo) {

        OperationCompte operationCompte = (OperationCompte)vo;
        Logger logger = Logger.getLogger(InsertOperationCompteTrt.class);
       try{
        /* Garnir le N° de l'operation_compte */
        OperationCompteDAO operationCompteDAO = 
            (OperationCompteDAO)context.getBean("operationCompteDAO");
        operationCompte.getOperationCompteId().setNumOperOcpt(operationCompteDAO.getSequenceOperationCompte());
        operationCompte.getOperationCompteId().setDatOperOcpt(new Date());
        /* inserer l'operation_compte */
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        crudService.create(operationCompte);
        return operationCompte;
        } catch (Exception e) {
            logger.error("Exception : ",e);   
            throw new RuntimeException(e);  
            
        }

    }
}
