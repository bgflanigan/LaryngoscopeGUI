package edu.bflan.laryngoscopegui;

public class MockSensorUpdater extends AbstractSensorUpdater {
	private int counter = 0;
	private final char[] charCodes;
	public MockSensorUpdater(char[] charCodes) {
		this.charCodes = charCodes;
	}
	@Override
	public void setup() {

	}

	@Override
	public void update() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		counter++;
		
		for (char c: charCodes) {
			updateSensor(c,100*(float)(counter%c)/c);
		}
		//for each character in charCodes, update force

	}

}
