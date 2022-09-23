package io.github.mwttg.games.platform.level;

import io.github.mwttg.games.platform.camera.FixedViewMatrix;
import io.github.mwttg.games.platform.camera.ScrollViewMatrix;
import io.github.mwttg.games.platform.entity.PlayerEntity;
import io.github.mwttg.games.platform.entity.WorldEntity;
import org.joml.Matrix4f;

public final class CameraSystem {

  public static Matrix4f getViewMatrix(final WorldEntity worldEntity, final PlayerEntity playerEntity) {
    final var transform = playerEntity.transform();
    final var component = worldEntity.levelStateComponent().getLevelComponent();

    if (component.cameraType() == CameraType.FIXED) {
      return FixedViewMatrix.get();
    } else {
      return ScrollViewMatrix.get(transform, component);
    }
  }
}
