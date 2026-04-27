package com.bna.smile.model.assVieEpargneEtude.model;

import java.util.ArrayList;
import java.util.List;

public class AssVieEpargneVo {

	private List<String> lines = new ArrayList<String>();
	private Long somme = 0L;

	public void setSomme(Long somme) {
		this.somme = somme;
	}

	public Long getSomme() {
		return somme;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public List<String> getLines() {
		return lines;
	}
}
