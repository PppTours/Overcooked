package fr.ovrckdlike.ppp.map;

import java.io.*;
import java.util.List;
import java.util.Scanner;


import fr.ovrckdlike.ppp.objects.*;
import fr.ovrckdlike.ppp.tiles.*;




public class Map {
	public static Map map = new Map();
	
	public Map() {
		
	}
	
	public void clearMap(List<Tile> tileList, List<Item> itemList, List<ContainerTile> containerTileList,List<Player> playerList) {
		tileList.clear();
		itemList.clear();
		containerTileList.clear();
		playerList.clear();
	}
	
	public static boolean buildMap(int numero, List<Tile> tileList, List<Item> itemList, List<ContainerTile> containerTileList) {
		File file = new File("res/maps/map"+numero+".csv");
		try {
			Scanner scan = new Scanner(file);
			int tilePosX = 0;
			int tilePosY = 0;
			while (scan.hasNext()) {
				String line = scan.next();
				String[] chunks = line.split(";", -1);
				for (String chunk:chunks) {
					int type = chunk.charAt(0) - '0';
					if (chunk.length() > 1 && chunk.charAt(1)>='a' && chunk.charAt(1)<='z') {
						char option = chunk.charAt(1);
						Tile tile;
						float[] tilePos = {tilePosX * 120f, tilePosY * 120f};
						
						
						int direction = 0;
						if (type == 7 || type == 4 || type == 5) {
							if (option == 'a') direction = 0;
							else if (option == 'b') direction = 1;
							else if (option == 'c') direction = 2;
							else direction = 3;
						}
						
						switch(type) {
						case 1:
							if (option == 'a') tile = new Table(tilePos, false, itemList);
							else tile = new Table(tilePos, true, itemList);
							break;
						case 3 :
							if (option == 'a') {
								float[] potPos = {0f, 0f};
								Pot onGas = new Pot(potPos);
								itemList.add(onGas);
								tile = new GasCooker(tilePos, onGas);
							}
							else {
								float[] panPos = {0f, 0f};
								Pot onGas = new Pot(panPos);
								itemList.add(onGas);
								tile = new GasCooker(tilePos, onGas);
							}
							break;
						case 4 :
							tile = new Sink(tilePos, direction);
							break;
						case 5 :
							tile = new Dryer(tilePos, direction);
							break;
						case 6 :
							option-= 'a';
							tile = new IngredientRefiller(tilePos, option);
							break;
						case 7 :
							tile = new ServiceTable(tilePos, direction);
							break;
						default :
							tileList.clear();
							return false;
						}
						if (tile != null) tileList.add(tile);
						if (type == 1 || type == 2 || type == 3 || type == 9 || type == 6) containerTileList.add((ContainerTile) (tile));
						
					}
					else {
						if (chunk.length() > 1) {
							type *= 10;
							type += chunk.charAt(1) - '0';
						}
						
						Tile tile;
						float[] tilePos = {tilePosX * 120f, tilePosY * 120f};
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
							tileList.clear();
							return false;
						}
						if (tile != null) tileList.add(tile);
						if (type == 1 || type == 2 || type == 3 || type == 9 || type == 6) containerTileList.add((ContainerTile) (tile));
					}
					tilePosX++;
				}
				tilePosX = 0;
				tilePosY++;
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
