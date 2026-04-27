package com.bna.smile.model.traitementCompensationRecu.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.Cheque30;
import com.bna.commun.model.Cheque31;
import com.bna.commun.model.Cheque32;
import com.bna.commun.model.Cheque33;
import com.oxia.fwk.core.ValueObject;

public class ListChequesRecusVo extends ValueObject implements Serializable {
    private Long typeCheque; // 30,31,32,33
    private boolean allCheques; // true,false
    private Date   dateComptable;
    private String etat; 
    private String structure; // code de la structure
    private List<Cheque30>    listCheques30 = new ArrayList<Cheque30>();
    private List<Cheque31>    listCheques31 = new ArrayList<Cheque31>();
    private List<Cheque32>    listCheques32 = new ArrayList<Cheque32>();
    private List<Cheque33>    listCheques33 = new ArrayList<Cheque33>();
    List<ChequeRecu> listChequesRecus = new ArrayList<ChequeRecu>();

    public ListChequesRecusVo() {
    }


	public Long getTypeCheque() {
		return typeCheque;
	}


	public void setTypeCheque(Long typeCheque) {
		this.typeCheque = typeCheque;
	}


	public boolean isAllCheques() {
		return allCheques;
	}


	public void setAllCheques(boolean allCheques) {
		this.allCheques = allCheques;
	}


	public Date getDateComptable() {
		return dateComptable;
	}

	public void setDateComptable(Date dateComptable) {
		this.dateComptable = dateComptable;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public List<Cheque30> getListCheques30() {
		return listCheques30;
	}

	public void setListCheques30(List<Cheque30> listCheques30) {
		this.listCheques30 = listCheques30;
	}

	public List<Cheque31> getListCheques31() {
		return listCheques31;
	}

	public void setListCheques31(List<Cheque31> listCheques31) {
		this.listCheques31 = listCheques31;
	}

	public List<Cheque32> getListCheques32() {
		return listCheques32;
	}

	public void setListCheques32(List<Cheque32> listCheques32) {
		this.listCheques32 = listCheques32;
	}

	public List<Cheque33> getListCheques33() {
		return listCheques33;
	}

	public void setListCheques33(List<Cheque33> listCheques33) {
		this.listCheques33 = listCheques33;
	}

	public List<ChequeRecu> getListChequesRecus() {
		return listChequesRecus;
	}

	public void setListChequesRecus(List<ChequeRecu> listChequesRecus) {
		this.listChequesRecus = listChequesRecus;
	}

	
}
