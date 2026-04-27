package com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.util;

import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.Personne;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetPersonneCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCptCmd;
import com.bna.smile.model.domainecommun.model.PersonneCpt;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.dao.DemandeCarteDAO;

import com.oxia.fwk.context.Context;

/**
 * Classe qui represente l'objet DemandeCarte, elle est utilis�e 
 * pour l'affichage dans les pages JSP
 * @author Ramzi
 */
public class DemandeCarteView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {
    private DemandeCarte demandeCarte;    
    private String       typePiecePorteur;  
    private String       etatDemandeCarte;
    private String       typePorteur;
    private String       nomPorteur;
    
       
    public DemandeCarteView() {
    }


    public void setDemandeCarte(DemandeCarte demandeCarte) {
        this.demandeCarte = demandeCarte;
    }

    public DemandeCarte getDemandeCarte() {
        return demandeCarte;
    }

    public void setTypePiecePorteur(String typePiecePorteur) {
        this.typePiecePorteur = typePiecePorteur;
    }

    public String getTypePiecePorteur() {
        if(demandeCarte.getCodTpceDcar() != null){
            Long typePiece = demandeCarte.getCodTpceDcar();
            if(typePiece.equals(Constants.COD_CIN))
                typePiecePorteur="CIN";
            else if (typePiece.equals(Constants.COD_RCS))
                typePiecePorteur="RCS";
            else if (typePiece.equals(Constants.COD_NUM_ORDRE))
                typePiecePorteur="NUM";
        }
        return typePiecePorteur;
    }

    public void setEtatDemandeCarte(String etatDemande) {
        this.etatDemandeCarte = etatDemande;
    }

    public String getEtatDemandeCarte() {
        if(demandeCarte.getCodEtatDcar() != null){
            String etatDcar = demandeCarte.getCodEtatDcar();
            boolean boolModif=false;
            if(demandeCarte.getBoolModpDcar()!=null && demandeCarte.getBoolModpDcar().equals(Long.valueOf("1"))){
                boolModif=true;
            }else{
                boolModif=false;
            }
            
            if(etatDcar.equals(Constants.COD_ETAT_DCAR_Attente))
                if(!boolModif)
                    etatDemandeCarte="Attente";
                else
                    etatDemandeCarte="Attente Pour Modif Plafond";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_AttenteDR))
                if(!boolModif)
                    etatDemandeCarte="Attente DR";
                else
                    etatDemandeCarte="Attente DR Pour Modif Plafond";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_AttenteScm))
                if(!boolModif)
                    etatDemandeCarte="Attente S.Com.Mon�tique";
                else
                    etatDemandeCarte="Attente SCM Pour Modif Plafond";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_AttenteScc))
                if(!boolModif)
                    etatDemandeCarte="Attente S.Com.Cr�dit";
                else
                    etatDemandeCarte="Attente SCC Pour Modif Plafond";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_AttenteGarantie))
                etatDemandeCarte="Attente Constitution Garantie";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_PrevaliderDR))
                if(!boolModif)
                    etatDemandeCarte="Prevalid�e DR";
                else
                    etatDemandeCarte="Prevalid�e DR Pour Modif Plafond";     
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_PrevaliderScm))
                if(!boolModif)
                    etatDemandeCarte="Prevalid�e S.Com.Mon�tique";
                else
                    etatDemandeCarte="Prevalid�e S.Com.Mon�tique Pour Modif Plafond";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_PrevaliderScc))
                if(!boolModif)
                    etatDemandeCarte="Prevalid�e S.Com.Cr�dit";
                else
                    etatDemandeCarte="Prevalid�e S.Com.Cr�dit Pour Modif Plafond";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_Valider))
                if(!boolModif)
                    etatDemandeCarte="Valid�e";
                else
                    etatDemandeCarte="Valid�e Pour Modif Plafond";     
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_CarteRecu))
                etatDemandeCarte="Carte Recu";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_CarteRemis))
                etatDemandeCarte="Carte Remise";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_RejetDelivreCarte))
                etatDemandeCarte="Rejet delivrance";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_RejetDemande))
                etatDemandeCarte="Rejet�e Agence";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_RejetDr))
                etatDemandeCarte="Rejet�e DR";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_RejetScm))
                etatDemandeCarte="Rejet�e S.Com.Mon�tique";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_RejetScc))
                etatDemandeCarte="Rejet�e S.Com.Cr�dit";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_DemandeRempl))
                etatDemandeCarte="Demande Remplacement";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_DemandeRemplValide))
                etatDemandeCarte="Envoy�e pour Remplacement";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_CarteRemplacee))
                etatDemandeCarte="Carte Remplac�e";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_RejetRemplacement))
                etatDemandeCarte="Demande Remplacement Rejet�e";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_DemandeModifPlafond))
                etatDemandeCarte="Demande Modification Plafond";
            else if (etatDcar.equals(Constants.COD_ETAT_DCAR_ModifPlafondRealise))
                etatDemandeCarte="Modif. Plafond Realis�e";
        }
        return etatDemandeCarte;
    }

    public void setTypePorteur(String typePorteur) {
        this.typePorteur = typePorteur;
    }

    public String getTypePorteur() {
        if(demandeCarte.getCodDemDcar() != null){
            String type = demandeCarte.getCodDemDcar();
            if(type.equals(Constants.COD_DEM_DCAR_Titulaire))
                typePorteur="Titulaire";
            else if (type.equals(Constants.COD_DEM_DCAR_Cotitulaire))
                typePorteur="Cotitulaire";
            else if (type.equals(Constants.COD_DEM_DCAR_Mandataire))
                typePorteur="Mandataire";
        }
    
        return typePorteur;
    }

    public void setNomPorteur(String nomPorteur) {
        this.nomPorteur = nomPorteur;
    }

    public String getNomPorteur() {
        if(demandeCarte.getNumPceDcar() != null){
            //------- recherche du porteur
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodTpceTpce(demandeCarte.getCodTpceDcar());
            personneStrc.setNumPcePers(demandeCarte.getNumPceDcar());
            GetPersonneCmd getPersonneCmd = new GetPersonneCmd();
            Personne personne = (Personne)getPersonneCmd.execute(personneStrc);
            
            if (demandeCarte.getCodTpceDcar().equals(Constants.COD_CIN)) { 
                if (personne.getNomNomPers()!=null){
                    nomPorteur = personne.getNomNomPers().replaceAll("  ","");
                }   
                if(personne.getNomPrnPers()!=null){
                    nomPorteur =nomPorteur+" "+personne.getNomPrnPers().replaceAll("  ","");
                }
             //   nomPorteur = personne.getNomNomPers().replaceAll("  ","")+ " " +personne.getNomPrnPers().replaceAll("  ","");
            }else{
                nomPorteur =" ";
              /*  if(demandeCarte.getNumCarDcar() != null) {
                    //recherche du nom de la carte avant migration         
                    String numCarte=demandeCarte.getNumCarDcar().toString();
                    Context context = ContextHandler.getContext();
                    DemandeCarteDAO demandeCarteDAO = 
                        (DemandeCarteDAO)context.getBean("demandeCarteDAO");            
                    nomPorteur=demandeCarteDAO.getNomAncCarte(numCarte);
                }*/
            }
            
        }

        return nomPorteur;
    }
}
