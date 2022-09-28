package io.github.mwttg.games.platform.entity;

import io.github.mwttg.games.platform.scene.Destination;
import io.github.mwttg.games.platform.scene.Scene;
import io.github.mwttg.games.platform.scene.SceneChangeSystem;
import io.github.mwttg.games.platform.scene.SceneId;
import io.github.mwttg.games.platform.scene.objects.SceneObjectsSystem;
import io.github.mwttg.games.platform.world.ActiveRespawn;
import io.github.mwttg.games.platform.world.WorldData;
import java.util.Map;
import org.joml.Matrix4f;

public class WorldEntity {

  private static final Matrix4f TRANSFORM = new Matrix4f().translate(0.0f, 0.0f, -1.0f);
  private static final Matrix4f BACKGROUND_TRANSFORM = new Matrix4f().translate(0.0f, 0.0f, -2.0f);

  private final Map<SceneId, Scene> scenesById;
  private Scene currentScene;
  private final WorldData worldData;

  public WorldEntity(final SceneId currentSceneId, final Map<SceneId, Scene> scenesById, final ActiveRespawn activeRespawn) {
    this.scenesById = scenesById;
    this.currentScene = scenesById.get(currentSceneId);
    this.worldData = new WorldData(activeRespawn);
  }

  public void update(final PlayerEntity playerEntity) {
    SceneObjectsSystem.update(this, playerEntity);
    SceneChangeSystem.update(this, playerEntity);
  }

  public void draw(final Matrix4f viewMatrix, final Matrix4f projectionMatrix) {
    currentScene.background().draw(BACKGROUND_TRANSFORM, viewMatrix, projectionMatrix);
    SceneObjectsSystem.draw(this, viewMatrix, projectionMatrix);
    currentScene.sprite().draw(TRANSFORM, viewMatrix, projectionMatrix);
  }

  public Scene getCurrentScene() {
    return currentScene;
  }

  public void changeScene(final SceneId sceneId) {
    this.currentScene = scenesById.get(sceneId);
  }

  public Map<SceneId, Scene> getScenesById() {
    return scenesById;
  }

  public WorldData getWorldData() {
    return worldData;
  }
}
