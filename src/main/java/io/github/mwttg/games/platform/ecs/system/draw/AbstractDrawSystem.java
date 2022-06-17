package io.github.mwttg.games.platform.ecs.system.draw;

import org.lwjgl.opengl.GL41;

public abstract class AbstractDrawSystem {

  protected static void enableVertexAttribArray() {
    GL41.glEnableVertexAttribArray(0); // vertices
    GL41.glEnableVertexAttribArray(1); // texture coordinates
  }

  protected static void disableVertexAttribArray() {
    GL41.glDisableVertexAttribArray(1); // texture coordinates
    GL41.glDisableVertexAttribArray(0); // vertices
  }
}
