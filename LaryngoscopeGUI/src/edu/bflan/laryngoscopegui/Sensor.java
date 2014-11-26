package edu.bflan.laryngoscopegui;

public class Sensor {
	private final char identifier;
	private float currentForce = 0;
	private String sensorName;
	private float forceThreshold;
	private float maxForce;

	public Sensor(char identifier, String sensorName, float forceThreshold) {
		this.identifier = identifier;
		this.sensorName = sensorName;
		this.forceThreshold = forceThreshold;
	}
	public void setForce(float force) {
		if (force > maxForce) {
			maxForce = force;
		}
		currentForce=force;
		
	}

	public char getIdentifier() {
		return identifier;
	}
	public float getForce() {
		return currentForce;
	}
	public String getSensorName() {
		return sensorName;
	}
	public float getThreshold() {
		return forceThreshold;
	}
	public float getMaxForce() {
		return maxForce;
	}

}
