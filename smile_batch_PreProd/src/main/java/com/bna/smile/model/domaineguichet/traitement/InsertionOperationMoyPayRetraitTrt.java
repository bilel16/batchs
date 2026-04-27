package com.bna.smile.model.domaineguichet.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.bna.commun.model.Cro;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.service.ICrudService;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.CroDAO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class InsertionOperationMoyPayRetraitTrt extends Traitement {
	Context context = ContextHandler.getContext();

	// Working copy pour le retrait effet
	private Long tva = 0L, commission = 0l;
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	public IValueObject perform(IValueObject vo) {
this.setSecurityFlag(false);
		OperationMoyPay operationMoyPay = (OperationMoyPay) vo;
		try {
			// &&
			// !(operationMoyPay.getTache().getTacheId().getCodOperOper().toString().equals(Constants.COD_OPER_DEBLOC_CPT_DEC.toString()))
			if (operationMoyPay.getCodEtatOmp().equalsIgnoreCase(Constants.COD_VALIDATION)) {
				// insertion dans la table Operation_Moy_Pay
				// insertOperationMoyPayTrt.setSecurityFlag(false);

				if (operationMoyPay.getDetailOperMoyPaiements() != null
						&& operationMoyPay.getDetailOperMoyPaiements().size() > 0) {
					for (Iterator it = operationMoyPay.getDetailOperMoyPaiements().iterator(); it.hasNext();) {
						DetailOperMoyPaiement detailOperMoyPaiement = (DetailOperMoyPaiement) it.next();
						commission += detailOperMoyPaiement.getMontValDomp();
					}
				}
				tva = operationMoyPay.getMontTvaOmp();
				System.out.println("tva:" + tva);
				System.out.println("commission:" + commission);

				if (operationMoyPay.getTache().getTacheId().getCodOperOper()
						.equals(Constants.COD_OPER_RETRAIT_EFFET.longValue())) {
					// Ne pas inserer TVA et comm pour le retrait effet pour le detail de l'extrait (percues à la
					// caisse)
					operationMoyPay.setMontTvaOmp(0L);
					operationMoyPay.setDetailOperMoyPaiements(null);
				}

				//operationMoyPayInserer = (OperationMoyPay) insertOperationMoyPayTrt.exec(operationMoyPay);

				
					this.setCroFlag(true);

				
			}

			else {
				this.setCroFlag(false);
			}
			
			this.setCroFlag(false);
		 genCroTextt(operationMoyPay);
			return operationMoyPay;
		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans PecRetraitTrt : ");
			text.append(e.toString());
			erreur.setCode("200");
			e.printStackTrace();
			erreur.setDescription(text.toString());
			erreur.setKey("PecRetraitTrt");
			operationMoyPay.addError(erreur);
			// return operationMoyPayInserer;
			throw new RuntimeException();
		}
	}

	public void genCroTextt(ValueObject vo) {
		OperationMoyPay operationMoyPay = (OperationMoyPay) vo;
		Long operation = operationMoyPay.getTache().getOperation().getCodOperOper();
		/*
		 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
		 */

		this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));

		if (operation.equals(Constants.COD_OPER_RETRAIT_DEPL_RECU)) {
			this.setLibRefCro("smile.omp.retraitDeplaceEMI");
			this.setCodRefInter(operationMoyPay.getRefIns1Omp());
		} else if (operation.equals(Constants.COD_OPER_RETRAIT)) {
			this.setLibRefCro("smile.omp.retraitMemeAgence");
		} else if (operation.equals(Constants.COD_OPER_RETRAIT_EFFET)) {
			this.setLibRefCro("smile.omp.retraitEffetMemeAgence");
		} else if (operation.equals(Constants.COD_OPER_DEBLOC_CPT_DEC)) {
			this.setLibRefCro("smile.omp.retraitSuccession");
		} else if (operation.equals(Constants.COD_OPER_RECUP_BC_PLAC)) {
			this.setLibRefCro("smile.omp.retraitBC");
		}

		try {
			this.setDatValCom(operationMoyPay.getDatOperOmp());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setDatValCro(operationMoyPay.getDatValOmp());
		this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());

		this.setDatExecCro(new Date());
		this.setCodEtatCro(7);
		this.setCodeProduit(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().toString());
		this.setDateOperation(operationMoyPay.getDatOperOmp());
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		this.setHeureOperation(heureString);
		this.setTypeOperationCro("A");

		if (operationMoyPay.getTache().getOperation().getCodOperOper().equals(Constants.COD_OPER_RETRAIT_DEPL_RECU)) {
			this.setCodStrcImpt(operationMoyPay.getStructureInitiatrice().getCodStrcStrc());
			Operation op = new Operation();
			op.setCodOperOper(Constants.COD_OPER_RETRAIT_DEPL_EMIS);
			Tache tache = new Tache();
			TacheId id = new TacheId(Constants.COD_TACHE_RETRAIT_DEPL_EMIS, op.getCodOperOper());
			tache.setOperation(op);
			tache.setTacheId(id);

			this.setOperationId(op.getCodOperOper().toString());
			this.setCodTachTach(tache.getTacheId().getCodTachTach());
		} else if (operationMoyPay.getTache().getOperation().getCodOperOper().equals(Constants.COD_OPER_RETRAIT_EFFET)) {
			this.setCodStrcImpt(operationMoyPay.getStructureInitiatrice().getCodStrcStrc());
			Operation op = new Operation();
			op.setCodOperOper(Constants.COD_OPER_RETRAIT_EFFET);
			Tache tache = new Tache();
			TacheId id = new TacheId(Constants.COD_TACHE_EFFET, op.getCodOperOper());
			tache.setOperation(op);
			tache.setTacheId(id);

			this.setOperationId(op.getCodOperOper().toString());
			this.setCodTachTach(tache.getTacheId().getCodTachTach());
		}

		else if (operationMoyPay.getTache().getOperation().getCodOperOper().equals(Constants.COD_OPER_RETRAIT)) {
			this.setCodStrcImpt(operationMoyPay.getStructureInitiatrice().getCodStrcStrc());
			Operation op = new Operation();
			op.setCodOperOper(Constants.COD_OPER_RETRAIT);
			Tache tache = new Tache();
			TacheId id = new TacheId(Constants.COD_TACHE_RETRAIT, op.getCodOperOper());
			tache.setOperation(op);
			tache.setTacheId(id);

			this.setOperationId(op.getCodOperOper().toString());
			this.setCodTachTach(tache.getTacheId().getCodTachTach());
		} else if (operationMoyPay.getTache().getOperation().getCodOperOper().equals(Constants.COD_OPER_DEBLOC_CPT_DEC)) {
			this.setCodStrcImpt(operationMoyPay.getStructureInitiatrice().getCodStrcStrc());

			Operation op = new Operation();
			op.setCodOperOper(Constants.COD_OPER_DEBLOC_CPT_DEC);
			Tache tache = new Tache();
			TacheId id = new TacheId(Constants.COD_TACH_DEBLOC_CPT_DEC_SUCCESSION, op.getCodOperOper());
			tache.setOperation(op);
			tache.setTacheId(id);

			this.setOperationId(op.getCodOperOper().toString());
			this.setCodTachTach(tache.getTacheId().getCodTachTach());
		} else if (operationMoyPay.getTache().getOperation().getCodOperOper().equals(Constants.COD_OPER_RECUP_BC_PLAC)) {
			this.setCodStrcImpt(operationMoyPay.getStructureInitiatrice().getCodStrcStrc());

			Operation op = new Operation();
			op.setCodOperOper(Constants.COD_OPER_RECUP_BC_PLAC);
			Tache tache = new Tache();
			TacheId id = new TacheId(Long.valueOf(2), Constants.COD_OPER_RECUP_BC_PLAC);
			tache.setOperation(op);
			tache.setTacheId(id);

			this.setOperationId(op.getCodOperOper().toString());
			this.setCodTachTach(tache.getTacheId().getCodTachTach());
		}

		this.setCodRefcOmp(operationMoyPay.getCodRefcOmp());
		
		this.setNumCinUser("9988");
		this.setCodTypUser("X");
		/*
		 * ------------------Garniture de la partie VARIABLE du CRO----------------------------------
		 */

		StringBuffer cro = new StringBuffer("");

		// contratClient
		// cro.append("COD_STRC_STRC=");
		cro.append("numCptBna=");
		cro.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc().toString(), '0',
				3));
		// cro.append("COD_PRD_PRD=");
		cro.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().toString(), '0', 4));
		// cro.append("NUM_CCPT_CCPT=");
		cro.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), '0',
				6) + ";");

		// Code de la devise
		if (operationMoyPay.getDevise().getCodDevDev() != null) {
			cro.append("COD_DEV_DEV=");
			cro.append(operationMoyPay.getDevise().getCodDevDev() + ";");
		}

		// Montant de la devise
		cro.append("MONT_DEV_OMP=");
		if (operationMoyPay.getMontDevOmp() != null && (!operationMoyPay.getMontDevOmp().equals(0))) {
			cro.append(operationMoyPay.getMontDevOmp() + ";");
		} else {
			cro.append("0;");
		}

		// Caisse de retrait
		cro.append("NUM_CAIS_CAIS=");
		cro.append(operationMoyPay.getNumeroCaisse() + ";");

		if (operation.equals(Constants.COD_OPER_RETRAIT) || operation.equals(Constants.COD_OPER_RECUP_BC_PLAC)) {
			// Montant retrait
			cro.append("MONT_DIN_OMP_RET=");
			cro.append(operationMoyPay.getMontDinOmp() + ";");

			// Montant cours de la devise
			/*
			 * cro.append("MONT_COUR_OMP="); if (operationMoyPay.getMontCourOmp() != null &&
			 * (!operationMoyPay.getMontCourOmp().equals(0))) {
			 * 
			 * cro.append(operationMoyPay.getMontCourOmp() + ";"); } else { cro.append("1;"); }
			 */

		} else if (operation.equals(Constants.COD_OPER_RETRAIT_DEPL_EMIS)
				|| operation.equals(Constants.COD_OPER_RETRAIT_DEPL_RECU)
				|| operation.equals(Constants.COD_OPER_RETRAIT_EFFET)) {// Cas de retrait Déplacé

			/*
			 * // Client Taxable ou non ExonerationTvaDAO exoTVA = new ExonerationTvaDAO(); ParamRechercheOpposition
			 * paramRecherche = new ParamRechercheOpposition(); paramRecherche.setDateDebutConsult(new Date());
			 * paramRecherche.setTypPceDemd(operationMoyPay.getTypePieceBeneficiaire().getCodTpceTpce());
			 * paramRecherche.setNumPceDemd(operationMoyPay.getNumPcebOmp());
			 * 
			 * cro.append("COD_TVA_CLT="); if (((PrimitiveVO) exoTVA.isClientExonereTVA(paramRecherche)).isVBool()) {
			 * cro.append("1;"); } else { cro.append("0;"); }
			 */

			cro.append("COD_TVA_CLT=");
			if (operationMoyPay.isClientExonereTVA()) {
				cro.append("1;");
			} else {
				cro.append("0;");
			}

			// Structure Initiatrice
			cro.append("COD_STRI_STRC=");
			cro.append(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString() + ";");

			// Structure Receptrice Décommentée à la date du 07/10/2013, champ présent ds la structure CRO
			cro.append("cod_strc_recep=");
			cro.append(operationMoyPay.getStructureReceptrice().getCodStrcStrc().toString() + ";");

			if (operationMoyPay.getMontCdinOmp() != null && (!operationMoyPay.getMontCdinOmp().equals(0))) {
				cro.append("MONT_CDIN_OMP=");
				cro.append(operationMoyPay.getMontCdinOmp() + ";");
			}

			// if (operationMoyPay.getDatValOmp() != null && (!operationMoyPay.getDatValOmp().equals(0))) {
			// formater = new SimpleDateFormat("dd/MM/yyyy");
			// String dateValeur = formater.format(operationMoyPay.getDatValOmp());
			// cro.append("DAT_VAL_OMP=");
			// cro.append(dateValeur + ";");
			// }

			long totalCommission = 0;
			// insertion des detailOperMoyPaiement (commissions)
			if (operationMoyPay.getDetailOperMoyPaiements() != null
					&& operationMoyPay.getDetailOperMoyPaiements().size() > 0) {
				for (Iterator it = operationMoyPay.getDetailOperMoyPaiements().iterator(); it.hasNext();) {
					DetailOperMoyPaiement detailOperMoyPaiement = (DetailOperMoyPaiement) it.next();
					cro.append("MONT_VAL_DOMP=");
					cro.append(detailOperMoyPaiement.getMontValDomp() + ";");
					totalCommission += detailOperMoyPaiement.getMontValDomp();
				}
			}

			// Montant TVA&&Comm
			cro.append("MONT_TVA_OMP=");
			if (operation.equals(Constants.COD_OPER_RETRAIT_EFFET)) {
				cro.append(tva + ";");
				// calculer la commission pour l'ulitisr plus bas
				totalCommission = commission;
				cro.append("MONT_VAL_DOMP=");
				cro.append(commission + ";");

			} else if (operationMoyPay.getMontTvaOmp() != null && (!operationMoyPay.getMontTvaOmp().equals(0))
					&& (!operation.equals(Constants.COD_OPER_RETRAIT_EFFET))) {
				cro.append(operationMoyPay.getMontTvaOmp() + ";");
			}
			// Montant retrait Net Commission et TVA
			// cro.append(operationMoyPay.getMontDinOmp() + ";");
			// since 25/09/2014
			cro.append("MONT_DIN_NET_COM_TVA=");
			if (!(operation.equals(Constants.COD_OPER_RETRAIT_EFFET))) {
				long montantNetComTva =
						operationMoyPay.getMontDinOmp() - (operationMoyPay.getMontTvaOmp() + totalCommission);
				cro.append(montantNetComTva + ";");

			} else {
				long montantNetComTva = operationMoyPay.getMontDinOmp() - (tva + totalCommission);
				cro.append(montantNetComTva + ";");
			}

			// Montant retrait + TVA
			// long totalAvecTVA = operationMoyPay.getMontDinOmp() + operationMoyPay.getMontTvaOmp();
			// since 25/09/2014
			if (!(operation.equals(Constants.COD_OPER_RETRAIT_EFFET))) {
				long totalAvecTVA = operationMoyPay.getMontDinOmp() - operationMoyPay.getMontTvaOmp();
				cro.append("MONT_DIN_NET_COM=");
				cro.append(totalAvecTVA + ";");
			} else {
				long totalAvecTVA = operationMoyPay.getMontDinOmp() - tva;
				cro.append("MONT_DIN_NET_COM=");
				cro.append(totalAvecTVA + ";");
			}

			// Montant Global
			// long montantGlobal = operationMoyPay.getMontDinOmp() + operationMoyPay.getMontTvaOmp() + totalCommission;
			// Since 25/09/2014
			long montantGlobal = operationMoyPay.getMontDinOmp();// + operationMoyPay.getMontTvaOmp() + totalCommission;
			cro.append("MONT_DIN_OMP_RET=");
			cro.append(montantGlobal + ";");

		}

		this.setCroText(cro.toString());
		 Cro croo= new Cro();
         CroDAO croDao = (CroDAO)context.getBean("croDAO");
         this.logger.debug("Contexxxxx :"+context);
         Long numSeqCro = croDao.getSequenceCro();                
         croo.setNumIdcroCro(numSeqCro);

         croo.setLibTextCro(this.getCroText());
         croo.setCodCodopCro(Long.parseLong(this.getOperationId()));
         
         croo.setDatOperCro(this.getDateOperation());
         croo.setDatExecCro(this.getDatExecCro());
         croo.setDatValCom(this.getDatValCom());
     
         croo.setCodProduitCro(Long.parseLong(this.getCodeProduit()));
         croo.setNumRefCro(this.getNumRefCro());
         croo.setLibRefCro(this.getLibRefCro());
         croo.setDatValCro(this.getDatValCro());
         croo.setCodStructure(Long.parseLong(this.getCodeStructInitiatrice()));
         croo.setCodRefcOmp(this.getCodRefcOmp());
         croo.setCodStrcImpt(this.getCodStrcImpt());
         croo.setCodEtatCro(this.getCodEtatCro());
         croo.setCodTachTach(this.getCodTachTach());
         croo.setCodTypUser(this.getCodTypUser());
         croo.setNumCinUser(this.getNumCinUser());
         croo.setTimeCro(this.getHeureOperation());
         croo.setCodTypOperCro(this.getTypeOperationCro());
         croo.setCodRefInter(this.getCodRefInter());                
         ICrudService curService =(ICrudService)context.getBean("CURService");    

         curService.create(croo);  
	}

	public void setCommission(Long commission) {
		this.commission = commission;
	}

	public Long getCommission() {
		return commission;
	}

	public Long getTva() {
		return tva;
	}

	public void setTva(Long tva) {
		this.tva = tva;
	}

	@Override
	protected void genCroText(ValueObject arg0) {
		// TODO Auto-generated method stub
		
	}

}
