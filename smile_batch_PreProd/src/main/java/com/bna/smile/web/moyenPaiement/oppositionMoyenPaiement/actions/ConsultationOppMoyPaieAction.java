package com.bna.smile.web.moyenPaiement.oppositionMoyenPaiement.actions;


import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;

import com.bna.commun.model.StructureDomaine;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.SmileUtil;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetDetailContratCmd;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.ChargerNatureMoyPaieCmd;

import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.LeveeOppositionCIBCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.LeveeOppositionChequesCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.LeveeOppositionLivretCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.OppositionCIBCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.OppositionCarteBanqueCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.OppositionCarteCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.OppositionChequesBanqueCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.OppositionChequesCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.OppositionLivretCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamOpposition;

import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.ChargerTypeMoyPaieCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.commande.GetListOppositionCmd;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ListeNatureMoyPaie;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.model.PersonneDemandeur;
import com.bna.smile.web.commun.model.Pouvoir;

import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.moyenPaiement.oppositionMoyenPaiement.forms.OppositionMoyenPaiementForm;

import com.bna.smile.web.commun.view.ContratView;

import com.bna.smile.web.moyenPaiement.demandeCarteBnacaire.actions.PecDemandeCarteBancaireAction;
import com.bna.smile.web.moyenPaiement.oppositionMoyenPaiement.forms.ConsultationOppMoyPaieForm;

import com.bna.smile.web.souscription.forms.ConsultationContratCompteForm;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
/**
 * @author lamia
 * @since 10/04/2008
 */
public class ConsultationOppMoyPaieAction extends DispatchAction {
    /**This is the main action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * 
     */
     private static final Logger logger = Logger.getLogger(ConsultationOppMoyPaieAction.class);
    public ActionForward initierPageConsult(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {
     
        ConsultationOppMoyPaieForm consultationOppMoyPaieForm = 
            (ConsultationOppMoyPaieForm)form;
    try{  
   //    Enumeration enum1 =request.getSession().getAttributeNames(); 
        SessionUtil sessionUtil =new SessionUtil();
        //Suppression des anciens Bean de type Form de la session, SAUF "consultationOppositionMoyenPaiementForm"
        sessionUtil.removeSession(request,"consultationOppositionMoyenPaiementForm"); 
        
        ParamAgence paramAgence = 
                (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 
                
        //verification de l'habilitation sur cet operation
        StructureDomaine structureDomaine = new StructureDomaine(paramAgence.getCodStrcStrc(),Constants.COD_DOM_CONTRATCOMPTE);
        boolean bool = SmileUtil.testDomaineOuvert(structureDomaine);
        
   //     Enumeration enum2 =request.getSession().getAttributeNames(); 
        
        Listes listTypeMoyPaie = new Listes();
        ListeNatureMoyPaie listNatureMoyPaie = new ListeNatureMoyPaie();
        
        ChargerTypeMoyPaieCmd chargerTypeMoyPaieCmd= new ChargerTypeMoyPaieCmd();
        ChargerNatureMoyPaieCmd chargerNatureMoyPaieCmd= new ChargerNatureMoyPaieCmd();
        
     //   Context context = ContextHandler.getContext();
        
        consultationOppMoyPaieForm.clearForm();
     //---affectation du parametre de session code agence, maticule personnel et date du jour
      

        consultationOppMoyPaieForm.getInitialisationView().setNumMatrUser(paramAgence.getNumMatrUser().toString());
        consultationOppMoyPaieForm.getInitialisationView().setCodeAgence(paramAgence.getCodStrcStrc().toString());
        consultationOppMoyPaieForm.getParamConsultOpposition().setCodStrcStrc(paramAgence.getCodStrcStrc().toString());
        consultationOppMoyPaieForm.getParamConsultOpposition().setCodStrcStrc2(paramAgence.getCodStrcStrc().toString());
        //----------------------------------------------------------------------------------------//
        consultationOppMoyPaieForm.getParamConsultOpposition().setChoix("0");
       
     
            
         ValueObject vo =new ValueObject();
         // listes de type moyen de paiement
         listTypeMoyPaie = (Listes)chargerTypeMoyPaieCmd.execute(vo);
            if(!listTypeMoyPaie.hasError()){
              consultationOppMoyPaieForm.setListTypMoyPaie(listTypeMoyPaie.getList());
             }
          // liste pour nature moyen paie
           listNatureMoyPaie = (ListeNatureMoyPaie)chargerNatureMoyPaieCmd.execute(vo);
              if(!listNatureMoyPaie.hasError()){
                consultationOppMoyPaieForm.setListNatureCarte(listNatureMoyPaie.getListCarte());
                consultationOppMoyPaieForm.setListNatureCheque(listNatureMoyPaie.getListCheque());
               }
               
        
        } catch (Exception e) {
            ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = 
                new ActionMessage("exception.generique", 
                                  e.getMessage());
            actionMessages.add("Erreur ", actionMessage);   
            this.saveMessages(request, actionMessages);
            logger.error("Exception : ",e);
            return mapping.findForward("error");

        }
        //initialiser le type de moyen de paiement à chèque
        consultationOppMoyPaieForm.getParamConsultOpposition().setTypeMoyPaie("1");
        return mapping.findForward("success");
    }

    public ActionForward rechercherOppositionSelonChoix(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws IOException, 
                                                                          ServletException {    
   
       Context context = ContextHandler.getContext();
       ConsultationOppMoyPaieForm consultationOppMoyPaieForm =   (ConsultationOppMoyPaieForm)form;
       consultationOppMoyPaieForm.setListOpposition(null);
           try{
               ParamRechercheOpposition paramRechercheOpposition= new ParamRechercheOpposition(); 
               paramRechercheOpposition.setTypeMoyPaie(Long.valueOf(consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie()));
               paramRechercheOpposition.setTypeOper(consultationOppMoyPaieForm.getParamConsultOpposition().getTypeOperation());
               
              /* if(consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals("3")){
                       paramRechercheOpposition.setNatureMoyPaie(consultationOppMoyPaieForm.getParamConsultOpposition().getNatureCarte());
                    }else if(consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals("1")){
                                   paramRechercheOpposition.setNatureMoyPaie(consultationOppMoyPaieForm.getParamConsultOpposition().getNatureCheque());
                                }
               */
               if(consultationOppMoyPaieForm.getParamConsultOpposition().getChoix().equals("0")){
                      paramRechercheOpposition.setCodPrdPrd(Long.valueOf(consultationOppMoyPaieForm.getParamConsultOpposition().getCodPrdPrd()));
                      paramRechercheOpposition.setCodStrcStrc(Long.valueOf(consultationOppMoyPaieForm.getParamConsultOpposition().getCodStrcStrc()));
                      paramRechercheOpposition.setNumCcptCcpt(Long.valueOf(consultationOppMoyPaieForm.getParamConsultOpposition().getNumCcptCcpt()));
                          }else if(consultationOppMoyPaieForm.getParamConsultOpposition().getChoix().equals("1")){
                                       //  paramRechercheOpposition.setCodStrcStrc(Long.valueOf(consultationOppMoyPaieForm.getParamConsultOpposition().getCodStrcStrc()));
                                         paramRechercheOpposition.setTypPceDemd(Long.getLong(consultationOppMoyPaieForm.getParamConsultOpposition().getTypPcePers()));
                                         paramRechercheOpposition.setNumPceDemd(consultationOppMoyPaieForm.getParamConsultOpposition().getNumPcePers());
                                         }else if(consultationOppMoyPaieForm.getParamConsultOpposition().getChoix().equals("2")){
                                               //    paramRechercheOpposition.setCodStrcStrc(Long.valueOf(consultationOppMoyPaieForm.getParamConsultOpposition().getCodStrcStrc()));
                                                   paramRechercheOpposition.setDateDebutConsult(
                                                                            DateHandler.strToDate(consultationOppMoyPaieForm.getParamConsultOpposition().getDateDebutconsult()));
                                                   
                                                   paramRechercheOpposition.setDateFinConsult(DateHandler.addJour(DateHandler.strToDate(consultationOppMoyPaieForm.getParamConsultOpposition().getDateFinconsult())
                                                                                                           ,1));
                                                 
                                                 }else if(consultationOppMoyPaieForm.getParamConsultOpposition().getChoix().equals("3")){
                                                               paramRechercheOpposition.setCodStrcStrc(Long.valueOf(consultationOppMoyPaieForm.getParamConsultOpposition().getCodStrcStrc2()));
                                                               paramRechercheOpposition.setNumMoypTmoy(consultationOppMoyPaieForm.getParamConsultOpposition().getNumMoyp());
                                                               paramRechercheOpposition.setCodPrdPrd(Long.valueOf(consultationOppMoyPaieForm.getParamConsultOpposition().getCodPrdPrd2()));
                                                               paramRechercheOpposition.setNumCcptCcpt(Long.valueOf(consultationOppMoyPaieForm.getParamConsultOpposition().getNumCcptCcpt2()));
                                                             }
                                
               GetListOppositionCmd getListOppositionCmd= new GetListOppositionCmd();
               Listes l = new Listes();
               l = (Listes)getListOppositionCmd.execute(paramRechercheOpposition);
            if((l != null)&& l.getList().size()!=0){
              consultationOppMoyPaieForm.setListOpposition(l.getList());
            }else {
                logger.error("La liste des oppositions est vide === NULL");
            }

        }catch (Exception e) {
                    ActionMessages actionMessages = new ActionMessages();
                    ActionMessage actionMessage = 
                        new ActionMessage("exception.generique", 
                                          e.getMessage());
                    actionMessages.add("Erreur ", actionMessage);   
                    this.saveMessages(request, actionMessages);
                    logger.error("Exception : ",e);
                    return mapping.findForward("error");
        
                }
        return mapping.findForward("success");
   
 } 
    public ActionForward imprimerOppositionMoyPaie(ActionMapping mapping, 
                                                          ActionForm form, 
                                                          HttpServletRequest request, 
                                                          HttpServletResponse response) throws IOException, 
                                                                                               ServletException {
             ConsultationOppMoyPaieForm consultationOppMoyPaieForm =   (ConsultationOppMoyPaieForm)form;
             ActionMessages actionMessages = new ActionMessages();
             try {    
                 CommonReportVO valueObject = new CommonReportVO();
                 ParamAgence paramAgence = new ParamAgence();
                 paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                 Map parameters = new HashMap();
                 
                 /*---------------------------------------------------------------------*/
                 String pCodStrcStrc = "P_COD_STRC_STRC";
                 String vCodStrcStrc = paramAgence.getCodStrcStrc().toString();
                 String pCodProd = "P_COD_PRD_PRD";
                 String pNumContratCpt = "P_NUM_CCPT_CCPT";
                 
                 String vCodProd = "";
                 String vNumContratCpt = "";
                /*------------------------------------------------------------------*/
                  String pCodTpceTpce = "P_COD_TPCE_TPCE";
                  String pNumTpceTpce = "P_NUM_PCE_OPMP";
                 String vCodTpceTpce = "";
                 String vNumTpceTpce = "";
                /*------------------------------------------------------------------*/
                 String pDateDeb = "P_DATE_DEB";
                 String pDateFin = "P_DATE_FIN";
                 String vDateFin="";
                 String vDateDeb="";
                 /*-----------------------------------------------------------------*/
                 String pTypeOper ="P_TYP_OPER";
                 String pTypeMoyPaie="P_TYP_MOYP";
                 String vTypeOper ="";
                 Long vTypeMoyPaie= Long.valueOf(consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie());
                // String pNaturCarte ="P_NATUR_CARTE";
                // Long vNaturCarte= new Long("0");
                 //String pNaturCheque ="P_NATUR_CHEQUE";
                 //String vNaturCheque= "S";
                 String pMatrUser = "P_NUM_MATR_USER";
                 String vMatrUser = paramAgence.getNumMatrUser().toString();
                 String pLibEtat="P_LIB_ETAT";
                 String vLibEtat="";
                // String natureCheq="";
              //   String natureCarte="";
                 //remplissage parametre nature de carte
               /*   if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(Constants.COD_MOYP_TMOY_Carte.toString())){
                      if(consultationOppMoyPaieForm.getParamConsultOpposition().getNatureCarte().equals(Constants.COD_TCAR_TCAR_ELECTRON)){
                          vNaturCarte = Long.valueOf(Constants.COD_TCAR_TCAR_ELECTRON);  natureCarte="BNA Visa Electron";
                          }else if(consultationOppMoyPaieForm.getParamConsultOpposition().getNatureCarte().equals(Constants.COD_TCAR_TCAR_CIBT)){
                              vNaturCarte = Long.valueOf(Constants.COD_TCAR_TCAR_CIBT);    natureCarte="CIBT";
                              }else if(consultationOppMoyPaieForm.getParamConsultOpposition().getNatureCarte().equals(Constants.COD_TCAR_TCAR_MAST_INT)){
                                      vNaturCarte = Long.valueOf(Constants.COD_TCAR_TCAR_MAST_INT);    natureCarte="Master Card Internationale";
                                      }else if(consultationOppMoyPaieForm.getParamConsultOpposition().getNatureCarte().equals(Constants.COD_TCAR_TCAR_MAST_NAT)){
                                          vNaturCarte = Long.valueOf(Constants.COD_TCAR_TCAR_MAST_NAT);      natureCarte="Master Card Nationale";
                                          }else if(consultationOppMoyPaieForm.getParamConsultOpposition().getNatureCarte().equals(Constants.COD_TCAR_TCAR_VISAGOLD_INT)){
                                                  vNaturCarte = Long.valueOf(Constants.COD_TCAR_TCAR_VISAGOLD_INT);      natureCarte="Visa Gold Internationale";
                                                  }else if(consultationOppMoyPaieForm.getParamConsultOpposition().getNatureCarte().equals(Constants.COD_TCAR_TCAR_VISAGOLD_NAT)){
                                                          vNaturCarte = Long.valueOf(Constants.COD_TCAR_TCAR_VISAGOLD_NAT);       natureCarte="Visa Gold Nationale";
                                                          }else if(consultationOppMoyPaieForm.getParamConsultOpposition().getNatureCarte().equals(Constants.COD_TCAR_TCAR_VISA_INT)){
                                                                  vNaturCarte = Long.valueOf(Constants.COD_TCAR_TCAR_VISA_INT);          natureCarte="Visa Internationale";
                                                                  }else if(consultationOppMoyPaieForm.getParamConsultOpposition().getNatureCarte().equals(Constants.COD_TCAR_TCAR_VISA_NAT)){
                                                                          vNaturCarte = Long.valueOf(Constants.COD_TCAR_TCAR_VISA_NAT);       natureCarte="Visa Nationale";
                                                                          }
                   }else if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(Constants.COD_MOYP_TMOY_Cheque.toString())){
                       //remplissage parametre nature de chèque
                            if(consultationOppMoyPaieForm.getParamConsultOpposition().getNatureCheque().equals(Constants.CODE_LETTRE_CHEQUE)){
                                vNaturCheque = Constants.CODE_LETTRE_CHEQUE;     natureCheq="Lettre de chèque";
                            }else if(consultationOppMoyPaieForm.getParamConsultOpposition().getNatureCheque().equals(Constants.CODE_CHEQUE_STANDARD)){
                                     vNaturCheque = Constants.CODE_CHEQUE_STANDARD;     natureCheq="Chèque standard";
                                    }else if(consultationOppMoyPaieForm.getParamConsultOpposition().getNatureCheque().equals(Constants.CODE_CHEQUE_PERSONALISE)){
                                            vNaturCheque = Constants.CODE_CHEQUE_PERSONALISE;   natureCheq="Chèque personalisé";
                                           }
                   }    
                 */
           if(consultationOppMoyPaieForm.getParamConsultOpposition().getTypeOperation().equals(Constants.COD_ETAT_OPMP_Opposition)){
               vTypeOper=Constants.COD_ETAT_OPMP_Opposition;
              if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(Constants.COD_MOYP_TMOY_Cheque.toString())){
                       vLibEtat = "Liste des oppositions sur chèques";
                       vTypeMoyPaie=Constants.COD_MOYP_TMOY_Cheque;
                       }else if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(Constants.COD_MOYP_TMOY_Carte.toString())){
                                 //  StringBuffer text = new StringBuffer("Liste des oppositions sur cartes bancaires (");
                                //   text.append(natureCarte);
                                 //  text.append(")");
                                   vLibEtat = "Liste des oppositions sur cartes bancaires";
                                   vTypeMoyPaie=Constants.COD_MOYP_TMOY_Carte;
                               } else if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(Constants.COD_MOYP_TMOY_CIB.toString())){
                                           vLibEtat = "Liste des oppositions sur CIB";
                                           vTypeMoyPaie=Constants.COD_MOYP_TMOY_CIB;
                                       }else if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(Constants.COD_MOYP_TMOY_Livret.toString())){
                                           vLibEtat = "Liste des oppositions sur livrets";
                                           vTypeMoyPaie=Constants.COD_MOYP_TMOY_Livret;
                                       }else if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(Constants.COD_MOYP_TMOY_Traite.toString())){
                                               vLibEtat = "Liste des oppositions sur traites";
                                               vTypeMoyPaie=Constants.COD_MOYP_TMOY_Traite;
                                           }else  if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(Constants.COD_MOYP_TMOY_BC_Plac.toString())){
                                               vLibEtat = "Liste des oppositions sur Bon de Caisse (Placement)";
                                               vTypeMoyPaie=Constants.COD_MOYP_TMOY_BC_Plac;
                                           }
               }else {
                   vTypeOper=Constants.COD_ETAT_OPMP_Levet;
                   if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(Constants.COD_MOYP_TMOY_Cheque.toString())){
                           vLibEtat = "Liste des 'Main Levée' sur chèques";
                           vTypeMoyPaie=Constants.COD_MOYP_TMOY_Cheque;
                           }else if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(Constants.COD_MOYP_TMOY_Carte.toString())){
                                       vLibEtat = "Liste des 'Main Levée' sur cartes bancaires";
                                       vTypeMoyPaie=Constants.COD_MOYP_TMOY_Carte;
                                   } else if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(Constants.COD_MOYP_TMOY_CIB.toString())){
                                               vLibEtat = "Liste des 'Main Levée' sur CIB";
                                               vTypeMoyPaie=Constants.COD_MOYP_TMOY_CIB;
                                           }else if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(Constants.COD_MOYP_TMOY_Livret.toString())){
                                               vLibEtat = "Liste des 'Main Levée' sur livrets";
                                               vTypeMoyPaie=Constants.COD_MOYP_TMOY_Livret;
                                           }else if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(Constants.COD_MOYP_TMOY_Traite.toString())){
                                                   vLibEtat = "Liste des 'Main Levée' sur traites";
                                                   vTypeMoyPaie=Constants.COD_MOYP_TMOY_Traite;
                                               }else if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(Constants.COD_MOYP_TMOY_BC_Plac.toString())){
                                                   vLibEtat = "Liste des 'Main Levée' sur Bon de Caisse (Placement)";
                                                   vTypeMoyPaie=Constants.COD_MOYP_TMOY_BC_Plac;
                                               }
               }
               
           
                 parameters.put(pLibEtat, vLibEtat);
                 parameters.put(pCodStrcStrc, vCodStrcStrc);
                 
                 parameters.put(pTypeMoyPaie, vTypeMoyPaie); 
                 parameters.put(pTypeOper, vTypeOper);  
                // parameters.put(pNaturCheque, vNaturCheque);  
          //       parameters.put(pNaturCarte, vNaturCarte); 
                 
                 parameters.put(pMatrUser, vMatrUser);
                 
                if (consultationOppMoyPaieForm.getParamConsultOpposition().getChoix().equals("2")) {
                       
                         if(!consultationOppMoyPaieForm.getParamConsultOpposition().getDateDebutconsult().equals("") &&
                            !consultationOppMoyPaieForm.getParamConsultOpposition().getDateFinconsult().equals("")){
                                vDateFin=consultationOppMoyPaieForm.getParamConsultOpposition().getDateFinconsult();
                                vDateDeb=consultationOppMoyPaieForm.getParamConsultOpposition().getDateDebutconsult();
                                parameters.put(pDateDeb,vDateDeb);
                                parameters.put(pDateFin,vDateFin);
                            /*    if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(
                                                                Constants.COD_MOYP_TMOY_Carte.toString())){
                                      valueObject.setNomReport("listeOppMP_periode_Carte");
                                      }else if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(
                                                                    Constants.COD_MOYP_TMOY_Cheque.toString())){
                                                        valueObject.setNomReport("listeOppMP_periode_Cheque");
                                                       }else {*/
                                                                valueObject.setNomReport("listeOppMP_periode"); //  }
    
                            }else {
                              //   valueObject.setNomReport("listeContratSD");
                                 }
                      
                     } else if (consultationOppMoyPaieForm.getParamConsultOpposition().getChoix().equals("1")) {
                                            vCodTpceTpce =consultationOppMoyPaieForm.getParamConsultOpposition().getTypPcePers();
                                            vNumTpceTpce = consultationOppMoyPaieForm.getParamConsultOpposition().getNumPcePers();
                                            parameters.put(pCodTpceTpce,vCodTpceTpce);
                                            parameters.put(pNumTpceTpce,vNumTpceTpce);
                                           /*    if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(
                                                                               Constants.COD_MOYP_TMOY_Carte.toString())){
                                                     valueObject.setNomReport("listeOppMP_Pce_Carte");
                                                     }else if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(
                                                                                   Constants.COD_MOYP_TMOY_Cheque.toString())){
                                                                       valueObject.setNomReport("listeOppMP_Pce_Cheque");
                                                                       }else {*/
                                                                               valueObject.setNomReport("listeOppMP_Pce");  // }
                                           }else if (consultationOppMoyPaieForm.getParamConsultOpposition().getChoix().equals("0")) {
                                                         vCodProd= consultationOppMoyPaieForm.getParamConsultOpposition().getCodPrdPrd();
                                                         vNumContratCpt = consultationOppMoyPaieForm.getParamConsultOpposition().getNumCcptCcpt();
                                                         parameters.put(pCodProd,vCodProd);
                                                         parameters.put(pNumContratCpt,vNumContratCpt);
                                                    
                                                     /*  if (consultationOppMoyPaieForm.getParamConsultOpposition().getTypeMoyPaie().equals(
                                                                                         Constants.COD_MOYP_TMOY_Carte.toString())){
                                                               valueObject.setNomReport("listeOppMP_ccpt_Carte");
                                                                }else {*/
                                                                          valueObject.setNomReport("listeOppMP_ccpt"); //  }
                                                        
                                                     }
                 
                 valueObject.setParams(parameters);
                 
                 //valueObject.setTypeImpression("F");/*P : printer , F: file */
                 request.getSession().setAttribute("CommonPrintVo",valueObject);
                 request.setAttribute("print","1");
                 
             return mapping.findForward("success");
             } catch (Exception e) {
             com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
             StringBuffer text = 
                 new StringBuffer("la transaction est Interrompu, une erreur dans ConsultationOppMoyPaieAction / Dispatch Action :imprimerOppositionMoyPaie ");
             text.append(e.toString());
             erreur.setCode("200");
             erreur.setDescription(text.toString());
             ActionMessage actionMessage = 
                 new ActionMessage("exception.generique", 
                                   erreur.getDescription());
             actionMessages.add("Erreur ", actionMessage);
             this.saveMessages(request, actionMessages);
             logger.error("Exception : ",e);
             return mapping.findForward("error");
             }                                                                                
                                                                                               
         } 
}
