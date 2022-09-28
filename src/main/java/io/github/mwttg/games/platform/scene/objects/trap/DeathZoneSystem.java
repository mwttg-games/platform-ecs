package io.github.mwttg.games.platform.scene.objects.trap;

import io.github.mwttg.games.platform.entity.PlayerEntity;
import io.github.mwttg.games.platform.entity.WorldEntity;
import io.github.mwttg.games.platform.player.TransformUtilities;
import org.joml.primitives.Rectanglef;

public final class DeathZoneSystem {

  private static final float SHRINK = 0.1f;

  public static void isPlayerDead(final WorldEntity world, final PlayerEntity player) {
    final var playerBox = playerCollisionBox(player);
    for (final DeathZone deathZone : world.getCurrentScene().sceneObjects().getDeathZones()) {
      if (deathZone.zone().intersectsRectangle(playerBox)) {
        player.playerStateComponent().switchToDeadState();
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
