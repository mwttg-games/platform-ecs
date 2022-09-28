package io.github.mwttg.games.platform.scene.objects.trap;

import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.scene.objects.SceneObject;
import org.joml.Matrix4f;
import org.joml.primitives.Rectanglef;

public record DeathZone(Rectanglef zone, Matrix4f transform, Drawable sprite) implements SceneObject {

  public static DeathZone create(final Rectanglef zone, final Drawable sprite) {
    final var transform = new Matrix4f().translate(zone.minX, zone.minY, -1.0f);
    return new DeathZone(zone, transform, sprite);
  }

  public void draw(final Matrix4f view, final Matrix4f projection) {
    sprite.draw(transform, view, projection);
  }
}
