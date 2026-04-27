package com.bna.smile.model.banqueAssurance.traitement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.model.ContratAssuranceVoyage;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailAssuranceVoyage;
import com.bna.commun.model.MontantMiseDiposition;
import com.bna.commun.model.Structure;
import com.bna.commun.model.TarifAssuranceVoyage;
import com.bna.commun.model.TraceAssuranceVoyage;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.CalanderHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.batch.test.BatchFrame;
import com.bna.smile.model.banqueAssurance.dao.AssuranceVoyageDAO;
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
	private BatchFrame mainFrame;

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
	public IValueObject perform(IValueObject vo) throws ParseException {


		SimpleDateFormat formatDateFile = new SimpleDateFormat("ddMMyyyy");
//		String jjmmyyyySys = formatDateFile.format(DateHandler.strToDate("14/01/2019"));
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
		Context context = ContextHandler.getContext();
		ICriteria criteria = searchEngine.createCriteria();

		
		List<ContratAssuranceVoyage> result =  searchEngine.find(ContratAssuranceVoyage.class,criteria);
		System.out.println("result "+result.size());
       


		if(result.size()>0) {
			for (int i = 0; i < result.size(); i++) {
				ContratAssuranceVoyage contratAssuranceVoyage = result.get(i);
				for(TraceAssuranceVoyage traceAssuranceVoyage : contratAssuranceVoyage.getTraceAssuranceVoyages()) {
					if(formatDateFile.format(traceAssuranceVoyage.getDateTracev()).equals(jjmmyyyySys)) {
//				}
//				if(formatDateFile.format(contratAssuranceVoyage.getDateCrtCassv()).equals(jjmmyyyySys)) {
						String codeOperation ="";
						String dateOperation = formatDateFile.format(traceAssuranceVoyage.getDateTracev());
						if(traceAssuranceVoyage.getOperation().getCodOperOper().equals(Long.valueOf("2317")))
							codeOperation="S";
						if(traceAssuranceVoyage.getOperation().getCodOperOper().equals(Long.valueOf("2318")))
							codeOperation="A";
						if(traceAssuranceVoyage.getOperation().getCodOperOper().equals(Long.valueOf("2320")))
							codeOperation="M";
				String dateAdh = formatDateFile.format(contratAssuranceVoyage.getDateCrtCassv());
				String numAdh = contratAssuranceVoyage.getNumCrtCassv();
				TarifAssuranceVoyage tarif = new TarifAssuranceVoyage();
				tarif=(TarifAssuranceVoyage)searchEngine.get(TarifAssuranceVoyage.class,contratAssuranceVoyage.getTarifAssuranceVoyage().getCodTassTassv());
				String typeAdh = tarif.getTypeAdhTassv();
				String codeAgence = StrHandler.rpad("" + contratAssuranceVoyage.getCodStrCassv(), '0', 3);
				String dateDepart =formatDateFile.format(contratAssuranceVoyage.getDateDebCassv());
				String dateRetour =formatDateFile.format(contratAssuranceVoyage.getDateFinCassv());
				String destination = contratAssuranceVoyage.getPays().getCodPaysPays();
				String numPassport =String.format("%1$-10s",contratAssuranceVoyage.getNumPasseportCassv());
				String nomPrenom =String.format("%1$-60s", contratAssuranceVoyage.getNomBenfCassv()+" "+contratAssuranceVoyage.getPrnBenfCassv());
				String dateNais = formatDateFile.format(contratAssuranceVoyage.getDateNaisCassv());
				TarifAssuranceVoyage tarifAssuranceVoyageNew = new TarifAssuranceVoyage();
				tarifAssuranceVoyageNew.setCodTassTassv(contratAssuranceVoyage.getTarifAssuranceVoyage().getCodTassTassv());
				ICriteria criteriaTarif = searchEngine.createCriteria();
				IExpression expressionTarif = searchEngine.createExpression();
				criteriaTarif.add(expressionTarif.eq("codTassTassv", tarifAssuranceVoyageNew.getCodTassTassv()));
				TarifAssuranceVoyage tt = new TarifAssuranceVoyage();
				List resTarif =  searchEngine.find(TarifAssuranceVoyage.class, criteriaTarif);
				tt = (TarifAssuranceVoyage) resTarif.get(0);
				String montantPrimeTotaleAss = String.format("%015d",tt.getPrmTotTassv());
				String montantRetenuSource=null;
				if(contratAssuranceVoyage.getRetSourceCassv()!= null)
					montantRetenuSource = String.format("%015d",contratAssuranceVoyage.getRetSourceCassv());
				else 
					montantRetenuSource = String.format("%015d",0L);

				String ligne = "";
				ligne =
						codeOperation+dateOperation+dateAdh + numAdh +typeAdh+ dateDepart + dateRetour + destination
						+ numPassport + nomPrenom + dateNais+montantPrimeTotaleAss+montantRetenuSource ;
//				if(typeAdh.equals("I")) {
				
				listeLignes.add(ligne);

				output.add(codeOperation);
				output.add(dateOperation);
				output.add(dateAdh);
				output.add(numAdh);
				output.add(typeAdh);
//				output.add(codeAgence);
				output.add(dateDepart);
				output.add(dateRetour);
				output.add(destination);
				output.add(numPassport);
				output.add(nomPrenom);
				output.add(montantPrimeTotaleAss);
				output.add(montantRetenuSource);
				output.add("\n");
//				} 
				if(typeAdh.equals("F")) {
					AssuranceVoyageDAO assuranceVoyageDAO = (AssuranceVoyageDAO) ContextHandler.getContext().getBean("assuranceVoyageDAO");
					List<DetailAssuranceVoyage> listeFamille = new ArrayList<DetailAssuranceVoyage>();
					listeFamille = assuranceVoyageDAO.getlisteFamille(contratAssuranceVoyage.getNumCrtCassv());
					for(DetailAssuranceVoyage detailAssuranceVoyage : listeFamille) {
						String ligneDetail = "";
					ligneDetail =
							codeOperation+dateOperation+dateAdh + numAdh +typeAdh+ dateDepart + dateRetour + destination
							+ String.format("%1$-10s",detailAssuranceVoyage.getNumPasseportDassv())+ 
							String.format("%1$-60s", detailAssuranceVoyage.getNomBenfDassv()+" "+detailAssuranceVoyage.getPrnBenfDassv())
							+ formatDateFile.format(detailAssuranceVoyage.getDateNaisDassv())+montantPrimeTotaleAss+ montantRetenuSource;
					listeLignes.add(ligneDetail);

					output.add(codeOperation);
					output.add(dateOperation);
					output.add(dateAdh);
					output.add(numAdh);
					output.add(typeAdh);
//					output.add(codeAgence);
					output.add(dateDepart);
					output.add(dateRetour);
					output.add(destination);
					output.add(String.format("%1$-10s",detailAssuranceVoyage.getNumPasseportDassv()));
					output.add(String.format("%1$-10s",detailAssuranceVoyage.getNomBenfDassv()+detailAssuranceVoyage.getPrnBenfDassv()));
					output.add(formatDateFile.format(detailAssuranceVoyage.getDateNaisDassv()));
					output.add(montantPrimeTotaleAss);
					output.add(montantRetenuSource);
					output.add("\n");
					
				}
				}

			}
				}
		}
		}

		System.out.println("output"+output.size());


if (output.size() > 0)
	if (output.get(output.size() - 1).equals("\n"))
		output.remove(output.size() - 1);

for (int j = 0; j < output.size(); j++) {
	System.out.println(output.get(j).length() + ":" + output.get(j));
	res += output.get(j);

}
System.out.println("*************");
System.out.println("MVTAMI " + res.length() + ": " + res);

SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
String dateFichier = dateFormat.format(new Date());
String fichierMNG = "MVTAMI_" + dateFichier;

try {
	String tempDir = "C:\\AMI";

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

public BatchFrame getMainFrame() {
	return mainFrame;
}

public void setMainFrame(BatchFrame mainFrame) {
	this.mainFrame = mainFrame;
}
}

