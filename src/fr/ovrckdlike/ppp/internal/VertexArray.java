package fr.ovrckdlike.ppp.internal;

import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;

public class VertexArray {

  private final int arrayId;

  public VertexArray() {
    arrayId = glGenVertexArrays();
  }

  public void bind() {
    glBindVertexArray(arrayId);
  }

  /**
   * Bind a buffer to this vertex array object and bind it a layout,
   * therefore when the layout will be called, all the added vertex
   * buffers will be used with their layouts.<br>
   * May be called only once !
   *
   * @param buffer The buffer to bind.
   * @param layout The layout to bind.
   */
  public void setBuffer(VertexBuffer buffer, VertexBufferLayout layout) {
    bind();
    buffer.bind();
    int offset = 0;
    for (int i = 0; i < layout.elements.size(); i++) {
      VertexBufferLayout.VertexBufferLayoutElement elem = layout.elements.get(i);
      glEnableVertexAttribArray(i);
      glVertexAttribPointer(i, elem.count, elem.type, false, layout.stride, offset);
      offset += elem.getSize();
    }
    unbind();
    VertexBuffer.unbind();
  }

  public void setIndices(IndexBuffer indices) {
    bind();
    indices.bind();
    unbind();
  }

  public static void unbind() {
    glBindVertexArray(0);
  }

  public void dispose() {
    glDeleteVertexArrays(arrayId);
  }

  /**
   * simple quad vertex array.
   */
  public static final VertexArray simpleQuad = new VertexArray();

  // initialize the simpleQuadArray vao
  static {
    simpleQuad.setBuffer(VertexBuffer.simpleQuadBuffer, VertexBufferLayout.simplePositionLayout);
    simpleQuad.setIndices(IndexBuffer.simpleQuadIndices);
  }
}
