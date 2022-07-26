package io.github.mwttg.games.platform.ecs.system.input;

import io.github.mwttg.games.platform.ecs.component.input.PlayerAction;
import org.lwjgl.glfw.GLFW;

public class PlayerInputSystem {

  public static PlayerAction getPlayerAction(final long windowId) {
    return new PlayerAction(
        isJumpingUp(windowId),
        isWalkingLeft(windowId),
        isWalkingRight(windowId));
  }

  private static boolean isWalkingLeft(final long windowId) {
    return isKeyPressed(windowId, GLFW.GLFW_KEY_LEFT);
  }

  private static boolean isWalkingRight(final long windowId) {
    return isKeyPressed(windowId, GLFW.GLFW_KEY_RIGHT);
  }

  private static boolean isJumpingUp(final long windowId) {
    return isKeyPressed(windowId, GLFW.GLFW_KEY_SPACE);
  }

  private static boolean isKeyPressed(final long windowId, final int key) {
    return GLFW.glfwGetKey(windowId, key) == GLFW.GLFW_PRESS;
  }
}
