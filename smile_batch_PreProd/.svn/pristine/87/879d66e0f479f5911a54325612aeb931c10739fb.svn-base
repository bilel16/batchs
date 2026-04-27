package com.bna.smile.batch.moulinette;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.virement.model.VirementVo;
import com.bna.smile.model.virement.traitement.VirementsAecheanceTrt;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

public class MoulinetteVirementsAecheanceControlM extends AbstractJob {

	private static final Logger logger = Logger.getLogger(MoulinetteVirementsAecheanceControlM.class);
	private VirementVo virementVo;

	// ************************//

	/**
	 * Consutructor
	 */
	public MoulinetteVirementsAecheanceControlM() {
		super();
	}

	public MoulinetteVirementsAecheanceControlM(VirementVo virementVo) {
		super();
		this.virementVo = virementVo;
	}

	/**
	 * point d'entrée de la moulinette
	 */
	public void perform() {

		try {
			fixerUser();
			IValueObject vo = new ValueObject();

			VirementsAecheanceTrt virementsAecheanceTrt = new VirementsAecheanceTrt();
			virementVo = (VirementVo) virementsAecheanceTrt.exec(virementVo);
			if (virementVo.isEtatEnregistrement() == true) {
				logger.info("Date : " + DateHandler.dateToStr(virementVo.getDateComptableAgence()) + " ==> Agence : "
						+ virementVo.getStructure().getCodStrcStrc() + " ---->  " + virementVo.getMessageValidation());

			} else {
				logger.error("Date : " + DateHandler.dateToStr(virementVo.getDateComptableAgence()) + " ==> Agence : "
						+ virementVo.getStructure().getCodStrcStrc() + " ---->  " + virementVo.getMessageValidation());
			}
			// //////////////
		} catch (Exception e) {
			logger.fatal("**** exception *** : " + this.getClass());
			logger.error("Date : " + DateHandler.dateToStr(virementVo.getDateComptableAgence()) + " ==> Agence : "
					+ virementVo.getStructure().getCodStrcStrc() + " ---->  " + e.getMessage());

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

}
