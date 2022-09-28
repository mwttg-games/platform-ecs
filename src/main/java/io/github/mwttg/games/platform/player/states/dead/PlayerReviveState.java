package io.github.mwttg.games.platform.player.states.dead;

import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.states.AbstractPlayerState;
import java.util.Map;
import org.joml.Matrix4f;

public class PlayerReviveState extends AbstractPlayerState {


  public PlayerReviveState(final Map<String, Drawable> drawableByName,
                           final PlayerStateComponent playerStateComponent,
                           final PlayerEffectComponent playerEffectComponent,
                           final Matrix4f transform,
                           final PlayerData playerData) {
    super(drawableByName, playerStateComponent, playerEffectComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return DEAD;
  }

  @Override
  public void update(final float deltaTime, final PlayerInput playerInput, final SensorComponent sensorComponent) {

  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    getPlayerStateComponent().switchToIdleRightState();
  }
}
