package com.bna.smile.batch.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;

public class BatchFrameRestoreFile extends JFrame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table = new JTable();
	private List<SwingInfoVo> infoVos = new ArrayList<SwingInfoVo>();
	private JPanel description_panel = new JPanel();
	public BatchFrameRestoreFile frame_Instance = null;
	private JButton btnExcuter;
	private JLabel end_exec_label;
	public static int POSITION_COMPENSATION = 1;
	public static int INSERT_COMPENSATION = 0;
	private JTextField txt_strc;
	
	
	private JTextField txt_dateComptable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					BatchFrameRestoreFile frame = new BatchFrameRestoreFile();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void updateInfo(String msg) {
		end_exec_label.setText(msg);
		contentPane.updateUI();
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
		if (!found && vo != null)
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
	public BatchFrameRestoreFile() {

		setTitle("Batch T\u00E9l\u00E9compensation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 907, 469);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Image icon = Toolkit.getDefaultToolkit().getImage("./images/BNA.jpg");
		this.setIconImage(icon);
		setResizable(false);
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 49, 858, 369);
		contentPane.add(panel);
		panel.setLayout(null);
		frame_Instance = this;
		
		btnExcuter = new JButton("Generer Fichiers");
		btnExcuter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getEnd_exec_label().setText("");
				
				contentPane.updateUI();
				
				if (getEnd_exec_label().getText().isEmpty()) {
					btnExcuter.setEnabled(false);

					new Thread(new MoulinetteRestoreFilesTest(frame_Instance)).start();

				

			}}
		});
		btnExcuter.setBounds(33, 189, 130, 23);
		panel.add(btnExcuter);

		end_exec_label = new JLabel("");
		end_exec_label.setFont(new Font("Tahoma", Font.BOLD, 11));
		end_exec_label.setForeground(Color.RED);
		end_exec_label.setBounds(193, 189, 332, 23);
		panel.add(end_exec_label);
		
		txt_strc = new JTextField();
		txt_strc.setBounds(143, 41, 130, 20);
		panel.add(txt_strc);
		txt_strc.setColumns(10);
		
		JLabel lblStructure = new JLabel("Structure :");
		lblStructure.setBounds(10, 44, 74, 14);
		panel.add(lblStructure);
		
		JLabel lblDateComptable = new JLabel("Date Comptable :");
		lblDateComptable.setBounds(10, 75, 123, 14);
		panel.add(lblDateComptable);
		
		txt_dateComptable = new JTextField();
		txt_dateComptable.setColumns(10);
		txt_dateComptable.setBounds(143, 72, 130, 20);
		panel.add(txt_dateComptable);
		
	

	}

	public JButton getBtnExcuter() {
		return btnExcuter;
	}

	public void setBtnExcuter(JButton btnExcuter) {
		this.btnExcuter = btnExcuter;
	}

	public void setInfoVos(List<SwingInfoVo> infoVos) {
		this.infoVos = infoVos;
	}

	public List<SwingInfoVo> getInfoVos() {
		return infoVos;
	}

	public void setEnd_exec_label(JLabel end_exec_label) {
		this.end_exec_label = end_exec_label;
	}

	public JLabel getEnd_exec_label() {
		return end_exec_label;
	}
	public JTextField getTxt_strc() {
		return txt_strc;
	}
	
	public void setTxt_strc(JTextField txt_strc) {
		this.txt_strc = txt_strc;
	}
	
	public JTextField getTxt_dateComptable() {
		return txt_dateComptable;
	}
	
	public void setTxt_dateComptable(JTextField txt_dateComptable) {
		this.txt_dateComptable = txt_dateComptable;
	}

	
}
