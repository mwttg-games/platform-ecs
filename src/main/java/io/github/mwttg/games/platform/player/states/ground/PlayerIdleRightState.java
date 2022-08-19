package io.github.mwttg.games.platform.player.states.ground;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.KeyInput;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerIdleRightState extends PlayerIdleState {

  public PlayerIdleRightState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                              final PlayerStateComponent playerStateComponent,
                              final Matrix4f transform,
                              final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return IDLE_RIGHT;
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
  public void handleStateTransitions(final PlayerInput playerInput, final SolidGridComponent solidGridComponent) {
    toWalkRight(playerInput.xAxis());
    toWalkLeft(playerInput.xAxis());
    toJumpUp(playerInput.jump());
  }

  private void toWalkRight(final int xAxis) {
    if (xAxis == 1) {
      getPlayerStateComponent().switchToWalkRightState();
    }
  }

  private void toWalkLeft(final int xAxis) {
    if (xAxis == -1) {
      getPlayerStateComponent().switchToWalkLeftState();
    }
  }

  private void toJumpUp(final KeyInput jump) {
    if (jump.isPressed()) {
      getPlayerStateComponent().switchToJumpUpRightState();
    }
  }
}
