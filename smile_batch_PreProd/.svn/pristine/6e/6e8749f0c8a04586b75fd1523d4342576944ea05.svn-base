package com.bna.smile.batch.test;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.batch.moulinette.MoulinetteTraitementFichier;
import com.bna.smile.model.domainetraitementfichier.TraitementFile;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class TeleCompensation24H implements Serializable{

  /** * * * * * * * * * * * * * * * * * * * 
    * Fenêtre principal de l'application  *
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
    Image img;
    MoulinetteTraitementFichier moulinetteTraitementFichier;
    TraitementFile traitementFile = new TraitementFile();
    
    public TeleCompensation24H() {
   
        JFrame.setDefaultLookAndFeelDecorated(true);
        fenetre = new JFrame("Télécompensation 24H");
        img = Toolkit.getDefaultToolkit().getImage("./images/BNA.jpg");
        fenetre.setIconImage(img);
        pane1 = new JPanel();
        pane2 = new JPanel();
        button1 = new JButton("Moulinette TéléCompensation");
        button2 = new JButton("Editer Compensation émise aux agences");
        button1.addActionListener(new LancerFenMoul());
        button2.addActionListener(new LancerFenEditComp());
        label3 = new JLabel("TÉLÉCOMPENSATION 24H");
        label3.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 25));
        pane1.add(label3, BorderLayout.NORTH);
        pane1.add(button1, BorderLayout.CENTER);
        pane1.add(button2, BorderLayout.CENTER);
        pane1.setBackground(Color.WHITE);
        jtp = new JTabbedPane();
        jtp.setForeground(Color.GREEN);
        jtp.setBackground(Color.YELLOW);
        fenetre.getContentPane().add(pane1, BorderLayout.CENTER);
        fenetre.setBounds(350, 350, 500, 250);
        fenetre.setResizable(false);
        fenetre.setVisible(true);
    }
    public  class LancerFenMoul implements ActionListener {
          public void actionPerformed(ActionEvent event) {
              new BatchTelecompensation(); 
          }
      }
    public  class LancerFenEditComp implements ActionListener {
          public void actionPerformed(ActionEvent event) {
              new EditEtatComp();
          }
      }
    public static void main(String[] args) {

        String[] path = { "./config/applicationContext-serviceTelecompensation.xml" };

        ApplicationContext springContext = new FileSystemXmlApplicationContext(path);
        Context context = (Context)ContextFactory.initContext("./config/applicationContext-1Spring.xml");
        context.setSpringContext(springContext);
        ContextHandler.setContext(context);
        new TeleCompensation24H();
    }
}
