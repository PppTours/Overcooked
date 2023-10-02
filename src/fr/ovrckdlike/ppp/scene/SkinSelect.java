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

public class SkinSelect extends Scene {
  private static SkinSelect instance;
  private Text title;
  private RoundRobin selectSkinP1;
  private RoundRobin selectSkinP2;
  private boolean navigate;

  public void resetNav() {
    navigate = false;
  }


  public static SkinSelect get() {
    if (instance == null) {
      instance = new SkinSelect();
    }
    return instance;
  }

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

  @Override
  public void render() {
    title.render();
    selectSkinP1.render();
    selectSkinP2.render();
  }

  @Override
  public void run() {
    boolean rightp1 = KeyListener.isKeyPressed(GLFW_KEY_D);
    boolean leftp1 = KeyListener.isKeyPressed(GLFW_KEY_A);

    if (rightp1) {
      selectSkinP1.moveSelectionRight();
    }
    if (leftp1) {
      selectSkinP1.moveSelectionLeft();
    }
    if (!rightp1 && !leftp1) {
      selectSkinP1.resetMove();
    }


    boolean rightp2 = KeyListener.isKeyPressed(GLFW_KEY_RIGHT);
    boolean leftp2 = KeyListener.isKeyPressed(GLFW_KEY_LEFT);

    if (rightp2) {
      selectSkinP2.moveSelectionRight();
    }
    if (leftp2) {
      selectSkinP2.moveSelectionLeft();
    }
    if (!rightp2 && !leftp2) {
      selectSkinP2.resetMove();
    }

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

}
