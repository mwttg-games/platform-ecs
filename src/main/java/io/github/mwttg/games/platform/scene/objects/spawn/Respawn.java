package io.github.mwttg.games.platform.scene.objects.spawn;

import org.joml.Matrix4f;
import org.joml.primitives.Rectanglef;

public record Respawn(Rectanglef zone, Matrix4f transform) {

  public static Respawn create(final Rectanglef zone) {
    final var transform = new Matrix4f().translate(zone.minX, zone.minY, -1.0f);
    return new Respawn(zone, transform);
  }
}
