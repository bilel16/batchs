package com.bna.smile.batch.moulinette;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.util.StopWatch;

import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.JourneeStructureBatch;
import com.bna.commun.model.JourneeStructureBatchId;
import com.bna.commun.model.Structure;
import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.test.Batch;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ListEditBatchVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.domainecompensation.gestionrejet.service.GestionRejetService;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.SuivFileTrt;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.Util;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.searchengine.SearchEngine;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class MoulinettePositionChequeACH extends AbstractJob {

	Context context = ContextHandler.getContext();

	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

	ICriteria criteria = searchEngine.createCriteria();
	ICriteria criteriaCpt = searchEngine.createCriteria();
	ICriteria criteriaChq = searchEngine.createCriteria();

	IExpression expression = searchEngine.createExpression();

	// thread
	List<Thread> execs = new ArrayList<Thread>();

	StopWatch stopWatch = new StopWatch(" Time Of Exec..");
	//

	Structure agence;

	public Batch mainFrame;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	Date dateComptableAgence = null;

	CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");

	GestionRejetService gestionRejetService = (GestionRejetService) context.getBean("gestionRejetService");

	public MoulinettePositionChequeACH(Batch mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void perform() {
		CompensationVo compensationVo = new CompensationVo();

		compensationVo.setBatch(mainFrame);

		try {
			fixerUser();

			List listAgences = compensationDAO.getListAgencesCompensationPilote();
			ListOrderedMap ListAg = null;

			if (listAgences != null && listAgences.size() > 0) {
				for (Iterator it1 = listAgences.iterator(); it1.hasNext();) {

					agence = new Structure();
					ListAg = (ListOrderedMap) it1.next();

					if ((ListAg.getValue(0)).toString() != null) {
						// agence.setCodStrcStrc(Long.valueOf(ListAg.getValue(0).toString()));
						agence = compensationDAO.findStructure(new Long((ListAg.getValue(0)).toString()));
					}

					if ((ListAg.getValue(1)).toString() != null) {
						ListAg.getValue(1);
						dateComptableAgence = DateHandler.strToDate(ListAg.getValue(1).toString());
					}

					// tester JourneeStructureBatch
					JourneeStructureBatch journeeStructureBatch = new JourneeStructureBatch();

					JourneeStructureBatch journeeStructureBatchRetour = new JourneeStructureBatch();

					JourneeStructureBatchId journeeStructureBatchId = new JourneeStructureBatchId();
					journeeStructureBatchId.setCodStrcStrc(agence.getCodStrcStrc());
					journeeStructureBatchId.setDatJrnJrn(dateComptableAgence);
					journeeStructureBatchId.setCodBatBmet(Constants.COD_BATCH_COMPENSATION_CHEQUE);

					journeeStructureBatch.setJourneeStructureBatchId(journeeStructureBatchId);

					BatchService batchService = (BatchService) context.getBean("batchService");

					journeeStructureBatchRetour = (JourneeStructureBatch) batchService
							.getJourneeStructureBatch(journeeStructureBatch);

					// if (journeeStructureBatchRetour != null&&
					// journeeStructureBatchRetour.getCodStatJsb().intValue() == 0)
					// {
					// structure non traitée

					compensationVo.setStrutcure(agence);
					compensationVo.setDateComptable(dateComptableAgence);

					SwingInfoVo infoVo = new SwingInfoVo();
					infoVo.setStructure("" + compensationVo.getStrutcure().getCodStrcStrc());
					infoVo.setEtat(Constants.STATUT_EN_COURS_POS);
					infoVo.setDateComptable(sdf.format(dateComptableAgence));

					mainFrame.getEnd_exec_label()
							.setText("Position Agence   : " + compensationVo.getStrutcure().getCodStrcStrc());

					SuivFileTrt.validPos(compensationVo.getDateComptable(), agence.getCodBctStrc());

					mainFrame.getBtnExcuter().setEnabled(false);

					mainFrame.addOrUpdateEtat(infoVo);

					compensationVo = (CompensationVo) gestionRejetService.positionCompensation(compensationVo);

					journeeStructureBatch.setDatCloJsb(new Date());
					journeeStructureBatch.setCodStatJsb(Long.valueOf("1"));

					// journeeStructureBatch = (JourneeStructureBatch)
					// batchService.updateJourneeStructureBatch(journeeStructureBatch);

					infoVo.setStructure("" + compensationVo.getStrutcure().getCodStrcStrc());
					infoVo.setEtat(Constants.STATUT_EN_TERMINE);
					infoVo.setDateComptable(sdf.format(dateComptableAgence));

					mainFrame.addOrUpdateEtat(infoVo);

					// }
					// else
					// {
					// logger.debug("Journée batch dejà insérée");
					// }
				}

				stopWatch.start("Exec of all agencies ");
				// for(Thread t:execs){
				// t.start();
				// }
				// for(Thread t:execs){
				// t.join();
				// }

				stopWatch.stop();

				logger.info("Total time execution of  :" + stopWatch.getTotalTimeSeconds() / 60 + " (min)");
				logger.debug("FIN POSITION COMPENSATION ACH ");

				mainFrame.getBtnExcuter().setEnabled(true);
				mainFrame.getBtnPosition().setEnabled(true);
				mainFrame.getMsgDetailChq().setVisible(false);
				mainFrame.getMsgDetail().setVisible(false);
				mainFrame.getEnd_exec_label().setText("Position Chéque ACH exécuté avec succès");
				// print(compensationVo);

			} else {
				logger.debug("La liste des agences est vide.");
			}
		} catch (Exception e) {
			logger.fatal("**** Exception *** MoulinettePositionCompensation *** : " + this.getClass());

			e.printStackTrace();

			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans MoulinettePositionCompensationACH : ");

			text.append(e.toString());

			erreur.setCode("500");
			erreur.setDescription(text.toString());
			erreur.setKey("MoulinettePositionCompensationACH");

			logger.error("Exception : ", e);
			// gestionException(dateComptableAgence, agence, e);
			throw new RuntimeException(e);
		}
	}

	public void fixerUser() {

		ContextCROHandler.setContext(ContextHandler.getContext());

		Personnel user = new Personnel();
		UserManager usermanager = (UserManager) ContextHandler.getContext().getBean("userManager");
		user = usermanager.getUser("9999");
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(),
				user.getAuthorities());
		auth.setDetails(user);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private void gestionException(Date dateComptable, Structure agence, Exception e) {
		BatchExeptionPlac batchExeptionPlac = new BatchExeptionPlac();

		batchExeptionPlac.setDatSystBate(new Date());
		batchExeptionPlac.setDatCompBate(dateComptable);
		batchExeptionPlac.setStructure(agence);
		batchExeptionPlac.setLibTpbmBate("Exception Batch Position compensation");
		batchExeptionPlac.setLibExpBate(e.getMessage());

		BatchService batchService = (BatchService) context.getBean("batchService");

		batchExeptionPlac = (BatchExeptionPlac) batchService.InsertBatchExeptionPlac(batchExeptionPlac);
	}

	private void print(CompensationVo compensationVo) throws IOException {
		// print etat

		System.out.println("printing ******************************");
		File currentDirectory = new File(new File(".").getAbsolutePath());

		Map params = new HashMap();
		params.put("COD_STRC_STRC", "900");
		params.put("LIB_STRC_STRC", "Trésorerie");
		params.put("P_PATH", currentDirectory.getCanonicalPath() + "//jasper//");

		JasperReport report = null;
		JasperPrint jasperPrint = null;

		ListEditBatchVo edit = new ListEditBatchVo();
		edit.setListe(
				compensationDAO.calculGlobalAgence(DateHandler.dateToStr(compensationVo.getDateComptable()), "30"));

		List<SwingInfoVo> liste = edit.getListe();
		params.put("montant_total_30", liste.get(0).getMontant_total_30());
		params.put("nombre_total_30", liste.get(0).getNombre_total_30());
		params.put("montant_total_31", liste.get(0).getMontant_total_31());
		params.put("nombre_total_31", liste.get(0).getNombre_total_31());
		params.put("montant_total_32", liste.get(0).getMontant_total_32());
		params.put("nombre_total_32", liste.get(0).getNombre_total_32());
		params.put("montant_total_33", liste.get(0).getMontant_total_33());
		params.put("nombre_total_33", liste.get(0).getNombre_total_33());

		params.put("MT30", liste.get(0).getMT30());
		params.put("NT30", liste.get(0).getNT30());
		params.put("MT31", liste.get(0).getMT31());
		params.put("NT31", liste.get(0).getNT31());
		params.put("MT32", liste.get(0).getMT32());
		params.put("NT32", liste.get(0).getNT32());
		params.put("MT33", liste.get(0).getMT33());
		params.put("NT33", liste.get(0).getNT33());

		params.put("P_DAT_OP", DateHandler.dateToStr(compensationVo.getDateComptable()));

		try {
			report = (JasperReport) JRLoader
					.loadObject(currentDirectory.getCanonicalPath() + "/jasper/Etat_Global_Cheque.jasper");

			jasperPrint = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(liste));
			OutputStream output = new FileOutputStream(
					new File(currentDirectory.getCanonicalPath() + "/jasper/JasperReport.pdf"));
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);
			output.close();
			Util.ShowPDF(currentDirectory.getCanonicalPath() + "/jasper/JasperReport.pdf");

		} catch (JRException e) {

			e.printStackTrace();
		}

	}
}

// class PosExec extends Thread {
// private Context context = ContextHandler.getContext();
// private GestionRejetService gestionRejetService = (GestionRejetService)
// context.getBean("gestionRejetService");
// private CompensationVo compensationVo = new CompensationVo();
// PosExec(final CompensationVo vo) {
// this.compensationVo =vo;
// }
// public void run() {
// logger.info("Debut position Agence : "
// +compensationVo.getStrutcure().getCodStrcStrc());
// gestionRejetService.positionCompensation(this.compensationVo);
// logger.info("Fin position Agence : "
// +compensationVo.getStrutcure().getCodStrcStrc());
//
//
//
//
// }
//
//
//
//
// }
