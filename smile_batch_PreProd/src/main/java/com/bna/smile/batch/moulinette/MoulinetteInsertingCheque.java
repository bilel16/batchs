package com.bna.smile.batch.moulinette;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.util.StopWatch;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.batch.test.Batch;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.commande.InsertingChequeCmd;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.SuivFileTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;

public class MoulinetteInsertingCheque {

	Context context = ContextHandler.getContext();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");
	FTPClient ftpClient;
	final InsertingChequeCmd insertingChequeCmd = new InsertingChequeCmd();
	List<Thread> lexec1 = new ArrayList<Thread>();
	public Batch mainFrame;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	CompensationVo compensationVo = null;
	String fileManquantRcp = "";
	int i = 0;

	public MoulinetteInsertingCheque(Batch mainFrame) {
		this.mainFrame = mainFrame;
	}

	public IValueObject perform(IValueObject arg0) {
		List listAgences = compensationDAO.getListAgencesCompensationPilote();
		ListOrderedMap ListAg = null;
		StopWatch elapsedTime = new StopWatch("Time of execution");
		elapsedTime.start("All agencies");

		if (listAgences != null && listAgences.size() > 0) {
			for (Iterator it1 = listAgences.iterator(); it1.hasNext();) {
				ListAg = (ListOrderedMap) it1.next();
				if ((ListAg.getValue(0)).toString() != null && (ListAg.getValue(1)).toString() != null) {
					System.out.println("********** " + ListAg.getValue(1).toString());
					compensationVo = new CompensationVo();
					compensationVo.setDateComptable(DateHandler.strToDate(ListAg.getValue(1).toString()));
					compensationVo
							.setStrutcure(compensationDAO.findStructure(new Long((ListAg.getValue(0)).toString())));
					SwingInfoVo infoVo = new SwingInfoVo();
					infoVo.setStructure("" + compensationVo.getStrutcure().getCodStrcStrc());
					infoVo.setEtat(Constants.STATUT_EN_COURS_INSERT);
					mainFrame.getEnd_exec_label()
							.setText("Insertion Agence : " + compensationVo.getStrutcure().getCodStrcStrc());

					// update 06042017
					String bctAge = StrHandler.lpad(compensationVo.getStrutcure().getCodBctStrc(), '0', 3);
					String remotePath = "agence" + bctAge + File.separatorChar + "out" + File.separatorChar;
					compensationVo.setNameFile(File.separatorChar + "10.1.2.11" + File.separatorChar + "Compensation"
							+ File.separatorChar + remotePath);

					// end update
					compensationVo.setDescription("");

					mainFrame.getBtnExcuter().setEnabled(false);
					infoVo.setDateComptable(sdf.format(compensationVo.getDateComptable()));
					mainFrame.addOrUpdateEtat(infoVo);
					compensationVo.setBatch(mainFrame);
					compensationVo = (CompensationVo) insertingChequeCmd.execute(compensationVo);
					if (compensationVo.getDescription() == null || compensationVo.getDescription().equals("")) {
						infoVo.setEtat(Constants.STATUT_EN_TERMINE);
						SuivFileTrt.validInsert(compensationVo.getDateComptable(), bctAge);
					} else {
						infoVo.setEtat(compensationVo.getDescription());

						i = i + 1;
						fileManquantRcp = i + "  Fichier 30-RCP Manquant . ";

					}
					mainFrame.addOrUpdateEtat(infoVo);

				}

			}
			mainFrame.getBtnExcuter().setEnabled(true);
			mainFrame.getBtnPosition().setEnabled(true);
			mainFrame.getMsgDetailChq().setVisible(false);
			mainFrame.getMsgDetail().setVisible(false);

			if (!fileManquantRcp.equals("")) {
				mainFrame.getBtnValidInsertion().setText("Forcer Insertion");
				mainFrame.getBtnValidInsertion().setEnabled(true);
				mainFrame.getEnd_exec_label().setText(fileManquantRcp);
			} else {
				mainFrame.getBtnValidInsertion().setEnabled(true);
				mainFrame.getEnd_exec_label().setText("Insertion Chéque exécuté avec succès");
			}

			try {

				print();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		elapsedTime.stop();
		// logger.info("Total time execution of :"+elapsedTime.getTotalTimeSeconds()/60+ " (min)");

		return null;
	}

	public void print() throws SQLException, IOException {
	}

	public IValueObject forcer(IValueObject arg0) {
		List listAgences = compensationDAO.getListAgencesCompensationPilote();
		ListOrderedMap ListAg = null;
		if (mainFrame.getBtnValidInsertion().getText().equals("Valider Insertion")) {
			mainFrame.getBtnValidInsertion().setVisible(false);
			mainFrame.getBtnExcuter().setVisible(false);
			mainFrame.getBtnPosition().setVisible(true);
			// compensationVo.getBatch().getMsgDetailChq().setText("");
			// compensationVo.getBatch().getMsgDetailChq().setText("");
			mainFrame.getEnd_exec_label().setText("Validation Insertion avec Succes.");

			mainFrame.getBtnExcuter().setEnabled(true);
			mainFrame.getBtnPosition().setEnabled(true);
			mainFrame.getMsgDetailChq().setVisible(false);
			mainFrame.getMsgDetail().setVisible(false);
			mainFrame.getBtnValidInsertion().setEnabled(true);
			mainFrame.getEnd_exec_label().setText("Insertion Chéque exécuté avec succès");

			SuivFileTrt.validerInsertion(compensationDAO.getDateCompta());

		} else {

			if (listAgences != null && listAgences.size() > 0) {
				for (Iterator it1 = listAgences.iterator(); it1.hasNext();) {
					ListAg = (ListOrderedMap) it1.next();
					if ((ListAg.getValue(0)).toString() != null && (ListAg.getValue(1)).toString() != null) {

						compensationVo = new CompensationVo();
						compensationVo.setDateComptable(DateHandler.strToDate(ListAg.getValue(1).toString()));
						break;
					}
				}
			}
			mainFrame.getBtnExcuter().setEnabled(true);
			mainFrame.getBtnPosition().setVisible(true);
			mainFrame.getBtnPosition().setEnabled(true);
			mainFrame.getBtnExcuter().setVisible(false);
			mainFrame.getMsgDetailChq().setVisible(false);
			mainFrame.getMsgDetail().setVisible(false);
			mainFrame.getBtnValidInsertion().setEnabled(true);
			mainFrame.getBtnValidInsertion().setVisible(false);

			SuivFileTrt.forcer(compensationVo.getDateComptable());
			mainFrame.getEnd_exec_label().setText("Forçage Insertion exécuté avec succès");

		}

		return null;
	}

}
