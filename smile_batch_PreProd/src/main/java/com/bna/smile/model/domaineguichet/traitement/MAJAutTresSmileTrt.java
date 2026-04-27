package com.bna.smile.model.domaineguichet.traitement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
import com.bna.smile.model.domaineguichet.model.MAJNSIVo;
import com.bna.smile.model.domaineguichet.service.GuichetService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class MAJAutTresSmileTrt extends Traitement {

	public MAJAutTresSmileTrt() {
	}

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	BufferedWriter bufWriter = null;

	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
	int nbExcep = 0;

	public IValueObject perform(IValueObject vo) {
		this.setVerifDomaine(false);
		this.setCroFlag(false);
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");

		String srcFile = "D:\\MAJNSISAUV\\bna";
		String srcFileLog = "D:\\MAJNSISAUV\\bnaLog" + formatter.format(new Date());
		String line = "";
		try {

			FileWriter fileWriterResult = null;
			fileWriterResult = new FileWriter(srcFileLog, true);
			bufWriter = new BufferedWriter(fileWriterResult);

			InputStream ips = new FileInputStream(srcFile);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);

			while ((line = br.readLine()) != null) {

				MAJNSIVo mAJNSIVo = new MAJNSIVo();
				mAJNSIVo.setLigne(line);
				mAJNSIVo.setBufWriter(bufWriter);
				GuichetService guichetService = (GuichetService) context.getBean("guichetService");
				try {
					mAJNSIVo = (MAJNSIVo) guichetService.MAJNSIAutTres(mAJNSIVo);
				} catch (Exception e) {

					nbExcep++;
				}

			}
			br.close();

			System.out.println("  ");
			System.out.println("  *************** Fin *****************");
			System.out.println("  ");
			bufWriter.close();

			File file = new File("D:\\MAJNSISAUV\\bna");
			File file2 = new File("D:\\MAJNSISAUV\\bna" + formatter.format(new Date()));
			file.renameTo(file2);

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans MAJAutTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("MAJAutSmileTrt");
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
