package fr.ovrckdlike.ppp.scene;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import fr.ovrckdlike.ppp.graphics.Color;
import fr.ovrckdlike.ppp.graphics.KeyListener;
import fr.ovrckdlike.ppp.graphics.Renderer;
import fr.ovrckdlike.ppp.gui.Button;
import fr.ovrckdlike.ppp.physics.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manage the pause scene.
 */
public class PauseScene extends Scene {

  /**
   * The instance of the pause scene.
   */
  private static PauseScene instance;

  /**
   * The list of buttons.
   */
  private final List<Button> buttonList;

  /**
   * The selected button.
   */
  private int selected;

  /**
   * The background of the pause scene.
   */
  private final Rectangle background;

  /**
   * The reset booleans.
   */
  private boolean upReset;

  /**
   * The reset booleans.
   */
  private boolean downReset;

  /**
   * The constructor of the pause scene.
   */
  private PauseScene() {
    upReset = downReset = true;

    selected = 0;
    buttonList = new ArrayList<>();

    background = new Rectangle(1920 / 2, 1080 / 2, 440, 530);

    Rectangle resumePos = new Rectangle(1920 / 2, 1080 / 2 - 170, 400, 150);
    Button resume = new Button(resumePos, "Resume", Color.darkGreen);
    buttonList.add(resume);

    Rectangle restartPos = new Rectangle(1920 / 2, 1080 / 2, 400, 150);
    Button restart = new Button(restartPos, "Restart", Color.grey);
    buttonList.add(restart);

    Rectangle quitPos = new Rectangle(1920 / 2, 1080 / 2 + 170, 400, 150);
    Button quit = new Button(quitPos, "Quit", Color.red);
    buttonList.add(quit);
  }

  /**
   * Get the instance of the pause scene (singleton).
   *
   * @return the instance of the pause scene.s
   */
  public static PauseScene get() {
    if (instance == null) {
      instance = new PauseScene();
    }
    return instance;
  }

  /**
   * Render the pause scene.
   */
  public void render() {
    Renderer.drawQuad(background, Color.transparentGrey);
    for (Button b : buttonList) {
      b.render();
    }
  }

  /**
   * Run the pause scene.
   */
  public void run() {
    boolean up = (KeyListener.isKeyPressed(GLFW_KEY_UP) || KeyListener.isKeyPressed(GLFW_KEY_W));
    boolean down = (KeyListener.isKeyPressed(GLFW_KEY_DOWN)
        || KeyListener.isKeyPressed(GLFW_KEY_S));


    if (up && upReset) {
      upReset = false;
      if (selected == 0) {
        selected = 2;
      } else {
        selected--;
      }
    }

    if (down && downReset) {
      downReset = false;
      if (selected == 2) {
        selected = 0;
      } else {
        selected++;
      }
    }

    if (!up) {
      upReset = true;
    }
    if (!down) {
      downReset = true;
    }

    for (Button b : buttonList) {
      b.setSelected(false);
    }
    buttonList.get(selected).setSelected(true);

    SceneManager sm = SceneManager.get();
    boolean validate = KeyListener.isKeyPressed(GLFW_KEY_ENTER);
    if (validate) {
      switch (selected) {
        case 0:
          sm.unpauseGame();
          break;
        case 1:
          sm.setSceneToGame();
          break;
        case 2:
          sm.setSceneToMain();
          sm.resetGame();
          break;
        default:
          break;
      }

    }
  }

}
