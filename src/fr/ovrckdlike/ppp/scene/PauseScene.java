package fr.ovrckdlike.ppp.scene;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.KeyListener;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.Button;

public class PauseScene extends Scene{
	private static PauseScene instance;
	private List<Button> buttonList;
	private Button resume;
	private Button quit;
	private Button restart;
	private int selected;
	
	private boolean upReset;
	private boolean downReset;
	
	private PauseScene() {
		upReset = downReset = true;
		
		selected = 0;
		buttonList = new ArrayList();
		
		float[] resumePos = {760, 295};
		float[] resumeSize = {400, 150};
		resume = new Button(resumePos, resumeSize, "Resume", Color.darkGreen);
		buttonList.add(resume);
		
		float[] restartPos = {760, 465};
		float[] restartSize = {400, 150};
		restart = new Button(restartPos, restartSize, "Restart", Color.grey);
		buttonList.add(restart);
		
		float[] quitPos = {760, 635};
		float[] quitSize = {400, 150};
		quit = new Button(quitPos, quitSize, "Quit", Color.red);
		buttonList.add(quit);
	}
	
	public static PauseScene get() {
		if (instance == null) {
			instance = new PauseScene();
		}
		return instance;
	}
	
	public void render() {
		Renderer.drawQuad(740, 275, 440, 530, Color.transparentGrey);
		for (Button b:buttonList) {
			b.render();
		}
	}
	
	public void run() {
		boolean up = (KeyListener.isKeyPressed(GLFW_KEY_UP) || KeyListener.isKeyPressed(GLFW_KEY_W));
		boolean down = (KeyListener.isKeyPressed(GLFW_KEY_DOWN) || KeyListener.isKeyPressed(GLFW_KEY_S));
		boolean validate = KeyListener.isKeyPressed(GLFW_KEY_ENTER);
		
		
		if (up && upReset) {
			upReset = false;
			if (selected == 0) selected = 2;
			else selected--;
		}
		
		if (down && downReset) {
			downReset = false;
			if (selected == 2) selected = 0;
			else selected++;
		}
		
		if (!up) upReset = true;
		if (!down) downReset = true;
		
		for (Button b:buttonList) {
			b.setSelected(false);
		}
		buttonList.get(selected).setSelected(true);
		
		SceneManager sm = SceneManager.get();
		if (validate) {
			switch (selected) {
			case 0 :
				sm.unpauseGame();
				break;
			case 1 :
				sm.setSceneToGame();
				break;
			case 2 :
				sm.setSceneToMain();
				break;
			default :
				break;
			}
			
		}
	}

}
