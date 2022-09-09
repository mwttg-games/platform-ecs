package io.github.mwttg.games.platform.player.states.ground;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.colision.SensorSystem;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import java.util.Map;
import org.joml.Matrix4f;

public abstract class PlayerIdleState extends PlayerOnGroundState {

  public PlayerIdleState(final Map<String, SpriteAnimationComponent> animationComponentByName,
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

  @Override
  public void update(final float deltaTime, final PlayerInput playerInput, final SensorComponent sensorComponent) {
    // nothing to do
  }

  protected boolean moveDownFromThinPlatform(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    final var isOnThinPlatform = SensorSystem.isOnThinPlatform(getTransform(), getPlayerData().getTileSize(), sensorComponent);

    if (isOnThinPlatform && playerInput.yAxis() == -1 && playerInput.jump()) {
      sensorComponent.lockThinPlatform();
      return true;
    } else {
      return false;
    }
  }
}
