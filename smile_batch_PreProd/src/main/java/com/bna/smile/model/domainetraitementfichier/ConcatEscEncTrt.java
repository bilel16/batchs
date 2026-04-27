package com.bna.smile.model.domainetraitementfichier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.CompensationVo;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.Util;
import com.bna.smile.model.traitementCompensationRecu.model.Configuration;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ConcatEscEncTrt  extends Traitement {
	
	
	Context context = ContextHandler.getContext();
	CRUDservice crudService = (CRUDservice) context.getBean("crudservice");
	CompensationDAO compensationDAO = (CompensationDAO) context.getBean("compensationDAO");
	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
	String pathTravail = Configuration.getLocalPathLc()+File.separatorChar+"IN"+File.separatorChar;

	File concat=  new File (Configuration.getLocalPathLc()+File.separator+"tmp"+File.separator+"concat");
	File travail=  new File (Configuration.getLocalPathLc()+File.separator+"tmp"+File.separator+"travail");

	


	
	
	@Override
	protected void genCroText(ValueObject arg0) {
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public IValueObject perform(IValueObject arg0) throws Exception {
		List listAgences = compensationDAO.getListAgencesCompensationPilotePFC();
		
		ListOrderedMap ListAg = null;
		
		if ( ! new File(Configuration.getLocalPathLc()+File.separator+"tmp").exists()) 
			new File(Configuration.getLocalPathLc()+File.separator+"tmp").mkdir();
		if(  ! concat.exists())
			concat.mkdir();
		
		if(  ! travail.exists())
			travail.mkdir();

			
			
		if (listAgences != null && listAgences.size() > 0) {
			for (Iterator it1 = listAgences.iterator(); it1.hasNext();) {
				 ListAg = (ListOrderedMap) it1.next();
				if ((ListAg.getValue(0)).toString() != null && (ListAg.getValue(1)).toString() != null) 
				{
					
					final CompensationVo compensationVo=new CompensationVo();
					compensationVo.setDateComptable(DateHandler.strToDate(ListAg.getValue(1).toString()));
					compensationVo.setStrutcure(compensationDAO.findStructure(new Long((ListAg.getValue(0)).toString())));
					String codBct = StrHandler.lpad(compensationVo.getStrutcure().getCodBctStrc(),'0',3);
					
					
					//String dateJour 		 = sdf.format(compensationVo.getDateComptable());
					//String rechercheFileLot1 = "03-"+codBct+"-41-21-0001-"+dateJour+".*"+"-788.ENV";
					String rechercheFileLot1 = "03-"+codBct+"-41-21-0001-"+".*"+"-788.ENV";
					String nameFileLot1 = "";
					String nameFileLot2 = "";

					//String rechercheFileLot2 = "03-"+codBct+"-41-21-0002-"+dateJour+".*"+"-788.ENV";

					SimpleDateFormat timeFileExtractFormat = new SimpleDateFormat("HHmmss");
					String timeFileExtract = timeFileExtractFormat.format(new Date());
					//String newFile = "03-"+codBct+"-41-21-0003-"+dateJour+timeFileExtract+"-788.ENV";
					String newFile = "";
					//String rechercheFileLot3 = "03-"+codBct+"-41-21-0003-"+dateJour+".*"+"-788.ENV"; 

					String firstLineFirstLot ="";
					String secondLineSecondLot ="";
					boolean fileExist = true;
					copyToTravail(codBct);
					File[] listFilesLot1 =getListFilesLot(codBct,"0001") ;
					File[] listFilesLot2 =getListFilesLot(codBct,"0002") ;

					
					
					
			
					
					
					
					
					// recherche file lot 1 
					
					
					for (int j = 0; j < listFilesLot1.length; j++) {
						 nameFileLot1 = listFilesLot1[j].getName();
						String dateFile = nameFileLot1.substring(18,26);
						String rechercheFileLot2 = "03-"+codBct+"-41-21-0002-"+dateFile+".*"+"-788.ENV";
						String rechercheFileLot3 = "03-"+codBct+"-41-21-0002-"+dateFile+".*"+"-788.ENV";
						newFile = "03-"+codBct+"-41-21-0003-"+dateFile+"-"+timeFileExtract+"-788.ENV"; 
						nameFileLot2="";

					// recherche file lot 2
						for (int i = 0; i < listFilesLot2.length; i++) {
							if (listFilesLot2[i].getName().matches(rechercheFileLot2)) {
								
								if (listFilesLot2[i].getName().matches(rechercheFileLot2)) nameFileLot2 = listFilesLot2[i].getName();
							
							}
						}
		
								InputStream ips = new FileInputStream(travail.getPath()+File.separator + nameFileLot1);
								InputStreamReader ipsr = new InputStreamReader(ips);
								BufferedReader br = new BufferedReader(ipsr);
								String line;
								while ((line = br.readLine()) != null && !line.trim().equals("") && !line.equals("FIN") && firstLineFirstLot.equals("")) {
									if ( line.substring(21, 23).equals("11"))
									{
											firstLineFirstLot = line ;

									}
								}
								br.close();
						
					
					
					
					if(!nameFileLot2.equals("")) {
						logger.info("Concatenation  : [" +nameFileLot1+","+nameFileLot2+"]");
						
						InputStream ips1 = new FileInputStream(travail.getPath()+File.separator + nameFileLot2);
						InputStreamReader ipsr1 = new InputStreamReader(ips1);
						BufferedReader br1 = new BufferedReader(ipsr1);
						String line1;
						while ((line1 = br1.readLine()) != null && !line1.trim().equals("") && !line1.equals("FIN") && secondLineSecondLot.equals("")) {
							if ( line1.substring(21, 23).equals("11"))
							{							
								secondLineSecondLot = line1 ;

							}
						}
						br1.close();

				} else {
					firstLineFirstLot="";
					secondLineSecondLot="";
				}
					
			
				
				
						
					
					
					if (!newFile.equals("")&& !nameFileLot2.equals("")) {
						
						writeToFile(pathTravail +newFile,getGlobalLine(firstLineFirstLot, secondLineSecondLot)) ;
						firstLineFirstLot="";
						secondLineSecondLot="";
	
						
					

					
				
							// Adding  lot 1 to file lot 3 


//							try {
								if(!nameFileLot1.equals("") && !nameFileLot2.equals(""))
								importToLot3(nameFileLot1,newFile);
								importToLot3(nameFileLot2,newFile);

							
							
//						} catch(Exception e) {
//							e.printStackTrace();
//							}
//						finally {
//							
//												
//
//					
//						}

						   
						}
						}
					
					//here
					
					

				}
					
					}
					

					

			
				

			
		}		

		


		return null;
	}

	

public File[] getListFilesLot(final String codBct,final String lot) {
	File repSource = new File(travail.getPath()+File.separator );
	return   repSource.listFiles(new FilenameFilter() {
	    public boolean accept(File dir, String name) {
	    	return name.contains("03-"+codBct+"-41-21-"+lot+"-") && name.endsWith("-788.ENV");
	    }
	});

}

public String getGlobalLine(String line1, String line2) {
	
	Long totMnt =Long.valueOf(0);
	Long totNbr =Long.valueOf(0);
	totMnt = Long.valueOf(line1.substring(28, 43))+Long.valueOf(line2.substring(28, 43)) ;
	totNbr = Long.valueOf(line1.substring(43, 53))+Long.valueOf(line2.substring(43, 53)) ;
	return line1.substring(0, 28)+StrHandler.lpad(""+totMnt,'0',15)+StrHandler.lpad(""+totNbr,'0',10)+line1.substring(53) ;
	
}
public  void writeToFile(String file,String line) {
	try {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(file), true));
		bw.write(line);
		bw.newLine();
		bw.close();
	} catch (Exception e) {
		logger.info("Error in writeToFile : " +file);
	}
}

public void importToLot3 (String source , String destination) {
	String line;
	InputStream ips;

	try {
		ips = new FileInputStream(travail.getPath() +File.separator+ source);
		InputStreamReader ipsr = new InputStreamReader(ips);
		BufferedReader br = new BufferedReader(ipsr);

	while ((line = br.readLine()) != null && !line.equals("") && !line.equals("FIN")) {
		if( line.substring(21, 23).equals("21"))
		{
			writeToFile(pathTravail +destination,line) ;

		}

	}

	br.close();
	Util.copy( travail.getPath()+File.separator+source, concat.getPath()+File.separator + source);
	Util.deleteFile( travail.getPath()+File.separator+source);

	
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void copyToTravail (final String codBct) {
	File repSource = new File(pathTravail);
	File[] files =   repSource.listFiles(new FilenameFilter() {
	    public boolean accept(File dir, String name) {
	    	return (name.contains("03-"+codBct+"-41-21-0001")|| name.contains("03-"+codBct+"-41-21-0002")) && name.endsWith("-788.ENV");
	    }
	});
	if(files != null && files.length >0) {
	for (int i = 0 ; i<files.length ; i++) {
		Util.copy(pathTravail+File.separator+files[i].getName(),travail.getPath()+File.separator + files[i].getName());
		Util.deleteFile(pathTravail+files[i].getName());

	}
	}

}



}
