package fr.ovrckdlike.ppp.internal;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Shader {

  private static Shader boundShader;

  private final String name;
  private final int id;
  private final Map<String, Integer> uniformCache = new HashMap<String, Integer>();

  private Shader(String name, int id) {
    this.name = name;
    this.id = id;
  }

  public static Shader compileAndCreateShader(String vertexPath, String fragmentPath)
      throws IllegalArgumentException, IOException {
    int id = glCreateProgram();
    String name = vertexPath + "|" + fragmentPath;
    glAttachShader(id, loadShaderFile(vertexPath, GL_VERTEX_SHADER));
    glAttachShader(id, loadShaderFile(fragmentPath, GL_FRAGMENT_SHADER));
    glLinkProgram(id);
    glValidateProgram(id);
    if (glGetProgrami(id, GL_VALIDATE_STATUS) == GL_FALSE) {
      throw new IllegalArgumentException(
          "Unable to validate shader program " + name + " ! " + glGetProgramInfoLog(id));
    }
    return new Shader(name, id);
  }

  private static int loadShaderFile(String shaderPath, int type)
      throws IOException, IllegalArgumentException {
    int shader = glCreateShader(type);
    try (InputStream is = Shader.class.getResourceAsStream(shaderPath)) {
      if (is == null) {
        throw new IOException("Resource " + shaderPath + " does not exist");
      }
      glShaderSource(shader, new String(is.readAllBytes()));
    } catch (IOException e) {
      throw new IOException("Unable to load shader " + shaderPath, e);
    }
    glCompileShader(shader);
    if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
      throw new IllegalArgumentException(
          "Unable to parse the shader " + shaderPath + " !\n" + glGetShaderInfoLog(shader));
    }
    return shader;
  }

  public void bind() {
    if (boundShader != this) {
      glUseProgram(id);
      boundShader = this;
    }
  }

  public static void unBind() {
    glUseProgram(0);
    boundShader = null;
  }

  public void setUniform1i(String name, int i) {
    assertBound();
    glUniform1i(getUniformLoc(name), i);
  }

  public void setUniform1f(String name, float f) {
    assertBound();
    glUniform1f(getUniformLoc(name), f);
  }

  public void setUniform2f(String name, float f1, float f2) {
    assertBound();
    glUniform2f(getUniformLoc(name), f1, f2);
  }

  public void setUniform3f(String name, float f1, float f2, float f3) {
    assertBound();
    glUniform3f(getUniformLoc(name), f1, f2, f3);
  }

  public void setUniform4f(String name, float f1, float f2, float f3, float f4) {
    assertBound();
    glUniform4f(getUniformLoc(name), f1, f2, f3, f4);
  }

  //public void setUniformMat4(String name, Matrix4f matrix) {
  //  assertBound();
  //  try(MemoryStack stack = MemoryStack.stackPush()) {
  //    glUniformMatrix4fv(getUniformLoc(name), false, matrix.get(stack.mallocFloat(16)));
  //  }
  //}

  private void assertBound() {
    if (boundShader != this) {
      throw new IllegalStateException("Cannot bind a uniform of unbound shader");
    }
  }

  private int getUniformLoc(String name) {
    int loc;
    if (uniformCache.containsKey(name)) {
      loc = uniformCache.get(name);
    } else {
      loc = glGetUniformLocation(id, name);
      uniformCache.put(name, loc);
      if (loc == -1) {
        System.out.println("A uniform does not exist in a shader ! " + name + " - " + this.name);
      }
    }
    return loc;
  }

}
