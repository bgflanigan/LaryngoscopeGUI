package edu.bflan.laryngoscopegui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class RealTimeFeedbackScreen extends JFrame {

	private JPanel contentPane;
	private SensorFeedbackChart chart;
	private TimerPane time;

	/**
	 * Launch the application.
	 */
	//public static void main(String[] args) {}
		

	/**
	 * Create the frame.
	 */
	public RealTimeFeedbackScreen(List<SensorRenderer> renderers) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		JPanel northPane = new JPanel();
		contentPane.add(northPane, BorderLayout.NORTH);
		northPane.setLayout(new FlowLayout());
		JButton btnStart = new JButton("Start");
		northPane.add(btnStart);
		JButton btnStop = new JButton("Stop");
		northPane.add(btnStop);
		time = new TimerPane();
		northPane.add(time);
		time.start();
		chart = new SensorFeedbackChart(renderers);
		contentPane.add(chart, BorderLayout.CENTER);
		JButton btnResults = new JButton("Results");
		contentPane.add(btnResults, BorderLayout.SOUTH);
	}

}
