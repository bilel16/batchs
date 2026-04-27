package com.bna.smile.batch.test;

import java.io.Serializable;

public class MoulinetteLiquidationEcheanceLauncher implements Serializable {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		try {
			MoulinetteLiquidationAecheanceTest moulinette = new MoulinetteLiquidationAecheanceTest();
			Thread threadMoulinette = new Thread(moulinette);
			threadMoulinette.start();
			threadMoulinette.join();
			
			System.out.println("JOBOK");
			
			System.exit(0);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("JOBNOTOK");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("JOBNOTOK");
		}
	}
}
