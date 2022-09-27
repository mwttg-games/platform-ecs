package io.github.mwttg.games.platform.camera;

import io.github.mwttg.games.platform.world.Scene;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class ScrollViewMatrix {

  private static final Vector3f BUFFER = new Vector3f();

  private ScrollViewMatrix() {
  }

  public static Matrix4f get(final Matrix4f transform, final Scene scene) {
    final var dimension = scene.dimension();
    transform.getTranslation(BUFFER);

    final float cameraX;
    final float cameraY;

    float minY = 5.0f;
    float maxY = dimension.height() - 5.0f;
    float minX = 10.0f;
    float maxX = dimension.width() - 10.0f;

    if (BUFFER.y() > maxY) {
      cameraY = dimension.height() - 10.0f;
    } else if (BUFFER.y() < minY) {
      cameraY = 0;
    } else {
      cameraY = BUFFER.y() - 5.0f;
    }


    if (BUFFER.x() > maxX) {
      cameraX = dimension.width() - 20.0f;
    } else if (BUFFER.x() < minX) {
      cameraX = 0;
    } else {
      cameraX = BUFFER.x() - 10.0f;
    }

    return new Matrix4f().setLookAt(cameraX, cameraY, 1.0f, cameraX, cameraY, 0.0f, 0.0f, 1.0f, 0.0f);
  }
}
