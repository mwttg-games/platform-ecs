package io.github.mwttg.games.platform.entity;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.PlayerStateSystem;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.effect.PlayerEffectSystem;
import java.util.Map;
import org.joml.Matrix4f;

public record PlayerEntity(long windowId,
                           Matrix4f transform,
                           Map<String, SpriteAnimationComponent> animationComponentByName,
                           PlayerStateComponent playerStateComponent,
                           PlayerEffectComponent playerEffectComponent,
                           PlayerData playerData) {

  public static PlayerEntity create(final long windowId,
                                    final Map<String, SpriteAnimationComponent> animationComponentByName,
                                    final float x,
                                    final float y) {
    final var transform = new Matrix4f().translate(x, y, 0.0f);
    final var playerData = new PlayerData();
    final var playerEffectComponent = new PlayerEffectComponent(animationComponentByName);
    final var playerStateComponent =
        new PlayerStateComponent(animationComponentByName, playerEffectComponent, transform, playerData);
    return new PlayerEntity(windowId, transform, animationComponentByName, playerStateComponent, playerEffectComponent,
        playerData);
  }

  public void draw(final Matrix4f viewMatrix, final Matrix4f projectionMatrix) {
    playerStateComponent.getCurrentState().draw(transform, viewMatrix, projectionMatrix);
    PlayerEffectSystem.draw(playerEffectComponent, viewMatrix, projectionMatrix);
  }

  public void update(final float deltaTime, final SolidGridComponent solidGridComponent) {
    PlayerStateSystem.update(windowId, playerStateComponent, deltaTime, solidGridComponent);
    PlayerEffectSystem.update(playerEffectComponent);
  }
}
