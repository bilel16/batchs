package com.bna.smile.model.domainecompensation.gestionrejet.traitement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.commons.dbcp.BasicDataSource;

import com.bna.commun.model.Amende;
import com.bna.commun.model.Anr;
import com.bna.commun.model.Arp;
import com.bna.commun.model.BlocageCheque;
import com.bna.commun.model.Cheque;
import com.bna.commun.model.Cheque30;
import com.bna.commun.model.ChequeId;
import com.bna.commun.model.Cnp;
import com.bna.commun.model.ComplementCnp;
import com.bna.commun.model.ComplementPapillon;
import com.bna.commun.model.Decompte;
import com.bna.commun.model.Devise;
import com.bna.commun.model.ErrorMigration;
import com.bna.commun.model.MouvementCompensation;
import com.bna.commun.model.Papillon;
import com.bna.commun.model.PapillonId;
import com.bna.commun.model.Preavis;
import com.bna.commun.model.SuiviHn;
import com.bna.commun.model.TraceCheque;
import com.bna.commun.model.Valeur;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationVo;
import com.bna.smile.model.traitementCompensationRecu.dao.RejetDAO;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;
import com.oxia.fwk.searchengine.SearchEngine;

/**
 * @author BNA
 *
 */
public class MoulinetteMigrationTrt2 extends Traitement {

	Context context = ContextHandler.getContext();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	ISearchEngine search = (SearchEngine) context.getBean("searchEngine");
	ICriteria criteria = search.createCriteria();
	IExpression expression = search.createExpression();
	CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");
	RejetDAO  rejetDao = (RejetDAO)context.getBean("rejetDAO");
	String valChq[]=   {"30"   ,   "31"   ,   "32"   ,   "33"};
	SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");



	@Override
	public IValueObject perform(IValueObject vo) {

		CompensationVo compensationVo = (CompensationVo) vo;
		String srcRejet = Configuration.getPathFileRejet()+"TRPLACEM."+ StrHandler.lpad(""+ compensationVo.getStrutcure().getCodStrcStrc(), '0',	3);
		System.out.println("Treatement file :" + srcRejet);
	    importRejet(srcRejet, StrHandler.lpad(""+ compensationVo.getStrutcure().getCodStrcStrc(), '0', 3));
		return compensationVo;
	}



	/**
	 * Function that read line  by line from file rejet and insert into database
	 * @param fichier
	 * @param codStrcStrc
	 */
	public void importRejet(String fichier, String codStrcStrc) {

		try {
			BufferedWriter bufWriter = null;
			FileWriter fileWriter = null;
			InputStream ips = new FileInputStream(fichier);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String line = null, line171, line172;
			
			// init all table for migration , delete previously migration
			clearDb();
			// treat file line by line
			long numberLine = 0;
			while ((line = br.readLine()) != null && !line.equals("end")) {
				if (line.length() > 3 && line.substring(0, 3).equals("171")) {
					line171 = line.substring(15);
					if ((line = br.readLine()) != null && line.length() > 3	&& line.substring(0, 3).equals("172")) {
						line172 = line.substring(15);
						line = line171+";"+line172;
						// control sur la ligne dans le cas ou 041 cod_strc manque une date
						String attribs[] =  line.split(";",-1);
						/*String attribs[] =  new String[attribs1.length+1];
						for ( int i = 0; i<55;i++) {
							attribs[i]=attribs1[i];
						}
						attribs[55]="99999999";
						for ( int j = 56; j<attribs1.length;j++) {
							attribs[j]=attribs1[j-1];
						}*/
						// on prend seulement les cheques de 2008 jusqu'a aujourd'hui . .
						Date datRejet = format.parse(attribs[1]) ;
						Date datRepere= format.parse("01072013");
						
						numberLine++;
						if (datRejet.compareTo(datRepere)>0) {
						if ((attribs=checkLineSeparator(codStrcStrc,attribs))== null || checkConsistencyData(codStrcStrc,attribs)==null) {
							continue;
						}else{
							//attribs = checkConsistencyData(codStrcStrc,attribs);
							createRejet(attribs, codStrcStrc);
							createErrorMigration(codStrcStrc, attribs,null);
							}							
					}
					}
					}
				
				

			}

			System.out.println("nombre de ligne : "+numberLine);

			br.close();
			fileWriter = new FileWriter(fichier, true);
			bufWriter = new BufferedWriter(fileWriter);
			bufWriter.newLine();
			bufWriter.write("end");
			bufWriter.close();
			printStatMigration(codStrcStrc);

		} catch (Exception e) {
			System.out.println(e.toString());
		}


	}

	@Override
	protected void genCroText(ValueObject arg0) {

	}


	/**
	 * Function that create à GIP's table ( cnp, preavis, cheque ) from  string'line
	 * @param line
	 * @param codStrcStrc
	 * @throws Exception
	 */
	public void createRejet(String [] attribs, String codStrcStrc) throws Exception {

	
		
		Cheque cheque = new Cheque();
		Preavis preavis = null;
		Papillon papillon = null;
		Cnp cnp = null;
		SuiviHn suiviHn = null;
		Amende amende = null;
		Anr anr = null;
		SimpleDateFormat format = new SimpleDateFormat();
		SimpleDateFormat format3 = new SimpleDateFormat("dd/MM/yyyy");

		format.applyPattern("ddMMyyyy");
		SimpleDateFormat format2 = new SimpleDateFormat();
		format2.applyPattern("yyyyMMdd");		



		// Creating Cheque
		  long numChq = new Long(attribs[0].substring(10));
          cheque = new Cheque(); cheque.setCodAgdeChq(attribs[5]);
		  cheque.setCodBadeChq(attribs[4]);
		  cheque.setCodAgemChq(attribs[11]);
		  cheque.setCodBaemChq(attribs[10]); 
		  cheque.setCodEnrChq(new Long("21")); //21 
		  cheque.setCodEtatChq("R"); //
		  cheque.setCodLemiChq(attribs[22]);
		  
		  
		  String mRejet ="";
		  mRejet += attribs[15].trim(); 
		  mRejet += attribs[16].trim();
		  mRejet += attribs[17].trim();
		  mRejet += attribs[18].trim();
		  
		  // si tout les 4 motif rej =00 , on prend le code d'envoi (composé sur 8 position).
		  if(Long.valueOf(mRejet)==0) mRejet =attribs[87].trim();
		  
	
		  cheque.setCodMrejChq(mRejet);
		  cheque.setCodNateChq("M");// Migration
		  cheque.setCodNcptChq("1");//
		  cheque.setCodRejChq(new Valeur(new Long("81"))); // 81  by default rejet 81
		  cheque.setCodSbenChq(new  Long("1"));
		  cheque.setCodSChq("N");
		  cheque.setMntPPartChq(null);
		  Date datDel = null;
		  Date datEm = null ;
		  Date datOp = null;
		  Date datPres = null;		  
		  try  {
			  datEm = attribs[21].equals("") ? null :format.parse(attribs[21]) ;
		  }
		  catch (Exception e) {
			  createErrorMigration(codStrcStrc,attribs,  "La valeur :[ "+attribs[21]+ "] n'est pas une date emission");
			return;
		}
		  
		  try  {
			  datPres = attribs[9].equals("") ? null :format.parse(attribs[9]) ;
		  }
		  catch (Exception e) {
			  createErrorMigration(codStrcStrc,attribs,  "La valeur :[ "+attribs[9]+ "] n'est pas une date presentation");
			return;	
		  }
		  try  {

			  datDel = attribs[20].equals("") ? null :format.parse(attribs[20]) ;
		  }
		  catch (Exception e) {
			  createErrorMigration(codStrcStrc,attribs,  "La valeur :[ "+attribs[20]+ "] n'est pas une date  delivrance");
			return;
		}
		  try  {
			  datOp = attribs[1].equals("") ? null :format.parse(attribs[1]) ;
		  }
		  catch (Exception e) {
			  createErrorMigration(codStrcStrc,attribs,  "La valeur :[ "+attribs[1]+ "] n'est pas une date operation");
			return;
		}		  
		  cheque.setDatDelChq(datDel);
		  cheque.setDatEmiChq(datEm);
		  if ( datOp == null) {
			  createErrorMigration(codStrcStrc,attribs, "date operation cheque null") ;
			  return;
		  }
		  cheque.setDatOpeChq(datOp); 
		  
		  Devise  devise= new Devise();
		  String codDev = attribs[6].trim().equals("")|| attribs[6].trim().equals("000") ?"788": attribs[6];
		  devise.setCodDevDev(new Long(codDev));
		  cheque.setDevise(devise);
		  
		  cheque.setMntChqChq(new Long(attribs[7]));
		  
		  cheque.setNomPrnChq(attribs[13]);
		  cheque.setNumEvenChq(new Long("0"));
		  cheque.setNumEvrcpChq(new Long("0"));
		  cheque.setNumLotChq(new Long("0001"));
		  cheque.setRefFicChq("N");
		  cheque.setRibTrecChq("N");
		  
		  // Cod situation rejet : 31 , 30 , 32 ...
		  String codVal = attribs[80].trim();
		  if(codVal.equals("")) codVal= attribs[88].trim();
		  if(!Arrays.asList(valChq).contains(codVal)) codVal= "30";
		  cheque.setValeur(new  Valeur(new Long(codVal)));
		  String ribTir = calculerRIB(attribs[4]+attribs[5]+codStrcStrc+attribs[0].substring(0,10));
		  ribTir= StrHandler.lpad(ribTir, '0',20);
		  String ribBen = attribs[12];
		  if(ribBen.equals("00000000000000000000")) {
			  createErrorMigration(codStrcStrc, attribs, "Rib benif incorrect, not found in ADT data base ! ");
			  return ;
		  }
		  ChequeId id = new ChequeId(numChq, ribTir,ribBen) ;
		  
		  // mig 
		  cheque.setDatMigChq(new Date());
		  cheque.setChequeId(id);
	 
		  
		  
		  

		  
		  
		  // Checked Value 
		  // Checked Value 
          Date datPayAmd = attribs[59].trim().equals("") ? null : format2.parse(attribs[59].trim()) ;
		  Date datRcnp = attribs[28].trim().equals("") ? null : format.parse(attribs[28].trim()) ;
		  Date datExploit = attribs[37].trim().equals("") ? null : format.parse(attribs[37].trim()) ;
		  Date datPv = attribs[49].trim().equals("") ? null : format.parse(attribs[49].trim()) ;	
		  Date datAnr = attribs[40].trim().equals("") ? null : format.parse(attribs[40].trim()) ;
       	  Date datArp = attribs[42].trim().equals("") ? null : format.parse(attribs[42].trim()) ;
       	  Date datReg = attribs[55].trim().equals("") ? null : format.parse(attribs[55].trim()) ;
       	  Date datInt = attribs[57].trim().equals("") ? null : format.parse(attribs[57].trim()) ;
       	  Date datCnpDec = attribs[53].trim().equals("") ? null : format.parse(attribs[53].trim()) ;
       	  // end Check value
       	  
       	  
			// BEGIN CHECHINK MONTANT
       	  		
			Long mntAmdDec = null;
			if (new Long(attribs[58]) != 0)
				mntAmdDec = new Long(attribs[58]);
			Long mntAnrDec = null;
			// Pas de commission ANR ! Pas de montant dans le fichier 
			//if (datAnr !=null)
			//	mntAnrDec = new Long(0);
			
			
			// TODO : Check this :  52 montant cnp , remplace par 54 montant regularisation ! confu. 
			Long mntCnpDec = null;
			if (new Long(attribs[54]) != 0) {
				mntCnpDec = new Long(attribs[54]);
			}
			else {
				datCnpDec= null;
			}
			Long mntHnDec  = null;
			if (new Long(attribs[48]) != 0)
				mntHnDec = new Long(attribs[48]);
			
			
			// begin  interet de retard 
			Long mntIntDec = null;
			if (new Long(attribs[56]) !=0) {
				mntIntDec = new Long(attribs[56]);
			} else {
				datInt = null;
			}
			
			// end interet retard 
			
			// begin preavis
			Long mntPreDec = 0L;
			mntPreDec = new Long(attribs[50]);
			Long mntRegChDec = null; 
			if ( new Long(attribs[54]) != 0)
				mntRegChDec =new Long(attribs[54]);
			//end  preavis
			
     	  
       	  // end Check value
       	  
       	  
			
			
			// END CHECHING MONTANT

       	  crudService.create(cheque);
       	  preavis = rejetDao.getPreavisAdt(""+numChq, ribTir, ribBen, attribs[5], attribs[1]);
       	  if (preavis != null) {
       		  preavis.setChequeId(cheque.getChequeId());
       		  preavis.setDatMigPre(new Date());
       		  preavis.setCheque(cheque);
       		  crudService.create(preavis);
       	  }
		
       	  cnp = rejetDao.getCnpAdt(""+numChq, ribTir, ribBen, attribs[5], attribs[1]);
       	  if (cnp != null) {
       		  cnp.setChequeId(cheque.getChequeId());
       		  cnp.setDatMigCnp(new Date());
       		  cnp.setCheque(cheque);
       		  crudService.create(cnp);
       		  
       		  List<ComplementCnp> complements= (List<ComplementCnp>) cnp.getComplementCnps();
       		  for (ComplementCnp complement : complements){
       			  complement.setDatMigCmp(new Date());
       			  complement.setCnp(cnp);
       			  crudService.create(complement);
       			  
       		
       		  }
       		  
  			// Creating SuiviHn
       		  // si date remise != null

  			if (datRcnp != null)
  			{
  				suiviHn = new SuiviHn();
  				suiviHn.setDatExpLrShn(datExploit);
  				suiviHn.setDatOpeChq(datOp);
  				suiviHn.setDatPvShn(datPv); // TODO : date paiement huissier notaire
  				suiviHn.setDatRcnpShn(datRcnp);
  				suiviHn.setMntFraisNImpShn(new Long(attribs[69]));  // montant imposable
  				suiviHn.setMntFraisShn(new Long(attribs[48])); // autre frais HN
  				String nomHn =attribs[30].trim().replaceAll("( )+", " ").length()>20 ?attribs[30].trim().replaceAll("( )+", " ").substring(0, 19):attribs[30].trim().replaceAll("( )+", " ");
  				suiviHn.setNomNomShn(nomHn);  // Cas ou le nom Hn est superieur à 30 caractere
  				suiviHn.setNomPrnShn(" "); 
  				if ( attribs[36].trim().equals(""))
  					suiviHn.setNumExpLrShn(null);
  				else
  					suiviHn.setNumExpLrShn(new Long(attribs[36].trim()));
  				
  				suiviHn.setRibHnShn(attribs[29].trim());
  				suiviHn.setTypRFisShn(null); 
  				suiviHn.setTypSignShn(null);
  				suiviHn.setChequeId(id);
  				suiviHn.setCnp(cnp);
  				// mig 
  				suiviHn.setDatMigHn(new Date());
  				crudService.create(suiviHn);
  			}
  			
  			// Creating ANR
  			
  			if (datAnr != null){
  				anr = new Anr();
  				anr.setChequeId(cheque.getChequeId());
  				anr.setAmende(amende);
  				anr.setDatAnrAnr(datAnr);
  				anr.setDatOpeChq(datOp);
  				anr.setCheque(cheque);
  				// mig
  				anr.setDatMigAnr(new Date());
  				crudService.create(anr);

  			}
  			
  			
  			 // Creating Amende
  			if (datPayAmd != null)
  			{
  				
  				amende = new Amende();
  				amende.setCodDelAme(null);
  				amende.setDatOpeChq(datOp);
  				amende.setDatPayAme(datPayAmd);
  				amende.setMntPayAme(mntAmdDec);
  				amende.setChequeId(cheque.getChequeId());
  				amende.setAnr(anr);
  				amende.setDatMigAmd(new Date());
  				crudService.create(amende);
  			}
  			

  			
  			// Creating  ARP 
  		
  		 if( datArp != null ){
  				
  				Arp arp = new Arp();
  				arp.setCheque(cheque);
  				arp.setChequeId(cheque.getChequeId());
  				arp.setDatArpArp(datArp);
  				arp.setMntRegArp(new Long(attribs[43]));
  				arp.setMntRginArp(0L); // TODO : Check this
  				arp.setDatOpeChq(datOp);
  				arp.setRefFicArp("N");
  				// mig
  				arp.setDatMigArp(new Date());
  				crudService.create(arp);
  			}
  		 
  		}
  			// Creating Decompte 
       	Decompte decompte=null;
  			if (preavis != null){
  			 	decompte = new Decompte();
  			 	decompte.setCheque(cheque);
  				decompte.setDatOpeChq(datOp);
  			 	decompte.setChequeId(cheque.getChequeId());
  				decompte.setMntPreDec(mntPreDec);
  				decompte.setDatPreDec(preavis.getDatPrePre());
  				decompte.setTvaPreDec(0L);
  				decompte.setDatMigDec(new Date());
  				//decompte.setFraisTelegramDec(0L);
  				crudService.create(decompte);
  				
  			
  			if (cnp != null){
  				if (numChq == 860541)
  					System.out.println("debug");
  				decompte.setDatCnpDec(datCnpDec);
  			// montant commission cnp dans le fichier toujours 0 !! , on prend montant de regularisation confu. avec montant cnp
  				// TODO check this
  				//decompte.setMntCnpDec(mntCnpDec);
  				decompte.setMntCnpDec(mntRegChDec);
 				decompte.setDatAmdeDec(datPayAmd);
  			//	decompte.setDatAnrDec(datAnr);
  				decompte.setDatHnDec(datPv);
  				decompte.setDatIntDec(datInt);
  				decompte.setDatRegChDec(datReg);
  				decompte.setMntAmdeDec(mntAmdDec);
  				//decompte.setMntAnrDec(mntAnrDec);
  				decompte.setMntHnDec(mntHnDec);
  				decompte.setMntIntDec(mntIntDec);
  				decompte.setMntRegChDec(null);
  				
  				//decompte.setTvaAnrDec(null);
  				decompte.setTvaCnpDec(null);
  				//decompte.setTvaHnDec(null);

  				crudService.update(decompte);
  				// mig


  			}
  			}

       	  papillon = rejetDao.getPapillonAdt(""+numChq, ribTir, ribBen, attribs[5], attribs[1]);
       	  if (papillon != null) {
       		papillon.setDatMigPap(new Date());
       		papillon.setCheque(cheque);
       		  crudService.create(papillon);
       		  
       		  List<ComplementPapillon> complements= rejetDao.getComplementPapillonAdt(papillon);
       		  for (ComplementPapillon complement : complements){
       			  complement.setDatMigCmp(new Date());
       			  complement.setPapillon(papillon);
       			  crudService.create(complement);
       		  }
       	  } 
       	 switch(Integer.valueOf(attribs[32].trim())) {
       	 
       	 case 2: cheque.setCodRejChq(new Valeur(Long.valueOf(Constants.COD_PREAVIS)));
       	 break;
       	 case 3:
       	 case 4: cheque.setCodRejChq(new Valeur(Long.valueOf(Constants.COD_CNP)));
       	 break;     
       	 case 6:
       	 case 66: cheque.setCodRejChq(new Valeur(Long.valueOf(Constants.COD_ARP)));
       	 break; 
       	 case 7:	 cheque.setCodRejChq(new Valeur(Long.valueOf(Constants.COD_CNP))); // ANR 
       	 break;          	 
       	 case 99:	 cheque.setCodRejChq(new Valeur(Long.valueOf(Constants.COD_PAPILLON)));
       	 break;         	   	 
       	 
       	 }
       	 crudService.update(cheque);
       	  
       	  
       	  
       	  
		  





	}
	
	
    /**
     * function that calculate the key of the rib , and return the complete rib 
     * @param RIB
     * @return
     */
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
                        resultat = RIB+"0" + nbr;
                else
                        resultat = RIB+nbr;
       }
        return resultat;
}
    

    /**
     * function that test if attrib if numeric or no 
     * @param s
     * @return
     */
    public boolean isNumeric(String s) {  
        return s.matches("[-+]?\\d*\\.?\\d+");  
    }  
    
	/**
	 * Function that insert the line  into table error_migration if there is an error with the line , like  line without rib of benef
	 * @param line
	 * @param motif
	 * @throws Exception
	 */
	public void createErrorMigration(String codStrcStrc,String[] attribs,String motif) throws Exception {

		
		//System.err.println(line);
		//  option -1 to get empty attribs at the and of line 
		//String attribs[] =  line.split(";",-1);
		
		
		
		
		
		ErrorMigration error = new ErrorMigration();
		error.setRcptchq(attribs[0]);
		error.setRdatrej(attribs[1]);
		error.setRnummvt(attribs[2]);
		error.setRdatsit(attribs[3]);
		error.setRcodbtir(attribs[4]);
		error.setRcodatir(attribs[5]);
		error.setRcoddev(attribs[6]);
		error.setRmontchq(attribs[7]);
		error.setRsoldpre(attribs[8]);
		error.setRdatpres(attribs[9]);
		error.setRcodbpre(attribs[10]);
		error.setRcodapre(attribs[11]);
		error.setRribben(attribs[12]);
		error.setRbenf(attribs[13]);
		error.setRcodreji(attribs[14]);
		error.setRcodrejn(attribs[15]);
		error.setRcodrejn2(attribs[16]);
		error.setRcodrejn3(attribs[17]);
		error.setRcodrejn4(attribs[18]);
		error.setRmotifn(attribs[19]);
		error.setRdatdel(attribs[20]);
		error.setRdatem(attribs[21]);
		error.setRlieem(attribs[22]);
		error.setRnumpap(attribs[23]);
		error.setRdatprea(attribs[24]);
		error.setRdatlim1r(attribs[25]);
		error.setRdatcnp(attribs[26]);
		error.setRnumcnp(attribs[27]);
		error.setRdatremh(attribs[28]);
		error.setRcpthuis(attribs[29]);
		error.setRnomhuis(attribs[30]);
		error.setRdatlimh(attribs[31]);
		error.setRcodsit(attribs[32]);
		error.setRlimsit(attribs[33]);
		error.setRmontbloc(attribs[34]);
		error.setRdatblocm(attribs[35]);
		error.setRnumexpl(attribs[36]);
		error.setRdatexpl(attribs[37]);
		error.setRnumlrec(attribs[38]);
		error.setRdatlrec(attribs[39]);
		error.setRdatanr(attribs[40]);
		error.setRdregchq(attribs[41]);
		error.setRdatarp(attribs[42]);
		error.setRmontver(attribs[43]);
		error.setRnumver(attribs[44]);
		error.setRcodpro(attribs[45]);
		error.setRcodop(attribs[46]);
		error.setRmodpay(attribs[47]);
		error.setRmonthuis(attribs[48]);
		error.setRdpayhuis(attribs[49]);
		error.setRmontprea(attribs[50]);
		error.setRdpayprea(attribs[51]);
		error.setRmcomcnp(attribs[52]);
		error.setRdpcomcnp(attribs[53]);
		error.setRmontcom(attribs[54]);
		error.setRdppmontcom(attribs[55]);
		error.setRmontint(attribs[56]);
		error.setRdpmontint(attribs[57]);
		error.setRmontquit(attribs[58]);
		error.setRdmontquit(attribs[59]);
		error.setRdatmaj(attribs[60]);
		error.setRcinsig1(attribs[61]);
		error.setRnomsig1(attribs[62]);
		error.setRcinsig2(attribs[63]);
		error.setRnomsig2(attribs[64]);
		error.setRcinsig3(attribs[65]);
		error.setRnomsig3(attribs[66]);
		error.setRsolddres(attribs[67]);
		error.setRtesthuis(attribs[68]);
		error.setRmontthuis1(attribs[69]);
		error.setRflagcom(attribs[70]);
		error.setRdatdbrej(attribs[71]);
		error.setRdatcrcnp(attribs[72]);
		error.setRfiller1(attribs[73]);
		error.setRfiller2(attribs[74]);
		error.setRmontinit(attribs[75]);
		error.setFlagsit(attribs[76]);
		error.setRmntbdev(attribs[77]);
		error.setRcourdev(attribs[78]);
		error.setRcodrejn5(attribs[79]);
		error.setRcodsitp(attribs[80]);
		error.setRrejcod21(attribs[81]);
		error.setRblocsauv(attribs[82]);
		error.setRmontints(attribs[83]);
		error.setRmontvers(attribs[84]);
		error.setRmontchqs(attribs[85]);
		error.setRmont353(attribs[86]);
		error.setRcodenv(attribs[87]);
		error.setRcodval(attribs[88]);
		error.setRdenvpap(attribs[89]);
		error.setMotif(motif);
		
		// ribtir pour correction des erreurs
		String rribtir = calculerRIB(attribs[4]+attribs[5]+codStrcStrc+attribs[0].substring(0,10));
		error.setRribtir(rribtir);
		crudService.create(error);
	
	}

public String [] checkLineSeparator(String codStrcStrc,String [] attribs) throws Exception {

	
	if (attribs.length ==91) {
		return attribs;
	}else {
		 createErrorMigration(codStrcStrc,attribs, "Error in line separator ");
		 return null;

	}
}
public boolean checkNumericData(String codStrcStrc,String line) throws Exception { 
	//  option -1 to get empty attribs at the and of line 
	//String attribs[] =  line.split(";",-1);
	String attribs1[] =  line.split(";",-1);
	String attribs[] =  new String[attribs1.length+1];
	for ( int i = 0; i<55;i++) {
		attribs[i]=attribs1[i];
	}
	attribs[55]="99999999";
	for ( int j = 56; j<attribs1.length;j++) {
		attribs[j]=attribs1[j-1];
	}
	
	String motif="";
	
	if ( ! isNumeric(attribs[0])) motif+=",Error in position 0: not numeric data";
	if ( ! isNumeric(attribs[1])) motif+=",Error in position 1: not numeric data";
	if ( ! isNumeric(attribs[2])) motif+=",Error in position 2: not numeric data";
	if ( ! isNumeric(attribs[3])) motif+=",Error in position 3: not numeric data";
	if ( ! isNumeric(attribs[4])) motif+=",Error in position 4: not numeric data";
	if ( ! isNumeric(attribs[5])) motif+=",Error in position 5: not numeric data";
	if ( ! isNumeric(attribs[6])) motif+=",Error in position 6: not numeric data";
	if ( ! isNumeric(attribs[7])) motif+=",Error in position 7: not numeric data";
	if ( ! isNumeric(attribs[8])) motif+=",Error in position 8: not numeric data";
	if ( ! isNumeric(attribs[9])) motif+=",Error in position 9: not numeric data";
	if ( ! isNumeric(attribs[10])) motif+=",Error in position 10: not numeric data";
	if ( ! isNumeric(attribs[11])) motif+=",Error in position 11: not numeric data";
	if ( ! isNumeric(attribs[12])) motif+=",Error in position 12: not numeric data";
	//if ( ! isNumeric(attribs[0])) motif+=",Error in position 13: not numeric data";
	//if ( ! isNumeric(attribs[0])) motif+=",Error in position 14: not numeric data";
	if ( ! isNumeric(attribs[15])) motif+=",Error in position 15: not numeric data";
	if ( ! isNumeric(attribs[16])) motif+=",Error in position 16: not numeric data";
	if ( ! isNumeric(attribs[17])) motif+=",Error in position 17: not numeric data";
	if ( ! isNumeric(attribs[18])) motif+=",Error in position 18: not numeric data";
	//if ( ! isNumeric(attribs[0])) motif+=",Error in position 19: not numeric data";
	if ( ! isNumeric(attribs[20])) motif+=",Error in position 20: not numeric data";
	if ( ! isNumeric(attribs[21])) motif+=",Error in position 21: not numeric data";
	//if ( ! isNumeric(attribs[0])) motif+=",Error in position 22: not numeric data";
	if ( ! isNumeric(attribs[23])) motif+=",Error in position 23: not numeric data";
	if ( ! isNumeric(attribs[24])) motif+=",Error in position 24: not numeric data";
	if ( ! isNumeric(attribs[25])) motif+=",Error in position 25: not numeric data";
	if ( ! isNumeric(attribs[26])) motif+=",Error in position 26: not numeric data";
	if ( ! isNumeric(attribs[27])) motif+=",Error in position 27: not numeric data";
	if ( ! isNumeric(attribs[28])) motif+=",Error in position 28: not numeric data";
	if ( ! isNumeric(attribs[29])) motif+=",Error in position 29: not numeric data";
	//if ( ! isNumeric(attribs[0])) motif+=",Error in position 30: not numeric data";
	if ( ! isNumeric(attribs[31])) motif+=",Error in position 31: not numeric data";
	if ( ! isNumeric(attribs[32])) motif+=",Error in position 32: not numeric data";
	if ( ! isNumeric(attribs[33])) motif+=",Error in position 33: not numeric data";
	if ( ! isNumeric(attribs[34])) motif+=",Error in position 34: not numeric data";
	if ( ! isNumeric(attribs[35])) motif+=",Error in position 35: not numeric data";
	if ( ! isNumeric(attribs[36])) motif+=",Error in position 36: not numeric data";
	if ( ! isNumeric(attribs[37])) motif+=",Error in position 37: not numeric data";
	if ( ! isNumeric(attribs[38])) motif+=",Error in position 38: not numeric data";
	if ( ! isNumeric(attribs[39])) motif+=",Error in position 39: not numeric data";
	if ( ! isNumeric(attribs[40])) motif+=",Error in position 40: not numeric data";
	if ( ! isNumeric(attribs[41])) motif+=",Error in position 41: not numeric data";
	if ( ! isNumeric(attribs[42])) motif+=",Error in position 42: not numeric data";
	if ( ! isNumeric(attribs[43])) motif+=",Error in position 43: not numeric data";
	if ( ! isNumeric(attribs[44])) motif+=",Error in position 44: not numeric data";
	if ( ! isNumeric(attribs[45])) motif+=",Error in position 45: not numeric data";
	if ( ! isNumeric(attribs[46])) motif+=",Error in position 46: not numeric data";
	//if ( ! isNumeric(attribs[0])) motif+=",Error in position 47: not numeric data";
	if ( ! isNumeric(attribs[48])) motif+=",Error in position 48: not numeric data";
	if ( ! isNumeric(attribs[49])) motif+=",Error in position 49: not numeric data";
	if ( ! isNumeric(attribs[50])) motif+=",Error in position 50: not numeric data";
	if ( ! isNumeric(attribs[51])) motif+=",Error in position 51: not numeric data";
	if ( ! isNumeric(attribs[52])) motif+=",Error in position 52: not numeric data";
	if ( ! isNumeric(attribs[53])) motif+=",Error in position 53: not numeric data";
	if ( ! isNumeric(attribs[54])) motif+=",Error in position 54: not numeric data";
	if ( ! isNumeric(attribs[55])) motif+=",Error in position 55: not numeric data";
	if ( ! isNumeric(attribs[56])) motif+=",Error in position 56: not numeric data";
	if ( ! isNumeric(attribs[57])) motif+=",Error in position 57: not numeric data";
	if ( ! isNumeric(attribs[58])) motif+=",Error in position 58: not numeric data";
	if ( ! isNumeric(attribs[59])) motif+=",Error in position 59: not numeric data";
	if ( ! isNumeric(attribs[60])) motif+=",Error in position 60: not numeric data";
	if ( ! isNumeric(attribs[61])) motif+=",Error in position 61: not numeric data";
	//if ( ! isNumeric(attribs[0])) motif+=",Error in position 62: not numeric data";
	if ( ! isNumeric(attribs[63])) motif+=",Error in position 63: not numeric data";
	//if ( ! isNumeric(attribs[0])) motif+=",Error in position 64: not numeric data";
	if ( ! isNumeric(attribs[65])) motif+=",Error in position 65: not numeric data";
	//if ( ! isNumeric(attribs[0])) motif+=",Error in position 66: not numeric data";
	if ( ! isNumeric(attribs[67])) motif+=",Error in position 67: not numeric data";
	if ( ! isNumeric(attribs[68])) motif+=",Error in position 68: not numeric data";
	if ( ! isNumeric(attribs[69])) motif+=",Error in position 69: not numeric data";
	//if ( ! isNumeric(attribs[0])) motif+=",Error in position 70: not numeric data";
	if ( ! isNumeric(attribs[71])) motif+=",Error in position 71: not numeric data";
	if ( ! isNumeric(attribs[72])) motif+=",Error in position 72: not numeric data";
	if ( ! isNumeric(attribs[73])) motif+=",Error in position 73: not numeric data";
	if ( ! isNumeric(attribs[74])) motif+=",Error in position 74: not numeric data";
	if ( ! isNumeric(attribs[75])) motif+=",Error in position 75: not numeric data";
	//if ( ! isNumeric(attribs[0])) motif+=",Error in position 76: not numeric data";
	//if ( ! isNumeric(attribs[0])) motif+=",Error in position 77: not numeric data";
	//if ( ! isNumeric(attribs[0])) motif+=",Error in position 78: not numeric data";
	//if ( ! isNumeric(attribs[0])) motif+=",Error in position 79: not numeric data";
	if ( ! isNumeric(attribs[80])) motif+=",Error in position 80: not numeric data";
	if ( ! isNumeric(attribs[81])) motif+=",Error in position 81: not numeric data";
	if ( ! isNumeric(attribs[82])) motif+=",Error in position 82: not numeric data";
	if ( ! isNumeric(attribs[83])) motif+=",Error in position 83: not numeric data";
	if ( ! isNumeric(attribs[84])) motif+=",Error in position 84: not numeric data";
	if ( ! isNumeric(attribs[85])) motif+=",Error in position 85: not numeric data";
	if ( ! isNumeric(attribs[86])) motif+=",Error in position 86: not numeric data";
	if ( ! isNumeric(attribs[87])) motif+=",Error in position 87: not numeric data";
	if ( ! isNumeric(attribs[88])) motif+=",Error in position 88: not numeric data";
	if ( ! isNumeric(attribs[89])) motif+=",Error in position 89: not numeric data";
	
	 if (motif.equals("")) {
		 return false;
	 } else{
		 createErrorMigration(codStrcStrc,attribs, motif);
		 return true;
	 }
	
}

public String [] checkConsistencyData(String codStrcStrc, String[] attribs) throws Exception {


	String motif="";
	String ribTir = calculerRIB(attribs[4]+attribs[5]+codStrcStrc+attribs[0].substring(0,10));

	// numcnp, date cnp, sig , hn.
	 if ( (attribs[27].trim().equals("")) && !(attribs[27].trim().equals(""))) {
		motif += ", num cnp not found " ;
	 }
	 if   ( !(attribs[27].trim().equals("")) && (attribs[27].trim().equals(""))){
		 motif += ", date cnp not found " ;		 
	 }
	 if  ( !(attribs[27].trim().equals("")) && !(attribs[27].trim().equals("")) && (attribs[27].trim().equals(""))) {
		 motif += ", rib hn not found " ;		 
	 }
//	 if  ( !(attribs[27].trim().equals("")) && !(attribs[27].trim().equals("")) && !(attribs[29].trim().equals("")) && (attribs[61].trim().equals(""))) {
//		 motif += ", aucun sig cnp found " ;		 
//	 }	
	// numpap , date envoi pap
	 // Si numero papillon n'existe pas , càd qu'il y a pas de papillon.
//	 if ( (attribs[23].trim().equals("")) && !(attribs[89].trim().equals(""))) {
//		 attribs[23] = rejetDao.getNumPapAdt(attribs[0].substring(10), ribTir,attribs[5],attribs[1]);
//	 }
	 if( !(attribs[23].trim().equals("")) && (attribs[89].trim().equals(""))) {

		 attribs[89] = rejetDao.getDatPapAdt(attribs[0].substring(10), ribTir,attribs[5],attribs[23].trim());

	 }
//	 if ( !(attribs[23].trim().equals("")) && !(attribs[89].trim().equals(""))&& (attribs[61].trim().equals(""))) {
//		 motif += ", aucun sig pap found " ;		 
//	 }
	// other control 
	 
	// code valeur 
	 if ( attribs[88].trim().equals("")){
		 // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
		 // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
		 // TODO : set by default cod_val to 30
		 attribs[88] = "30";
		 //motif += ", cod val not found " ;		 
	 }
	// rib beneificiaire
		// rib beneificiaire
	 if ( attribs[12].trim().equals("") || attribs[12].trim().equals("00000000000000000000")){
		 String ribBen = rejetDao.getRibBenAdt(attribs[0].substring(10), ribTir,attribs[5]);
		 if (ribBen == null){
			 attribs[12]="99999999999999999999";
		 //motif="rib benef not found";
		         }
		 else {
    		System.out.println("Getting rib benificiaire from ADT ...: "+ribBen);
	        attribs[12]=ribBen;}
	 }
	 
	 if ( !attribs[36].trim().equals("") && !isNumeric(attribs[36].trim())){
		 motif +=","+attribs[36]+ " n est un num exploit";
	 }	 
	 
	 
	 if (motif.equals("")) {
		 return attribs;
	 } else{
		 createErrorMigration(codStrcStrc,attribs, motif);
		 return null;
	 }
	 
	
	
}
public void clearDb() {
	System.out.println("Begining initializing db ...");
	System.out.println("Deleting  Old migration data...");
	//rejetDao.initDbForMigration();
	System.out.println("Ending initializing db ...");
	
}



// print etat migration
public void printStatMigration(String codStrcStrc) throws IOException {

    String pathReportCheque = "D:\\jasper\\stat_mig_chq.jrxml";
    String pathReportPreavis = "D:\\jasper\\stat_mig_preavis.jrxml";
    String pathReportPapillon = "D:\\jasper\\stat_mig_papillon.jrxml";
    String pathReportCnp = "D:\\jasper\\stat_mig_cnp.jrxml";
    String pathReportError = "D:\\jasper\\stat_mig_error.jrxml";
    String pathReportMig = "D:\\jasper\\stat_mig_mig.jrxml";
    
    
    // Util class must be static
    Util util= new Util();
    
    Map params = new HashMap();
   
	params.put("cod_strc_strc", codStrcStrc);
	params.put("P_PATH","D:\\jasper\\" );
	
	
	String dirAge = "";
	
	System.out.println("Directory :"+dirAge );
	String  pdfChq=dirAge+"\\stat_mig_chq.pdf";
	String  pdfPre=dirAge+"\\stat_mig_preavis.pdf";
	String  pdfPap=dirAge+"\\stat_mig_papillon.pdf";
	String  pdfCnp=dirAge+"\\stat_mig_cnp.pdf";
	String  pdfError=dirAge+"\\stat_mig_error.pdf";
	String  pdfMig=dirAge+"\\stat_mig_mig.pdf";	
	
	
	util.editJasper(pathReportCheque,pdfChq,params);
	Util.ShowPDF(pdfChq);
	
	
	util.editJasper(pathReportPreavis,pdfPre,params);
	Util.ShowPDF(pdfPre);
	
	util.editJasper(pathReportPapillon,pdfPap,params);
	Util.ShowPDF(pdfPap);
	
	util.editJasper(pathReportCnp,pdfCnp,params);
	Util.ShowPDF(pdfCnp);
	
	util.editJasper(pathReportError,pdfError,params);
	Util.ShowPDF(pdfError);
	
	util.editJasper(pathReportMig,pdfMig,params);
	Util.ShowPDF(pdfMig);


}



}
