package io.github.mwttg.games.platform.entity;

import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerDefinition;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.PlayerStateSystem;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.effect.PlayerEffectSystem;
import java.util.Map;
import org.joml.Matrix4f;

public record PlayerEntity(long windowId,
                           Matrix4f transform,
                           Map<String, Drawable> drawableByName,
                           PlayerStateComponent playerStateComponent,
                           PlayerEffectComponent playerEffectComponent,
                           PlayerData playerData) {

  public static PlayerEntity create(final long windowId,
                                    final float x,
                                    final float y,
                                    final String jsonDefinitionFile) {
    final var drawableByName = PlayerDefinition.createDrawablesByName(jsonDefinitionFile);
    final var transform = new Matrix4f().translate(x, y, 0.0f);
    final var playerData = new PlayerData();
    final var playerEffectComponent = new PlayerEffectComponent(drawableByName);
    final var playerStateComponent = new PlayerStateComponent(drawableByName, playerEffectComponent, transform, playerData);
    return new PlayerEntity(windowId, transform, drawableByName, playerStateComponent, playerEffectComponent,
        playerData);
  }

  public void draw(final Matrix4f viewMatrix, final Matrix4f projectionMatrix) {
    playerStateComponent.getCurrentState().draw(transform, viewMatrix, projectionMatrix);
    PlayerEffectSystem.draw(playerEffectComponent, viewMatrix, projectionMatrix);
  }

  public void update(final float deltaTime, final SensorComponent sensorComponent) {
    PlayerStateSystem.update(windowId, playerStateComponent, deltaTime, sensorComponent);
    PlayerEffectSystem.update(playerEffectComponent);
  }

  public void setPosition(float x, float y) {
    transform.translation(x, y, 0.0f);
  }
}
