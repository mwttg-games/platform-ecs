package io.github.mwttg.games.platform.ecs.entity;

import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.ecs.PlaneFactory;
import io.github.mwttg.games.platform.ecs.component.draw.SpriteComponent;
import io.github.mwttg.games.platform.ecs.component.movement.SolidGridComponent;
import io.github.mwttg.games.platform.ecs.system.draw.SpriteSystem;
import org.joml.Matrix4f;

public record SceneEntity(Matrix4f transform, SpriteComponent spriteComponent,
                          SolidGridComponent solidGridComponent) {

  public static SceneEntity create(final String gridFilename,
                                   final String textureFilename,
                                   final int widthInTiles,
                                   final int heightInTiles) {
    final var transform = new Matrix4f().translate(0.0f, 0.0f, -1.0f);
    final SpriteComponent spriteComponent = createSpriteComponent(textureFilename, widthInTiles, heightInTiles);
    final SolidGridComponent gridComponent = new SolidGridComponent(gridFilename);

    return new SceneEntity(transform, spriteComponent, gridComponent);

  }

  private static SpriteComponent createSpriteComponent(final String textureFilename, final int width, final int height) {
    final var textureId = Texture.create(textureFilename);
    final var planeData = PlaneFactory.create(width, height);
    return SpriteComponent.create(planeData.geometry(), planeData.textureCoordinates(), textureId);
  }

  public void draw(final Matrix4f viewMatrix, final Matrix4f projectionMatrix) {
    SpriteSystem.draw(spriteComponent, transform, viewMatrix, projectionMatrix);
  }
}
