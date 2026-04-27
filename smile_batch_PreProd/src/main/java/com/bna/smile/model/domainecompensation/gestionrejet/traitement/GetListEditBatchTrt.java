package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import org.apache.log4j.Logger;

import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ListEditBatchVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * @author Haythem ayari
 * 
 */
public class GetListEditBatchTrt extends Traitement {

	public static final Logger logger = Logger.getLogger(GetListEditBatchTrt.class);
	Context context = ContextHandler.getContext();

	@Override
	public IValueObject perform(IValueObject vo) {

		CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");
		ListEditBatchVo editBatchVo = (ListEditBatchVo) vo;

		Structure strc = null;
		System.out.println("editBatchVo.isGlobal()" + editBatchVo.isGlobal());
		if (!editBatchVo.isGlobal()) {

			if (editBatchVo.getStructure() != null && !editBatchVo.getStructure().equals("")) {
				strc = compensationDAO.findStructure(Long.valueOf(editBatchVo.getStructure()));
				editBatchVo.setStructure(StrHandler.lpad(strc.getCodBctStrc(), '0', 3));
			} else {
				editBatchVo.setStructure(null);
			}
			editBatchVo.setListe(compensationDAO.getListEditBatch(editBatchVo.getSens(), editBatchVo.getDateStart(),
					editBatchVo.getDateEnd(), editBatchVo.getValeur(), editBatchVo.getStructure()));
		} else {
			if (editBatchVo.getValeur().equalsIgnoreCase("20")) {
				if (editBatchVo.getStructure() != null && !editBatchVo.getStructure().equals("")) {
					strc = compensationDAO.findStructure(Long.valueOf(editBatchVo.getStructure()));
					editBatchVo.setStructure(StrHandler.lpad(strc.getCodBctStrc(), '0', 3));
				} else {
					editBatchVo.setStructure(null);
				}
				editBatchVo.setListe(compensationDAO.getListEditBatch(editBatchVo.getSens(),
						editBatchVo.getDateStart(), editBatchVo.getDateEnd(), editBatchVo.getValeur(),
						editBatchVo.getStructure()));

			} else {
				editBatchVo.setListe(compensationDAO.calculGlobalAgence(editBatchVo.getDateStart(),
						editBatchVo.getValeur()));
			}
		}

		return editBatchVo;
	}

	@Override
	protected void genCroText(ValueObject arg0) {
		// TODO Auto-generated method stub

	}

}
