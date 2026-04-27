package com.bna.smile.model.virement.commande;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.virement.service.IVirementService;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICommande;
import com.oxia.fwk.core.IValueObject;

public class VirementCmd implements ICommande {

	public VirementCmd() {
	}

	public IValueObject getCategorieCompte(IValueObject vo) {

		Context context = ContextHandler.getContext();
		IVirementService iVirementService = (IVirementService) context.getBean("iVirementService");
		return iVirementService.getDetailCompte(vo);

	}

	@Override
	public IValueObject execute(IValueObject vo) {
		Context context = ContextHandler.getContext();
		IVirementService iVirementService = (IVirementService) context.getBean("iVirementService");
		return iVirementService.verifierRib(vo);
	}

	public IValueObject verifierRib(IValueObject vo) {

		Context context = ContextHandler.getContext();
		IVirementService iVirementService = (IVirementService) context.getBean("iVirementService");
		return iVirementService.verifierRib(vo);

	}

	public IValueObject verifierValiditerRibDoEtBenif(IValueObject vo) {

		Context context = ContextHandler.getContext();
		IVirementService iVirementService = (IVirementService) context.getBean("iVirementService");
		return iVirementService.verifierValiditerRibDo(vo);

	}

	public IValueObject virementsAecheance(IValueObject vo) {

		Context context = ContextHandler.getContext();
		IVirementService iVirementService = (IVirementService) context.getBean("iVirementService");
		return iVirementService.virementsAecheance(vo);

	}

	public IValueObject virementAgence(IValueObject vo) {

		Context context = ContextHandler.getContext();
		IVirementService iVirementService = (IVirementService) context.getBean("iVirementService");
		return iVirementService.virementAgence(vo);

	}

	public IValueObject modifierDetailVirement(IValueObject vo) {

		Context context = ContextHandler.getContext();
		IVirementService iVirementService = (IVirementService) context.getBean("iVirementService");
		return iVirementService.modifierDetailVirement(vo);

	}

	public IValueObject verfierContratCpt(IValueObject vo) {

		Context context = ContextHandler.getContext();
		IVirementService iVirementService = (IVirementService) context.getBean("iVirementService");
		return iVirementService.verfierContratCpt(vo);

	}

	public IValueObject rejeterGlobalVirement(IValueObject vo) {

		Context context = ContextHandler.getContext();
		IVirementService iVirementService = (IVirementService) context.getBean("iVirementService");
		return iVirementService.rejeterGlobalVirement(vo);

	}

}
