package io.github.mwttg.games.platform.player.states.air;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import java.util.Map;
import org.joml.Matrix4f;

public abstract class PlayerJumpUpState extends PlayerInAirState {

  public PlayerJumpUpState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                           final PlayerStateComponent playerStateComponent,
                           final Matrix4f transform,
                           final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, transform, playerData);
  }

  @Override
  public void enter() {
    super.enter();
  }

  @Override
  public void exit() {
    super.exit();
  }
}
