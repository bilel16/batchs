package com.bna.smile.batch.test;

import static java.util.concurrent.TimeUnit.MINUTES;

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
import java.util.concurrent.ScheduledFuture;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.log4j.Logger;

import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;

public class BatchEnvoiSmsPowerCardFrame extends JFrame implements Serializable {

	/**
	 * 
	 */
	Logger logger = Logger.getLogger(BatchEnvoiSmsPowerCardFrame.class);
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table = new JTable();
	private List<SwingInfoVo> infoVos = new ArrayList<SwingInfoVo>();
	private JPanel description_panel = new JPanel();
	private ButtonGroup buttonGroup = new ButtonGroup();
	public BatchEnvoiSmsPowerCardFrame frame_Instance = null;
	private JButton btnExcuter;
	private JLabel end_exec_label;
	final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					BatchEnvoiSmsPowerCardFrame frame = new BatchEnvoiSmsPowerCardFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void addOrUpdateEtat(SwingInfoVo vo) {
		boolean found = false;
		try {
			if (infoVos != null) {
				for (int i = 0; i < infoVos.size(); i++) {
					if (vo.getNumeroTelephone().equals(infoVos.get(i).getNumeroTelephone())) {
						infoVos.set(i, vo);
						found = true;
						break;
					}
				}
				if (!found)
					infoVos.add(vo);

				Object[][] etatList = new Object[infoVos.size()][3];

				for (int i = 0; i < infoVos.size(); i++) {
					etatList[i][0] = infoVos.get(i).getNumeroTelephone();
					etatList[i][1] = infoVos.get(i).getEtat();
					etatList[i][2] = infoVos.get(i).getInfo();
				}
				String[] columnNames = { "Mobile", "ETAT", "SMS" };
				DefaultTableModel model = new DefaultTableModel(etatList, columnNames);

				table = new JTable(model);
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				TableColumn col1 = table.getColumnModel().getColumn(0);
				col1.setPreferredWidth(100);
				TableColumn col2 = table.getColumnModel().getColumn(1);
				col2.setPreferredWidth(200);
				TableColumn col3 = table.getColumnModel().getColumn(2);
				col3.setPreferredWidth(500);

				table.removeAll();
				description_panel.removeAll();
				table.setEnabled(false);
				description_panel.setLayout(new BorderLayout());
				description_panel.add(new JScrollPane(table), BorderLayout.CENTER);
				contentPane.add(description_panel);
				contentPane.updateUI();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public BatchEnvoiSmsPowerCardFrame() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy' 'HH:mm:ss");
		
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

				final Runnable maTache = new Runnable() {

					public void run() {
						logger.info("-----HEURE DEBUT TACHE  : "+format.format(new Date())+" -----");
						new Thread(new MoulinetteEnvoiSMSPowerCardTest(frame_Instance)).start();
						logger.info("-----HEURE FIN TACHE    : "+format.format(new Date())+" -----");
					}

				};
				ScheduledFuture<?> maTacheFuture = executor.scheduleAtFixedRate(maTache, 0, 30, MINUTES);
				Runtime.getRuntime().addShutdownHook(new Thread() {
					public void run() {
					executor.shutdown();
					}
					});

				

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
		btnExcuter.setBounds(122, 147, 89, 23);
		panel.add(btnExcuter);

		end_exec_label = new JLabel("");
		end_exec_label.setFont(new Font("Tahoma", Font.BOLD, 11));
		end_exec_label.setForeground(Color.RED);
		end_exec_label.setBounds(20, 223, 332, 14);
		panel.add(end_exec_label);

		JLabel lblMoulinetteDenvoiSms = new JLabel("Moulinette d'envoi SMS PowerCard ");
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

}
