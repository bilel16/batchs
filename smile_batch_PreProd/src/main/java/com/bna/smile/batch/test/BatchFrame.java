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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;

public class BatchFrame extends JFrame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table = new JTable();
	private List<SwingInfoVo> infoVos = new ArrayList<SwingInfoVo>();
	private JPanel description_panel = new JPanel();
	private ButtonGroup buttonGroup = new ButtonGroup();
	public BatchFrame frame_Instance = null;
	public Batch frame_Batch = null;
	private JRadioButton radio_prelev;
	private JRadioButton radio_cheque;
	private JRadioButton radio_effet;
	private JButton btnExcuter;
	private JLabel end_exec_label;
	private JButton btnConsultation;
	private JButton btnPosition;
	public static int POSITION_COMPENSATION = 1;
	public static int INSERT_COMPENSATION = 0;
	private JLabel infoEncours = new JLabel("");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					BatchFrame frame = new BatchFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void updateInfo(String msg) {
		infoEncours.setText(msg);
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
	public BatchFrame() {

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
		panel.setBounds(10, 50, 474, 369);
		contentPane.add(panel);
		panel.setLayout(null);

		radio_cheque = new JRadioButton("Ch\u00E8que");
		buttonGroup.add(radio_cheque);
		radio_cheque.setBounds(35, 29, 89, 23);
		panel.add(radio_cheque);

		radio_effet = new JRadioButton("Lettre de change");
		buttonGroup.add(radio_effet);
		radio_effet.setBounds(35, 56, 140, 23);
		panel.add(radio_effet);
		frame_Instance = this;
		infoEncours.setForeground(Color.RED);
		infoEncours.setFont(new Font("Tahoma", Font.BOLD, 12));
		infoEncours.setBounds(494, 11, 374, 27);
		contentPane.add(infoEncours);

		btnExcuter = new JButton("Lecture Fichier");
		btnExcuter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getEnd_exec_label().setText("");
				infoVos.clear();
				addOrUpdateEtat(null);
				infoEncours.setText("");
				contentPane.updateUI();
				if (!radio_effet.isSelected() && !radio_cheque.isSelected() && !radio_prelev.isSelected()) {
					JOptionPane.showMessageDialog(frame_Instance, "Veuillez sélectionner avant d'executer");
				}
				if (radio_effet.isSelected()) {
					btnExcuter.setEnabled(false);
					btnPosition.setEnabled(false);

					new Thread(new MoulinetteCompensationEffetTest(frame_Instance)).start();

				} else if (radio_prelev.isSelected()) {
					btnExcuter.setEnabled(false);
					new Thread(new MoulinetteInsertionPrelevementsDomiciliationTest(frame_Instance)).start();

				} else if (radio_cheque.isSelected()) {
					btnExcuter.setEnabled(false);
					new Thread(new MoulinetteChequeTest(BatchFrame.INSERT_COMPENSATION, frame_Batch)).start();

				}

			}
		});
		btnExcuter.setBounds(179, 189, 130, 23);
		panel.add(btnExcuter);

		radio_prelev = new JRadioButton("Pr\u00E9l\u00E8vement");
		radio_prelev.setSelected(true);
		buttonGroup.add(radio_prelev);
		radio_prelev.setBounds(35, 94, 109, 23);
		panel.add(radio_prelev);

		end_exec_label = new JLabel("");
		end_exec_label.setFont(new Font("Tahoma", Font.BOLD, 11));
		end_exec_label.setForeground(Color.RED);
		end_exec_label.setBounds(76, 223, 332, 14);
		panel.add(end_exec_label);

		btnConsultation = new JButton("Consultation");
		btnConsultation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Consultation frame = new Consultation();
				frame.setVisible(true);
			}
		});
		btnConsultation.setBounds(21, 189, 109, 23);
		panel.add(btnConsultation);

		btnPosition = new JButton("Position");
		btnPosition.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getEnd_exec_label().setText("");
				infoVos.clear();
				addOrUpdateEtat(null);
				infoEncours.setText("");
				contentPane.updateUI();
				if (!radio_effet.isSelected() && !radio_cheque.isSelected() && !radio_prelev.isSelected()) {
					JOptionPane.showMessageDialog(frame_Instance, "Veuillez sélectionner avant d'executer");
				}
				if (radio_effet.isSelected()) {
					btnExcuter.setEnabled(false);
					btnPosition.setEnabled(false);
					new Thread(new MoulinettePositionEffetTest(frame_Instance)).start();

				} else if (radio_prelev.isSelected()) {
					btnExcuter.setEnabled(false);
					btnPosition.setEnabled(false);
					new Thread(new MoulinettePositionPrelevementsDomiciliationTest(frame_Instance)).start();

				} else if (radio_cheque.isSelected()) {
					btnExcuter.setEnabled(false);
					btnPosition.setEnabled(false);
					new Thread(new MoulinetteChequeTest(BatchFrame.POSITION_COMPENSATION, frame_Batch)).start();

				}

			}
		});
		btnPosition.setBounds(319, 189, 89, 23);
		panel.add(btnPosition);

		description_panel = new JPanel();
		description_panel.setBounds(494, 49, 374, 370);
		contentPane.add(description_panel);
		description_panel.setLayout(new BorderLayout(0, 0));

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

	public JButton getBtnPosition() {
		return btnPosition;
	}

	public void setBtnPosition(JButton btnPosition) {
		this.btnPosition = btnPosition;
	}

	public JLabel getInfoEncours() {
		return infoEncours;
	}

	public void setInfoEncours(JLabel infoEncours) {
		this.infoEncours = infoEncours;
	}

	public Batch getFrame_Batch() {
		return frame_Batch;
	}

	public void setFrame_Batch(Batch frame_Batch) {
		this.frame_Batch = frame_Batch;
	}

}
