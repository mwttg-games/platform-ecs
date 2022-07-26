package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.component.input.PlayerAction;
import io.github.mwttg.games.platform.ecs.component.movement.MovementStateComponent;

class MovementStateSystem {

  private MovementStateSystem() {
  }

  // mutable call (movementStateComponent is modified)
  static void setState(final MovementStateComponent movementStateComponent,
                       final float horizontalVelocity,
                       final PlayerAction playerAction) {
    setHorizontalState(movementStateComponent, horizontalVelocity);
    setVerticalState(movementStateComponent, playerAction);
  }

  private static void setVerticalState(final MovementStateComponent movementStateComponent, final PlayerAction playerAction) {
    if (playerAction.jump()) {
      movementStateComponent.activateJumping();
    }
  }

  private static void setHorizontalState(final MovementStateComponent movementStateComponent, final float velocity) {
    if (velocity < 0.0f) {
      movementStateComponent.activateWalkingLeft();
    }

    if (velocity > 0.0f) {
      movementStateComponent.activateWalkingRight();
    }

    if (velocity == 0.0f) {
      movementStateComponent.deactivateWalking();
    }
  }
}
