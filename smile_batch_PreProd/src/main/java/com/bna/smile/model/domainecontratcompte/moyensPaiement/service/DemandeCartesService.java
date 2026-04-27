package com.bna.smile.model.domainecontratcompte.moyensPaiement.service;

import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.AnnulRenouvCarteBancaireTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.DelivranceCarteBancaireTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.DemandeModifPlafondTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.DemandeRemplacementCarteTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.DestructionCarteBancaireTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.EnvoiDrDqmrpTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.EnvoiSccTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.EnvoiScmTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetCarteBancaireTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetCartesEligibleContratTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetDemandeCarteTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetListCartesBancairesTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.GetListDemandesCartesTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.MiseAttenteDemandeCarteTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.ModifPlafondTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.PecDemandeCarteTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.PecDrDqmrpTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.PecSccTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.PecScmTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.ReceptionCarteBancaireTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.RejetDelivCarteTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.RejetDemandeCarteTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.RestitutionCarteBancaireTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.ValidationDemandeCarteTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.VerifDemandeCarteEnCoursTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.VerifEligibiliteCarteTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement.VerifPossedeTypeCarteTrt;
import com.oxia.fwk.core.IValueObject;


public class DemandeCartesService {

    public DemandeCartesService() {
    }
    /**
     * permettant de Vérifier si un type de carte est éligible sur un contrat donné. 
     * @author Ramzi
     * @param TypeCarteCpt
     * @return PrimitiveVO
     * @since 15/06/2007
     * 
     */
     public   IValueObject verifEligibiliteCarte(IValueObject vo) {
         VerifEligibiliteCarteTrt verifEligibiliteCarteTrt = 
             new VerifEligibiliteCarteTrt();
         return (verifEligibiliteCarteTrt.exec(vo));
     }
     
    /**
     * Vérifier s’il existe une demande de carte de même type en cours d’exécution  pour un porteur donné sur un contrat donné.
     * @author Ramzi
     * @param PersonneTypeCarteCpt:: PersonneStrc : le porteur,  TypeCarteCpt : type de carte et contrat
     * @return DemandeCarte
     * @since 19/06/2007
     * 
     */ 
    
    public  IValueObject verifDemandeCarteEnCours(IValueObject vo) {
        VerifDemandeCarteEnCoursTrt verifDemandeCarteEnCoursTrt = 
            new VerifDemandeCarteEnCoursTrt();
        return (verifDemandeCarteEnCoursTrt.exec(vo));
    }
     
      /**
       * Rejeter une demande carte donnée selon operation de rejet:rejet demande, rejet délivrance ; selon le
       * motif de rejet:demande en cours, demande carte de ^meme type, rejet DR/DQMRP...; et selon personnel qui a fait l'opération
       * @author Ramzi
       * @param DemandeCarte:: DemandeCarte aprés modification du : codOperOper: operation de rejet:rejet demande, rejet délivrance et
       * du codMotfMrej; motif de rejet:demande en cours, demande carte de ^meme type, rejet DR/DQMRP...
       * @return DemandeCarte
       * @since 19/06/2007
       * 
       */
     public  IValueObject rejetDemandeCarte(IValueObject vo) {
         RejetDemandeCarteTrt rejetDemandeCarteTrt = 
             new RejetDemandeCarteTrt();
         rejetDemandeCarteTrt.setVerifDomaine(false); 
         return (rejetDemandeCarteTrt.exec(vo));
     }
     
    /**
     * Vérifier s’il existe une carte de type donnée valide pour un porteur donné sur un contrat donné.
     * @author Ramzi
     * @param PersonneTypeCarteCpt
     * @return CarteBancaire
     * @since 21/06/2007
     * 
     */ 
     public  IValueObject verifPossedeTypeCarte(IValueObject vo) {
         VerifPossedeTypeCarteTrt verifPossedeTypeCarte = 
             new VerifPossedeTypeCarteTrt();   
         return (verifPossedeTypeCarte.exec(vo));
     }
     
    /**
     * Prise en charge d’une demande de carte donnée.
     * @author Ramzi
     * @param DemandeCarte
     * @return DemandeCarte
     * @since 21/06/2007
     * 
     */
     public  IValueObject pecDemandeCarte(IValueObject vo) {
         PecDemandeCarteTrt pecDemandeCarteTrt = 
             new PecDemandeCarteTrt();
         return (pecDemandeCarteTrt.exec(vo));
     }
    /**
     * extraire demande carte
     * @author Ramzi
     * @param PrimitiveVO: String numéro de la demande carte 
     * @return DemandeCarte
     * @since 21/06/2007
     * 
     */
     public  IValueObject getDemandeCarte(IValueObject vo) {
         GetDemandeCarteTrt getDemandeCarteTrt = 
             new GetDemandeCarteTrt();
         return (getDemandeCarteTrt.exec(vo));
     }
    /**
     * Valider une demande de carte.
     * @author Ramzi
     * @param DemandeCarte 
     * @return DemandeCarte
     * @since 21/06/2007
     * 
     */
     public  IValueObject validationDemandeCarte(IValueObject vo) {
         ValidationDemandeCarteTrt validationDemandeCarteTrt = 
             new ValidationDemandeCarteTrt();
         return (validationDemandeCarteTrt.exec(vo));
     }
    /**
     * Mettre en attente une demande de carte donnée.
     * @author Ramzi
     * @param DemandeCarte 
     * @return DemandeCarte
     * @since 21/06/2007
     * 
     */
     public  IValueObject miseAttenteDemandeCarte(IValueObject vo) {
         MiseAttenteDemandeCarteTrt miseAttenteDemandeCarteTrt = 
             new MiseAttenteDemandeCarteTrt();
         return (miseAttenteDemandeCarteTrt.exec(vo));
     }
    /**
     * Envoi d’une demande de carte donnée à la DR pour décision
     * @author Ramzi
     * @param DemandeCarte 
     * @return DemandeCarte
     * @since 21/06/2007
     * 
     */
     public  IValueObject envoiDrDqmrp(IValueObject vo) {
         EnvoiDrDqmrpTrt envoiDrDqmrpTrt = 
             new EnvoiDrDqmrpTrt();
         return (envoiDrDqmrpTrt.exec(vo));
     }
    /**
     * Envoi d’une demande de carte donnée à la SCM pour décision
     * @author Ramzi
     * @param DemandeCarte 
     * @return DemandeCarte
     * @since 23/03/2009
     * 
     */
     public  IValueObject envoiScm(IValueObject vo) {
    
         EnvoiScmTrt envoiScmTrt = 
             new EnvoiScmTrt();
         envoiScmTrt.setVerifDomaine(false);
         return (envoiScmTrt.exec(vo));
     }
    /**
     * Envoi d’une demande de carte donnée à la SCC pour décision
     * @author Ramzi
     * @param DemandeCarte 
     * @return DemandeCarte
     * @since 23/03/2009
     * 
     */
     public  IValueObject envoiScc(IValueObject vo) {
         EnvoiSccTrt envoiSccTrt = 
             new EnvoiSccTrt();
         envoiSccTrt.setVerifDomaine(false);
         return (envoiSccTrt.exec(vo));
     }
    /**
     * Prise en charge décision DR/DQMRP
     * @author Ramzi
     * @param DemandeCarte 
     * @return DemandeCarte
     * @since 20/07/2007
     * 
     */
     public  IValueObject pecDrDqmrp(IValueObject vo) {
         PecDrDqmrpTrt pecDrDqmrpTrt = 
             new PecDrDqmrpTrt();
         pecDrDqmrpTrt.setVerifDomaine(false);
         return (pecDrDqmrpTrt.exec(vo));
     }
    
    /**
     * Prise en charge décision SCM
     * @author Ramzi
     * @param DemandeCarte 
     * @return DemandeCarte
     * @since 26/03/2009
     * 
     */
     public  IValueObject pecScm(IValueObject vo) {
         PecScmTrt pecScmTrt = new PecScmTrt();
         pecScmTrt.setVerifDomaine(false);
         return (pecScmTrt.exec(vo));
     }
    /**
     * Prise en charge décision SCC
     * @author Ramzi
     * @param DemandeCarte 
     * @return DemandeCarte
     * @since 26/03/2009
     * 
     */
     public  IValueObject pecScc(IValueObject vo) {
         PecSccTrt pecSccTrt = new PecSccTrt();
         pecSccTrt.setVerifDomaine(false);
         return (pecSccTrt.exec(vo));
     }
    /**
     * Extraire la liste des types de carte qui sont éligible pour un contrat donné. 
     * @author Ramzi
     * @param ContratCpt
     * @return Listes : de TypeCarte
     * @since 28/06/2007
     * 
     */
     public  IValueObject getCartesEligibleContrat(IValueObject vo) {
         GetCartesEligibleContratTrt getCartesEligibleContratTrt = 
             new GetCartesEligibleContratTrt();
         return (getCartesEligibleContratTrt.exec(vo));
     }
     
     /**
      * permet de donner la liste de toutes les demandes de cartes selon critères de recherches
      * @author Ramzi
      * @since 05/07/2007
      * 
      */
     public  IValueObject getListDemandesCartes(IValueObject vo) {
         GetListDemandesCartesTrt getListDemandesCartesTrt = 
             new GetListDemandesCartesTrt();
         return (getListDemandesCartesTrt.exec(vo));
     }
     
    /**
     * permet de donner la liste de toutes les cartes selon critères de recherches
     * @author Ramzi
     * @since 23/07/2007
     * 
     */
    public  IValueObject getListCartesBancaires(IValueObject vo) {
        GetListCartesBancairesTrt getListCartesBancairesTrt = 
            new GetListCartesBancairesTrt();
        return (getListCartesBancairesTrt.exec(vo));
    }
    /**
     * extraire carte bancaire selon numéro carte et retourne 
     * les erreur applicatifs suivantes: contrat non valide, Fin pouvoir sur cet opération
     * @author Ramzi
     * @param PrimitiveVO: String numéro de la demande carte 
     * @return CarteBancaire
     * @since 21/06/2007
     * 
     */
    public  IValueObject getCarteBancaire(IValueObject vo) {
        GetCarteBancaireTrt getCarteBancaireTrt = 
            new GetCarteBancaireTrt();
        return (getCarteBancaireTrt.exec(vo));
    }
    
    /**
     * Recevoir une demande de carte.
     * @author Ramzi
     * @param CarteBancaire 
     * @return CarteBancaire
     * @since 26/07/2007
     * 
     */
     public  IValueObject receptionCarteBancaire(IValueObject vo) {
         ReceptionCarteBancaireTrt receptionCarteBancaireTrt = 
             new ReceptionCarteBancaireTrt();
         return (receptionCarteBancaireTrt.exec(vo));
     }
    /**
     * Rejet suite à une delivrance:carte mal confectionnée ou contrat non valide.
     * @author Ramzi
     * @param CarteBancaire 
     * @return CarteBancaire
     * @since 26/07/2007
     * 
     */
     public  IValueObject rejetDelivCarte(IValueObject vo) {
         RejetDelivCarteTrt rejetDelivCarteTrt = 
             new RejetDelivCarteTrt();
         return (rejetDelivCarteTrt.exec(vo));
     }
    /**
     *Delivrance renouvellement carte.
     * @author Ramzi
     * @param CarteBancaire 
     * @return CarteBancaire
     * @since 26/07/2007
     * 
     */
     public  IValueObject delivranceCarteBancaire(IValueObject vo) {
         DelivranceCarteBancaireTrt delivranceCarteBancaireTrt = 
             new DelivranceCarteBancaireTrt();
         return (delivranceCarteBancaireTrt.exec(vo));
     }
    /**
     * Annulation renouvellement carte.
     * @author Ramzi
     * @param CarteBancaire 
     * @return CarteBancaire
     * @since 26/07/2007
     * 
     */
     public  IValueObject annulRenouvCarteBancaire(IValueObject vo) {
         AnnulRenouvCarteBancaireTrt annulRenouvCarteBancaireTrt = 
             new AnnulRenouvCarteBancaireTrt();
         return (annulRenouvCarteBancaireTrt.exec(vo));
     }
    /**
     * Demande de remplacement carte.
     * @author Ramzi
     * @param CarteBancaire 
     * @return CarteBancaire
     * @since 26/07/2007
     * 
     */
     public  IValueObject demandeRemplacementCarte(IValueObject vo) {
         DemandeRemplacementCarteTrt demandeRemplacementCarteTrt = 
             new DemandeRemplacementCarteTrt();
         return (demandeRemplacementCarteTrt.exec(vo));
     }
    /**
     * Demande de modification plafond carte.
     * @author Ramzi
     * @param CarteBancaire 
     * @return CarteBancaire
     * @since 08/04/2009
     * 
     */
     public  IValueObject demandeModifPlafond(IValueObject vo) {
         DemandeModifPlafondTrt demandeModifPlafondTrt = 
             new DemandeModifPlafondTrt();
         return (demandeModifPlafondTrt.exec(vo));
     }
    /**
     * modification plafond carte.
     * @author Ramzi
     * @param CarteBancaire 
     * @return CarteBancaire
     * @since 18/01/2010
     * 
     */
     public  IValueObject modifPlafond(IValueObject vo) {
         ModifPlafondTrt modifPlafondTrt = 
             new ModifPlafondTrt();
         return (modifPlafondTrt.exec(vo));
     }
   
    /**
     * Restitution carte bancaire.
     * @author Ramzi
     * @param CarteBancaire 
     * @return CarteBancaire
     * @since 26/07/2007
     * 
     */
    public  IValueObject restitutionCarteBancaire(IValueObject vo) {
        RestitutionCarteBancaireTrt restitutionCarteBancaireTrt = 
            new RestitutionCarteBancaireTrt();
        return (restitutionCarteBancaireTrt.exec(vo));
    }
    /**
     * Destruction carte bancaire.
     * @author Ramzi
     * @param CarteBancaire 
     * @return CarteBancaire
     * @since 26/07/2007
     * 
     */
    public  IValueObject destructionCarteBancaire(IValueObject vo) {
        DestructionCarteBancaireTrt destructionCarteBancaireTrt = 
            new DestructionCarteBancaireTrt();
        return (destructionCarteBancaireTrt.exec(vo));
    }
    
     
    
    
    
    
}
