package io.github.mwttg.games.platform.player.states.ladder;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.colision.SensorComponent;
import io.github.mwttg.games.platform.player.colision.SensorSystem;
import io.github.mwttg.games.platform.player.effect.PlayerEffectComponent;
import io.github.mwttg.games.platform.player.physics.ClimbDown;
import io.github.mwttg.games.platform.player.physics.ClimbUp;
import io.github.mwttg.games.platform.player.physics.SnapToGrid;
import io.github.mwttg.games.platform.player.states.AbstractPlayerState;
import java.util.Map;
import org.joml.Matrix4f;

public class PlayerOnLadderState extends AbstractPlayerState {

  public PlayerOnLadderState(
      final Map<String, SpriteAnimationComponent> animationComponentByName,
      final PlayerStateComponent playerStateComponent,
      final PlayerEffectComponent playerEffectComponent,
      final Matrix4f transform,
      final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, playerEffectComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return ON_LADDER;
  }

  @Override
  public void enter() {
    super.enter();
    getPlayerData().resetJumpCounter();
    SnapToGrid.snapX(getTransform(), getPlayerData());
  }

  @Override
  public void update(final float deltaTime, final PlayerInput playerInput, final SensorComponent sensorComponent) {
    if (playerInput.yAxis() == 1) {
      ClimbUp.execute(deltaTime, getPlayerData(), getTransform(), sensorComponent);
    }

    if (playerInput.yAxis() == -1) {
      ClimbDown.execute(deltaTime, getPlayerData(), getTransform(), sensorComponent);
    }
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SensorComponent sensorComponent) {
    if (playerInput.xAxis() == -1) {
      getPlayerStateComponent().switchToWalkLeftState();
    }

    if (playerInput.xAxis() == 1) {
      getPlayerStateComponent().switchToWalkRightState();
    }

    if (playerInput.jump() && getPlayerData().getFacingDirection() == FacingDirection.LEFT) {
      getPlayerStateComponent().switchToJumpUpLeftState();
    }

    if (playerInput.jump() && getPlayerData().getFacingDirection() == FacingDirection.RIGHT) {
      getPlayerStateComponent().switchToJumpUpRightState();
    }

    final var onLadder = SensorSystem.isOnLadder(getTransform(), getPlayerData().getTileSize(), sensorComponent);
    if (!onLadder && getPlayerData().getFacingDirection() == FacingDirection.LEFT) {
      getPlayerStateComponent().switchToFallDownLeftState();
    }

    if (!onLadder && getPlayerData().getFacingDirection() == FacingDirection.RIGHT) {
      getPlayerStateComponent().switchToFallDownRightState();
    }

    final var onGround = SensorSystem.isGroundTouchedFromLadder(getTransform(), getPlayerData().getTileSize(), sensorComponent);
    if (onGround && getPlayerData().getFacingDirection() == FacingDirection.LEFT) {
      getPlayerStateComponent().switchToIdleLeftState();
    }

    if (onGround && getPlayerData().getFacingDirection() == FacingDirection.RIGHT) {
      getPlayerStateComponent().switchToIdleRightState();
    }
  }
}
