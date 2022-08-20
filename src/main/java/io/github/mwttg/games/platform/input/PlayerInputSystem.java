package io.github.mwttg.games.platform.input;

import org.lwjgl.glfw.GLFW;

public final class PlayerInputSystem {

  private PlayerInputSystem() {
  }

  public static PlayerInput getPlayerInput(final long windowId) {
    final var dx = getXAxis(windowId);
    final var dy = getYAxis(windowId);
    final var jump = getJump(windowId);

    return new PlayerInput(dx, dy, jump);
  }

  private static boolean getJump(final long windowId) {
    return GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS;
  }

  private static int getXAxis(final long windowId) {
    var dx = 0;

    if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_PRESS) {
      dx = dx - 1;
    }

    if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS) {
      dx = dx + 1;
    }

    return dx;
  }

  private static int getYAxis(final long windowId) {
    var dy = 0;

    if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_DOWN) == GLFW.GLFW_PRESS) {
      dy = dy - 1;
    }

    if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_UP) == GLFW.GLFW_PRESS) {
      dy = dy + 1;
    }

    return dy;
  }
}
