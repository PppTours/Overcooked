package fr.ovrckdlike.ppp.internal;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;

import java.util.ArrayList;
import java.util.List;

public class VertexBufferLayout {

  public class VertexBufferLayoutElement {
    int type;
    int count;

    VertexBufferLayoutElement(int type, int count) {
      this.type = type;
      this.count = count;
    }

    /**
     * Return the size in bytes of the layout element.
     */
    int getSize() {
      return (
        type == GL_FLOAT         ? 4 :
        type == GL_UNSIGNED_BYTE ? 1 :
        type == GL_UNSIGNED_INT  ? 4 : 0
        ) * count;
    }
  }

  public List<VertexBufferLayoutElement> elements = new ArrayList<>();
  public int stride;

  public VertexBufferLayout addFloats(int count) {
    elements.add(new VertexBufferLayoutElement(GL_FLOAT, count));
    stride += elements.get(elements.size() - 1).getSize();
    return this;
  }

  public VertexBufferLayout addUnsignedBytes(int count) {
    elements.add(new VertexBufferLayoutElement(GL_UNSIGNED_BYTE, count));
    stride += elements.get(elements.size() - 1).getSize();
    return this;
  }

  public VertexBufferLayout addUnsignedInt(int count) {
    elements.add(new VertexBufferLayoutElement(GL_UNSIGNED_INT, count));
    stride += elements.get(elements.size() - 1).getSize();
    return this;
  }

  // 2 floats for position
  public static final VertexBufferLayout simplePositionLayout
      = new VertexBufferLayout().addFloats(2);
  // 2 floats for position   2 floats for texture mapping
  public static final VertexBufferLayout texturedLayout
      = new VertexBufferLayout().addFloats(2).addFloats(2);

}
