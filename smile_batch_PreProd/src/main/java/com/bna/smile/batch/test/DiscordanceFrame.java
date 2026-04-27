package com.bna.smile.batch.test;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecompensation.gestionrejet.dao.DiscordanceSoldeDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.context.ContextFactory;

public class DiscordanceFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	private JLabel lblNewLabel = new JLabel("");
	
	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}

	
	public void setLblNewLabel(JLabel lblNewLabel) {
		this.lblNewLabel = lblNewLabel;
	}

	public JPanel loading_pan = new JPanel();
	public DiscordanceFrame frame_Instance = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					DiscordanceFrame frame = new DiscordanceFrame();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void updateInfo1(final String info) {
		SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

			@Override
			protected Void doInBackground() throws Exception {
				// Here not in the EDT

				publish(info); // published values are passed to the #process(List) method

				return null;
			}

			@Override
			protected void process(List<String> infos) {
				// chunks are values retrieved from #publish()
				// Here we are on the EDT and can safely update the UI
				lblNewLabel.setText(infos.get(infos.size() - 1).toString());
			}

			@Override
			protected void done() {
				// Invoked when the SwingWorker has finished
				// We are on the EDT, we can safely update the UI
				lblNewLabel.setText("Done");
			}
		};
		worker.execute();
	}

	public void updateInfo(String msg) {
		lblNewLabel.setText(msg);
		contentPane.updateUI();
	}

	public DiscordanceFrame() {
		setTitle("Discordance Solde");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 731, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(77, 93, 575, 30);
		contentPane.add(lblNewLabel);
		loading_pan.setBounds(642, 23, 52, 49);
		contentPane.add(loading_pan);

		JButton btnNewButton = new JButton("Executer Solde Initial");
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String[] path =
						{ "./config/spring.xml", "./config/applicationContext-DAO.xml",
								"./config/applicationContext-habilitation.xml",
								"./config/applicationContext-resources.xml", "./config/applicationContext-service.xml",
								"./config/applicationContext-serviceBatch.xml",
								"./config/applicationContext-serviceHabil.xml",
								"./config/applicationContext-traitements.xml", "./config/quartz-oxia-calendars.xml",
								"./config/quartz-oxia-core.xml", "./config/quartz-oxia-jobs.xml",
								"./config/quartz-oxia-listners.xml", "./config/quartz-oxia-triggers.xml" };

				ApplicationContext springContext = new FileSystemXmlApplicationContext(path);
				Context context = (Context) ContextFactory.initContext("./config/applicationContext-1Spring.xml");
				context.setSpringContext(springContext);
				ContextHandler.setContext(context);
				DiscordanceSoldeDAO discordanceSoldeDAO = (DiscordanceSoldeDAO) context.getBean("discordanceSoldeDAO");
				ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
				// loading_pan.setBounds(642, 23, 52, 49);
				// ImageIcon loading = new ImageIcon("loading83.gif");
				// loading_pan.add(new JLabel("Veuillez Patienter... ", loading, JLabel.CENTER));
				contentPane.updateUI();
				long start = System.currentTimeMillis();
				List<Long> strcs = discordanceSoldeDAO.getAgPilolte();
				for (int i = 0; i < strcs.size(); i++) {
					taskExecutor.execute(new MoulinetteExtractSolde(frame_Instance, strcs.get(i)));
					// new Thread(new MoulinetteExtractSolde(frame_Instance, strcs.get(i))).start();
				}
			//updateInfo("Extraction  Solde  Terminée " );

			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.setForeground(Color.RED);
		btnNewButton.setBounds(10, 36, 170, 30);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Verifier Discordance Solde");
		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

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
									"./config/quartz-oxia-jobs.xml", "./config/quartz-oxia-listners.xml",
									"./config/quartz-oxia-triggers.xml" };

					ApplicationContext springContext = new FileSystemXmlApplicationContext(path);
					Context context = (Context) ContextFactory.initContext("./config/applicationContext-1Spring.xml");
					context.setSpringContext(springContext);
					ContextHandler.setContext(context);
				}
				DiscordanceSoldeDAO discordanceSoldeDAO =
						(DiscordanceSoldeDAO) ContextHandler.getContext().getBean("discordanceSoldeDAO");
				ThreadPoolTaskExecutor taskExecutor =
						(ThreadPoolTaskExecutor) ContextHandler.getContext().getBean("taskExecutor");

				List<Long> strcs = discordanceSoldeDAO.getAgPilolte();

				for (int i = 0; i < strcs.size(); i++) {
					taskExecutor.execute(new MoulinetteVerifSolde(frame_Instance, strcs.get(i)));

					// new Thread(new MoulinetteVerifSolde(frame_Instance, strcs.get(i))).start();
				}

			}
		});
		btnNewButton_1.setForeground(Color.BLUE);
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1.setBounds(283, 273, 234, 44);
		contentPane.add(btnNewButton_1);
		frame_Instance = this;
	}
}
