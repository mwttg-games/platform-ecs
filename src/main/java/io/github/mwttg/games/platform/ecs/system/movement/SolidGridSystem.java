package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.component.movement.BlockedDirections;
import io.github.mwttg.games.platform.ecs.component.movement.CollisionSensorPositions;
import io.github.mwttg.games.platform.ecs.component.movement.EntityTileSize;
import io.github.mwttg.games.platform.ecs.component.movement.SolidGridComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;
import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolidGridSystem {

  private static final Logger LOG = LoggerFactory.getLogger(SolidGridSystem.class);

  public static BlockedDirections getBlockedDirections(final SolidGridComponent solidGridComponent,
                                                       final TransformComponent transformComponent,
                                                       final EntityTileSize tileSize) {
    final var modelMatrix = transformComponent.getModelMatrix();
    final var transform = new Vector3f();
    modelMatrix.getTranslation(transform);
    final var sensorPositions = CollisionSensorPositions.get(tileSize, transform);

    final var top1 = solidGridComponent.isBlocked(sensorPositions.top1());
    final var top2 = solidGridComponent.isBlocked(sensorPositions.top2());
    final var bottom1 = solidGridComponent.isBlocked(sensorPositions.bottom1());
    final var bottom2 = solidGridComponent.isBlocked(sensorPositions.bottom2());
    final var left1 = solidGridComponent.isBlocked(sensorPositions.left1());
    final var left2 = solidGridComponent.isBlocked(sensorPositions.left2());
    final var right1 = solidGridComponent.isBlocked(sensorPositions.right1());
    final var right2 = solidGridComponent.isBlocked(sensorPositions.right2());

    LOG.info("blocked directions: {} {} {} {} {} {} {} {}", top1, top2, bottom1, bottom2, left1, left2, right1, right2);

    return new BlockedDirections(
        top1 || top2,
        bottom1 || bottom2,
        left1 || left2,
        right1 || right2);
  }
}
