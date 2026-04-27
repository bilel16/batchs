package com.bna.plugins;

import javax.servlet.ServletContext;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class ApplicationLunch implements PlugIn {

    public ApplicationLunch() {
    }

    public void destroy() {
    }

    public void init(ActionServlet actionServlet, ModuleConfig moduleConfig) {
        try {

            ServletContext servletCtx = actionServlet.getServletContext();
            Context context = 
                (Context)ContextFactory.initWebContext(servletCtx);
            actionServlet.getServletContext().setAttribute("CONTEXT", context);
            ContextHandler.setContext(context);
            ContextCROHandler.setContext(context);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
