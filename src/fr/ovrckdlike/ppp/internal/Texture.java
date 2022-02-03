package fr.ovrckdlike.ppp.internal;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class Texture {

	private static Texture boundTexture;
	
	public static Texture CatSkin; 		//déclarer les textures ici
	public static Texture table;
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

	
	
	

	public final String path;
	public final int width, height;
	public final int id;
	
	private Texture(String path, int id, int width, int height) {
		this.path = path;
		this.id = id;
		this.width = width;
		this.height = height;
	}
	
	public static Texture loadTexture(String path) throws IOException {
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
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
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
}
