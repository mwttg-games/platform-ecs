package io.github.mwttg.games.platform.player.states.ground;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.KeyInput;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import io.github.mwttg.games.platform.player.SolidGridSystem;
import io.github.mwttg.games.platform.player.physics.MoveLeft;
import java.util.Map;
import org.joml.Matrix4f;

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
    super.enter();
    getPlayerData().setFacingDirection(FacingDirection.LEFT);
  }

  @Override
  public void exit() {
    super.exit();
  }

  @Override
  public void update(final float deltaTime, final PlayerInput playerInput, final SolidGridComponent solidGridComponent) {
    MoveLeft.execute(deltaTime, getPlayerData(), getTransform(), solidGridComponent);
  }

  @Override
  public void handleStateTransitions(final PlayerInput playerInput, final SolidGridComponent solidGridComponent) {
    toIdleLeft(playerInput.xAxis());
    toWalkRight(playerInput.xAxis());
    toJumpUpLeft(playerInput.jump());

    final var onGround = SolidGridSystem.isGroundTouched(getTransform(), getPlayerData().getTileSize(), solidGridComponent);
    toFallDownLeft(onGround);
  }

  private void toIdleLeft(final int xAxis) {
    if (xAxis == 0) {
      getPlayerStateComponent().switchToIdleLeftState();
    }
  }

  private void toJumpUpLeft(final KeyInput jump) {
    if (jump.isPressed()) {
      getPlayerStateComponent().switchToJumpUpLeftState();
    }
  }

  private void toFallDownLeft(final boolean onGround) {
    if (!onGround) {
      getPlayerStateComponent().switchToFallDownLeftState();
    }
  }

  private void toWalkRight(final int xAxis) {
    if (xAxis == 1) {
      getPlayerStateComponent().switchToWalkRightState();
    }
  }
}
