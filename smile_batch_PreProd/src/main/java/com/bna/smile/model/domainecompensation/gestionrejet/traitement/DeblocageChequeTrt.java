package com.bna.smile.model.domainecompensation.gestionrejet.traitement;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.BlocageCheque;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.Devise;
import com.bna.commun.model.MouvementCompensation;
import com.bna.commun.model.Operation;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.model.TypePiece;
import com.bna.commun.traitements.InsertionOperationMoyPaySansCROTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.util.TraitementConditionBanque;
import com.bna.commun.vo.ContratCptSold;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ReglementChequeVo;
import com.bna.smile.model.traitementCompensationRecu.dao.RejetDAO;
import com.bna.smile.web.commun.controller.UtilCtr;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class DeblocageChequeTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	IExpression expression = searchEngine.createExpression();
	ICriteria criteria = searchEngine.createCriteria();
	SimpleDateFormat formaterDate = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formaterHeure = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat formaterDateCro = new SimpleDateFormat("yyyyMMdd");


	public DeblocageChequeTrt() {
	}

	public IValueObject perform(IValueObject vo) {
		logger.info("Cro d'operation Deblocage Cheque");

		RejetDAO rejetDao = (RejetDAO) context.getBean("rejetDAO");
		ReglementChequeVo reglementChequeVo = (ReglementChequeVo) vo;

		OperationMoyPay operationMoyPay = new OperationMoyPay();
		ContratCpt contratCptTireur = reglementChequeVo.getContratCpt();
		Long sommeBlocage = 0L;
		Operation oper = new Operation();
		oper.setCodOperOper(Constants.COD_OPERATION_DEBLOCAGE_CHEQUE);
		Tache tache = new Tache();
		tache.setOperation(oper);
		
		try {

		// ----------------Mise à jour blocage Chèque----------/

			
			// debloquer tout les montant INT et CHQ
		criteria.add(expression.eq("contratCpt.contratCptId.numCcptCcpt", contratCptTireur.getContratCptId().getNumCcptCcpt()));
		criteria.add(expression.eq("contratCpt.contratCptId.codPrdPrd", contratCptTireur.getContratCptId().getCodPrdPrd()));
		criteria.add(expression.eq("contratCpt.contratCptId.codStrcStrc", contratCptTireur.getContratCptId().getCodStrcStrc()));
		criteria.add(expression.in("typeBlocBloc", new String[] {"CHQ","INR"}));
		criteria.add(expression.eq("numChqChq", reglementChequeVo.getCheque().getChequeId().getNumChqChq()));

		List<BlocageCheque> listeBlocage = searchEngine.find(BlocageCheque.class, criteria);
		logger.info("List Bloc to Update:" + listeBlocage.size());
		for (int i = 0; i < listeBlocage.size(); i++) {
			BlocageCheque blocageCheque = listeBlocage.get(i);
			
			/** mettre datFin pour tout les blocages relatives aux interet et montant du cheque en question*/
			blocageCheque.setDatFblocBloc(reglementChequeVo.getDateComptable());
			crudService.update(blocageCheque);
			
			/** le calcul de la somme blocage ne se fait que pour le montant chéque ;; c'est la somme avec laquel on alimente le compte client */
			if(blocageCheque.getTypeBlocBloc().equals("CHQ")) {
			sommeBlocage += blocageCheque.getMntBlocBloc();
			}
		}

		
		/** on  n'insere operation moy pay que si le compte client est modifié , on alimente le compte client par la somme blocage pour CHEQUE*/
		if(UtilCtr.isContratValide(contratCptTireur))  {
			
	
		String valueDateRecue = "";
		String valueDateComRecue = "";


			TraitementConditionBanque traitementConditionBanque = new TraitementConditionBanque();
			traitementConditionBanque.setCodOperOper("" + Constants.COD_OPERATION_DEBLOCAGE_CHEQUE);
			traitementConditionBanque.setCodStrcStrc("" + contratCptTireur.getContratCptId().getCodStrcStrc());
			traitementConditionBanque.setCodPrdCpt("" + contratCptTireur.getContratCptId().getCodPrdPrd());
			traitementConditionBanque.setNumCcptCcpt("" + contratCptTireur.getContratCptId().getNumCcptCcpt());
			traitementConditionBanque.setDateReference(formaterDate.format(reglementChequeVo.getDateComptable()));
			traitementConditionBanque.getCB();
			valueDateRecue = traitementConditionBanque.getDatevaleur();
			valueDateComRecue = traitementConditionBanque.getDatevaleurComm();
			if (valueDateRecue != null && valueDateRecue.equals("NAN")) {
				valueDateRecue = null;
			}
			if (valueDateComRecue != null && valueDateComRecue.equals("NAN")) {
				valueDateComRecue = null;
			}

			// 00. setting obj operationMoyPay
			operationMoyPay.setLibObjOpOmp("DEBLOCAGE PROVISION");

			// 01. setting personnel initiateur et valideur
			Personnel personnelInit = new Personnel();
			personnelInit.setNumMatrUser(reglementChequeVo.getParamAgence().getNumMatrUser());
			operationMoyPay.setPersonnelInitiateur(personnelInit);
			operationMoyPay.setPersonnelValideur(personnelInit);

			// 02. setting structure initiatrice
			Structure structureInit = new Structure();
			structureInit.setCodStrcStrc(contratCptTireur.getContratCptId().getCodStrcStrc());
			operationMoyPay.setStructureInitiatrice(structureInit);

			// 03. setting structure receptrice
			Structure structureRecep = new Structure();
			structureRecep.setCodStrcStrc(contratCptTireur.getContratCptId().getCodStrcStrc());
			operationMoyPay.setStructureReceptrice(structureRecep);

			// 05. setting devise et montant
			Devise devise = new Devise();

			// 05.1 Prelevement en dinar
			devise.setCodDevDev(Constants.COD_DEV_DINAR);

			operationMoyPay.setMontDinOmp(sommeBlocage);
			operationMoyPay.setMontApreOmp(contratCptTireur.getMontSoldCcpt() + sommeBlocage);

			operationMoyPay.setMontSoldCcpt(contratCptTireur.getMontSoldCcpt());
			operationMoyPay.setDevise(devise);

			// 06. setting contrat compte
			operationMoyPay.setContratCpt(contratCptTireur);

			// 07. setting type demandeur (Titulaire, CoTitulaire, Mandataire)

			operationMoyPay.setCodDemOmp(Constants.COD_TYPE_POUVOIR_TITULAIRE);

			// 08. setting info Reference Operation //à completter
			operationMoyPay.setCodRefbOmp(""+ reglementChequeVo.getCheque().getChequeId().getNumChqChq());
			operationMoyPay.setCodRefcOmp("0");

			// 09. insertion du Donneur D'ordre

			TypePiece typePieceDemandeur = new TypePiece();
			typePieceDemandeur.setCodTpceTpce(Long.valueOf(contratCptTireur.getClient().getPersonne().getTypePiece()
					.getCodTpceTpce()));
			operationMoyPay.setTypePieceDemandeur(typePieceDemandeur);
			operationMoyPay.setNumPcedOmp(contratCptTireur.getClient().getPersonne().getNumPcePers());
			operationMoyPay.setNomNomdOmp(contratCptTireur.getClient().getPersonne().getNomNommPers());
			operationMoyPay.setNomPrndOmp(contratCptTireur.getClient().getPersonne().getNomPrnPers());

			// 10 Preparing data of Operation and tache

			TacheId tacheId = new TacheId();
			tacheId.setCodOperOper(Constants.COD_OPERATION_DEBLOCAGE_CHEQUE);
			tacheId.setCodTachTach(Constants.COD_TACHE_REJET_CHEQUE_PREAVIS);
			tache.setTacheId(tacheId);

			operationMoyPay.setCodEtatOmp(Constants.COD_VALIDATION);

			operationMoyPay.setTache(tache);

			// 11. setting date operation moyen paiement

			operationMoyPay.setDatOperOmp(reglementChequeVo.getDateComptable());

			// 12. setting date valeur moyen paiement
			Date dateValOmp = null;
			if (valueDateRecue != null && valueDateRecue.length() > 0) {
				dateValOmp = formaterDate.parse(valueDateRecue);
			} else {
				dateValOmp = reglementChequeVo.getDateComptable();
			}
			operationMoyPay.setDatValOmp(dateValOmp);

			// 13.1 setting date system moyen paiement
			Date dateSysOmp = formaterDate.parse(formaterDate.format(new Date()));
			operationMoyPay.setDatSystOmp(dateSysOmp);

			// 13.2 setting date valeur Commission moyen paiement
			Date dateValComOmp = null;
			if (valueDateComRecue != null && valueDateComRecue.length() > 0) {
				dateValComOmp = formaterDate.parse(valueDateComRecue);
			} else {
				dateValComOmp = reglementChequeVo.getDateComptable();
			}
			operationMoyPay.setDateValeurCommission(dateValComOmp);

			// 14. setting sens operation

			operationMoyPay.setCodSensOmp(Constants.COD_SENS_CR);

			// 15. setting montant tva
			operationMoyPay.setMontTvaOmp(0L);

			// 17. Insertion code ref client
			operationMoyPay.setCodRefcOmp(reglementChequeVo.getParamAgence().getNumMatrUser());

			// 20. Insertion motif operation
			operationMoyPay.setLibMotfOmp("DEBLOCAGE PROVISION");

			// 02. insertion operation moyen paiement
			InsertionOperationMoyPaySansCROTrt insertOperationMoyPaySansCROTrt =
					new InsertionOperationMoyPaySansCROTrt();
			operationMoyPay = (OperationMoyPay) insertOperationMoyPaySansCROTrt.exec(operationMoyPay);

			logger.info("Operation Mo Pay done success");
			logger.info("operationMoyPay :" + operationMoyPay);
			reglementChequeVo.setOperationMoyPay(operationMoyPay);
			this.setCroFlag(true);
			// ***************** Insertion Mouvement Compensation******************//
			// Mouvement Copensation //
			MouvementCompensation mouvementCompensation = new MouvementCompensation();
			mouvementCompensation.setCheque(reglementChequeVo.getCheque());
			mouvementCompensation.setOperationMoyPay(operationMoyPay);
			mouvementCompensation.setTache(tache);
			mouvementCompensation.setPersonnel(personnelInit);
			mouvementCompensation.setDatOpeMvtc(reglementChequeVo.getDateComptable());
			mouvementCompensation.setMntOpeMvtc(sommeBlocage);
			mouvementCompensation.setNumSeqMvtc(rejetDao.getSequenceMvtCompensation());
			crudService.create(mouvementCompensation);

			// reglementChequeVo.setMontantReglement(reglementChequeVo.getMontantReglement() + sommeBlocage);

			ContratCptSold contratCptSold = new ContratCptSold();
			UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
			contratCptSold.setContratCpt(contratCptTireur);
			contratCptSold.setSens(Constants.COD_SENS_CR);
			contratCptSold.setSolde(sommeBlocage);
			contratCptTireur = (ContratCpt) updateSoldTrt.exec(contratCptSold);
			
			
		} // Ending checking isContratValid
		else {
			this.setCroFlag(false);

		}
			HibernateTemplate hibernateTemplate = (HibernateTemplate) this.context.getBean("hibernateTemplate");
			hibernateTemplate.getSessionFactory().getCurrentSession().evict(contratCptTireur);
			contratCptTireur = (ContratCpt) hibernateTemplate.get(ContratCpt.class, contratCptTireur.getContratCptId());
			reglementChequeVo.setContratCpt(contratCptTireur);

		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans DeblocageChequeTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("DeblocageChequeTrt");
			reglementChequeVo.addError(erreur);
			logger.error("Erreur au niveau DeblocageChequeTrt : ", e);
			throw new  RuntimeException(e);

		}
		return (reglementChequeVo);


	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}
	public void genCroText(ValueObject vo) {

		ReglementChequeVo reglementChequeVo = (ReglementChequeVo) vo;
		OperationMoyPay operationMoyPay = reglementChequeVo.getOperationMoyPay();
		ContratCpt contratCptTireur = reglementChequeVo.getContratCpt();
		logger.info("starting Cro deblocage... ");
		Operation operation = new Operation();

		operation.setCodOperOper(Constants.COD_OPERATION_DEBLOCAGE_CHEQUE);
		Produit produit = new Produit();
		produit.setCodPrdPrd(Constants.COD_PRODUIT_CHEQUE);

		/*
		 * ---------------------- Garniture de la partie FIXE du CRO -----------------------------------
		 */

		if(operationMoyPay.getNumOperOmp()!=null)
			this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
		else
			this.setNumRefCro(Long.valueOf(formaterDateCro.format(reglementChequeVo.getDateComptable())));

		this.setLibRefCro("Deblocage  Provision");
		this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc() + "");

		this.setCodStrcImpt(operationMoyPay.getStructureReceptrice().getCodStrcStrc());
		this.setDatExecCro(new Date());
		this.setCodEtatCro(0);
		this.setCodeProduit(produit.getCodPrdPrd().toString());
		this.setOperationId(operation.getCodOperOper().toString());
		this.setDateOperation(operationMoyPay.getDatOperOmp());
		this.setDatValCro(operationMoyPay.getDatValOmp());
		this.setDatValCom(operationMoyPay.getDateValeurCommission());
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		this.setHeureOperation(heureString);
		this.setTypeOperationCro("O");
		this.setCodTachTach(Constants.COD_TACHE_REJET_CHEQUE_PREAVIS);
		this.setCodRefcOmp(operationMoyPay.getCodRefcOmp());
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.oxia.security.abc.model.Personnel user = null;
		if (obj instanceof UserDetails) {
			user = (com.oxia.security.abc.model.Personnel) obj;
		}
		/**  9999 : pour dire que c'est le batch ( num matr user 9999 )*/
		this.setNumCinUser("9999");
		this.setCodTypUser(user.getMatriculeTyp());

		/*
		 * ------------------Garniture de la partie VARIABLE du CRO----------------------------------
		 */

		StringBuffer cro = new StringBuffer("");
		// *** Compte Client ****//
		
		RejetDAO rejetDao = (RejetDAO) context.getBean("rejetDAO");
		String codAgentEco = rejetDao.getAgentEconomique(contratCptTireur.getClient().getPersonne());
		// 2 : financier // 1 : non financier
		cro.append("AGENT_ECONOMIQUE.COD_NAT_CLT="+codAgentEco+";");		


		cro.append("Numcptcli=");
		cro.append(StrHandler.lpad(contratCptTireur.getContratCptId().getCodStrcStrc().toString(), '0', 3));
		// cro.append("COD_PRD_PRD=");
		cro.append(StrHandler.lpad(contratCptTireur.getContratCptId().getCodPrdPrd().toString(), '0', 4));
		// cro.append("NUM_CCPT_CCPT=");
		cro.append(StrHandler.lpad(contratCptTireur.getContratCptId().getNumCcptCcpt().toString(), '0', 6) + ";");
		// *** Mnt Cheque ****//
		cro.append("MNT_RJT_CHQ_AGE=");
		long mntChq = reglementChequeVo.getCheque().getMntChqChq().longValue();
		long mntBloq = Long.valueOf(0);
		if (reglementChequeVo.getSommeBlocage() != null)
			mntBloq = reglementChequeVo.getSommeBlocage().longValue();
		cro.append((mntChq - mntBloq) + ";");
		// *** Montant Debblocage ****//
		cro.append("MNT_DBQ_CHQ=");
		cro.append(reglementChequeVo.getSommeBlocage() + ";");

		// ***Numero Cheque ****//
		cro.append("numcheque=");
		cro.append(reglementChequeVo.getCheque().getChequeId().getNumChqChq() + ";");

		// ***rib benificiare ****//
		cro.append("MNT_CHQ_CLT=");
		cro.append(reglementChequeVo.getCheque().getMntChqChq() + ";");
		
		cro.append("ETAT_CPT=");
		if (contratCptTireur.getCodEtatCcpt().equals(
				Constants.COD_VALIDATION)) {
			cro.append("0;");
		} else if (contratCptTireur.getCodEtatCcpt().equals(
				Constants.COD_ETAT_CPT_TCONTENTIEU)
				|| contratCptTireur.getCodEtatCcpt().equals(
						Constants.COD_ETAT_CPT_RESILIE)) {
			cro.append("6;");
		}


		cro.append("codval=");
		cro.append(reglementChequeVo.getCodValVal() + ";");
		// ***rib benificiare ****//
		cro.append("rib_benef=");
		cro.append(reglementChequeVo.getCheque().getChequeId().getRibBenChq() + ";");
		// ***Rib tireur ****//
		cro.append("rib_tireur=");
		cro.append(reglementChequeVo.getCheque().getChequeId().getRibTirChq() + ";");
		
		// *** Date compensation ****//
		cro.append("Dat_comp=");
		cro.append(formaterDateCro.format(reglementChequeVo.getDateComptable()) + ";");

		
		cro.append("codrej=");
		cro.append(reglementChequeVo.getCheque().getCodMrejChq() + ";");

		



		this.setCroText(cro.toString());
		logger.info("Cro Deblocage generer avec succés");
}


}