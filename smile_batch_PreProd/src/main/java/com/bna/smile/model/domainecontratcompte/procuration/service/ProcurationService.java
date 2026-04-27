package com.bna.smile.model.domainecontratcompte.procuration.service;


import com.bna.commun.model.MandatOperation;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecontratcompte.procuration.model.ListMandatOperationVo;
import com.bna.smile.model.domainecontratcompte.procuration.model.ParamMandatOperationVo;
import com.bna.smile.model.domainecontratcompte.procuration.model.PouvoirVo;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.AnnulMandatTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.ConsultEnveloppeRestanteTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.CreatMandatTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.CreationMandatTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.DebutDernierePeriodeTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.DetailMandatTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.GetContratByDosJurTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.GetListMandatOperationPersonneContratOperationTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.GetMandatAvaliderTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.GetMandatParDemandeTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.GetMandatReserveTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.GetMandatTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.GetPouvoirPersonneContratTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.GetTraceMandCptTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.GetTraceMandatTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.InsertDetailMandatPersonneTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.InsertMandatOperationTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.InsertMandatPersonneTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.InsertMandatTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.InsertTraceMandatTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.MiseAJourMandatTraceTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.MiseAJourMandatTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.UpdateDetailMandatPersonneTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.UpdateMandatOperationTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.UpdateMandatPersonneTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.UpdateMandatTraceTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.UpdateMandatTrt;
import com.bna.smile.model.domainecontratcompte.procuration.traitement.ValidModifMandTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;


public class ProcurationService extends BasicService{

    private GetMandatTrt getMandatTrt ;
    private InsertMandatTrt insertMandatTrt ;
    private InsertTraceMandatTrt insertTraceMandatTrt ;
    private CreationMandatTrt creationMandatTrt ;
    private CreatMandatTrt creatMandatTrt ;
    private MiseAJourMandatTrt miseAJourMandatTrt ;
    private UpdateMandatTrt updateMandatTrt ;
    private InsertMandatPersonneTrt insertMandatPersonneTrt ;
    private UpdateMandatPersonneTrt updateMandatPersonneTrt ;
    private InsertDetailMandatPersonneTrt insertDetailMandatPersonneTrt ;
    private UpdateDetailMandatPersonneTrt updateDetailMandatPersonneTrt ;
    private ValidModifMandTrt validModifMandTrt ;
    private InsertMandatOperationTrt insertMandatOperationTrt ;
    private UpdateMandatOperationTrt updateMandatOperationTrt ;
    private DetailMandatTrt detailMandatTrt;
    private AnnulMandatTrt annulMandatTrt ;
    private GetMandatAvaliderTrt getMandatAvaliderTrt ;
    private GetMandatParDemandeTrt getMandatParDemandeTrt;
    private DebutDernierePeriodeTrt debutDernierePeriodeTrt ;
    private ConsultEnveloppeRestanteTrt consultEnveloppeRestanteTrt ;
    private MiseAJourMandatTraceTrt miseAJourMandatTraceTrt ;
    private UpdateMandatTraceTrt updateMandatTraceTrt ;
    private GetTraceMandatTrt getTraceMandatTrt ;
    private GetTraceMandCptTrt getTraceMandCptTrt ;



    public ProcurationService() {
    }

    /**
     * Methode permettant d'avoir un Mandat dans la BD apartir de numMandMand
     * @param vo : Mandat
     * @return Mandat
     * @author BOUSSEN Youssef
     */
    public

    IValueObject getMandat(IValueObject vo) {
        
        return (getMandatTrt.exec(vo));
    }

    /**
     * Methode permettant d'inserer un Mandat dans la BD
     * @param vo : Mandat
     * @return Mandat
     * @author BOUSSEN Youssef
     */
    public IValueObject insertMandat(IValueObject vo) {

        return (insertMandatTrt.exec(vo));
    }

    /**
     * Methode permettant d'inserer une TraceMandat dans la BD
     * @param vo : TraceMandat
     * @return TraceMandat
     * @author BOUSSEN Youssef
     */
    public

    IValueObject insertTraceMandat(IValueObject vo) {

        return (insertTraceMandatTrt.exec(vo));
    }
    
    /**
     * Methode permettant la Création d'un mandat avec l'ensemble des détails
     * @param vo : Mandat
     * @return Mandat
     * @author BOUSSEN Youssef
     */
    public

    IValueObject CreationMandat(IValueObject vo) {

        return (creationMandatTrt.exec(vo));
    }

    /**
     * Methode permettant la Création d'un mandat avec l'ensemble des détails
     * lors de la creation
     * @param vo : Mandat
     * @return Mandat
     * @author BOUSSEN Youssef
     */
    public

    IValueObject CreatMandat(IValueObject vo) {
  
        return (creatMandatTrt.exec(vo));
    }

    /**
     * Methode permettant la MAJ d'un mandat avec l'ensemble des détails
     * @param vo : MandatTache
     * @return Mandat
     * @author BOUSSEN Youssef
     */
    public

    IValueObject MiseAJourMandat(IValueObject vo) {

        return (miseAJourMandatTrt.exec(vo));
    }

    /**
     * Methode permettant la MAJ un Mandat dans la BD
     * @param vo : Mandat
     * @return Mandat
     * @author BOUSSEN Youssef
     */
    public

    IValueObject updateMandat(IValueObject vo) {

        return (updateMandatTrt.exec(vo));
    }

    /**
     * Methode permettant l'insertion d'une MandatPersonne
     *  il y aura une creation d'une nouvelle DetailMandatPersonne
     * @param vo : MandatPersonne
     * @return MandatPersonne
     * @author BOUSSEN Youssef
     */
    public IValueObject InsertMandatPersonne(IValueObject vo) {

        return (insertMandatPersonneTrt.exec(vo));
    }

    /**
     * Methode permettant la MAJ d'une MandatPersonne
     * s'il ya changement d'etat (suppression du mandataire) il y aura
     * une creation d'une nouvelle DetailMandatPersonne et la MAJ de la
     * derniere DetailMandatPersonne (date fin = date systeme)
     * @param vo : MandatPersonne
     * @return MandatPersonne
     * @author BOUSSEN Youssef
     */
    public IValueObject updateMandatPersonne(IValueObject vo) {

        return (updateMandatPersonneTrt.exec(vo));
    }

    /**
     * methode permettant l'insertion d'un nouveau DetailMandatPersonne
     * et de fermer l'ancien (date fin = date systeme) s'il existe
     * @param vo : MandatPersonne
     * @return DetailMandatPersonne
     * @author BOUSSEN Youssef
     */
    public IValueObject insertDetailMandatPersonne(IValueObject vo) {

        return (insertDetailMandatPersonneTrt.exec(vo));
    }

    /**
     * methode permettant la MAJ de la derniere DetailMandatPersonne
     * d'une MandatPersonne donnée
     * @param vo : MandatPersonne
     * @return DetailMandatPersonne
     * @author BOUSSEN Youssef
     */
    public IValueObject updateDetailMandatPersonne(IValueObject vo) {

        return (updateDetailMandatPersonneTrt.exec(vo));
    }

    /**
     * methode permettant la validation de la modification d'un Mandat
     * @param vo : ParamModifMandVo
     * @return ParamModifMandVo
     * @author BOUSSEN Youssef
     */
    public IValueObject validModifMand(IValueObject vo) {

        return (validModifMandTrt.exec(vo));
    }

    public IValueObject insertMandatOperation(IValueObject vo) {

        return (insertMandatOperationTrt.exec(vo));
    }

    public IValueObject updateMandatOperation(IValueObject vo) {

        return (updateMandatOperationTrt.exec(vo));
    }

    public IValueObject annulMandat(IValueObject vo) {
       
        return (annulMandatTrt.exec(vo));
    }

    public IValueObject detailMandat(IValueObject vo) {

        return (detailMandatTrt.exec(vo));
    }

   

    public IValueObject getMandatAvalider(IValueObject vo) {
       
        return (getMandatAvaliderTrt.exec(vo));
    }

    public IValueObject getMandatParDemande(IValueObject vo) {
       
        return (getMandatParDemandeTrt.exec(vo));
    }

    /**
     * 
     * @param  Value Object : ParamMandatOperationVo
     * @return Value Object : ListMandatOperationVo
     */
    public ValueObject getListMandatOperationPersonneContratOperation(ValueObject vo) {
        ParamMandatOperationVo paramMandatOperationVo = (ParamMandatOperationVo)vo;
        GetListMandatOperationPersonneContratOperationTrt getListMandatOperationTrt = new GetListMandatOperationPersonneContratOperationTrt();
        ListMandatOperationVo listMandatOperationVo = (ListMandatOperationVo)getListMandatOperationTrt.execute(paramMandatOperationVo);
        return (listMandatOperationVo);
    }

    /**
     * Classe qui permet d'extraire tous les pouvoirs d'une personne sur 
     * un contrat donné (input ContratPersonne) et retourne le type du pouvoir (T,M,C) et la Liste des mandats ou  Cotitulaire
     *  selon le cas.
     * @param vo : ContratPersonne
     * @return vo : PouvoirVo
     * @author Ramzi
     * 
     */
    public ValueObject getPouvoirPersonneContrat(ValueObject vo) {
        ContratPersonne contratPersonne = (ContratPersonne)vo;
        GetPouvoirPersonneContratTrt getPouvoirPersonneContratTrt = new GetPouvoirPersonneContratTrt();
        PouvoirVo pouvoirVo = (PouvoirVo)getPouvoirPersonneContratTrt.execute(contratPersonne);
        return (pouvoirVo);
    }

    /**
     * Classe qui permet de determiner la date 
     * du debut de la derniere periode par rapport à la date du jour
     * @param  vo : MandatOperation
     * @return vo : PrimitiveVo
     * @author BOUSSEN Youssef & KRIAA Hatem
     * 
     */
    public IValueObject DebutDernierePeriode(IValueObject vo) {

        MandatOperation mandatOperation = (MandatOperation)vo;
        PrimitiveVO primitiveVo =  (PrimitiveVO)debutDernierePeriodeTrt.exec(mandatOperation);
        return (primitiveVo);
    }   
    
    /**
     * Classe qui permet de determiner l'enveloppe restante pour 
     * une operation d'un mandat
     * @param  vo : MandatOperation
     * @return vo : PrimitiveVo
     * @author BOUSSEN Youssef
     */
    public IValueObject ConsultEnveloppeRestante(IValueObject vo) {
       
        MandatOperation mandatOperation = (MandatOperation)vo;
        PrimitiveVO primitiveVo =  (PrimitiveVO)consultEnveloppeRestanteTrt.exec(mandatOperation);
        return (primitiveVo);
    }   
    /**
     * Methode permettant la MAJ d'un mandat avec l'ensemble des détails
     * @param vo : MandatTache
     * @return Mandat
     * @author BOUSSEN Youssef
     */
    public

    IValueObject MiseAJourMandatTrace(IValueObject vo) {

        return (miseAJourMandatTraceTrt.exec(vo));
    }

    
    /**
     * Methode permettant la MAJ un Mandat dans la BD et l'insertion d'une trace
     * @param vo : Mandat
     * @return Mandat
     * @author BOUSSEN Youssef
     */
    public

    IValueObject updateMandatTrace(IValueObject vo) {

        return (updateMandatTraceTrt.exec(vo));
    }
    public IValueObject getTraceMandat(IValueObject vo) {
       
        return (getTraceMandatTrt.exec(vo));
    }
    public IValueObject getTraceMandatCpt(IValueObject vo) {
       
        return (getTraceMandCptTrt.exec(vo));
    }
    
    public IValueObject getContratByDosJur(IValueObject vo) {

        GetContratByDosJurTrt getContratByDosJurTrt = new GetContratByDosJurTrt();
        return (getContratByDosJurTrt.exec(vo));

    }
    public IValueObject getMandatReserve(IValueObject vo) {
        GetMandatReserveTrt getMandatReserveTrt = new GetMandatReserveTrt();
        return (getMandatReserveTrt.exec(vo));
    }

    public void setGetMandatTrt(GetMandatTrt getMandatTrt) {
        this.getMandatTrt = getMandatTrt;
    }

    public GetMandatTrt getGetMandatTrt() {
        return getMandatTrt;
    }

    public void setInsertMandatTrt(InsertMandatTrt insertMandatTrt) {
        this.insertMandatTrt = insertMandatTrt;
    }

    public InsertMandatTrt getInsertMandatTrt() {
        return insertMandatTrt;
    }

    public void setInsertTraceMandatTrt(InsertTraceMandatTrt insertTraceMandatTrt) {
        this.insertTraceMandatTrt = insertTraceMandatTrt;
    }

    public InsertTraceMandatTrt getInsertTraceMandatTrt() {
        return insertTraceMandatTrt;
    }

    public void setCreationMandatTrt(CreationMandatTrt creationMandatTrt) {
        this.creationMandatTrt = creationMandatTrt;
    }

    public CreationMandatTrt getCreationMandatTrt() {
        return creationMandatTrt;
    }

    public void setCreatMandatTrt(CreatMandatTrt creatMandatTrt) {
        this.creatMandatTrt = creatMandatTrt;
    }

    public CreatMandatTrt getCreatMandatTrt() {
        return creatMandatTrt;
    }

    public void setMiseAJourMandatTrt(MiseAJourMandatTrt miseAJourMandatTrt) {
        this.miseAJourMandatTrt = miseAJourMandatTrt;
    }

    public MiseAJourMandatTrt getMiseAJourMandatTrt() {
        return miseAJourMandatTrt;
    }

    public void setUpdateMandatTrt(UpdateMandatTrt updateMandatTrt) {
        this.updateMandatTrt = updateMandatTrt;
    }

    public UpdateMandatTrt getUpdateMandatTrt() {
        return updateMandatTrt;
    }

    public void setInsertMandatPersonneTrt(InsertMandatPersonneTrt insertMandatPersonneTrt) {
        this.insertMandatPersonneTrt = insertMandatPersonneTrt;
    }

    public InsertMandatPersonneTrt getInsertMandatPersonneTrt() {
        return insertMandatPersonneTrt;
    }

    public void setUpdateMandatPersonneTrt(UpdateMandatPersonneTrt updateMandatPersonneTrt) {
        this.updateMandatPersonneTrt = updateMandatPersonneTrt;
    }

    public UpdateMandatPersonneTrt getUpdateMandatPersonneTrt() {
        return updateMandatPersonneTrt;
    }

    public void setUpdateDetailMandatPersonneTrt(UpdateDetailMandatPersonneTrt updateDetailMandatPersonneTrt) {
        this.updateDetailMandatPersonneTrt = updateDetailMandatPersonneTrt;
    }

    public UpdateDetailMandatPersonneTrt getUpdateDetailMandatPersonneTrt() {
        return updateDetailMandatPersonneTrt;
    }

    public void setValidModifMandTrt(ValidModifMandTrt validModifMandTrt) {
        this.validModifMandTrt = validModifMandTrt;
    }

    public ValidModifMandTrt getValidModifMandTrt() {
        return validModifMandTrt;
    }

    public void setInsertMandatOperationTrt(InsertMandatOperationTrt insertMandatOperationTrt) {
        this.insertMandatOperationTrt = insertMandatOperationTrt;
    }

    public InsertMandatOperationTrt getInsertMandatOperationTrt() {
        return insertMandatOperationTrt;
    }

    public void setUpdateMandatOperationTrt(UpdateMandatOperationTrt updateMandatOperationTrt) {
        this.updateMandatOperationTrt = updateMandatOperationTrt;
    }

    public UpdateMandatOperationTrt getUpdateMandatOperationTrt() {
        return updateMandatOperationTrt;
    }

    public void setDetailMandatTrt(DetailMandatTrt detailMandatTrt) {
        this.detailMandatTrt = detailMandatTrt;
    }

    public DetailMandatTrt getDetailMandatTrt() {
        return detailMandatTrt;
    }

    public void setAnnulMandatTrt(AnnulMandatTrt annulMandatTrt) {
        this.annulMandatTrt = annulMandatTrt;
    }

    public AnnulMandatTrt getAnnulMandatTrt() {
        return annulMandatTrt;
    }

    public void setGetMandatAvaliderTrt(GetMandatAvaliderTrt getMandatAvaliderTrt) {
        this.getMandatAvaliderTrt = getMandatAvaliderTrt;
    }

    public GetMandatAvaliderTrt getGetMandatAvaliderTrt() {
        return getMandatAvaliderTrt;
    }

    public void setGetMandatParDemandeTrt(GetMandatParDemandeTrt getMandatParDemandeTrt) {
        this.getMandatParDemandeTrt = getMandatParDemandeTrt;
    }

    public GetMandatParDemandeTrt getGetMandatParDemandeTrt() {
        return getMandatParDemandeTrt;
    }

    public void setDebutDernierePeriodeTrt(DebutDernierePeriodeTrt debutDernierePeriodeTrt) {
        this.debutDernierePeriodeTrt = debutDernierePeriodeTrt;
    }

    public DebutDernierePeriodeTrt getDebutDernierePeriodeTrt() {
        return debutDernierePeriodeTrt;
    }

    public void setConsultEnveloppeRestanteTrt(ConsultEnveloppeRestanteTrt consultEnveloppeRestanteTrt) {
        this.consultEnveloppeRestanteTrt = consultEnveloppeRestanteTrt;
    }

    public ConsultEnveloppeRestanteTrt getConsultEnveloppeRestanteTrt() {
        return consultEnveloppeRestanteTrt;
    }

    public void setMiseAJourMandatTraceTrt(MiseAJourMandatTraceTrt miseAJourMandatTraceTrt) {
        this.miseAJourMandatTraceTrt = miseAJourMandatTraceTrt;
    }

    public MiseAJourMandatTraceTrt getMiseAJourMandatTraceTrt() {
        return miseAJourMandatTraceTrt;
    }

    public void setUpdateMandatTraceTrt(UpdateMandatTraceTrt updateMandatTraceTrt) {
        this.updateMandatTraceTrt = updateMandatTraceTrt;
    }

    public UpdateMandatTraceTrt getUpdateMandatTraceTrt() {
        return updateMandatTraceTrt;
    }

    public void setGetTraceMandatTrt(GetTraceMandatTrt getTraceMandatTrt) {
        this.getTraceMandatTrt = getTraceMandatTrt;
    }

    public GetTraceMandatTrt getGetTraceMandatTrt() {
        return getTraceMandatTrt;
    }

    public void setGetTraceMandCptTrt(GetTraceMandCptTrt getTraceMandCptTrt) {
        this.getTraceMandCptTrt = getTraceMandCptTrt;
    }

    public GetTraceMandCptTrt getGetTraceMandCptTrt() {
        return getTraceMandCptTrt;
    }

    public void setInsertDetailMandatPersonneTrt(InsertDetailMandatPersonneTrt insertDetailMandatPersonneTrt) {
        this.insertDetailMandatPersonneTrt = insertDetailMandatPersonneTrt;
    }

    public InsertDetailMandatPersonneTrt getInsertDetailMandatPersonneTrt() {
        return insertDetailMandatPersonneTrt;
    }
}
