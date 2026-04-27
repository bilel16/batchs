package com.bna.smile.batch.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.batch.moulinette.MoulInteretServiPlac;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinetteServiPlacTest implements Runnable {

	@Override
	public void run() {
		String[] path = 
	        { "./config/spring.xml", "./config/applicationContext-DAO.xml", "./config/applicationContext-habilitation.xml", 
	          "./config/applicationContext-resources.xml", "./config/applicationContext-service.xml", 
	          "./config/applicationContext-serviceBatch.xml", 
	          "./config/applicationContext-serviceHabil.xml", 
	          "./config/applicationContext-traitements.xml", "./config/quartz-oxia-calendars.xml", 
	          "./config/quartz-oxia-core.xml", "./config/quartz-oxia-jobs.xml", "./config/security.xml",
	          "./config/quartz-oxia-listners.xml", "./config/quartz-oxia-triggers.xml" };

	        ApplicationContext springContext =  
	            new FileSystemXmlApplicationContext(path);
	        Context context= (Context) ContextFactory.initContext("./config/applicationContext-1Spring.xml");
	        context.setSpringContext(springContext);
	        ContextHandler.setContext(context);

		MoulInteretServiPlac moulinette = new MoulInteretServiPlac();
		moulinette.perform();
		
		  System.out.println("Moulinette Interet Servie Placement  terminée avec succès");
	}

}
