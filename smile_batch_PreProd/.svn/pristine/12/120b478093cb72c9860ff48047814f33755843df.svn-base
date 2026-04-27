package com.bna.smile.batch.moulinette;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.test.BatchRenouvellementAssuranceVieDecouvertFrame;
import com.bna.smile.model.banqueAssurance.model.ParamAdhesion;
import com.bna.smile.model.banqueAssurance.traitement.ResiliationAutoRenouvellementAutoAssuranceVieDecouvertTrt;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

public class MoulinetteRenouvellementAssVieDecouvert extends AbstractJob {

	private static final Logger logger = Logger.getLogger(MoulinetteRenouvellementAssVieDecouvert.class);
	private BatchRenouvellementAssuranceVieDecouvertFrame mainFrame;
	private ParamAdhesion paramAdhesion;

	// ************************//

	/**
	 * Consutructor
	 */
	public MoulinetteRenouvellementAssVieDecouvert() {
		super();
	}

	public MoulinetteRenouvellementAssVieDecouvert(ParamAdhesion paramAdhesion) {
		super();
		this.paramAdhesion = paramAdhesion;
	}

	public MoulinetteRenouvellementAssVieDecouvert(ParamAdhesion paramAdhesion,
			BatchRenouvellementAssuranceVieDecouvertFrame mainFrame) {
		super();
		this.paramAdhesion = paramAdhesion;
		this.mainFrame = mainFrame;
	}

	/**
	 * point d'entrée de la moulinette
	 */
	public void perform() {

		try {
			fixerUser();

			ResiliationAutoRenouvellementAutoAssuranceVieDecouvertTrt resiliationAutoRenouvellementAutoAssuranceVieDecouvertTrt =
					new ResiliationAutoRenouvellementAutoAssuranceVieDecouvertTrt();
			paramAdhesion = (ParamAdhesion) resiliationAutoRenouvellementAutoAssuranceVieDecouvertTrt.exec(paramAdhesion);
			if (paramAdhesion.isEtatValidation() == true) {
				SwingInfoVo infoVo = new SwingInfoVo();
				infoVo.setStructure("" + paramAdhesion.getStructure().getCodStrcStrc());
				infoVo.setEtat(Constants.STATUT_EN_TERMINE);
				infoVo.setDateComptable(DateHandler.dateToStr(paramAdhesion.getDateComptable()));
				infoVo.setInfo(paramAdhesion.getMessageValidation());
				mainFrame.getBtnExcuter().setEnabled(false);
				mainFrame.addOrUpdateEtat(infoVo);
			} else {
				SwingInfoVo infoVo = new SwingInfoVo();
				infoVo.setStructure("" + paramAdhesion.getStructure().getCodStrcStrc());
				infoVo.setEtat(Constants.STATUT_EN_ERRUR);
				infoVo.setDateComptable(DateHandler.dateToStr(paramAdhesion.getDateComptable()));
				infoVo.setInfo(paramAdhesion.getMessageValidation());
				mainFrame.getBtnExcuter().setEnabled(true);
				mainFrame.addOrUpdateEtat(infoVo);
			}
			// //////////////
		} catch (Exception e) {
			logger.fatal("**** exception *** : " + this.getClass());
			SwingInfoVo infoVo = new SwingInfoVo();
			infoVo.setStructure("" + paramAdhesion.getStructure().getCodStrcStrc());
			infoVo.setEtat(Constants.STATUT_EN_ERRUR);
			infoVo.setDateComptable(DateHandler.dateToStr(paramAdhesion.getDateComptable()));
			infoVo.setInfo(e.getMessage());
			mainFrame.getBtnExcuter().setEnabled(true);
			mainFrame.addOrUpdateEtat(infoVo);
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

	public void setMainFrame(BatchRenouvellementAssuranceVieDecouvertFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public BatchRenouvellementAssuranceVieDecouvertFrame getMainFrame() {
		return mainFrame;
	}

	public ParamAdhesion getParamAdhesion() {
		return paramAdhesion;
	}

	public void setParamAdhesion(ParamAdhesion paramAdhesion) {
		this.paramAdhesion = paramAdhesion;
	}

}
