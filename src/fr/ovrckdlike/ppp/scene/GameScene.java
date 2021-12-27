package fr.ovrckdlike.ppp.scene;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.objects.Player;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.graphics.KeyListener;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.internal.Texture;


import static org.lwjgl.glfw.GLFW.*;

public class GameScene extends Scene {
	private Player player1;
	private Player player2;
	
	public static GameScene game = new GameScene();
	
	public GameScene() {
		float posp1[] = {300, 1080/2};
		float posp2[] = {300, 1080/2};
		
		this.player1 = new Player( posp1 );
		this.player2 = new Player( posp2 );
		
	}
	
	
	
	
	public void render() {
		Renderer.drawQuad(1920/2, 1080/2, 120, 120, Color.red);		
		Renderer.drawTexture(player1.getPos()[0], player1.getPos()[1], 100, 100, Texture.smiley);
		Renderer.drawTexture(player2.getPos()[0], player2.getPos()[1], 150, 150, Texture.smiley);
	}
	
	public void run() {
		boolean p1up = KeyListener.get().isKeyPressed(GLFW_KEY_W);
		boolean p1down = KeyListener.get().isKeyPressed(GLFW_KEY_S);
		boolean p1left = KeyListener.get().isKeyPressed(GLFW_KEY_A);
		boolean p1right = KeyListener.get().isKeyPressed(GLFW_KEY_D);
		
		boolean p2up = KeyListener.get().isKeyPressed(GLFW_KEY_UP);
		boolean p2down = KeyListener.get().isKeyPressed(GLFW_KEY_DOWN);
		boolean p2left = KeyListener.get().isKeyPressed(GLFW_KEY_LEFT);
		boolean p2right = KeyListener.get().isKeyPressed(GLFW_KEY_RIGHT);
		
		
		if ( p1up || p1down || p1left || p1right ) {
			player1.changeAngle(p1up, p1down, p1left, p1right);
			player1.movePlayer(Time.get().getDt());
		}
		
		if ( p2up || p2down || p2left || p2right ) {
			player2.changeAngle(p2up, p2down, p2left, p2right);
			player2.movePlayer(Time.get().getDt());
		}
	}

}
