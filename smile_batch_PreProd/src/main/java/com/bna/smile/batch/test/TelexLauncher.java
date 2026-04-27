package com.bna.smile.batch.test;

import java.io.Serializable;

import com.bna.smile.batch.telex.Telex;;



public class TelexLauncher  implements Serializable{

	
	public static void main(String[] args) throws InterruptedException {
	    Telex telex = new Telex();
		telex.setArgs(args);
		Thread threadTelex = new Thread(telex);
		threadTelex.start();
		threadTelex.join();
		System.exit(0);
	}
}
