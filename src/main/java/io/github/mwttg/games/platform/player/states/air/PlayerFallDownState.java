package io.github.mwttg.games.platform.player.states.air;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector2i;

public abstract class PlayerFallDownState extends PlayerInAirState {

  public PlayerFallDownState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                             final PlayerStateComponent playerStateComponent,
                             final Matrix4f transform,
                             final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, transform, playerData);
  }

  protected void handleStateTransitions(final Vector2i inputVector, final boolean onGround) {
    if (onGround && inputVector.x() == 1) {
      getPlayerStateComponent().switchToWalkRightState();
    }

    if (onGround && inputVector.x() == -1) {
      getPlayerStateComponent().switchToWalkLeftState();
    }

    if (onGround && inputVector.x() == 0 && getPlayerData().getFacingDirection() == FacingDirection.RIGHT) {
      getPlayerStateComponent().switchToIdleRightState();
    }

    if (onGround && inputVector.x() == 0 && getPlayerData().getFacingDirection() == FacingDirection.LEFT) {
      getPlayerStateComponent().switchToIdleLeftState();
    }
  }
}
