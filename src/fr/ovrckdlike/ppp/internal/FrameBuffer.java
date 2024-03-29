package fr.ovrckdlike.ppp.internal;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.GL_READ_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glBlitFramebuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorageMultisample;

import fr.ovrckdlike.ppp.graphics.Window;

/**
 * Deprecated ?.
 */
public class FrameBuffer {

  private static FrameBuffer boundBuffer;

  private final int id;
  public int textureId;
  public final int width;
  public final int height;

  public FrameBuffer(int width, int height) {
    this.id = glGenFramebuffers();
    this.width = width;
    this.height = height;
    bind();
  }

  public void addTextureTarget(int textureType, int attachment) {
    int id = glGenTextures();
    glBindTexture(GL_TEXTURE_2D, id);
    glTexImage2D(GL_TEXTURE_2D, 0, textureType, width, height, 0, textureType, GL_UNSIGNED_BYTE, 0);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    glFramebufferTexture2D(GL_FRAMEBUFFER, attachment, GL_TEXTURE_2D, id, 0);
    glBindTexture(GL_TEXTURE_2D, 0);
    if (attachment == GL_COLOR_ATTACHMENT0) {
      this.textureId = id;
    }
  }

  public void addRenderBuffer(int msaa, int format, int attachment) {
    int rbo = glGenRenderbuffers();
    glBindRenderbuffer(GL_RENDERBUFFER, rbo);
    glRenderbufferStorageMultisample(GL_RENDERBUFFER, msaa, format, width, height);
    glFramebufferRenderbuffer(GL_FRAMEBUFFER, attachment, GL_RENDERBUFFER, rbo);
  }

  public void finish() {
    int status = glCheckFramebufferStatus(GL_FRAMEBUFFER);
    if (status != GL_FRAMEBUFFER_COMPLETE) {
      throw new IllegalStateException("Error while creating a frame buffer " + status);
    }
    unbind();
  }

  public void bind() {
    if (boundBuffer == this) {
      return;
    }
    glBindFramebuffer(GL_FRAMEBUFFER, id);
    glViewport(0, 0, width, height);
    boundBuffer = this;
  }

  public static void unbind() {
    if (boundBuffer == null) {
      return;
    }
    glBindFramebuffer(GL_FRAMEBUFFER, 0);
    glViewport(0, 0, Window.getWindow().getWidth(), Window.getWindow().getHeight());
    boundBuffer = null;
  }

  public void dispose() {
    glDeleteFramebuffers(id);
  }

  public void bindTexture() {
    glBindTexture(GL_TEXTURE_2D, textureId);
  }

  public static void blitMsaa(FrameBuffer src, FrameBuffer dst) {
    glBindFramebuffer(GL_READ_FRAMEBUFFER, src.id);
    glBindFramebuffer(GL_DRAW_FRAMEBUFFER, dst.id);
    glBlitFramebuffer(0, 0, src.width, src.height,
        0, 0, src.width, src.height,
        GL_COLOR_BUFFER_BIT, GL_LINEAR);
  }

  public static void unbindTexture() {
    glBindTexture(GL_TEXTURE_2D, 0);
  }

}
