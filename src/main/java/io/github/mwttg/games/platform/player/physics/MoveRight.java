package io.github.mwttg.games.platform.player.physics;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.TransformUtilities;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.colision.SensorSystem;
import org.joml.Matrix4f;

public final class MoveRight {

  private MoveRight() {
  }

  public static void execute(final float deltaTime,
                             final PlayerData playerData,
                             final Matrix4f transform,
                             final SensorComponent sensorComponent) {
    moveRight(deltaTime, transform);
    handleCollision(playerData, sensorComponent, transform);
  }

  private static void moveRight(final float deltaTime,
                               final Matrix4f transform) {
    final var deltaX = Configuration.PLAYER_WALK_VELOCITY * deltaTime;
    transform.translate(deltaX, 0.0f, 0.0f);
  }

  private static void handleCollision(final PlayerData playerData,
                                      final SensorComponent sensorComponent,
                                      final Matrix4f transform) {
    final var blocked = SensorSystem.isRightBlocked(transform, playerData.getTileSize(), sensorComponent);
    if (blocked) {
      final var position = TransformUtilities.getPosition(transform);
      final var correctionX = -(position.x() - (int) position.x()); // snap to Grid
      transform.translate(correctionX, 0.0f, 0.0f);
    }
  }
}
