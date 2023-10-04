package fr.ovrckdlike.ppp.scene;

/**
 * This class manage the scenes.
 */
public class SceneManager {
  /**
   * The instance of the scene manager.
   */
  private static SceneManager instance;

  /**
   * The main menu scene.
   */
  private final MainMenu main;

  /**
   * The game scene.
   */
  private final GameScene game;

  /**
   * The pause scene.
   */
  private final PauseScene pause;

  /**
   * The skin select scene.
   */
  private final SkinSelect skin;

  /**
   * The map selection scene.
   */
  private final MapSelect map;

  /**
   * The current scene.
   */
  private Scene current;

  /**
   * If the game is paused.
   */
  private boolean paused;

  /**
   * A constructor of the scene manager.
   * It initializes the scenes.
   * (singleton).
   */
  private SceneManager() {
    paused = false;
    map = MapSelect.get();
    main = MainMenu.get();
    game = GameScene.get();
    pause = PauseScene.get();
    skin = SkinSelect.get();

    current = main;
  }

  /**
   * Get the instance of the scene manager (singleton).
   *
   * @return the instance of the scene manager.
   */
  public static SceneManager get() {
    if (instance == null) {
      instance = new SceneManager();
    }
    return instance;
  }

  /**
   * Change scene and go to the skin selection scene.
   */
  public void setSceneToSkinSelect() {
    current = skin;
    ((SkinSelect) current).resetNav();
    current.setCooldown(.2f);
  }

  /**
   * Change scene and go to the map selection scene.
   */
  public void setSceneToMapSelect() {
    current = map;
    ((MapSelect) current).resetNav();
    current.setCooldown(.2f);
  }

  /**
   * Change scene and go to the main menu scene.
   */
  public void setSceneToMain() {
    paused = false;
    current = instance.main;
    current.setCooldown(.2f);
  }

  /**
   * Reset the game.
   */
  public void resetGame() {
    game.reset();
  }

  /**
   * Change scene and go to the game scene.
   */
  public void setSceneToGame() {
    game.reset();
    paused = false;
    current = instance.game;
    current.setCooldown(.2f);
  }

  /**
   * Execute the current scene.
   */
  public void execute() {
    if (paused) {
      game.render();
    }
    current.execute();
  }

  /**
   * Pause the game.
   */
  public void pauseGame() {
    paused = true;
    current = instance.pause;
  }

  /**
   * Unpause the game.
   */
  public void unpauseGame() {
    instance.paused = false;
    instance.current = instance.game;
  }
}
