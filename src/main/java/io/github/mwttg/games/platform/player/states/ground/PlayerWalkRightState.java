package io.github.mwttg.games.platform.player.states.ground;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import io.github.mwttg.games.platform.player.SolidGridSystem;
import io.github.mwttg.games.platform.player.physics.MoveRight;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerWalkRightState extends PlayerWalkState {

  public PlayerWalkRightState(final Map<String, SpriteAnimationComponent> animationComponentByName,
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
  public void update(final float deltaTime, final PlayerInput playerInput, final SolidGridComponent solidGridComponent) {
    MoveRight.execute(deltaTime, getPlayerData(), getTransform(), solidGridComponent);
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SolidGridComponent solidGridComponent) {
    toIdleRight(playerInput);
    toWalkLeft(playerInput);
    toJumpUpRight(playerInput);

    final var onGround = SolidGridSystem.isGroundTouched(getTransform(), getPlayerData().getTileSize(), solidGridComponent);
    toFallDownRight(onGround);
  }

  private void toIdleRight(final PlayerInput playerInput) {
    if (playerInput.xAxis() == 0) {
      getPlayerStateComponent().switchToIdleRightState();
    }
  }

  private void toJumpUpRight(final PlayerInput playerInput) {
    if (playerInput.jump()) {
      getPlayerStateComponent().switchToJumpUpRightState();
    }
  }

  private void toFallDownRight(final boolean onGround) {
    if (!onGround) {
      getPlayerStateComponent().switchToFallDownRightState();
    }
  }

  private void toWalkLeft(final PlayerInput playerInput) {
    if (playerInput.xAxis() == -1) {
      getPlayerStateComponent().switchToWalkLeftState();
    }
  }
}
