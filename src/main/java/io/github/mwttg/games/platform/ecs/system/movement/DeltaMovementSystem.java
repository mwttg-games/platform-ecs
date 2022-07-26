package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.movement.MovementState;
import org.joml.Vector3f;

class DeltaMovementSystem {

  private DeltaMovementSystem() {
  }

  static Vector3f update(final MovementState movementState,
                         final float horizontalVelocity,
                         final float deltaTime,
                         final GameState gameState) {
    final var currentRiseTime = movementState.getJumpTimings().getCurrentDuration();
    final var maxRiseTime = getMaxRiseTime(gameState);
    var deltaDistanceX = 0.0f;
    var deltaDistanceY = 0.0f;

    // jump movement
    if (movementState.isJumping() && currentRiseTime <= maxRiseTime) {
      deltaDistanceY = ActionJump.getDeltaDistanceY(movementState, gameState, deltaTime);
    }

    if (movementState.isJumping() && currentRiseTime > maxRiseTime) {
      movementState.activateFalling();
    }

    // left right movement
    if (movementState.isWalkingLeft() || movementState.isWalkingRight()) {
      deltaDistanceX = ActionWalk.getDeltaDistanceX(horizontalVelocity, deltaTime);
    }

    // fall movement
    if (movementState.isFalling()) {
      deltaDistanceY = ActionFall.getDeltaDistanceY(movementState, gameState, deltaTime);
    }

    return new Vector3f(deltaDistanceX, deltaDistanceY, 0.0f);
  }

  private static float getMaxRiseTime(final GameState gameState) {
    final var upGravity = gameState.sceneConfiguration().verticalUpGravity();
    final var initJumpVelocity = gameState.playerConfiguration().initialJumpVelocity();
    return initJumpVelocity / upGravity;
  }
}
