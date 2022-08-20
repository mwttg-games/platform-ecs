package io.github.mwttg.games.platform.player.effect;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.joml.Matrix4f;

public class PlayerEffectComponent {

  private final Map<String, SpriteAnimationComponent> animationComponentByName;
  private List<PlayerEffect> effects;

  public PlayerEffectComponent(final Map<String, SpriteAnimationComponent> animationComponentByName) {
    this.animationComponentByName = animationComponentByName;
    this.effects = new ArrayList<>();
  }

  public Map<String, SpriteAnimationComponent> getAnimationComponentByName() {
    return animationComponentByName;
  }

  public List<PlayerEffect> getEffects() {
    return effects;
  }

  public void updateAliveEffects(final List<PlayerEffect> alive) {
    effects = new ArrayList<>(alive);
  }

  public void startEffect(final String name, final Matrix4f transform, final int lifeTime) {
    final var current = System.currentTimeMillis();
    final var modelMatrix = new Matrix4f(transform);
    final var effect = new PlayerEffect(name, modelMatrix, current, lifeTime);
    effects.add(effect);
  }
}
