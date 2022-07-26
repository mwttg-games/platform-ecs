package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.movement.MovementState;
import io.github.mwttg.games.platform.ecs.component.movement.SolidGridComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TileSize;
import io.github.mwttg.games.platform.ecs.system.input.PlayerInputSystem;
import org.joml.Matrix4f;

public class MovementSystem {

  private MovementSystem() {
  }

  public static void update(final long windowId,
                            final Matrix4f transform,
                            final TileSize tileSize,
                            final MovementState movementState,
                            final SolidGridComponent solidGridComponent,
                            final float deltaTime,
                            final GameState gameState) {
    final var playerAction = PlayerInputSystem.getPlayerAction(windowId);
    final var horizontalVelocity = HorizontalVelocitySystem.getHorizontalVelocity(playerAction, gameState);
    MovementStateSystem.setState(movementState, horizontalVelocity, playerAction);
    final var delta = DeltaMovementSystem.update(movementState, horizontalVelocity, deltaTime, gameState);
    transform.translate(delta); // mutable
    final var blockedDirections = SolidGridSystem.getBlockedDirections(solidGridComponent, transform, tileSize);
    final var correction =
        MovementCollisionSystem.getCorrectionTranslation(blockedDirections, transform, movementState);
    transform.translate(correction);
  }
}
