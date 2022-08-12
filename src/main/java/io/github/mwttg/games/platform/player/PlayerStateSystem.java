package io.github.mwttg.games.platform.player;

public final class PlayerStateSystem {

  private PlayerStateSystem() {
  }

  public static void update(final long windowId,
                            final PlayerStateComponent playerStateComponent,
                            final float deltaTime,
                            final SolidGridComponent solidGridComponent) {
    final var inputVector = PlayerInputSystem.getPlayerInput(windowId);
    final var currentState = playerStateComponent.getCurrentState();
    currentState.handleStateTransitions(inputVector, solidGridComponent);
    currentState.update(deltaTime, inputVector, solidGridComponent);
  }
}
