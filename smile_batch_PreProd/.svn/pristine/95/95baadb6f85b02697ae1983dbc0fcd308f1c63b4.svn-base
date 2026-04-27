package com.bna.smile.batch.moulinette;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import com.bna.commun.model.ContratAssuranceVoyage;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailAssuranceVoyage;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TraceAssuranceVoyage;
import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.batch.test.RenouvellementAssuranceVoyageFrame;
import com.bna.smile.model.banqueAssurance.commande.RenouvellementAssuranceVoyageCmd;
import com.bna.smile.model.banqueAssurance.dao.AssuranceVoyageDAO;
import com.bna.smile.model.banqueAssurance.model.ParamAssuranceVoyage;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.searchengine.SearchEngine;
import com.oxia.scheduling.quartz.core.AbstractJob;
//import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;

public class MoulinetteRenouvAssuranceVoyage extends AbstractJob {

	private static final Logger logger = Logger.getLogger(MoulinetteRenouvAssuranceVoyage.class);
	private RenouvellementAssuranceVoyageFrame mainFrame;
	private List<ContratAssuranceVoyage> contratAssuranceVoyages;

	/**
	 * Consutructor
	 */
	public MoulinetteRenouvAssuranceVoyage() {
		super();
	}

	public MoulinetteRenouvAssuranceVoyage(RenouvellementAssuranceVoyageFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
	}

	/**
	 * ^point d'entrée de la moulinette
	 */
	public void perform() {

		try {
//			fixerUser();
			ParamAssuranceVoyage paramAssuranceVoyage = new ParamAssuranceVoyage();
			Context context = ContextHandler.getContext();
			ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
			AssuranceVoyageDAO assuranceVoyageDAO = (AssuranceVoyageDAO) context.getBean("assuranceVoyageDAO");
			ICriteria criteriaResiliation = searchEngine.createCriteria();
			IExpression expressionResiliation = searchEngine.createExpression();
			
			ICriteria criteriaRenouvellement = searchEngine.createCriteria();
			IExpression expressionRenouvellemnt = searchEngine.createExpression();
			
			criteriaResiliation.add(expressionResiliation.eq("codEtatCassv","VAR"));
			List<ContratAssuranceVoyage> result1 =  searchEngine.find(ContratAssuranceVoyage.class,criteriaResiliation);
			
			criteriaRenouvellement.add(expressionRenouvellemnt.eq("codEtatCassv","V"));
			criteriaRenouvellement.add(expressionRenouvellemnt.or(expressionRenouvellemnt.eq("tarifAssuranceVoyage.codTassTassv",Long.valueOf("6")),
					expressionRenouvellemnt.eq("tarifAssuranceVoyage.codTassTassv",Long.valueOf("12"))));
			List<ContratAssuranceVoyage> result2 =  searchEngine.find(ContratAssuranceVoyage.class,criteriaRenouvellement);
			
			// GuichetService guichetService = (GuichetService) context.getBean("guichetService");
			// guichetService.MAJNSI(new ValueObject());
			for(ContratAssuranceVoyage contratAssuranceVoyage : result1) {
				CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
//				String codStrc = contratAssuranceVoyage.getNumCrtCassv().substring(0, 3);
//				contratAssuranceVoyage = result.get(0);
					System.out.println("NOM"+contratAssuranceVoyage.getNomBenfCassv());
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Calendar c1 = new GregorianCalendar();
					Calendar c2 = new GregorianCalendar();
					c1.setTime(new Date());
					c1.add(Calendar.DATE, -1);
					c2.setTime(contratAssuranceVoyage.getDateFinCassv());
					String dateRetourStr = sdf.format(c1.getTime()); 
					String dateFinStr = sdf.format(c2.getTime());
					Date dateRetour = sdf.parse(dateRetourStr);
					Date dateFin = sdf.parse(dateFinStr);
					System.out.println("dateRetour "+dateRetour);
					System.out.println("dateFin "+dateFin);
					System.out.println(contratAssuranceVoyage.getDateFinCassv().equals(dateRetour));
					if(dateFin.equals(dateRetour)) {
						contratAssuranceVoyage.setCodEtatCassv(Constants.COD_ASSUR_VOYAGE_RESILIE);
//					contratAssuranceVoyage = (ContratAssuranceVoyage)searchEngine.get(ContratAssuranceVoyage.class, contratAssuranceVoyage.getNumCrtCassv());
					crudService.update(contratAssuranceVoyage);
					System.out.println(contratAssuranceVoyage.getCodEtatCassv());
					
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
			} 
			for(ContratAssuranceVoyage contratAssuranceVoyage : result2) {
				ContratAssuranceVoyage contratAssuranceVoyageNew = contratAssuranceVoyage;
				if(contratAssuranceVoyage.getTarifAssuranceVoyage().getCodTassTassv().equals(Long.valueOf("12"))) {
					ICriteria criteriaDetails = searchEngine.createCriteria();
				IExpression expressionDetails = searchEngine.createExpression();
				criteriaDetails.add(expressionDetails.eq("numCrtDassv",contratAssuranceVoyage.getNumCrtCassv()));
				List<DetailAssuranceVoyage> result =  searchEngine.find(DetailAssuranceVoyage.class,criteriaDetails);
				
				paramAssuranceVoyage.setDetailsAssuranceVoyages(result);
				
				}
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Calendar c1 = new GregorianCalendar();
				Calendar c2 = new GregorianCalendar();
				c1.setTime(new Date());
				c1.add(Calendar.DATE, -1);
				c2.setTime(contratAssuranceVoyageNew.getDateFinCassv());
				String dateRetourStr = sdf.format(c1.getTime()); 
				String dateFinStr = sdf.format(c2.getTime());
				Date dateRetour = sdf.parse(dateRetourStr);
				Date dateFin = sdf.parse(dateFinStr);
				System.out.println("dateRetour "+dateRetour);
				System.out.println("dateFin "+dateFin);
				System.out.println(contratAssuranceVoyageNew.getDateFinCassv().equals(dateRetour));
				if(dateFin.equals(dateRetour)) {
					contratAssuranceVoyage.setCodEtatCassv(Constants.COD_ASSUR_VOYAGE_RESILIE);
					CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
					crudService.update(contratAssuranceVoyage);
					paramAssuranceVoyage.setContratAssuranceVoyageNew(contratAssuranceVoyageNew);
					System.out.println("dateRetourIN "+dateRetour);
					System.out.println("dateFinIN "+dateFin);
					Structure structure = new Structure();
					structure.setCodStrcStrc(contratAssuranceVoyage.getCodStrCassv());
					ContratCpt contratEligible = new ContratCpt();
					contratEligible=(ContratCpt)searchEngine.get(ContratCpt.class, contratAssuranceVoyage.getContratCpt().getContratCptId());
					if(contratEligible!=null && contratEligible.getProvision(
									DateHandler.strToDate(assuranceVoyageDAO.getDateComptableByStructure(structure)))>=contratAssuranceVoyage.getMntPrcomCassv()) {
			RenouvellementAssuranceVoyageCmd annulerContrAssVoyageCmd = new RenouvellementAssuranceVoyageCmd();
			paramAssuranceVoyage = (ParamAssuranceVoyage) annulerContrAssVoyageCmd.execute(paramAssuranceVoyage);
				}
				}
			}
			} catch (Exception e) {
			logger.fatal("**** exception *** : " + this.getClass() + " ----- " + e.getMessage());
			// throw new RuntimeException(e);
		}
	}

//	public void fixerUser() {
//		ContextCROHandler.setContext(ContextHandler.getContext());
//
//		Personnel user = new Personnel();
//		UserManager usermanager = (UserManager) ContextHandler.getContext().getBean("userManager");
//		user = usermanager.getUser("9999");
//
//		UsernamePasswordAuthenticationToken auth =
//				new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
//		auth.setDetails(user);
//		SecurityContextHolder.getContext().setAuthentication(auth);
//	}


}
