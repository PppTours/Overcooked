package fr.ovrckdlike.ppp.scene;


import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import java.util.List;

import fr.ovrckdlike.ppp.gameplay.RecipeScheduler;
import fr.ovrckdlike.ppp.graphics.KeyListener;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.objects.Ingredient;
import fr.ovrckdlike.ppp.objects.IngredientContainer;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Plate;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.tiles.ContainerTile;
import fr.ovrckdlike.ppp.tiles.Dryer;
import fr.ovrckdlike.ppp.tiles.GasCooker;
import fr.ovrckdlike.ppp.tiles.PlateReturn;
import fr.ovrckdlike.ppp.tiles.Sink;
import fr.ovrckdlike.ppp.tiles.Tile;

public class GameScene extends Scene {
	private static GameScene game;
	
	private List<Item> itemList;
	private List<Tile> tileList;
	private List<Player> playerList;
	private RecipeScheduler recSch;
	
	private byte plateToReturn;
	
	
	private int mapNum;
	private Map map;
	
		
	public static GameScene get() {
		if (game == null) {
			game = new GameScene();
		}
		return game;
	}
	
	public static void deleteItem(Item i) {
		game.itemList.remove(i);
	}
	
	public static List<Player> getPlayers() {
		return game.playerList;
	}
	
	public static boolean isRunning() {
		return game.running;
	}
	
	public static void addPlateToReturn() {
		game.plateToReturn++;
	}
	
	
	public static void setRunning(boolean run) {
		game.running = run;
	}
	
	
	private GameScene() {
		
		running = false;
		this.mapNum = 0;
		this.map = Map.get();
		if (!Map.buildMap(mapNum))
			System.out.println("Error while building the map");
		
		Texture.loadForMapType(map.getType());
		
		itemList = map.getItemList();
		tileList = map.getTileList();
		playerList = map.getPlayerList();
		
		recSch = RecipeScheduler.get();
		recSch.setRecSet(0);
		
		plateToReturn = 0;
	}
	
	public void render() {
		
		
		for (Player p:playerList) {
			p.render();
		}
		for (Tile tile : tileList) {
			tile.render();
		}
		for (Item item : itemList) {
			item.render();
		}
		recSch.render();
	}
	
	public void run() {
		boolean flagDropP1 = true;
		boolean flagDropP2 = true;
			
		
		for (Item item : itemList) {
			item.collide(playerList, itemList, tileList);
		}
		for (Player player:playerList) {
			player.collide(tileList, playerList);
		}
		
		if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
			System.exit(0);
		}
		
		recSch.run();
		
		for(Tile tile:tileList) {
			if (tile instanceof GasCooker) {
				((GasCooker) tile).cook();
			}
		}
		
		for (Player p:playerList) {
			p.updateKeyPressed();
			
			for (Tile tile:tileList) {
				if (tile instanceof ContainerTile) {
						
					if ((tile).isInTile(p.whereToDrop()) && Time.get().timeSince(p.getLastPickDrop()) > 0.25 && p.getPickDrop()) {
						p.resetLastPickDrop();
						Item item = p.getInHand();
						ContainerTile cTile = (ContainerTile) tile;
						
						if (item instanceof Ingredient && (cTile).getContent() instanceof IngredientContainer) {
							if (((IngredientContainer)(cTile).getContent()).fill((Ingredient) item)) {
								itemList.remove(item);
								p.drop();
							}
							else {
								p.drop();
								item = (cTile).takeOrDrop(item);
								p.take(item);
							}
						}
						else if (item instanceof IngredientContainer && ((ContainerTile) tile).getContent() instanceof Ingredient) {
							if (((IngredientContainer) item).fill(((Ingredient)(cTile).getContent()))) {
								itemList.remove(cTile.takeOrDrop(null));
							}
							else {
								p.drop();
								item = (cTile).takeOrDrop(item);
								p.take(item);
							}
						}
						else {
							p.drop();
							item = (cTile).takeOrDrop(item);
							p.take(item);
						}
						
					}
				}
				
				if (tile instanceof Dryer && p.getInHand() == null) {
					if (tile.isInTile(p.whereToDrop()) && Time.get().timeSince(p.getLastPickDrop()) > 0.25 && p.getPickDrop()) {
						((Dryer) tile).takePlate(p);
					}
				}
				
				if (tile instanceof Sink && p.getInHand() instanceof Plate) {
					if (tile.isInTile(p.whereToDrop()) && Time.get().timeSince(p.getLastPickDrop()) > 0.25 && p.getPickDrop()) {
						if (((Plate)p.getInHand()).getDirty()) {
							Sink sink = (Sink) tile;
							sink.addPlate();
							itemList.remove(p.getInHand());
							p.drop();
						}
					}
					if (tile.isInTile(p.whereToDrop()) && p.getInteract()) {
						tile.use(p);
					}
				}
				
				if (plateToReturn > 0) {
					if (tile instanceof PlateReturn) {
						((PlateReturn) tile).addPlate();
						plateToReturn--;
					}
				}	
			}
			
			
			for (Tile tile: tileList) {
				if (p.getPickDrop() && tile.isInTile(p.whereToDrop())) flagDropP1 = false;
				if (p.getInteract()) {
					if (tile.isInTile(p.whereToDrop())) {
						tile.use(p);
					}
				}
				if (!p.getInteract()) {
					p.unlockMove();
				
				}
			}
			
			if (p.getPickDrop() && Time.get().timeSince(p.getLastPickDrop()) > 0.25 && flagDropP1) {
				p.resetLastPickDrop();
				if (p.getInHand() == null) {
					p.takeNearestItem(itemList);
				}
				else {
					p.drop();
				}
			}
		}
	}
}
