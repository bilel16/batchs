package com.bna.smile.model.prelevement.model;

import java.util.Date;

import com.oxia.fwk.core.ValueObject;

public class ADDetailPrelevementVo extends ValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long numLot; // NUMBER(4)
	private Date datOpe; // DATE
	private Long codSen; // NUMBER(1)
	private String codAge; // VARCHAR2(10)
	private String codBan; // VARCHAR2(10)
	private Long codVal; // NUMBER(2)
	private Long codNatEta; // NUMBER(1)
	private String codDev; // VARCHAR2(3)
	private Long codEnr; // NUMBER(2)
	private Long mntPrl; // NUMBER(18,3)
	private Long numPrl; // NUMBER(7)
	private String ribTir; // NUMBER(20)
	private String codBanDes; // VARCHAR2(10)
	private String codAgeDes; // VARCHAR2(10)
	private String ribBen; // NUMBER(20)
	private Long codEmePrl; // NUMBER(6)
	private String numRefDom; // VARCHAR2(20)
	private String libPrl; // VARCHAR2(50)
	private Date datCmpIni; // DATE
	private String motRej; // VARCHAR2(8)
	private Date datEch; // DATE
	private String refFic; // VARCHAR2(100)
	private Long numEvtEnv; // NUMBER
	private Long numEvtRcp; // NUMBER
	private String rjtReg; // VARCHAR2(1)

	// *********Setter and Getter***************//
	public Long getNumLot() {
		return numLot;
	}

	public void setNumLot(Long numLot) {
		this.numLot = numLot;
	}

	public Date getDatOpe() {
		return datOpe;
	}

	public void setDatOpe(Date datOpe) {
		this.datOpe = datOpe;
	}

	public String getCodAge() {
		return codAge;
	}

	public void setCodAge(String codAge) {
		this.codAge = codAge;
	}

	public String getCodBan() {
		return codBan;
	}

	public void setCodBan(String codBan) {
		this.codBan = codBan;
	}

	public String getCodDev() {
		return codDev;
	}

	public void setCodDev(String codDev) {
		this.codDev = codDev;
	}

	
	public Long getMntPrl() {
		return mntPrl;
	}

	public void setMntPrl(Long mntPrl) {
		this.mntPrl = mntPrl;
	}


	public Long getCodEnr() {
		return codEnr;
	}

	public void setCodEnr(Long codEnr) {
		this.codEnr = codEnr;
	}

	public Long getNumPrl() {
		return numPrl;
	}

	public void setNumPrl(Long numPrl) {
		this.numPrl = numPrl;
	}

	public String getRibTir() {
		return ribTir;
	}

	public void setRibTir(String ribTir) {
		this.ribTir = ribTir;
	}

	public String getCodBanDes() {
		return codBanDes;
	}

	public void setCodBanDes(String codBanDes) {
		this.codBanDes = codBanDes;
	}

	public String getCodAgeDes() {
		return codAgeDes;
	}

	public void setCodAgeDes(String codAgeDes) {
		this.codAgeDes = codAgeDes;
	}

	public String getRibBen() {
		return ribBen;
	}

	public void setRibBen(String ribBen) {
		this.ribBen = ribBen;
	}

	public String getNumRefDom() {
		return numRefDom;
	}

	public void setNumRefDom(String numRefDom) {
		this.numRefDom = numRefDom;
	}

	public String getLibPrl() {
		return libPrl;
	}

	public void setLibPrl(String libPrl) {
		this.libPrl = libPrl;
	}

	public Date getDatCmpIni() {
		return datCmpIni;
	}

	public void setDatCmpIni(Date datCmpIni) {
		this.datCmpIni = datCmpIni;
	}

	public String getMotRej() {
		return motRej;
	}

	public void setMotRej(String motRej) {
		this.motRej = motRej;
	}

	public Date getDatEch() {
		return datEch;
	}

	public void setDatEch(Date datEch) {
		this.datEch = datEch;
	}

	public String getRefFic() {
		return refFic;
	}

	public void setRefFic(String refFic) {
		this.refFic = refFic;
	}

	public Long getNumEvtEnv() {
		return numEvtEnv;
	}

	public void setNumEvtEnv(Long numEvtEnv) {
		this.numEvtEnv = numEvtEnv;
	}

	public Long getNumEvtRcp() {
		return numEvtRcp;
	}

	public void setNumEvtRcp(Long numEvtRcp) {
		this.numEvtRcp = numEvtRcp;
	}

	public String getRjtReg() {
		return rjtReg;
	}

	public void setRjtReg(String rjtReg) {
		this.rjtReg = rjtReg;
	}

	public Long getCodSen() {
		return codSen;
	}

	public void setCodSen(Long codSen) {
		this.codSen = codSen;
	}

	public Long getCodVal() {
		return codVal;
	}

	public void setCodVal(Long codVal) {
		this.codVal = codVal;
	}

	public Long getCodNatEta() {
		return codNatEta;
	}

	public void setCodNatEta(Long codNatEta) {
		this.codNatEta = codNatEta;
	}

	public Long getCodEmePrl() {
		return codEmePrl;
	}

	public void setCodEmePrl(Long codEmePrl) {
		this.codEmePrl = codEmePrl;
	}

}
