package io.github.mwttg.games.platform.player.effect;

import org.joml.Matrix4f;

public record PlayerEffect(String animationName, Matrix4f transform, long createTimestamp, int lifetime) {

  public boolean isDead() {
    return System.currentTimeMillis() > createTimestamp + lifetime;
  }
}
