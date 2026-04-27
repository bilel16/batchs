package com.bna.smile.model.clotureDomaine.traitement;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.JourneeStructureDomaine;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.clotureDomaine.model.JournStrucDomVo;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.security.abc.model.Personnel;

public class ClotureDomaineTrt extends Traitement {
    public ClotureDomaineTrt() {
    }


    public IValueObject perform(IValueObject vo) {

        this.setCroFlag(false);
        Context context = ContextHandler.getContext();
        ISearchEngine searchEngine = 
            (ISearchEngine)Context.getInstance().getSpringContext().getBean("searchEngine");
        ICriteria criteria = searchEngine.createCriteria();
        IExpression expression = searchEngine.createExpression();
        JournStrucDomVo journStrucDomVo = (JournStrucDomVo)vo;
        JourneeStructureDomaine journeeStructureDomaine = 
            this.getJourneeStructureDomaine(journStrucDomVo.getJourneeStructureDomaineId());
        journStrucDomVo.setLibDomaine(journeeStructureDomaine.getDomaineMetier().getLibDomDomm());
        try {
            if (this.checkClotureJournee()) {
                if ((journeeStructureDomaine.getCodStatJsd().equals(Constants.ETAT_JSDOM_COURCLO)) || 
                    (journeeStructureDomaine.getCodStatJsd().equals(Constants.ETAT_JSDOM_OUV))) {

                    journeeStructureDomaine.setCodStatJsd(Constants.ETAT_JSDOM_CLO);
                    journeeStructureDomaine.setDatCloJsd(new Date());
                    CRUDservice crudService = 
                        (CRUDservice)context.getBean("crudservice");
                    crudService.update(journeeStructureDomaine);

                    /*generation cro*/
                    Structure strcture = 
                        (Structure)searchEngine.get(Structure.class, 
                                                    journStrucDomVo.getJourneeStructureDomaineId().get_codStrcStrc());
                    if (strcture.getTypeStructure().getCodTstrTstr().equals(new Long(1))) {
                        this.setCroFlag(true);
                    }


                } else {
                    com.oxia.fwk.core.Error erreur = 
                        new com.oxia.fwk.core.Error();
                    StringBuffer text = 
                        new StringBuffer("Le domaine est d�ja cl�tur�e...");
                    erreur.setCode("100");
                    erreur.setDescription(text.toString());
                    erreur.setKey("ClotureDomaineTrt");
                    journStrucDomVo.addError(erreur);
                }
            } else {
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = 
                    new StringBuffer("La journ�e est d�ja cl�tur�e...");
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("ClotureDomaineTrt");
                journStrucDomVo.addError(erreur);

            }


        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans ClotureDomaineTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("ClotureDomaineTrt");
            journStrucDomVo.addError(erreur);
            logger.error(" *** Erreur lors de la ClotureDomaineTrt concernant l'agence " + 
                         journStrucDomVo.getJourneeStructureDomaineId().getCodStrcStrc() + 
                         " : ", e);
            throw new RuntimeException(e);

        }
        return (journStrucDomVo);
    }


    public void genCroText(ValueObject vo) {
        JournStrucDomVo journStrucDomVo = (JournStrucDomVo)vo;
        try {

            /*partie fixe*/
            Object obj = 
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Personnel user = null;
            if (obj instanceof UserDetails) {
                user = (Personnel)obj;
            }

            this.setLibRefCro("SMILE:DOMAINE");
            this.setNumRefCro(journStrucDomVo.getJourneeStructureDomaineId().getCodDomDomm());
            this.setDateOperation(journStrucDomVo.getJourneeStructureDomaineId().getDatJrnJrn());
            this.setCodeStructInitiatrice(journStrucDomVo.getJourneeStructureDomaineId().getCodStrcStrc().toString());
            this.setCodEtatCro(0);
            this.setCodeProduit(journStrucDomVo.getJourneeStructureDomaineId().getCodDomDomm().toString());
            this.setOperationId(Constants.COD_OPER_CLO_DOM);
            this.setCodTachTach(1);
            this.setTypeOperationCro("O");
            this.setDatValCro(journStrucDomVo.getJourneeStructureDomaineId().getDatJrnJrn());
            SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");
            String heureString = formater.format(new Date());
            this.setHeureOperation(heureString);
            this.setNumCinUser(user.getNumMatrUser());
            this.setCodTypUser(user.getMatriculeTyp());
            this.setCodStrcImpt(journStrucDomVo.getJourneeStructureDomaineId().getCodStrcStrc());
            this.setDatExecCro(new Date());
            this.setDatValCom(journStrucDomVo.getJourneeStructureDomaineId().getDatJrnJrn());
            this.setCodRefcOmp("NULL");

            /*partie variable*/

            StringBuffer cro = new StringBuffer("");
            ListOrderedMap listTypModif = null;
            if (journStrucDomVo.getJourneeStructureDomaineId().getCodDomDomm().equals(Constants.COD_DOM_CLIENT)) {

                cro.append("COD_OPER_OPER=" + 
                           Constants.COD_OPER_PRELEV_ASSUR_VIE + 
                           ";" + 
                           "COD_DONE_DICT=OPERATION_MOY_PAY.MONT_DIN_OMP" + 
                           ";" + "TOTAL_RECAP=" + 
                           journStrucDomVo.getNbrOper672() + ";" + 
                           "MONTANT_RECAP=" + 
                            Math.round(journStrucDomVo.getMntOper672()) + ";");
                           
                cro.append("COD_OPER_OPER=" + 
                           Constants.COD_OPER_REGLEMENT_ASSUR_VIE + 
                           ";" + 
                           "COD_DONE_DICT=OPERATION_MOY_PAY.MONT_DIN_OMP" + 
                           ";" + "TOTAL_RECAP=" + 
                           journStrucDomVo.getNbrOper703() + ";" + 
                           "MONTANT_RECAP=" + 
                            Math.round(journStrucDomVo.getMntOper703()) + ";");


            } else {
                cro.append("COD_OPER_OPER=" + 
                           Constants.OPER_DEMANDE_SOUSC_COMPTE.toString() + 
                           ";" + "COD_DONE_DICT=NULL" + ";" + "TOTAL_RECAP=" + 
                           journStrucDomVo.getNbrSousc() + ";" + 
                           "MONTANT_RECAP=0" + ";");

                cro.append("COD_OPER_OPER=" + 
                           Constants.COD_OPER_CREAT_MANDAT.toString() + ";" + 
                           "COD_DONE_DICT=NULL" + ";" + "TOTAL_RECAP=" + 
                           journStrucDomVo.getNbrMandCre() + ";" + 
                           "MONTANT_RECAP=0" + ";");

                cro.append("COD_OPER_OPER=" + 
                           Constants.COD_OPER_MODIF_MANDAT.toString() + ";" + 
                           "COD_DONE_DICT=NULL" + ";" + "TOTAL_RECAP=" + 
                           journStrucDomVo.getNbrMandMod() + ";" + 
                           "MONTANT_RECAP=0" + ";");

                cro.append("COD_OPER_OPER=" + 
                           Constants.COD_OPER_RENOUV_MAND.toString() + ";" + 
                           "COD_DONE_DICT=NULL" + ";" + "TOTAL_RECAP=" + 
                           journStrucDomVo.getNbrMandRen() + ";" + 
                           "MONTANT_RECAP=0" + ";");

                cro.append("COD_OPER_OPER=" + 
                           Constants.COD_OPER_ANNUL_MAND.toString() + ";" + 
                           "COD_DONE_DICT=NULL" + ";" + "TOTAL_RECAP=" + 
                           journStrucDomVo.getNbrMandAnn() + ";" + 
                           "MONTANT_RECAP=0" + ";");

                cro.append("COD_OPER_OPER=" + 
                           Constants.OPER_VALIDATION_DEM_CHQ.toString() + ";" + 
                           "COD_DONE_DICT=NULL" + ";" + "TOTAL_RECAP=" + 
                           journStrucDomVo.getNbrCheq() + ";" + 
                           "MONTANT_RECAP=0" + ";");


            }
            this.setCroText(cro.toString());


        }


        catch (Exception e) {

            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans ClotureDomaineTrt : ");
            text.append(e.toString());
            erreur.setCode("200");
            erreur.setDescription(text.toString());
            erreur.setKey("ClotureDomaineTrt");
            vo.addError(erreur);
            logger.error(" *** Erreur lors de la ClotureDomaineTrt du mandat concernant l'agence " + 
                         journStrucDomVo.getJourneeStructureDomaineId().getCodStrcStrc() + 
                         " : ", e);
        }
    }


    public String getNumeroTache(ValueObject vo) {

        return (Constants.COD_OPER_CLO_DOM + 
                StrHandler.lpad(Constants.COD_TACH_CLO_DOM, '0', 2));
    }

}
