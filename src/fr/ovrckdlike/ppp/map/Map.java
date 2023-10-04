package fr.ovrckdlike.ppp.map;

import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Pan;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.objects.Pot;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.scene.HyperParameters;
import fr.ovrckdlike.ppp.tiles.Bin;
import fr.ovrckdlike.ppp.tiles.CuttingTable;
import fr.ovrckdlike.ppp.tiles.Dryer;
import fr.ovrckdlike.ppp.tiles.Furnace;
import fr.ovrckdlike.ppp.tiles.GasCooker;
import fr.ovrckdlike.ppp.tiles.IngredientRefiller;
import fr.ovrckdlike.ppp.tiles.PlateReturn;
import fr.ovrckdlike.ppp.tiles.ServiceTable;
import fr.ovrckdlike.ppp.tiles.Sink;
import fr.ovrckdlike.ppp.tiles.Table;
import fr.ovrckdlike.ppp.tiles.Tile;
import fr.ovrckdlike.ppp.tiles.Wall;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * A class that represents a level in the game.
 */
public class Map {
  /**
   * The instance of the map.
   */
  public static Map map;

  /**
   * The list of tiles in the map.
   */
  private final List<Tile> tileList;

  /**
   * The list of items in the map.
   */
  private final List<Item> itemList;

  /**
   * The list of players in the map.
   */
  private final List<Player> playerList;

  /**
   * The type of the map.
   */
  private MapType type;

  /**
   * Get the instance of the map.
   *
   * @return The instance of the map.
   */
  public static Map get() {
    if (map == null) {
      map = new Map();
    }
    return map;
  }

  /**
   * Constructor of Map.
   */
  private Map() {
    tileList = new ArrayList<>();
    itemList = new ArrayList<>();
    playerList = new ArrayList<>();
  }

  /**
   * Get the type of the map.
   *
   * @return The type of the map.
   */
  public MapType getType() {
    return type;
  }

  /**
   * Get the list of tiles in the map.
   *
   * @return The list of tiles in the map.
   */
  public List<Tile> getTileList() {
    return tileList;
  }

  /**
   * Get the list of items in the map.
   *
   * @return The list of items in the map.
   */
  public List<Item> getItemList() {
    return itemList;
  }

  /**
   * Get the list of players in the map.
   *
   * @return The list of players in the map.
   */
  public List<Player> getPlayerList() {
    return playerList;
  }


  /**
   * Clear the map.
   */
  public void clearMap() {
    tileList.clear();
    itemList.clear();
    playerList.clear();
  }

  /**
   * Build the map from a file.
   *
   * @param numero The number of the map.
   * @return True if the map is built, false otherwise.
   */
  public static boolean buildMap(int numero) {
    System.out.println("check");
    Map.get();
    File file = new File("res/maps/map" + numero + "/map.csv");
    try {
      Scanner scan = new Scanner(file);
      int tilePosX = 0;
      int tilePosY = 0;
      HashMap<Character, Dryer> dryerMap = new HashMap();
      ArrayList<Sink> sinkList = new ArrayList();
      while (scan.hasNext()) {
        String line = scan.next();
        String[] chunks = line.split(";", -1);
        if (line.charAt(0) != '$') {
          for (String chunk : chunks) {
            int type = chunk.charAt(0) - '0';
            if (chunk.length() > 1 && chunk.charAt(1) >= 'a' && chunk.charAt(1) <= 'z') {
              char option = chunk.charAt(1);
              Tile tile;
              Dot tilePos = new Dot(tilePosX * 120 + 60, tilePosY * 120 + 60);


              int direction = 0;
              if (type == 7 || type == 4 || type == 5) {
                if (option == 'b') {
                  direction = 1;
                } else if (option == 'c') {
                  direction = 2;
                } else if (option == 'd') {
                  direction = 3;
                }
              }

              switch (type) {
                case 1:
                  if (option == 'a') {
                    tile = new Table(tilePos, "");
                  } else if (option == 'c') {
                    tile = new Table(tilePos, "EXT");
                  } else {
                    tile = new Table(tilePos, "PLA");
                  }
                  break;
                case 3:
                  if (option == 'a') {
                    Dot potPos = new Dot(0f, 0f);
                    Pot onGas = new Pot(potPos);
                    map.itemList.add(onGas);
                    tile = new GasCooker(tilePos, onGas);
                  } else {
                    Dot panPos = new Dot(0f, 0f);
                    Pan onGas = new Pan(panPos);
                    map.itemList.add(onGas);
                    tile = new GasCooker(tilePos, onGas);
                  }
                  break;
                case 4:
                  tile = new Sink(tilePos, direction);
                  sinkList.add((Sink) tile);
                  break;
                case 5:
                  tile = new Dryer(tilePos, direction);
                  dryerMap.put((Character) option, (Dryer) tile);
                  break;
                case 6:
                  option -= 'a';
                  tile = new IngredientRefiller(tilePos, option);
                  break;
                case 7:
                  tile = new ServiceTable(tilePos, direction);
                  break;
                default:
                  map.tileList.clear();
                  return true;
              }
              if (tile != null) {
                map.tileList.add(tile);
              }
            } else {
              if (chunk.length() > 1) {
                type *= 10;
                type += chunk.charAt(1) - '0';
              }

              Tile tile;
              Dot tilePos = new Dot(tilePosX * 120f + 60, tilePosY * 120f + 60);
              switch (type) {
                case 0:
                  tile = null;
                  break;
                case 2:
                  tile = new CuttingTable(tilePos);
                  break;
                case 11:
                  tile = new Bin(tilePos);
                  break;
                case 8:
                  tile = new PlateReturn(tilePos);
                  break;
                case 9:
                  tile = new Furnace(tilePos);
                  break;
                case 10:
                  tile = new Wall(tilePos);
                  break;
                default:
                  System.out.println(type);
                  map.tileList.clear();
                  return true;
              }
              if (tile != null) {
                map.tileList.add(tile);
              }
            }
            tilePosX++;
          }
          tilePosX = 0;
          tilePosY++;
        } else {
          float[] playerPosRaw = new float[4];
          for (int i = 0; i < 4; i++) {
            playerPosRaw[i] = Float.parseFloat(chunks[i + 1]);
          }

          Dot posP1 = new Dot(playerPosRaw[0], playerPosRaw[1]);
          Dot posP2 = new Dot(playerPosRaw[2], playerPosRaw[3]);


          Player player1 = new Player(posP1, (byte) 1, HyperParameters.get().getSkinP1());
          Player player2 = new Player(posP2, (byte) 2, HyperParameters.get().getSkinP2());

          map.playerList.add(player1);
          map.playerList.add(player2);

          try {
            map.type = MapType.valueOf(chunks[5]);
            System.out.println(chunks[5]);
          } catch (IllegalArgumentException e) {
            System.out.println("error detected in map file" + numero);
            map.type = MapType.DEFAULT;
          }
        }
      }
      for (Sink s : sinkList) {
        int dir = s.getDirection();
        if (dir == 0) {
          s.setAttachedDryer(dryerMap.get('a'));
        } else if (dir == 1) {
          s.setAttachedDryer(dryerMap.get('b'));
        } else if (dir == 2) {
          s.setAttachedDryer(dryerMap.get('c'));
        } else {
          s.setAttachedDryer(dryerMap.get('d'));
        }
      }

      scan.close();
      return false;
    } catch (FileNotFoundException e) {
      System.out.println("failed to find " + file);
      return true;
    }
  }
}
