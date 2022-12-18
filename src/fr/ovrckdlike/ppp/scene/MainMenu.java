package fr.ovrckdlike.ppp.scene;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.KeyListener;
import fr.ovrckdlike.ppp.gui.Text;
import fr.ovrckdlike.ppp.physics.Time;

public class MainMenu extends Scene {
	private static MainMenu instance;
	private Text text;
	
	public static MainMenu get() {
		if (instance == null) {
			instance = new MainMenu();
		}
		return instance;
	}
	
	public void render() {
		text.changeSize(52 + (float)(2*Math.sin(Time.get().getCurrentTime()/(1E9f))));
		text.render();
	}
	
	public void run() {
		boolean left1 = KeyListener.isKeyPressed(GLFW_KEY_LEFT);
		boolean right1 = KeyListener.isKeyPressed(GLFW_KEY_RIGHT);
		boolean up1 = KeyListener.isKeyPressed(GLFW_KEY_UP);
		boolean down1 = KeyListener.isKeyPressed(GLFW_KEY_DOWN);
		boolean left2 = KeyListener.isKeyPressed(GLFW_KEY_A);
		boolean right2 = KeyListener.isKeyPressed(GLFW_KEY_D);
		boolean up2 = KeyListener.isKeyPressed(GLFW_KEY_W);
		boolean down2 = KeyListener.isKeyPressed(GLFW_KEY_S);
		
		boolean quit = KeyListener.isKeyPressed(GLFW_KEY_ESCAPE);
		
		
		if (left1 || right1 || up1 || down1 || left2 || right2 || up2 || down2) {
			SceneManager.get().setSceneToGame();
		}
		
		if (quit) {
			System.exit(0);
		}
	}
		

	private MainMenu() {
		float[] textPos = {1920/2, 800};
		text = new Text("Move any joystick to start", textPos, Color.black, 50, 0);
	}
}