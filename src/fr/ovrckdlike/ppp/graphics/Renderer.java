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

/**
 * A class that contains all the methods to draw on the screen.
 */
public class Renderer {
  /**
   * The default shader used to draw textured objects.
   */
  static Shader defaultTextured;

  /**
   * The default shader used to draw colored objects.
   */
  static Shader simpleQuadShader;

  /**
   * Initializes the default shaders.
   *
   * @param shader the default shader used to draw textured objects.
   */
  private static void setCameraUniform(Shader shader) {
    float fov = 1920;
    float ratio = Window.DISPLAY_RATIO;
    float w = fov;
    float h = fov / ratio;
    shader.setUniform4f("u_camera", 0, 0, w, h);
  }

  /**
   * Draw a colored rectangle.
   *
   * @param rect the rectangle to draw.
   * @param color the color of the rectangle.
   */
  public static void drawQuad(Rectangle rect, Color color) {
    VertexArray.simpleQuad.bind();
    double pi = Math.PI;

    simpleQuadShader.bind();
    simpleQuadShader.setUniform4f("u_color", color.valR, color.valG, color.valB, color.valA);
    simpleQuadShader.setUniform4f(
        "u_position", rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    simpleQuadShader.setUniform1f("u_theta", rect.getAngle());
    setCameraUniform(simpleQuadShader);
    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, NULL);
  }

  /**
   * Draw a textured rectangle.
   *
   * @param rect the rectangle to draw.
   * @param texture the texture of the rectangle.
   */
  public static void drawTexture(Rectangle rect, Texture texture) {
    Rectangle copy = new Rectangle(rect);
    copy.setPos(new Dot(copy.getX(), 1080 - copy.getY()));
    copy.resize(copy.getWidth(), -copy.getHeight());
    VertexArray.simpleQuad.bind();
    defaultTextured.bind();
    defaultTextured.setUniform4f(
        "u_position", copy.getX(), copy.getY(), copy.getWidth(), copy.getHeight());
    defaultTextured.setUniform4f("u_tex", 0, 0, 1, 1);
    defaultTextured.setUniform4f("u_alphaColor", 1, 1, 1, 1);
    defaultTextured.setUniform1f("u_theta", copy.getAngle());
    setCameraUniform(defaultTextured);
    texture.bind();
    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, NULL);
  }

  /**
   * Draw a textured rectangle with a transparency.
   *
   * @param rect the rectangle to draw.
   * @param alpha the transparency of the rectangle.
   * @param texture the texture of the rectangle.
   */
  public static void drawTextureTransparent(Rectangle rect, float alpha, Texture texture) {
    Rectangle copy = new Rectangle(rect);
    copy.setPos(new Dot(copy.getX(), 1080 - copy.getY()));
    copy.resize(copy.getWidth(), -copy.getHeight());
    VertexArray.simpleQuad.bind();
    defaultTextured.bind();
    defaultTextured.setUniform4f(
        "u_position", copy.getX(), copy.getY(), copy.getWidth(), copy.getHeight());
    defaultTextured.setUniform4f("u_tex", 0, 0, 1, 1);
    defaultTextured.setUniform4f("u_alphaColor", 1, 1, 1, alpha);
    defaultTextured.setUniform1f("u_theta", copy.getAngle());
    setCameraUniform(defaultTextured);
    texture.bind();
    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, NULL);
  }

  /**
   * Draw a letter.
   *
   * @param c the letter to draw.
   * @param x the x position of the letter.
   * @param y the y position of the letter.
   * @param size the size of the letter.
   * @param color the color of the letter.
   * @param font the font of the letter.
   * @param angle the angle of the letter.
   */
  public static void drawLetter(
      char c, float x, float y, int size, Color color, Texture font, float angle) {
    y = 1080 - y;
    int width = size * 7;
    int height = -size * 8;

    VertexArray.simpleQuad.bind();
    defaultTextured.bind();
    defaultTextured.setUniform4f("u_position", x, y, width, height);

    int charCode = c;

    int line = 0;
    //�
    //�
    //�
    //�
    //�
    //�
    //�
    int column = switch (charCode) {
      case 232 -> {
        //�
        line = 8;
        yield 1;
      }
      case 233 -> {
        line = 8;
        yield 0;
      }
      case 234 -> {
        line = 8;
        yield 2;
      }
      case 235 -> {
        line = 8;
        yield 3;
      }
      case 249 -> {
        line = 8;
        yield 5;
      }
      case 224 -> {
        line = 8;
        yield 4;
      }
      case 238 -> {
        line = 8;
        yield 7;
      }
      case 239 -> {
        line = 8;
        yield 6;
      }
      case 231 -> {
        //�
        line = 8;
        yield 8;
      }
      default -> {
        line = charCode / 16;
        yield charCode % 16;
      }
    };
    line -= 2;
    if (line > 6) {
      line = 0;
      column = 0;
    }
    float charPosX = column / 16f;
    float charPosY = line / 7f;
    defaultTextured.setUniform4f("u_tex", charPosX, charPosY, 1 / 16f, 1 / 7f);
    defaultTextured.setUniform4f("u_alphaColor", color.valR, color.valG, color.valB, color.valA);
    defaultTextured.setUniform1f("u_theta", angle);
    setCameraUniform(defaultTextured);
    font.bind();
    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, NULL);
  }
}
