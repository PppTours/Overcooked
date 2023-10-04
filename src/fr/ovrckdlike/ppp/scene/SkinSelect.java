package fr.ovrckdlike.ppp.scene;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.KeyListener;
import fr.ovrckdlike.ppp.gui.RoundRobin;
import fr.ovrckdlike.ppp.gui.SkinCard;
import fr.ovrckdlike.ppp.gui.Text;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

/**
 * A class that represents the skin selection scene.
 */
public class SkinSelect extends Scene {
  /**
   * The instance of the skin selection scene.
   */
  private static SkinSelect instance;

  /**
   * The title of the scene.
   */
  private final Text title;

  /**
   * The skin selector for player 1.
   */
  private final RoundRobin selectSkinP1;

  /**
   * The skin selector for player 2.
   */
  private final RoundRobin selectSkinP2;

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
   * Gets the instance of the skin selection scene.
   *
   * @return the instance of the skin selection scene.
   */
  public static SkinSelect get() {
    if (instance == null) {
      instance = new SkinSelect();
    }
    return instance;
  }

  /**
   * Constructs a skin selection scene.
   */
  private SkinSelect() {
    navigate = false;
    title = new Text("Select a character",
        new Rectangle(960f, 750f, 700f, 50f), Color.black);
    selectSkinP1 = new RoundRobin(new Rectangle(400f, 540f, 350f, 350f));
    selectSkinP2 = new RoundRobin(new Rectangle(1520f, 540f, 350f, 350f));
    for (Texture skin : Texture.getSkins()) {
      SkinCard sc1 = new SkinCard(new Dot(0f, 0f), skin);
      SkinCard sc2 = new SkinCard(sc1);

      selectSkinP1.addSelectCard(sc1);
      selectSkinP2.addSelectCard(sc2);
    }
  }

  /**
   * Renders the skin selection scene.
   */
  @Override
  public void render() {
    title.render();
    selectSkinP1.render();
    selectSkinP2.render();
  }

  /**
   * Manages the navigation in the skin selection scene.
   */
  @Override
  public void run() {
    skinSelection(GLFW_KEY_D, GLFW_KEY_A, selectSkinP1);


    skinSelection(GLFW_KEY_RIGHT, GLFW_KEY_LEFT, selectSkinP2);

    clockCooldown();


    boolean selected = KeyListener.isKeyPressed(GLFW_KEY_ENTER);
    boolean back = KeyListener.isKeyPressed(GLFW_KEY_BACKSPACE);

    if (!selected && !back && !navigate) {
      navigate = true;
    }

    if (selected && noCooldown() && navigate) {
      HyperParameters.get().setSkinP1((Texture) selectSkinP1.getSelectedCard().getChoice());
      HyperParameters.get().setSkinP2((Texture) selectSkinP2.getSelectedCard().getChoice());
      SceneManager.get().setSceneToGame();
    }

    if (back && noCooldown() && navigate) {
      SceneManager.get().setSceneToMapSelect();
    }

  }

  /**
   * Manages the skin selection.
   *
   * @param glfwKeyRight The key to move the selection to the right.
   * @param glfwKeyLeft The key to move the selection to the left.
   * @param selectSkinP The skin selector.
   */
  private void skinSelection(int glfwKeyRight, int glfwKeyLeft, RoundRobin selectSkinP) {
    boolean rightp = KeyListener.isKeyPressed(glfwKeyRight);
    boolean leftp = KeyListener.isKeyPressed(glfwKeyLeft);

    if (rightp) {
      selectSkinP.moveSelectionRight();
    }
    if (leftp) {
      selectSkinP.moveSelectionLeft();
    }
    if (!rightp && !leftp) {
      selectSkinP.resetMove();
    }
  }

}
