package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.component.movement.BlockedDirections;
import io.github.mwttg.games.platform.ecs.component.movement.MovementStateComponent;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

class MovementCollisionSystem {

  private MovementCollisionSystem() {
  }

  static Vector3f getCorrectionTranslation(final BlockedDirections blockedDirections,
                                           final Matrix4f transform,
                                           final MovementStateComponent movementStateComponent) {
    final var position = getPosition(transform);

    float x = 0.0f;
    float y = 0.0f;

    if (blockedDirections.top()) {
      y = -(position.y() - (int) position.y());
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
      x = -(position.x() - (int) position.x());
      movementStateComponent.activateFalling();
    }

    return new Vector3f(x, y, 0.0f);
  }


  private static Vector2f getPosition(final Matrix4f transform) {
    final var position = new Vector3f();
    transform.getTranslation(position);
    return new Vector2f(position.x(), position.y());
  }
}
