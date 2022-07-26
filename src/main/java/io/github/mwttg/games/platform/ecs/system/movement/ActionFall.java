package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.movement.MovementStateComponent;

class ActionFall {

  private ActionFall() {
  }

  static float getDeltaDistanceY(final MovementStateComponent movementStateComponent,
                                 final GameState gameState,
                                 final float deltaTime) {
    movementStateComponent.updateFallTiming(deltaTime);

    final var currentDuration = movementStateComponent.getFallTimings().getCurrentDuration();
    final var lastDuration = movementStateComponent.getFallTimings().getLastDuration();
    final var downGravity = gameState.sceneConfiguration().verticalDownGravity();

    final var lastDistance = (-downGravity / 2.0f * lastDuration * lastDuration);
    final var currentDistance = (-downGravity / 2.0f * currentDuration * currentDuration);
    return currentDistance - lastDistance;
  }
}
