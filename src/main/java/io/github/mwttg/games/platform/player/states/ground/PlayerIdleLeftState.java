package io.github.mwttg.games.platform.player.states.ground;

import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.input.PlayerInput;
import io.github.mwttg.games.platform.player.FacingDirection;
import io.github.mwttg.games.platform.player.PlayerData;
import io.github.mwttg.games.platform.player.PlayerStateComponent;
import io.github.mwttg.games.platform.player.SolidGridComponent;
import java.util.Map;
import org.joml.Matrix4f;

public final class PlayerIdleLeftState extends PlayerIdleState {

  public PlayerIdleLeftState(final Map<String, SpriteAnimationComponent> animationComponentByName,
                             final PlayerStateComponent playerStateComponent,
                             final Matrix4f transform,
                             final PlayerData playerData) {
    super(animationComponentByName, playerStateComponent, transform, playerData);
  }

  @Override
  protected String getAnimationName() {
    return IDLE_LEFT;
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
  public void handleStateTransitions(final PlayerInput playerInput, final SolidGridComponent solidGridComponent) {
    toWalkLeft(playerInput);
    toWalkRight(playerInput);
    toJumpUp(playerInput);
  }

  private void toWalkLeft(final PlayerInput playerInput) {
    if (playerInput.xAxis() == -1) {
      getPlayerStateComponent().switchToWalkLeftState();
    }
  }

  private void toWalkRight(final PlayerInput playerInput) {
    if (playerInput.xAxis() == 1) {
      getPlayerStateComponent().switchToWalkRightState();
    }
  }

  private void toJumpUp(final PlayerInput playerInput) {
    if (playerInput.jump()) {
      getPlayerStateComponent().switchToJumpUpLeftState();
    }
  }
}
