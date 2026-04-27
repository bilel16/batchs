package com.bna.smile.batch.moulinette;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.test.BatchAFBFrame;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.AFBVo;
import com.bna.smile.model.domainecommun.traitement.GestionAFBTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

public class MoulinetteAFB extends AbstractJob {

	private static final Logger logger = Logger.getLogger(MoulinetteAFB.class);
	private BatchAFBFrame mainFrame;
	private AFBVo AFBVo;

	// ************************//

	/**
	 * Consutructor
	 */
	public MoulinetteAFB() {
		super();
	}

	public MoulinetteAFB(AFBVo AFBVo) {
		super();
		this.AFBVo = AFBVo;
	}

	public MoulinetteAFB(AFBVo AFBVo, BatchAFBFrame mainFrame) {
		super();
		this.AFBVo = AFBVo;
		this.mainFrame = mainFrame;
	}

	/**
	 * point d'entrée de la moulinette
	 */
	public void perform() {

		try {
			fixerUser();
			IValueObject vo = new ValueObject();

			GestionAFBTrt gestionAFBTrt = new GestionAFBTrt();
			AFBVo = (AFBVo) gestionAFBTrt.exec(AFBVo);
			if (AFBVo.isEtatEnregistrement() == true) {
				SwingInfoVo infoVo = new SwingInfoVo();
				infoVo.setNumSocieteAFB(AFBVo.getSocietesAFBView().getNumSoctAFB());
				infoVo.setNomSocieteAFB(AFBVo.getSocietesAFBView().getNomSoctAFB());
				infoVo.setEtat(Constants.STATUT_EN_TERMINE);
				infoVo.setDateComptable(DateHandler.dateToStr(AFBVo.getDateComptable()));
				infoVo.setInfo(AFBVo.getMessageValidation());
				mainFrame.getBtnExcuter().setEnabled(false);
				mainFrame.addOrUpdateEtat(infoVo);
			} else {
				SwingInfoVo infoVo = new SwingInfoVo();
				infoVo.setNumSocieteAFB(AFBVo.getSocietesAFBView().getNumSoctAFB());
				infoVo.setNomSocieteAFB(AFBVo.getSocietesAFBView().getNomSoctAFB());
				infoVo.setEtat(Constants.STATUT_EN_ERRUR);
				infoVo.setDateComptable(DateHandler.dateToStr(AFBVo.getDateComptable()));
				infoVo.setInfo(AFBVo.getMessageValidation());
				mainFrame.getBtnExcuter().setEnabled(true);
				mainFrame.addOrUpdateEtat(infoVo);
			}
			// //////////////
		} catch (Exception e) {
			logger.fatal("**** exception *** : " + this.getClass());
			SwingInfoVo infoVo = new SwingInfoVo();
			infoVo.setNumSocieteAFB(AFBVo.getSocietesAFBView().getNumSoctAFB());
			infoVo.setNomSocieteAFB(AFBVo.getSocietesAFBView().getNomSoctAFB());
			infoVo.setEtat(Constants.STATUT_EN_ERRUR);
			infoVo.setDateComptable(DateHandler.dateToStr(AFBVo.getDateComptable()));
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

	public void setMainFrame(BatchAFBFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public BatchAFBFrame getMainFrame() {
		return mainFrame;
	}

	public AFBVo getAFBVo() {
		return AFBVo;
	}

	public void setAFBVo(AFBVo aFBVo) {
		AFBVo = aFBVo;
	}

}
