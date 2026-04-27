package com.bna.smile.batch.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.batch.moulinette.MoulinetteMigrationCompensation;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinetteMigrationCompensationTest {
	 public static void main(String[] args) {

	        String[] path = 
	        { "./config/spring.xml", "./config/applicationContext-DAO.xml", "./config/applicationContext-habilitation.xml", 
	          "./config/applicationContext-resources.xml", "./config/applicationContext-service.xml", 
	          "./config/applicationContext-serviceBatch.xml", 
	          "./config/applicationContext-serviceHabil.xml", 
	          "./config/applicationContext-traitements.xml", "./config/quartz-oxia-calendars.xml", 
	          "./config/quartz-oxia-core.xml", "./config/quartz-oxia-jobs.xml", "./config/security.xml",
	          "./config/quartz-oxia-listners.xml", "./config/quartz-oxia-triggers.xml" };

	        ApplicationContext springContext = new FileSystemXmlApplicationContext(path);
	        Context context= (Context) ContextFactory.initContext("./config/applicationContext-1Spring.xml");
	        context.setSpringContext(springContext);
	        ContextHandler.setContext(context);
	        MoulinetteMigrationCompensation  importing = new MoulinetteMigrationCompensation();
	        importing.perform();
	        return;
	        
	    }

}
