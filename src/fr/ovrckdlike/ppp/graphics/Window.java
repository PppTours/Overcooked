package fr.ovrckdlike.ppp.graphics;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import fr.ovrckdlike.ppp.internal.Shader;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.Time;
import fr.ovrckdlike.ppp.scene.SceneManager;
import java.io.IOException;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;





public class Window {

  private static final int DISPLAY_RATIO_W = 16;
  private static final int DISPLAY_RATIO_H = 9;
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

  private static Window window = null;

  private long audioContext;
  private long audioDevice;


  private Window() {
    this.width = 1920;
    this.height = 1080;
    this.name = "overcooked like";
  }


  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public static Window getWindow() {
    if (Window.window == null) {
      Window.window = new Window();
    }
    return Window.window;
  }

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




