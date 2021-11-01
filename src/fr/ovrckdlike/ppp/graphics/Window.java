package fr.ovrckdlike.ppp.graphics;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
	private int width;				// largeur
	private int height;				// hauteur
	private String name;			// titre de la fenetre
	private long glfwWindow; 		// "pointeur" vers la fenetre
	
	private static Window window = null;
	
	private Window() {
		this.width = 1920;
		this.height = 1080;
		this.name = "overcooked like";
	}
	
	public static Window get() {
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
		
	}
	
	public void loop() {
		while (!glfwWindowShouldClose(glfwWindow)) {
			// récupération des events
			glfwPollEvents();
			
			glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT);
			
			if (KeyListener.isKeyPressed(GLFW_KEY_U)) {
				System.out.println("ca marche !");
			}
			
			glfwSwapBuffers(glfwWindow);
			
		}
	}
	
	
}










