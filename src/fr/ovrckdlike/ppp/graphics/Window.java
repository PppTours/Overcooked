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
	
	GameScene gameScene;
	
	
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
		
		
		gameScene = new GameScene();
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
			
			Texture.smiley = Texture.loadTexture("/textures/smile.png");	//affecter la texture a la variable ici
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public void loop() {
		
		Time time = Time.get();
		
		while (!glfwWindowShouldClose(glfwWindow)) {
			// récupération des events
			glfwPollEvents();
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			
			gameScene.render();
			
			glfwSwapBuffers(glfwWindow);
			
			time.updateTime();
			
		}
	}
}










