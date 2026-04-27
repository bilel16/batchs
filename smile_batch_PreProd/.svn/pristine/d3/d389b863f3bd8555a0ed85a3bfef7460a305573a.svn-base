package com.bna.smile.batch.test;

import com.bna.commun.util.DateHandler;
import com.bna.smile.model.domainetraitementfichier.Parametres;
import com.bna.smile.model.domainetraitementfichier.Util;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.text.DateFormatter;

public class EditEtatComp {

  /** * * * * * * * * * * * * * * * * * * *
    * Fenêtre Edition Etat Compensation:  *
    * Chèque,domiciliation,effets et prélèvement * 
    * @author JAOUALI Yossri              *
    * @since 28/10/2010                   *
    * * * * * * * * * * * * * * * * * * * **/
    JFrame fenetre;
    JTabbedPane jtp;
    JLabel label;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    JPanel pane1;
    JPanel pane2;
    JButton button1;
    JButton button2;
    JButton button3;
    Image img;
    JFormattedTextField dateField = null;
    Parametres parametres = new Parametres();
    String pathRepComp = null;
    String pathFileInRep;
    Util util=new Util();
    public EditEtatComp() {

        fenetre = new JFrame("Edition Compensation");
        img = Toolkit.getDefaultToolkit().getImage("./images/BNA.jpg");
        fenetre.setIconImage(img);
        pane1 = new JPanel();
        pane2 = new JPanel();

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        DateFormatter df = new DateFormatter(format);
        dateField = new JFormattedTextField(df);
        dateField.setPreferredSize(new Dimension(100, 20));
        dateField.setText(format.format(new Date()));

        button1 = new JButton("Editer Compensation Chèque");
        button1.addActionListener(new RechercherChq());
        button2 = new JButton("Editer Compensation Prélèvement");
        button2.addActionListener(new RechercherPrel());
        button3 = new JButton("Editer Compensation Effet");
        button3.addActionListener(new RechercherEffet());
        label2 = new JLabel("Date Télécompensation (jj/mm/aaaa)");

        pane1.add(label2, BorderLayout.NORTH);
        pane1.add(dateField, BorderLayout.NORTH);
        pane1.add(button1, BorderLayout.CENTER);
        pane1.add(button2, BorderLayout.CENTER);
        pane1.add(button3, BorderLayout.CENTER);
        pane1.setBackground(Color.WHITE);

        jtp = new JTabbedPane();
        jtp.setForeground(Color.GREEN);
        jtp.setBackground(Color.YELLOW);

        fenetre.getContentPane().add(pane1, BorderLayout.CENTER);
        fenetre.setBounds(350, 350, 500, 250);
        fenetre.setResizable(false);
        fenetre.setVisible(true);
    }

        
    public void traiter(String date,String valeur) {

        String pathReportCheque = "./jasper/Etat_Compensation_Cheque.jrxml";
        String pathReportPrel= "./jasper/Etat_Compensation_Domiciliation.jrxml";
        String pathReportEffet= "./jasper/Etat_Compensation_Effet.jrxml";
        DateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
        Map params = new HashMap();
       
    try {
        params.put("P_DATE_JOURNEE", date);
        pathRepComp = parametres.getTypeValeur(String.valueOf("Chemin répertoire génération PDF"));
        
        if(valeur.equals("chèque")){
        pathFileInRep=pathRepComp+"\\Compensation_Cheque" + formatter.format(DateHandler.strToDate(date)) + ".pdf";
        util.editJasper(pathReportCheque,pathFileInRep,params);
        }
        else if(valeur.equals("prélèvement")){
        pathFileInRep=pathRepComp+"\\Compensation_Cheque" + formatter.format(DateHandler.strToDate(date)) + ".pdf";
        util.editJasper(pathReportPrel,pathFileInRep,params);
        }     
        else if(valeur.equals("effet")){
        pathFileInRep=pathRepComp+"\\Compensation_Effet" + formatter.format(DateHandler.strToDate(date)) + ".pdf";
        util.editJasper(pathReportEffet,pathFileInRep,params);
        } 
        util.ShowPDF(pathFileInRep);
    } 
    catch (InvalidPropertiesFormatException e1) {

    e1.printStackTrace();
    } catch (IOException e1) {

    e1.printStackTrace();
    }
    }

    public class RechercherChq implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            traiter(dateField.getText(),"chèque");
        }
    }
    public class RechercherPrel implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            traiter(dateField.getText(),"prélèvement");
        }
    }
    public class RechercherEffet implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            traiter(dateField.getText(),"effet");
        }
    }
}

