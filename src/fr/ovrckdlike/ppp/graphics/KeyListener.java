package fr.ovrckdlike.ppp.graphics;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * A class that handles keyboard input.
 */
public class KeyListener {
  /**
   * The singleton instance of the key listener.
   */
  private static KeyListener instance;

  /**
   * An array of booleans representing whether a key is pressed or not.
   */
  private boolean[] keyPressed = new boolean[350];

  /**
   * The private constructor of the key listener.
   */
  private KeyListener() {

  }

  /**
   * Returns the singleton instance of the key listener.
   *
   * @return the singleton instance of the key listener
   */
  public static KeyListener get() {
    if (KeyListener.instance == null) {
      KeyListener.instance = new KeyListener();
    }
    return KeyListener.instance;
  }

  /**
   * The callback method for the key listener. It is called whenever a key is pressed or released.
   *
   * @param window the window
   * @param key the key pressed
   * @param scancode the scancode
   * @param action the action (press or release)
   * @param mods the mods
   */
  public static void keyCallback(long window, int key, int scancode, int action, int mods) {
    if (key != -1) {
      if (action == GLFW_PRESS) {
        get().keyPressed[key] = true;
      } else if (action == GLFW_RELEASE) {
        get().keyPressed[key] = false;
      }
    }
  }


  /**
   * Returns whether a key is pressed or not.
   *
   * @param keyCode the key code that we want to check.
   * @return whether a key is pressed or not
   */
  public static boolean isKeyPressed(int keyCode) {
    return get().keyPressed[keyCode];
  }
}