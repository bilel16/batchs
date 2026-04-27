package com.bna.smile.model.clotureDomaine.traitement;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bna.commun.model.JourneeStructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
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

public class GetDonneeSouscriptionTrt extends Traitement {
    public GetDonneeSouscriptionTrt() {
    }

    public IValueObject perform(IValueObject vo) throws SQLException, 
                                                        Exception {
        TableauDeBordVo tableauDeBordVo = (TableauDeBordVo)vo;

        Context context = ContextHandler.getContext();
        try {

            JourneeStructureDomaine journeeStructureDomaine = 
                this.getJourneeStructureDomaine(tableauDeBordVo.getJourneeStructureDomaineId());
            if (this.checkClotureJournee()) {

                if ((journeeStructureDomaine.getCodStatJsd().equals(Constants.ETAT_JSDOM_OUV)) || 
                    (journeeStructureDomaine.getCodStatJsd().equals(Constants.ETAT_JSDOM_COURCLO))) {
                    /*mise � jour de l'etat du domaine*/

                    journeeStructureDomaine.setCodStatJsd(Constants.ETAT_JSDOM_COURCLO);
                    CRUDservice crudService = 
                        (CRUDservice)context.getBean("crudservice");
                    crudService.update(journeeStructureDomaine);

                    /*recherche des donn�e du domaine*/
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
                    if (tableauDeBordVo.getChoixRecherche().equals(Constants.DOMAINE_SOUSCRIPTION)) {
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
statistiqueSouscriptionDAO.getlistNbreSouscRejeteParTypeContrat(tableauDeBordVo.getCodeStructure(), 
                                                                DateHandler.strToDate(DateHandler.dateJour()));
                        tableauDeBordVo.setListSouscriptionRejParTypeContrat(list);

                        //----------- List nombre de signature par type de contrat
                        list = 
statistiqueSouscriptionDAO.getlistNombreSignatureParTypeContrat(tableauDeBordVo.getCodeStructure(), 
                                                                DateHandler.strToDate(DateHandler.dateJour()));
                        tableauDeBordVo.setListNombreSignatureParTypeContrat(list);

                    } else if (tableauDeBordVo.getChoixRecherche().equals(Constants.DOMAINE_PROCURATION)) {
                        //#########################################################
                        //############ PROCURATION   ##############################
                        //#########################################################   

                        //----------  nombre mandat 
                        nombre = 
                                statistiqueProcurationDAO.getNombreMandat(tableauDeBordVo.getCodeStructure(), 
                                                                          "V", 
                                                                          DateHandler.strToDate(DateHandler.dateJour()));
                        tableauDeBordVo.setNombreMandatCree(nombre);

                        //---------- List nombre mandats cr�es par type contrat  
                        list = 
statistiqueProcurationDAO.getNombreMandatParTypeContrat(tableauDeBordVo.getCodeStructure(), 
                                                        "V", 
                                                        DateHandler.strToDate(DateHandler.dateJour()));
                        tableauDeBordVo.setListMandatCreationParTypeContrat(list);

                        //---------- List nombre mandats renouvl�s par type contrat  
                        list = 
statistiqueProcurationDAO.getNombreMandatRenouvleTypeContrat(tableauDeBordVo.getCodeStructure(), 
                                                             "V", 
                                                             DateHandler.strToDate(DateHandler.dateJour()));
                        tableauDeBordVo.setListMandatRenouvellementParTypeContrat(list);

                        //---------- List nombre mandats modifi�s par type contrat  
                        list = 
statistiqueProcurationDAO.getNombreMandatModifieTypeContrat(tableauDeBordVo.getCodeStructure(), 
                                                            "H", 
                                                            DateHandler.strToDate(DateHandler.dateJour()));
                        tableauDeBordVo.setListMandatModificationParTypeContrat(list);

                        //---------- List nombre mandats annul�s par type contrat  
                        list = 
statistiqueProcurationDAO.getNombreMandatAnnulesTypeContrat(tableauDeBordVo.getCodeStructure(), 
                                                            "N", 
                                                            DateHandler.strToDate(DateHandler.dateJour()));
                        tableauDeBordVo.setListMandatAnnulationParTypeContrat(list);
                    } else if (tableauDeBordVo.getChoixRecherche().equals(Constants.DOMAINE_SUPPORT_PAIEMENT)) {
                        //#########################################################
                        //############ SUPPORT MOYEN PAIEMENT  ####################
                        //#########################################################   


                        //---------- List nombre demande carnet de ch�que  par type
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

                        list = 
statistiqueCarnetDeChequeDAO.getNombreDemandeCarnetDeCheque(tableauDeBordVo.getCodeStructure(), 
                                                            Constants.DEM_CHQ_TOT_SATISFAITE.toString(), 
                                                            DateHandler.strToDate(DateHandler.dateJour()));

                        tableauDeBordVo.setListenombreChequierDemandeParTyperRecu(list);

                        list = 
statistiqueCarnetDeChequeDAO.getNombreDemandeCarnetDeCheque(tableauDeBordVo.getCodeStructure(), 
                                                            Constants.DEM_CHQ_TOT_DELIVREE.toString(), 
                                                            DateHandler.strToDate(DateHandler.dateJour()));

                        tableauDeBordVo.setListenombreChequierDemandeParTypeDeliv(list);


                    } else if (tableauDeBordVo.getChoixRecherche().equals(Constants.DOMAINE_OPPOSITION)) {
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

                    } else if (tableauDeBordVo.getChoixRecherche().equals(Constants.DOMAINE_MODIFICATION)) {

                        list = 
statistiqueModificationDonneeDAO.getNombreModificationParTypeModification(tableauDeBordVo.getCodeStructure(), 
                                                                          DateHandler.strToDate(DateHandler.dateJour()));
                        tableauDeBordVo.setListeModificationDonneeParType(list);
                        
                        /*données ass vie */
                        GetStatAssVieTrt getStatAssVieTrt=new GetStatAssVieTrt();
                        tableauDeBordVo=(TableauDeBordVo)getStatAssVieTrt.exec(tableauDeBordVo);
                    }
                } else if (journeeStructureDomaine.getCodStatJsd().equals(Constants.ETAT_JSDOM_SCLO)) {
                    com.oxia.fwk.core.Error erreur = 
                        new com.oxia.fwk.core.Error();
                    StringBuffer text = 
                        new StringBuffer("la session n'est pas encore ouverte...");
                    erreur.setCode("100");
                    erreur.setDescription(text.toString());
                    erreur.setKey("GetDonneeSouscriptionTrt");
                    tableauDeBordVo.addError(erreur);

                } else {

                    com.oxia.fwk.core.Error erreur = 
                        new com.oxia.fwk.core.Error();
                    StringBuffer text = 
                        new StringBuffer("Le domaine est d�ja clotur�e...");
                    erreur.setCode("100");
                    erreur.setDescription(text.toString());
                    erreur.setKey("GetDonneeSouscriptionTrt");
                    tableauDeBordVo.addError(erreur);


                }
            } else {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("La journ�e est d�ja cl�tur�e...");
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("GetDonneeSouscriptionTrt");
                tableauDeBordVo.addError(erreur);

            }
            return (tableauDeBordVo);


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            erreur.setCode("Technique");
            erreur.setDescription("GetDonneeSouscriptionTrt " + 
                                  e.getMessage());
            ;
            tableauDeBordVo.addError(erreur);
            throw new Exception(e);
        }
    }

    public void genCroText(ValueObject vo) {

    }
}
