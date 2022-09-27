package io.github.mwttg.games.platform.player.states.air;

import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.physics.FallDown;
import io.github.mwttg.games.platform.player.physics.MoveLeft;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerFallDownLeftState extends PlayerFallDownState {

  public PlayerFallDownLeftState(final Map<String, Drawable> drawableByName,
                                 final PlayerStateComponent playerStateComponent,
                                 final PlayerEffectComponent playerEffectComponent,
                                 final Matrix4f transform,
                                 final PlayerData playerData) {
    super(drawableByName, playerStateComponent, playerEffectComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return FALL_LEFT;
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
  public void update(final float deltaTime, final PlayerInput playerInput, final SensorComponent sensorComponent) {
    if (playerInput.xAxis() == -1) {
      MoveLeft.execute(deltaTime, getPlayerData(), getTransform(), sensorComponent);
    }
    FallDown.execute(getInAirTime(), deltaTime, getPlayerData(), getTransform(), sensorComponent);
    updateInAirTime(deltaTime);
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    if (inputRight(playerInput)) {
      getPlayerStateComponent().switchToFallDownRightState(getInAirTime());
    } else if (coyoteTime(playerInput)) {
      getPlayerStateComponent().switchToJumpUpLeftState();
    } else if (doubleJump(playerInput)) {
      getPlayerStateComponent().switchToJumpUpLeftState();
    } else if (onGround(sensorComponent)) {
      getPlayerStateComponent().switchToIdleLeftState();
    } else if (grabLadder(playerInput, sensorComponent)) {
      getPlayerStateComponent().switchToIdleOnLadderState();
    }
  }
}
