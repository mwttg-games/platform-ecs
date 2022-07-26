package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.component.movement.BlockedDirections;
import io.github.mwttg.games.platform.ecs.component.movement.CollisionSensorPositions;
import io.github.mwttg.games.platform.ecs.component.movement.SolidGridComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TileSize;
import org.joml.Matrix4f;
import org.joml.Vector3f;

class SolidGridSystem {

  private SolidGridSystem() {
  }

  static BlockedDirections getBlockedDirections(final SolidGridComponent solidGridComponent,
                                                final Matrix4f transform,
                                                final TileSize tileSize) {
    final var currentPosition = getCurrentPosition(transform);
    final var sensorPositions = CollisionSensorPositions.get(tileSize, currentPosition);

    final var top1 = solidGridComponent.isBlocked(sensorPositions.top1());
    final var top2 = solidGridComponent.isBlocked(sensorPositions.top2());
    final var bottom1 = solidGridComponent.isBlocked(sensorPositions.bottom1());
    final var bottom2 = solidGridComponent.isBlocked(sensorPositions.bottom2());
    final var left1 = solidGridComponent.isBlocked(sensorPositions.left1());
    final var left2 = solidGridComponent.isBlocked(sensorPositions.left2());
    final var right1 = solidGridComponent.isBlocked(sensorPositions.right1());
    final var right2 = solidGridComponent.isBlocked(sensorPositions.right2());

    return new BlockedDirections(
        top1 || top2,
        bottom1 || bottom2,
        left1 || left2,
        right1 || right2);
  }

  private static Vector3f getCurrentPosition(final Matrix4f transform) {
    final var result = new Vector3f();
    transform.getTranslation(result);

    return result;
  }
}
