package fr.ovrckdlike.ppp.internal;

import javax.imageio.ImageIO;

import fr.ovrckdlike.ppp.map.MapType;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class Texture {

	private static Texture boundTexture;
	
	public static Texture CatSkin; 		//déclarer les textures ici
	public static Texture table;
	public static Texture flattenPizzaDough;
	public static Texture cuttingTable;
	public static Texture salade;
	public static Texture gasCooker;
	public static Texture sink;
	public static Texture dryer;
	public static Texture bin;
	public static Texture ingredientRefiller;
	public static Texture serviceTable;
	public static Texture plateReturn;
	public static Texture pot;
	public static Texture potEmpty;
	public static Texture potTomato;
	public static Texture potOnion;
	public static Texture potMushroom;
	public static Texture mushroom;
	public static Texture tomato;
	public static Texture cheese;
	public static Texture onion;
	public static Texture meat;
	public static Texture pasta;
	public static Texture sausage;
	public static Texture pizzaDough;
	public static Texture burgerBread;
	public static Texture rice;
	public static Texture chicken;
	public static Texture potato;
	public static Texture font;
	public static Texture wall;
	public static Texture plate;
	public static Texture recipeBackground;
	public static Texture circle;
	public static Texture slicedTomato;
	public static Texture slicedSalade;
	public static Texture slicedOnion;
	public static Texture slicedMushroom;
	public static Texture slicedSausage;
	public static Texture slicedMeat;
	public static Texture slicedCheese;
	public static Texture slicedBread;
	public static Texture slicedChicken;
	public static Texture slicedPotato;
	public static Texture furnaceFront;
	public static Texture furnaceBack;
	public static Texture dirtyPlate;
	

	public final String path;
	public final int width, height;
	public final int id;
	
	private Texture(String path, int id, int width, int height) {
		this.path = path;
		this.id = id;
		this.width = width;
		this.height = height;
	}
	
	public static Texture loadTexture(String path, boolean linear) throws IOException {
		int id, width, height;

		try (InputStream is = Texture.class.getResourceAsStream(path)) {
			if (is == null)
				throw new IOException("Resource " + path + " does not exist");
			BufferedImage image = ImageIO.read(is);

			width = image.getWidth();
			height = image.getHeight();

			int size = width * height;
			int[] data = new int[size];

			image.getRGB(0, 0, width, height, data, 0, width);

			int[] px = new int[size];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					int pos = i * width + j;
					int a = (data[pos] & 0xff000000) >> 24;
					int r = (data[pos] & 0x00ff0000) >> 16;
					int g = (data[pos] & 0x0000ff00) >> 8;
					int b = (data[pos] & 0x000000ff);
					px[(i) * width + j] =
							a << 24 |
							b << 16 |
							g << 8 |
							r;
				}
			}

			id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			if (linear) {
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			}
			else {
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			}
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, px);
			unbind();
		}
			
		return new Texture(path, id, width, height);
	}
	
//	public Texture(int width, int height, ByteBuffer buffer) {
//		this.id = glGenTextures();
//		this.width = width;
//		this.height = height;
//		this.path = null;
//		glBindTexture(GL_TEXTURE_2D, id);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
//		glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
//		glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
//		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
//		unbind();
//	}
	
	public void bind() {
		if(boundTexture != this) {
			glBindTexture(GL_TEXTURE_2D, id);
			boundTexture = this;
		}
	}
	
	public static void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
		boundTexture = null;
	}

	public void dispose() {
		glDeleteTextures(id);
	}

	public static void loadForMapType(MapType type) {
		try {
			Texture.salade = Texture.loadTexture("/textures/general/salade.png", true);
			Texture.onion = Texture.loadTexture("/textures/general/onion.png", true);
			Texture.tomato = Texture.loadTexture("/textures/general/tomato.png", true);
			Texture.cheese = Texture.loadTexture("/textures/general/cheese.png", true);
			Texture.meat = Texture.loadTexture("/textures/general/meat.png", true);
			Texture.mushroom = Texture.loadTexture("/textures/general/mushroom.png", true);
			Texture.pasta = Texture.loadTexture("/textures/general/noodles.png", true);
			Texture.sausage = Texture.loadTexture("/textures/general/sausage.png", true);
			Texture.pizzaDough = Texture.loadTexture("/textures/general/pizza_dough.png", true);	
			Texture.burgerBread = Texture.loadTexture("/textures/general/bread.png", true);
			Texture.rice = Texture.loadTexture("/textures/general/rice.png", true);
			Texture.chicken = Texture.loadTexture("/textures/general/chicken.png", true);
			Texture.potato = Texture.loadTexture("/textures/general/potato.png", true);
			
			switch (type) {
			case DEFAULT :
				break;
			case BURGER :
				Texture.slicedTomato = Texture.loadTexture("/textures/burger/sliced_tomato.png", true);
				Texture.slicedSalade = Texture.loadTexture("/textures/burger/sliced_salade.png", true);
				Texture.slicedCheese = Texture.loadTexture("/textures/burger/grated_cheese.png", true);
				Texture.slicedBread = Texture.loadTexture("/textures/burger/cut_bread.png", true);
				Texture.slicedMeat = Texture.loadTexture("/textures/burger/sliced_meat.png", true);
				break;
			case NOODLES :
				Texture.slicedTomato = Texture.loadTexture("/textures/noodles/tomato_layer.png", true);
				Texture.slicedCheese = Texture.loadTexture("/textures/noodles/cheese_layer.png", true);
				Texture.slicedMeat = Texture.loadTexture("/textures/noodles/bolognese_layer.png", true);
				break;
			case SOUP :
				Texture.potMushroom = Texture.loadTexture("/textures/soups/pot_mushroom.png", true);
				Texture.potTomato = Texture.loadTexture("/textures/soups/pot_tomato.png", true);
				Texture.potOnion = Texture.loadTexture("/textures/soups/pot_onion.png", true);
				Texture.slicedTomato = Texture.loadTexture("/textures/soups/sliced_tomato.png", true);
				Texture.slicedOnion = Texture.loadTexture("/textures/soups/sliced_onion.png", true);
				Texture.slicedMushroom = Texture.loadTexture("/textures/soups/sliced_mushroom.png", true);
				break;
			case PIZZA :
				Texture.slicedTomato = Texture.loadTexture("/textures/pizza/sliced_tomato.png", true);
				Texture.flattenPizzaDough = Texture.loadTexture("Texture/pizza/flatten_pizza_dough.png", true);
				Texture.slicedSausage = Texture.loadTexture("/textures/pizza/sliced_sausage.png", true);
				Texture.slicedCheese = Texture.loadTexture("/textures/pizza/grated_cheese.png", true);
				break;
			case SALAD :
				Texture.slicedTomato = Texture.loadTexture("/textures/salad/sliced_tomato.png", true);
				Texture.slicedSalade = Texture.loadTexture("/textures/salad/sliced_salade.png", true);
				Texture.slicedOnion = Texture.loadTexture("/textures/salad/sliced_onion.png", true);
				break;
			case CHICKEN :
				Texture.slicedChicken = Texture.loadTexture("/textures/chickens/cut_chicken.png", true);
				Texture.slicedPotato = Texture.loadTexture("/textures/chickens/cut_potatoes.png", true);
				Texture.slicedMushroom = Texture.loadTexture("/textures/chickens/sliced_mushroom.png", true);
				break;
			}
		}
		catch (IOException e) {
			System.out.println("unable to laod textures");
			e.printStackTrace();
			System.exit(0);
		}
		
		
	}
}
