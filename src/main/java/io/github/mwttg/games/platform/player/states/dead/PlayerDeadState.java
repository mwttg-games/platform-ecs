package io.github.mwttg.games.platform.player.states.dead;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.states.AbstractPlayerState;
import java.util.Map;
import org.joml.Matrix4f;

public class PlayerDeadState extends AbstractPlayerState {

  private float delay;

  public PlayerDeadState(final Map<String, Drawable> drawableByName,
                         final PlayerStateComponent playerStateComponent,
                         final PlayerEffectComponent playerEffectComponent,
                         final Matrix4f transform,
                         final PlayerData playerData) {
    super(drawableByName, playerStateComponent, playerEffectComponent, transform, playerData);
  }

  @Override
  public void enter() {
    delay = 0.0f;
  }

  @Override
  public void exit() {
  }

  @Override
  protected String getAnimationName() {
    return DEAD;
  }

  @Override
  public void update(final float deltaTime, final PlayerInput playerInput, final SensorComponent sensorComponent) {
    delay = delay + deltaTime;
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    if (delay >= Configuration.DELAY_DEATH_TIME) {
      getPlayerStateComponent().switchToReviveState();
    }
  }
}
