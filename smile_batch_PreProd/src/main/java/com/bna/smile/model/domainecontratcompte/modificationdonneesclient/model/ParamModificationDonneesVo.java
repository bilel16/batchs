package com.bna.smile.model.domainecontratcompte.modificationdonneesclient.model;

import java.util.Date;

import com.bna.commun.model.Client;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Personne;
import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.model.TypeModification;
import com.oxia.fwk.core.ValueObject;

/**
 * Classe pour la modification des données client
 * elle contient les objet à modifier comme Personne
 * et le type de modififcation à effectuer
 * @author Mdimagh Med Lassaad
 * @since  31/06/07 
 */
public class ParamModificationDonneesVo extends ValueObject {
    private Personne personneModifie;
    private Client clientModifie;
    private TypeModification typeModification;
    private String matriculeUser;
    private Long codeStructure;
    private ContratCpt contratModifie;
    private String     typePersonneAvecContrat = new String();
    private PieceAnnexe anciennePieceAnnexe;
    private PieceAnnexe nouvellePieceAnnexe;

    private String numeroPieceAnnexeAncien;
    private String numeroPieceAnnexenouvelle;
    private Long codeTypePieceAnnexe;
    private Long numSeqPesAnnexe;
    private Date dateDelivranceAnnexe;
    private Date dateFinValiditeAnnexe;
    private Long codGouveroratAnnxe;
        
    public ParamModificationDonneesVo() {
    }

    public void setPersonneModifie(Personne personneModifie) {
        this.personneModifie = personneModifie;
    }

    public Personne getPersonneModifie() {
        return personneModifie;
    }

    public void setMatriculeUser(String matriculeUser) {
        this.matriculeUser = matriculeUser;
    }

    public String getMatriculeUser() {
        return matriculeUser;
    }


    public void setTypeModification(TypeModification typeModification) {
        this.typeModification = typeModification;
    }

    public TypeModification getTypeModification() {
        return typeModification;
    }

    public void setContratModifie(ContratCpt contratModifie) {
        this.contratModifie = contratModifie;
    }

    public ContratCpt getContratModifie() {
        return contratModifie;
    }

    public void setAnciennePieceAnnexe(PieceAnnexe anciennePieceAnnexe) {
        this.anciennePieceAnnexe = anciennePieceAnnexe;
    }

    public PieceAnnexe getAnciennePieceAnnexe() {
        return anciennePieceAnnexe;
    }

    public void setNouvellePieceAnnexe(PieceAnnexe nouvellePieceAnnexe) {
        this.nouvellePieceAnnexe = nouvellePieceAnnexe;
    }

    public PieceAnnexe getNouvellePieceAnnexe() {
        return nouvellePieceAnnexe;
    }

    public void setClientModifie(Client clientModifie) {
        this.clientModifie = clientModifie;
    }

    public Client getClientModifie() {
        return clientModifie;
    }

    public void setNumeroPieceAnnexeAncien(String numeroPieceAnnexeAncien) {
        this.numeroPieceAnnexeAncien = numeroPieceAnnexeAncien;
    }

    public String getNumeroPieceAnnexeAncien() {
        return numeroPieceAnnexeAncien;
    }

    public void setNumeroPieceAnnexenouvelle(String numeroPieceAnnexenouvelle) {
        this.numeroPieceAnnexenouvelle = numeroPieceAnnexenouvelle;
    }

    public String getNumeroPieceAnnexenouvelle() {
        return numeroPieceAnnexenouvelle;
    }

    public void setCodeTypePieceAnnexe(Long codeTypePieceAnnexe) {
        this.codeTypePieceAnnexe = codeTypePieceAnnexe;
    }

    public Long getCodeTypePieceAnnexe() {
        return codeTypePieceAnnexe;
    }

    public void setNumSeqPesAnnexe(Long numSeqPesAnnexe) {
        this.numSeqPesAnnexe = numSeqPesAnnexe;
    }

    public Long getNumSeqPesAnnexe() {
        return numSeqPesAnnexe;
    }

    public void setDateDelivranceAnnexe(Date dateDelivranceAnnexe) {
        this.dateDelivranceAnnexe = dateDelivranceAnnexe;
    }

    public Date getDateDelivranceAnnexe() {
        return dateDelivranceAnnexe;
    }

    public void setDateFinValiditeAnnexe(Date dateFinValiditeAnnexe) {
        this.dateFinValiditeAnnexe = dateFinValiditeAnnexe;
    }

    public Date getDateFinValiditeAnnexe() {
        return dateFinValiditeAnnexe;
    }

    public void setCodGouveroratAnnxe(Long codGouveroratAnnxe) {
        this.codGouveroratAnnxe = codGouveroratAnnxe;
    }

    public Long getCodGouveroratAnnxe() {
        return codGouveroratAnnxe;
    }

    public void setCodeStructure(Long codeStructure) {
        this.codeStructure = codeStructure;
    }

    public Long getCodeStructure() {
        return codeStructure;
    }


    public void setTypePersonneAvecContrat(String typePersonneAvecContrat) {
        this.typePersonneAvecContrat = typePersonneAvecContrat;
    }

    public String getTypePersonneAvecContrat() {
        return typePersonneAvecContrat;
    }
}
