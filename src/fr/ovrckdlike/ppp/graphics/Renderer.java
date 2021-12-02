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
    public static void drawTexture(float x, float y, float width, float height, Texture texture) {
    	 VertexArray.simpleQuad.bind();
         defaultTextured.bind();
         defaultTextured.setUniform4f("u_position", x, y, width, height);
         defaultTextured.setUniform4f("u_tex", 0, 0, 1, 1);
         setCameraUniform(defaultTextured);
         texture.bind();
         glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, NULL);
    }
}
