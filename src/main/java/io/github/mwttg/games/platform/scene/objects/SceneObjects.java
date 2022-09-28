package io.github.mwttg.games.platform.scene.objects;

import io.github.mwttg.games.platform.scene.objects.spawn.Respawn;
import io.github.mwttg.games.platform.scene.objects.trap.DeathZone;
import java.util.Map;
import java.util.Set;

public class SceneObjects {

  private final Set<DeathZone> deathZones;
  private final Map<String, Respawn> respawnsById;

  public SceneObjects(final Set<DeathZone> deathZones, final Map<String, Respawn> respawnsById) {
    this.deathZones = deathZones;
    this.respawnsById = respawnsById;
  }

  public Set<DeathZone> getDeathZones() {
    return deathZones;
  }

  public Respawn getRespawn(final String id) {
    return respawnsById.get(id);
  }

  public Map<String, Respawn> getRespawnsById() {
    return respawnsById;
  }
}
