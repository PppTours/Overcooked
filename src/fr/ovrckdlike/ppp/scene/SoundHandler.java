package fr.ovrckdlike.ppp.scene;


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
  public static Sound putting = new Sound("Putting", "", false);
  public static Sound taking = new Sound("Taking", "", false);
  public static Sound dropping = new Sound("Dropping", "", false);
  public static Sound extinguish = new Sound("Extinguish", "", true);
  public static Sound ignite = new Sound("Ignite", "", false);
  public static Sound fire = new Sound("Fire", "", true);
  public static Sound gasLighting = new Sound("GasLighting", "", false);
  public static Sound gasCooking = new Sound("GasCooking", "", true);
  public static Sound endCooking = new Sound("EndCooking", "", false);
  public static Sound ovenTimer = new Sound("OvenTimer", "", false);


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
   * Arrête tous les sons.
   */
  public static void stopAll() {
    extinguish.stop();
    fire.stop();
  }

  /**
   * Charge les différents sons.
   */
  public void loadSounds() {
    walking.setPath("res/sounds/walking.ogg");
    dashing.setPath("res/sounds/dashing.ogg");

    cutting.setPath("res/sounds/cutting.ogg");
    putting.setPath("res/sounds/putting.ogg");
    taking.setPath("res/sounds/taking.ogg");
    taking.setPath("res/sounds/dropping.ogg");

    extinguish.setPath("res/sounds/extinguish.ogg");
    ignite.setPath("res/sounds/ignite.ogg");
    fire.setPath("res/sounds/fire.ogg");

    gasLighting.setPath("res/sounds/gasLighting.ogg");
    gasCooking.setPath("res/sounds/gasCooking.ogg");
    endCooking.setPath("res/sounds/endCooking.ogg");
    ovenTimer.setPath("res/sounds/ovenTimer.ogg");
  }
}
