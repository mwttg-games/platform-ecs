package io.github.mwttg.games.platform.scene.objects;

import io.github.mwttg.games.platform.entity.PlayerEntity;
import io.github.mwttg.games.platform.entity.WorldEntity;
import io.github.mwttg.games.platform.scene.objects.spawn.Respawn;
import io.github.mwttg.games.platform.scene.objects.spawn.RespawnSystem;
import io.github.mwttg.games.platform.scene.objects.trap.DeathZone;
import io.github.mwttg.games.platform.scene.objects.trap.DeathZoneSystem;
import java.util.Map;
import org.joml.Matrix4f;

public final class SceneObjectsSystem {

  public static void update(final WorldEntity worldEntity, final PlayerEntity playerEntity) {
    RespawnSystem.update(worldEntity, playerEntity);
    DeathZoneSystem.isPlayerDead(worldEntity, playerEntity);
  }

  public static void draw(final WorldEntity world, final Matrix4f view, final Matrix4f projection) {
    drawDeathZones(world, view, projection);
    drawRespawn(world, view, projection);
  }

  private static void drawRespawn(final WorldEntity world, final Matrix4f view, final Matrix4f projection) {
    for (final Map.Entry<String, Respawn> entry : world.getCurrentScene().sceneObjects().getRespawnsById().entrySet()) {
      if (world.getWorldData().getActiveRespawn().getSceneId() == world.getCurrentScene().sceneId()
          && entry.getKey().equals(world.getWorldData().getActiveRespawn().getRespawnId())) {
        ObjectType.RESPAWN_ON.getDrawable().draw(entry.getValue().transform(), view, projection);
      } else {
        ObjectType.RESPAWN_OFF.getDrawable().draw(entry.getValue().transform(), view, projection);
      }
    }
  }

  private static void drawDeathZones(final WorldEntity world, final Matrix4f view, final Matrix4f projection) {
    for (final DeathZone deathZone : world.getCurrentScene().sceneObjects().getDeathZones()) {
      deathZone.draw(view, projection);
    }
  }
}
