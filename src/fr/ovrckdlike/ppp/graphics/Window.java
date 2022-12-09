package fr.ovrckdlike.ppp.graphics;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import fr.ovrckdlike.ppp.internal.Shader;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.physics.*;
import fr.ovrckdlike.ppp.scene.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class Window {
	
    private static final int DISPLAY_RATIO_W = 16, DISPLAY_RATIO_H = 9;
    public static final float DISPLAY_RATIO = (float) DISPLAY_RATIO_W/DISPLAY_RATIO_H;
	
	private int width;				// largeur
	private int height;				// hauteur
	private String name;			// titre de la fenetre
	private long glfwWindow; 		// "pointeur" vers la fenetre
	
	private static Window window = null;
	
	public static GameScene gameScene;
	public static MainMenu mainMenu;
	
	
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
		
		// très important ! (mais je saisis pas trop pourquoi...)
		GL.createCapabilities();
		
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		
		try {
			Renderer.simpleQuadShader = Shader.compileAndCreateShader("/shaders/simple_quad.vert", "/shaders/simple_quad.frag");
			Renderer.defaultTextured = Shader.compileAndCreateShader("/shaders/default_textured.vert", "/shaders/default_textured.frag");
			
			Texture.CatSkin = Texture.loadTexture("/textures/skins/player_cat_skin.png", true);
			Texture.table = Texture.loadTexture("/textures/tiles/table.png", true);
			Texture.cuttingTable = Texture.loadTexture("/textures/tiles/cutting_table.png", true);
			Texture.gasCooker = Texture.loadTexture("/textures/tiles/gas_cooker.png", true);
			Texture.sink = Texture.loadTexture("/textures/tiles/sink.png", true);
			Texture.dryer = Texture.loadTexture("/textures/tiles/dryer.png", true);
			Texture.bin = Texture.loadTexture("/textures/tiles/bin.png", true);
			Texture.ingredientRefiller = Texture.loadTexture("/textures/tiles/ingredient_refiller.png", true);
			Texture.serviceTable = Texture.loadTexture("/textures/tiles/service_table.png", true);
			Texture.plateReturn = Texture.loadTexture("/textures/tiles/plate_return.png", true);
			Texture.pot = Texture.loadTexture("/textures/general/pot.png", true);
			Texture.potEmpty = Texture.loadTexture("/textures/general/pot_empty.png", true);
			Texture.font = Texture.loadTexture("/font/font.png", false);
			Texture.wall = Texture.loadTexture("/textures/tiles/wall.png", true);
			Texture.plate = Texture.loadTexture("/textures/general/plate.png", true);
			Texture.recipeBackground =Texture.loadTexture("/textures/gui/recipe_background.png", true);
			Texture.circle = Texture.loadTexture("/textures/gui/circle.png", false); //false ?
			Texture.furnaceBack = Texture.loadTexture("/textures/tiles/furnace_background.png", true);
			Texture.furnaceFront = Texture.loadTexture("/textures/tiles/furnace_foreground.png", true);
			Texture.dirtyPlate = Texture.loadTexture("/textures/general/plate_dirty.png", true);
		
			
		} catch (IOException e) {
			System.out.println("unable to load texture");
			e.printStackTrace();
		}
		
		mainMenu = new MainMenu();
		gameScene = GameScene.get();

	}
	
	public void loop() {
		
		Time time = Time.get();
		
		while (!glfwWindowShouldClose(glfwWindow)) {
			// récupération des events
			glfwPollEvents();
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			gameScene.run();
			gameScene.render();
			
			
			glfwSwapBuffers(glfwWindow);
			
			time.updateTime();
			
		}
	}
}




