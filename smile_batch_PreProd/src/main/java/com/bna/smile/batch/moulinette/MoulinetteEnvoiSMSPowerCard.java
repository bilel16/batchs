package com.bna.smile.batch.moulinette;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.batch.test.BatchEnvoiSmsPowerCardFrame;
import com.bna.smile.model.SMS.traitement.GestionEnvoiSMSPowerCardTrt;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.EnvoiSMSVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

public class MoulinetteEnvoiSMSPowerCard extends AbstractJob {

	private static final Logger logger = Logger.getLogger(MoulinetteEnvoiSMSPowerCard.class);
	private BatchEnvoiSmsPowerCardFrame mainFrame;
	private EnvoiSMSVo envoiSMSVo;

	// ************************//

	/**
	 * Consutructor
	 */
	public MoulinetteEnvoiSMSPowerCard() {
		super();
	}

	public MoulinetteEnvoiSMSPowerCard(EnvoiSMSVo envoiSMSVo) {
		super();
		this.envoiSMSVo = envoiSMSVo;
	}

	public MoulinetteEnvoiSMSPowerCard(EnvoiSMSVo envoiSMSVo, BatchEnvoiSmsPowerCardFrame mainFrame) {
		super();
		this.envoiSMSVo = envoiSMSVo;
		this.mainFrame = mainFrame;
	}

	/**
	 * point d'entrée de la moulinette
	 */
	public void perform() {

		try {
			fixerUser();
			IValueObject vo = new ValueObject();

			GestionEnvoiSMSPowerCardTrt gestionEnvoiSMSPowerCardTrt = new GestionEnvoiSMSPowerCardTrt();
			envoiSMSVo = (EnvoiSMSVo) gestionEnvoiSMSPowerCardTrt.exec(envoiSMSVo);
			if (envoiSMSVo.isEtatEnregistrement() == true) {
				SwingInfoVo infoVo = new SwingInfoVo();
				infoVo.setNumeroTelephone(envoiSMSVo.getNumTelephone());
				infoVo.setEtat(Constants.STATUT_EN_TERMINE);
				infoVo.setInfo(envoiSMSVo.getMessageValidation());
				mainFrame.getBtnExcuter().setEnabled(false);
				mainFrame.addOrUpdateEtat(infoVo);
			} else {
				SwingInfoVo infoVo = new SwingInfoVo();
				infoVo.setNumeroTelephone(envoiSMSVo.getNumTelephone());
				infoVo.setEtat(Constants.STATUT_EN_ERRUR);
				infoVo.setInfo(envoiSMSVo.getMessageValidation());
				mainFrame.getBtnExcuter().setEnabled(true);
				mainFrame.addOrUpdateEtat(infoVo);
			}
			// //////////////
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("**** exception *** : " + this.getClass());
			SwingInfoVo infoVo = new SwingInfoVo();
			infoVo.setNumeroTelephone(envoiSMSVo.getNumTelephone());
			infoVo.setEtat(Constants.STATUT_EN_ERRUR);
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

	public void setMainFrame(BatchEnvoiSmsPowerCardFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public BatchEnvoiSmsPowerCardFrame getMainFrame() {
		return mainFrame;
	}

	public EnvoiSMSVo getEnvoiSMSVo() {
		return envoiSMSVo;
	}

	public void setEnvoiSMSVo(EnvoiSMSVo envoiSMSVo) {
		this.envoiSMSVo = envoiSMSVo;
	}

}
