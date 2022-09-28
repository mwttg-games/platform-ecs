package io.github.mwttg.games.platform.scene;

import io.github.mwttg.games.basic.utilities.files.JsonFile;
import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.PlaneFactory;
import io.github.mwttg.games.platform.camera.CameraType;
import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.draw.Sprite;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.scene.objects.ObjectType;
import io.github.mwttg.games.platform.scene.objects.SceneObjects;
import io.github.mwttg.games.platform.scene.objects.spawn.Respawn;
import io.github.mwttg.games.platform.scene.objects.trap.DeathZone;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
                              Map<Rectanglef, Destination> connections,
                              Map<String, Rectanglef> respawns,
                              Set<DeathZoneDefinition> deathZones) {

  public record Dimension(int width, int height) {
  }

  record DeathZoneDefinition(Rectanglef zone, ObjectType type) {
  }

  public static Scene create(final String filename) {
    final var definition = JsonFile.readFrom(filename, SceneDefinition.class);
    final var spriteComponent = createSprite(definition);
    final var background = createBackgroundSprite(definition);
    final var sensorComponent = new SensorComponent(definition.solidGrid());
    final var connections = definition.connections();
    final var sceneObjects = createSceneObjects(definition);

    return new Scene(definition.sceneId(),
        definition.cameraType(),
        definition.dimension(),
        sensorComponent,
        connections,
        spriteComponent,
        background,
        sceneObjects);
  }

  private static SceneObjects createSceneObjects(final SceneDefinition sceneDefinition) {
    final Set<DeathZone> deathZones = new HashSet<>();
    for (final DeathZoneDefinition item : sceneDefinition.deathZones()) {
      deathZones.add(DeathZone.create(item.zone(), item.type().getDrawable()));
    }

    final Map<String, Respawn> respawnsById = new HashMap<>();
    for (final Map.Entry<String, Rectanglef> entry : sceneDefinition.respawns().entrySet()) {
      respawnsById.put(entry.getKey(), Respawn.create(entry.getValue()));
    }

    return new SceneObjects(deathZones, respawnsById);
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