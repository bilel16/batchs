package com.bna.smile.batch.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class BatchFrameMoneyGram extends JFrame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table = new JTable();
	private List<SwingInfoVo> infoVos = new ArrayList<SwingInfoVo>();
	private JPanel description_panel = new JPanel();
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnLancer;
	public BatchFrameMoneyGram frame_Instance = null;
	private JLabel infolabel = new JLabel("");

	
	public JLabel getInfolabel() {
		return infolabel;
	}

	
	public void setInfolabel(JLabel infolabel) {
		this.infolabel = infolabel;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					BatchFrameMoneyGram frame = new BatchFrameMoneyGram();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void addOrUpdateEtat(SwingInfoVo vo) {
		boolean found = false;
		for (int i = 0; i < infoVos.size(); i++) {
			if (vo.getStructure().equals(infoVos.get(i).getStructure())) {
				infoVos.set(i, vo);
				found = true;
				break;
			}
		}
		if (!found)
			infoVos.add(vo);

		Object[][] etatList = new Object[infoVos.size()][3];

		for (int i = 0; i < infoVos.size(); i++) {
			etatList[i][0] = infoVos.get(i).getStructure();
			etatList[i][1] = infoVos.get(i).getEtat();
			etatList[i][2] = infoVos.get(i).getDateComptable();

		}
		String[] columnNames = { "Structure", "Etat d'exécution", "Date Comptable" };
		DefaultTableModel model = new DefaultTableModel(etatList, columnNames);

		table = new JTable(model);
		table.removeAll();
		description_panel.removeAll();
		table.setEnabled(false);
		description_panel.setLayout(new BorderLayout());
		description_panel.add(new JScrollPane(table), BorderLayout.CENTER);
		contentPane.add(description_panel);
		contentPane.updateUI();

	}

	/**
	 * Create the frame.
	 */
	public BatchFrameMoneyGram() {

		setTitle("Batch MoneyGram");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		infolabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		infolabel.setForeground(Color.RED);
		infolabel.setBounds(40, 90, 400, 14);
		
		contentPane.add(infolabel);
		
		
		
		
		
		contentPane.add(infolabel);
		Image icon = Toolkit.getDefaultToolkit().getImage("./images/BNA.jpg");
		this.setIconImage(icon);
		setResizable(false);
		frame_Instance = this;
		btnLancer = new JButton("Lancer");
		btnLancer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLancer.setEnabled(false);
				new Thread(new MoulinetteEnvoisMoneyGramTest(frame_Instance)).start();
			}
		});
		btnLancer.setBounds(175, 49, 89, 23);
		contentPane.add(btnLancer);

	}

	

	public void setInfoVos(List<SwingInfoVo> infoVos) {
		this.infoVos = infoVos;
	}

	public List<SwingInfoVo> getInfoVos() {
		return infoVos;
	}
}
