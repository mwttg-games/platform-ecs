package io.github.mwttg.games.platform.world;

import io.github.mwttg.games.basic.utilities.files.JsonFile;
import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.PlaneFactory;
import io.github.mwttg.games.platform.camera.CameraType;
import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.draw.Sprite;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import java.util.Map;
import org.joml.primitives.Rectanglef;

/**
 * Used for parsing a world definition json file.
 */
public record SceneDefinition(SceneId sceneId,
                              Dimension dimension,
                              CameraType cameraType,
                              String solidGrid,
                              String sprite,
                              String background,
                              Map<Rectanglef, Destination> connections) {

  public record Dimension(int width, int height) {
  }

  public static Scene create(final String filename) {
    final var definition = JsonFile.readFrom(filename, SceneDefinition.class);
    final var spriteComponent = createSprite(definition);
    final var background = createBackgroundSprite(definition);
    final var sensorComponent = new SensorComponent(definition.solidGrid());
    final var connections = definition.connections();

    return new Scene(definition.sceneId(),
        definition.cameraType(),
        definition.dimension(),
        sensorComponent,
        connections,
        spriteComponent,
        background);
  }

  private static Drawable createSprite(final SceneDefinition sceneDefinition) {
    final var dimension = sceneDefinition.dimension();
    final var plane = PlaneFactory.create(dimension.width(), dimension.height());
    final var filename = sceneDefinition.sprite();
    final var textureId = Texture.create(filename);

    return Sprite.create(plane.geometry(), plane.textureCoordinates(), textureId);
  }

  private static Drawable createBackgroundSprite(final SceneDefinition sceneDefinition) {
    final var dimension = sceneDefinition.dimension();
    final var plane = PlaneFactory.create(dimension.width(), dimension.height());
    final var filename = sceneDefinition.background();
    final var textureId = Texture.create(filename);

    return Sprite.create(plane.geometry(), plane.textureCoordinates(), textureId);
  }
}