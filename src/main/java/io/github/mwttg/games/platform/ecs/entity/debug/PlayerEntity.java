package io.github.mwttg.games.platform.ecs.entity.debug;

import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.ecs.PlaneFactory;
import io.github.mwttg.games.platform.ecs.PlayerConfiguration;
import io.github.mwttg.games.platform.ecs.SceneConfiguration;
import io.github.mwttg.games.platform.ecs.component.draw.SpriteComponent;
import io.github.mwttg.games.platform.ecs.component.input.PlayerInputComponent;
import io.github.mwttg.games.platform.ecs.component.movement.KinematicComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TransformComponent;
import io.github.mwttg.games.platform.ecs.system.Timer;
import io.github.mwttg.games.platform.ecs.system.draw.SpriteSystem;
import io.github.mwttg.games.platform.ecs.system.movement.PlayerMovementSystem;
import org.joml.Matrix4f;

public record PlayerEntity(long windowId,
                    TransformComponent transformComponent,
                    SpriteComponent spriteComponent,
                    PlayerInputComponent inputComponent,
                    KinematicComponent kinematicComponent,
                    Timer timer,
                    PlayerConfiguration playerConfiguration,
                    SceneConfiguration sceneConfiguration) {

  public static PlayerEntity create(final long windowId,
                                    final String textureFilename,
                                    final PlayerConfiguration playerConfiguration,
                                    final SceneConfiguration sceneConfiguration) {
    final var transformComponent = new TransformComponent(10.0f, 9.0f);
    final SpriteComponent spriteComponent = createSpriteComponent(textureFilename);
    final var playerInputComponent = new PlayerInputComponent();
    final var playerKinematicComponent = new KinematicComponent();
    final var timer = new Timer();

    return new PlayerEntity(windowId,
        transformComponent,
        spriteComponent,
        playerInputComponent,
        playerKinematicComponent,
        timer,
        playerConfiguration,
        sceneConfiguration);
  }

  private static SpriteComponent createSpriteComponent(final String textureFilename) {
    final var textureId = Texture.create(textureFilename);
    final var planeData = PlaneFactory.create(1, 1);
    return SpriteComponent.create(planeData.geometry(), planeData.textureCoordinates(), textureId);
  }

  public void updateLogic() {
    PlayerMovementSystem.update(windowId, inputComponent, kinematicComponent, playerConfiguration, sceneConfiguration, timer,
        transformComponent);
  }

  public void updateRender(final Matrix4f viewMatrix, final Matrix4f projectionMatrix) {
    SpriteSystem.draw(spriteComponent, transformComponent.getModelMatrix(), viewMatrix, projectionMatrix);
  }
}
