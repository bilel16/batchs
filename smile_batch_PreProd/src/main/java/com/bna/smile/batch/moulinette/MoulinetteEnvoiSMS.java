package com.bna.smile.batch.moulinette;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.test.BatchEnvoiSMSFrame;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.EnvoiSMSVo;
import com.bna.smile.model.SMS.traitement.GestionEnvoiSMSTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

public class MoulinetteEnvoiSMS extends AbstractJob {

	private static final Logger logger = Logger.getLogger(MoulinetteEnvoiSMS.class);
	private BatchEnvoiSMSFrame mainFrame;
	private EnvoiSMSVo envoiSMSVo;

	// ************************//

	/**
	 * Consutructor
	 */
	public MoulinetteEnvoiSMS() {
		super();
	}

	public MoulinetteEnvoiSMS(EnvoiSMSVo envoiSMSVo) {
		super();
		this.envoiSMSVo = envoiSMSVo;
	}

	public MoulinetteEnvoiSMS(EnvoiSMSVo envoiSMSVo, BatchEnvoiSMSFrame mainFrame) {
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

			GestionEnvoiSMSTrt gestionEnvoiSMSTrt = new GestionEnvoiSMSTrt();
			envoiSMSVo = (EnvoiSMSVo) gestionEnvoiSMSTrt.exec(envoiSMSVo);
			if (envoiSMSVo.isEtatEnregistrement() == true) {
				SwingInfoVo infoVo = new SwingInfoVo();
				infoVo.setCompteClient(envoiSMSVo.getContratCpt().getContratCptId().getCompteClient());
				infoVo.setNumeroTelephone(envoiSMSVo.getNumTelephone());
				infoVo.setEtat(Constants.STATUT_EN_TERMINE);
				infoVo.setDateComptable(DateHandler.dateToStr(envoiSMSVo.getDateComptable()));
				infoVo.setInfo(envoiSMSVo.getMessageValidation());
				mainFrame.getBtnExcuter().setEnabled(false);
				mainFrame.addOrUpdateEtat(infoVo);
			} else {
				SwingInfoVo infoVo = new SwingInfoVo();
				infoVo.setCompteClient(envoiSMSVo.getContratCpt().getContratCptId().getCompteClient());
				infoVo.setNumeroTelephone(envoiSMSVo.getNumTelephone());
				infoVo.setEtat(Constants.STATUT_EN_ERRUR);
				infoVo.setDateComptable(DateHandler.dateToStr(envoiSMSVo.getDateComptable()));
				infoVo.setInfo(envoiSMSVo.getMessageValidation());
				mainFrame.getBtnExcuter().setEnabled(true);
				mainFrame.addOrUpdateEtat(infoVo);
			}
			// //////////////
		} catch (Exception e) {
			logger.fatal("**** exception *** : " + this.getClass());
			SwingInfoVo infoVo = new SwingInfoVo();
			infoVo.setCompteClient(envoiSMSVo.getContratCpt().getContratCptId().getCompteClient());
			infoVo.setEtat(Constants.STATUT_EN_ERRUR);
			infoVo.setDateComptable(DateHandler.dateToStr(envoiSMSVo.getDateComptable()));
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

	public void setMainFrame(BatchEnvoiSMSFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public BatchEnvoiSMSFrame getMainFrame() {
		return mainFrame;
	}

	
	public EnvoiSMSVo getEnvoiSMSVo() {
		return envoiSMSVo;
	}

	
	public void setEnvoiSMSVo(EnvoiSMSVo envoiSMSVo) {
		this.envoiSMSVo = envoiSMSVo;
	}

}
