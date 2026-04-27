package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * @author nbdour
 * 
 */

public class MoulinetteInsertingTrt extends Traitement {

	Context context = ContextHandler.getContext();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	HibernateTemplate hibTemplate = (HibernateTemplate) context
			.getBean("hibernateTemplate");
	ISearchEngine search = (SearchEngine) context.getBean("searchEngine");
	ICriteria criteria = search.createCriteria();
	IExpression expression = search.createExpression();
	CompensationDAO compensationDAO = (CompensationDAO) context
			.getBean("compensationDAO");
	SimpleDateFormat formatDateImg = new SimpleDateFormat("yyyyMMdd");
	// nombre de cheque
	public static Random random = new Random();
	public String pathChequeTraite;
	public String pathChequeTravail;
	Long mntTot = Long.valueOf(0);
	Long mntIntra = Long.valueOf(0);

	Long nbreTot = Long.valueOf(0);
	Long nbreIntra = Long.valueOf(0);
	private final int SUCCES = 1;
	private final int FAIL = 0;
	String wmmjj = null;
	String datImg;
	String pathImg = File.separator + File.separator
			+ Configuration.getServerPath() + "\\Compensation";
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	CompensationVo compensationVo = new CompensationVo();

	@Override
	public IValueObject perform(IValueObject vo) {
		compensationVo = (CompensationVo) vo;
		SimpleDateFormat formatDate = new SimpleDateFormat("ddMMyyyy");
		String srcFile30, srcFile32, srcFile31, srcFile33 = null;
		String ageBct = null;
		String ageBna = null;
		Date dateSys = new Date();

		try {
			ageBct = StrHandler.lpad(compensationVo.getStrutcure()
					.getCodBctStrc(), '0', 3);
			ageBna = StrHandler.lpad(""
					+ compensationVo.getStrutcure().getCodStrcStrc(), '0', 3);

			wmmjj = formatDate.format(compensationVo.getDateComptable());
			datImg = formatDateImg.format(compensationVo.getDateComptable());
			String path = File.separatorChar + Configuration.getParentPath()
					+ File.separatorChar + Configuration.getLocalPathCheque()
					+ File.separatorChar + "reçu" + File.separatorChar
					+ "cheque" + File.separatorChar + "agence" + ageBct
					+ File.separatorChar + formatDate.format(dateSys)
					+ File.separatorChar;

			//pathChequeTravail = path + "travail" + File.separatorChar;
			pathChequeTravail = File.separatorChar+compensationVo.getNameFile() + File.separatorChar;
			//pathChequeTraite = path + "traite" + File.separatorChar;

			final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

			int returnValue = 1;

			// file 30
			srcFile30 = pathChequeTravail + "03-" + ageBct + "-"
					+ Constants.COD_CHEQUE_PREMIERE_PRESENTATION + "-" + wmmjj
					+ "-788.RCP";
			
			System.out.println(srcFile30);
			if(!SuivFileTrt.isTreated("03-" + ageBct + "-"+ Constants.COD_CHEQUE_PREMIERE_PRESENTATION + "-" + wmmjj+ "-788.RCP")) 
			{
			if (new File(srcFile30).exists()){
				
				 importFromFile(srcFile30, ""
				 + Constants.COD_CHEQUE_PREMIERE_PRESENTATION);
				 SuivFileTrt.ajouterFichierProvAvecMontant("03-" + ageBct + "-"
				 + Constants.COD_CHEQUE_PREMIERE_PRESENTATION + "-" + wmmjj
				 + "-788.RCP", ageBct, compensationVo.getDateComptable(),
				 returnValue,
				 Constants.COD_CHEQUE_PREMIERE_PRESENTATION,mntTot,nbreTot,mntTot-mntIntra,nbreTot-nbreIntra,nbreIntra,mntIntra);
				compensationDAO.appurement(ageBna,"30");

				}
			else {
				
				compensationVo.setDescription("RCP Manquant");

			
		}}
			// file 31

			srcFile31 = pathChequeTravail + "03-" + ageBct + "-"
					+ Constants.COD_CHEQUE_REPRESENTATION_PAIEMENT_PARTIEL
					+ "-" + wmmjj + "-788.RCP";

			if (new File(srcFile31).exists()) {
				try{
				importFromFile(srcFile31, ""
						+ Constants.COD_CHEQUE_REPRESENTATION_PAIEMENT_PARTIEL);
				SuivFileTrt.ajouterFichierProvAvecMontant("03-" + ageBct + "-"
						+ Constants.COD_CHEQUE_REPRESENTATION_PAIEMENT_PARTIEL
						+ "-" + wmmjj + "-788.RCP", ageBct,
						compensationVo.getDateComptable(), returnValue,
						Constants.COD_CHEQUE_REPRESENTATION_PAIEMENT_PARTIEL,
						mntTot, nbreTot, mntTot - mntIntra,
						nbreTot - nbreIntra, nbreIntra, mntIntra);
				}
				catch(Exception e){}
			}
			// file 32
			srcFile32 = pathChequeTravail + "03-" + ageBct + "-"
					+ Constants.COD_CHEQUE_REPRESENTATION_SUITE_ARP + "-"
					+ wmmjj + "-788.RCP";

			if (new File(srcFile32).exists()) {
				 importFromFile(srcFile32, ""
				 + Constants.COD_CHEQUE_REPRESENTATION_SUITE_ARP);
				 SuivFileTrt.ajouterFichierProvAvecMontant("03-" + ageBct + "-"
				 + Constants.COD_CHEQUE_REPRESENTATION_SUITE_ARP + "-"
				 + wmmjj + "-788.RCP", ageBct,
				 compensationVo.getDateComptable(), returnValue,
				 Constants.COD_CHEQUE_REPRESENTATION_SUITE_ARP,mntTot,nbreTot,mntTot-mntIntra,nbreTot-nbreIntra,nbreIntra,mntIntra);
			}

			// file 33

			srcFile33 = pathChequeTravail + "03-" + ageBct + "-"
					+ Constants.COD_CHEQUE_REPRESENTATION_SUITE_PAPILLON + "-"
					+ wmmjj + "-788.RCP";
			
			if(!SuivFileTrt.isTreated("03-" + ageBct + "-"+ Constants.COD_CHEQUE_REPRESENTATION_SUITE_PAPILLON + "-" + wmmjj+ "-788.RCP")) 
			{

			if (new File(srcFile33).exists()) {

				importFromFile(srcFile33, ""
						+ Constants.COD_CHEQUE_REPRESENTATION_SUITE_PAPILLON);
				SuivFileTrt.ajouterFichierProvAvecMontant("03-" + ageBct + "-"
						+ Constants.COD_CHEQUE_REPRESENTATION_SUITE_PAPILLON
						+ "-" + wmmjj + "-788.RCP", ageBct,
						compensationVo.getDateComptable(), returnValue,
						Constants.COD_CHEQUE_REPRESENTATION_SUITE_PAPILLON,
						mntTot, nbreTot, mntTot - mntIntra,
						nbreTot - nbreIntra, nbreIntra, mntIntra);
				compensationDAO.appurement(ageBna,"33");

			} 
			}

		} catch (Throwable e) {
			compensationVo.getBatch().getMsgDetailChq().setForeground(Color.red);
			compensationVo.getBatch().getMsgDetailChq().setText(compensationVo.getBatch().getMsgDetailChq().getText()+ " Erreur !");
			e.printStackTrace();
			SwingInfoVo infoVo = new SwingInfoVo();
			infoVo.setStructure(""+ compensationVo.getStrutcure().getCodStrcStrc());

			infoVo.setEtat("Erreur insertion");
			infoVo.setDateComptable(sdf.format(compensationVo.getDateComptable()));
			compensationVo.getBatch().addOrUpdateEtat(infoVo);
			logger.info(e.getMessage());
			// throw new RuntimeException();
		}

		return compensationVo;

	}

	public void importFromFile(String fichier, String codChq) throws Exception {
		logger.info("Inserting data : " + fichier);

		nbreTot = Long.valueOf(0);
		mntTot = Long.valueOf(0);

		nbreIntra = Long.valueOf(0);
		mntIntra = Long.valueOf(0);

		// Begin Import

		BufferedWriter bufWriter = null;
		FileWriter fileWriter = null;
		InputStream ips = new FileInputStream(fichier);
		InputStreamReader ipsr = new InputStreamReader(ips);
		BufferedReader br = new BufferedReader(ipsr);
		String line;
		while ((line = br.readLine()) != null && !line.equals("FIN") && line.length()>5) {
			if (line.substring(21, 23).equals(
					"" + Constants.COD_ENREGISTREMENT_DETAIL_PRESENTATION)) {
				if (codChq.equals(""
						+ Constants.COD_CHEQUE_PREMIERE_PRESENTATION))
					create30(line);
				if (codChq.equals(""
						+ Constants.COD_CHEQUE_REPRESENTATION_PAIEMENT_PARTIEL))
					create31(line);
				if (codChq.equals(""
						+ Constants.COD_CHEQUE_REPRESENTATION_SUITE_ARP))
					create32(line);
				if (codChq.equals(""
						+ Constants.COD_CHEQUE_REPRESENTATION_SUITE_PAPILLON))
					create33(line);

				nbreTot = nbreTot + 1;
				String codBanqueEm = line.substring(4, 6);
				// si intra
				if (codBanqueEm.equals("03")) {
					nbreIntra = nbreIntra + 1;
					mntIntra = mntIntra + Long.valueOf(line.substring(26, 41));
				}
				mntTot = mntTot + (Long.valueOf(line.substring(26, 41)));
			}

		}

		br.close();
		fileWriter = new FileWriter(fichier, true);
		bufWriter = new BufferedWriter(fileWriter);
		bufWriter.newLine();
		bufWriter.write("FIN");
		bufWriter.close();
		//Util.copy(fichier, pathChequeTraite + new File(fichier).getName());
		//Util.deleteFile(fichier);

	}

	public Cheque30 create30(String line) throws Exception, SQLException {
		Cheque30 c30 = new Cheque30();

		Cheque30Id id = new Cheque30Id();
		String strDatOp = line.substring(9, 17);
		String strDatEm = line.substring(123, 131);
		c30.setRibTir(line.substring(48, 68));
		c30.setNumChq(Long.valueOf(line.substring(41, 48)));
		SimpleDateFormat datOpFmrt = new SimpleDateFormat("yyyyMMdd");
		c30.setCodUgOpe(line.substring(48, 68).substring(5, 8));

		compensationVo
				.getBatch()
				.getMsgDetailChq()
				.setText(
						"Chéque :  [Valeur :30,Numéro :" + c30.getNumChq()
								+ "]");

		id = new Cheque30Id(c30.getCodUgOpe(), datOpFmrt.parse(strDatOp),c30.getNumChq(), Long.valueOf(line.substring(56,66)));
		
		System.out.println(c30.getNumChq());
		if (isCheckInserted(line.substring(48, 68).substring(5, 8),Long.valueOf(line.substring(41, 48)), line.substring(56,66), "30",c30.getDatJouOpe())) {
			c30 = (Cheque30) search.loadForUpdate(Cheque30.class, id);
		}

		c30.setSens(Long.valueOf(line.substring(0, 1)));
		c30.setCodVal(Long.valueOf(line.substring(1, 3)));
		c30.setNatRem(Long.valueOf(line.substring(3, 4)));
		c30.setCodRem(line.substring(4, 6));
		c30.setCodCenReg(line.substring(73, 93).substring(2, 5));
		c30.setCodInsDes(line.substring(48, 68).substring(0, 2));
		c30.setCodCenRegDes(line.substring(48, 68).substring(2, 5));
		c30.setDatOpe(datOpFmrt.parse(strDatOp));
		c30.setDatEmi(datOpFmrt.parse(strDatEm));
		c30.setNumLot(Long.valueOf(line.substring(17, 21)));
		c30.setCodEnr(Long.valueOf(line.substring(21, 23)));
		c30.setCodDev((line.substring(23, 26)));
		c30.setMntChq(Long.valueOf(line.substring(26, 41)));
		c30.setRibBen(line.substring(73, 93));
		c30.setNomBen(line.substring(93, 123));
		c30.setLieEmi(line.substring(131, 132));
		c30.setSitBen(Long.valueOf(line.substring(132, 133)));
		c30.setNatCpt(line.substring(133, 134));
		c30.setMotRej(Long.valueOf(line.substring(134, 142)));
		c30.setCheque30Id(id);
		c30.setCodSta("N");
		c30.setCmpAuto("B");
		String imgVer = "";
		String imgRec = "";
		if (c30.getRibBen().substring(0, 2).equals("03")) {
			imgVer = pathImg + File.separator + "AGENCE" + c30.getCodCenReg()
					+ File.separator + "Emis" + File.separator + datImg
					+ File.separator + "30" + File.separator + "Defalc"
					+ File.separator + "Images" + File.separator
					+ line.substring(41, 48) + line.substring(48, 68)
					+ "03V.JPG";
			imgRec = pathImg + File.separator + "AGENCE" + c30.getCodCenReg()
					+ File.separator + "Emis" + File.separator + datImg
					+ File.separator + "30" + File.separator + "Defalc"
					+ File.separator + "Images" + File.separator
					+ line.substring(41, 48) + line.substring(48, 68)
					+ "03R.JPG";

		} else {

			imgVer = pathImg + File.separator + "AGENCE"
					+ c30.getCodCenRegDes() + File.separator + "Recu"
					+ File.separator + datImg + File.separator + "30"
					+ File.separator + "Images" + File.separator
					+ line.substring(41, 48) + line.substring(48, 68)
					+ c30.getCodRem() + "V.JPG";
			imgRec = pathImg + File.separator + "AGENCE"
					+ c30.getCodCenRegDes() + File.separator + "Recu"
					+ File.separator + datImg + File.separator + "30"
					+ File.separator + "Images" + File.separator
					+ line.substring(41, 48) + line.substring(48, 68)
					+ c30.getCodRem() + "R.JPG";

		}

		c30.setImgRec(imgRec);
		c30.setImgVer(imgVer);

		if (!isCheckInserted(line.substring(48, 68).substring(5, 8),Long.valueOf(line.substring(41, 48)),line.substring(56, 66),"30",c30.getDatOpe())) {
		
			crudService.create(c30);
			System.out.println("Insertion cheque num [" + c30.getNumChq() + "]");

		} else {
			System.out.println("Update cheque num [" + c30.getNumChq() + "]");
			crudService.update(c30);

		}

		return c30;

	}

	public Cheque31 create31(String line) throws NumberFormatException,
			Exception {

		Cheque31 c30 = new Cheque31();

		c30.setSens(Long.valueOf(line.substring(0, 1)));
		c30.setCodVal(Long.valueOf(line.substring(1, 3)));
		c30.setNatRem(Long.valueOf(line.substring(3, 4)));
		c30.setCodInsDes(line.substring(4, 6));
		String strDatOp = line.substring(9, 17);
		String strDatEm = line.substring(93, 101);
		String datCnp = line.substring(102, 110);
		SimpleDateFormat datOpFmrt = new SimpleDateFormat("yyyyMMdd");
		c30.setDatOpe(datOpFmrt.parse(strDatOp));
		c30.setDatEmi(datOpFmrt.parse(strDatEm));
		c30.setDatCnp(datOpFmrt.parse(datCnp));
		c30.setNumLot(Long.valueOf(line.substring(17, 21)));
		c30.setCodEnr(Long.valueOf(line.substring(21, 23)));
		c30.setCodDev((line.substring(23, 26)));
		c30.setMntChq(Long.valueOf(line.substring(26, 41)));
		c30.setNumChq(Long.valueOf(line.substring(41, 48)));

		compensationVo
				.getBatch()
				.getMsgDetailChq()
				.setText(
						"Chéque :  [Valeur :31,Numéro :" + c30.getNumChq()
								+ "]");

		c30.setRibTir(line.substring(48, 68));
		c30.setCodCenRegDes(line.substring(48, 68).substring(2, 5));
		c30.setRibBen(line.substring(73, 93));
		c30.setLieEmi(line.substring(101, 102));
		c30.setNumCnp(Long.valueOf(line.substring(110, 114)));
		c30.setCodDevPos(line.substring(114, 117));
		c30.setMntRec(Long.valueOf(line.substring(117, 132)));
		Cheque31Id id = null;
		id = new Cheque31Id(line.substring(48, 68).substring(5, 8),
				datOpFmrt.parse(strDatOp), Long.valueOf(c30.getRibTir()
						.substring(8, 18)), c30.getNumChq());
		c30.setCheque31Id(id);
		String imgVer = null;
		String imgRec = null;
		if (c30.getRibBen().substring(0, 2).equals("03")) {
			imgVer = pathImg + File.separator + "AGENCE" + c30.getCodCenReg()
					+ File.separator + "Emis" + File.separator + datImg
					+ File.separator + "31" + File.separator + "Defalc"
					+ File.separator + "Images" + File.separator
					+ line.substring(41, 48) + line.substring(48, 68)
					+ "03V.JPG";
			imgRec = pathImg + File.separator + "AGENCE" + c30.getCodCenReg()
					+ File.separator + "Emis" + File.separator + datImg
					+ File.separator + "31" + File.separator + "Defalc"
					+ File.separator + "Images" + File.separator
					+ line.substring(41, 48) + line.substring(48, 68)
					+ "03R.JPG";

		} else {

			imgVer = pathImg + File.separator + "AGENCE"
					+ c30.getCodCenRegDes() + File.separator + "Recu"
					+ File.separator + datImg + File.separator + "31"
					+ File.separator + "Images" + File.separator
					+ line.substring(41, 48) + line.substring(48, 68)
					+ c30.getCodRem() + "V.JPG";
			imgRec = pathImg + File.separator + "AGENCE"
					+ c30.getCodCenRegDes() + File.separator + "Recu"
					+ File.separator + datImg + File.separator + "31"
					+ File.separator + "Images" + File.separator
					+ line.substring(41, 48) + line.substring(48, 68)
					+ c30.getCodRem() + "R.JPG";

		}

		c30.setImgRec(imgRec);
		c30.setImgVer(imgVer);
		c30.setCodSta("N");
		c30.setCmpAuto("B");

		crudService.create(c30);

		return c30;

	}

	public Cheque32 create32(String line) throws NumberFormatException,
			Exception {

		Cheque32 c32 = new Cheque32();

		c32.setSens(Long.valueOf(line.substring(0, 1)));
		c32.setCodVal(Long.valueOf(line.substring(1, 3)));
		c32.setNatRem(Long.valueOf(line.substring(3, 4)));
		String strDatOp = line.substring(9, 17);
		String strDatEm = line.substring(93, 101);
		String datCnp = line.substring(102, 110);
		SimpleDateFormat datOpFmrt = new SimpleDateFormat("yyyyMMdd");
		c32.setDatOpe(datOpFmrt.parse(strDatOp));
		c32.setDatEmi(datOpFmrt.parse(strDatEm));
		c32.setDatCnp(datOpFmrt.parse(datCnp));
		c32.setCodRem(line.substring(4, 6));
		c32.setCodCenReg(line.substring(73, 93).substring(2, 5));
		c32.setRibTir(line.substring(48, 68));
		c32.setCodInsDes(line.substring(48, 68).substring(0, 2));
		c32.setCodCenRegDes(line.substring(48, 68).substring(2, 5));
		c32.setNumLot(Long.valueOf(line.substring(17, 21)));
		c32.setCodEnr(Long.valueOf(line.substring(21, 23)));
		c32.setCodDev((line.substring(23, 26)));
		c32.setMntChq(Long.valueOf(line.substring(26, 41)));
		c32.setNumChq(Long.valueOf(line.substring(41, 48)));
		compensationVo
				.getBatch()
				.getMsgDetailChq()
				.setText(
						"Chéque :  [Valeur :32,Numéro :" + c32.getNumChq()
								+ "]");

		c32.setRibBen(line.substring(73, 93));
		c32.setLieEmi(line.substring(101, 102));
		c32.setNumCnp(Long.valueOf(line.substring(110, 114)));
		c32.setCodDevPos(line.substring(114, 117));
		c32.setMntRec(Long.valueOf(line.substring(117, 132))
				+ Long.valueOf(line.substring(132, 147)));
		Cheque32Id id = null;
		id = new Cheque32Id(line.substring(48, 68).substring(5, 8),datOpFmrt.parse(strDatOp),  c32.getNumChq(),Long.valueOf(line.substring(56, 66)));
		c32.setCheque32Id(id);
//		if (!isCheckInserted(line.substring(48, 68).substring(5, 8),Long.valueOf(line.substring(41, 48)),line.substring(56, 66),"32")) {
//			c32 = (Cheque32) search.loadForUpdate(Cheque32.class, id);
//		}
		String imgVer = null;
		String imgRec = null;
		if (c32.getRibBen().substring(0, 2).equals("03")) {
			imgVer = pathImg + File.separator + "AGENCE" + c32.getCodCenReg()
					+ File.separator + "Emis" + File.separator + datImg
					+ File.separator + "32" + File.separator + "Defalc"
					+ File.separator + "Images" + File.separator
					+ line.substring(41, 48) + line.substring(48, 68)
					+ "03V.JPG";
			imgRec = pathImg + File.separator + "AGENCE" + c32.getCodCenReg()
					+ File.separator + "Emis" + File.separator + datImg
					+ File.separator + "32" + File.separator + "Defalc"
					+ File.separator + "Images" + File.separator
					+ line.substring(41, 48) + line.substring(48, 68)
					+ "03R.JPG";

		} else {

			imgVer = pathImg + File.separator + "AGENCE"
					+ c32.getCodCenRegDes() + File.separator + "Recu"
					+ File.separator + datImg + File.separator + "32"
					+ File.separator + "Images" + File.separator
					+ line.substring(41, 48) + line.substring(48, 68)
					+ c32.getCodRem() + "V.JPG";
			imgRec = pathImg + File.separator + "AGENCE"
					+ c32.getCodCenRegDes() + File.separator + "Recu"
					+ File.separator + datImg + File.separator + "32"
					+ File.separator + "Images" + File.separator
					+ line.substring(41, 48) + line.substring(48, 68)
					+ c32.getCodRem() + "R.JPG";

		}
		c32.setImgRec(imgRec);
		c32.setImgVer(imgVer);
		c32.setCodSta("N");
		c32.setCmpAuto("B");
		
		if (!isCheckInserted(line.substring(48, 68).substring(5, 8),Long.valueOf(line.substring(41, 48)),line.substring(56, 66),"32",c32.getDatJouOpe())) {
			
			// hibTemplate.save(c30);
			// hibTemplate.flush();
			crudService.create(c32);

		} else {
			//crudService.update(c32);

		}
		return c32;


	}

	public Cheque33 create33(String line) throws Exception {
		Cheque33 c33 = new Cheque33();
		Cheque33Id id = new Cheque33Id();
		String strDatOp = line.substring(9, 17);
		String strDatEm = line.substring(123, 131);
		c33.setRibTir(line.substring(48, 68));
		c33.setNumChq(Long.valueOf(line.substring(41, 48)));
		compensationVo
				.getBatch()
				.getMsgDetailChq()
				.setText(
						"Chéque :  [Valeur :33,Numéro :" + c33.getNumChq()
								+ "]");

		SimpleDateFormat datOpFmrt = new SimpleDateFormat("yyyyMMdd");
		c33.setCodUgOpe(line.substring(48, 68).substring(5, 8));

		id = new Cheque33Id(c33.getCodUgOpe(), datOpFmrt.parse(strDatOp),c33.getNumChq(), Long.valueOf(line.substring(56,66)));
		
		System.out.println(c33.getNumChq());
		if (isCheckInserted(line.substring(48, 68).substring(5, 8),Long.valueOf(line.substring(41, 48)), line.substring(56,66), "33",c33.getDatJouOpe())) {
			c33 = (Cheque33) search.loadForUpdate(Cheque33.class, id);
		}
		c33.setSens(Long.valueOf(line.substring(0, 1)));
		c33.setCodVal(Long.valueOf(line.substring(1, 3)));
		c33.setNatRem(Long.valueOf(line.substring(3, 4)));
		c33.setDatOpe(datOpFmrt.parse(strDatOp));
		c33.setNumLot(Long.valueOf(line.substring(17, 21)));
		c33.setCodEnr(Long.valueOf(line.substring(21, 23)));
		c33.setCodDev((line.substring(23, 26)));
		c33.setMntChq(Long.valueOf(line.substring(26, 41)));
		c33.setCodRem(line.substring(4, 6));
		c33.setCodCenReg(line.substring(73, 93).substring(2, 5));
		c33.setCodInsDes(line.substring(48, 68).substring(0, 2));
		c33.setCodCenRegDes(line.substring(48, 68).substring(2, 5));
		c33.setRibBen(line.substring(73, 93));
		c33.setNomBen(line.substring(93, 123));
		c33.setDatEmi(datOpFmrt.parse(strDatEm));
		c33.setLieEmi(line.substring(131, 132));
		c33.setSitBen(Long.valueOf(line.substring(132, 133)));
		c33.setNatCpt(line.substring(133, 134));
		//id = new Cheque33Id(line.substring(48, 68).substring(5, 8),datOpFmrt.parse(strDatOp), Long.valueOf(c30.getRibTir().substring(8, 18)), c30.getNumChq());
		c33.setCheque33Id(id);
		c33.setCodSta("N");
		c33.setCmpAuto("B");
		String imgVer = null;
		String imgRec = null;
		if (c33.getRibBen().substring(0, 2).equals("03")) {
			imgVer = pathImg + File.separator + "AGENCE" + c33.getCodCenReg()
					+ File.separator + "Emis" + File.separator + datImg
					+ File.separator + "33" + File.separator + "Defalc"
					+ File.separator + "Images" + File.separator
					+ line.substring(41, 48) + line.substring(48, 68)
					+ "03V.JPG";
			imgRec = pathImg + File.separator + "AGENCE" + c33.getCodCenReg()
					+ File.separator + "Emis" + File.separator + datImg
					+ File.separator + "33" + File.separator + "Defalc"
					+ File.separator + "Images" + File.separator
					+ line.substring(41, 48) + line.substring(48, 68)
					+ "03R.JPG";

		} else {

			imgVer = pathImg + File.separator + "AGENCE"
					+ c33.getCodCenRegDes() + File.separator + "Recu"
					+ File.separator + datImg + File.separator + "33"
					+ File.separator + "Images" + File.separator
					+ line.substring(41, 48) + line.substring(48, 68)
					+ c33.getCodRem() + "V.JPG";
			imgRec = pathImg + File.separator + "AGENCE"
					+ c33.getCodCenRegDes() + File.separator + "Recu"
					+ File.separator + datImg + File.separator + "33"
					+ File.separator + "Images" + File.separator
					+ line.substring(41, 48) + line.substring(48, 68)
					+ c33.getCodRem() + "R.JPG";

		}
		c33.setImgRec(imgRec);
		c33.setImgVer(imgVer);

		if (!isCheckInserted(line.substring(48, 68).substring(5, 8),Long.valueOf(line.substring(41, 48)),line.substring(56, 66),"33",c33.getDatJouOpe())) {
			
			// hibTemplate.save(c30);
			// hibTemplate.flush();
			crudService.create(c33);

		} else 
		{			

			crudService.update(c33);

		}
		return c33;

	}

	@Override
	protected void genCroText(ValueObject arg0) {

	}

	public boolean isCheckInserted(String codUg, Long numChq, String num,
			String codVal,Date datOpe) {
		if (codVal.equals("30")) {
			criteria = search.createCriteria();
			expression = search.createExpression();
			criteria.add(expression.eq("cheque30Id.codUg", codUg));
			criteria.add(expression.eq("numChq", numChq));
			//criteria.add(expression.eq("datJouOpe", datOpe));

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
			//criteria.add(expression.eq("datJouOpe", datOpe));

			List<Cheque33> l30 = search.find(Cheque33.class, criteria);
			if (l30.isEmpty())
				return false;
			else
			{
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
			else
			{
				return true;
				
			}

		}

		return false;
	}

	public void appurement(String codUg,String codVal) {
		
		if(codVal.equals("30")) {
		criteria = search.createCriteria();
		expression = search.createExpression();
		criteria.add(expression.eq("cheque30Id.codUg", codUg));
		criteria.add(expression.ne("cmpAuto", "B"));
		List<Cheque30> l30 = search.find(Cheque30.class, criteria);
		for (Cheque30 c30 : l30)
			crudService.remove(c30);
		}
		if(codVal.equals("33")) {
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
