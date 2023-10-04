package fr.ovrckdlike.ppp.scene;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.KeyListener;
import fr.ovrckdlike.ppp.gui.MapCard;
import fr.ovrckdlike.ppp.gui.RoundRobin;
import fr.ovrckdlike.ppp.gui.Text;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;
import java.io.File;

/**
 * A class that represents the map selection scene.
 */
public class MapSelect extends Scene {
  /**
   * The instance of the map selection scene.
   */
  private static MapSelect instance;

  /**
   * The map selector.
   */
  private final RoundRobin mapSelector;

  /**
   * The title of the scene.
   */
  private final Text title;

  /**
   * A boolean that represents if the player can navigate.
   */
  private boolean navigate;

  /**
   * Resets the navigation.
   */
  public void resetNav() {
    navigate = false;
  }

  /**
   * Gets the instance of the map selection scene.
   *
   * @return the instance of the map selection scene.
   */
  public static MapSelect get() {
    if (instance == null) {
      instance = new MapSelect();
    }
    return instance;
  }

  /**
   * Constructs a map selection scene.
   */
  private MapSelect() {
    navigate = false;
    title = new Text("Select your map", new Rectangle(960, 75, 700, 60), Color.black);
    mapSelector = new RoundRobin(new Rectangle(960, 540, 1000, 800));

    File mapDir = new File("res/maps");
    for (int i = 0; i < mapDir.listFiles().length; i++) {
      mapSelector.addSelectCard(new MapCard(new Dot(960, 540), i));
    }
  }

  /**
   * Renders the map selection scene.
   */
  @Override
  public void render() {
    title.render();
    mapSelector.render();

  }

  /**
   * Updates the map selection scene.
   */
  @Override
  public void run() {
    boolean backToTitle = KeyListener.isKeyPressed(GLFW_KEY_BACKSPACE);
    boolean select = KeyListener.isKeyPressed(GLFW_KEY_ENTER);

    clockCooldown();

    if (!navigate && !backToTitle && !select) {
      navigate = true;
    }

    if (backToTitle && noCooldown() && navigate) {
      SceneManager.get().setSceneToMain();
    }

    boolean right = KeyListener.isKeyPressed(GLFW_KEY_A)
        || KeyListener.isKeyPressed(GLFW_KEY_RIGHT);
    boolean left = KeyListener.isKeyPressed(GLFW_KEY_D)
        || KeyListener.isKeyPressed(GLFW_KEY_LEFT);

    if (!right && !left) {
      mapSelector.resetMove();
    }


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
