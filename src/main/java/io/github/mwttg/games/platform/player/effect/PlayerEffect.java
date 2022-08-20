package io.github.mwttg.games.platform.player.effect;

import org.joml.Matrix4f;

public record PlayerEffect(String name, Matrix4f transform, long createTimestamp, int lifetime) {

  public boolean isDead(final long currentTimestamp) {
    return currentTimestamp > createTimestamp + lifetime;
  }
}
