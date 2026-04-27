package com.bna.smile.model.banqueAssurance.traitement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import com.bna.commun.model.Assurances;
import com.bna.commun.model.ContratAssuranceVoyage;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
//import com.bna.commun.model.DetailSessionCaisse;
import com.bna.commun.model.Devise;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.banqueAssurance.dao.AssuranceVoyageDAO;
import com.bna.smile.model.banqueAssurance.model.ParamAssuranceVoyage;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratCptByIdCmd;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecommun.traitement.GetContratCptByIdTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class InsertNewContratAssuranceVoyageTrt extends Traitement{
	public InsertNewContratAssuranceVoyageTrt() {
	}
	public Context context = ContextHandler.getContext();
	private String codRefInter =String.valueOf(0);
	
	public IValueObject perform(IValueObject vo) {

		ParamAssuranceVoyage paramAssuranceVoyage = (ParamAssuranceVoyage)vo;
		ContratAssuranceVoyage contratAssuranceVoyage = paramAssuranceVoyage.getContratAssuranceVoyageNew();
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
		ICriteria criteria = searchEngine.createCriteria();
		IExpression expression = searchEngine.createExpression();

		try{

			Context context = ContextHandler.getContext();
			CRUDservice crudService = (CRUDservice)context.getBean("crudservice");           
			crudService.create(contratAssuranceVoyage); 
			OperationMoyPay	operationMoyPay = new OperationMoyPay();
//			OperationMoyPay operationMoyPayAssur1= new OperationMoyPay();
//			OperationMoyPay operationMoyPayAssur2= new OperationMoyPay();
			AssuranceVoyageDAO assuranceVoyageDAO = (AssuranceVoyageDAO) ContextHandler.getContext().getBean("assuranceVoyageDAO");

			//			// Tache de creation Assurance Voyage sur Credit
			Tache tache = new Tache();
			TacheId tacheId = new TacheId();
			tacheId.setCodTachTach(Constants.TACHE_PRISE_EN_CHARGE);
			tacheId.setCodOperOper(Constants.COD_OPER_SOUSC_ASSUR_VOYAGE);

			tache.setTacheId(tacheId);
			operationMoyPay.setTache(tache);


			// Date Opération
			operationMoyPay.setDatOperOmp(assuranceVoyageDAO.getDateComptable());
//			operationMoyPayAssur1.setDatOperOmp(DateHandler.strToDate(paramAssuranceVoyage.getParamAgence().getDateComptable()));
//			operationMoyPayAssur2.setDatOperOmp(DateHandler.strToDate(paramAssuranceVoyage.getParamAgence().getDateComptable()));
			// personnel initiateur
			Personnel personnelInitiateur = null;
			personnelInitiateur = new Personnel();
			personnelInitiateur.setNumMatrUser("9999");

			operationMoyPay.setPersonnelInitiateur(personnelInitiateur);
//			operationMoyPayAssur1.setPersonnelInitiateur(personnelInitiateur);
//			operationMoyPayAssur2.setPersonnelInitiateur(personnelInitiateur);
			// personnel valideur

			operationMoyPay.setPersonnelValideur(personnelInitiateur);
//			operationMoyPayAssur1.setPersonnelValideur(personnelInitiateur);
//			operationMoyPayAssur2.setPersonnelValideur(personnelInitiateur);

			// Contrat Compte

			if(contratAssuranceVoyage.getContratCpt() != null) {
				System.out.println(contratAssuranceVoyage.getContratCpt().getContratCptId().getCodPrdPrd());
				operationMoyPay.setContratCpt(contratAssuranceVoyage.getContratCpt());
			}
//			operationMoyPayAssur1.setContratCpt(assuranceVoyageDAO.getContratCptAssur(paramAssuranceVoyage.getContratAssuranceVoyage().getNumCrtCassv()).get(0));
//			operationMoyPayAssur2.setContratCpt(assuranceVoyageDAO.getContratCptAssur(paramAssuranceVoyage.getContratAssuranceVoyage().getNumCrtCassv()).get(0));
			// Devise

			Devise devise = new Devise(Long.valueOf("788"));
			operationMoyPay.setDevise(devise);
//			operationMoyPayAssur1.setDevise(devise);
//			operationMoyPayAssur2.setDevise(devise);

			// etat valider
			operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);
//			operationMoyPayAssur1.setCodEtatOmp(Constants.COD_VALIDATION);
//			operationMoyPayAssur2.setCodEtatOmp(Constants.COD_VALIDATION);

			// Sens de l'opération débit du contratClient
			operationMoyPay.setCodSensOmp(Constants.COD_SENS_DB);

			// Sens de l'opération crédit du contratAssurance
//			operationMoyPayAssur1.setCodSensOmp(Constants.COD_SENS_CR);
//			operationMoyPayAssur2.setCodSensOmp(Constants.COD_SENS_CR);

			// --------------------Structure Initiatrice---------------------------------//
			Structure structureInitiatrice = new Structure();
			structureInitiatrice.setCodStrcStrc(paramAssuranceVoyage.getStructure().getCodStrcStrc());
			operationMoyPay.setStructureInitiatrice(structureInitiatrice);
//			operationMoyPayAssur1.setStructureInitiatrice(structureInitiatrice);
//			operationMoyPayAssur2.setStructureInitiatrice(structureInitiatrice);

			// structure récéptrice
			Structure structureReceptrice = new Structure();

			String assurance = "";
			Assurances  codAss = (Assurances)searchEngine.get(Assurances.class, Long.valueOf(2002));
			assurance = codAss.getCodAssAss().toString();
			criteria.add(expression.eq("codAssAss",Long.valueOf(assurance)));
			List listContratAssurance = searchEngine.find(Assurances.class, criteria);
			Assurances contratCptAssurance = new Assurances();
			/*Contrat assurance*/
			if (listContratAssurance != null && listContratAssurance.size() > 0) {
				contratCptAssurance = (Assurances)listContratAssurance.get(0);
			}
			
			structureReceptrice.setCodStrcStrc(contratCptAssurance
					.getContratCpt().getContratCptId().getCodStrcStrc());
			System.out.println("IN assurance");
			operationMoyPay.setStructureReceptrice(structureReceptrice);
//			operationMoyPayAssur1.setStructureReceptrice(structureReceptrice);
//			operationMoyPayAssur2.setStructureReceptrice(structureReceptrice);

			// ref client
			operationMoyPay.setCodRefcOmp(contratAssuranceVoyage.getNumCrtCassv().toString());
			operationMoyPay.setCodRefbOmp(contratAssuranceVoyage.getNumCrtCassv().toString());

//			operationMoyPayAssur1.setCodRefcOmp(contratAssuranceVoyage.getNumCrtCassv().toString());
//			operationMoyPayAssur1.setCodRefbOmp(contratAssuranceVoyage.getNumCrtCassv().toString()+" MNT_PRIME");
//
//			operationMoyPayAssur2.setCodRefcOmp(contratAssuranceVoyage.getNumCrtCassv().toString());
//			operationMoyPayAssur2.setCodRefbOmp(contratAssuranceVoyage.getNumCrtCassv().toString()+" MNT_RETSOURCE");
			// demandeur
			operationMoyPay.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);
			operationMoyPay.setTypePieceDemandeur(new TypePiece(Long.valueOf("3"),"PASSEPORT","PAS"));
			operationMoyPay.setNumPcedOmp(contratAssuranceVoyage.getNumPasseportCassv());
			operationMoyPay.setNomNomdOmp(contratAssuranceVoyage.getNomBenfCassv());
			operationMoyPay.setNomPrndOmp(contratAssuranceVoyage.getPrnBenfCassv());

//			operationMoyPayAssur1.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);
//			operationMoyPayAssur1.setTypePieceDemandeur(new TypePiece(Long.valueOf("3"),"PASSEPORT","PAS"));
//			operationMoyPayAssur1.setNumPcedOmp(contratAssuranceVoyage.getNumPasseportCassv());
//			operationMoyPayAssur1.setNomNomdOmp(contratAssuranceVoyage.getNomBenfCassv());
//			operationMoyPayAssur1.setNomPrndOmp(contratAssuranceVoyage.getPrnBenfCassv());
//
//			operationMoyPayAssur2.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);
//			operationMoyPayAssur2.setTypePieceDemandeur(new TypePiece(Long.valueOf("3"),"PASSEPORT","PAS"));
//			operationMoyPayAssur2.setNumPcedOmp(contratAssuranceVoyage.getNumPasseportCassv());
//			operationMoyPayAssur2.setNomNomdOmp(contratAssuranceVoyage.getNomBenfCassv());
//			operationMoyPayAssur2.setNomPrndOmp(contratAssuranceVoyage.getPrnBenfCassv());

			// libelle

			operationMoyPay.setLibObjOpOmp("Renouvellement contrat annuel assurance voyage");
//			operationMoyPayAssur1.setLibObjOpOmp("Règlement assureur");
//			operationMoyPayAssur2.setLibObjOpOmp("Règlement assureur");

			// time prise en charge

			/*
			 * SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss"); String heureString =
			 * formater.format(paramAgence.getDateOp()); operationMoyPay.setTimePecOperation(heureString);
			 */

			// --------------------------------------------------------------//
			// ---------- Affecter les conditions de banque---- ---------------//
			// ------------------------------------------------------------//

			// Date Valeur 1982/286
			//    										List<String> palierChar = new ArrayList<String>();
			//    													if (contratAssuranceVoyage.getTarifAssuranceVoyage().getAssurances().getContratCpt().getContratCptId().getCodStrcStrc()
			//    															.equals(paramAssuranceVoyage.getParamAgence().getCodStrcStrc())) {
			//    														palierChar.add("41");
			//    													} else {
			//    														palierChar.add("42");
			//    													}
			//    										TraitementConditionBanque traitementConditionBanque;
			//    										TraitementConditionBanque traitementConditionBanqueAssur;
			//    											traitementConditionBanque =
			//    												executeCondBanque(String.valueOf(Constants.COD_OPER_ANNULATION_ASSUR_VOYAGE)
			//    														, palierChar,paramAssuranceVoyage);
			//    											traitementConditionBanqueAssur =
			//    													executeCondBanque(String.valueOf(Constants.COD_OPER_ANN_REGLEMENT_ASSUREUR)
			//    															, palierChar,paramAssuranceVoyage);

			//    						operationMoyPay.setDatValOmp(DateHandler.strToDate(traitementConditionBanque.getDatevaleur()));
			//    						operationMoyPayAssur1.setDatValOmp(DateHandler.strToDate(traitementConditionBanqueAssur.getDatevaleur()));
			//    						operationMoyPayAssur2.setDatValOmp(DateHandler.strToDate(traitementConditionBanqueAssur.getDatevaleur()));

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			operationMoyPay.setDatValOmp(assuranceVoyageDAO.getDateComptable());
//			operationMoyPayAssur1.setDatValOmp(sdf.parse(paramAssuranceVoyage.getParamAgence().getDateComptable()));
//			operationMoyPayAssur2.setDatValOmp(sdf.parse(paramAssuranceVoyage.getParamAgence().getDateComptable()));
			//			operationMoyPay2.setDatValOmp(sdf.parse(paramAgence.getDateComptable()));
			// }
			contratAssuranceVoyage =(ContratAssuranceVoyage) searchEngine.get(ContratAssuranceVoyage.class, contratAssuranceVoyage.getNumCrtCassv());
			
			//Hibernate.initialize(contratAssuranceVoyage);
			operationMoyPay.setContratCpt(contratAssuranceVoyage.getContratCpt());
			if(true) {
				if (operationMoyPay.getContratCpt().getMontSoldCcpt() != null) {

					ContratCpt contratCpt = new ContratCpt();
					GetContratCptByIdCmd cmd = new GetContratCptByIdCmd();
					contratCpt = (ContratCpt) cmd.execute(operationMoyPay.getContratCpt());
					// Solde Client avant l'opération
					operationMoyPay.setMontSoldCcpt(contratCpt.getMontSoldCcpt());
					// Solde Client aprés l'opération
//					if(paramAssuranceVoyage.isPersonnel()) {
//						operationMoyPay.setMontApreOmp(contratCpt.getMontSoldCcpt() - contratAssuranceVoyage.getTarifAssuranceVoyage()
//								.getPrmTotTassv());
//						operationMoyPay.setMontDinOmp(contratAssuranceVoyage.getTarifAssuranceVoyage()
//								.getPrmTotTassv());
//					} else {
					operationMoyPay.setMontApreOmp(contratCpt.getMontSoldCcpt() - contratAssuranceVoyage.getMntPrcomCassv());
					operationMoyPay.setMontDinOmp(contratAssuranceVoyage.getMntPrcomCassv());
//					}
				} else {
					operationMoyPay.setMontApreOmp(contratAssuranceVoyage.getMntPrcomCassv());
					operationMoyPay.setMontDinOmp(contratAssuranceVoyage.getMntPrcomCassv());
				}
			}
			if(contratAssuranceVoyage.getCodEtatCassv().equals(Constants.COD_VALIDATION)){


				// MAJ etat solde du compte client------------------------
			
				if(contratAssuranceVoyage.getContratCpt() != null) {
					UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
					ContratCptSold contratCptSold = new ContratCptSold();
					ContratCptSold contratCptSoldAssur = new ContratCptSold();
					InsertOperationMoyPayTrt insertOperationMoyPayTrt=new InsertOperationMoyPayTrt();
					GetContratCptByIdTrt getContratCpt = new GetContratCptByIdTrt();
					// ----------------------------------------------------------------//
					// --------- Recherche du contrat client pour savoir le solde reel--------//
					// ----------------------------------------------------------------//
					ContratCpt contratCptReel = new ContratCpt();
					ContratCptId contratCptIdReel = new ContratCptId();
					contratCptIdReel = operationMoyPay.getContratCpt().getContratCptId();
					contratCptReel.setContratCptId(contratCptIdReel);
					contratCptReel = (ContratCpt) getContratCpt.exec(contratCptReel);

					operationMoyPay=(OperationMoyPay) insertOperationMoyPayTrt.exec(operationMoyPay);  
					contratCptSold.setContratCpt(contratCptReel);
					contratCptSold.setSens("D");
					contratCptSold.setSolde(operationMoyPay.getMontDinOmp());
					updateSoldTrt.setSecurityFlag(false);
					updateSoldTrt.exec(contratCptSold);
					paramAssuranceVoyage.setOperationMoyPayCr(operationMoyPay);
				}

				if(!paramAssuranceVoyage.getStructure().getCodStrcStrc().equals(contratCptAssurance.getContratCpt().getContratCptId().getCodStrcStrc()))
					codRefInter =
					new GenerateReferenceInterSiege().getRISWithUpdate(contratAssuranceVoyage.getCodStrCassv(),
							assuranceVoyageDAO.getDateComptable());

				System.out.println(operationMoyPay.getCodSensOmp());
				Operation operation = new Operation();
				operation.setCodOperOper(tacheId.getCodOperOper());
				paramAssuranceVoyage.setOperation3(operation);
				paramAssuranceVoyage.setTacheCreation(tache);
				this.setCroFlag(true);
			}
			ReglementAssurVoyageTrt reglementAssurVoyageTrt = new ReglementAssurVoyageTrt();
			reglementAssurVoyageTrt.exec(paramAssuranceVoyage);
		}catch(Exception e){
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text =  new StringBuffer("Erreur dans InsertNewContratAssuranceVoyageTrt : ");
			text.append(e.toString());
			erreur.setCode("500");
			erreur.setDescription(text.toString());
			erreur.setKey("InsertNewContratAssuranceVoyageTrt");
			logger.error("Exception : ",e);  
			throw new RuntimeException(e);
		}
		return (paramAssuranceVoyage); 
	}

	public TraitementConditionBanque executeCondBanque(String codeOperation, List<String> palierChar,ParamAssuranceVoyage paramAssuranceVoyage) {

		TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();
		ContratCpt contratCpt = paramAssuranceVoyage.getContratAssuranceVoyage().getContratCpt();
		try {

			// traitementConditionBanque.setCodOperOper("");
			// traitementConditionBanque.setCodPrdPrd(Constants.COD_PRODUIT_VIREMENT_PERMANENT + "");
			traitementConditionBanque.setCodOperOper(codeOperation);
			traitementConditionBanque.setCodPrdPrd(contratCpt.getContratCptId().getCodPrdPrd().toString());
			traitementConditionBanque.setNumCcptCcpt(contratCpt.getContratCptId().getNumCcptCcpt().toString());
			traitementConditionBanque.setCodStrcStrc(contratCpt.getContratCptId().getCodStrcStrc().toString());
			traitementConditionBanque.setCodPrdCpt(contratCpt.getContratCptId().getCodPrdPrd().toString());
			traitementConditionBanque.setCodTpceTpce(contratCpt.getClient().getPersonne().getTypePiece()
					.getCodTpceTpce().toString());
			traitementConditionBanque.setNumPcePers(contratCpt.getClient().getPersonne().getNumPcePers());

			traitementConditionBanque.setPalierChar(new ArrayList<String>(palierChar));
			traitementConditionBanque.setDateReference(paramAssuranceVoyage.getParamAgence().getDateComptable());
			traitementConditionBanque.getCB();
			System.out.println("traitementConditionBanquetraitementConditionBanque"
					+ traitementConditionBanque.getDatevaleur());

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("veuillez transmettre ce message à l'équipe informatique: ");
			text.append("Exception au niveau de l'agence:");
			text.append(paramAssuranceVoyage.getParamAgence().getCodStrcStrc());
			text.append(".Exception Cond Bq:");
			text.append(e.getMessage());
			erreur.setCode("500");
			erreur.setDescription(text.toString());
			logger.error(e);
			throw new RuntimeException(e.getMessage());
		}
		return traitementConditionBanque;
	}


public void genCroText(ValueObject vo) {
	
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	ICriteria criteria = searchEngine.createCriteria();
	IExpression expression = searchEngine.createExpression();

//	logger.info("starting CreationCROPositionVirementTrt method ");

	ParamAssuranceVoyage paramAssuranceVoyage = (ParamAssuranceVoyage) vo;
    ContratAssuranceVoyage contratAssuranceVoyage = paramAssuranceVoyage.getContratAssuranceVoyageNew();
	OperationMoyPay operationMoyPay = new OperationMoyPay();
	Tache tache = new Tache();
	tache = paramAssuranceVoyage.getTacheCreation();
	operationMoyPay = paramAssuranceVoyage.getOperationMoyPayCr();
	Operation operation = new Operation();
//	Operation operation2 = new Operation();
	operation = paramAssuranceVoyage.getOperation3();
//	operation2 = paramAssuranceVoyage.getOperation2();

	Produit produit = new Produit();
	produit.setCodPrdPrd(Long.valueOf("2307"));
	// produit = virementVo.getProduit();
//	boolean etatBenifMemeAgence = virementVo.isEtatBenifMemeAgence();
	/*
	 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
	 */
    if(operationMoyPay != null)
	this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
	this.setLibRefCro("RENOUVELLEMENT CONTRAT ANNUEL ASSURANCE VOYAGE");

	if (paramAssuranceVoyage.getStructure().getCodStrcStrc() != null
			&& paramAssuranceVoyage.getStructure().getCodStrcStrc().longValue() != 0) {
		this.setCodeStructInitiatrice(paramAssuranceVoyage.getStructure().getCodStrcStrc().toString());
	} else {
		this.setCodeStructInitiatrice("900");
	}
	
	String assurance = "";
	Assurances  codAss = (Assurances)searchEngine.get(Assurances.class, Long.valueOf(2002));
	assurance = codAss.getCodAssAss().toString();
	criteria.add(expression.eq("codAssAss",Long.valueOf(assurance)));
	List listContratAssurance = searchEngine.find(Assurances.class, criteria);
	Assurances contratCptAssurance = new Assurances();
	/*Contrat assurance*/
	if (listContratAssurance != null && listContratAssurance.size() > 0) {
		contratCptAssurance = (Assurances)listContratAssurance.get(0);
	}
	if(operationMoyPay != null)
	this.setCodStrcImpt(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc());
	
	this.setDatExecCro(new Date());
	this.setCodEtatCro(0);
	this.setCodeProduit(produit.getCodPrdPrd().toString());
	this.setOperationId(operation.getCodOperOper().toString());
	if(operationMoyPay != null)
	this.setDateOperation(operationMoyPay.getDatOperOmp());
	if(operationMoyPay != null)
	this.setDatValCro(operationMoyPay.getDatValOmp());
		this.setDatValCom(null);
	SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
	formater = new SimpleDateFormat("HH:mm:ss");
	String heureString = formater.format(new Date());
	this.setHeureOperation(heureString);
	this.setTypeOperationCro("O");
	this.setCodTachTach(tache.getTacheId().getCodTachTach());
	this.setCodRefcOmp(contratAssuranceVoyage.getNumCrtCassv().toString());
	Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	com.oxia.security.abc.model.Personnel user = null;
	if (obj instanceof UserDetails) {
		user = (com.oxia.security.abc.model.Personnel) obj;
	}
	this.setNumCinUser("9999");
	this.setCodTypUser("M");
	this.setCodRefInter(codRefInter);
	/*
	 * ------------------Garniture de la partie VARIABLE du CRO----------------------------------
	 */

	StringBuffer cro = new StringBuffer("");

	// Ref intersiege
	cro.append("code_ref_is=43;");
//	cro.append( ";");

	// *** Montant brut du virement reçu par client ****//
	cro.append("cod_strc_recep=");
	cro.append(contratCptAssurance.getContratCpt().getContratCptId().getCodStrcStrc().toString() + ";");	

	// *** Montant de la commission sur virement reçu ****//
	
	
	if(contratAssuranceVoyage.getTypeBenfCassv().equals("P")) {
		cro.append("ETAT_CLIENT_BNA=0;");
//		cro.append(new Long(1) + ";");
	} else {
		cro.append("ETAT_CLIENT_BNA=1;");
	}
	// *** Code référence inter siège ****//
	cro.append("IS=");
		cro.append(codRefInter + ";");
	// *** Montant brut de la commission ****//
//			if(paramAssuranceVoyage.getContratAssuranceVoyage().getContratCpt() != null 
//					&& paramAssuranceVoyage.getContratAssuranceVoyage().getContratCpt().getContratCptId() 
//					.getCodPrdPrd().equals(Long.valueOf("103"))) {
//				cro.append("MONT_COMM_ADH=0;");
//				
//			} else {
				cro.append("MONT_COMM_ADH=");
				cro.append(contratAssuranceVoyage.getComBanqueCassv()+ ";");
//			}

	// *** Prime total Assureur ****//
	cro.append("MONT_PRIME_ASS=");
	cro.append(contratAssuranceVoyage.getTarifAssuranceVoyage().getPrmTotTassv() + ";");

	// ***Prime tot+commission BNA****//
	cro.append("MONT_PRIME_COM_TOTAL_ASS=");
//	if(paramAssuranceVoyage.getContratAssuranceVoyage().getContratCpt() != null 
//			&& paramAssuranceVoyage.getContratAssuranceVoyage().getContratCpt().getContratCptId() 
//			.getCodPrdPrd().equals(Long.valueOf("103"))) {
		cro.append(contratAssuranceVoyage.getTarifAssuranceVoyage().getPrmTotTassv()+
				contratAssuranceVoyage.getComBanqueCassv()+";");
//	} else {
//		cro.append(paramAssuranceVoyage.getContratAssuranceVoyage().getTarifAssuranceVoyage().getPrmTotTassv()+
//				paramAssuranceVoyage.getContratAssuranceVoyage().getTarifAssuranceVoyage().getComBanqueTassv()+ ";");
//	}

		// Retenu à la source + prime tot assureur
		cro.append("MONT_PRIME_RET_SOURCE_ASS=");
//		Long montant = 0L;
//		if(paramAssuranceVoyage.getContratAssuranceVoyage().getContratCpt() != null 
//			&& paramAssuranceVoyage.getContratAssuranceVoyage().getContratCpt().getContratCptId() 
//			.getCodPrdPrd().equals(Long.valueOf("103")))
//			montant = contratAssuranceVoyage.
//				getTarifAssuranceVoyage().getPrmTotTassv();
//		else 
//			montant = paramAssuranceVoyage.getContratAssuranceVoyage().
//			getTarifAssuranceVoyage().getPrmTotTassv()+((contratAssuranceVoyage.getTarifAssuranceVoyage().getComBanqueTassv()
//			*Constants.TAUX_DECLARATION_RETENU)/100);
		cro.append(contratAssuranceVoyage.getRetSourceCassv()+contratAssuranceVoyage.getTarifAssuranceVoyage().getPrmTotTassv() + ";");

	// *** Rtenu à la source ****//
//		Long reteunu = 0L;
//		if(contratAssuranceVoyage.getContratCpt() != null 
//				&& paramAssuranceVoyage.getContratAssuranceVoyage().getContratCpt().getContratCptId() 
//				.getCodPrdPrd().equals(Long.valueOf("103"))) {
//			cro.append("MONT_RET_SOURCE=0;");
//		} else {
//			reteunu = (contratAssuranceVoyage.getTarifAssuranceVoyage().getComBanqueTassv()
//					*Constants.TAUX_DECLARATION_RETENU)/100;
			cro.append(contratAssuranceVoyage.getRetSourceCassv() + ";");
//		}

	// *** Numéro du contrat ****//
	cro.append("NUM_ADH_ASS=");
		cro.append(contratAssuranceVoyage.getNumCrtCassv() + ";");

	// *** Numéro de la caisse ****//
			cro.append("NUM_CAIS_CAIS=null;");	
	

	// *** Numéro du contratCpt client asureur ****//
	if(contratAssuranceVoyage.getContratCpt() == null) {
		cro.append("numCptBna=null;");	
	} if(contratAssuranceVoyage.getContratCpt() != null) {
	cro.append("numCptBna=");
	cro.append(contratAssuranceVoyage.getContratCpt().getContratCptId().getCompteClient().replace(" ", "")+ ";");
	}
	// *** Organisme assureur ****//
	cro.append("ORG_ADH_ASS=");
	cro.append(contratAssuranceVoyage.getTarifAssuranceVoyage().getAssurances().getLibAssAss() + ";");

	this.setCroText(cro.toString());


}
}
