package io.github.mwttg.games.platform.camera;

import org.joml.Matrix4f;

public final class FixedViewMatrix {

  private static final Matrix4f INSTANCE = new Matrix4f().setLookAt(0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

  private FixedViewMatrix() {
  }

  public static Matrix4f get() {
    return INSTANCE;
  }
}
