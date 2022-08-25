package io.github.mwttg.games.platform.player.states.air;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.GridComponent;
import io.github.mwttg.games.platform.player.colision.GridSystem;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.physics.JumpUp;
import io.github.mwttg.games.platform.player.physics.MoveLeft;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerJumpUpLeftState extends PlayerJumpUpState {

  public PlayerJumpUpLeftState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                               final PlayerStateComponent playerStateComponent,
                               final PlayerEffectComponent playerEffectComponent,
                               final Matrix4f transform,
                               final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, playerEffectComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return JUMP_LEFT;
  }

  @Override
  public void enter() {
    super.enter();
    getPlayerData().setFacingDirection(FacingDirection.LEFT);
  }

  @Override
  public void enter(final float alreadyUsedAirTime) {
    super.enter(alreadyUsedAirTime);
    getPlayerData().setFacingDirection(FacingDirection.LEFT);
  }

  @Override
  public void exit() {
    super.exit();
  }

  @Override
  public void update(final float deltaTime, final PlayerInput playerInput, final GridComponent gridComponent) {
    if (playerInput.xAxis() == -1) {
      MoveLeft.execute(deltaTime, getPlayerData(), getTransform(), gridComponent);
    }
    JumpUp.execute(getInAirTime(), deltaTime, getPlayerData(), getTransform(), gridComponent);
    updateInAirTime(deltaTime);
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final GridComponent gridComponent) {
    toFallDownLeft();
    toJumpUpRight(playerInput);
    doubleJumpToJumpUpLeft(playerInput);
    toFallDownLeft(gridComponent);
    toOnLadder(playerInput, gridComponent);
  }

  private void toOnLadder(final PlayerInput playerInput, final GridComponent gridComponent) {
    final var onLadder = GridSystem.isOnLadder(getTransform(), getPlayerData().getTileSize(), gridComponent);
    if (onLadder && playerInput.yAxis() == 1) {
      getPlayerStateComponent().switchToOnLadderState();
    }
  }

  private void toFallDownLeft() {
    if (getInAirTime() >= Configuration.PLAYER_MAX_RISE_TIME) {
      getPlayerStateComponent().switchToFallDownLeftState();
    }
  }

  private void toFallDownLeft(final GridComponent gridComponent) {
    final var isTopBlocked = GridSystem.isTopBlocked(getTransform(), getPlayerData().getTileSize(), gridComponent);
    if (isTopBlocked) {
      getPlayerStateComponent().switchToFallDownLeftState();
    }
  }

  private void toJumpUpRight(final PlayerInput playerInput) {
    if (playerInput.xAxis() == 1 && getInAirTime() < Configuration.PLAYER_MAX_RISE_TIME) {
      getPlayerStateComponent().switchToJumpUpRightState(getInAirTime());
    }
  }

  private void doubleJumpToJumpUpLeft(final PlayerInput playerInput) {
    if (playerInput.jump()
        && getPlayerData().getPlayerAbility().hasDoubleJump()
        && getPlayerData().getJumpCounter() < Configuration.PLAYER_MAX_JUMP_AMOUNT) {
      getPlayerStateComponent().switchToJumpUpLeftState();
    }
  }
}
