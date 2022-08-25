package io.github.mwttg.games.platform.player.physics;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.TransformUtilities;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.colision.SensorSystem;
import org.joml.Matrix4f;

public final class FallDown {

  private FallDown() {
  }

  public static void execute(final float inAirTime,
                             final float deltaTime,
                             final PlayerData playerData,
                             final Matrix4f transform,
                             final SensorComponent sensorComponent) {
    fallDown(inAirTime, deltaTime, transform);
    handleCollision(playerData, sensorComponent, transform);
  }

  private static void fallDown(final float inAirTime, final float deltaTime, final Matrix4f transform) {
    final var lastDistance = (-Configuration.FALL_DOWN_GRAVITY / 2.0f) * inAirTime * inAirTime;
    final var currentInAirTime = inAirTime + deltaTime;
    final var currentDistance = (-Configuration.FALL_DOWN_GRAVITY / 2.0f) * currentInAirTime * currentInAirTime;
    final var deltaY = currentDistance - lastDistance;
    transform.translate(0.0f, deltaY, 0.0f);
  }

  private static void handleCollision(final PlayerData playerData,
                                     final SensorComponent sensorComponent,
                                     final Matrix4f transform) {
    final var onGround = SensorSystem.isBottomBlocked(transform, playerData.getTileSize(), sensorComponent);
    if (onGround) {
      final var position = TransformUtilities.getPosition(transform);
      final var correctionY = ((int) position.y() + 1.0f) - position.y();
      transform.translate(0.0f, correctionY, 0.0f);
    }
  }
}
