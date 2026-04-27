package com.bna.smile.model.domainecompensation.gestionrejet.model;

import java.util.Date;

import com.oxia.fwk.core.ValueObject;

public class ChequeACHVo extends ValueObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long mntTot; // NUMBER(15,3)
	private Long nbrTot; // NUMBER(10)
	private Long mntRejAdt; // NUMBER(15,3)
	private String refOper; // VARCHAR2(9)
	private String codSta; // VARCHAR2(5)
	private Date datEnvPa; // DATE
	private String refLot; // VARCHAR2(9)
	private Long arcFlg; // NUMBER(1)
	private String envAge; // VARCHAR2(1)

	private Long numChq; // NUMBER(7)
	private Long ribTir; // NUMBER(20)
	private Long numLot; // NUMBER(4)
	private Date datOpe; // DATE
	private Long mntChq; // NUMBER(15,3)
	private Long mntRec; // NUMBER(15,3)
	private Long mntReg; // MNT_REG
	private Long mntRegInt; // MNT_REG_INT
	private Long ribBen; // NUMBER(20)
	private String nomPrn; // VARCHAR2(30)
	private Date datEmi; // DATE
	private Date datCnp;
	private Long codSen; // NUMBER(1)
	private Long codNatEta; // NUMBER(1)
	private Long codEnr; // NUMBER(2)
	private Long numCnp; // NUMBER(2)
	private String codAge; // VARCHAR2(10)
	private String codBan; // VARCHAR2(10)
	private String codAgeDes; // VARCHAR2(10)
	private String codBanDes; // VARCHAR2(10)
	private String codLieEmiChq; // VARCHAR2(1)
	private Long codSit; // NUMBER(1)
	private Long codNatCpt; // NUMBER(1)
	private String codDev; // VARCHAR2(3)
	private String codDevPos; // VARCHAR2(3)
	private Long codVal; // NUMBER(2)
	private String refFic; // VARCHAR2(50)
	private String codMotRej; // VARCHAR2(8)
	private String ribTirRec; // VARCHAR2(20)
	private Long numEvtEnv; // NUMBER
	private Long numEvtRcp; // NUMBER
	private String rjtReg; // VARCHAR2(1)
	private byte[] imgR; // BLOB
	private byte[] imgV; // BLOB

	public Long getMntTot() {
		return mntTot;
	}

	public void setMntTot(Long mntTot) {
		this.mntTot = mntTot;
	}

	public Long getNbrTot() {
		return nbrTot;
	}

	public void setNbrTot(Long nbrTot) {
		this.nbrTot = nbrTot;
	}

	public Long getMntRejAdt() {
		return mntRejAdt;
	}

	public void setMntRejAdt(Long mntRejAdt) {
		this.mntRejAdt = mntRejAdt;
	}

	public String getRefOper() {
		return refOper;
	}

	public void setRefOper(String refOper) {
		this.refOper = refOper;
	}

	public String getCodSta() {
		return codSta;
	}

	public void setCodSta(String codSta) {
		this.codSta = codSta;
	}

	public Date getDatEnvPa() {
		return datEnvPa;
	}

	public void setDatEnvPa(Date datEnvPa) {
		this.datEnvPa = datEnvPa;
	}

	public String getRefLot() {
		return refLot;
	}

	public void setRefLot(String refLot) {
		this.refLot = refLot;
	}

	public Long getArcFlg() {
		return arcFlg;
	}

	public void setArcFlg(Long arcFlg) {
		this.arcFlg = arcFlg;
	}

	public String getEnvAge() {
		return envAge;
	}

	public void setEnvAge(String envAge) {
		this.envAge = envAge;
	}

	public Long getNumChq() {
		return numChq;
	}

	public void setNumChq(Long numChq) {
		this.numChq = numChq;
	}

	public Long getRibTir() {
		return ribTir;
	}

	public void setRibTir(Long ribTir) {
		this.ribTir = ribTir;
	}

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

	public Long getMntChq() {
		return mntChq;
	}

	public void setMntChq(Long mntChq) {
		this.mntChq = mntChq;
	}

	public Long getRibBen() {
		return ribBen;
	}

	public void setRibBen(Long ribBen) {
		this.ribBen = ribBen;
	}

	public String getNomPrn() {
		return nomPrn;
	}

	public void setNomPrn(String nomPrn) {
		this.nomPrn = nomPrn;
	}

	public Date getDatEmi() {
		return datEmi;
	}

	public void setDatEmi(Date datEmi) {
		this.datEmi = datEmi;
	}

	public Long getCodSen() {
		return codSen;
	}

	public void setCodSen(Long codSen) {
		this.codSen = codSen;
	}

	public Long getCodNatEta() {
		return codNatEta;
	}

	public void setCodNatEta(Long codNatEta) {
		this.codNatEta = codNatEta;
	}

	public Long getCodEnr() {
		return codEnr;
	}

	public void setCodEnr(Long codEnr) {
		this.codEnr = codEnr;
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

	public String getCodAgeDes() {
		return codAgeDes;
	}

	public void setCodAgeDes(String codAgeDes) {
		this.codAgeDes = codAgeDes;
	}

	public String getCodBanDes() {
		return codBanDes;
	}

	public void setCodBanDes(String codBanDes) {
		this.codBanDes = codBanDes;
	}

	public String getCodLieEmiChq() {
		return codLieEmiChq;
	}

	public void setCodLieEmiChq(String codLieEmiChq) {
		this.codLieEmiChq = codLieEmiChq;
	}

	public Long getCodSit() {
		return codSit;
	}

	public void setCodSit(Long codSit) {
		this.codSit = codSit;
	}

	public Long getCodNatCpt() {
		return codNatCpt;
	}

	public void setCodNatCpt(Long codNatCpt) {
		this.codNatCpt = codNatCpt;
	}

	public String getCodDev() {
		return codDev;
	}

	public void setCodDev(String codDev) {
		this.codDev = codDev;
	}

	public Long getCodVal() {
		return codVal;
	}

	public void setCodVal(Long codVal) {
		this.codVal = codVal;
	}

	public String getRefFic() {
		return refFic;
	}

	public void setRefFic(String refFic) {
		this.refFic = refFic;
	}

	public String getCodMotRej() {
		return codMotRej;
	}

	public void setCodMotRej(String codMotRej) {
		this.codMotRej = codMotRej;
	}

	public String getRibTirRec() {
		return ribTirRec;
	}

	public void setRibTirRec(String ribTirRec) {
		this.ribTirRec = ribTirRec;
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

	public byte[] getImgR() {
		return imgR;
	}

	public void setImgR(byte[] imgR) {
		this.imgR = imgR;
	}

	public byte[] getImgV() {
		return imgV;
	}

	public void setImgV(byte[] imgV) {
		this.imgV = imgV;
	}

	public Long getMntRec() {
		return mntRec;
	}

	public void setMntRec(Long mntRec) {
		this.mntRec = mntRec;
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

	public String getCodDevPos() {
		return codDevPos;
	}

	public void setCodDevPos(String codDevPos) {
		this.codDevPos = codDevPos;
	}

	public Long getMntReg() {
		return mntReg;
	}

	public void setMntReg(Long mntReg) {
		this.mntReg = mntReg;
	}

	public Long getMntRegInt() {
		return mntRegInt;
	}

	public void setMntRegInt(Long mntRegInt) {
		this.mntRegInt = mntRegInt;
	}

}
