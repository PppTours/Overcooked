package fr.ovrckdlike.ppp.graphics;

import static org.lwjgl.glfw.GLFW.*;

public class KeyListener {
	private static KeyListener instance;
	private boolean keyPressed[] = new boolean[350];
	
	private KeyListener() {
		
	}
	
	public static KeyListener get() {
		if (KeyListener.instance == null) {
			KeyListener.instance = new KeyListener();
		}
		return KeyListener.instance;
	}
	
	
	public static void keyCallback(long window, int key, int scancode, int action, int mods) {
		if (action == GLFW_PRESS) {
			get().keyPressed[key] = true;
		} else if (action == GLFW_RELEASE) {
			get().keyPressed[key] = false;
		}
	}
	
	
<<<<<<< HEAD
	public boolean isKeyPressed(int keyCode) {
		return keyPressed[keyCode];
=======
	static public boolean isKeyPressed(int keyCode) {
		return get().keyPressed[keyCode];
>>>>>>> 7d5b4ca7d7a6f5c1975452bf82dd89ce66627db7
	}
	
	
	
}

	