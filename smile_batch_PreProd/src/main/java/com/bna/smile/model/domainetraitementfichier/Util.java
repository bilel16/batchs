package com.bna.smile.model.domainetraitementfichier;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;

import com.oxia.fwk.context.Context;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import java.sql.Connection;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Map;
import java.util.Scanner;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;


public class Util implements Serializable {


    Context context = ContextHandler.getContext();
    DataSource dataSource = (DataSource)context.getBean("dataSource");
    Connection co = null;


    /**
     * Methode permettant de lister un répertoire
     * @param  cheminRepSource:String
     * @return Tableau de type String[]
     */
    public

    String[] listerRepertoire(String cheminRepSource) {

        File repSource = new File(cheminRepSource);
        String[] list = 
            repSource.list(); // on crée une liste des noms des fichiers d'un répertoire

        return list;
    }

    /**
     * Methode permettant de copier un fichier dans un autre répertoire
     * @param  SourceFile:String , NewDestFile:String
     */
    public int copy(String SourceFile, String NewDestFile) {

        Scanner inputFile = null;
        PrintWriter outputFile = null;
        File nomFichier = null;
        int ret=0;
        try {

            nomFichier = 
                    new File(SourceFile); // je met SourceFile dans nomFichier
            inputFile = 
                    new Scanner(nomFichier); //je met nomFichier dans inputfile

            /*inputfile est initialisé,il est lié au fichier externe SourceFile et est ouvert en lecture*/

            outputFile = 
                    new PrintWriter(NewDestFile); //ouvre le fichier NewDestFile

            //écrit dans le fichier nexDestFile le contenu du fichier source

            while (inputFile.hasNext()) //regarde si la ligne suivante existe
            {
                outputFile.println(inputFile.nextLine()); //inputfile.nextline() voir scanner et file au debut de la méthode
            }
            ret=1;
            
        } catch (Exception e) {
           
            e.printStackTrace();

        } finally {
            outputFile.close(); //ferme le fichier en écriture
            inputFile.close(); //inputfile est fermé
            return ret;
        }
    }

    /**
     * Methode permettant de supprimer un répertoire
     * @param  file:File
     * @return boolean
     */
    public

    boolean deleteDirectory(File file) {

        boolean resultat = true;

        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    resultat &= deleteDirectory(files[i]);
                } else {
                    resultat &= files[i].delete();
                }
            }
        }
        resultat &= file.delete();
        return (resultat);
    }


    /**
     * Methode permettant de donner un format de temps de type hhmmss
     * @param 
     * @return String
     */
    public

    String timeForNameFile() {

        Calendar calendar = Calendar.getInstance();
        String hours = 
            StrHandler.lpad(String.valueOf(calendar.getTime().getHours()), '0', 
                            2);
        String minutes = 
            StrHandler.lpad(String.valueOf(calendar.getTime().getMinutes()), 
                            '0', 2);
        String seconds = 
            StrHandler.lpad(String.valueOf(calendar.getTime().getSeconds()), 
                            '0', 2);

        return hours + minutes + seconds;
    }

    /**
     * Methode permettant de donner un format de temps de type hh:mm:ss
     * @param 
     * @return String
     */
    public

    String timeForMsg() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        return sdf.format(calendar.getTime());
    }

    /**
     * Methode permettant de tester la validité d'une date
     * @param annee:int ,mois:int et jour:int
     * @return boolean
     */
    public

    boolean testDateValide(int annee, int mois, int jour) {
        Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.set(annee, mois, jour);
        try {
            c.getTime();
        } catch (IllegalArgumentException iAE) {
            iAE.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Methode permettant d'afficher un fichier PDF à l'écran
     * @param  pathFile:String
     * @return 
     */
    public

    void ShowPDF(String pathFile) {

        Process p;
        try {
            p = 
  Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + 
                            pathFile);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void editJasper(String pathReport, String pathFileGen, Map map) {
        JasperReport jasperReport;
        JasperPrint jasperPrint;

        try {
            co = dataSource.getConnection();
            jasperReport = JasperCompileManager.compileReport(pathReport);
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, co);
            JasperExportManager.exportReportToPdfFile(jasperPrint, 
                                                      pathFileGen);
        } catch (SQLException e) {

        } catch (JRException e) {

        }
    }
}
