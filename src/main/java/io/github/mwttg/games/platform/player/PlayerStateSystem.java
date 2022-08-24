package io.github.mwttg.games.platform.player;

import io.github.mwttg.games.platform.input.PlayerInputSystem;
import io.github.mwttg.games.platform.player.colision.GridComponent;

public final class PlayerStateSystem {

  private PlayerStateSystem() {
  }

  public static void update(final long windowId,
                            final PlayerStateComponent playerStateComponent,
                            final float deltaTime,
                            final GridComponent gridComponent) {
    final var playerInput = PlayerInputSystem.getPlayerInput(windowId);
    playerStateComponent.getCurrentState().handleStateTransitions(playerInput, gridComponent);
    playerStateComponent.getCurrentState().update(deltaTime, playerInput, gridComponent);
  }
}
