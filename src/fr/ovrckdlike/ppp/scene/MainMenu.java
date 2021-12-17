package fr.ovrckdlike.ppp.scene;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.Button;
import fr.ovrckdlike.ppp.internal.Texture;

public class MainMenu extends Scene {
	
	Button level1;
	Button level2;
	
	public void render() {
		
	}
	public void run() {
		
	}
	
	public MainMenu() {
		id = 0;
		int pos1[] = {0,0};
		int pos2[] = {0, 1};
		int size1[] = {0,0};
		int size2[] = {0,1};
		boolean selected = false;
		boolean clicked = false;
		level1 = new Button("text1", Color.darkGreen, pos1, size1);
		level2 = new Button("text2", Color.lightGrey, pos2, size2);
	}

}
