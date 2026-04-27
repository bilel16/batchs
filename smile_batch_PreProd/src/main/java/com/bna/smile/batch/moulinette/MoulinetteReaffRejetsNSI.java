package com.bna.smile.batch.moulinette;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.batch.test.BatchReaffRejetsNSIFrame;
import com.bna.smile.model.domaineguichet.traitement.ReaffecterRejtesTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

public class MoulinetteReaffRejetsNSI extends AbstractJob {

	private static final Logger logger = Logger.getLogger(MoulinetteReaffRejetsNSI.class);
	private BatchReaffRejetsNSIFrame mainFrame;

	/**
	 * Consutructor
	 */
	public MoulinetteReaffRejetsNSI() {
		super();
	}

	public MoulinetteReaffRejetsNSI(BatchReaffRejetsNSIFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
	}

	/**
	 * ^point d'entrée de la moulinette
	 */
	public void perform() {

		try {
			fixerUser();
			Context context = ContextHandler.getContext();
			ReaffecterRejtesTrt reaffecterRejtesTrt = new ReaffecterRejtesTrt(mainFrame);
			reaffecterRejtesTrt.exec(new ValueObject());
		} catch (Exception e) {
			logger.fatal("**** exception *** : " + this.getClass() + " ----- " + e.getMessage());
			// throw new RuntimeException(e);
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

}
