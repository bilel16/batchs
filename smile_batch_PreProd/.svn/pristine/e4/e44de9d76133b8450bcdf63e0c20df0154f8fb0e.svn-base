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
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import javax.swing.SwingConstants;

public class BatchEnvoiSMSFrame extends JFrame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table = new JTable();
	private List<SwingInfoVo> infoVos = new ArrayList<SwingInfoVo>();
	private JPanel description_panel = new JPanel();
	private ButtonGroup buttonGroup = new ButtonGroup();
	public BatchEnvoiSMSFrame frame_Instance = null;
	private JButton btnExcuter;
	private JLabel end_exec_label;
	private JTextField textDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					BatchEnvoiSMSFrame frame = new BatchEnvoiSMSFrame();
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
			if (vo.getCompteClient().equals(infoVos.get(i).getCompteClient())) {
				infoVos.set(i, vo);
				found = true;
				break;
			}
		}
		if (!found)
			infoVos.add(vo);

		Object[][] etatList = new Object[infoVos.size()][4];

		for (int i = 0; i < infoVos.size(); i++) {
			etatList[i][0] = infoVos.get(i).getCompteClient();
			etatList[i][1] = infoVos.get(i).getNumeroTelephone();
			etatList[i][2] = infoVos.get(i).getDateComptable();
			etatList[i][3] = infoVos.get(i).getInfo();
		}
		String[] columnNames = { "Compte", "Mobile", "Date Comptable", "Statistique" };
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
	public BatchEnvoiSMSFrame() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		setTitle("Batch Envoi SMS");
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

				btnExcuter.setEnabled(false);
				end_exec_label.setText("");
				new Thread(new MoulinetteEnvoiSMSTest(frame_Instance)).start();

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
		btnExcuter.setBounds(122, 189, 89, 23);
		panel.add(btnExcuter);

		end_exec_label = new JLabel("");
		end_exec_label.setFont(new Font("Tahoma", Font.BOLD, 11));
		end_exec_label.setForeground(Color.RED);
		end_exec_label.setBounds(20, 223, 332, 14);
		panel.add(end_exec_label);

		textDate = new JTextField();
		textDate.setBounds(125, 144, 86, 23);
		textDate.setText(dateFormat.format(dateComptable));
		panel.add(textDate);
		textDate.setColumns(10);

		JLabel lblDateDu = new JLabel("Date  du :");
		lblDateDu.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		lblDateDu.setBounds(34, 144, 73, 23);
		panel.add(lblDateDu);

		JLabel lblMoulinetteDenvoiSms = new JLabel("Moulinette d'envoi SMS ");
		lblMoulinetteDenvoiSms.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblMoulinetteDenvoiSms.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoulinetteDenvoiSms.setBounds(20, 54, 301, 23);
		panel.add(lblMoulinetteDenvoiSms);

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

	public JTextField getTextDate() {
		return textDate;
	}

	public void setTextDate(JTextField textDate) {
		this.textDate = textDate;
	}

}
