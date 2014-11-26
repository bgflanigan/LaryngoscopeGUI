package edu.bflan.laryngoscopegui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

public class CaptureThread implements Runnable {

	private volatile boolean running = false;
	private Thread captureThread;
	private List<Sensor> sensors;
	private TimerPane timer;
	private File outfile;
	private BufferedWriter printer;
	private Mode mode;
	
	public CaptureThread(TimerPane timer, List<Sensor> sensors, Mode mode) {
		captureThread = new Thread(this);
		this.sensors = sensors;
		this.timer = timer;
		captureThread.setDaemon(true);
		outfile = new File(".tmp.swp");
		this.mode = mode;
	}

	public void start() throws IOException {
		running = true;
		printer = new BufferedWriter(new FileWriter(outfile));
		captureThread.start();
	}
	
	@Override
	public void run() {
		try {
			printer.write("Time");
			for(Sensor s : sensors) {
				printer.write(","+s.getSensorName());
			}
			printer.write("\n");
			while(running) {
				long time = timer.getTime();
				printer.write(""+time);
				for (Sensor s : sensors) {
					float force = s.getForce();
					if (force > s.getThreshold())
						mode.onSensorBreached(s);
					printer.write(","+force);
				}
				printer.write("\n");
				while(running && timer.getTime() - time < 50); // wait for 50 ms (20 Hz)
			}
		} catch (IOException e) {
			System.out.println("Fatal error in capture. Aborting.");
			e.printStackTrace();
		}
	}
	
	public void stop() {
		running = false;
		try {
			captureThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			printer.close();
		} catch(Exception e) {}
	}

	public void saveAs(String filename) throws IOException {
		File saveFile = new File(filename);
		copyFile(outfile, saveFile);
	}

	public static void copyFile(File sourceFile, File destFile) throws IOException {
		if(!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;

		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		}
		finally {
			if(source != null) {
				source.close();
			}
			if(destination != null) {
				destination.close();
			}
		}
	}

	public String getResultOverview() {
		StringBuilder overview = new StringBuilder();
		for (Sensor s : sensors) {
			float force = s.getMaxForce();
			float thresh = s.getThreshold();
			float percent = force * 100 / thresh;
			overview.append('\n')
					.append(s.getSensorName()).append('\n')
					.append("----------------------------------\n")
					.append("\tMax Force: ").append(force).append("lbs\n")
					.append("\tThreshold: ").append(thresh).append("lbs\n")
					.append("\tPercent of Threshold Reached: ").append(percent).append("%\n");
		}
		return overview.toString();
	}
}
