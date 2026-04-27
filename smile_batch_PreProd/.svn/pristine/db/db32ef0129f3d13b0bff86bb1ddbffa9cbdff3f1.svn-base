package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.net.ftp.FTPClient;

import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.batch.test.BatchFrame;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class GetFilesEffetTrt extends Traitement {

	Context context = ContextHandler.getContext();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");

	@Override
	protected void genCroText(ValueObject arg0) {
		// TODO Auto-generated method stub

	}
    private BatchFrame mainFrame;
	
	public BatchFrame getMainFrame() {
		return mainFrame;
	}
	
	public void setMainFrame(BatchFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	@Override
	protected IValueObject perform(IValueObject arg0) throws Exception {
		CompensationVo compensationVo = new CompensationVo();
		List listAgences = compensationDAO.getListAgencesCompensationPilote();
		ListOrderedMap ListAg = null;
		SimpleDateFormat formatDate = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat formatDateIm = new SimpleDateFormat("yyyyMMdd");
		Date dateSys = new Date();

		if (listAgences != null && listAgences.size() > 0) {
			for (Iterator it1 = listAgences.iterator(); it1.hasNext();) {
				ListAg = (ListOrderedMap) it1.next();

				if ((ListAg.getValue(0)).toString() != null && (ListAg.getValue(1)).toString() != null) {
					Structure strc = compensationDAO.findStructure(new Long((ListAg.getValue(0)).toString()));
					if (strc.getTypeStructure().getCodTstrTstr().equals(1L)
							|| strc.getTypeStructure().getCodTstrTstr().equals(6L)) {
					compensationVo.setDateComptable(DateHandler.strToDate(ListAg.getValue(1).toString()));
					compensationVo
							.setStrutcure(strc);
					SwingInfoVo infoVo = new SwingInfoVo();
					infoVo.setStructure("" + strc.getCodStrcStrc());
					infoVo.setEtat(Constants.STATUT_EN_COURS_LECT);
					mainFrame.updateInfo("Agence "+strc.getCodStrcStrc() +" "+ Constants.STATUT_EN_COURS_LECT);

					mainFrame.addOrUpdateEtat(infoVo);
					String agBct=StrHandler.lpad(compensationVo.getStrutcure().getCodBctStrc(), '0', 3);
					String pathTravail =
							File.separatorChar + Configuration.getParentPath() + File.separatorChar
									+ Configuration.getLocalPathCheque() + File.separatorChar + "reçu"
									+ File.separatorChar + "effet" + File.separatorChar + "agence"
									+ agBct + File.separatorChar
									+ formatDate.format(dateSys) + File.separatorChar + "travail" + File.separatorChar;
					String remotePath =
							File.separatorChar + "AGENCE" + agBct
									+ File.separatorChar + "Out" + File.separatorChar;
					logger.info("Importaion Fichier :" + agBct+ ":"
							+ compensationVo.getDateComptable());
					FTPClient ftpClient=Util.connectToFtp();
					Util.copyFtpFilesData(ftpClient,agBct,compensationVo.getDateComptable(),new String[]{"40","41","42"},pathTravail,remotePath);
					}
				}

			}

		}
		
		return null;
	}

}