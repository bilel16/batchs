package com.bna.smile.model.domaineguichet.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

import com.bna.commun.constant.Constants;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.ContratCptSold;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;


public class InsertOpMoyPayActelEmisTrt extends Traitement {

	public Context context = ContextHandler.getContext();
	OperationMoyPay operationMoyPay;
	public InsertOpMoyPayActelEmisTrt() {
	}

	public IValueObject perform(IValueObject vo) {
	 operationMoyPay = (OperationMoyPay) vo;
	 ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
		

		try {
			// /*** insertion dans la table Operation_Moy_Pay
			InsertOperationMoyPayTrt insertOperationMoyPayTrt = new InsertOperationMoyPayTrt();
			insertOperationMoyPayTrt.setVerifDomaine(false);
			
			ContratCpt contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class, operationMoyPay.getContratCpt().getContratCptId());
			
			
			operationMoyPay.setMontSoldCcpt(contratCpt.getMontSoldCcpt());
			
			UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
			ContratCptSold contratCptSold = new ContratCptSold();
			contratCptSold.setContratCpt(contratCpt);
			contratCptSold.setSolde(operationMoyPay.getMontDinOmp());
			
			if(operationMoyPay.getCodSensOmp().equals(Constants.COD_SENS_DB))
			{
				operationMoyPay.setMontApreOmp(contratCpt.getMontSoldCcpt() - operationMoyPay.getMontDinOmp());
				contratCptSold.setSens(Constants.COD_SENS_DB);
			}else
			{
				operationMoyPay.setMontApreOmp(contratCpt.getMontSoldCcpt() + operationMoyPay.getMontDinOmp());
				contratCptSold.setSens(Constants.COD_SENS_CR);
			}
			contratCpt = (ContratCpt) updateSoldTrt.exec(contratCptSold);
			
			
			operationMoyPay = (OperationMoyPay) insertOperationMoyPayTrt.exec(operationMoyPay);
			this.setCroFlag(true);
			return operationMoyPay;
		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans MAJ NSI InsertOpMoyPayActelEmisTrt : ");
			text.append(e.toString());
			erreur.setCode("300");
			erreur.setDescription(text.toString());
			erreur.setKey("InsertOpMoyPayTrt");
			operationMoyPay.addError(erreur);
			throw new RuntimeException();

		}

	}

	public void genCroText(ValueObject vo) {

		/* ---------------------- Garniture de la partie FIXE du CRO ----------------------------------- */

		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.oxia.security.abc.model.Personnel user = null;
		if (obj instanceof UserDetails) {
			user = (com.oxia.security.abc.model.Personnel) obj;
		}

		this.setNumRefCro(Long.valueOf(operationMoyPay.getNumOperOmp()));
		this.setLibRefCro("smile.batch.ACTEL.TransfertEmis");
		this.setDatValCro(operationMoyPay.getDatValOmp());
		this.setCodeStructInitiatrice(operationMoyPay.getStructureInitiatrice().getCodStrcStrc().toString());
		this.setCodStrcImpt(operationMoyPay.getStructureInitiatrice().getCodStrcStrc());
		this.setCodEtatCro(0);
		this.setCodeProduit(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().toString());
		this.setOperationId(operationMoyPay.getTache().getTacheId().getCodOperOper().toString());
		this.setCodTachTach(operationMoyPay.getTache().getTacheId().getCodTachTach().longValue());
		this.setDateOperation(operationMoyPay.getDatOperOmp());

		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		formater = new SimpleDateFormat("HH:mm:ss");
		String heureString = formater.format(new Date());
		this.setHeureOperation(heureString);
		this.setTypeOperationCro("O");
		this.setDatExecCro(operationMoyPay.getDatSystOmp()); // date system
		this.setCodRefcOmp(" ");

		this.setNumCinUser(user.getNumMatrUser());
		this.setCodTypUser(user.getMatriculeTyp());
		if(operationMoyPay.getCodSensOmp().equals("C"))
			this.setCodRefInter(operationMoyPay.getRefIns1Omp());
		else
			this.setCodRefInter(operationMoyPay.getRefIns2Omp());
		/* ------------------Garniture de la partie VARIABLE du CRO---------------------------------- */
		StringBuffer cro = new StringBuffer("");
		StringBuffer contratCPT = new StringBuffer("");
		// contratClient
		contratCPT.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getCodStrcStrc()
				.toString(), '0', 3));
		contratCPT.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getCodPrdPrd().toString(),
				'0', 4));
		contratCPT.append(StrHandler.lpad(operationMoyPay.getContratCpt().getContratCptId().getNumCcptCcpt()
				.toString(), '0', 6));
		contratCPT.append(";");
		cro.append("numCptBna=");
		cro.append(contratCPT.toString());
		cro.append("cod_strc_recep=");
		cro.append( operationMoyPay.getStructureReceptrice().getCodStrcStrc().toString()+";");
		
		cro.append("MNT_MOUV_MOUV=");
		cro.append(operationMoyPay.getMontDinOmp() + ";");
		cro.append("SENS_MOUV_MOUV=");
		if(operationMoyPay.getCodSensOmp().equals("C"))
		cro.append( "0;");
		else
		cro.append( "1;");	
		
		cro.append("Refis_1=");
		cro.append(operationMoyPay.getRefIns1Omp()+ ";");
		cro.append("Refis_2=");
		cro.append(operationMoyPay.getRefIns2Omp() + ";");
		
		this.setCroText(cro.toString());
	}

	public String getNumeroTache(IValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}
}
