package com.bna.smile.batch.test;

public class EnvoiFichierCapiLauncher {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws InterruptedException {
		MoulinetteEnvoiFichierCapi moulinette = new MoulinetteEnvoiFichierCapi();
		moulinette.setArgs(args);
		Thread threadMoulinette = new Thread(moulinette);
		threadMoulinette.start();
		threadMoulinette.join();
		System.exit(0);
	}

}
