package io.github.mwttg.games.platform.ecs.entity;

import io.github.mwttg.games.opengl.basic.utilities.geometry.MeshFactory;
import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.ecs.component.draw.SpriteStatesAnimationComponent;
import io.github.mwttg.games.platform.ecs.component.movement.MovementState;
import io.github.mwttg.games.platform.ecs.component.movement.SolidGridComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TileSize;
import io.github.mwttg.games.platform.ecs.system.draw.SpriteStateAnimationSystem;
import io.github.mwttg.games.platform.ecs.system.movement.MovementSystem;
import java.util.List;
import org.joml.Matrix4f;

public record PlayerEntity(long windowId,
                           Matrix4f transform,
                           TileSize tileSize,
                           MovementState movementState,
                           SpriteStatesAnimationComponent spriteComponent) {

  // render
  public void draw(final Matrix4f viewMatrix, final Matrix4f projectionMatrix) {
    SpriteStateAnimationSystem.draw(spriteComponent, transform, viewMatrix, projectionMatrix);
  }

  // physics
  public void update(final SolidGridComponent solidGridComponent, final float deltaTime, final GameState gameState) {
    MovementSystem.update(windowId, transform, tileSize, movementState, spriteComponent, solidGridComponent, deltaTime, gameState);
  }

  public static PlayerEntity create(final long windowId,
                                    final TileSize tileSize,
                                    final SpriteStatesAnimationComponent spriteComponent,
                                    final float x,
                                    final float y) {
    final var transform = new Matrix4f().translate(x, y, 0.0f);
    final var movementState = new MovementState();

    return new PlayerEntity(windowId, transform, tileSize, movementState, spriteComponent);
  }
}
