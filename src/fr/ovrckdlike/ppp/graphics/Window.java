package fr.ovrckdlike.ppp.graphics;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_DECORATED;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.ALC10.ALC_DEFAULT_DEVICE_SPECIFIER;
import static org.lwjgl.openal.ALC10.alcCloseDevice;
import static org.lwjgl.openal.ALC10.alcCreateContext;
import static org.lwjgl.openal.ALC10.alcDestroyContext;
import static org.lwjgl.openal.ALC10.alcGetString;
import static org.lwjgl.openal.ALC10.alcMakeContextCurrent;
import static org.lwjgl.openal.ALC10.alcOpenDevice;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryUtil.NULL;

import fr.ovrckdlike.ppp.internal.Shader;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.scene.SceneManager;
import java.io.IOException;

import fr.ovrckdlike.ppp.scene.SoundHandler;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;

/**
 * La fenetre du jeu.
 */
public class Window {

  /**
   * Le ratio de la fenetre (longueur).
   */
  private static final int DISPLAY_RATIO_W = 16;

  /**
   * Le ratio de la fenetre (hauteur).
   */
  private static final int DISPLAY_RATIO_H = 9;

  /**
   * Le ratio de la fenetre.
   */
  public static final float DISPLAY_RATIO = (float) DISPLAY_RATIO_W / DISPLAY_RATIO_H;

  /**
   * La largeur de la fenetre.
   */
  private int width;

  /**
   * La hauteur de la fenetre.
   */
  private int height;

  /**
   * Le nom de la fenetre.
   */
  private String name;

  /**
   * La fenetre.
   */
  private long glfwWindow;

  /**
   * L'instance de la fenetre.
   */
  private static Window window = null;

  /**
   * Le contexte audio.
   */
  private long audioContext;

  /**
   * Le périphérique audio.
   */
  private long audioDevice;


  /**
   * Constructeur de la fenetre.
   */
  private Window() {
    this.width = 1920;
    this.height = 1080;
    this.name = "overcooked like";
  }

  /**
   * Getter de la longueur.
   *
   * @return la longueur de la fenetre.
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Getter de la largeur.
   *
   * @return la largeur de la fenetre.
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Getter of the singleton of the window.
   *
   * @return the window.
   */
  public static Window getWindow() {
    if (Window.window == null) {
      Window.window = new Window();
    }
    return Window.window;
  }

  /**
   * Launching of the window, manage the stop of the window too.
   */
  public void run() {
    System.out.println("hello LWJGL" + Version.getVersion() + " !");


    init();
    loop();

    // Destruction du contexte audio
    alcDestroyContext(audioContext);
    alcCloseDevice(audioDevice);



    // libération de la mémoire
    glfwFreeCallbacks(glfwWindow);
    glfwDestroyWindow(glfwWindow);

    // ferme GLFW et supprime le callback
    glfwTerminate();
    glfwSetErrorCallback(null).free();
  }

  /**
   * Initialisation of the window.
   * Initialisation of OpenGL and OpenAL
   */
  public void init() {
    // création du retour d'erreur
    GLFWErrorCallback.createPrint(System.err).set();

    // init GLFW
    if (!glfwInit()) {
      throw new IllegalStateException("GLFW ne s'initialise pas correctement");
    }
    // config fenetre
    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
    glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

    glfwWindow = glfwCreateWindow(this.width, this.height, this.name, NULL, NULL);
    if (glfwWindow == NULL) {
      throw new IllegalStateException("la création de la fenetre a échoué");
    }

    glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

    // Contexte OpenGL
    glfwMakeContextCurrent(glfwWindow);

    // activation de la v-sync
    glfwSwapInterval(1);

    // affichage de la fenetre
    glfwShowWindow(glfwWindow);

    // Initialisation du périphérique audio
    String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
    audioDevice = alcOpenDevice(defaultDeviceName);

    int[] attributes = {0};
    audioContext = alcCreateContext(audioDevice, attributes);
    alcMakeContextCurrent(audioContext);

    ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
    ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

    if (!alCapabilities.OpenAL10) {
      assert false : "OpenAL 1.0 is not supported";
    }

    // très important ! (mais je saisis pas trop pourquoi...)
    GL.createCapabilities();


    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

    try {
      Renderer.simpleQuadShader = Shader.compileAndCreateShader(
          "/shaders/simple_quad.vert", "/shaders/simple_quad.frag");
      Renderer.defaultTextured = Shader.compileAndCreateShader(
          "/shaders/default_textured.vert", "/shaders/default_textured.frag");

      // load gui, skins, and miscellaneous textures
      Texture.loadGeneralTextures();
    } catch (IOException e) {
      System.out.println("unable to load shaders");
      e.printStackTrace();
    }

    SoundHandler soundHandler = SoundHandler.get();
    soundHandler.loadSounds();
  }

  /**
   * Loop of the window. Manage the events.
   */
  public void loop() {

    Time time = Time.get();

    while (!glfwWindowShouldClose(glfwWindow)) {
      // récupération des events
      glfwPollEvents();

      glClear(GL_COLOR_BUFFER_BIT);


      SceneManager sceneManager = SceneManager.get();
      sceneManager.execute();


      glfwSwapBuffers(glfwWindow);

      time.updateTime();
    }
  }
}




