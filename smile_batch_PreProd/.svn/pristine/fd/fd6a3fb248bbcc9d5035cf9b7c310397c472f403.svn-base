package com.bna.smile.batch.test;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.model.Structure;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.moulinette.MoulinetteBNASms;
import com.bna.smile.model.virement.dao.VirementGlobalDAO;
import com.bna.smile.model.virement.model.VirementVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class MoulinetteBNASmsTest implements Runnable {

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
		
		try {
			VirementGlobalDAO virementGlobalDAO = (VirementGlobalDAO) context.getBean("virementGlobalDAO");
			List listAgences = virementGlobalDAO.getListAgencesVirement();
			
			Date dateComptableAgence = null;
			
			if (listAgences != null && listAgences.size() > 0) {

				for (Object result : listAgences) {
					VirementVo virementVo = new VirementVo();
					Structure agence = new Structure();
					ListOrderedMap map = (ListOrderedMap) result;

					agence.setCodStrcStrc(Long.valueOf(map.get("COD_STRC_STRC") + ""));
					dateComptableAgence = DateHandler.strToDate(map.get("DAT_JRN_JRN").toString());

					virementVo.setStructure(agence);
					virementVo.setDateComptableAgence(dateComptableAgence);

					MoulinetteBNASms moulinetteSms = new MoulinetteBNASms(virementVo);
					moulinetteSms.perform();
					
				}
				
				System.out.println("Fin du traitement...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
