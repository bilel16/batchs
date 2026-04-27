package com.bna.smile.model.domaineguichet.traitement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.Order;

import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.JourneeStructureBatch;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TmpBatchVirNSI;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.test.BatchMAJNSIActelFrame;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.domaineguichet.dao.GuichetDAO;
import com.bna.smile.model.domaineguichet.model.AgencesMAJNSIVo;
import com.bna.smile.model.domaineguichet.model.MAJNSIVo;
import com.bna.smile.model.domaineguichet.service.GuichetService;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class MAJNSIActelTrt extends Traitement {

	private BatchMAJNSIActelFrame mainFrame;

	/**
	 * Consutructor
	 */
	public MAJNSIActelTrt() {
		super();
	}

	public MAJNSIActelTrt(BatchMAJNSIActelFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

	// à ne pas laisser en variable global

	List<Long> listAgencesMAJNSIStrc = null;
	List<AgencesMAJNSIVo> listAgencesMAJNSIStrcBtch = null;
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	BufferedWriter bufWriter = null;
	GuichetDAO guichetDao;
	int nbExcep = 0;
	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

	public IValueObject perform(IValueObject vo) {
		this.setVerifDomaine(false);
		this.setCroFlag(false);
		Context context = ContextHandler.getContext();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");

		String srcFile = "D:\\MAJNSISAUV\\MAJNSIActel";
		String srcFileLog = "D:\\MAJNSISAUV\\MAJNSIActelLog" + formatter.format(new Date());
		String line = "";
		srcFile += formatter.format(new Date());
		try {

			FileWriter fileWriterResult = null;
			fileWriterResult = new FileWriter(srcFileLog, true);
			bufWriter = new BufferedWriter(fileWriterResult);

			guichetDao = (GuichetDAO) context.getBean("guichetDAO");

			// /*** traitement Batch
			listAgencesMAJNSIStrc = guichetDao.getListAgencesMajNsiActel();

			try {
				
				GuichetService guichetService = (GuichetService) context.getBean("guichetService");
				MAJNSIVo mAJNSIVo = new MAJNSIVo();
				mAJNSIVo.setListAgencesMAJNSIStrc(listAgencesMAJNSIStrc);
				mAJNSIVo.setMainFrameActel(mainFrame);
				mAJNSIVo = (MAJNSIVo) guichetService.MAJNSIActelFileCopy(mAJNSIVo);
			} catch (Exception e) {

				File f = new File(srcFile);
				f.deleteOnExit();
				logger.error("Error MAJNSI Actel file copying");

				bufWriter.write(" Error MAJNSI Actel file copying " + e.toString());
				bufWriter.newLine();

			}

			listAgencesMAJNSIStrcBtch = guichetDao.getListAgencesMajNsiActelStrcBtch();
			for (int i = 0; i < listAgencesMAJNSIStrcBtch.size(); i++) {
				SwingInfoVo infoVo = new SwingInfoVo();
				infoVo.setStructure("" + listAgencesMAJNSIStrcBtch.get(i).getJourneeStructureBatch()
						.getJourneeStructureBatchId().get_codStrcStrc());
				infoVo.setEtat(Constants.STATUT_EN_COURS_INSERT);
				infoVo.setDateComptable(formatDate.format(listAgencesMAJNSIStrcBtch.get(i).getJourneeStructureBatch()
						.getJourneeStructureBatchId().get_datJrnJrn()));
				mainFrame.addOrUpdateEtat(infoVo);
			}
			for (int i = 0; i < listAgencesMAJNSIStrcBtch.size(); i++) {

				listAgencesMAJNSIStrcBtch.get(i).getJourneeStructureBatch().setCodStatJsb(1L);
				BatchService batchService = (BatchService) context.getBean("batchService");
				JourneeStructureBatch journeeStructureBatch =
						(JourneeStructureBatch) batchService.updateJourneeStructureBatch(listAgencesMAJNSIStrcBtch.get(
								i).getJourneeStructureBatch());
				
				
				
				ICriteria criteriaAgVirNsi = searchEngine.createCriteria();
				IExpression expressionVirNsi = searchEngine.createExpression();
				criteriaAgVirNsi.add(expressionVirNsi.eq("structure.codStrcStrc", journeeStructureBatch
						.getJourneeStructureBatchId().get_codStrcStrc()));
				criteriaAgVirNsi.add(expressionVirNsi.eq("boolIns", 9L));
				
				criteriaAgVirNsi.addOrder(Order.asc("idTmpVir"));
				List listAgVirNsi = searchEngine.find(TmpBatchVirNSI.class, criteriaAgVirNsi);
				if (listAgVirNsi != null && listAgVirNsi.size() > 0) {
					for (Iterator it = listAgVirNsi.iterator(); it.hasNext();) {
						TmpBatchVirNSI tmpBatchVirNSI = (TmpBatchVirNSI) it.next();

						MAJNSIVo mAJNSIVo = new MAJNSIVo();
						mAJNSIVo.setLigne(tmpBatchVirNSI.getdataVir());
						mAJNSIVo.setListAgencesMAJNSI(listAgencesMAJNSIStrcBtch);
						mAJNSIVo.setBufWriter(bufWriter);
						mAJNSIVo.setTmpBatchVirNSI(tmpBatchVirNSI);
						GuichetService guichetService = (GuichetService) context.getBean("guichetService");
						try {
							mAJNSIVo = (MAJNSIVo) guichetService.MAJNSILine(mAJNSIVo);
						} catch (Exception e) {

							nbExcep++;
						}

					}
				}

				journeeStructureBatch.setDatCloJsb(new Date());
				journeeStructureBatch =
						(JourneeStructureBatch) batchService.updateJourneeStructureBatch(journeeStructureBatch);
				Structure strc = new Structure();
				strc.setCodStrcStrc(journeeStructureBatch.getJourneeStructureBatchId().get_codStrcStrc());
				gestionStatistique(journeeStructureBatch.getJourneeStructureBatchId().getDatJrnJrn(), strc,
						listAgencesMAJNSIStrcBtch.get(i).getNbTotalOperations(), listAgencesMAJNSIStrcBtch.get(i)
								.getNbOperationsPosi(), listAgencesMAJNSIStrcBtch.get(i).getNbOperationsInter(),
						listAgencesMAJNSIStrcBtch.get(i).getNbOperationsRej(), listAgencesMAJNSIStrcBtch.get(i)
								.getSommeOp());
				SwingInfoVo infoVo = new SwingInfoVo();
				infoVo.setStructure("" + journeeStructureBatch
						.getJourneeStructureBatchId().get_codStrcStrc());
				infoVo.setEtat(Constants.STATUT_EN_TERMINE);
				infoVo.setDateComptable(formatDate.format(journeeStructureBatch
						.getJourneeStructureBatchId().get_datJrnJrn()));
				String info=" Nombre total des opérations = " + listAgencesMAJNSIStrcBtch.get(i).getNbTotalOperations()
						+ "  ; Nombre des opérations comptes internes = " +  listAgencesMAJNSIStrcBtch.get(i).getNbOperationsInter()
						+ " ; Nombre des opérations positionnées  = " + listAgencesMAJNSIStrcBtch.get(i)
						.getNbOperationsPosi() + " ; Nombre des rejets = " + listAgencesMAJNSIStrcBtch.get(i).getNbOperationsRej()
						+ "  ;  pour la somme Credit de : " + (listAgencesMAJNSIStrcBtch.get(i)
								.getSommeOp()) + " Dinars"
				     + "  ;  pour la somme Debit de : " + (listAgencesMAJNSIStrcBtch.get(i)
						.getSommeOpDebit()) + " Dinars";
				infoVo.setInfo(info);
				mainFrame.getBtnExcuter().setEnabled(false);
				mainFrame.addOrUpdateEtat(infoVo);
			}

			if (nbExcep > 0) {
				Structure strc = new Structure();
				strc.setCodStrcStrc(949L);
				Date dateCpt = DateHandler.strToDate(formatDate.format(new Date()));
				gestionExceptionEndBatch(dateCpt, strc, nbExcep + "  exceptions Batch MAJ NSI ");
			}
			System.out.println("  ");
			System.out.println("  *************** Fin *****************");
			System.out.println("  ");
			bufWriter.close();

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans MAJNSI Actel: ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("MAJNSI");
			logger.error("Exception : ", e);
			vo.addError(erreur);
			Structure strc = new Structure();
			strc.setCodStrcStrc(949L);
			Date dateCpt = DateHandler.strToDate(formatDate.format(new Date()));
			gestionException(dateCpt, strc, e, " Last Ligne : " + line);
			try {
				bufWriter.close();
			} catch (Exception e2) {

			}
			// throw new RuntimeException(e);

		}
		return vo;
	}

	private void gestionException(Date dateOper, Structure agence, Exception e, String donnee) {

		try {
			bufWriter.write(agence.getCodStrcStrc() + " " + DateHandler.dateToStr(dateOper) + " " + e.toString()
					+ " ** " + donnee);
			bufWriter.newLine();
		} catch (IOException e1) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans MAJNSI-perf : ");
			text.append(e1.getMessage());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("MAJNSI-perf_fileLogExcep");
			logger.error("Exception : ", e1);
		}

	}

	private void gestionStatistique(Date dateOp, Structure agence, int nbOperation, int nbOperationPos,
			int nbOperationInt, int nbRejet, Long sommePositionne) {

		BatchStatPlacement batchStatPlacement = new BatchStatPlacement();
		batchStatPlacement.setCodEtatBats("V");
		batchStatPlacement.setDatSystBats(new Date());
		batchStatPlacement.setDatCompBats(dateOp);
		batchStatPlacement.setStructure(agence);
		BatchMetier batchMetier = new BatchMetier();
		batchMetier.setCodBatBmet(Constants.COD_BATCH_MAJNSI_Actel);
		batchStatPlacement.setBatchMetier(batchMetier);
		batchStatPlacement.setLibExtrBats(" Sucées de l’exécution : Nombre total des opérations = " + nbOperation
				+ "  ; Nombre des opérations comptes internes = " + nbOperationInt
				+ " ; Nombre des opérations positionnées  = " + nbOperationPos + " ; Nombre des rejets = " + nbRejet
				+ "  ;  pour la somme de : " + (sommePositionne) + " Dinars");
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchStatPlacement = (BatchStatPlacement) batchService.InsertBatchStatPlacement(batchStatPlacement);
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	private void gestionExceptionEndBatch(Date dateOper, Structure agence, String donnee) {

		BatchExeptionPlac batchExeptionPlac = new BatchExeptionPlac();
		batchExeptionPlac.setDatSystBate(new Date());
		batchExeptionPlac.setDatCompBate(dateOper);
		batchExeptionPlac.setStructure(agence);
		batchExeptionPlac.setLibTpbmBate("Exception Batch MAJ NSI Actel");
		batchExeptionPlac.setLibExpBate(" ** " + donnee);
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchExeptionPlac = (BatchExeptionPlac) batchService.InsertBatchExeptionPlac(batchExeptionPlac);
	}

}
