package io.github.mwttg.games.platform.player.states.ladder;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.colision.SensorSystem;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.physics.SlideDown;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerSlideDownLadder extends PlayerOnLadderState {

  public PlayerSlideDownLadder(
      final Map<String, SpriteAnimationComponent> animationComponentByName,
      final PlayerStateComponent playerStateComponent,
      final PlayerEffectComponent playerEffectComponent,
      final Matrix4f transform, final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, playerEffectComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return ON_LADDER;
  }

  @Override
  public void update(final float deltaTime, final PlayerInput playerInput, final SensorComponent sensorComponent) {
    SlideDown.execute(deltaTime, getPlayerData(), getTransform(), sensorComponent);
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    if (noUpDownInput(playerInput)) {
      getPlayerStateComponent().switchToIdleOnLadderState();
    } else if (moveUp(playerInput)) {
      getPlayerStateComponent().switchToClimbUpLadderState();
    } else if (leaveLadderGroundLeft(sensorComponent)) {
      getPlayerStateComponent().switchToIdleLeftState();
    } else if (leaveLadderGroundRight(sensorComponent)) {
      getPlayerStateComponent().switchToIdleRightState();
    } else if (leaveLadderDownLeft(sensorComponent)) {
      getPlayerStateComponent().switchToFallDownLeftState();
    } else if (leaveLadderDownRight(sensorComponent)) {
      getPlayerStateComponent().switchToFallDownRightState();
    }
  }

  private boolean leaveLadderGroundLeft(final SensorComponent sensorComponent) {
    final var onGround = SensorSystem.isGroundTouchedFromLadder(getTransform(), getPlayerData().getTileSize(), sensorComponent);
    return onGround && getPlayerData().getFacingDirection() == FacingDirection.LEFT;
  }

  private boolean leaveLadderGroundRight(final SensorComponent sensorComponent) {
    final var onGround = SensorSystem.isGroundTouchedFromLadder(getTransform(), getPlayerData().getTileSize(), sensorComponent);
    return onGround && getPlayerData().getFacingDirection() == FacingDirection.RIGHT;
  }
}
