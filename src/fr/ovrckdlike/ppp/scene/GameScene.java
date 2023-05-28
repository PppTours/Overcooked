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
import fr.ovrckdlike.ppp.physics.Dot;
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
	
	public void reset() {
		map.clearMap();
		recSch.reset();
		itemList = map.getItemList();
		tileList = map.getTileList();
		playerList = map.getPlayerList();
		
		this.mapNum = 0;			// passer la map en param
		this.map = Map.get();
		if (!Map.buildMap(mapNum))
			System.out.println("Error while building the map");
		
		Texture.loadForMapType(map.getType());
		
		
		recSch = RecipeScheduler.get(); //TODO verifier le reset
		
	}
	
	public static void deleteItem(Item i) {
		game.itemList.remove(i);
	}
	
	public static List<Player> getPlayers() {
		return game.playerList;
	}
	
	public static void addPlateToReturn() {
		game.plateToReturn++;
		System.out.println(game.plateToReturn);
	}
	
	private GameScene() {
		this.mapNum = 0; 		// change map here
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
					
		
		if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
			SceneManager.get().pauseGame();
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
				
				//player can exchange items with this tile
				if (tile instanceof ContainerTile) {
					
					//player is in range, press the key and cooldown is ok
					if (tile.getSpace().includes(p.whereToDrop()) && Time.get().timeSince(p.getLastPickDrop()) > 0.25 && p.getPickDrop()) {
						p.resetLastPickDrop();
						Item item = p.getInHand();
						ContainerTile cTile = (ContainerTile) tile;
						
						//player wants to merge ingredient in hand with container in tile
						if (item instanceof Ingredient && (cTile).getContent() instanceof IngredientContainer) {
							//merge is successful (ingredients corresponds
							if (((IngredientContainer)(cTile).getContent()).fill((Ingredient) item)) {
								itemList.remove(item);
								p.drop();
							}
							//merge aborted, player exchange the item in hand with the content of tile
							else {
								System.out.println("fezfze");
								p.drop();
								item = (cTile).takeOrDrop(item);
								p.take(item);
							}
						}
						
						//player wants to merge ingredient in tile with container in hand
						else if (item instanceof IngredientContainer && ((ContainerTile) tile).getContent() instanceof Ingredient) {
							//successful
							if (((IngredientContainer) item).fill(((Ingredient)(cTile).getContent()))) {
								itemList.remove(cTile.takeOrDrop(null));
							}
							//aborted
							else {
								p.drop();
								item = (cTile).takeOrDrop(item);
								p.take(item);
							}
						}
						//player exchange item in hand with tile content
						else {
							p.drop();
							item = (cTile).takeOrDrop(item);
							p.take(item);
						}
						
					}
				}
				
				// player take a plate in the dryer
				if (tile instanceof Dryer && p.getInHand() == null) {
					if (tile.isInTile(p.whereToDrop()) && Time.get().timeSince(p.getLastPickDrop()) > 0.25 && p.getPickDrop()) {
						((Dryer) tile).takePlate(p);
					}
				}
				
				//player drop a plate in sink
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
		for (Item item : itemList) {
			item.collideEntity(playerList);
			item.collideEntity(itemList);
			item.collideTile(tileList);
		}

		for (Player player:playerList) {
			player.collideTile(tileList);
			player.collidePlayer(playerList);
			
		}
	}
}
