package io.github.mwttg.games.platform.ecs.entity.debug;

import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.PlaneFactory;
import io.github.mwttg.games.platform.ecs.component.draw.SpriteComponent;
import io.github.mwttg.games.platform.ecs.component.input.PlayerInputComponent;
import io.github.mwttg.games.platform.ecs.component.movement.CollisionSensorComponent;
import io.github.mwttg.games.platform.ecs.component.movement.JumpComponent;
import io.github.mwttg.games.platform.ecs.component.movement.SolidGridComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;
import io.github.mwttg.games.platform.ecs.component.movement.VelocityComponent;
import io.github.mwttg.games.platform.ecs.system.draw.SpriteSystem;
import io.github.mwttg.games.platform.ecs.system.movement.PlayerMovementSystem;
import org.joml.Matrix4f;

public record PlayerEntity(long windowId,
                           TransformComponent transformComponent,
                           SpriteComponent spriteComponent,
                           PlayerInputComponent inputComponent,
                           VelocityComponent velocityComponent,
                           JumpComponent jumpComponent,
                           CollisionSensorComponent collisionSensorComponent,
                           GameState gameState) {

  public static PlayerEntity create(final long windowId,
                                    final String textureFilename,
                                    final float x,
                                    final float y,
                                    final GameState gameState) {
    final var transformComponent = new TransformComponent(x, y);
    final SpriteComponent spriteComponent = createSpriteComponent(textureFilename);
    final var playerInputComponent = new PlayerInputComponent();
    final var velocityComponent = new VelocityComponent();
    final var jumpComponent = new JumpComponent();
    final var collisionSensorComponent = new CollisionSensorComponent(1.0f, 1.0f);

    return new PlayerEntity(windowId,
        transformComponent,
        spriteComponent,
        playerInputComponent,
        velocityComponent,
        jumpComponent,
        collisionSensorComponent,
        gameState);
  }

  private static SpriteComponent createSpriteComponent(final String textureFilename) {
    final var textureId = Texture.create(textureFilename);
    final var planeData = PlaneFactory.create(1, 1);
    return SpriteComponent.create(planeData.geometry(), planeData.textureCoordinates(), textureId);
  }

  public void updateLogic(final float deltaTime, final SolidGridComponent solidGridComponent) {
    PlayerMovementSystem.update(windowId,
        inputComponent,
        jumpComponent,
        velocityComponent,
        collisionSensorComponent,
        solidGridComponent,
        transformComponent,
        gameState,
        deltaTime);
  }



  public void updateRender(final Matrix4f viewMatrix, final Matrix4f projectionMatrix) {
    SpriteSystem.draw(spriteComponent, transformComponent.getModelMatrix(), viewMatrix, projectionMatrix);
  }
}
