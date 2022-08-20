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
import io.github.mwttg.games.platform.player.physics.MoveRight;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerJumpUpRightState extends PlayerJumpUpState {

  public PlayerJumpUpRightState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                                final PlayerStateComponent playerStateComponent,
                                final Matrix4f transform,
                                final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, transform, playerData);
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
  public void enter(final float alreadyUsedAirTime) {
    super.enter(alreadyUsedAirTime);
    getPlayerData().setFacingDirection(FacingDirection.RIGHT);
  }

  @Override
  public void exit() {
    super.exit();
  }

  @Override
  public void update(final float deltaTime, final PlayerInput playerInput, final SolidGridComponent solidGridComponent) {
    if (playerInput.xAxis() == 1) {
      MoveRight.execute(deltaTime, getPlayerData(), getTransform(), solidGridComponent);
    }
    JumpUp.execute(getInAirTime(), deltaTime, getPlayerData(), getTransform(), solidGridComponent);
    updateInAirTime(deltaTime);
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SolidGridComponent solidGridComponent) {
    toFallDownRight();
    toJumpUpLeft(playerInput);
    doubleJump(playerInput);

    final var isTopBlocked = SolidGridSystem.isTopBlocked(getTransform(), getPlayerData().getTileSize(), solidGridComponent);
    toFallDownRight(isTopBlocked);
  }

  private void toFallDownRight() {
    if (getInAirTime() >= Configuration.PLAYER_MAX_RISE_TIME) {
      getPlayerStateComponent().switchToFallDownRightState();
    }
  }

  private void toFallDownRight(final boolean isTopBlocked) {
    if (isTopBlocked) {
      getPlayerStateComponent().switchToFallDownRightState();
    }
  }

  private void toJumpUpLeft(final PlayerInput playerInput) {
    if (playerInput.xAxis() == -1 && getInAirTime() < Configuration.PLAYER_MAX_RISE_TIME) {
      getPlayerStateComponent().switchToJumpUpLeftState(getInAirTime());
    }
  }

  private void doubleJump(final PlayerInput playerInput) {
    if (playerInput.jump()
        && getPlayerData().getPlayerAbility().hasDoubleJump()
        // && getPlayerStateComponent().getPreviousState() instanceof PlayerInAirState
        && getPlayerData().getJumpCounter() < Configuration.PLAYER_MAX_JUMP_AMOUNT) {
      getPlayerStateComponent().switchToJumpUpRightState();
    }
  }
}
