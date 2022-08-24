package io.github.mwttg.games.platform.player.physics;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.colision.GridComponent;
import io.github.mwttg.games.platform.player.colision.GridSystem;
import io.github.mwttg.games.platform.player.TransformUtilities;
import org.joml.Matrix4f;

public final class MoveLeft {

  private MoveLeft() {
  }

  public static void execute(final float deltaTime,
                             final PlayerData playerData,
                             final Matrix4f transform,
                             final GridComponent gridComponent) {
    moveLeft(deltaTime, transform);
    handleCollision(playerData, gridComponent, transform);
  }

  private static void moveLeft(final float deltaTime,
                               final Matrix4f transform) {
    final var deltaX = -Configuration.PLAYER_WALK_VELOCITY * deltaTime;
    transform.translate(deltaX, 0.0f, 0.0f);
  }

  private static void handleCollision(final PlayerData playerData,
                                      final GridComponent gridComponent,
                                      final Matrix4f transform) {
    final var blocked = GridSystem.isLeftBlocked(transform, playerData.getTileSize(), gridComponent);
    if (blocked) {
      final var position = TransformUtilities.getPosition(transform);
      final var correctionX = ((int) position.x() + 1.0f) - position.x(); // snap to Grid
      transform.translate(correctionX, 0.0f, 0.0f);
    }
  }
}
