package com.bna.smile.batch.moulinette;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.test.BatchVirementCompteVertFrame;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.traitement.VirementsLieesComptesVertsTrt;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

public class MoulinetteVirementsLieesComptesVerts extends AbstractJob {

	private static final Logger logger = Logger.getLogger(MoulinetteVirementsLieesComptesVerts.class);
	private BatchVirementCompteVertFrame mainFrame;
	private VirementVo virementVo;

	/*******************************/

	public MoulinetteVirementsLieesComptesVerts() {
		super();

	}

	public MoulinetteVirementsLieesComptesVerts(BatchVirementCompteVertFrame mainFrame, VirementVo virementVo) {
		super();
		this.mainFrame = mainFrame;
		this.virementVo = virementVo;
	}

	public MoulinetteVirementsLieesComptesVerts(VirementVo virementVo) {
		super();

		this.virementVo = virementVo;
	}

	/**
	 * point d'entrée de la moulinette
	 */
	public void perform() {

		try {
			fixerUser();

			VirementsLieesComptesVertsTrt virementsLieesComptesVertsTrt = new VirementsLieesComptesVertsTrt();
			virementVo = (VirementVo) virementsLieesComptesVertsTrt.exec(virementVo);

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

	public void setVirementVo(VirementVo virementVo) {
		this.virementVo = virementVo;
	}

	public VirementVo getVirementVo() {
		return virementVo;
	}

	public void setMainFrame(BatchVirementCompteVertFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public BatchVirementCompteVertFrame getMainFrame() {
		return mainFrame;
	}

}
