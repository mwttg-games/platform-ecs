package io.github.mwttg.games.platform.player;

import io.github.mwttg.games.platform.input.PlayerInputSystem;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.colision.SensorSystem;

public final class PlayerStateSystem {

  private PlayerStateSystem() {
  }

  public static void update(final long windowId,
                            final PlayerStateComponent playerStateComponent,
                            final float deltaTime,
                            final SensorComponent sensorComponent) {
    final var playerInput = PlayerInputSystem.getPlayerInput(windowId);
    playerStateComponent.getCurrentState().handleStateTransitions(playerInput, sensorComponent);
    playerStateComponent.getCurrentState().update(deltaTime, playerInput, sensorComponent);
    SensorSystem.update(deltaTime, sensorComponent);
  }
}
