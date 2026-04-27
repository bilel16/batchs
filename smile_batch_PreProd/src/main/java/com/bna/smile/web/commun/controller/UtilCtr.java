package com.bna.smile.web.commun.controller;

import java.net.InetAddress;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.bna.commun.model.Adresse;
import com.bna.commun.model.CertificationCheques;
import com.bna.commun.model.Cheque;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.PariteOfficielle;
import com.bna.commun.model.PariteOfficielleId;
import com.bna.commun.model.Personne;
import com.bna.commun.traitements.UpdateSoldTrt;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.ContratCptSold;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainechange.commande.GetCoursPariteOffCmd;
import com.bna.smile.model.domainecommun.commande.GetContratCptByIdCmd;
import com.bna.smile.model.domainecommun.traitement.ExonerationTvaDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.model.ParamRechercheOpposition;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.searchengine.SearchEngine;

public abstract class UtilCtr {

	private static final Log LOGGER = LogFactory.getLog(UtilCtr.class);
	public static Context context = ContextHandler.getContext();
	public static List<String> splitInChunks(String s, int chunkSize) {
		List<String> result = new ArrayList<String>();
		if (chunkSize > 0) {
			int length = s.length();
			for (int i = 0; i < length; i += chunkSize) {
				result.add(s.substring(i, Math.min(length, i + chunkSize)));
			}
		}
		return result;
	}
	public static ContratCpt getContratCptByRIB(String ribClient) {
		try {
			ribClient = StrHandler.lpad(ribClient, '0', 20);
			ContratCpt contratCpt = new ContratCpt();
			ContratCptId contratCptId = new ContratCptId();
			System.out.println(ribClient.substring(5, 8) + "/" + ribClient.substring(8, 12) + "/"
					+ ribClient.substring(12, 18));
			contratCptId.setCodStrcStrc(Long.valueOf(ribClient.substring(5, 8)));
			contratCptId.setCodPrdPrd(Long.valueOf(ribClient.substring(8, 12)));
			contratCptId.setNumCcptCcpt(Long.valueOf(ribClient.substring(12, 18)));
			contratCpt.setContratCptId(contratCptId);
			GetContratCptByIdCmd getContratCptByIdCmd = new GetContratCptByIdCmd();
			ContratCpt contratCptRetrieve = new ContratCpt();
			contratCptRetrieve = (ContratCpt) getContratCptByIdCmd.execute(contratCpt);
			return contratCptRetrieve;
		} catch (NumberFormatException e) {
			LOGGER.error("Cannot format : " + e.getMessage());
			return null;
		}

	}

	/**
	 * @author Ayari haythem
	 * 
	 * @return :court achat bna
	 */
	public static Double getCoursAchatBna(String codeDev) {
		if (!codeDev.equals("788")) {
			CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");
			Double coursAchat = compensationDAO.getCoursAchatBna(codeDev);
			return coursAchat;
		} else {
			return new Double(1);
		}
	}

	/**
	 * @author bdour
	 * @param Date
	 * @return quantieme
	 */

	public static double getQuantieme(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		return gc.get(GregorianCalendar.DAY_OF_YEAR);
	}

	/**
	 * @author nbdour
	 * @param mnt
	 *            : montant en dinar
	 * @param codDev
	 *            : code devise
	 * @param jrn
	 *            : date du jour
	 * @return :Conversion du montant ( dinar) en devise
	 */
	/**
	 * @author nbdour
	 * @param mnt
	 *            : montant en dinar
	 * @param codDev
	 *            : code devise
	 * @param jrn
	 *            : date du jour
	 * @return :Conversion du montant ( en dinar) en devise selon court achat bna
	 */

	public static Long calculMntDev(Long mnt, Long codDev, Date jrn) {
		CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");
		// Double coursAchat = caisseDAO.getCoursAchatBna(""+codDev, jrn);
		Double coursAchat = compensationDAO.getCoursAchatBna("" + codDev);
		Long nbreUnit = compensationDAO.getNbreUnitDev(codDev);
		Double var = Math.rint(mnt * (nbreUnit / coursAchat));
		Long mntDev = var.longValue();
		return mntDev;

	}

	public static Long changeDeviseToTND(Long mnt, Long decDev, Long uniteDev, Double coursChange) {
		Double montant;
		if (mnt != null && decDev != null && uniteDev != null && coursChange != null) {
			montant = (double) Math.round(((mnt / Math.pow(10, decDev) * 1000 * coursChange) / uniteDev));
			montant = Math.rint(montant);
			return montant.longValue();
		} else {
			return null;
		}

	}

	public static Long changeConverti(ContratCpt cpt, Long mnt, Double coursChange, Double coursFixe) {

		// *** Montant Commission ****//
		Long montantDevise =
				UtilCtr.changeTNDToDevise(mnt, cpt.getDevise().getNbrDecDev(), cpt.getDevise().getNbrUnitDev(),
						coursChange);
		return UtilCtr.changeDeviseToTND(montantDevise, cpt.getDevise().getNbrDecDev(),
				cpt.getDevise().getNbrUnitDev(), coursFixe);

	}

	public static boolean isDinarConvertible(ContratCpt cpt) {
		if (cpt != null) {
			Integer codPrdPrd = cpt.getProduit().getCodPrdPrd().intValue();
			return Arrays.asList(Constants.listCompteEnDinarsConvertibles).contains(codPrdPrd);
		}
		return false;

	}

	public static Long changeTNDToDevise(Long mnt, Long decDev, Long uniteDev, Double coursChange) {
		Double montant;
		if (mnt != null && uniteDev != null && coursChange != null) {

			montant = (double) Math.round((((mnt / 1000) * uniteDev) / coursChange) * Math.pow(10, decDev));
			montant = Math.rint(montant);
			return montant.longValue();
		} else {
			return null;
		}

	}

	/**
	 * @author nbdour
	 * @param cpt
	 *            : contrat to be updated
	 * @param sensCrDb
	 *            : DB or CR
	 * @param montant
	 *            :
	 * @param dateComptable
	 *            : Date Comptable
	 * @return cpt : the updated contrat
	 */

	/**
	 * @author nbdour
	 * @param cpt
	 *            : contrat to be updated
	 * @param sensCrDb
	 *            : DB or CR
	 * @param montant
	 *            :
	 * @param dateComptable
	 *            : Date Comptable
	 * @return cpt : the updated contrat
	 */
	public static ContratCpt updateSolde(ContratCpt cpt, String sensCrDb, Long montant, Date dateComptable) {
		HibernateTemplate hibernateTemplate =
				(HibernateTemplate) ContextHandler.getContext().getBean("hibernateTemplate");
		cpt = (ContratCpt) hibernateTemplate.get(ContratCpt.class, cpt.getContratCptId());
		if (!cpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_RESILIE)
				&& !cpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_TCONTENTIEU)) {
			ContratCptSold contratCptSold = new ContratCptSold();
			UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
			contratCptSold.setContratCpt(cpt);
			contratCptSold.setSens(sensCrDb);

			// Checking Devise
			if (cpt.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
				contratCptSold.setSens(sensCrDb);
				contratCptSold.setSolde(montant);
				
				System.out.println("Mise à jour solde  effectué : chéque en dinar tiré sur compte en Dinar");
				LOGGER.error("Mise à jour solde  effectué : chéque en dinar tiré sur compte en Dinar");


			} else {
				CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");
				Double coursAchat = compensationDAO.getCoursAchatBna("" + cpt.getDevise().getCodDevDev());

				Long montantDevise =
						changeTNDToDevise(montant, cpt.getDevise().getNbrDecDev(), cpt.getDevise().getNbrUnitDev(),
								coursAchat);
				Double coursFixe = UtilCtr.getCoursFixe(dateComptable, cpt.getDevise().getCodDevDev());
				Long mmontantDinar =
						changeDeviseToTND(montantDevise, cpt.getDevise().getNbrDecDev(), cpt.getDevise()
								.getNbrUnitDev(), coursFixe);

				// Set Montant Devise and Montant Dinar
				contratCptSold.setSoldeDevise(montantDevise);
				contratCptSold.setSolde(mmontantDinar);

				System.out.println("Mise à jour solde  effectué : chéque en dinar tiré sur compte en devise");
				LOGGER.error("Mise à jour solde  effectué : chéque en dinar tiré sur compte en devise");

			}
			cpt = (ContratCpt) updateSoldTrt.exec(contratCptSold);

		} else {
			System.out.println("Mise à jour solde non effectué : contrat cloturé ou transféré à contencieux");
			LOGGER.info("Mise à jour solde non effectué : contrat cloturé ou transféré à contencieux");
		}
		return cpt;
	}

	public static ContratCpt updateSoldeDevDin(ContratCpt cpt, String sensCrDb, Long montantDin, Long montantDev) {
		HibernateTemplate hibernateTemplate =
				(HibernateTemplate) ContextHandler.getContext().getBean("hibernateTemplate");
		cpt = (ContratCpt) hibernateTemplate.get(ContratCpt.class, cpt.getContratCptId());
		ContratCptSold contratCptSold = new ContratCptSold();
		UpdateSoldTrt updateSoldTrt = new UpdateSoldTrt();
		contratCptSold.setContratCpt(cpt);
		contratCptSold.setSens(sensCrDb);

		// Checking Devise
		if (cpt.getDevise().getCodDevDev().equals(Constants.COD_DEV_DINAR)) {
			contratCptSold.setSolde(montantDin);

		} else {
			// Set Montant Devise and Montant Dinar
			contratCptSold.setSoldeDevise(montantDev);
			contratCptSold.setSolde(montantDin);

		}
		cpt = (ContratCpt) updateSoldTrt.exec(contratCptSold);

		return cpt;
	}

	/**
	 * @author nbdour
	 * @param mnt
	 *            : montant en dinar
	 * @param codDev
	 *            : code devise
	 * @param jrn
	 *            : date du jour
	 * @return :Conversion du montant ( en dinar) en devise selon court achat bna
	 */
	public static Double getCoursFixe(Date dateComptable, Long codDev) {

		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		PariteOfficielleId pariteId = new PariteOfficielleId();
		pariteId.setAnnee(new Long(year.format(dateComptable)));
		pariteId.setCodDevDev(codDev);
		PariteOfficielle parite = new PariteOfficielle();
		GetCoursPariteOffCmd cmd = new GetCoursPariteOffCmd();
		parite = (PariteOfficielle) cmd.execute(pariteId);
		return parite.getMontCoursPaof();
	}

	public static boolean exoneration(String codTpceTpce, String numPcePers, Date date) {
		ExonerationTvaDAO exonerationTvaDAO = new ExonerationTvaDAO();
		ParamRechercheOpposition param = new ParamRechercheOpposition();
		param.setTypPceDemd(Long.valueOf(codTpceTpce));
		param.setNumPceDemd(numPcePers);
		param.setDateDebutConsult(date);

		PrimitiveVO res = (PrimitiveVO) exonerationTvaDAO.isClientExonereTVA(param);
		return res.isVBool();
	}

	public static boolean isChqCertif(Cheque vo) {

		Cheque chq = (Cheque) vo;
		PrimitiveVO primitive= new PrimitiveVO();
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");
		IExpression expression = searchEngine.createExpression();
		ICriteria criteria = searchEngine.createCriteria();
		
		//  compte client 
		ContratCpt contratCpt = UtilCtr.getContratCptByRIB(chq.getChequeId().getRibTirChq());
		criteria.add(expression.eq("contratCpt", contratCpt));
		// cheque
		criteria.add(expression.eq("numChqCchq", chq.getChequeId().getNumChqChq()));
		// etat certif  valide
		criteria.add(expression.eq("codEtatCchq", Long.valueOf(1)));
		// etat certif cheque non paye 
		criteria.add(expression.eq("codPayCchq", Long.valueOf(0)));

		try {
			List l = searchEngine.find(CertificationCheques.class, criteria);
			if (l.isEmpty()) {
				return  false;
			}else{
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	
	}

	public static boolean isContratValide(ContratCpt cpt) {
		if (cpt != null && cpt.getCodEtatCcpt()!=null) {
			return cpt.getCodEtatCcpt().equals(Constants.COD_ETAT_CPT_VALID);
		}
		return false;

	}
	public static Adresse getAdressePersonne(Personne person) {
		if(person.getAdresseProf().toString().replaceAll("( )+", " ").trim().equals(""))
			return person.getAdresseProf();
		if(person.getAdresseResid().toString().replaceAll("( )+", " ").trim().equals(""))
			return person.getAdresseProf();
		
		return null;
	}

	public static String corrigerChaineCaractere(String texte) {
		StringBuffer result = new StringBuffer();
		if (texte != null && texte.length() != 0) {
			int index = -1;
			char c = (char) 0;
			String chars   = "àâäéèêëîïôöùûüç@\\|#&.;:^°'\"/*<>";
			String replace = "aaaeeeeiioouuuc                  ";
			for (int i = 0; i < texte.length(); i++) {
				c = texte.charAt(i);
				if ((index = chars.indexOf(c)) != -1)
					result.append(replace.charAt(index));
				else
					result.append(c);
			}
		}

		return result.toString();
	}
	
	
	public static String normalizeWord(String word) {
		if(word!=null && !word.isEmpty()){
		return Normalizer.normalize(word, Normalizer.Form.NFD).replaceAll("[^\\x00-\\x7F]", "");}
		else
		{
			return "";
		}
	}
	public static String getHostName() {
		String retour = "";

		try {
			retour = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {

		}

		return retour;
	}
	
	

}
