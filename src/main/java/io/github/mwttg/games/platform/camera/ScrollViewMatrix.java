package io.github.mwttg.games.platform.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class ScrollViewMatrix {

  private static final Vector3f BUFFER = new Vector3f();

  private ScrollViewMatrix() {
  }

  public static Matrix4f get(final Matrix4f transform) {
    transform.getTranslation(BUFFER);

    return new Matrix4f().setLookAt(BUFFER.x(), BUFFER.y(), 1.0f, BUFFER.x(), BUFFER.y(), 0.0f, 0.0f, 1.0f, 0.0f);
  }
}
