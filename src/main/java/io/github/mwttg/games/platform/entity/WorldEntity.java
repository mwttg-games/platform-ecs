package io.github.mwttg.games.platform.entity;

import io.github.mwttg.games.platform.draw.SpriteSystem;
import io.github.mwttg.games.platform.level.LevelStateComponent;
import io.github.mwttg.games.platform.level.LevelStateSystem;
import org.joml.Matrix4f;

public record WorldEntity(LevelStateComponent levelStateComponent) {

  private static final Matrix4f TRANSFORM = new Matrix4f().translate(0.0f, 0.0f, -1.0f);
  private static final Matrix4f BACKGROUND_TRANSFORM = new Matrix4f().translate(0.0f, 0.0f, -2.0f);


  public void update(final PlayerEntity playerEntity) {
    LevelStateSystem.update(levelStateComponent, playerEntity);
  }

  public void draw(final Matrix4f viewMatrix, final Matrix4f projectionMatrix) {
    final var background = levelStateComponent.getLevelComponent().background();
    SpriteSystem.draw(background, BACKGROUND_TRANSFORM, viewMatrix, projectionMatrix);

    final var spriteComponent = levelStateComponent.getLevelComponent().spriteComponent();
    SpriteSystem.draw(spriteComponent, TRANSFORM, viewMatrix, projectionMatrix);
  }
}
