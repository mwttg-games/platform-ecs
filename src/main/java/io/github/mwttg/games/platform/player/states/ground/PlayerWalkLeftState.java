package io.github.mwttg.games.platform.player.states.ground;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.colision.SensorSystem;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.physics.MoveLeft;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerWalkLeftState extends PlayerWalkState {

  public PlayerWalkLeftState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                             final PlayerStateComponent playerStateComponent,
                             final PlayerEffectComponent playerEffectComponent,
                             final Matrix4f transform,
                             final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, playerEffectComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return WALK_LEFT;
  }

  @Override
  public void enter() {
    super.enter();
    getPlayerData().setFacingDirection(FacingDirection.LEFT);
  }

  @Override
  public void exit() {
    super.exit();
  }

  @Override
  public void update(final float deltaTime, final PlayerInput playerInput, final SensorComponent sensorComponent) {
    MoveLeft.execute(deltaTime, getPlayerData(), getTransform(), sensorComponent);
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    toIdleLeft(playerInput);
    toWalkRight(playerInput);
    toJumpUpLeft(playerInput);
    toFallDownLeft(sensorComponent);
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

  private void toIdleLeft(final PlayerInput playerInput) {
    if (playerInput.xAxis() == 0) {
      getPlayerStateComponent().switchToIdleLeftState();
    }
  }

  private void toJumpUpLeft(final PlayerInput playerInput) {
    if (playerInput.jump()) {
      getPlayerStateComponent().switchToJumpUpLeftState();
    }
  }

  private void toFallDownLeft(final SensorComponent sensorComponent) {
    final var onGround = SensorSystem.isGroundTouched(getTransform(), getPlayerData().getTileSize(), sensorComponent);
    if (!onGround) {
      getPlayerStateComponent().switchToFallDownLeftState();
    }
  }

  private void toWalkRight(final PlayerInput playerInput) {
    if (playerInput.xAxis() == 1) {
      getPlayerStateComponent().switchToWalkRightState();
    }
  }
}
