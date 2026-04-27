package com.bna.smile.batch.test;


import com.bna.smile.batch.moulinette.MoulinetteTraitementFichier;
import com.bna.smile.model.domainetraitementfichier.Parametres;
import com.bna.smile.model.domainetraitementfichier.TraitementFile;
import com.bna.smile.model.domainetraitementfichier.Util;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.Timer;


public class BatchTelecompensation implements Serializable {


   /** * * * * * * * * * * * * * * * * * * * *
     * Fenêtre Batch Traitement des fichiers *
     * Telecompensations:VP,10,30,81...      *
     * @author JAOUALI Yossri                *
     * @since 28/10/2010                     *
     * * * * * * * * * * * * * * * * * * * * **/

    JFrame fenetre;
    JTabbedPane jtp;
    JLabel label;
    JLabel label2;
    //JLabel label3;
    JLabel label4;
    JPanel pane1;
    JPanel pane2;
    JButton button1;
    //JButton button2;
    JButton button3;
    JButton button4;
    Image img;
    MoulinetteTraitementFichier moulinetteTraitementFichier;
    TraitementFile traitementFile = new TraitementFile();
    Timer timer2 = new Timer(1000, new MessageListenerTF());
    Util util;
    Parametres parametres;

    public BatchTelecompensation() {
        fenetre = new JFrame("Batchs Telecompensation");
        img = Toolkit.getDefaultToolkit().getImage("./images/BNA.jpg");
        fenetre.setIconImage(img);
        pane1 = new JPanel();
        pane2 = new JPanel();
        button1 = new JButton("Initialiser Repertoires");
        button1.addActionListener(new InitialiserRepertoires());
        // button2 = new JButton("Arrêter T.A");
        //button2.addActionListener(new StopListenerAccuse());
        // button2.setEnabled(false);
        button3 = new JButton("Demarrer T.F");
        button3.addActionListener(new LancerListenerTraiterFile());
        button4 = new JButton("Arrêter T.F");
        button4.addActionListener(new StopListenerTraiterFile());
        button4.setEnabled(false);
        label = new JLabel();
        label2 = new JLabel();
        //label3 = new JLabel("BATCH TRAITEMENT ACCUSeS");
        label4 = new JLabel("BATCH TRAITEMENT FICHIERS");
        label.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 22));
        label2.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 22));
        //label3.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 25));
        label4.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 25));
        // pane1.add(label3, BorderLayout.NORTH);
        
        // pane1.add(button2, BorderLayout.CENTER);
        pane1.add(label, BorderLayout.SOUTH);
        pane1.setBackground(Color.WHITE);
        pane2.add(label4, BorderLayout.NORTH);
        pane2.add(button3, BorderLayout.CENTER);
        pane2.add(button4, BorderLayout.CENTER);
        pane2.add(label2, BorderLayout.SOUTH);
        pane1.add(button1, BorderLayout.WEST);
        pane2.setBackground(Color.WHITE);
        jtp = new JTabbedPane();
        jtp.setForeground(Color.GREEN);
        jtp.setBackground(Color.YELLOW);

        //jtp.addTab("BATCH TRAITEMENT ACCUSeS", pane1);
        //jtp.addTab("BATCH TRAITEMENT FICHIERS", pane2);
        fenetre.getContentPane().add(pane2, BorderLayout.CENTER);
        fenetre.getContentPane().add(pane1, BorderLayout.SOUTH);
        fenetre.setBounds(350, 350, 500, 250);
        fenetre.setResizable(false);
        fenetre.setVisible(true);
    }

    public class InitialiserRepertoires implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String reponse;
            String message = "Entrer votre mot de passe";
            reponse = JOptionPane.showInputDialog(null, message);
            if (reponse != null) {
                if (reponse.equals("Bna2011")) {
                    parametres = new Parametres();
                    util = new Util();
                    File repertoireTravail=new File(parametres.getCheminRepertoireTravail());
                    File repertoireDestLocale=new File(parametres.getCheminRepertoireDestinationLocale());
                    File repertoireTraitement=new File(parametres.getCheminRepertoireTraitement());
                    util.deleteDirectory(repertoireTravail);
                    util.deleteDirectory(repertoireDestLocale);
                    util.deleteDirectory(repertoireTraitement);
                    repertoireTravail.mkdir();
                    repertoireDestLocale.mkdir();
                    repertoireTraitement.mkdir();

                    JOptionPane.showMessageDialog(null, "Tous les repertoires sont initialises");
                } else {
                    JOptionPane.showMessageDialog(null, "Desole , mot de passe errone");
                }
            }
        }
    }

    public class MessageListenerTF implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            label2.setText(moulinetteTraitementFichier.lancerTimerBatch());
        }
    }

    public class LancerListenerTraiterFile implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            button4.setEnabled(true);
            moulinetteTraitementFichier = new MoulinetteTraitementFichier();
            moulinetteTraitementFichier.lancerTimerBatch();
            timer2.start();
        }

    }

    public class StopListenerTraiterFile implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            moulinetteTraitementFichier.stopperTimerBatch();
            label2.setText(" ");
            timer2.stop();
        }
    }

}

