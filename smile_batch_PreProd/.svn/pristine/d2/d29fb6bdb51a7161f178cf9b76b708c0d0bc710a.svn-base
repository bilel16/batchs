package com.bna.smile.model.banqueAssurance.traitement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.criterion.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bna.commun.model.Assurances;
import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.ContratAssuranceVoyage;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailAssuranceVoyage;
import com.bna.commun.model.JourneeStructureBatch;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TarifAssuranceVoyage;
import com.bna.commun.model.TmpBatchVirNSI;
import com.bna.commun.model.TraceAssuranceVoyage;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.batch.test.BatchMAJNSIFrame;
import com.bna.smile.batch.test.RenouvellementAssuranceVoyageFrame;
import com.bna.smile.model.banqueAssurance.commande.InsertDetailContratAssuranceVoyageCmd;
import com.bna.smile.model.banqueAssurance.dao.AssuranceVoyageDAO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.domaineguichet.dao.GuichetDAO;
import com.bna.smile.model.domaineguichet.model.AgencesMAJNSIVo;
import com.bna.smile.model.banqueAssurance.model.ParamAssuranceVoyage;
import com.bna.smile.model.domaineguichet.model.MAJNSIVo;
import com.bna.smile.model.domaineguichet.model.MvtDevise;
import com.bna.smile.model.domaineguichet.service.GuichetService;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.bna.smile.model.prelevement.dao.PrelevementDAO;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.bna.smile.web.commun.model.ParamAgence;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

import oracle.oc4j.admin.management.mbeans.Constant;

public class RenouvellementAssuranceVoyageTrt extends Traitement {

	private RenouvellementAssuranceVoyageFrame mainFrame;
	private Long numSeq;
	private String numCrt;
	

	/**
	 * Consutructor
	 */
	public RenouvellementAssuranceVoyageTrt() {
		super();
	}

	public RenouvellementAssuranceVoyageTrt(RenouvellementAssuranceVoyageFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
	}

	Context context = ContextHandler.getContext();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

//			dateOperOmp = sdf1.parse(paramAgence.getDateComptable());
	
	

	// à ne pas laisser en variable global

	List<Long> listAgencesMAJNSIStrc = null;
	List<MvtDevise> mvtDevises = null;
	List<AgencesMAJNSIVo> listAgencesMAJNSIStrcBtch = null;

	BufferedWriter bufWriter = null;
	GuichetDAO guichetDao;
	int nbExcep = 0;
	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
	AgencesMAJNSIVo agNonNsi= new AgencesMAJNSIVo();

	public IValueObject perform(IValueObject vo) {
		ParamAssuranceVoyage paramAssuranceVoyage = (ParamAssuranceVoyage)vo;
		ContratAssuranceVoyage contratAssuranceVoyage = paramAssuranceVoyage.getContratAssuranceVoyageNew();
		List<DetailAssuranceVoyage> details = paramAssuranceVoyage.getDetailsAssuranceVoyages();
		try {
			
			
			ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
//			paramAgence.getCodStrcStrc();
//			String num = "";
			AssuranceVoyageDAO assuranceVoyageDAO = (AssuranceVoyageDAO) context.getBean("assuranceVoyageDAO");
			this.numSeq = assuranceVoyageDAO.getSequenceContratAssuranceVoyage();
			String num = StrHandler.lpad(this.numSeq.toString(), '0', 9);
			String assurance = "";
			Assurances  codAss = (Assurances)searchEngine.get(Assurances.class, Long.valueOf(2002));
			assurance = codAss.getCodAssAss().toString();

//			numCrt = "120"+assurance+num;
//		for (ContratAssuranceVoyage contratAssuranceVoyage : result) {
			String codStrc = contratAssuranceVoyage.getNumCrtCassv().substring(0, 3);
//		    contratAssuranceVoyage = result.get(0);
//			System.out.println("NOM"+contratAssuranceVoyage.getNomBenfCassv());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//			Calendar c1 = new GregorianCalendar();
//			Calendar c2 = new GregorianCalendar();
//			c1.setTime(new Date());
//			c1.add(Calendar.DATE, -1);
//			c2.setTime(contratAssuranceVoyage.getDateFinCassv());
//			String dateRetourStr = sdf.format(c1.getTime()); 
//			String dateFinStr = sdf.format(c2.getTime());
//			Date dateRetour = sdf.parse(dateRetourStr);
//			Date dateFin = sdf.parse(dateFinStr);
//			System.out.println("dateRetour "+dateRetour);
//			System.out.println("dateFin "+dateFin);
//			System.out.println(contratAssuranceVoyage.getDateFinCassv().equals(dateRetour));
//			if(dateFin.equals(dateRetour))
//				contratAssuranceVoyage.setCodEtatCassv(Constants.COD_ASSUR_VOYAGE_RESILIE);
////			contratAssuranceVoyage = (ContratAssuranceVoyage)searchEngine.get(ContratAssuranceVoyage.class, contratAssuranceVoyage.getNumCrtCassv());
//			crudService.update(contratAssuranceVoyage);
//			System.out.println(contratAssuranceVoyage.getCodEtatCassv());
			numCrt = codStrc+assurance+num;
			if(contratAssuranceVoyage.getTarifAssuranceVoyage().getCodTassTassv().equals(Long.valueOf("12"))) {
				for(DetailAssuranceVoyage detailAssuranceVoyage : details) {
					InsertDetailContratAssuranceVoyageCmd insertDetailContratAssuranceVoyageCmd =
							new InsertDetailContratAssuranceVoyageCmd();
					DetailAssuranceVoyage newDetail = new DetailAssuranceVoyage();
					Long numSeqDetailContrat = assuranceVoyageDAO.getSequenceDetailContratAssuranceVoyage();
					String numDetailContrat = codStrc+assurance+numSeqDetailContrat.toString();
					newDetail.setNumCrtDassv(numCrt);
					newDetail.setNumPasscrtDassv(contratAssuranceVoyage.getNumPasseportCassv().toString());
					newDetail.setNumSeqDassv(Long.valueOf(numDetailContrat));
					newDetail.setNomBenfDassv(detailAssuranceVoyage.getNomBenfDassv());
					newDetail.setPrnBenfDassv(detailAssuranceVoyage.getPrnBenfDassv());
					newDetail.setDateNaisDassv(detailAssuranceVoyage.getDateNaisDassv());
					newDetail.setNumPasseportDassv(detailAssuranceVoyage.getNumPasscrtDassv());

					newDetail =
							(DetailAssuranceVoyage) insertDetailContratAssuranceVoyageCmd.execute(newDetail);
				}
			}
			contratAssuranceVoyage.setNumCrtCassv(numCrt);
			contratAssuranceVoyage.setDateDebCassv(new Date());
			Calendar c3 = new GregorianCalendar();
			c3.setTime(new Date());
			c3.add(Calendar.YEAR, 1);
			c3.add(Calendar.DATE, -1);
			String dateNouvRetourStr = sdf.format(c3.getTime()); 
			Date dateNouvRetour = sdf.parse(dateNouvRetourStr);
			contratAssuranceVoyage.setDateCrtCassv(new Date());
			contratAssuranceVoyage.setDateFinCassv(dateNouvRetour);
			contratAssuranceVoyage.setCodEtatCassv(Constants.COD_VALIDATION);
//			crudService.create(contratAssuranceVoyageNew);
			Structure structure = new Structure();
			structure.setCodStrcStrc(contratAssuranceVoyage.getCodStrCassv());
			paramAssuranceVoyage.setStructure(structure);
			if(contratAssuranceVoyage.getContratCpt() != null){
//				ContratCpt contratCpt = contratAssuranceVoyage.getContratCpt();
//				System.out.println("compte client"+contratCpt.getContratCptId().getCompteClient());
//				System.out.println("PROVISION"+contratCpt.getProvision(
//					DateHandler.strToDate(assuranceVoyageDAO.getDateComptableByStructure(structure))));
//				if(contratCpt.getProvision(
//						DateHandler.strToDate(assuranceVoyageDAO.getDateComptableByStructure(structure)))>=
//					contratAssuranceVoyage.getMntPrcomCassv()) {
			InsertNewContratAssuranceVoyageTrt insertContratAssuranceVoyageTrt = new InsertNewContratAssuranceVoyageTrt();
			paramAssuranceVoyage.setContratAssuranceVoyageNew(contratAssuranceVoyage);
			insertContratAssuranceVoyageTrt.exec(paramAssuranceVoyage);
			
			// Insertion dans la table trace
			Calendar cal = Calendar.getInstance();
			TraceAssuranceVoyage traceAssuranceVoyage2 = new TraceAssuranceVoyage();
			traceAssuranceVoyage2.setNumSeqTracev(assuranceVoyageDAO.getSequenceTraceAssurVoyage());
			traceAssuranceVoyage2.setDateTracev(cal.getTime());
			Operation operation2 = new Operation();
			operation2.setCodOperOper(Constants.COD_OPER_RENOUVELLEMENT_ASSUR_VOYAGE);
			traceAssuranceVoyage2.setOperation(operation2);
			
			Personnel personnel = null;
			personnel = new Personnel();
			personnel.setNumMatrUser("9999");
			
			traceAssuranceVoyage2.setPersonnel(personnel);
			traceAssuranceVoyage2.setContratAssuranceVoyage(contratAssuranceVoyage);
			crudService.create(traceAssuranceVoyage2);
			}
//			}
//		}

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans Renouvellement Assurance Voyage : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("Assurance Voyage");
			logger.error("Exception : ", e);
			vo.addError(erreur);
			Structure strc = new Structure();
			strc.setCodStrcStrc(949L);

		}
		return paramAssuranceVoyage;
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
			int nbOperationInt, int nbRejet, Long sommePositionne, Long sommeDebit) {

		BatchStatPlacement batchStatPlacement = new BatchStatPlacement();
		batchStatPlacement.setCodEtatBats("V");
		batchStatPlacement.setDatSystBats(new Date());
		batchStatPlacement.setDatCompBats(dateOp);
		batchStatPlacement.setStructure(agence);
		BatchMetier batchMetier = new BatchMetier();
		batchMetier.setCodBatBmet(Constants.COD_BATCH_MAJNSI);
		batchStatPlacement.setBatchMetier(batchMetier);
		batchStatPlacement.setLibExtrBats(" Sucées de l’exécution : Nombre total des opérations = " + nbOperation
				+ "  ; Nombre des opérations comptes internes = " + nbOperationInt
				+ " ; Nombre des opérations positionnées  = " + nbOperationPos + " ; Nombre des rejets = " + nbRejet
				+ "  ;  pour la somme Credit de : " + (sommePositionne) + " Dinars"
		         + "  ;  pour la somme Debit de : " + sommeDebit + " Dinars");
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
		batchExeptionPlac.setLibTpbmBate("Exception Batch MAJ NSI");
		batchExeptionPlac.setLibExpBate(" ** " + donnee);
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchExeptionPlac = (BatchExeptionPlac) batchService.InsertBatchExeptionPlac(batchExeptionPlac);
	}



}
