package fr.ovrckdlike.ppp.scene;


import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.objects.*;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.KeyListener;
import fr.ovrckdlike.ppp.graphics.Renderer;

import java.util.*;



import static org.lwjgl.glfw.GLFW.*;

public class GameScene extends Scene {
	private Player player1;
	private Player player2;
	
	private long lastP1Action1;
	private long lastP2Action1;
	
	private Item salade;
	
	private ArrayList<Item> itemList = new ArrayList<Item>();
	private ArrayList<Tile> tileList = new ArrayList<Tile>();
	
	public static GameScene game = new GameScene();
	
	public GameScene() {
		float posp1[] = {300, 1080/2};
		float posp2[] = {300, 1080/2};
		float posSalade[] = {500, 500};
		
		this.lastP1Action1 = 0;
		this.lastP2Action1 = 0;
		
		
		
		this.player1 = new Player( posp1 );
		this.player2 = new Player( posp2 );
		this.salade = new Ingredient(1, posSalade);
		itemList.add(salade);
	}
	
	public void render() {
		this.player1.render();
		this.player2.render();
		this.salade.render();
		
	}
	
	public void run() {
		boolean p1up = KeyListener.get().isKeyPressed(GLFW_KEY_W);
		boolean p1down = KeyListener.get().isKeyPressed(GLFW_KEY_S);
		boolean p1left = KeyListener.get().isKeyPressed(GLFW_KEY_A);
		boolean p1right = KeyListener.get().isKeyPressed(GLFW_KEY_D);
		boolean p1action1 = KeyListener.get().isKeyPressed(GLFW_KEY_TAB);
		
		boolean p2up = KeyListener.get().isKeyPressed(GLFW_KEY_UP);
		boolean p2down = KeyListener.get().isKeyPressed(GLFW_KEY_DOWN);
		boolean p2left = KeyListener.get().isKeyPressed(GLFW_KEY_LEFT);
		boolean p2right = KeyListener.get().isKeyPressed(GLFW_KEY_RIGHT);
		boolean p2action1 = KeyListener.get().isKeyPressed(GLFW_KEY_RIGHT_CONTROL);
		
		
		if ( p1up || p1down || p1left || p1right ) {
			player1.changeAngle(p1up, p1down, p1left, p1right);
			player1.movePlayer(Time.get().getDt());
		}
		
		if ( p2up || p2down || p2left || p2right ) {
			player2.changeAngle(p2up, p2down, p2left, p2right);
			player2.movePlayer(Time.get().getDt());
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
		
	}

}
