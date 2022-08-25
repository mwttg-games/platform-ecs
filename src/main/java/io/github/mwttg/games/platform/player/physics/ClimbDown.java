package io.github.mwttg.games.platform.player.physics;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.TransformUtilities;
import io.github.mwttg.games.platform.player.colision.GridComponent;
import io.github.mwttg.games.platform.player.colision.GridSystem;
import org.joml.Matrix4f;

public final class ClimbDown {

  private ClimbDown() {
  }

  public static void execute(final float deltaTime,
                             final PlayerData playerData,
                             final Matrix4f transform,
                             final GridComponent gridComponent) {
    moveDown(deltaTime, transform);
    handleCollision(playerData, gridComponent, transform);
  }

  private static void moveDown(final float deltaTime,
                               final Matrix4f transform) {
    final var deltaY = -Configuration.PLAYER_CLIMB_VELOCITY * deltaTime;
    transform.translate(0.0f, deltaY, 0.0f);
  }

  private static void handleCollision(final PlayerData playerData,
                                      final GridComponent gridComponent,
                                      final Matrix4f transform) {
    final var blocked = GridSystem.isBottomBlocked(transform, playerData.getTileSize(), gridComponent);
    final var ladder = GridSystem.isLadderBelow(transform, playerData.getTileSize(), gridComponent);
    if (blocked && !ladder) {
      final var position = TransformUtilities.getPosition(transform);
      final var correctionY = ((int) position.y() + 1.0f) - position.y();
      transform.translate(0.0f, correctionY, 0.0f);
    }
  }
}
