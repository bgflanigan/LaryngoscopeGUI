

package edu.bflan.laryngoscopegui;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

public class LaryngoscopeRunner {
	
	public static void main(String[] args) {
		SensorUpdater testUpdater = new MockSensorUpdater(new char []{'A','B','C','D','E','P','S','R'});
		Sensor sensor1 = new Sensor('A',"sensor1",15);
		Sensor sensor2 = new Sensor('C',"sensor2",21);
		Sensor sensor3 = new Sensor('G',"sensor3",4);
		Sensor sensor4 = new Sensor('S',"sensor4",12);
		Sensor sensor5 = new Sensor('B',"sensor5",10);
		Sensor sensor6 = new Sensor('D',"sensor6",15);
		Sensor sensor7 = new Sensor('E',"sensor7",20);
		Sensor sensor8 = new Sensor('R',"sensor8",25);
		
		testUpdater.registerSensor(sensor1);
		testUpdater.registerSensor(sensor2);
		testUpdater.registerSensor(sensor3);
		testUpdater.registerSensor(sensor4);
		testUpdater.registerSensor(sensor5);
		testUpdater.registerSensor(sensor6);
		testUpdater.registerSensor(sensor7);
		testUpdater.registerSensor(sensor8);
		
		final List<SensorRenderer> renderers = new ArrayList<>();
		renderers.add(new SensorRenderer(sensor1));
		renderers.add(new SensorRenderer(sensor2));
		renderers.add(new SensorRenderer(sensor3));
		renderers.add(new SensorRenderer(sensor4));
		renderers.add(new SensorRenderer(sensor5));
		renderers.add(new SensorRenderer(sensor6));
		renderers.add(new SensorRenderer(sensor7));
		renderers.add(new SensorRenderer(sensor8));
		
		testUpdater.start();
		final RealTimeFeedbackScreen frame = new RealTimeFeedbackScreen(renderers);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		while (true){
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					frame.repaint();
				
				}
			});
		}
	}
}
