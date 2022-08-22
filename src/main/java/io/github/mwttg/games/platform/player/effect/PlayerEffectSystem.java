package io.github.mwttg.games.platform.player.effect;

import io.github.mwttg.games.platform.draw.SpriteAnimationSystem;
import java.util.ArrayList;
import org.joml.Matrix4f;

public class PlayerEffectSystem {

  private PlayerEffectSystem() {
  }

  public static void draw(final PlayerEffectComponent playerEffectComponent, final Matrix4f view, final Matrix4f projection) {
    for (final PlayerEffect effect : playerEffectComponent.getEffects()) {
      final var animation = playerEffectComponent.getAnimationComponentByName().get(effect.animationName());
      SpriteAnimationSystem.draw(animation, effect.transform(), view, projection);
    }
  }

  public static void update(final PlayerEffectComponent playerEffectComponent) {
    final var updated = new ArrayList<PlayerEffect>();

    for (final PlayerEffect effect : playerEffectComponent.getEffects()) {
      if (!effect.isDead()) {
        updated.add(effect);
      }
    }

    playerEffectComponent.updateAliveEffects(updated);
  }
}
