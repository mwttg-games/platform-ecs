package io.github.mwttg.games.platform.player.states.ground;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import io.github.mwttg.games.platform.player.SolidGridSystem;
import io.github.mwttg.games.platform.player.physics.MoveLeft;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector2i;

public final class PlayerWalkLeftState extends PlayerWalkState {

  public PlayerWalkLeftState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                             final PlayerStateComponent playerStateComponent,
                             final Matrix4f transform,
                             final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return WALK_LEFT;
  }

  @Override
  public void enter() {
    getPlayerData().setFacingDirection(FacingDirection.LEFT);
  }

  @Override
  public void update(final float deltaTime, final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    MoveLeft.execute(deltaTime, getPlayerData(), getTransform(), solidGridComponent);
  }

  @Override
  public void handleStateTransitions(final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    if (inputVector.x() == 0) {
      getPlayerStateComponent().switchToIdleLeftState();
    }

    if (inputVector.x() == 1) {
      getPlayerStateComponent().switchToWalkRightState();
    }

    if (inputVector.y() == 1) {
      getPlayerStateComponent().switchToJumpUpLeftState();
    }

    final var onGround = SolidGridSystem.isGroundTouched(getTransform(), getPlayerData().getTileSize(), solidGridComponent);
    if (!onGround) {
      getPlayerStateComponent().switchToFallDownLeftState();
    }
  }
}