package com.bna.smile.web.reporting.actions;

import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.ContratCptId;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.commande.GetContratCptByIdCmd;
import com.bna.smile.model.domainecommun.commande.GetRibCmd;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.reporting.commande.GetListOperMoyPayExtraitCmd;
import com.bna.smile.model.reporting.commande.PrinterCmd;
import com.bna.smile.model.reporting.model.CommonReportVO;
import com.bna.smile.model.reporting.model.ParamMoyPayVo;
import com.bna.smile.web.commun.forms.ExonerationTvaForm;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.commun.util.SessionUtil;
import com.bna.smile.web.placement.view.DemandeDecisionView;
import com.bna.smile.web.reporting.View.ExtraitView;
import com.bna.smile.web.reporting.forms.ExtraitCptForm;
import com.oxia.fwk.context.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class ExtraitCptAction extends DispatchAction {

	private static final Logger logger = Logger
			.getLogger(ExtraitCptAction.class);

	public ActionForward initierPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		StringBuffer text = new StringBuffer(
				"L'initialisation de la consultation de l'éxtrait de compte a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
		ExtraitCptForm extraitCptForm = (ExtraitCptForm) form;
		SessionUtil sessionUtil = new SessionUtil();
		try {
			// Suppression des anciens Bean de type Form de la session, SAUF
			// "ExtraitCptForm"
			sessionUtil.removeSession(request, "extraitCptForm");

			ParamAgence paramAgence = new ParamAgence();
			paramAgence = (ParamAgence) request.getSession().getAttribute(
					"paramAgBNA");
			extraitCptForm.clearForm();
			if (paramAgence != null) {
				extraitCptForm.setCodStrcStrc(paramAgence.getCodStrcStrc()
						.toString());
				extraitCptForm.getInitialisationView().setNumMatrUser(
						paramAgence.getNumMatrUser());
				extraitCptForm.getInitialisationView().setDateComptable(
						paramAgence.getDateComptable());
			} else {
				logger.debug("L'objet param agence est null");
			}
			return mapping.findForward("success");
		} catch (Exception e) {
			text.append("Exception au niveau de l'agence:");
			text.append(extraitCptForm.getCodStrcStrc());
			text.append(". Exception :");
			text.append(e.toString());
			ActionMessages actionMessages = new ActionMessages();
			ActionMessage actionMessage = new ActionMessage(
					"exception.generique", e.getMessage());
			actionMessages.add("Erreur ", actionMessage);
			this.saveMessages(request, actionMessages);
			logger.error(text.toString(), e);
			return mapping.findForward("error");
		}

	}

	public ActionForward executeExtrait(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		StringBuffer text = new StringBuffer(
				"L'affichage de l'éxtrait de compte a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");

		ContratCpt contrat = new ContratCpt();
		ContratCptId idContrat = new ContratCptId();
		ExtraitCptForm extraitCptForm = (ExtraitCptForm) form;
		ParamMoyPayVo paramMoyPayVo = new ParamMoyPayVo();
		GetListOperMoyPayExtraitCmd getListOperMoyPayExtraitCmd = new GetListOperMoyPayExtraitCmd();
		GetContratCptByIdCmd getContratCptByIdCmd = new GetContratCptByIdCmd();
		GetRibCmd getRibCmd = new GetRibCmd();
		Listes l = new Listes();
		ExtraitView extraitView = new ExtraitView();

		try {

			extraitCptForm.setPersonneExist(true);
			extraitCptForm.setVerifDatCompt(true);
			idContrat.setCodStrcStrc(new Long(extraitCptForm.getCodStrcStrc()));
			idContrat.setCodPrdPrd(new Long(extraitCptForm.getCodPrdPrd()));
			idContrat.setNumCcptCcpt(new Long(extraitCptForm.getNumCcptCcpt()));

			contrat.setContratCptId(idContrat);
			contrat = (ContratCpt) getContratCptByIdCmd.execute(contrat);

			extraitCptForm.setListeOperationMoyPay(new ArrayList());

			if (contrat != null) {
				if (contrat.getClient().getPersonne().getNomNomPers() != null
						&& contrat.getClient().getPersonne().getNomPrnPers() != null) {
					extraitCptForm.setNomPrnPers(contrat.getClient()
							.getPersonne().getNomNomPers().trim()
							+ " "
							+ contrat.getClient().getPersonne().getNomPrnPers()
									.trim());
				} else {
					extraitCptForm.setNomPrnPers("");
				}
				PrimitiveVO rib = (PrimitiveVO) (getRibCmd.execute(contrat));
				extraitCptForm.setRib(rib.getVString());
				if (contrat.getMontBlocCcpt() != null) {
					extraitCptForm.setMontantBloque(StrHandler.formatmnt(Math
							.abs(contrat.getMontBlocCcpt().doubleValue())));
				} else {
					text.append("Le montant bloqué est vide");
					logger.debug("Le montant bloqué est vide");
				}
				if (contrat.getMontAutCcpt() != null) {
					extraitCptForm.setFaciliteCaisse(StrHandler.formatmnt(Math
							.abs(contrat.getMontAutCcpt().doubleValue())));
				} else {
					text.append("La cote autorisé est vide");
					logger.debug("La cote autorisé est vide");
				}
				if (contrat.getMontSoldCcpt() != null) {
					extraitCptForm.setSoldeCompte(StrHandler.formatmnt(Math
							.abs(contrat.getMontSoldCcpt().doubleValue())));
					if (contrat.getMontSoldCcpt() < 0) {
						extraitCptForm.setSoldeCompte(extraitCptForm
								.getSoldeCompte()
								+ " DB");
					} else {
						extraitCptForm.setSoldeCompte(extraitCptForm
								.getSoldeCompte()
								+ " CR");
					}
				} else {
					text.append("Le solde est vide");
					logger.debug("Le solde est vide");
				}
				if (contrat.getDevise() != null) {
					extraitCptForm.setDevise((contrat.getDevise()
							.getLibDevDev()));
				} else {
					text.append("La devise est vide");
					logger.debug("La devise est vide");
				}
				if (contrat.getDatEautCcpt() != null) {
					extraitCptForm.setDevise(DateHandler.dateToStr(contrat
							.getDatEautCcpt()));
				} else {
					text.append("La date échéance est vide");
					logger.debug("La date échéance est vide");
				}

				if (paramMoyPayVo != null) {
					paramMoyPayVo.setIdContrat(contrat.getContratCptId());

					if (!extraitCptForm.getInitialisationView()
							.getCodeOperation().equals("EJOUR")) {
						// extrait par période (global)
						if (!extraitCptForm.getDateDeb().equals("")
								&& !extraitCptForm.getDateFin().equals("")) {
							paramMoyPayVo.setDateDeb(DateHandler
									.strToDate(extraitCptForm.getDateDeb()));
							paramMoyPayVo.setDateFin(DateHandler.addJour(
									DateHandler.strToDate(extraitCptForm
											.getDateFin()), 1));
						} else {
							logger.debug("les dates (période) sont vides");
						}
					} else {
						// extrait du jour comptable
						paramMoyPayVo.setDateDeb(DateHandler
								.strToDate(extraitCptForm
										.getInitialisationView()
										.getDateComptable()));
						paramMoyPayVo.setDateFin(DateHandler.addJour(
								paramMoyPayVo.getDateDeb(), 1));
					}
				} else {
					logger.debug("paramMoyPayVo est null");
				}

				l = (Listes) getListOperMoyPayExtraitCmd.execute(paramMoyPayVo);
				if (!l.hasError()) {
					if ((l != null) && l.getList() != null) {
						if (l.getList().size() != 0) {
							Collection extraitCpt = new ArrayList();

							Collection<OperationMoyPay> listOpMoyPay = l
									.getList();
							for (OperationMoyPay operationMoyPay : listOpMoyPay) {
								if (operationMoyPay != null) {
									extraitView = créerExtraitView(
											operationMoyPay, null);
									double montantSommeDetail = 0;

									if (operationMoyPay
											.getDetailOperMoyPaiements() != null
											&& operationMoyPay
													.getDetailOperMoyPaiements()
													.size() != 0) {
										for (Iterator it = operationMoyPay
												.getDetailOperMoyPaiements()
												.iterator(); it.hasNext();) {
											DetailOperMoyPaiement detailOperMoyPaiement = (DetailOperMoyPaiement) it
													.next();
											montantSommeDetail = montantSommeDetail
													+ Math
															.abs(detailOperMoyPaiement
																	.getMontValDomp()
																	.doubleValue());
										}
									}
									double netPercu = 0;
									if (operationMoyPay.getMontTvaOmp() != null) {
										netPercu = operationMoyPay
												.getMontDinOmp().doubleValue()
												- montantSommeDetail
												- operationMoyPay
														.getMontTvaOmp()
														.doubleValue();
									} else {
										netPercu = operationMoyPay
												.getMontDinOmp().doubleValue()
												- montantSommeDetail;
									}
									extraitView.setNetPercu(StrHandler
											.formatmnt(netPercu));
									if (extraitView != null) {
										extraitCpt.add(extraitView);
									} else {
										logger.debug("extraitView es vide");
									}
									if (!extraitCptForm.getInitialisationView()
											.getCodeOperation()
											.equals("IMPAVI")) {
										if (operationMoyPay
												.getDetailOperMoyPaiements() != null
												&& operationMoyPay
														.getDetailOperMoyPaiements()
														.size() != 0) {
											extraitView = créerExtraitView(
													null,
													operationMoyPay
															.getDetailOperMoyPaiements());
											if (extraitView != null) {
												extraitCpt.add(extraitView);
											} else {
												logger
														.debug("extraitView es vide");
											}
										}
									}
								}
							}
							extraitCptForm.setListeOperationMoyPay(extraitCpt);
						}
					}
				} else {
					logger
							.error("Erreur dans l'objet de retour du traitement 'exoCltTva'");
					text.append("Exception au niveau de l'agence:");
					text.append(extraitCptForm.getCodStrcStrc());
					text.append(". Exception :");
					text.append(l.getErrorMessage());
					com.oxia.fwk.core.Error erreur = l.getErrors().get(0);
					ActionMessages actionMessages = new ActionMessages();
					ActionMessage actionMessage = new ActionMessage(
							"exception.generique", erreur.getDescription());
					actionMessages.add("Erreur ", actionMessage);
					this.saveMessages(request, actionMessages);
					return mapping.findForward("error");
				}

			} else {
				extraitCptForm.setPersonneExist(false);
				logger.debug("contrat est null");
			}

			return mapping.findForward("success");
		} catch (Exception e) {
			text.append("Exception au niveau de l'agence:");
			text.append(extraitCptForm.getCodStrcStrc());
			text.append(". Exception :");
			text.append(e.toString());
			ActionMessages actionMessages = new ActionMessages();
			ActionMessage actionMessage = new ActionMessage(
					"exception.generique", e.getMessage());
			actionMessages.add("Erreur ", actionMessage);
			this.saveMessages(request, actionMessages);
			logger.error(text.toString(), e);
			return mapping.findForward("error");
		}
	}

	public ExtraitView créerExtraitView(OperationMoyPay operationMoyPay,
			Set detailsOperation) {

		ExtraitView extraitView = new ExtraitView();
		if (operationMoyPay != null) {
			// ------------------------------------------Remplir données
			// opération ( retrait , Escompte ... )
			extraitView.setDateJour(DateHandler.dateToStr(operationMoyPay
					.getDatOperOmp()));
			extraitView.setLibelleOperation(operationMoyPay.getTache()
					.getOperation().getLibOperOper());
			extraitView.setDateValeur(DateHandler.dateToStr(operationMoyPay
					.getDatValOmp()));
			if (operationMoyPay.getMontApreOmp() != null) {
				extraitView.setSoldeApresOp(StrHandler.formatmnt(Math
						.abs(operationMoyPay.getMontApreOmp().doubleValue())));
			} else {
				extraitView.setSoldeApresOp("");
			}
			extraitView.setNumOperation(operationMoyPay.getNumOperOmp());
			extraitView.setCodeOperation(operationMoyPay.getTache()
					.getTacheId().getCodOperOper());
			if (operationMoyPay.getMontApreOmp() != null) {
				if (operationMoyPay.getMontApreOmp() < 0) {
					extraitView.setSensMontant("DB");
				} else {
					extraitView.setSensMontant("CR");
				}
			} else {
				extraitView.setSensMontant("");
			}
			if (operationMoyPay.getCodSensOmp().equals("D")) {
				extraitView.setMontantDebit(StrHandler.formatmnt(Math
						.abs(operationMoyPay.getMontDinOmp().doubleValue())));
			} else {
				extraitView.setMontantCredit(StrHandler.formatmnt(Math
						.abs(operationMoyPay.getMontDinOmp().doubleValue())));
			}
		}
		// ------------------------------------------Remplir les données du
		// détails opération (commission ... )
		if (detailsOperation != null) {
			for (Iterator it = detailsOperation.iterator(); it.hasNext();) {
				DetailOperMoyPaiement detailOperMoyPaiement = (DetailOperMoyPaiement) it
						.next();
				extraitView.setDateJour("");
				extraitView.setLibelleOperation(detailOperMoyPaiement
						.getNomencElemtCondition().getLibNecdNecd());
				extraitView.setDateValeur(DateHandler
						.dateToStr(detailOperMoyPaiement.getDatValDomp()));
				extraitView.setMontantDebit(StrHandler.formatmnt(Math
						.abs(detailOperMoyPaiement.getMontValDomp()
								.doubleValue())));
				extraitView.setSoldeApresOp("");
			}
		}
		return extraitView;
	}

	public ActionForward imprimerExtraitCompte(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		StringBuffer text = new StringBuffer(
				"L'impression de l'extrait a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
		ExtraitCptForm extraitCptForm = (ExtraitCptForm) form;
		try {
			// if(extraitCptForm.isVerifDatCompt()){
			if (extraitCptForm.getListeOperationMoyPay() != null) {
				CommonReportVO valueObject = new CommonReportVO();
				Map parameters = new HashMap();
				String libParametre;
				String valParametre;
				String pLibEtat = "P_LIB_ETAT";
				String vLibEtat = "";
				StringBuffer txtLibEtat = new StringBuffer("Extrait de compte ");

				if (extraitCptForm.getInitialisationView() != null) {
					libParametre = "P_COD_STRC";
					valParametre = extraitCptForm.getCodStrcStrc();
					parameters.put(libParametre, valParametre);
					// Ajout du parametre matricule utilisateur
					libParametre = "P_NUM_MATR_USER";
					valParametre = extraitCptForm.getInitialisationView()
							.getNumMatrUser();
					parameters.put(libParametre, valParametre);
				} else {
					logger
							.debug(" extraitCptForm.getInitialisationView() == null ");
				}

				if (extraitCptForm.isPersonneExist()) {
					libParametre = "P_COD_PRD";
					valParametre = extraitCptForm.getCodPrdPrd();
					parameters.put(libParametre, valParametre);
					libParametre = "P_NUM_CPTR_CPT";
					StringBuffer txtNumCpt = new StringBuffer();
					txtNumCpt.append(StrHandler.lpad(extraitCptForm
							.getCodStrcStrc(), '0', 3));
					txtNumCpt.append(StrHandler.lpad(extraitCptForm
							.getCodPrdPrd(), '0', 4));
					txtNumCpt.append(StrHandler.lpad(extraitCptForm
							.getNumCcptCcpt(), '0', 6));
					txtNumCpt.append(extraitCptForm.getCle());
					valParametre = txtNumCpt.toString();
					parameters.put(libParametre, valParametre);
					libParametre = "P_NUM_CPTR";
					valParametre = extraitCptForm.getNumCcptCcpt();
					parameters.put(libParametre, valParametre);
					if (extraitCptForm.getInitialisationView()
							.getCodeOperation().equals("EJOUR")) {
						// envoie de la date comptable comme parametre de
						// recherche dans la date deb et fin
						libParametre = "P_DATE_FIN";
						if (extraitCptForm.getInitialisationView() != null) {
							valParametre = extraitCptForm
									.getInitialisationView().getDateComptable();
						}
						parameters.put(libParametre, valParametre);
						libParametre = "P_DATE_DEB";
						parameters.put(libParametre, valParametre);
						// libelle etat
						txtLibEtat.append("des opérations du ");
						txtLibEtat.append(valParametre);
					} else if (extraitCptForm.getInitialisationView()
							.getCodeOperation().equals("EGLOB")) {
						// envoie de la période
						// libelle etat
						txtLibEtat.append("des opérations du ");
						libParametre = "P_DATE_DEB";
						valParametre = extraitCptForm.getDateDeb();
						txtLibEtat.append(valParametre);
						txtLibEtat.append(" au ");
						parameters.put(libParametre, valParametre);
						libParametre = "P_DATE_FIN";
						valParametre = extraitCptForm.getDateFin();
						txtLibEtat.append(valParametre);
						parameters.put(libParametre, valParametre);
					}

					libParametre = "P_RIB";
					valParametre = extraitCptForm.getRib();
					parameters.put(libParametre, valParametre);

				}

				// Titre du fichier à imprimer
				vLibEtat = txtLibEtat.toString();
				parameters.put(pLibEtat, vLibEtat);
				valueObject.setParams(parameters);
				parameters = null;
				// indiquer le nom du fichier jasper
				valueObject.setNomReport("Extrait");
				request.getSession().setAttribute("CommonPrintVo", valueObject);
				request.setAttribute("print", "1");
			}
		} catch (Exception e) {

			text.append("Exception au niveau de l'agence:");
			text.append(extraitCptForm.getCodStrcStrc());
			text.append(". Exception :");
			text.append(e.toString());
			ActionMessages actionMessages = new ActionMessages();
			ActionMessage actionMessage = new ActionMessage(
					"exception.generique", e.getMessage());
			actionMessages.add("Erreur ", actionMessage);
			this.saveMessages(request, actionMessages);
			logger.error(text.toString(), e);
			return mapping.findForward("error");

		}
		return mapping.findForward("success");

	}

	public ActionForward imprimerAvisOperation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		StringBuffer text = new StringBuffer(
				"L'impression de l'avis d'opération a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
		ExtraitCptForm extraitCptForm = (ExtraitCptForm) form;
		try {
			// if(extraitCptForm.isVerifDatCompt()){
			if (extraitCptForm.getListeOperationMoyPay() != null) {
				CommonReportVO valueObject = new CommonReportVO();
				Map parameters = new HashMap();
				String libParametre;
				String valParametre;
				String pLibEtat = "P_LIB_ETAT";
				String vLibEtat = "";
				StringBuffer txtLibEtat = new StringBuffer("Avis d'opéartion ");

				if (extraitCptForm.getInitialisationView() != null) {
					libParametre = "P_COD_STRC";
					valParametre = extraitCptForm.getCodStrcStrc();
					parameters.put(libParametre, valParametre);
					// Ajout du parametre matricule utilisateur
					libParametre = "P_NUM_MATR_USER";
					valParametre = extraitCptForm.getInitialisationView()
							.getNumMatrUser();
					parameters.put(libParametre, valParametre);
				} else {
					logger
							.debug(" extraitCptForm.getInitialisationView() == null ");
				}

				if (extraitCptForm.isPersonneExist()) {
					// récupérer le numéro de l'opération choisi
					Collection<ExtraitView> listOperationMoyPay;
					listOperationMoyPay = extraitCptForm
							.getListeOperationMoyPay();
					for (ExtraitView extraitView : listOperationMoyPay) {
						if (extraitView != (null)) {

							if (extraitView.getNumOperation().equals(
									extraitCptForm.getNumOperationChoisi())) {
								parameters.put("P_NUM_OPER_OMP", extraitView
										.getNumOperation());
								parameters.put("P_COD_OPER", extraitView
										.getCodeOperation());
								parameters.put("NET_PERCU", extraitView
										.getNetPercu());
								// parameters.put("DUPLICATA","DUPLICATA");
								parameters.put("DUPLICATA", "");
							}
						}
					}

				}

				// Titre du fichier à imprimer
				vLibEtat = txtLibEtat.toString();
				parameters.put(pLibEtat, vLibEtat);
				valueObject.setParams(parameters);
				parameters = null;
				// indiquer le nom du fichier jasper
				valueObject.setNomReport("AvisOperation");
				request.getSession().setAttribute("CommonPrintVo", valueObject);
				request.setAttribute("print", "1");
			}
		} catch (Exception e) {

			text.append("Exception au niveau de l'agence:");
			text.append(extraitCptForm.getCodStrcStrc());
			text.append(". Exception :");
			text.append(e.toString());
			ActionMessages actionMessages = new ActionMessages();
			ActionMessage actionMessage = new ActionMessage(
					"exception.generique", e.getMessage());
			actionMessages.add("Erreur ", actionMessage);
			this.saveMessages(request, actionMessages);
			logger.error(text.toString(), e);
			return mapping.findForward("error");

		}
		return mapping.findForward("success");

	}

	public ActionForward imprimerAvisOperationMultiple(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		StringBuffer text = new StringBuffer(
				"L'impression de l'avis d'opération a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
		ExtraitCptForm extraitCptForm = (ExtraitCptForm) form;
		try {

			if (extraitCptForm.getListeOperationMoyPay() != null) {
				CommonReportVO valueObject = new CommonReportVO();
				Map parameters = new HashMap();
				String libParametre;
				String valParametre;
				String pLibEtat = "P_LIB_ETAT";
				String vLibEtat = "";
				StringBuffer txtLibEtat = new StringBuffer("Avis d'opération ");

				if (extraitCptForm.getInitialisationView() != null) {
					libParametre = "P_COD_STRC";
					valParametre = extraitCptForm.getCodStrcStrc();
					parameters.put(libParametre, valParametre);
					// Ajout du parametre matricule utilisateur
					libParametre = "P_NUM_MATR_USER";
					valParametre = extraitCptForm.getInitialisationView()
							.getNumMatrUser();
					parameters.put(libParametre, valParametre);
				} else {
					logger
							.debug(" extraitCptForm.getInitialisationView() == null ");
				}

				if (extraitCptForm.isPersonneExist()) {

					parameters.put("P_NUM_OPER_OMP", extraitCptForm
							.getNumOperationChoisi().toString());
					// parameters.put("P_NUM_OPER_OMP","120110218006741");
					// System.out.println(extraitCptForm.getNumOperationChoisi().toString());

				}

				// Titre du fichier à imprimer
				vLibEtat = txtLibEtat.toString();
				parameters.put(pLibEtat, vLibEtat);
				valueObject.setParams(parameters);
				parameters = null;
				// indiquer le nom du fichier jasper
				valueObject.setNomReport("AvisOperationMultiple");
				request.getSession().setAttribute("CommonPrintVo", valueObject);
				request.setAttribute("print", "1");
			}
		} catch (Exception e) {

			text.append("Exception au niveau de l'agence:");
			text.append(extraitCptForm.getCodStrcStrc());
			text.append(". Exception :");
			text.append(e.toString());
			ActionMessages actionMessages = new ActionMessages();
			ActionMessage actionMessage = new ActionMessage(
					"exception.generique", e.getMessage());
			actionMessages.add("Erreur ", actionMessage);
			this.saveMessages(request, actionMessages);
			logger.error(text.toString(), e);
			return mapping.findForward("error");

		}
		return mapping.findForward("success");

	}

	public ActionForward genererAvisOperation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		StringBuffer text = new StringBuffer(
				"L'impression de l'avis d'opération a été interrompue, veuillez transmettre ce message à l'équipe informatique: ");
		ExtraitCptForm extraitCptForm = (ExtraitCptForm) form;
		  ParamAgence paramAgence = 
              (ParamAgence)request.getSession().getAttribute("paramAgBNA"); /// structure de l'agent 

		String dateJour=paramAgence.getDateJours();
		try {

			if (extraitCptForm.getListeOperationMoyPay() != null) {
				// CommonReportVO valueObject = new CommonReportVO();
				Map parameters = new HashMap();
				String libParametre;
				String valParametre;
				String pLibEtat = "P_LIB_ETAT";
				String vLibEtat = "";
				StringBuffer txtLibEtat = new StringBuffer("Avis d'opération ");

				if (extraitCptForm.getInitialisationView() != null) {
					libParametre = "P_COD_STRC";
					valParametre = extraitCptForm.getCodStrcStrc();
					parameters.put(libParametre, valParametre);
					// Ajout du parametre matricule utilisateur
					libParametre = "P_NUM_MATR_USER";
					valParametre = extraitCptForm.getInitialisationView()
							.getNumMatrUser();
					parameters.put(libParametre, valParametre);
				} else {
					logger
							.debug(" extraitCptForm.getInitialisationView() == null ");
				}

				if (extraitCptForm.isPersonneExist()) {

					// Titre du fichier à imprimer
					vLibEtat = txtLibEtat.toString();
					parameters.put(pLibEtat, vLibEtat);
					BasicDataSource dataSource = (BasicDataSource) Context
							.getInstance().getBean("dataSource");
					String rapport = "AvisOperationMultiple";
					String rootFolder = getServlet().getServletContext()
							.getRealPath("")
							+ File.separatorChar
							+ "reporting"
							+ File.separatorChar;
					String rootOutFolder = getServlet().getServletContext()
							.getRealPath("")
							+ File.separatorChar
							+ "reportingPDF";
					String folderStc=rootOutFolder+ File.separatorChar+
							StrHandler.lpad(extraitCptForm.getCodStrcStrc().toString(),'0', 3);

					parameters.put("P_PATH", rootFolder);

					File file = new File(rootFolder);
					File fileRepPDF = new File(rootOutFolder);
					File fileStrc = new File(folderStc);
					fileRepPDF.mkdir();
					fileStrc.mkdir();

					String numCCpt = StrHandler.lpad(extraitCptForm.getCodStrcStrc().toString(),'0', 3)+
					StrHandler.lpad(extraitCptForm.getCodPrdPrd().toString(),'0', 4)+
					StrHandler.lpad(extraitCptForm.getNumCcptCcpt().toString(),'0', 6);
									
					String str = extraitCptForm.getNumOperationChoisi()
							.toString();
					String[] temp;

					/* delimiter */
					String delimiter = ",";
					/*
					 * given string will be split by the argument delimiter
					 * provided.
					 */
					temp = str.split(delimiter);
					/* print substrings */

					for (int i = 0; i < temp.length; i++) {
						parameters.put("P_NUM_OPER_OMP", temp[i]);
						// - Création du rapport au format PDF
						JasperPrint jasperPrint = JasperFillManager.fillReport(
								new FileInputStream(new File(file, rapport
										+ ".jasper")), parameters, dataSource
										.getConnection());

						// on exporte l'état dans un fichier pdf
						
						String pathDateRep=folderStc+ File.separatorChar+dateJour.replace("/", "_");
						File dateRep=new File(pathDateRep);
						dateRep.mkdir();
						JasperExportManager.exportReportToPdfFile(jasperPrint,
								pathDateRep + File.separatorChar+
								numCCpt + "_" + temp[i].replace("'", "")
								+ ".pdf");

					}

				}

			}
		} catch (Exception e) {

			text.append("Exception au niveau de l'agence:");
			text.append(extraitCptForm.getCodStrcStrc());
			text.append(". Exception :");
			text.append(e.toString());
			ActionMessages actionMessages = new ActionMessages();
			ActionMessage actionMessage = new ActionMessage(
					"exception.generique", e.getMessage());
			actionMessages.add("Erreur ", actionMessage);
			this.saveMessages(request, actionMessages);
			logger.error(text.toString(), e);
			return mapping.findForward("error");

		}
		return mapping.findForward("success");

	}

}
