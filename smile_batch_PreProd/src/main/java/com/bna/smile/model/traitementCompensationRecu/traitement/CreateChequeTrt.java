package com.bna.smile.model.traitementCompensationRecu.traitement;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * @author BNA
 * 
 */
public class CreateChequeTrt extends Traitement {

	/**
	 * 
	 */
	public Context context = ContextHandler.getContext();
	/**
	 * 
	 */
	//private static final Log LOGGER = LogFactory.getLog(PouvoirCtr.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bna.commun.traitements.Traitement#genCroText(com.oxia.fwk.core.
	 * ValueObject)
	 */
	@Override
	protected void genCroText(ValueObject paramValueObject) 
	{
		//LOGGER.debug("Generation cro : in createCheque Function...");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.oxia.fwk.beans.traitement.AbstractTraitement#perform(com.oxia.fwk
	 * .core.IValueObject)
	 */
	@Override
	protected IValueObject perform(IValueObject vo) throws Exception
{
		/*LOGGER.debug("Beginning creating Cheque : in function CreateCheque.perform()... ");
		CompensationRecuVo compVo = (CompensationRecuVo) vo;
		Cheque cheque = compVo.getSelectedCheque();
		cheque.setCodEtatChq("PAP");
		try {
			CRUDservice crudService = (CRUDservice) context
					.getBean("crudservice");
			crudService.create(cheque);
			LOGGER.debug("Ending Creating Cheque ...");
			return (cheque);
		} catch (Exception e) {
			com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
			StringBuffer text = new StringBuffer("Error in CreateChequeTrt : ");
			text.append(e.toString());
			erreur.setCode("200");
			erreur.setDescription(text.toString());
			erreur.setKey("CreateChequeTrt");
			cheque.addError(erreur);
			throw new RuntimeException();*/
		return null;
		}


	
}
