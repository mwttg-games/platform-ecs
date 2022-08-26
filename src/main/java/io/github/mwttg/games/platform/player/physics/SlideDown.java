package io.github.mwttg.games.platform.player.physics;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.TransformUtilities;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.colision.SensorSystem;
import org.joml.Matrix4f;

public final class SlideDown {

  private SlideDown() {
  }

  public static void execute(final float deltaTime,
                             final PlayerData playerData,
                             final Matrix4f transform,
                             final SensorComponent sensorComponent) {
    moveDown(deltaTime, transform);
    handleCollision(playerData, sensorComponent, transform);
  }

  private static void moveDown(final float deltaTime,
                               final Matrix4f transform) {
    final var deltaY = -Configuration.PLAYER_CLIMB_VELOCITY * deltaTime;
    transform.translate(0.0f, deltaY, 0.0f);
  }

  private static void handleCollision(final PlayerData playerData,
                                      final SensorComponent sensorComponent,
                                      final Matrix4f transform) {
    final var blocked = SensorSystem.isBottomBlocked(transform, playerData.getTileSize(), sensorComponent);
    final var ladder = SensorSystem.isLadderBelow(transform, playerData.getTileSize(), sensorComponent);
    if (blocked && !ladder) {
      final var position = TransformUtilities.getPosition(transform);
      final var correctionY = ((int) position.y() + 1.0f) - position.y();
      transform.translate(0.0f, correctionY, 0.0f);
    }
  }
}
