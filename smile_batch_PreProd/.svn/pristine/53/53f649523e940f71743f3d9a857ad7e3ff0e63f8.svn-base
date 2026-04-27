package com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.util;

import com.bna.commun.model.CarteBancaire;
import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.Personne;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetPersonneCmd;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.dao.DemandeCarteDAO;

import com.oxia.fwk.context.Context;

/**
 * Classe qui represente l'objet CarteBancaire, elle est utilisée 
 * pour l'affichage dans les pages JSP
 * @author Ramzi
 */
public class CarteBancaireView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {
    private CarteBancaire carteBancaire;    
    private String       typePiecePorteur;  
    private String       etatCarteBancaire;
    private String       typePorteur;
    private String       numCarteBancaire;
    private String       nomPorteur;
    
       
    public CarteBancaireView() {
    }

    public void setTypePiecePorteur(String typePiecePorteur) {
        this.typePiecePorteur = typePiecePorteur;
    }

    public String getTypePiecePorteur() {
        if(carteBancaire.getDemandeCarte().getCodTpceDcar() != null){
            Long typePiece = carteBancaire.getDemandeCarte().getCodTpceDcar();
            if(typePiece.equals(Constants.COD_CIN))
                typePiecePorteur="CIN";
            else if (typePiece.equals(Constants.COD_RCS))
                typePiecePorteur="RCS";
            else if (typePiece.equals(Constants.COD_NUM_ORDRE))
                typePiecePorteur="NUM";
        }
        return typePiecePorteur;
    }

    public void setEtatCarteBancaire(String etatDemande) {
        this.etatCarteBancaire = etatDemande;
    }

    public String getEtatCarteBancaire() {
        if(carteBancaire.getCodEtatCarb() != null){
            String etatcar = carteBancaire.getCodEtatCarb();
            if(etatcar.equals(Constants.COD_ETAT_CARB_CarteCree))
                etatCarteBancaire="Crée";
            else if (etatcar.equals(Constants.COD_ETAT_CARB_CarteDetruite))
                etatCarteBancaire="Détruite";
            else if (etatcar.equals(Constants.COD_ETAT_CARB_CarteMalConfect))
                etatCarteBancaire="Mal confectionnée";
            else if (etatcar.equals(Constants.COD_ETAT_CARB_CarteRecu))
                etatCarteBancaire="Reçue";
            else if (etatcar.equals(Constants.COD_ETAT_CARB_CarteRemise))
                etatCarteBancaire="En circulation";
            else if (etatcar.equals(Constants.COD_ETAT_CARB_CarteRemplacee))
                etatCarteBancaire="Remplacée";
            else if (etatcar.equals(Constants.COD_ETAT_CARB_CarteRestituee))
                etatCarteBancaire="Restituée";
            else if (etatcar.equals(Constants.COD_ETAT_CARB_RejetDelivreCarte))
                etatCarteBancaire="Rejetée";
            else if (etatcar.equals(Constants.COD_ETAT_CARB_EnOpposition))
                etatCarteBancaire="En Opposition";
            else if (etatcar.equals(Constants.COD_ETAT_CARB_AnnulMonetique))
                etatCarteBancaire="Annulée Hors Agence";
                
            
           
            
        }
        return etatCarteBancaire;
    }

    public void setTypePorteur(String typePorteur) {
        this.typePorteur = typePorteur;
    }

    public String getTypePorteur() {
        if(carteBancaire.getDemandeCarte().getCodDemDcar() != null){
            String type = carteBancaire.getDemandeCarte().getCodDemDcar();
            if(type.equals(Constants.COD_DEM_DCAR_Titulaire))
                typePorteur="Titulaire";
            else if (type.equals(Constants.COD_DEM_DCAR_Cotitulaire))
                typePorteur="Cotitulaire";
            else if (type.equals(Constants.COD_DEM_DCAR_Mandataire))
                typePorteur="Mandataire";
        }
    
        return typePorteur;
    }

    public void setCarteBancaire(CarteBancaire carteBancaire) {
        this.carteBancaire = carteBancaire;
    }

    public CarteBancaire getCarteBancaire() {
        return carteBancaire;
    }

    public void setNumCarteBancaire(String numCarteBancaire) {
        this.numCarteBancaire = numCarteBancaire;
    }

    public String getNumCarteBancaire() {
        return carteBancaire.getCarteBancaireId().getCodBinTcar().toString()+StrHandler.lpad(carteBancaire.getCarteBancaireId().getNumCarbCarb().toString(),'0',10);
    }

    public void setNomPorteur(String nomPorteur) {
        this.nomPorteur = nomPorteur;
    }

    public String getNomPorteur() {
        DemandeCarte demandeCarte = carteBancaire.getDemandeCarte();
        if(demandeCarte != null && demandeCarte.getNumPceDcar() != null){
            //------- recherche du porteur
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodTpceTpce(demandeCarte.getCodTpceDcar());
            personneStrc.setNumPcePers(demandeCarte.getNumPceDcar());
            GetPersonneCmd getPersonneCmd = new GetPersonneCmd();
            Personne personne = (Personne)getPersonneCmd.execute(personneStrc);
            
            if (demandeCarte.getCodTpceDcar().equals(Constants.COD_CIN)) { 
                nomPorteur = personne.getNomNomPers().replaceAll("  ","")+ " " +personne.getNomPrnPers().replaceAll("  ","");
            }else{
                if(demandeCarte.getNumCarDcar() != null) {
                    //recherche du nom de la carte avant migration         
                    String numCarte=demandeCarte.getNumCarDcar().toString();
                    Context context = ContextHandler.getContext();
                    DemandeCarteDAO demandeCarteDAO = 
                        (DemandeCarteDAO)context.getBean("demandeCarteDAO");            
                    nomPorteur=demandeCarteDAO.getNomAncCarte(numCarte);
                }
            }
            
        }

        return nomPorteur;
    }
}
