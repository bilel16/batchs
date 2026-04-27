package com.bna.smile.model.assVieEpargneEtude.traitement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypeMoyenPaiement;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.InsertionOperationMoyPaySansCROTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.assVieEpargneEtude.dao.AssVieEpargneDAO;
import com.bna.smile.model.assVieEpargneEtude.model.ContratEpargneEtudeVo;
import com.bna.smile.model.assVieEpargneEtude.model.ListFaiezVo;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.logging.Log;

public class PrelevementDeuxiemeTrancheEpargneEtudeTrt extends Traitement {

	Log logger = new Log(PrelevementDeuxiemeTrancheEpargneEtudeTrt.class);

	Context context = ContextHandler.getContext();
	AssVieEpargneDAO assVieEpargneDAO = (AssVieEpargneDAO) context.getBean("assVieEpargneDAO");


	public IValueObject perform(IValueObject vo) {
		
		ListFaiezVo listContratFaiezVo = (ListFaiezVo) vo;
		List<ContratEpargneEtudeVo> list = listContratFaiezVo.getList();
		Calendar date = Calendar.getInstance();
		String sysdate = null;
		SimpleDateFormat formatDateFile = new SimpleDateFormat("dd/MM/yyyy");
		sysdate = formatDateFile.format(date.getTime());
		try {
			for (ContratEpargneEtudeVo contratFaiezVo : list) {
				OperationMoyPay operationMoyPayCredit = new OperationMoyPay();
				ContratCpt contratCptBen = null;

				// ------- Operation Moyen de Paiement Crédit

				Long codOperCredit = 0L;
				String libOperCredit = "";
				codOperCredit = 2253L;
				libOperCredit = "Règlement Assureur";
				// 00. setting obj operationMoyPay
				operationMoyPayCredit.setLibObjOpOmp(libOperCredit);
				
				Personnel personnelInit = new Personnel();
				
				personnelInit.setNumMatrUser("8888");
				personnelInit.setNumCinUser("8888");

				operationMoyPayCredit.setPersonnelInitiateur(personnelInit);;
				operationMoyPayCredit.setPersonnelValideur(personnelInit);
				contratCptBen = UtilCtr.getContratCptByRIB("03135120010100020120");

				// 02. setting structure initiatrice
				Structure structureInit = new Structure();
				structureInit.setCodStrcStrc(contratCptBen.getContratCptId().getCodStrcStrc());
				operationMoyPayCredit.setStructureInitiatrice(structureInit);

//				// 03. setting structure receptrice
				Structure structureRecep = new Structure();
				structureRecep.setCodStrcStrc(contratFaiezVo.getContratCpt().getContratCptId().getCodStrcStrc());
				operationMoyPayCredit.setStructureReceptrice(structureRecep);


				operationMoyPayCredit.setDevise(contratCptBen.getDevise());

				// 05. setting devise et montant
				operationMoyPayCredit.setMontDinOmp(contratFaiezVo.getCategorie().getMontTranch2Cat());

				operationMoyPayCredit.setMontApreOmp(contratCptBen.getMontSoldCcpt() + contratFaiezVo.getCategorie().getMontTranch2Cat());

				operationMoyPayCredit.setMontSoldCcpt(contratCptBen.getMontSoldCcpt());

				// 06. setting contrat compte
				operationMoyPayCredit.setContratCpt(contratCptBen);

				// 07. setting type demandeur (Titulaire, CoTitulaire,
				// Mandataire)
				operationMoyPayCredit.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);

				// 08. setting info Reference Operation //à completter
				operationMoyPayCredit.setCodRefbOmp(StrHandler.lpad(contratFaiezVo.getContratCpt().getContratCptId().getCodStrcStrc().toString(), '0',
						3)+StrHandler.lpad(contratFaiezVo.getContratCpt().getContratCptId().getCodPrdPrd().toString(), '0', 4)+StrHandler.lpad(contratFaiezVo.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), '0',
								6));
				operationMoyPayCredit.setCodRefcOmp("0");

				// 09. insertion du Donneur D'ordre
				TypePiece typePieceDemandeur = new TypePiece();
				typePieceDemandeur.setCodTpceTpce(
						Long.valueOf(contratFaiezVo.getCodTpceTpce()));
				operationMoyPayCredit.setTypePieceDemandeur(typePieceDemandeur);
				operationMoyPayCredit.setTypePieceBeneficiaire(typePieceDemandeur);
				operationMoyPayCredit.setNumPcedOmp(contratFaiezVo.getNumPcePers());
				operationMoyPayCredit.setNomNomdOmp(contratFaiezVo.getNomNomPers());
				operationMoyPayCredit.setNomPrndOmp(contratFaiezVo.getNomPrnPers());

				// 10 Preparing data of Operation and tache
				Operation oper = new Operation();
				oper.setCodOperOper(codOperCredit);
				Tache tache = new Tache();
				tache.setOperation(oper);
				TacheId tacheId = new TacheId();
				tacheId.setCodOperOper(codOperCredit);
				tacheId.setCodTachTach(1L);
				tache.setTacheId(tacheId);

				operationMoyPayCredit.setCodEtatOmp(Constants.COD_VALIDATION);
				operationMoyPayCredit.setTache(tache);

				// 11. setting date operation moyen paiement
				operationMoyPayCredit
						.setDatOperOmp(DateHandler.strToDate(sysdate));

				// 12. setting date valeur moyen paiement
				operationMoyPayCredit.setDatValOmp(CalanderHandler.GetNextWorkingDay(DateHandler.strToDate(sysdate)));

				// 13.1 setting date system moyen paiement
				operationMoyPayCredit.setDatSystOmp(new Date());

				// 14. setting sens operation

				operationMoyPayCredit.setCodSensOmp(Constants.COD_SENS_CR);

				// 15. setting montant tva
				operationMoyPayCredit.setMontTvaOmp(0L);
				operationMoyPayCredit.setMontCdinOmp(Double.valueOf("0"));

				// 20. Insertion motif operation
				operationMoyPayCredit.setLibMotfOmp(libOperCredit);

				// 02. insertion operation moyen paiement

				InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrtCredit = new InsertionOperationMoyPaySansCROTrt();
				operationMoyPayCredit = (OperationMoyPay) insertOperationMoyPaySansCROTrtCredit.exec(operationMoyPayCredit);

				logger.info("Operation Moy Pay done success:" + operationMoyPayCredit.getNumOperOmp());

				this.setCroFlag(true);

				/*********** Mise à jour solde ************/

				ContratCptSold contratCptSold = new ContratCptSold();
				UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
				contratCptSold.setContratCpt(contratCptBen);

				contratCptSold.setSens(Constants.COD_SENS_CR);

				// Checking Devise
				contratCptSold.setSolde(contratFaiezVo.getCategorie().getMontTranch2Cat());

				contratCptBen = (ContratCpt) updateSoldTrt.exec(contratCptSold);

				Cro2253Trt cro2253Trt = new Cro2253Trt();
				cro2253Trt.exec(operationMoyPayCredit);

				// ----Operation Moyen de Paiement Débit
				OperationMoyPay operationMoyPayDebit = new OperationMoyPay();
				ContratCpt contratCptDeb = null;
				
				Long codOperDebit = 0L;
				String libOperDebit = "";

				codOperDebit = 2252L;
				libOperDebit = "Souscription Assurance FAIEZ";

				// 00. setting obj operationMoyPay
				operationMoyPayDebit.setLibObjOpOmp(libOperDebit);

				// 01. setting personnel initiateur et valideur
				operationMoyPayDebit.setPersonnelInitiateur(personnelInit);
				operationMoyPayDebit.setPersonnelValideur(personnelInit);
				contratCptDeb = contratFaiezVo.getContratCpt();

				// 02. setting structure initiatrice
				Structure structureInitD = new Structure();
				structureInitD.setCodStrcStrc(contratCptDeb.getContratCptId().getCodStrcStrc());
				operationMoyPayDebit.setStructureInitiatrice(structureInitD);

//				// 03. setting structure receptrice
//				Structure structureRecepD = new Structure();
//				structureRecepD.setCodStrcStrc(contratCptDeb.getContratCptId().getCodStrcStrc());
//				operationMoyPayDebit.setStructureReceptrice(structureRecepD);

				operationMoyPayDebit.setDevise(contratCptBen.getDevise());

				// 05. setting devise et montant
				operationMoyPayDebit.setMontDinOmp(contratFaiezVo.getCategorie().getMontTranch2Cat());

				operationMoyPayDebit.setMontApreOmp(contratCptDeb.getMontSoldCcpt() - contratFaiezVo.getCategorie().getMontTranch2Cat());

				operationMoyPayDebit.setMontSoldCcpt(contratCptDeb.getMontSoldCcpt());

				// 06. setting contrat compte
				operationMoyPayDebit.setContratCpt(contratCptDeb);

				// 07. setting type demandeur (Titulaire, CoTitulaire,
				// Mandataire)

				operationMoyPayDebit.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);

				// 08. setting info Reference Operation //à completter
				operationMoyPayDebit.setCodRefbOmp(StrHandler.lpad(contratFaiezVo.getContratCpt().getContratCptId().getCodStrcStrc().toString(), '0',
						3)+StrHandler.lpad(contratFaiezVo.getContratCpt().getContratCptId().getCodPrdPrd().toString(), '0', 4)+StrHandler.lpad(contratFaiezVo.getContratCpt().getContratCptId().getNumCcptCcpt().toString(), '0',
								6));
				operationMoyPayDebit.setCodRefcOmp("0");

				// 09. insertion du Donneur D'ordre
				TypePiece typePieceDemandeurDebit = new TypePiece();
				typePieceDemandeurDebit.setCodTpceTpce(
						Long.valueOf(contratFaiezVo.getCodTpceTpce()));
				operationMoyPayDebit.setTypePieceDemandeur(typePieceDemandeurDebit);
				operationMoyPayDebit.setTypePieceBeneficiaire(typePieceDemandeurDebit);
				operationMoyPayDebit.setNumPcedOmp(contratFaiezVo.getNumPcePers());
				operationMoyPayDebit.setNomNomdOmp(contratFaiezVo.getNomNomPers());
				operationMoyPayDebit.setNomPrndOmp(contratFaiezVo.getNomPrnPers());

				// 10 Preparing data of Operation and tache
				Operation operDebit = new Operation();
				operDebit.setCodOperOper(codOperDebit);
				Tache tacheDebit = new Tache();
				tacheDebit.setOperation(operDebit);
				TacheId tacheIdDebit = new TacheId();
				tacheIdDebit.setCodOperOper(codOperDebit);
				tacheIdDebit.setCodTachTach(1L);
				tacheDebit.setTacheId(tacheIdDebit);

				operationMoyPayDebit.setCodEtatOmp(Constants.COD_VALIDATION);

				operationMoyPayDebit.setTache(tacheDebit);

				// 11. setting date operation moyen paiement
				operationMoyPayDebit
						.setDatOperOmp(DateHandler.strToDate(sysdate));

				// 12. setting date valeur moyen paiement
					operationMoyPayDebit
							.setDatValOmp(DateHandler.strToDate(sysdate));

				// 13.1 setting date system moyen paiement
				operationMoyPayDebit.setDatSystOmp(new Date());

				// 14. setting sens operation
				operationMoyPayDebit.setCodSensOmp(Constants.COD_SENS_DB);

				// 15. setting montant tva
				operationMoyPayDebit.setMontTvaOmp(0L);
				operationMoyPayDebit.setMontCdinOmp(Double.valueOf("0"));

				// 20. Insertion motif operation
				operationMoyPayDebit.setLibMotfOmp(libOperDebit);

				// 02. insertion operation moyen paiement

				InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrtDebit = new InsertionOperationMoyPaySansCROTrt();
				operationMoyPayDebit = (OperationMoyPay) insertOperationMoyPaySansCROTrtDebit.exec(operationMoyPayDebit);

				logger.info("Operation Mo Pay done success:" + operationMoyPayDebit.getNumOperOmp());


				/*********** Mise à jour solde ************/

				ContratCptSold contratCptSoldDebit = new ContratCptSold();
				UpdateSoldTrt updateSoldTrtDebit = new UpdateSoldTrt();
				contratCptSoldDebit.setContratCpt(contratFaiezVo.getContratCpt());
				contratCptSoldDebit.setSens(Constants.COD_SENS_DB);

				contratCptSoldDebit.setSolde(contratFaiezVo.getCategorie().getMontTranch2Cat());
				contratCptDeb = (ContratCpt) updateSoldTrtDebit.exec(contratCptSoldDebit);

				// cro
				Cro2252Trt cro2252Trt = new Cro2252Trt();
				cro2252Trt.exec(operationMoyPayDebit);
				
				assVieEpargneDAO.InsertTraceTrancheFaiezPayé(contratFaiezVo, sysdate, 2);
			}
				
			
			
		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			List listErrors = new ArrayList();
			erreur.setKey(" Erreur Technique" + e.getMessage());
			erreur.setCode("Technique");
			erreur.setDescription("errors.PrelevementTrancheEpargneEtudeTrt");
			listErrors.add(erreur);
			logger.error(e.getMessage());
			throw new RuntimeException();

		}
		return vo;
	}


	@Override
	protected void genCroText(ValueObject vo) {
		// TODO Auto-generated method stub
		
	}

}
