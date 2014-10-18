package edu.bflan.laryngoscopegui;

public class Sensor {
	private final char identifier;
	private float currentForce = 0;

	public Sensor(char identifier) {
		this.identifier = identifier;
	}
	public void setForce(float force) {
		currentForce=force;
		System.out.println(identifier+":"+force);
		
	}

	public char getIdentifier() {
		return identifier;
	}

}
