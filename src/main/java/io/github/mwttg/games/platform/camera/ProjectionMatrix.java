package io.github.mwttg.games.platform.camera;

import org.joml.Matrix4f;

public final class ProjectionMatrix {

  private static final Matrix4f INSTANCE = new Matrix4f().setOrtho(-10.0f, 10.0f, -5.0f, 5.0f, 0.01f, 100.0f);


  private ProjectionMatrix() {
  }

  public static Matrix4f get() {
    return INSTANCE;
  }
}
