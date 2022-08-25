package io.github.mwttg.games.platform.player.states.ground;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.colision.SensorSystem;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerIdleRightState extends PlayerIdleState {

  public PlayerIdleRightState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                              final PlayerStateComponent playerStateComponent,
                              final PlayerEffectComponent playerEffectComponent,
                              final Matrix4f transform,
                              final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, playerEffectComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return IDLE_RIGHT;
  }

  @Override
  public void enter() {
    super.enter();
    getPlayerData().setFacingDirection(FacingDirection.RIGHT);
  }

  @Override
  public void exit() {
    super.exit();
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    toWalkRight(playerInput);
    toWalkLeft(playerInput);
    toJumpUp(playerInput);
    toLadderUp(playerInput, sensorComponent);
    toLadderDown(playerInput, sensorComponent);
  }

  private void toLadderDown(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    final var aboveLadder = SensorSystem.isLadderBelow(getTransform(), getPlayerData().getTileSize(), sensorComponent);
    if (aboveLadder && playerInput.yAxis() == -1) {
      getPlayerStateComponent().switchToOnLadderState();
    }
  }

  private void toLadderUp(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    final var onLadder = SensorSystem.isOnLadder(getTransform(), getPlayerData().getTileSize(), sensorComponent);
    if (onLadder && playerInput.yAxis() == 1) {
      getPlayerStateComponent().switchToOnLadderState();
    }
  }

  private void toWalkRight(final PlayerInput playerInput) {
    if (playerInput.xAxis() == 1) {
      getPlayerStateComponent().switchToWalkRightState();
    }
  }

  private void toWalkLeft(final PlayerInput playerInput) {
    if (playerInput.xAxis() == -1) {
      getPlayerStateComponent().switchToWalkLeftState();
    }
  }

  private void toJumpUp(final PlayerInput playerInput) {
    if (playerInput.jump()) {
      getPlayerStateComponent().switchToJumpUpRightState();
    }
  }
}
