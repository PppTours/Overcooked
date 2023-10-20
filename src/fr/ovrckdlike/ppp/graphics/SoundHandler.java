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

  /**
   * The different sounds.
   */
  public static Sound walking = new Sound("Walking", "", false);
  public static Sound dashing = new Sound("Dashing", "", false);
  public static Sound cutting = new Sound("Cutting", "", false);
  public static Sound cooking = new Sound("Cooking", "", false);
  public static Sound baking = new Sound("Baking", "", false);
  public static Sound putting = new Sound("Putting", "", false);
  public static Sound taking = new Sound("Taking", "", false);
  public static Sound washing = new Sound("Washing", "", false);
  public static Sound music = new Sound("Music", "", false);
  public static Sound win = new Sound("Win", "", false);
  public static Sound lose = new Sound("Lose", "", false);
  public static Sound menu = new Sound("Menu", "", false);
  public static Sound click = new Sound("Click", "", false);


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
    walking.setPath("res/sounds/walking.ogg");
    dashing.setPath("res/sounds/dashing.ogg");

    cutting.setPath("res/sounds/cutting.ogg");
    /*
    cooking.setPath("res/sounds/cooking.ogg");
    baking.setPath("res/sounds/baking.ogg");
    washing.setPath("res/sounds/washing.ogg");

    putting.setPath("res/sounds/putting.ogg");
    taking.setPath("res/sounds/taking.ogg");

    music.setPath("res/sounds/music.ogg");

    win.setPath("res/sounds/win.ogg");
    lose.setPath("res/sounds/lose.ogg");

    menu.setPath("res/sounds/menu.ogg");
    click.setPath("res/sounds/click.ogg");
     */
  }
}
