package com.bna.smile.batch.moulinette;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.test.BatchFrame;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.prelevement.model.PrelevementVo;
import com.bna.smile.model.prelevement.service.PrelevementBatchService;
import com.bna.smile.model.prelevement.traitement.GestionPrelevementsDomiciliationsTrt;
import com.oxia.fwk.context.Context;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

public class MoulinettePrelevements extends AbstractJob {

	private static final Logger logger = Logger.getLogger(MoulinettePrelevements.class);
	Context context = ContextHandler.getContext();
	PrelevementBatchService prelevementBatchService = (PrelevementBatchService) context
			.getBean("iPrelevementBatchService");

	private PrelevementVo prelevementVo;
	private BatchFrame mainFrame;

	/**
	 * Consutructor
	 */
	public MoulinettePrelevements() {
		super();

	}

	public MoulinettePrelevements(PrelevementVo prelevementVo) {
		super();
		this.prelevementVo = prelevementVo;
	}

	/**
	 * point d'entrée de la moulinette
	 */
	public void perform() {

		try {

			fixerUser();
			GestionPrelevementsDomiciliationsTrt gestionPrelevementsDomiciliationsTrt =
					new GestionPrelevementsDomiciliationsTrt();
			prelevementVo = (PrelevementVo) gestionPrelevementsDomiciliationsTrt.exec(prelevementVo);

			if (prelevementVo.isEtatEnregistrementPrelevement() == true) {
				SwingInfoVo infoVo = new SwingInfoVo();
				infoVo.setStructure("" + prelevementVo.getStructure().getCodStrcStrc());
				infoVo.setEtat(Constants.STATUT_EN_TERMINE);
				infoVo.setDateComptable(DateHandler.dateToStr(prelevementVo.getDateComptable()));
				mainFrame.getBtnExcuter().setEnabled(false);
				mainFrame.addOrUpdateEtat(infoVo);
			} else {
				SwingInfoVo infoVo = new SwingInfoVo();
				infoVo.setStructure("" + prelevementVo.getStructure().getCodStrcStrc());
				infoVo.setEtat(Constants.STATUT_EN_ERRUR);
				infoVo.setDateComptable(DateHandler.dateToStr(prelevementVo.getDateComptable()));
				mainFrame.getBtnExcuter().setEnabled(true);
				mainFrame.addOrUpdateEtat(infoVo);
			}
			// vo = (IValueObject) prelevementBatchService.gestionPrelevementsDomiciliationRecus(vo);
			// //////////////

		} catch (Exception e) {
			logger.fatal("**** exception *** : " + this.getClass());
			SwingInfoVo infoVo = new SwingInfoVo();
			infoVo.setStructure("" + prelevementVo.getStructure().getCodStrcStrc());
			infoVo.setEtat(Constants.STATUT_EN_ERRUR);
			infoVo.setDateComptable(DateHandler.dateToStr(prelevementVo.getDateComptable()));
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

	public PrelevementVo getPrelevementVo() {
		return prelevementVo;
	}

	public void setPrelevementVo(PrelevementVo prelevementVo) {
		this.prelevementVo = prelevementVo;
	}

	public void setMainFrame(BatchFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public BatchFrame getMainFrame() {
		return mainFrame;
	}

}
