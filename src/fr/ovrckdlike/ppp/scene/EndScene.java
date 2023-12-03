package fr.ovrckdlike.ppp.scene;

import fr.ovrckdlike.ppp.gui.Text;

public class EndScene extends Scene {
  private static EndScene instance;

  private Text score;

  private EndScene() {
  }

  public static EndScene get() {
    if (instance == null) {
      instance = new EndScene();
    }
    return instance;
  }

  @Override
  public void render() {

  }

  @Override
  public void run() {

  }
}
