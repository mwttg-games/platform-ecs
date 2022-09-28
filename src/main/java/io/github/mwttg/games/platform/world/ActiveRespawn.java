package io.github.mwttg.games.platform.world;

import io.github.mwttg.games.platform.scene.Scene;
import io.github.mwttg.games.platform.scene.SceneId;
import io.github.mwttg.games.platform.scene.objects.spawn.Respawn;
import java.util.Objects;
import java.util.StringJoiner;
import org.joml.Vector2f;

public class ActiveRespawn {

  private SceneId sceneId;
  private String respawnId;

  public ActiveRespawn(final SceneId sceneId, final String respawnId) {
    this.sceneId = sceneId;
    this.respawnId = respawnId;
  }

  public void changeRespawn(final Scene scene, final String respawnId) {
    this.sceneId = scene.sceneId();
    this.respawnId = respawnId;
  }

  public SceneId getSceneId() {
    return sceneId;
  }

  public String getRespawnId() {
    return respawnId;
  }
}
