package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.commun.model.BatchMetier;
import com.bna.commun.model.BatchStatPlacement;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationEffetVo;
import com.bna.smile.model.domaineplacement.service.BatchService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class PositionCompensationEffetTrt extends Traitement {
	public PositionCompensationEffetTrt() {
	}
	Context context = ContextHandler.getContext();
	SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");

	public IValueObject perform(IValueObject vo) {

		//this.setCroFlag(true);
		
		try {
			CompensationEffetVo compensationVo=(CompensationEffetVo)vo;
			

					PositionEffet21Trt positionEffet21Trt = new PositionEffet21Trt();
					compensationVo =(CompensationEffetVo)positionEffet21Trt.exec(compensationVo);
					Long mnt21  = compensationVo.getMontGlobEffet();
					Long nbre21 = compensationVo.getMontGlobEffet();
					
					PositionEffet22Trt positionEffet22Trt = new PositionEffet22Trt();
					compensationVo =(CompensationEffetVo)positionEffet22Trt.exec(compensationVo);
					Long mnt22  = compensationVo.getMontGlobEffet();
					Long nbre22 = compensationVo.getMontGlobEffet();

					
					PositionEffet25Trt positionEffet25Trt = new PositionEffet25Trt();
					compensationVo =(CompensationEffetVo)positionEffet25Trt.exec(compensationVo);
					Long mnt25  = compensationVo.getMontGlobEffet();
					Long nbre25 = compensationVo.getMontGlobEffet();

					
					
				/*insertion stat et maj du batchjournee
				 * 
				 */
					/****************** Statistique ****************/
					String messageStatistique = "";

					messageStatistique = "Sucées de l’exécution :\n";
					if (nbre21 > Long.valueOf(0)) {
						messageStatistique += nbre21+
								" effet 21 positionés pour un mnt = " + mnt21 + "  ; \n ";
					}
					if (nbre22 > Long.valueOf(0)) {
						messageStatistique += nbre22+
								" effet 22 positionés pour un mnt = " + mnt22 + "  ; \n ";
					}
					if (nbre25 > Long.valueOf(0)) {
						messageStatistique += nbre25+
								" effet 25 positionés pour un mnt = " + mnt25 + "  ; \n ";
					}
					

				//	gestionStatistique(compensationVo, messageStatistique);
					

			return (compensationVo);
		} catch (Exception e) {
			e.printStackTrace();
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer(
					"Erreur dans PositionCompensationTrt : ");
			text.append(e.toString());
			erreur.setCode("100");
			erreur.setDescription(text.toString());
			erreur.setKey("PositionCompensationTrt");
			logger.error("Exception : ", e);
			throw new RuntimeException(e);

		}
	}
	private void gestionStatistique(CompensationEffetVo compensationVo, String message) {

		BatchStatPlacement batchStatPlacement = new BatchStatPlacement();
		batchStatPlacement.setCodEtatBats("V");
		batchStatPlacement.setDatSystBats(new Date());
		batchStatPlacement.setDatCompBats(compensationVo.getDateComptable());
		batchStatPlacement.setStructure(compensationVo.getStructure());
		batchStatPlacement.setLibExtrBats(message);
		BatchMetier batchMetier = new BatchMetier();
		batchMetier.setCodBatBmet(Constants.COD_BATCH_COMPENSATION_CHEQUE);
		batchStatPlacement.setBatchMetier(batchMetier);
		BatchService batchService = (BatchService) context.getBean("batchService");
		batchStatPlacement = (BatchStatPlacement) batchService.InsertBatchStatPlacement(batchStatPlacement);
	}
	public void genCroText(ValueObject vo) {


	}

	public String getNumeroTache(ValueObject vo) {
		return (Constants.CODE_RESSOURCE_GENERALE);

	}

}
