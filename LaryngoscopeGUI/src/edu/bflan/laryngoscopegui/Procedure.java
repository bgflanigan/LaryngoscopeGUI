package edu.bflan.laryngoscopegui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Procedure {
	public static List<Procedure> loadProcedures(File file) throws IOException {
		try (FileReader fr = new FileReader(file); BufferedReader reader = new BufferedReader(fr)) {
			List<Procedure> procedures = new ArrayList<>();
			HashSet<Character> chars = new HashSet<>();
			Procedure currentProcedure = null;
			int lineNo = 0;
			String line;
			while((line = reader.readLine()) != null) {
				lineNo++;
				line = line.trim();
				if (line.length() == 0 || line.charAt(0) == '#')
					continue;
				String[] lines = line.split(":");
				if ("procedure".equalsIgnoreCase(lines[0])) {
					if (lines.length != 2) {
						System.out.println("Error parsing "+file.getName()+" - on line "+lineNo+" - should be procedure:<name>");
						return null;
					}
					if (currentProcedure != null && currentProcedure.size() == 0) {
						System.out.println("Warning parsing "+file.getName()+" - Procedure "+currentProcedure.getName()+" contains no sensors");
					}
					currentProcedure = new Procedure(lines[1]);
					procedures.add(currentProcedure);
					chars.clear();
				} else if ("sensor".equalsIgnoreCase(lines[0])) {
					if (currentProcedure == null) {
						System.out.println("Error parsing "+file.getName()+" - on line "+lineNo+" - Must declare a procedure before declaring sensors");
						return null;
					}
					if (lines.length != 4) {
						System.out.println("Error parsing "+file.getName()+" - on line "+lineNo+" - should be sensor:<char>:<name>:<threshold>");
						return null;
					}
					if (lines[1].length() != 1) {
						System.out.println("Error parsing "+file.getName()+" - on line "+lineNo+" - identifier must be only one character. Found '"+lines[1]+"'");
						return null;
					}
					char ident = lines[1].charAt(0);
					String name = lines[2];
					float threshold;
					try {
						threshold = Float.parseFloat(lines[3]);
					} catch(NumberFormatException e) {
						System.out.println("Error parsing "+file.getName()+" - on line "+lineNo+" - threshold was not properly formatted");
						return null;
					}
					if (!chars.add(ident)) {
						System.out.println("Error parsing "+file.getName()+" - on line "+lineNo+" - Procedure "+currentProcedure.getName()+" contains multiple sensors with identifier "+ident);
						return null;
					}
					Sensor newSensor = new Sensor(ident, name, threshold);
					currentProcedure.addSensor(newSensor);
				}
			}
			if (currentProcedure == null) {
				System.out.println("Warning: file "+file.getName()+" contains no procedures!");
			} else if (currentProcedure.size() == 0) {
				System.out.println("Warning parsing "+file.getName()+" - Procedure "+currentProcedure.getName()+" contains no sensors");
				return null;
			}
			return procedures;
		}
	}

	private String procedureName;
	private List<Sensor> sensors;
	
	public Procedure(String procedureName) {
		this.procedureName = procedureName;
		this.sensors = new ArrayList<Sensor>();
	}
	
	private void addSensor(Sensor newSensor) {
		sensors.add(newSensor);
	}
	
	public int size() {
		return sensors.size();
	}

	public String getName() {
		return procedureName;
	}
	
	public List<Sensor> makeSensors() {
		return sensors;
	}
}
