package io.github.mwttg.games.platform.ecs.component.movement;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * ----x---------------x----
 * |                       |
 * x                       x
 * |                       |
 * |                       |
 * |                       |
 * x                       x
 * |                       |
 * ----x---------------x----
 */
public record CollisionSensorPositions(
    Vector2f top1,
    Vector2f top2,
    Vector2f bottom1,
    Vector2f bottom2,
    Vector2f left1,
    Vector2f left2,
    Vector2f right1,
    Vector2f right2) {

  private static final float MARGIN = 0.25f;

  // the transform is usually the bottom left corner NOT the tile center
  public static CollisionSensorPositions get(final TileSize tileSize, final Vector3f transform) {
    final var width = tileSize.width();
    final var height = tileSize.height();
    final var x = transform.x();
    final var y = transform.y();

    return new CollisionSensorPositions(
        getTop1(x, y, height),
        getTop2(x, y, width, height),
        getBottom1(x, y),
        getBottom2(x, y, width),
        getLeft1(x, y, height),
        getLeft2(x, y),
        getRight1(x, y, width, height),
        getRight2(x, y, width));
  }

  private static Vector2f getTop1(final float x, final float y, final float tileHeight) {
    return new Vector2f(x + MARGIN, y + tileHeight);
  }

  private static Vector2f getTop2(final float x, final float y, final float tileWidth, final float tileHeight) {
    return new Vector2f(x + tileWidth - MARGIN, y + tileHeight);
  }

  private static Vector2f getBottom1(final float x, final float y) {
    return new Vector2f(x + MARGIN, y);
  }

  private static Vector2f getBottom2(final float x, final float y, final float tileWidth) {
    return new Vector2f(x + tileWidth - MARGIN, y);
  }

  private static Vector2f getLeft1(final float x, final float y, final float tileHeight) {
    return new Vector2f(x, y + tileHeight - MARGIN);
  }

  private static Vector2f getLeft2(final float x, final float y) {
    return new Vector2f(x, y + MARGIN);
  }

  private static Vector2f getRight1(final float x, final float y, final float tileWidth, final float tileHeight) {
    return new Vector2f(x + tileWidth, y + tileHeight - MARGIN);
  }

  private static Vector2f getRight2(final float x, final float y, final float tileWidth) {
    return new Vector2f(x + tileWidth, y + MARGIN);
  }
}
