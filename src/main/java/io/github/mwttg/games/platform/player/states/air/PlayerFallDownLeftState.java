package io.github.mwttg.games.platform.player.states.air;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import io.github.mwttg.games.platform.player.SolidGridSystem;
import io.github.mwttg.games.platform.player.physics.FallDown;
import io.github.mwttg.games.platform.player.physics.MoveLeft;
import io.github.mwttg.games.platform.player.states.ground.PlayerOnGroundState;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector2i;

public final class PlayerFallDownLeftState extends PlayerFallDownState {

  public PlayerFallDownLeftState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                                 final PlayerStateComponent playerStateComponent,
                                 final Matrix4f transform,
                                 final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return IDLE_LEFT;
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
  public void update(final float deltaTime, final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    if (inputVector.x() == -1) {
      MoveLeft.execute(deltaTime, getPlayerData(), getTransform(), solidGridComponent);
    }
    FallDown.execute(getInAirTime(), deltaTime, getPlayerData(), getTransform(), solidGridComponent);
    updateInAirTime(deltaTime);
  }

  @Override
  public void handleStateTransitions(final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    System.out.println("jumpCounter: " +getPlayerData().getJumpCounter());
    toFallDownRight(inputVector);
    coyoteTimeToJumpUpLeft(inputVector);
    doubleJump(inputVector);

    final var onGround = SolidGridSystem.isGroundTouched(getTransform(), getPlayerData().getTileSize(), solidGridComponent);
    toIdleLeft(inputVector, onGround);
    toWalkLeft(inputVector, onGround);
  }

  private void toFallDownRight(final Vector2i inputVector) {
    if (inputVector.x() == 1) {
      getPlayerStateComponent().switchToFallDownRightState(getInAirTime());
    }
  }

  private void coyoteTimeToJumpUpLeft(final Vector2i inputVector) {
    if (inputVector.y() == 1
        && getInAirTime() <= Configuration.COYOTE_TIME
        && getPlayerStateComponent().getPreviousState() instanceof PlayerOnGroundState) {
      getPlayerStateComponent().switchToJumpUpLeftState();
    }
  }

  private void doubleJump(final Vector2i inputVector) {
    if (inputVector.y() == 1
        && getPlayerData().getPlayerAbility().hasDoubleJump()
        //  && getPlayerStateComponent().getPreviousState() instanceof PlayerInAirState
        && getPlayerData().getJumpCounter() < Configuration.PLAYER_MAX_JUMP_AMOUNT) {
      getPlayerStateComponent().switchToJumpUpLeftState();
    }
  }

  private void toIdleLeft(final Vector2i inputVector, final boolean onGround) {
    if (onGround && inputVector.x() == 0 && getPlayerData().getFacingDirection() == FacingDirection.LEFT) {
      getPlayerStateComponent().switchToIdleLeftState();
    }
  }

  private void toWalkLeft(final Vector2i inputVector, final boolean onGround) {
    if (onGround && inputVector.x() == -1) {
      getPlayerStateComponent().switchToWalkLeftState();
    }
  }
}
