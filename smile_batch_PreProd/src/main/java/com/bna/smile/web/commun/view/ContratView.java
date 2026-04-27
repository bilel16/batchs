package com.bna.smile.web.commun.view;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.util.DateHandler;import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;

public class ContratView extends com.oxia.fwk.core.ValueObject implements java.io.Serializable {


    private String codStrcStrc="";
    private String codPrdPrd="";
    private String numCcptCcpt="";
    private String cle="";
    private String etatContrat="";
    private String nomIntiCcpt="";
    private String codEtatCcpt="";
    private String montSoldCcpt="";
    private String sensSolde="";
    private String libDevDev="";
    private String codDevDev="";
    private String nbrDecDev="";
  
    private String montBlocCcpt="";
    private String montAutCcpt="";
    private String montSdevCcpt;
    private String numeroCompte="";

    private String messageContratCpt="";
    private ContratCpt contratCpt = new ContratCpt();
    private String montSoldFCcpt="";
    

    public void clear(){
        codStrcStrc="";
        codPrdPrd="";
        numCcptCcpt= "";
        cle="";
        etatContrat="";
        nomIntiCcpt="";
        codEtatCcpt="";
        montSoldCcpt="";
        sensSolde="";
        libDevDev="";
        codDevDev="";
        montBlocCcpt="";
        montAutCcpt="";
        messageContratCpt="";
        contratCpt.setContratCptId(null);
        montSoldFCcpt="";        
        numeroCompte="";
    }


    public ContratView() { 

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
               montSoldCcpt = StrHandler.formatMontant(contratCpt.getMontSoldCcpt(),3);
                
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
                if(contratCpt.getMontSoldCcpt() == null){
                    sensSolde = ""; 
                }else{
                    if (contratCpt.getMontSoldCcpt().longValue()<0){
                        sensSolde = "DB"; 
                    }else {
                        sensSolde = "CR";
                    }
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
                montBlocCcpt = StrHandler.formatMontant(contratCpt.getMontBlocCcpt(),3);
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
                montAutCcpt = StrHandler.formatMontant(contratCpt.getMontAutCcpt(),3);
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
      Long l = Long.valueOf(0);
      if(contratCpt.getContratCptId() != null){   
          if(contratCpt.getMontSoldCcpt()== null)
              contratCpt.setMontSoldCcpt(Long.valueOf(0));
          if(contratCpt.getMontBlocCcpt()== null)
              contratCpt.setMontBlocCcpt(Long.valueOf(0));
          if(contratCpt.getMontAutCcpt()== null)
              contratCpt.setMontAutCcpt(Long.valueOf(0));              
          l = (contratCpt.getMontSoldCcpt()-contratCpt.getMontBlocCcpt()+contratCpt.getMontAutCcpt());
      }      
      return (l.toString());
    }



    public String get_montSoldFCcpt() {
        return montSoldFCcpt;
    }

    public void setMontSdevCcpt(String montSdevCcpt) {
        this.montSdevCcpt = montSdevCcpt;
    }

    public String getMontSdevCcpt() {
        if (contratCpt.getContratCptId() != null) {
                    if (contratCpt.getMontSdevCcpt() != null && (!contratCpt.getMontSdevCcpt().equals(0))) {
                        montSdevCcpt = 
                                StrHandler.formatMontant(contratCpt.getMontSdevCcpt(),contratCpt.getDevise().getNbrDecDev());
                    } else {
                        montSdevCcpt = "0.000";
                    }
                }
                return montSdevCcpt;
    }


    public void setCodDevDev(String codDevDev) {
        this.codDevDev = codDevDev;
    }

    public String getCodDevDev() {
        if(contratCpt.getContratCptId() != null){
            codDevDev = contratCpt.getDevise().getCodDevDev().toString();
        }
        return codDevDev;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getNumeroCompte() {
        StringBuffer numero = new StringBuffer();

               numero.append(StrHandler.lpad(contratCpt.getContratCptId().getCodStrcStrc().toString(), 
                                             '0', 3));
               numero.append(" ");
               numero.append(StrHandler.lpad(contratCpt.getContratCptId().getCodPrdPrd().toString(), 
                                             '0', 4));
               numero.append(" ");
               numero.append(StrHandler.lpad(contratCpt.getContratCptId().getNumCcptCcpt().toString(), 
                                             '0', 6));
               numeroCompte = numero.toString();
               return numeroCompte;
    }

    public void setNbrDecDev(String nbrDecDev) {

        this.nbrDecDev = nbrDecDev;
    }

    public String getNbrDecDev() {
        if(contratCpt.getContratCptId() != null){
            nbrDecDev = contratCpt.getDevise().getNbrDecDev().toString();
        }
        return nbrDecDev;
    }
}
