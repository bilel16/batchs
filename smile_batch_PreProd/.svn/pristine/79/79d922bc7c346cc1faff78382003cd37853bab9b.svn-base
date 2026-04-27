package com.bna.smile.model.clotureDomaine.service;

import com.bna.smile.model.clotureDomaine.traitement.ClotureDomPlacementTrt;
import com.bna.smile.model.clotureDomaine.traitement.ClotureDomaineTrt;
import com.bna.smile.model.clotureDomaine.traitement.ClotureJourneeSmileTrt;
import com.bna.smile.model.clotureDomaine.traitement.CloturerDomMoyPaiTrt;
import com.bna.smile.model.clotureDomaine.traitement.DetailAvancRembLiqTrt;
import com.bna.smile.model.clotureDomaine.traitement.DetailContratPlacementTrt;
import com.bna.smile.model.clotureDomaine.traitement.DetailDonneeClotureTrt;
import com.bna.smile.model.clotureDomaine.traitement.DetailOperMoyPayTrt;
import com.bna.smile.model.clotureDomaine.traitement.DetailRelationClientTrt;
import com.bna.smile.model.clotureDomaine.traitement.DetailSouscPlacementTrt;
import com.bna.smile.model.clotureDomaine.traitement.GetDonnePlacementTrt;
import com.bna.smile.model.clotureDomaine.traitement.GetDonneeMoyPaiTrt;
import com.bna.smile.model.clotureDomaine.traitement.GetDonneeSouscriptionTrt;
import com.bna.smile.model.clotureDomaine.traitement.GetListJournStructDomTrt;
import com.bna.smile.model.clotureDomaine.traitement.GetStatAssVieTrt;
import com.bna.smile.model.clotureDomaine.traitement.GetStatAvancPlacTrt;
import com.bna.smile.model.clotureDomaine.traitement.GetStatInteretServiTrt;
import com.bna.smile.model.clotureDomaine.traitement.GetStatLiquidTrt;
import com.bna.smile.model.clotureDomaine.traitement.GetStatOpposPlacTrt;
import com.bna.smile.model.clotureDomaine.traitement.GetStatSouscPlacTrt;
import com.bna.smile.model.clotureDomaine.traitement.GetStatrenouvPlacTrt;
import com.bna.smile.model.clotureDomaine.traitement.UpdateDomaineTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;

public class ClotureDomaineService extends BasicService {
    public ClotureDomaineService() {
    }
    private GetDonneeSouscriptionTrt getDonneeSouscriptionTrt;
    private ClotureDomaineTrt clotureDomaineTrt;
    private UpdateDomaineTrt updateDomaineTrt;
    private DetailDonneeClotureTrt detailDonneeClotureTrt;
    private DetailRelationClientTrt detailRelationClientTrt;


    public IValueObject getDonneeSouscript(IValueObject vo) {

        return (getDonneeSouscriptionTrt.exec(vo));
    }

    public IValueObject cloturerDomaine(IValueObject vo) {
        ClotureDomaineTrt clotureDomaineTrt = new ClotureDomaineTrt();
        return (clotureDomaineTrt.exec(vo));
    }

    public IValueObject updateDomaine(IValueObject vo) {

        return (updateDomaineTrt.exec(vo));
    }

    public void setGetDonneeSouscriptionTrt(GetDonneeSouscriptionTrt getDonneeSouscriptionTrt) {
        this.getDonneeSouscriptionTrt = getDonneeSouscriptionTrt;
    }

    public GetDonneeSouscriptionTrt getGetDonneeSouscriptionTrt() {
        return getDonneeSouscriptionTrt;
    }

    public void setClotureDomaineTrt(ClotureDomaineTrt clotureDomaineTrt) {
        this.clotureDomaineTrt = clotureDomaineTrt;
    }

    public ClotureDomaineTrt getClotureDomaineTrt() {
        return clotureDomaineTrt;
    }

    public void setUpdateDomaineTrt(UpdateDomaineTrt updateDomaineTrt) {
        this.updateDomaineTrt = updateDomaineTrt;
    }

    public UpdateDomaineTrt getUpdateDomaineTrt() {
        return updateDomaineTrt;
    }

    public IValueObject getDetailDonneeCloture(IValueObject vo) {

        return (detailDonneeClotureTrt.exec(vo));
    }

    public IValueObject getDetailRelationClient(IValueObject vo) {

        return (detailRelationClientTrt.exec(vo));
    }


    public void setDetailDonneeClotureTrt(DetailDonneeClotureTrt detailDonneeClotureTrt) {
        this.detailDonneeClotureTrt = detailDonneeClotureTrt;
    }

    public DetailDonneeClotureTrt getDetailDonneeClotureTrt() {
        return detailDonneeClotureTrt;
    }

    public void setDetailRelationClientTrt(DetailRelationClientTrt detailRelationClientTrt) {
        this.detailRelationClientTrt = detailRelationClientTrt;
    }

    public DetailRelationClientTrt getDetailRelationClientTrt() {
        return detailRelationClientTrt;
    }

    public IValueObject cloturerJournee(IValueObject vo) {
        ClotureJourneeSmileTrt clotureJourneeSmileTrt = 
            new ClotureJourneeSmileTrt();
        return (clotureJourneeSmileTrt.exec(vo));
    }

    public IValueObject getListJournStructDom(IValueObject vo) {
        GetListJournStructDomTrt getListJournStructDomTrt = 
            new GetListJournStructDomTrt();
        return (getListJournStructDomTrt.exec(vo));
    }

    public IValueObject getDonneePlacement(IValueObject vo) {

        GetDonnePlacementTrt getDonnePlacementTrt = new GetDonnePlacementTrt();
        return (getDonnePlacementTrt.exec(vo));

    }

    public IValueObject GetStatSouscPlac(IValueObject vo) {

        GetStatSouscPlacTrt getStatSouscPlacTrt = new GetStatSouscPlacTrt();
        return (getStatSouscPlacTrt.exec(vo));

    }

    public IValueObject GetStatAvancPlac(IValueObject vo) {

        GetStatAvancPlacTrt getStatAvancPlacTrt = new GetStatAvancPlacTrt();
        return (getStatAvancPlacTrt.exec(vo));

    }

    public IValueObject GetStatLiquid(IValueObject vo) {

        GetStatLiquidTrt getStatLiquidTrt = new GetStatLiquidTrt();
        return (getStatLiquidTrt.exec(vo));

    }

    public IValueObject cloturerDomPlacement(IValueObject vo) {

        ClotureDomPlacementTrt clotureDomPlacementTrt = 
            new ClotureDomPlacementTrt();
        return (clotureDomPlacementTrt.exec(vo));

    }

    public IValueObject getInteretServi(IValueObject vo) {

        GetStatInteretServiTrt getStatInteretServiTrt = 
            new GetStatInteretServiTrt();
        return (getStatInteretServiTrt.exec(vo));

    }

    public IValueObject getStatrenouvPlac(IValueObject vo) {

        GetStatrenouvPlacTrt getStatrenouvPlacTrt = new GetStatrenouvPlacTrt();
        return (getStatrenouvPlacTrt.exec(vo));

    }

    public IValueObject getStatOpposPlac(IValueObject vo) {

        GetStatOpposPlacTrt getStatOpposPlacTrt = new GetStatOpposPlacTrt();
        return (getStatOpposPlacTrt.exec(vo));

    }

    public IValueObject detailSouscPlacement(IValueObject vo) {

        DetailSouscPlacementTrt detailSouscPlacementTrt = 
            new DetailSouscPlacementTrt();
        return (detailSouscPlacementTrt.exec(vo));

    }

    public IValueObject detailContratPlacement(IValueObject vo) {

        DetailContratPlacementTrt detailContratPlacementTrt = 
            new DetailContratPlacementTrt();
        return (detailContratPlacementTrt.exec(vo));

    }

    public IValueObject detailAvancRembLiq(IValueObject vo) {

        DetailAvancRembLiqTrt detailAvancRembLiqTrt = 
            new DetailAvancRembLiqTrt();
        return (detailAvancRembLiqTrt.exec(vo));

    }

    public IValueObject detailOperMoyPay(IValueObject vo) {

        DetailOperMoyPayTrt detailOperMoyPayTrt = new DetailOperMoyPayTrt();
        return (detailOperMoyPayTrt.exec(vo));

    }
    public IValueObject getStatAssVie(IValueObject vo) {

        GetStatAssVieTrt getStatAssVieTrt = new GetStatAssVieTrt();
        return (getStatAssVieTrt.exec(vo));

    }
    public IValueObject getDonneeMoyPai(IValueObject vo) {

        GetDonneeMoyPaiTrt getDonneeMoyPaiTrt = new GetDonneeMoyPaiTrt();
        return (getDonneeMoyPaiTrt.exec(vo));

    }
    public IValueObject cloturerDomMoyPai(IValueObject vo) {

        CloturerDomMoyPaiTrt cloturerDomMoyPaiTrt = new CloturerDomMoyPaiTrt();
        return (cloturerDomMoyPaiTrt.exec(vo));

    }
    
}
