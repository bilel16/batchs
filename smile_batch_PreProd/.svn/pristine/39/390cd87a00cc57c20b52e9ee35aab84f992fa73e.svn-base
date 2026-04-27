package com.bna.smile.batch.moulinette;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.test.BatchCommissionSouscriptionPackFrame;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement.GestionPerceptionCommissionFraisPackTrt;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

public class MoulinetteCommissionFraisTenueCompte extends AbstractJob {

	private static final Logger logger = Logger.getLogger(MoulinetteCommissionFraisTenueCompte.class);
	private BatchCommissionSouscriptionPackFrame mainFrame;
	private VirementVo virementVo;
	Context context = ContextHandler.getContext();

	// ************************//

	/**
	 * Consutructor
	 */
	public MoulinetteCommissionFraisTenueCompte() {
		super();
	}

	public MoulinetteCommissionFraisTenueCompte(VirementVo virementVo) {
		super();
		this.virementVo = virementVo;
	}

	public MoulinetteCommissionFraisTenueCompte(VirementVo virementVo, BatchCommissionSouscriptionPackFrame mainFrame) {
		super();
		this.virementVo = virementVo;
		this.mainFrame = mainFrame;
	}

	/**
	 * point d'entrée de la moulinette
	 */
	public void perform() {

		try {
			fixerUser();
			IValueObject vo = new ValueObject();
			

			GestionPerceptionCommissionFraisPackTrt gestionPerceptionCommissionFraisPack = new GestionPerceptionCommissionFraisPackTrt();
			virementVo = (VirementVo) gestionPerceptionCommissionFraisPack.exec(virementVo);
			if (virementVo.isEtatEnregistrement() == true) {
				SwingInfoVo infoVo = new SwingInfoVo();
				infoVo.setStructure("" + virementVo.getStructure().getCodStrcStrc());
				infoVo.setEtat(Constants.STATUT_EN_TERMINE);
				infoVo.setDateComptable(DateHandler.dateToStr(virementVo.getDateComptableAgence()));
				infoVo.setInfo(virementVo.getMessageValidation());
				mainFrame.getBtnExcuter().setEnabled(false);
				mainFrame.addOrUpdateEtat(infoVo);
			} else {
				SwingInfoVo infoVo = new SwingInfoVo();
				infoVo.setStructure("" + virementVo.getStructure().getCodStrcStrc());
				infoVo.setEtat(Constants.STATUT_EN_ERRUR);
				infoVo.setDateComptable(DateHandler.dateToStr(virementVo.getDateComptableAgence()));
				infoVo.setInfo(virementVo.getMessageValidation());
				mainFrame.getBtnExcuter().setEnabled(true);
				mainFrame.addOrUpdateEtat(infoVo);
			}
			// //////////////
		} catch (Exception e) {
			logger.fatal("**** exception *** : " + this.getClass());
			SwingInfoVo infoVo = new SwingInfoVo();
			infoVo.setStructure("" + virementVo.getStructure().getCodStrcStrc());
			infoVo.setEtat(Constants.STATUT_EN_ERRUR);
			infoVo.setDateComptable(DateHandler.dateToStr(virementVo.getDateComptableAgence()));
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

	public void setMainFrame(BatchCommissionSouscriptionPackFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public BatchCommissionSouscriptionPackFrame getMainFrame() {
		return mainFrame;
	}

	public void setVirementVo(VirementVo virementVo) {
		this.virementVo = virementVo;
	}

	public VirementVo getVirementVo() {
		return virementVo;
	}

}
