package com.bna.smile.model.clotureDomaine.model;

import com.oxia.fwk.core.ValueObject;

public class StatSouscription extends ValueObject{

    private Long nbrTotSouscr = Long.valueOf(0);
    private Long nbrSouscrVal = Long.valueOf(0);
    private Long nbrSouscrAtt = Long.valueOf(0);
    private Long nbrSouscrRes = Long.valueOf(0);





    public StatSouscription() {
    }

    public void setNbrTotSouscr(Long nbrTotSouscr) {
        this.nbrTotSouscr = nbrTotSouscr;
    }

    public Long getNbrTotSouscr() {
        return nbrTotSouscr;
    }

    public void setNbrSouscrVal(Long nbrSouscrVal) {
        this.nbrSouscrVal = nbrSouscrVal;
    }

    public Long getNbrSouscrVal() {
        return nbrSouscrVal;
    }

    public void setNbrSouscrAtt(Long nbrSouscrAtt) {
        this.nbrSouscrAtt = nbrSouscrAtt;
    }

    public Long getNbrSouscrAtt() {
        return nbrSouscrAtt;
    }


    public void setNbrSouscrRes(Long nbrSouscrRes) {
        this.nbrSouscrRes = nbrSouscrRes;
    }

    public Long getNbrSouscrRes() {
        return nbrSouscrRes;
    }
}
