package com.bna.smile.model.domainetraitementfichier;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

public class TimerBatch  implements Serializable{

   /** * * * * * * * * * * * * * * * * * * * * * * * * * * * 
     * Le traitement s'exécute à des intervalles réguliers *
     * de temps programmables à travers un Timer           *
     * @author JAOUALI Yossri                              *
     * @since 28/10/2010                                   *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * **/

    private static Logger logger = Logger.getLogger(TimerBatch.class);
    long t1 = 0 ,t2 = 0;
    TraitementFile traitementFile = new TraitementFile();
    Util util=new Util();
    String message = "EN EXÉCUTION--DERNIER MESSAGE: " + util.timeForMsg();
    Timer timer = new Timer();
    Parametres parametres = new Parametres();
    
    
    /**
     * Methode permettant de démarrer le Timer
     * @param  
     * @return String
     */
    
    public String lancerBatch() {
    
    final long  DELAY = parametres.getCompteur(); //délais pour "DELAY" ms.
    final  long PERIODE= parametres.getPeriode();  // Traitement se répète chaque "PERIODE" ms
    
    timer.schedule(new TimerTask() {
                    public void run() {
                        try {
                            Calendar now = Calendar.getInstance();
                            t1 = now.getTimeInMillis();
                            String repSource = parametres.getCheminRepertoireSource(); // répertoire source qui contient les fichiers sources reçus par les agences (VP, Virement…)
                            String repTravail = parametres.getCheminRepertoireTravail();  // répertoire contenant les fichiers sources déplacés dans la machine locale afin de les traiter par la suite
                            String repDestinationADT = parametres.getCheminRepertoireDestination(); // répertoire qui stocke les fichiers renommés et défalqués 
                            String repDestinationLocale = parametres.getCheminRepertoireDestinationLocale(); // répertoire ADT contenant les fichiers renommés et défalqués qui seront consommés par l'ADT
                            String repTraitement = parametres.getCheminRepertoireTraitement(); // répertoire contenant les fichiers traités par la moulinette
                            String repTest = parametres.getCheminRepertoireTest(); // répertoire contenant les fichiers traités par la moulinette
                             
                           
                            if ((t1 - t2) >= new Long(PERIODE)) {  
                            
                                System.gc();
                              
                                if (!(new File(repSource).exists() && 
                                      new File(repSource).isDirectory())) {
                                    logger.error("Le chemin" + " " + repSource + " " + "est inexistant");
                                    
                                    JOptionPane.showMessageDialog(null, "Le chemin" + " " + repSource + " " + "est inexistant");
                               
                                } else if (!(new File(repDestinationADT).exists() && 
                                             new File(repDestinationADT).isDirectory())) {
                                    logger.error("Le chemin" + " " + repDestinationADT + " " + "est inexistant");
                                    
                                    JOptionPane.showMessageDialog(null, "Le chemin" + " " + repDestinationADT + " " + "est inexistant");
                                } else {

                                    message = "EN EXÉCUTION--DERNIER MESSAGE: " + util.timeForMsg();
     
                                   // traitementFile.traiterFichiers(repSource, repTravail, repDestinationADT, repDestinationLocale, repTraitement,repTest);
         
                                }
                                t2 = t1;
                            }
                        } catch (Exception e) {
                       
                            JOptionPane.showMessageDialog(null, "Erreur!!!!"+e);
                            logger.error(e);

                        }
                    }
                }, DELAY,1000);
                
        return message;
    }
    
    /**
     * Methode permettant de suspendre le Timer
     * @param  
     * @return
     */
     
    public void stopperBatch() {
        timer.cancel();
    }
}
