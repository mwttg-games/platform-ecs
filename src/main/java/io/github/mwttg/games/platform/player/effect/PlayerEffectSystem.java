package io.github.mwttg.games.platform.player.effect;

import io.github.mwttg.games.platform.draw.SpriteAnimationSystem;
import java.util.ArrayList;
import java.util.List;
import org.joml.Matrix4f;

public class PlayerEffectSystem {

  private PlayerEffectSystem() {
  }

  public static void draw(final PlayerEffectComponent playerEffectComponent, final Matrix4f view, final Matrix4f projection) {
    for (final PlayerEffect effect : playerEffectComponent.getEffects()) {
      final var animation = playerEffectComponent.getAnimationComponentByName().get(effect.name());
      SpriteAnimationSystem.draw(animation, effect.transform(), view, projection);
    }
  }

  public static void update(final PlayerEffectComponent playerEffectComponent) {
    final var current = System.currentTimeMillis();
    final var effects = playerEffectComponent.getEffects();
    final var indexToRemove = new ArrayList<PlayerEffect>();
    for (final PlayerEffect effect : effects) {
      if (effect.isDead(current)) {
        indexToRemove.add(effect);
      }
    }

    final var effectsAlive = remove(effects, indexToRemove);
    playerEffectComponent.updateAliveEffects(effectsAlive);
  }

  private static List<PlayerEffect> remove(final List<PlayerEffect> source, final List<PlayerEffect> removables) {

    final var result = new ArrayList<>(source);
    for (final PlayerEffect effect : removables) {
      result.remove(effect);
    }
    return result;
  }
}
