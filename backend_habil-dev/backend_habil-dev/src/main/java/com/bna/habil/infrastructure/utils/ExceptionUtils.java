package com.bna.habil.infrastructure.utils;

import com.bna.habil.infrastructure.security.model.ResponseHabil;

public class ExceptionUtils {

    private ExceptionUtils() {
    }

    public static ResponseHabil handleException(Exception e) {
        String exceptionName = e.getClass().getName();

        if (exceptionName.contains("DataAccessResourceFailureException")) {
            return (new ResponseHabil(1, "Echec de connexion à la base de données", null));
        } else if (exceptionName.contains("ResourceNotFoundException")) {
            return (new ResponseHabil(1, e.getMessage(), null));

        } else if (exceptionName.contains("MethodArgumentTypeMismatchException")) {
            return (new ResponseHabil(1, "Les données saisies sont mal formatées", null));
        } else if (exceptionName.contains("OperationImpossibleException")) {
            return (new ResponseHabil(1, e.getMessage(), null));
        } else if (exceptionName.contains("SubstitutionChargeException")) {
            return (new ResponseHabil(1, "Les charges des nouvelles garanties ne couvre pas la totalité des charges des anciennes garanties.", null));
        } else if (exceptionName.contains("Une opération similaire existe déjà.")) {

            return new ResponseHabil(1, e.getMessage(), null);
        } else if (exceptionName.contains("SoldeInsuffisantException")) {

            return new ResponseHabil(1, "Échec de l'opération de blocage de montant : Solde du Compte insuffisant", null);
        } else if (exceptionName.contains("DepotAffecteNonTrouveException")) {

            return new ResponseHabil(1, "Échec de l'opération de blocage :  dépôt affecté n'existe pas!", null);
        } else {
            return (new ResponseHabil(1, "Problème Technique", null));
        }
    }
}

