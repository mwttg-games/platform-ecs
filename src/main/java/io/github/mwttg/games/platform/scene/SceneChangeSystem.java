package io.github.mwttg.games.platform.scene;

import io.github.mwttg.games.platform.entity.PlayerEntity;
import io.github.mwttg.games.platform.entity.WorldEntity;
import io.github.mwttg.games.platform.player.TransformUtilities;
import org.joml.primitives.Rectanglef;

public class SceneChangeSystem {

  private static final float SHRINK = 0.45f;

  public static void update(final WorldEntity world, final PlayerEntity player) {
    final var connections = world.getCurrentScene().levelConnections();
    final var connectorBoxes = connections.keySet();
    final var playerBox = playerCollisionBox(player);

    for (final Rectanglef box : connectorBoxes) {
      if (box.intersectsRectangle(playerBox)) {
        final var destination = connections.get(box);
        world.changeScene(destination.sceneId());

        final var x = destination.spawnPosition().x();
        final var y = destination.spawnPosition().y();
        player.setPosition(x, y);
        return;
      }
    }
  }

  private static Rectanglef playerCollisionBox(final PlayerEntity playerEntity) {
    final var tileSize = playerEntity.playerData().getTileSize();
    final var position = TransformUtilities.getPosition(playerEntity.transform());
    return new Rectanglef(position.x() + SHRINK,
        position.y(),
        position.x() + tileSize.width() - SHRINK,
        position.y() + tileSize.height());
  }
}
