package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.component.movement.BlockedDirections;
import io.github.mwttg.games.platform.ecs.component.movement.EntityTileSize;
import io.github.mwttg.games.platform.ecs.component.movement.MovementStateComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MovementCollisionSystem {

  private static final Logger LOG = LoggerFactory.getLogger(MovementCollisionSystem.class);

  public static void update(final EntityTileSize entityTileSize,
                            final BlockedDirections blockedDirections,
                            final Matrix4f transform,
                            final MovementStateComponent movementStateComponent) {
    final var position = getPosition(transform);

    float x = 0.0f;
    float y = 0.0f;

    if (blockedDirections.top()) {
      y =  - (position.y() - (int) position.y());
      movementStateComponent.activateFalling();
    }

    if (blockedDirections.bottom()) {
      y = ((int) position.y() + 1.0f) - position.y();
      movementStateComponent.deactivateFalling();
    }

    if (!blockedDirections.bottom() && !movementStateComponent.isJumping()) {
      movementStateComponent.activateFalling();
    }

    if (blockedDirections.left()) {
      x = ((int) position.x() + 1.0f) - position.x();
      movementStateComponent.activateFalling();
    }

    if (blockedDirections.right()) {
      x = - (position.x() - (int) position.x());
      movementStateComponent.activateFalling();
    }

    LOG.info("blockedDirections = {} position = {} x = {} y = {}", blockedDirections, position, x, y);
    transform.translate(x, y, 0.0f); // mutable call (call by reference)
  }


  private static Vector2f getPosition(final Matrix4f transform) {
    final var position = new Vector3f();
    transform.getTranslation(position);
    return new Vector2f(position.x(), position.y());
  }
}
