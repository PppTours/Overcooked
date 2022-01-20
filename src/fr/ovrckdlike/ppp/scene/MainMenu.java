package fr.ovrckdlike.ppp.scene;

import fr.ovrckdlike.ppp.graphics.Color;
import java.util.ArrayList;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.Button;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.graphics.KeyListener;
import static org.lwjgl.glfw.GLFW.*;

public class MainMenu extends Scene {
	
	Button level1;
	Button level2;
	ArrayList<Button> ListeLevel = new ArrayList<Button>();
	
	public void render() {
		level1.render();
		level2.render();
	}
	public void run() {
		//Boolean pup = KeyListener.isKeyPressed(GLFW_KEY_UP);
		//Boolean pdown = KeyListener.isKeyPressed(GLFW_KEY_DOWN);
		int cursor = 0;
		Boolean pleft = KeyListener.isKeyPressed(GLFW_KEY_LEFT);
		Boolean pright = KeyListener.isKeyPressed(GLFW_KEY_RIGHT);
		Boolean pselect = KeyListener.isKeyPressed(GLFW_KEY_HOME);
		switch (cursor) {
		case 0 :
			for (Button button : ListeLevel)
				button.setSelected(false);
			level1.setSelected(true);
		case 1 :
			for (Button button : ListeLevel)
				button.setSelected(false);
			level2.setSelected(true);
		}
		if (pleft && cursor == 1) {
			cursor = 0;
		}
		else if (pright && cursor == 0) {
			cursor = 1;
		}
	}
		/*else if (pselect && cursor == 0) {
			;
		}
		else if (pselect && cursor == 1) {
			;
		}*/

	public MainMenu() {
		id = 0;
		int pos1[] = {0,0};
		int pos2[] = {0, 1};
		int size1[] = {0,0};
		int size2[] = {0,1};
		boolean selected1 = false;
		boolean selected2 = false;
		level1 = new Button("text1", Color.darkGreen, pos1, size1, selected1);
		level2 = new Button("text2", Color.lightGrey, pos2, size2, selected2);
	}
}