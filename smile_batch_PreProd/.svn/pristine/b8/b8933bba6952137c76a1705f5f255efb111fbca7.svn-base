package com.bna.smile.batch.moulinette;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.model.ParamLiquidation;
import com.bna.smile.model.domaineplacement.traitement.LiquidationAEcheanceTrt;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

public class MoulinetteLiquidationAecheance extends AbstractJob {

	private static final Logger logger = Logger.getLogger(MoulinetteLiquidationAecheance.class);

	/**
	 * ^point d'entrée de la moulinette
	 */
	public void perform() {

		try {
			fixerUser();
			ParamLiquidation paramLiquidation = new ParamLiquidation();
			LiquidationAEcheanceTrt liquidationAEcheanceTrt = new LiquidationAEcheanceTrt();
			paramLiquidation = (ParamLiquidation) liquidationAEcheanceTrt.exec(paramLiquidation);
			System.out.println("Fin Moulinette Liquidation A Echeance ");
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("**** exception *** : " + this.getClass() + " ----- " + e.getMessage());
			System.err.println("Erreur au niveau de la Moulinette Liquidation A Echeance :" + e.getMessage());

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
