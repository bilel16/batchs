package com.bna.smile.model.constant;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import com.bna.commun.model.Activite;
import com.bna.commun.model.CatSocProf;
import com.bna.commun.model.Client;
import com.bna.commun.model.CodePostal;
import com.bna.commun.model.Devise;
import com.bna.commun.model.Employeur;
import com.bna.commun.model.Gouvernorat;
import com.bna.commun.model.Groupe;
import com.bna.commun.model.MotifRejet;
import com.bna.commun.model.NiveauInstruction;
import com.bna.commun.model.Operation;
import com.bna.commun.model.Pays;
import com.bna.commun.model.Produit;
import com.bna.commun.model.Profession;
import com.bna.commun.model.RegimeMatrimonial;
import com.bna.commun.model.Structure;
import com.bna.commun.model.Tribunal;
import com.bna.commun.model.TypePiece;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.searchengine.SearchEngine;

public class Constants {

	
	public static final Integer TELEX_SENDED = 1;
	public static final Integer TELEX_NOT_SENDED = 0;
	public static final String[] ADMIN_MAILS = {"mohamed.gharbi@bna.tn"};
	
	public static final String COD_TPER_TPER_PERSONNE_PHYSIQUE_INDIVIDUELLE = "1";
	public static final Long COD_OPERATION_DEBLOCAGE_CHEQUE = Long.valueOf("810");
	public static final Long COD_OPERATION_DERESERVATION_CHEQUE = Long.valueOf("3735");
	public static final Long COD_TACHE_REJET_CHEQUE_PREAVIS = Long.valueOf("1");
	public static final Long COD_PRODUIT_CHEQUE = Long.valueOf("1055");
	public static final Long COD_OPERATION_REGLEMENT_CHEQUE = Long.valueOf("806");
	public static final Long COD_OPERATION_PERCEPTION_COMMISSION_PREAVIS = Long.valueOf("807"); // CRO Preavis

	public static final String DEFAULT_COD_TPER_TPER = "1";
	public static final String DEFAULT_COD_CATP_CATP = "1";
	public static final String COD_TYPE_MAND_GENERAL = "G"; // Mandat Général
	public static final String COD_TYPE_MAND_SPECIAL = "S"; // Mandat Spécial
	public static final String COD_TYPE_SIGANTURE_SEPAREE = "S"; // signature séparée
	public static final String COD_TYPE_SIGNATURE_CONJOINTE = "C"; // signature conjointe
	public static final String COD_ETAT_MAND_PERSONNE_VALID = "V"; // Mandat Personne Valide
	public static final String COD_ETAT_MAND_VALID = "V";
	public static final String COD_ETAT_MAND_ATT = "A";
	public static final String COD_ETAT_MAND_ATT_PRE = "S";
	public static final String COD_SYCL_MOD = "M";
	public static final String COD_ETAT_MAND_ANN = "N";
	public static final String COD_SANS_ETAT = "T";
	public static final String COD_ETAT_MAND_ATT_PRE_MOD = "SM";
	public static final String COD_ETAT_MAND_ATT_VAL_MOD = "AM";
	public static final String COD_ETAT_MAND_ATT_PRE_ANN = "SA";
	public static final String COD_ETAT_MAND_ATT_VAL_ANN = "AA";
	public static final String COD_ETAT_MAND_ATT_PRE_REN = "SR";
	public static final String COD_ETAT_MAND_ATT_VAL_REN = "AR";
	public static final String COD_ETAT_MAND_VAL_RES = "VR";
	public static final String COD_ETAT_MAND_R = "R";
	public static final String COD_ETAT_MAND_REJ_MOD = "RM";
	public static final String COD_ETAT_MAND_HIST = "H";
	public static final String COD_ETAT_CPT_VALID = "V";
	public static final String COD_ETAT_CPT_REJETE = "N";
	public static final String COD_ETAT_CPT_ATT = "A";
	public static final String COD_ETAT_CPT_RESILIE = "R";
	public static final String COD_ETAT_CPT_SEMIACTIF = "S";
	public static final String COD_ETAT_CPT_BLOQUE = "B";
	public static final String COD_ETAT_CPT_TCONTENTIEU = "T";
	public static final String COD_ETAT_CLT_ATT = "NA";
	public static final String COD_ETAT_CLT_ACTIF = "A";
	public static final Long COD_STRC_STRC = Long.valueOf(106);
	public static final Long COD_STRC_DAJ = Long.valueOf(834);
	public static final Long COD_DR = Long.valueOf(2);
	public static final Long COD_CIN = Long.valueOf(2);
	public static final Long COD_RCS = Long.valueOf(9);
	public static final Long COD_PASS = Long.valueOf(3);
	public static final Long COD_CSEJ = Long.valueOf(4);
	public static final Long COD_NUM_ORDRE = Long.valueOf(11);
	public static final Long COD_NUM_CPT = Long.valueOf(10);
	public static final String COD_CATEGORIE_MINEUR = "4";
	public static final String COD_MAJ_TUN_INC = "2";
	public static final String COD_MAJ_ETR_INC = "53";
	public static final Long COD_QUALIT_TUTEUR = Long.valueOf(4);
	public static final String PERSPHYSIQUE = "1";
	public static final String PERSMORALE = "2";
	public static final String ENTCOTITULAIRE = "3";
	public static final String[] COD_TRIB_TRIB = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
			"14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27" };
	public static final String COD_SFAM_PRD = "03";
	public static final String COD_GFAM_PRD = "02";
	public static final String COD_CATEGORIE_P_TUN_INC = "2";
	public static final String COD_CATEGORIE_P_ETR_INC = "53";
	public static final String COD_CATEGORIE_P_ETR = "51";
	public static final String COD_CATEGORIE_P_ETR_MIN_EMANCIPE = "52";
	public static final String COD_CATEGORIE_P_MIN_EMANCIPE = "3";
	// Forme Juridique
	public static final String COD_FORME_JURI_PERS_PHYS_TUNISIENNE = "210";
	public static final String COD_FORME_JURI_PERS_PHYS_ETRANGERE = "220";

	public static final Long COD_COMPTE_VERT = Long.valueOf(165);
	public static final Long COD_COMPTE_INTERNE_VIR = Long.valueOf(135);
	public static final Long COD_COMPTE_ECONOMIE_SUR_SALAIRE = Long.valueOf(195);
	public static final Long COD_COMPTE_ALLOC_TOURISTIQUE = Long.valueOf(126);
	public static final Long COD_COMPTE_CHEQUE_PERSONNEL = Long.valueOf(103);
	public static final Long COD_COMPTE_CHEQUE = Long.valueOf(101);

	// region pour scanarization signature PP sur titulaire
	public static final int PosXFRPPTi = 333;
	public static final int PosYFRPPTi = 611;
	public static final int WidthFRPPTi = 444;
	public static final int HeightFRPPTi = 194;
	public static final int PosXARPPTi = 0;
	public static final int PosYARPPTi = 0;
	public static final int WidthARPPTi = 0;
	public static final int HeightARPPTi = 0;

	// region pour scanarization signature PP sur Mandataire 1
	public static final int PosXFRPPMand1 = 770;
	public static final int PosYFRPPMand1 = 1025;
	public static final int WidthFRPPMand1 = 278;
	public static final int HeightFRPPMand1 = 170;
	public static final int PosXARPPMand1 = 0;
	public static final int PosYARPPMand1 = 0;
	public static final int WidthARPPMand1 = 0;
	public static final int HeightARPPMand1 = 0;

	// region pour scanarization signature PP sur Mandataire 2
	public static final int PosXFRPPMand2 = 770;
	public static final int PosYFRPPMand2 = 1200;
	public static final int WidthFRPPMand2 = 278;
	public static final int HeightFRPPMand2 = 180;
	public static final int PosXARPPMand2 = 0;
	public static final int PosYARPPMand2 = 0;
	public static final int WidthARPPMand2 = 0;
	public static final int HeightARPPMand2 = 0;

	// region pour scanarization signature PM sur representant légal
	public static final int PosXFRPMRep = 100;
	public static final int PosYFRPMRep = 1560;
	public static final int WidthFRPMRep = 870;
	public static final int HeightFRPMRep = 400;
	public static final int PosXARPMRep = 0;
	public static final int PosYARPMRep = 0;
	public static final int WidthARPMRep = 0;
	public static final int HeightARPMRep = 0;

	// region pour scanarization signature PM sur Mandataire 1
	public static final int PosXFRPMMand1 = 460;
	public static final int PosYFRPMMand1 = 530;
	public static final int WidthFRPMMand1 = 510;
	public static final int HeightFRPMMand1 = 170;
	public static final int PosXARPMMand1 = 0;
	public static final int PosYARPMMand1 = 0;
	public static final int WidthARPMMand1 = 0;
	public static final int HeightARPMMand1 = 0;

	// region pour scanarization signature PM sur Mandataire 2
	public static final int PosXFRPMMand2 = 460;
	public static final int PosYFRPMMand2 = 730;
	public static final int WidthFRPMMand2 = 510;
	public static final int HeightFRPMMand2 = 170;
	public static final int PosXARPMMand2 = 0;
	public static final int PosYARPMMand2 = 0;
	public static final int WidthARPMMand2 = 0;
	public static final int HeightARPMMand2 = 0;

	// region pour scanarization signature PM sur Mandataire 3
	public static final int PosXFRPMMand3 = 460;
	public static final int PosYFRPMMand3 = 930;
	public static final int WidthFRPMMand3 = 510;
	public static final int HeightFRPMMand3 = 170;
	public static final int PosXARPMMand3 = 0;
	public static final int PosYARPMMand3 = 0;
	public static final int WidthARPMMand3 = 0;
	public static final int HeightARPMMand3 = 0;

	// region pour scanarization signature PM sur Mandataire 4
	public static final int PosXFRPMMand4 = 460;
	public static final int PosYFRPMMand4 = 1130;
	public static final int WidthFRPMMand4 = 510;
	public static final int HeightFRPMMand4 = 170;
	public static final int PosXARPMMand4 = 0;
	public static final int PosYARPMMand4 = 0;
	public static final int WidthARPMMand4 = 0;
	public static final int HeightARPMMand4 = 0;

	// region pour scanarization signature PM sur Mandataire 5
	public static final int PosXFRPMMand5 = 460;
	public static final int PosYFRPMMand5 = 1330;
	public static final int WidthFRPMMand5 = 510;
	public static final int HeightFRPMMand5 = 170;
	public static final int PosXARPMMand5 = 0;
	public static final int PosYARPMMand5 = 0;
	public static final int WidthARPMMand5 = 0;
	public static final int HeightARPMMand5 = 0;

	// constantes pour le module souscription et gestion des demandes cheques...
	public static final String COD_CATEGORIE_PHY_TUN_MAJ = "1";
	public static final Long OPER_DEMANDE_SOUSC_COMPTE = Long.valueOf("1");
	public static final Long TACHE_DEMANDE_SOUSC_COMPTE = Long.valueOf("1");
	public static final Long OPER_VALIDATION_COMPTE = Long.valueOf("2");
	public static final Long TACHE_VALIDATION_COMPTE = Long.valueOf("1");
	public static final Long OPER_DEM_CHQ = Long.valueOf("7");
	public static final Long TACHE_DEM_CHQ_STND = Long.valueOf("1");
	public static final Long OPER_DEM_CHQ_LC_PERS = Long.valueOf("18");
	public static final Long TACHE_DEM_CHQ_LC_PERS = Long.valueOf("1");
	public static final Long OPER_AUTO_CHQ_LC_PERS = Long.valueOf("27");
	public static final Long TACHE_AUTO_CHQ_LC_PERS = Long.valueOf("1");
	public static final Long OPER_VALIDATION_DEM_CHQ = Long.valueOf("9");
	public static final Long TACHE_VALIDATION_DEM_CHQ_STND = Long.valueOf("1");
	public static final Long OPER_VALIDATION_DEM_CHQ_LC_PERS = Long.valueOf("20");
	public static final Long TACHE_VALIDATION_DEM_CHQ_LC_PERS = Long.valueOf("1");
	public static final Long OPER_RECEPTION_CHQ = Long.valueOf("14");
	public static final Long TACHE_RECEPTION_CHQ_STND = Long.valueOf("1");
	public static final Long OPER_RECEPTION_CHQ_LC_PERS = Long.valueOf("23");
	public static final Long TACHE_RECEPTION_CHQ_LC_PERS = Long.valueOf("1");
	public static final Long OPER_RECEPTION_PLAGE_LC = Long.valueOf("28");
	public static final Long TACHE_RECEPTION_PLAGE_LC = Long.valueOf("1");
	public static final Long OPER_DELIV_CHQ = Long.valueOf("15");
	public static final Long TACHE_DELIV_CHQ_STND = Long.valueOf("1");
	public static final Long OPER_DELIV_CHQ_LC_PERS = Long.valueOf("24");
	public static final Long TACHE_DELIV_CHQ_LC_PERS = Long.valueOf("1");
	public static final Long OPER_REMISE_PLAGE_LC = Long.valueOf("29");
	public static final Long TACHE_REMISE_PLAGE_LC = Long.valueOf("1");
	public static final Long OPER_DEST_CHQ = Long.valueOf("17");
	public static final Long TACHE_DEST_CHQ_STND = Long.valueOf("1");
	public static final Long OPER_DEST_CHQ_LC_PERS = Long.valueOf("26");
	public static final Long TACHE_DEST_CHQ_LC_PERS = Long.valueOf("1");
	public static final Long OPER_REST_CHQ = Long.valueOf("16");
	public static final Long TACHE_REST_CHQ_STND = Long.valueOf("1");
	public static final Long OPER_REST_CHQ_LC_PERS = Long.valueOf("25");
	public static final Long TACHE_REST_CHQ_LC_PERS = Long.valueOf("1");
	public static final Long OPER_DCIS_CHQ = Long.valueOf("8");
	public static final Long TACHE_DCIS_CHQ_STND = Long.valueOf("1");
	public static final Long OPER_DCIS_CHQ_LC_PERS = Long.valueOf("19");
	public static final Long TACHE_DCIS_CHQ_LC_PERS = Long.valueOf("1");

	public static final Long DEM_CHQ_ATTENTE = Long.valueOf("1");
	public static final Long DEM_CHQ_VALIDEE = Long.valueOf("2");
	public static final Long DEM_CHQ_REJETEE = Long.valueOf("3");
	public static final Long DEM_CHQ_TOT_SATISFAITE = Long.valueOf("4");
	public static final Long DEM_CHQ_PART_SATISFAITE = Long.valueOf("5");
	public static final Long DEM_CHQ_TOT_DELIVREE = Long.valueOf("6");
	public static final Long DEM_CHQ_PART_DELIVREE = Long.valueOf("7");
	public static final Long DEM_CHQ_ENVOYEE_DR = Long.valueOf("8");
	public static final Long MOTIF_DEM_EN_COURS = Long.valueOf("6");
	public static final Long MOTIF_DEM_INTERDIT_CHQ = Long.valueOf("2");
	public static final Long MOTIF_CHQ_OPP = Long.valueOf("32");
	public static final Long MOTIF_DEM_CPT_INVALIDE = Long.valueOf("1");
	public static final Long MOTIF_DEM_MAND_INVALIDE = Long.valueOf("3");
	public static final Long MOTIF_POUVOIR_INSUFFISANT = Long.valueOf("3");
	public static final Long MOTIF_BLOC_JUDIC = Long.valueOf("5");
	public static final Long OPER_TRANSFERTCTX_COMPTE = Long.valueOf("91");
	public static final Long MOTIF_ATT_CLO = Long.valueOf("2");
	public static final Long MOTIF_ANN_CLO = Long.valueOf("0");

	public static final String CREATION_DEM_CHQ = "9";
	public static final String VALIDATION_DEM_CHQ = "10";
	public static final String RECEPTION_CARNETS_CHEQUES = "12";
	public static final String DELIVRANCE_CARNETS_CHEQUES = "13";
	public static final String DESTRUCTION_CARNETS_CHEQUES = "14";
	public static final String RESTITUTION_CARNETS_CHEQUES = "15";
	public static final String VALIDATION_DEM_CHQ_ENVOYEE_DR = "11";
	public static final String CONSULTATION_DEMANDE_CHQ = "16";
	public static final String CONSULTATION_HISTORIQUE_CHQ = "17";
	public static final String EDITION_DEMANDE_CHQ = "18";
	public static final String EDITION_CHQUIERS = "19";
	public static final String CODE_LETTRE_CHEQUE = "L";
	public static final String CODE_CHEQUE_PERSONALISE = "P";
	public static final String CODE_CHEQUE_STANDARD = "S";

	// //**************************************************************************

	public static final String COD_PAYS_TUNISIE = "TUN"; // code de la tunisie

	// --------------- type de modification
	public static final String COD_MODIF_IDENTIFIANT = "1"; // Identifiant
	public static final String COD_MODIF_IDENT_SEC = "2"; // Identifiant secondaire
	public static final String COD_MODIF_NOM = "3"; // nom et prenom
	public static final String COD_MODIF_ADR_RES = "6"; // adresse
	public static final String COD_MODIF_QUALITE = "4"; // qualite
	public static final String COD_MODIF_ACTIVITE = "5"; // activite
	public static final String COD_MODIF_ADR_CORR = "7"; // adresse correspondance
	public static final String COD_MODIF_CONTACT = "8"; // Contact
	public static final String COD_MODIF_COMPLEMENTAIRE = "9"; // Données Complemntaire
	public static final String COD_MODIF_RAISON_SOCIALE_PM = "10"; // Raison sociale
	public static final String COD_MODIF_FORME_JUR_PM = "11"; // FormeJuridique
	public static final String COD_MODIF_MATRICULE_PM = "12"; // Matricule

	public static final String COD_MODIF_NOMINAT_COMP = "16"; // Matricule
	public static final String COD_MODIF_SOCIALE = "17"; // Matricule
	public static final String COD_MODIF_CATEG_CPT = "19"; // changement de CAtegorie
	public static final String COD_MODIF_TRANSF_CPT = "20"; //
	public static final String COD_MODIF_CAPITAL_GROUP = "21"; //
	public static final String COD_MODIF_CHANGEMENT_ID = "22"; // Changement d'identifiant
	public static final String COD_MODIF_AJOUT_PIECE = "23"; // Ajout d'une pièce annexe
	public static final String COD_MODIF_CHANGEMENT_CATEGORIE = "24"; // Ajout d'une pièce annexe
	public static final String COD_QUAL_FOURNISSEUR = "46"; // code qualité du fournisseur
	public static final String COD_QUAL_ACTIONNAIRE = "45"; // code qualité des actionnaires
	public static final String COD_QUAL_HERITIER = "50"; // code qualité des Heritiers
	public static final String COD_MODIF_TYPLIQ = "30";
	public static final Long TMM = new Long("6");
	public static final Long Taux_Ref_TMM = Long.valueOf("1");
	public static final Long COD_PRD_PRD_PEL = Long.valueOf("105");
	public static final Long COD_PRD_PRD_PEM = Long.valueOf("111");
	public static final Long COD_PRD_PRD_PEE = Long.valueOf("177");
	public static final Long COD_PRD_PRD_VERT = Long.valueOf("165");
	public static final Long COD_PRD_PRD_EPS = Long.valueOf("121");
	public static final Long COD_PRD_PRD_EPS_CARTE = Long.valueOf("166");
	public static final Long COD_PRD_PRD_CPT_INTERNE = Long.valueOf("141");

	public static final Long COD_RESIDENT = new Long("1");
	public static final Long COD_NON_RESIDENT = new Long("0");

	public static final int COD_PRD_PRD_LIM_PEE = 24;
	public static final int COD_PRD_PRD_LIM_PEL = 3;
	// ---------------------------------------------------------------//
	// ----- Constante (Opération Tache) pour le module modufication donnée -----------//

	public static final Long COD_OPER_MODIF_NOM = Long.valueOf("384");
	public static final Long COD_TACHE_MODIF_NOM = Long.valueOf("1");

	public static final Long COD_OPER_MODIF_INTITULE_CPT = Long.valueOf("591");
	public static final Long COD_TACHE_MODIF_INTITULE_CPT = Long.valueOf("1");

	public static final Long COD_OPER_MODIF_TYPE_CPT = Long.valueOf("592");
	public static final Long COD_TACHE_MODIF_TYPE_CPT = Long.valueOf("1");

	public static final Long COD_OPER_MODIF_ADR_CORR = Long.valueOf("385");
	public static final Long COD_TACHE_MODIF_ADR_CORR = Long.valueOf("1");

	public static final Long COD_OPER_MODIF_ACTIVITE = Long.valueOf("386");
	public static final Long COD_TACHE_MODIF_ACTIVITE = Long.valueOf("1");

	public static final Long COD_OPER_MODIF_IDENTIFIANT = Long.valueOf("383");
	public static final Long COD_TACHE_MODIF_IDENTIFIANT = Long.valueOf("1");

	public static final Long COD_OPER_MODIF_MATRICULE_FISCALE = Long.valueOf("389");
	public static final Long COD_TACHE_MODIF_MATRICULE_FISCALE = Long.valueOf("1");

	// ------------------pour La gestion des cartes bancaires

	// /COD_TPER_TCAR
	public static final String COD_TPER_TCAR_PP = "PP";
	public static final String COD_TPER_TCAR_PM = "PM";
	public static final String COD_TPER_TCAR_TP = "TP";
	public static final String COD_OPER_TCAR_R = "R";
	public static final String COD_OPER_TCAR_A = "A";
	// /COD_ETAT_DCAR
	public static final String COD_ETAT_DCAR_Attente = "1";
	public static final String COD_ETAT_DCAR_AttenteDR = "2";
	public static final String COD_ETAT_DCAR_AttenteGarantie = "3";
	public static final String COD_ETAT_DCAR_PrevaliderDR = "4";
	public static final String COD_ETAT_DCAR_AttenteScm = "30";
	public static final String COD_ETAT_DCAR_PrevaliderScm = "31";
	public static final String COD_ETAT_DCAR_AttenteScc = "32";
	public static final String COD_ETAT_DCAR_PrevaliderScc = "33";
	public static final String COD_ETAT_DCAR_RejetScm = "34";
	public static final String COD_ETAT_DCAR_RejetScc = "35";
	public static final String COD_ETAT_DCAR_Valider = "5";
	public static final String COD_ETAT_DCAR_CarteRecu = "6";
	public static final String COD_ETAT_DCAR_CarteRemis = "7";
	public static final String COD_ETAT_DCAR_RejetDemande = "8";
	public static final String COD_ETAT_DCAR_RejetDelivreCarte = "9";
	public static final String COD_ETAT_DCAR_DemandeRempl = "10";
	public static final String COD_ETAT_DCAR_DemandeRemplValide = "11";
	public static final String COD_ETAT_DCAR_CarteRemplacee = "12";
	public static final String COD_ETAT_DCAR_RejetDr = "13";
	public static final String COD_ETAT_DCAR_RejetRemplacement = "14";
	public static final String COD_ETAT_DCAR_DemandeModifPlafond = "36";
	public static final String COD_ETAT_DCAR_ModifPlafondRealise = "37";

	// public static final String COD_ETAT_DCAR_RejetDQMRP = "10";
	// /COD_ETAT_CARB
	public static final String COD_ETAT_CARB_CarteCree = "18";
	public static final String COD_ETAT_CARB_CarteRecu = "19";
	public static final String COD_ETAT_CARB_CarteRemise = "20";
	public static final String COD_ETAT_CARB_AnnulRenouvel = "21";
	// public static final String COD_ETAT_CARB_DemandeRempl = "22";
	// public static final String COD_ETAT_CARB_EnvoiRempl = "23";
	public static final String COD_ETAT_CARB_CarteRemplacee = "24";
	public static final String COD_ETAT_CARB_CarteRestituee = "25";
	public static final String COD_ETAT_CARB_CarteDetruite = "26";
	public static final String COD_ETAT_CARB_CarteMalConfect = "28";
	public static final String COD_ETAT_CARB_RejetDelivreCarte = "29";
	public static final String COD_ETAT_CARB_EnOpposition = "27";
	public static final String COD_ETAT_CARB_AnnulMonetique = "30";

	// /BOOL_ANNL_CARB
	public static final Long BOOL_ANNL_CARB_OUI = Long.valueOf("1");
	// /COD_OPER_OPER / et code tache module carte
	public static final Long COD_OPER_OPER_PECDemandeCarte = Long.valueOf("33");
	public static final Long COD_TACH_TACH_PECDemandeCarte = Long.valueOf("1");
	public static final Long COD_OPER_OPER_PrevalidDR = Long.valueOf("33");
	public static final Long COD_TACH_TACH_PrevalidDR = Long.valueOf("2");
	public static final Long COD_OPER_OPER_PrevalidScm = Long.valueOf("33");
	public static final Long COD_TACH_TACH_PrevalidScm = Long.valueOf("4");
	public static final Long COD_OPER_OPER_PrevalidScc = Long.valueOf("33");
	public static final Long COD_TACH_TACH_PrevalidScc = Long.valueOf("5");
	public static final Long COD_OPER_OPER_ValidDemande = Long.valueOf("33");
	public static final Long COD_TACH_TACH_ValidDemande = Long.valueOf("3");
	public static final Long COD_OPER_OPER_DemandeModifPlafond = Long.valueOf("34");
	public static final Long COD_TACH_TACH_DemandeModifPlafond = Long.valueOf("1");
	public static final Long COD_OPER_OPER_DemandeRempl = Long.valueOf("35");
	public static final Long COD_TACH_TACH_DemandeRempl = Long.valueOf("1");
	public static final Long COD_OPER_OPER_ValidDemandeRempl = Long.valueOf("35");
	public static final Long COD_TACH_TACH_ValidDemandeRempl = Long.valueOf("2");
	public static final Long COD_OPER_OPER_RemplaceeCarte = Long.valueOf("35");
	public static final Long COD_TACH_TACH_RemplaceeCarte = Long.valueOf("3");
	public static final Long COD_OPER_OPER_AnnulRenouvel = Long.valueOf("37");
	public static final Long COD_TACH_TACH_AnnulRenouvel = Long.valueOf("1");

	// public static final Long COD_OPER_OPER_CreeCarte = Long.valueOf("1405");
	public static final Long COD_OPER_OPER_ReceptCarte = Long.valueOf("44");
	public static final Long COD_TACH_TACH_ReceptCarte = Long.valueOf("1");
	public static final Long COD_OPER_OPER_DelivrCarte = Long.valueOf("45");
	public static final Long COD_TACH_TACH_DelivrCarte = Long.valueOf("1");
	public static final Long COD_OPER_OPER_RestitueeCarte = Long.valueOf("46");
	public static final Long COD_TACH_TACH_RestitueeCarte = Long.valueOf("1");
	public static final Long COD_OPER_OPER_DetruireCarte = Long.valueOf("47");
	public static final Long COD_TACH_TACH_DetruireCarte = Long.valueOf("1");

	public static final Long COD_OPER_OPER_ConsultationDemandeCarte = Long.valueOf("-1");
	public static final Long COD_OPER_OPER_ConsultationCarte = Long.valueOf("-2");
	public static final Long COD_OPER_OPER_RenouvelAut = Long.valueOf("-3");
	// /COD_MOTF_REJET:motif de rejet
	public static final Long COD_MOTF_REJET_ContratNonValide = Long.valueOf("1");
	public static final Long COD_MOTF_REJET_FinMandat = Long.valueOf("3");
	public static final Long COD_MOTF_REJET_PouvoirInsuffisant = Long.valueOf("4");
	public static final Long COD_MOTF_REJET_DemandeEnCours = Long.valueOf("6");
	public static final Long COD_MOTF_REJET_ClientDouteux = Long.valueOf("7");
	public static final Long COD_MOTF_REJET_CarteDelivree = Long.valueOf("20");
	// ///////////////////// public static final Long COD_MOTF_REJET_RejetDrDqmrp = Long.valueOf("21");
	public static final Long COD_MOTF_REJET_MalConfectionnee = Long.valueOf("22");
	// /COD_DEM_DCAR: type demandeur: Titulaire, Mandataire ou Cotitulaire
	public static final String COD_DEM_DCAR_Titulaire = "T";
	public static final String COD_DEM_DCAR_Cotitulaire = "C";
	public static final String COD_DEM_DCAR_Mandataire = "M";

	// -----------------------------------------------------------//
	// ---------- MISE A DISPOSTION ---------------//
	public static final String COD_ETAT_MISE_DISPOSITION_ATTENTE = "A";
	public static final String COD_ETAT_MISE_DISPOSITION_VALIDE = "V";

	// -------- Code des taches
	public static final Long TACHE_PRISE_EN_CHARGE = Long.valueOf("1");
	public static final Long TACHE_VALIDATION = Long.valueOf("2");

	// /titre des pages jsp selon opération
	public static final String TITRE_PECDemandeCarte = "Prise en charge demande carte bancaire";
	public static final String TITRE_PrevalidDR = "Prise en charge décision DR";
	public static final String TITRE_PrevalidScm = "Prise en charge décision Sous Comité Monétique";
	public static final String TITRE_PrevalidScc = "Prise en charge décision Sous Comité de Crédit";
	public static final String TITRE_ValidDemande =
			"Validation demande octroi | modification plafond | remplacement carte";
	public static final String TITRE_ReceptCarte = "Réception des cartes confectionées et remplacées";
	public static final String TITRE_DelivrCarte = "Délivrance des cartes bancaires";
	public static final String TITRE_AnnulRenouvel = "Annulation renouvellement cartes bancaires";
	public static final String TITRE_DemandeRempl = "Demande de remplacement carte bancaire";
	public static final String TITRE_RemplaceeCarte = "Remplacement carte bancaire par DQMRP";
	public static final String TITRE_RenouvelAutomatique =
			"Consultation renouvellement automatique des cartes bancaires";
	public static final String TITRE_RestitueeCarte = "Restitution des cartes bancaires";
	public static final String TITRE_DetruireCarte = "Déstruction des cartes bancaires";
	public static final String TITRE_ConsultationDemandeCarte = "Consultation des demandes de carte bancaire";
	public static final String TITRE_ConsultationCarte = "Consultation des cartes bancaires";
	public static final String TITRE_DemandeModifPlafond = "Demande de modification plafond carte bancaire";

	// /Mesage lib fieldset liste de recherche
	public static final String Message_libFieldsetChoix_Demande = "Liste des Demandes";
	public static final String Message_libFieldsetChoix_Carte = "Liste des Cartes";

	// /Mesage choix liste demande
	public static final String Message_Choix_Demande = "Veuillez choisir une demande pour pouvoir la traiter ...";
	public static final String Message_Choix_Carte = "Veuillez choisir une carte pour pouvoir la traiter ...";

	// /Mesage Demande ou carte choisie
	public static final String Message_Demande_Choisie = "Demande choisie";
	public static final String Message_Carte_Choisie = "Carte choisie";
	public static final String Message_Choix_NumeroDemande = "Par numéro de demande";
	public static final String Message_Choix_NumeroCarte = "Par numéro de carte";

	// ---------------pour Les sequences agence (SeqAgence)
	public static final String LIB_SEQ_SEQA_NumDemDchq = "SEQ_NUM_DEM_DCHQ";
	public static final String LIB_SEQ_SEQA_NumDemDcar = "SEQ_NUM_DEM_DCAR";
	public static final String LIB_SEQ_SEQA_NumCertCchq = "SEQ_NUM_DEM_CERT_CHQ";
	public static final String LIB_SEQ_OPER_MOYEN_PAIEMENT = "SEQ_OPER_MOYEN_PAIEMENT";

	public static final Long ETAT_CHQ_RECU = Long.valueOf("1");
	public static final Long ETAT_CHQ_REMI = Long.valueOf("2");
	public static final Long ETAT_CHQ_RESTITUE = Long.valueOf("3");
	public static final Long ETAT_CHQ_DETRUIT = Long.valueOf("4");
	public static final Long ETAT_CHQ_REJETE = Long.valueOf("5");

	// ----------------- Les codes des operations -----------------------------//
	public static final Long COD_OPER_CREAT_MANDAT = Long.valueOf(3);
	public static final Long COD_TACHE_SAISIE_MANDAT = Long.valueOf(1);
	public static final Long COD_TACHE_PREVALID_MANDAT = Long.valueOf(2);
	public static final Long COD_TACHE_VALID_MANDAT = Long.valueOf(3);
	public static final Long COD_OPER_MODIF_MANDAT = Long.valueOf(5);
	public static final Long COD_TACHE_MODIF_MANDAT = Long.valueOf(1);
	public static final Long COD_TACHE_PREVMODIF_MANDAT = Long.valueOf(2);
	public static final Long COD_TACHE_VALIDMODIF_MANDAT = Long.valueOf(3);
	public static final Long COD_OPER_LEV_RES = Long.valueOf(1005);
	public static final Long COD_TACHE_LEV_RES = Long.valueOf(1);
	// operation Renouvellement mandat
	public static final Long COD_OPER_RENOUV_MAND = Long.valueOf(4);
	public static final Long COD_TACH_SAISIE_RENOUV_MAND = Long.valueOf(1);
	public static final Long COD_TACH_PREV_RENOUV_MAND = Long.valueOf(2);
	public static final Long COD_TACH_VAL_RENOUV_MAND = Long.valueOf(3);
	public static final Long COD_TACH_ANL_RENOUV_MAND = Long.valueOf(4);
	// operation Annulation mandat
	public static final Long COD_OPER_ANNUL_MAND = Long.valueOf(6);
	public static final Long COD_TACH_SAISIE_ANNUL_MAND = Long.valueOf(1);
	public static final Long COD_TACH_PREV_ANNUL_MAND = Long.valueOf(2);

	public static final Long COD_TACH_VAL_ANNUL_MAND = Long.valueOf(3);
	public static final Long COD_TACH__ANNUL_SAISIE_ANNUL_MAND = Long.valueOf(4);

	// cloture contrat
	public static final Long COD_OPER_CLOT_CPT = Long.valueOf(76);
	public static final Long COD_TACH_SAISIE_CLOT = Long.valueOf(1);
	public static final Long COD_TACH_ANN_CLOT = Long.valueOf(2);
	public static final Long COD_TACH_VAL_CLOT = Long.valueOf(3);

	// Transfert contentieux
	public static final Long COD_OPER_TRANS_CTX = Long.valueOf(572);
	public static final Long COD_TACH_SAISIE_TRANS_CTX = Long.valueOf(1);
	public static final Long COD_TACH_ANN_TRANS_CTX = Long.valueOf(2);
	public static final Long COD_TACH_VAL_TRANS_CTX = Long.valueOf(2);
	// blocage contrat
	public static final Long COD_OPER_BLOC_CPT_DEC = Long.valueOf(83);
	public static final Long COD_OPER_BLOC_CPT_FAIL = Long.valueOf(88);
	public static final Long COD_OPER_BLOC_CPT_JUR = Long.valueOf(91);
	public static final Long COD_TACH_BLOC_CPT = Long.valueOf(1);
	// déblocage contrat
	public static final Long COD_OPER_DEBLOC_CPT_DEC = Long.valueOf(86);
	public static final Long COD_OPER_DEBLOC_CPT_FAIL = Long.valueOf(89);
	public static final Long COD_OPER_DEBLOC_CPT_JUR = Long.valueOf(92);
	public static final Long COD_TACH_DEBLOC_CPT = Long.valueOf(1);

	// bocage montant
	public static final Long COD_OPER_BLOC_MNT_SAI = Long.valueOf(311);
	public static final Long COD_TACH_BLOC_MNT = Long.valueOf(1);

	// débocage montant
	public static final Long COD_OPER_DEBLOC_MNT_SAI = Long.valueOf(312);
	public static final Long COD_TACH_DEBLOC_MNT = Long.valueOf(1);

	// renouvellement livret
	public static final Long COD_OPER_RENOUV_LIV = Long.valueOf(63);
	public static final Long COD_TACH_RENOUV_LIV = Long.valueOf(1);

	// transfert contrat PEE
	public static final Long COD_OPER_TRANSF_CPT = Long.valueOf(66);
	public static final Long COD_TACH_TRANSF_CPT = Long.valueOf(1);

	// changement cathegorie regime
	public static final Long COD_OPER_CHANG_CAT_RGM = Long.valueOf(64);
	public static final Long COD_TACH_CHANG_CAT_RGM = Long.valueOf(1);

	// motif blocage compte
	public static final Long COD_MOTIF_DEC = Long.valueOf(3);
	public static final Long COD_MOTIF_FAIL = Long.valueOf(4);
	public static final Long COD_MOTIF_JUD = Long.valueOf(5);

	public static final Long COD_OPER_RENOUV_MANDAT = Long.valueOf(4);
	public static final Long COD_TACHE_SAISIE_RENOUV = Long.valueOf(1);
	public static final Long COD_TACHE_PREV_RENOUV = Long.valueOf(2);
	public static final Long COD_TACHE_VAL_RENOUV = Long.valueOf(3);
	public static final Long COD_OPER_ANNUL_MANDAT = Long.valueOf(6);
	public static final Long COD_TACHE_SAISIE_ANNUL = Long.valueOf(1);
	public static final Long COD_TACHE_PREV_ANNUL = Long.valueOf(2);
	public static final Long COD_TACHE_VAL_ANNUL = Long.valueOf(3);

	public static final Long COD_OPER_RETRAIT = Long.valueOf(96);
	public static final Long COD_TACHE_RETRAIT = Long.valueOf(1);

	public static final Long COD_OPER_RETRAIT_DEPL_EMIS = Long.valueOf(100);
	public static final Long COD_TACHE_RETRAIT_DEPL_EMIS = Long.valueOf(1);
	public static final Long COD_TACHE_VALID_RETRAIT_DEPL_RECU = Long.valueOf(2);
	public static final Long COD_TACHE_VALID_RETRAIT_DEPL_EMIS = Long.valueOf(3);
	public static final Long COD_OPER_RETRAIT_MAD = Long.valueOf(97);
	public static final Long COD_TACHE_RETRAIT_MAD = Long.valueOf(1);
	public static final Long COD_OPER_RETRAIT_MG = Long.valueOf(283);
	public static final Long COD_TACHE_RETRAIT_MG = Long.valueOf(1);
	public static final Long COD_OPER_RETRAIT_CA = Long.valueOf(97);
	public static final Long COD_TACHE_RETRAIT_CA = Long.valueOf(1);
	public static final Long COD_PRD_PRD_MAD = Long.valueOf(1251);

	public static final Long PERIODE_RENOUVELLEMNET = Long.valueOf("2");

	public static final Long PERIODE_RECEPT_DESTRUCTION = Long.valueOf("3");

	// ----------------- Les codes des modifications -----------------------------//
	public static final Long COD_MODIF_TRANSFERT_EPARGN = Long.valueOf(18);
	public static final Long COD_MODIF_CHANG_CAT_EPARGN = Long.valueOf(19);

	// ----------------- Les codes des moyens de payement -----------------------------//
	public static final Long COD_CHEQUE = Long.valueOf(1);
	public static final Long COD_OM = Long.valueOf(2);
	public static final Long COD_LIVRET = Long.valueOf(3);

	// ----------------- Les codes devise -----------------------------//

	public static final Long COD_DEV_DINAR = Long.valueOf(788);
	public static final Long COD_DEV_EURO = Long.valueOf(978);
	// -----------------------------------------------------------------------------
	public static final String COD_TYPE_POUVOIR_TITULAIRE = "T";
	public static final String COD_TYPE_POUVOIR_MANDATAIRE = "M";
	public static final String COD_TYPE_POUVOIR_COTITULAIRE = "C";
	public static final String COD_TYPE_POUVOIR_AUCUN = "";
	public static final String COD_TYPE_POUVOIR_INCONNU = "N";
	public static final String COD_TYPE_POUVOIR_INCAPABLE = "I";
	public static final String COD_TYPE_POUVOIR_TIERS = "TR";

	// les code operation pour le module certification cheque.

	public static final Long COD_OPER_CERT_CHQ = Long.valueOf(106);
	public static final Long COD_OPER_CERT_CHQ_BENEF = Long.valueOf(107);
	public static final Long COD_OPER_CERT_AUTRE_AG = Long.valueOf(108);
	// public static final Long COD_OPER_CERT_CHQ_EMISE = Long.valueOf(105);
	// public static final Long COD_OPER_CERT_CHQ_RECUE = Long.valueOf(106);
	public static final Long COD_OPER_REGL_CHQ_CERT = Long.valueOf(109);
	public static final Long COD_OPER_ANNUL_CERT_CHQ = Long.valueOf(110);
	public static final Long COD_OPER_REST_CERT_CHQ = Long.valueOf(111);

	public static final Long ETAT_CERT_ATTENTE = Long.valueOf("0");
	public static final Long ETAT_CERT_VALIDE = Long.valueOf("1");
	public static final Long ETAT_CERT_ANNULEE = Long.valueOf("2");
	public static final Long ETAT_CERT_RESTITUEE = Long.valueOf("3");
	public static final Long ETAT_CERT_REGLEE = Long.valueOf("4");
	public static final Long ETAT_CERT_REJETEE = Long.valueOf("5");
	public static final Long ETAT_CERT_PREVALIDEE = Long.valueOf("6");

	public static final Long ETAT_CERT_PAYE = Long.valueOf("1");
	public static final Long ETAT_CERT_NON_PAYE = Long.valueOf("0");

	// les code operation/tache pour le module opposition moyen paiement.

	public static final Long COD_TACHE_CERT_CHQ = Long.valueOf(2);
	public static final Long COD_TACHE_CERT_CHQ_BENEF = Long.valueOf(2);
	public static final Long COD_TACHE_CERT_CHQ_EMISE = Long.valueOf(1);
	public static final Long COD_TACHE_CERT_CHQ_RECU = Long.valueOf(2);
	public static final Long COD_TACHE_REGL_CHQ_CERT = Long.valueOf(1);
	public static final Long COD_TACHE_ANNUL_CERT_CHQ = Long.valueOf(1);
	public static final Long COD_TACHE_REST_CERT_CHQ = Long.valueOf(1);

	public static final Long COD_OPER_OPER_OPPOSITION_CHQ_CLIENT = Long.valueOf("49");
	public static final Long COD_TACH_TACH_OPPOSITION_CHQ_CLIENT = Long.valueOf("1");
	public static final Long COD_OPER_OPER_OPPOSITION_CHQ_BANQUE = Long.valueOf("50");
	public static final Long COD_TACH_TACH_OPPOSITION_CHQ_BANQUE = Long.valueOf("1");
	public static final Long COD_OPER_OPER_LEVEE_CHQ = Long.valueOf("51");
	public static final Long COD_TACH_TACH_LEVEE_CHQ = Long.valueOf("1");
	public static final Long COD_OPER_OPER_OPPOSITION_LIVRET_CLIENT = Long.valueOf("54");
	public static final Long COD_TACH_TACH_OPPOSITION_LIVRET_CLIENT = Long.valueOf("1");
	public static final Long COD_OPER_OPER_LEVEE_LIVRET = Long.valueOf("55");
	public static final Long COD_TACH_TACH_LEVEE_LIVRET = Long.valueOf("1");
	public static final Long COD_OPER_OPER_OPPOSITION_CIB_CLIENT = Long.valueOf("56");
	public static final Long COD_TACH_TACH_OPPOSITION_CIB_CLIENT = Long.valueOf("1");
	public static final Long COD_OPER_OPER_LEVEE_CIB = Long.valueOf("57");
	public static final Long COD_TACH_TACH_LEVEE_CIB = Long.valueOf("1");
	public static final Long COD_OPER_OPER_OPPOSITION_CARTE_CLIENT = Long.valueOf("58");
	public static final Long COD_TACH_TACH_OPPOSITION_CARTE_CLIENT = Long.valueOf("1");
	public static final Long COD_OPER_OPER_OPPOSITION_CARTE_BANQUE = Long.valueOf("59");
	public static final Long COD_TACH_TACH_OPPOSITION_CARTE_BANQUE = Long.valueOf("1");

	// /COD_ACTR_OPMP: type demandeur: Titulaire, Mandataire ou Cotitulaire
	public static final String COD_ACTR_OPMP_Titulaire = "T";
	public static final String COD_ACTR_OPMP_Cotitulaire = "C";
	public static final String COD_ACTR_OPMP_Mandataire = "M";
	public static final String COD_ACTR_OPMP_Tiers = "TR";
	public static final String COD_ACTR_OPMP_ChefAgence = "CA";

	// /COD_MOYP_TMOY: type du moyen de payement
	public static final Long COD_MOYP_TMOY_Cheque = Long.valueOf("1");
	public static final Long COD_MOYP_TMOY_Traite = Long.valueOf("2");
	public static final Long COD_MOYP_TMOY_Carte = Long.valueOf("3");
	public static final Long COD_MOYP_TMOY_Livret = Long.valueOf("4");
	public static final Long COD_MOYP_TMOY_CIB = Long.valueOf("5");
	public static final Long COD_MOYP_TMOY_BC_Plac = Long.valueOf("8");

	// /COD_ETAT_OPMP: code etat moyen paiement
	public static final String COD_ETAT_OPMP_Opposition = "O";
	public static final String COD_ETAT_OPMP_Levet = "L";

	// /COD_ETAT_CHQI: code etat chequier
	public static final String COD_ETAT_CHQI_Delivre = "2";
	// ----------------- Les codes de la MAD-----------------------------//
	public static final String COD_MONEYGRAM = "2";
	public static final String COD_MISADISPOSITION = "1";
	public static final String COD_CASHADVANCE = "3";

	// ----------------- Les etats de l'Operation MoyPay-----------------//
	public static final String COD_ATTENTE = "A";
	public static final String COD_PREVALID = "P";
	public static final String COD_VALIDATION = "V";

	// Versement espece
	public static final Long BOOL_RESTRICTION_VERS = Long.valueOf("1");

	// ----------------- Les etats de l'Operation MoyPay-----------------//
	public static final String COD_SENS_CR = "C";
	public static final String COD_SENS_DB = "D";
	public static final Long SEUIL_RETRAIT = Long.valueOf(2000000);
	// ----------------- Les codes de DetailOperationMoyPay -------------//
	public static final String COD_TYPE_COMMISSION = "C"; // commissionl
	public static final String COD_TYPE_DAT_VAL = "D"; // date valeur
	public static final String COD_TYPE_TVA = "T"; // TVA

	// les code operation pour le module versement.
	public static final Long COD_OPER_VERSEMENT = Long.valueOf(101);
	public static final Long COD_OPER_VERSEMENT_DEPLACE = Long.valueOf(103);

	// ############### constantes pour le module placement ########################//
	public static final Double TAUX_IRC = Double.valueOf("20");
	public static final String ETAT_DEM_DECIS_SAISIE = "S"; // demande saisie en attente de validation par le chef
															// d'agence
	public static final String ETAT_DEM_DECIS_VALIDE = "V"; // valide pour souscription de placement ( par agence ou par
															// tresorerie)
	public static final String ETAT_DEM_DECIS_SAISI_VALIDEE = "SV"; // saisie validé par le chef d'agence...
	public static final String ETAT_DEM_DECIS_ETUDEE = "E"; // Etudiée au niveau de la trésorerie sans ou avec condition
															// Pref...
	public static final String ETAT_DEM_DECIS_ETUDE_VALIDEE = "EV"; // Etude validée par le responsable Tresorerie
	public static final String ETAT_DEM_DECIS_REJETEE = "R"; // Demande de placement rejetée
	public static final String ETAT_DEM_RENOUV_FORCE = "F"; // Demande de renouvellement placement forcée par batch
	public static final String ETAT_CPLA_REJETE = "R"; // Contrat de placement rejeté
	public static final String ETAT_DEM_DECIS_NOTIFIE = "N"; // Demande notifiée et en attente de validation par la
															 // tresorerie.
	public static final String ETAT_DEM_DECIS_TRAITE = "T"; // Demande traitée : souscription contrat placement.
	public static final String ETAT_ARL_EN_ATTENTE = "A"; // AvancRembLiquid en attente
	public static final String ETAT_ARL_VALIDEE = "V"; // AvancRembLiquid validée
	public static final String ETAT_ARL_REJETEE = "R"; // AvancRembLiquid rejetée
	public static final String ETAT_DOPL_EN_ATTENTE = "A"; // DetailsOperationPlacement en attente
	public static final String ETAT_DOPL_VALIDEE = "V"; // DetailsOperationPlacement validée
	public static final String ETAT_DOPL_REJETEE = "R"; // DetailsOperationPlacement rejetée
	public static final String INTERVAL_TMM_MENSUEL = "M"; // Interval Mensuel
	public static final String INTERVAL_TMM_JOURNALIER = "J"; // Interval Journalier
	public static final Long NBR_JOURS_BC_CAT = Long.valueOf("36500"); // Nbr jours*100
	public static final Long NBR_JOURS_BNAPLC = Long.valueOf("36000"); // Nbr jours*100
	public static final String ETAT_CPT_PLACEMENT_LIQUIDE = "L"; // contrat palcement liquidé
	public static final String ETAT_CPT_PLC_ATT_LIQUID = "AL"; // contrat palcement en attente de liquidation
	public static final String ETAT_CPT_PLC_ATT_RESILIATION = "RE"; // contrat palcement en attente de resiliation
	public static final String ETAT_CPT_PLC_RESILIE = "RL"; // contrat palcement en attente de resiliation
	public static final String ETAT_CONTRAT_PLAC_ECHULIQ = "EL"; // Echu en attente de liquidation
	public static final String ETAT_CONTRAT_PLAC_ANNULE = "AN"; // placement annulé.
	public static final Long COD_OPER_SAISI_DEM_PLAC = Long.valueOf("294");
	public static final Long COD_TACHE_SAISI_DEM_PLAC = Long.valueOf("1");
	public static final Long COD_TACHE_VALID_SAISI_DEM_PLAC = Long.valueOf("2");
	public static final Long COD_OPER_ETUDE_DEM_PLAC = Long.valueOf("296");
	public static final Long COD_TACHE_ETUDE_DEM_PLAC = Long.valueOf("1");
	public static final Long COD_TACHE_VALID_ETUDE_DEM_PLAC = Long.valueOf("2");
	public static final Long COD_OPER_NOTIFICATION_DEM_PLAC = Long.valueOf("297");
	public static final Long COD_TACHE_NOTIFICATION_DEM_PLAC = Long.valueOf("2");
	public static final Long COD_OPER_SOUSC_PLAC = Long.valueOf("298");
	public static final Long COD_OPER_SOUSC_PLAC_SBDV = Long.valueOf("642");
	public static final Long OPER_INT_PRE_SOUSC_PLAC_SBDV = Long.valueOf("643");
	public static final Long OPER_INT_POST_SOUSC_PLAC_SBDV = Long.valueOf("644");
	public static final Long OPER_RENOUVEL_PLAC_AVAN = Long.valueOf("317");
	public static final Long OPER_RENOUVEL_PLAC_APRE = Long.valueOf("318");
	public static final Long COD_OPER_SOUSC_PLACEMENT = Long.valueOf("298");
	public static final String ETAT_CONTRAT_PLAC_VALIDE = "V";
	public static final String ETAT_CONTRAT_PLAC_ATTENTE = "A";
	public static final String ETAT_CONTRAT_PLAC_RENOUVELE = "RN";
	public static final String ETAT_CPLAC_ATT_RENOUVEL = "AR";
	public static final Long COD_OPER_AVANCE_PLAC = Long.valueOf("300");
	public static final Long COD_OPER_REMB_AVANCE_PLAC = Long.valueOf("301");
	public static final Long COD_OPER_DEMANDE_LIQUID_ANTICIPE = Long.valueOf("309");
	public static final Long COD_OPER_DEMANDE_LIQUID_ANTICIPE_PARTIELLE = Long.valueOf("625");
	public static final Long COD_OPER_VERSEMENT_INTERET_LIQUID_ANTICIPE = Long.valueOf("321");
	public static final Long COD_OPER_RESTITUTION_INTERET_LIQUID_ANTICIPE = Long.valueOf("322");
	public static final Long COD_OPER_RISTOURNE_INTERET_LIQUID_ANTICIPE = Long.valueOf("331");
	public static final Long COD_OPER_LIQUID_AECH_PLAC = Long.valueOf("311");
	public static final Long COD_OPER_RESILIATION = Long.valueOf("629");
	public static final Long COD_OPER_VERS_INTERET_SUITE_RESILIATION = Long.valueOf("631");
	public static final Long COD_OPER_RISTOURNE_INTERET_SUITE_RESILIATION = Long.valueOf("630");
	public static final Long COD_OPER_DEMANDE_LIQUID_ANTICIPE_SBDV = Long.valueOf("641");
	public static final Long COD_OPER_DEMANDE_LIQUID_ANTICIPE_PARTIELLE_SBDV = Long.valueOf("645");

	public static final Long COD_PRD_BC_PLAC = Long.valueOf("1001");
	public static final Long COD_PRD_CAT_PLAC = Long.valueOf("1002");
	public static final Long COD_PRD_BNAPLC_PLAC = Long.valueOf("1004");
	public static final Long COD_PRD_BCDC_PLAC = Long.valueOf("1008");
	public static final Long COD_PRD_CATDC_PLAC = Long.valueOf("1009");
	public static final Long COD_DIR_TRESORERIE = Long.valueOf("900");
	public static final Long COD_TACHE_INTERET_AVANC_PLAC = Long.valueOf("1");
	public static final Long COD_TACHE_VALID_AVANC_PLAC = Long.valueOf("2");
	public static final Long COD_TACHE_VALID_REMB_AVANC_PLAC = Long.valueOf("1");
	public static final Long COD_TACHE_VALID_LIQ_ECHEANCE = Long.valueOf("1");
	public static final Long COD_TACHE_VALID_PLAC = Long.valueOf("2");
	public static final Long COD_TACHE_DEMANDE_LIQUIDATION_ANTICIPE = Long.valueOf("1");
	public static final Long COD_TACHE_VALIDATION_LIQUIDATION_ANTICIPE = Long.valueOf("2");
	public static final Long COD_TACHE_INTERET_LIQUID_ANTICIPE = Long.valueOf("1");
	public static final Long COD_OPER_PERSEPT_INTERET_AVANCE_PLAC = Long.valueOf("302");
	public static final Long COD_OPER_REMB_INTERET_REMB_AVANCE_PLAC = Long.valueOf("303");
	public static final Long COD_OPER_ABON_INTERET_REMB_AVANCE_PLAC = Long.valueOf("304");
	public static final Long COD_OPER_PERSEPT_INTERET_REMB_AVANCE_PLAC = Long.valueOf("616");
	public static final Long COD_OPER_PERSEPT_INTERET_AVANCE_PLAC_LIQ = Long.valueOf("302");
	public static final Long COD_OPER_RECUP_BC_PLAC = Long.valueOf("313");
	public static final Long OPER_INT_PRE_SOUSC_PLAC = Long.valueOf("320");
	public static final Long OPER_INT_POST_SOUSC_PLAC = Long.valueOf("613");
	public static final Long COD_OPER_INTERET_SOUSC_PLAC = Long.valueOf("320");
	public static final Long COD_OPER_VERSEMENT_INTERET_PLAC_POST = Long.valueOf("617");
	public static final Long COD_OPER_ABONNE_AVANC_ECHU_PLAC = Long.valueOf("615");
	public static final Long COD_OPER_ABONNE_INTERET_PLAC_POSTCOMPTE = Long.valueOf("618");
	public static final Long COD_OPER_ABONNE_EXTOURN_PLAC = Long.valueOf("619");
	public static final Long COD_OPER_ABONNE_INTERET_PLAC_PRECOMPTE = Long.valueOf("620");
	public static final String COD_STRUCT_INIT_BATCH_PLACEMENT = "900";
	public static final String COD_OPERATION_FIN_BATCH = "603";

	public static final Long COD_TACH_INTERET_SOUSC_PLAC = Long.valueOf("1");
	public static final Long COD_TACH_RECUP_BC_PLAC = Long.valueOf("2");

	public static final Long COD_OPER_OPER_OPPOSITION_BC_PLAC = Long.valueOf("323");
	public static final Long COD_TACH_TACH_OPPOSITION_BC_PLAC = Long.valueOf("1");
	public static final Long COD_OPER_OPER_LEV_OPP_BC_PLAC = Long.valueOf("324");
	public static final Long COD_TACH_TACH_LEV_OPP_BC_PLAC = Long.valueOf("1");

	public static final String COD_FAV_GENERAL = "G";
	public static final String COD_FAV_INDEXE = "I";
	public static final String COD_FAV_FAVEUR = "F";
	public static final String COD_FAV_PREFERENTIEL = "P";

	public static final Long COD_CATP_COTITU = Long.valueOf("29");
	public static final String CODE_AVANCE = "AVAN";
	public static final String CODE_REMBOURSEMENT_AVANCE = "REMB";
	public static final String CODE_LIQUIDATION_ANTICIPE = "LIQA";
	public static final String CODE_RESILIATION_PLAC = "RESL";
	public static final String CODE_LIQUIDATION_ECHEANCE = "LIQE";

	public static final Long COD_TMOY_ESPECE = Long.valueOf("6");

	public static final String NATURE_DEMD_SOUSC = "S";
	public static final String NATURE_DEMD_CESSION = "C";
	public static final String NATURE_DEMD_RENOUV = "R";
	public static final String PLACEMENT_PRECOMPTE = "PRE";
	public static final String PLACEMENT_POSTCOMPTE = "POST";

	public static final Long COD_BATCH_LQUIDATION = Long.valueOf("93");
	public static final Long COD_BATCH_MAJNSI_Actel = Long.valueOf("69");
	public static final Long COD_BATCH_MAJNSI = Long.valueOf("70");
	public static final Long COD_BATCH_RENOUVEL = Long.valueOf("94");
	public static final Long COD_BATCH_INTERET_SERVI = Long.valueOf("95");
	public static final Long COD_BATCH_ABONNEMENT_PLAC = Long.valueOf("96");
	public static final Long COD_BATCH_PRELEVEMENT_ASS_VIE = Long.valueOf("99");
	public static final Long COD_BATCH_RESIL_RENOUV_ASS_VIE = Long.valueOf("80");
	public static final Long COD_BATCH_RENOUV_ASS_VOYAGE = Long.valueOf("10");
	public static final Long COD_BATCH_ENVOI_FICHIER_AMI = Long.valueOf("11");
	public static final Long COD_BATCH_PAIEMENT_ASS_FAIEZ = Long.valueOf("12");
	public static final Long COD_BATCH_ENVOI_FICHIER_CAPI = Long.valueOf("14");
	// ############################################################################//
	// Codes des Mises à disposition
	public static final String CODE_MISE_A_DISPOSITION = "RMD";
	public static final String CODE_MONEY_GRAM = "RMG";
	public static final String CODE_CACH_ADVANCE = "RCA";

	// ############################################################################//
	// Codes type carte bancaire
	public static final String COD_TCAR_TCAR_CIBT = "539995";
	public static final String COD_TCAR_TCAR_CIBT_ANCIEN = "978853";
	public static final String COD_TCAR_TCAR_ELECTRON = "410510";
	public static final String COD_TCAR_TCAR_MAST_NAT = "543223";
	public static final String COD_TCAR_TCAR_MAST_INT = "540218";
	public static final String COD_TCAR_TCAR_VISA_NAT = "455041";
	public static final String COD_TCAR_TCAR_VISA_INT = "455040";
	public static final String COD_TCAR_TCAR_VISAGOLD_NAT = "465245";
	public static final String COD_TCAR_TCAR_VISAGOLD_INT = "465246";

	// ############################################################
	// ############ HABILITATION
	public static final String CODE_PROFIL_CHEF_AGENCE = "CHAG";
	public static final String CODE_PROFIL_SECOND_CHEF_AGENCE = "CHAGSE";

	// numero de la ressource generale: pour les traitement internes exmple: getContratPersonne...
	public static final String CODE_RESSOURCE_GENERALE = "120";

	// ####################################################################################333
	// ############ NUMERO DES RESSOURCES : MODIFICATION DONNEES CLIENT
	public static final String RESS_MODIF_CHANGEMENT_ID = "38302";
	public static final String RESS_MODIF_IDENTIFIANT = "38301";
	public static final String RESS_MODIF_AJOUT_PIECE = "38303";
	public static final String RESS_MODIF_IDENT_SEC = "38304";
	public static final String RESS_MODIF_ADR_RES = "38501";
	public static final String RESS_MODIF_ADR_CORR = "38501";
	public static final String RESS_MODIF_NOM = "38401";
	public static final String RESS_MODIF_INTITULE_CPT = "59101";
	public static final String RESS_MODIF_TYPE_CPT = "59201";
	public static final String RESS_MODIF_ACTIVITE = "38601";
	public static final String RESS_MODIF_QUALITE = "38701";
	public static final String RESS_MODIF_COMPLEMENTAIRE = "39001";
	public static final String RESS_MODIF_NOMINAT_COMP = "39001";
	public static final String RESS_MODIF_SOCIALE = "39002";
	public static final String RESS_MODIF_CONTACT = "38502";
	public static final String RESS_MODIF_RAISON_SOCIALE_PM = "38401";
	public static final String RESS_MODIF_MATRICULE_PM = "38901";
	public static final String RESS_MODIF_CAPITAL_GROUP = "39005";
	public static final String RESS_MODIF_CHANG_CAT_EPARGN = "6501";
	public static final String RESS_MODIF_TRANSFERT_EPARGN = "6601";

	// #########################################################
	// ###### Categorie des personne
	public static final String PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR = "1";
	public static final String PERSONNE_PHYSIQUE_TUNISIENNE_MAJEUR_INCAPABLE = "2";
	public static final String MINEUR_EMANCIPE = "3";
	public static final String MINEUR = "4";
	public static final String PERSONNE_PHYSIQUE_ETRANGERE = "5";
	public static final String PERSONNE_PHYSIQUE_ETRANGERE_MAJEUR = "51";
	public static final String PERSONNE_PHYSIQUE_ETRANGERE_MINEUR_EMANCIPE = "52";
	public static final String PERSONNE_PHYSIQUE_ETRANGERE_MAJEUR_INCAPABLE = "53";

	// ############ Domaine Statistique
	public static final String DOMAINE_SOUSCRIPTION = "1";
	public static final String DOMAINE_PROCURATION = "2";
	public static final String DOMAINE_SUPPORT_PAIEMENT = "3";
	public static final String DOMAINE_OPPOSITION = "4";
	public static final String DOMAINE_MODIFICATION = "5";

	public static final Long COD_DOM_CONTRATCOMPTE = Long.valueOf(1);
	public static final Long COD_DOM_CLIENT = Long.valueOf(2);
	public static final Long COD_DOM_PLACEMENT = Long.valueOf(4);
	public static final Long COD_DOM_CHANGE = Long.valueOf(6);
	public static final Long COD_DOM_MOY_PAI = Long.valueOf(7);
	public static final Long COD_DOM_GUICHET = Long.valueOf(8);
	// ############ cloture Domaine et journéé

	public static final Long ETAT_JSDOM_OUV = Long.valueOf(0);
	public static final Long ETAT_JSDOM_COURCLO = Long.valueOf(1);
	public static final Long ETAT_JSDOM_CLO = Long.valueOf(2);
	public static final Long ETAT_JSDOM_SCLO = Long.valueOf(3);
	public static final Long ETAT_JSTR_CLO = Long.valueOf(1);
	public static final Long ETAT_JSTR_OUV = Long.valueOf(0);
	public static final String COD_OPER_DEB_JRN = "408";
	public static final String COD_TACH_DEB_JRN = "1";
	public static final String COD_OPER_DEB_SES = "596";
	public static final String COD_TACH_DEB_SES = "1";
	public static final String COD_OPER_CLO_DOM = "597";
	public static final String COD_TACH_CLO_DOM = "1";
	public static final String COD_OPER_CLO_JRN = "598";
	public static final String COD_TACH_CLO_JRN = "1";

	// ################# ExonerationTVA
	public static final String COD_ETAT_ETVA_ATTENTE = "A";
	public static final String COD_ETAT_ETVA_VALIDE = "V";
	public static final String COD_ETAT_ETVA_ANNULE = "N";
	public static final String COD_ETAT_ETVA_MODIF = "M";
	public static final char COD_ETA_ETVA_ATTENT = 'A';
	public static final char COD_ETA_ETVA_VALID = 'V';
	public static final char COD_ETA_ETVA_ANNUL = 'N';
	public static final char COD_ETA_ETVA_MODIF = 'M';
	public static final String COD_STRC_DCOMPT = "830";
	public static final Long COD_OPER_CRE_ETVA = Long.valueOf(599);
	public static final Long COD_OPER_MODIF_ETVA = Long.valueOf(600);
	public static final Long COD_OPER_ANNUL_ETVA = Long.valueOf(601);
	public static final Long COD_TACH_PEC_ETVA = Long.valueOf(1);
	public static final Long COD_TACH_VAL_ETVA = Long.valueOf(2);
	public static final Long COD_TACH_MODIF_ETVA = Long.valueOf(1);
	public static final Long COD_TACH_ANNUL_ETVA = Long.valueOf(1);

	public static final Long PARAM_AGE_MINEUR = Long.valueOf(18);

	// ################# Assurance Vie
	public static final Long COD_OPER_REGLEMENT_ASSUR_VIE = Long.valueOf(703);
	public static final Long COD_OPER_SOUSCRIPTION_ASSUR_VIE = Long.valueOf(2980);
	public static final Long COD_OPER_ANNULATION_SOUSCRIPTION_ASSUR_VIE = Long.valueOf(2981);
	public static final Long COD_OPER_RENOUVELLEMENT_SOUSCRIPTION_ASSUR_VIE = Long.valueOf(2982);
	public static final Long COD_OPER_REGLEMENT_ASSUREUR_ASSUR_VIE = Long.valueOf(2983);
	public static final Long COD_OPER_RESILIATION_SOUSCRIPTION_ASSUR_VIE = Long.valueOf(2984);
	public static final Long COD_OPER_ANNULATION_REGLEMENT_ASSUREUR_ASSUR_VIE = Long.valueOf(2985);
	public static final Long COD_TACH_PRELEV_ASSUR_VIE = Long.valueOf(1);
	public static final int TAUX_RETENU_A_LA_SOURCE_ASSUR_VIE = 10;
	public static final String COD_ETA_VALID_ASSUR_VIE = "V";
	public static final String COD_ETA_RESIL_ASSUR_VIE = "R";
	public static final String COD_TACH_PEC = "01";
	public static final String COD_TACH_VALID = "02";
	public static final Long COD_OPER_PRELEV_ASSUR_VIE = Long.valueOf(672);
	public static final Long COD_TACH_REGLEMENT_ASSUR_VIE = Long.valueOf(1);
	public static final Long COD_OPER_ADH_ASSUR_VIE = Long.valueOf(671);
	public static final Long COD_OPER_RESIL_ASS_VIE = Long.valueOf(673);
	public static final Long COD_OPER_CHG_CPT_ASSUR_VIE = Long.valueOf(674);
	public static final Long COD_OPER_GEST_ASSUR_VIE = Long.valueOf(704);
	public static final String LIB_ASS_MGA = "MGA";
	public static final String LIB_ASS_MAGHREBIA = "MAGHREBIA";
	public static final Long COD_ASS_MAGHREBIA = Long.valueOf(2000);
	public static final Long COD_ASS_VIE_AMIE = Long.valueOf(2004);
	public static final Long COD_ASS_VIE_AMIE_DECOUVERT = Long.valueOf(2005);
	public static final Long COD_PRD_ASSUR_VIE = Long.valueOf(2156);
	public static final Long COD_PRD_ASSUR_VIE_DECOUVERT = Long.valueOf(2355);
	public static final Long COD_STRC_DQMRP = Long.valueOf(781);

	public static final Long COD_MOTIF_RESIL_ASSUR_VIE_CLOT_CPT = Long.valueOf(1);
	public static final Long COD_MOTIF_RESIL_ASSUR_VIE_TRANSF_CONT = Long.valueOf(2);
	public static final Long COD_MOTIF_RESIL_ASSUR_VIE_DECES = Long.valueOf(3);
	public static final Long COD_MOTIF_RESIL_ASSUR_VIE_SUP_70 = Long.valueOf(4);
	public static final Long COD_MOTIF_RESIL_ASSUR_VIE_AUTRE = Long.valueOf(5);
	public static final Long COD_MOTIF_RESIL_ASSUR_VIE_SUP_80 = Long.valueOf(6);
	public static final int COD_NBR_MOIS_RENOUV_ASSUR_VIE = 12;
	// ################# domaine Moyen de paiement
	public static final Long COD_OPER_REC_REJ_VIR = Long.valueOf(947);
	public static final Long COD_OPER_POS_VIR = Long.valueOf(822);
	public static final Long COD_OPER_POS_REJ = Long.valueOf(948);
	public static final Long COD_OPER_REC_VIR = Long.valueOf(821);
	public static final Long COD_OPER_REJ_VIR = Long.valueOf(823);
	public static final Long COD_OPER_ENV_REJ_VIR = Long.valueOf(824);

	public static final Long COD_OPER_INSERT_COURS_CHANGE = Long.valueOf(1007);
	public static final Long COD_OPER_REAJUST_COURS_CHANGE = Long.valueOf(1008);
	public static final Long COD_TACH_PEC_COURS_CHANGE = Long.valueOf(1);
	public static final Long COD_TACH_VLD_COURS_CHANGE = Long.valueOf(2);
	// ###### Encaissement Effet ########//
	public static final Long COD_MOYP_TMOY_EFFET = Long.valueOf("11");
	public static final Long MNT_SEUIL_EFF = Long.valueOf("0");
	public static final Long MNT_SEUIL_CPT_VERT = Long.valueOf("10000");
	public static final Long COD_OPERATION_REJET_EFFET = Long.valueOf("827");
	public static final Long COD_OPERATION_REJET_LCR = Long.valueOf("834");

	public static final Long COD_OPERATION_DEBLOCAGE_PROVISION_EFFET = Long.valueOf("1012");
	public static final Long COD_OPER_RECEP_COMP_EFFET = Long.valueOf("825");
	public static final Long COD_OPER_RECEP_COMP_LCR = Long.valueOf("832");
	public static final Long COD_OPER_RECEP_COMP_OC = Long.valueOf("838");
	public static final Long COD_OPER_RECEP_COMP_BC = Long.valueOf("841");

	public static final Long COD_OPER_RECEP_REJET_EFFET = Long.valueOf("949");
	// ESC + ENC
	public static final Long COD_OPER_RECEP_REJET_EFFET_LCR_GLOBAL = Long.valueOf("2070");
	public static final Long COD_OPER_RECEP_REJET_ENC_LCR = Long.valueOf("963");
	public static final Long COD_OPER_RECEP_REJET_ESC_LCR = Long.valueOf("964");
	public static final Long COD_OPER_RECEP_RECLAM_EFFET = Long.valueOf("989");
	public static final Long COD_PRODUIT_EFFET = Long.valueOf("1061");
	public static final Long COD_PRODUIT_EFFET_OC = Long.valueOf("1229");

	public static final Long COD_OPERATION_DENOUEMENT_CPT_IMPAYE = Long.valueOf("724");
	public static final Long COD_OPERATION_DENOUEMENT_CPT_IMPAYE_LCR = Long.valueOf("1018");
	public static final Long COD_OPERATION_PAIEMENT_EFFET = Long.valueOf("826"); // 826
	public static final Long COD_OPERATION_PAIEMENT_LCR = Long.valueOf("833"); // 833
	public static final Long COD_OPERATION_PAIEMENT_BC = Long.valueOf("842");
	public static final Long COD_OPERATION_PAIEMENT_OC = Long.valueOf("839");

	public static final Long COD_OPERATION_ENCAISSEMENT_EFFET_PAYEE = Long.valueOf("962"); // 826
	public static final Long COD_OPERATION_ENCAISSEMENT_LCR_PAYEE = Long.valueOf("2069");

	public static final Long COD_TACHE_EFFET = Long.valueOf("1");
	public static final Long COD_NECD_NECD_REJET_EFFET = Long.valueOf("4");
	public static final Long COD_NECD_NECD_PAIEMENT_EFFET = Long.valueOf("197");

	public static final Long COD_TACHE_PAIEMENT_EFFET = Long.valueOf("1");
	public static final Long COD_TYPE_EFFET_NORMALISE = 41L;
	public static final Long COD_NECD_NECD_DEBLOCAGE_PROVISION_EFFET = Long.valueOf("3");
	public static final Long COD_PRD_FACIL_CAISSE = Long.valueOf(2139);
	// ####################################################################################333
	// ############ NUMERO DES RESSOURCES : GESTION CAISSE
	public static final String RESS_OUV_CAISSE = "27401";
	public static final String RESS_CHANG_CAISSIER = "27501";
	public static final String RESS_OUV_CAISSE_VAC = "27101";
	public static final Long COD_OPER_OUV_CAISSE_VAC = Long.valueOf("271");
	public static final Long COD_TACH_OUV_CAISSE_VAC = Long.valueOf("1");
	public static final Long COD_OPER_ENV_INTER_CAISSE = Long.valueOf("412");
	public static final Long COD_TACH_ENV_INTER_CAISSE = Long.valueOf("1");
	public static final Long COD_OPER_ALIM_INTER_CAISSE = Long.valueOf("277");
	public static final Long COD_OPER_ENV_EXTERN_CAISSE = Long.valueOf("278");
	public static final Long COD_TACH_ENV_EXTERN_CAISSE = Long.valueOf("1");

	// ############################################################################//
	// Codes de la gestion des caisses
	public static final String STATUS_CAISSE_OUVERTE = "O";
	public static final String STATUS_CAISSE_FERMER = "F";
	public static final String STATUS_CAISSE_INITIALISE = "I";
	public static final String TYPE_CAISSE_PRINCIPALE = "P";
	public static final String TYPE_CAISSE_VACATION = "V";
	public static final String SENS_MOUVEMENT_CREDIT = "C";
	public static final String SENS_MOUVEMENT_DEBIT = "D";
	public static final String STATUS_MVT_ENVOI = "0";
	public static final String STATUS_PEC_ENVOI_EXTERN = "A";

	// ############################################################################//
	// / Virement ///

	public static final Integer[] listCompteEnDinars = { 101, 103, 104, 109, 110,112, 115, 129, 321, 126, 134, 136, 137,
			121, 117, 118, 120, 153, 157, 159, 177, 111, 105, 165,144, 146,166,158,213};

	public static final Integer[] listCompteEnDinarsConvertibles = { 102, 106, 116, 140, 155, 167, 171, 183, 193, 195,	131, 411, 509 };

	public static final Integer[] listCompteSpeciauxEnDinars = { 149, 185, 181, 187, 124, 167, 171, 183, 193, 195, 131,
			411 };

	public static final Integer[] listCompteEnDevises = { 108, 130, 132, 142, 191, 163, 169, 179, 147, 421, 507, 1035,148 };

	public static final Long[] produitVirementPermanentEligible = { new Long(101), new Long(103), new Long(104),
			new Long(109), new Long(115), new Long(129), new Long(126), new Long(0134), new Long(137), new Long(102),
			new Long(106), new Long(116), new Long(140), new Long(155), new Long(167), new Long(171), new Long(183),
			new Long(139), new Long(195), new Long(131), new Long(149), new Long(185), new Long(181), new Long(187),
			new Long(124), new Long(121), new Long(120), new Long(118), new Long(117), new Long(153), new Long(157),
			new Long(159), new Long(177) ,new Long(166),new Long(158)  };

	public static final Long[] produitVirementPonctuelNonEligible = { new Long(111), new Long(165), new Long(177),
			new Long(105), new Long(108), new Long(130), new Long(132), new Long(142), new Long(191), new Long(163),
			new Long(169), new Long(179), new Long(147), new Long(421) };

	public static final Long[] produitVirementMasseEligible = { new Long(101), new Long(109), new Long(115),
			new Long(129), new Long(137) ,new Long(158) };

	public static final Long[] produitCompteEpargneLiee =
			{ new Long(177), new Long(111), new Long(105), new Long(165) };

	public static final Long COD_PRODUIT_VIREMENT_PERMANENT = Long.valueOf("1064");
	public static final Long COD_PRODUIT_VIREMENT_PERMANENT_LIES = Long.valueOf("2280");
	public static final Long COD_PRODUIT_VIREMENT_PONCTUEL = Long.valueOf("1063");
	public static final Long COD_PRODUIT_VIREMENT_MASSE = Long.valueOf("1062");
	public static final Long COD_PRODUIT_VIREMENT_ADT = Long.valueOf("1063");

	public static final Long COD_ETAT_VIREMENT_ATTENTE = Long.valueOf("0");
	public static final Long COD_ETAT_VIREMENT_EXECUTER = Long.valueOf("1");
	public static final Long COD_ETAT_VIREMENT_ANNULER = Long.valueOf("2");
	public static final Long COD_ETAT_VIREMENT_RESILIER = Long.valueOf("3");
	public static final Long COD_ETAT_VIREMENT_ENCOUREXECUTION = Long.valueOf("4");
	public static final Long COD_ETAT_VIREMENT_REJETER = Long.valueOf("5");
	public static final Long COD_ETAT_VIREMENT_DECALAGE_AUTO_J_PLUS_UN = Long.valueOf("6");
	public static final Long COD_ETAT_VIREMENT_DECALAGE_AUTO_DEFINITIF = Long.valueOf("7");

	public static final Long COD_OPER_DEMANDE_VIREMENT = Long.valueOf("714");
	public static final Long COD_TACH_PEC_VIR = Long.valueOf("01");
	public static final Long COD_TACH_PEC_DETAIL_VIR = Long.valueOf("02");
	public static final Long COD_TACH_MODIF_VIR = Long.valueOf("03");
	public static final Long COD_TACH_ANNUL_VIR = Long.valueOf("04");

	public static final Long COD_OPER_RESILIATION_VIREMENT = Long.valueOf("215");
	public static final Long COD_TACH_RESILIER_VIR = Long.valueOf("01");

	public static final Long COD_OPER_MODIFICATION_ECHEANCE_VIREMENT = Long.valueOf("207");
	public static final Long COD_TACH_MODIFICATION_ECHEANCE_VIR = Long.valueOf("01");

	public static final Long COD_OPER_EXECUTION_VIREMENT_PONCTUEL = Long.valueOf("715");
	public static final Long COD_TACH_EXECUTION_VIREMENT_PONCTUEL = Long.valueOf("01");

	public static final Long COD_OPER_EXECUTION_VIREMENT_PERMANENT = Long.valueOf("720");
	public static final Long COD_TACH_EXECUTION_VIREMENT_PERMANENT = Long.valueOf("01");

	public static final Long COD_OPER_EXECUTION_VIREMENT_MASSE = Long.valueOf("718");
	public static final Long COD_TACH_EXECUTION_VIREMENT_MASSE = Long.valueOf("01");

	public static final Long COD_OPER_EXECUTION_VIREMENT_SUCCESSION = Long.valueOf("1228");

	public static final Long COD_FLAG_FICHIER_VIREMENT_NON_TRAITER = Long.valueOf("0");
	public static final Long COD_FLAG_FICHIER_VIREMENT_TRAITER = Long.valueOf("1");

	public static final Long COD_OPER_POSITION_VIREMENT = Long.valueOf("822");
	public static final Long COD_OPER_VIR_SIEGE = Long.valueOf("2018");
	public static final Long COD_OPER_VIR_ACTEL_EMIS = Long.valueOf("2121");
	public static final Long COD_OPER_VIR_ACTEL_RECUS = Long.valueOf("2122");
	public static final Long COD_OPER_POSITION_VIREMENT_COMPTE_DEVISES = Long.valueOf("1064");
	public static final Long COD_OPER_REAFFECTATION_REJETS_VIREMENT = Long.valueOf("948");
	public static final Long COD_OPER_ENVOI_VIREMENT = Long.valueOf("721");
	public static final Long COD_OPER_RECEPTION_VIREMENT_AGENCE = Long.valueOf("821");
	public static final Long COD_OPER_REJET_VIREMENT = Long.valueOf("823");
	public static final Long COD_OPER_ENVOI_REJETS_VIREMENT = Long.valueOf("824");
	public static final Long COD_OPER_RECEPTION_REJETS_VIREMENT = Long.valueOf("947");
	public static final Long COD_OPER_RECEPTION_VIREMENT_SGMT = Long.valueOf("1203");
	public static final Long COD_OPER_ALIMENTATION_FAVEUR_COMPTE_VERT = Long.valueOf("1204");
	public static final Long COD_OPER_ALIMENTATION_FAVEUR_COMPTE_DEPOT = Long.valueOf("1205");
	public static final Long COD_OPER_ALIMENTATION_APARTIR_COMPTE_VERT = Long.valueOf("1206");
	public static final Long COD_TACH_ALIMENTATION_FAVEUR_COMPTE_DEPOT = Long.valueOf("1");

	public static final Long COD_OPER_VIREMENT_EMIS_DEVISE_MEME_AG = Long.valueOf("2086");
	public static final Long COD_OPER_VIREMENT_RECU_DEVISE_MEME_AG = Long.valueOf("2087");
	public static final Long COD_TACH_VIREMENT_DEVISE_MEME_AG = Long.valueOf("1");
	
	public static final Long MONTANT_VIREMENT_SGMT = Long.valueOf("100000000");
	public static final Long SOLDE_MIN_COMPTE_DEPOT = Long.valueOf("500000");
	public static final Long SOLDE_MAX_COMPTE_VERT = Long.valueOf("10000000");

	public static final String LIB_SEQUENCE_LOT_AGENCE = "SEQ_LOT_VIREMENT";
	public static final String LIB_SEQUENCE_VIR_SGMT = "SEQ_VIR_SGMT";

	public static final Long SOLDE_MIN_COMPTE_DAV = Long.valueOf("100000");
	public static final Long SOLDE_MIN_COMPTE_EPARGNE = Long.valueOf("10000");
	public static final String PATH_FICHIER_VIREMENT = "../";

	public static final Long COD_NECD_NECD_822 = Long.valueOf("272");
	public static final Long COD_NECD_NECD_715 = Long.valueOf("267");
	public static final Long COD_NECD_NECD_718 = Long.valueOf("271");
	public static final Long COD_NECD_NECD_720 = Long.valueOf("270");
	public static final Long COD_NECD_NECD_1064 = Long.valueOf("134");
	public static final Long COD_NECD_NECD_1228 = Long.valueOf("267");

	// public static final int NBRE_MAX_AGENCE = new Integer(161);

	public static final Long COD_SENS_EMIS = Long.valueOf("1");
	public static final Long COD_SENS_RECU = Long.valueOf("2");
	public static final Long MIN_COMMISSION = Long.valueOf("1500");

	public static final Long COD_BATCH_VIREMENT_AECHEANCE = Long.valueOf("79");
	public static final Long COD_BATCH_VIREMENT_LIEES_COMPTES_VERTS = Long.valueOf("80");
	public static final Long COD_ENREGISTREMENT_VIREMENT = Long.valueOf("10");
	public static final Long COD_BATCH_FRAIS_TENUE_CPTE_PACK = Long.valueOf("13");
	// ############################################################################//
	// / PRELEVEMENTS ///

	public static final Long COD_PRODUIT_PRELEVEMENTS = Long.valueOf("1066");

	public static final Long COD_OPER_RESILIATION_CONTRAT_DOMICILIATION = Long.valueOf("237");
	public static final Long COD_OPER_RECEP_PRELEV_RECU = Long.valueOf("816");
	public static final Long COD_OPER_RECEP_LOT_CONTRAT_DOMICILIATION = Long.valueOf("817");
	public static final Long COD_OPER_REGLEMENT_PRELEVEMENT = Long.valueOf("818");
	public static final Long COD_OPER_REJET_PRELEVEMENT = Long.valueOf("819");
	public static final Long COD_OPER_ENVOI_REJET_PRELEVEMENT = Long.valueOf("820");
	public static final Long COD_OPER_REAFFECTAION_REJETS_PRELEVEMENTS = Long.valueOf("1293");
	public static final Long COD_OPER_OPPOSITION_SUR_PRELEVEMENT = Long.valueOf("1292");
	public static final Long COD_OPER_LEVEE_OPPOSITION_SUR_PRELEVEMENT = Long.valueOf("0");
	public static final Long COD_ENREGISTREMENT_PRELEVEMENT = Long.valueOf("20");
	public static final Long COD_ENREGISTREMENT_DOMICILIATION = Long.valueOf("80");
	public static final Long COD_OPER_ENVOI_PRELEVEMENT_COMPENSATION = Long.valueOf("781");

	public static final Long COD_TACH_RECEP_CONTRAT_DOMICILIATION = Long.valueOf("1");
	public static final Long COD_TACH_RECEP_PRELEV_RECU = Long.valueOf("1");
	public static final Long COD_TACH_REGLEMENT_PRELEV_RECU = Long.valueOf("1");
	public static final Long COD_TACH_REJET_PRELEV_RECU = Long.valueOf("1");
	public static final Long COD_TACH_ENVOI_PRELEVEMENT_COMPENSATION = Long.valueOf("1");

	public static final Long COD_NECD_NECD_817 = Long.valueOf("323");
	public static final Long COD_NECD_NECD_818 = Long.valueOf("36");
	public static final Long COD_NECD_NECD_819 = Long.valueOf("283");
	public static final Long COD_NECD_NECD_1292 = Long.valueOf("38");
	public static final Long COD_NECD_NECD_1293 = Long.valueOf("192");

	public static final Long[] produitPrelevementsEligible = { new Long(101), new Long(103), new Long(109),new Long(112),
			new Long(115), new Long(136), new Long(149), new Long(155),new Long(158), new Long(181),new Long(183) };

	public static final String COD_ETAT_PRELEVEMENT_ATTENTE = "A";
	public static final String COD_ETAT_PRELEVEMENT_EXECUTE = "V";
	public static final String COD_ETAT_PRELEVEMENT_REJETE = "R";

	public static final String COD_ETAT_DOMICILIATION_VALIDE = "V";
	public static final String COD_ETAT_DOMICILIATION_RESILIE = "R";
	public static final String COD_ETAT_DOMICILIATION_ANNULE = "A";

	public static final String COD_ETAT_DETAIL_DOM_TEMP_VALIDE = "V";
	public static final String COD_ETAT_DETAIL_DOM_TEMP_REJETE = "R";
	public static final String COD_ETAT_DETAIL_DOM_TEMP_ATTENTE = "A";
	public static final String COD_ETAT_OPPOSITION_PRELEVEMENT = "O";
	public static final String COD_ETAT_LEVEE_OPPOSITION_PRELEVEMENT = "L";
	public static final String COD_NATURE_AJOUT_TRACE_DOMICILIATION = "A";
	public static final String COD_NATURE_MODIFICATION_TRACE_DOMICILIATION = "M";
	public static final String COD_NATURE_SUPPRESSION_TRACE_DOMICILIATION = "S";
	public static final String COD_NATURE_RESILIATION_TRACE_DOMICILIATION = "R";

	public static final Long COD_BATCH_PRELEVEMENT_AECHEANCE = Long.valueOf("81");
	public static final Long COD_BATCH_DOMICILIATION = Long.valueOf("82");
	public static final Long COD_BATCH_COMPENSATION_CHEQUE = Long.valueOf("60");
	public static final Long COD_BATCH_COMPENSATION_EFFET = Long.valueOf("41");
	public static final Long COD_ENREGISTREMENT_GLOBAL_PRESENTATION = Long.valueOf("11");
	public static final Long COD_ENREGISTREMENT_GLOBAL_REJET = Long.valueOf("12");

	public static final Long COD_ENREGISTREMENT_DETAIL_PRESENTATION = Long.valueOf("21");
	public static final Long COD_ENREGISTREMENT_DETAIL_REJET = Long.valueOf("22");

	// ***********Motif Rejet_Prelevement **************//
	public static final Long COD_REJET_OPPO_AUTRES_MOTIF = Long.valueOf("4");
	public static final Long COD_REJET_ABS_PROVISION = Long.valueOf("10");
	public static final Long COD_REJET_INSUFF_PROVISION = Long.valueOf("11");
	public static final Long COD_REJET_INDISPO_PROVISION = Long.valueOf("12");
	public static final Long COD_REJET_COMPTE_CLOTURE = Long.valueOf("13");
	public static final Long COD_REJET_TITULAIRE_DECEDE = Long.valueOf("20");
	public static final Long COD_REJET_VALEUR_MAL_ACHEMINEE = Long.valueOf("39");
	public static final Long COD_REJET_ABSENCE_CON_DOMI = Long.valueOf("40");
	public static final Long COD_REJET_PREL_DEJA_REGLE = Long.valueOf("43");
	public static final Long COD_REJET_PREL_DEJA_REJETE = Long.valueOf("44");
	public static final Long COD_REJET_OPERATION_NON_AUTORISEE = Long.valueOf("47");
	public static final Long COD_REJET_AUTRES_MOTIFS = Long.valueOf("60");

	// ############################################################################//
	// / COMPENSATION ///
	public static final Long COD_OPER_BLOC_PROV = Long.valueOf("808");
	public static final Long COD_TACH_BLOC_PROV = Long.valueOf("1");
	public static final Long COD_OPER_RECEP_COMP = Long.valueOf("805");
	public static final Long COD_TACH_RECEP_COMP = Long.valueOf("1");
	public static final Long MNT_SEUIL_CHQ = Long.valueOf("20000");

	public static final String COD_PREAVIS = "81";
	public static final String COD_CNP = "82";
	public static final String COD_ARP = "83";
	public static final String COD_PAPILLON = "84";

	// Codes Compensation
	public static final Long COD_CHEQUE_PREMIERE_PRESENTATION = Long.valueOf("30");
	public static final Long COD_CHEQUE_REPRESENTATION_PAIEMENT_PARTIEL = Long.valueOf("31");
	public static final Long COD_CHEQUE_REPRESENTATION_SUITE_ARP = Long.valueOf("32");
	public static final Long COD_CHEQUE_REPRESENTATION_SUITE_PAPILLON = Long.valueOf("33");

	// Cod Opp.
	public static final String COD_ETAT_OPP_CHQ_PERT = "P";
	public static final String COD_ETAT_OPP_CHQ_VOL = "V";
	public static final String COD_ETAT_OPP_CHQ_FAIL = "F";
	public static final String COD_ETAT_OPP_CHQ_AUT = "A";

	// Etat Chq Tmp
	public static final String COD_ETAT_CHQ_TMP_POSITIONE = "P";
	public static final String COD_ETAT_CHQ_TMP_REGLE = "R";
	public static final String COD_ETAT_CHQ_TMP_TOILETTE = "T";
	public static final String COD_ETAT_CHQ_TMP_TRAITE = "O";

	// Etat Chq
	public static final String COD_ETAT_CHQ_PAYE = "P";
	public static final String COD_ETAT_CHQ_REJETE = "R";

	// ETAt CHQ Devise
	public static final String COD_CHQ_LIB_DINAR_CPT_DEV = "M";

	// Cod. Rej
	public static final String COD_MREJ_CHQ_MAL_ACHEMINE = "39";
	public static final String COD_MREJ_CHQ_DEJ_REGL = "43";
	public static final String COD_MREJ_CHQ_DEJ_REJ = "44";
	public static final String COD_MREJ_CHQ_OPP_PERT = "01";
	public static final String COD_MREJ_CHQ_OPP_VOL = "02";
	public static final String COD_MREJ_CHQ_OPP_FAIL = "03";
	public static final String COD_MREJ_CHQ_OPP_AUT = "04";
	public static final String COD_MREJ_CHQ_INF_SEUIL = "14";
	public static final String COD_MREJ_CHQ_PERSCRITE = "31"; // prescrite : à vérifier : mal interprété juridiquement
	public static final String COD_MREJ_CHQ_COMP_CLOT = "13";
	public static final String COD_MREJ_CHQ_INDISP_PROV = "12";
	public static final String COD_MREJ_CHQ_ABS_PROV = "10";
	public static final String COD_MREJ_CHQ_INSUFF_PROV = "11";
	public static final String COD_MREJ_CHQ_DECE_PERS = "20";
	public static final String COD_MREJ_CHQ_TIRE_SOI_MEME = "15";
	public static final String COD_MREJ_CHQ_MAL_PRES = "36";
	public static final String COD_MREJ_CHQ_MNT_REC_ERR = "16";

	public static final Long SEUIL_SLD_CR_101 = Long.valueOf(100000);
	public static final Long SEUIL_MIN_165 = Long.valueOf(10000);
	public static final Long COD_REJET_PAPILLON = Long.valueOf("84");
	public static final Long COD_REJET_PREAVIS = Long.valueOf("81");
	public static final Long COD_REJET_CNP_OPPOSITION = Long.valueOf("82");
	// fichiers manuels
	public static final Long COD_REJET_CNP_MANUELLE = Long.valueOf("87");
	public static final Long COD_ARP_MANUELLE = Long.valueOf("88");
	public static final Long COD_REJET_ANR_MANUELLE = Long.valueOf("89");

	public static final Long COD_REJET_ANR = Long.valueOf("89");
	public static final String COD_CHEQUE_MANUEL_VALIDE = "MAN";

	public static final String COD_ETAT_CHEQUE_PAYEE = "P";
	public static final String COD_ETAT_CHEQUE_REJETEE = "R";

	// Etat EFF
	public static final String COD_ETAT_EFF_TMP_FIN = "F";
	public static final String COD_ETAT_EFF_TMP_POS = "P";
	public static final String COD_ETAT_EFF_TMP_REC = "E";
	public static final Long COD_TYPE_EFFET_NON_NORMALISE = 40L;
	public static final String COD_ETAT_EFFET_POSITIONNE = "P";
	public static final String COD_ETAT_EFFET_TRAITE = "F";
	public static final String COD_ETAT_EFFET_TRANSFERE = "T";
	public static final String COD_EFFET_MANUEL_VALIDE = "MAN";
	public static final String COD_EFFET_TRAITEMENT_AUTO = "A";
	public static final String COD_ETAT_EFFET_REJETE = "R";
	public static final String COD_ETAT_EFFET_IMPAYE = "I";
	public static final String COD_ETAT_EFFET_REJET_LIE_PROVISION = "L";
	public static final String COD_DELIVRANCE_EFFET_GUICHET = "G";
	public static final String COD_DELIVRANCE_EFFET_COURIER = "C";
	public static final String COD_ETAT_EFFET_TOILETTAGE = "T";
	public static final String COD_ETAT_EFFET_REGLE = "P";
	public static final String COD_ETAT_EFFET_PROTESTE = "O";
	public static final String COD_TYPE_BLOC_EFFET_BAP = "BAP";
	public static final String COD_ETAT_EFF_IMP = "I";
	public static final String COD_ETAT_EFF_REJ = "R";
	public static final String COD_ETAT_EFF_REG = "P";
	public static final String COD_ETAT_EFF_PROT = "O";
	public static final String COD_ETAT_EFF_REC = "E";
	public static final Long COD_TYP_MOY_PAI_EFFET = Long.valueOf("11");
	public static final Long COD_LCN = Long.valueOf(41);
	public static final Long COD_LCR = Long.valueOf(40);
	public static final Long COD_BC = Long.valueOf(43);
	public static final Long COD_OC = Long.valueOf(42);

	public static final Long COD_OBLIGATION_CAUTIONNEE = Long.valueOf(42);
	public static final Long[] produitEligibleEncaissementEffet = { Long.valueOf(121), Long.valueOf(183),
			Long.valueOf(191), Long.valueOf(179), Long.valueOf(135), Long.valueOf(101), Long.valueOf(103),
			Long.valueOf(109), Long.valueOf(115), Long.valueOf(136) };

	// Motif encaissement

	public static final String COD_MREJ_EFF_OPP_PERTE = "01";
	public static final String COD_MREJ_EFF_OPP_VOL = "02";
	public static final String COD_MREJ_EFF_OPP_FAILLITE = "03";
	public static final String COD_MREJ_EFF_OPP_AUTRE = "04";
	public static final String COD_MREJ_EFF_PROV_ABS = "10";
	public static final String COD_MREJ_EFF_PROV_INDIP = "12";
	public static final String COD_MREJ_EFF_PROV_INSUFF = "11";
	public static final String COD_MREJ_EFF_COMPT_CLOTURE = "13";
	public static final String COD_MREJ_EFF_COMPT_DECES = "20";
	public static final String COD_MREJ_EFF_VAL_MAL_PRESENTE = "36";
	public static final String COD_MREJ_EFF_VAL_MAL_ACHEMINEE = "39";
	public static final String COD_MREJ_EFF_VAL_PRESCRITE = "31";
	public static final String COD_MREJ_EFF_VAL_DEJ_REJ = "44";
	public static final String COD_MREJ_EFF_VAL_DEJ_REG = "43";
	public static final String COD_MREJ_EFF_OPER_NON_AUTO = "47";

	// COD Effet Aval / Bap
	public static final Long COD_EFF_AVAL = Long.valueOf(2);
	public static final Long COD_EFF_AVAL_END = Long.valueOf(3);
	public static final Long COD_EFF_BAP = Long.valueOf(3);

	// ###### Fenetre de lencement ########//
	public static final String STATUT_EN_COURS = "En cours";
	public static final String STATUT_EN_COURS_LECT = "En cours de lecture";
	public static final String STATUT_EN_COURS_POS = "En cours de position";
	public static final String STATUT_EN_COURS_INSERT = "En cours d'insertion";
	public static final String STATUT_EN_COURS_ENVOI = "En cours d'envoi";
	public static final String STATUT_EN_ERRUR = "Erreur";
	public static final String STATUT_EN_TERMINE = "Terminé";

	// GOD
	public static final Long COD_OPER_BLOC_GOD = 2063L;
	public static final Long COD_OPER_DBLOC_GOD = 2064L;

	public static final Long COD_TACH_PEC_BLOC_DEB_GOD = 1L;
	public static final Long COD_TACH_ANNUL_BLOC_DEB_GOD = 2L;
	public static final Long COD_TACH_MODIF_BLOC_DEB_GOD = 3L;
	public static final Long COD_TACH_VALID_BLOC_DEB_GOD = 4L;
	public static final String COD_ASSUR_VOYAGE_RESILIE = "R";

	//***** Souscription Pack ***************//
	public static final Long COD_OPER_FRAIS_ABONNEMENT_PACK = 3284L;
	
	//***** SAGA DEPOT AFFECTEE ***************//
	public static final Long COD_OPER_BLOCAGE_MONTANT_AFFECTE = 3731L;
	// ############################################################################//

	public static final Class getclass(String classname) throws Exception {

		Class retour = null;
		try {
			if (classname.equals("Client")) {
				retour = Client.class;
			} else if (classname.equals("Produit")) {
				retour = Produit.class;
			} else if (classname.equals("Nationalite") || classname.equals("PaysNaisClt")
					|| classname.equals("PaysCpt")) {
				retour = Pays.class;
			} else if (classname.equals("Devises")) {
				retour = Devise.class;
			} else if (classname.equals("CodePostalCpt")) {
				retour = CodePostal.class;
			} else if (classname.equals("Gouvernorats")) {
				retour = Gouvernorat.class;
			} else if (classname.equals("Operation")) {
				retour = Operation.class;
			} else if (classname.equals("NiveauInstruction")) {
				retour = NiveauInstruction.class;
			} else if (classname.equals("RegimeMatrimonial")) {
				retour = RegimeMatrimonial.class;
			} else if (classname.equals("CategorieSocioProf")) {
				retour = CatSocProf.class;
			} else if (classname.equals("Tribunal")) {
				retour = Tribunal.class;
			} else if (classname.equals("TypePiece")) {
				retour = TypePiece.class;

			} else if (classname.equals("MotifRejet")) {
				retour = MotifRejet.class;

			} else if (classname.equals("Groupe")) {
				retour = Groupe.class;
			} else if (classname.equals("Structure")) {
				retour = Structure.class;
			} else if (classname.equals("Employeur")) {
				retour = Employeur.class;
			} else if (classname.equals("Activite")) {
				retour = Activite.class;
			} else if (classname.equals("Profession")) {
				retour = Profession.class;
			}

		} catch (Exception e) {
			throw new Exception("erreurclasse");
		}
		return retour;

	}

	public static final boolean isNumber(String number) {
		try {
			String ValidChars = "0123456789";
			boolean IsNumber = true;
			char val;
			int i = 0;

			for (i = 0; i < number.length() && IsNumber == true; i++) {
				val = number.charAt(i);
				if (ValidChars.indexOf(val) == -1) {
					IsNumber = false;
				}
			}
			return IsNumber;
		} catch (Exception nmbFormE) {
			System.out.println(nmbFormE.toString());
			nmbFormE.printStackTrace();
			return false;
		}
	}

	public static final boolean verifRCS(String numRCS, String codTypPers) {
		Context context = ContextHandler.getContext();
		ISearchEngine searchEngine = (SearchEngine) context.getBean("searchEngine");

		String codRCS = numRCS.substring(0, 1);
		String codTribRCS = "";
		int positionAnnee = numRCS.length() - 4;
		String anneeRCS = numRCS.substring(positionAnnee);
		int positionrestRcs = 0;
		String restRcs = "";
		int valrestRcs = 0;
		boolean trouve = false;
		int anneeActuel = Long.valueOf(DateHandler.dateToStr(new Date()).substring(6)).intValue();

		if (numRCS.length() <= 7 || numRCS.length() > 13) {
			return false;

		} else {
			// verif codRCS
			if (codTypPers.equals(Constants.PERSPHYSIQUE)) {
				if (!codRCS.equals("A") && !codRCS.equals("D")) {
					return false;
				}
			} else {
				if (!codRCS.equals("B") && !codRCS.equals("C")) {
					return false;
				}
			}
			// verif le reste de RCS est nombre
			if (!Constants.isNumber(numRCS.substring(1))) {
				return false;
			}
			// verif anneeRCS
			if (!Constants.isNumber(anneeRCS)) {
				return false;
			} else {
				int valAnneeRCS = Integer.parseInt(anneeRCS);
				if (valAnneeRCS == 1995 || valAnneeRCS == 1996 || valAnneeRCS == 2003) {
					return true;
				}
				if (valAnneeRCS < 1995 || valAnneeRCS > anneeActuel) {
					return false;
				} else {
					if (valAnneeRCS > 1996 && valAnneeRCS < 2003) {
						positionrestRcs = 2;
						codTribRCS = numRCS.substring(1, positionrestRcs);
						if (!codTribRCS.equals("1")) {
							return false;
						}
					} else if (valAnneeRCS >= 2004) {
						positionrestRcs = 3;
						codTribRCS = numRCS.substring(1, positionrestRcs);
					} else {
						return true;
					}

					// verif restRCS
					restRcs = numRCS.substring(positionrestRcs, positionAnnee);
					valrestRcs = Integer.parseInt(restRcs);
					if (valrestRcs > 99999 || String.valueOf(valrestRcs).length() != restRcs.length()) {
						return false;
					}

					// verif codTribunal
					List listeTribunal = searchEngine.findAll(Tribunal.class);
					Iterator iterator = listeTribunal.iterator();
					for (int i = 0; iterator.hasNext() && !trouve; i++) {
						Tribunal tribunal = (Tribunal) iterator.next();
						int val = tribunal.getCodTribTrib().intValue();
						if (val == Integer.parseInt(codTribRCS)) {
							trouve = true;
						}
					}
					if (!trouve) {
						return false;
					}
				}
			}

			return true;
		}
	}

	public static final boolean verifMatriculeFiscal(String matriculeFiscal) {
		String numeroMatricule = new String("");
		String cleMatricule = new String("");
		String codeTva = new String("");
		String codeCategorie = new String("");
		String numeroEtablissement = new String("");
		String test = new String("true");
		if (matriculeFiscal.length() != 13) {
			test = "false";
		} else {
			numeroMatricule = matriculeFiscal.substring(0, 7);
			cleMatricule = matriculeFiscal.substring(7, 8);
			codeTva = matriculeFiscal.substring(8, 9);
			codeCategorie = matriculeFiscal.substring(9, 10);
			numeroEtablissement = matriculeFiscal.substring(10, 13);

			if (!isNumber(numeroMatricule)) {
				test = "false";
			} else if (!cleMatricule.equals(String.valueOf(calculCleMatriculeFiscal(numeroMatricule)))) {
				test = "false";
			} else if ((!codeTva.equals("A")) && (!codeTva.equals("N")) && (!codeTva.equals("B"))
					&& (!codeTva.equals("P"))) {
				test = "false";
			} else if ((!codeCategorie.equals("P")) && (!codeCategorie.equals("C")) && (!codeCategorie.equals("M"))
					&& (!codeCategorie.equals("E"))) {
				test = "false";
			} else if (!isNumber(numeroEtablissement)) {
				test = "false";
			} else if (codeCategorie.equals("E")) {
				Long numero = Long.valueOf(numeroEtablissement);
				if (numero.equals(0)) {
					test = "false";
				}
			} else if (!codeCategorie.equals("E")) {
				Long numero = Long.valueOf(numeroEtablissement);
				if (!numero.equals(Long.valueOf("0"))) {
					test = "false";
				}
			}
		}

		if (test.equals("true")) {
			return true;
		} else {
			return false;
		}

	}

	public static double modulo(double x, double m) {
		double res;
		res = Math.floor(x / m);
		res = x - res * m;
		return res;
	}

	public static final char calculCleMatriculeFiscal(String numero) {
		int somme = 0;
		char[] lettres =
				{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
						'W', 'X', 'Y', 'Z' };

		for (int i = 0; i < numero.length(); i++) {
			String c = numero.substring(numero.length() - 1 - i, numero.length() - i);
			int chiffre = Integer.parseInt(c);
			somme = somme + (chiffre * (i + 1));
		}
		double numeroCle = modulo(Double.parseDouble(String.valueOf(somme)), Double.parseDouble("23")) + 1;
		int num = new Double(numeroCle).intValue();
		char cle = lettres[num - 1];
		return cle;
	}

	/**
	 * Cette fonction determine le clé d'un compte : A,B,...
	 * 
	 * @return le clet
	 * @author : Ramzi
	 */
	public static final String determinerCle(String structure, String produit, String numeroCompte) {
		String[] montab =
				{ "A", "B", "C", "D", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W",
						"X", "Y", "Z" };
		String cle13 = structure + produit + numeroCompte;
		System.out.println(cle13);
		long cle = Long.valueOf(cle13).longValue() % 23;
		String lettre = montab[(int) cle];
		return lettre;
	}

	// /depanage Retrait
	public static final Long COD_OPER_RETRAIT_EFFET = Long.valueOf(2107);
	public static final Long COD_TACHE_RETRAIT_EFFET = Long.valueOf(1);
	public static final Long COD_COMMISSION_RETRAIT_DEPL_EMIS = Long.valueOf(39);
	public static final Long COD_COMMISSION_RETRAIT_EFFET = Long.valueOf(324);
	public static final Long COD_COMMISSION_RETRAIT_DEPL_CHEQUE_CERTIFIE_EMIS = Long.valueOf(372);

	public static final Long COD_OPER_RETRAIT_DEPL_RECU = Long.valueOf(1093);
	public static final Long COD_OPER_RETRAIT_DEVISES = Long.valueOf(1090);
	public static final Long COD_OPER_RETRAIT_DEVISES_DIFF_DEVISE_COMPTE = Long.valueOf(1943);

	public static final Long COD_OPER_RETRAIT_DINARS_COMPTE_DEVISES = Long.valueOf(1123);
	public static final Long COD_TACHE_PEC_VERS = Long.valueOf(1);

	public static final Long COD_OPER_MAINLEVEE_SAISIE = Long.valueOf(2222);
	public static final Long COD_OPER_PEC_MAINLEVEE_SAISIE = Long.valueOf(1989);
	public static final Long COD_OPER_VAL_MAINLEVEE_SAISIE = Long.valueOf(1989);
	public static final Long COD_OPER_PEC_REAF_BLOC = Long.valueOf(1986);
	public static final Long COD_OPER_VALIDATION_CREATION_SAISIE_ARRET = Long.valueOf(1111);
	public static final Long COD_OPER_PEC_REALISATION_SAISIE_ARRET = Long.valueOf(4444);

	public static final Long COD_OPER_BLOCAGE_SAISIE_ARRET = Long.valueOf(1984);
	public static final Long COD_TACH_VALIDATION_BLOCAGE_SAISIE_ARRET = Long.valueOf(1);
	public static final Long COD_OPER_PERCEPTION_COMMISSION_SAISIE_ARRET = Long.valueOf(2016);// Perception commission
																							  // sur
	public static final Long COD_COMMISSION_PERCEPTION_COMMISSION_SAISIE_ARRET = Long.valueOf(55);
	public static final Long COD_TACH_PERCEPTION_COMMISSION_SAISIE_ARRET = Long.valueOf(1); // arrêt / opposition

	// administrative
	public static final Long COD_OPER_REALISATION_SAISIE_ARRET = Long.valueOf(1990);
	public static final Long COD_COD_TACH_PEC_REALISATION_SAISIE_ARRET = Long.valueOf(1);
	public static final Long COD_OPER_EXCECUTION_RECUE_SAISIE_ARRET = Long.valueOf(1991);
	public static final Long COD_TACH_EXCECUTION_RECUE_SAISIE_ARRET = Long.valueOf(1);
	public static final Long COD_OPER_CREATION_SAISIE_ARRET = Long.valueOf(9999);
	public static final Long COD_OPER_AFFECTATION_CONTRAT_SAISIE_ARRET = Long.valueOf(6666);
	public static final Long COD_OPER_MODIFICATION_SAISIE_ARRET = Long.valueOf(7777);
	public static final Long COD_OPER_RECHERCHE_CLIENT_SAISIE_ARRET = Long.valueOf(8888);
	public static final Long COD_OPER_CREATION_MODIFICATION_BLOCAGE_BAISSE = Long.valueOf(1987);// 5555
	public static final Long COD_OPER_CREATION_MODIFICATION_BLOCAGE_HAUSSE = Long.valueOf(2017);
	public static final Long COD_OPER_CREATION_SAISIE_DEVISES = Long.valueOf(1964);
	public static final Long COD_OPER_MAINLEVEE_SAISIE_DEVISES = Long.valueOf(2101);
	public static final Long COD_COD_TACH_VALIDATION_REALISATION_SAISIE_ARRET = Long.valueOf(2);
	// déblocage contrat
	public static final Long COD_TACH_DEBLOC_CPT_DEC_SUCCESSION = Long.valueOf(1);
	
	public static final Long COD_OPER_SOUSC_ASSUR_VOYAGE = Long.valueOf(2317);
	public static final Long COD_OPER_RENOUVELLEMENT_ASSUR_VOYAGE = Long.valueOf(2323);
	public static final Long COD_OPER_REGLEMENT_ASSUREUR = Long.valueOf(2319);
}
