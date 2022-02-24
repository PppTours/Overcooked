package fr.ovrckdlike.ppp.scene;


import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.objects.*;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.tiles.*;
import fr.ovrckdlike.ppp.graphics.*;
import fr.ovrckdlike.ppp.gui.Text;

import java.util.*;



import static org.lwjgl.glfw.GLFW.*;

public class GameScene extends Scene {
	private int map;
	
	private Player player1;
	private Player player2;
	
	private long lastP1Action1;
	private long lastP2Action1;
	private long lastP1Action2;
	private long lastP2Action2;
	
	
	private ArrayList<Item> itemList = new ArrayList<Item>();
	private ArrayList<Tile> tileList = new ArrayList<Tile>();
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private ArrayList<ContainerTile> containerTileList = new ArrayList<ContainerTile>();
	
	private Table temp;
	private Table temp2;
	private Pot tempPot;
	private Pot tempIng;
	
	//public static GameScene game = new GameScene();
	
	public GameScene() {
		this.map = 0;
		
		float posp1[] = {200, 1080/2};
		float posp2[] = {300, 1080/2};		

		
		this.lastP1Action1 = 0;
		this.lastP2Action1 = 0;
		this.lastP1Action2 = 0;
		this.lastP2Action2 = 0;

		
		this.player1 = new Player( posp1 );
		this.player2 = new Player( posp2 );
		
		playerList.add(player1);
		playerList.add(player2);
		
	}
	
	public void render() {
		this.player1.render();
		this.player2.render();
		
		temp.render();
		temp2.render();
		
		for (Tile tile : tileList) {
			tile.render();
		}
		for (Item item : itemList) {
			item.render();
		}
	}
	
	public void run() {
		boolean p1up = KeyListener.isKeyPressed(GLFW_KEY_W);
		boolean p1down = KeyListener.isKeyPressed(GLFW_KEY_S);
		boolean p1left = KeyListener.isKeyPressed(GLFW_KEY_A);
		boolean p1right = KeyListener.isKeyPressed(GLFW_KEY_D);
		boolean p1action1 = KeyListener.isKeyPressed(GLFW_KEY_TAB);
		boolean p1action2 = KeyListener.isKeyPressed(GLFW_KEY_F);
		boolean p1dash = KeyListener.isKeyPressed(GLFW_KEY_SPACE);
		
		boolean p2up = KeyListener.isKeyPressed(GLFW_KEY_UP);
		boolean p2down = KeyListener.isKeyPressed(GLFW_KEY_DOWN);
		boolean p2left = KeyListener.isKeyPressed(GLFW_KEY_LEFT);
		boolean p2right = KeyListener.isKeyPressed(GLFW_KEY_RIGHT);
		boolean p2action1 = KeyListener.isKeyPressed(GLFW_KEY_RIGHT_CONTROL);
		boolean p2action2 = KeyListener.isKeyPressed(GLFW_KEY_KP_0);
		boolean p2dash = KeyListener.isKeyPressed(GLFW_KEY_ENTER);
		
		
		if ( p1up || p1down || p1left || p1right ) {
			player1.changeAngle(p1up, p1down, p1left, p1right);
			player1.movePlayer(Time.get().getDt());
		}
		
		if ( p2up || p2down || p2left || p2right ) {
			player2.changeAngle(p2up, p2down, p2left, p2right);
			player2.movePlayer(Time.get().getDt());
		}
		
		for (Item item : itemList) {
			item.collide(playerList, itemList, tileList);

		}
		for (Player player:playerList) {
			player.collide(tileList, playerList);
			
		}
		
		for (ContainerTile tile:containerTileList) {
			if (tile.isInTile(player1.whereToDrop()) && Time.get().timeSince(lastP1Action2) > 0.25 && p1action2) {
				lastP1Action2 = Time.get().getCurrentTime();
				Item item = player1.getInHand();
				player1.drop();
				item = tile.takeOrDrop(item);
				player1.take(item);
			}
			if (tile.isInTile(player2.whereToDrop()) && Time.get().timeSince(lastP2Action2) > 0.25 && p2action2) {
				lastP2Action2 = Time.get().getCurrentTime();
				Item item = player2.getInHand();
				player2.drop();
				item = tile.takeOrDrop(item);
				player2.take(item);
			}
		}
		
		
		if (p1action1 && Time.get().timeSince(lastP1Action1) > 0.25) {
			lastP1Action1 = Time.get().getCurrentTime();
			if (player1.getInHand() == null) {
				this.player1.takeNearestItem(itemList); 
			}
			else {
				player1.drop();
			}
		}
		
		if (p2action1 && Time.get().timeSince(lastP2Action1) > 0.25) {
			lastP2Action1 = Time.get().getCurrentTime();
			if (player2.getInHand() == null) {
				this.player2.takeNearestItem(itemList);
			}
			else {
				player2.drop();
			}
		}
		if (p1dash) player1.dash(Time.get().getDt());
		else player1.releaseDash(Time.get().getDt());
		
		if (p2dash) player2.dash(Time.get().getDt());
		else player2.releaseDash(Time.get().getDt());
			
	
		
	}
}
