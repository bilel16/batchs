package com.bna.smile.model.banqueAssurance.service;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.smile.model.banqueAssurance.traitement.InsertDetailContratAssuranceVoyageTrt;
import com.bna.smile.model.banqueAssurance.traitement.RenouvellementAssuranceVoyageTrt;
import com.oxia.fwk.beans.service.BasicService;
import com.oxia.fwk.core.IValueObject;

public class AssuranceVoyageService extends BasicService{
    public AssuranceVoyageService() {
    }
    protected String sqlQuery;
    protected JdbcTemplate jt;
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

	public IValueObject renouvelerAssuranceVoyage(IValueObject vo) {
		RenouvellementAssuranceVoyageTrt renouvellementAssuranceVoyageTrt = new RenouvellementAssuranceVoyageTrt();
		return (renouvellementAssuranceVoyageTrt.exec(vo));
	}
	
	 public IValueObject insertDetailAssranceVoyage(IValueObject vo) {
	    	InsertDetailContratAssuranceVoyageTrt insertDetailContratAssuranceVoyageTrt = new InsertDetailContratAssuranceVoyageTrt();
	        return (insertDetailContratAssuranceVoyageTrt.exec(vo));
	    }

}
