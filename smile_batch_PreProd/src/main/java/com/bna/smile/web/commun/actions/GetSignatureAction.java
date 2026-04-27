package com.bna.smile.web.commun.actions;

import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Personne;
import com.bna.commun.model.Signature;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetPouvoirPersonneContratCmd;
import com.bna.smile.model.domainecontratcompte.procuration.model.PouvoirVo;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.GetSignaturesCmd;
import com.bna.smile.web.commun.forms.GetSignatureForm;
import com.bna.smile.web.souscription.forms.SignaturesContratCompteForm;

import java.awt.image.BufferedImage;

import java.io.IOException;

import java.io.InputStream;

import javax.imageio.ImageIO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class GetSignatureAction extends DispatchAction {
    public GetSignatureAction() {
    }
    
    public ActionForward initierPage(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws IOException, 
                                                                               ServletException {
    try {
          GetSignatureForm getSignatureForm = 
              (GetSignatureForm)form;
          getSignatureForm.setBufferedImageAr(null);
          getSignatureForm.setBufferedImageFr(null); 
          
          // recherche personne 
          PersonneStrc personneStrc = new PersonneStrc();
          personneStrc.setCodTpceTpce(getSignatureForm.getCodTpceTpce());
          personneStrc.setNumPcePers(getSignatureForm.getNumPcePers());   
          
          GetPersonneTrt getPersonneTrt = new GetPersonneTrt();
          Personne personne = (Personne) getPersonneTrt.exec(personneStrc);
          
          if(personne.getNumSeqPers()!=null){
              //Affichage nom prenom
              /// getSignatureForm.setTitre("Signatures personne.getNomNomPers()+ " " +personne.getNomPrnPers());
              //Recherche et Affichage pouvoir
               ContratCptId cpId = new ContratCptId();
               cpId.setCodStrcStrc(Long.valueOf(getSignatureForm.getCodStrcStrc()));
               cpId.setCodPrdPrd(Long.valueOf(getSignatureForm.getCodPrdPrd()));
               cpId.setNumCcptCcpt(Long.valueOf(getSignatureForm.getNumCcptCcpt()));

               ContratPersonne contratPersonne = new ContratPersonne();
               contratPersonne.setContratCptId(cpId);
               contratPersonne.setPersonneId(personneStrc);
                   
              //Affichage des Signatures actuel du signataire
               GetSignaturesCmd getSignaturesCmd = new GetSignaturesCmd();
               
               Signature signature = (Signature)getSignaturesCmd.execute(contratPersonne);
               
              if(signature!=null){
                  getSignatureForm.setTestExistSignature("1");
                  //signature arabe
                  InputStream inputStreamAr = signature.getImgArsiSignStream();
                   
                  //create image from InputStream
                  BufferedImage imgAr=null;
                  if(inputStreamAr!=null)
                      imgAr = ImageIO.read(inputStreamAr);     
                  getSignatureForm.setBufferedImageAr(imgAr);
                  request.getSession().setAttribute("bufferedImageAr",imgAr);
                  
                  //signature francais
                  InputStream inputStreamFr = signature.getImgFrsiSignStream();
                    
                  //create image from InputStream
                  BufferedImage imgFr = null;
                  if(inputStreamFr!=null)
                      imgFr = ImageIO.read(inputStreamFr);    
                  getSignatureForm.setBufferedImageFr(imgFr);
                  request.getSession().setAttribute("bufferedImageFr",imgFr);
                  
                  if(imgFr == null && imgAr == null){
                      getSignatureForm.setAlertSignature("Les signatures français & arabe pour cet personne sur ce contrat ne sont pas encore scannées.");
                  } 
              }else {
                  //getSignatureForm.setTestExistSignature("0");
                  getSignatureForm.setAlertSignature("Il n'existe aucune signature sur ce contrat");
              }
           
          }
    
    
      } catch (Exception e) {
        System.out.println("Erreur dans GetSignatureAction /  initierPage" + e.getMessage());
      }
    
    return mapping.findForward("success");
  }
  
}
