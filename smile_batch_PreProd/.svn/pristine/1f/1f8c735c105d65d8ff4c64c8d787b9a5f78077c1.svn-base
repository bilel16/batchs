package com.bna.smile.model.domaineguichet.traitement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class MAJSoldesTrt extends Traitement {

	public MAJSoldesTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	BufferedWriter bufWriter = null;

	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

	public IValueObject perform(IValueObject vo) {
		this.setVerifDomaine(false);
		this.setCroFlag(false);
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");

		String srcFile = "D:\\MAJNSISAUV\\SLDJOUR";
		String srcFileLog = "D:\\MAJNSISAUV\\SLDJOURLog" + formatter.format(new Date());
		String line = "";
		try {

			FileWriter fileWriterResult = null;
			fileWriterResult = new FileWriter(srcFileLog, true);
			bufWriter = new BufferedWriter(fileWriterResult);

			InputStream ips = new FileInputStream(srcFile);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);

			while ((line = br.readLine()) != null) {

				try {
					ContratCptId contratCptId = new ContratCptId();
					contratCptId.setCodStrcStrc(new Long(line.trim().substring(0, 3)));
					contratCptId.setCodPrdPrd(new Long(line.trim().substring(3, 7)));
					contratCptId.setNumCcptCcpt(new Long(line.trim().substring(7, 13)));

					ContratCpt contratCpt = (ContratCpt) searchEngine.loadForUpdate(ContratCpt.class, contratCptId);

					if (contratCpt == null) {
						bufWriter.write(line.trim().substring(0, 3) + " " + line.trim().substring(3, 7) + " "
								+ line.trim().substring(7, 13) + " ** Compte inexistant");
						bufWriter.newLine();
					} else {
						Long mnt = new Long(line.trim().substring(14, 29));
						if (line.trim().substring(30, 32).equals("DB"))
							mnt *= (-1);

						contratCpt.setMontSoldCcpt(mnt);
						crudService.update(contratCpt);
					}

				} catch (Exception e) {

					bufWriter.write(e.toString() + " : " + line);
					bufWriter.newLine();
				}
			}
			br.close();

			System.out.println("  ");
			System.out.println("  *************** Fin *****************");
			System.out.println("  ");
			bufWriter.close();

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans MAJSoldesTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("MAJSoldesTrt");
			logger.error("Exception : ", e);
			vo.addError(erreur);

			try {
				bufWriter.write(e.toString() + " : " + line);
				bufWriter.newLine();
				bufWriter.close();
			} catch (Exception e2) {

			}
			// throw new RuntimeException(e);

		}
		return vo;
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

}
