package io.github.mwttg.games.platform.player.colision;

import io.github.mwttg.games.platform.player.TileSize;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public final class SensorSystem {

  // How much can a player 'sink' into a grid cell for a thin platform
  // Checking 'only the top of a cell'
  // TODO refactor check if last position was above or below
  private static final float THIN_PLATFORM_DELTA = 0.3f;

  private SensorSystem() {
  }

  public static void update(final float deltaTime, final SensorComponent sensorComponent) {
    sensorComponent.updateLock(deltaTime);
  }

  public static boolean isLeftBlocked(final Matrix4f transform,
                                      final TileSize tileSize,
                                      final SensorComponent sensorComponent) {
    final var leftTop = SensorPosition.getLeftTop(transform, tileSize);
    final var leftBottom = SensorPosition.getLeftBottom(transform);
    final var tile1 = sensorComponent.getTileType(leftTop);
    final var tile2 = sensorComponent.getTileType(leftBottom);

    return isSolid(tile1, tile2);
  }

  public static boolean isRightBlocked(final Matrix4f transform,
                                       final TileSize tileSize,
                                       final SensorComponent sensorComponent) {
    final var rightTop = SensorPosition.getRightTop(transform, tileSize);
    final var rightBottom = SensorPosition.getRightBottom(transform, tileSize);
    final var tile1 = sensorComponent.getTileType(rightTop);
    final var tile2 = sensorComponent.getTileType(rightBottom);

    return isSolid(tile1, tile2);
  }

  public static boolean isBottomBlocked(final Matrix4f transform,
                                        final TileSize tileSize,
                                        final SensorComponent sensorComponent) {
    final var bottomLeft = SensorPosition.getBottomLeft(transform);
    final var bottomRight = SensorPosition.getBottomRight(transform, tileSize);
    final var tile1 = sensorComponent.getTileType(bottomLeft);
    final var tile2 = sensorComponent.getTileType(bottomRight);

    return isSolid(tile1, tile2) || isLadderTopOnly(tile1, tile2)
        || isOnThinPlatform(tile1, bottomLeft, sensorComponent)
        || isOnThinPlatform(tile2, bottomRight, sensorComponent);
  }

  public static boolean isGroundTouched(final Matrix4f transform,
                                        final TileSize tileSize,
                                        final SensorComponent sensorComponent) {
    final var groundLeft = SensorPosition.getGroundLeft(transform);
    final var groundRight = SensorPosition.getGroundRight(transform, tileSize);
    final var tile1 = sensorComponent.getTileType(groundLeft);
    final var tile2 = sensorComponent.getTileType(groundRight);

    return isSolid(tile1, tile2) || isLadderTopOnly(tile1, tile2)
        || isOnThinPlatform(tile1, groundLeft, sensorComponent)
        || isOnThinPlatform(tile2, groundRight, sensorComponent);
  }

  public static boolean isGroundTouchedFromLadder(final Matrix4f transform,
                                                  final TileSize tileSize,
                                                  final SensorComponent sensorComponent) {
    final var groundLeft = SensorPosition.getGroundLeft(transform);
    final var groundRight = SensorPosition.getGroundRight(transform, tileSize);
    final var tile1 = sensorComponent.getTileType(groundLeft);
    final var tile2 = sensorComponent.getTileType(groundRight);

    return isSolid(tile1, tile2);
  }

  public static boolean isTopBlocked(final Matrix4f transform,
                                     final TileSize tileSize,
                                     final SensorComponent sensorComponent) {
    final var topLeft = SensorPosition.getTopLeft(transform, tileSize);
    final var topRight = SensorPosition.getTopRight(transform, tileSize);
    final var tile1 = sensorComponent.getTileType(topLeft);
    final var tile2 = sensorComponent.getTileType(topRight);

    return isSolid(tile1, tile2);
  }

  public static boolean isOnLadder(final Matrix4f transform,
                                   final TileSize tileSize,
                                   final SensorComponent sensorComponent) {
    final var ladderTop = SensorPosition.getLadderTop(transform, tileSize);
    final var ladderBottom = SensorPosition.getLadderBottom(transform, tileSize);
    final var tile1 = sensorComponent.getTileType(ladderTop);
    final var tile2 = sensorComponent.getTileType(ladderBottom);

    return isLadder(tile1, tile2);
  }

  public static boolean isLadderBelow(final Matrix4f transform,
                                      final TileSize tileSize,
                                      final SensorComponent sensorComponent) {
    final var centerBottom = SensorPosition.getGroundCenter(transform, tileSize);
    final var tile = sensorComponent.getTileType(centerBottom);

    return isLadder(tile);
  }

  public static boolean isOnThinPlatform(final Matrix4f transform,
                                         final TileSize tileSize,
                                         final SensorComponent sensorComponent) {
    final var groundLeft = SensorPosition.getGroundLeft(transform);
    final var groundRight = SensorPosition.getGroundRight(transform, tileSize);
    final var tile1 = sensorComponent.getTileType(groundLeft);
    final var tile2 = sensorComponent.getTileType(groundRight);

    return isThinPlatform(tile1, tile2);
  }

  private static boolean isOnThinPlatform(final TileType tile,
                                          final Vector2f position,
                                          final SensorComponent sensorComponent) {
    final var inGridCell = 1 - (position.y() - ((int) position.y()));
    return (tile == TileType.THIN_PLATFORM)
        && (inGridCell < THIN_PLATFORM_DELTA)
        && !sensorComponent.isThinPlatformLocked();
  }

  private static boolean isThinPlatform(final TileType... tileTypes) {
    for (final TileType type : tileTypes) {
      if (type == TileType.THIN_PLATFORM) {
        return true;
      }
    }

    return false;
  }

  private static boolean isLadderTopOnly(final TileType... tileTypes) {
    for (final TileType type : tileTypes) {
      if (type == TileType.LADDER_TOP) {
        return true;
      }
    }

    return false;
  }

  private static boolean isLadder(final TileType... tileTypes) {
    for (final TileType type : tileTypes) {
      if (type == TileType.LADDER || type == TileType.LADDER_TOP) {
        return true;
      }
    }

    return false;
  }

  private static boolean isSolid(final TileType... tileTypes) {
    for (final TileType type : tileTypes) {
      if (type == TileType.SOLID) {
        return true;
      }
    }

    return false;
  }
}
