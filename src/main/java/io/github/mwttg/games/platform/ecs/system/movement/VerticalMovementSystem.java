package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.input.PlayerInputComponent;
import io.github.mwttg.games.platform.ecs.component.movement.MovementStateComponent;
import io.github.mwttg.games.platform.ecs.component.movement.VelocityComponent;

public class VerticalMovementSystem {

  public static void update(final PlayerInputComponent playerInputComponent,
                            final VelocityComponent velocityComponent,
                            final MovementStateComponent movementStateComponent,
                            final GameState gameState) {

    if (playerInputComponent.getJump().isPressed()) {
      movementStateComponent.activateJumping();

      final var upGravity = gameState.sceneConfiguration().verticalUpGravity();
      final var initJumpVelocity = gameState.playerConfiguration().initialJumpVelocity();
      final var riseTime = initJumpVelocity / upGravity;
      velocityComponent.setRiseTime(riseTime);
    }
  }
}
