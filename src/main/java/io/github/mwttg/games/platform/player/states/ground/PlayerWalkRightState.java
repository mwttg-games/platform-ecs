package io.github.mwttg.games.platform.player.states.ground;

import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.physics.MoveRight;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerWalkRightState extends PlayerWalkState {

  public PlayerWalkRightState(final Map<String, Drawable> drawableByName,
                              final PlayerStateComponent playerStateComponent,
                              final PlayerEffectComponent playerEffectComponent,
                              final Matrix4f transform,
                              final PlayerData playerData) {
    super(drawableByName, playerStateComponent, playerEffectComponent, transform, playerData);
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
  public void exit() {
    super.exit();
  }

  @Override
  public void update(final float deltaTime, final PlayerInput playerInput, final SensorComponent sensorComponent) {
    MoveRight.execute(deltaTime, getPlayerData(), getTransform(), sensorComponent);
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    if (idle(playerInput)) {
      getPlayerStateComponent().switchToIdleRightState();
    } else if (inputLeft(playerInput)) {
      getPlayerStateComponent().switchToWalkLeftState();
    } else if (jumpUp(playerInput)) {
      getPlayerStateComponent().switchToJumpUpRightState();
    } else if (fallDown(sensorComponent)) {
      getPlayerStateComponent().switchToFallDownRightState();
    } else if (grabLadder(playerInput, sensorComponent)) {
      getPlayerStateComponent().switchToIdleOnLadderState();
    } else if (goLadderDown(playerInput, sensorComponent)) {
      getPlayerStateComponent().switchToIdleOnLadderState();
    }
  }
}
