package fr.ovrckdlike.ppp.graphics;

import fr.ovrckdlike.ppp.internal.Shader;
import fr.ovrckdlike.ppp.internal.VertexArray;
import fr.ovrckdlike.ppp.internal.Texture;


import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Renderer {
	static Shader defaultTextured;
    static Shader simpleQuadShader;
    
    

    private static void setCameraUniform(Shader shader) {
        float fov = 1920;
        float ratio = Window.DISPLAY_RATIO;
        float w = fov;
        float h = fov/ratio;
        float centerX = w/2f;
        float centerY = h/2f;
        shader.setUniform4f("u_camera", centerX-w*.5f, centerY-h*.5f, w, h);
    }

    public static void drawQuad(float x, float y, float width, float height, Color color) {
        VertexArray.simpleQuad.bind();
        simpleQuadShader.bind();
        simpleQuadShader.setUniform4f("u_color", color.r, color.b, color.g, color.a);
        simpleQuadShader.setUniform4f("u_position", x, y, width, height);
        setCameraUniform(simpleQuadShader);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, NULL);
    }
    public static void drawTexture(float x, float y, float width, float height, float angle, Texture texture) {
    	y = 1080 - y;
    	height = -height;
    	VertexArray.simpleQuad.bind();
        defaultTextured.bind();
        defaultTextured.setUniform4f("u_position", x, y, width, height);
        defaultTextured.setUniform4f("u_tex", 0, 0, 1, 1);
        defaultTextured.setUniform4f("u_alphaColor", 1, 1, 1, 1);
        defaultTextured.setUniform1f("u_theta", angle);
        setCameraUniform(defaultTextured);
        texture.bind();
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, NULL);
    }
    public static void drawTextureTransparent(float x, float y, float width, float height, float angle, float alpha, Texture texture) {
    	y = 1080 - y;
    	height = -height;
    	alpha *= 1/255f;
    	VertexArray.simpleQuad.bind();
        defaultTextured.bind();
        defaultTextured.setUniform4f("u_position", x, y, width, height);
        defaultTextured.setUniform4f("u_tex", 0, 0, 1, 1);
        defaultTextured.setUniform4f("u_alphaColor", 1, 1, 1, alpha);
        defaultTextured.setUniform1f("u_theta", angle);
        setCameraUniform(defaultTextured);
        texture.bind();
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, NULL);
    }
}
