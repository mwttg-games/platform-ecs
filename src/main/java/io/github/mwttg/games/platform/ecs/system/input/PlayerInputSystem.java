package io.github.mwttg.games.platform.ecs.system.input;

import io.github.mwttg.games.platform.ecs.component.input.PlayerInputComponent;
import org.lwjgl.glfw.GLFW;

public class PlayerInputSystem {

  public static void update(final long windowId, final PlayerInputComponent playerInputComponent) {
    // left
    if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_PRESS) {
      playerInputComponent.getLeft().pressKey();
    } else {
      playerInputComponent.getLeft().releaseKey();
    }

    // right
    if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS) {
      playerInputComponent.getRight().pressKey();
    } else {
      playerInputComponent.getRight().releaseKey();
    }

    // jump
    if (GLFW.glfwGetKey(windowId, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS) {
      playerInputComponent.getJump().pressKey();
    } else {
      playerInputComponent.getJump().releaseKey();
    }
  }
}
