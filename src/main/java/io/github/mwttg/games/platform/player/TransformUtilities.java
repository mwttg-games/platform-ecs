package io.github.mwttg.games.platform.player;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class TransformUtilities {

  private TransformUtilities() {
  }

  public static Vector3f getPosition(final Matrix4f transform) {
    final var result = new Vector3f();
    transform.getTranslation(result);
    return result;
  }
}
