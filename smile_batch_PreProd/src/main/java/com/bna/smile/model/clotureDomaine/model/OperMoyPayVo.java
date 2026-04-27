package com.bna.smile.model.clotureDomaine.model;

import java.util.Date;

import com.oxia.fwk.core.ValueObject;

public class OperMoyPayVo extends ValueObject{
    public OperMoyPayVo() {
    }
    private Long codStrcStrc;
    private Date dateOperOpm;
    private Date dateValOpm;
    private Long codOperOpm;
    private Long codprdprd;

    public void setCodStrcStrc(Long codStrcStrc) {
        this.codStrcStrc = codStrcStrc;
    }

    public Long getCodStrcStrc() {
        return codStrcStrc;
    }

    public void setDateOperOpm(Date dateOperOpm) {
        this.dateOperOpm = dateOperOpm;
    }

    public Date getDateOperOpm() {
        return dateOperOpm;
    }

    public void setCodOperOpm(Long codOperOpm) {
        this.codOperOpm = codOperOpm;
    }

    public Long getCodOperOpm() {
        return codOperOpm;
    }

    public void setCodprdprd(Long codprdprd) {
        this.codprdprd = codprdprd;
    }

    public Long getCodprdprd() {
        return codprdprd;
    }

    public void setDateValOpm(Date dateValOpm) {
        this.dateValOpm = dateValOpm;
    }

    public Date getDateValOpm() {
        return dateValOpm;
    }
}
