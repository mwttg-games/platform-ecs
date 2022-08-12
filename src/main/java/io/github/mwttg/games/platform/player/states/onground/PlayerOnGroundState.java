package io.github.mwttg.games.platform.player.states.onground;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.draw.SpriteAnimationSystem;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.states.PlayerState;
import java.util.Map;
import org.joml.Matrix4f;

public abstract class PlayerOnGroundState implements PlayerState {

  private final Map<String, SpriteAnimationComponent> animationComponentByName;
  private final PlayerStateComponent playerStateComponent;
  private final Matrix4f transform;
  private final PlayerData playerData;

  public PlayerOnGroundState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                             final PlayerStateComponent playerStateComponent,
                             final Matrix4f transform,
                             final PlayerData playerData) {
    this.animationComponentByName = animationComponentByName;
    this.playerStateComponent = playerStateComponent;
    this.transform = transform;
    this.playerData = playerData;
  }

  protected abstract String getAnimationName();

  @Override
  public void draw(final Matrix4f model, final Matrix4f view, final Matrix4f projection) {
    final var animationName = getAnimationName();
    final var animation = animationComponentByName.get(animationName);
    SpriteAnimationSystem.draw(animation, model, view, projection);
  }

  protected PlayerStateComponent getPlayerStateComponent() {
    return playerStateComponent;
  }

  protected Matrix4f getTransform() {
    return transform;
  }

  protected PlayerData getPlayerData() {
    return playerData;
  }
}
