package io.github.mwttg.games.platform.player.physics;

import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.TransformUtilities;
import org.joml.Matrix4f;

public final class SnapToGrid {

  private SnapToGrid() {
  }

  public static void snapX(final Matrix4f transform, final PlayerData playerData) {
    final var position = TransformUtilities.getPosition(transform);
    final var half = playerData.getTileSize().width() / 2.0f;
    final var centerX = position.x() + half;
    final var gridX = (int) centerX;

    if (position.x() <= gridX + half) {
      final var deltaX = gridX - position.x();
      transform.translate(deltaX, 0.0f, 0.0f);
    } else {
      final var deltaX = position.x() - gridX;
      transform.translate(deltaX, 0.0f, 0.0f);
    }
  }
}
