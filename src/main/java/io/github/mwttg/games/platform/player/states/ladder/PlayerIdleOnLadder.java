package io.github.mwttg.games.platform.player.states.ladder;

import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerIdleOnLadder extends PlayerOnLadderState {

  public PlayerIdleOnLadder(final Map<String, Drawable> drawableByName,
      final PlayerStateComponent playerStateComponent,
      final PlayerEffectComponent playerEffectComponent,
      final Matrix4f transform, final PlayerData playerData) {
    super(drawableByName, playerStateComponent, playerEffectComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return CLIMB_LADDER_UP;
  }

  @Override
  public void update(final float deltaTime, final PlayerInput playerInput, final SensorComponent sensorComponent) {
    // nothing to do
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    if (moveUp(playerInput)) {
      getPlayerStateComponent().switchToClimbUpLadderState();
    } else if (moveDown(playerInput)) {
      getPlayerStateComponent().switchToSlideDownLadderState();
    } else if (jumpLeft(playerInput)) {
      getPlayerStateComponent().switchToJumpUpLeftState();
    } else if (jumpRight(playerInput)) {
      getPlayerStateComponent().switchToJumpUpRightState();
    }
  }

  private boolean jumpLeft(final PlayerInput playerInput) {
    return playerInput.jump() && getPlayerData().getFacingDirection() == FacingDirection.LEFT;
  }

  private boolean jumpRight(final PlayerInput playerInput) {
    return playerInput.jump() && getPlayerData().getFacingDirection() == FacingDirection.RIGHT;
  }
}
