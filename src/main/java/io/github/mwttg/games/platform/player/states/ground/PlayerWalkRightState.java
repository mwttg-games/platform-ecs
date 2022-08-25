package io.github.mwttg.games.platform.player.states.ground;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.GridComponent;
import io.github.mwttg.games.platform.player.colision.GridSystem;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.physics.MoveRight;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerWalkRightState extends PlayerWalkState {

  public PlayerWalkRightState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                              final PlayerStateComponent playerStateComponent,
                              final PlayerEffectComponent playerEffectComponent,
                              final Matrix4f transform,
                              final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, playerEffectComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return WALK_RIGHT;
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
  public void update(final float deltaTime, final PlayerInput playerInput, final GridComponent gridComponent) {
    MoveRight.execute(deltaTime, getPlayerData(), getTransform(), gridComponent);
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final GridComponent gridComponent) {
    toIdleRight(playerInput);
    toWalkLeft(playerInput);
    toJumpUpRight(playerInput);
    toFallDownRight(gridComponent);
    toLadderUp(playerInput, gridComponent);
    toLadderDown(playerInput, gridComponent);
  }

  private void toLadderDown(final PlayerInput playerInput, final GridComponent gridComponent) {
    final var aboveLadder = GridSystem.isLadderBelow(getTransform(), getPlayerData().getTileSize(), gridComponent);
    if (aboveLadder && playerInput.yAxis() == -1) {
      getPlayerStateComponent().switchToOnLadderState();
    }
  }

  private void toLadderUp(final PlayerInput playerInput, final GridComponent gridComponent) {
    final var onLadder = GridSystem.isOnLadder(getTransform(), getPlayerData().getTileSize(), gridComponent);
    if (onLadder && playerInput.yAxis() == 1) {
      getPlayerStateComponent().switchToOnLadderState();
    }
  }

  private void toIdleRight(final PlayerInput playerInput) {
    if (playerInput.xAxis() == 0) {
      getPlayerStateComponent().switchToIdleRightState();
    }
  }

  private void toJumpUpRight(final PlayerInput playerInput) {
    if (playerInput.jump()) {
      getPlayerStateComponent().switchToJumpUpRightState();
    }
  }

  private void toFallDownRight(final GridComponent gridComponent) {
    final var onGround = GridSystem.isGroundTouched(getTransform(), getPlayerData().getTileSize(), gridComponent);
    if (!onGround) {
      getPlayerStateComponent().switchToFallDownRightState();
    }
  }

  private void toWalkLeft(final PlayerInput playerInput) {
    if (playerInput.xAxis() == -1) {
      getPlayerStateComponent().switchToWalkLeftState();
    }
  }
}
