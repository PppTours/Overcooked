package fr.ovrckdlike.ppp.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import fr.ovrckdlike.ppp.internal.Texture;
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




public class Map {
	public static Map map;
	
	private List<Tile> tileList;
	private List<Item> itemList;
	private List<Player> playerList;
	private MapType type;
	
	public static Map get() {
		if (map == null) map = new Map();
		return map;
	}
	
	private Map() {
		tileList = new ArrayList<Tile>();
		itemList = new ArrayList<Item>();
		playerList = new ArrayList<Player>();
	}
	
	public MapType getType() {
		return type;
	}
	
	public List<Tile> getTileList(){
		return tileList;
	}
	
	public List<Item> getItemList(){
		return itemList;
	}
	
	public List<Player> getPlayerList(){
		return playerList;
	}
	
	
	public void clearMap() {
		tileList.clear();
		itemList.clear();
		playerList.clear();
	}

	public static boolean buildMap(int numero) {
		Map.get();
		File file = new File("res/maps/map"+numero+"/map.csv");
		try {
			Scanner scan = new Scanner(file);
			int tilePosX = 0;
			int tilePosY = 0;
			HashMap<Character, Dryer> dryerMap = new HashMap();
			ArrayList<Sink> sinkList = new ArrayList();
			while (scan.hasNext()) {
				String line = scan.next();
				if(line.charAt(0) != '§') {
					String[] chunks = line.split(";", -1);
					for (String chunk:chunks) {
						int type = chunk.charAt(0) - '0';
						if (chunk.length() > 1 && chunk.charAt(1)>='a' && chunk.charAt(1)<='z') {
							char option = chunk.charAt(1);
							Tile tile;
							Dot tilePos = new Dot(tilePosX*120+60, tilePosY*120+60);
							
							
							int direction = 0;
							if (type == 7 || type == 4 || type == 5) {
								if (option == 'a') direction = 0;
								else if (option == 'b') direction = 1;
								else if (option == 'c') direction = 2;
								else direction = 3;
							}
							
							switch(type) {
							case 1:
								if (option == 'a') tile = new Table(tilePos, false);
								else tile = new Table(tilePos, true);
								break;
							case 3 :
								if (option == 'a') {
									Dot potPos = new Dot(0f, 0f);
									Pot onGas = new Pot(potPos);
									map.itemList.add(onGas);
									tile = new GasCooker(tilePos, onGas);
								}
								else {
									Dot panPos = new Dot(0f, 0f);
									Pan onGas = new Pan(panPos);
									map.itemList.add(onGas);
									tile = new GasCooker(tilePos, onGas);
								}
								break;
							case 4 :
								tile = new Sink(tilePos, direction);
								sinkList.add((Sink) tile);
								break;
							case 5 :
								tile = new Dryer(tilePos, direction);
								dryerMap.put((Character) option, (Dryer)tile);
								break;
							case 6 :
								option-= 'a';
								tile = new IngredientRefiller(tilePos, option);
								break;
							case 7 :
								tile = new ServiceTable(tilePos, direction);
								break;
							default :
								map.tileList.clear();
								return false;
							}
							if (tile != null) map.tileList.add(tile);
							
						}
						else {
							if (chunk.length() > 1) {
								type *= 10;
								type += chunk.charAt(1) - '0';
							}
							
							Tile tile;
							Dot tilePos = new Dot(tilePosX * 120f + 60, tilePosY * 120f + 60);
							switch(type) {
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
								return false;
							}
							if (tile != null) map.tileList.add(tile);
						}
						tilePosX++;
					}
					tilePosX = 0;
					tilePosY++;
				}
				else {
					String[] chunks = line.split(";", -1);
					float[] playerPosRaw = new float[4];
					for (int i = 0; i < 4; i++) {
						playerPosRaw[i] = Float.parseFloat(chunks[i+1]);
					}
					
					Dot posP1 = new Dot(playerPosRaw[0], playerPosRaw[1]);
					Dot posP2 = new Dot(playerPosRaw[2], playerPosRaw[3]);
					
					
					Player player1 = new Player(posP1, (byte) 1, HyperParameters.get().getSkinP1());
					Player player2 = new Player(posP2, (byte) 2, HyperParameters.get().getSkinP2());
					
					map.playerList.add(player1);
					map.playerList.add(player2);
					
					try {
						map.type = MapType.valueOf(chunks[5]);
					}
					catch (IllegalArgumentException e) {
						System.out.println("error detected in map file" +numero);
						map.type = MapType.DEFAULT;
					}
					
				}
				
				
			}
			for (Sink s:sinkList) {
				int dir = s.getDirection();
				if (dir == 0) s.setAttachedDryer(dryerMap.get('a'));
				else if (dir == 1) s.setAttachedDryer(dryerMap.get('b'));
				else if (dir == 2) s.setAttachedDryer(dryerMap.get('c'));
				else s.setAttachedDryer(dryerMap.get('d'));
			}
			
			scan.close();
			return true;
		} 
		catch (FileNotFoundException e) {
			System.out.println("failed to find " + file);
			return false;
		}
		
	}
}
