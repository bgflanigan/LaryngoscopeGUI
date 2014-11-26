package edu.bflan.laryngoscopegui;

public class TestMode implements Mode {

	@Override
	public String getName() {
		return "Test Mode";
	}

	@Override
	public void onSensorBreached(Sensor s) {
		// Do nothing
	}

}
