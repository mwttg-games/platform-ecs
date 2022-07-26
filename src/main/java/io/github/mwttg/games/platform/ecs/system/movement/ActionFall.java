package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.movement.MovementState;

class ActionFall {

  private ActionFall() {
  }

  static float getDeltaDistanceY(final MovementState movementState,
                                 final GameState gameState,
                                 final float deltaTime) {
    movementState.updateFallTiming(deltaTime);

    final var currentDuration = movementState.getFallTimings().getCurrentDuration();
    final var lastDuration = movementState.getFallTimings().getLastDuration();
    final var downGravity = gameState.sceneConfiguration().verticalDownGravity();

    final var lastDistance = (-downGravity / 2.0f * lastDuration * lastDuration);
    final var currentDistance = (-downGravity / 2.0f * currentDuration * currentDuration);
    return currentDistance - lastDistance;
  }
}
