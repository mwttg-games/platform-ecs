package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.input.PlayerAction;

class HorizontalVelocitySystem {

  private HorizontalVelocitySystem() {
  }

  static float getHorizontalVelocity(final PlayerAction playerAction,
                                     final GameState gameState) {
    final var walkVelocity = gameState.playerConfiguration().walkVelocity();
    var leftVelocityAddend = 0.0f;
    var rightVelocityAddend = 0.0f;

    if (playerAction.left()) {
      leftVelocityAddend = -walkVelocity;
    }

    if (playerAction.right()) {
      rightVelocityAddend = walkVelocity;
    }

    return leftVelocityAddend + rightVelocityAddend;
  }
}
