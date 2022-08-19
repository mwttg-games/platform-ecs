package io.github.mwttg.games.platform.player.states.air;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import io.github.mwttg.games.platform.player.SolidGridSystem;
import io.github.mwttg.games.platform.player.physics.JumpUp;
import io.github.mwttg.games.platform.player.physics.MoveRight;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector2i;

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
  public void update(final float deltaTime, final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    if (inputVector.x() == 1) {
      MoveRight.execute(deltaTime, getPlayerData(), getTransform(), solidGridComponent);
    }
    JumpUp.execute(getInAirTime(), deltaTime, getPlayerData(), getTransform(), solidGridComponent);
    updateInAirTime(deltaTime);
  }

  @Override
  public void handleStateTransitions(final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    System.out.println("jumpCounter: " +getPlayerData().getJumpCounter());
    toFallDownRight();
    toJumpUpLeft(inputVector);
    doubleJump(inputVector);

    final var isTopBlocked = SolidGridSystem.isTopBlocked(getTransform(), getPlayerData().getTileSize(), solidGridComponent);
    toFallDownRight(isTopBlocked);
  }

  private void toFallDownRight() {
    if (getInAirTime() >= Configuration.PLAYER_MAX_RISE_TIME) {
      getPlayerStateComponent().switchToFallDownRightState();
    }
  }

  private void toJumpUpLeft(final Vector2i inputVector) {
    if (inputVector.x() == -1 && getInAirTime() < Configuration.PLAYER_MAX_RISE_TIME) {
      getPlayerStateComponent().switchToJumpUpLeftState(getInAirTime());
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

  private void toFallDownRight(final boolean isTopBlocked) {
    if (isTopBlocked) {
      getPlayerStateComponent().switchToFallDownRightState();
    }
  }
}
