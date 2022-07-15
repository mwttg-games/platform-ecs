package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.component.movement.CollisionSensorComponent;
import io.github.mwttg.games.platform.ecs.component.movement.SolidGridComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;
import io.github.mwttg.games.platform.ecs.component.movement.VelocityComponent;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class SolidGridSystem {

  public static void update(final TransformComponent transformComponent,
                            final VelocityComponent velocityComponent,
                            final CollisionSensorComponent collisionSensorComponent,
                            final SolidGridComponent solidGridComponent) {
    final var position = getPosition(transformComponent.getModelMatrix());
    final var topLeft = solidGridComponent.isBlocked(collisionSensorComponent.getTopLeft(position));
    final var bottomLeft = solidGridComponent.isBlocked(collisionSensorComponent.getBottomLeft(position));
    final var topRight = solidGridComponent.isBlocked(collisionSensorComponent.getTopRight(position));
    final var bottomRight = solidGridComponent.isBlocked(collisionSensorComponent.getBottomRight(position));

    if ((topLeft || bottomLeft) && velocityComponent.getHorizontal() < 0.0f) {
      velocityComponent.setHorizontal(0.0f);
    }

    if ((topRight || bottomRight) && velocityComponent.getHorizontal() > 0.0f) {
      velocityComponent.setHorizontal(0.0f);
    }
  }

  // for a 1.0x1.0 tile (like the player) get center position
  private static Vector2f getPosition(final Matrix4f transform) {
    final var position = new Vector3f();
    transform.getTranslation(position);
    return new Vector2f(position.x() + 0.5f, position.y() + 0.5f);
  }
}
