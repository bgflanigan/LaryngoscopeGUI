

package edu.bflan.laryngoscopegui;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

public class LaryngoscopeRunner {
	
	public static void main(String[] args) {
		Sensor sensor1 = new Sensor('a',"Back of Esophagus",1);
		Sensor sensor2 = new Sensor('b',"Hypopharynx",1);
		Sensor sensor3 = new Sensor('c',"Left Vocal Cord",1);
		Sensor sensor4 = new Sensor('d',"Right Vocal Cord",1);
		Sensor sensor5 = new Sensor('e',"Carina",1);
		Sensor sensor6 = new Sensor('f',"Front of Esophagus",1);
		Sensor sensor7 = new Sensor('g',"Epiglottis",1);
		Sensor sensor8 = new Sensor('h',"Vallecula",1);
		
		final List<Sensor> sensors = new ArrayList<>();
		sensors.add(sensor1);
		sensors.add(sensor2);
		sensors.add(sensor3);
		sensors.add(sensor4);
		sensors.add(sensor5);
		sensors.add(sensor6);
		sensors.add(sensor7);
		sensors.add(sensor8);
		
		
		final RealTimeFeedbackScreen frame = new RealTimeFeedbackScreen(sensors,new PracticeMode());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
	}
}
