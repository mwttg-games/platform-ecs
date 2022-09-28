package io.github.mwttg.games.platform.scene.objects.spawn;

import io.github.mwttg.games.platform.entity.PlayerEntity;
import io.github.mwttg.games.platform.entity.WorldEntity;
import io.github.mwttg.games.platform.player.TransformUtilities;
import io.github.mwttg.games.platform.player.states.dead.PlayerReviveState;
import java.util.Map;
import org.joml.primitives.Rectanglef;

public final class RespawnSystem {

  private static final float SHRINK = 0.1f;

  public static void update(final WorldEntity world, final PlayerEntity player) {
    activateRespawn(world, player);
    respawnIfNecessary(world, player);
  }

  private static void respawnIfNecessary(final WorldEntity world, final PlayerEntity player) {
    if (player.playerStateComponent().getCurrentState() instanceof PlayerReviveState) {
      final var sceneId = world.getWorldData().getActiveRespawn().getSceneId();
      final var respawnId = world.getWorldData().getActiveRespawn().getRespawnId();
      final var transform = world.getScenesById().get(sceneId).sceneObjects().getRespawn(respawnId).transform();
      final var position = TransformUtilities.getPosition(transform);
      player.setPosition(position.x(), position.y());
      world.changeScene(sceneId);
    }
  }

  private static void activateRespawn(final WorldEntity world, final PlayerEntity player) {
    final var playerBox = playerCollisionBox(player);
    for (final Map.Entry<String, Respawn> entry : world.getCurrentScene().sceneObjects().getRespawnsById().entrySet()) {
      final var spawnBox = entry.getValue().zone();
      if (playerBox.intersectsRectangle(spawnBox)) {
        final var currentScene = world.getCurrentScene();
        final var id = entry.getKey();
        world.getWorldData().getActiveRespawn().changeRespawn(currentScene, id);
      }
    }
  }

  private static Rectanglef playerCollisionBox(final PlayerEntity playerEntity) {
    final var tileSize = playerEntity.playerData().getTileSize();
    final var position = TransformUtilities.getPosition(playerEntity.transform());
    return new Rectanglef(position.x() + SHRINK,
        position.y(),
        position.x() + tileSize.width() - SHRINK,
        position.y() + tileSize.height() - SHRINK);
  }
}
