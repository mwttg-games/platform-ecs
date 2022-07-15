package io.github.mwttg.games.platform.ecs.component.movement;

import lombok.Data;
import org.joml.Vector2f;

@Data
public class CollisionSensorComponent {

  private final float tileWidth;
  private final float tileHeight;

  public Vector2f getTopLeft(final Vector2f tileCenter) {
    return new Vector2f(tileCenter.x() - (tileWidth / 2.0f), tileCenter.y() + (tileHeight / 2.0f));
  }

  public Vector2f getBottomLeft(final Vector2f tileCenter) {
    return new Vector2f(tileCenter.x() - (tileWidth / 2.0f), tileCenter.y() - (tileHeight / 2.0f));
  }

  public Vector2f getTopRight(final Vector2f tileCenter) {
    return new Vector2f(tileCenter.x() + (tileWidth / 2.0f), tileCenter.y() + (tileHeight / 2.0f));
  }

  public Vector2f getBottomRight(final Vector2f tileCenter) {
    return new Vector2f(tileCenter.x() + (tileWidth / 2.0f), tileCenter.y() - (tileHeight / 2.0f));
  }

}
