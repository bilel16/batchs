package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.commande;

import org.apache.log4j.Logger;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecontratcompte.modificationdonneesclient.service.ModificationDonneesService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

/**
 * Cette classe permet d'extraire la liste des pi_èce annexe d'une personne par sont clé primaire
 * @author Mdimagh Med Lassaad
 * @since 02/06/2008
 */
public class GetListPieceAnnexeParNumSeqPersCmd {
    public GetListPieceAnnexeParNumSeqPersCmd() {
    }
    static Logger logger = Logger.getLogger(GetListPieceAnnexeParNumSeqPersCmd.class);
    public IValueObject execute(IValueObject vo) {
        logger.info("Entree GetListPieceAnnexeParNumSeqPersCmd");
        Context context = ContextHandler.getContext();
        ModificationDonneesService modificationDonneesService = 
            (ModificationDonneesService)context.getBean("modificationDonneesService");

        return (modificationDonneesService.getListeDesPiecesAnnexes(vo));
      //  logger.info("Sortie GetListPieceAnnexeParNumSeqPersCmd");
        
    }
}
