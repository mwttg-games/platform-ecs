package io.github.mwttg.games.platform.player;

import io.github.mwttg.games.basic.utilities.files.JsonFile;
import io.github.mwttg.games.opengl.basic.utilities.geometry.MeshFactory;
import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.draw.AnimatedSprite;
import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.draw.Sprite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used for parsing a player definition json file.
 */
public record PlayerDefinition(TileSize tileSize, Map<String, AnimationDefinition> animations) {

  public record AnimationDefinition(String spriteSheet, int frames, List<Integer> timings) {
  }

  public static Map<String, Drawable> createDrawablesByName(final String filename) {
    final var definition = JsonFile.readFrom(filename, PlayerDefinition.class);
    final var tileSize = definition.tileSize;

    final var result = new HashMap<String, Drawable>();

    for (final Map.Entry<String, AnimationDefinition> entry : definition.animations.entrySet()) {
      final var key = entry.getKey();
      final var value = entry.getValue();
      final var textureId = Texture.create(value.spriteSheet());

      if (value.frames == 0) {
        final var plane = MeshFactory.createSprite(tileSize.width(), tileSize.height());
        final var sprite = Sprite.create(plane.geometry(), plane.textureCoordinates(), textureId);
        result.put(key, sprite);
      } else {

        if (value.frames() != value.timings().size()) {
          throw new RuntimeException("player definition file is wrong (frames != timings (count))");
        }

        final var plane = MeshFactory.createAnimatedSprite(value.frames(), tileSize.width(), tileSize.height());
        final var animation = AnimatedSprite.create(plane.geometry(), plane.textureCoordinates(), textureId, value.timings());
        result.put(key, animation);
      }
    }

    return result;
  }
}
