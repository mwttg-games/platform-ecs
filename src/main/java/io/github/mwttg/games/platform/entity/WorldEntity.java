package io.github.mwttg.games.platform.entity;

import io.github.mwttg.games.platform.world.Destination;
import io.github.mwttg.games.platform.world.Scene;
import io.github.mwttg.games.platform.world.SceneChangeSystem;
import io.github.mwttg.games.platform.world.SceneId;
import java.util.Map;
import org.joml.Matrix4f;

public class WorldEntity {

  private static final Matrix4f TRANSFORM = new Matrix4f().translate(0.0f, 0.0f, -1.0f);
  private static final Matrix4f BACKGROUND_TRANSFORM = new Matrix4f().translate(0.0f, 0.0f, -2.0f);

  private SceneId currentSceneId;
  private final Map<SceneId, Scene> scenesById;

  public WorldEntity(final SceneId currentSceneId, final Map<SceneId, Scene> scenesById) {
    this.currentSceneId = currentSceneId;
    this.scenesById = scenesById;
  }

  public void update(final PlayerEntity playerEntity) {
    SceneChangeSystem.update(this, playerEntity);
  }

  public void draw(final Matrix4f viewMatrix, final Matrix4f projectionMatrix) {
    scenesById.get(currentSceneId).background().draw(BACKGROUND_TRANSFORM, viewMatrix, projectionMatrix);
    scenesById.get(currentSceneId).sprite().draw(TRANSFORM, viewMatrix, projectionMatrix);
  }

  public Scene getCurrentScene() {
    return scenesById.get(currentSceneId);
  }

  public void changeScene(final Destination destination) {
    this.currentSceneId = destination.sceneId();
  }
}
