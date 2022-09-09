package io.github.mwttg.games.platform.player.colision;

import io.github.mwttg.games.platform.player.TileSize;
import io.github.mwttg.games.platform.player.TransformUtilities;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public final class SensorPosition {

  private static final float MARGIN = 0.2f;
  private static final float DELTA = 0.0005f;

  private SensorPosition() {
  }

  /**
   * ---------------
   * |             |
   * x             |
   * |             |
   * |             |
   * |             |
   * ---------------
   */
  static Vector2f getLeftTop(final Matrix4f transform, final TileSize tileSize) {
    final var position = TransformUtilities.getPosition(transform);
    return new Vector2f(position.x(), position.y() + tileSize.height() - MARGIN);
  }

  /**
   * ---------------
   * |             |
   * |             |
   * |             |
   * x             |
   * |             |
   * ---------------
   */
  static Vector2f getLeftBottom(final Matrix4f transform) {
    final var position = TransformUtilities.getPosition(transform);
    return new Vector2f(position.x(), position.y() + MARGIN);
  }

  /**
   * ---------------
   * |             |
   * |             x
   * |             |
   * |             |
   * |             |
   * ---------------
   */
  static Vector2f getRightTop(final Matrix4f transform, final TileSize tileSize) {
    final var position = TransformUtilities.getPosition(transform);
    return new Vector2f(position.x() + tileSize.width(), position.y() + tileSize.height() - MARGIN);
  }

  /**
   * ---------------
   * |             |
   * |             |
   * |             |
   * |             x
   * |             |
   * ---------------
   */
  static Vector2f getRightBottom(final Matrix4f transform, final TileSize tileSize) {
    final var position = TransformUtilities.getPosition(transform);
    return new Vector2f(position.x() + tileSize.width(), position.y() + MARGIN);
  }

  /**
   * ---------------
   * |             |
   * |             |
   * |             |
   * |             |
   * |             |
   * ---x-----------
   */
  static Vector2f getBottomLeft(final Matrix4f transform) {
    final var position = TransformUtilities.getPosition(transform);
    return new Vector2f(position.x() + MARGIN, position.y());
  }

  /**
   * ---------------
   * |             |
   * |             |
   * |             |
   * |             |
   * |             |
   * -----------x---
   */
  static Vector2f getBottomRight(final Matrix4f transform, final TileSize tileSize) {
    final var position = TransformUtilities.getPosition(transform);
    return new Vector2f(position.x() + tileSize.width() - MARGIN, position.y());
  }

  /**
   * ---x-----------
   * |             |
   * |             |
   * |             |
   * |             |
   * |             |
   * ---------------
   */
  static Vector2f getTopLeft(final Matrix4f transform, final TileSize tileSize) {
    final var position = TransformUtilities.getPosition(transform);
    return new Vector2f(position.x() + MARGIN, position.y() + tileSize.height());
  }

  /**
   * -----------x---
   * |             |
   * |             |
   * |             |
   * |             |
   * |             |
   * ---------------
   */
  static Vector2f getTopRight(final Matrix4f transform, final TileSize tileSize) {
    final var position = TransformUtilities.getPosition(transform);
    return new Vector2f(position.x() + tileSize.width() - MARGIN, position.y() + tileSize.height());
  }

  /**
   * ---------------
   * |             |
   * |             |
   * |             |
   * |             |
   * |             |
   * ---------------
   *    x
   */
  static Vector2f getGroundLeft(final Matrix4f transform) {
    final var position = TransformUtilities.getPosition(transform);
    return new Vector2f(position.x() + MARGIN, position.y() - DELTA);
  }

  /**
   * ---------------
   * |             |
   * |             |
   * |             |
   * |             |
   * |             |
   * ---------------
   *            x
   */
  static Vector2f getGroundRight(final Matrix4f transform, final TileSize tileSize) {
    final var position = TransformUtilities.getPosition(transform);
    return new Vector2f(position.x() + tileSize.width() - MARGIN, position.y() - DELTA);
  }

  /**
   * ---------------
   * |             |
   * |             |
   * |             |
   * |             |
   * |             |
   * ---------------
   *       x
   */
  static Vector2f getGroundCenter(final Matrix4f transform, final TileSize tileSize) {
    final var Position = TransformUtilities.getPosition(transform);
    return new Vector2f(Position.x() + (tileSize.width() / 2.0f), Position.y() - DELTA);
  }

  /**
   * ---------------
   * |             |
   * |             |
   * |             |
   * |             |
   * |             |
   * -------x-------
   */
  static Vector2f getLadderTop(final Matrix4f transform, final TileSize tileSize) {
    final var position = TransformUtilities.getPosition(transform);
    return new Vector2f(position.x() + (tileSize.width() / 2.0f), position.y() + tileSize.height() - MARGIN);
  }

  /**
   * ---------------
   * |      x      |
   * |             |
   * |             |
   * |             |
   * |             |
   * ---------------
   */
  static Vector2f getLadderBottom(final Matrix4f transform, final TileSize tileSize) {
    final var position = TransformUtilities.getPosition(transform);
    return new Vector2f(position.x() + (tileSize.width() / 2.0f), position.y());
  }
}
