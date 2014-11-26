package edu.bflan.laryngoscopegui;

public interface Mode {

	public String getName();
	public void onSensorBreached(Sensor s);

}
