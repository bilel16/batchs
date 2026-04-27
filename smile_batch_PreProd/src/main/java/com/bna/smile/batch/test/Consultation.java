package com.bna.smile.batch.test;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JRViewer;

import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.DateHandler;
import com.bna.commun.util.StrHandler;
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.CompensationDAO;
import com.bna.smile.model.domainecompensation.gestionrejet.model.ListEditBatchVo;
import com.bna.smile.model.domainecompensation.gestionrejet.model.SwingInfoVo;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class Consultation extends JFrame {

	private JPanel contentPane;
	private JTextField dateStart;
	private JTextField dateEnd;
	private JTable table = new JTable();
	private List<SwingInfoVo> infoVos = new ArrayList<SwingInfoVo>();
	private Panel table_panel = new Panel();
	private Choice liste_valeur = new Choice();
	private Choice liste_agence = new Choice();
	private Choice liste_produit = new Choice();
	private Choice liste_sense = new Choice();
	private JLabel validation_label = new JLabel("");
	private JRadioButton radio_enc = new JRadioButton("Encaissement");
	private JRadioButton radio_esc = new JRadioButton("Escompte");
	private ButtonGroup group = new ButtonGroup();
	private JButton button_edition = new JButton("Edition");
	private JButton button_rechercher = new JButton("Rechercher");
	private ListEditBatchVo editBatchVo = new ListEditBatchVo();
	public Consultation frame_Instance = null;
	private JTextField agence;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					Consultation frame = new Consultation();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private PrimitiveVO dateValidator(String date) {
		Pattern p = Pattern.compile("[0-9]{1,8}");

		Matcher m = p.matcher(date.replace("/", ""));
		if (!m.matches()) {
			PrimitiveVO vo = new PrimitiveVO();
			vo.setVBool(false);
			vo.setVString(date);
			return vo;
		}
		if (date.isEmpty() || date == null) {
			PrimitiveVO vo = new PrimitiveVO();
			vo.setVBool(false);
			vo.setVString("");
			return vo;
		}
		// System.out.println("Date validator:" + date);
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

		Calendar cal = Calendar.getInstance();
		cal.set(1959, 1, 1);

		try {
			if (date.contains("/")) {
				Date d = sdf2.parse(date);
				if (d.before(cal.getTime())) {
					PrimitiveVO vo = new PrimitiveVO();
					vo.setVBool(false);
					vo.setVString(sdf2.format(d));
					return vo;

				} else {
					PrimitiveVO vo = new PrimitiveVO();
					vo.setVBool(true);
					vo.setVString(sdf2.format(d));
					return vo;
				}

			} else {
				Date d = sdf.parse(date);
				if (d.before(cal.getTime())) {
					PrimitiveVO vo = new PrimitiveVO();
					vo.setVBool(false);
					vo.setVString(sdf2.format(d));
					return vo;

				} else {
					PrimitiveVO vo = new PrimitiveVO();
					vo.setVBool(true);
					vo.setVString(sdf2.format(d));
					return vo;
				}
			}

		} catch (ParseException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			PrimitiveVO vo = new PrimitiveVO();
			vo.setVBool(false);
			vo.setVString(date);
			return vo;

		}

	}

	public void validateAgence() {
		if (!agence.getText().trim().isEmpty()) {
			Pattern p = Pattern.compile("[0-9]{0,3}");
			Matcher m = p.matcher(agence.getText());
			if (!m.matches()) {
				validation_label.setText("Code Agence invalide");
			}
		}

	}

	public void validateDate() {

		validation_label.setText("");

		// LOGGER.info("Validatng date ech");
		if (dateStart == null || dateStart.getText().isEmpty() || dateEnd == null || dateEnd.getText().isEmpty()) {

			validation_label.setText("Veuillez saisir la date début et la date fin");
		} else {
			validation_label.setText("");
			if (dateValidator(dateStart.getText()).isVBool() == false) {

				validation_label.setText("La date début est invalide");
			} else {

				dateStart.setText(dateValidator(dateStart.getText()).getVString());

				if (dateValidator(dateEnd.getText()).isVBool()) {
					dateEnd.setText(dateValidator(dateEnd.getText()).getVString());

					if (DateHandler.strToDate(dateStart.getText()).after(DateHandler.strToDate(dateEnd.getText()))) {

						validation_label.setText("La date de début  doit être avant  la date fin");
					}

				} else {
					validation_label.setText("La date fin est invalide");
				}
			}
		}

	}

	/**
	 * Create the frame.
	 */
	public Consultation() {
		setTitle("Consultation/Edition");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 921, 664);
		Image icon = Toolkit.getDefaultToolkit().getImage("./images/BNA.jpg");
		this.setIconImage(icon);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		button_rechercher = new JButton("Rechercher");
		frame_Instance = this;
		table_panel.removeAll();
		contentPane.updateUI();
		this.setResizable(false);
		button_rechercher.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				table_panel.removeAll();
				contentPane.updateUI();
				validateDate();
				validateAgence();
				if (validation_label.getText().isEmpty()) {
					button_rechercher.setEnabled(false);
					button_edition.setEnabled(false);
					editBatchVo.setStructure(null);
					if (liste_sense.getSelectedIndex() == 0)
						editBatchVo.setSens("RCP");
					else
						editBatchVo.setSens("ENV");

					editBatchVo.setValeur(liste_valeur.getSelectedItem());
					editBatchVo.setDateStart(dateStart.getText());
					editBatchVo.setDateEnd(dateEnd.getText());

					editBatchVo.setStructure(agence.getText());

					new Thread(new EditionBatch(frame_Instance)).start();

				} else {
					button_edition.setEnabled(false);
				}
			}
		});
		button_rechercher.setBounds(27, 171, 131, 23);
		contentPane.add(button_rechercher);

		JLabel lblSens = new JLabel("Sens :");
		lblSens.setBounds(27, 49, 63, 20);
		contentPane.add(lblSens);

		liste_sense = new Choice();
		liste_sense.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				fillValeur();
			}
		});
		liste_sense.setBounds(93, 49, 129, 20);
		liste_sense.add("Réception");
		liste_sense.add("Envois");
		contentPane.add(liste_sense);

		JLabel lblValeur = new JLabel("Valeur :");
		lblValeur.setBounds(255, 84, 49, 20);

		contentPane.add(lblValeur);

		liste_valeur = new Choice();
		liste_valeur.setBounds(310, 84, 107, 20);
		contentPane.add(liste_valeur);

		liste_produit = new Choice();
		liste_produit.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				fillValeur();
			}
		});
		liste_produit.add("Chèque");
		liste_produit.add("Lettre de Change");
		liste_produit.add("Prélèvement");
		liste_produit.setBounds(93, 84, 129, 20);

		contentPane.add(liste_produit);

		JLabel lblProduit = new JLabel("Produit :");
		lblProduit.setBounds(27, 84, 63, 20);

		contentPane.add(lblProduit);

		JLabel lblAgence = new JLabel("Agence :");
		lblAgence.setBounds(27, 127, 63, 20);
		contentPane.add(lblAgence);

		table_panel.setBounds(10, 273, 885, 342);
		contentPane.add(table_panel);

		JLabel lblDate = new JLabel("Date Op\u00E9ration ");
		lblDate.setBounds(6, 13, 91, 23);
		contentPane.add(lblDate);

		dateStart = new JTextField(DateHandler.dateToStr(new Date()));
		dateStart.setBounds(136, 13, 107, 20);
		contentPane.add(dateStart);
		dateStart.setColumns(10);

		dateEnd = new JTextField(DateHandler.dateToStr(new Date()));
		dateEnd.setColumns(10);
		dateEnd.setBounds(282, 13, 107, 20);
		contentPane.add(dateEnd);

		JLabel lblA = new JLabel("A :");
		lblA.setBounds(255, 14, 27, 20);
		contentPane.add(lblA);

		validation_label = new JLabel("");
		validation_label.setForeground(Color.RED);
		validation_label.setBounds(395, 13, 374, 20);
		contentPane.add(validation_label);

		button_edition = new JButton("Edition");
		button_edition.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				button_edition.setEnabled(false);
				try {
					printEtat();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				button_edition.setEnabled(true);
			}

		});
		button_edition.setBounds(247, 171, 131, 23);
		contentPane.add(button_edition);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(93, 13, 1, 1);
		contentPane.add(desktopPane);

		JLabel lblDe = new JLabel("DE :");
		lblDe.setBounds(103, 13, 27, 23);
		contentPane.add(lblDe);

		radio_enc = new JRadioButton("Encaissement");
		radio_enc.setBounds(438, 83, 109, 23);
		contentPane.add(radio_enc);

		radio_esc = new JRadioButton("Escompte");
		radio_esc.setBounds(549, 83, 109, 23);
		contentPane.add(radio_esc);
		group = new ButtonGroup();
		group.add(radio_esc);
		group.add(radio_enc);

		agence = new JTextField("");
		agence.setColumns(10);
		agence.setBounds(93, 127, 129, 20);
		contentPane.add(agence);

		JButton btnEditionGlobalChque = new JButton("Edition globale Ch\u00E8que");
		btnEditionGlobalChque.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				validation_label.setText("");
				validateDate();
				validateAgence();
				if (ContextHandler.getContext() == null) {
					String[] path =
							{ "./config/spring.xml", "./config/applicationContext-DAO.xml",
									"./config/applicationContext-habilitation.xml",
									"./config/applicationContext-resources.xml",
									"./config/applicationContext-service.xml",
									"./config/applicationContext-serviceBatch.xml",
									"./config/applicationContext-serviceHabil.xml",
									"./config/applicationContext-traitements.xml",
									"./config/quartz-oxia-calendars.xml", "./config/quartz-oxia-core.xml",
									"./config/quartz-oxia-jobs.xml", "./config/security.xml",
									"./config/quartz-oxia-listners.xml", "./config/quartz-oxia-triggers.xml" };

					ApplicationContext springContext = new FileSystemXmlApplicationContext(path);
					Context context = (Context) ContextFactory.initContext("./config/applicationContext-1Spring.xml");
					context.setSpringContext(springContext);
					ContextHandler.setContext(context);
				}

				CompensationDAO compensationDAO =
						(CompensationDAO) ContextHandler.getContext().getBean("compensationDAO");

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

				try {
					today = sdf.parse(sdf.format(new Date()));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}

				if (validation_label.getText().isEmpty()) {

					if (DateHandler.strToDate(dateStart.getText()).equals(today)
							&& compensationDAO.verifPositionCheque() > 0) {
						validation_label.setText("Veuillez effectuer la position");

					} else {
						button_rechercher.setEnabled(false);
						button_edition.setEnabled(false);
						editBatchVo.setStructure(null);
						editBatchVo.setGlobal(true);

						editBatchVo.setValeur("30");
						editBatchVo.setDateStart(dateStart.getText());
						new Thread(new EditionGlobal(frame_Instance)).start();
					}

				} else {
					button_edition.setEnabled(false);
				}
			}
		});
		btnEditionGlobalChque.setBounds(416, 138, 185, 23);
		contentPane.add(btnEditionGlobalChque);

		JButton btnEditionGlobaleChque = new JButton("Edition globale Effet");
		btnEditionGlobaleChque.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				validation_label.setText("");
				validateDate();
				validateAgence();
				if (ContextHandler.getContext() == null) {
					String[] path =
							{ "./config/spring.xml", "./config/applicationContext-DAO.xml",
									"./config/applicationContext-habilitation.xml",
									"./config/applicationContext-resources.xml",
									"./config/applicationContext-service.xml",
									"./config/applicationContext-serviceBatch.xml",
									"./config/applicationContext-serviceHabil.xml",
									"./config/applicationContext-traitements.xml",
									"./config/quartz-oxia-calendars.xml", "./config/quartz-oxia-core.xml",
									"./config/quartz-oxia-jobs.xml", "./config/security.xml",
									"./config/quartz-oxia-listners.xml", "./config/quartz-oxia-triggers.xml" };

					ApplicationContext springContext = new FileSystemXmlApplicationContext(path);
					Context context = (Context) ContextFactory.initContext("./config/applicationContext-1Spring.xml");
					context.setSpringContext(springContext);
					ContextHandler.setContext(context);
				}

				if (validation_label.getText().isEmpty()) {

					button_rechercher.setEnabled(false);
					button_edition.setEnabled(false);
					editBatchVo.setStructure(null);
					editBatchVo.setGlobal(true);

					editBatchVo.setValeur("41");
					editBatchVo.setDateStart(dateStart.getText());
					new Thread(new EditionGlobal(frame_Instance)).start();

				} else {
					button_edition.setEnabled(false);
				}
			}
		});
		btnEditionGlobaleChque.setBounds(416, 171, 185, 23);
		contentPane.add(btnEditionGlobaleChque);

		JButton btnEditionPrelevementPositionees = new JButton("Edition Prélèv Positionnés");
		btnEditionPrelevementPositionees.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				validation_label.setText("");
				validateDate();
				validateAgence();
				if (ContextHandler.getContext() == null) {
					String[] path =
							{ "./config/spring.xml", "./config/applicationContext-DAO.xml",
									"./config/applicationContext-habilitation.xml",
									"./config/applicationContext-resources.xml",
									"./config/applicationContext-service.xml",
									"./config/applicationContext-serviceBatch.xml",
									"./config/applicationContext-serviceHabil.xml",
									"./config/applicationContext-traitements.xml",
									"./config/quartz-oxia-calendars.xml", "./config/quartz-oxia-core.xml",
									"./config/quartz-oxia-jobs.xml", "./config/security.xml",
									"./config/quartz-oxia-listners.xml", "./config/quartz-oxia-triggers.xml" };

					ApplicationContext springContext = new FileSystemXmlApplicationContext(path);
					Context context = (Context) ContextFactory.initContext("./config/applicationContext-1Spring.xml");
					context.setSpringContext(springContext);
					ContextHandler.setContext(context);
				}

				CompensationDAO compensationDAO =
						(CompensationDAO) ContextHandler.getContext().getBean("compensationDAO");

				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

				try {
					today = sdf.parse(sdf.format(new Date()));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}

				if (validation_label.getText().isEmpty()) {

					button_rechercher.setEnabled(false);
					button_edition.setEnabled(false);
					editBatchVo.setStructure(null);
					editBatchVo.setGlobal(true);

					editBatchVo.setValeur("20");
					editBatchVo.setDateStart(dateStart.getText());
					new Thread(new EditionGlobal(frame_Instance)).start();

				} else {
					button_edition.setEnabled(false);
				}
			}
		});
		btnEditionPrelevementPositionees.setBounds(416, 205, 185, 23);
		contentPane.add(btnEditionPrelevementPositionees);

		radio_esc.setVisible(false);
		radio_enc.setVisible(false);
		fillValeur();

	}

	public Choice getListe_agence() {
		return liste_agence;
	}

	public void setListe_agence(Choice liste_agence) {
		this.liste_agence = liste_agence;
	}

	public void afficherEtat(List<SwingInfoVo> vos) {

		Object[][] etatList = new Object[vos.size()][9];

		for (int i = 0; i < vos.size(); i++) {
			etatList[i][0] = vos.get(i).getStructure();
			etatList[i][1] = vos.get(i).getLib_structure();
			etatList[i][2] = vos.get(i).getNombre_intra();
			etatList[i][3] = vos.get(i).getMontant_intra();
			etatList[i][4] = vos.get(i).getNombre_inter();
			etatList[i][5] = vos.get(i).getMontant_inter();
			etatList[i][6] = vos.get(i).getNombre_total();
			etatList[i][7] = vos.get(i).getMontant_total();
			etatList[i][8] = vos.get(i).getDateComptable();

		}
		String[] columnNames =
				{ "Structure", "Libellé", "Nombre Intra", "Montant Intra", "Nombre Inter", "Montant Inter",
						"Nombre Total", "Montant Total", "Date Operation" };
		DefaultTableModel model = new DefaultTableModel(etatList, columnNames);

		table = new JTable(model) {

			public DefaultTableCellRenderer getCellRenderer(int row, int col) {
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				if (col == 0)
					centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				if (col == 1)
					centerRenderer.setHorizontalAlignment(SwingConstants.LEFT);
				if (col == 2)
					centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				if (col == 3)
					centerRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
				if (col == 4)
					centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				if (col == 5)
					centerRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
				if (col == 6)
					centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				if (col == 7)
					centerRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
				if (col == 8)
					centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

				return centerRenderer;
			}
		};
		table.removeAll();
		table_panel.removeAll();
		table.setEnabled(false);
		table_panel.setLayout(new BorderLayout());
		table_panel.add(new JScrollPane(table), BorderLayout.CENTER);
		contentPane.add(table_panel);
		contentPane.updateUI();

	}

	// print etat
	public void printEtatGlobal(List<SwingInfoVo> liste, String valeur) throws IOException {

		File currentDirectory = new File(new File(".").getAbsolutePath());
		Map params = new HashMap();
		params.put("COD_STRC_STRC", "900");
		params.put("LIB_STRC_STRC", "Trésorerie");
		params.put("P_PATH", currentDirectory.getCanonicalPath() + "//jasper//");
		JasperReport report = null;
		JasperPrint jasperPrint = null;
		
		if (valeur.equals("20")) {
			
			params.put("P_DAT_OP", dateStart.getText());
			
			try {
				report =
						(JasperReport) JRLoader.loadObject(currentDirectory.getCanonicalPath()
								+ "/jasper/Etat_Global_Prelevement.jasper");
				// System.out.println(currentDirectory.getCanonicalPath() + "/jasper/Etat_Global_Cheque.jasper");
				// System.out.println(report);
				jasperPrint = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(liste));

			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (valeur.equals("30")) {
			params.put("montant_total_30", liste.get(0).getMontant_total_30());
			params.put("nombre_total_30", liste.get(0).getNombre_total_30());
			params.put("montant_total_31", liste.get(0).getMontant_total_31());
			params.put("nombre_total_31", liste.get(0).getNombre_total_31());
			params.put("montant_total_32", liste.get(0).getMontant_total_32());
			params.put("nombre_total_32", liste.get(0).getNombre_total_32());
			params.put("montant_total_33", liste.get(0).getMontant_total_33());
			params.put("nombre_total_33", liste.get(0).getNombre_total_33());

			params.put("MT30", liste.get(0).getMT30());
			params.put("NT30", liste.get(0).getNT30());
			params.put("MT31", liste.get(0).getMT31());
			params.put("NT31", liste.get(0).getNT31());
			params.put("MT32", liste.get(0).getMT32());
			params.put("NT32", liste.get(0).getNT32());
			params.put("MT33", liste.get(0).getMT33());
			params.put("NT33", liste.get(0).getNT33());

			params.put("P_DAT_OP", dateStart.getText());
			// System.out.println(dateStart.getText());
			// System.out.println(liste.size());

			try {
				report =
						(JasperReport) JRLoader.loadObject(currentDirectory.getCanonicalPath()
								+ "/jasper/Etat_Global_Cheque.jasper");
				// System.out.println(currentDirectory.getCanonicalPath() + "/jasper/Etat_Global_Cheque.jasper");
				// System.out.println(report);
				jasperPrint = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(liste));

			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			params.put("montant_total_40_21", liste.get(0).getMontant_total_40_21());
			params.put("nombre_total_40_21", liste.get(0).getNombre_total_40_21());
			params.put("montant_total_40_25", liste.get(0).getMontant_total_40_25());
			params.put("nombre_total_40_25", liste.get(0).getNombre_total_40_25());
			params.put("montant_total_40_22", liste.get(0).getMontant_total_40_22());
			params.put("nombre_total_40_22", liste.get(0).getNombre_total_40_22());
			params.put("montant_total_41_21", liste.get(0).getMontant_total_41_21());
			params.put("nombre_total_41_21", liste.get(0).getNombre_total_41_21());
			params.put("montant_total_41_25", liste.get(0).getMontant_total_41_25());
			params.put("nombre_total_41_25", liste.get(0).getNombre_total_41_25());
			params.put("montant_total_41_22", liste.get(0).getMontant_total_41_22());
			params.put("nombre_total_41_22", liste.get(0).getNombre_total_41_22());
			params.put("montant_total_42_21", liste.get(0).getMontant_total_42_21());
			params.put("nombre_total_42_21", liste.get(0).getNombre_total_42_21());
			params.put("P_DAT_OP", dateStart.getText());
			// System.out.println(dateStart.getText());
			// System.out.println(liste.size());
			params.put("P_ETAT_POSITION", "");
			CompensationDAO compensationDAO = (CompensationDAO) ContextHandler.getContext().getBean("compensationDAO");
			if (compensationDAO.verifPositionEffet() > 0) {
				params.put("P_ETAT_POSITION", "Position Non Effectuée");
			}

			try {
				report =
						(JasperReport) JRLoader.loadObject(currentDirectory.getCanonicalPath()
								+ "/jasper/Etat_Global_Effet.jasper");
				// System.out.println(currentDirectory.getCanonicalPath() + "/jasper/Etat_Global_Effet.jasper");
				// System.out.println(report);

				jasperPrint = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(liste));

			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		JFrame frame = new JFrame("Report");
		frame.getContentPane().add(new JRViewer(jasperPrint));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// frame.pack();
		frame.setVisible(true);

	}

	// print etat
	public void printEtat() throws IOException {

		File currentDirectory = new File(new File(".").getAbsolutePath());
		Map params = new HashMap();
		System.out.println();
		params.put("COD_STRC_STRC", "900");
		params.put("LIB_STRC_STRC", "Trésorerie");
		params.put("P_PATH", currentDirectory.getCanonicalPath() + "//jasper//");
		params.put("P_DAT_DEB", dateStart.getText());
		params.put("P_DAT_FIN", dateEnd.getText());
		params.put("P_VALEUR", liste_valeur.getSelectedItem());
		params.put("P_SENS", liste_sense.getSelectedItem());

		Long tot = 0L;
		Long NBRtot = 0L;
		for (int i = 0; i < infoVos.size(); i++) {
			tot += Long.valueOf(infoVos.get(i).getMontant_total().replace(" ", "").replace(".", ""));
			NBRtot += Long.valueOf(infoVos.get(i).getNombre_total().replace(" ", "").replace(".", ""));
		}
		params.put("P_TOT", StrHandler.formatMontant(tot, 3L));
		params.put("P_NBR", "" + NBRtot);
		System.out.println(currentDirectory.getCanonicalPath());
		System.out.println(currentDirectory.getAbsolutePath());
		JasperReport report = null;
		try {
			report =
					(JasperReport) JRLoader.loadObject(currentDirectory.getCanonicalPath()
							+ "/jasper/etatbatchSmile.jasper");
			JasperPrint jasperPrint =
					JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(infoVos));

			JFrame frame = new JFrame("Report");
			frame.getContentPane().add(new JRViewer(jasperPrint));
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			// frame.pack();
			frame.setVisible(true);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void fillValeur() {

		liste_valeur.removeAll();
		// radio_esc.setVisible(false);
		// radio_enc.setVisible(false);
		if (liste_produit.getSelectedIndex() == 0) {
			if (liste_sense.getSelectedIndex() == 0) {
				liste_valeur.add("30");
				liste_valeur.add("31");
				liste_valeur.add("32");
				liste_valeur.add("33");
			} else {
				liste_valeur.add("81-21");
				liste_valeur.add("82-21");
				liste_valeur.add("83-21");
				liste_valeur.add("84-21");
				liste_valeur.add("30-22");
			}

		}

		if (liste_produit.getSelectedIndex() == 1) {
			// radio_esc.setVisible(true);
			// radio_enc.setVisible(true);
			if (liste_sense.getSelectedIndex() == 0) {
				liste_valeur.add("41-21");
				liste_valeur.add("40-21");
				liste_valeur.add("42-21");
				liste_valeur.add("40-22");
				liste_valeur.add("40-25");
				liste_valeur.add("41-22");
				liste_valeur.add("41-25");
			} else {
				liste_valeur.add("41-21");
				liste_valeur.add("40-21");
				liste_valeur.add("40-22");
				liste_valeur.add("41-22");

			}
		}
		if (liste_produit.getSelectedIndex() == 2) {
			// radio_esc.setVisible(true);
			// radio_enc.setVisible(true);
			if (liste_sense.getSelectedIndex() == 0)
				liste_valeur.add("20-21");

			else {
				liste_valeur.add("20-22");

			}
		}

	}

	public List<SwingInfoVo> getInfoVos() {
		return infoVos;
	}

	public void setInfoVos(List<SwingInfoVo> infoVos) {
		this.infoVos = infoVos;
	}

	public JButton getButton_edition() {
		return button_edition;
	}

	public void setButton_edition(JButton button_edition) {
		this.button_edition = button_edition;
	}

	public JButton getButton_rechercher() {
		return button_rechercher;
	}

	public void setButton_rechercher(JButton button_rechercher) {
		this.button_rechercher = button_rechercher;
	}

	public ListEditBatchVo getEditBatchVo() {
		return editBatchVo;
	}

	public void setEditBatchVo(ListEditBatchVo editBatchVo) {
		this.editBatchVo = editBatchVo;
	}

	public Consultation getFrame_Instance() {
		return frame_Instance;
	}

	public void setFrame_Instance(Consultation frame_Instance) {
		this.frame_Instance = frame_Instance;
	}
}
