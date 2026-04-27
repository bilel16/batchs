package com.bna.smile.batch.moulinette;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.AFBVo;
import com.bna.smile.model.domainecommun.traitement.GestionAFBTrt;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

public class MoulinetteAFBControlM extends AbstractJob {

	private static final Logger logger = Logger.getLogger(MoulinetteAFBControlM.class);
	private AFBVo AFBVo;

	// ************************//

	/**
	 * Consutructor
	 */
	public MoulinetteAFBControlM() {
		super();
	}

	public MoulinetteAFBControlM(AFBVo AFBVo) {
		super();
		this.AFBVo = AFBVo;
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
				System.out.println(AFBVo.getMessageValidation());
			} else {
				System.out.println("Erreur: " + AFBVo.getMessageValidation());
			}
			// //////////////
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("**** exception *** : " + this.getClass());

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

	public AFBVo getAFBVo() {
		return AFBVo;
	}

	public void setAFBVo(AFBVo aFBVo) {
		AFBVo = aFBVo;
	}

}
