package fr.ovrckdlike.ppp.internal;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class IndexBuffer {

  private final int bufferId;
  private final int dataSize;

  public IndexBuffer(int[] data) {
    bufferId = glGenBuffers();
    dataSize = data.length;
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferId);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW);
    unbind();
  }

  public void bind() {
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferId);
  }

  public static void unbind() {
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
  }

  public void dispose() {
    glDeleteBuffers(bufferId);
  }

  public int size() {
    return dataSize;
  }

  public static final IndexBuffer simpleQuadIndices = new IndexBuffer(new int[] {
      0, 1, 2,
      2, 3, 0
  });
}
