package io.github.mwttg.games.platform.player.states.ladder;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.physics.ClimbUp;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerClimbUpLadder extends PlayerOnLadderState {

  public PlayerClimbUpLadder(
      final Map<String, SpriteAnimationComponent> animationComponentByName,
      final PlayerStateComponent playerStateComponent,
      final PlayerEffectComponent playerEffectComponent,
      final Matrix4f transform, final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, playerEffectComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return CLIMB_LADDER_UP;
  }

  @Override
  public void update(final float deltaTime, final PlayerInput playerInput, final SensorComponent sensorComponent) {
    ClimbUp.execute(deltaTime, getPlayerData(), getTransform(), sensorComponent);
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    if (noUpDownInput(playerInput)) {
      getPlayerStateComponent().switchToIdleOnLadderState();
    } else if (moveDown(playerInput)) {
      getPlayerStateComponent().switchToSlideDownLadderState();
    } else if (leaveLadderDownLeft(sensorComponent)) {
      getPlayerStateComponent().switchToFallDownLeftState();
    } else if (leaveLadderDownRight(sensorComponent)) {
      getPlayerStateComponent().switchToFallDownRightState();
    }
  }
}
