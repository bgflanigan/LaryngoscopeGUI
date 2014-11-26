package edu.bflan.laryngoscopegui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TimerPane extends JPanel {
	long elapsedTime;
	long lastSampleTime;
	boolean isRunning;

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(287, 27);
	}
	@Override
	public void paintComponent(Graphics g) {
		update();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1000, 1000);
		g.setColor(Color.RED);
		g.setFont(g.getFont().deriveFont(Font.BOLD, 22));
		long minutes = getTime()/60000;
		long seconds = (getTime()/1000)%60;
		long milliseconds = getTime()%1000;
		g.drawString(String.format("%02d:%02d:%03d",minutes, seconds, milliseconds), 5, 22);
	}
	public void start() {
		isRunning = true;
		lastSampleTime = System.currentTimeMillis();
	}

	public void stop() {
		isRunning = false;
	}
	
	public void reset() {
		elapsedTime = 0;	
	}
	
	public long getTime() {
		return elapsedTime;
	}
	
	public void update() {
		long currentTime = System.currentTimeMillis();
		if (isRunning){
			long dt = currentTime - lastSampleTime;
			elapsedTime += dt;
		}
		lastSampleTime = currentTime;
	}
}

