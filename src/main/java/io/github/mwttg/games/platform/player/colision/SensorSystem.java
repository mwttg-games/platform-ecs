package io.github.mwttg.games.platform.player.colision;

import io.github.mwttg.games.platform.player.TileSize;
import io.github.mwttg.games.platform.player.TransformUtilities;
import org.joml.Matrix4f;
import org.joml.Vector2f;

/**
 * Basically a bunch of collision sensors (and their positions).
 * ----x---------------x----
 * |                       |
 * x                       x
 * |                       |
 * |                       |
 * |                       |
 * x                       x
 * |                       |
 * ----x---------------x----
 *     x               x
 *
 * <p>x = sensor</p>
 */
public class SensorSystem {

  private static final float MARGIN = 0.15f;

  /**
   * This is needed because we need to check if we are on the ground slightly under the player, otherwise
   * we would have permanent switching states between walk and in air state.
   */
  private static final float DELTA = 0.0005f;

  private SensorSystem() {
  }

  public static boolean isLeftBlocked(final Matrix4f transform,
                                      final TileSize tileSize,
                                      final SensorComponent sensorComponent) {
    final var currentPosition = TransformUtilities.getPosition(transform);
    final var sensor1Position = new Vector2f(currentPosition.x(), currentPosition.y() + tileSize.height() - MARGIN);
    final var sensor2Position = new Vector2f(currentPosition.x(), currentPosition.y() + MARGIN);
    final var tileType1 = sensorComponent.getTileType(sensor1Position);
    final var tileType2 = sensorComponent.getTileType(sensor2Position);

    return (tileType1 == TileType.SOLID) || (tileType2 == TileType.SOLID);
  }

  public static boolean isRightBlocked(final Matrix4f transform,
                                       final TileSize tileSize,
                                       final SensorComponent sensorComponent) {
    final var currentPosition = TransformUtilities.getPosition(transform);
    final var sensor1Position =
        new Vector2f(currentPosition.x() + tileSize.width(), currentPosition.y() + tileSize.height() - MARGIN);
    final var sensor2Position = new Vector2f(currentPosition.x() + tileSize.width(), currentPosition.y() + MARGIN);
    final var tileType1 = sensorComponent.getTileType(sensor1Position);
    final var tileType2 = sensorComponent.getTileType(sensor2Position);

    return (tileType1 == TileType.SOLID) || (tileType2 == TileType.SOLID);
  }

  public static boolean isBottomBlocked(final Matrix4f transform,
                                        final TileSize tileSize,
                                        final SensorComponent sensorComponent) {
    final var currentPosition = TransformUtilities.getPosition(transform);
    final var sensor1Position = new Vector2f(currentPosition.x() + MARGIN, currentPosition.y());
    final var sensor2Position = new Vector2f(currentPosition.x() + tileSize.width() - MARGIN, currentPosition.y());
    final var tileType1 = sensorComponent.getTileType(sensor1Position);
    final var tileType2 = sensorComponent.getTileType(sensor2Position);

    return (tileType1 == TileType.SOLID) || (tileType1 == TileType.LADDER_TOP)
        || (tileType2 == TileType.SOLID) || (tileType2 == TileType.LADDER_TOP);
  }

  public static boolean isGroundTouched(final Matrix4f transform,
                                        final TileSize tileSize,
                                        final SensorComponent sensorComponent) {
    final var currentPosition = TransformUtilities.getPosition(transform);
    final var sensor1Position = new Vector2f(currentPosition.x() + MARGIN, currentPosition.y() - DELTA);
    final var sensor2Position = new Vector2f(currentPosition.x() + tileSize.width() - MARGIN, currentPosition.y() - DELTA);
    final var tileType1 = sensorComponent.getTileType(sensor1Position);
    final var tileType2 = sensorComponent.getTileType(sensor2Position);

    return (tileType1 == TileType.SOLID) || (tileType1 == TileType.LADDER_TOP)
        || (tileType2 == TileType.SOLID) || (tileType2 == TileType.LADDER_TOP);
  }

  public static boolean isGroundTouchedFromLadder(final Matrix4f transform,
                                        final TileSize tileSize,
                                        final SensorComponent sensorComponent) {
    final var currentPosition = TransformUtilities.getPosition(transform);
    final var sensor1Position = new Vector2f(currentPosition.x() + MARGIN, currentPosition.y() - DELTA);
    final var sensor2Position = new Vector2f(currentPosition.x() + tileSize.width() - MARGIN, currentPosition.y() - DELTA);
    final var tileType1 = sensorComponent.getTileType(sensor1Position);
    final var tileType2 = sensorComponent.getTileType(sensor2Position);

    return (tileType1 == TileType.SOLID) || (tileType2 == TileType.SOLID);
  }

  public static boolean isTopBlocked(final Matrix4f transform,
                                     final TileSize tileSize,
                                     final SensorComponent sensorComponent) {
    final var currentPosition = TransformUtilities.getPosition(transform);
    final var sensor1Position = new Vector2f(currentPosition.x() + MARGIN, currentPosition.y() + tileSize.height());
    final var sensor2Position =
        new Vector2f(currentPosition.x() + tileSize.width() - MARGIN, currentPosition.y() + tileSize.height());
    final var tileType1 = sensorComponent.getTileType(sensor1Position);
    final var tileType2 = sensorComponent.getTileType(sensor2Position);

    return (tileType1 == TileType.SOLID) || (tileType2 == TileType.SOLID);
  }

  public static boolean isOnLadder(final Matrix4f transform,
                                   final TileSize tileSize,
                                   final SensorComponent sensorComponent) {
    final var currentPosition = TransformUtilities.getPosition(transform);
    final var centerBottom = new Vector2f(currentPosition.x() + (tileSize.width() / 2.0f), currentPosition.y());
    final var centerTop =
        new Vector2f(currentPosition.x() + (tileSize.width() / 2.0f), currentPosition.y() + tileSize.height());
    final var tileType1 = sensorComponent.getTileType(centerBottom);
    final var tileType2 = sensorComponent.getTileType(centerTop);

    return (tileType1 == TileType.LADDER) || (tileType1 == TileType.LADDER_TOP)
        || (tileType2 == TileType.LADDER) || (tileType2 == TileType.LADDER_TOP);
  }

  public static boolean isLadderBelow(final Matrix4f transform,
                                   final TileSize tileSize,
                                   final SensorComponent sensorComponent) {
    final var currentPosition = TransformUtilities.getPosition(transform);
    final var centerBottom = new Vector2f(currentPosition.x() + (tileSize.width() / 2.0f), currentPosition.y() - DELTA);
    final var tileType1 = sensorComponent.getTileType(centerBottom);

    return (tileType1 == TileType.LADDER) || (tileType1 == TileType.LADDER_TOP);
  }
}
