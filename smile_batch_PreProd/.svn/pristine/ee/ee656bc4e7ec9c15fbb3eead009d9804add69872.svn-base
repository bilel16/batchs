package com.bna.smile.web.operationguichet.view;

import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;

public class VersementView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {
private String numeroOperation;
private String dateOperation;
private String libelleDevise;
private String montantDinars;
private String montantDevise;
private String coursPariteOfficielle;
private String numeroCompte;
private String agenceInitiatrice;
private String agenceReceptrice;
private String caisse;

private OperationMoyPay operationMoyPay;
    public VersementView() {
    }



    public void setMontantDinars(String montantDinars) {
        this.montantDinars = montantDinars;
    }

    public String getMontantDinars() {
        if (operationMoyPay != null ){
            //String montant = StrHandler.formatmnt(Double.valueOf(operationMoyPay.getMontDinOmp()));
             String montant = StrHandler.formatMontant(operationMoyPay.getMontDinOmp(),3);
             return montant;
         }else {
            return "";
        }
    }

    public void setMontantDevise(String montantDevise) {
        this.montantDevise = montantDevise;
    }

    public String getMontantDevise() {
      if (operationMoyPay != null && operationMoyPay.getMontDevOmp()!= null ){
        String montant = StrHandler.formatMontant(operationMoyPay.getMontDevOmp().longValue(),operationMoyPay.getDevise().getNbrDecDev().longValue());
        return montant;
      }else{
        return montantDevise;
      }
    }

    public void setCoursPariteOfficielle(String coursPariteOfficielle) {
        this.coursPariteOfficielle = coursPariteOfficielle;
    }

    public String getCoursPariteOfficielle() {
        return coursPariteOfficielle;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getNumeroCompte() {
        if (operationMoyPay != null ){
         StringBuffer numero = new StringBuffer();
         numero.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3));
         numero.append(" ");
         numero.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().toString() ,'0',4));
         numero.append(" ");
         numero.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getNumCcptCcpt().toString() ,'0',6));
         return numero.toString();
        }else {
         return "";
        }
    
    }

    public void setOperationMoyPay(OperationMoyPay operationMoyPay) {
        this.operationMoyPay = operationMoyPay;
    }

    public OperationMoyPay getOperationMoyPay() {
        return operationMoyPay;
    }

    public void setLibelleDevise(String libelleDevise) {
        this.libelleDevise = libelleDevise;
    }

    public String getLibelleDevise() {
     if (operationMoyPay != null ){
      // GetDeviseCmd getDevise = GetDeviseCmd();
        String devise = operationMoyPay.getDevise().getCodDevDev() +"-"+operationMoyPay.getDevise().getLibSiglDev();
        return devise;
     }else {
       return "";
     }
    }

    public void setDateOperation(String dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getDateOperation() {
     if (operationMoyPay != null ){
      return(DateHandler.dateToStr(operationMoyPay.getDatOperOmp()));
     }else {
      return dateOperation;
     }
    }

    public void setNumeroOperation(String numeroOperation) {
        this.numeroOperation = numeroOperation;
    }

    public String getNumeroOperation() {
    if (operationMoyPay != null ){
        return operationMoyPay.getNumOperOmp();
    }else{
        return "";
    }
    }

    public void setAgenceInitiatrice(String agenceInitiatrice) {
        this.agenceInitiatrice = agenceInitiatrice;
    }

    public String getAgenceInitiatrice() {
        if (operationMoyPay != null && operationMoyPay.getStructureInitiatrice()!=null 
         &&  operationMoyPay.getStructureInitiatrice().getCodStrcStrc()!=null 
          && (!operationMoyPay.getStructureInitiatrice().getCodStrcStrc().equals(""))){
        return operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString() + " : "+ operationMoyPay.getStructureInitiatrice().getLibStrcStrc();
        }else{
        return "";
        }
    }

    public void setAgenceReceptrice(String agenceReceptrice) {
        this.agenceReceptrice = agenceReceptrice;
    }

    public String getAgenceReceptrice() {
        if (operationMoyPay != null && operationMoyPay.getStructureReceptrice()  !=null
        &&  operationMoyPay.getStructureReceptrice().getCodStrcStrc()!=null 
         && (!operationMoyPay.getStructureReceptrice().getCodStrcStrc().equals(""))){
        return operationMoyPay.getStructureReceptrice().getCodStrcStrc().toString() + " : "+ operationMoyPay.getStructureReceptrice().getLibStrcStrc();
        }
        return "";
    }

    public void setCaisse(String caisse) {
        this.caisse = caisse;
    }

    public String getCaisse() {
        if (operationMoyPay != null && operationMoyPay.getAffectationCaisseStructure() != null ){
            String text = operationMoyPay.getAffectationCaisseStructure().getCodCaisAc() + " "+
            operationMoyPay.getAffectationCaisseStructure().getLibCaisAc();
            return text;
        }
        return caisse;
    }
}
