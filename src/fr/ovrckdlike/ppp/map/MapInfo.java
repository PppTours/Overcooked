package fr.ovrckdlike.ppp.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * A class that represents the information of a map.
 */
public class MapInfo {
  /**
   * Name of the map.
   */
  private String name;

  /**
   * Difficulty of the map.
   * Between 1 (easy) and 3 (hard)
   */
  private int difficulty;

  /**
   * Maximum score of the map.
   */
  private int maxScore;
  /**
   * Number of the map.
   */
  private final int mapNum;
  /**
   * Type of the map.
   *
   * @see MapType
   */
  private MapType type;

  /**
   * Constructor of the MapInfo class. Needs the number of the map.
   *
   * @param map the number of the map.
   */
  public MapInfo(int map) {
    mapNum = map;
    File infoFile = new File("res/maps/map" + map + "/data.txt");
    try (BufferedReader input = new BufferedReader(new FileReader(infoFile))) {
      String line;
      while ((line = input.readLine()) != null) {
        // ignore comments
        line = line.split("#")[0];
        if (line.contains("name")) {
          name = line.split(":")[1];
        }
        if (line.contains("difficulty")) {
          difficulty = Integer.parseInt(line.split(":")[1]);
        }
        if (line.contains("maxScore")) {
          maxScore = Integer.parseInt(line.split(":")[1]);
        }
        if (line.contains("type")) {
          type = MapType.valueOf(line.split(":")[1]);
        }
      }

    } catch (FileNotFoundException e) {
      System.err.print("file " + infoFile + " not found");
      e.printStackTrace();
      System.exit(0);
    } catch (IOException e) {
      System.err.print("unable to read " + infoFile);
      e.printStackTrace();
      System.exit(0);
    }
  }

  /**
   * Getter of the name of the map.
   *
   * @return the name of the map.
   */
  public String getName() {
    return name;
  }

  /**
   * Getter of the difficulty of the map.
   *
   * @return the difficulty of the map.
   */
  public int getDifficulty() {
    return difficulty;
  }

  /**
   * Getter of the maximum score of the map.
   *
   * @return the maximum score of the map.
   */
  public int getMaxScore() {
    return maxScore;
  }

  /**
   * Getter of the number of the map.
   *
   * @return the number of the map.
   */
  public int getMapNum() {
    return mapNum;
  }

  /**
   * Getter of the type of the map.
   *
   * @return the type of the map.
   */
  public MapType getType() {
    return type;
  }
}
