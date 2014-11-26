package edu.bflan.laryngoscopegui;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class ProcedureSelectScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private Mode mode;
	private JPanel contentPane;
	private List<Procedure> procedures;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProcedureSelectScreen frame = new ProcedureSelectScreen(new PracticeMode());
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
	public ProcedureSelectScreen(Mode mode) {
		this.mode = mode;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblSelectAProcedure = new JLabel("Select a Procedure");
		try {
			procedures = Procedure.loadProcedures(new File("procedures.txt"));
			if (procedures == null)
				throw new IOException();
		} catch (IOException e) {
			lblSelectAProcedure = new JLabel("Load procedures failed");
			procedures = new ArrayList<>();
			e.printStackTrace();
		}
		
		List<JButton> procButtons = new ArrayList<JButton>();
		for (Procedure proc : procedures) {
			JButton button = new JButton(proc.getName());
			button.addActionListener(new ActionListener() {
				Procedure procedure = proc;
				@Override
				public void actionPerformed(ActionEvent e) {
					finish(procedure.makeSensors());
				}
			});
			procButtons.add(button);
		}
		
		JButton btnAddNewProcedure = new JButton("Edit Procedures");
		btnAddNewProcedure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File file = new File("procedures.txt");
				try {
					if (System.getProperty("os.name").toLowerCase().contains("windows")) {
						String cmd = "rundll32 url.dll,FileProtocolHandler " + file.getCanonicalPath();
						Runtime.getRuntime().exec(cmd);
					} else {
						Desktop.getDesktop().edit(file);
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		ParallelGroup dynGroup = gl_contentPane.createParallelGroup(Alignment.TRAILING)
											   .addComponent(btnAddNewProcedure);
		for (JButton button : procButtons) {
			dynGroup = dynGroup.addComponent(button);
		}
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(162)
							.addComponent(lblSelectAProcedure))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(141)
							.addGroup(dynGroup)))
					.addContainerGap(152, Short.MAX_VALUE))
		);
		SequentialGroup seq = gl_contentPane.createSequentialGroup()
											.addGap(36)
											.addComponent(lblSelectAProcedure)
											.addGap(18);
		for (JButton button : procButtons) {
			seq = seq.addComponent(button)
					 .addPreferredGap(ComponentPlacement.RELATED, 2, Short.MAX_VALUE);
		}
		seq = seq.addPreferredGap(ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
				 .addComponent(btnAddNewProcedure)
				 .addGap(25);
		ParallelGroup vertGroup = gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(seq);
		gl_contentPane.setVerticalGroup(vertGroup);
		contentPane.setLayout(gl_contentPane);
	}
	
	protected void finish(List<Sensor> sensors) {
		RealTimeFeedbackScreen realTimeFeedbackScreen = new RealTimeFeedbackScreen(sensors,mode);
		dispose();
		realTimeFeedbackScreen.setVisible(true);
	}

}
