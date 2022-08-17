package io.github.mwttg.games.platform.player.states.air;

import io.github.mwttg.games.platform.Configuration;
import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import io.github.mwttg.games.platform.player.SolidGridSystem;
import io.github.mwttg.games.platform.player.physics.JumpUp;
import io.github.mwttg.games.platform.player.physics.MoveRight;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector2i;

public final class PlayerJumpUpRightState extends PlayerJumpUpState {

  public PlayerJumpUpRightState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                                final PlayerStateComponent playerStateComponent,
                                final Matrix4f transform,
                                final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return WALK_RIGHT;
  }

  @Override
  public void enter() {
    super.enter();
    getPlayerData().setFacingDirection(FacingDirection.RIGHT);
  }

  @Override
  public void exit() {
    super.exit();
  }

  @Override
  public void update(final float deltaTime, final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    if (inputVector.x() == 1) {
      MoveRight.execute(deltaTime, getPlayerData(), getTransform(), solidGridComponent);
    }
    JumpUp.execute(getInAirTime(), deltaTime, getPlayerData(), getTransform(), solidGridComponent);
    updateInAirTime(deltaTime);
  }

  @Override
  public void handleStateTransitions(final Vector2i inputVector, final SolidGridComponent solidGridComponent) {
    if (inputVector.x() == 1 && getInAirTime() >= Configuration.PLAYER_MAX_RISE_TIME) {
      getPlayerStateComponent().switchToFallDownRightState();
    }

    if (inputVector.x() == -1 && getInAirTime() >= Configuration.PLAYER_MAX_RISE_TIME) {
      getPlayerStateComponent().switchToFallDownLeftState();
    }

    if (inputVector.x() == 0 && getInAirTime() >= Configuration.PLAYER_MAX_RISE_TIME) {
      getPlayerStateComponent().switchToFallDownRightState();
    }

    if (inputVector.x() == -1 && getInAirTime() < Configuration.PLAYER_MAX_RISE_TIME) {
      getPlayerStateComponent().switchToJumpUpLeftState(getInAirTime());
    }

    final var isTopBlocked = SolidGridSystem.isTopBlocked(getTransform(), getPlayerData().getTileSize(), solidGridComponent);
    if (isTopBlocked) {
      getPlayerStateComponent().switchToFallDownRightState();
    }
  }
}
