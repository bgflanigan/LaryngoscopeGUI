package edu.bflan.laryngoscopegui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class RealTimeFeedbackScreen extends JFrame {

	private static final long serialVersionUID = 6784579839519709221L;
	private JPanel contentPane;
	private SensorFeedbackChart chart;
	private TimerPane time;
	private CaptureThread captureThread;
	private SensorUpdater testUpdater;
	private Timer repaintTimer;
	/**
	 * Launch the application.
	 */
	//public static void main(String[] args) {}
		

	/**
	 * Create the frame.
	 */
	public RealTimeFeedbackScreen(List<Sensor> sensors, Mode mode) {
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
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				time.start();
			}
		});
		northPane.add(btnStart);
		JButton btnStop = new JButton("Pause");
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				time.stop();
			}
		});
		northPane.add(btnStop);
		time = new TimerPane();
		northPane.add(time);
		repaintTimer = new Timer(17, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RealTimeFeedbackScreen.this.repaint();
			}
		});
		
		//testUpdater = new DeviceSensorUpdater();
		testUpdater = new MockSensorUpdater(new char[] {'a','b','c','d','e','f','g','h'});
		for (Sensor s : sensors) {
			testUpdater.registerSensor(s);
		}
		captureThread = new CaptureThread(time, sensors, mode);
		List<SensorRenderer> renderers = new ArrayList<>();
		for (Sensor s : sensors) {
			SensorRenderer renderer = new SensorRenderer(s);
			renderers.add(renderer);
		}
		chart = new SensorFeedbackChart(renderers);
		contentPane.add(chart, BorderLayout.CENTER);
		JButton btnResults = new JButton("Results");
		btnResults.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				captureThread.stop();
				testUpdater.stop();
				repaintTimer.stop();
				ResultsScreen results = new ResultsScreen(captureThread);
				results.setVisible(true);
				RealTimeFeedbackScreen.this.dispose();
			}
			
		});
		contentPane.add(btnResults, BorderLayout.SOUTH);
		testUpdater.start();
		try {
			captureThread.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		time.start();
		repaintTimer.start();
	}

}
