package com.bna.smile.batch.moulinette;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.log4j.Logger;

import com.bna.commun.util.ContextCROHandler;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.domainecaisse.dao.CaisseDAO;
import com.oxia.fwk.context.Context;
import com.oxia.scheduling.quartz.core.AbstractJob;
import com.oxia.security.abc.model.Personnel;
import com.oxia.security.abc.service.UserManager;


public class MoulDeclCaisseGab extends AbstractJob {

    private static final Logger logger = 
        Logger.getLogger(MoulDeclCaisseGab.class);

    /**
     *^point d'entrée de la moulinette
     */
    
    public static double getQuantieme(String dateS) {
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
    	
		try {
			Date date;
			date = simpleDateFormat.parse(dateS);
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(date);
			return gc.get(GregorianCalendar.DAY_OF_YEAR);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		
	}
    //écrit le résultat des trie
	public void ecrire(String nomFic, List<String> texte, String nbrline) {

		String adressedufichier = "C:/GAB/" + nomFic;

		try {

			FileWriter fw = new FileWriter(adressedufichier, false);

			BufferedWriter output = new BufferedWriter(fw);

			String Newligne = System.getProperty("line.separator");
			for (String line : texte) {
				output.write(line + Newligne);

			}
			output.write(nbrline);

			output.flush();

			output.close();

			System.out.println("fichier créé");
		} catch (IOException ioe) {
			System.out.print("Erreur : ");
			ioe.printStackTrace();
		}

	}
    public void perform() {

        try {
        	
            fixerUser();
            Context context = ContextHandler.getContext();
    		CaisseDAO caisseDAO = (CaisseDAO) context.getBean("caisseDAO");
    		List<String[]> list=caisseDAO.getMntSortDetailSessionCaisseGab();
    		
			
    		if(list!=null && !list.isEmpty()){
    			
    			List<String> texte=new ArrayList<String>();
    			//SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
    			//String dateSys = sdf.format(new Date());
    			String[] tablin0=list.get(0);
    			String dateSys=tablin0[3];
    			//SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
    			//String dateSys2 = sdf2.format(new Date());
    			String nbrLin="999"+tablin0[5]+String.format("%05d", list.size());
    		for(String[] tab :list){

    			String quantieme =
    				      StrHandler
    				        .lpad(String.valueOf(new Double(getQuantieme(tab[4])).intValue()),
    				          '0', 3);
    			int numcais=Long.valueOf(tab[1]).intValue()%10;
    			String refIS= StrHandler.lpad(tab[2], '0', 3)+quantieme+"MT"+numcais;
    			String line ="";
    			line=
    					"001"
    					+tab[3] //date date comptable j-1
    					+String.format("%03d", Long.valueOf(tab[2])) // code agence
    					+"1104000851"
    					+"0207"
    					+"00000033"
    					+tab[5] //date comptable j-2
    					+String.format("%015d", Long.valueOf(tab[0])) // mnt
    					+"+"
    					+refIS //ref inter siege
    					+"000000000000000+0000"; 
    			System.out.println(line);
    			texte.add(line);


    		}
    		ecrire("SM"+dateSys+".851", texte, nbrLin);
    		}
        } catch (Exception e) {
        	logger.error(e.getMessage());
            logger.fatal("**** Exception *** MoulDeclCaisseGab *** : " + this.getClass());
        }
    }


    public void fixerUser() {
            ContextCROHandler.setContext(ContextHandler.getContext());
            
        Personnel user = new Personnel();
        UserManager usermanager = (UserManager)ContextHandler.getContext().getBean("userManager");
        user = usermanager.getUser("9999");
        
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        auth.setDetails(user);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    
}