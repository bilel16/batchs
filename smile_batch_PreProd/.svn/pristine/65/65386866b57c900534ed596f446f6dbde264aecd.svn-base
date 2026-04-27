package com.bna.smile.web.reporting.actions;

import com.bna.smile.model.reporting.commande.ImprimerRetraitCmd;
import com.bna.smile.model.reporting.model.ParamRetraitVo;
import com.bna.smile.web.commun.model.ParamAgence;
import com.bna.smile.web.operationguichet.form.GuichetRetraitForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class RetraitEspAction extends DispatchAction{
    public  ActionForward initierPage(ActionMapping mapping, ActionForm form, 
                              HttpServletRequest request, 
                              HttpServletResponse response) throws IOException, 
                                                                   ServletException {
    
        try{
             
             return mapping.findForward("success");  
            } 
            catch (Exception e) {
            System.out.println(">>>>>>>>>>> erreur " );
                e.printStackTrace();
                return mapping.findForward("error");
            }
            
       }
        public  ActionForward imprimerChqOm(ActionMapping mapping, ActionForm form, 
                                  HttpServletRequest request, 
                                  HttpServletResponse response) throws IOException, 
                                                                       ServletException {
               GuichetRetraitForm guichetRetraitForm = (GuichetRetraitForm)form;
            try{   
                  String operMoyPay=request.getAttribute("NumOperOmp").toString();
                //if (guichetRetraitForm.getChoixRetraitEspece().equalsIgnoreCase("7")){
                    ParamRetraitVo   paramRetraitVo=new ParamRetraitVo();  
                    ParamAgence paramAgence = new ParamAgence();
                    ImprimerRetraitCmd imprimerRetraitCmd=new ImprimerRetraitCmd();
                    String path="";
                    
                    path=getServlet().getServletContext().getRealPath("") + "\\reporting\\";
                    paramAgence = (ParamAgence)request.getSession().getAttribute("paramAgBNA");
                    
                    paramRetraitVo.setNumMatrUser(paramAgence.getNumMatrUser().toString());
                    paramRetraitVo.setPath(path);
                    paramRetraitVo.setLibEtat("Chèque OM");
                    paramRetraitVo.setNumOperMp(operMoyPay);
                    
                    /*Context context= ContextHandler.getContext(); 
                    BasicDataSource datasource =(BasicDataSource)context.getBean("dataSource");
                    Connection connection=datasource.getConnection();
                
                   String file_name = request.getRealPath("")+"\\reporting\\RIB.jasper";
                    Map parameters = new HashMap();
                    ParamAgence paramAgence=new ParamAgence();
                    paramAgence=(ParamAgence) request.getSession().getAttribute("paramAgBNA");
                    
                    String pLibEtat="P_LIB_ETAT";
                    String pMatrUser="P_NUM_MATR_USER";
                    String pLogo="P_LOGO";
                    
                    
                    String vLibEtat="Relevé d'Identité Bancaire";
                    String vMatrUser=paramAgence.getNumMatrUser().toString();
                    String vLogo=request.getRealPath("")+"\\reporting\\";
                    
                    parameters.put(pMatrUser, vMatrUser);
                    parameters.put(pLibEtat, vLibEtat);
                    parameters.put(pLogo, vLogo);
                    
                    JasperRunManager.runReportToPdfFile(file_name,parameters, connection);*/
                //}
                 return mapping.findForward("success");  
                } 
                catch (Exception e) {
                System.out.println(">>>>>>>>>>> erreur " );
                    e.printStackTrace();
                    return mapping.findForward("error");
                }
                
           }

    }
