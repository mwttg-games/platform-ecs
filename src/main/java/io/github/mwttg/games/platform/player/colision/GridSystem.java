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
public class GridSystem {

  private static final float MARGIN = 0.15f;

  /**
   * This is needed because we need to check if we are on the ground slightly under the player, otherwise
   * we would have permanent switching states between walk and in air state.
   */
  private static final float DELTA = 0.0005f;

  private GridSystem() {
  }

  public static boolean isLeftBlocked(final Matrix4f transform,
                                      final TileSize tileSize,
                                      final GridComponent gridComponent) {
    final var currentPosition = TransformUtilities.getPosition(transform);
    final var sensor1Position = new Vector2f(currentPosition.x(), currentPosition.y() + tileSize.height() - MARGIN);
    final var sensor2Position = new Vector2f(currentPosition.x(), currentPosition.y() + MARGIN);
    final var sensor1Blocked = gridComponent.isBlocked(sensor1Position);
    final var sensor2Blocked = gridComponent.isBlocked(sensor2Position);

    return sensor1Blocked || sensor2Blocked;
  }

  public static boolean isRightBlocked(final Matrix4f transform,
                                       final TileSize tileSize,
                                       final GridComponent gridComponent) {
    final var currentPosition = TransformUtilities.getPosition(transform);
    final var sensor1Position =
        new Vector2f(currentPosition.x() + tileSize.width(), currentPosition.y() + tileSize.height() - MARGIN);
    final var sensor2Position = new Vector2f(currentPosition.x() + tileSize.width(), currentPosition.y() + MARGIN);
    final var sensor1Blocked = gridComponent.isBlocked(sensor1Position);
    final var sensor2Blocked = gridComponent.isBlocked(sensor2Position);

    return sensor1Blocked || sensor2Blocked;
  }

  public static boolean isBottomBlocked(final Matrix4f transform,
                                        final TileSize tileSize,
                                        final GridComponent gridComponent) {
    final var currentPosition = TransformUtilities.getPosition(transform);
    final var sensor1Position = new Vector2f(currentPosition.x() + MARGIN, currentPosition.y());
    final var sensor2Position = new Vector2f(currentPosition.x() + tileSize.width() - MARGIN, currentPosition.y());
    final var sensor1Blocked = gridComponent.isBlocked(sensor1Position);
    final var sensor2Blocked = gridComponent.isBlocked(sensor2Position);

    return sensor1Blocked || sensor2Blocked;
  }

  public static boolean isGroundTouched(final Matrix4f transform,
                                        final TileSize tileSize,
                                        final GridComponent gridComponent) {
    final var currentPosition = TransformUtilities.getPosition(transform);
    final var sensor1Position = new Vector2f(currentPosition.x() + MARGIN, currentPosition.y() - DELTA);
    final var sensor2Position = new Vector2f(currentPosition.x() + tileSize.width() - MARGIN, currentPosition.y() - DELTA);
    final var sensor1Blocked = gridComponent.isBlocked(sensor1Position);
    final var sensor2Blocked = gridComponent.isBlocked(sensor2Position);

    return sensor1Blocked || sensor2Blocked;
  }

  public static boolean isTopBlocked(final Matrix4f transform,
                                     final TileSize tileSize,
                                     final GridComponent gridComponent) {
    final var currentPosition = TransformUtilities.getPosition(transform);
    final var sensor1Position = new Vector2f(currentPosition.x() + MARGIN, currentPosition.y() + tileSize.height());
    final var sensor2Position =
        new Vector2f(currentPosition.x() + tileSize.width() - MARGIN, currentPosition.y() + tileSize.height());
    final var sensor1Blocked = gridComponent.isBlocked(sensor1Position);
    final var sensor2Blocked = gridComponent.isBlocked(sensor2Position);

    return sensor1Blocked || sensor2Blocked;
  }
}
