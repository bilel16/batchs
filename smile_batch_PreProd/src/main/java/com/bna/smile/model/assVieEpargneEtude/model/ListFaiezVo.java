package com.bna.smile.model.assVieEpargneEtude.model;


import java.util.ArrayList;
import java.util.List;
import com.oxia.fwk.core.ValueObject;

public class ListFaiezVo extends ValueObject {

	private static final long serialVersionUID = 1L;
	private List<ContratEpargneEtudeVo> list = new ArrayList<ContratEpargneEtudeVo>();
	public ListFaiezVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ListFaiezVo(List<ContratEpargneEtudeVo> list) {
		super();
		this.list = list;
	}
	public List<ContratEpargneEtudeVo> getList() {
		return list;
	}
	public void setList(List<ContratEpargneEtudeVo> list) {
		this.list = list;
	}
	
}
