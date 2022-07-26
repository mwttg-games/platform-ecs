package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.movement.MovementState;

class ActionJump {

  private ActionJump() {
  }

  static float getDeltaDistanceY(final MovementState movementState,
                                 final GameState gameState,
                                 final float deltaTime) {
    movementState.updateJumpTiming(deltaTime);

    final var currentDuration = movementState.getJumpTimings().getCurrentDuration();
    final var lastDuration = movementState.getJumpTimings().getLastDuration();
    final var upGravity = gameState.sceneConfiguration().verticalUpGravity();
    final var initJumpVelocity = gameState.playerConfiguration().initialJumpVelocity();

    final var lastDistance = (-upGravity / 2.0f * lastDuration * lastDuration) + (initJumpVelocity * lastDuration);
    final var currentDistance = (-upGravity / 2.0f * currentDuration * currentDuration) + (initJumpVelocity * currentDuration);
    return currentDistance - lastDistance;
  }
}
