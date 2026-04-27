package com.bna.smile.model.histSoldePascale.model;

import java.util.ArrayList;
import java.util.List;

import com.bna.commun.vo.PrimitiveVO;

public class HistSoldePascalVo {

	private List<String> lines = new ArrayList<String>();
	private List<PrimitiveVO> compteDevises = new ArrayList<PrimitiveVO>();
	private List<PrimitiveVO> compteDinars = new ArrayList<PrimitiveVO>();

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

	public void setCompteDinars(List<PrimitiveVO> compteDinars) {
		this.compteDinars = compteDinars;
	}

	public List<PrimitiveVO> getCompteDinars() {
		return compteDinars;
	}

	public void setCompteDevises(List<PrimitiveVO> compteDevises) {
		this.compteDevises = compteDevises;
	}

	public List<PrimitiveVO> getCompteDevises() {
		return compteDevises;
	}
}
