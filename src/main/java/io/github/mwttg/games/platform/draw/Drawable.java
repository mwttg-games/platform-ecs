package io.github.mwttg.games.platform.draw;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL41;

public interface Drawable {

  void draw(final Matrix4f model, final Matrix4f view, final Matrix4f projection);

  static void enableVertexAttribArray() {
    GL41.glEnableVertexAttribArray(0); // vertices
    GL41.glEnableVertexAttribArray(1); // texture coordinates
  }

  static void disableVertexAttribArray() {
    GL41.glDisableVertexAttribArray(1); // texture coordinates
    GL41.glDisableVertexAttribArray(0); // vertices
  }
}
