package io.github.mwttg.games.platform.player.states.ground;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector2i;

public abstract class PlayerIdleState extends PlayerOnGroundState {

  public PlayerIdleState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                         final PlayerStateComponent playerStateComponent,
                         final Matrix4f transform,
                         final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, transform, playerData);
  }

  @Override
  public void update(final float deltaTime, final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    // nothing to do
  }

  @Override
  public void handleStateTransitions(final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    if (inputVector.x() == -1) {
      getPlayerStateComponent().switchToWalkLeftState();
    }

    if (inputVector.x() == 1) {
      getPlayerStateComponent().switchToWalkRightState();
    }

    if (inputVector.y() == 1 && getPlayerData().getFacingDirection() == FacingDirection.RIGHT) {
      getPlayerStateComponent().switchToJumpUpRightState();
    }

    if (inputVector.y() == 1 && getPlayerData().getFacingDirection() == FacingDirection.LEFT) {
      getPlayerStateComponent().switchToJumpUpLeftState();
    }
  }
}
