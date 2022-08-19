package io.github.mwttg.games.platform.input;

import org.lwjgl.glfw.GLFW;

public final class PlayerInputSystem {

  private static float JUMP_TIMER = 0.0f;
  private static boolean LAST_JUMP = false;

  private PlayerInputSystem() {
  }

  public static PlayerInput getPlayerInput(final long windowId, final float deltaTime) {
    final var dx = getXAxis(windowId);
    final var dy = getYAxis(windowId);
    final var jump = getJump(windowId, deltaTime);

    return new PlayerInput(dx, dy, jump);
  }

  private static  KeyInput getJump(final long windowId, final float deltaTime) {
    if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_RELEASE && LAST_JUMP) {
      final var temp = JUMP_TIMER;
      JUMP_TIMER = 0.0f;
      LAST_JUMP = false;
      return new KeyInput(true, temp);
    }

    if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS) {
      LAST_JUMP = true;
      JUMP_TIMER = JUMP_TIMER + deltaTime;
    }

    return new KeyInput(false, 0.0f);
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
