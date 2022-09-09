package io.github.mwttg.games.platform.player.states.ground;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.colision.SensorSystem;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.states.AbstractPlayerState;
import java.util.Map;
import org.joml.Matrix4f;

public abstract class PlayerOnGroundState extends AbstractPlayerState {

  protected PlayerOnGroundState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                                final PlayerStateComponent playerStateComponent,
                                final PlayerEffectComponent playerEffectComponent,
                                final Matrix4f transform,
                                final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, playerEffectComponent, transform, playerData);
  }

  @Override
  public void enter() {
    super.enter();
    getPlayerData().resetJumpCounter();
  }

  @Override
  public void exit() {
    super.exit();
  }

  protected boolean grabLadder(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    final var onLadder = SensorSystem.isOnLadder(getTransform(), getPlayerData().getTileSize(), sensorComponent);
    return onLadder && playerInput.yAxis() == 1;
  }

  protected boolean goLadderDown(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    final var aboveLadder = SensorSystem.isLadderBelow(getTransform(), getPlayerData().getTileSize(), sensorComponent);
    return aboveLadder && playerInput.yAxis() == -1;
  }
}
