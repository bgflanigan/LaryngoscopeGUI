package edu.bflan.laryngoscopegui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PracticeTestScreen extends JFrame {

	private static final long serialVersionUID = -4517440181499922962L;

	private JPanel contentPane;
	private Mode mode;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PracticeTestScreen frame = new PracticeTestScreen();
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
	public PracticeTestScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Practice Mode");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mode = new PracticeMode();
				finish();
			}
		});
		btnNewButton.setBounds(65, 116, 120, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Test Mode");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mode = new TestMode();
				finish();
			}
		});
		btnNewButton_1.setBounds(245, 116, 114, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblSelectAMode = new JLabel("Select a Mode");
		lblSelectAMode.setBounds(180, 57, 107, 14);
		contentPane.add(lblSelectAMode);
		
		JLabel lblCompleteVisualAnd = new JLabel("Train with Visual");
		lblCompleteVisualAnd.setBounds(32, 150, 210, 30);
		contentPane.add(lblCompleteVisualAnd);
		
		JLabel lblCompleteVisualAnd1 = new JLabel("and Auditory Feedback");
		lblCompleteVisualAnd1.setBounds(32, 164, 210, 30);
		contentPane.add(lblCompleteVisualAnd1);
		
		JLabel lblTestWithVisual = new JLabel("Test with Visual"); 
		lblTestWithVisual.setBounds(255, 158, 169, 14);
		contentPane.add(lblTestWithVisual);
		
		JLabel lblTestWithVisual1 = new JLabel("Feedback Only");
		lblTestWithVisual1.setBounds(255, 172, 169, 14);
		contentPane.add(lblTestWithVisual1);
	}
	
	private void finish() {
		ProcedureSelectScreen pScreen = new ProcedureSelectScreen(mode);
		pScreen.setVisible(true);
		PracticeTestScreen.this.dispose();
	}
	
	public Mode getMode() {
		return mode;
	}
}
