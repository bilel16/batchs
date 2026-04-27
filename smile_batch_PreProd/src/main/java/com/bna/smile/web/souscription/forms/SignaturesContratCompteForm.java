package com.bna.smile.web.souscription.forms;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SignaturesContratCompteForm extends ActionForm {
    private String codeTraitement;
    private String titre;
    private String operation;
    private String codStrcStrc;
    private String codPrdPrd;
    private String numCcptCcpt;
    private String cleCompte;
    private String typePieceSign;
    private String numPieceSign;
    private String nomPrenom;
    private String pouvoir;
    private String alert;
    private String reqCode;

    private String etatCapture;


    /**Reset all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }


    public void clearForm(HttpServletRequest request) {
        //titre = "";
        //operation = "";
        //codStrcStrc = "";
        codPrdPrd = "";
        numCcptCcpt = "";
        cleCompte = "";
        numPieceSign = "";
        nomPrenom = "";
        pouvoir = "";
        alert = "";
        request.getSession().setAttribute("bufferedImageFr",null);
        request.getSession().setAttribute("bufferedImageAr",null);
        etatCapture = "";
    }

    /**Validate all properties to their default values.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return ActionErrors A list of all errors found.
     */
    public ActionErrors validate(ActionMapping mapping, 
                                 HttpServletRequest request) {
        return super.validate(mapping, request);
    }

    public void setCodStrcStrc(String codStrcStrc) {
        this.codStrcStrc = codStrcStrc;
    }

    public String getCodStrcStrc() {
        return codStrcStrc;
    }

    public void setCodPrdPrd(String codPrdPrd) {
        this.codPrdPrd = codPrdPrd;
    }

    public String getCodPrdPrd() {
        return codPrdPrd;
    }

    public void setNumCcptCcpt(String numCcptCcpt) {
        this.numCcptCcpt = numCcptCcpt;
    }

    public String getNumCcptCcpt() {
        return numCcptCcpt;
    }

    public void setCleCompte(String cleCompte) {
        this.cleCompte = cleCompte;
    }

    public String getCleCompte() {
        return cleCompte;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTitre() {
        return titre;
    }

    public void setPouvoir(String pouvoir) {
        this.pouvoir = pouvoir;
    }

    public String getPouvoir() {
        return pouvoir;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setEtatCapture(String etatCapture) {
        this.etatCapture = etatCapture;
    }

    public String getEtatCapture() {
        return etatCapture;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public void setTypePieceSign(String typePieceSign) {
        this.typePieceSign = typePieceSign;
    }

    public String getTypePieceSign() {
        return typePieceSign;
    }

    public void setNumPieceSign(String numPieceSign) {
        this.numPieceSign = numPieceSign;
    }

    public String getNumPieceSign() {
        return numPieceSign;
    }

    public void setCodeTraitement(String codeTraitement) {
        this.codeTraitement = codeTraitement;
    }

    public String getCodeTraitement() {
        return codeTraitement;
    }
}
