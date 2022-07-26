package io.github.mwttg.games.platform.ecs.entity.debug;

import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.PlaneFactory;
import io.github.mwttg.games.platform.ecs.component.draw.SpriteComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TileSize;
import io.github.mwttg.games.platform.ecs.component.movement.MovementState;
import io.github.mwttg.games.platform.ecs.component.movement.SolidGridComponent;
import io.github.mwttg.games.platform.ecs.system.draw.SpriteSystem;
import io.github.mwttg.games.platform.ecs.system.movement.MovementSystem;
import org.joml.Matrix4f;

public record PlayerEntity2(long windowId,
                            Matrix4f transform,
                            TileSize tileSize,
                            MovementState movementState,
                            SpriteComponent spriteComponent) {

  // render
  public void draw(final Matrix4f viewMatrix, final Matrix4f projectionMatrix) {
    SpriteSystem.draw(spriteComponent, transform, viewMatrix, projectionMatrix);
  }

  // physics
  public void update(final SolidGridComponent solidGridComponent, final float deltaTime, final GameState gameState) {
    MovementSystem.update(windowId, transform, tileSize, movementState, solidGridComponent, deltaTime, gameState);
  }

  public static PlayerEntity2 create(final long windowId,
                                     final String textureFilename,
                                     final TileSize tileSize,
                                     final float x,
                                     final float y) {
    final var transform = new Matrix4f().translate(x, y, 0.0f);
    final var movementStateComponent = new MovementState();
    final var spriteComponent = createSpriteComponent(textureFilename);

    return new PlayerEntity2(windowId, transform, tileSize, movementStateComponent, spriteComponent);
  }

  private static SpriteComponent createSpriteComponent(final String textureFilename) {
    final var textureId = Texture.create(textureFilename);
    final var planeData = PlaneFactory.create(1, 1);
    return SpriteComponent.create(planeData.geometry(), planeData.textureCoordinates(), textureId);
  }
}