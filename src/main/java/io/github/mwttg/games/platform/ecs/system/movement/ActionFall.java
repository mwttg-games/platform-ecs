package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.movement.MovementStateComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;

class ActionFall {

  static void execute(final MovementStateComponent movementStateComponent,
                      final TransformComponent transformComponent,
                      final GameState gameState,
                      final float deltaTime) {
    movementStateComponent.updateFallTiming(deltaTime);

    final var currentDuration = movementStateComponent.getFallTimings().getCurrentDuration();
    final var lastDuration = movementStateComponent.getFallTimings().getLastDuration();
    final var downGravity = gameState.sceneConfiguration().verticalDownGravity();

    final var lastDistance = (-downGravity / 2.0f * lastDuration * lastDuration);
    final var currentDistance = (-downGravity / 2.0f * currentDuration * currentDuration);
    final var deltaDistance = currentDistance - lastDistance;

    transformComponent.getModelMatrix().translate(0.0f, deltaDistance, 0.0f);
  }
}
