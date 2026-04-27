package com.bna.smile.model.domainecaisse.service;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecaisse.traitement.CreationCaisseTrt;
import com.bna.smile.model.domainecaisse.traitement.CreationCaisseVacationTrt;
import com.bna.smile.model.domainecaisse.traitement.GetDetailCaisseStructureTrt;
import com.bna.smile.model.domainecaisse.traitement.GetListJourneeCaisseTrt;
import com.bna.smile.model.domainecaisse.traitement.GetListMouvementCaisseTrt;
import com.bna.smile.model.domainecaisse.traitement.GetListSessionJrnCaisseTrt;
import com.bna.smile.model.domainecaisse.traitement.GetSituationCaisseCentraleTrt;
import com.bna.smile.model.domainecaisse.traitement.InsertListMouvementSessionCaisseTrt;
import com.bna.smile.model.domainecaisse.traitement.InsertMouvementCaisseTrt;
import com.bna.smile.model.domainecaisse.traitement.UpdateListDetailsSessionCaissesMouvementCaisseTrt;
import com.bna.smile.model.domainecaisse.traitement.UpdateListMouvementCaisseTrt;
import com.bna.smile.model.domainecaisse.traitement.UpdateSessionJrnCaisseTrt;
import com.bna.smile.model.domainecaisse.traitement.ValidAlimentationCaisseTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;


public class CaisseService extends BasicService{
    public Context context = ContextHandler.getContext();
    public CaisseService() {
    }
    
    /**
     * Methode qui permet l'ajout d'un nouvelle Caisseà une structure
     * 
     * @param vo
     * @return
     */
    public IValueObject creationCaisse(IValueObject vo) {

       CreationCaisseTrt creationCaisseTrt = new CreationCaisseTrt();
        return (creationCaisseTrt.exec(vo));
    }
    
    /**
     * Methode qui permet la recherche de la situation de la caisse centrale
     * 
     * @param vo
     * @return
     */
    public IValueObject GetSituationCaisseCentrale(IValueObject vo) {

       GetSituationCaisseCentraleTrt getSituationCaisseCentraleTrt = 
            new GetSituationCaisseCentraleTrt();
        return (getSituationCaisseCentraleTrt.exec(vo));
    }
    
    /**
     * Methode qui permet de retourner la liste des caisses d'une structure pour une journée
     * JERBI Lamia
     * @param vo ListeCaisseStructureVo
     * @return ListeCaisseStructureVo
     */
    public IValueObject GetListeSessionJrnCaisse(IValueObject vo) {

       GetListSessionJrnCaisseTrt getListSessionJrnCaisseTrt = new GetListSessionJrnCaisseTrt();
        getListSessionJrnCaisseTrt.setSecurityFlag(false);
        return (getListSessionJrnCaisseTrt.exec(vo));
    }
    
    /**
     * Methode qui permet d'inserer un mouvement caisse
     * 
     * @param vo MouvementsCaisses
     * @return   MouvementsCaisses
     */
    public IValueObject insertMouvementsCaisses(IValueObject vo) {

        InsertMouvementCaisseTrt insertMouvementCaisseTrt = 
            new InsertMouvementCaisseTrt();
        //insertMouvementCaisseTrt.setSecurityFlag(false);
        return (insertMouvementCaisseTrt.exec(vo));
    }
    
    /**
     * Methode qui permet de rechercher le dernier détail caisse structure
     * 
     * @param vo situationDetailCaisseStructureVo
     * @return   situationDetailCaisseStructureVo
     */
    public IValueObject getDetailCaisseStructure(IValueObject vo) {

        GetDetailCaisseStructureTrt getDetailCaisseStructureTrt = 
            new GetDetailCaisseStructureTrt();
        getDetailCaisseStructureTrt.setSecurityFlag(false);
        return (getDetailCaisseStructureTrt.exec(vo));
    }
    
    /**
     * Methode qui permet de maj SessionJrnCaisse
     * 
     * @param vo SessionJrnCaisse
     * @return   SessionJrnCaisse
     */
    public IValueObject updateSessionJrnCaisse(IValueObject vo) {

        UpdateSessionJrnCaisseTrt updateSessionJrnCaisseTrt = new UpdateSessionJrnCaisseTrt();
        updateSessionJrnCaisseTrt.setSecurityFlag(false);
        return (updateSessionJrnCaisseTrt.exec(vo));
    }

    /**
     * Methode qui permet la creation d'une SessionJrnCaisse caisse de vacation
     * 
     * @param vo SessionJrnCaissePrVac
     * @return   SessionJrnCaissePrVac
     */
    public IValueObject creationCaisseVacation(IValueObject vo) {

        CreationCaisseVacationTrt creationCaisseVacationTrt = new CreationCaisseVacationTrt();
        creationCaisseVacationTrt.setSecurityFlag(false);
        return (creationCaisseVacationTrt.exec(vo));
    }

    /**
     * Methode qui permet la recherche des mouvements d'une caisse
     * 
     * @param vo 
     * @return   
     */
    public IValueObject getListMvtCaisse(IValueObject vo) {

        GetListMouvementCaisseTrt getListMouvementCaisseTrt = new GetListMouvementCaisseTrt();
        return (getListMouvementCaisseTrt.exec(vo));
    }

    /**
     * Methode qui permet la creation d'une listes de mouvement caisse
     * 
     * @param vo Listes
     * @return   Listes
     */
    public IValueObject insertListMouvementSessionCaisse(IValueObject vo) {

        InsertListMouvementSessionCaisseTrt insertListMouvementSessionCaisseTrt = new InsertListMouvementSessionCaisseTrt();
        insertListMouvementSessionCaisseTrt.setSecurityFlag(false);
        return (insertListMouvementSessionCaisseTrt.exec(vo));
    }

    
    /**
     * Methode qui permet la MAJ d'une listes de mouvement caisse
     * 
     * @param vo Listes
     * @return   Listes
     */
    public IValueObject updateListMouvementCaisse(IValueObject vo) {

        UpdateListMouvementCaisseTrt updateListMouvementCaisseTrt = new UpdateListMouvementCaisseTrt();
        updateListMouvementCaisseTrt.setSecurityFlag(false);
        return (updateListMouvementCaisseTrt.exec(vo));
    }
    
    
    
    public IValueObject validAlimInterCaisse(IValueObject vo) {
        ValidAlimentationCaisseTrt validAlimentationCaisseTrt = new ValidAlimentationCaisseTrt();
        return (validAlimentationCaisseTrt.exec(vo));
    }


    /**
     * Methode qui permet la MAJ d'une listes de mouvement caisse
     * et d'une liste des DetailsSessionCaisse
     * @param vo Listes
     * @return   Listes
     */
    public IValueObject updateListDetailsSessionCaissesMouvementCaisse (IValueObject vo) {

        UpdateListDetailsSessionCaissesMouvementCaisseTrt updateListDetailsSessionCaissesMouvementCaisseTrt = new UpdateListDetailsSessionCaissesMouvementCaisseTrt();
        updateListDetailsSessionCaissesMouvementCaisseTrt.setSecurityFlag(false);
        return (updateListDetailsSessionCaissesMouvementCaisseTrt.exec(vo));
    }
    
    /**
     * Methode qui permet la recherche journée caisse 
     * 
     * @param vo 
     * @return   
     */
    public IValueObject getListJourneeCaisse(IValueObject vo) {

        GetListJourneeCaisseTrt getListJourneeCaisseTrt = new GetListJourneeCaisseTrt();
        return (getListJourneeCaisseTrt.exec(vo));
    }
}
