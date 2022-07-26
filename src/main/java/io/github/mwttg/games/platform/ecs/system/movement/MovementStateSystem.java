package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.component.input.PlayerAction;
import io.github.mwttg.games.platform.ecs.component.movement.MovementState;

class MovementStateSystem {

  private MovementStateSystem() {
  }

  // mutable call (movementStateComponent is modified)
  static void setState(final MovementState movementState,
                       final float horizontalVelocity,
                       final PlayerAction playerAction) {
    setHorizontalState(movementState, horizontalVelocity);
    setVerticalState(movementState, playerAction);
  }

  private static void setVerticalState(final MovementState movementState, final PlayerAction playerAction) {
    if (playerAction.jump()) {
      movementState.activateJumping();
    }
  }

  private static void setHorizontalState(final MovementState movementState, final float velocity) {
    if (velocity < 0.0f) {
      movementState.activateWalkingLeft();
    }

    if (velocity > 0.0f) {
      movementState.activateWalkingRight();
    }

    if (velocity == 0.0f) {
      movementState.deactivateWalking();
    }
  }
}
