package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.movement.MovementStateComponent;
import org.joml.Vector3f;

class DeltaMovementSystem {

  private DeltaMovementSystem() {
  }

  static Vector3f update(final MovementStateComponent movementStateComponent,
                         final float horizontalVelocity,
                         final float deltaTime,
                         final GameState gameState) {
    final var currentRiseTime = movementStateComponent.getJumpTimings().getCurrentDuration();
    final var maxRiseTime = getMaxRiseTime(gameState);
    var deltaDistanceX = 0.0f;
    var deltaDistanceY = 0.0f;

    // jump movement
    if (movementStateComponent.isJumping() && currentRiseTime <= maxRiseTime) {
      deltaDistanceY = ActionJump.getDeltaDistanceY(movementStateComponent, gameState, deltaTime);
    }

    if (movementStateComponent.isJumping() && currentRiseTime > maxRiseTime) {
      movementStateComponent.activateFalling();
    }

    // left right movement
    if (movementStateComponent.isWalkingLeft() || movementStateComponent.isWalkingRight()) {
      deltaDistanceX = ActionWalk.getDeltaDistanceX(horizontalVelocity, deltaTime);
    }

    // fall movement
    if (movementStateComponent.isFalling()) {
      deltaDistanceY = ActionFall.getDeltaDistanceY(movementStateComponent, gameState, deltaTime);
    }

    return new Vector3f(deltaDistanceX, deltaDistanceY, 0.0f);
  }

  private static float getMaxRiseTime(final GameState gameState) {
    final var upGravity = gameState.sceneConfiguration().verticalUpGravity();
    final var initJumpVelocity = gameState.playerConfiguration().initialJumpVelocity();
    return initJumpVelocity / upGravity;
  }
}
