package fr.ovrckdlike.ppp.graphics;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.system.MemoryUtil.NULL;

import fr.ovrckdlike.ppp.internal.Shader;
import fr.ovrckdlike.ppp.internal.Texture;
import fr.ovrckdlike.ppp.internal.VertexArray;
import fr.ovrckdlike.ppp.physics.Dot;
import fr.ovrckdlike.ppp.physics.Rectangle;

public class Renderer {
	static Shader defaultTextured;
    static Shader simpleQuadShader;
    
    

    private static void setCameraUniform(Shader shader) {
        float fov = 1920;
        float ratio = Window.DISPLAY_RATIO;
        float w = fov;
        float h = fov/ratio;
        shader.setUniform4f("u_camera", 0, 0, w, h);
    }
    
    public static void drawQuad(Rectangle rect, Color color) {
        VertexArray.simpleQuad.bind();
        double pi = Math.PI;
        
        simpleQuadShader.bind();
        simpleQuadShader.setUniform4f("u_color", color.r, color.g, color.b, color.a);
        simpleQuadShader.setUniform4f("u_position", rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        simpleQuadShader.setUniform1f("u_theta", rect.getAngle());
        setCameraUniform(simpleQuadShader);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, NULL);
    }
    
    public static void drawTexture(Rectangle rect, Texture texture) {
    	Rectangle copy = new Rectangle(rect);
    	copy.setPos(new Dot(copy.getX(), 1080 - copy.getY()));
    	copy.resize(copy.getWidth(), -copy.getHeight());
    	VertexArray.simpleQuad.bind();
        defaultTextured.bind();
        defaultTextured.setUniform4f("u_position", copy.getX(), copy.getY(), copy.getWidth(), copy.getHeight());
        defaultTextured.setUniform4f("u_tex", 0, 0, 1, 1);
        defaultTextured.setUniform4f("u_alphaColor", 1, 1, 1, 1);
        defaultTextured.setUniform1f("u_theta", copy.getAngle());
        setCameraUniform(defaultTextured);
        texture.bind();
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, NULL);
    }
    
    public static void drawTextureTransparent(Rectangle rect, float alpha, Texture texture) {
    	Rectangle copy = new Rectangle(rect);
    	copy.setPos(new Dot(copy.getX(), 1080 - copy.getY()));
    	copy.resize(copy.getWidth(), -copy.getHeight());
    	VertexArray.simpleQuad.bind();
        defaultTextured.bind();
        defaultTextured.setUniform4f("u_position", copy.getX(), copy.getY(), copy.getWidth(), copy.getHeight());
        defaultTextured.setUniform4f("u_tex", 0, 0, 1, 1);
        defaultTextured.setUniform4f("u_alphaColor", 1, 1, 1, alpha);
        defaultTextured.setUniform1f("u_theta", copy.getAngle());
        setCameraUniform(defaultTextured);
        texture.bind();
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, NULL);
    }
    
    public static void drawLetter(char c, float x, float y, int size, Color color, Texture font, float angle) {
    	y = 1080 - y;
    	 int width = size*7;
    	 int height = -size*8;
    	 
    	 VertexArray.simpleQuad.bind();
    	 defaultTextured.bind();
    	 defaultTextured.setUniform4f("u_position", x, y, width, height);
    	 
    	 int charCode = c;
    	 
    	 int line = 0;
    	 int column = 0;
    	 switch (charCode) {
    	 case 232:
    		 //è
    		 line = 8;
    		 column = 1;
    		 break;
    	 case 233:
    		 line = 8;
    		 column = 0;
    		 //é
    		 break;
    	 case 234:
    		 line = 8;
    		 column = 2;
    		 //ê
    		 break;
    	 case 235:
    		 line = 8;
    		 column = 3;
    		 //ë
    		 break;
    	 case 249:
    		 line = 8;
    		 column = 5;
    		 //ù
    		 break;
    	 case 224:
    		 line = 8;
    		 column = 4;
    		 //à
    		 break;
    	 case 238:
    		 line = 8;
    		 column = 7;
    		 //î
    		 break;
    	 case 239:
    		 line = 8;
    		 column = 6;
    		 //ï
    		 break;
    	 case 231:
    		 //ç
    		 line = 8;
    		 column = 8;
    		 break;
    	 default:
    		 line = charCode / 16;
    		 column = charCode % 16;
    	 }
    	 line -= 2;
    	 if (line > 6) {
    		 line = 0;
    		 column = 0;
    	 }
    	 float charPosX = column/16f;
    	 float charPosY = line/7f;
    	 defaultTextured.setUniform4f("u_tex", charPosX, charPosY, 1/16f, 1/7f);
    	 defaultTextured.setUniform4f("u_alphaColor", color.r, color.g, color.b, color.a);
    	 defaultTextured.setUniform1f("u_theta", angle);
    	 setCameraUniform(defaultTextured);
    	 font.bind();
    	 glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, NULL);
    }
}
