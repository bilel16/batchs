package com.bna.smile.model.reporting.traitement;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;

import com.bna.commun.model.OperationMoyPay;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domaineguichet.traitement.GetOperationMoyPayByIDTrt;
import com.bna.smile.model.reporting.model.Nombre;
import com.bna.smile.model.reporting.model.ParamRetraitVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ImprimerRetraitTrt extends Traitement {
    public ImprimerRetraitTrt() {
    }
    public IValueObject perform(IValueObject vo)  throws Exception{
        
        try
        {
            Context context= ContextHandler.getContext(); 
            BasicDataSource datasource =(BasicDataSource)context.getBean("dataSource");
            Connection connection=datasource.getConnection();
            
            Nombre nbre=new Nombre();
            Map parameters = new HashMap();
            OperationMoyPay operationMoyPay=new OperationMoyPay();
            PrimitiveVO primitiveVO=new PrimitiveVO();
            GetOperationMoyPayByIDTrt getOperationMoyPayByIDTrt=new GetOperationMoyPayByIDTrt();
            
            ParamRetraitVo paramRetrait=(ParamRetraitVo)vo;
            String file_name=paramRetrait.getPath()+"etat_ret_omn.jasper";
             
            String pLibEtat="P_LIB_ETAT";
            String pMatrUser="P_NUM_MATR_USER";
            String pOperMoyPay="P_NUM_OPER_OPM";
            String pLogo="P_LOGO";
            String pLettre="P_LETTRE";
            String vMatrUser="";
            String vLibEtat=paramRetrait.getLibEtat();
            
            String vLogo=paramRetrait.getPath();
            String vOperMoyPay=paramRetrait.getNumOperMp();
            
            primitiveVO.setVString(vOperMoyPay);
            operationMoyPay=(OperationMoyPay) getOperationMoyPayByIDTrt.exec(primitiveVO);
            if (paramRetrait.getNumMatrUser()==null)
                vMatrUser=operationMoyPay.getUser().getIdUser().toString();
            else
               vMatrUser=paramRetrait.getNumMatrUser();
            String vLettre=nbre.getLettre((Long)operationMoyPay.getMontDinOmp());
            
            parameters.put(pMatrUser, vMatrUser);
            parameters.put(pLibEtat, vLibEtat);
            parameters.put(pOperMoyPay, vOperMoyPay);
            parameters.put(pLogo, vLogo);
            parameters.put(pLettre, vLettre);
        
//             byte[] bytes = JasperRunManager.runReportToPdf(file_name,parameters, connection);
            return paramRetrait;
        }
         catch (Exception e) {
               /* com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("PecDemandeCertificationTrt "+e.getMessage());;
                paramDemandeChequeCertifie.getCertificationCheques().addError(erreur);*/
                throw new Exception(e);
        }   
        
    }
    public void genCroText(ValueObject vo) {
            ;
        } 
    
}
