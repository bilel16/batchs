package com.bna.smile.model.telex.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.telex.model.CodPostal;
import com.bna.smile.model.telex.model.Devise;
import com.bna.smile.model.telex.model.Gouvernorat;
import com.bna.smile.model.telex.model.NotificationTelex;
import com.bna.smile.model.telex.model.Structure;

public class TelexDAO {

	protected String sqlQuery;
	protected JdbcTemplate jt;
	protected DataSource dataSource;
	
	
	public String getSqlQuery() {
		return sqlQuery;
	}


	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}


	public JdbcTemplate getJt() {
		return jt;
	}


	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}


	public DataSource getDataSource() {
		return dataSource;
	}


	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	public SimpleDateFormat getFormaterDate() {
		return formaterDate;
	}


	public void setFormaterDate(SimpleDateFormat formaterDate) {
		this.formaterDate = formaterDate;
	}


	public static Logger getLogger() {
		return logger;
	}


	private SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	private static final Logger logger = Logger.getLogger(TelexDAO.class);
	
	
	public List<NotificationTelex> getListeNotification(Integer etat) {

		jt = new JdbcTemplate(dataSource);

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		String requete = "select DATE_GENERATION,Relation,ADRESSE,CODE_POSTAL,"
				+ "TYPE_DOSSIER,NUM_DOSSIER,DATE_OPERATION,CODE_DEVISE, "
				+ "MONTANT, ORDONNATEUR, COMMENTAIRE, CODE_AGENCE,NUM_TEL,COMPTE_CLIENT    "
				+ "from TR_NOTIFICATION_AF where ETAT = " + etat;

		List<NotificationTelex> list = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				NotificationTelex notification_telex = new NotificationTelex();
				notification_telex.setDATE_GENERATION(rs.getDate("DATE_GENERATION"));
				notification_telex.setRELATION(rs.getString("Relation"));
				notification_telex.setADRESSE(rs.getString("ADRESSE"));
				notification_telex.setCODE_POSTAL(rs.getString("CODE_POSTAL"));

				

				notification_telex.setTYPE_DOSSIER(rs.getString("TYPE_DOSSIER"));
				notification_telex.setNUM_DOSSIER(rs.getLong("NUM_DOSSIER"));
				notification_telex.setDATE_OPERATION(rs.getDate("DATE_OPERATION"));
				notification_telex.setCODE_DEVISE(rs.getInt("CODE_DEVISE"));

	

				notification_telex.setMONTANT(rs.getLong("MONTANT"));
				notification_telex.setORDONNATEUR(rs.getString("ORDONNATEUR"));
				notification_telex.setCOMMENTAIRE(rs.getString("COMMENTAIRE"));
				
				
				notification_telex.setCODE_AGENCE(rs.getInt("CODE_AGENCE"));
				notification_telex.setNUM_TEL(rs.getLong("NUM_TEL"));
				notification_telex.setCOMPTE_CLIENT(rs.getString("COMPTE_CLIENT"));

				return notification_telex;
			}
		});
		return list;
	}
	
	
	
	public Devise getDevise(Integer codDevise) {

		jt = new JdbcTemplate(dataSource);
		
		String requete = "select COD_DEV_DEV, LIB_DEV_DEV, LIB_SIGL_DEV, NBR_DEC_DEV from Devise where COD_DEV_DEV = "+codDevise;

		List<Devise> list = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				Devise devise = new Devise();
				
				devise.setCOD_DEV_DEV(rs.getInt("COD_DEV_DEV"));
				devise.setLIB_DEV_DEV(rs.getString("LIB_DEV_DEV"));
				devise.setLIB_SIGL_DEV(rs.getString("LIB_SIGL_DEV"));
				devise.setNBR_DEC_DEV(rs.getInt("NBR_DEC_DEV"));
				
				return devise;
			}
		});
		
		//Structure structure = (Structure) jt.queryForObject(requete, Structure.class);
		if(list.isEmpty()) return null;
		
		return list.get(0);
	}
	
	public CodPostal getVille(Integer codPostal) {
		
		
		
		jt = new JdbcTemplate(dataSource);
		
		String requete = "select COD_GOUV_GOUV, COD_CP_CP, LIB_CP_CP from CODE_POSTAL where COD_CP_CP =  "+codPostal;

		List<CodPostal> list = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				CodPostal codPostal = new CodPostal();
				
				codPostal.setCOD_GOUV_GOUV(rs.getInt("COD_GOUV_GOUV"));
				codPostal.setLIB_CP_CP(rs.getString("LIB_CP_CP"));
				codPostal.setCOD_CP_CP(rs.getInt("COD_CP_CP"));
				
				return codPostal;
			}
		});
		
		//Structure structure = (Structure) jt.queryForObject(requete, Structure.class);
		if(list.isEmpty()) return null;
		
		return list.get(0);
	}
	
	public Gouvernorat getGouvernorat(Integer codGouvernorat) {
		
		
		
		jt = new JdbcTemplate(dataSource);
		
		
		String requete = "select COD_GOUV_GOUV,LIB_GOUV_GOUV  from GOUVERNORAT where COD_GOUV_GOUV = "+codGouvernorat ;

		List<Gouvernorat> list = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				Gouvernorat gouvernorat = new Gouvernorat();
				
				gouvernorat.setCOD_GOUV_GOUV(rs.getInt("COD_GOUV_GOUV"));
				gouvernorat.setLIB_GOUV_GOUV(rs.getString("LIB_GOUV_GOUV"));
				
				return gouvernorat;
			}
		});
		
		//Structure structure = (Structure) jt.queryForObject(requete, Structure.class);
		if(list.isEmpty()) return null;
		
		return list.get(0);
	}
	
	public Structure getStructure(Integer cod_strc_strc) {
		
		jt = new JdbcTemplate(dataSource);
	
		String requete = "select COD_STRC_STRC, LIB_STRC_STRC, LIB_MAIL_STRC from STRUCTURE where  COD_STRC_STRC = "+cod_strc_strc ;

		List<Structure> list = jt.query(requete, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				Structure structure = new Structure();
				
				structure.setCOD_STRC_STRC(rs.getInt("COD_STRC_STRC"));
				structure.setLIB_STRC_STRC(rs.getString("LIB_STRC_STRC"));
				structure.setLIB_MAIL_STRC(rs.getString("LIB_MAIL_STRC"));
				
				return structure;
			}
		});
		
		//Structure structure = (Structure) jt.queryForObject(requete, Structure.class);
		if(list.isEmpty()) return null;
		
		return list.get(0);
	}
	
	public void updateEtatNotification(Long num_dossier, Long num_telex, Integer etat) {

		jt = new JdbcTemplate(dataSource);
		jt.execute("update TR_NOTIFICATION_AF set ETAT = "+etat+" where NUM_DOSSIER = "+num_dossier+" and NUM_TEL ="+num_telex);
		
	}
	
}
