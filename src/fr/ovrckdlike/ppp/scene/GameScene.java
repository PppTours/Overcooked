package fr.ovrckdlike.ppp.scene;


import java.util.List;

import fr.ovrckdlike.ppp.gameplay.RecipeScheduler;
import fr.ovrckdlike.ppp.map.Map;
import fr.ovrckdlike.ppp.objects.Ingredient;
import fr.ovrckdlike.ppp.objects.IngredientContainer;
import fr.ovrckdlike.ppp.objects.Item;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.tiles.ContainerTile;
import fr.ovrckdlike.ppp.tiles.PlateReturn;
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
	
	private Player player1;
	private Player player2;
	
	private long lastP1Action1;
	private long lastP2Action1;
	private long lastP1Action2;
	private long lastP2Action2;
	
		
	public static GameScene get() {
		if (game == null) {
			game = new GameScene();
		}
		return game;
	}
	
	public static Player[] getPlayers() {
		Player[] pList = new Player[2];
		pList[0] = game.player1;
		pList[1] = game.player2;
		return pList;
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
		if (!Map.buildMap(mapNum))		//TODO : remettre itemList
			System.out.println("Error while building the map");
		
		itemList = map.getItemList();
		tileList = map.getTileList();
		playerList = map.getPlayerList();
		
		recSch = RecipeScheduler.get();
		recSch.setRecSet(0);
		
		plateToReturn = 0;
		
		
		float posp1[] = {200, 1080/2};
		float posp2[] = {1720, 1080/2};

		
		this.lastP1Action1 = 0;
		this.lastP2Action1 = 0;
		this.lastP1Action2 = 0;
		this.lastP2Action2 = 0;

		
		this.player1 = new Player(posp1, (byte) 1);
		this.player2 = new Player(posp2, (byte) 2);
		
		playerList.add(player1);
		playerList.add(player2);
		
	}
	
	public void render() {
		
		player1.render();
		player2.render();
		
		
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
		
		recSch.run();
		
		for (Player p:playerList) {
			p.updateKeyPressed();
			
			for (Tile tile:tileList) {
				if (tile instanceof ContainerTile) {
						
					if ((tile).isInTile(p.whereToDrop()) && Time.get().timeSince(lastP1Action1) > 0.25 && p.getPickDrop()) {//TODO mettre les actions joueurs dans la classe player
						lastP1Action1 = Time.get().getCurrentTime();
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
