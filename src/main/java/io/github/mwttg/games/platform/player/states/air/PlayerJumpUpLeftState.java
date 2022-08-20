package io.github.mwttg.games.platform.player.states.air;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import io.github.mwttg.games.platform.player.SolidGridSystem;
import io.github.mwttg.games.platform.player.physics.JumpUp;
import io.github.mwttg.games.platform.player.physics.MoveLeft;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerJumpUpLeftState extends PlayerJumpUpState {

  public PlayerJumpUpLeftState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                               final PlayerStateComponent playerStateComponent,
                               final Matrix4f transform,
                               final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, transform, playerData);
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
  public void update(final float deltaTime, final PlayerInput playerInput, final SolidGridComponent solidGridComponent) {
    if (playerInput.xAxis() == -1) {
      MoveLeft.execute(deltaTime, getPlayerData(), getTransform(), solidGridComponent);
    }
    JumpUp.execute(getInAirTime(), deltaTime, getPlayerData(), getTransform(), solidGridComponent);
    updateInAirTime(deltaTime);
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SolidGridComponent solidGridComponent) {
    toFallDownLeft();
    toJumpUpRight(playerInput);
    doubleJumpToJumpUpLeft(playerInput);

    final var isTopBlocked = SolidGridSystem.isTopBlocked(getTransform(), getPlayerData().getTileSize(), solidGridComponent);
    toFallDownLeft(isTopBlocked);
  }

  private void toFallDownLeft() {
    if (getInAirTime() >= Configuration.PLAYER_MAX_RISE_TIME) {
      getPlayerStateComponent().switchToFallDownLeftState();
    }
  }

  private void toFallDownLeft(final boolean isTopBlocked) {
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
    System.out.println(getPlayerData().getJumpCounter());
    if (playerInput.jump()
        && getPlayerData().getPlayerAbility().hasDoubleJump()
        && getPlayerData().getJumpCounter() < Configuration.PLAYER_MAX_JUMP_AMOUNT) {
      getPlayerStateComponent().switchToJumpUpLeftState();
    }
  }
}
