package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.model.Structure;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.batch.test.BatchFrameMoneyGram;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationEffetVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * @author BNA
 * 
 */
public class EnvoisFichierTrt extends Traitement {

	Context context = ContextHandler.getContext();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	ISearchEngine search = (SearchEngine) context.getBean("searchEngine");
	ICriteria criteria = search.createCriteria();
	IExpression expression = search.createExpression();
	CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");
	File imgCheques[] = null;
	CompensationEffetVo compensationVo;
	String ageDes = "";
	String banDes = "";
	private BatchFrameMoneyGram mainFrame;

	public BatchFrameMoneyGram getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(BatchFrameMoneyGram mainFrame) {
		this.mainFrame = mainFrame;
	}

	private String formatString(int number, String value) {
		String res = "";
		value = value.trim();

		for (int i = 0; i < number; i++) {
			if (i < value.length())
				res += value.charAt(i);
			else
				res += " ";

		}
		return res;
	}

	@Override
	public IValueObject perform(IValueObject vo) {

		compensationVo = (CompensationEffetVo) vo;
		mainFrame.getInfolabel().setText("");

		SimpleDateFormat formatDateFile = new SimpleDateFormat("ddMMyyyy");
		String jjmmyyyySys = formatDateFile.format(new Date());
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

		IExpression expression = searchEngine.createExpression();

		String res = "";
		List<String> output = new ArrayList<String>();
		List<String> listeLignes = new ArrayList<String>();

		Long nbreGlobal = 0L;
		Long nbreGlobalFichier = 0L;
		/********* MNG *********/

		// isertion de donnees BD
		List listAgences = compensationDAO.getListAgencesCompensation();
		ListOrderedMap ListAg = null;

		if (listAgences != null && listAgences.size() > 0) {
			for (Iterator it1 = listAgences.iterator(); it1.hasNext();) {
				ListAg = (ListOrderedMap) it1.next();

				if ((ListAg.getValue(0)).toString() != null && (ListAg.getValue(1)).toString() != null) {
					String dateCompStrc = ListAg.getValue(1).toString();
					Structure strc = compensationDAO.findStructure(new Long((ListAg.getValue(0)).toString()));
					if (strc.getTypeStructure().getCodTstrTstr().equals(1L)
							|| strc.getTypeStructure().getCodTstrTstr().equals(6L)
							|| strc.getTypeStructure().getCodTstrTstr().equals(7L)) {

						CompensationEffetVo compensationVo = new CompensationEffetVo();
						// compensationVo.setDateComptable(DateHandler.strToDate("08/12/2015"));
						// compensationVo.setDateComptable(DateHandler.strToDate(dateCompStrc));

						// date comptable -1

						compensationVo.setDateComptable(CalanderHandler.getDateOuvrableBeforeNDays(
								DateHandler.strToDate(dateCompStrc), -1));

						compensationVo.setStructure(strc);
						strc.setCodBctStrc(StrHandler.lpad(strc.getCodBctStrc(), '0', 3));
						List<MontantMiseDiposition> liste = new ArrayList<MontantMiseDiposition>();
						ICriteria criteria = searchEngine.createCriteria();
						criteria.add(expression.eq("structureByCodEmetStrc.codStrcStrc", strc.getCodStrcStrc()));
						criteria.add(expression.eq("datMmadMmad", compensationVo.getDateComptable()));
						criteria.add(expression.eq("codEtatMmad", "V"));
						criteria.add(expression.eq("montantMiseDipositionId.codTypeMmad", "RMG"));
						// criteria.add(expression.isNull("datValMmad"));

						liste = searchEngine.find(MontantMiseDiposition.class, criteria);
						nbreGlobal += liste.size();
						System.out.println("******RMG :*******" + liste.size() + " mvt pour strc : "
								+ strc.getCodStrcStrc());
						res = "";
						// output = new ArrayList<String>();
						GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();
						String quantieme =
								StrHandler.lpad(String.valueOf(new Double(generateReferenceInterSiege
										.getQuantieme(compensationVo.getDateComptable())).intValue()), '0', 3);

						for (int i = 0; i < liste.size(); i++) {
							MontantMiseDiposition mnt = liste.get(i);
							String codeStart = "001";
							String dateOperation = formatDateFile.format(mnt.getDatMmadMmad());
							String codeAgence = StrHandler.lpad("" + strc.getCodStrcStrc(), '0', 3);
							String ref1 = "1104000910";
							String codeOperation = "0201";
							String codeTransaction = "00000009";
							String dateComptable =
									formatDateFile.format(CalanderHandler.getDateOuvrableBeforeNDays(
											mnt.getDatMmadMmad(), -1));// Date valeur
							System.out.println(dateOperation + "/" + dateComptable);
							String mntOperation = StrHandler.lpad("" + mnt.getMontMontMmad(), '0', 15);
							String signe = "+";
							// String refInter = codeAgence + quantieme + "H28";
							// Prendre la référence du mouvement directement sans interventions
							String refInter = mnt.getCodRefInter();
							String soldeCpt = "000000000000000";
							String signeCpt = "+";
							String strcCtr = "0000";
							String numPiece = StrHandler.lpad("" + mnt.getNumPceMmad(), '0', 10);// 10positionpas 15
							// assurer les 6 positions de l'autorisation
							String codAutorisation = "";
							if (mnt.getNumCartMmad().length() <= 6) {
								codAutorisation = StrHandler.lpad(mnt.getNumCartMmad(), '0', 6);
							} else {
								codAutorisation = mnt.getNumCartMmad().substring(0, 6);
							}
							// assurer les 12 positions du nomBen
							String nomBen =
									formatString(12, mnt.getNomPrnMmad().trim() + " " + mnt.getNomNomMmad().trim());
							if (nomBen.length() > 12) {
								nomBen = nomBen.substring(0, 12);
							}
							String codePays = "";
							if (mnt.getPays() != null && mnt.getPays().getCodPaysPays() != null) {
								codePays = "" + compensationDAO.getCodeBctPays(mnt.getPays().getCodPaysPays()) + " ";
							} else {
								codePays = "000" + " ";
							}
							// 8 positions
							String refMoney = "";
							refMoney = mnt.getCodConfMmad();
							if (refMoney.length() <= 8) {
								refMoney = StrHandler.lpad(refMoney, '0', 8);
							} else {
								refMoney = refMoney.substring(0, 8);
							}
							String endStrc = "00000.00";// 5 zeros suivis de deux zéros

							mnt.setDatValMmad(DateHandler.strToDate(dateCompStrc));
							CRUDservice cruDservice = new CRUDservice();
							crudService.update(mnt);

							String ligne = "";
							ligne =
									codeStart + dateOperation + codeAgence + ref1 + codeOperation + codeTransaction
											+ dateComptable + mntOperation + signe + refInter + soldeCpt + signeCpt
											+ strcCtr + numPiece + codAutorisation + nomBen + codePays + refMoney
											+ endStrc;
							listeLignes.add(ligne);

							output.add(codeStart);
							output.add(dateOperation);
							output.add(codeAgence);
							output.add(ref1);
							output.add(codeOperation);
							output.add(codeTransaction);
							output.add(dateComptable);
							output.add(mntOperation);
							output.add(signe);
							// Prendre la référence du mouvement directement sans interventions
							output.add(refInter);
							output.add(soldeCpt);
							output.add(signeCpt);
							output.add(strcCtr);
							output.add(numPiece);
							output.add(codAutorisation);
							output.add(nomBen);
							output.add(codePays);
							output.add(refMoney);
							output.add(endStrc);
							output.add("\n");

						}

					}

				}
			}

		}

		if (output.size() > 0)
			if (output.get(output.size() - 1).equals("\n"))
				output.remove(output.size() - 1);

		for (int j = 0; j < output.size(); j++) {
			System.out.println(output.get(j).length() + ":" + output.get(j));
			res += output.get(j);

		}
		System.out.println("*************");
		System.out.println("MVTMNG " + res.length() + ": " + res);

		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		String dateFichier = dateFormat.format(new Date());
		String fichierMNG = "MVTMNG_" + dateFichier + ".910";

		try {
			String tempDir = "C:\\MoneyGram";

			File file = new File(tempDir + File.separatorChar + fichierMNG);
			if (file.exists() && file.length() > 0) {
				file.delete();
			}
			if (!file.exists())
				file.createNewFile();

			for (String ligneFichier : listeLignes) {
				writeToFile(file, ligneFichier);
				nbreGlobalFichier++;

			}
			// FileUtils.writeStringToFile(file, res);

			// Util.copy(rootPath + File.separatorChar + pathAg + File.separatorChar + fichierMNG, succesPath
			// + File.separatorChar + fichierMNG);
			// boolean succes = Util.sendFileFTP(succesPath + File.separatorChar + fichierMNG, remotePath + fichierMNG);

		} catch (IOException e) {
			e.printStackTrace();

		}
		mainFrame.getInfolabel().setText(
				"Exporation terminée : " + nbreGlobal + " mouvement(s)  ==> " + nbreGlobalFichier
						+ " ligne(s) exportées");
		logger.info("Exporation done..");
		return compensationVo;

	}

	@Override
	protected void genCroText(ValueObject arg0) {
		// TODO Auto-generated method stub

	}

	public static void writeToFile(File file, String text) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(text);
			bw.newLine();
			bw.close();
		} catch (Exception e) {
		}
	}

}
