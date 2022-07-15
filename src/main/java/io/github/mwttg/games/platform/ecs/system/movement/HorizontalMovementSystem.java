package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.input.PlayerInputComponent;
import io.github.mwttg.games.platform.ecs.component.movement.MovementStateComponent;
import io.github.mwttg.games.platform.ecs.component.movement.VelocityComponent;

public class HorizontalMovementSystem {

  public static void update(final PlayerInputComponent playerInputComponent,
                            final VelocityComponent velocityComponent,
                            final MovementStateComponent movementStateComponent,
                            final GameState gameState) {
    final var walkVelocity = gameState.playerConfiguration().walkVelocity();
    var leftVelocityAddend = 0.0f;
    var rightVelocityAddend = 0.0f;

    if (playerInputComponent.getLeft().isPressed()) {
      leftVelocityAddend = -walkVelocity;
    }

    if (playerInputComponent.getRight().isPressed()) {
      rightVelocityAddend = walkVelocity;
    }

    final var resultVelocity = leftVelocityAddend + rightVelocityAddend;
    setState(movementStateComponent, resultVelocity);
    velocityComponent.setHorizontal(resultVelocity);
  }

  private static void setState(MovementStateComponent movementStateComponent, float resultVelocity) {
    if (resultVelocity < 0.0f) {
      movementStateComponent.activateWalkingLeft();
    }

    if (resultVelocity > 0.0f) {
      movementStateComponent.activateWalkingRight();
    }

    if (resultVelocity == 0.0f) {
      movementStateComponent.deactivateWalking();
    }
  }
}
