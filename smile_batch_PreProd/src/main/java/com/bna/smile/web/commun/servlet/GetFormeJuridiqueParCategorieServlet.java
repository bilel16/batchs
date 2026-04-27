package com.bna.smile.web.commun.servlet;

import com.bna.smile.model.domainecommun.model.ListTypeCatTpce;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.commande.ChargerTypeCatPcePersonneCmd;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.TypeCatPers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;

public class GetFormeJuridiqueParCategorieServlet  extends BaseAjaxServlet {
    public GetFormeJuridiqueParCategorieServlet() {
    }

    
    public String getXmlContent(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        String codeCategorie = request.getParameter("codCatpCatp");
        String codeTypePersonne = request.getParameter("codTperTper");
        
        ChargerTypeCatPcePersonneCmd chargerTypeCatPcePersonneCmd = 
            new ChargerTypeCatPcePersonneCmd();
        TypeCatPers typeCatPersVo = new TypeCatPers();
        ListTypeCatTpce listTypeCatTpce = new ListTypeCatTpce();
        
        typeCatPersVo.setCodTperTper("2"); 
        typeCatPersVo.setCodCatpCatp(codeCategorie);
        
        listTypeCatTpce = 
                (ListTypeCatTpce)chargerTypeCatPcePersonneCmd.execute(typeCatPersVo);
                
        return new AjaxXmlBuilder().addItems(listTypeCatTpce.getListeCategp_Formj(), "libFjFj", "codFjFj").toString();
        
    }
}
