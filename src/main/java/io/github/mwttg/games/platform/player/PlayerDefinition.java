package io.github.mwttg.games.platform.player;

import io.github.mwttg.games.basic.utilities.files.JsonFile;
import io.github.mwttg.games.opengl.basic.utilities.geometry.MeshFactory;
import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.draw.AnimatedSprite;
import io.github.mwttg.games.platform.draw.Drawable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used for parsing a player definition json file.
 */
public record PlayerDefinition(TileSize tileSize, Map<String, AnimationDefinition> animations) {

  public record AnimationDefinition(int frames, String spriteSheet, List<Integer> timings) {
  }

  public static Map<String, Drawable> createDrawablesByName(final String filename) {
    final var definition = JsonFile.readFrom(filename, PlayerDefinition.class);
    final var tileSize = definition.tileSize;

    final var result = new HashMap<String, Drawable>();

    for (final Map.Entry<String, AnimationDefinition> entry : definition.animations.entrySet()) {
      final var key = entry.getKey();
      final var value = entry.getValue();

      if (value.frames() != value.timings().size()) {
        throw new RuntimeException("player definition file is wrong (frames != timings (count))");
      }

      final var plane = MeshFactory.createAnimatedSprite(value.frames(), tileSize.width(), tileSize.height());
      final var textureId = Texture.create(value.spriteSheet());
      final var component = AnimatedSprite.create(plane.geometry(),
          plane.textureCoordinates(),
          textureId, value.timings());
      result.put(key, component);
    }

    return result;
  }
}
