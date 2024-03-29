package io.github.mwttg.games.platform.player.states;

import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import java.util.Map;
import org.joml.Matrix4f;

public abstract class AbstractPlayerState implements PlayerState {

  private final Map<String, Drawable> animationComponentByName;
  private final PlayerStateComponent playerStateComponent;
  private final PlayerEffectComponent playerEffectComponent;
  private final Matrix4f transform;
  private final PlayerData playerData;

  public AbstractPlayerState(final Map<String, Drawable> drawableByName,
                             final PlayerStateComponent playerStateComponent,
                             final PlayerEffectComponent playerEffectComponent,
                             final Matrix4f transform,
                             final PlayerData playerData) {
    this.animationComponentByName = drawableByName;
    this.playerStateComponent = playerStateComponent;
    this.playerEffectComponent = playerEffectComponent;
    this.transform = transform;
    this.playerData = playerData;
  }

  protected abstract String getAnimationName();

  @Override
  public void enter() {
  }

  @Override
  public void exit() {
  }

  @Override
  public void draw(final Matrix4f model, final Matrix4f view, final Matrix4f projection) {
    animationComponentByName.get(getAnimationName()).draw(model, view, projection);
  }

  protected PlayerStateComponent getPlayerStateComponent() {
    return playerStateComponent;
  }

  protected PlayerEffectComponent getPlayerEffectComponent() {
    return playerEffectComponent;
  }

  protected Matrix4f getTransform() {
    return transform;
  }

  protected PlayerData getPlayerData() {
    return playerData;
  }

  protected boolean inputLeft(final PlayerInput playerInput) {
    return playerInput.xAxis() == -1;
  }

  protected boolean inputRight(final PlayerInput playerInput) {
    return playerInput.xAxis() == 1;
  }

  protected boolean jumpUp(final PlayerInput playerInput) {
    return playerInput.jump() && playerInput.yAxis() != -1;
  }
}
