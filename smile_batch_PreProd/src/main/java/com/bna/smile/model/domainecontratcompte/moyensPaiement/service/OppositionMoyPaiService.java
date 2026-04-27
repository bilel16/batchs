package com.bna.smile.model.domainecontratcompte.moyensPaiement.service;

import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.ChargerNatureMoyPaieTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.ChargerTypeMoyPaieTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetListOppositionTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.LeveeOppositionCIBTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.LeveeOppositionChequesTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.LeveeOppositionLivretTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.OppositionBcPlacTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.OppositionCIBTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.OppositionCarteBanqueTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.OppositionCarteTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.OppositionChequesBanqueTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.OppositionChequesTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.OppositionLivretTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;


public class OppositionMoyPaiService  extends BasicService  {
    
  /*  private   OppositionChequesTrt oppositionChequesTrt ;
    private   OppositionChequesBanqueTrt oppositionChequesBanqueTrt ;
    private   LeveeOppositionChequesTrt leveeOppositionChequesTrt ;
    private   OppositionLivretTrt oppositionLivretTrt ;
    private   LeveeOppositionLivretTrt leveeOppositionLivretTrt;
    private   OppositionCIBTrt oppositionCIBTrt;
    private   LeveeOppositionCIBTrt leveeOppositionCIBTrt ;
    private   OppositionCarteTrt oppositionCarteTrt ;
    private   OppositionCarteBanqueTrt oppositionCarteBanqueTrt;
    private   ChargerTypeMoyPaieTrt chargerTypeMoyPaieTrt;
    private   ChargerNatureMoyPaieTrt chargerNatureMoyPaieTrt ;
    private   GetListOppositionTrt getListOppositionTrt ;*/

    public OppositionMoyPaiService() {
    }

     /**
      * Opposition cheque.
      * @author Ramzi
      * @param ParamOpposition
      * @return ParamOpposition
      * @since 28/09/2007
      * 
      */
    public IValueObject oppositionCheques(IValueObject vo){
            OppositionChequesTrt oppositionChequesTrt = new OppositionChequesTrt(); 
            return (oppositionChequesTrt.exec(vo));
    }
    /**
     * Opposition cheque par banque.
     * @author Ramzi
     * @param ParamOpposition
     * @return ParamOpposition
     * @since 28/09/2007
     * 
     */
    public IValueObject oppositionChequesBanque(IValueObject vo) {
       OppositionChequesBanqueTrt oppositionChequesBanqueTrt = new OppositionChequesBanqueTrt();
       return (oppositionChequesBanqueTrt.exec(vo));
    }
    
    /**
     * Levee Opposition cheque.
     * @author Ramzi
     * @param ParamOpposition
     * @return ParamOpposition
     * @since 28/09/2007
     * 
     */
     public IValueObject leveeOppositionCheques(IValueObject vo) {
         LeveeOppositionChequesTrt leveeOppositionChequesTrt = new LeveeOppositionChequesTrt();
         return (leveeOppositionChequesTrt.exec(vo));
     }
     
    /**
     * Opposition livret par client.
     * @author Ramzi
     * @param ParamOpposition
     * @return ParamOpposition
     * @since 28/09/2007
     * 
     */
    public IValueObject oppositionLivret(IValueObject vo) {
       OppositionLivretTrt oppositionLivretTrt = new OppositionLivretTrt();
       return (oppositionLivretTrt.exec(vo));
    }
    /**
     * Levee Opposition livret.
     * @author Ramzi
     * @param ParamOpposition
     * @return ParamOpposition
     * @since 28/09/2007
     * 
     */
     public IValueObject leveeOppositionLivret(IValueObject vo) {
         LeveeOppositionLivretTrt leveeOppositionLivretTrt = new LeveeOppositionLivretTrt();
         return (leveeOppositionLivretTrt.exec(vo));
     }
     
    /**
     * Opposition cib par client.
     * @author Ramzi
     * @param ParamOpposition
     * @return ParamOpposition
     * @since 28/09/2007
     * 
     */
    public IValueObject oppositionCib(IValueObject vo) {
       OppositionCIBTrt oppositionCIBTrt = new OppositionCIBTrt();
       return (oppositionCIBTrt.exec(vo));
    }
    /**
     * Levee Opposition cib.
     * @author Ramzi
     * @param ParamOpposition
     * @return ParamOpposition
     * @since 28/09/2007
     * 
     */
     public IValueObject leveeOppositionCib(IValueObject vo) {
         LeveeOppositionCIBTrt leveeOppositionCIBTrt = new LeveeOppositionCIBTrt();
         return (leveeOppositionCIBTrt.exec(vo));
     }
     
    /**
     * Opposition carte par client.
     * @author Ramzi
     * @param ParamOpposition
     * @return ParamOpposition
     * @since 28/09/2007
     * 
     */
    public IValueObject oppositionCarte(IValueObject vo) {
        OppositionCarteTrt oppositionCarteTrt = new OppositionCarteTrt();
       return (oppositionCarteTrt.exec(vo));
    }
    
    /**
     * Opposition carte par banque.
     * @author Ramzi
     * @param ParamOpposition
     * @return ParamOpposition
     * @since 28/09/2007
     * 
     */
    public IValueObject oppositionCarteBanque(IValueObject vo) {
       OppositionCarteBanqueTrt oppositionCarteBanqueTrt = new OppositionCarteBanqueTrt();
       return (oppositionCarteBanqueTrt.exec(vo));
    }
    /**
     * Opposition BC
     * @author Ramzi
     * @param ParamOpposition
     * @return ParamOpposition
     * @since 09/12/2009
     * 
     */
    public IValueObject oppositionBcPlac(IValueObject vo) {
       OppositionBcPlacTrt oppositionBcPlacTrt = new OppositionBcPlacTrt();
       return (oppositionBcPlacTrt.exec(vo));
    }
     
    /**
     * charger liste des types de moyen de paiement.
     * @author Lamia
     * @return listTypMoyPaie
     * @since 10/04/2007
     * 
     */
    public IValueObject chargerTypMoyPaie(IValueObject vo) {
        ChargerTypeMoyPaieTrt chargerTypeMoyPaieTrt = new ChargerTypeMoyPaieTrt();
        return (chargerTypeMoyPaieTrt.exec(vo));
    } 
    /**
     * charger liste pour nature de moyen de paiement (carte :BNA visa electron,... et chèque: cheque standards, lettre de chèque...).
     * @author Lamia
     * @return listNatureMoyPaie
     * @since 10/04/2007
     * 
     */
    public IValueObject chargerNatureMoyPaie(IValueObject vo) {
        ChargerNatureMoyPaieTrt chargerNatureMoyPaieTrt = new ChargerNatureMoyPaieTrt();
        return (chargerNatureMoyPaieTrt.exec(vo));

    } 
    /**
     * charger liste des oppositions sur moyen de paiement
     * @author Lamia
     * @return 
     * @since 14/04/2007
     * 
     */
    public IValueObject getListOppositions(IValueObject vo) {
        GetListOppositionTrt getListOppositionTrt = new GetListOppositionTrt();
        return (getListOppositionTrt.exec(vo));
    }

}
