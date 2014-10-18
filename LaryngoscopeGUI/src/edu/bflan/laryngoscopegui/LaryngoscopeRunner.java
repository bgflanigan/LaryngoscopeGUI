

package edu.bflan.laryngoscopegui;

public class LaryngoscopeRunner {
	
	public static void main(String[] args) {
		SensorUpdater testUpdater = new MockSensorUpdater(new char []{'A','B','C','D','E','P','S','R'});
		Sensor sensor1 = new Sensor('A');
		Sensor sensor2 = new Sensor('C');
		Sensor sensor3 = new Sensor('G');
		Sensor sensor4 = new Sensor('S');
		
		testUpdater.registerSensor(sensor1);
		testUpdater.registerSensor(sensor2);
		testUpdater.registerSensor(sensor3);
		testUpdater.registerSensor(sensor4);
		
		testUpdater.start();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		testUpdater.stop();
	}
}
