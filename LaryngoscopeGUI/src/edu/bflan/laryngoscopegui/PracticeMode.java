package edu.bflan.laryngoscopegui;

public class PracticeMode implements Mode {

	@Override
	public String getName() {
		return "Practice Mode";
	}

	@Override
	public void onSensorBreached(Sensor s) {
		java.awt.Toolkit.getDefaultToolkit().beep();
	}

}
