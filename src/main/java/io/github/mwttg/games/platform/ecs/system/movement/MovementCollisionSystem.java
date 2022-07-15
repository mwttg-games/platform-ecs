package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.component.movement.CollisionSensorComponent;
import io.github.mwttg.games.platform.ecs.component.movement.MovementStateComponent;
import io.github.mwttg.games.platform.ecs.component.movement.SolidGridComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class MovementCollisionSystem {

  public static void update(final TransformComponent transformComponent,
                            final MovementStateComponent movementStateComponent,
                            final CollisionSensorComponent collisionSensorComponent,
                            final SolidGridComponent solidGridComponent) {
    // TODO extract sensor positions and extend if's (because snap top also true snap left -> topLeft
    snapTop(transformComponent, movementStateComponent, solidGridComponent, collisionSensorComponent);
    snapBottom(transformComponent, movementStateComponent, solidGridComponent, collisionSensorComponent);
    snapLeft(transformComponent, solidGridComponent, collisionSensorComponent);
    snapRight(transformComponent, solidGridComponent, collisionSensorComponent);
  }

  private static void snapTop(final TransformComponent transformComponent,
                              final MovementStateComponent movementStateComponent,
                              final SolidGridComponent solidGridComponent,
                              final CollisionSensorComponent collisionSensorComponent) {
    final var position = getPosition(transformComponent.getModelMatrix());
    final var topLeft = solidGridComponent.isBlocked(collisionSensorComponent.getTopLeft(position));
    final var topRight = solidGridComponent.isBlocked(collisionSensorComponent.getTopRight(position));

    if (topLeft || topRight) { // we hit a block
      final var xFrac = (position.y()) - ((int) position.y());  // center position
      final var y = (xFrac - 0.5f); // half tileSize of the player

      transformComponent.getModelMatrix().translate(0.0f, -y, 0.0f);
      movementStateComponent.activateFalling();
    }
  }

  private static void snapBottom(final TransformComponent transformComponent,
                                 final MovementStateComponent movementStateComponent,
                                 final SolidGridComponent solidGridComponent,
                                 final CollisionSensorComponent collisionSensorComponent) {
    final var position = getPosition(transformComponent.getModelMatrix());
    final var bottomLeft = solidGridComponent.isBlocked(collisionSensorComponent.getBottomLeft(position));
    final var bottomRight = solidGridComponent.isBlocked(collisionSensorComponent.getBottomRight(position));

    if (bottomLeft || bottomRight) { // we hit the ground // todo what happens when we hit a side wall mid air
      final var yFrac = (position.y()) - ((int) position.y());  // center position
      final var y = 1.0f - (yFrac + 0.5f); // half tileSize of the player

      transformComponent.getModelMatrix().translate(0.0f, y, 0.0f);
      movementStateComponent.deactivateFalling();
    }

    if (!bottomLeft && !bottomRight && !movementStateComponent.isJumping()) { // no ground below us
      movementStateComponent.activateFalling();
    }
  }

  private static void snapLeft(final TransformComponent transformComponent,
                               final SolidGridComponent solidGridComponent,
                               final CollisionSensorComponent collisionSensorComponent) {
    final var position = getPosition(transformComponent.getModelMatrix());
    final var bottomLeft = solidGridComponent.isBlocked(collisionSensorComponent.getBottomLeft(position));
    final var topLeft = solidGridComponent.isBlocked(collisionSensorComponent.getTopLeft(position));


    if (topLeft || bottomLeft) { // we hit a left block
      final var xFrac = (position.x()) - ((int) position.x());  // center position
      final var x = 1.0f - (xFrac + 0.5f); // half tileSize of the player

      transformComponent.getModelMatrix().translate(x, 0.0f, 0.0f);
    }
  }

  private static void snapRight(final TransformComponent transformComponent,
                                final SolidGridComponent solidGridComponent,
                                final CollisionSensorComponent collisionSensorComponent) {
    final var position = getPosition(transformComponent.getModelMatrix());
    final var bottomRight = solidGridComponent.isBlocked(collisionSensorComponent.getBottomRight(position));
    final var topRight = solidGridComponent.isBlocked(collisionSensorComponent.getTopRight(position));


    if (topRight || bottomRight) { // we hit a left block
      final var xFrac = (position.x()) - ((int) position.x());  // center position
      final var x = (xFrac - 0.5f); // half tileSize of the player

      transformComponent.getModelMatrix().translate(-x, 0.0f, 0.0f);
    }
  }

  // TODO make more generic (if tile size differs)
  // for a 1.0x1.0 tile (like the player) get center position
  private static Vector2f getPosition(final Matrix4f transform) {
    final var position = new Vector3f();
    transform.getTranslation(position);
    return new Vector2f(position.x() + 0.5f, position.y() + 0.5f);
  }
}
