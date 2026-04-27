package com.bna.smile.web.commun.util;

import com.oxia.fwk.logging.Log;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {

    Log logger = new Log(SessionUtil.class);
    
    public SessionUtil() {
    }
    
    public void removeSession(HttpServletRequest request,String formName){
          
          
              Enumeration e=request.getSession().getAttributeNames();   
              
              
                  while(e.hasMoreElements()){
                  
                      String str=e.nextElement().toString();
                          
                          if(str.indexOf("Form")>0){
                              
                              if(!str.equals(formName))
                                 {
                                   request.getSession().removeAttribute(str);
                                     }else {
                                       logger.info("class SessionUtil: Form non supprimée : "+str);
                                     }
                                     
                          }
                          
                  }
          }      
}
