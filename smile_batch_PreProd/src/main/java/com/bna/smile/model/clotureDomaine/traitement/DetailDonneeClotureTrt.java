package com.bna.smile.model.clotureDomaine.traitement;


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

public class DetailDonneeClotureTrt extends Traitement {
    public DetailDonneeClotureTrt() {
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

            //############ SOUSCRIPTION  ##############################

            if (tableauDeBordVo.getChoixRecherche().equals(Constants.DOMAINE_SOUSCRIPTION)) {

                //----------- List  contrat en attente
                list = 
statistiqueSouscriptionDAO.getlistSouscriptionProduit(tableauDeBordVo.getCodeStructure(), 
                                                      "A", 
                                                      DateHandler.strToDate(DateHandler.dateJour()), 
                                                      tableauDeBordVo.getCodeProduit());
                tableauDeBordVo.setListDetailSouscrEnAttente(list);

                //----------- List  contrat valide
                list = 
statistiqueSouscriptionDAO.getlistSouscriptionProduit(tableauDeBordVo.getCodeStructure(), 
                                                      "V", 
                                                      DateHandler.strToDate(DateHandler.dateJour()), 
                                                      tableauDeBordVo.getCodeProduit());
                tableauDeBordVo.setListDetailSouscrvalide(list);

                //----------- List  contrat rejeté

                list = 
statistiqueSouscriptionDAO.getlistSouscRejProduit(tableauDeBordVo.getCodeStructure(), 
                                                  DateHandler.strToDate(DateHandler.dateJour()), 
                                                  tableauDeBordVo.getCodeProduit());
                tableauDeBordVo.setListDetailSouscrRej(list);

                //----------- List nombre de signature par type de contrat
                list = 
statistiqueSouscriptionDAO.getlistNombreSignatureParTypeContrat(tableauDeBordVo.getCodeStructure(), 
                                                                DateHandler.strToDate(DateHandler.dateJour()));
                tableauDeBordVo.setListNombreSignatureParTypeContrat(list);

            } else if (tableauDeBordVo.getChoixRecherche().equals(Constants.DOMAINE_PROCURATION)) {
                //#########################################################
                //############ PROCURATION   ##############################
                //#########################################################   


                //---------- List creation Mandat 
                list = 
statistiqueProcurationDAO.getlistCreationProcuration(tableauDeBordVo.getCodeStructure(), 
                                                     new Long(3), new Long(3), 
                                                     DateHandler.strToDate(DateHandler.dateJour()), 
                                                     tableauDeBordVo.getCodeProduit());
                tableauDeBordVo.setListeDetailCreationMandat(list);

                list = 
statistiqueProcurationDAO.getlistCreationProcuration(tableauDeBordVo.getCodeStructure(), 
                                                     new Long(5), new Long(3), 
                                                     DateHandler.strToDate(DateHandler.dateJour()), 
                                                     tableauDeBordVo.getCodeProduit());
                tableauDeBordVo.setListeDetailModifMandat(list);

                list = 
statistiqueProcurationDAO.getlistCreationProcuration(tableauDeBordVo.getCodeStructure(), 
                                                     new Long(6), new Long(3), 
                                                     DateHandler.strToDate(DateHandler.dateJour()), 
                                                     tableauDeBordVo.getCodeProduit());
                tableauDeBordVo.setListeDetailAnnulMandat(list);

                list = 
statistiqueProcurationDAO.getlistCreationProcuration(tableauDeBordVo.getCodeStructure(), 
                                                     new Long(4), new Long(3), 
                                                     DateHandler.strToDate(DateHandler.dateJour()), 
                                                     tableauDeBordVo.getCodeProduit());
                tableauDeBordVo.setListeDetailRenouvMandat(list);

            } else if (tableauDeBordVo.getChoixRecherche().equals(Constants.DOMAINE_SUPPORT_PAIEMENT)) {
                //#########################################################
                //############ SUPPORT MOYEN PAIEMENT  ####################
                //#########################################################   


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
