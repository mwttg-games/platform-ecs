package io.github.mwttg.games.platform.player.physics;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.colision.GridComponent;
import io.github.mwttg.games.platform.player.colision.GridSystem;
import io.github.mwttg.games.platform.player.TransformUtilities;
import org.joml.Matrix4f;

public final class FallDown {

  private FallDown() {
  }

  public static void execute(final float inAirTime,
                             final float deltaTime,
                             final PlayerData playerData,
                             final Matrix4f transform,
                             final GridComponent gridComponent) {
    fallDown(inAirTime, deltaTime, transform);
    handleCollision(playerData, gridComponent, transform);
  }

  private static void fallDown(final float inAirTime, final float deltaTime, final Matrix4f transform) {
    final var lastDistance = (-Configuration.FALL_DOWN_GRAVITY / 2.0f) * inAirTime * inAirTime;
    final var currentInAirTime = inAirTime + deltaTime;
    final var currentDistance = (-Configuration.FALL_DOWN_GRAVITY / 2.0f) * currentInAirTime * currentInAirTime;
    final var deltaY = currentDistance - lastDistance;
    transform.translate(0.0f, deltaY, 0.0f);
  }

  private static void handleCollision(final PlayerData playerData,
                                     final GridComponent gridComponent,
                                     final Matrix4f transform) {
    final var onGround = GridSystem.isBottomBlocked(transform, playerData.getTileSize(), gridComponent);
    if (onGround) {
      final var position = TransformUtilities.getPosition(transform);
      final var correctionY = ((int) position.y() + 1.0f) - position.y();
      transform.translate(0.0f, correctionY, 0.0f);
    }
  }
}
