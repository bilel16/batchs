package com.bna.smile.model.domainecommun.commande;


    import com.bna.commun.model.ExonerationCltTva;
    import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.ParamExonerationCltTva;
import com.bna.smile.model.domainecommun.service.ExonerationTVAService;
    import com.oxia.fwk.context.Context;
    import com.oxia.fwk.core.ICommande;
    import com.oxia.fwk.core.IValueObject;


    /**Fichier: InsertExonerationTvaCmd.java
 * @version 1.0.0 du 26/01/2006
 * Copyright(c) 2006 BNA (www.bna.com.tn)
 * Classe: CreatExoneartionTvaCmd
 * package com.bna.smile.model.domainecommun.commande
 * @author : Jerbi Lamia
 */
    public class CreatExoneartionTvaCmd implements ICommande {
        public CreatExoneartionTvaCmd() {
        }

        /**
         * Methode execute
         * @param vo Objet : ParamExonerationCltTva
         * @return   Objet :
         */
        public IValueObject execute(IValueObject vo) {
            ParamExonerationCltTva paramExonerationCltTva = (ParamExonerationCltTva)vo;
           
            Context context = ContextHandler.getContext();
            ExonerationTVAService exonerationTVAService = 
                (ExonerationTVAService)context.getBean("exonerationTVAService");
            ParamExonerationCltTva paramExonerationCltTvaRetour = 
                (ParamExonerationCltTva)exonerationTVAService.creatExonerationCltTva(paramExonerationCltTva);
            return (paramExonerationCltTvaRetour);
        }

    }