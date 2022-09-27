package io.github.mwttg.games.platform.player.states.ground;

import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.colision.SensorSystem;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import java.util.Map;
import org.joml.Matrix4f;

public abstract class PlayerWalkState extends PlayerOnGroundState {

  public PlayerWalkState(final Map<String, Drawable> animationComponentByName,
                         final PlayerStateComponent playerStateComponent,
                         final PlayerEffectComponent playerEffectComponent,
                         final Matrix4f transform,
                         final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, playerEffectComponent, transform, playerData);
  }

  @Override
  public void enter() {
    super.enter();
  }

  @Override
  public void exit() {
    super.exit();
  }

  protected boolean idle(final PlayerInput playerInput) {
    return playerInput.xAxis() == 0;
  }

  protected boolean fallDown(final SensorComponent sensorComponent) {
    return !SensorSystem.isGroundTouched(getTransform(), getPlayerData().getTileSize(), sensorComponent);
  }
}
