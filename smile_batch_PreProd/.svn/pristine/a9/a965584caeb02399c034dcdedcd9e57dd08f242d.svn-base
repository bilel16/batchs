package com.bna.commun.validator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import com.bna.smile.model.constant.Constants;

public abstract class AbstractValidator {

	public static final String REGEX_MONTANT_FORMATTE = "^[0-9]{1,16}(\\.[0-9]{1,3})?$";
	public static final String REGEX_MONTANT = "^[0-9]{1,16}$";

	public static String formatCode(String code, int length) {
		int tmp = length - code.length();
		for (int i = 0; i < tmp; i++) {
			code = "0" + code;
		}
		return code;
	}

	public static boolean checkNumPieceAndGetMessage(String numPiece, String typePiece, String componentName) {
		if (numPiece != null && !"".equals(numPiece.trim())) {
			if (Constants.COD_CIN.toString().equals(typePiece)) {
				if (!numPiece.matches("[0-9]{8}")) {

					return false;
				} else {
					if (numPiece.matches("1{8}") || numPiece.matches("2{8}") || numPiece.matches("3{8}")
							|| numPiece.matches("4{8}") || numPiece.matches("5{8}") || numPiece.matches("6{8}")
							|| numPiece.matches("7{8}") || numPiece.matches("8{8}") || numPiece.matches("9{8}")
							|| numPiece.matches("12345678") || numPiece.matches("23456789")
							|| numPiece.matches("01010101") || numPiece.matches("02020202")
							|| numPiece.matches("03030303")) {

						return false;
					}
				}
			} else {
				if (Constants.COD_CSEJ.toString().equals(typePiece)
						|| Constants.COD_PASS.toString().equals(typePiece)) {
					if (numPiece.length() < 10) {
						numPiece = AbstractValidator.formatCode(numPiece, 10);
					}
					numPiece = numPiece.toUpperCase();
				}
			}
		}
		return true;
	}

	public static boolean validerMontant(String montant) {
		if (montant != null && !"".equals(montant.trim()) && montant.matches(REGEX_MONTANT_FORMATTE)) {
			return true;
		}
		return false;
	}

	public static String formatMontant(String montant) {
		if (validerMontant(montant)) {
			Double montantDouble = Double.valueOf(montant);
			if (montant.matches(REGEX_MONTANT)) {
				montantDouble = montantDouble / 1000;
			}
			DecimalFormat df = new DecimalFormat("# #0.000");
			DecimalFormatSymbols dcmlFS = new DecimalFormatSymbols();
			dcmlFS.setDecimalSeparator('.');
			df.setDecimalFormatSymbols(dcmlFS);
			return df.format(montantDouble);
		}
		return null;
	}

	public static String formatMontantWithEsp(String montant) {
		if (validerMontant(montant)) {
			Double montantDouble = Double.valueOf(montant);
			if (montant.matches(REGEX_MONTANT)) {
				montantDouble = montantDouble / 1000;
			}
			DecimalFormat df = new DecimalFormat("# #0.000");
			DecimalFormatSymbols dcmlFS = new DecimalFormatSymbols();
			dcmlFS.setDecimalSeparator('.');
			df.setDecimalFormatSymbols(dcmlFS);
			String montantSansEsp = df.format(montantDouble);
			String aprVirgule = "";
			String avantVirgule = "";
			if (montantSansEsp.contains(".")) {
				aprVirgule = montantSansEsp.substring(montantSansEsp.lastIndexOf("."));
				avantVirgule = montantSansEsp.substring(0, montantSansEsp.lastIndexOf("."));
			} else {
				avantVirgule = montantSansEsp;
			}

			String resultat = "";
			String decimal = "";
			for (int i = avantVirgule.length(); i > 0;) {
				if (avantVirgule.length() > 3) {
					decimal = avantVirgule.substring(i - 3, i);
					avantVirgule = avantVirgule.substring(0, avantVirgule.length() - 3);
					resultat = decimal + " " + resultat;
					i = i - 3;
				} else {
					resultat = avantVirgule + " " + resultat;
					break;
				}
			}
			resultat = resultat.substring(0, resultat.lastIndexOf(" "));
			return resultat + aprVirgule;
		}
		return null;
	}

	public static String validAndFormatMontant(String montant) {
		return formatMontant(montant);
	}

	public static String validAndFormatMontantWithEsp(String montant) {
		return formatMontantWithEsp(montant);
	}

	public static String validAndFormatMontantAndGetMessage(String montant, String componentName) {
		String res = validAndFormatMontant(montant);
		if (res == null) {

		}
		return res;
	}

	public static String validAndFormatMontantWithEspAndGetMessage(String montant, String componentName) {
		String res = validAndFormatMontantWithEsp(montant);
		if (res == null) {

		}
		return res;
	}

}
