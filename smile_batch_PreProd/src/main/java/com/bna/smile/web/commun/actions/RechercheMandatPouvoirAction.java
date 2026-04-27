package com.bna.smile.web.commun.actions;

import com.bna.smile.model.constant.Constants;
import com.bna.commun.model.CoTitulaire;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.Mandat;
import com.bna.commun.model.MandatOperation;
import com.bna.commun.model.MandatPersonne;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Personne;
import com.bna.commun.model.TypePiece;
import com.bna.smile.model.domainecommun.commande.GetListMembreCotitulaireCmd;
import com.bna.smile.model.domainecommun.commande.GetPersonneCmd;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetDetailMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetMandatCmd;
import com.bna.smile.model.domainecontratcompte.procuration.commande.GetPouvoirPersonneContratCmd;
import com.bna.smile.model.domainecontratcompte.procuration.model.DetailMandat;
import com.bna.smile.model.domainecontratcompte.procuration.model.PouvoirVo;
import com.bna.smile.web.commun.forms.RechercheMandatPouvoirForm;
import com.bna.smile.web.commun.model.Pouvoir;
import com.bna.smile.web.procuration.util.MandatView;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class RechercheMandatPouvoirAction extends DispatchAction {
    public RechercheMandatPouvoirAction() {
    }
    
    public ActionForward initierPage(ActionMapping mapping, 
                                                       ActionForm form, 
                                                       HttpServletRequest request, 
                                                       HttpServletResponse response) throws IOException, 
                                                                                            ServletException {
                                                                                            
        
        
        RechercheMandatPouvoirForm rechercheMandatPouvoirForm = 
            (RechercheMandatPouvoirForm)form;
 try{
        rechercheMandatPouvoirForm.clearForm();
            
        Pouvoir pouvoir = new Pouvoir();
        pouvoir.setTypePouvoir("");
        //----------------- Rechercher la personne demandeur -----------------------------
        GetPersonneCmd getPersonneCmd = new GetPersonneCmd();
        PersonneStrc personneStrc = new PersonneStrc();
        personneStrc.setCodTpceTpce(Long.valueOf(rechercheMandatPouvoirForm.getCodTpceTpce())); 
        personneStrc.setNumPcePers(rechercheMandatPouvoirForm.getNumPcePers() );
        if(personneStrc.getCodTpceTpce().equals(Constants.COD_PASS) || personneStrc.getCodTpceTpce().equals(Constants.COD_CSEJ)){
            pouvoir.setCodPieceAnnexe(personneStrc.getCodTpceTpce().toString());
            pouvoir.setNumPieceAnnexe(personneStrc.getNumPcePers());            
        }
         Personne personne = (Personne) getPersonneCmd.execute(personneStrc);
        
        if (personne.getNumSeqPers()!= null){
        
           //affecter l'objet demandeur au pouvoir
            pouvoir.setDemandeur(personne); 
           //verifier si demandeur incapable
           if(personne.getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_ETR_INC) || personne.getCategoriePersonne().getCodCatpCatp().equals(Constants.COD_CATEGORIE_P_TUN_INC) ){
                rechercheMandatPouvoirForm.setTypePouvoir("I");
                pouvoir.setTypePouvoir("I"); 
            }else{
               //---------------------------------------------------
                //-------- determiner le type du pouvoir ------------
                //---------------------------------------------------
                
             if(personne.getDatDecePers() == null && personne.getNumDecePers() == null){
                // personne non décédé...
                ContratCptId contratCptId = new ContratCptId();
                
                contratCptId.setCodPrdPrd(Long.valueOf(rechercheMandatPouvoirForm.getCodeProduit()));
                contratCptId.setCodStrcStrc(Long.valueOf(rechercheMandatPouvoirForm.getCodeAgence()));
                contratCptId.setNumCcptCcpt((Long.valueOf(rechercheMandatPouvoirForm.getNumCCpt())));
                
                if(personne.getTypePiece().getCodTpceTpce().equals(Constants.COD_NUM_ORDRE)){
                    personneStrc.setCodTpceTpce(personne.getTypePiece().getCodTpceTpce());
                    personneStrc.setNumPcePers(personne.getNumPcePers());                    
                }else{ 
                  personneStrc.setCodTpceTpce(Long.valueOf(rechercheMandatPouvoirForm.getCodTpceTpce()));
                  personneStrc.setNumPcePers(rechercheMandatPouvoirForm.getNumPcePers());
                }
                ContratPersonne contratPersonne = new ContratPersonne();
                contratPersonne.setContratCptId(contratCptId);
                contratPersonne.setPersonneId(personneStrc);
                Operation operation = new Operation();
                operation.setCodOperOper(Long.valueOf(rechercheMandatPouvoirForm.getCodeOperation()));
                
                contratPersonne.setOperation(operation);
                
                GetPouvoirPersonneContratCmd getPouvoir = new GetPouvoirPersonneContratCmd();
                PouvoirVo pouvoirVo =(PouvoirVo)getPouvoir.execute(contratPersonne) ;
                
                rechercheMandatPouvoirForm.setNumSeqPersDemandeur(personne.getNumSeqPers().toString());
                rechercheMandatPouvoirForm.setPersonneDemandeur(personne);
                //---------------- s'il n y a pas d'erreur
                if (! pouvoirVo.hasError()){
                  if (pouvoirVo.getTypePouvoir()!= null && (!pouvoirVo.getTypePouvoir().equalsIgnoreCase(""))){
                       rechercheMandatPouvoirForm.setTypePouvoir(pouvoirVo.getTypePouvoir());
                       if (pouvoirVo.getTypePouvoir().equalsIgnoreCase(Constants.COD_TYPE_POUVOIR_MANDATAIRE)){
                            List listMandatGeneral = pouvoirVo.getListMandatOperation().getListMandatsGeneraux();
                            List listMandatSpecial = pouvoirVo.getListMandatOperation().getListMandatsSpeciauxOperations();
                            listMandatGeneral.addAll(listMandatSpecial);
                            //affectation de la liste mandat view
                            List listMandatView = setListMandatView(listMandatGeneral);
                            rechercheMandatPouvoirForm.setListMandats(listMandatView);
                            rechercheMandatPouvoirForm.setMessageTexte("Liste des mandats :");
                        
                        //-----------------------------------------------------------------------------//
                        //------------------------- Cas des Co-titulaires  -----------------------------//
                        //-----------------------------------------------------------------------------//
                         
                        }else if (pouvoirVo.getTypePouvoir().equalsIgnoreCase(Constants.COD_TYPE_POUVOIR_COTITULAIRE)){
                            CoTitulaire cotitulaire = (CoTitulaire) pouvoirVo.getCoTitulaire();
                            GetListMembreCotitulaireCmd listMembreCotitulaire = 
                                new GetListMembreCotitulaireCmd();
                             personneStrc.setCodTpceTpce(cotitulaire.getClient().getPersonne().getTypePiece().getCodTpceTpce());
                             personneStrc.setNumPcePers(cotitulaire.getClient().getPersonne().getNumPcePers());
                             Listes l =(Listes)listMembreCotitulaire.execute(personneStrc);
                            
                             if (l.getList().size()>0){
                             
                                 rechercheMandatPouvoirForm.setListCotitulaire(l.getList());
                                 for(Iterator iter =l.getList().iterator(); iter.hasNext() ;){
                                     rechercheMandatPouvoirForm.getListdesCotitulaireChoisi().add("");
                                     iter.next();
                                 }
                                 
                                 CoTitulaire coTitulaire = (CoTitulaire)l.getList().get(0);
                                 rechercheMandatPouvoirForm.setTypeCotitulaire(coTitulaire.getCodTcotCoti());  
                                 rechercheMandatPouvoirForm.setTypeSignatureCotitulaire(coTitulaire.getCodSigCoti());
                                 
                             }            
                        }else if (pouvoirVo.getTypePouvoir().equalsIgnoreCase(Constants.COD_TYPE_POUVOIR_TITULAIRE)){
                            //si titulaire remplissage de l'objet pouvoir
                           
                            rechercheMandatPouvoirForm.setTypePouvoir(Constants.COD_TYPE_POUVOIR_TITULAIRE);
                            pouvoir.setTypePouvoir(rechercheMandatPouvoirForm.getTypePouvoir());
                        }
                  }else{
                      pouvoir.setTypePouvoir("");
                  }
               }
             }else{
            //fin test deces...
               rechercheMandatPouvoirForm.setTypePouvoir("D");
               pouvoir.setTypePouvoir("D");  
                 
             }
            }//Fin else incapable
            
        }else{
        //------------- Demandeur inexistant dans la base
            //affecter du demandeur saisie au pouvoir
            Personne demandeur = new Personne();
            demandeur.setNumPcePers(rechercheMandatPouvoirForm.getNumPcePers());
            TypePiece typePieceDemandeur = new TypePiece();
            typePieceDemandeur.setCodTpceTpce(Long.valueOf(rechercheMandatPouvoirForm.getCodTpceTpce()));
            demandeur.setTypePiece(typePieceDemandeur);
            demandeur.setNomNomPers("");
            demandeur.setNomPrnPers("");
            
            pouvoir.setDemandeur(demandeur); 
            rechercheMandatPouvoirForm.setTypePouvoir("N");
            pouvoir.setTypePouvoir("N"); 
            //request.getSession().setAttribute("pouvoir",pouvoir);
            //rechercheMandatPouvoirForm.setErreurDemandeur("Le demandeur est inconnu, veuillez verifier votre saisie. ");    
        }
        //pouvoir.setTypePouvoir(rechercheMandatPouvoirForm.getTypePouvoir());
        request.getSession().setAttribute("pouvoir",pouvoir);
        
        } catch (Exception e) {
           System.out.println("Erreur initierPage --------- " + e.getMessage());
      }
        return mapping.findForward("success");
    }
    
    public ActionForward detailMandat(ActionMapping mapping, 
                                                           ActionForm form, 
                                                           HttpServletRequest request, 
                                                           HttpServletResponse response) throws IOException, 
                                                                                                ServletException {
                               
        RechercheMandatPouvoirForm rechercheMandatPouvoirForm = 
            (RechercheMandatPouvoirForm)form;
        //clear des liste mandataires et liste operations
        rechercheMandatPouvoirForm.setListMandatsPersonne(null);
        rechercheMandatPouvoirForm.setListdesOperations(null);
        rechercheMandatPouvoirForm.setNumeroOperationChoisi("");
        
        Mandat mandat = new Mandat();
        mandat.setNumMandMand(Long.valueOf(rechercheMandatPouvoirForm.getNumMandChoisi()));
        GetMandatCmd getMandat = new GetMandatCmd();
        mandat = (Mandat)getMandat.execute(mandat);
        rechercheMandatPouvoirForm.setMandatChoisi(mandat);
        
        //(1)--- affecter les données du mandat chosi dans les champs 
        rechercheMandatPouvoirForm.setTypeMandat(mandat.getCodTypMand());
        if (mandat.getCodTypMand().equals(Constants.COD_TYPE_MAND_GENERAL)){
           if(mandat.getNbrMinMand()!=null){
            rechercheMandatPouvoirForm.setNombreMinimumMandataire(Integer.valueOf(mandat.getNbrMinMand().intValue()));
           }
            rechercheMandatPouvoirForm.setTypeSignature(mandat.getCodSignMand());
        }
       //--(1)--
       //--(2)------------------ Affichage des opérations du mandat ------------------------
        GetDetailMandatCmd getDetailMandatCmd = new GetDetailMandatCmd();
        DetailMandat detailMandat = (DetailMandat)getDetailMandatCmd.execute(mandat);
        rechercheMandatPouvoirForm.setListMandatsPersonne(detailMandat.getListeMandatPersonnes());
        List listMandatPersonneChoisi = new ArrayList();
        
        for(Iterator iter =detailMandat.getListeMandatPersonnes().iterator(); iter.hasNext() ;){
           
            listMandatPersonneChoisi.add("");
            iter.next();
        }
        rechercheMandatPouvoirForm.setListdesMandatsPersonneChoisi(listMandatPersonneChoisi);
        //---(2)-----------------------
        
       //(3)--------------- Afficher que les opérations conçernées en cas d'un mandat spécial
        if (mandat.getCodTypMand().equals(Constants.COD_TYPE_MAND_SPECIAL)){
            List listeDesOperation = new ArrayList();
         //   List listeDesOperationsChoisis = new ArrayList();
            for(Iterator it =detailMandat.getListeMandatOperations().iterator();it.hasNext();){
                MandatOperation mandatOperation = (MandatOperation) it.next();
                if (mandatOperation.getOperation().getCodOperOper().equals(Long.valueOf(rechercheMandatPouvoirForm.getCodeOperation()))){
                    listeDesOperation.add(mandatOperation);
                    //listeDesOperationsChoisis.add("");
                }
            }
           // rechercheMandatPouvoirForm.setListdesOperationsChoisi(listeDesOperationsChoisis);
            rechercheMandatPouvoirForm.setListdesOperations(listeDesOperation);   
            rechercheMandatPouvoirForm.setNombreOperation(String.valueOf(listeDesOperation.size()));
             if (listeDesOperation.size()==1){
                MandatOperation mOp = (MandatOperation)listeDesOperation.get(0);
                rechercheMandatPouvoirForm.setNumeroOperationChoisi(mOp.getMandatOperationId().getNumMaopMaop().toString());
             }
        }
        //--(3)----------------
        return mapping.findForward("success");
    }
    public void verifierDemandeur(ActionForm form) throws IOException, 
                                                                             ServletException {
    
        RechercheMandatPouvoirForm pechercheMandatPouvoirForm = 
            (RechercheMandatPouvoirForm)form;
     
          
        pechercheMandatPouvoirForm.clearForm();
       
        
      
    }
    
    
    

    private List setListMandatView(List list){
        
        List listMandatView = new ArrayList(); 
        Iterator it = list.iterator();
        Mandat mandat = new Mandat();
        
        for(;it.hasNext();){
            mandat = (Mandat)it.next();
            MandatView mandatView = new MandatView();
            mandatView.setMandat(mandat);
            
            listMandatView.add(mandatView); 
        }  
        return listMandatView;
    }
    
    public ActionForward validate (ActionMapping mapping, 
                                                           ActionForm form, 
                                                           HttpServletRequest request, 
                                                           HttpServletResponse response) throws IOException, 
                                                                                                ServletException {
                               
        RechercheMandatPouvoirForm rechercheMandatPouvoirForm = 
            (RechercheMandatPouvoirForm)form;
            
        rechercheMandatPouvoirForm.setActionError("");
        Pouvoir pouvoir = (Pouvoir) request.getSession().getAttribute("pouvoir");
        pouvoir.setTypePouvoir(rechercheMandatPouvoirForm.getTypePouvoir());
        
        //---------------------- Cas de mandataire ----------------------------------------//
        if (rechercheMandatPouvoirForm.getTypePouvoir().equals(Constants.COD_TYPE_POUVOIR_MANDATAIRE)) {
         pouvoir.setMandat(rechercheMandatPouvoirForm.getMandatChoisi());
         setMandatOperationChoisie(rechercheMandatPouvoirForm);
         pouvoir.setListMandatOperation(rechercheMandatPouvoirForm.getListdesOperationsChoisi());
       
         pouvoir.setListMandatPersonne(rechercheMandatPouvoirForm.getListdesMandatairesChoisi());
         //   rechercheMandatPouvoirForm.setActionError("");
         if(rechercheMandatPouvoirForm.getMandatChoisi().getCodSignMand().equals("C")){
             //le cas d'une signature conjointe
             int nbMandatairesValides = 0;
             
             for(Iterator it = rechercheMandatPouvoirForm.getMandatChoisi().getMandatPersonnes().iterator(); it.hasNext(); ){
                MandatPersonne mandP = (MandatPersonne)it.next();
                if(mandP.getCodEtatMp().equals("V"))
                    nbMandatairesValides = nbMandatairesValides +1;
             }
             
             
             if(rechercheMandatPouvoirForm.getMandatChoisi().getNbrMinMand().equals("") || rechercheMandatPouvoirForm.getMandatChoisi().getNbrMinMand().equals(Long.valueOf(0)) && 
                 rechercheMandatPouvoirForm.getListdesMandatairesChoisi().size() <  nbMandatairesValides  ){
                 rechercheMandatPouvoirForm.setActionError("Vous devez cocher tous les mandataires de ce mandat...");
             }else if(rechercheMandatPouvoirForm.getMandatChoisi().getNbrMinMand()>Long.valueOf(0) && nbMandatairesValides <  rechercheMandatPouvoirForm.getMandatChoisi().getNbrMinMand()){
                 rechercheMandatPouvoirForm.setActionError("Vous devez cocher au moins  "+  rechercheMandatPouvoirForm.getMandatChoisi().getNbrMinMand() +" mandataires de ce mandat...");
             }
         }
         
        //---------------------- Cas des Cotitulaires ----------------------------------------// 
        }else if (rechercheMandatPouvoirForm.getTypePouvoir().equals(Constants.COD_TYPE_POUVOIR_COTITULAIRE)){
                  pouvoir.setListCotitulaire(rechercheMandatPouvoirForm.getListMembreCotitulaireChoisi());
            
        }
        request.getSession().setAttribute("pouvoir",pouvoir);
        return mapping.findForward("success");
      }
      
    
    private void setMandatOperationChoisie(RechercheMandatPouvoirForm rechForm){

        rechForm.setListdesOperationsChoisi(new ArrayList());
        if(rechForm.getListdesOperations() != null){
            for(Iterator it = rechForm.getListdesOperations().iterator(); it.hasNext(); ){
                MandatOperation mandatOperation = (MandatOperation) it.next();
                if (mandatOperation.getMandatOperationId().getNumMaopMaop().equals(Long.valueOf(rechForm.getNumeroOperationChoisi()))){
                    rechForm.getListdesOperationsChoisi().add(mandatOperation);
                    return ;
                }
            }
        }
    }
    /*public  ActionForward chargerPouvoir(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {

       try{
            RechercheMandatPouvoirForm rechercheMandatPouvoirForm = 
                (RechercheMandatPouvoirForm)form;
           // String pathActionForward = "/"+rechercheMandatPouvoirForm.getActionFormAppelante()+".do";
            PouvoirForm pouvoirForm = (PouvoirForm)request.getSession().getAttribute("pouvoirForm"); /// form bean du chargement    
            PersonneDemandeur personneDemandeur = pouvoirForm.getPersonneDemandeur();  
            Pouvoir pouvoir = (Pouvoir)request.getSession().getAttribute("pouvoir"); /// structure de l'agent 
        
            personneDemandeur = pouvoir.chargerPouvoir(personneDemandeur);     
            
            //redirection vers la page appelante
            //ActionForward actionForward = mapping.findForward("success");
            //ActionForward newActionForward = new ActionForward();
            //newActionForward.setPath(pathActionForward);
           //newActionForward.setRedirect(true);
            return mapping.findForward("success");
        } catch (Exception e) {
            System.out.println("Erreur chargerPouvoir  " + e.getMessage());
            return mapping.findForward("error");
        }   
        
        
    }  */
      
      
      
} 

