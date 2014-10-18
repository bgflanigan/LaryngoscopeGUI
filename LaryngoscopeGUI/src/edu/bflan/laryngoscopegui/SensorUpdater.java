package edu.bflan.laryngoscopegui;

public interface SensorUpdater {
	public void registerSensor(Sensor toAdd);
	public void start();
	public void stop();
}
