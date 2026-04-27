package com.bna.smile.batch.moulinette;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationVo;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.MoulinetteMigrationTrt;
import com.bna.smile.model.traitementCompensationRecu.dao.RejetDAO;
import com.oxia.fwk.context.Context;

public class  MoulinetteMigrationCompensation {
	
	Context context = ContextHandler.getContext();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");
	RejetDAO  rejetDao = (RejetDAO)context.getBean("rejetDAO");
	MoulinetteMigrationTrt moulinetteMigrationTrt= new MoulinetteMigrationTrt();






	public  void perform( ) {
		
		
		CompensationVo compensationVo=new CompensationVo();
		List listAgences = compensationDAO.getListAgencesCompensation();
		ListOrderedMap ListAg = null;
		if (listAgences != null && listAgences.size() > 0) {
			for (Iterator it1 = listAgences.iterator(); it1.hasNext();) {
				ListAg = (ListOrderedMap) it1.next();
				if ((ListAg.getValue(0)).toString() != null && (ListAg.getValue(1)).toString() != null) 
				{
					compensationVo.setDateComptable(DateHandler.strToDate(ListAg.getValue(1).toString()));
					compensationVo.setStrutcure(compensationDAO.findStructure(new Long((ListAg.getValue(0)).toString())));
					if( compensationVo.getStrutcure().getCodStrcStrc()==155L ){
					System.out.println("Begining treatment of agency : "+ StrHandler.lpad(""+compensationVo.getStrutcure().getCodStrcStrc(),'0',3));
					moulinetteMigrationTrt.exec(compensationVo);
					System.out.println("Ending treatment of agency : "+ StrHandler.lpad(""+compensationVo.getStrutcure().getCodStrcStrc(),'0',3));

					}
		
				}
				 
				} 		
			}
		}


	
	

}
