package com.bna.smile.model.domainecommun.traitement;

import com.bna.commun.model.ContratCpt;

import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.traitements.InsertOperationMoyPayTrt;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;

import com.bna.commun.vo.PrimitiveVO;

import com.bna.smile.model.domainecommun.service.CRUDservice;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

import java.math.BigInteger;

import java.text.SimpleDateFormat;

import java.util.Date;

public class GetRibTrt extends Traitement  {
    public GetRibTrt() {
    }
    public String calculerRIB(String RIB) {
            String cle = "";
            String resultat = "";
            if (RIB.length() == 18) {

                    String RI = RIB;
                    BigInteger rr = new BigInteger(RI.concat("00"));
                    int rest = rr.mod(new BigInteger("97")).intValue();
                    int nb = 97 - rest;
                    String nbr = "" + nb;
                    if (nbr.length() == 1)
                            resultat = "0" + nbr;
                    else
                            resultat = nbr;
           }
            return resultat;
    }
    public IValueObject perform(IValueObject vo) throws Exception {
    
    try
    {
    ContratCpt contrat= (ContratCpt)vo;
    PrimitiveVO primitiveVO=new PrimitiveVO();
    String codeBanque="";
    String codeStrct="";
    String codeBctStrc="";
    String codePrd="";
    String numContratCpt="";
    String cleRib="";
    String rib="";
    
    codeBanque="03";
    codeStrct=StrHandler.lpad(contrat.getStructure().getCodStrcStrc().toString(),'0',3);
    codeBctStrc=StrHandler.lpad(contrat.getStructure().getCodBctStrc().toString(),'0',3);
    codePrd=StrHandler.lpad(contrat.getContratCptId().getCodPrdPrd().toString(),'0',4);
    numContratCpt=StrHandler.lpad(contrat.getContratCptId().getNumCcptCcpt().toString(),'0',6);
    cleRib=calculerRIB(codeBanque+codeBctStrc+codeStrct+codePrd+numContratCpt);
    rib=codeBanque+codeBctStrc+codeStrct+codePrd+numContratCpt+cleRib;
    primitiveVO.setName("rib");
    primitiveVO.setVString(rib);
        return(primitiveVO);
    }
    catch (Exception e) {
     
        throw new Exception(e);
    }
    
    
    }
    public void genCroText(ValueObject vo) {
            ;
        } 
  
    
}
