package fr.ovrckdlike.ppp.internal;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class VertexBuffer {

  private final int bufferId;

  public VertexBuffer(float... data) {
    bufferId = glGenBuffers();
    setData(data);
  }

  public void setData(float... data) {
    bind();
    glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
    unbind();
  }

  public void bind() {
    glBindBuffer(GL_ARRAY_BUFFER, bufferId);
  }

  public static void unbind() {
    glBindBuffer(GL_ARRAY_BUFFER, 0);
  }

  public void dispose() {
    glDeleteBuffers(bufferId);
  }

  public static final VertexBuffer simpleQuadBuffer = new VertexBuffer(
      0, 0,
      1, 0,
      1, 1,
      0, 1
  );

}
