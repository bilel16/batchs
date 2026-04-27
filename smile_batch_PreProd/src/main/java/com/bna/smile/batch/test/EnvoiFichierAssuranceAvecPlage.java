//package com.bna.smile.batch.test;
//
//import com.bna.commun.util.DateHandler;
//
//public class EnvoiFichierAssuranceAvecPlage {
//
//	public static void main(String[] args) throws InterruptedException {
//		validateArgs(args);
//		MoulinetteEnvoiFichierAssurance task = new MoulinetteEnvoiFichierAssurance();
//		task.setDateDebutEnvoie(args[0]);
//		task.setNombreDeJourneeAEnvoi(Integer.valueOf(args[1]));
//		Thread threadMoulinette = new Thread(task);
//		threadMoulinette.start();
//		threadMoulinette.join();
//		System.exit(0);
//
//	}
//
//	private static void validateArgs(String[] args) throws InterruptedException {
//		if (args.length != 2) {
//			throw new InterruptedException("nombre d'argument doit etre 2 ");
//		} else {
//			if (DateHandler.strToDate(args[0]) == null) {
//				throw new InterruptedException(
//						"le premier agrument date est non valide , le format de la date doit etre dd/MM/yyyy ");
//			}
//			try {
//				if (Integer.valueOf(args[1]) < 0) {
//					throw new InterruptedException(
//							"le deuxieme argument nombre de journee a envoyer doit etre un entier positif");
//				}
//			} catch (NumberFormatException e) {
//				throw new InterruptedException("le deuxieme argument nombre de journee a envoyer doit etre un entier");
//			}
//
//		}
//
//	}
//
//}
