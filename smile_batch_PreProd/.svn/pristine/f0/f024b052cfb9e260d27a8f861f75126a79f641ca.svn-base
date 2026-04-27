package com.bna.smile.model.domaineguichet.traitement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.bna.commun.model.BatchExeptionPlac;
import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.model.BatchVirFileCpy;
import com.bna.commun.model.ContratCpt;
import com.bna.commun.model.DetailOperMoyPaiement;
import com.bna.commun.model.JourneeStructureBatch;
import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tache;
import com.bna.commun.model.TacheId;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GenerateReferenceInterSiege;
import com.bna.smile.model.domainecommun.traitement.GetRibTrt;
import com.bna.smile.model.domaineguichet.dao.GuichetDAO;
import com.bna.smile.model.domaineguichet.model.AgencesMAJNSIVo;
import com.bna.smile.model.domaineguichet.model.MAJNSIVo;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

public class MAJNSIActelFromOmpControlMTrt extends Traitement {

	Context context = ContextHandler.getContext();
	ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

	// à ne pas laisser en variable global

	List<Long> listAgencesMAJNSIStrc = null;
	List<AgencesMAJNSIVo> listAgencesMAJNSIStrcBtch = null;
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	BufferedWriter bufWriter = null;
	GuichetDAO guichetDao;
	int nbExcep = 0;
	SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

	public IValueObject perform(IValueObject vo) {
		MAJNSIVo mAJNSIVo = (MAJNSIVo) vo;
		this.setVerifDomaine(false);
		this.setCroFlag(false);
		Context context = ContextHandler.getContext();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
		String srcFileLog = "D:\\MAJNSISAUV\\MAJNSIActelLog" + formatter.format(new Date());
		String line = "";
		try {

			FileWriter fileWriterResult = null;
			fileWriterResult = new FileWriter(srcFileLog, true);
			bufWriter = new BufferedWriter(fileWriterResult);

			guichetDao = (GuichetDAO) context.getBean("guichetDAO");
			GenerateReferenceInterSiege generateReferenceInterSiege = new GenerateReferenceInterSiege();
			// /*** traitement Batch

			listAgencesMAJNSIStrcBtch = guichetDao.getListAgencesMajNsiActelStrcBtch();

			long nbLineCpyed = 0;
			for (int i = 0; i < listAgencesMAJNSIStrcBtch.size(); i++) {

				listAgencesMAJNSIStrcBtch.get(i).getJourneeStructureBatch().setCodStatJsb(1L);
				BatchService batchService = (BatchService) context.getBean("batchService");
				JourneeStructureBatch journeeStructureBatch = (JourneeStructureBatch) batchService
						.updateJourneeStructureBatch(listAgencesMAJNSIStrcBtch.get(i).getJourneeStructureBatch());

				List OperOmp = guichetDao.getListOperMoyPayActel(listAgencesMAJNSIStrcBtch.get(i)
						.getJourneeStructureBatch().getJourneeStructureBatchId().getCodStrcStrc().toString());
				if (OperOmp != null && OperOmp.size() > 0) {
					guichetDao.updateListOperMoyPayActel(OperOmp, "1");
				}
				listAgencesMAJNSIStrcBtch.get(i).setNbTotalOperations(OperOmp.size());
				nbLineCpyed += OperOmp.size();
				if (OperOmp != null && OperOmp.size() > 0) {
					for (Iterator it = OperOmp.iterator(); it.hasNext();) {
						OperationMoyPay omp = (OperationMoyPay) it.next();

						if ((omp.getDetailOperMoyPaiements() == null || (omp.getDetailOperMoyPaiements() != null
								&& omp.getDetailOperMoyPaiements().size() == 0)) && omp.getMontDinOmp() > 0) {
							OperationMoyPay ompRec = affecterOperOmpRecu(omp, false, false, listAgencesMAJNSIStrcBtch
									.get(i).getJourneeStructureBatch().getJourneeStructureBatchId().get_datJrnJrn());
							OperationMoyPay ompEmis = affecterOperOmpEmis(ompRec, omp.getContratCpt());
							String ref1 = generateReferenceInterSiege.getRISWithUpdate(
									ompEmis.getStructureInitiatrice().getCodStrcStrc(), ompEmis.getDatOperOmp());
							String ref2 = generateReferenceInterSiege.getRISWithUpdate(
									ompEmis.getStructureInitiatrice().getCodStrcStrc(), ompEmis.getDatOperOmp());
							ompEmis.setRefIns1Omp(ref1);
							ompEmis.setRefIns2Omp(ref2);
							ompRec.setRefIns1Omp(ref1);
							ompRec.setRefIns2Omp(ref2);
							ompRec.setCodRefbOmp(insertString(ompRec.getCodRefbOmp(),
									ompEmis.getContratCpt().getContratCptId().getCodStrcStrc() + " ", 2));
							ompRec.setCodRefbOmp(
									ompRec.getCodRefbOmp().substring(0, Math.min(ompRec.getCodRefbOmp().length(), 30)));
							InsertOpMoyPayActelEmisTrt insertOpMoyPayActelEmisTrt = new InsertOpMoyPayActelEmisTrt();
							InsertOpMoyPayActelRecuTrt insertOpMoyPayActelRecuTrt = new InsertOpMoyPayActelRecuTrt();
							insertOpMoyPayActelEmisTrt.exec(ompEmis);
							insertOpMoyPayActelRecuTrt.exec(ompRec);

							listAgencesMAJNSIStrcBtch.get(i)
									.setNbOperationsPosi(listAgencesMAJNSIStrcBtch.get(i).getNbOperationsPosi() + 1);
							if (ompRec.getCodSensOmp().equals("C")) {
								listAgencesMAJNSIStrcBtch.get(i).setSommeOp(
										listAgencesMAJNSIStrcBtch.get(i).getSommeOp() + ompRec.getMontDinOmp());
							} else {
								listAgencesMAJNSIStrcBtch.get(i).setSommeOpDebit(
										listAgencesMAJNSIStrcBtch.get(i).getSommeOpDebit() + ompRec.getMontDinOmp());
							}

						} else {
							if (omp.getMontDinOmp() > 0) {
								OperationMoyPay ompRec = affecterOperOmpRecu(omp, false, false,
										listAgencesMAJNSIStrcBtch.get(i).getJourneeStructureBatch()
												.getJourneeStructureBatchId().get_datJrnJrn());
								OperationMoyPay ompEmis = affecterOperOmpEmis(ompRec, omp.getContratCpt());
								String ref1 = generateReferenceInterSiege.getRISWithUpdate(
										ompEmis.getStructureInitiatrice().getCodStrcStrc(), ompEmis.getDatOperOmp());
								String ref2 = generateReferenceInterSiege.getRISWithUpdate(
										ompEmis.getStructureInitiatrice().getCodStrcStrc(), ompEmis.getDatOperOmp());
								ompEmis.setRefIns1Omp(ref1);
								ompEmis.setRefIns2Omp(ref2);
								ompRec.setRefIns1Omp(ref1);
								ompRec.setRefIns2Omp(ref2);

								ompRec.setCodRefbOmp(insertString(ompRec.getCodRefbOmp(),
										ompEmis.getContratCpt().getContratCptId().getCodStrcStrc() + "", 2));
								ompRec.setCodRefbOmp(ompRec.getCodRefbOmp().substring(0,
										Math.min(ompRec.getCodRefbOmp().length(), 30)));

								InsertOpMoyPayActelEmisTrt insertOpMoyPayActelEmisTrt =
										new InsertOpMoyPayActelEmisTrt();
								InsertOpMoyPayActelRecuTrt insertOpMoyPayActelRecuTrt =
										new InsertOpMoyPayActelRecuTrt();
								insertOpMoyPayActelEmisTrt.exec(ompEmis);
								insertOpMoyPayActelRecuTrt.exec(ompRec);
								listAgencesMAJNSIStrcBtch.get(i).setNbOperationsPosi(
										listAgencesMAJNSIStrcBtch.get(i).getNbOperationsPosi() + 1);
								if (ompRec.getCodSensOmp().equals("C")) {
									listAgencesMAJNSIStrcBtch.get(i).setSommeOp(
											listAgencesMAJNSIStrcBtch.get(i).getSommeOp() + ompRec.getMontDinOmp());
								} else {
									listAgencesMAJNSIStrcBtch.get(i)
											.setSommeOpDebit(listAgencesMAJNSIStrcBtch.get(i).getSommeOpDebit()
													+ ompRec.getMontDinOmp());
								}

							}

							DetailOperMoyPaiement det;
							Iterator itd = omp.getDetailOperMoyPaiements().iterator();
							if (itd.hasNext()) {
								det = (DetailOperMoyPaiement) itd.next();
								if (det.getNumCodDomp().equals(1l)) {
									if (omp.getMontTvaOmp() != null && omp.getMontTvaOmp() > 0) {
										OperationMoyPay ompRecTva = affecterOperOmpRecu(omp, false, true,
												listAgencesMAJNSIStrcBtch.get(i).getJourneeStructureBatch()
														.getJourneeStructureBatchId().get_datJrnJrn());
										OperationMoyPay ompEmisTVA =
												affecterOperOmpEmis(ompRecTva, omp.getContratCpt());
										String ref1 = generateReferenceInterSiege.getRISWithUpdate(
												ompEmisTVA.getStructureInitiatrice().getCodStrcStrc(),
												ompEmisTVA.getDatOperOmp());
										String ref2 = generateReferenceInterSiege.getRISWithUpdate(
												ompEmisTVA.getStructureInitiatrice().getCodStrcStrc(),
												ompEmisTVA.getDatOperOmp());
										ompEmisTVA.setRefIns1Omp(ref1);
										ompEmisTVA.setRefIns2Omp(ref2);
										ompRecTva.setRefIns1Omp(ref1);
										ompRecTva.setRefIns2Omp(ref2);
										ompEmisTVA.setCodSensOmp(Constants.COD_SENS_CR);
										ompRecTva.setCodSensOmp(Constants.COD_SENS_DB);

										if (ompEmisTVA.getCodRefbOmp() != null
												&& ompEmisTVA.getCodRefbOmp().length() > 0) {
											ompEmisTVA
													.setCodRefbOmp(insertString(ompEmisTVA.getCodRefbOmp(), "TVA ", 2));
											ompEmisTVA.setCodRefbOmp(ompEmisTVA.getCodRefbOmp().substring(0,
													Math.min(ompEmisTVA.getCodRefbOmp().length(), 30)));
											ompRecTva.setCodRefbOmp(insertString(ompRecTva.getCodRefbOmp(),
													ompEmisTVA.getContratCpt().getContratCptId().getCodStrcStrc()
															+ " TVA ",
													2));
											ompRecTva.setCodRefbOmp(ompRecTva.getCodRefbOmp().substring(0,
													Math.min(ompRecTva.getCodRefbOmp().length(), 30)));
										} else {
											ompEmisTVA.setCodRefbOmp(": TVA ");
											ompRecTva.setCodRefbOmp(
													":" + ompEmisTVA.getContratCpt().getContratCptId().getCodStrcStrc()
															+ " TVA ");
										}
										InsertOpMoyPayActelEmisTrt insertOpMoyPayActelEmisTrt =
												new InsertOpMoyPayActelEmisTrt();
										InsertOpMoyPayActelRecuTrt insertOpMoyPayActelRecuTrt =
												new InsertOpMoyPayActelRecuTrt();
										insertOpMoyPayActelEmisTrt.exec(ompEmisTVA);
										insertOpMoyPayActelRecuTrt.exec(ompRecTva);

										listAgencesMAJNSIStrcBtch.get(i).setNbTotalOperations(
												listAgencesMAJNSIStrcBtch.get(i).getNbTotalOperations() + 1);
										listAgencesMAJNSIStrcBtch.get(i).setNbOperationsPosi(
												listAgencesMAJNSIStrcBtch.get(i).getNbOperationsPosi() + 1);
										if (ompRecTva.getCodSensOmp().equals("C")) {
											listAgencesMAJNSIStrcBtch.get(i)
													.setSommeOp(listAgencesMAJNSIStrcBtch.get(i).getSommeOp()
															+ ompRecTva.getMontDinOmp());
										} else {
											listAgencesMAJNSIStrcBtch.get(i)
													.setSommeOpDebit(listAgencesMAJNSIStrcBtch.get(i).getSommeOpDebit()
															+ ompRecTva.getMontDinOmp());
										}

									}

								}

								if (det.getMontValDomp() != null && det.getMontValDomp() > 0) {
									OperationMoyPay ompRecComm = affecterOperOmpRecu(omp, true, false,
											listAgencesMAJNSIStrcBtch.get(i).getJourneeStructureBatch()
													.getJourneeStructureBatchId().get_datJrnJrn());
									OperationMoyPay ompEmisCOmm = affecterOperOmpEmis(ompRecComm, omp.getContratCpt());
									String ref1 = generateReferenceInterSiege.getRISWithUpdate(
											ompEmisCOmm.getStructureInitiatrice().getCodStrcStrc(),
											ompEmisCOmm.getDatOperOmp());
									String ref2 = generateReferenceInterSiege.getRISWithUpdate(
											ompEmisCOmm.getStructureInitiatrice().getCodStrcStrc(),
											ompEmisCOmm.getDatOperOmp());
									ompEmisCOmm.setRefIns1Omp(ref1);
									ompEmisCOmm.setRefIns2Omp(ref2);
									ompRecComm.setRefIns1Omp(ref1);
									ompRecComm.setRefIns2Omp(ref2);
									ompEmisCOmm.setCodSensOmp(Constants.COD_SENS_CR);
									ompRecComm.setCodSensOmp(Constants.COD_SENS_DB);
									if (ompEmisCOmm.getCodRefbOmp() != null
											&& ompEmisCOmm.getCodRefbOmp().length() > 0) {
										ompEmisCOmm.setCodRefbOmp(insertString(ompEmisCOmm.getCodRefbOmp(), "Com ", 2));
										ompEmisCOmm.setCodRefbOmp(ompEmisCOmm.getCodRefbOmp().substring(0,
												Math.min(ompEmisCOmm.getCodRefbOmp().length(), 30)));
										ompRecComm.setCodRefbOmp(insertString(ompRecComm.getCodRefbOmp(),
												ompEmisCOmm.getContratCpt().getContratCptId().getCodStrcStrc()
														+ " Com ",
												2));
										ompRecComm.setCodRefbOmp(ompRecComm.getCodRefbOmp().substring(0,
												Math.min(ompRecComm.getCodRefbOmp().length(), 30)));
									} else {
										ompEmisCOmm.setCodRefbOmp(": Com ");
										ompRecComm.setCodRefbOmp(
												":" + ompEmisCOmm.getContratCpt().getContratCptId().getCodStrcStrc()
														+ " Com ");
									}
									InsertOpMoyPayActelEmisTrt insertOpMoyPayActelEmisTrt =
											new InsertOpMoyPayActelEmisTrt();
									InsertOpMoyPayActelRecuTrt insertOpMoyPayActelRecuTrt =
											new InsertOpMoyPayActelRecuTrt();
									insertOpMoyPayActelEmisTrt.exec(ompEmisCOmm);
									insertOpMoyPayActelRecuTrt.exec(ompRecComm);
									listAgencesMAJNSIStrcBtch.get(i).setNbTotalOperations(
											listAgencesMAJNSIStrcBtch.get(i).getNbTotalOperations() + 1);
									listAgencesMAJNSIStrcBtch.get(i).setNbOperationsPosi(
											listAgencesMAJNSIStrcBtch.get(i).getNbOperationsPosi() + 1);
									if (ompRecComm.getCodSensOmp().equals("C")) {
										listAgencesMAJNSIStrcBtch.get(i)
												.setSommeOp(listAgencesMAJNSIStrcBtch.get(i).getSommeOp()
														+ ompRecComm.getMontDinOmp());
									} else {
										listAgencesMAJNSIStrcBtch.get(i)
												.setSommeOpDebit(listAgencesMAJNSIStrcBtch.get(i).getSommeOpDebit()
														+ ompRecComm.getMontDinOmp());
									}
								}

							}

						}

					}
					guichetDao.updateListOperMoyPayActel(OperOmp, "2");
				}

			}

			for (int i = 0; i < listAgencesMAJNSIStrcBtch.size(); i++) {

				List OperOmpGlobal = guichetDao.getListContratCptGloabalActel(
						listAgencesMAJNSIStrcBtch.get(i).getJourneeStructureBatch().getJourneeStructureBatchId()
								.getCodStrcStrc().toString(),
						listAgencesMAJNSIStrcBtch.get(i).getJourneeStructureBatch().getJourneeStructureBatchId()
								.getDatJrnJrn());
				if (OperOmpGlobal != null && OperOmpGlobal.size() > 0) {
					for (Iterator it = OperOmpGlobal.iterator(); it.hasNext();) {
						OperationMoyPay omp = (OperationMoyPay) it.next();
						if (omp.getMontDinOmp() > 0) {
							OperationMoyPay ompRec = affecterOperOmpRecu(omp, false, false, listAgencesMAJNSIStrcBtch
									.get(i).getJourneeStructureBatch().getJourneeStructureBatchId().get_datJrnJrn());
							OperationMoyPay ompEmis = affecterOperOmpEmis(ompRec, omp.getContratCpt());
							String ref1 = generateReferenceInterSiege.getRISWithUpdate(
									ompEmis.getStructureInitiatrice().getCodStrcStrc(), ompEmis.getDatOperOmp());
							String ref2 = generateReferenceInterSiege.getRISWithUpdate(
									ompEmis.getStructureInitiatrice().getCodStrcStrc(), ompEmis.getDatOperOmp());
							ompEmis.setRefIns1Omp(ref1);
							ompEmis.setRefIns2Omp(ref2);
							ompRec.setRefIns1Omp(ref1);
							ompRec.setRefIns2Omp(ref2);

							ompRec.setCodRefbOmp(" : " + ompEmis.getContratCpt().getContratCptId().getCodStrcStrc()
									+ " " + ompRec.getCodRefbOmp());
							ompRec.setCodRefbOmp(
									ompRec.getCodRefbOmp().substring(0, Math.min(ompRec.getCodRefbOmp().length(), 30)));
							ompEmis.setCodRefbOmp(" ");
							InsertOpMoyPayActelEmisTrt insertOpMoyPayActelEmisTrt = new InsertOpMoyPayActelEmisTrt();
							InsertOpMoyPayActelRecuTrt insertOpMoyPayActelRecuTrt = new InsertOpMoyPayActelRecuTrt();
							insertOpMoyPayActelEmisTrt.exec(ompEmis);
							insertOpMoyPayActelRecuTrt.exec(ompRec);
							guichetDao.updateOperMoyPayActelGlobal(
									"" + ompEmis.getContratCpt().getContratCptId().getCodStrcStrc(),
									"" + ompEmis.getContratCpt().getContratCptId().getCodPrdPrd(),
									"" + ompEmis.getContratCpt().getContratCptId().getNumCcptCcpt());
							listAgencesMAJNSIStrcBtch.get(i)
									.setNbOperationsPosi(listAgencesMAJNSIStrcBtch.get(i).getNbOperationsPosi() + 1);
							if (ompRec.getCodSensOmp().equals("C")) {
								listAgencesMAJNSIStrcBtch.get(i).setSommeOp(
										listAgencesMAJNSIStrcBtch.get(i).getSommeOp() + ompRec.getMontDinOmp());
							} else {
								listAgencesMAJNSIStrcBtch.get(i).setSommeOpDebit(
										listAgencesMAJNSIStrcBtch.get(i).getSommeOpDebit() + ompRec.getMontDinOmp());
							}

						}
					}
				}

				BatchService batchService = (BatchService) context.getBean("batchService");

				listAgencesMAJNSIStrcBtch.get(i).getJourneeStructureBatch().setDatCloJsb(new Date());
				JourneeStructureBatch journeeStructureBatch = (JourneeStructureBatch) batchService
						.updateJourneeStructureBatch(listAgencesMAJNSIStrcBtch.get(i).getJourneeStructureBatch());
				Structure strc = new Structure();
				strc.setCodStrcStrc(journeeStructureBatch.getJourneeStructureBatchId().get_codStrcStrc());
				gestionStatistique(journeeStructureBatch.getJourneeStructureBatchId().getDatJrnJrn(), strc,
						listAgencesMAJNSIStrcBtch.get(i).getNbTotalOperations(),
						listAgencesMAJNSIStrcBtch.get(i).getNbOperationsPosi(),
						listAgencesMAJNSIStrcBtch.get(i).getNbOperationsInter(),
						listAgencesMAJNSIStrcBtch.get(i).getNbOperationsRej(),
						listAgencesMAJNSIStrcBtch.get(i).getSommeOp());

				String info = " Nombre total des opérations = "
						+ listAgencesMAJNSIStrcBtch.get(i).getNbTotalOperations()
						+ "  ; Nombre des opérations comptes internes = "
						+ listAgencesMAJNSIStrcBtch.get(i).getNbOperationsInter()
						+ " ; Nombre des opérations positionnées  = "
						+ listAgencesMAJNSIStrcBtch.get(i).getNbOperationsPosi() + " ; Nombre des rejets = "
						+ listAgencesMAJNSIStrcBtch.get(i).getNbOperationsRej() + "  ;  pour la somme Credit de : "
						+ (listAgencesMAJNSIStrcBtch.get(i).getSommeOp()) + " Dinars" + "  ;  pour la somme Debit de : "
						+ (listAgencesMAJNSIStrcBtch.get(i).getSommeOpDebit()) + " Dinars";

				System.out.println(
						"Date : " + formatDate.format(journeeStructureBatch.getJourneeStructureBatchId().getDatJrnJrn())
								+ " --> Agence : " + journeeStructureBatch.getJourneeStructureBatchId().getCodStrcStrc()
								+ " ---> " + info);
			}

			BatchVirFileCpy batchVirFileCpyed = new BatchVirFileCpy();
			batchVirFileCpyed.setdateFileCre(new Date());
			batchVirFileCpyed.setfileSize(nbLineCpyed);
			batchVirFileCpyed.setdateBatchCpy(new Date());
			SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
			batchVirFileCpyed.settimeExec(sdfDate.format(new Date()));
			batchVirFileCpyed.setnbLineCpy(nbLineCpyed);
			BatchMetier batch = new BatchMetier();
			batch.setCodBatBmet(Constants.COD_BATCH_MAJNSI_Actel);
			batchVirFileCpyed.setBatchMetier(batch);
			CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
			crudService.create(batchVirFileCpyed);

			if (nbExcep > 0) {
				Structure strc = new Structure();
				strc.setCodStrcStrc(949L);
				Date dateCpt = DateHandler.strToDate(formatDate.format(new Date()));
				gestionExceptionEndBatch(dateCpt, strc, nbExcep + "  exceptions Batch MAJ NSI ");
			}
			System.out.println("  ");
			System.out.println("  *************** Fin *****************");
			System.out.println("  ");
			bufWriter.close();

		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans MAJNSI Actel: ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("MAJNSI");
			logger.error("Exception : ", e);
			vo.addError(erreur);
			Structure strc = new Structure();
			strc.setCodStrcStrc(949L);
			Date dateCpt = DateHandler.strToDate(formatDate.format(new Date()));
			gestionException(dateCpt, strc, e, " Last Ligne : " + line);
			try {
				bufWriter.close();
			} catch (Exception e2) {

			}
			throw new RuntimeException(e);

		}
		return vo;
	}

	OperationMoyPay affecterOperOmpEmis(OperationMoyPay ompRec, ContratCpt cpt) {
		OperationMoyPay ompEmis = new OperationMoyPay();
		BeanUtils.copyProperties(ompRec, ompEmis);
		if (ompEmis.getCodSensOmp().equals(Constants.COD_SENS_CR)) {
			ompEmis.setCodSensOmp(Constants.COD_SENS_DB);
		} else {
			ompEmis.setCodSensOmp(Constants.COD_SENS_CR);
		}
		Tache tache = new Tache();
		TacheId tacheId = new TacheId();
		tacheId.setCodOperOper(Constants.COD_OPER_VIR_ACTEL_EMIS);
		tacheId.setCodTachTach(1l);
		tache.setTacheId(tacheId);
		ompEmis.setTache(tache);
		ompEmis.setContratCpt(cpt);
		return ompEmis;
	}

	OperationMoyPay affecterOperOmpRecu(OperationMoyPay omp, boolean isComm, boolean isTva, Date dateCompta) {
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
		OperationMoyPay ompRecu = new OperationMoyPay();
		ContratCpt cpt = new ContratCpt();
		cpt.setContratCptId(omp.getContratCpt().getContratCpt().getContratCptId());
		ompRecu.setContratCpt(cpt);
		Tache tache = new Tache();
		TacheId tacheId = new TacheId();
		tacheId.setCodOperOper(Constants.COD_OPER_VIR_ACTEL_RECUS);
		tacheId.setCodTachTach(1l);
		tache.setTacheId(tacheId);
		ompRecu.setTache(tache);
		ompRecu.setMontDinOmp(omp.getMontDinOmp());
		ompRecu.setDatOperOmp(omp.getDatOperOmp());
		ompRecu.setDatSystOmp(DateHandler.strToDate(formatDate.format(new Date())));
		ompRecu.setCodSensOmp(omp.getCodSensOmp());
		ompRecu.setCodEtatOmp("V");
		ompRecu.setDatValOmp(omp.getDatValOmp());
		ompRecu.setDevise(omp.getDevise());
		ompRecu.setTypePieceDemandeur(omp.getTypePieceDemandeur());
		ompRecu.setNumPcedOmp(omp.getNumPcedOmp());
		ompRecu.setNomNomdOmp(omp.getNomNomdOmp());
		ompRecu.setNomPrndOmp(omp.getNomPrndOmp());
		Structure structureInit = new Structure();
		structureInit.setCodStrcStrc(omp.getContratCpt().getContratCptId().getCodStrcStrc());
		Structure structureRecep = new Structure();
		structureRecep.setCodStrcStrc(omp.getContratCpt().getContratCpt().getContratCptId().getCodStrcStrc());
		ompRecu.setStructureInitiatrice(structureInit);
		ompRecu.setStructureReceptrice(structureRecep);
		Personnel personnel = new Personnel();
		personnel.setNumMatrUser("9999");
		ompRecu.setPersonnelInitiateur(personnel);// / personne initiatrice seulement au
												  // cas
												  // de

		ompRecu.setPersonnelValideur(personnel);// / personnel initiatrice = personnel
												// validateur
		ompRecu.setCodRefcOmp(omp.getCodRefcOmp());

		if (omp.getTache() != null && omp.getTache().getTacheId() != null
				&& omp.getTache().getTacheId().getCodOperOper() != null) {
			ompRecu.setCodRefmOmp(omp.getTache().getTacheId().getCodOperOper() + "");
		}
		if (ompRecu.getCodRefcOmp() == null)
			ompRecu.setCodRefcOmp(" ");
		// ompRecu.setCodRefbOmp(omp.getTache().getOperation().get);
		ompRecu.setCodRefbOmp(omp.getCodRefbOmp());
		if (omp.getCodRefbOmp() != null)
			ompRecu.setCodRefbOmp(ompRecu.getCodRefbOmp().substring(0, Math.min(ompRecu.getCodRefbOmp().length(), 30)));
		ompRecu.setCodDemOmp(omp.getCodDemOmp());

		PrimitiveVO primitiveVO = new PrimitiveVO();
		GetRibTrt getRibTrt = new GetRibTrt();
		ContratCpt contratCpt = (ContratCpt) searchEngine.get(ContratCpt.class, omp.getContratCpt().getContratCptId());
		if (contratCpt != null && contratCpt.getStructure() != null) {
			primitiveVO = (PrimitiveVO) getRibTrt.exec(contratCpt);
			String rib_tireur = primitiveVO.getVString();
			if (rib_tireur != null) {
				ompRecu.setNumRibOmp(rib_tireur);
			}

			String libObjOpOmp = "VIR AUTO DU " + contratCpt.getNomIntiCcpt();

			if (libObjOpOmp.length() > 50) {
				libObjOpOmp = libObjOpOmp.substring(0, 49);
			}
			ompRecu.setLibObjOpOmp(libObjOpOmp);

			String libMotifOmp = "TRANSFERT AUTO DE L'AG " + contratCpt.getContratCptId().getCodStrcStrc()
					+ " DE LA PART DE " + contratCpt.getNomIntiCcpt();
			if (libMotifOmp.length() > 100) {
				libMotifOmp = libMotifOmp.substring(0, 99);
			}
			ompRecu.setLibMotfOmp(libMotifOmp);

		}
		if (isTva) {
			ompRecu.setMontDinOmp(omp.getMontTvaOmp());
			Iterator it = ompRecu.getDetailOperMoyPaiements().iterator();

			DetailOperMoyPaiement det;
			if (it.hasNext()) {
				det = (DetailOperMoyPaiement) it.next();
				if (det.getDatValDomp() != null)
					ompRecu.setDatValOmp(det.getDatValDomp());
			}
		} else if (isComm) {
			// DetailOperMoyPaiement det=omp.getDetailOperMoyPaiements().
			Iterator it = omp.getDetailOperMoyPaiements().iterator();
			DetailOperMoyPaiement det;
			if (it.hasNext()) {
				det = (DetailOperMoyPaiement) it.next();
				ompRecu.setMontDinOmp(det.getMontValDomp());
				if (det.getDatValDomp() != null)
					ompRecu.setDatValOmp(det.getDatValDomp());
				System.out.println(omp.getNumOperOmp());
			} else
				return null;

		}

		return ompRecu;
	}

	private void gestionException(Date dateOper, Structure agence, Exception e, String donnee) {

		try {
			bufWriter.write(agence.getCodStrcStrc() + " " + DateHandler.dateToStr(dateOper) + " " + e.toString()
					+ " ** " + donnee);
			bufWriter.newLine();
		} catch (IOException e1) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Erreur dans MAJNSI-perf : ");
			text.append(e1.getMessage());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("MAJNSI-perf_fileLogExcep");
			logger.error("Exception : ", e1);
		}

	}

	private void gestionStatistique(Date dateOp, Structure agence, int nbOperation, int nbOperationPos,
			int nbOperationInt, int nbRejet, Long sommePositionne) {

		BatchStatPlacement batchStatPlacement = new BatchStatPlacement();
		batchStatPlacement.setCodEtatBats("V");
		batchStatPlacement.setDatSystBats(new Date());
		batchStatPlacement.setDatCompBats(dateOp);
		batchStatPlacement.setStructure(agence);
		BatchMetier batchMetier = new BatchMetier();
		batchMetier.setCodBatBmet(Constants.COD_BATCH_MAJNSI_Actel);
		batchStatPlacement.setBatchMetier(batchMetier);
		batchStatPlacement.setLibExtrBats(" Sucées de l’exécution : Nombre total des opérations = " + nbOperation
				+ "  ; Nombre des opérations comptes internes = " + nbOperationInt
				+ " ; Nombre des opérations positionnées  = " + nbOperationPos + " ; Nombre des rejets = " + nbRejet
				+ "  ;  pour la somme de : " + (sommePositionne) + " Dinars");
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchStatPlacement = (BatchStatPlacement) batchService.InsertBatchStatPlacement(batchStatPlacement);
	}

	public void genCroText(ValueObject vo) {

	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);
	}

	private void gestionExceptionEndBatch(Date dateOper, Structure agence, String donnee) {

		BatchExeptionPlac batchExeptionPlac = new BatchExeptionPlac();
		batchExeptionPlac.setDatSystBate(new Date());
		batchExeptionPlac.setDatCompBate(dateOper);
		batchExeptionPlac.setStructure(agence);
		batchExeptionPlac.setLibTpbmBate("Exception Batch MAJ NSI Actel");
		batchExeptionPlac.setLibExpBate(" ** " + donnee);
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchExeptionPlac = (BatchExeptionPlac) batchService.InsertBatchExeptionPlac(batchExeptionPlac);
	}

	public static String insertString(String bag, String marble, int index) {
		String bagBegin = bag.substring(0, index);
		String bagEnd = bag.substring(index);
		return bagBegin + marble + bagEnd;
	}

}
