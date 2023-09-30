package fr.ovrckdlike.ppp.internal;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import fr.ovrckdlike.ppp.map.MapType;

public class Texture {

	private static Texture boundTexture;
	
	// skins
	public static Texture catSkin, rabbitSkin, wolfSkin, dragonSkin, apeSkin;
	
	// tiles
	public static Texture table, gasCooker, cuttingTable, wall, plateReturn, sink, dryer, bin, ingredientRefiller,
							serviceTable, furnaceFront, furnaceBack;
	
	// ingredients
	public static Texture salade, mushroom, tomato, cheese, onion, meat, pasta, sausage, pizzaDough, burgerBread, rice, chicken, potato;
	
	// prepared ingredients
	public static Texture slicedTomato, slicedSalade, slicedOnion, slicedMushroom, slicedSausage, slicedMeat, slicedCheese, 
							slicedBread, slicedChicken, slicedPotato, flattenPizzaDough;
	
	// items
	public static Texture potEmpty, potTomato, potOnion, potMushroom, pot, plate, pan, dirtyPlate, extinguisher;
	
	// gui
	public static Texture pepper, recipeBackground, circle, arrowLeft, arrowRight, font;
	
	// other
	public static Texture fire;
	
	public final String path;
	public final int width, height;
	public final int id;
	
	private Texture(String path, int id, int width, int height) {
		this.path = path;
		this.id = id;
		this.width = width;
		this.height = height;
	}
	
	// linear = texture smoothing (bluring)
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
	
	public static List<Texture> getSkins() {
		List<Texture> res = new ArrayList();
		res.add(catSkin);
		res.add(dragonSkin);
		res.add(rabbitSkin);
		res.add(wolfSkin);
		res.add(apeSkin);
		return res;
	}
	
	public static void loadGeneralTextures() {
		try {
			// skins
			Texture.catSkin = Texture.loadTexture("/textures/skins/player_cat_skin.png", true);
			Texture.apeSkin = Texture.loadTexture("/textures/skins/player_ape_skin.png", true);
			Texture.dragonSkin = Texture.loadTexture("/textures/skins/player_dragon_skin.png", true);
			Texture.rabbitSkin = Texture.loadTexture("/textures/skins/player_rabbit_skin.png", true);
			Texture.wolfSkin = Texture.loadTexture("/textures/skins/player_wolf_skin.png", true);
			
			// gui
			Texture.pepper = Texture.loadTexture("/textures/gui/pepper.png", true);
			Texture.recipeBackground =Texture.loadTexture("/textures/gui/recipe_background.png", true);
			Texture.circle = Texture.loadTexture("/textures/gui/circle.png", false); //false ?
			Texture.arrowLeft = Texture.loadTexture("/textures/gui/arrow_left.png", true);
			Texture.arrowRight = Texture.loadTexture("/textures/gui/arrow_right.png", true);
			Texture.font = Texture.loadTexture("/font/font.png", false);
			
			// items
			Texture.pot = Texture.loadTexture("/textures/general/pot.png", true);
			Texture.potEmpty = Texture.loadTexture("/textures/general/pot_empty.png", true);
			Texture.plate = Texture.loadTexture("/textures/general/plate.png", true);
			Texture.dirtyPlate = Texture.loadTexture("/textures/general/plate_dirty.png", true);
			Texture.pan = Texture.loadTexture("/textures/general/pan.png", true);
			Texture.extinguisher = Texture.loadTexture("/textures/general/extinguisher.png", true);
			
			// tiles
			Texture.table = Texture.loadTexture("/textures/tiles/table.png", true);
			Texture.cuttingTable = Texture.loadTexture("/textures/tiles/cutting_table.png", true);
			Texture.gasCooker = Texture.loadTexture("/textures/tiles/gas_cooker.png", true);
			Texture.sink = Texture.loadTexture("/textures/tiles/sink.png", true);
			Texture.dryer = Texture.loadTexture("/textures/tiles/dryer.png", true);
			Texture.bin = Texture.loadTexture("/textures/tiles/bin.png", true);
			Texture.ingredientRefiller = Texture.loadTexture("/textures/tiles/ingredient_refiller.png", true);
			Texture.serviceTable = Texture.loadTexture("/textures/tiles/service_table.png", true);
			Texture.plateReturn = Texture.loadTexture("/textures/tiles/plate_return.png", true);
			Texture.wall = Texture.loadTexture("/textures/tiles/wall.png", true);
			Texture.furnaceBack = Texture.loadTexture("/textures/tiles/furnace_background.png", true);
			Texture.furnaceFront = Texture.loadTexture("/textures/tiles/furnace_foreground.png", true);
			
			//fire
			Texture.fire = Texture.loadTexture("/textures/general/fire.png", true);
			
		} catch (IOException e) {
			System.out.println("unable to load textures");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void loadForMapType(MapType type) {
		try {
			//ingredients
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
			System.out.println("unable to load textures");
			e.printStackTrace();
			System.exit(0);
		}
		
		
	}
}
