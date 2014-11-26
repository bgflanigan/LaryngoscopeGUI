package edu.bflan.laryngoscopegui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class SensorRenderer {

	private float threshold;
	private Sensor sensor;


	public SensorRenderer(Sensor sensor) {
		this.threshold=sensor.getThreshold();
		this.sensor = sensor;

	}
	
	public void repaint(Graphics g,FontMetrics fontMetrics, int width, int height) {
		int barWidth = width/3;
		int barHeight = height/2;
		int barTop = width/3;
		int dotDepth = barTop+barHeight;
		int dotRadius = barWidth/2;
		int textHeight = 14;
		int dotLabelDepth = barTop+barHeight+dotRadius+17;
		int forceTextDepth = height - (height - (dotLabelDepth+textHeight))/2 - 6;
		float force = sensor.getForce();
		int forceBarHeight = Math.min((int)(barHeight*force/threshold),barHeight);
		g.setColor(Color.black);
		drawCenteredString(""+threshold, g, fontMetrics, 20);
		g.setColor(Color.red);
		g.fillRect(-barWidth/2, dotDepth-forceBarHeight, barWidth, forceBarHeight);
		g.setColor(Color.black);
		g.drawRect(- barWidth/2, barTop, barWidth, barHeight);
		if (force >= threshold) {
			g.setColor(new Color(.75f,0.0f,0.0f));
		} else { 
			g.setColor(Color.green);
		}
		g.fillOval(-dotRadius, dotDepth-dotRadius, 2*dotRadius, 2*dotRadius);
		g.setColor(Color.black);
		drawCenteredString(sensor.getSensorName(),g,fontMetrics, dotLabelDepth);
		drawCenteredString(String.format("%.2f",force), g, fontMetrics,forceTextDepth);
	}
	public void drawCenteredString(String text,Graphics g, FontMetrics fontMetrics, int yCoord) {
		int textWidth = fontMetrics.stringWidth(text);
		g.drawString(text,-textWidth/2, yCoord);
	}
}
