package edu.bflan.laryngoscopegui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class ResultsScreen extends JFrame {

	private static final long serialVersionUID = 1662677903836533179L;

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnBrowse;
	private JFileChooser fileChooser = new JFileChooser();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sensor s1 = new Sensor('A', "Epiglottis", 15);
					Sensor s2 = new Sensor('B', "Vallecula", 5);
					s1.setForce(15.2f);
					s2.setForce(2);
					List<Sensor> sensors = new ArrayList<Sensor>();
					sensors.add(s1);
					sensors.add(s2);
					ResultsScreen frame = new ResultsScreen(new CaptureThread(null, sensors, null));
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
	public ResultsScreen(CaptureThread capper) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSaveFile = new JButton("Save File");
		btnSaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String text = textField.getText();
				if (!text.isEmpty()) {
					try {
						capper.saveAs(text);
						ResultsScreen.this.dispose();
					} catch (IOException e) {
						System.out.println("Error: Could not save to file \""+text+"\"");
						e.printStackTrace();
					}
				}
			}
		});
		btnSaveFile.setBounds(347, 227, 77, 23);
		contentPane.add(btnSaveFile);
		
		textField = new JTextField();
		textField.setBounds(10, 230, 247, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
	            if (fileChooser.showSaveDialog(btnBrowse) == JFileChooser.APPROVE_OPTION) {
	                textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
	            }
			}
		});
		btnBrowse.setBounds(258, 227, 79, 23);
		contentPane.add(btnBrowse);
		
		JTextPane textPane = new JTextPane();
		JScrollPane scroll = new JScrollPane(textPane);
		String text = "Results Overview:\n"
					+ "=================\n";
		text += capper.getResultOverview();
		
		textPane.setText(text);
		textPane.setEditable(false);
		scroll.setBounds(10, 11, 414, 211);
		contentPane.add(scroll);
		//TODO: put this after visible()
		JScrollBar bar = scroll.getVerticalScrollBar();
		bar.setValue(bar.getMinimum());
	}
}
