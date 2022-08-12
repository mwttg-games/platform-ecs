package io.github.mwttg.games.platform.player.physics;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import io.github.mwttg.games.platform.player.SolidGridSystem;
import io.github.mwttg.games.platform.player.TransformUtilities;
import org.joml.Matrix4f;

public final class MoveRight {

  private MoveRight() {
  }

  public static void execute(final float deltaTime,
                             final PlayerData playerData,
                             final Matrix4f transform,
                             final SolidGridComponent solidGridComponent) {
    moveRight(deltaTime, transform);
    handleCollision(playerData, solidGridComponent, transform);
  }

  private static void moveRight(final float deltaTime,
                               final Matrix4f transform) {
    final var deltaX = Configuration.PLAYER_WALK_VELOCITY * deltaTime;
    transform.translate(deltaX, 0.0f, 0.0f);
  }

  private static void handleCollision(final PlayerData playerData,
                                      final SolidGridComponent solidGridComponent,
                                      final Matrix4f transform) {
    final var blocked = SolidGridSystem.isRightBlocked(transform, playerData.getTileSize(), solidGridComponent);
    if (blocked) {
      final var position = TransformUtilities.getPosition(transform);
      final var correctionX = -(position.x() - (int) position.x()); // snap to Grid
      transform.translate(correctionX, 0.0f, 0.0f);
    }
  }
}
