package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.PlayerConfiguration;
import io.github.mwttg.games.platform.ecs.component.input.PlayerInputComponent;
import io.github.mwttg.games.platform.ecs.component.movement.CollisionSensorComponent;
import io.github.mwttg.games.platform.ecs.component.movement.SolidGridComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;
import io.github.mwttg.games.platform.ecs.component.movement.VelocityComponent;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class PlayerHorizontalMovementSystem {
  public static void update(final TransformComponent transformComponent,
                            final PlayerInputComponent playerInputComponent,
                            final VelocityComponent velocityComponent,
                            final SolidGridComponent solidGridComponent,
                            final CollisionSensorComponent collisionSensorComponent,
                            final float deltaTime,
                            final PlayerConfiguration playerConfiguration) {
    setHorizontalVelocity(playerInputComponent, velocityComponent, playerConfiguration);
    translate(transformComponent, velocityComponent, deltaTime);
    checkForCollision(transformComponent, solidGridComponent, collisionSensorComponent, velocityComponent);
  }

  private static void setHorizontalVelocity(final PlayerInputComponent playerInputComponent,
                                            final VelocityComponent velocityComponent,
                                            final PlayerConfiguration playerConfiguration) {
    var leftVelocityAddend = 0.0f;
    var rightVelocityAddend = 0.0f;

    if (playerInputComponent.getLeft().isPressed()) {
      leftVelocityAddend = -playerConfiguration.walkVelocity();
    }

    if (playerInputComponent.getRight().isPressed()) {
      rightVelocityAddend = playerConfiguration.walkVelocity();
    }

    velocityComponent.setHorizontal(leftVelocityAddend + rightVelocityAddend);
  }

  private static void translate(final TransformComponent transformComponent,
                                final VelocityComponent velocityComponent,
                                final float deltaTime) {
    final var deltaX = velocityComponent.getHorizontal() * deltaTime;
    transformComponent.getModelMatrix().translate(deltaX, 0.0f, 0.0f);
  }

  private static void checkForCollision(final TransformComponent transformComponent,
                                        final SolidGridComponent solidGridComponent,
                                        final CollisionSensorComponent collisionSensorComponent,
                                        final VelocityComponent velocityComponent) {
    final var position = getPosition(transformComponent.getModelMatrix());
    final var topLeft = solidGridComponent.isBlocked(collisionSensorComponent.getTopLeft(position));
    final var bottomLeft = solidGridComponent.isBlocked(collisionSensorComponent.getBottomLeft(position));
    final var topRight = solidGridComponent.isBlocked(collisionSensorComponent.getTopRight(position));
    final var bottomRight = solidGridComponent.isBlocked(collisionSensorComponent.getBottomRight(position));

    // snap to grid if collision (for left side)
    if ((topLeft || bottomLeft) && velocityComponent.getHorizontal() < 0.0f) {
      final var playerLeft = collisionSensorComponent.getTopLeft(position).x();
      final var snapX = (((int) playerLeft) + 1.0f) - playerLeft;
      transformComponent.getModelMatrix().translate(snapX, 0.0f, 0.0f);
    }

    if ((topRight || bottomRight) && velocityComponent.getHorizontal() > 0.0f) {
      final var playerRight = collisionSensorComponent.getTopLeft(position).x();
      final var snapX = playerRight - ((int) playerRight);
      transformComponent.getModelMatrix().translate(-snapX, 0.0f, 0.0f);
    }
  }

  // for a 1.0x1.0 tile (like the player) get center position
  private static Vector2f getPosition(final Matrix4f transform) {
    final var position = new Vector3f();
    transform.getTranslation(position);
    return new Vector2f(position.x() + 0.5f, position.y() + 0.5f);
  }
}
