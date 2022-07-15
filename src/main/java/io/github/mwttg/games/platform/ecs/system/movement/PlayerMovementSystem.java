package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.input.PlayerInputComponent;
import io.github.mwttg.games.platform.ecs.component.movement.CollisionSensorComponent;
import io.github.mwttg.games.platform.ecs.component.movement.JumpComponent;
import io.github.mwttg.games.platform.ecs.component.movement.SolidGridComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;
import io.github.mwttg.games.platform.ecs.component.movement.VelocityComponent;
import io.github.mwttg.games.platform.ecs.system.input.PlayerInputSystem;

public class PlayerMovementSystem {
  public static void update(final long windowId,
                            final PlayerInputComponent playerInputComponent,
                            final JumpComponent jumpComponent,
                            final VelocityComponent velocityComponent,
                            final CollisionSensorComponent collisionSensorComponent,
                            final SolidGridComponent solidGridComponent,
                            final TransformComponent transformComponent,
                            final GameState gameState,
                            final float deltaTime) {
    PlayerInputSystem.update(windowId, playerInputComponent);
    PlayerHorizontalMovementSystem.update(transformComponent, playerInputComponent, velocityComponent, solidGridComponent,
        collisionSensorComponent, deltaTime, gameState.playerConfiguration());
    PlayerVerticalMovementSystem.update(transformComponent, playerInputComponent, jumpComponent, solidGridComponent,
        collisionSensorComponent, gameState);
  }
}
