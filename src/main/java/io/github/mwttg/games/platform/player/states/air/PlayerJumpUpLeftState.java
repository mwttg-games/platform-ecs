package io.github.mwttg.games.platform.player.states.air;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
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
  public void update(final float deltaTime, final PlayerInput playerInput, final SensorComponent sensorComponent) {
    if (playerInput.xAxis() == -1) {
      MoveLeft.execute(deltaTime, getPlayerData(), getTransform(), sensorComponent);
    }
    JumpUp.execute(getInAirTime(), deltaTime, getPlayerData(), getTransform(), sensorComponent);
    updateInAirTime(deltaTime);
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    if (jumpHighReached()) {
      getPlayerStateComponent().switchToFallDownLeftState();
    } else if (inputRight(playerInput)) {
      getPlayerStateComponent().switchToJumpUpRightState(getInAirTime());
    } else if (topBlocked(sensorComponent)) {
      getPlayerStateComponent().switchToFallDownLeftState();
    } else if (doubleJump(playerInput)) {
      getPlayerStateComponent().switchToJumpUpLeftState();
    } else if (grabLadder(playerInput, sensorComponent)) {
      getPlayerStateComponent().switchToOnLadderState();
    }
  }
}
