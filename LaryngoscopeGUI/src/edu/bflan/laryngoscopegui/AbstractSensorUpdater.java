package edu.bflan.laryngoscopegui;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSensorUpdater implements SensorUpdater, Runnable{
	private Map<Character, Sensor> sensors = new HashMap<>();
	private Thread updateThread;
	private boolean running;
	@Override
	public void registerSensor(Sensor toAdd) {
		assert (!sensors.containsKey(toAdd.getIdentifier()));
		sensors.put(toAdd.getIdentifier(), toAdd);
	}
	protected void updateSensor(char identifier, float force) {
		//find sensor registered to character
		Sensor sensorTriggered = sensors.get(identifier);
		if (sensorTriggered != null ) {
			//update force
			sensorTriggered.setForce(force);
		}
	}
	@Override
	public final void start() {
		assert (updateThread == null);
		updateThread = new Thread(this);
		running = true;
		updateThread.start();
	}
	@Override
	public final void run() {
		setup();
		while (running) {
			update();
		}
	}
	public abstract void setup();
	public abstract void update();
	
	@Override
	public final void stop() {
		running = false;
		try {
			updateThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
