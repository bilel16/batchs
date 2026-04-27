package com.bna.smile.web.operationguichet.view;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;

public class ContratView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {

    private String codStrcStrc;
    private String codPrdPrd;
    private String numCcptCcpt;
    private String cle;
    private String etatContrat;
    private String nomIntiCcpt;
    private String codEtatCcpt;
    private String montSoldCcpt;
    private String sensSolde;
    private String libDevDev;
    private String montBlocCcpt;
    private String montAutCcpt;

    private String messageContratCpt;
    private ContratCpt contratCpt = new ContratCpt();
    

    private String montSoldFCcpt;


    public ContratView() {
    
     /* codStrcStrc = "";
      codPrdPrd = "";
      numCcptCcpt = "";
      cle = "";
   */
      etatContrat = "";
      nomIntiCcpt = "";
      codEtatCcpt = "";
      montSoldCcpt = "";
      sensSolde = "";
      libDevDev = "";
      montBlocCcpt = "";
      montAutCcpt = "";
      messageContratCpt = "";
      contratCpt = new ContratCpt();
    }

    public void setNomIntiCcpt(String nomIntiCcpt) {
        this.nomIntiCcpt = nomIntiCcpt;
    }

    public String getNomIntiCcpt() {
        if(contratCpt.getContratCptId() != null){
            nomIntiCcpt = contratCpt.getNomIntiCcpt();
        }
        return nomIntiCcpt;
    }

    public void setCodEtatCcpt(String codEtatCcpt) {
        this.codEtatCcpt = codEtatCcpt;
    }

    public String getCodEtatCcpt() {
        if(contratCpt.getContratCptId() != null){
            codEtatCcpt = contratCpt.getCodEtatCcpt();
        }
        return codEtatCcpt;
    }

    public void setMontSoldCcpt(String montSoldCcpt) {
        this.montSoldCcpt = montSoldCcpt;
    }

    public String getMontSoldCcpt() {
        if(contratCpt.getContratCptId() != null){
            if(contratCpt.getMontSoldCcpt() != null){
                montSoldCcpt = StrHandler.formatmnt(Math.abs(contratCpt.getMontSoldCcpt().doubleValue()));
            }else {
                montSoldCcpt = "0.000"; 
            }
        }
        return montSoldCcpt;
        
    }

    public void setSensSolde(String sensSolde) {
        this.sensSolde = sensSolde;
    }

    public String getSensSolde() {
        if(contratCpt.getContratCptId() != null){
                if (contratCpt.getMontSoldCcpt().longValue()<0){
                    sensSolde = "DB"; 
                }else {
                    sensSolde = "CR";
                }
        }
        return sensSolde;
    }

    public void setLibDevDev(String libDevDev) {
        this.libDevDev = libDevDev;
    }

    public String getLibDevDev() {
        if(contratCpt.getContratCptId() != null){
            libDevDev = contratCpt.getDevise().getLibDevDev();
        }
        return libDevDev;
    }

    public void setMontBlocCcpt(String montBlocCcpt) {
        this.montBlocCcpt = montBlocCcpt;
    }

    public String getMontBlocCcpt() {
        if(contratCpt.getContratCptId() != null){
            if(contratCpt.getMontBlocCcpt() != null){
                montBlocCcpt = StrHandler.formatmnt(Math.abs(contratCpt.getMontBlocCcpt().doubleValue()));
            }else {
                montBlocCcpt = "0.000"; 
            }
        }
        return montBlocCcpt;
    }

    public void setMontAutCcpt(String montAutCcpt) {
        this.montAutCcpt = montAutCcpt;
    }

    public String getMontAutCcpt() {
        if(contratCpt.getContratCptId() != null){
            if(contratCpt.getMontAutCcpt() != null){
                montAutCcpt = StrHandler.formatmnt(Math.abs(contratCpt.getMontAutCcpt().doubleValue()));
            }else {
                montAutCcpt = "0.000"; 
            }
        }
        return montAutCcpt;
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

    public void setCle(String cle) {
        this.cle = cle;
    }

    public String getCle() {
        return cle;
    }

    public void setEtatContrat(String etatContrat) {
        this.etatContrat = etatContrat;
    }

    public String getEtatContrat() {
        if(contratCpt.getContratCptId() != null){
            etatContrat = contratCpt.getCodEtatCcpt();
        }
        return etatContrat;
    }

    public void setContratCpt(ContratCpt contratCpt) {
        this.contratCpt = contratCpt;
    }

    public ContratCpt getContratCpt() {
        return contratCpt;
    }

    public void setMessageContratCpt(String messageContratCpt) {
        this.messageContratCpt = messageContratCpt;
    }

    public String getMessageContratCpt() {
        if(contratCpt.getContratCptId() != null){
            if(!contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)){
                if(contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_ATT)){
                    messageContratCpt = "Le Contrat est en attente de validation, Veuillez vérifier le numéro du contrat saisi SVP.";
                }else if(contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_REJETE)){
                    messageContratCpt = "La demande du Contrat est rejetée. Veuillez vérifier le numéro du contrat saisi SVP.";
                }else if(contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_RESILIE)){
                    messageContratCpt = "le contrat est résilié, Veuillez vérifier le numéro du contrat saisi SVP .";
                }else if(contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_SEMIACTIF)){
                    messageContratCpt = "le contrat est semi-actif, Veuillez vérifier le numéro du contrat saisi SVP.";
                }else if(contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_BLOQUE)){
                    messageContratCpt = "le contrat est bloqué, Veuillez vérifier le numéro du contrat saisi SVP.";
                }else if(contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_TCONTENTIEU)){
                    messageContratCpt = "le contrat est transféré à contentiueux, Veuillez vérifier le numéro du contrat saisi SVP.";
                }else{  
                    messageContratCpt = "le contrat est non valide, Veuillez vérifier le numéro du contrat saisi SVP.";
                }
            }
        }

        return messageContratCpt;
    }


    public void setMontSoldFCcpt(String montSoldFCcpt) {
        this.montSoldFCcpt = montSoldFCcpt;
    }

    public String getMontSoldFCcpt() {
      Long l = (Long.valueOf(montSoldCcpt)-Long.valueOf(montBlocCcpt)+Long.valueOf(montAutCcpt));
        return (l.toString());
    }

}
