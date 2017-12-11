package handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;

import entities.Player;

/*
 * This class handles reading/writing data to files
 */

public class DataHandler {
	
	public static final String dir = System.getProperty("user.home") + "\\TMMOJ";
	public static final String inventFile = dir + "\\inventory.gme";
	public static final String version = "1.0.1";
	
	public static void init() {
		File file = new File(dir); // Store the directory
		boolean exists = file.exists();
		if (!exists) initiate(file);
		else {
			String[] data = readFile(dir + "\\player.gme");
			String version = data[0];
			if (!version.equals(DataHandler.version)) {
				initiate(file);
			}
		}
	}
	
	private static void initiate(File file) {
		file.mkdir();
		// Create a blank player file
		// x, y, health, xScroll
		Object[] data = {version, 105, 105, 200, 0};
		writeToFile(dir + "\\player.gme", false, data);
		Object[] data2 = {"1-20-8", "2-20-9", "3-20-10"};
		writeToFile(inventFile, false, data2);
	}
	
	public static boolean exists(String file) {
		File f = new File(dir); // Store the directory
		return f.exists();
	}
	
	public static void createDirectory(String dir) {
		File file = new File(dir);
		boolean exists = file.exists();
		if (!exists) file.mkdir();
	}
	
	public static void createFile(String file) {
		Object[] data = {""};
		writeToFile(file, false, data);
	}
	
	public static void writeToFile(String file, boolean append, Object[] data) {
		try {
			FileWriter writer = new FileWriter(file, append);
			PrintWriter printLine = new PrintWriter(writer);
			for (Object j : data) printLine.printf("%s" + "%n", j);
			printLine.close();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public static void writeToFile(String file, boolean append, String data) {
		try {
			FileWriter writer = new FileWriter(file, append);
			PrintWriter printLine = new PrintWriter(writer);
			printLine.printf("%s" + "%n", data);
			printLine.close();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public static String[] readFile(String file) {
		String[] data = new String[getLineNum(file)];
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
			String line;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				data[i++] = line;
			}
			reader.close();
		} catch (Exception e) { e.printStackTrace(); }
		return data;
	}
	
	public static int getLineNum(String file) {
		int num = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
			while (reader.readLine() != null) num++;
			reader.close();
		} catch (Exception e) { e.printStackTrace(); }
		return num;
	}
	
	public static void savePlayer(Player player) {
		Object[] data = {version, player.getPosition().x, player.getPosition().y, player.getHealth(), player.getMap().getX()};
		writeToFile(dir + "//player.gme", false, data);
		Object[] invData = player.getInvent().getData();
		writeToFile(inventFile, false, invData);
	}

}
