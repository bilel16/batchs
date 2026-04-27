package com.bna.smile.model.statistique.model;

import java.util.ArrayList;
import java.util.List;

import com.bna.commun.model.JourneeStructureDomaineId;
import com.bna.smile.model.clotureDomaine.model.StatSouscription;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe des variable de tableau de bord
 * @author Mdimagh Med Lassaad
 * @since 07/05/2008
 */
public class TableauDeBordVo extends ValueObject {

    //--------------------------------
    //------ Structure
    private Long codeStructure = Long.valueOf(0);
    private String  choixRecherche  = "";
    private JourneeStructureDomaineId journeeStructureDomaineId;
    private Long codeProduit= Long.valueOf(0);;
    //----------------------------------
    //------ Souscription
    private List listNombreSouscriptionParTypeContrat = new ArrayList(0);
    private List listNombreSouscriptionParTypeClient = new ArrayList(0);
    private List listNombreSouscAttParTypeClient = new ArrayList(0);
    private List listNombreSignatureParTypeContrat = new ArrayList(0);
    private Long nombreTatalSouscription = Long.valueOf(0);
    private List listSouscriptionAttParTypeContrat = new ArrayList(0);
    private List listSouscriptionAnnParTypeContrat = new ArrayList(0);
    private List listSouscriptionRejParTypeContrat = new ArrayList(0);
    private StatSouscription statSouscription=new StatSouscription();
    private List listDetailSouscrEnAttente = new ArrayList(0);
    private List listDetailSouscrvalide = new ArrayList(0);
    private List listDetailSouscrRej = new ArrayList(0);
    private String etatContrat = "";
    //--------------------------------------
    //------- Mandat
    private Long nombreMandatCree = Long.valueOf(0);
    private List listMandatCreationParTypeContrat = new ArrayList(0);
    private List listMandatRenouvellementParTypeContrat = new ArrayList(0);
    private List listMandatModificationParTypeContrat = new ArrayList(0);
    private List listMandatAnnulationParTypeContrat = new ArrayList(0);
    private List listeDetailCreationMandat = new ArrayList(0);
    private List listeDetailModifMandat = new ArrayList(0);
    private List listeDetailAnnulMandat = new ArrayList(0);
    private List listeDetailRenouvMandat = new ArrayList(0);
    //--------------------------------------
    //------- Siupport moyen de paiement

    private List listenombreChequierDemandeParTypeValide          = new ArrayList(0);
    private List listenombreChequierDemandeParTypeAttente         = new ArrayList(0);
    private List listenombreChequierDemandeParTypeRejete          = new ArrayList(0);
    private List listenombreChequierDemandeParTyperRecu          = new ArrayList(0);
    private List listenombreChequierDemandeParTypeDeliv         = new ArrayList(0);
   //-- carte
    private List listenombreCarteDemandeParType             = new ArrayList(0); // valid�
    private List listenombreCarteDemandeParTypeNonValide    = new ArrayList(0); // non encore valid� : attente et prevalid�
    private List listenombreCarteRecuParType                = new ArrayList(0); //re�us
    private List listenombreCarteDelivreParType             = new ArrayList(0); //d�livr�es
    private List listenombreCarteAnnuleParType              = new ArrayList(0); //annul�es
    private List listenombreCarteRejeteesParType            = new ArrayList(0); //rejet�es  
    
    private Long nombreCarteRecu                = Long.valueOf(0);
    private Long nombreCarteRemis               = Long.valueOf(0);
    private Long nombreCarteDemandeNonValide    = Long.valueOf(0);
    private Long nombreCarteRejetDemande        = Long.valueOf(0);
    private Long nombreCarteDemandeRemplValide  = Long.valueOf(0);
    private Long nombreCarteRemplacee           = Long.valueOf(0);
   
    //--------------------------------------
    //------- Opposition

    private List listeOppositionParType         = new ArrayList(0);
    private List listLeveOppositionParType      = new ArrayList(0);

   
    //--------------------------------------
    //------- Liste modification donn�es

    private List listeModificationDonneeParType         = new ArrayList(0);
    //-------données assurance vie
    private Long nbrOper672;
    private Double mntOper672;
    private Long nbrOper703;
    private Double mntOper703;
    
   
    public TableauDeBordVo() {
    }

    public void setNombreTatalSouscription(Long nombreTatalSouscription) {
        this.nombreTatalSouscription = nombreTatalSouscription;
    }

    public Long getNombreTatalSouscription() {
        return nombreTatalSouscription;
    }

    public void setCodeStructure(Long codeStructure) {
        this.codeStructure = codeStructure;
    }

    public Long getCodeStructure() {
        return codeStructure;
    }

    public void setListNombreSouscriptionParTypeContrat(List listNombreSouscriptionParTypeContrat) {
        this.listNombreSouscriptionParTypeContrat = 
                listNombreSouscriptionParTypeContrat;
    }

    public List getListNombreSouscriptionParTypeContrat() {
        return listNombreSouscriptionParTypeContrat;
    }

    public void setListNombreSouscriptionParTypeClient(List listNombreSouscriptionParTypeClient) {
        this.listNombreSouscriptionParTypeClient = 
                listNombreSouscriptionParTypeClient;
    }

    public List getListNombreSouscriptionParTypeClient() {
        return listNombreSouscriptionParTypeClient;
    }

    public void setListMandatCreationParTypeContrat(List listMandatCreationParTypeContrat) {
        this.listMandatCreationParTypeContrat = 
                listMandatCreationParTypeContrat;
    }

    public List getListMandatCreationParTypeContrat() {
        return listMandatCreationParTypeContrat;
    }

    public void setListMandatRenouvellementParTypeContrat(List listMandatRenouvellementParTypeContrat) {
        this.listMandatRenouvellementParTypeContrat = 
                listMandatRenouvellementParTypeContrat;
    }

    public List getListMandatRenouvellementParTypeContrat() {
        return listMandatRenouvellementParTypeContrat;
    }

    public void setListMandatModificationParTypeContrat(List listMandatModificationParTypeContrat) {
        this.listMandatModificationParTypeContrat = 
                listMandatModificationParTypeContrat;
    }

    public List getListMandatModificationParTypeContrat() {
        return listMandatModificationParTypeContrat;
    }

    public void setListMandatAnnulationParTypeContrat(List listMandatAnnulationParTypeContrat) {
        this.listMandatAnnulationParTypeContrat = 
                listMandatAnnulationParTypeContrat;
    }

    public List getListMandatAnnulationParTypeContrat() {
        return listMandatAnnulationParTypeContrat;
    }

    public void setNombreMandatCree(Long nombreMandatCree) {
        this.nombreMandatCree = nombreMandatCree;
    }

    public Long getNombreMandatCree() {
        return nombreMandatCree;
    }

    public void setListenombreCarteDemandeParType(List listenombreCarteDemandeParType) {
        this.listenombreCarteDemandeParType = listenombreCarteDemandeParType;
    }

    public List getListenombreCarteDemandeParType() {
        return listenombreCarteDemandeParType;
    }

    public void setListenombreCarteDemandeParTypeNonValide(List listenombreCarteDemandeParTypeNonValide) {
        this.listenombreCarteDemandeParTypeNonValide = listenombreCarteDemandeParTypeNonValide;
    }

    public List getListenombreCarteDemandeParTypeNonValide() {
        return listenombreCarteDemandeParTypeNonValide;
    }

    public void setNombreCarteRecu(Long nombreCarteRecu) {
        this.nombreCarteRecu = nombreCarteRecu;
    }

    public Long getNombreCarteRecu() {
        return nombreCarteRecu;
    }

    public void setNombreCarteRemis(Long nombreCarteRemis) {
        this.nombreCarteRemis = nombreCarteRemis;
    }

    public Long getNombreCarteRemis() {
        return nombreCarteRemis;
    }

    public void setNombreCarteRejetDemande(Long nombreCarteRejetDemande) {
        this.nombreCarteRejetDemande = nombreCarteRejetDemande;
    }

    public Long getNombreCarteRejetDemande() {
        return nombreCarteRejetDemande;
    }

    public void setNombreCarteDemandeRemplValide(Long nombreCarteDemandeRemplValide) {
        this.nombreCarteDemandeRemplValide = nombreCarteDemandeRemplValide;
    }

    public Long getNombreCarteDemandeRemplValide() {
        return nombreCarteDemandeRemplValide;
    }

    public void setNombreCarteRemplacee(Long nombreCarteRemplacee) {
        this.nombreCarteRemplacee = nombreCarteRemplacee;
    }

    public Long getNombreCarteRemplacee() {
        return nombreCarteRemplacee;
    }

    public void setListenombreChequierDemandeParTypeAttente(List listenombreChequierDemandeParTypeAttente) {
        this.listenombreChequierDemandeParTypeAttente = listenombreChequierDemandeParTypeAttente;
    }

    public List getListenombreChequierDemandeParTypeAttente() {
        return listenombreChequierDemandeParTypeAttente;
    }

    public void setListenombreChequierDemandeParTypeRejete(List listenombreChequierDemandeParTypeRejete) {
        this.listenombreChequierDemandeParTypeRejete = listenombreChequierDemandeParTypeRejete;
    }

    public List getListenombreChequierDemandeParTypeRejete() {
        return listenombreChequierDemandeParTypeRejete;
    }

    public void setListenombreChequierDemandeParTypeValide(List listenombreChequierDemandeParTypeValide) {
        this.listenombreChequierDemandeParTypeValide = listenombreChequierDemandeParTypeValide;
    }

    public List getListenombreChequierDemandeParTypeValide() {
        return listenombreChequierDemandeParTypeValide;
    }

    public void setListeOppositionParType(List listeOppositionParType) {
        this.listeOppositionParType = listeOppositionParType;
    }

    public List getListeOppositionParType() {
        return listeOppositionParType;
    }

    public void setListLeveOppositionParType(List listLeveOppositionParType) {
        this.listLeveOppositionParType = listLeveOppositionParType;
    }

    public List getListLeveOppositionParType() {
        return listLeveOppositionParType;
    }

    public void setListenombreCarteRecuParType(List listenombreCarteRecuParType) {
        this.listenombreCarteRecuParType = listenombreCarteRecuParType;
    }

    public List getListenombreCarteRecuParType() {
        return listenombreCarteRecuParType;
    }

    public void setListenombreCarteDelivreParType(List listenombreCarteDelivreParType) {
        this.listenombreCarteDelivreParType = listenombreCarteDelivreParType;
    }

    public List getListenombreCarteDelivreParType() {
        return listenombreCarteDelivreParType;
    }

    public void setListenombreCarteAnnuleParType(List listenombreCarteAnnuleParType) {
        this.listenombreCarteAnnuleParType = listenombreCarteAnnuleParType;
    }

    public List getListenombreCarteAnnuleParType() {
        return listenombreCarteAnnuleParType;
    }


    public void setListNombreSignatureParTypeContrat(List listNombreSignatureParTypeContrat) {
        this.listNombreSignatureParTypeContrat = listNombreSignatureParTypeContrat;
    }

    public List getListNombreSignatureParTypeContrat() {
        return listNombreSignatureParTypeContrat;
    }

    public void setNombreCarteDemandeNonValide(Long nombreCarteDemandeNonValide) {
        this.nombreCarteDemandeNonValide = nombreCarteDemandeNonValide;
    }

    public Long getNombreCarteDemandeNonValide() {
        return nombreCarteDemandeNonValide;
    }

    public void setListenombreCarteRejeteesParType(List listenombreCarteRejeteesParType) {
        this.listenombreCarteRejeteesParType = listenombreCarteRejeteesParType;
    }

    public List getListenombreCarteRejeteesParType() {
        return listenombreCarteRejeteesParType;
    }

    public void setListeModificationDonneeParType(List listeModificationDonneeParType) {
        this.listeModificationDonneeParType = listeModificationDonneeParType;
    }

    public List getListeModificationDonneeParType() {
        return listeModificationDonneeParType;
    }


    public void setChoixRecherche(String choixRecherche) {
        this.choixRecherche = choixRecherche;
    }

    public String getChoixRecherche() {
        return choixRecherche;
    }


    public void setListSouscriptionAttParTypeContrat(List listSouscriptionAttParTypeContrat) {
        this.listSouscriptionAttParTypeContrat = listSouscriptionAttParTypeContrat;
    }

    public List getListSouscriptionAttParTypeContrat() {
        return listSouscriptionAttParTypeContrat;
    }

    public void setListSouscriptionAnnParTypeContrat(List listSouscriptionAnnParTypeContrat) {
        this.listSouscriptionAnnParTypeContrat = listSouscriptionAnnParTypeContrat;
    }

    public List getListSouscriptionAnnParTypeContrat() {
        return listSouscriptionAnnParTypeContrat;
    }

   
    public void setStatSouscription(StatSouscription statSouscription) {
        this.statSouscription = statSouscription;
    }

    public StatSouscription getStatSouscription() {
        return statSouscription;
    }

    public void setJourneeStructureDomaineId(JourneeStructureDomaineId journeeStructureDomaineId) {
        this.journeeStructureDomaineId = journeeStructureDomaineId;
    }

    public JourneeStructureDomaineId getJourneeStructureDomaineId() {
        return journeeStructureDomaineId;
    }

    public void setListNombreSouscAttParTypeClient(List listNombreSouscAttParTypeClient) {
        this.listNombreSouscAttParTypeClient = listNombreSouscAttParTypeClient;
    }

    public List getListNombreSouscAttParTypeClient() {
        return listNombreSouscAttParTypeClient;
    }

    public void setListDetailSouscrEnAttente(List listDetailSouscrEnAttente) {
        this.listDetailSouscrEnAttente = listDetailSouscrEnAttente;
    }

    public List getListDetailSouscrEnAttente() {
        return listDetailSouscrEnAttente;
    }

    public void setListDetailSouscrvalide(List listDetailSouscrvalide) {
        this.listDetailSouscrvalide = listDetailSouscrvalide;
    }

    public List getListDetailSouscrvalide() {
        return listDetailSouscrvalide;
    }

    public void setListeDetailCreationMandat(List listeDetailCreationMandat) {
        this.listeDetailCreationMandat = listeDetailCreationMandat;
    }

    public List getListeDetailCreationMandat() {
        return listeDetailCreationMandat;
    }

    public void setListeDetailModifMandat(List listeDetailModifMandat) {
        this.listeDetailModifMandat = listeDetailModifMandat;
    }

    public List getListeDetailModifMandat() {
        return listeDetailModifMandat;
    }

    public void setCodeProduit(Long codeProduit) {
        this.codeProduit = codeProduit;
    }

    public Long getCodeProduit() {
        return codeProduit;
    }

    public void setListSouscriptionRejParTypeContrat(List listSouscriptionRejParTypeContrat) {
        this.listSouscriptionRejParTypeContrat = listSouscriptionRejParTypeContrat;
    }

    public List getListSouscriptionRejParTypeContrat() {
        return listSouscriptionRejParTypeContrat;
    }

    public void setListDetailSouscrRej(List listDetailSouscrRej) {
        this.listDetailSouscrRej = listDetailSouscrRej;
    }

    public List getListDetailSouscrRej() {
        return listDetailSouscrRej;
    }

    public void setEtatContrat(String etatContrat) {
        this.etatContrat = etatContrat;
    }

    public String getEtatContrat() {
        return etatContrat;
    }

    public void setListeDetailAnnulMandat(List listeDetailAnnulMandat) {
        this.listeDetailAnnulMandat = listeDetailAnnulMandat;
    }

    public List getListeDetailAnnulMandat() {
        return listeDetailAnnulMandat;
    }

    public void setListeDetailRenouvMandat(List listeDetailRenouvMandat) {
        this.listeDetailRenouvMandat = listeDetailRenouvMandat;
    }

    public List getListeDetailRenouvMandat() {
        return listeDetailRenouvMandat;
    }

    public void setListenombreChequierDemandeParTyperRecu(List listenombreChequierDemandeParTyperRecu) {
        this.listenombreChequierDemandeParTyperRecu = listenombreChequierDemandeParTyperRecu;
    }

    public List getListenombreChequierDemandeParTyperRecu() {
        return listenombreChequierDemandeParTyperRecu;
    }

    public void setListenombreChequierDemandeParTypeDeliv(List listenombreChequierDemandeParTypeDeliv) {
        this.listenombreChequierDemandeParTypeDeliv = listenombreChequierDemandeParTypeDeliv;
    }

    public List getListenombreChequierDemandeParTypeDeliv() {
        return listenombreChequierDemandeParTypeDeliv;
    }

    public void setNbrOper672(Long nbrOper672) {
        this.nbrOper672 = nbrOper672;
    }

    public Long getNbrOper672() {
        return nbrOper672;
    }

    public void setMntOper672(Double mntOper672) {
        this.mntOper672 = mntOper672;
    }

    public Double getMntOper672() {
        return mntOper672;
    }

    public void setNbrOper703(Long nbrOper703) {
        this.nbrOper703 = nbrOper703;
    }

    public Long getNbrOper703() {
        return nbrOper703;
    }

    public void setMntOper703(Double mntOper703) {
        this.mntOper703 = mntOper703;
    }

    public Double getMntOper703() {
        return mntOper703;
    }
}
