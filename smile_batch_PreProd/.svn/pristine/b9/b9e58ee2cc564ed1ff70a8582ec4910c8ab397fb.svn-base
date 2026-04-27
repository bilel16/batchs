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
import com.bna.smile.model.prelevement.traitement.InsertionPrelevementsDomiciliationsTrt;
import com.oxia.fwk.context.Context;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

public class MoulinettePrelevementsDomiciliations extends AbstractJob {

	private static final Logger logger = Logger.getLogger(MoulinettePrelevementsDomiciliations.class);
	Context context = ContextHandler.getContext();
	PrelevementBatchService prelevementBatchService = (PrelevementBatchService) context
			.getBean("iPrelevementBatchService");
	private PrelevementVo prelevementVo;
	private BatchFrame mainFrame;

	/**
	 * Consutructor
	 */
	public MoulinettePrelevementsDomiciliations() {
		super();

	}

	public MoulinettePrelevementsDomiciliations(PrelevementVo prelevementVo) {
		super();
		this.prelevementVo = prelevementVo;
	}

	public MoulinettePrelevementsDomiciliations(PrelevementVo prelevementVo, BatchFrame mainFrame) {
		super();
		this.prelevementVo = prelevementVo;
		this.mainFrame = mainFrame;
	}

	public void perform() {

		try {
			fixerUser();

			InsertionPrelevementsDomiciliationsTrt insertionPrelevementsDomiciliationsTrt =
					new InsertionPrelevementsDomiciliationsTrt();
			prelevementVo = (PrelevementVo) insertionPrelevementsDomiciliationsTrt.exec(prelevementVo);

			logger.info("Moulinette InsertionPrelevementsDomiciliations a été générée avec sucées");
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

	public void setPrelevementVo(PrelevementVo prelevementVo) {
		this.prelevementVo = prelevementVo;
	}

	public PrelevementVo getPrelevementVo() {
		return prelevementVo;
	}

	public BatchFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(BatchFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

}
