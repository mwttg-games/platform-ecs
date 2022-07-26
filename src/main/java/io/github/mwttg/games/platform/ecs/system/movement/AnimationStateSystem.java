package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.component.draw.SpriteStatesAnimationComponent;
import io.github.mwttg.games.platform.ecs.component.movement.Direction;
import io.github.mwttg.games.platform.ecs.component.movement.MovementState;

class AnimationStateSystem {

  private AnimationStateSystem() {
  }

  static void setCurrentAnimation(final MovementState movementState,
                                  final SpriteStatesAnimationComponent spriteStatesAnimationComponent) {
    final var direction = movementState.getLastDirection();

    if (movementState.isFalling() || movementState.isJumping()) {
      if (direction == Direction.LEFT) {
        spriteStatesAnimationComponent.setCurrentState("idleLeft");
      } else {
        spriteStatesAnimationComponent.setCurrentState("idleRight");
      }
    }

    if (movementState.isWalkingLeft()) {
      spriteStatesAnimationComponent.setCurrentState("walkLeft");
    }

    if (movementState.isWalkingRight()) {
      spriteStatesAnimationComponent.setCurrentState("walkRight");
    }
  }
}
