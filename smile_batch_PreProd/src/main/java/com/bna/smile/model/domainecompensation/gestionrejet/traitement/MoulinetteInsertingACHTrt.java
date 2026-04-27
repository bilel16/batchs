package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.awt.Color;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.Cheque30;
import com.bna.commun.model.Cheque30Id;
import com.bna.commun.model.Cheque31;
import com.bna.commun.model.Cheque31Id;
import com.bna.commun.model.Cheque32;
import com.bna.commun.model.Cheque32Id;
import com.bna.commun.model.Cheque33;
import com.bna.commun.model.Cheque33Id;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ChequeACHVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * @author 5556
 * @since 11/03/2026 Refonte SNT - ACH
 **/

public class MoulinetteInsertingACHTrt extends Traitement {

	Context context = ContextHandler.getContext();

	ISearchEngine search = (SearchEngine) context.getBean("searchEngine");
	ICriteria criteria = search.createCriteria();
	IExpression expression = search.createExpression();

	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");

	SimpleDateFormat formatDateImg = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

	CompensationVo compensationVo = new CompensationVo();

	CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");

	HibernateTemplate hibTemplate = (HibernateTemplate) context.getBean("hibernateTemplate");

	// nombre de cheque
	public static Random random = new Random();

	// public String pathChequeTraite;
	// public String pathChequeTravail;

	Long mntTot = Long.valueOf(0);
	Long mntIntra = Long.valueOf(0);

	Long nbreTot = Long.valueOf(0);
	Long nbreIntra = Long.valueOf(0);

	private final int SUCCES = 1;
	private final int FAIL = 0;

	String wmmjj = null;
	String datImg;

	// String pathImg = File.separator + File.separator
	// + Configuration.getServerPath() + "\\Compensation";

	@Override
	public IValueObject perform(IValueObject vo) {

		compensationVo = (CompensationVo) vo;

		SimpleDateFormat formatDate = new SimpleDateFormat("ddMMyyyy");

		// String srcFile30, srcFile32, srcFile31, srcFile33 = null;
		String ageBct = null;
		String ageBna = null;

		try {
			ageBct = StrHandler.lpad(compensationVo.getStrutcure().getCodBctStrc(), '0', 3);

			ageBna = StrHandler.lpad("" + compensationVo.getStrutcure().getCodStrcStrc(), '0', 3);

			wmmjj = formatDate.format(compensationVo.getDateComptable());

			datImg = formatDateImg.format(compensationVo.getDateComptable());

			int returnValue = 1;
			// Valeur Cheque 30
			importFromCheque30DataBase(compensationVo.getDateComptable(), ageBct,
					"" + Constants.COD_CHEQUE_PREMIERE_PRESENTATION);

			SuivFileTrt.ajouterFichierProvAvecMontant(
					"03-" + ageBct + "-" + Constants.COD_CHEQUE_PREMIERE_PRESENTATION + "-" + wmmjj + "-788.RCP",
					ageBct, compensationVo.getDateComptable(), returnValue, Constants.COD_CHEQUE_PREMIERE_PRESENTATION,
					mntTot, nbreTot, mntTot - mntIntra, nbreTot - nbreIntra, nbreIntra, mntIntra);

			compensationDAO.appurement(ageBna, "30");

			// Valeur Cheque 31
			importFromCheque31DataBase(compensationVo.getDateComptable(), ageBct,
					"" + Constants.COD_CHEQUE_REPRESENTATION_PAIEMENT_PARTIEL);

			SuivFileTrt.ajouterFichierProvAvecMontant(
					"03-" + ageBct + "-" + Constants.COD_CHEQUE_REPRESENTATION_PAIEMENT_PARTIEL + "-" + wmmjj
							+ "-788.RCP",
					ageBct, compensationVo.getDateComptable(), returnValue,
					Constants.COD_CHEQUE_REPRESENTATION_PAIEMENT_PARTIEL, mntTot, nbreTot, mntTot - mntIntra,
					nbreTot - nbreIntra, nbreIntra, mntIntra);

			// Valeur Cheque 32
			importFromCheque32DataBase(compensationVo.getDateComptable(), ageBct,
					"" + Constants.COD_CHEQUE_REPRESENTATION_SUITE_ARP);

			SuivFileTrt.ajouterFichierProvAvecMontant(
					"03-" + ageBct + "-" + Constants.COD_CHEQUE_REPRESENTATION_SUITE_ARP + "-" + wmmjj + "-788.RCP",
					ageBct, compensationVo.getDateComptable(), returnValue,
					Constants.COD_CHEQUE_REPRESENTATION_SUITE_ARP, mntTot, nbreTot, mntTot - mntIntra,
					nbreTot - nbreIntra, nbreIntra, mntIntra);

			// Valeur Cheque 33
			importFromCheque33DataBase(compensationVo.getDateComptable(), ageBct,
					"" + Constants.COD_CHEQUE_REPRESENTATION_SUITE_PAPILLON);

			SuivFileTrt.ajouterFichierProvAvecMontant(
					"03-" + ageBct + "-" + Constants.COD_CHEQUE_REPRESENTATION_SUITE_PAPILLON + "-" + wmmjj
							+ "-788.RCP",
					ageBct, compensationVo.getDateComptable(), returnValue,
					Constants.COD_CHEQUE_REPRESENTATION_SUITE_PAPILLON, mntTot, nbreTot, mntTot - mntIntra,
					nbreTot - nbreIntra, nbreIntra, mntIntra);

			compensationDAO.appurement(ageBna, "33");

		} catch (Throwable e) {
			compensationVo.getBatch().getMsgDetailChq().setForeground(Color.red);
			compensationVo.getBatch().getMsgDetailChq()
					.setText(compensationVo.getBatch().getMsgDetailChq().getText() + " Erreur !");

			e.printStackTrace();

			SwingInfoVo infoVo = new SwingInfoVo();
			infoVo.setStructure("" + compensationVo.getStrutcure().getCodStrcStrc());

			infoVo.setEtat("Erreur insertion");
			infoVo.setDateComptable(sdf.format(compensationVo.getDateComptable()));

			compensationVo.getBatch().addOrUpdateEtat(infoVo);

			logger.info(e.getMessage());
			// throw new RuntimeException();
		}

		return compensationVo;

	}

	public void importFromCheque30DataBase(Date dateComptable, String codBct, String codValChq) throws Exception {

		nbreTot = Long.valueOf(0);
		mntTot = Long.valueOf(0);

		nbreIntra = Long.valueOf(0);
		mntIntra = Long.valueOf(0);

		List<ChequeACHVo> listChequesACH = new ArrayList<ChequeACHVo>();

		listChequesACH = compensationDAO.getListCheques30ACHAgence(dateComptable, codBct, codValChq);

		long numberLine = listChequesACH.size();

		for (ChequeACHVo chequeACHVo : listChequesACH) {
			if (("" + Constants.COD_CHEQUE_PREMIERE_PRESENTATION).equals(codValChq))
				create30(chequeACHVo);
			if (("" + Constants.COD_CHEQUE_REPRESENTATION_PAIEMENT_PARTIEL).equals(codValChq))
				create31(chequeACHVo);
			if (("" + Constants.COD_CHEQUE_REPRESENTATION_SUITE_ARP).equals(codValChq))
				create32(chequeACHVo);
			if (("" + Constants.COD_CHEQUE_REPRESENTATION_SUITE_PAPILLON).equals(codValChq))
				create33(chequeACHVo);

			nbreTot = nbreTot + 1;
			String codBanqueEm = chequeACHVo.getCodBan();
			// si intra
			if (codBanqueEm.equals("03")) {
				nbreIntra = nbreIntra + 1;
				mntIntra = mntIntra + chequeACHVo.getMntChq();
			}
			mntTot = mntTot + chequeACHVo.getMntChq();
		}

	}

	public void importFromCheque31DataBase(Date dateComptable, String codBct, String codValChq) throws Exception {

		nbreTot = Long.valueOf(0);
		mntTot = Long.valueOf(0);

		nbreIntra = Long.valueOf(0);
		mntIntra = Long.valueOf(0);

		List<ChequeACHVo> listChequesACH = new ArrayList<ChequeACHVo>();

		listChequesACH = compensationDAO.getListCheques31ACHAgence(dateComptable, codBct, codValChq);

		long numberLine = listChequesACH.size();

		for (ChequeACHVo chequeACHVo : listChequesACH) {

			if (("" + Constants.COD_CHEQUE_REPRESENTATION_PAIEMENT_PARTIEL).equals(codValChq))
				create31(chequeACHVo);

			nbreTot = nbreTot + 1;
			String codBanqueEm = chequeACHVo.getCodBan();
			// si intra
			if (codBanqueEm.equals("03")) {
				nbreIntra = nbreIntra + 1;
				mntIntra = mntIntra + chequeACHVo.getMntChq();
			}
			mntTot = mntTot + chequeACHVo.getMntChq();
		}

	}

	public void importFromCheque32DataBase(Date dateComptable, String codBct, String codValChq) throws Exception {

		nbreTot = Long.valueOf(0);
		mntTot = Long.valueOf(0);

		nbreIntra = Long.valueOf(0);
		mntIntra = Long.valueOf(0);

		List<ChequeACHVo> listChequesACH = new ArrayList<ChequeACHVo>();

		listChequesACH = compensationDAO.getListCheques32ACHAgence(dateComptable, codBct, codValChq);

		long numberLine = listChequesACH.size();

		for (ChequeACHVo chequeACHVo : listChequesACH) {
			if (("" + Constants.COD_CHEQUE_REPRESENTATION_SUITE_ARP).equals(codValChq))
				create32(chequeACHVo);

			nbreTot = nbreTot + 1;
			String codBanqueEm = chequeACHVo.getCodBan();
			// si intra
			if (codBanqueEm.equals("03")) {
				nbreIntra = nbreIntra + 1;
				mntIntra = mntIntra + chequeACHVo.getMntChq();
			}
			mntTot = mntTot + chequeACHVo.getMntChq();
		}

	}

	public void importFromCheque33DataBase(Date dateComptable, String codBct, String codValChq) throws Exception {

		nbreTot = Long.valueOf(0);
		mntTot = Long.valueOf(0);

		nbreIntra = Long.valueOf(0);
		mntIntra = Long.valueOf(0);

		List<ChequeACHVo> listChequesACH = new ArrayList<ChequeACHVo>();

		listChequesACH = compensationDAO.getListCheques33ACHAgence(dateComptable, codBct, codValChq);

		long numberLine = listChequesACH.size();

		for (ChequeACHVo chequeACHVo : listChequesACH) {
			if (("" + Constants.COD_CHEQUE_REPRESENTATION_SUITE_PAPILLON).equals(codValChq))
				create33(chequeACHVo);

			nbreTot = nbreTot + 1;
			String codBanqueEm = chequeACHVo.getCodBan();
			// si intra
			if (codBanqueEm.equals("03")) {
				nbreIntra = nbreIntra + 1;
				mntIntra = mntIntra + chequeACHVo.getMntChq();
			}
			mntTot = mntTot + chequeACHVo.getMntChq();
		}

	}

	public Cheque30 create30(ChequeACHVo chequeACHVo) throws Exception, SQLException {

		SimpleDateFormat datOpFmrt = new SimpleDateFormat("yyyyMMdd");

		String ribTir = "0" + chequeACHVo.getRibTir();
		String codUgOpe = ribTir.substring(5, 8);

		Cheque30Id id = new Cheque30Id();
		Long num = 0L; // Long.valueOf(line.substring(56, 66))
		id = new Cheque30Id(codUgOpe, chequeACHVo.getDatOpe(), chequeACHVo.getNumChq(), num);

		Cheque30 c30 = new Cheque30();

		c30.setRibTir(ribTir);
		c30.setNumChq(chequeACHVo.getNumChq());
		c30.setCodUgOpe(codUgOpe);

		compensationVo.getBatch().getMsgDetailChq().setText("Chéque :  [Valeur :30,Numéro :" + c30.getNumChq() + "]");

		System.out.println(c30.getNumChq());
		if (isCheckInserted(c30.getCodUgOpe(), c30.getNumChq(), "" + num, "30", c30.getDatJouOpe())) {
			c30 = (Cheque30) search.loadForUpdate(Cheque30.class, id);
		}

		c30.setCheque30Id(id);

		c30.setSens(chequeACHVo.getCodSen());
		c30.setCodVal(chequeACHVo.getCodVal());

		c30.setNatRem(chequeACHVo.getCodNatEta());// nature remettant
		c30.setCodRem(chequeACHVo.getCodBan());// line.substring(4, 6));//code banque émitrice

		c30.setCodCenReg(chequeACHVo.getCodAge());// line.substring(73, 93).substring(2, 5));code agence emitrice

		c30.setCodInsDes(chequeACHVo.getCodBanDes());// (line.substring(48, 68).substring(0, 2));//code banque
														// desitination

		c30.setCodCenRegDes(chequeACHVo.getCodAgeDes());// line.substring(48, 68).substring(2, 5));//code agence
														// destination

		c30.setDatOpe(chequeACHVo.getDatOpe());
		c30.setDatEmi(chequeACHVo.getDatEmi());
		c30.setNumLot(chequeACHVo.getNumLot());
		c30.setCodEnr(chequeACHVo.getCodEnr());
		c30.setCodDev(chequeACHVo.getCodDev());
		c30.setMntChq(chequeACHVo.getMntChq());
		c30.setRibBen("0" + chequeACHVo.getRibBen());
		c30.setNomBen(chequeACHVo.getNomPrn());
		c30.setLieEmi(chequeACHVo.getCodLieEmiChq());
		c30.setSitBen(chequeACHVo.getCodSit());
		c30.setNatCpt("" + chequeACHVo.getCodNatCpt());
		// c30.setMotRej(chequeACHVo.getCodMotRej());

		c30.setCodSta("N");
		c30.setCmpAuto("B");

		String imgVer = "";
		String imgRec = "";

		if (c30.getRibBen().substring(0, 2).equals("03")) {
			// imgVer = pathImg + File.separator + "AGENCE" + c30.getCodCenReg() +
			// File.separator + "Emis" + File.separator
			// + datImg + File.separator + "30" + File.separator + "Defalc" + File.separator
			// + "Images"
			// + File.separator + line.substring(41, 48) + line.substring(48, 68) +
			// "03V.JPG";
			// imgRec = pathImg + File.separator + "AGENCE" + c30.getCodCenReg() +
			// File.separator + "Emis" + File.separator
			// + datImg + File.separator + "30" + File.separator + "Defalc" + File.separator
			// + "Images"
			// + File.separator + line.substring(41, 48) + line.substring(48, 68) +
			// "03R.JPG";

		} else {

			// imgVer = pathImg + File.separator + "AGENCE" + c30.getCodCenRegDes() +
			// File.separator + "Recu"
			// + File.separator + datImg + File.separator + "30" + File.separator + "Images"
			// + File.separator
			// + line.substring(41, 48) + line.substring(48, 68) + c30.getCodRem() +
			// "V.JPG";
			// imgRec = pathImg + File.separator + "AGENCE" + c30.getCodCenRegDes() +
			// File.separator + "Recu"
			// + File.separator + datImg + File.separator + "30" + File.separator + "Images"
			// + File.separator
			// + line.substring(41, 48) + line.substring(48, 68) + c30.getCodRem() +
			// "R.JPG";

		}

		c30.setImgRec(imgRec);
		c30.setImgVer(imgVer);

		if (!isCheckInserted(c30.getCodUgOpe(), c30.getNumChq(), "" + num, "30", c30.getDatJouOpe())) {
			crudService.create(c30);
			System.out.println("Insertion cheque num [" + c30.getNumChq() + "]");

		} else {
			System.out.println("Update cheque num [" + c30.getNumChq() + "]");
			crudService.update(c30);
		}

		return c30;

	}

	public Cheque31 create31(ChequeACHVo chequeACHVo) throws NumberFormatException, Exception {

		Cheque31 c31 = new Cheque31();

		String ribTir = "0" + chequeACHVo.getRibTir();
		String codUgOpe = ribTir.substring(5, 8);

		String num = "";
		c31.setCodUgOpe(codUgOpe);

		c31.setSens(chequeACHVo.getCodSen());
		c31.setCodVal(chequeACHVo.getCodVal());
		c31.setNatRem(chequeACHVo.getCodNatEta());
		c31.setCodInsDes(chequeACHVo.getCodBanDes());// line.substring(4, 6));

		SimpleDateFormat datOpFmrt = new SimpleDateFormat("yyyyMMdd");

		c31.setDatOpe(chequeACHVo.getDatOpe());
		c31.setDatEmi(chequeACHVo.getDatEmi());
		c31.setDatCnp(chequeACHVo.getDatCnp());
		c31.setNumLot(chequeACHVo.getNumLot());
		c31.setCodEnr(chequeACHVo.getCodEnr());
		c31.setCodDev(chequeACHVo.getCodDev());
		c31.setMntChq(chequeACHVo.getMntChq());
		c31.setNumChq(chequeACHVo.getNumChq());

		c31.setCodRem(chequeACHVo.getCodBan());// line.substring(4, 6));//code banque émitrice

		c31.setCodCenRegDes(chequeACHVo.getCodAgeDes());// line.substring(48, 68).substring(2, 5));//code agence

		compensationVo.getBatch().getMsgDetailChq().setText("Chéque :  [Valeur :31,Numéro :" + c31.getNumChq() + "]");

		c31.setRibTir(ribTir);

		c31.setCodCenRegDes(chequeACHVo.getCodAgeDes()); // line.substring(48, 68).substring(2, 5));code agence
															// destination

		c31.setRibBen("0" + chequeACHVo.getRibBen());
		c31.setLieEmi(chequeACHVo.getCodLieEmiChq());
		c31.setNumCnp(chequeACHVo.getNumCnp());
		c31.setCodDevPos(chequeACHVo.getCodDevPos());
		c31.setMntRec(chequeACHVo.getMntRec());

		Cheque31Id id = new Cheque31Id(codUgOpe, chequeACHVo.getDatOpe(),
				Long.valueOf(c31.getRibTir().substring(8, 18)), c31.getNumChq());
		c31.setCheque31Id(id);

		String imgVer = "";
		String imgRec = "";

		if (c31.getRibBen().substring(0, 2).equals("03")) {
			// imgVer = pathImg + File.separator + "AGENCE" + c31.getCodCenReg() +
			// File.separator + "Emis" + File.separator
			// + datImg + File.separator + "31" + File.separator + "Defalc" + File.separator
			// + "Images"
			// + File.separator + line.substring(41, 48) + line.substring(48, 68) +
			// "03V.JPG";
			// imgRec = pathImg + File.separator + "AGENCE" + c31.getCodCenReg() +
			// File.separator + "Emis" + File.separator
			// + datImg + File.separator + "31" + File.separator + "Defalc" + File.separator
			// + "Images"
			// + File.separator + line.substring(41, 48) + line.substring(48, 68) +
			// "03R.JPG";

		} else {

			// imgVer = pathImg + File.separator + "AGENCE" + c31.getCodCenRegDes() +
			// File.separator + "Recu"
			// + File.separator + datImg + File.separator + "31" + File.separator + "Images"
			// + File.separator
			// + line.substring(41, 48) + line.substring(48, 68) + c31.getCodRem() +
			// "V.JPG";
			// imgRec = pathImg + File.separator + "AGENCE" + c31.getCodCenRegDes() +
			// File.separator + "Recu"
			// + File.separator + datImg + File.separator + "31" + File.separator + "Images"
			// + File.separator
			// + line.substring(41, 48) + line.substring(48, 68) + c31.getCodRem() +
			// "R.JPG";

		}

		c31.setImgRec(imgRec);
		c31.setImgVer(imgVer);
		c31.setCodSta("N");
		c31.setCmpAuto("B");

		crudService.create(c31);

		return c31;

	}

	public Cheque32 create32(ChequeACHVo chequeACHVo) throws NumberFormatException, Exception {

		Cheque32 c32 = new Cheque32();

		String ribTir = "0" + chequeACHVo.getRibTir();
		String codUgOpe = ribTir.substring(5, 8);

		c32.setSens(chequeACHVo.getCodSen());
		c32.setCodVal(chequeACHVo.getCodVal());
		c32.setNatRem(chequeACHVo.getCodNatEta());

		c32.setDatOpe(chequeACHVo.getDatOpe());
		c32.setDatEmi(chequeACHVo.getDatEmi());
		c32.setDatCnp(chequeACHVo.getDatCnp());

		c32.setCodRem(chequeACHVo.getCodBan());// line.substring(4, 6));//code banque émitrice

		c32.setCodCenReg(chequeACHVo.getCodAge());// line.substring(73, 93).substring(2, 5));code agence emitrice

		c32.setCodInsDes(chequeACHVo.getCodBanDes());// (line.substring(48, 68).substring(0, 2));//code banque
														// desitination

		c32.setCodCenRegDes(chequeACHVo.getCodAgeDes());// line.substring(48, 68).substring(2, 5));//code agence

		c32.setRibTir(ribTir);

		c32.setNumLot(chequeACHVo.getNumLot());
		c32.setCodEnr(chequeACHVo.getCodEnr());
		c32.setCodDev(chequeACHVo.getCodDev());
		c32.setMntChq(chequeACHVo.getMntChq());
		c32.setNumChq(chequeACHVo.getNumChq());

		compensationVo.getBatch().getMsgDetailChq().setText("Chéque :  [Valeur :32,Numéro :" + c32.getNumChq() + "]");

		c32.setRibBen("0" + chequeACHVo.getRibBen());
		c32.setLieEmi(chequeACHVo.getCodLieEmiChq());
		c32.setNumCnp(chequeACHVo.getNumCnp());
		c32.setCodDevPos(chequeACHVo.getCodDevPos());
		c32.setMntRec(chequeACHVo.getMntReg() + chequeACHVo.getMntRegInt());

		Long num = 0L; // Long.valueOf(line.substring(56, 66))
		Cheque32Id id = new Cheque32Id(codUgOpe, chequeACHVo.getDatOpe(), c32.getNumChq(), num);

		c32.setCheque32Id(id);

		// if (!isCheckInserted(codUgOpe, c32.getNumChq(),"" + num, "32",
		// chequeACHVo.getDatOpe())) {
		// c32 = (Cheque32) search.loadForUpdate(Cheque32.class, id);
		// }
		String imgVer = null;
		String imgRec = null;
		if (c32.getRibBen().substring(0, 2).equals("03")) {
			// imgVer = pathImg + File.separator + "AGENCE" + c32.getCodCenReg() +
			// File.separator + "Emis" + File.separator
			// + datImg + File.separator + "32" + File.separator + "Defalc" + File.separator
			// + "Images"
			// + File.separator + line.substring(41, 48) + line.substring(48, 68) +
			// "03V.JPG";
			// imgRec = pathImg + File.separator + "AGENCE" + c32.getCodCenReg() +
			// File.separator + "Emis" + File.separator
			// + datImg + File.separator + "32" + File.separator + "Defalc" + File.separator
			// + "Images"
			// + File.separator + line.substring(41, 48) + line.substring(48, 68) +
			// "03R.JPG";

		} else {

			// imgVer = pathImg + File.separator + "AGENCE" + c32.getCodCenRegDes() +
			// File.separator + "Recu"
			// + File.separator + datImg + File.separator + "32" + File.separator + "Images"
			// + File.separator
			// + line.substring(41, 48) + line.substring(48, 68) + c32.getCodRem() +
			// "V.JPG";
			// imgRec = pathImg + File.separator + "AGENCE" + c32.getCodCenRegDes() +
			// File.separator + "Recu"
			// + File.separator + datImg + File.separator + "32" + File.separator + "Images"
			// + File.separator
			// + line.substring(41, 48) + line.substring(48, 68) + c32.getCodRem() +
			// "R.JPG";

		}
		c32.setImgRec(imgRec);
		c32.setImgVer(imgVer);
		c32.setCodSta("N");
		c32.setCmpAuto("B");

		if (!isCheckInserted(codUgOpe, c32.getNumChq(), "" + num, "32", chequeACHVo.getDatOpe())) {
			// hibTemplate.save(c30);
			// hibTemplate.flush();
			crudService.create(c32);

		} else {
			// crudService.update(c32);

		}
		return c32;

	}

	public Cheque33 create33(ChequeACHVo chequeACHVo) throws Exception {

		String ribTir = "0" + chequeACHVo.getRibTir();
		String codUgOpe = ribTir.substring(5, 8);

		Cheque33 c33 = new Cheque33();

		Cheque33Id id = new Cheque33Id();

		c33.setRibTir(ribTir);
		c33.setNumChq(chequeACHVo.getNumChq());

		compensationVo.getBatch().getMsgDetailChq().setText("Chéque :  [Valeur :33,Numéro :" + c33.getNumChq() + "]");

		c33.setCodUgOpe(codUgOpe);
		Long num = 0L;// Long.valueOf(line.substring(56, 66))
		id = new Cheque33Id(c33.getCodUgOpe(), chequeACHVo.getDatOpe(), c33.getNumChq(), num);

		System.out.println(c33.getNumChq());
		if (isCheckInserted(c33.getCodUgOpe(), c33.getNumChq(), "" + num, "33", chequeACHVo.getDatOpe())) {
			c33 = (Cheque33) search.loadForUpdate(Cheque33.class, id);
		}

		c33.setSens(chequeACHVo.getCodSen());
		c33.setCodVal(chequeACHVo.getCodVal());
		c33.setNatRem(chequeACHVo.getCodNatEta());
		c33.setDatOpe(chequeACHVo.getDatOpe());
		c33.setNumLot(chequeACHVo.getNumLot());
		c33.setCodEnr(chequeACHVo.getCodEnr());
		c33.setCodDev(chequeACHVo.getCodDev());
		c33.setMntChq(chequeACHVo.getMntChq());

		c33.setCodRem(chequeACHVo.getCodBan());// line.substring(4, 6));//code banque émitrice

		c33.setCodCenReg(chequeACHVo.getCodAge());// line.substring(73, 93).substring(2, 5));code agence emitrice

		c33.setCodInsDes(chequeACHVo.getCodBanDes());// (line.substring(48, 68).substring(0, 2));//code banque
														// desitination

		c33.setCodCenRegDes(chequeACHVo.getCodAgeDes());// line.substring(48, 68).substring(2, 5));//code agence

		c33.setRibBen("0" + chequeACHVo.getRibTir());
		c33.setNomBen(chequeACHVo.getNomPrn());
		c33.setDatEmi(chequeACHVo.getDatEmi());
		c33.setLieEmi(chequeACHVo.getCodLieEmiChq());
		c33.setSitBen(chequeACHVo.getCodSit());
		c33.setNatCpt("" + (chequeACHVo.getCodNatCpt() != null ? chequeACHVo.getCodNatCpt() : ""));

		c33.setCheque33Id(id);
		c33.setCodSta("N");
		c33.setCmpAuto("B");
		String imgVer = null;
		String imgRec = null;
		if (c33.getRibBen().substring(0, 2).equals("03")) {
			// imgVer = pathImg + File.separator + "AGENCE" + c33.getCodCenReg() +
			// File.separator + "Emis" + File.separator
			// + datImg + File.separator + "33" + File.separator + "Defalc" + File.separator
			// + "Images"
			// + File.separator + line.substring(41, 48) + line.substring(48, 68) +
			// "03V.JPG";
			// imgRec = pathImg + File.separator + "AGENCE" + c33.getCodCenReg() +
			// File.separator + "Emis" + File.separator
			// + datImg + File.separator + "33" + File.separator + "Defalc" + File.separator
			// + "Images"
			// + File.separator + line.substring(41, 48) + line.substring(48, 68) +
			// "03R.JPG";

		} else {

			// imgVer = pathImg + File.separator + "AGENCE" + c33.getCodCenRegDes() +
			// File.separator + "Recu"
			// + File.separator + datImg + File.separator + "33" + File.separator + "Images"
			// + File.separator
			// + line.substring(41, 48) + line.substring(48, 68) + c33.getCodRem() +
			// "V.JPG";
			// imgRec = pathImg + File.separator + "AGENCE" + c33.getCodCenRegDes() +
			// File.separator + "Recu"
			// + File.separator + datImg + File.separator + "33" + File.separator + "Images"
			// + File.separator
			// + line.substring(41, 48) + line.substring(48, 68) + c33.getCodRem() +
			// "R.JPG";

		}
		c33.setImgRec(imgRec);
		c33.setImgVer(imgVer);

		if (!isCheckInserted(c33.getCodUgOpe(), c33.getNumChq(), "" + num, "33", chequeACHVo.getDatOpe())) {
			crudService.create(c33);
		} else {
			crudService.update(c33);
		}
		return c33;

	}

	@Override
	protected void genCroText(ValueObject arg0) {

	}

	public boolean isCheckInserted(String codUg, Long numChq, String num, String codVal, Date datOpe) {
		if (codVal.equals("30")) {
			criteria = search.createCriteria();
			expression = search.createExpression();
			criteria.add(expression.eq("cheque30Id.codUg", codUg));
			criteria.add(expression.eq("numChq", numChq));
			// criteria.add(expression.eq("datJouOpe", datOpe));

			criteria.add(expression.eq("cheque30Id.num", Long.valueOf(num)));
			List<Cheque30> l30 = search.find(Cheque30.class, criteria);
			if (l30.isEmpty())
				return false;
			else
				return true;

		}

		if (codVal.equals("33")) {
			criteria = search.createCriteria();
			expression = search.createExpression();
			criteria.add(expression.eq("cheque33Id.codUg", codUg));
			criteria.add(expression.eq("numChq", numChq));
			criteria.add(expression.eq("cheque33Id.num", Long.valueOf(num)));
			// criteria.add(expression.eq("datJouOpe", datOpe));

			List<Cheque33> l30 = search.find(Cheque33.class, criteria);
			if (l30.isEmpty())
				return false;
			else {
				return true;

			}

		}

		if (codVal.equals("32")) {
			criteria = search.createCriteria();
			expression = search.createExpression();
			criteria.add(expression.eq("numChq", numChq));
			criteria.add(expression.eq("cheque32Id.num", Long.valueOf(num)));
			List<Cheque32> l30 = search.find(Cheque32.class, criteria);
			if (l30.isEmpty())
				return false;
			else {
				return true;

			}

		}

		return false;
	}

	public void appurement(String codUg, String codVal) {

		if (codVal.equals("30")) {
			criteria = search.createCriteria();
			expression = search.createExpression();
			criteria.add(expression.eq("cheque30Id.codUg", codUg));
			criteria.add(expression.ne("cmpAuto", "B"));
			List<Cheque30> l30 = search.find(Cheque30.class, criteria);
			for (Cheque30 c30 : l30)
				crudService.remove(c30);
		}
		if (codVal.equals("33")) {
			criteria = search.createCriteria();
			expression = search.createExpression();
			criteria.add(expression.eq("cheque33Id.codUg", codUg));
			criteria.add(expression.ne("cmpAuto", "B"));
			List<Cheque33> l33 = search.find(Cheque33.class, criteria);

			for (Cheque33 c33 : l33)
				crudService.remove(c33);

		}
	}

}
