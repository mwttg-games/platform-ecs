package io.github.mwttg.games.platform.player;

import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;

public final class PlayerInputSystem {

  private PlayerInputSystem() {
  }

  public static Vector2i getPlayerInput(final long windowId) {
    var dx = 0;
    var dy = 0;

    if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_PRESS) {
      dx = dx - 1;
    }

    if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS) {
      dx = dx + 1;
    }

    if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS) {
      dy = dy + 1;
    }

    return new Vector2i(dx, dy);
  }
}
