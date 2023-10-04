package fr.ovrckdlike.ppp.physics;

/**
 * The Time class manage the time of the game.
 * It allows to get the time since the last frame, the fps and the delta time.
 */
public class Time {
  /**
   * The time of the current frame.
   */
  private long currentFrame = System.nanoTime();

  /**
   * The fps of the game.
   */
  private int fps = 0;

  /**
   * The delta time of the game in nanoseconde.
   */
  private long dt;

  /**
   * The instance of the Time class.
   */
  private static Time time = null;
  
  
  /*public Time Time() {
    time = new Time();
    return time;
  }*/

  /**
   * Get the instance of the Time class.
   *
   * @return The instance of the Time class.
   */
  public static Time get() {
    if (time == null) {
      time = new Time();
    }
    return Time.time;
  }

  /**
   * Get the current time.
   *
   * @return The current time.
   */
  public long getCurrentTime() {
    return System.nanoTime();
  }

  /**
   * Get the time since the last frame.
   *
   * @param time The time of the last frame.
   * @return The time since the last frame.
   */
  public float timeSince(long time) {
    long currentTime = System.nanoTime();
    return (float) ((currentTime - time) / 1E9);
  }

  /**
   * Update the time.
   */
  public void updateTime() {
    long lastFrame = currentFrame;
    currentFrame = System.nanoTime();
    dt = currentFrame - lastFrame;
    fps = (int) (1 / (dt / 1E9));
  }

  /**
   * Get the fps of the game.
   *
   * @return The fps of the game.
   */
  public int getFps() {
    return fps;
  }

  /**
   * Get the delta time of the game.
   *
   * @return The delta time of the game.
   */
  public long getDt() {
    return dt;
  }

  /**
   * Get the delta time of the game in seconds.
   */
  public float getDtS() {
    return dt / 1E9f;
  }

}
