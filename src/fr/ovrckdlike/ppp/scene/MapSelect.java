package fr.ovrckdlike.ppp.scene;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;

import java.io.File;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.KeyListener;
import fr.ovrckdlike.ppp.gui.MapCard;
import fr.ovrckdlike.ppp.gui.RoundRobin;
import fr.ovrckdlike.ppp.gui.Text;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

public class MapSelect extends Scene{
	private static MapSelect instance;
	private RoundRobin mapSelector;
	private Text title;
	private boolean navigate;
	
	public void resetNav() {
		navigate = false;
	}

	public static MapSelect get() {
		if (instance == null) instance = new MapSelect();
		return instance;
	}
	
	private MapSelect() {
		navigate = false;
		title = new Text("Select your map", new Rectangle(960, 75, 700, 60), Color.black);
		mapSelector = new RoundRobin(new Rectangle(960, 540, 1000, 800));
		
		File mapDir = new File("res/maps");
		for (int i = 0; i < mapDir.listFiles().length; i++) {
			mapSelector.addSelectCard(new MapCard(new Dot(960, 540), i));
		}
	}
	
	@Override
	public void render() {
		title.render();
		mapSelector.render();
		
	}

	@Override
	public void run() {
		boolean backToTitle = KeyListener.isKeyPressed(GLFW_KEY_BACKSPACE);
		boolean right = KeyListener.isKeyPressed(GLFW_KEY_A) || KeyListener.isKeyPressed(GLFW_KEY_RIGHT);
		boolean left = KeyListener.isKeyPressed(GLFW_KEY_D) || KeyListener.isKeyPressed(GLFW_KEY_LEFT);
		boolean select = KeyListener.isKeyPressed(GLFW_KEY_ENTER);
		
		clockCooldown();
		
		if (!navigate && !backToTitle && !select) navigate = true;
		
		if (backToTitle && noCooldown() && navigate) SceneManager.get().setSceneToMain();
		if (!right && !left)mapSelector.resetMove();

		
		if (right) {
			mapSelector.moveSelectionRight();

		}
		if (left) {
			mapSelector.moveSelectionLeft();
		}
		
		if (select && noCooldown() && navigate) {
			HyperParameters.get().setMap((int) mapSelector.getSelectedCard().getChoice());
			SceneManager.get().setSceneToSkinSelect();
		}
	}

}
