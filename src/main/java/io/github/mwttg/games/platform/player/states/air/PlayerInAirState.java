package io.github.mwttg.games.platform.player.states.air;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.states.AbstractPlayerState;
import java.util.Map;
import org.joml.Matrix4f;

public abstract class PlayerInAirState extends AbstractPlayerState {

  private float inAirTime;

  public PlayerInAirState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                          final PlayerStateComponent playerStateComponent,
                          final PlayerEffectComponent playerEffectComponent,
                          final Matrix4f transform,
                          final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, playerEffectComponent, transform, playerData);
    this.inAirTime = 0.0f;
  }

  @Override
  public void exit() {
    super.exit();
  }

  @Override
  public void enter() {
    super.enter();
    inAirTime = 0.0f;
  }

  public void enter(float alreadyUsedAirTime) {
    inAirTime = alreadyUsedAirTime;
  }

  protected float getInAirTime() {
    return inAirTime;
  }

  protected void updateInAirTime(final float deltaTime) {
    inAirTime = inAirTime + deltaTime;
  }
}
