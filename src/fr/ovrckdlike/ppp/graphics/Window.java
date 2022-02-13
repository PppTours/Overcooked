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
		
		// lib�ration de la m�moire
		glfwFreeCallbacks(glfwWindow);
		glfwDestroyWindow(glfwWindow);
		
		// ferme GLFW et supprime le callback 
		glfwTerminate();
		glfwSetErrorCallback(null).free();
		
		
		
	}
	
	public void init() {
		
		mainMenu = new MainMenu();
		gameScene = new GameScene();
		
		// cr�ation du retour d'erreur
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
			throw new IllegalStateException("la cr�ation de la fenetre a �chou�");
		}
		
		
		glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
		
		// Contexte OpenGL
		glfwMakeContextCurrent(glfwWindow);
		
		// activation de la v-sync
		glfwSwapInterval(1);
		
		// affichage de la fenetre
		glfwShowWindow(glfwWindow);
		
		// tr�s important ! (mais je saisis pas trop pourquoi...)
		GL.createCapabilities();
		
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		
		try {
			Renderer.simpleQuadShader = Shader.compileAndCreateShader("/shaders/simple_quad.vert", "/shaders/simple_quad.frag");
			Renderer.defaultTextured = Shader.compileAndCreateShader("/shaders/default_textured.vert", "/shaders/default_textured.frag");
			
			
			Texture.CatSkin = Texture.loadTexture("/textures/player_cat_skin.png", true);	//affecter la texture a la variable ici
			Texture.table = Texture.loadTexture("/textures/table.png", true);
			Texture.cuttingTable = Texture.loadTexture("/textures/cutting_table.png", true);
			Texture.salade = Texture.loadTexture("/textures/salade.png", true);
			Texture.gasCooker = Texture.loadTexture("/textures/gas_cooker.png", true);
			Texture.sink = Texture.loadTexture("/textures/sink.png", true);
			Texture.dryer = Texture.loadTexture("/textures/dryer.png", true);
			Texture.bin = Texture.loadTexture("/textures/bin.png", true);
			Texture.ingredientRefiller = Texture.loadTexture("/textures/ingredient_refiller.png", true);
			Texture.serviceTable = Texture.loadTexture("/textures/service_table.png", true);
			Texture.plateReturn = Texture.loadTexture("/textures/plate_return.png", true);
			Texture.pot = Texture.loadTexture("/textures/pot.png", true);
			Texture.potEmpty = Texture.loadTexture("/textures/pot_empty.png", true);
			Texture.potMushroom = Texture.loadTexture("/textures/pot_mushroom.png", true);
			Texture.potTomato = Texture.loadTexture("/textures/pot_tomato.png", true);
			Texture.potOnion = Texture.loadTexture("/textures/pot_onion.png", true);
			Texture.onion = Texture.loadTexture("/textures/onion.png", true);
			Texture.tomato = Texture.loadTexture("/textures/tomato.png", true);
			Texture.cheese = Texture.loadTexture("/textures/cheese.png", true);
			Texture.meat = Texture.loadTexture("/textures/meat.png", true);
			Texture.mushroom = Texture.loadTexture("/textures/mushroom.png", true);
			Texture.pasta = Texture.loadTexture("/textures/noodles.png", true);
			Texture.sausage = Texture.loadTexture("/textures/sausage.png", true);
			Texture.pizzaDough = Texture.loadTexture("/textures/pizza_dough.png", true);
			Texture.burgerBread = Texture.loadTexture("/textures/bread.png", true);
			Texture.rice = Texture.loadTexture("/textures/rice.png", true);
			Texture.chicken = Texture.loadTexture("/textures/chicken.png", true);
			Texture.potato = Texture.loadTexture("/textures/potato.png", true);
			Texture.font = Texture.loadTexture("/font/font.png", false);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public void loop() {
		
		Time time = Time.get();
		
		while (!glfwWindowShouldClose(glfwWindow)) {
			// r�cup�ration des events
			glfwPollEvents();
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			gameScene.run();
			gameScene.render();
			
			
			glfwSwapBuffers(glfwWindow);
			
			time.updateTime();
			
		}
	}
}










