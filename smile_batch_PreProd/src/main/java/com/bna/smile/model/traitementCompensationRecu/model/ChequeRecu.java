package com.bna.smile.model.traitementCompensationRecu.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.Cheque30;
import com.bna.commun.model.Cheque31;
import com.bna.commun.model.Cheque32;
import com.bna.commun.model.Cheque33;
import com.oxia.fwk.core.ValueObject;

public class ChequeRecu extends ValueObject implements Serializable {
	
	private String codStrcStrc;
	private Date datJou;
	private Long numSeq;
	private Long num;
	private Long codValVal;
	private Date datOpe;
	private Long mntChq;
	private Long numChq;
	private String ribTir;
	private String ribBen;
	private String nomBen;
	private Date datEmi;
	private String lieEmi;
	private String natCpt;
	private Long motRej;
	private String codRej1;
	private String codRej2;
	private String codRej3;
	private String codRej4;
	private Date datCnp;
    private Long numCnp;
	private Blob imgVer;
	private Blob imgRec;


	public ChequeRecu() {
	}


	public String getCodStrcStrc() {
		return codStrcStrc;
	}


	public void setCodStrcStrc(String codStrcStrc) {
		this.codStrcStrc = codStrcStrc;
	}


	public Date getDatJou() {
		return datJou;
	}


	public void setDatJou(Date datJou) {
		this.datJou = datJou;
	}


	public Long getNumSeq() {
		return numSeq;
	}


	public void setNumSeq(Long numSeq) {
		this.numSeq = numSeq;
	}


	public Long getNum() {
		return num;
	}


	public void setNum(Long num) {
		this.num = num;
	}


	public Long getCodValVal() {
		return codValVal;
	}


	public void setCodValVal(Long codValVal) {
		this.codValVal = codValVal;
	}


	public Date getDatOpe() {
		return datOpe;
	}


	public void setDatOpe(Date datOpe) {
		this.datOpe = datOpe;
	}


	public Long getMntChq() {
		return mntChq;
	}


	public void setMntChq(Long mntChq) {
		this.mntChq = mntChq;
	}


	public Long getNumChq() {
		return numChq;
	}


	public void setNumChq(Long numChq) {
		this.numChq = numChq;
	}


	public String getRibTir() {
		return ribTir;
	}


	public void setRibTir(String ribTir) {
		this.ribTir = ribTir;
	}


	public String getRibBen() {
		return ribBen;
	}


	public void setRibBen(String ribBen) {
		this.ribBen = ribBen;
	}


	public String getNomBen() {
		return nomBen;
	}


	public void setNomBen(String nomBen) {
		this.nomBen = nomBen;
	}


	public Date getDatEmi() {
		return datEmi;
	}


	public void setDatEmi(Date datEmi) {
		this.datEmi = datEmi;
	}


	public String getLieEmi() {
		return lieEmi;
	}


	public void setLieEmi(String lieEmi) {
		this.lieEmi = lieEmi;
	}


	public String getNatCpt() {
		return natCpt;
	}


	public void setNatCpt(String natCpt) {
		this.natCpt = natCpt;
	}


	public Long getMotRej() {
		return motRej;
	}


	public void setMotRej(Long motRej) {
		this.motRej = motRej;
	}


	public String getCodRej1() {
		return codRej1;
	}


	public void setCodRej1(String codRej1) {
		this.codRej1 = codRej1;
	}


	public String getCodRej2() {
		return codRej2;
	}


	public void setCodRej2(String codRej2) {
		this.codRej2 = codRej2;
	}


	public String getCodRej3() {
		return codRej3;
	}


	public void setCodRej3(String codRej3) {
		this.codRej3 = codRej3;
	}


	public String getCodRej4() {
		return codRej4;
	}


	public void setCodRej4(String codRej4) {
		this.codRej4 = codRej4;
	}


	public Date getDatCnp() {
		return datCnp;
	}


	public void setDatCnp(Date datCnp) {
		this.datCnp = datCnp;
	}


	public Long getNumCnp() {
		return numCnp;
	}


	public void setNumCnp(Long numCnp) {
		this.numCnp = numCnp;
	}


	public Blob getImgVer() {
		return imgVer;
	}


	public void setImgVer(Blob imgVer) {
		this.imgVer = imgVer;
	}


	public Blob getImgRec() {
		return imgRec;
	}


	public void setImgRec(Blob imgRec) {
		this.imgRec = imgRec;
	}


	

}
