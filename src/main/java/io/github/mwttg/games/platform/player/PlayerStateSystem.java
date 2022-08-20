package io.github.mwttg.games.platform.player;

import io.github.mwttg.games.platform.input.PlayerInputSystem;

public final class PlayerStateSystem {

  private PlayerStateSystem() {
  }

  public static void update(final long windowId,
                            final PlayerStateComponent playerStateComponent,
                            final float deltaTime,
                            final SolidGridComponent solidGridComponent) {
    final var playerInput = PlayerInputSystem.getPlayerInput(windowId);
    final var currentState = playerStateComponent.getCurrentState();
    currentState.handleStateTransitions(playerInput, solidGridComponent);
    currentState.update(deltaTime, playerInput, solidGridComponent);
  }
}
