package edu.bflan.laryngoscopegui;

import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class SensorFeedbackChart extends JPanel {
	private static final long serialVersionUID = -136660975865195255L;
	
	List<SensorRenderer> renderers;
	public SensorFeedbackChart(List<SensorRenderer> renderers) {
		this.renderers = renderers;
	}
	
	public void paintComponent(Graphics g) {
		int translationDistance = this.getWidth()/(renderers.size()*2);
		
		for(SensorRenderer renderer : renderers) {
			g.translate(translationDistance,0);
			renderer.repaint(g,this.getFontMetrics(g.getFont()),this.getWidth()/renderers.size(),this.getHeight());
			g.translate(translationDistance,0);
		}
		
		
	}
}
