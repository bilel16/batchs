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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.log4j.Logger;

import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;

public class BatchAFBFrame extends JFrame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(BatchAFBFrame.class);
	private JPanel contentPane;
	private JTable table = new JTable();
	private List<SwingInfoVo> infoVos = new ArrayList<SwingInfoVo>();
	private JPanel description_panel = new JPanel();
	private ButtonGroup buttonGroup = new ButtonGroup();
	public BatchAFBFrame frame_Instance = null;
	private JRadioButton radio_AFB;
	private JButton btnExcuter;
	private JLabel end_exec_label;
	private JTextField textDateDebut;
	private JTextField textDateFin;
	private JTextField textNumSociete;
	final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					BatchAFBFrame frame = new BatchAFBFrame();
					frame.setVisible(true);
					new Thread(new MoulinetteAFBTest(frame)).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void addOrUpdateEtat(SwingInfoVo vo) {
		boolean found = false;
		for (int i = 0; i < infoVos.size(); i++) {
			if (vo.getNumSocieteAFB().equals(infoVos.get(i).getNumSocieteAFB())) {
				infoVos.set(i, vo);
				found = true;
				break;
			}
		}
		if (!found)
			infoVos.add(vo);

		Object[][] etatList = new Object[infoVos.size()][4];

		for (int i = 0; i < infoVos.size(); i++) {
			etatList[i][0] = infoVos.get(i).getNomSocieteAFB();
			etatList[i][1] = infoVos.get(i).getEtat();
			etatList[i][2] = infoVos.get(i).getDateComptable();
			etatList[i][3] = infoVos.get(i).getInfo();
		}
		String[] columnNames = { "Société", "Etat d'exécution", "Date Comptable", "Statistique" };
		DefaultTableModel model = new DefaultTableModel(etatList, columnNames);

		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumn col1 = table.getColumnModel().getColumn(0);
		col1.setPreferredWidth(100);

		TableColumn col2 = table.getColumnModel().getColumn(1);
		col2.setPreferredWidth(150);
		TableColumn col3 = table.getColumnModel().getColumn(2);
		col3.setPreferredWidth(120);
		TableColumn col4 = table.getColumnModel().getColumn(3);
		col4.setPreferredWidth(1200);
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
	public BatchAFBFrame() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy' 'HH:mm:ss");
		setTitle("Batch AFB");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Image icon = Toolkit.getDefaultToolkit().getImage("./images/BNA.jpg");
		this.setIconImage(icon);
		setResizable(false);
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 50, 350, 369);
		contentPane.add(panel);
		panel.setLayout(null);

		frame_Instance = this;
		btnExcuter = new JButton("Lancer");
		btnExcuter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (radio_AFB.isSelected()) {
					btnExcuter.setEnabled(false);
					end_exec_label.setText("Lacement du Batch AFB");

					// final Runnable maTache = new Runnable() {

					// public void run() {
					logger.info("-----HEURE DEBUT TACHE  : " + format.format(new Date()) + " -----");
					new Thread(new MoulinetteAFBTest(frame_Instance)).start();
					logger.info("-----HEURE FIN TACHE    : " + format.format(new Date()) + " -----");
					// }

					// };
					// ScheduledFuture<?> maTacheFuture = executor.scheduleAtFixedRate(maTache, 0, 24, HOURS);
					// Runtime.getRuntime().addShutdownHook(new Thread() {
					// public void run() {
					// executor.shutdown();
					// }
					// });
					/// new Thread(new MoulinetteAFBTest(frame_Instance)).start();

				}

			}
		});

		Date dateComptable = new Date();
		try {

			Calendar c = new GregorianCalendar();
			c.setTime(dateComptable);
			c.add(Calendar.DAY_OF_MONTH, -1);
			dateComptable = c.getTime();

		} catch (Exception e1) {

			e1.printStackTrace();

			dateComptable = new Date();
		}
		btnExcuter.setBounds(90, 189, 89, 23);
		panel.add(btnExcuter);

		radio_AFB = new JRadioButton("AFB");
		radio_AFB.setSelected(true);
		buttonGroup.add(radio_AFB);
		radio_AFB.setBounds(32, 64, 109, 23);
		panel.add(radio_AFB);

		end_exec_label = new JLabel("");
		end_exec_label.setFont(new Font("Tahoma", Font.BOLD, 11));
		end_exec_label.setForeground(Color.RED);
		end_exec_label.setBounds(20, 223, 332, 14);
		panel.add(end_exec_label);

		JLabel lblPriode = new JLabel("Soci\u00E9t\u00E9 :");
		lblPriode.setBounds(10, 101, 73, 23);
		panel.add(lblPriode);

		textDateDebut = new JTextField();
		textDateDebut.setBounds(93, 144, 86, 20);
		textDateDebut.setText(dateFormat.format(dateComptable));
		panel.add(textDateDebut);
		textDateDebut.setColumns(10);

		textDateFin = new JTextField();
		textDateFin.setBounds(215, 144, 86, 20);
		textDateFin.setText(dateFormat.format(dateComptable));
		panel.add(textDateFin);
		textDateFin.setColumns(10);

		JLabel label = new JLabel("\u00E0");
		label.setBounds(192, 147, 23, 14);
		panel.add(label);

		JLabel label_1 = new JLabel("P\u00E9riode  du :");
		label_1.setBounds(10, 147, 73, 23);
		panel.add(label_1);

		textNumSociete = new JTextField();
		textNumSociete.setText("99");
		textNumSociete.setBounds(93, 102, 86, 20);
		panel.add(textNumSociete);
		textNumSociete.setColumns(10);

		description_panel = new JPanel();
		description_panel.setBounds(370, 49, 600, 370);
		contentPane.add(description_panel);
		description_panel.setLayout(new BorderLayout(0, 0));

	}

	/******************************/

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

	public void setTextDateDebut(JTextField textDateDebut) {
		this.textDateDebut = textDateDebut;
	}

	public JTextField getTextDateDebut() {
		return textDateDebut;
	}

	public void setTextDateFin(JTextField textDateFin) {
		this.textDateFin = textDateFin;
	}

	public JTextField getTextDateFin() {
		return textDateFin;
	}

	public void setTextNumSociete(JTextField textNumSociete) {
		this.textNumSociete = textNumSociete;
	}

	public JTextField getTextNumSociete() {
		return textNumSociete;
	}
}
