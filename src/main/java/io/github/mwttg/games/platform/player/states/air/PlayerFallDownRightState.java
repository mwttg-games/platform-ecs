package io.github.mwttg.games.platform.player.states.air;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import io.github.mwttg.games.platform.player.SolidGridSystem;
import io.github.mwttg.games.platform.player.physics.FallDown;
import io.github.mwttg.games.platform.player.physics.MoveRight;
import io.github.mwttg.games.platform.player.states.ground.PlayerOnGroundState;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerFallDownRightState extends PlayerFallDownState {

  public PlayerFallDownRightState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                                  final PlayerStateComponent playerStateComponent,
                                  final Matrix4f transform,
                                  final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return FALL_RIGHT;
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
    FallDown.execute(getInAirTime(), deltaTime, getPlayerData(), getTransform(), solidGridComponent);
    updateInAirTime(deltaTime);
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SolidGridComponent solidGridComponent) {
    toFallDownLeft(playerInput);
    coyoteTimeToJumpUpRight(playerInput);
    doubleJumpToJumpUpRight(playerInput);

    final var onGround = SolidGridSystem.isGroundTouched(getTransform(), getPlayerData().getTileSize(), solidGridComponent);
    toIdleRight(playerInput.xAxis(), onGround);
    toWalkRight(playerInput.xAxis(), onGround);
  }

  private void toFallDownLeft(final PlayerInput playerInput) {
    if (playerInput.xAxis() == -1) {
      getPlayerStateComponent().switchToFallDownLeftState(getInAirTime());
    }
  }

  private void coyoteTimeToJumpUpRight(final PlayerInput playerInput) {
    if (playerInput.jump()
        && getInAirTime() <= Configuration.COYOTE_TIME
        && getPlayerStateComponent().getPreviousState() instanceof PlayerOnGroundState) {
      getPlayerStateComponent().switchToJumpUpRightState();
    }
  }

  private void doubleJumpToJumpUpRight(final PlayerInput playerInput) {
    if (playerInput.jump()
        && getPlayerData().getPlayerAbility().hasDoubleJump()
        && getPlayerData().getJumpCounter() < Configuration.PLAYER_MAX_JUMP_AMOUNT) {
      getPlayerStateComponent().switchToJumpUpRightState();
    }
  }

  private void toIdleRight(final int xAxis, final boolean onGround) {
    if (onGround && xAxis == 0 && getPlayerData().getFacingDirection() == FacingDirection.RIGHT) {
      getPlayerStateComponent().switchToIdleRightState();
    }
  }

  private void toWalkRight(final int xAxis, final boolean onGround) {
    if (onGround && xAxis == 1) {
      getPlayerStateComponent().switchToWalkRightState();
    }
  }
}
