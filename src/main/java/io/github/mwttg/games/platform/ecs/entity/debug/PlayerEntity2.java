package io.github.mwttg.games.platform.ecs.entity.debug;

import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.PlaneFactory;
import io.github.mwttg.games.platform.ecs.component.draw.SpriteComponent;
import io.github.mwttg.games.platform.ecs.component.input.PlayerInputComponent;
import io.github.mwttg.games.platform.ecs.component.movement.CollisionSensorComponent;
import io.github.mwttg.games.platform.ecs.component.movement.MovementStateComponent;
import io.github.mwttg.games.platform.ecs.component.movement.SolidGridComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;
import io.github.mwttg.games.platform.ecs.component.movement.VelocityComponent;
import io.github.mwttg.games.platform.ecs.system.draw.SpriteSystem;
import io.github.mwttg.games.platform.ecs.system.movement.MovementStateSystem;
import io.github.mwttg.games.platform.ecs.system.movement.MovementSystem;
import org.joml.Matrix4f;

public record PlayerEntity2(long windowId,
                            PlayerInputComponent playerInputComponent,
                            TransformComponent transformComponent,
                            VelocityComponent velocityComponent,
                            MovementStateComponent movementStateComponent,
                            CollisionSensorComponent collisionSensorComponent,
                            SpriteComponent spriteComponent) {

  // render
  public void draw(final Matrix4f viewMatrix, final Matrix4f projectionMatrix) {
    SpriteSystem.draw(spriteComponent, transformComponent.getModelMatrix(), viewMatrix, projectionMatrix);
  }

  // physics
  public void update(final SolidGridComponent solidGridComponent, final float deltaTime, final GameState gameState) {
    MovementSystem.update(windowId, playerInputComponent, transformComponent, velocityComponent, movementStateComponent, collisionSensorComponent, solidGridComponent, deltaTime, gameState);
  }

  public static PlayerEntity2 create(final long windowId,
                                    final String textureFilename,
                                    final float x,
                                    final float y) {
    final var playerInputComponent = new PlayerInputComponent();
    final var transformComponent = new TransformComponent(x, y);
    final var velocityComponent = new VelocityComponent();
    final var movementStateComponent = new MovementStateComponent();
    final var collisionSensorComponent = new CollisionSensorComponent(1.0f, 1.0f);
    final var spriteComponent = createSpriteComponent(textureFilename);

    return new PlayerEntity2(windowId, playerInputComponent, transformComponent, velocityComponent,movementStateComponent, collisionSensorComponent, spriteComponent);
  }

  private static SpriteComponent createSpriteComponent(final String textureFilename) {
    final var textureId = Texture.create(textureFilename);
    final var planeData = PlaneFactory.create(1, 1);
    return SpriteComponent.create(planeData.geometry(), planeData.textureCoordinates(), textureId);
  }
}