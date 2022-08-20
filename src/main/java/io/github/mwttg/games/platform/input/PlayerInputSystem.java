package io.github.mwttg.games.platform.input;

import org.lwjgl.glfw.GLFW;

public final class PlayerInputSystem {

  private static boolean WAS_JUMP_PRESSED_BEFORE = false;

  private PlayerInputSystem() {
  }

  public static PlayerInput getPlayerInput(final long windowId) {
    final var dx = getXAxis(windowId);
    final var dy = getYAxis(windowId);
    final var jump = getJump(windowId);

    return new PlayerInput(dx, dy, jump);
  }

  // GLFW.GLFW_PRESS is true for multiple cycles (in the main loop), that means it's a bit difficult to check if the jump
  // button was pressed, specifically when doing a double jump, this work around makes the double jump mechanics a bit easier
  private static boolean getJump(final long windowId) {
    if (!WAS_JUMP_PRESSED_BEFORE && GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS) {
      WAS_JUMP_PRESSED_BEFORE = true;
      return true;
    }

    if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_RELEASE) {
      WAS_JUMP_PRESSED_BEFORE = false;
    }

    return false;
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
