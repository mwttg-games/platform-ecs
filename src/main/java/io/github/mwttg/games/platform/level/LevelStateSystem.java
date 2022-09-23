package io.github.mwttg.games.platform.level;

import io.github.mwttg.games.platform.entity.PlayerEntity;
import io.github.mwttg.games.platform.player.TransformUtilities;
import org.joml.primitives.Rectanglef;

public class LevelStateSystem {

  private static final float SHRINK = 0.45f;

  public static void update(final LevelStateComponent levelStateComponent, final PlayerEntity playerEntity) {
    final var connections = levelStateComponent.getLevelComponent().levelConnections();
    final var connectorBoxes = connections.keySet();
    final var playerBox = playerCollisionBox(playerEntity);

    for (final Rectanglef box : connectorBoxes) {
      if (box.intersectsRectangle(playerBox)) {
        final var destination = connections.get(box);
        levelStateComponent.changeLevel(destination);

        final var x = destination.spawnPosition().x();
        final var y = destination.spawnPosition().y();
        playerEntity.setPosition(x, y);
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
