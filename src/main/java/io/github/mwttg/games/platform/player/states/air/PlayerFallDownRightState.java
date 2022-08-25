package io.github.mwttg.games.platform.player.states.air;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.colision.SensorSystem;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.physics.FallDown;
import io.github.mwttg.games.platform.player.physics.MoveRight;
import io.github.mwttg.games.platform.player.states.ground.PlayerOnGroundState;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerFallDownRightState extends PlayerFallDownState {

  public PlayerFallDownRightState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                                  final PlayerStateComponent playerStateComponent,
                                  final PlayerEffectComponent playerEffectComponent,
                                  final Matrix4f transform,
                                  final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, playerEffectComponent, transform, playerData);
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
  public void update(final float deltaTime, final PlayerInput playerInput, final SensorComponent sensorComponent) {
    if (playerInput.xAxis() == 1) {
      MoveRight.execute(deltaTime, getPlayerData(), getTransform(), sensorComponent);
    }
    FallDown.execute(getInAirTime(), deltaTime, getPlayerData(), getTransform(), sensorComponent);
    updateInAirTime(deltaTime);
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    toFallDownLeft(playerInput);
    coyoteTimeToJumpUpRight(playerInput);
    doubleJumpToJumpUpRight(playerInput);

    final var onGround = SensorSystem.isGroundTouched(getTransform(), getPlayerData().getTileSize(), sensorComponent);
    toIdleRight(playerInput.xAxis(), onGround);
    toWalkRight(playerInput.xAxis(), onGround);

    toOnLadder(playerInput, sensorComponent);
  }

  private void toOnLadder(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    final var onLadder = SensorSystem.isOnLadder(getTransform(), getPlayerData().getTileSize(), sensorComponent);
    if (onLadder && playerInput.yAxis() == 1) {
      getPlayerStateComponent().switchToOnLadderState();
    }
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
