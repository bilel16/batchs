package com.bna.smile.web.souscription.actions;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Signature;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetPouvoirPersonneContratCmd;
import com.bna.smile.model.domainecontratcompte.procuration.model.PouvoirVo;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetSignaturesCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.InsertSignaturesCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.ModifSignaturesCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.SignaturePersCpt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.InsertSignaturesTrt;
import com.bna.smile.web.commun.model.ParamAgence;


import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.souscription.forms.SignaturesContratCompteForm;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.InputStream;

import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.log4j.Logger;



public class SignaturesContratCompteAction extends DispatchAction  {
    /**This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     */
    //String s = "";
     private int PosXFR;
     private int PosYFR;
     private int WidthFR;
     private int HeightFR;
     private int PosXAR;
     private int PosYAR;
     private int WidthAR;
     private int HeightAR;     
     


    public ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
         Logger logger = Logger.getLogger(SignaturesContratCompteAction.class);
         SignaturesContratCompteForm signaturesContratCompteForm = 
            (SignaturesContratCompteForm)form;
       
        try{
            SessionUtil sessionUtil =new SessionUtil();
            //Suppression des anciens Bean de type Form de la session, SAUF "signaturesContratCompteForm"
            sessionUtil.removeSession(request,"signaturesContratCompteForm"); 
            
            ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent  
                
            //verification de l'habilitation sur cet operation
            StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CONTRATCOMPTE);
            boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
           
            signaturesContratCompteForm.clearForm(request);
           
            String codeTraitement = signaturesContratCompteForm.getCodeTraitement();
            // affichage operation selon appel de menu
            if(codeTraitement.equals("saisiePpTitAn")){
                signaturesContratCompteForm.setTitre("Pec Signature Titulaire du Compte (Specimen P.Phy Ancien)");
            }else if(codeTraitement.equals("saisiePpTitNv")){
                signaturesContratCompteForm.setTitre("Pec Signature Titulaire du Compte (Specimen P.Phy Nouveau)");
            }else if(codeTraitement.equals("saisiePpMand1An")){
                signaturesContratCompteForm.setTitre("Pec Signature Mandataire I (Specimen P.Phy Ancien)");
            }else if(codeTraitement.equals("saisiePpMand1Nv")){
                signaturesContratCompteForm.setTitre("Pec Signature Mandataire I (Specimen P.Phy Nouveau)");
            }else if(codeTraitement.equals("saisiePpMand2An")){
                signaturesContratCompteForm.setTitre("Pec Signature Mandataire II (Specimen P.Phy Ancien)");
            }else if(codeTraitement.equals("saisiePpMand2Nv")){
                signaturesContratCompteForm.setTitre("Pec Signature Mandataire II (Specimen P.Phy Nouveau)");
            }else if(codeTraitement.equals("saisiePmRepLeg")){
                signaturesContratCompteForm.setTitre("Pec Signature Représentant Légal (Specimen P.Moral)");
            }else if(codeTraitement.equals("saisiePmMand1")){
                signaturesContratCompteForm.setTitre("Pec Signature Mandataire I (Specimen P.Moral)");
            }else if(codeTraitement.equals("saisiePmMand2")){
                signaturesContratCompteForm.setTitre("Pec Signature Mandataire II (Specimen P.Moral)");
            }else if(codeTraitement.equals("saisiePmMand3")){
                signaturesContratCompteForm.setTitre("Pec Signature Mandataire III (Specimen P.Moral)");
            }else if(codeTraitement.equals("saisiePmMand4")){
                signaturesContratCompteForm.setTitre("Pec Signature Mandataire IV (Specimen P.Moral)");
            }else if(codeTraitement.equals("saisiePmMand5")){
                signaturesContratCompteForm.setTitre("Pec Signature Mandataire V (Specimen P.Moral)");
            }else if(codeTraitement.equals("modifPpTitAn")){
                signaturesContratCompteForm.setTitre("Modification Signature Titulaire du Compte (Specimen P.Phy Ancien)");
            }else if(codeTraitement.equals("modifPpTitNv")){
                signaturesContratCompteForm.setTitre("Modification Signature Titulaire du Compte (Specimen P.Phy Nouveau)");
            }else if(codeTraitement.equals("modifPpMand1An")){
                signaturesContratCompteForm.setTitre("Modification Signature Mandataire I (Specimen P.Phy Ancien)");
            }else if(codeTraitement.equals("modifPpMand1Nv")){
                signaturesContratCompteForm.setTitre("Modification Signature Mandataire I (Specimen P.Phy Nouveau)");
            }else if(codeTraitement.equals("modifPpMand2An")){
                signaturesContratCompteForm.setTitre("Modification Signature Mandataire II (Specimen P.Phy Ancien)");
            }else if(codeTraitement.equals("modifPpMand2Nv")){
                signaturesContratCompteForm.setTitre("Modification Signature Mandataire II (Specimen P.Phy Nouveau)");
            }else if(codeTraitement.equals("modifPmRepLeg")){
                signaturesContratCompteForm.setTitre("Modification Signature Représentant Légal (Specimen P.Moral)");
            }else if(codeTraitement.equals("modifPmMand1")){
                signaturesContratCompteForm.setTitre("Modification Signature Mandataire I (Specimen P.Moral)");
            }else if(codeTraitement.equals("modifPmMand2")){
                signaturesContratCompteForm.setTitre("Modification Signature Mandataire II (Specimen P.Moral)");
            }else if(codeTraitement.equals("modifPmMand3")){
                signaturesContratCompteForm.setTitre("Modification Signature Mandataire III (Specimen P.Moral)");
            }else if(codeTraitement.equals("modifPmMand4")){
                signaturesContratCompteForm.setTitre("Modification Signature Mandataire IV (Specimen P.Moral)");
            }else if(codeTraitement.equals("modifPmMand5")){
                signaturesContratCompteForm.setTitre("Modification Signature Mandataire V (Specimen P.Moral)");
            }else{
                signaturesContratCompteForm.setTitre("Consultation Signatures");
            }
            // sauvgarde operation dans la form
            signaturesContratCompteForm.setOperation(request.getParameter("codeTraitement"));
            signaturesContratCompteForm.setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
            
            return mapping.findForward("success");
        } catch (Exception e) { 
          //  logger.error("Exception : ",e);
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  e.getMessage());
            actionMessages.add("Erreur ", actionMessage);   
            this.saveMessages(request, actionMessages);
            logger.error("Erreur au niveau de l'agence <<" +signaturesContratCompteForm.getCodStrcStrc()+ ">>. Exception : ",e); 
            return mapping.findForward("error");
        }
    }
    
    public boolean rechercheContrat(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws IOException, 
                                                                               ServletException {
    Logger logger = Logger.getLogger(SignaturesContratCompteAction.class);
    SignaturesContratCompteForm signaturesContratCompteForm = 
        (SignaturesContratCompteForm)form;
    try {
        
        GetDetailContratCmd getDetailContratCmd = 
            new GetDetailContratCmd();
        ContratCptId contratCptId = new ContratCptId();

        contratCptId.setCodStrcStrc(new Long(signaturesContratCompteForm.getCodStrcStrc()));
        contratCptId.setCodPrdPrd(new Long(signaturesContratCompteForm.getCodPrdPrd()));
        contratCptId.setNumCcptCcpt(new Long(signaturesContratCompteForm.getNumCcptCcpt()));
       
        //------------- Recherche des donnée du Contrat et du Client 
        ContratCpt contratCpt = 
            (ContratCpt)getDetailContratCmd.execute(contratCptId);
        //test si contrat existant
        if (contratCpt.getContratCptId() != null) {
            //test si contrat valide
            if (!contratCpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID)) {               
                signaturesContratCompteForm.setAlert("ContratNonvalide");
                return false;
            }  
        }else {
                //--------------------------------------------------------------------
                //------------- Contrat Inexistant
                signaturesContratCompteForm.setAlert("ContratNonexistant");
                return false;
        }
    
        } catch (Exception e) { 
          //  logger.error("Exception : ",e);
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  e.getMessage());
            actionMessages.add("Erreur ", actionMessage);   
            this.saveMessages(request, actionMessages);
            logger.error("Erreur au niveau de l'agence <<" +signaturesContratCompteForm.getCodStrcStrc()+ ">>. Exception : ",e); 
            throw new RuntimeException(e); 
        }
    return true;
}

    public ActionForward recherchePersonne(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws IOException, 
                                                                               ServletException {
    Logger logger = Logger.getLogger(SignaturesContratCompteAction.class);
    InputStream inputStreamAr=null;
    InputStream inputStreamFr =null;
    logger.info("debut recherchePersonne");
    SignaturesContratCompteForm signaturesContratCompteForm = 
        (SignaturesContratCompteForm)form;
    try {
       
        //----------- Effacer les resultats avant
        signaturesContratCompteForm.setAlert("");
        signaturesContratCompteForm.setNomPrenom("");
        signaturesContratCompteForm.setPouvoir("");
        signaturesContratCompteForm.setEtatCapture(""); 
        //verifier validité contrat
        logger.info("appel rechercheContrat from recherchePersonne");
        if(!rechercheContrat(mapping,form,request,response)){
            logger.info("recherchePersonne : contrat introuvable");
            return mapping.findForward("success");
        }
        // recherche personne 
        PersonneStrc personneStrc = new PersonneStrc();
        personneStrc.setCodTpceTpce(Long.valueOf(signaturesContratCompteForm.getTypePieceSign()));
        personneStrc.setNumPcePers(signaturesContratCompteForm.getNumPieceSign());   
        logger.info("recherchePersonne : appel getPersonneTrt : numPiece="+signaturesContratCompteForm.getNumPieceSign());
        GetPersonneTrt getPersonneTrt = new GetPersonneTrt();
        Personne personne = (Personne) getPersonneTrt.exec(personneStrc);
        
        if(personne.getNumSeqPers()!=null){
            logger.info("recherchePersonne : personne !=null : NumSeq="+personne.getNumSeqPers());
            //Affichage nom prenom
             signaturesContratCompteForm.setNomPrenom(personne.getNomNomPers()+ " " +personne.getNomPrnPers());
            //Recherche et Affichage pouvoir
             ContratCptId cpId = new ContratCptId();
             cpId.setCodStrcStrc(Long.valueOf(signaturesContratCompteForm.getCodStrcStrc()));
             cpId.setCodPrdPrd(Long.valueOf(signaturesContratCompteForm.getCodPrdPrd()));
             cpId.setNumCcptCcpt(Long.valueOf(signaturesContratCompteForm.getNumCcptCcpt()));

             ContratPersonne contratPersonne = new ContratPersonne();
             contratPersonne.setContratCptId(cpId);
             personneStrc.setCodTpceTpce(personne.getTypePiece().getCodTpceTpce());
             personneStrc.setNumPcePers(personne.getNumPcePers());
             contratPersonne.setPersonneId(personneStrc);
             GetPouvoirPersonneContratCmd cmd = new GetPouvoirPersonneContratCmd();

             PouvoirVo pouvoir = (PouvoirVo)cmd.execute(contratPersonne);
             String typePouvoir = pouvoir.getTypePouvoir();
             logger.info("recherchePersonne : appel getPouvoirPersonneContratCmd CodStrcStrc="+cpId.getCodStrcStrc()+" CodPrdPrd="+cpId.getCodPrdPrd()+" NumCcptCcpt="+cpId.getNumCcptCcpt()+"--pouvoir="+pouvoir.getTypePouvoir());
             if(typePouvoir.equals("T"))              
                 signaturesContratCompteForm.setPouvoir("Le Signataire est le Titulaire du compte");
             else if(typePouvoir.equals("C"))              
                 signaturesContratCompteForm.setPouvoir("Le Signataire est un Membre CoTitulaire sur ce compte");
             else if(typePouvoir.equals("M"))   
                 signaturesContratCompteForm.setPouvoir("Le Signataire est un Mandataire sur ce compte");
             else{ 
                    // arret de l'opération si aucun pouvoir
                    signaturesContratCompteForm.setAlert("aucunPouvoir");
                    return mapping.findForward("success");
             }
             //verifier pour Pec|modif Signature Mandataire :le signataire est non le  Titulaire du compte
             String codeTraitement = signaturesContratCompteForm.getOperation();
             if(!codeTraitement.equals("consult") && !codeTraitement.equals("saisiePpTitAn") && !codeTraitement.equals("saisiePpTitNv") && !codeTraitement.equals("modifPpTitAn") && !codeTraitement.equals("modifPpTitNv")&& typePouvoir.equals("T")){
                 signaturesContratCompteForm.setAlert("nonMandataireSign");
                 return mapping.findForward("success");  
             }
                 
            //Affichage des Signatures actuel du signataire
             GetSignaturesCmd getSignaturesCmd = new GetSignaturesCmd();
             
             Signature signature = (Signature)getSignaturesCmd.execute(contratPersonne);
            signaturesContratCompteForm.setEtatCapture("noInsert"); 
             
            if(signature!=null){
                //signature arabe
                inputStreamAr = signature.getImgArsiSignStream();
                 
                //create image from InputStream
                BufferedImage imgAr=null;
                if(inputStreamAr!=null)
                    imgAr = ImageIO.read(inputStreamAr);    
                request.getSession().setAttribute("bufferedImageAr",imgAr);
                
                //signature francais
                inputStreamFr = signature.getImgFrsiSignStream();
                  
                //create image from InputStream
                BufferedImage imgFr = null;
                if(inputStreamFr!=null)
                    imgFr = ImageIO.read(inputStreamFr);    
                request.getSession().setAttribute("bufferedImageFr",imgFr);
                
                
                //if(inputStreamAr != null || inputStreamFr != null){
                    signaturesContratCompteForm.setEtatCapture("insert");
               // }else{
              //    
              //  }
                logger.info("recherchePersonne : EtatCapture="+signaturesContratCompteForm.getEtatCapture());
                logger.info("recherchePersonne : Operation="+signaturesContratCompteForm.getOperation());
                
          
            }
            // si opérations de sasie signature alors verifier que cette opération n'est pas encore effectuée
            if(signaturesContratCompteForm.getOperation().indexOf("saisie") == 0 && signaturesContratCompteForm.getEtatCapture().equals("insert")){
                //--------------------------------------------------------------------
                //------------- saisieEffectue
                signaturesContratCompteForm.setAlert("saisieEffectue");
                // si opérations de modif signature alors verifier que cette opération n'est pas encore effectuée
            }else if(signaturesContratCompteForm.getOperation().indexOf("modif") == 0 && signaturesContratCompteForm.getEtatCapture().equals("noInsert")){
                //--------------------------------------------------------------------
                //------------- saisieEffectue
                signaturesContratCompteForm.setAlert("saisieNonEffectue");              
            }else if(signaturesContratCompteForm.getOperation().equals("consult") && signaturesContratCompteForm.getEtatCapture().equals("noInsert")){
                //--------------------------------------------------------------------
                //------------- saisieEffectue
                signaturesContratCompteForm.setAlert("saisieNonEffectue"); 
            }
        }else {
                //--------------------------------------------------------------------
                //------------- Personne Inexistant
                signaturesContratCompteForm.setAlert("PersonneNonexistant");
        }
    
        } catch (Exception e) { 
          //  logger.error("Exception : ",e);
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  e.getMessage());
            actionMessages.add("Erreur ", actionMessage);   
            this.saveMessages(request, actionMessages);
            logger.error("Erreur au niveau de l'agence <<" +signaturesContratCompteForm.getCodStrcStrc()+ ">>. Exception : ",e); 
            return mapping.findForward("error");
        }
    finally{
        if(inputStreamAr!=null) inputStreamAr.close();
        if(inputStreamFr!=null) inputStreamFr.close();
    }
    logger.info("fin recherchePersonne");
    return mapping.findForward("success");
}

    public ActionForward annuler(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {
        SignaturesContratCompteForm signaturesContratCompteForm = 
            (SignaturesContratCompteForm)form;
        signaturesContratCompteForm.clearForm(request);

        return mapping.findForward("success");
    }
    public ActionForward refresh(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {
        return mapping.findForward("success");
    }
    
    public ActionForward scanner(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
            Logger logger = Logger.getLogger(SignaturesContratCompteAction.class);                                                              
            logger.info("debut scanner");
            String messageScan="scanage ok";
            SignaturesContratCompteForm signaturesContratCompteForm = 
                (SignaturesContratCompteForm)form;
            //ObjectInputStream inputFromApplet;
            BufferedImage bufferedImage = null;
            request.getSession().setAttribute("bufferedImageFr",null);
            request.getSession().setAttribute("bufferedImageAr",null);
            InputStream in=null;
            ObjectInputStream inputFromApplet=null;
            try
            {
                /************Reception de l'image byte[] de l'applet*******************/
                //response.setContentType("application/x-java-serialized-object");
                in = request.getInputStream();
                inputFromApplet = new ObjectInputStream(in);
                byte[] sendimage = (byte[])inputFromApplet.readObject();
                bufferedImage = fromByteArray(sendimage);
            //----------- Effacer les resultats avant
            logger.info("scanner : numPiece="+signaturesContratCompteForm.getNumPieceSign());
            logger.info("scanner : numCompte="+signaturesContratCompteForm.getCodStrcStrc()+signaturesContratCompteForm.getCodPrdPrd()+signaturesContratCompteForm.getNumCcptCcpt());
            
            signaturesContratCompteForm.setAlert("");
            
           /*Titulaire PP*/
           /* int PosXFR = 333;
            int PosYFR = 611;
            int WidthFR = 444;
            int HeightFR = 194;
            int PosXAR = 10;
            int PosYAR = 10;
            int WidthAR = 10;
                int HeightAR = 10;             
            */
            
            if(bufferedImage != null) {  
                affecterRegionScan(signaturesContratCompteForm.getOperation(), bufferedImage.getWidth(), bufferedImage.getHeight()); 
                // prise de la sub-image FR si pas de debord
                if (WidthFR != 0 && (bufferedImage.getWidth() >= PosXFR + WidthFR) && 
                     (bufferedImage.getHeight() >= PosYFR + HeightFR)) {
                     BufferedImage bufferedImageFr = 
                     bufferedImage.getSubimage(PosXFR,PosYFR,WidthFR,HeightFR);
                     request.getSession().setAttribute("bufferedImageFr",bufferedImageFr);
                
                } else {
                        logger.info("Deborde ou region de taille zéro, lors de la prise de la signature FR");
                }
                
                // prise de la sub-image AR si pas de debord
               /* if (WidthAR != 0 && (bufferedImage.getWidth() >= PosXAR + WidthAR) && 
                     (bufferedImage.getHeight() >= PosYAR + HeightAR)) {
                     BufferedImage bufferedImageAr = 
                     bufferedImage.getSubimage(PosXAR,PosYAR,WidthAR,HeightAR);
                     request.getSession().setAttribute("bufferedImageAr",bufferedImageAr);
                     
                
                } else {
                        logger.info("Deborde ou region de taille zéro, lors de la prise de la signature AR");
                }*/
                
            //si non success scan
            } else {
                //--------------------------------------------------------------------
                //------------- Contrat Inexistant
                signaturesContratCompteForm.setAlert("errorScan");
                logger.error("scanner : Fatale erreur de scan.");
                logger.error("Fatale erreur de scan. Veuillez verifier la connexion avec le scanner!");
            }
                if(inputFromApplet!=null) inputFromApplet.close();
                if(in!=null) in.close();
          
            
            }
            catch (Exception e)
            {
                if(inputFromApplet!=null) inputFromApplet.close();
                if(in!=null) in.close();
                messageScan="scanage ko";
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      e.getMessage());
                actionMessages.add("Erreur ", actionMessage);   
                this.saveMessages(request, actionMessages);
                logger.error("Erreur au niveau de l'agence <<" +signaturesContratCompteForm.getCodStrcStrc()+ ">>. Exception : ",e); 
                return mapping.findForward("error");
            //    logger.error("Exception : ",e);
            }
            response.getOutputStream().write(messageScan.getBytes());
            response.getOutputStream().close();
            logger.info("fin scanner : numPiece="+signaturesContratCompteForm.getNumPieceSign());
            logger.info("fin scanner : numCompte="+signaturesContratCompteForm.getCodStrcStrc()+signaturesContratCompteForm.getCodPrdPrd()+signaturesContratCompteForm.getNumCcptCcpt());
            return null;
        }
    
    public ActionForward valider(ActionMapping mapping, ActionForm form, 
                                 HttpServletRequest request, 
                                 HttpServletResponse response) throws IOException, 
                                                                      ServletException {
        Logger logger = Logger.getLogger(SignaturesContratCompteAction.class); 
        ActionMessages actionMessages = new ActionMessages();      
        SignaturesContratCompteForm signaturesContratCompteForm = 
            (SignaturesContratCompteForm)form;
        logger.info("valider : numPiece="+signaturesContratCompteForm.getNumPieceSign());
        logger.info("valider : numCompte="+signaturesContratCompteForm.getCodStrcStrc()+signaturesContratCompteForm.getCodPrdPrd()+signaturesContratCompteForm.getNumCcptCcpt());
    try{
        if(request.getSession().getAttribute("bufferedImageFr")!=null ){    
            //remplissage du Vo d'entré SignaturePersCpt;
            SignaturePersCpt signaturePersCpt = new SignaturePersCpt();
            //
            ContratCptId cpId = new ContratCptId();
            cpId.setCodStrcStrc(Long.valueOf(signaturesContratCompteForm.getCodStrcStrc()));
            cpId.setCodPrdPrd(Long.valueOf(signaturesContratCompteForm.getCodPrdPrd()));
            cpId.setNumCcptCcpt(Long.valueOf(signaturesContratCompteForm.getNumCcptCcpt()));
    
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodTpceTpce(Long.valueOf(signaturesContratCompteForm.getTypePieceSign()));
            //personneStrc.setNumPcePers("00012345");
            personneStrc.setNumPcePers(signaturesContratCompteForm.getNumPieceSign());
    
            ContratPersonne contratPersonne = new ContratPersonne();
            contratPersonne.setContratCptId(cpId);
            contratPersonne.setPersonneId(personneStrc);
    
            signaturePersCpt.setContratPersonne(contratPersonne);
            signaturePersCpt.setBufferedImageAr((BufferedImage)request.getSession().getAttribute("bufferedImageAr"));
            signaturePersCpt.setBufferedImageFr((BufferedImage)request.getSession().getAttribute("bufferedImageFr"));
            logger.info("valider : signaturePersCpt:"+signaturePersCpt.getContratPersonne().getContratCptId().getCodStrcStrc()
            +"-"+signaturePersCpt.getContratPersonne().getContratCptId().getCodPrdPrd()+"-"+signaturePersCpt.getContratPersonne().getContratCptId()+"-"
            +"-Personne-"+signaturePersCpt.getContratPersonne().getPersonneId().getCodTpceTpce()+"-"+signaturePersCpt.getContratPersonne().getPersonneId().getNumPcePers());
            
            ParamAgence paramAgence =(ParamAgence)request.getSession().getAttribute("paramAgBNA");
            signaturePersCpt.setNumMatricule(Long.valueOf(paramAgence.getNumMatrUser()));
            signaturePersCpt.setDateModification(paramAgence.getDateOp());
            //appel à la commnade de modification si modif et insertion si insertion
             logger.info("valider : form operation="+signaturesContratCompteForm.getOperation());
            if(signaturesContratCompteForm.getOperation().indexOf("modif") == 0){       
                ModifSignaturesCmd modifSignaturesCmd = new ModifSignaturesCmd();
                signaturePersCpt = (SignaturePersCpt)modifSignaturesCmd.execute(signaturePersCpt); 
                if(signaturePersCpt==null) return mapping.findForward("error");
            }else if(signaturesContratCompteForm.getOperation().indexOf("saisie") == 0){
                InsertSignaturesCmd insertSignaturesCmd = new InsertSignaturesCmd();
                signaturePersCpt = (SignaturePersCpt)insertSignaturesCmd.execute(signaturePersCpt); 
                if(signaturePersCpt==null) return mapping.findForward("error");
            }
            request.getSession().removeAttribute("bufferedImageFr");
            request.getSession().removeAttribute("bufferedImageAr");
        }
        
            //System.gc();
    } catch (Exception e) {
                ActionMessage actionMessage = 
                    new ActionMessage("exception.generique", 
                                      e.getMessage());
                actionMessages.add("Erreur ", actionMessage);   
                this.saveMessages(request, actionMessages);
                logger.error("Erreur au niveau de l'agence <<" +signaturesContratCompteForm.getCodStrcStrc()+ ">>. Exception : ",e);  
               return mapping.findForward("error");
    } 
    
       
        //return mapping.findForward("indexSMILE");
        //return initierPage(mapping, form, request, response);
         
         StringBuffer message = new StringBuffer(
                "La scanarisation du spécimen de signature au nom de " +signaturesContratCompteForm.getNomPrenom()+
                " sur le compte " +
                StrHandler.lpad(signaturesContratCompteForm.getCodStrcStrc().toString(), '0', 3) + " "+
                StrHandler.lpad(signaturesContratCompteForm.getCodPrdPrd().toString(), '0', 4) + " "+
                StrHandler.lpad(signaturesContratCompteForm.getNumCcptCcpt().toString(), '0', 6)+
                " a été crée avec succès.");
         ActionMessage actionMessage = new ActionMessage("exception.generique",message.toString());
         actionMessages.add("Msg_validation ", actionMessage);
         this.saveMessages(request, actionMessages);
         return mapping.findForward("confirmationGeneraleSignature");
    }
    private BufferedImage fromByteArray(byte[] imagebytes) {
            Logger logger = Logger.getLogger(SignaturesContratCompteAction.class); 
            ByteArrayInputStream bt=null;
            try {
                if (imagebytes != null && (imagebytes.length > 0)) {
                    bt=new ByteArrayInputStream(imagebytes);
                    BufferedImage im = ImageIO.read(bt);
                    if(bt!=null) bt.close();
                    return im;
                }
                if(bt!=null) bt.close();              
            } catch (IOException e) {
                logger.error("Exception : ",e);
                throw new IllegalArgumentException(e.toString());
            }
            try{
                if(bt!=null) bt.close();
            }
            catch(Exception ex){
                logger.error("Exception : ",ex);
                throw new RuntimeException(ex.toString());
            }
        return null;
    }
          
    private byte[] toByteArray(BufferedImage o) {
        Logger logger = Logger.getLogger(SignaturesContratCompteAction.class); 
        try{
                if(o != null) {
                    BufferedImage image = (BufferedImage)o;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
                    try {
                        ImageIO.write(image, "jpeg", baos);
                    } catch (IOException e) {
                        throw new IllegalStateException(e.toString());
                    }
                    byte[] b = baos.toByteArray();
                    return b;
                }
                return new byte[0];
        }catch(Exception ex){
            logger.error("Exception : ",ex);
            throw new RuntimeException(ex.toString());
        }
            
    }
    
    
    private void affecterRegionScan(String codeTraitement, int newMaxWidth, int newMaxHeidth){        
    
        Logger logger = Logger.getLogger(SignaturesContratCompteAction.class); 
        try{
            if(codeTraitement.equals("saisiePpTitAn") || codeTraitement.equals("modifPpTitAn")){
                PosXFR = Constants.PosXFRPPTi         +20;
                PosYFR = Constants.PosYFRPPTi + 130   + 50;
                WidthFR = Constants.WidthFRPPTi       +250;
                HeightFR = Constants.HeightFRPPTi     + 100;
                PosXAR = Constants.PosXARPPTi;
                PosYAR = Constants.PosYARPPTi;
                WidthAR = Constants.WidthARPPTi;
                HeightAR = Constants.HeightARPPTi;
            }else if(codeTraitement.equals("saisiePpTitNv") || codeTraitement.equals("modifPpTitNv")){   
                PosXFR = Constants.PosXFRPPTi                       -44;
                PosYFR = Constants.PosYFRPPTi + 130                 -40;
                WidthFR = Constants.WidthFRPPTi                     +88;
                HeightFR = Constants.HeightFRPPTi                   +100;
                PosXAR = Constants.PosXARPPTi;
                PosYAR = Constants.PosYARPPTi;
                WidthAR = Constants.WidthARPPTi;
                HeightAR = Constants.HeightARPPTi;
            }else if(codeTraitement.equals("saisiePpMand1An") || codeTraitement.equals("modifPpMand1An")){
                PosXFR = Constants.PosXFRPPMand1       -40;
                PosYFR = Constants.PosYFRPPMand1 + 190 -10 ;
                WidthFR = Constants.WidthFRPPMand1     +40;
                HeightFR = Constants.HeightFRPPMand1   +110;
                PosXAR = Constants.PosXARPPMand1;
                PosYAR = Constants.PosYARPPMand1;
                WidthAR = Constants.WidthARPPMand1;
                HeightAR = Constants.HeightARPPMand1;
            }else if(codeTraitement.equals("saisiePpMand1Nv") || codeTraitement.equals("modifPpMand1Nv")){
                PosXFR = Constants.PosXFRPPMand1;
                PosYFR = Constants.PosYFRPPMand1 + 190;
                WidthFR = Constants.WidthFRPPMand1;
                HeightFR = Constants.HeightFRPPMand1;
                PosXAR = Constants.PosXARPPMand1;
                PosYAR = Constants.PosYARPPMand1;
                WidthAR = Constants.WidthARPPMand1;
                HeightAR = Constants.HeightARPPMand1;
            }else if(codeTraitement.equals("saisiePpMand2An") || codeTraitement.equals("modifPpMand2An")){
                PosXFR = Constants.PosXFRPPMand2       -40;
                PosYFR = Constants.PosYFRPPMand2 + 220 +60;
                WidthFR = Constants.WidthFRPPMand2     +40;
                HeightFR = Constants.HeightFRPPMand2   +100;
                PosXAR = Constants.PosXARPPMand2;
                PosYAR = Constants.PosYARPPMand2;
                WidthAR = Constants.WidthARPPMand2;
                HeightAR = Constants.HeightARPPMand2;
            }else if(codeTraitement.equals("saisiePpMand2Nv") || codeTraitement.equals("modifPpMand2Nv")){
                PosXFR = Constants.PosXFRPPMand2;
                PosYFR = Constants.PosYFRPPMand2 + 220;
                WidthFR = Constants.WidthFRPPMand2;
                HeightFR = Constants.HeightFRPPMand2;
                PosXAR = Constants.PosXARPPMand2;
                PosYAR = Constants.PosYARPPMand2;
                WidthAR = Constants.WidthARPPMand2;
                HeightAR = Constants.HeightARPPMand2;
            }else if(codeTraitement.equals("saisiePmRepLeg") || codeTraitement.equals("modifPmRepLeg")){
                PosXFR = Constants.PosXFRPMRep;
                PosYFR = Constants.PosYFRPMRep  ;
                WidthFR = Constants.WidthFRPMRep;
                HeightFR = Constants.HeightFRPMRep;
                PosXAR = Constants.PosXARPMRep;
                PosYAR = Constants.PosYARPMRep;
                WidthAR = Constants.WidthARPMRep;
                HeightAR = Constants.HeightARPMRep;
            }else if(codeTraitement.equals("saisiePmMand1") || codeTraitement.equals("modifPmMand1")){
                PosXFR = Constants.PosXFRPMMand1;
                PosYFR = Constants.PosYFRPMMand1 +30 ;
                WidthFR = Constants.WidthFRPMMand1;
                HeightFR = Constants.HeightFRPMMand1 +10;
                PosXAR = Constants.PosXARPMMand1;
                PosYAR = Constants.PosYARPMMand1;
                WidthAR = Constants.WidthARPMMand1;
                HeightAR = Constants.HeightARPMMand1;
            }else if(codeTraitement.equals("saisiePmMand2") || codeTraitement.equals("modifPmMand2")){
                PosXFR = Constants.PosXFRPMMand2 ;
                PosYFR = Constants.PosYFRPMMand2 ;
                WidthFR = Constants.WidthFRPMMand2;
                HeightFR = Constants.HeightFRPMMand2  +10;
                PosXAR = Constants.PosXARPMMand2;
                PosYAR = Constants.PosYARPMMand2;
                WidthAR = Constants.WidthARPMMand2;
                HeightAR = Constants.HeightARPMMand2;
            }else if(codeTraitement.equals("saisiePmMand3") || codeTraitement.equals("modifPmMand3")){
                PosXFR = Constants.PosXFRPMMand3;
                PosYFR = Constants.PosYFRPMMand3 ;
                WidthFR = Constants.WidthFRPMMand3;
                HeightFR = Constants.HeightFRPMMand3  +10; 
                PosXAR = Constants.PosXARPMMand3;
                PosYAR = Constants.PosYARPMMand3;
                WidthAR = Constants.WidthARPMMand3;
                HeightAR = Constants.HeightARPMMand3;
            }else if(codeTraitement.equals("saisiePmMand4") || codeTraitement.equals("modifPmMand4")){
                PosXFR = Constants.PosXFRPMMand4 ;
                PosYFR = Constants.PosYFRPMMand4;
                WidthFR = Constants.WidthFRPMMand4 ;
                HeightFR = Constants.HeightFRPMMand4+10;
                PosXAR = Constants.PosXARPMMand4;
                PosYAR = Constants.PosYARPMMand4;
                WidthAR = Constants.WidthARPMMand4;
                HeightAR = Constants.HeightARPMMand4;
            }else if(codeTraitement.equals("saisiePmMand5") || codeTraitement.equals("modifPmMand5")){
                PosXFR = Constants.PosXFRPMMand5;
                PosYFR = Constants.PosYFRPMMand5 ;
                WidthFR = Constants.WidthFRPMMand5;
                HeightFR = Constants.HeightFRPMMand5 +10;
                PosXAR = Constants.PosXARPMMand5;
                PosYAR = Constants.PosYARPMMand5;
                WidthAR = Constants.WidthARPMMand5;
                HeightAR = Constants.HeightARPMMand5;
            }
            //Recalcule des position selon la nouvelle metric
            PosXFR = (PosXFR * newMaxWidth)/1696 ;
            PosYFR = (PosYFR * newMaxHeidth)/2800 ;
            WidthFR = (WidthFR * newMaxWidth)/1696;
            HeightFR = (HeightFR * newMaxHeidth)/2800;
            PosXAR = (PosXAR * newMaxWidth)/1696;
            PosYAR = (PosYAR * newMaxHeidth)/2800;
            WidthAR = (WidthAR * newMaxWidth)/1696;
            HeightAR = (HeightAR * newMaxHeidth)/2800;
        } catch (Exception e) {
            logger.error("Exception : ",e);
            throw new RuntimeException(e);
        } 
        
    }




}
