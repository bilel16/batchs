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
import com.bna.commun.model.Devise;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.service.CURService;
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
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecommun.traitement.GetContratCptByIdTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class ReglementAssurVoyageTrt extends Traitement{
	public ReglementAssurVoyageTrt() {
	}
	public Context context = ContextHandler.getContext();
	private String codRefInter =String.valueOf(0);
	
	protected IValueObject perform(IValueObject vo) throws Exception {
		
		ParamAssuranceVoyage paramAssuranceVoyage = (ParamAssuranceVoyage)vo; 
		ContratAssuranceVoyage contratAssuranceVoyage = paramAssuranceVoyage.getContratAssuranceVoyageNew();
		
		OperationMoyPay operationMoyPayAssur1= new OperationMoyPay();
		OperationMoyPay operationMoyPayAssur2= new OperationMoyPay();
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
		ICriteria criteria = searchEngine.createCriteria();
		IExpression expression = searchEngine.createExpression();
		CURService crudService = (CURService)context.getBean("CURService");

		try{
			AssuranceVoyageDAO assuranceVoyageDAO = (AssuranceVoyageDAO) ContextHandler.getContext().getBean("assuranceVoyageDAO");

			//Traitement d'annulation de l'ancien contrat
			//			operationMoyPay2 = new OperationMoyPay();
						//
						//			// Tache d'annulation Assurance Voyage sur Credit
						Tache tacheAssur = new Tache();
						TacheId tacheIdAssur = new TacheId();
						tacheIdAssur.setCodTachTach(Constants.TACHE_PRISE_EN_CHARGE);
						tacheIdAssur.setCodOperOper(Constants.COD_OPER_REGLEMENT_ASSUREUR);
						System.out.println("tacheIdAssur"+tacheIdAssur);
						
						tacheAssur.setTacheId(tacheIdAssur);
						operationMoyPayAssur1.setTache(tacheAssur);
						operationMoyPayAssur2.setTache(tacheAssur);
			
			
						// Date Opération
						operationMoyPayAssur1.setDatOperOmp(assuranceVoyageDAO.getDateComptable());
						operationMoyPayAssur2.setDatOperOmp(assuranceVoyageDAO.getDateComptable());
						// personnel initiateur
						Personnel personnelInitiateur = null;
						personnelInitiateur = new Personnel();
						personnelInitiateur.setNumMatrUser("9999");
			
						operationMoyPayAssur1.setPersonnelInitiateur(personnelInitiateur);
						operationMoyPayAssur2.setPersonnelInitiateur(personnelInitiateur);
						// personnel valideur
			
						operationMoyPayAssur1.setPersonnelValideur(personnelInitiateur);
						operationMoyPayAssur2.setPersonnelValideur(personnelInitiateur);

						// Contrat Compte

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
//						operationMoyPayAssur1.setContratCpt(assuranceVoyageDAO.getContratCptAssur(contratAssuranceVoyage.getNumCrtCassv()).get(0));
//						operationMoyPayAssur2.setContratCpt(assuranceVoyageDAO.getContratCptAssur(contratAssuranceVoyage.getNumCrtCassv()).get(0));
						operationMoyPayAssur1.setContratCpt(contratCptAssurance.getContratCpt());
						operationMoyPayAssur2.setContratCpt(contratCptAssurance.getContratCpt());
						// Devise
						
						Devise devise = new Devise(Long.valueOf("788"));
						operationMoyPayAssur1.setDevise(devise);
						operationMoyPayAssur2.setDevise(devise);
			
						// etat valider
						operationMoyPayAssur1.setCodEtatOmp(Constants.COD_VALIDATION);
						operationMoyPayAssur2.setCodEtatOmp(Constants.COD_VALIDATION);
			
						// Sens de l'opération crédit du contratClient
			
						// Sens de l'opération crédit du contratAssurance
						operationMoyPayAssur1.setCodSensOmp(Constants.COD_SENS_CR);
						operationMoyPayAssur2.setCodSensOmp(Constants.COD_SENS_CR);
			
						// --------------------Structure Initiatrice---------------------------------//
						Structure structureInitiatrice = new Structure();
						structureInitiatrice.setCodStrcStrc(paramAssuranceVoyage.getStructure().getCodStrcStrc());
						operationMoyPayAssur1.setStructureInitiatrice(structureInitiatrice);
						operationMoyPayAssur2.setStructureInitiatrice(structureInitiatrice);

						// structure récéptrice
						Structure structureReceptrice = new Structure();

						structureReceptrice.setCodStrcStrc(contratCptAssurance.getContratCpt().getContratCptId().getCodStrcStrc());
						System.out.println("IN assurance");
						operationMoyPayAssur1.setStructureReceptrice(structureReceptrice);
						operationMoyPayAssur2.setStructureReceptrice(structureReceptrice);

						// ref client

						operationMoyPayAssur1.setCodRefcOmp(contratAssuranceVoyage.getNumCrtCassv().toString());
						operationMoyPayAssur1.setCodRefbOmp(contratAssuranceVoyage.getNumCrtCassv().toString()+" MNT_PRIME");
						
						operationMoyPayAssur2.setCodRefcOmp(contratAssuranceVoyage.getNumCrtCassv().toString());
						operationMoyPayAssur2.setCodRefbOmp(contratAssuranceVoyage.getNumCrtCassv().toString()+" MNT_RETSOURCE");
						// demandeur
						operationMoyPayAssur1.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);
						operationMoyPayAssur1.setTypePieceDemandeur(new TypePiece(Long.valueOf("3"),"PASSEPORT","PAS"));
						operationMoyPayAssur1.setNumPcedOmp(contratAssuranceVoyage.getNumPasseportCassv());
						operationMoyPayAssur1.setNomNomdOmp(contratAssuranceVoyage.getNomBenfCassv());
						operationMoyPayAssur1.setNomPrndOmp(contratAssuranceVoyage.getPrnBenfCassv());
						
						operationMoyPayAssur2.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);
						operationMoyPayAssur2.setTypePieceDemandeur(new TypePiece(Long.valueOf("3"),"PASSEPORT","PAS"));
						operationMoyPayAssur2.setNumPcedOmp(contratAssuranceVoyage.getNumPasseportCassv());
						operationMoyPayAssur2.setNomNomdOmp(contratAssuranceVoyage.getNomBenfCassv());
						operationMoyPayAssur2.setNomPrndOmp(contratAssuranceVoyage.getPrnBenfCassv());
			
						// libelle
			
						operationMoyPayAssur1.setLibObjOpOmp("Règlement assureur");
						operationMoyPayAssur2.setLibObjOpOmp("Règlement assureur");
			
						// time prise en charge
			
						/*
						 * SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss"); String heureString =
						 * formater.format(paramAgence.getDateOp()); operationMoyPay.setTimePecOperation(heureString);
						 */
			
						// --------------------------------------------------------------//
						// ---------- Affecter les conditions de banque---- ---------------//
						// ------------------------------------------------------------//
			
						// Date Valeur 1982/286
//										List<String> palierChar = new ArrayList<String>();
//													if (contratAssuranceVoyage.getTarifAssuranceVoyage().getAssurances().getContratCpt().getContratCptId().getCodStrcStrc()
//															.equals(paramAssuranceVoyage.getParamAgence().getCodStrcStrc())) {
//														palierChar.add("41");
//													} else {
//														palierChar.add("42");
//													}
//										TraitementConditionBanque traitementConditionBanque;
//										TraitementConditionBanque traitementConditionBanqueAssur;
//											traitementConditionBanque =
//												executeCondBanque(String.valueOf(Constants.COD_OPER_ANNULATION_ASSUR_VOYAGE)
//														, palierChar,paramAssuranceVoyage);
//											traitementConditionBanqueAssur =
//													executeCondBanque(String.valueOf(Constants.COD_OPER_ANN_REGLEMENT_ASSUREUR)
//															, palierChar,paramAssuranceVoyage);
			
//						operationMoyPay.setDatValOmp(DateHandler.strToDate(traitementConditionBanque.getDatevaleur()));
//						operationMoyPayAssur1.setDatValOmp(DateHandler.strToDate(traitementConditionBanqueAssur.getDatevaleur()));
//						operationMoyPayAssur2.setDatValOmp(DateHandler.strToDate(traitementConditionBanqueAssur.getDatevaleur()));
						
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
						operationMoyPayAssur1.setDatValOmp(assuranceVoyageDAO.getDateComptable());
						operationMoyPayAssur2.setDatValOmp(assuranceVoyageDAO.getDateComptable());
			//			operationMoyPay2.setDatValOmp(sdf.parse(paramAgence.getDateComptable()));
						// }
						
						if(operationMoyPayAssur1.getContratCpt() != null) {
							if (operationMoyPayAssur1.getContratCpt().getMontSoldCcpt() != null) {
			
								ContratCpt contratCpt = new ContratCpt();
								GetContratCptByIdCmd cmd = new GetContratCptByIdCmd();
								contratCpt = (ContratCpt) cmd.execute(operationMoyPayAssur1.getContratCpt());
								// Solde Client avant l'opération
								operationMoyPayAssur1.setMontSoldCcpt(contratCpt.getMontSoldCcpt());
								// Solde Client aprés l'opération
								operationMoyPayAssur1.setMontApreOmp(contratCpt.getMontSoldCcpt() + contratAssuranceVoyage.getTarifAssuranceVoyage()
										.getPrmTotTassv());
								operationMoyPayAssur1.setMontDinOmp(contratAssuranceVoyage.getTarifAssuranceVoyage()
										.getPrmTotTassv());
							} else {
								operationMoyPayAssur1.setMontApreOmp(contratAssuranceVoyage.getTarifAssuranceVoyage()
										.getPrmTotTassv());
								operationMoyPayAssur1.setMontDinOmp(contratAssuranceVoyage.getTarifAssuranceVoyage()
										.getPrmTotTassv());
									}
								}
						
						if(operationMoyPayAssur2.getContratCpt() != null) {
							if (operationMoyPayAssur2.getContratCpt().getMontSoldCcpt() != null) {
			
								ContratCpt contratCpt = new ContratCpt();
								GetContratCptByIdCmd cmd = new GetContratCptByIdCmd();
								contratCpt = (ContratCpt) cmd.execute(operationMoyPayAssur2.getContratCpt());
								// Solde Client avant l'opération
								operationMoyPayAssur2.setMontSoldCcpt(operationMoyPayAssur1.getMontApreOmp());
								// Solde Client aprés l'opération
//								if(contratAssuranceVoyage.getContratCpt()!= null && contratAssuranceVoyage.getContratCpt().getContratCptId().getCodPrdPrd()
//										.equals(Long.valueOf("103"))) {
									operationMoyPayAssur2.setMontApreOmp(operationMoyPayAssur1.getMontApreOmp()+contratAssuranceVoyage.getRetSourceCassv());
								operationMoyPayAssur2.setMontDinOmp(contratAssuranceVoyage.getRetSourceCassv());
//								}else {
//									System.out.println("Montant après OMP "+operationMoyPayAssur1.getMontApreOmp());
//								operationMoyPayAssur2.setMontApreOmp(operationMoyPayAssur1.getMontApreOmp() -( 
//										(contratAssuranceVoyage.getTarifAssuranceVoyage().getComBanqueTassv() * Constants.TAUX_DECLARATION_RETENU)/100));
//								operationMoyPayAssur2.setMontDinOmp((
//										contratAssuranceVoyage.getTarifAssuranceVoyage().getComBanqueTassv() * Constants.TAUX_DECLARATION_RETENU)/100);
//								}
							} else {
//								if(contratAssuranceVoyage.getContratCpt()!= null && contratAssuranceVoyage.getContratCpt().getContratCptId().getCodPrdPrd()
//										.equals(Long.valueOf("103"))) {
									operationMoyPayAssur2.setMontApreOmp(contratAssuranceVoyage.getRetSourceCassv());
									operationMoyPayAssur2.setMontDinOmp(contratAssuranceVoyage.getRetSourceCassv());
//								}
//								else {
//								operationMoyPayAssur2.setMontApreOmp(
//										(contratAssuranceVoyage.getTarifAssuranceVoyage().getComBanqueTassv() * Constants.TAUX_DECLARATION_RETENU)/100);
//								operationMoyPayAssur2.setMontDinOmp(
//										(contratAssuranceVoyage.getTarifAssuranceVoyage().getComBanqueTassv() * Constants.TAUX_DECLARATION_RETENU)/100);
//								}
									}
								}
								// Solde Client aprés l'opération
								//					operationMoyPay.setMontApreOmp(contratAssuranceVoyage.getMntPrcomCassv());
			if(contratAssuranceVoyage.getCodEtatCassv().equals(Constants.COD_VALIDATION)){


				// MAJ etat solde du compte client------------------------
				UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
				ContratCptSold contratCptSoldAssur = new ContratCptSold();
				InsertOperationMoyPayTrt insertOperationMoyPayTrt=new InsertOperationMoyPayTrt();
				GetContratCptByIdTrt getContratCpt = new GetContratCptByIdTrt();
				if(operationMoyPayAssur1.getContratCpt() != null) {
					ContratCpt contratCptReelAssur = new ContratCpt();
					ContratCptId contratCptIdReelAssur = new ContratCptId();
					contratCptIdReelAssur = operationMoyPayAssur1.getContratCpt().getContratCptId();
					contratCptReelAssur.setContratCptId(contratCptIdReelAssur);
					contratCptReelAssur = (ContratCpt) getContratCpt.exec(contratCptReelAssur);

					operationMoyPayAssur1 = (OperationMoyPay) insertOperationMoyPayTrt.exec(operationMoyPayAssur1); 
					operationMoyPayAssur2 = (OperationMoyPay) insertOperationMoyPayTrt.exec(operationMoyPayAssur2);
					contratCptSoldAssur.setContratCpt(contratCptReelAssur);
					contratCptSoldAssur.setSens("C");
					contratCptSoldAssur.setSolde(operationMoyPayAssur1.getMontDinOmp()+operationMoyPayAssur2.getMontDinOmp());
					//contratCptSold.setSoldeDevise(montantBrutDevises);
					updateSoldTrt.setSecurityFlag(false);
					updateSoldTrt.exec(contratCptSoldAssur);
					paramAssuranceVoyage.setOperationMoyPayAssureur1(operationMoyPayAssur1);
					paramAssuranceVoyage.setOperationMoyPayAssureur2(operationMoyPayAssur2);

				}
				
				
				if(!paramAssuranceVoyage.getStructure().getCodStrcStrc().equals(contratCptAssurance.getContratCpt()
						.getContratCptId().getCodStrcStrc()))
					codRefInter =
					new GenerateReferenceInterSiege().getRISWithUpdate(contratAssuranceVoyage.getCodStrCassv(),
							assuranceVoyageDAO.getDateComptable());
				
//				MouvementGuichet mouvementGuichet = paramAssuranceVoyage.getMouvementGuichet() ;
//				if(mouvementGuichet != null) {
//					mouvementGuichet.setCodRefInter(codRefInter);
//					crudService.create(mouvementGuichet);
//					// 03. MAJ Details caisse
//					GetDetailSessionCaisseTrt getDetailSessionCaisseTrt = new GetDetailSessionCaisseTrt();
//					DetailSessionCaisse detailSessionCaisse = new DetailSessionCaisse();
//					SessionJrnCaisse sessionJrnCaisse = new SessionJrnCaisse();
//					sessionJrnCaisse.setNumSeqSjc(mouvementGuichet.getSessionJrnCaisse().getNumSeqSjc());
//					detailSessionCaisse.setSessionJrnCaisse(sessionJrnCaisse);
////					Devise devisemouv = new Devise();
////					devisemouv.setCodDevDev(mouvementGuichet.getDevise().getCodDevDev());
//					detailSessionCaisse.setDevise(devise);
//
//					Listes listes = (Listes) getDetailSessionCaisseTrt.exec(detailSessionCaisse);
//					List listDetailSessionCaisse = listes.getList();
//					detailSessionCaisse = (DetailSessionCaisse) listDetailSessionCaisse.get(0);
//					ParamGuichet paramGuichet = new ParamGuichet();
//					paramGuichet.setNumSeqSjc(mouvementGuichet.getSessionJrnCaisse().getNumSeqSjc());
//					// paramGuichet.setOperationMoyPay(operationMoyPayNew);
//					paramGuichet.setMontantSoldeEntree(Long.valueOf("0"));
//					paramGuichet.setMontantSoldeSortie(contratAssuranceVoyage.getMntPrcomCassv());
//					paramGuichet.setDetailSessionCaisse(detailSessionCaisse);
//					UpdateDetailSessionCaisseTrt updateDetailSessionCaisseTrt = new UpdateDetailSessionCaisseTrt();
//					// try {
//					paramGuichet.setMouvementGuichet(mouvementGuichet);
//					detailSessionCaisse = (DetailSessionCaisse) updateDetailSessionCaisseTrt.exec(paramGuichet);
//					System.out.println("solde detail caisse"+detailSessionCaisse.getSoldeInit());
//				}
				
//				Operation operation1 = new Operation();
				Operation operation = new Operation();
				operation.setCodOperOper(tacheIdAssur.getCodOperOper());
//				paramAssuranceVoyage.setOperation1(operation1);
				paramAssuranceVoyage.setOperation4(operation);
				this.setCroFlag(true);
			}
//			crudService.update(contratAssuranceVoyage);
			
			//Creation du nouveau Contrat
			
		}catch(Exception e){
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text =  new StringBuffer("Erreur dans AnnulerReglementAssurVoyageTrt : ");
			text.append(e.toString());
			erreur.setCode("500");
			erreur.setDescription(text.toString());
			erreur.setKey("AnnulerReglementAssurVoyageTrt");
			logger.error("Exception : ",e);  
			throw new RuntimeException(e);
		}
		return (paramAssuranceVoyage); 
	}
	
	public TraitementConditionBanque executeCondBanque(String codeOperation, List<String> palierChar,
			ParamAssuranceVoyage paramAssuranceVoyage) {

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
	protected void genCroText(ValueObject vo) {

//		logger.info("starting CreationCROPositionVirementTrt method ");

		ParamAssuranceVoyage paramAssuranceVoyage = (ParamAssuranceVoyage) vo;
//		MouvementGuichet mouvementGuichet = paramAssuranceVoyage.getMouvementGuichet();
//		ContratAssuranceVoyage contratAssuranceVoyage
		OperationMoyPay operationMoyPay = new OperationMoyPay();
//		OperationMoyPay operationMoyPayAssur1 = paramAssuranceVoyage.getOperationMoyPayAssureur1();
		operationMoyPay = paramAssuranceVoyage.getOperationMoyPayAssureur1();
//		Operation operation1 = new Operation();
		Operation operation = new Operation();
//		operation1 = paramAssuranceVoyage.getOperation1();
		operation = paramAssuranceVoyage.getOperation4();

		Produit produit = new Produit();
		produit.setCodPrdPrd(Long.valueOf("2307"));
		// produit = virementVo.getProduit();
//		boolean etatBenifMemeAgence = virementVo.isEtatBenifMemeAgence();
		/*
		 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
		 */

		this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
		this.setLibRefCro("REGLEMENT ASSUREUR VOYAGE");

		if (paramAssuranceVoyage.getStructure().getCodStrcStrc() != null
				&& paramAssuranceVoyage.getStructure().getCodStrcStrc().longValue() != 0) {
			this.setCodeStructInitiatrice(paramAssuranceVoyage.getStructure().getCodStrcStrc().toString());
		} else {
			this.setCodeStructInitiatrice("900");
		}
		this.setCodStrcImpt(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc());
		this.setDatExecCro(new Date());
		this.setCodEtatCro(0);
		this.setCodeProduit(produit.getCodPrdPrd().toString());
		this.setOperationId(operation.getCodOperOper().toString());
		this.setDateOperation(operationMoyPay.getDatOperOmp());
		this.setDatValCro(operationMoyPay.getDatValOmp());
		this.setDatValCom(null);
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		this.setHeureOperation(heureString);
		this.setTypeOperationCro("O");
		this.setCodTachTach(operationMoyPay.getTache().getTacheId().getCodTachTach());
		this.setCodRefcOmp(operationMoyPay.getCodRefcOmp());
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
//		cro.append( ";");

		// *** Structure receptrice****//
		cro.append("cod_strc_recep=");
		cro.append(operationMoyPay.getStructureReceptrice().getCodStrcStrc().toString() + ";");

		// *** Montant de la commission sur virement reçu ****//
		
		
		if(paramAssuranceVoyage.getContratAssuranceVoyageNew().getTypeBenfCassv().equals("P")) {
			cro.append("ETAT_CLIENT_BNA=0;");
//			cro.append(new Long(1) + ";");
		} else {
			cro.append("ETAT_CLIENT_BNA=1;");
		}
		// *** Code référence inter siège ****//
		cro.append("IS=");
			cro.append(codRefInter + ";");
		// *** Montant brut de la commission ****//
//				if(paramAssuranceVoyage.getContratAssuranceVoyage().getContratCpt() != null 
//						&& paramAssuranceVoyage.getContratAssuranceVoyage().getContratCpt().getContratCptId() 
//						.getCodPrdPrd().equals(Long.valueOf("103"))) {
//					cro.append("MONT_COMM_ADH=0;");
//					
//				} else {
					cro.append("MONT_COMM_ADH=");
					cro.append(paramAssuranceVoyage.getContratAssuranceVoyageNew().getComBanqueCassv()+ ";");
//				}

		// *** Montant de la prime tot assureur ****//
		cro.append("MONT_PRIME_ASS=");
		cro.append(paramAssuranceVoyage.getContratAssuranceVoyageNew().getTarifAssuranceVoyage().getPrmTotTassv() + ";");

		// *** Prime+commission ****//
		cro.append("MONT_PRIME_COM_TOTAL_ASS=");
//		if(paramAssuranceVoyage.getContratAssuranceVoyage().getContratCpt() != null 
//				&& paramAssuranceVoyage.getContratAssuranceVoyage().getContratCpt().getContratCptId() 
//				.getCodPrdPrd().equals(Long.valueOf("103"))) {
			cro.append(paramAssuranceVoyage.getContratAssuranceVoyageNew().getTarifAssuranceVoyage().getPrmTotTassv()+
					paramAssuranceVoyage.getContratAssuranceVoyageNew().getComBanqueCassv()+";");
//		} else {
//			cro.append(paramAssuranceVoyage.getContratAssuranceVoyage().getTarifAssuranceVoyage().getPrmTotTassv()+
//					paramAssuranceVoyage.getContratAssuranceVoyage().getTarifAssuranceVoyage().getComBanqueTassv()+ ";");
//		}

			// Prime+Retenu à la source
			cro.append("MONT_PRIME_RET_SOURCE_ASS=");
//			Long montant = 0L;
//			if(paramAssuranceVoyage.getContratAssuranceVoyage().getContratCpt() != null 
//				&& paramAssuranceVoyage.getContratAssuranceVoyage().getContratCpt().getContratCptId() 
//				.getCodPrdPrd().equals(Long.valueOf("103")))
//				montant = paramAssuranceVoyage.getContratAssuranceVoyage().
//					getTarifAssuranceVoyage().getPrmTotTassv();
//			else 
//			 montant = paramAssuranceVoyage.getContratAssuranceVoyage().
//				getTarifAssuranceVoyage().getPrmTotTassv()+((paramAssuranceVoyage.getContratAssuranceVoyage().getTarifAssuranceVoyage().getComBanqueTassv()
//				*Constants.TAUX_DECLARATION_RETENU)/100);
			cro.append(paramAssuranceVoyage.getContratAssuranceVoyageNew().getTarifAssuranceVoyage().getPrmTotTassv()
					+paramAssuranceVoyage.getContratAssuranceVoyageNew().getRetSourceCassv() + ";");

		// *** Rtenu à la source ****//
//			Long reteunu = 0L;
//			if(paramAssuranceVoyage.getContratAssuranceVoyage().getContratCpt() != null 
//					&& paramAssuranceVoyage.getContratAssuranceVoyage().getContratCpt().getContratCptId() 
//					.getCodPrdPrd().equals(Long.valueOf("103"))) {
				cro.append("MONT_RET_SOURCE=");
//			} else {
//				reteunu = (paramAssuranceVoyage.getContratAssuranceVoyage().getTarifAssuranceVoyage().getComBanqueTassv()
//						*Constants.TAUX_DECLARATION_RETENU)/100;
				cro.append(paramAssuranceVoyage.getContratAssuranceVoyageNew().getRetSourceCassv() + ";");
//			}

		// *** Numéro de l'adhésion ****//
		cro.append("NUM_ADH_ASS=");
			cro.append(paramAssuranceVoyage.getContratAssuranceVoyageNew().getNumCrtCassv() + ";");

		// *** Montant Cours BBE brut du virement reçu ****//
		cro.append("NUM_CAIS_CAIS=null;");
//		cro.append(paramAssuranceVoyage.getIdentifiantCaisse() + ";");

		// ***Num contratCpt assureur ****//
		cro.append("numCptBna=");
		cro.append(operationMoyPay.getContratCpt().getContratCptId().getCompteClient().replace(" ", "")+ ";");
		// *** Organisme assureur ****//
		cro.append("ORG_ADH_ASS=");
		cro.append(paramAssuranceVoyage.getContratAssuranceVoyageNew().getTarifAssuranceVoyage().getAssurances().getLibAssAss() + ";");

		this.setCroText(cro.toString());
	

	}
	
}
