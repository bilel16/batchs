package com.bna.smile.web.commun.util;

import com.bna.commun.model.PieceAnnexe;

import java.util.Date;

public class PieceAnnexeView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {
    private PieceAnnexe pieceAnnexe;
    private String codePiece;
    private String libellePiece;
    private String numeroPiece;
    private String dateDelivrance;
    private String dateFinValidite;
    private String codeGouvernorat;
    private String libelleGouvernorat;

    public PieceAnnexeView() {
    }

    public void setPieceAnnexe(PieceAnnexe pieceAnnexe) {
        this.pieceAnnexe = pieceAnnexe;
    }

    public PieceAnnexe getPieceAnnexe() {
        return pieceAnnexe;
    }


    public void setDateDelivrance(String dateDelivrance) {
        this.dateDelivrance = dateDelivrance;
    }

    public String getDateDelivrance() {
        return dateDelivrance;
    }

    public void setDateFinValidite(String dateFinValidite) {
        this.dateFinValidite = dateFinValidite;
    }

    public String getDateFinValidite() {
        return dateFinValidite;
    }

    public void setCodePiece(String codePiece) {
        this.codePiece = codePiece;
    }

    public String getCodePiece() {
        return codePiece;
    }

    public void setLibellePiece(String libellePiece) {
        this.libellePiece = libellePiece;
    }

    public String getLibellePiece() {
        return libellePiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        this.numeroPiece = numeroPiece;
    }

    public String getNumeroPiece() {
        return numeroPiece;
    }

    public void setCodeGouvernorat(String codeGouvernorat) {
        this.codeGouvernorat = codeGouvernorat;
    }

    public String getCodeGouvernorat() {
        return codeGouvernorat;
    }

    public void setLibelleGouvernorat(String libelleGouvernorat) {
        this.libelleGouvernorat = libelleGouvernorat;
    }

    public String getLibelleGouvernorat() {
        return libelleGouvernorat;
    }
}
