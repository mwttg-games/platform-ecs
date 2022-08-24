package io.github.mwttg.games.platform.player.physics;

import static io.github.mwttg.games.platform.Configuration.JUMP_UP_GRAVITY;
import static io.github.mwttg.games.platform.Configuration.PLAYER_JUMP_VELOCITY;

import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.TransformUtilities;
import io.github.mwttg.games.platform.player.colision.GridComponent;
import io.github.mwttg.games.platform.player.colision.GridSystem;
import org.joml.Matrix4f;

public final class JumpUp {

  private JumpUp() {
  }

  public static void execute(final float inAirTime,
                             final float deltaTime,
                             final PlayerData playerData,
                             final Matrix4f transform,
                             final GridComponent gridComponent) {
    jumpUp(inAirTime, deltaTime, transform);
    handleCollision(playerData, gridComponent, transform);
  }

  private static void jumpUp(final float inAirTime, final float deltaTime, final Matrix4f transform) {
    final var lastDistance = (-JUMP_UP_GRAVITY / 2.0f * inAirTime * inAirTime) + (PLAYER_JUMP_VELOCITY * inAirTime);
    final var currentInAirTime = inAirTime + deltaTime;
    final var currentDistance =
        (-JUMP_UP_GRAVITY / 2.0f * currentInAirTime * currentInAirTime) + (PLAYER_JUMP_VELOCITY * currentInAirTime);
    final var deltaY = currentDistance - lastDistance;
    transform.translate(0.0f, deltaY, 0.0f);
  }

  public static void handleCollision(final PlayerData playerData,
                                     final GridComponent gridComponent,
                                     final Matrix4f transform) {
    final var blocked = GridSystem.isTopBlocked(transform, playerData.getTileSize(), gridComponent);
    if (blocked) {
      final var position = TransformUtilities.getPosition(transform);
      final var correctionY = -(position.y() - (int) position.y());
      transform.translate(0.0f, correctionY, 0.0f);
    }
  }
}
