package com.bna.smile.model.statistique.traitement;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.statistique.dao.StatistiqueCarnetDeChequeDAO;
import com.bna.smile.model.statistique.dao.StatistiqueCarteDAO;
import com.bna.smile.model.statistique.dao.StatistiqueModificationDonneeDAO;
import com.bna.smile.model.statistique.dao.StatistiqueOppositionDAO;
import com.bna.smile.model.statistique.dao.StatistiqueProcurationDAO;
import com.bna.smile.model.statistique.dao.StatistiqueSouscriptionDAO;
import com.bna.smile.model.statistique.dao.TableauDeBordDAO;
import com.bna.smile.model.statistique.model.TableauDeBordVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetTableauDeBordTrt extends Traitement {
    public GetTableauDeBordTrt() {
    }


    public IValueObject perform(IValueObject vo) throws SQLException, 
                                                        Exception {

        TableauDeBordVo tableauDeBordVo = (TableauDeBordVo)vo;

        Context context = ContextHandler.getContext();


        try {
            TableauDeBordDAO tableauDeBordDAO = 
                (TableauDeBordDAO)context.getBean("tableauDeBordDAO");
            StatistiqueSouscriptionDAO statistiqueSouscriptionDAO = 
                (StatistiqueSouscriptionDAO)context.getBean("statistiqueSouscriptionDAO");
            StatistiqueProcurationDAO statistiqueProcurationDAO = 
                (StatistiqueProcurationDAO)context.getBean("statistiqueProcurationDAO");
            StatistiqueCarnetDeChequeDAO statistiqueCarnetDeChequeDAO = 
                (StatistiqueCarnetDeChequeDAO)context.getBean("statistiqueCarnetDeChequeDAO");
            StatistiqueCarteDAO statistiqueCarteDAO = 
                (StatistiqueCarteDAO)context.getBean("statistiqueCarteDAO");
            
            StatistiqueOppositionDAO statistiqueOppositionDAO = 
                (StatistiqueOppositionDAO)context.getBean("statistiqueOppositionDAO");

            StatistiqueModificationDonneeDAO statistiqueModificationDonneeDAO = 
                (StatistiqueModificationDonneeDAO)context.getBean("statistiqueModificationDonneeDAO");
            Long nombre = Long.valueOf(0);
            List list = new ArrayList();
            //#########################################################
            //############ SOUSCRIPTION  ##############################
            //#########################################################
          if(tableauDeBordVo.getChoixRecherche().equals(Constants.DOMAINE_SOUSCRIPTION)){
            //---------- Nombre de contrat 
             nombre = 
                statistiqueSouscriptionDAO.getNombreTotalSouscription(tableauDeBordVo.getCodeStructure(), 
                                                                      "V", 
                                                                      DateHandler.strToDate(DateHandler.dateJour()));
              tableauDeBordVo.setNombreTatalSouscription(nombre);
              tableauDeBordVo.getStatSouscription().setNbrSouscrVal(nombre);
              nombre = 
                 statistiqueSouscriptionDAO.getNombreTotalSouscription(tableauDeBordVo.getCodeStructure(), 
                                                                       "A", 
                                                                       DateHandler.strToDate(DateHandler.dateJour()));
           
               tableauDeBordVo.getStatSouscription().setNbrSouscrAtt(nombre);
              nombre = 
                 statistiqueSouscriptionDAO.getNombreTotalSouscription(tableauDeBordVo.getCodeStructure(), 
                                                                       "R", 
                                                                       DateHandler.strToDate(DateHandler.dateJour()));
               tableauDeBordVo.getStatSouscription().setNbrSouscrRes(nombre);   
                 
              

            //----------- List nombre contrat par produit
             list = 
                statistiqueSouscriptionDAO.getlistNombreSouscriptionParTypeContrat(tableauDeBordVo.getCodeStructure(), 
                                                                                   "V", 
                                                                                   DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListNombreSouscriptionParTypeContrat(list);
              list = 
                 statistiqueSouscriptionDAO.getlistNombreSouscriptionParTypeContrat(tableauDeBordVo.getCodeStructure(), 
                                                                                    "A", 
                                                                                    DateHandler.strToDate(DateHandler.dateJour()));
              tableauDeBordVo.setListSouscriptionAttParTypeContrat(list);
              list = 
                 statistiqueSouscriptionDAO.getlistNombreSouscriptionParTypeContrat(tableauDeBordVo.getCodeStructure(), 
                                                                                    "N", 
                                                                                    DateHandler.strToDate(DateHandler.dateJour()));
              tableauDeBordVo.setListSouscriptionAnnParTypeContrat(list);              
              list = 
                 statistiqueSouscriptionDAO.getlistNombreSouscriptionParTypeContrat(tableauDeBordVo.getCodeStructure(), 
                                                                                    "B", 
                                                                                    DateHandler.strToDate(DateHandler.dateJour()));
            /*  tableauDeBordVo.setListSouscriptionBlocParTypeContrat(list);  
              list = 
                 statistiqueSouscriptionDAO.getlistNombreSouscriptionParTypeContrat(tableauDeBordVo.getCodeStructure(), 
                                                                                    "T", 
                                                                                    DateHandler.strToDate(DateHandler.dateJour()));
              tableauDeBordVo.setListSouscriptionCtxParTypeContrat(list); */                

            //----------- List nombre contrat par type client
            list = 
statistiqueSouscriptionDAO.getlistNombreSouscriptionParTypeClient(tableauDeBordVo.getCodeStructure(), 
                                                                  "V", 
                                                                  DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListNombreSouscriptionParTypeClient(list);
              //----------- List nombre contrat en attente par type client
              list = 
              statistiqueSouscriptionDAO.getlistNombreSouscriptionParTypeClient(tableauDeBordVo.getCodeStructure(),
                                                                    "A", 
                                                                    DateHandler.strToDate(DateHandler.dateJour()));
              tableauDeBordVo.setListNombreSouscAttParTypeClient(list);
          
            //----------- List nombre de signature par type de contrat
            list = 
            statistiqueSouscriptionDAO.getlistNombreSignatureParTypeContrat(tableauDeBordVo.getCodeStructure(), DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListNombreSignatureParTypeContrat(list);
          
          }else  if(tableauDeBordVo.getChoixRecherche().equals(Constants.DOMAINE_PROCURATION)){
            //#########################################################
            //############ PROCURATION   ##############################
            //#########################################################   

            //----------  nombre mandat 
            nombre = 
                    statistiqueProcurationDAO.getNombreMandat(tableauDeBordVo.getCodeStructure(), 
                                                              "V", 
                                                              DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setNombreMandatCree(nombre);

            //---------- List nombre mandats crées par type contrat  
            list = 
statistiqueProcurationDAO.getNombreMandatParTypeContrat(tableauDeBordVo.getCodeStructure(), 
                                                        "V", 
                                                        DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListMandatCreationParTypeContrat(list);

            //---------- List nombre mandats renouvlés par type contrat  
            list = 
statistiqueProcurationDAO.getNombreMandatRenouvleTypeContrat(tableauDeBordVo.getCodeStructure(), 
                                                             "V", 
                                                             DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListMandatRenouvellementParTypeContrat(list);

            //---------- List nombre mandats modifiés par type contrat  
            list = 
statistiqueProcurationDAO.getNombreMandatModifieTypeContrat(tableauDeBordVo.getCodeStructure(), 
                                                            "H", 
                                                            DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListMandatModificationParTypeContrat(list);

            //---------- List nombre mandats annulés par type contrat  
            list = 
statistiqueProcurationDAO.getNombreMandatAnnulesTypeContrat(tableauDeBordVo.getCodeStructure(), 
                                                            "N", 
                                                            DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListMandatAnnulationParTypeContrat(list);

            //#########################################################
            //############ SUPPORT MOYEN PAIEMENT  ####################
            //#########################################################   
         
         }else  if(tableauDeBordVo.getChoixRecherche().equals(Constants.DOMAINE_SUPPORT_PAIEMENT)){
         
            //---------- List nombre demande carnet de chèque  par type
            list = 
            statistiqueCarnetDeChequeDAO.getNombreDemandeCarnetDeCheque(tableauDeBordVo.getCodeStructure(), 
                                                     Constants.DEM_CHQ_VALIDEE.toString(), 
                                                     DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListenombreChequierDemandeParTypeValide(list);
            
            list = 
            statistiqueCarnetDeChequeDAO.getNombreDemandeCarnetDeCheque(tableauDeBordVo.getCodeStructure(),
                                                     Constants.DEM_CHQ_ATTENTE.toString(), 
                                                     DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListenombreChequierDemandeParTypeAttente(list);

            list = 
            statistiqueCarnetDeChequeDAO.getNombreDemandeCarnetDeCheque(tableauDeBordVo.getCodeStructure(),
                                                     Constants.DEM_CHQ_REJETEE.toString(), 
                                                     DateHandler.strToDate(DateHandler.dateJour()));
                                                     
            tableauDeBordVo.setListenombreChequierDemandeParTypeRejete(list);
            
        
            //---------- List nombre demandede carte  par type (etat 5)
            
            list = statistiqueCarteDAO.getNombreCarteDemande(tableauDeBordVo.getCodeStructure(), 
                                          Constants.COD_ETAT_DCAR_Valider, 
                                          DateHandler.strToDate(DateHandler.dateJour())); // liset des demande de catres validées par type de carte
            
            tableauDeBordVo.setListenombreCarteDemandeParType(list);
            
            //-- les cartes non encore validées ( etat 1/2/3/4 )
            list = statistiqueCarteDAO.getListeNombreCarteDemandeNonValide (tableauDeBordVo.getCodeStructure(), DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListenombreCarteDemandeParTypeNonValide(list);
            
            //-- les cartes reçus
            list = statistiqueCarteDAO.getListeNombreCarteRecus(tableauDeBordVo.getCodeStructure(), DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListenombreCarteRecuParType(list);
            
            //-- les cartes délivrées
            list = statistiqueCarteDAO.getListeNombreCarteDelivrees(tableauDeBordVo.getCodeStructure(), DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListenombreCarteDelivreParType(list);
     
            //-- les cartes annulées        
            list = statistiqueCarteDAO.getListeNombreCarteAnnulees(tableauDeBordVo.getCodeStructure(), DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListenombreCarteAnnuleParType(list);
            
            //-- les cartes rejetées        
            list = statistiqueCarteDAO.getListeNombreCarteRejetees(tableauDeBordVo.getCodeStructure(), DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListenombreCarteRejeteesParType(list);
                      
           }else  if(tableauDeBordVo.getChoixRecherche().equals(Constants.DOMAINE_OPPOSITION)){
            //---------- Opposition Moyens de paiement
            list = 
            statistiqueOppositionDAO.getNombreOpposition(tableauDeBordVo.getCodeStructure(),
                                          Constants.COD_ETAT_OPMP_Opposition, 
                                          DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListeOppositionParType(list);
            
            list = 
            statistiqueOppositionDAO.getNombreOpposition(tableauDeBordVo.getCodeStructure(),
                                          Constants.COD_ETAT_OPMP_Levet, 
                                          DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListLeveOppositionParType(list);
            
            //#########################################################
            //############ MODIFICATION DONNEES CLIENT #################
            //#########################################################   
            
             }else  if(tableauDeBordVo.getChoixRecherche().equals(Constants.DOMAINE_MODIFICATION)){
             
            list = statistiqueModificationDonneeDAO.getNombreModificationParTypeModification(tableauDeBordVo.getCodeStructure(),DateHandler.strToDate(DateHandler.dateJour()));
            tableauDeBordVo.setListeModificationDonneeParType(list);
            
            }
            
            return (tableauDeBordVo);

           
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("GetTableauDeBordTrt " + e.getMessage());
            ;
            tableauDeBordVo.addError(erreur);
            throw new Exception(e);
        }
    }

    public void genCroText(ValueObject vo) {

    }
}
