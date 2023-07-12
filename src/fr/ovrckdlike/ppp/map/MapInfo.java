package fr.ovrckdlike.ppp.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MapInfo {
	private String name;
	private int difficulty;	// between 1 (easy) and 3 (hard)
	private int maxScore;
	private int mapNum;
	private MapType type;
	

	public MapInfo(int map) {
		mapNum = map;
		File infoFile = new File("res/maps/map"+map+"/data.txt");
		try(BufferedReader input = new BufferedReader(new FileReader(infoFile))) {
			
			String line;
			while ((line = input.readLine()) != null) {
				line = line.split("#")[0];	// ignore comments
				if (line.contains("name")) name = line.split(":")[1];
				if (line.contains("difficulty")) difficulty = Integer.parseInt(line.split(":")[1]);
				if (line.contains("maxScore")) maxScore = Integer.parseInt(line.split(":")[1]);
				if (line.contains("type")) type = MapType.valueOf(line.split(":")[1]);
			}
			
		} catch (FileNotFoundException e) {
			System.err.print("file "+ infoFile + " not found");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			System.err.print("unable to read "+ infoFile);
			e.printStackTrace();
			System.exit(0);
		}
	}

	public String getName() {
		return name;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public int getMapNum() {
		return mapNum;
	}

	public MapType getType() {
		return type;
	}
}
