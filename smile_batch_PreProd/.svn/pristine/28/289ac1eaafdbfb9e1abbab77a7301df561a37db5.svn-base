package com.bna.smile.batch.moulinette;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domaineplacement.traitement.AbonnementPlacementTrt;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

public class MoulAbonnementPlac extends AbstractJob {

    private static final Logger logger = 
        Logger.getLogger(MoulinetteLiquidationAecheance.class);

    /**
     *^point d'entrée de la moulinette
     */
    public void perform() {

        try {
            fixerUser();
            IValueObject vo = new ValueObject();
            AbonnementPlacementTrt abonnementPlacementTrt = new AbonnementPlacementTrt();
            vo =abonnementPlacementTrt.exec(vo);
        } catch (Exception e) {
            logger.fatal("**** exception *** : " + this.getClass());

        }
    }


    public void fixerUser() {
            ContextCROHandler.setContext(ContextHandler.getContext());
            
        Personnel user = new Personnel();
        UserManager usermanager = (UserManager)ContextHandler.getContext().getBean("userManager");
        user = usermanager.getUser("9999");
        
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        auth.setDetails(user);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    
}
