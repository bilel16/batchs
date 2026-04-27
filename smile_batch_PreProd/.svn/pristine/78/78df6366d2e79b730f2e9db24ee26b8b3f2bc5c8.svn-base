package com.bna.smile.model.banqueAssurance.service;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.smile.model.banqueAssurance.traitement.GetListAdhesionAssVieTrt;
import com.bna.smile.model.banqueAssurance.traitement.GetListAssureurTrt;
import com.bna.smile.model.banqueAssurance.traitement.InsertAssureurTrt;
import com.bna.smile.model.banqueAssurance.traitement.PecChangCptFactTrt;
import com.bna.smile.model.banqueAssurance.traitement.PecResiliationAssVieTrt;
import com.bna.smile.model.banqueAssurance.traitement.PrelevementAdhesionAssuranceVieDecouvertTrt;
import com.bna.smile.model.banqueAssurance.traitement.PrelevementAdhesionAssuranceVieTrt;
import com.bna.smile.model.banqueAssurance.traitement.RejeterChangCptFactTrt;
import com.bna.smile.model.banqueAssurance.traitement.UpdateAssureurTrt;
import com.bna.smile.model.banqueAssurance.traitement.ValidChangCptFactTrt;
import com.bna.smile.model.banqueAssurance.traitement.ValidResiliationAssVieTrt;
import com.bna.smile.model.banqueAssurance.traitement.ValiderAdhesionAssVieTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;

public class AssuranceVieService extends BasicService {

	public AssuranceVieService() {
	}

	protected String sqlQuery;
	protected JdbcTemplate jt;
	protected DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public IValueObject getListAdhesionAssVie(IValueObject vo) {
		GetListAdhesionAssVieTrt getListAdhesionAssVieTrt = new GetListAdhesionAssVieTrt();
		return (getListAdhesionAssVieTrt.exec(vo));
	}

	public IValueObject pecChangCptFact(IValueObject vo) {
		PecChangCptFactTrt pecChangCptFactTrt = new PecChangCptFactTrt();
		return (pecChangCptFactTrt.exec(vo));
	}

	public IValueObject validChangCptFact(IValueObject vo) {
		ValidChangCptFactTrt validChangCptFactTrt = new ValidChangCptFactTrt();
		return (validChangCptFactTrt.exec(vo));
	}

	public IValueObject validAdhesionAssVie(IValueObject vo) {
		ValiderAdhesionAssVieTrt validerAdhesionAssVieTrt = new ValiderAdhesionAssVieTrt();
		return (validerAdhesionAssVieTrt.exec(vo));
	}

	public IValueObject getListAssureur(IValueObject vo) {
		GetListAssureurTrt getListAssureurTrt = new GetListAssureurTrt();
		return (getListAssureurTrt.exec(vo));
	}

	public IValueObject rejeterChangCptFact(IValueObject vo) {
		RejeterChangCptFactTrt rejeterChangCptFactTrt = new RejeterChangCptFactTrt();
		return (rejeterChangCptFactTrt.exec(vo));
	}

	public IValueObject pecResiliationAssVie(IValueObject vo) {
		PecResiliationAssVieTrt pecResiliationAssVieTrt = new PecResiliationAssVieTrt();
		return (pecResiliationAssVieTrt.exec(vo));
	}

	public IValueObject PrelevementAdhesionAssuranceVie(IValueObject vo) {
		PrelevementAdhesionAssuranceVieTrt prelevementAdhesionAssuranceVieTrt =
				new PrelevementAdhesionAssuranceVieTrt();
		return (prelevementAdhesionAssuranceVieTrt.exec(vo));
	}

	public IValueObject preleverAdhesionAssuranceVieDecouvert(IValueObject vo) {
		PrelevementAdhesionAssuranceVieDecouvertTrt prelevementAdhesionAssuranceVieDecouvertTrt =
				new PrelevementAdhesionAssuranceVieDecouvertTrt();
		return (prelevementAdhesionAssuranceVieDecouvertTrt.exec(vo));
	}

	public IValueObject validResiliation(IValueObject vo) {
		ValidResiliationAssVieTrt validResiliationAssVieTrt = new ValidResiliationAssVieTrt();
		return (validResiliationAssVieTrt.exec(vo));
	}

	public IValueObject insertAssureur(IValueObject vo) {
		InsertAssureurTrt insertAssureurTrt = new InsertAssureurTrt();
		return (insertAssureurTrt.exec(vo));
	}

	public IValueObject updateAssureur(IValueObject vo) {
		UpdateAssureurTrt updateAssureurTrt = new UpdateAssureurTrt();
		return (updateAssureurTrt.exec(vo));
	}

	/**
	 * Methode qui permet de verouiller le batch lors de son execution. * @return Long
	 */
	public Long isBatchExec() {
		jt = new JdbcTemplate(dataSource);
		String requete =
				"select count(*) from JOURNEE_STRUCTURE j where j.COD_STRC_STRC = 900 and j.COD_SOLD_JRN != 0 ";

		Long nbre = (Long) jt.queryForObject(requete, Long.class);
		return nbre;
	}

}
