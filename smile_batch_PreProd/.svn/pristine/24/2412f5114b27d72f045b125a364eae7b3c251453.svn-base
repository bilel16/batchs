package com.bna.smile.batch.moulinette;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.ParamSms;
import com.bna.smile.model.domainecommun.service.AbonnementService;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

public class MoulinetteBNASms extends AbstractJob {

	private static final Logger logger = Logger.getLogger(MoulinetteBNASms.class);
	private VirementVo virementVo;

	public MoulinetteBNASms(VirementVo virementVo) {
		super();
		this.virementVo = virementVo;
	}

	/**
	 * ^point d'entrée de la moulinette
	 */
	public void perform() {

		try {

			if (virementVo != null && virementVo.getStructure() != null
					&& virementVo.getStructure().getCodStrcStrc() != null) {
				Connection con = null;

				ResultSet rs = null;
				Statement stmt = null;

				String requeteSms =
						"select ID,CODE_PRODUIT,CODE_OPERATION,DATE_OPERATION,DATE_VALEUR,CIN,NUM_COMPTE,NUM_ABONNEMENT,"
								+ "MONTANT,TAX,CYCLE , to_number(Substr(NUM_COMPTE,0,3)) as CODE_AGENCE FROM  BNA_BILLING@NEWSMSDB.BNA.TN "
								+ " where ETAT = 'A' " + " and to_number(Substr(NUM_COMPTE,0,3))="
								+ virementVo.getStructure().getCodStrcStrc() + " order by NUM_COMPTE ";

				// String requeteSms = "select num_seq_cpla FROM contrat_placement";

				fixerUser();
				Context context = ContextCROHandler.getContext();
				DataSource ds = (DataSource) context.getBean("dataSource");
				con = ds.getConnection();

				stmt = con.createStatement();
				rs = this.execute(requeteSms, stmt);

				System.out.println(requeteSms);
				AbonnementService abonnementService = (AbonnementService) context.getBean("abonnementService");

				// InsertCroBNASmsTrt insertCroBNASmsTrt = new InsertCroBNASmsTrt();
				ParamSms paramSms = new ParamSms();
				paramSms.setCon(con);

				while (rs.next()) {
					paramSms.setID(Long.valueOf(rs.getInt(1)));
					paramSms.setCodProduit(rs.getString(2));
					paramSms.setCodeOperation(rs.getString(3));
					paramSms.setDateOperation(rs.getString(4));
					paramSms.setDateValeur(rs.getString(5));
					paramSms.setCin(rs.getString(6));
					paramSms.setNumCompte(rs.getString(7));
					paramSms.setNumAbonnement(Long.valueOf(rs.getInt(8)));
					paramSms.setMontant(Long.valueOf(rs.getInt(9)));
					paramSms.setTax(Long.valueOf(rs.getInt(10)));
					paramSms.setCodeAgence(Long.valueOf(rs.getInt(11)));
					paramSms = (ParamSms) abonnementService.insertCroBNASmst(paramSms);

				}
			}

		} catch (Exception e) {
			logger.fatal("**** exception *** : " + this.getClass() + " ----- " + e.getMessage());

		}
	}

	public void fixerUser() {
		ContextCROHandler.setContext(ContextHandler.getContext());

		Personnel user = new Personnel();
		UserManager usermanager = (UserManager) ContextHandler.getContext().getBean("userManager");
		user = usermanager.getUser("9999");

		UsernamePasswordAuthenticationToken auth =
				new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
		auth.setDetails(user);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	public ResultSet execute(String query, Statement stmt) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public VirementVo getVirementVo() {
		return virementVo;
	}

	public void setVirementVo(VirementVo virementVo) {
		this.virementVo = virementVo;
	}

}
