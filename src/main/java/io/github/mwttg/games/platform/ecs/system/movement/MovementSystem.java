package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.input.PlayerInputComponent;
import io.github.mwttg.games.platform.ecs.component.movement.CollisionSensorComponent;
import io.github.mwttg.games.platform.ecs.component.movement.EntityTileSize;
import io.github.mwttg.games.platform.ecs.component.movement.MovementStateComponent;
import io.github.mwttg.games.platform.ecs.component.movement.SolidGridComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;
import io.github.mwttg.games.platform.ecs.component.movement.VelocityComponent;
import io.github.mwttg.games.platform.ecs.system.input.PlayerInputSystem;

public class MovementSystem {

  public static void update(final long windowId,
                            final PlayerInputComponent playerInputComponent,
                            final TransformComponent transformComponent,
                            final VelocityComponent velocityComponent,
                            final MovementStateComponent movementStateComponent,
                            final CollisionSensorComponent collisionSensorComponent,
                            final SolidGridComponent solidGridComponent,
                            final float deltaTime,
                            final GameState gameState) {
    PlayerInputSystem.update(windowId, playerInputComponent);
    HorizontalMovementSystem.update(playerInputComponent, velocityComponent, movementStateComponent, gameState);
    VerticalMovementSystem.update(playerInputComponent, velocityComponent, movementStateComponent, gameState);
    MovementStateSystem.update(movementStateComponent, transformComponent, velocityComponent, deltaTime, gameState);


    final var tileSize = new EntityTileSize(1.0f, 1.0f);
    final var blockedDirections = SolidGridSystem.getBlockedDirections(solidGridComponent, transformComponent, tileSize);
    MovementCollisionSystem.update(tileSize, blockedDirections, transformComponent.getModelMatrix(), movementStateComponent);
  }
}
