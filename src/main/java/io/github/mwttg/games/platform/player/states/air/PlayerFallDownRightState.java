package io.github.mwttg.games.platform.player.states.air;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
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
import org.joml.Vector2i;

public final class PlayerFallDownRightState extends PlayerFallDownState {

  public PlayerFallDownRightState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                                  final PlayerStateComponent playerStateComponent,
                                  final Matrix4f transform,
                                  final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, transform, playerData);
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
  public void enter(final float alreadyUsedAirTime) {
    super.enter(alreadyUsedAirTime);
    getPlayerData().setFacingDirection(FacingDirection.RIGHT);
  }

  @Override
  public void exit() {
    super.exit();
  }

  @Override
  public void update(final float deltaTime, final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    if (inputVector.x() == 1) {
      MoveRight.execute(deltaTime, getPlayerData(), getTransform(), solidGridComponent);
    }
    FallDown.execute(getInAirTime(), deltaTime, getPlayerData(), getTransform(), solidGridComponent);
    updateInAirTime(deltaTime);
  }

  @Override
  public void handleStateTransitions(final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    System.out.println("jumpCounter: " +getPlayerData().getJumpCounter());
    toFallDownLeft(inputVector);
    coyoteTimeToJumpUpRight(inputVector);
    doubleJump(inputVector);

    final var onGround = SolidGridSystem.isGroundTouched(getTransform(), getPlayerData().getTileSize(), solidGridComponent);
    toIdleRight(inputVector, onGround);
    toWalkRight(inputVector, onGround);
  }

  private void toFallDownLeft(final Vector2i inputVector) {
    if (inputVector.x() == -1) {
      getPlayerStateComponent().switchToFallDownLeftState(getInAirTime());
    }
  }

  private void coyoteTimeToJumpUpRight(final Vector2i inputVector) {
    if (inputVector.y() == 1
        && getInAirTime() <= Configuration.COYOTE_TIME
        && getPlayerStateComponent().getPreviousState() instanceof PlayerOnGroundState) {
      getPlayerStateComponent().switchToJumpUpRightState();
    }
  }

  private void doubleJump(final Vector2i inputVector) {
    if (inputVector.y() == 1
        && getPlayerData().getPlayerAbility().hasDoubleJump()
        // && getPlayerStateComponent().getPreviousState() instanceof PlayerInAirState
        && getPlayerData().getJumpCounter() < Configuration.PLAYER_MAX_JUMP_AMOUNT) {
      getPlayerStateComponent().switchToJumpUpRightState();
    }
  }

  private void toIdleRight(final Vector2i inputVector, final boolean onGround) {
    if (onGround && inputVector.x() == 0 && getPlayerData().getFacingDirection() == FacingDirection.RIGHT) {
      getPlayerStateComponent().switchToIdleRightState();
    }
  }

  private void toWalkRight(final Vector2i inputVector, final boolean onGround) {
    if (onGround && inputVector.x() == 1) {
      getPlayerStateComponent().switchToWalkRightState();
    }
  }
}
