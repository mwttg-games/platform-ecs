package io.github.mwttg.games.platform.ecs.system.movement;

import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.input.PlayerInputComponent;
import io.github.mwttg.games.platform.ecs.component.movement.CollisionSensorComponent;
import io.github.mwttg.games.platform.ecs.component.movement.JumpComponent;
import io.github.mwttg.games.platform.ecs.component.movement.SolidGridComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class PlayerVerticalMovementSystem {

  public static void update(final TransformComponent transformComponent,
                            final PlayerInputComponent playerInputComponent,
                            final JumpComponent jumpComponent,
                            final SolidGridComponent solidGridComponent,
                            final CollisionSensorComponent collisionSensorComponent,
                            final GameState gameState) {

    final var position = getPosition(transformComponent.getModelMatrix());
    final var topLeft = solidGridComponent.isBlocked(collisionSensorComponent.getTopLeft(position));
    final var bottomLeft = solidGridComponent.isBlocked(collisionSensorComponent.getBottomLeft(position));
    final var topRight = solidGridComponent.isBlocked(collisionSensorComponent.getTopRight(position));
    final var bottomRight = solidGridComponent.isBlocked(collisionSensorComponent.getBottomRight(position));

    if ((topLeft || topRight) && !bottomLeft && !bottomRight) {
      jumpComponent.setFalling(true);
      jumpComponent.getJumpTimer().reset();
    }

    if ((bottomLeft || bottomRight) && !topLeft && !topRight) {
      jumpComponent.setInAir(false);
      jumpComponent.setFalling(false);
      jumpComponent.setJumpCount(0);
      jumpComponent.getJumpTimer().reset();
    }

    // start Jump (start timings)
    if (playerInputComponent.getJump().isPressed() && jumpComponent.getJumpCount() == 0) {
      jumpComponent.setInAir(true);
      jumpComponent.setFalling(false);
      jumpComponent.setJumpCount(1);
      jumpComponent.getJumpTimer().reset();
      jumpComponent.setGravityFactor(gameState.sceneConfiguration().verticalUpGravity());
      jumpComponent.setRiseDuration(
          gameState.playerConfiguration().initialJumpVelocity() / gameState.sceneConfiguration().verticalUpGravity());
    }

    final var lastDuration = jumpComponent.getJumpTimer().getLastDuration();
    final var duration =  jumpComponent.getJumpTimer().getDuration();

    if (jumpComponent.isInAir()
        && duration < jumpComponent.getRiseDuration()
        && !jumpComponent.isFalling()) {
      jumpComponent.setGravityFactor(gameState.sceneConfiguration().verticalUpGravity());
    } else {
      jumpComponent.setGravityFactor(gameState.sceneConfiguration().verticalDownGravity());
    }


    // inside a jump
    var deltaY = 0.0f;

    if (jumpComponent.isInAir() && !jumpComponent.isFalling()) {
      var lastDeltaY = (-jumpComponent.getGravityFactor() / 2.0f
          * lastDuration
          * lastDuration)
          + (gameState.playerConfiguration().initialJumpVelocity() * lastDuration);

      var newDeltaY = (-jumpComponent.getGravityFactor() / 2.0f
          * duration
          * duration)
          + (gameState.playerConfiguration().initialJumpVelocity() * duration);

      deltaY = newDeltaY - lastDeltaY;
      System.out.println(jumpComponent.getGravityFactor()+ "  " + jumpComponent.getRiseDuration());
    } else if (jumpComponent.isFalling()) {
      // falling from an edge will be canceled if collision
      var lastDeltaY = (-gameState.sceneConfiguration().verticalDownGravity() / 2.0f
          * lastDuration
          * lastDuration);

      var newDeltaY = (-gameState.sceneConfiguration().verticalDownGravity() / 2.0f
          * duration
          * duration);

      deltaY = newDeltaY - lastDeltaY;
    } else {
      deltaY = 0.0f;
    }

    transformComponent.getModelMatrix().translate(0.0f, deltaY, 0.0f);


    // ------
  }

  // for a 1.0x1.0 tile (like the player) get center position
  private static Vector2f getPosition(final Matrix4f transform) {
    final var position = new Vector3f();
    transform.getTranslation(position);
    return new Vector2f(position.x() + 0.5f, position.y() + 0.5f);
  }
}
