package io.github.mwttg.games.platform.entity;

import io.github.mwttg.games.basic.utilities.files.JsonFile;
import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.PlaneFactory;
import io.github.mwttg.games.platform.camera.CameraType;
import io.github.mwttg.games.platform.draw.SpriteComponent;
import io.github.mwttg.games.platform.level.Destination;
import io.github.mwttg.games.platform.level.LevelComponent;
import io.github.mwttg.games.platform.level.LevelId;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import java.util.Map;
import org.joml.primitives.Rectanglef;

public record LevelDefinition(LevelId levelId,
                              Dimension dimension,
                              CameraType cameraType,
                              String solidGrid,
                              String sprite,
                              String background,
                              Map<Rectanglef, Destination> connections) {

  public record Dimension(int width, int height) {
  }

  public static LevelComponent create(final String filename) {
    final var definition = JsonFile.readFrom(filename, LevelDefinition.class);
    final var spriteComponent = createSprite(definition);
    final var background = createBackgroundSprite(definition);
    final var sensorComponent = new SensorComponent(definition.solidGrid());
    final var connections = definition.connections();

    return new LevelComponent(definition.levelId(),
        definition.cameraType(),
        definition.dimension(),
        sensorComponent,
        connections,
        spriteComponent,
        background);
  }

  private static SpriteComponent createSprite(final LevelDefinition levelDefinition) {
    final var dimension = levelDefinition.dimension();
    final var plane = PlaneFactory.create(dimension.width(), dimension.height());
    final var filename = levelDefinition.sprite();
    final var textureId = Texture.create(filename);

    return SpriteComponent.create(plane.geometry(), plane.textureCoordinates(), textureId);
  }

  private static SpriteComponent createBackgroundSprite(final LevelDefinition levelDefinition) {
    final var dimension = levelDefinition.dimension();
    final var plane = PlaneFactory.create(dimension.width(), dimension.height());
    final var filename = levelDefinition.background();
    final var textureId = Texture.create(filename);

    return SpriteComponent.create(plane.geometry(), plane.textureCoordinates(), textureId);
  }
}