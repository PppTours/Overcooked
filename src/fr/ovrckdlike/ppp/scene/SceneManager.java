package fr.ovrckdlike.ppp.scene;

public class SceneManager {
  private static SceneManager instance;
  private MainMenu main;
  private GameScene game;
  private PauseScene pause;
  private SkinSelect skin;
  private MapSelect map;
  private Scene current;
  private boolean paused;

  private SceneManager() {
    paused = false;
    map = MapSelect.get();
    main = MainMenu.get();
    game = GameScene.get();
    pause = PauseScene.get();
    skin = SkinSelect.get();

    current = main;
  }

  public static SceneManager get() {
    if (instance == null) {
      instance = new SceneManager();
    }
    return instance;
  }

  public void setSceneToSkinSelect() {
    current = skin;
    ((SkinSelect) current).resetNav();
    current.setCooldown(.2f);
  }

  public void setSceneToMapSelect() {
    current = map;
    ((MapSelect) current).resetNav();
    current.setCooldown(.2f);
  }

  public void setSceneToMain() {
    paused = false;
    current = instance.main;
    current.setCooldown(.2f);
  }

  public void resetGame() {
    game.reset();
  }

  public void setSceneToGame() {
    game.reset();
    paused = false;
    current = instance.game;
    current.setCooldown(.2f);
  }

  public void execute() {
    if (paused) {
      game.render();
    }
    current.execute();
  }

  public void pauseGame() {
    paused = true;
    current = instance.pause;
  }

  public void unpauseGame() {
    instance.paused = false;
    instance.current = instance.game;
  }
}
