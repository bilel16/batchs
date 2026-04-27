package com.bna.smile.model.domaineguichet.model;

import java.util.Date;




public class MvtDevise {
Long ID_TMP_VIR;
Date DATE_VIR ;
String DATA_VIR ; 
String LIB_OPER ;
String REF_INTER_SIEGE;
Long MNT_DEV_DEV;
String COD_SENS_OPER ;
public MvtDevise()
{
	
}
		
public MvtDevise(Long iD_TMP_VIR, Date dATE_VIR, String dATA_VIR, String lIB_OPER, String rEF_INTER_SIEGE,
		Long mNT_DEV_DEV, String cOD_SENS_OPER) {
	super();
	ID_TMP_VIR = iD_TMP_VIR;
	DATE_VIR = dATE_VIR;
	DATA_VIR = dATA_VIR;
	LIB_OPER = lIB_OPER;
	REF_INTER_SIEGE = rEF_INTER_SIEGE;
	MNT_DEV_DEV = mNT_DEV_DEV;
	COD_SENS_OPER = cOD_SENS_OPER;
}

@Override
public boolean equals(Object object)
{
    boolean sameSame = false;

    if (object != null && object instanceof MvtDevise)
    {
        MvtDevise mvtDevise=(MvtDevise)object;
        if(mvtDevise.getREF_INTER_SIEGE().equals(this.getREF_INTER_SIEGE())&& mvtDevise.getCOD_SENS_OPER().equals(this.getCOD_SENS_OPER()) && mvtDevise.getMNT_DEV_DEV().equals(this.getMNT_DEV_DEV()))
        	sameSame=true;
        
    }

    return sameSame;
}

public Long getID_TMP_VIR() {
	return ID_TMP_VIR;
}

public void setID_TMP_VIR(Long iD_TMP_VIR) {
	ID_TMP_VIR = iD_TMP_VIR;
}

public Date getDATE_VIR() {
	return DATE_VIR;
}

public void setDATE_VIR(Date dATE_VIR) {
	DATE_VIR = dATE_VIR;
}

public String getDATA_VIR() {
	return DATA_VIR;
}

public void setDATA_VIR(String dATA_VIR) {
	DATA_VIR = dATA_VIR;
}

public String getLIB_OPER() {
	return LIB_OPER;
}

public void setLIB_OPER(String lIB_OPER) {
	LIB_OPER = lIB_OPER;
}

public String getREF_INTER_SIEGE() {
	return REF_INTER_SIEGE;
}

public void setREF_INTER_SIEGE(String rEF_INTER_SIEGE) {
	REF_INTER_SIEGE = rEF_INTER_SIEGE;
}

public Long getMNT_DEV_DEV() {
	return MNT_DEV_DEV;
}

public void setMNT_DEV_DEV(Long mNT_DEV_DEV) {
	MNT_DEV_DEV = mNT_DEV_DEV;
}

public String getCOD_SENS_OPER() {
	return COD_SENS_OPER;
}

public void setCOD_SENS_OPER(String cOD_SENS_OPER) {
	COD_SENS_OPER = cOD_SENS_OPER;
}


}
