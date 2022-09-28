package io.github.mwttg.games.platform.scene.objects;

import io.github.mwttg.games.opengl.basic.utilities.geometry.MeshFactory;
import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.draw.Sprite;

public enum ObjectType {

  SPIKE_5L("./data/world1/objects/spikes_length5.png", 5.0f, 1.0f),

  SPIKE_4L("./data/world1/objects/spikes_length4.png", 4.0f, 1.0f),

  RESPAWN_ON("./data/world1/objects/respawn_activated.png", 1.0f, 1.0f),

  RESPAWN_OFF("./data/world1/objects/respawn_deactivated.png", 1.0f, 1.0f);

  private final Drawable drawable;

  ObjectType(final String spriteFilename, final float width, final float height) {
    this.drawable = createSprite(spriteFilename, width, height);
  }

  private static Drawable createSprite(final String spriteFilename, final float width, final float height) {
    final var plane = MeshFactory.createSprite(width, height);
    final var textureId = Texture.create(spriteFilename);
    return Sprite.create(plane.geometry(), plane.textureCoordinates(), textureId);
  }

  public Drawable getDrawable() {
    return drawable;
  }
}
