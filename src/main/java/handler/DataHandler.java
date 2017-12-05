package handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import entities.Player;

/*
 * This class handles reading/writing data to files
 */

public class DataHandler {
	
	public static final String dir = System.getProperty("user.home") + "//TMMOJ"; 
	
	public static void init() {
		File file = new File(dir); // Store the directory
		boolean exists = file.exists();
		if (!exists) { // Check if it existsFile yourFile = new File("score.txt");
			file.mkdir();
			// Create a blank player file
			Object[] data = {40, 40, 200, 0, 0};
			writeToFile(dir + "//player.gme", false, data);
		}
		
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
		Object[] data = {player.getPosition().x, player.getPosition().y, player.getHealth(), player.getXScroll(), player.getYScroll()};
		writeToFile(dir + "//player.gme", false, data);
	}

}
