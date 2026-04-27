package com.bna.smile.batch.test;

import java.io.Serializable;

public class CommissinFraisPackBatchLauncher implements Serializable {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		try {
			MoulinetteCommissionTenueComptePackControlMTest moulinette = new MoulinetteCommissionTenueComptePackControlMTest();
			Thread threadMoulinette = new Thread(moulinette);
			threadMoulinette.start();
			threadMoulinette.join();
			System.exit(0);
			System.out.println("JOBOK");
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("JOBNOTOK");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("JOBNOTOK");
		}
	}

}
