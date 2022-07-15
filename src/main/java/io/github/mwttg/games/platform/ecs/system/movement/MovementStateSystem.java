package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.movement.MovementStateComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;
import io.github.mwttg.games.platform.ecs.component.movement.VelocityComponent;

public class MovementStateSystem {

  public static void update(final MovementStateComponent movementStateComponent,
                            final TransformComponent transformComponent,
                            final VelocityComponent velocityComponent,
                            final float deltaTime,
                            final GameState gameState) {
    final var horizontalVelocity = velocityComponent.getHorizontal();
    final var currentRiseTime = movementStateComponent.getJumpTimings().getCurrentDuration();
    final var maxRiseTime = velocityComponent.getRiseTime();

    // jump movement
    if (movementStateComponent.isJumping() &&  currentRiseTime <= maxRiseTime) {
      ActionJump.execute(movementStateComponent, transformComponent, gameState, deltaTime);
    }

    if (movementStateComponent.isJumping() && currentRiseTime > maxRiseTime) {
      movementStateComponent.activateFalling();
    }


    // left right movement
    if (movementStateComponent.isWalkingLeft() || movementStateComponent.isWalkingRight()) {
      ActionWalk.execute(transformComponent, horizontalVelocity, deltaTime);
    }

    // fall movement
    if (movementStateComponent.isFalling()) {
      ActionFall.execute(movementStateComponent, transformComponent, gameState, deltaTime);
    }
  }
}
