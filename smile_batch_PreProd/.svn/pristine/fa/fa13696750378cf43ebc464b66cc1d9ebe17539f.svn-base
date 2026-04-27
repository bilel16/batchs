package com.bna.smile.batch.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.bna.smile.model.domainecompensation.gestionrejet.traitement.Util;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class Batch extends JFrame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table = new JTable();
	private List<SwingInfoVo> infoVos = new ArrayList<SwingInfoVo>();
	private JPanel description_panel = new JPanel();
	private ButtonGroup buttonGroup = new ButtonGroup();
	public Batch frame_Instance = null;
	private JRadioButton radio_cheque;
	private JButton btnExcuter;
	private JLabel end_exec_label;
	private JLabel label3;
	private JLabel msg;
	private JLabel msgDetail;
	private JLabel msgDetailChq;
	private JLabel msgDetailChq1;
	




	private JButton btnConsultation1;
	private JButton btnForcage;

	private JButton btnConsultation2;
	private JButton btnAgence;


	private JButton btnPosition;
	private JButton btnValidInsertion;

	public static int VALIDINSERT_COMPENSATION = 2;
	public static int INSERT_COMPENSATION_AGENCE = 3;

	public static int POSITION_COMPENSATION = 1;
	public static int INSERT_COMPENSATION = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					Batch frame = new Batch();
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
	public Batch() {
		
		
        String[] path = 
        { "./config/spring.xml", "./config/applicationContext-DAO.xml", "./config/applicationContext-habilitation.xml", 
          "./config/applicationContext-resources.xml", "./config/applicationContext-service.xml", 
          "./config/applicationContext-serviceBatch.xml", 
          "./config/applicatio" +
          "" +
          "" +
          "nContext-serviceHabil.xml", 
          "./config/applicationContext-traitements.xml", "./config/quartz-oxia-calendars.xml", 
          "./config/quartz-oxia-core.xml", "./config/quartz-oxia-jobs.xml", "./config/security.xml",
          "./config/quartz-oxia-listners.xml", "./config/quartz-oxia-triggers.xml" };
        ApplicationContext springContext = new FileSystemXmlApplicationContext(path);
        Context context= (Context) ContextFactory.initContext("./config/applicationContext-1Spring.xml");
        context.setSpringContext(springContext);
        ContextHandler.setContext(context);

        
        
        setDefaultLookAndFeelDecorated(true);

        label3 = new JLabel("COMPENSATION CHEQUE");
        label3.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 25));
        
        
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
		panel.setBackground(Color.WHITE);

		radio_cheque = new JRadioButton("Ch\u00E8que");
		buttonGroup.add(radio_cheque);
		radio_cheque.setBounds(35, 29, 89, 23);
		label3.setBounds(35, 29, 600, 30);
		panel.add(label3);
		//panel.add(radio_cheque);

		frame_Instance = this;
		btnExcuter = new JButton("1-Insertion");
		btnExcuter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getEnd_exec_label().setText("");
				infoVos.clear();
				addOrUpdateEtat(null);
				msgDetail.setVisible(true);
				msgDetailChq.setVisible(true);

				btnExcuter.setEnabled(false);
				btnPosition.setEnabled(false);
				new Thread(new MoulinetteChequeTest(Batch.INSERT_COMPENSATION, frame_Instance)).start();
				//new Thread(new MoulinetteChequeTest(Batch.POSITION_COMPENSATION, frame_Instance)).start();

				btnExcuter.setEnabled(false);
				btnPosition.setEnabled(false);



			}
		});
		btnExcuter.setBounds(10, 120, 200, 30);
		btnExcuter.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
		panel.add(btnExcuter);


		
        msg = new JLabel("");
        msg.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        msg.setBounds(10, 180, 200, 30);
		panel.add(msg);
		
		
		
        msgDetail = new JLabel("Detail :");
        msgDetail.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        msgDetail.setBounds(10, 230, 200, 30);
		panel.add(msgDetail);
		msgDetail.setVisible(false);
		
		
		msgDetailChq = new JLabel("");
        msgDetailChq.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
        msgDetailChq.setForeground(Color.BLUE);
        msgDetailChq.setBounds(10, 250, 600, 30);
		panel.add(msgDetailChq);

		

		end_exec_label = new JLabel("");
		end_exec_label.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 25));
		end_exec_label.setForeground(Color.RED);
		end_exec_label.setBounds(10, 200, 400, 30);
		panel.add(end_exec_label);

		btnConsultation1 = new JButton("Etat Insertion");
		btnConsultation2 = new JButton("Etat Position");
		btnAgence = new JButton("Forcer Agence");


		btnConsultation1.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent e) {
				//Consultation frame = new Consultation();
			     File currentDirectory = new File(new File(".").getAbsolutePath());

				try {
					try {
						print("INSERTION");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					CompensationDAO compensationDAO = (CompensationDAO) ContextHandler.getContext().getBean("compensationDAO");

					String dateCompt = compensationDAO.getDateCompta();
					SimpleDateFormat formaterDate = new SimpleDateFormat("ddMMyyyy");

					Util.ShowPDF(currentDirectory.getCanonicalPath()+ "\\jasper\\Etat_Global_Cheque_"+formaterDate.format(DateHandler.strToDate(dateCompt))+"_INSERTION.PDF");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//frame.setVisible(true);
			}
		});
		btnConsultation2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
					//Consultation frame = new Consultation();
				     File currentDirectory = new File(new File(".").getAbsolutePath());

					try {
						try {
							print("POSITION");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						CompensationDAO compensationDAO = (CompensationDAO) ContextHandler.getContext().getBean("compensationDAO");

						String dateCompt = compensationDAO.getDateCompta();
						SimpleDateFormat formaterDate = new SimpleDateFormat("ddMMyyyy");
						System.out.println(currentDirectory.getCanonicalPath()+ "\\jasper\\Etat_Global_Cheque_"+formaterDate.format(DateHandler.strToDate(dateCompt))+"_POSITION.PDF");

						Util.ShowPDF(currentDirectory.getCanonicalPath()+ "\\jasper\\Etat_Global_Cheque_"+formaterDate.format(DateHandler.strToDate(dateCompt))+"_POSITION.PDF");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//frame.setVisible(true);
				}
			});
		
		
		btnConsultation2.setBounds(220, 300, 200, 30);
		btnConsultation2.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
		panel.add(btnConsultation2);

		btnConsultation1.setBounds(10, 300, 200, 30);
		btnConsultation1.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
		panel.add(btnConsultation1);

		btnPosition = new JButton("2-Position");
		btnPosition.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getEnd_exec_label().setText("");
				infoVos.clear();
				addOrUpdateEtat(null);

				msgDetail.setVisible(true);
				msgDetailChq.setVisible(true);

					btnExcuter.setEnabled(false);
					btnPosition.setEnabled(false);
					new Thread(new MoulinetteChequeTest(Batch.POSITION_COMPENSATION, frame_Instance)).start();


		

			}
		});
		
		
		
		btnAgence.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getEnd_exec_label().setText("");
				infoVos.clear();
				addOrUpdateEtat(null);
				msgDetail.setVisible(true);
				msgDetailChq.setVisible(true);

				btnExcuter.setEnabled(false);
				btnPosition.setEnabled(false);
				new Thread(new MoulinetteChequeTest(Batch.INSERT_COMPENSATION_AGENCE, frame_Instance)).start();
				//new Thread(new MoulinetteChequeTest(Batch.POSITION_COMPENSATION, frame_Instance)).start();

				btnExcuter.setEnabled(false);
				btnPosition.setEnabled(false);



			}
		});
		
		
		btnAgence.setBounds(220, 335, 200, 30);
		btnAgence.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
		panel.add(btnAgence);
		
		
		btnPosition.setBounds(10, 120, 200, 30);
		btnPosition.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
		panel.add(btnPosition);
		btnPosition.setVisible(false);
		
		
		
		//  validation  insertion
		btnValidInsertion = new JButton("Valider Insertion");
		btnValidInsertion.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getEnd_exec_label().setText("");
				infoVos.clear();
				addOrUpdateEtat(null);

				msgDetail.setVisible(true);
				msgDetailChq.setVisible(true);

				btnValidInsertion.setEnabled(false);
				btnValidInsertion.setEnabled(false);
					new Thread(new MoulinetteChequeTest(Batch.VALIDINSERT_COMPENSATION, frame_Instance)).start();


		

			}
		});
		btnValidInsertion.setBounds(220, 120, 200, 30);
		btnValidInsertion.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 15));
		panel.add(btnValidInsertion);
		btnValidInsertion.setEnabled(false);
		
		
		
		//


		description_panel = new JPanel();
		description_panel.setBounds(494, 49, 374, 370);
		contentPane.add(description_panel);
		description_panel.setLayout(new BorderLayout(0, 0));

	}

	public JLabel getMsgDetail() {
		return msgDetail;
	}

	public void setMsgDetail(JLabel msgDetail) {
		this.msgDetail = msgDetail;
	}

	public JLabel getMsgDetailChq() {
		return msgDetailChq;
	}

	public void setMsgDetailChq(JLabel msgDetailChq) {
		this.msgDetailChq = msgDetailChq;
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
	
	public void print(String titre) throws SQLException, IOException {
		// try {
		File currentDirectory = new File(new File(".").getAbsolutePath());

		Map params = new HashMap();
		params.put("COD_STRC_STRC", "900");
		params.put("LIB_STRC_STRC", "Trésorerie");
		params.put("P_PATH", currentDirectory.getCanonicalPath() + "//jasper//");
		JasperReport report = null;
		JasperPrint jasperPrint = null;
		CompensationDAO compensationDAO = (CompensationDAO) ContextHandler.getContext().getBean("compensationDAO");

		String dateCompt = compensationDAO.getDateCompta();
		SimpleDateFormat formaterDate = new SimpleDateFormat("ddMMyyyy");
 		params.put("agences_forces", compensationDAO.getListAgencesCompensationForces(DateHandler.strToDate(dateCompt)));

 		params.put("P_DAT_OP",dateCompt );
 		params.put("P_TITRE", titre);
 	
 		


		Context contextCX = ContextHandler.getContext();
		DataSource datasource = (DataSource) contextCX.getBean("dataSource");
		Connection connection = datasource.getConnection();
		
		if( (compensationDAO.verifPositionCheque(DateHandler.strToDate(dateCompt)) && titre.equals("POSITION")) ||  titre.equals("INSERTION")) {

		try {
			final File position =new File(currentDirectory.getCanonicalPath()+ "/jasper/Etat_Global_Cheque_position.jasper");
			final File insertion =new File(currentDirectory.getCanonicalPath()+ "/jasper/Etat_Global_Cheque_insertion.jasper");

if(titre.equals("POSITION"))
			jasperPrint =	JasperFillManager.fillReport(new FileInputStream(position), params, connection);
if(titre.equals("INSERTION"))
	jasperPrint =	JasperFillManager.fillReport(new FileInputStream(insertion), params, connection);

	
			final JRPdfExporter exporter = new JRPdfExporter();
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

			exporter.exportReport();

	//		final File fileout = new File(currentDirectory.getCanonicaloath()+ "/jasper/Etat_Global_Cheque.PDF");
//			final String fileName = "report-" + (fileout.listFiles().length + 1) + ".pdf";
			final OutputStream outputStream = new FileOutputStream(currentDirectory.getCanonicalPath()+ "/jasper/Etat_Global_Cheque_"+formaterDate.format(DateHandler.strToDate(dateCompt))+"_"+titre+".PDF");


			baos.writeTo(outputStream);
			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		}
	}

	public void setBtnValidInsertion(JButton btnValidInsetion) {
		this.btnValidInsertion = btnValidInsetion;
	}

	public JButton getBtnValidInsertion() {
		return btnValidInsertion;
	}


	
}
