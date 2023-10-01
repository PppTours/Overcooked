package fr.ovrckdlike.ppp.graphics;


import fr.ovrckdlike.ppp.internal.Sound;

/**
 * A class that handles the sounds.
 */
public class SoundHandler {
  /**
   * The instance of the SoundHandler class.
   */
  private static SoundHandler instance;
  public static Sound walking;
  public static Sound dashing;
  public static Sound cutting;
  public static Sound cooking;
  public static Sound baking;
  public static Sound putting;
  public static Sound taking;
  public static Sound washing;
  public static Sound music;
  public static Sound win;
  public static Sound lose;
  public static Sound menu;
  public static Sound click;


  /**
   * Constructor of SoundHandler class.
   */
  private SoundHandler() {
    try {
      loadSounds();
    } catch (Exception e) {
      System.out.println("unable to load sounds");
      e.printStackTrace();
    }
  }

  /**
   * Récupère l'instance de la classe SoundHandler (singleton).
   *
   * @return L'instance de la classe SoundHandler
   */
  public static SoundHandler get() {
    if (instance == null) {
      instance = new SoundHandler();
    }
    return instance;
  }

  /**
   * Joue un son.
   *
   * @param sound Le son à jouer
   */
  public static void play(Sound sound) {
    sound.play();
  }

  /**
   * Arrête un son.
   *
   * @param sound Le son à arrêter
   */
  public static void stop(Sound sound) {
    sound.stop();
  }

  /**
   * Charge les différents sons.
   */
  public void loadSounds() {
    walking = new Sound("Walking", "res/sounds/walking.ogg", false);
    /*
    dashing = new Sound("Dashing", "res/sounds/dashing.ogg", false);
    cutting = new Sound("Cutting", "res/sounds/cutting.ogg", false);
    cooking = new Sound("res/sounds/cooking.ogg", false);
    putting = new Sound("res/sounds/putting.ogg", false);
    taking = new Sound("res/sounds/taking.ogg", false);
    washing = new Sound("res/sounds/washing.ogg", false);
    music = new Sound("res/sounds/music.ogg", false);
    win = new Sound("res/sounds/win.ogg", false);
    lose = new Sound("res/sounds/lose.ogg", false);
    menu = new Sound("res/sounds/menu.ogg", false);
    click = new Sound("res/sounds/click.ogg", false);

     */
  }
}
