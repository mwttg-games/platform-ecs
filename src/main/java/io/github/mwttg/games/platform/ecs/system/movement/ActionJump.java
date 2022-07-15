package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.movement.MovementStateComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;

class ActionJump {

  static void execute(final MovementStateComponent movementStateComponent,
                      final TransformComponent transformComponent,
                      final GameState gameState,
                      final float deltaTime) {
    movementStateComponent.updateJumpTiming(deltaTime);

    final var currentDuration = movementStateComponent.getJumpTimings().getCurrentDuration();
    final var lastDuration = movementStateComponent.getJumpTimings().getLastDuration();
    final var upGravity = gameState.sceneConfiguration().verticalUpGravity();
    final var initJumpVelocity = gameState.playerConfiguration().initialJumpVelocity();

    final var lastDistance = (-upGravity / 2.0f * lastDuration * lastDuration) + (initJumpVelocity * lastDuration);
    final var currentDistance = (-upGravity / 2.0f * currentDuration * currentDuration) + (initJumpVelocity * currentDuration);
    final var deltaDistance = currentDistance - lastDistance;

    transformComponent.getModelMatrix().translate(0.0f, deltaDistance, 0.0f);
  }
}
